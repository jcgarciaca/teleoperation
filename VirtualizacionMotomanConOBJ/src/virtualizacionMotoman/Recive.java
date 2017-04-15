package virtualizacionMotoman;

import java.net.*;
import java.util.StringTokenizer;
import java.io.*;



public class Recive{
	
	private static final int PUERTO = 9002; // puerto por omisi�n
	private Socket socketCliente; // socket para enlazar con el socket de servidor

	
	public static void main(String[] args) throws Exception{
		//ComunicacionLAN.OpenPort();		
		new Recive();
	}
	/*
	public static void main(String args[]) {
		new ClienteChat(args);
	}*/
	/** Constructor
	*
	* @param args nombre de la m�quina donde se ejecuta el servidor y puerto
	* por el que escucha.
	*/
	public Recive() {
		//iniciarPuertoSerial();
		System.out.println("Arrancando el cliente.");
		arrancarCliente();
		procesarMensajes();
		
	}
	
	//private Com com1;
	/*public void iniciarPuertoSerial(){
		//Definici�n de parametros
		Parameters settings;
		try {
			settings = new Parameters();	
		
		//definici�n del puerto que se va a utilizar
			
			
		settings.setPort("COM3");	
		settings.setBaudRate("115200");		
		//asignamos los parametros al objeto com1
		com1 = new Com(settings);
		
		Thread.sleep (5000);
		
		System.out.println("ok");		
		
		
		//envio de cadena de caracteres
		String Datos = null;
		String caracter="";
		String recibido="";
		for(int i=0;i<10;i++){
			recibido="";
			caracter="";
			while(!caracter.equals("\n")){
				caracter =com1.receiveSingleString();
				recibido += caracter;
			}
			//Thread.sleep (500);		
			System.out.print(recibido);		
		}		
		Thread.sleep (500);	
		System.out.println("Puerto Abierto");		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	private void arrancarCliente() {
		// Si se introduce como argumento un nombre de m�quina y un n�mero de puerto, se
		// considera que el servidor est� ubicado all� y que escucha por ese n�mero de puerto; en caso
		// contrario, se considera que est� en la m�quina local (localhost) y que escucha por el
		// puerto PUERTO.
		// Cualquier excepci�n en la creaci�n del socket es fatal para el programa. Para dar la
		// mayor informaci�n posible al usuario se tratan los cuatro tipos posibles de
		// excepciones:
		// 1) Excepci�n porque no se ha introducido un n�mero puerto v�lido (es decir, entero).
		// 2) Excepci�n porque no se encuentra ning�n ordenador con el nombre introducido por el
		// usuario.
		// 3) Excepci�n debida a restricciones de seguridad en el ordenador servidor.
		// 4) Excepci�n general de Entrada/Salida.
		try {
			
			socketCliente = new Socket("168.176.27.186", PUERTO); // puerto del servidor por omisi�n
			//socketCliente = new Socket("localhost", PUERTO);
			//socketCliente = new Socket("192.168.1.101", PUERTO);
			/*
			if (args.length == 2 )
				socketCliente = new Socket (args[0], Integer.parseInt(args[1]));
			else
				socketCliente = new Socket("localhost", PUERTO); // puerto del servidor por omisi�n
			*/
			System.out.println("cliente Iniciado.");
		}
		catch (java.lang.NumberFormatException e1) {
			// No se puede arrancar el cliente porque se introdujo un n�mero de puerto que no es entero.
			// Error irrecuperable: se sale del programa. No hace falta limpiar el socket, pues no lleg� a
			// crearse.
			errorFatal(e1, "N�mero de puerto inv�lido.");
		}
		catch (java.net.UnknownHostException e2) {
			// No se puede arrancar el cliente. Error irrecuperable: se sale del programa.
			// No hace falta limpiar el socket, pues no lleg� a crearse.
			errorFatal(e2, "No se localiza el ordenador servidor con ese nombre.");
		}
		catch (java.lang.SecurityException e3) {
			// No se puede arrancar el cliente. Error irrecuperable: se sale del programa.
			// No hace falta limpiar el socket, pues no lleg� a crearse.
			String mensaje ="Hay restricciones de seguridad en el servidor para conectarse por el " +
			"puerto " + PUERTO;
			errorFatal(e3, mensaje);
		}
		catch (IOException e4) {
			// No se puede arrancar el cliente. Error irrecuperable: se sale del programa.
			// No hace falta limpiar el socket, pues no lleg� a crearse.
			String mensaje = "No se puede conectar con el puerto " + PUERTO + " de la m�quina " +
			"servidora. Aseg�rese de que el servidor est� en marcha.";
			errorFatal(e4, mensaje);
		}
	}
	
	/** Crea los flujos de entrada y salida asociados al socket que ha llevado a cabo con �xito su
	* conexi�n al servidor y asocia un hilo ThreadCliente al flujo de entrada del socket. As�
	* el usuario podr� a la vez escribir y recibir mensajes.
	*/
	private PrintWriter salida;
	private BufferedReader entradaConsola;
	private void procesarMensajes() {
		// flujos de Entrada/Salida
		BufferedReader entrada=null;
		salida=null;
		// Se crean los flujos de E/S: dos para el socket y uno para la entrada por consola.
		
		try {
			
			entrada= new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			salida = new PrintWriter(socketCliente.getOutputStream(), true);
			entradaConsola = new BufferedReader(new InputStreamReader(System.in ));			
						
			//ObjectInputStream is = new ObjectInputStream(socketCliente.getInputStream());
			//ObjetoPrueba o1 = (ObjetoPrueba)is.readObject();
			//is.close();
			
			
			// Se crea un hilo que se encarga de recibir y mostrar por consola los mensajes del
			// servidor (procedentes de otros clientes).
			/*System.out.println("Entra");
			System.out.println("leer: "+o1.getNumero());
			System.out.println("leer: "+o1.getTexto());*/
			new ThreadClienteLocal(entrada,salida);
			
			// Se entra en un bucle infinito para leer la entrada del usuario por la consola y enviarla al
			// servidor de chat.
			
			/*while (true)
				salida.println(entradaConsola.readLine());*/	
			
			
			new ThreadProceso(salida, entradaConsola);
			
			
			

		}
		catch (IOException e) {
			e.printStackTrace();
			// Limpieza del socket y de los flujos asociados para que el cierre sea "limpio".
			if ( entrada != null) {
				try {
					entrada.close();
				}
				catch (Exception e1) {
					entrada = null;
				}
			}
			if ( salida != null) {
				try {
					salida.close();
				}
				catch (Exception e1) {
						salida = null;
				}
			}
			if ( socketCliente != null) {
				try {
					socketCliente.close();
				}
				catch (Exception e1) {
					socketCliente = null;
				}
			}
			//Aviso al usuario y cierre.
			String mensaje = "Se ha perdido la comunicaci�n con el servidor. Seguramente se debe a "+
			" que se ha cerrado el servidor o a errores de transmisi�n";
			errorFatal(e, mensaje);
		}
	}
	
	/**
	* Informa al usuario de la excepci�n arrojada y sale de la aplicaci�n.
	*
	* @param excepcion excepci�n cuya informaci�n se va a mostrar.
	* @param mensajeError mensaje orientativo sobre la excepci�n .
	*/
	private static void errorFatal(Exception excepcion, String mensajeError) {
		excepcion.printStackTrace();
		//JOptionPane.showMessageDialog(null, "Error fatal."+ System.getProperty("line.separator") +
		//mensajeError, "Informaci�n para el usuario", JOptionPane.WARNING_MESSAGE);
		System.exit(-1);
	}
}
	/**
	* Esta clase representa el hilo que gestiona la entrada de mensajes procedentes del
	* servidor de chat.
	*/
class ThreadClienteLocal extends Thread {
	private BufferedReader entrada;
	private PrintWriter salida;
	private ComunicacionLAN motoman = new ComunicacionLAN();
	/**
	* Constructor.
	*
	* @param entrada BufferedReader bufferedReader con la entrada que llega al socket de
	* ClienteChat
	 * @param com1 
	* @throws IOException
	*/
	public ThreadClienteLocal(BufferedReader entrada, PrintWriter salida) throws IOException {
		this.entrada=entrada;
		this.salida=salida;
		start(); // Se arranca el hilo.
	}
	/**
	* Muestra por consola los mensajes enviados por el servidor.
	*/
	public void run() {
		//�ltima l�nea del aviso de desconexi�n por falta de actividad.
		String fin1 = "> *****************ADIOS*****************";
		//�ltima l�nea del aviso de desconexi�n por uso de la orden DESCONECTAR.
		String fin2 = "> ***********HASTA LA VISTA****************";
		String linea = null;
		StringTokenizer st;
		String A;
        int[] a= new int[6];
        int n=0;
		try {
					
			/*System.out.println("primero: "+entrada.read());
			String aa= entrada.toString();
			
			//entrada.
			
			byte[] bytes = aa.getBytes();			
			
			
			ByteArrayInputStream bs= new ByteArrayInputStream( bytes ); // bytes es el byte[]
			ObjectInputStream is = new ObjectInputStream(bs);			
			
			ObjetoPrueba o1 = (ObjetoPrueba)is.readObject();
			is.close();
			
			System.out.println("atributos: "+ o1.getNumero() );
			System.out.println("atributos: "+ o1.getTexto() );*/
			

			
			while( ( linea=entrada.readLine() ) != null ) {
				

		        st = new StringTokenizer(linea,";",true);
		        
		        n=0;
		        salida.println("num de tokens "+st.countTokens());
		        while (st.hasMoreTokens() && st.countTokens()>5) {
		        	A=st.nextToken();
		            if(!(A.equals(";"))){
		            	a[n]=Integer.parseInt(A);
		            	System.out.println(a[n]);
		            	n++;
		            }
		        }

		        motoman.enviarPosicion(a[0],a[1],a[2],a[3],a[4],a[5]);
				
		        
				System.out.println(">Lleg� un Dato:" +linea);
				salida.println(" Recibido> "+linea+"\n");
		        
		        
				// Si se produce una desconexi�n por que se ha terminado el tiempo permitido de
				// inactividad o porque se ha dado previamente la orden DESCONECTAR, se sale del
				// bucle.
				if ( linea.equals(fin1) || linea.equals(fin2) )
					break;
			}
			
			
		}
		catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (entrada !=null) {
				try {
					entrada.close();
				}
				catch (IOException e2) {} // No se hace nada con la excepci�n
			}
			System.exit(-1);
		}
	} // fin try-catch-finally
}

class ThreadProceso extends Thread {
	private BufferedReader entradaConsola;
	private PrintWriter salida;

	public ThreadProceso(PrintWriter salida, BufferedReader entradaConsola) {
		this.entradaConsola=entradaConsola;
		this.salida=salida;
		start();
	}
	/**
	* Muestra por consola los mensajes enviados por el servidor.
	*/
	public void run() {	
		String linea="";
		while (true)
			try {			
				linea=entradaConsola.readLine();
				salida.println("Msj. M�quina RP> "+linea);	
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}