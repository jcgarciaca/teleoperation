package receptorvideo;

import processing.core.*;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.*;

import javax.imageio.*;

public class ReceptorVideo extends PApplet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Port we are receiving.
	int port = 9000;
	DatagramSocket ds; 
	// A byte array to read into (max size of 65536, could be smaller)
	byte[] buffer = new byte[65536]; 

	PImage video;
	
	
	public void setup(){
		size(400,300);
		  try {
		    ds = new DatagramSocket(port);
		  } catch (SocketException e) {
		    e.printStackTrace();
		  } 
		  video = createImage(320,240,RGB);
	}
	
	public void draw(){
		// checkForImage() is blocking, stay tuned for threaded example!
		  checkForImage();

		  // Draw the image
		  background(0);
		  imageMode(CENTER);
		  image(video,width/2,height/2);
	}
	
	public void checkForImage(){
		DatagramPacket p = new DatagramPacket(buffer, buffer.length); 
		  try {
		    ds.receive(p);
		  } catch (IOException e) {
		    e.printStackTrace();
		  } 
		  byte[] data = p.getData();

		  println("Received datagram with " + data.length + " bytes." );

		  // Read incoming data into a ByteArrayInputStream
		  ByteArrayInputStream bais = new ByteArrayInputStream( data );

		  // We need to unpack JPG and put it in the PImage video
		  video.loadPixels();
		  try {
		    // Make a BufferedImage out of the incoming bytes
		    BufferedImage img = ImageIO.read(bais);
		    // Put the pixels into the video PImage
		    img.getRGB(0, 0, video.width, video.height, video.pixels, 0, video.width);
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		  // Update the PImage pixels
		  video.updatePixels();
	}
	

}
