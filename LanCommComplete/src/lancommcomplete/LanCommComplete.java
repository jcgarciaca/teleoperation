package lancommcomplete;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*; // Necesaria para usar la colección Set de java.

public class LanCommComplete {
	
	public static native int MoverMotoman(int pulseS, int pulseL,
			int pulseU, int pulseR, int pulseB, int pulseT);
	
	public static native int MoverMotoman(String jobName);
	
	static{
		System.loadLibrary("VBToJavaMotoCom");
	}
	
	private static ServerSocket socketServidor;
	private static Selector selector;
	private static ServerSocketChannel canalServidor;
	
	static ArrayList<PuntosTrayectoria> trayectoria = new ArrayList<PuntosTrayectoria>();
	static PrintWriter salida;
	
	
	public static final int PUERTO = 9002; // puerto por defecto
	
	static String IPServer="localhost";//168.176.26.91

	public static void main(String[] args) throws java.io.IOException {

		try {
			canalServidor = ServerSocketChannel.open();
			canalServidor.configureBlocking(false);
			ServerSocket socketServidor = canalServidor.socket();
			InetSocketAddress dirSocket = new InetSocketAddress(IPServer,PUERTO);//168.176.36.125
			socketServidor.bind(dirSocket);
			Selector selector = Selector.open();
			canalServidor.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("El servidor esta escuchando por el puerto "
					+ PUERTO);

			int ses = 0;

			while (true) { // bucle infinito

				try {
					ses = selector.select(); //
					System.out.println("true");
				} catch (IOException e) {
					System.out.println("Error el lalmar al metodo select();");
				}

				if (ses > 0) {// suceso E/S
					Set claves = selector.selectedKeys();
					Iterator it = claves.iterator();

					while (it.hasNext()) {
						System.out.println("it.hasNext()");
						SelectionKey clave = (SelectionKey) it.next(); // Se
																		// obtiene
																		// cada
																		// SelectionKey
																		// del
																		// Set.
						if (clave.isAcceptable()) {

							try {
								System.out
										.println("El cliente se ha conectado al servidor");
								SocketChannel canalCliente = (SocketChannel) canalServidor
										.accept();// lee lo que manda el cliente
								canalCliente.configureBlocking(false);
								canalCliente.register(selector,
										SelectionKey.OP_READ);

							}

							catch (java.lang.SecurityException e1) {
								System.out
										.println("Restricciones de seguridad. Los clientes no se pueden conectar al puerto");
								if (socketServidor != null) {
									try {
										socketServidor.close();
									} catch (IOException e2) {
									}
								}

							} catch (IOException e3) {// queda pendiente
								System.out
										.println("El cliente a cerrado la conexion subitamente");
								ServerSocketChannel canalCliente = null;
								if (canalCliente.socket() != null) {
									try {
										canalCliente.close();
									} catch (IOException e4) {
									}
								}
							}
						}
						if (clave.isReadable()) {// (clave.isReadable()){
							System.out.println("El servidor lee datos mandados por el cliente");

							SocketChannel canalCliente = (SocketChannel) clave.channel();// Se obtiene el canal de socket
												// de la clave.
							canalCliente.configureBlocking(false);
							
							ByteBuffer bufC=ByteBuffer.allocate(5); // 0 teach, 1 tarea, 2 borrar
				   	    	ByteBuffer bufInd = ByteBuffer.allocate(5); // 0 posicion, 1 gripper, 2 timmer
							ByteBuffer bufS=ByteBuffer.allocate(5);
							ByteBuffer bufL=ByteBuffer.allocate(5);
							ByteBuffer bufU=ByteBuffer.allocate(5);
							ByteBuffer bufR=ByteBuffer.allocate(5);
							ByteBuffer bufB=ByteBuffer.allocate(5);
							ByteBuffer bufT=ByteBuffer.allocate(5);
							ByteBuffer bufGripper=ByteBuffer.allocate(5);//
							ByteBuffer bufTimmer=ByteBuffer.allocate(5);//
							ByteBuffer buftypMovimiento=ByteBuffer.allocate(5);// tipo de movimiento
							ByteBuffer buftvelParam=ByteBuffer.allocate(5);//
							

							try {
								canalCliente.read(new ByteBuffer[]{bufC, bufInd, bufS,bufL,bufU,bufR,bufB,bufT,bufGripper,bufTimmer,buftypMovimiento,buftvelParam});
								
								bufC.rewind();
								bufInd.rewind();
								bufS.rewind();
								bufL.rewind();
								bufU.rewind();
								bufR.rewind();
								bufB.rewind();
								bufT.rewind();
								bufGripper.rewind();
								bufTimmer.rewind();
								buftypMovimiento.rewind();
								buftvelParam.rewind();

								int comando=bufC.asIntBuffer().get();
								
								if(comando==0){// se deben guardar los datos en un arreglo
									int vS = bufS.asIntBuffer().get();
									int vL = bufL.asIntBuffer().get();
									int vU = bufU.asIntBuffer().get();
									int vR = bufR.asIntBuffer().get();
									int vB = bufB.asIntBuffer().get();
									int vT = bufT.asIntBuffer().get();
									int estGripper = bufGripper.asIntBuffer().get(); // 0 - abierto ----- 1 - cerrado
									int timmer = bufTimmer.asIntBuffer().get(); //tiempo
									int typMovimiento = buftypMovimiento.asIntBuffer().get();;//0 MOVJ; 1 MOVL
									
									System.out.println("---- esto es " + typMovimiento);
									
									int velParam = buftvelParam.asIntBuffer().get();
									int indice = bufInd.asIntBuffer().get();
									String tMov = null;
									if(typMovimiento == 0) tMov = "MOVJ";
									else if(typMovimiento == 1) tMov = "MOVL";
									
									System.out.println("---- esto es2 " + tMov);
									
									// esto es de prueba
									if(estGripper==0)System.out.println("Gripper abierto");
									if(estGripper==1)System.out.println("Gripper cerrado");
									
									trayectoria.add(new PuntosTrayectoria(indice, vS, vL, vU, vR, vB, vT, estGripper, timmer, tMov,  velParam));
									System.out.println("comando: "+comando+" Indice: "+indice+" S:" + vS + " L:" + vL
											+ " U:" + vU + " R:" + vR + " B:" + vB+ " T:" + vT + " timmer: "+timmer);
								}
								else if (comando==1){// se debe generar el archibo JBI, se da tarea
									
									int cont = 0;
									for(int i = 0; i < trayectoria.size(); i++){							        	
							        	if(trayectoria.get(i).indice == 0) cont++;							        
							        }
									
									System.out.println("se debe generar el archivo jbi");
									if(trayectoria.size() > 0){
										salida = new PrintWriter(new FileWriter("MOTOMAN.JBI"));
										salida.println("/JOB");
										salida.println("//NAME MOTOMAN");
										salida.println("//POS");
										salida.println("///NPOS " + cont + ",0,0,0,0,0");
										salida.println("///TOOL 0");
										salida.println("///POSTYPE PULSE");
										salida.println("///PULSE");	        
								        
										cont = 0;
								        for(int i = 0; i < trayectoria.size(); i++){
								        	
								        	if(trayectoria.get(i).indice == 0){
								        		
									        	if(i < 10) salida.print("C0000" + cont + "=");
									            else if(i < 100) salida.print("C000" + cont + "=");
									            else if(i < 1000) salida.print("C00" + cont + "=");
									            
									            salida.println((int)(trayectoria.get(i).ejeS * 14353 / 10) + "," +
									            		(int)(trayectoria.get(i).ejeL * 13003 / 10) + "," +
									            		(int)(trayectoria.get(i).ejeU * 13767 / 10) + "," +
									            		(int)(trayectoria.get(i).ejeR * 5689 / 10) + "," +
									            		(int)(trayectoria.get(i).ejeB * 8239 / 10) + "," +
									            		(int)(trayectoria.get(i).ejeT * 4255 / 10));
									            cont++;
								        	}
								        }							        
								        
								        
								        salida.println("//INST");
								        salida.println("///DATE 2009/01/15 17:41");
								        salida.println("///ATTR RW");
								        salida.println("///GROUP1 RB1");
								        salida.println("NOP");
								        
								        cont = 0;
								        for(int i = 0; i < trayectoria.size(); i++){
								        	if(trayectoria.get(i).indice == 0){
									        	salida.print(trayectoria.get(i).tMov + " ");
									            if(cont < 10) salida.print("C0000" + cont);
									            else if(cont < 100) salida.print("C000" + cont);
									            else if(cont < 1000) salida.print("C00" + cont);
									            
									            if(trayectoria.get(i).tMov == "MOVJ") salida.println(" VJ=" + trayectoria.get(i).velInterpolacion + ".00");
									            else if(trayectoria.get(i).tMov == "MOVL") salida.println(" V=" + "46.0");//trayectoria.get(i).velInterpolacion);
									            cont++;
								        	}else if(trayectoria.get(i).indice == 1){
								        		if(trayectoria.get(i).estGripper == 0) {
								        			salida.println("TIMER T=1.00");
								        			salida.println("HAND HNO: 2 OFF");
								        			salida.println("TIMER T=1.00");
								        		}
								        		else if(trayectoria.get(i).estGripper == 1) {
								        			salida.println("TIMER T=1.00");
								        			salida.println("HAND HNO: 2 ON");
								        			salida.println("TIMER T=1.00");
								        		}
								        	}else if (trayectoria.get(i).indice == 2){
								        		salida.println("TIMER T="+trayectoria.get(i).timmer+".00");
								        	}
								        }
								        salida.println("END");
								        salida.close();
									}
									
								}else if (comando ==2){// borrar los puntos de latrayectoria
									trayectoria.clear();
									System.out.println("Borrar");
								}

							}

							catch (IOException e2) {
								System.out.println("Error en la conexion con el cliente. Desconexion o problemas con las redes intermedias");

								canalCliente.close();
								try {
									Socket saux = canalCliente.socket();
									if (saux != null) {
										saux.close();
										saux = null;
									}
								} catch (IOException e3) {
								} // No se hace nada.
							}

							canalCliente.close();
							canalCliente.socket().close();// se cierra el socket
															// asociado.
						}
						it.remove();
					}
				}
			}

		} catch (java.net.BindException e1) {
			System.out.println("No puede arrancarse el servidor por el puerto "
					+ PUERTO + " Seguramente, el puerto est‡ ocupado.");
		} catch (java.lang.SecurityException e2) {
			System.out
					.println("No puede arrancarse el servidor por el puerto "
							+ PUERTO
							+ ". Seguramente, hay restricciones de seguridad.");
		} catch (IOException e3) {
			System.out.println("algo paso");
			if (socketServidor != null) {
				try {
					socketServidor.close();
				} catch (IOException e4) {
				} // No se hace nada.
			}
		} // fin del try-catch
	}

}
