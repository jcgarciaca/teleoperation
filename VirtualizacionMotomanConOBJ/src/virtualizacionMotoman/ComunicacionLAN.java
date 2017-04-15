package virtualizacionMotoman;

public class ComunicacionLAN {
	
	public static native int OpenPort();
	public static native int ClosePort();
	public static native int SetPosition(int posS, int posL, int posU, 
			int posR, int posB, int posT);
	public static native int ExecuteJob(String jobname);
	
	static{
		//System.loadLibrary("VBToJavaMotoCom");
	}
	//int pulsS, pulsL, pulsU, pulsR, pulsB, pulsT, ind;
	
	public void abrirPuerto(){
		System.out.println("Abre puerto " + OpenPort());
	}
	
	public void cerrarPuerto(){
		System.out.println("Abre puerto " + ClosePort());
	}
	
	public void enviarPosicion(int pulsS, int pulsL, int pulsU, int pulsR, int pulsB, int pulsT){
		System.out.println("Abre puerto " + SetPosition(pulsS, pulsL, pulsU, pulsR, pulsB, pulsT));
		System.out.println("Termina Posicionamiento");
	}
	
	public void prueba(){
		System.out.println("probado");
	}
	
	public static void main(String[] args) {
		  OpenPort();
    }
	
	
		
	/*public void run(){
	      // Aquí el código pesado que tarda mucho
//		if(ind == 0) {
//			System.out.println("Abre puerto " + OpenPort());
//			System.out.println("Termina 1");			
//		}
		//if(ind == 1) {
		//SetPosition(129177,0,0,0,0,0);
		//System.out.println("en hilo---> "+pulsS+" - "+pulsL+" - "+pulsU+" - ");
			//System.out.println("Set position " + SetPosition(pulsS, pulsL, pulsU, pulsR, pulsB, pulsT));
			System.out.println("Termina Posicionamiento");
		//}
//		else if(ind == 2) {
//			System.out.println("Cierra puerto " + ClosePort());
//			System.out.println("Termina 3");
//		}else{}
	}*/
	
	
	
	
	
	
	
//	public ComunicacionLAN() {
//        initComponents();
//    }
//	
//	public void mostrarTextoGUI(String texto){
//        jTextArea1.setText(jTextArea1.getText() + texto + "\n");
//    }
//    
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">
//    private void initComponents() {
//
//        jLabel1 = new javax.swing.JLabel();
//        jSeparator1 = new javax.swing.JSeparator();
//        jScrollPane1 = new javax.swing.JScrollPane();
//        jTextArea1 = new javax.swing.JTextArea();
//        botonConectar = new javax.swing.JButton();
//        botonCerrar = new javax.swing.JButton();
//        jLabel2 = new javax.swing.JLabel();
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//
//        jLabel1.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
//        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
//        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        jLabel1.setText("Robot Motoman MH6");
//
//        jTextArea1.setColumns(20);
//        jTextArea1.setRows(5);
//        jScrollPane1.setViewportView(jTextArea1);
//
//        botonConectar.setText("Conectar");
//        botonConectar.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                botonConectarActionPerformed(evt);
//            }
//        });
//
//        botonCerrar.setText("Cerrar");
//        botonCerrar.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                botonCerrarActionPerformed(evt);
//            }
//        });
//
//        jLabel2.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
//        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
//        jLabel2.setText("Universidad Nacional de Colombia");
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addGap(10, 10, 10)
//                .addComponent(jScrollPane1)
//                .addGap(10, 10, 10))
//            .addGroup(layout.createSequentialGroup()
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(layout.createSequentialGroup()
//                        .addContainerGap()
//                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
//                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
//                            .addComponent(jLabel1)
//                            .addComponent(jLabel2)))
//                    .addGroup(layout.createSequentialGroup()
//                        .addGap(79, 79, 79)
//                        .addComponent(botonConectar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addGap(56, 56, 56)
//                        .addComponent(botonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                .addContainerGap())
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addContainerGap()
//                .addComponent(jLabel1)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(jLabel2)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
//                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(botonConectar)
//                    .addComponent(botonCerrar))
//                .addContainerGap())
//        );
//
//        pack();
//    }// </editor-fold>
//
//    private void botonConectarActionPerformed(java.awt.event.ActionEvent evt) {
//        // TODO add your handling code here:
//		System.out.println("Abre puerto " + OpenPort());		
//    }
//
//    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {
//        // TODO add your handling code here:
//    	System.out.println("Cierra puerto " + ClosePort());
//    	System.exit(0);
//    }
//    
//    
//    //////////////////////Comunicacion NIO //////////////////////
//    private static ServerSocket socketServidor;
//	private static Selector selector;
//	private static ServerSocketChannel canalServidor;
//	
//	static ArrayList<PuntosTrayectoria> trayectoria = new ArrayList<PuntosTrayectoria>();
//	static PrintWriter salida;
//	
//	
//	public static final int PUERTO = 9002; // puerto por defecto
//	
//	static String IPServer="168.176.26.91";
//	
//	
//	//////////////////////Comunicacion NIO //////////////////////
//    
//    
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) throws java.io.IOException {    	
//    	
//    	int pulsS = 0, pulsL = 0, pulsU = 0, pulsR = 0, pulsB = 0, pulsT = 0;
//    	
////        Runtime.getRuntime().addShutdownHook(new Thread(){
////            public void run(){                
////                System.out.println("Cierra puerto " + ClosePort());
////            }
////        });        
//        
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ComunicacionLAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ComunicacionLAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ComunicacionLAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ComunicacionLAN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                new ComunicacionLAN().setVisible(true);
//            }
//        });        
//        
//        ////////////////////// Comunicacion NIO //////////////////////
//        try {
//			canalServidor = ServerSocketChannel.open();
//			canalServidor.configureBlocking(false);
//			ServerSocket socketServidor = canalServidor.socket();
//			InetSocketAddress dirSocket = new InetSocketAddress(IPServer,PUERTO);//168.176.36.125
//			socketServidor.bind(dirSocket);
//			Selector selector = Selector.open();
//			canalServidor.register(selector, SelectionKey.OP_ACCEPT);
//			System.out.println("El servidor esta escuchando por el puerto "
//					+ PUERTO);
//
//			int ses = 0;
//
//			while (true) { // bucle infinito
//
//				try {
//					ses = selector.select(); //
//					System.out.println("true");
//				} catch (IOException e) {
//					System.out.println("Error el llamar al metodo select();");
//				}
//
//				if (ses > 0) {// suceso E/S
//					Set claves = selector.selectedKeys();
//					Iterator it = claves.iterator();
//
//					while (it.hasNext()) {
//						System.out.println("it.hasNext()");
//						SelectionKey clave = (SelectionKey) it.next(); // Se
//																		// obtiene
//																		// cada
//																		// SelectionKey
//																		// del
//																		// Set.
//						if (clave.isAcceptable()) {
//
//							try {
//								System.out
//										.println("El cliente se ha conectado al servidor");
//								SocketChannel canalCliente = (SocketChannel) canalServidor
//										.accept();// lee lo que manda el cliente
//								canalCliente.configureBlocking(false);
//								canalCliente.register(selector,
//										SelectionKey.OP_READ);
//
//							}
//
//							catch (java.lang.SecurityException e1) {
//								System.out
//										.println("Restricciones de seguridad. Los clientes no se pueden conectar al puerto");
//								if (socketServidor != null) {
//									try {
//										socketServidor.close();
//									} catch (IOException e2) {
//									}
//								}
//
//							} catch (IOException e3) {// queda pendiente
//								System.out
//										.println("El cliente a cerrado la conexion subitamente");
//								ServerSocketChannel canalCliente = null;
//								if (canalCliente.socket() != null) {
//									try {
//										canalCliente.close();
//									} catch (IOException e4) {
//									}
//								}
//							}
//						}
//						if (clave.isReadable()) {// (clave.isReadable()){
//							System.out.println("El servidor lee datos mandados por el cliente");
//
//							SocketChannel canalCliente = (SocketChannel) clave.channel();// Se obtiene el canal de socket
//												// de la clave.
//							canalCliente.configureBlocking(false);
//							
//							ByteBuffer bufC=ByteBuffer.allocate(5); // 0 teach, 1 tarea, 2 borrar
//				   	    	ByteBuffer bufInd = ByteBuffer.allocate(5); // 0 posicion, 1 gripper, 2 timmer
//							ByteBuffer bufS=ByteBuffer.allocate(5);
//							ByteBuffer bufL=ByteBuffer.allocate(5);
//							ByteBuffer bufU=ByteBuffer.allocate(5);
//							ByteBuffer bufR=ByteBuffer.allocate(5);
//							ByteBuffer bufB=ByteBuffer.allocate(5);
//							ByteBuffer bufT=ByteBuffer.allocate(5);
//							ByteBuffer bufGripper=ByteBuffer.allocate(5);//
//							ByteBuffer bufTimmer=ByteBuffer.allocate(5);//
//							ByteBuffer buftypMovimiento=ByteBuffer.allocate(5);// tipo de movimiento
//							ByteBuffer buftvelParam=ByteBuffer.allocate(5);//
//							
//
//							try {
//								canalCliente.read(new ByteBuffer[]{bufC, bufInd, bufS,bufL,bufU,bufR,bufB,bufT,bufGripper,bufTimmer,buftypMovimiento,buftvelParam});
//								//nalCliente.write(new ByteBuffer[{bufC, bufInd, bufS,bufL,bufU,bufR,bufB,bufT,bufGripper,buftypMovimiento,buftvelParam});
//								bufC.rewind();
//								bufInd.rewind();
//								bufS.rewind();
//								bufL.rewind();
//								bufU.rewind();
//								bufR.rewind();
//								bufB.rewind();
//								bufT.rewind();
//								bufGripper.rewind();
//								bufTimmer.rewind();
//								buftypMovimiento.rewind();
//								buftvelParam.rewind();
//
//								int comando=bufC.asIntBuffer().get();
//								
//								if(comando==0){// se deben guardar los datos en un arreglo
//									int vS = bufS.asIntBuffer().get();
//									int vL = bufL.asIntBuffer().get();
//									int vU = bufU.asIntBuffer().get();
//									int vR = bufR.asIntBuffer().get();
//									int vB = bufB.asIntBuffer().get();
//									int vT = bufT.asIntBuffer().get();
//									int estGripper = bufGripper.asIntBuffer().get(); // 0 - abierto ----- 1 - cerrado
//									int timmer = bufTimmer.asIntBuffer().get(); //tiempo
//									int typMovimiento = buftypMovimiento.asIntBuffer().get();;//0 MOVJ; 1 MOVL
//									int velParam = buftvelParam.asIntBuffer().get();
//									int indice = bufInd.asIntBuffer().get();
//									String tMov = null;
//									if(typMovimiento == 0) tMov = "MOVJ";
//									else if(typMovimiento == 1) tMov = "MOVL";
//									
//									// esto es de prueba
//									if(estGripper==0)System.out.println("Gripper abierto");
//									if(estGripper==1)System.out.println("Gripper cerrado");
//									
//									
//									pulsS = (int)(vS * 14353 / 10);
//									pulsL = (int)(vL * 13003 / 10);
//									pulsU = (int)(vU * 13767 / 10);
//									pulsR = (int)(vR * 5689 / 10);
//									pulsB = (int)(vB * 8239 / 10);
//									pulsT = (int)(vT * 4255 / 10);
//									
//									
//									
//									System.out.println("Calculados los puntos");
//									System.out.println("Set position " + SetPosition(pulsS, pulsL, pulsU, pulsR, pulsB, pulsT));
//									System.out.println("Sale de ejecutar");
//									
//									trayectoria.add(new PuntosTrayectoria(indice, vS, vL, vU, vR, vB, vT, estGripper, timmer, tMov,  velParam));
//									System.out.println("comando: "+comando+" Indice: "+indice+" S:" + vS + " L:" + vL
//											+ " U:" + vU + " R:" + vR + " B:" + vB+ " T:" + vT + " timmer: "+timmer);
//																		
//									
//									
//									System.out.println("Posiciones: " + vS + ", " + vL + ", " + vU + ", "
//											+ vR + ", " + vB + ", " + vT);
//									
//								}
//								else if (comando==1){// se debe generar el archibo JBI, se da tarea
//									
//									int cont = 0;
//									for(int i = 0; i < trayectoria.size(); i++){							        	
//							        	if(trayectoria.get(i).indice == 0) cont++;							        
//							        }
//									
//									System.out.println("se debe generar el archivo jbi");
//									if(trayectoria.size() > 0){
//										salida = new PrintWriter(new FileWriter("MOTOMAN.JBI"));
//										salida.println("/JOB");
//										salida.println("//NAME MOTOMAN");
//										salida.println("//POS");
//										salida.println("///NPOS " + cont + ",0,0,0,0,0");
//										salida.println("///TOOL 0");
//										salida.println("///POSTYPE PULSE");
//										salida.println("///PULSE");	        
//								        
//										cont = 0;
//								        for(int i = 0; i < trayectoria.size(); i++){
//								        	
//								        	if(trayectoria.get(i).indice == 0){
//								        		
//									        	if(i < 10) salida.print("C0000" + cont + "=");
//									            else if(i < 100) salida.print("C000" + cont + "=");
//									            else if(i < 1000) salida.print("C00" + cont + "=");
//									            
//									            salida.println((int)(trayectoria.get(i).ejeS * 14353 / 10) + "," +
//									            		(int)(trayectoria.get(i).ejeL * 13003 / 10) + "," +
//									            		(int)(trayectoria.get(i).ejeU * 13767 / 10) + "," +
//									            		(int)(trayectoria.get(i).ejeR * 5689 / 10) + "," +
//									            		(int)(trayectoria.get(i).ejeB * 8239 / 10) + "," +
//									            		(int)(trayectoria.get(i).ejeT * 4255 / 10));
//									            cont++;
//								        	}
//								        }							        
//								        
//								        
//								        salida.println("//INST");
//								        salida.println("///DATE 2009/01/15 17:41");
//								        salida.println("///ATTR RW");
//								        salida.println("///GROUP1 RB1");
//								        salida.println("NOP");
//								        
//								        cont = 0;
//								        for(int i = 0; i < trayectoria.size(); i++){
//								        	if(trayectoria.get(i).indice == 0){
//									        	salida.print(trayectoria.get(i).tMov + " ");
//									            if(cont < 10) salida.print("C0000" + cont);
//									            else if(cont < 100) salida.print("C000" + cont);
//									            else if(cont < 1000) salida.print("C00" + cont);
//									            
//									            if(trayectoria.get(i).tMov == "MOVJ") salida.println(" VJ=" + trayectoria.get(i).velInterpolacion + ".00");
//									            else if(trayectoria.get(i).tMov == "MOVL") salida.println(" V=" + "46.0");//trayectoria.get(i).velInterpolacion);
//									            cont++;
//								        	}else if(trayectoria.get(i).indice == 1){
//								        		if(trayectoria.get(i).estGripper == 0) {
//								        			salida.println("TIMER T=1.00");
//								        			salida.println("HAND HNO: 2 OFF");
//								        			salida.println("TIMER T=1.00");
//								        		}
//								        		else if(trayectoria.get(i).estGripper == 1) {
//								        			salida.println("TIMER T=1.00");
//								        			salida.println("HAND HNO: 2 ON");
//								        			salida.println("TIMER T=1.00");
//								        		}
//								        	}else if (trayectoria.get(i).indice == 2){
//								        		salida.println("TIMER T="+trayectoria.get(i).timmer+".00");
//								        	}
//								        }
//								        salida.println("END");
//								        salida.close();		
////										trayectoria.clear();
//									}
//									System.out.println("Set position " + ExecuteJob("MOTOMAN.JBI"));
//									
//									
//								}else if (comando ==2){// borrar los puntos de latrayectoria
//									trayectoria.clear();
//									System.out.println("Borrar");
//								}
//
//							}
//
//							catch (IOException e2) {
//								System.out.println("Error en la conexion con el cliente. Desconexion o problemas con las redes intermedias");
//
//								canalCliente.close();
//								// canalCliente.socket().close();// se cierra el
//								// socket asociado.
//								try {
//									Socket saux = canalCliente.socket();
//									if (saux != null) {
//										saux.close();
//										saux = null;
//									}
//								} catch (IOException e3) {
//								} // No se hace nada.
//							}
//
//							canalCliente.close();
//							canalCliente.socket().close();// se cierra el socket
//															// asociado.
//						}
//						it.remove();
//					}
//				}
//			}
//
//		} catch (java.net.BindException e1) {
//			System.out.println("No puede arrancarse el servidor por el puerto "
//					+ PUERTO + " Seguramente, el puerto est‡ ocupado.");
//		} catch (java.lang.SecurityException e2) {
//			System.out
//					.println("No puede arrancarse el servidor por el puerto "
//							+ PUERTO
//							+ ". Seguramente, hay restricciones de seguridad.");
//		} catch (IOException e3) {
//			System.out.println("algo paso");
//			if (socketServidor != null) {
//				try {
//					socketServidor.close();
//				} catch (IOException e4) {
//				} // No se hace nada.
//			}
//		} // fin del try-catch
//        //////////////////////Comunicacion NIO //////////////////////
//    }
//    
//    
//
//
//	// Variables declaration - do not modify
//    private javax.swing.JButton botonCerrar;
//    private javax.swing.JButton botonConectar;
//    private javax.swing.JLabel jLabel1;
//    private javax.swing.JLabel jLabel2;
//    private javax.swing.JScrollPane jScrollPane1;
//    private javax.swing.JSeparator jSeparator1;
//    private javax.swing.JTextArea jTextArea1;
//    // End of variables declaration
//	
//	
////	public static void main(String[] args) {				
////		System.out.println("Inicia aplicación");
////		System.out.println("Abre puerto " + OpenPort());
////		System.out.println("Set position " + SetPosition(0, 0, 0, 0, 0, 0));
////		System.out.println("Cierra puerto " + ClosePort());
////	}
}
