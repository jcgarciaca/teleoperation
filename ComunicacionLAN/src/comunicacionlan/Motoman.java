package comunicacionlan;

import processing.core.*;
import remixlab.proscene.*;
import saito.objloader.*;

public class Motoman {
	
	InteractiveFrame [] frameArray;
	PGraphics3D pgl;
	Scene my_scene;
	
	OBJModel base, ejeS, ejeL, ejeU, ejeR, ejeB, ejeTOp, ejeTCl;
	
	float[] valorEjes = new float[6];
	int[] posicion = new int[3];
	float[] pos = new float[3];
	
	
	int dirEje = 0;
	
	int sobreS = 0, sobreL = 0, sobreU = 0, sobreR = 0, sobreB = 0, sobreT = 0;
	
	public Motoman(Scene scene, 
			OBJModel base, 
			OBJModel ejeS, 
			OBJModel ejeL, 
			OBJModel ejeU, 
			OBJModel ejeR, 
			OBJModel ejeB,
			OBJModel ejeTOp,
			OBJModel ejeTCl){
		my_scene = scene;
		pgl = scene.pg3d;
		frameArray = new InteractiveFrame[7];
	    for (int i=0; i<7; ++i) {
	    	frameArray[i] = new InteractiveFrame(scene);
		    // Creates a hierarchy of frames
		    if (i>0) frame(i).setReferenceFrame(frame(i-1));
	    }
	    
	    this.base = base;
	    this.ejeS = ejeS;
	    this.ejeL = ejeL;
	    this.ejeU = ejeU;
	    this.ejeR = ejeR;
	    this.ejeB = ejeB;
	    this.ejeTOp = ejeTOp;
	    this.ejeTCl = ejeTCl;
	    
	    // Initialize frames
	    frame(0).setTranslation(0, 0, (float)50.8);
	    frame(1).setTranslation(15, 10, (float)28.2); // Ubicacion Articulacion L
	    frame(2).setTranslation(0, -4, (float)61.4);  // Posicion Articulacion U
	    frame(3).setTranslation((float)21.8, -6, (float)15.5);  // Posicion Articulacion R
	    frame(4).setTranslation((float)42.2, 0, 0);  // Posicion Articulacion B
	    frame(5).setTranslation(0, 0, (float)-8.5);  // Posicion Articulacion T
	    frame(6).setTranslation(0, 0, -18); // Posicion final del actuador	       
	    
	    LocalConstraint XAxis = new LocalConstraint();
	    XAxis.setTranslationConstraint(AxisPlaneConstraint.Type.FORBIDDEN,  new PVector(0.0f,0.0f,0.0f));
	    XAxis.setRotationConstraint   (AxisPlaneConstraint.Type.AXIS, new PVector(1.0f,0.0f,0.0f));
	    
	    LocalConstraint YAxis = new LocalConstraint();
	    YAxis.setTranslationConstraint(AxisPlaneConstraint.Type.FORBIDDEN,  new PVector(0.0f,0.0f,0.0f));
	    YAxis.setRotationConstraint   (AxisPlaneConstraint.Type.AXIS, new PVector(0.0f,1.0f,0.0f));
	    
	    LocalConstraint ZAxis = new LocalConstraint();
	    ZAxis.setTranslationConstraint(AxisPlaneConstraint.Type.FORBIDDEN,  new PVector(0.0f,0.0f,0.0f));
	    ZAxis.setRotationConstraint   (AxisPlaneConstraint.Type.AXIS, new PVector(0.0f,0.0f,1.0f));
	    
	    frame(0).setConstraint(ZAxis);
	    
	    frame(1).setConstraint(YAxis);
	    frame(2).setConstraint(YAxis);
	    
	    frame(3).setConstraint(XAxis);
	    frame(4).setConstraint(YAxis);
	    frame(5).setConstraint(ZAxis);
	    
	    frame(6).setConstraint(ZAxis); // doesn't matter
	}
	
	
	public void draw(float[] ejes, int posX, int posY, int flag) {
		
		if(posX > 580 && posX < 630 || posX > 640 && posX < 690){ // ubicacion de los botones
			if(posY > 20 && posY < 164){
				if(posY > 20 && posY < 39){
					sobreS = 1;
					sobreL = 0;
					sobreU = 0;
					sobreR = 0;
					sobreB = 0;
					sobreT = 0;
				}else if(posY > 45 && posY < 64){				
					sobreS = 0;
					sobreL = 1;
					sobreU = 0;
					sobreR = 0;
					sobreB = 0;
					sobreT = 0;
				}else if(posY > 70 && posY < 89){				
					sobreS = 0;
					sobreL = 0;
					sobreU = 1;
					sobreR = 0;
					sobreB = 0;
					sobreT = 0;
				}else if(posY > 95 && posY < 114){				
					sobreS = 0;
					sobreL = 0;
					sobreU = 0;
					sobreR = 1;
					sobreB = 0;
					sobreT = 0;
				}else if(posY > 120 && posY < 139){				
					sobreS = 0;
					sobreL = 0;
					sobreU = 0;
					sobreR = 0;
					sobreB = 1;
					sobreT = 0;
				}else if(posY > 145 && posY < 164){				
					sobreS = 0;
					sobreL = 0;
					sobreU = 0;
					sobreR = 0;
					sobreB = 0;
					sobreT = 1;
				}
			}else{
				sobreS = 0;
				sobreL = 0;
				sobreU = 0;
				sobreR = 0;
				sobreB = 0;
				sobreT = 0;
			}
		}else{
			sobreS = 0;
			sobreL = 0;
			sobreU = 0;
			sobreR = 0;
			sobreB = 0;
			sobreT = 0;
		}
		
		valorEjes = ejes;
		
		pgl.pushMatrix();
		pgl.fill(128, 128, 128);
		pgl.scale(1);
		pgl.noStroke();
		pgl.translate(0, 0, 34);
		
		for (int k = 0; k < base.getFaceCount(); k++) {
		    PVector[] faceVertices = base.getFaceVertices(k);
		    pgl.beginShape(PApplet.TRIANGLES);
		    for (int i = 0; i < faceVertices.length; i++)
		    	pgl.vertex(faceVertices[i].x, faceVertices[i].y, faceVertices[i].z);
		    pgl.endShape(PApplet.TRIANGLES);
		}
		
		pgl.popMatrix();
				
		if(valorEjes[0] > 0) dirEje = -1;
		else if(valorEjes[0] < 0) dirEje = 1;
		
		
		pgl.pushMatrix();
		frame(0).applyTransformation();
		frame(0).setRotation(new Quaternion(new PVector(0, 0, dirEje), (float)(Math.abs(valorEjes[0])*Math.PI / 180) ));
				
		
	    setColor( frame(0).grabsMouse() );
	    if(sobreS == 1) pgl.fill(255, 0, 0);
	    else pgl.fill(0, 0, 255);
		pgl.scale(1);
		pgl.noStroke();
		
		for (int k = 0; k < ejeS.getFaceCount(); k++) {
		    PVector[] faceVertices = ejeS.getFaceVertices(k);
		    pgl.beginShape(PApplet.TRIANGLES);
		    for (int i = 0; i < faceVertices.length; i++)
		    	pgl.vertex(faceVertices[i].x, faceVertices[i].y, faceVertices[i].z);
		    pgl.endShape(PApplet.TRIANGLES);
		}
	    
	    pgl.pushMatrix();//not really necessary here
	    
	    if(valorEjes[1] > 0) dirEje = 1;
		else if(valorEjes[1] < 0) dirEje = -1;
	    
	    
	    frame(1).applyTransformation();
	    frame(1).setRotation(new Quaternion(new PVector(0, dirEje, 0), (float)(Math.abs(valorEjes[1])*Math.PI / 180) ));
	    
	    setColor( frame(1).grabsMouse() );
	    if(sobreL == 1) pgl.fill(255, 0, 0);
	    else pgl.fill(128, 128, 128);
	    
	    
		pgl.scale(1);
		pgl.noStroke();
		
		for (int k = 0; k < ejeL.getFaceCount(); k++) {
		    PVector[] faceVertices = ejeL.getFaceVertices(k);
		    pgl.beginShape(PApplet.TRIANGLES);
		    for (int i = 0; i < faceVertices.length; i++)
		    	pgl.vertex(faceVertices[i].x, faceVertices[i].y, faceVertices[i].z);
		    pgl.endShape(PApplet.TRIANGLES);
		}
	    
	    pgl.pushMatrix();//not really necessary here
	    
	    if(valorEjes[2] > 0) dirEje = -1;
		else if(valorEjes[2] < 0) dirEje = 1;
	    
	    frame(2).applyTransformation();
	    frame(2).setRotation(new Quaternion(new PVector(0, dirEje, 0), (float)(Math.abs(valorEjes[2])*Math.PI / 180) ));
	    
	    setColor( frame(2).grabsMouse() );
	    if(sobreU == 1) pgl.fill(255, 0, 0);
	    else pgl.fill(0, 0, 255);
	    
	    
		pgl.scale(1);
		pgl.noStroke();
		
		for (int k = 0; k < ejeU.getFaceCount(); k++) {
		    PVector[] faceVertices = ejeU.getFaceVertices(k);
		    pgl.beginShape(PApplet.TRIANGLES);
		    for (int i = 0; i < faceVertices.length; i++)
		    	pgl.vertex(faceVertices[i].x, faceVertices[i].y, faceVertices[i].z);
		    pgl.endShape(PApplet.TRIANGLES);
		}
	    
	    pgl.pushMatrix();//not really necessary here
	    
	    if(valorEjes[3] > 0) dirEje = 1;
		else if(valorEjes[3] < 0) dirEje = -1;
	    
	    frame(3).applyTransformation();
	    frame(3).setRotation(new Quaternion(new PVector(dirEje, 0, 0), (float)(Math.abs(valorEjes[3])*Math.PI / 180) ));
	    
	    setColor( frame(3).grabsMouse() );
	    
	    
	    if(sobreR == 1) pgl.fill(255, 0, 0);
	    else pgl.fill(128, 128, 128);
		pgl.scale(1);
		pgl.noStroke();
		
		for (int k = 0; k < ejeR.getFaceCount(); k++) {
		    PVector[] faceVertices = ejeR.getFaceVertices(k);
		    pgl.beginShape(PApplet.TRIANGLES);
		    for (int i = 0; i < faceVertices.length; i++)
		    	pgl.vertex(faceVertices[i].x, faceVertices[i].y, faceVertices[i].z);
		    pgl.endShape(PApplet.TRIANGLES);
		}
	    
	    pgl.pushMatrix();//not really necessary here
	    
	    if(valorEjes[4] > 0) dirEje = -1;
		else if(valorEjes[4] < 0) dirEje = 1;
	    
	    frame(4).applyTransformation();
	    frame(4).setRotation(new Quaternion(new PVector(0, dirEje, 0), (float)(Math.abs(valorEjes[4])*Math.PI / 180) ));
	    
	    setColor( frame(4).grabsMouse() );
	    
	    	    
	    if(sobreB == 1) pgl.fill(255, 0, 0);
	    else pgl.fill(0, 0, 255);
		pgl.scale(1);
		pgl.noStroke();
		
		for (int k = 0; k < ejeB.getFaceCount(); k++) {
		    PVector[] faceVertices = ejeB.getFaceVertices(k);
		    pgl.beginShape(PApplet.TRIANGLES);
		    for (int i = 0; i < faceVertices.length; i++)
		    	pgl.vertex(faceVertices[i].x, faceVertices[i].y, faceVertices[i].z);
		    pgl.endShape(PApplet.TRIANGLES);
		}
	    
	    pgl.pushMatrix();//not really necessary here
	    
	    if(valorEjes[5] > 0) dirEje = -1;
		else if(valorEjes[5] < 0) dirEje = 1;
	    
	    frame(5).applyTransformation();
	    frame(5).setRotation(new Quaternion(new PVector(0, 0, dirEje), (float)(Math.abs(valorEjes[5])*Math.PI / 180) ));
	    
	    setColor( frame(5).grabsMouse() );    
	    
	    if(sobreT == 1) pgl.fill(255, 0, 0);
	    else pgl.fill(128, 128, 128);
		pgl.scale(1);
		pgl.noStroke();
		
		if(flag == 0){
			for (int k = 0; k < ejeTOp.getFaceCount(); k++) {
			    PVector[] faceVertices = ejeTOp.getFaceVertices(k);
			    pgl.beginShape(PApplet.TRIANGLES);
			    for (int i = 0; i < faceVertices.length; i++)
			    	pgl.vertex(faceVertices[i].x, faceVertices[i].y, faceVertices[i].z);
			    pgl.endShape(PApplet.TRIANGLES);
			}
		}else if(flag == 1){
			for (int k = 0; k < ejeTCl.getFaceCount(); k++) {
			    PVector[] faceVertices = ejeTCl.getFaceVertices(k);
			    pgl.beginShape(PApplet.TRIANGLES);
			    for (int i = 0; i < faceVertices.length; i++)
			    	pgl.vertex(faceVertices[i].x, faceVertices[i].y, faceVertices[i].z);
			    pgl.endShape(PApplet.TRIANGLES);
			}
		}
		
		pgl.pushMatrix();//not really necessary here
		frame(6).applyTransformation();
		pgl.popMatrix();// frame(6)
	    
	    pgl.popMatrix();//frame(5)
	    
	    pgl.popMatrix();//frame(4)
	        
	    pgl.popMatrix();//frame(3)
	    
	    pgl.popMatrix();//frame(2)
	    
	    pgl.popMatrix();//frame(1)
	    
	    //totally necessary
	    pgl.popMatrix();//frame(0)
	}	
		
	public int[] getPosicion(){
		posicion[0] = Math.round(frame(4).position().x * 10);
		posicion[1] = Math.round(frame(4).position().y * 10);
		posicion[2] = Math.round(frame(4).position().z * 10 - 340);
		return posicion;
	}
	
	public float[] obtenerPosicion(){
		pos[0] = (frame(4).position().x);
		pos[1] = (frame(4).position().y);
		pos[2] = (frame(4).position().z);
		return pos;
	}
	
	
	// Posicion de los frames que pueden entrar a los planos de seguridad
	public float[] getFrame2(){
		pos[0] = (frame(2).position().x);
		pos[1] = (frame(2).position().y);
		pos[2] = (frame(2).position().z);
		return pos;
	}
	
	public float[] getFrame3(){
		pos[0] = (frame(3).position().x);
		pos[1] = (frame(3).position().y);
		pos[2] = (frame(3).position().z);
		return pos;
	}
	
	public float[] getFrame4(){
		pos[0] = (frame(4).position().x);
		pos[1] = (frame(4).position().y);
		pos[2] = (frame(4).position().z);
		return pos;
	}
	
	public float[] getFrame5(){
		pos[0] = (frame(5).position().x);
		pos[1] = (frame(5).position().y);
		pos[2] = (frame(5).position().z);
		return pos;
	}
	
	public float[] getFrame6(){
		pos[0] = (frame(6).position().x);
		pos[1] = (frame(6).position().y);
		pos[2] = (frame(6).position().z);
		return pos;
	}
	
	public void setColor(boolean selected) {
	    if (selected) pgl.fill(200, 200, 0);
	    else pgl.fill(200, 200, 200);
	}
	  
	public InteractiveFrame frame(int i) {
	    return frameArray[i];
	}
}
