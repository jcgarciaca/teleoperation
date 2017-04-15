package pruebasonservidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import processing.core.*;

public class PruebaConServidor extends PApplet{
	
	public void setup(){
		size(400, 400);
	}
	
	public void draw(){
		background(128);
		noStroke();
		fill(255,0,0);
		rect(100, 100, 100, 100);
	}
	
	public void mousePressed(){
		if(mouseX <= 200){
			try {
				enviarDatos();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error en envio");
			}
		}
	}
	
	public void keyPressed(){
		if(keyCode == ENTER){
			try {
				enviarDatos();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error en envio");
			}
		}
	}
	
	public void enviarDatos() throws IOException{
		SocketChannel canalCliente = SocketChannel.open();
        canalCliente.connect(new InetSocketAddress("192.168.255.4", 9000));
        
        
        int vS=0;
    	int vL=1;
    	int vU=2;
    	int vR=3;
    	int vB=4;
    	int vT=5;
        
        ByteBuffer bufS=ByteBuffer.allocate(5);
        ByteBuffer bufL=ByteBuffer.allocate(5);
        ByteBuffer bufU=ByteBuffer.allocate(5);
        ByteBuffer bufR=ByteBuffer.allocate(5);
        ByteBuffer bufB=ByteBuffer.allocate(5);
        ByteBuffer bufT=ByteBuffer.allocate(5);
        
        bufS.asIntBuffer().put(vS);
		bufL.asIntBuffer().put(vL);
		bufU.asIntBuffer().put(vU);
		bufR.asIntBuffer().put(vB);
		bufB.asIntBuffer().put(vR);
		bufT.asIntBuffer().put(vT);
		
		canalCliente.write(new ByteBuffer[]{bufS,bufL,bufU,bufB,bufR,bufT});
        
        canalCliente.close();
        canalCliente.socket().close();
        
	}

}
