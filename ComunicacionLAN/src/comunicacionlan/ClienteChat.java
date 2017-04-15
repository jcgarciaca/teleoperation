//package comunicacionlan;
//
//import java.net.*;
//import java.nio.ByteBuffer;
//import java.io.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.applet.*;
//
///**
//* Esta clase actúa como cliente para el chat. Recibe los mensajes de otros clientes y los muestra
//* por consola (la Entrada/Salida estándar).
//*
//* El programa puede ejecutarse sin argumentos (se considera que el programa servidor
//* ServidorChat.java se ejecuta en la máquina local y por el número de puerto 9000) o con dos
//* argumentos (nombre del ordenador servidor y número de puerto). Si no se introducen dos
//* argumentos, se considera que se está en el primer caso.
//* Ejemplos de uso: java ClienteChat; java ClienteChat www.mimaquina.com 1200
//*
//* Si tiene el programa servidor en una máquina remota e intenta usar este programa cliente desde
//* una red con cortafuegos o un proxy, ejecútelo con código como éste: <p>
//* java -DproxySet=true -DproxyHost=PROXY -DproxyPort=PUERTO ClienteChat www.mimaquina.com
//* 1200 </p>
//* (PROXY es el nombre de la máquina que actúa como proxy o cortafuegos y PUERTO es el
//* número de puerto por el que escucha esta máquina).
//*
//*/
//
//
//public class ClienteChat extends Applet implements ActionListener{
//	
//	private int PUERTO = 9002; // puerto por omisión
//	private Socket socketCliente; // socket para enlazar con el socket de servidor
//	/** Punto de entrada a la aplicación. En este método se arranca el cliente
//	* de chat y se comienza el procesado de los mensajes de los clientes.
//	*
//	* @param args nombre de la máquina donde se ejecuta el servidor y puerto
//	* por el que escucha.
//	*/
//	
//	private Button buttonEnviar;
//	private Button buttonConectar;
//	private TextField CampoCod;
//	private TextArea txtConsola;
//	private Panel PDatos;
//	private Panel PConsola;
//	private TextField nPuerto;
//	
//	public void init() {	
//		
//		//this.setLayout(new GridLayout(2,1));
//		
//		PDatos = new Panel();
//		PConsola= new Panel();
//		
//		buttonConectar=new Button( "CONECTAR" );  
//		buttonEnviar = new Button( "Enviar" );  
//		CampoCod = new TextField();
//		CampoCod.setColumns(10);
//		
//		nPuerto = new TextField( String.valueOf(PUERTO) );
//		nPuerto.setColumns(10);
//		
//		txtConsola = new  TextArea(10,10);
//		txtConsola.append("Consola"+"\n");
//		txtConsola.setColumns(30);
//		txtConsola.setEditable(false);
//		//txtConsola.setEnabled(false);
//		  
//		PDatos.add( CampoCod );   
//		PDatos.add( buttonEnviar );
//		PDatos.add( buttonConectar );
//		PDatos.add( nPuerto );
//		PConsola.add( txtConsola );
//		
//		add(PDatos);
//		add(PConsola);
//	    
//	    this.setSize(400, 250);
//	    
//	    buttonEnviar.addActionListener(this);
//	    buttonConectar.addActionListener(this);
//	    
//	    
//	}
//	/*
//	public static void main(String args[]) {
//		new ClienteChat(args);
//	}*/
//	/** Constructor
//	*
//	* @param args nombre de la máquina donde se ejecuta el servidor y puerto
//	* por el que escucha.
//	*/
//	public void IniciarClienteChat() {
//		
//		//Frame f1 = new Frame("Ventana");
//		//f1.setSize(300, 300);
//		//f1.setVisible(true);
//		
//		System.out.println("Arrancando el cliente.");
//		arrancarCliente();
//		procesarMensajes();
//	}
//	/** Arranca el cliente: lo intenta conectar al servidor por el núm. de puerto PUERTO o por
//	* el especificado como argumento.
//	*
//	* @param args nombre de la máquina donde se ejecuta el servidor y puerto
//	* por el que escucha.
//	*/
//	private void arrancarCliente() {
//		// Si se introduce como argumento un nombre de máquina y un número de puerto, se
//		// considera que el servidor está ubicado allí y que escucha por ese número de puerto; en caso
//		// contrario, se considera que está en la máquina local (localhost) y que escucha por el
//		// puerto PUERTO.
//		// Cualquier excepción en la creación del socket es fatal para el programa. Para dar la
//		// mayor información posible al usuario se tratan los cuatro tipos posibles de
//		// excepciones:
//		// 1) Excepción porque no se ha introducido un número puerto válido (es decir, entero).
//		// 2) Excepción porque no se encuentra ningún ordenador con el nombre introducido por el
//		// usuario.
//		// 3) Excepción debida a restricciones de seguridad en el ordenador servidor.
//		// 4) Excepción general de Entrada/Salida.
//		try {
//			
//			//socketCliente = new Socket("10.203.29.240", PUERTO); // puerto del servidor por omisión
//			//socketCliente = new Socket("localhost", PUERTO);
//			socketCliente = new Socket("www.labmecatronica.unal.edu.co", PUERTO);
//			//socketCliente = new Socket("168.176.26.91", PUERTO);
//			//www.labmecatronica.unal.edu.co
//			/*
//			if (args.length == 2 )
//				socketCliente = new Socket (args[0], Integer.parseInt(args[1]));
//			else
//				socketCliente = new Socket("localhost", PUERTO); // puerto del servidor por omisión
//			*/
//			System.out.println("cliente Iniciado.");
//		}
//		catch (java.lang.NumberFormatException e1) {
//			// No se puede arrancar el cliente porque se introdujo un número de puerto que no es entero.
//			// Error irrecuperable: se sale del programa. No hace falta limpiar el socket, pues no llegó a
//			// crearse.
//			errorFatal(e1, "Número de puerto inválido.");
//		}
//		catch (java.net.UnknownHostException e2) {
//			// No se puede arrancar el cliente. Error irrecuperable: se sale del programa.
//			// No hace falta limpiar el socket, pues no llegó a crearse.
//			errorFatal(e2, "No se localiza el ordenador servidor con ese nombre.");
//		}
//		catch (java.lang.SecurityException e3) {
//			// No se puede arrancar el cliente. Error irrecuperable: se sale del programa.
//			// No hace falta limpiar el socket, pues no llegó a crearse.
//			String mensaje ="Hay restricciones de seguridad en el servidor para conectarse por el " +
//			"puerto " + PUERTO;
//			errorFatal(e3, mensaje);
//		}
//		catch (IOException e4) {
//			// No se puede arrancar el cliente. Error irrecuperable: se sale del programa.
//			// No hace falta limpiar el socket, pues no llegó a crearse.
//			String mensaje = "No se puede conectar con el puerto " + PUERTO + " de la máquina " +
//			"servidora. Asegúrese de que el servidor está en marcha.";
//			errorFatal(e4, mensaje);
//		}
//	}
//	
//	/** Crea los flujos de entrada y salida asociados al socket que ha llevado a cabo con éxito su
//	* conexión al servidor y asocia un hilo ThreadCliente al flujo de entrada del socket. Así
//	* el usuario podrá a la vez escribir y recibir mensajes.
//	*/
//	private PrintWriter salida;
//	private BufferedReader entradaConsola;
//	private void procesarMensajes() {
//		// flujos de Entrada/Salida
//		BufferedReader entrada=null;
//		salida=null;
//		// Se crean los flujos de E/S: dos para el socket y uno para la entrada por consola.
//		
//		try {
//			
//			entrada= new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
//			salida = new PrintWriter(socketCliente.getOutputStream(), true);
//			entradaConsola = new BufferedReader(new InputStreamReader(System.in ));			
//						
//			//ObjectInputStream is = new ObjectInputStream(socketCliente.getInputStream());
//			//ObjetoPrueba o1 = (ObjetoPrueba)is.readObject();
//			//is.close();
//			
//			
//			// Se crea un hilo que se encarga de recibir y mostrar por consola los mensajes del
//			// servidor (procedentes de otros clientes).
//			/*System.out.println("Entra");
//			System.out.println("leer: "+o1.getNumero());
//			System.out.println("leer: "+o1.getTexto());*/
//			new ThreadCliente(entrada,txtConsola);
//			
//			// Se entra en un bucle infinito para leer la entrada del usuario por la consola y enviarla al
//			// servidor de chat.
//			
//			/*while (true)
//				salida.println(entradaConsola.readLine());*/	
//			
//			
//			//new ThreadProceso(salida, entradaConsola,txtConsola);
//			
//
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//			// Limpieza del socket y de los flujos asociados para que el cierre sea "limpio".
//			if ( entrada != null) {
//				try {
//					entrada.close();
//				}
//				catch (Exception e1) {
//					entrada = null;
//				}
//			}
//			if ( salida != null) {
//				try {
//					salida.close();
//				}
//				catch (Exception e1) {
//						salida = null;
//				}
//			}
//			if ( socketCliente != null) {
//				try {
//					socketCliente.close();
//				}
//				catch (Exception e1) {
//					socketCliente = null;
//				}
//			}
//			//Aviso al usuario y cierre.
//			String mensaje = "Se ha perdido la comunicación con el servidor. Seguramente se debe a "+
//			" que se ha cerrado el servidor o a errores de transmisión";
//			errorFatal(e, mensaje);
//		}
//	}
//	
//	/**
//	* Informa al usuario de la excepción arrojada y sale de la aplicación.
//	*
//	* @param excepcion excepción cuya información se va a mostrar.
//	* @param mensajeError mensaje orientativo sobre la excepción .
//	*/
//	private static void errorFatal(Exception excepcion, String mensajeError) {
//		excepcion.printStackTrace();
//		//JOptionPane.showMessageDialog(null, "Error fatal."+ System.getProperty("line.separator") +
//		//mensajeError, "Información para el usuario", JOptionPane.WARNING_MESSAGE);
//		System.exit(-1);
//	}
//	
//	public void actionPerformed(ActionEvent e) {
//		
//		if (e.getSource() == buttonEnviar){
//			System.out.println( ">>>" + CampoCod.getText() );	
//			salida.println(CampoCod.getText());			
//			CampoCod.setText("");
//		}else if(e.getSource() == buttonConectar){					
//			buttonConectar.setEnabled(false);
//			PUERTO= Integer.valueOf(nPuerto.getText());
//			txtConsola.append("Conectado por "+PUERTO+"\n");
//			IniciarClienteChat();
//		}
//	}
//
//}
//	/**
//	* Esta clase representa el hilo que gestiona la entrada de mensajes procedentes del
//	* servidor de chat.
//	*/
//class ThreadCliente extends Thread {
//	private BufferedReader entrada;
//	private TextArea txtConsola;
//	/**
//	* Constructor.
//	*
//	* @param entrada BufferedReader bufferedReader con la entrada que llega al socket de
//	* ClienteChat
//	* @throws IOException
//	*/
//	public ThreadCliente (BufferedReader entrada,TextArea txtConsola) throws IOException {
//		this.entrada=entrada;
//		this.txtConsola=txtConsola;
//		start(); // Se arranca el hilo.
//	}
//	/**
//	* Muestra por consola los mensajes enviados por el servidor.
//	*/
//	public void run() {
//		//Última línea del aviso de desconexión por falta de actividad.
//		String fin1 = "> *****************ADIOS*****************";
//		//Última línea del aviso de desconexión por uso de la orden DESCONECTAR.
//		String fin2 = "> ***********HASTA LA VISTA****************";
//		String linea = null;
//		try {
//					
//			/*System.out.println("primero: "+entrada.read());
//			String aa= entrada.toString();
//			
//			//entrada.
//			
//			byte[] bytes = aa.getBytes();			
//			
//			
//			ByteArrayInputStream bs= new ByteArrayInputStream( bytes ); // bytes es el byte[]
//			ObjectInputStream is = new ObjectInputStream(bs);			
//			
//			ObjetoPrueba o1 = (ObjetoPrueba)is.readObject();
//			is.close();
//			
//			System.out.println("atributos: "+ o1.getNumero() );
//			System.out.println("atributos: "+ o1.getTexto() );*/
//		
//			while( ( linea=entrada.readLine() ) != null ) {
//				System.out.println(linea);
//				linea=linea.replace("Ã³", "ó");
//				txtConsola.append(linea+"\n");
//				// Si se produce una desconexión por que se ha terminado el tiempo permitido de
//				// inactividad o porque se ha dado previamente la orden DESCONECTAR, se sale del
//				// bucle.
//				if ( linea.equals(fin1) || linea.equals(fin2) )
//					break;
//			}
//			
//			
//		}
//		catch (IOException e1) {
//			e1.printStackTrace();
//		}finally {
//			if (entrada !=null) {
//				try {
//					entrada.close();
//				}
//				catch (IOException e2) {} // No se hace nada con la excepción
//			}
//			System.exit(-1);
//		}
//	} // fin try-catch-finally
//}
///*
//class ThreadProceso extends Thread {
//	private BufferedReader entradaConsola;
//	private PrintWriter salida;
//	private TextArea txtConsola;
//
//	public ThreadProceso(PrintWriter salida, BufferedReader entradaConsola, TextArea txtConsola) {
//		this.entradaConsola=entradaConsola;
//		this.salida=salida;
//		this.txtConsola=txtConsola;
//		start();
//	}
//	/**
//	* Muestra por consola los mensajes enviados por el servidor.
//	*/
//	/*
//	public void run() {	
//		String linea="";
//		while (true)
//			try {			
//				linea=entradaConsola.readLine();
//				salida.println(linea);	
//				txtConsola.append(linea+"\n");
//				
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//	}
//}*/