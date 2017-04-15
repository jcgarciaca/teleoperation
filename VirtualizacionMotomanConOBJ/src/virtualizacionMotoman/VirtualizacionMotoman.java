package virtualizacionMotoman;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import controlP5.*;
import processing.core.*;
import remixlab.proscene.*;

import saito.objloader.*;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class VirtualizacionMotoman extends PApplet{	
	
	Scene scene;
	PGraphics canvas;
	Motoman motoman;
	Cinematica cinematica = new Cinematica(790, 0, 769);	
	
	OBJModel base, ejeS, ejeL, ejeU, ejeR, ejeB, ejeTOp, ejeTCl, mesaPC, mesaTorno, baseMesaRobot;
	OBJModel baseMesaRobot2, mesaPC2, mesaTorno2;
	
	ControlP5 controlP5;
	ListBox tipoMovimiento;
	Button botonGuardar, botonTarea, botonSimular, botonBorrar, botonTimer;
	Button botonSPos, botonSNeg, botonLPos, botonLNeg, botonUPos, botonUNeg, botonRPos, botonRNeg, botonBPos, botonBNeg, botonTPos, botonTNeg;
	Slider valorVelocidad;
	
	Button botonHome, botonGripper, botonIrBanda, usarBanda, botonCarga;
	
	// Botones XYZ
	
	Button botonXPos, botonXNeg, botonYPos, botonYNeg, botonZPos, botonZNeg;
	
	// Botones XYZ
	
	Textarea consola;
	
	int estGripper = 0; // 0 - abierto ----- 1 - cerrado
	int estGripperSim = 0;
	
	int presionaBanda = 0, presionaCarga = 0;
	
	int typMovimiento = 0;
	int velocidad = 60, velParam = 0;
	
	int timmer=0;
	
	// Comunicacion
	int comando=0; // 0 teach, 1 tarea, 2 borrar
	int indice = 0; // 0 posicion, 1 gripper, 2 timmer
	String IPServer="localhost";//168.176.26.91
	int portServer=9002;
	
	int  seguroP1 = 0, seguroP2 = 1, seguro = 0, trabajaConBanda = 0;

	
	PrintWriter salida;	
	
	ArrayList<PuntosTrayectoria> trayectoria = new ArrayList<PuntosTrayectoria>();
	ArrayList<PuntosSimulacion> simulacion = new ArrayList<PuntosSimulacion>();
	ArrayList<Coordenada> drawTrayectoria = new ArrayList<Coordenada>();
	
	float[] valorEjes = {0, 0, 0, 0, 0, 0};
	String tMov = "MOVJ";
	
	
	int numPuntos = 15;
	float deltaS = 0, deltaL = 0, deltaU = 0, deltaR = 0, deltaB = 0, deltaT = 0;
	float[] ejesSimulacion = {0, 0, 0, 0, 0, 0};
			
	int simulando = 0, contador = 0;
	
	float[] ejesDraw = {0,0,0,0,0,0};
	
	controlP5.Textlabel posXLabel, posYLabel, posZLabel, valorXLabel, valorYLabel, valorZLabel, segLabel;
	Textfield temporizador;
	
	Camera camara;
	
	public void setup(){
		size(790, 650);
			
		canvas = createGraphics(570, 650, P3D);        		
		scene = new Scene(this, (PGraphics3D) canvas);
		scene.setAxisIsDrawn(false);
		scene.setGridIsDrawn(false);		
		scene.setFrameSelectionHintIsDrawn(false);
		
		controlP5 = new ControlP5(this);
		controlP5.setAutoDraw(false);		
		
		// Botones Joint
		botonSPos = controlP5.addButton("spos",2,580,20,50,19);
		botonSNeg = controlP5.addButton("sneg",3,640,20,50,19);
		botonLPos = controlP5.addButton("lpos",4,580,45,50,19);
		botonLNeg = controlP5.addButton("lneg",5,640,45,50,19);
		botonUPos = controlP5.addButton("upos",6,580,70,50,19);
		botonUNeg = controlP5.addButton("uneg",7,640,70,50,19);
		botonRPos = controlP5.addButton("rpos",8,580,95,50,19);
		botonRNeg = controlP5.addButton("rneg",9,640,95,50,19);
		botonBPos = controlP5.addButton("bpos",10,580,120,50,19);
		botonBNeg = controlP5.addButton("bneg",11,640,120,50,19);
		botonTPos = controlP5.addButton("tpos",12,580,145,50,19);
		botonTNeg = controlP5.addButton("tneg",13,640,145,50,19);
		// Botones Joint
		
		// Botones XYZ
		botonXPos = controlP5.addButton("xpos", 16, 580, 200, 50, 19);
		botonYPos = controlP5.addButton("ypos", 17, 580, 225, 50, 19);
		botonZPos = controlP5.addButton("zpos", 18, 580, 250, 50, 19);
		
		botonXNeg = controlP5.addButton("xneg", 18, 640, 200, 50, 19);
		botonYNeg = controlP5.addButton("yneg", 19, 640, 225, 50, 19);
		botonZNeg = controlP5.addButton("zneg", 20, 640, 250, 50, 19);
		// Botones XYZ
		
		
		tipoMovimiento = controlP5.addListBox("myList", 580, 330, 100, 80);
        
		tipoMovimiento.setItemHeight(20);
		tipoMovimiento.setBarHeight(20);        
        
		tipoMovimiento.setColorBackground(color(40, 128));
		tipoMovimiento.setColorActive(color(255, 128));

		tipoMovimiento.captionLabel().toUpperCase(true);
		tipoMovimiento.captionLabel().set("Tipo de movimiento");
		tipoMovimiento.captionLabel().setColor(0xffff0000);
		tipoMovimiento.captionLabel().style().marginTop = 3;
		tipoMovimiento.valueLabel().style().marginTop = 3;
		tipoMovimiento.addItem("MOVJ", 0);
		tipoMovimiento.addItem("MOVL", 1);
		
		valorVelocidad = controlP5.addSlider("velocidad",10,100,60,580,430,100,10);
		
		botonGuardar = controlP5.addButton("teach",0,730,20,50,19);	
		botonTarea = controlP5.addButton("tarea",1,730,60,50,19);
		botonBorrar = controlP5.addButton("borrar",15,730,100,50,19);		
		botonSimular = controlP5.addButton("simular",14,730,140,50,19);
		
		
		botonHome = controlP5.addButton("home", 21, 730, 200, 50, 19);		
		botonIrBanda = controlP5.addButton("banda", 23, 730, 225, 50, 19);		
		botonCarga = controlP5.addButton("carga", 25, 730, 250, 50, 19);
		
		
		botonGripper = controlP5.addButton("gripper", 22, 730, 310, 50, 19);
		usarBanda = controlP5.addButton("torno", 24, 730, 340, 50, 19);
		botonTimer = controlP5.addButton("timer", 26, 730, 390, 50, 19);
		temporizador = controlP5.addTextfield("", 610, 390, 50, 19);
		temporizador.setValue("0");
		segLabel = controlP5.addTextlabel("s","seg", 670, 395);
		
		posXLabel = controlP5.addTextlabel("pX","Coor. X", 580, 460);
		posYLabel = controlP5.addTextlabel("pY","Coor. Y", 580, 485);
		posZLabel = controlP5.addTextlabel("pZ","Coor. Z", 580, 510);
				 
		valorXLabel = controlP5.addTextlabel("vX","0", 650, 460);
		valorYLabel = controlP5.addTextlabel("vY","0", 650, 485);
		valorZLabel = controlP5.addTextlabel("vZ","0", 650, 510);	
		
		
		// Consola de salida		
		consola = controlP5.addTextarea("label", 
				"Motoman MH6",				
				580, 540, 200, 100);
		
		consola.setText("\t\t\t\t\t\t\t\t\t\t\t\tMotoman MH6 \n\t\t\t\tUniversidad Nacional de Colombia \n" +
				"----------------------------------------");
		
		consola.setColorBackground(0);
		
		// Carga piezas del robot
		base = new OBJModel(this, "base.obj", "absolute", TRIANGLES);
		ejeS = new OBJModel(this, "ejeS.obj", "absolute", TRIANGLES);
		ejeL = new OBJModel(this, "ejeL.obj", "absolute", TRIANGLES);
		ejeU = new OBJModel(this, "ejeU.obj", "absolute", TRIANGLES);
		ejeR = new OBJModel(this, "ejeR.obj", "absolute", TRIANGLES);
		ejeB = new OBJModel(this, "ejeB.obj", "absolute", TRIANGLES);
		ejeTOp = new OBJModel(this, "ejeTOp.obj", "absolute", TRIANGLES);
		ejeTCl = new OBJModel(this, "ejeTCl.obj", "absolute", TRIANGLES);
		
		
		// Entorno de trabajo
		
		 mesaPC = new OBJModel(this, "MesaPC.obj", "absolute", TRIANGLES);
		 mesaTorno = new OBJModel(this, "MesaTorno.obj", "absolute", TRIANGLES);
		 baseMesaRobot = new OBJModel(this, "BaseMesaRobot.obj", "absolute", TRIANGLES);
		 
		 baseMesaRobot2 = new OBJModel(this, "BaseMesaRobot.obj", "absolute", TRIANGLES);
		 mesaPC2 = new OBJModel(this, "MesaPC.obj", "absolute", TRIANGLES);
		 mesaTorno2 = new OBJModel(this, "MesaTorno.obj", "absolute", TRIANGLES);
		 
		 motoman = new Motoman(scene, base, ejeS, ejeL, ejeU, ejeR, ejeB, ejeTOp, ejeTCl);

		 camara = new Camera(scene);
		 camara.frame().linkTo(motoman.frame(4));
		 camara.setOrientation(new Quaternion(new PVector(0, 0, 1), (float)Math.PI/2));
		 
		 baseMesaRobot2.translate(new PVector(0, 0, -155.5f));
		 mesaPC2.translate(new PVector(-126, 55, -155.5f));
		 mesaTorno2.translate(new PVector(-146, -74, -155.5f));		 
	}
			
	public void draw(){
		
		if(mouseX > canvas.width){
			scene.disableMouseHandling();
		}else {
			if(mouseY < canvas.height){
				scene.enableMouseHandling();
			}else{
				scene.disableMouseHandling();
			}			
		}
		
		frameRate(55);
		background(80);
											
		canvas.beginDraw();
		canvas.lights();
		canvas.background(200);
		scene.beginDraw();
		
		if(simulando == 0) {
			motoman.draw(valorEjes, mouseX, mouseY, estGripper);
			botonBorrar.unlock();
		}
		else if(simulando == 1){
			frameRate(15);
			botonBorrar.lock();
			
			ejesDraw[0] = simulacion.get(contador).ejeS;
			ejesDraw[1] = simulacion.get(contador).ejeL;
			ejesDraw[2] = simulacion.get(contador).ejeU;
			ejesDraw[3] = simulacion.get(contador).ejeR;
			ejesDraw[4] = simulacion.get(contador).ejeB;
			ejesDraw[5] = simulacion.get(contador).ejeT;
			
			estGripperSim = simulacion.get(contador).estGripper;
			motoman.draw(ejesDraw, 0, 0, estGripperSim);
			
			
			// dibujar la trayectoria que se ejecuta
			
			float [] pos =(motoman.obtenerPosicion());
			drawTrayectoria.add(new Coordenada(pos[0],pos[1],pos[2]));
			if(drawTrayectoria.size()>1){
				canvas.stroke(128, 128, 0);
				for(int i = 0; i < contador; i++){
					canvas.line((drawTrayectoria.get(i).x), (drawTrayectoria.get(i).y), (drawTrayectoria.get(i).z),
							 	 (drawTrayectoria.get(i+1).x), (drawTrayectoria.get(i+1).y), (drawTrayectoria.get(i+1).z));
				}
			}
			
			
			
			contador++;
					
			if(contador == simulacion.size()){								
				contador = 0;
				simulando = 0;
				simulacion.clear();
				drawTrayectoria.clear();
			}
		}
				
				
		valorXLabel.setValue(String.valueOf(motoman.getPosicion()[0]));
		valorYLabel.setValue(String.valueOf(-motoman.getPosicion()[1]));
		valorZLabel.setValue(String.valueOf(motoman.getPosicion()[2]));
		
		valorXLabel.update();
		
		canvas.noStroke();
		canvas.fill(255);
		canvas.beginShape();
		canvas.vertex(-300, 250);
		canvas.vertex(300, 250);
		canvas.vertex(300, -250);
		canvas.vertex(-300, -250);
		canvas.endShape(CLOSE);

		// Dibuja entorno del laboratorio
		canvas.fill(0);
		for (int k = 0; k < baseMesaRobot.getFaceCount(); k++) {
		    PVector[] faceVertices = baseMesaRobot.getFaceVertices(k);
		    canvas.beginShape(TRIANGLES);
		    for (int i = 0; i < faceVertices.length; i++)
		      canvas.vertex(faceVertices[i].x, faceVertices[i].y, faceVertices[i].z);
		    canvas.endShape();
		}
		
		canvas.fill(170, 100, 5);
		canvas.translate(126, -55, 0);
		for (int k = 0; k < mesaPC.getFaceCount(); k++) {
		    PVector[] faceVertices = mesaPC.getFaceVertices(k);
		    canvas.beginShape(TRIANGLES);
		    for (int i = 0; i < faceVertices.length; i++)
		      canvas.vertex(faceVertices[i].x, faceVertices[i].y, faceVertices[i].z);
		    canvas.endShape();
		}		
		
		canvas.fill(20, 100, 100);
		canvas.translate(20, 129, 0);
		for (int k = 0; k < mesaTorno.getFaceCount(); k++) {
		    PVector[] faceVertices = mesaTorno.getFaceVertices(k);
		    canvas.beginShape(TRIANGLES);
		    for (int i = 0; i < faceVertices.length; i++)
		      canvas.vertex(faceVertices[i].x, faceVertices[i].y, faceVertices[i].z);
		    canvas.endShape();
		}		
		
		// Verifica los planos de seguridad
		verificarPlanosSeguridad();
		
		if(seguro == 0) botonGuardar.lock();
		else botonGuardar.unlock();
		
		// planos de seguridad alimentador torno
		if(trabajaConBanda == 0){
			if(seguroP2 == 1) canvas.fill(0,0,255, 50);
			else canvas.fill(255,0,0, 50);
			canvas.beginShape();
				canvas.vertex(60, -25, 122);
				canvas.vertex(60, 55, 122);
				canvas.vertex(-310, 55, 122);
				canvas.vertex(-310, -25, 122);
			canvas.endShape();
			canvas.beginShape();
				canvas.vertex(60, -25, 0);
				canvas.vertex(60, -25, 122);
				canvas.vertex(-310, -25, 122);
				canvas.vertex(-310, -25, 0);
			canvas.endShape();
		}else{
			if(seguroP2 == 1) canvas.fill(0,0,255, 50);
			else canvas.fill(255,0,0, 50);
			canvas.beginShape();
				canvas.vertex(-147, -25, 115);
				canvas.vertex(-147, 25, 115);
				canvas.vertex(-170, 25, 115);
				canvas.vertex(-170, -25, 115);
			canvas.endShape();
			canvas.beginShape();
				canvas.vertex(60, -25, 0);
				canvas.vertex(60, -25, 122);
				canvas.vertex(-147, -25, 122);
				canvas.vertex(-147, -25, 115);
				canvas.vertex(-170, -25, 115);
				canvas.vertex(-170, -25, 122);				
				canvas.vertex(-310, -25, 122);
				canvas.vertex(-310, -25, 0);
			canvas.endShape();
			canvas.beginShape();
				canvas.vertex(-147, -25, 115);
				canvas.vertex(-147, 25, 115);
				canvas.vertex(-147, 25, 122);
				canvas.vertex(-147, -25, 122);
			canvas.endShape();
				canvas.beginShape();
				canvas.vertex(-147, 25, 115);
				canvas.vertex(-170, 25, 115);
				canvas.vertex(-170, 25, 122);
				canvas.vertex(-147, 25, 122);
			canvas.endShape();
			canvas.beginShape();
				canvas.vertex(-170, 25, 115);
				canvas.vertex(-170, -25, 115);
				canvas.vertex(-170, -25, 122);
				canvas.vertex(-170, 25, 122);
			canvas.endShape();
			canvas.beginShape();
				canvas.vertex(60, -25, 122);
				canvas.vertex(60, 55, 122);
				canvas.vertex(-310, 55, 122);
				canvas.vertex(-310, -25, 122);
				canvas.vertex(-170, -25, 122);
				canvas.vertex(-170, 25, 122);
				canvas.vertex(-147, 25, 122);
				canvas.vertex(-147, -25, 122);
			canvas.endShape();
		}
		
		
		// planos de seguridad mesa pc
		if(seguroP1 == 1) canvas.fill(0,0,255, 50);
		else canvas.fill(255,0,0, 50);
		canvas.beginShape();
			canvas.vertex(43, -110, 0);
			canvas.vertex(-48, -110, 0);
			canvas.vertex(-48, -110, 120);
			canvas.vertex(43, -110, 120);
		canvas.endShape();
		canvas.beginShape();
			canvas.vertex(-48, -110, 0);
			canvas.vertex(-48, -250, 0);
			canvas.vertex(-48, -250, 120);
			canvas.vertex(-48, -110, 120);
		canvas.endShape();
		canvas.beginShape();
			canvas.vertex(-48, -110, 120);
			canvas.vertex(-48, -250, 120);
			canvas.vertex(43, -250, 120);
			canvas.vertex(43, -110, 120);
		canvas.endShape();		
			
		scene.endDraw();
		canvas.endDraw();
		image(canvas, 0, 0);
		
		controlP5.draw();
		
	} // end draw
	
	
	public void verificarPlanosSeguridad(){
		float[] posicion;
		
		// Con mesa PC
		posicion = motoman.getFrame6();
		
		if(posicion[0] >= 100 && posicion[1] <= -25 && posicion[2] <= 120) seguroP1 = 0;
		else{
			posicion = motoman.getFrame4();
			if(posicion[0] >= 100 && posicion[1] <= -25 && posicion[2] <= 120) seguroP1 = 0;
			else seguroP1 = 1;
		}

		// Con torno
		posicion = motoman.getFrame6();		
		if(trabajaConBanda == 0){
			if(posicion[1] >= 47 && posicion[2] <= 121) seguroP2 = 0;
			else{
				posicion = motoman.getFrame4();
				if(posicion[1] >= 47 && posicion[2] <= 121) seguroP2 = 0;
				else{
					posicion = motoman.getFrame3();
					if(posicion[1] >= 47 && posicion[2] <= 121) seguroP2 = 0;
					else{
						posicion = motoman.getFrame2();
						if(posicion[1] >= 33 && posicion[2] <= 121) seguroP2 = 0;
						else seguroP2 = 1;
					}
				}				
			}
		}else{
			if(posicion[1] >= 90 && posicion[2] <= 121) seguroP2 = 0;
			else{
				posicion = motoman.getFrame6();
				if(posicion[1] >= 47 && posicion[2] <= 121 && (posicion[0] < -20 || posicion[0] > -3)) seguroP2 = 0;
				else{
					posicion = motoman.getFrame4();
					if(posicion[1] >= 47 && posicion[2] <= 121 && (posicion[0] < -20 || posicion[0] > -3)) seguroP2 = 0;
					else{
						posicion = motoman.getFrame3();
						if(posicion[1] >= 47 && posicion[2] <= 121 && (posicion[0] < -20 || posicion[0] > -3)) seguroP2 = 0;
						else{
							posicion = motoman.getFrame2();							
							if(posicion[1] >= 33 && posicion[2] <= 121 && (posicion[0] < -20 || posicion[0] > -3)) seguroP2 = 0;
							else seguroP2 = 1;
						}
					}
				}				
			}
		}
		
		if(seguroP1 == 0 || seguroP2 == 0 || motoman.getFrame4()[2] < 10 || motoman.getFrame6()[2] < 10) seguro = 0;
		else seguro = 1;
	}
	
	public void controlEvent(ControlEvent theEvent) {		
		
		if(theEvent.isGroup() && theEvent.name().equals("myList")){
			typMovimiento = (int)theEvent.group().value();
			
			if(typMovimiento == 0) tMov = "MOVJ";
			else if(typMovimiento == 1) tMov = "MOVL"; 
			
		}else theEvent.controller().name();
	}	
		
	public void teach() throws IOException{
		
		comando = 0;		
		indice = 0;
		
		if(presionaBanda == 0 && presionaCarga == 0){
			consola.setText(consola.text() + "Teach X: " + motoman.getPosicion()[0] +
					" Y: " + -motoman.getPosicion()[1] +
					" Z: " + motoman.getPosicion()[2] + "\n");
		}
		
		
		enviarEstado(indice);	
	}

	public void tarea() throws IOException{
		consola.setText(consola.text() + "Generar tarea\n");
		
		comando=1;
		try {
   	     	SocketChannel canalCliente = SocketChannel.open();
   	     	canalCliente.connect(new InetSocketAddress(IPServer, portServer));

   	    	ByteBuffer bufC=ByteBuffer.allocate(5);// bufC es el comando que indica:0 para mandar el areglo de joints
   	    										   // 1 como comando para generar la tarea
   	        
   	        bufC.asIntBuffer().put(comando);

   			
   			canalCliente.write(new ByteBuffer[]{bufC});
   	        
   	        canalCliente.close();
   	        canalCliente.socket().close();
   	        
   	        
       	} catch (java.net.BindException e1) {
               System.out.println("El puerto esta ocupado");
           }       
       	
       	 catch (java.lang.SecurityException e2) {
                System.out.println("Hay restricciones de seguridad");
            }             

       	catch (ConnectException e3) {
       		System.out.println("No se pudo conectar al servidor");
           }

		
	}
	
	public void borrar() throws IOException{
		trayectoria.clear();
		drawTrayectoria.clear();
		comando=2;
		consola.setText(consola.text() + "Borrar trayectoria\n");
		try {
   	     	SocketChannel canalCliente = SocketChannel.open();
   	     	canalCliente.connect(new InetSocketAddress(IPServer, portServer));

   	    	ByteBuffer bufC=ByteBuffer.allocate(5);// bufC es el comando que indica:0 para mandar el areglo de joints
   	    										   // 1 como comando para generar la tarea
   	        
   	        bufC.asIntBuffer().put(comando);

   			
   			canalCliente.write(new ByteBuffer[]{bufC});
   	        
   	        canalCliente.close();
   	        canalCliente.socket().close();
   	        
   	        
       	} catch (java.net.BindException e1) {
               System.out.println("El puerto esta ocupado");
           }       
       	
       	 catch (java.lang.SecurityException e2) {
                System.out.println("Hay restricciones de seguridad");
            }             

       	catch (ConnectException e3) {
       		System.out.println("No se pudo conectar al servidor");
           }

	}
	
	
	
	public void spos(){
		if(valorEjes[0] <= 160) valorEjes[0] += 5;
	}
	
	public void sneg(){
		if(valorEjes[0] >= -160) valorEjes[0] -= 5;
	}
	
	public void lpos(){
		if(valorEjes[1] <= 145) valorEjes[1] += 5;
	}
	
	public void lneg(){
		if(valorEjes[1] >= -80) valorEjes[1] -= 5;
	}
	
	public void upos(){
		if(valorEjes[2] <= 240) valorEjes[2] += 5;
	}
	
	public void uneg(){
		if(valorEjes[2] >= -165) valorEjes[2] -= 5;
	}
	
	public void rpos(){
		if(valorEjes[3] <= 170) valorEjes[3] += 5;
	}
	
	public void rneg(){
		if(valorEjes[3] >= -170) valorEjes[3] -= 5;
	}
	
	public void bpos(){
		if(valorEjes[4] <= 215) valorEjes[4] += 5;
	}
	
	public void bneg(){
		if(valorEjes[4] >= -35) valorEjes[4] -= 5;
	}
	
	public void tpos(){
		if(valorEjes[5] <= 350) valorEjes[5] += 5;
	}
	
	public void tneg(){
		if(valorEjes[5] >= -350) valorEjes[5] -= 5;
	}
	
	
	
	public void home(){
		valorEjes[0] = 0;
		valorEjes[1] = 0;
		valorEjes[2] = 0;
		valorEjes[3] = 0;
		valorEjes[4] = 0;
		valorEjes[5] = 0;
	}
	
	public void banda() throws IOException{
		presionaBanda = 1;
		consola.setText(consola.text() + "Sistema Alimentador de Barras\n");
		int auxTypeMov = typMovimiento;
		int auxVelocidad = velParam;
		
		valorEjes[1] = -21.6f;
		valorEjes[2] = 49;
		valorEjes[3] = 0;
		valorEjes[4] = 90;
		
		typMovimiento = 0;// movimiento Joint
		velParam = 50;
		teach();
		
		valorEjes[0] = -95;
		valorEjes[5] = 97;
		typMovimiento = 0;// movimiento Joint
		velParam = 50;
		teach();
		
		valorEjes[0] = -95;
		valorEjes[1] = -41;
		valorEjes[2] = -13;
		valorEjes[3] = 0;
		valorEjes[4] = 90;
		valorEjes[5] = 97;
		
		typMovimiento = 0;// movimiento Joint
		velParam = 50;
		teach();
		
		valorEjes[0] = -95;
		valorEjes[1] = 0;
		valorEjes[2] = 3;
		valorEjes[3] = 0;
		valorEjes[4] = -10;
		valorEjes[5] = 97;
		typMovimiento = 1;// movimiento lineal
		velParam = 50;
		teach();
		
		valorEjes[0] = -96;
		valorEjes[1] = -4;
		valorEjes[2] = -16;
		valorEjes[3] = 0;
		valorEjes[4] = 4;
		valorEjes[5] = 97;
		typMovimiento = 1;// movimiento lineal
		velParam = 50;
		teach();
		
		typMovimiento = auxTypeMov;
		velParam = auxVelocidad;
		presionaBanda = 0;
	}
	
	public void gripper() throws IOException{// 0 abierto, 1 cerrado
		comando = 0;		
		indice = 1;
		
		if(estGripper == 0) estGripper = 1;
		else estGripper = 0;		
		enviarEstado(indice);
		
		System.out.println(estGripper);
	}
	
	public void torno(){
		if(trabajaConBanda == 0) trabajaConBanda = 1;
		else trabajaConBanda = 0;
	}
	
	public void timer() throws IOException{
		indice=2;
		timmer=Integer.parseInt(temporizador.getText());
		enviarEstado(indice);
		System.out.println(timmer);
	}
	
	public void carga() throws IOException{
		presionaCarga = 1;
		consola.setText(consola.text() + "Carga de Material\n");
		int auxTypeMov = typMovimiento;
		int auxVelocidad = velParam;
		
		valorEjes[0] = 90;
		valorEjes[1] = -3;
		valorEjes[2] = -17;
		valorEjes[3] = 0;
		valorEjes[4] = 14;
		valorEjes[5] = 79;
		typMovimiento = 0;// movimiento Joint
		velParam = 50;
		teach();
		
		valorEjes[0] = 102;
		valorEjes[1] = 60;
		valorEjes[2] = 18.5f;
		valorEjes[3] = 0;
		valorEjes[4] = 41;
		valorEjes[5] = 79;
		typMovimiento = 0;// movimiento joint
		velParam = 50;
		teach();
		
		valorEjes[0] = 102;
		valorEjes[1] = 74.5f;
		valorEjes[2] = 23;
		valorEjes[3] = 0;
		valorEjes[4] = 51;
		valorEjes[5] = 79;
		typMovimiento = 1;// movimiento lineal
		velParam = 50;
		teach();
		
		typMovimiento = auxTypeMov;
		velParam = auxVelocidad;
		presionaCarga = 0;
	}
	
	public void enviarEstado(int ind) throws IOException{// indice 0: teach; 1 gripper; 2 timmer
		System.out.println("teach");
		if(typMovimiento == 0) velParam = velocidad;
		else if(typMovimiento == 1) velParam = 90 * velocidad;
		trayectoria.add(new PuntosTrayectoria(valorEjes[0], valorEjes[1], valorEjes[2], valorEjes[3], valorEjes[4], valorEjes[5], tMov, velParam, estGripper));
				
		try {
   	     	SocketChannel canalCliente = SocketChannel.open();
   	     	canalCliente.connect(new InetSocketAddress(IPServer, portServer));
   	        
   	    	
   	    	float vS=valorEjes[0];
   	    	float vL=valorEjes[1];
   	    	float vU=valorEjes[2];
   	    	float vR=valorEjes[3];
   	    	float vB=valorEjes[4];
   	    	float vT=valorEjes[5];
   	 
   	     	
   	    	ByteBuffer bufC=ByteBuffer.allocate(5);// bufC es el comando que indica:0 para mandar el areglo de joints
   	    										   // 1 como comando para generar la tarea
   	    	ByteBuffer bufInd = ByteBuffer.allocate(5); // 0 - Posicion, 1 - Gripper, 2 - Timmer
   	    	
   	    	ByteBuffer bufS=ByteBuffer.allocate(5);
   	        ByteBuffer bufL=ByteBuffer.allocate(5);
   	        ByteBuffer bufU=ByteBuffer.allocate(5);
   	        ByteBuffer bufR=ByteBuffer.allocate(5);
   	        ByteBuffer bufB=ByteBuffer.allocate(5);
   	        ByteBuffer bufT=ByteBuffer.allocate(5);
   	        ByteBuffer bufGripper=ByteBuffer.allocate(5);
			ByteBuffer bufTimmer=ByteBuffer.allocate(5);//
   	        ByteBuffer buftypMovimiento=ByteBuffer.allocate(5);
   	        ByteBuffer buftvelParam=ByteBuffer.allocate(5);
   	        
   	        bufC.asIntBuffer().put(comando);
   	        bufInd.asIntBuffer().put(indice);
   	        bufS.asIntBuffer().put((int) vS);
   			bufL.asIntBuffer().put((int) vL);
   			bufU.asIntBuffer().put((int) vU);
   			bufR.asIntBuffer().put((int) vR);
   			bufB.asIntBuffer().put((int) vB);
   			bufT.asIntBuffer().put((int) vT);
   			bufGripper.asIntBuffer().put(estGripper);
   			bufTimmer.asIntBuffer().put(timmer);
   			buftypMovimiento.asIntBuffer().put(typMovimiento);
   			buftvelParam.asIntBuffer().put(velParam);
   			
   			canalCliente.write(new ByteBuffer[]{bufC, bufInd, bufS,bufL,bufU,bufR,bufB,bufT,bufGripper,bufTimmer, buftypMovimiento,buftvelParam});
   	        canalCliente.close();
   	        canalCliente.socket().close();
   	        
   	        
       	} catch (java.net.BindException e1) {
               System.out.println("El puerto esta ocupado");
           }       
       	
       	 catch (java.lang.SecurityException e2) {
                System.out.println("Hay restricciones de seguridad");
            }             

       	catch (ConnectException e3) {
       		System.out.println("No se pudo conectar al servidor");
           }
	}
	
	// Botones XYZ
	
	float[] valoresAng = new float[3];
	public void xpos(){		
		
		cinematica.changeX(motoman.getPosicion()[0] + 20);
		cinematica.changeY(-motoman.getPosicion()[1]);
		cinematica.changeZ(motoman.getPosicion()[2] - 450);		
					
		cinematica.solve(valorEjes[2]);
		valoresAng = cinematica.getAng();
		
		verificar();		
	}
	
	public void ypos(){
		
		cinematica.changeX(motoman.getPosicion()[0]);
		cinematica.changeY(-motoman.getPosicion()[1] + 20);
		cinematica.changeZ(motoman.getPosicion()[2] - 450);
						
		cinematica.solve(valorEjes[2]);
		valoresAng = cinematica.getAng();
		
		verificar();
	}
	
	public void zpos(){
				
		cinematica.changeX(motoman.getPosicion()[0]);
		cinematica.changeY(-motoman.getPosicion()[1]);
		cinematica.changeZ(motoman.getPosicion()[2] - 450 + 20);		
		
		cinematica.solve(valorEjes[2]);
		valoresAng = cinematica.getAng();
		
		verificar();
	}
	
	public void xneg(){
		
		cinematica.changeX(motoman.getPosicion()[0] - 20);
		cinematica.changeY(-motoman.getPosicion()[1]);
		cinematica.changeZ(motoman.getPosicion()[2] - 450);		
		
		cinematica.solve(valorEjes[2]);
		valoresAng = cinematica.getAng();
		
		verificar();
	}
	
	public void yneg(){
		
		cinematica.changeX(motoman.getPosicion()[0]);
		cinematica.changeY(-motoman.getPosicion()[1] - 20);
		cinematica.changeZ(motoman.getPosicion()[2] - 450);		
		
		cinematica.solve(valorEjes[2]);
		valoresAng = cinematica.getAng();
		
		verificar();
	}
	
	public void zneg(){
		
		if(motoman.getFrame4()[2] - 2 > 12 && motoman.getFrame6()[2] - 2 > 10){
			cinematica.changeX(motoman.getPosicion()[0]);
			cinematica.changeY(-motoman.getPosicion()[1]);
			cinematica.changeZ(motoman.getPosicion()[2] - 450 - 20);		
			
			cinematica.solve(valorEjes[2]);
			valoresAng = cinematica.getAng();
			
			verificar();
		}
	}
	
	public void verificar(){
		
		float c2 = (float)(Math.cos(valoresAng[1]*Math.PI/180)), 
				c3 = (float)(Math.cos(valoresAng[2]*Math.PI/180));
		
		float s2 = (float)(Math.sin(valoresAng[1]*Math.PI/180)), 
				s3 = (float)(Math.sin(valoresAng[2]*Math.PI/180));
		
		float zAng = Math.round(614*c2 + 155*c2*c3 + 640*c2*s3 - 640*c3*s2 + 155*s2*s3);
				
		if(Math.abs(zAng - cinematica.z) < 0.1){
			valorEjes[0] = valoresAng[0];
			valorEjes[1] = valoresAng[1];
			valorEjes[2] = valoresAng[2];
		}		
	}
	
	
	// Botones XYZ
	
	public void simular() throws InterruptedException {
				
		simulacion.clear();
		
		if(trayectoria.size()>0){
		for(int i = 0; i < trayectoria.size()-1; i++){
			deltaS = (trayectoria.get(i+1).ejeS - trayectoria.get(i).ejeS) / numPuntos;
			deltaL = (trayectoria.get(i+1).ejeL - trayectoria.get(i).ejeL) / numPuntos;
			deltaU = (trayectoria.get(i+1).ejeU - trayectoria.get(i).ejeU) / numPuntos;
			deltaR = (trayectoria.get(i+1).ejeR - trayectoria.get(i).ejeR) / numPuntos;
			deltaB = (trayectoria.get(i+1).ejeB - trayectoria.get(i).ejeB) / numPuntos;
			deltaT = (trayectoria.get(i+1).ejeT - trayectoria.get(i).ejeT) / numPuntos;
			
			for(int ind = 0; ind <= numPuntos; ind++){
				ejesSimulacion[0] = trayectoria.get(i).ejeS + (deltaS * ind);
				ejesSimulacion[1] = trayectoria.get(i).ejeL + (deltaL * ind);
				ejesSimulacion[2] = trayectoria.get(i).ejeU + (deltaU * ind);
				ejesSimulacion[3] = trayectoria.get(i).ejeR + (deltaR * ind);
				ejesSimulacion[4] = trayectoria.get(i).ejeB + (deltaB * ind);
				ejesSimulacion[5] = trayectoria.get(i).ejeT + (deltaT * ind);
				
				simulacion.add(new PuntosSimulacion(ejesSimulacion[0],
													ejesSimulacion[1],
													ejesSimulacion[2],
													ejesSimulacion[3],
													ejesSimulacion[4],
													ejesSimulacion[5],
													trayectoria.get(i).estGripper));
			}			
		}		
		
		simulando = 1;
		}
	}
}