package guimotoman;

import processing.core.PApplet;
import remixlab.proscene.Scene;
import toxi.geom.mesh.STLReader;
import toxi.geom.mesh.TriangleMesh;
import toxi.processing.ToxiclibsSupport;

public class GUIMotoman extends PApplet{
	
	Scene scene;
	Motoman motoman;
	
	TriangleMesh meshBase, meshS, meshL, meshR, meshU, meshB, meshT;
	ToxiclibsSupport gfxBase, gfxS, gfxL, gfxR, gfxU, gfxB, gfxT;
	
	float[] ang = {0, 0, 0, 0, 0, 0};
	
	//float[] ang = new float[6]; 
	
	public void setup(){
		size(640, 480, P3D);
		scene = new Scene(this);
		scene.setAxisIsDrawn(false);
		scene.setGridIsDrawn(false);
		scene.setFrameSelectionHintIsDrawn(true);
		// press 'f' to display frame selection hints
		scene.setShortcut('f', Scene.KeyboardAction.DRAW_FRAME_SELECTION_HINT);
		
		meshBase = (TriangleMesh)new STLReader().loadBinary(sketchPath("base.stl"),STLReader.TRIANGLEMESH);
		meshS = (TriangleMesh)new STLReader().loadBinary(sketchPath("ejeS.stl"),STLReader.TRIANGLEMESH);
		meshL = (TriangleMesh)new STLReader().loadBinary(sketchPath("ejeL.stl"),STLReader.TRIANGLEMESH);
		meshU = (TriangleMesh)new STLReader().loadBinary(sketchPath("ejeU.stl"),STLReader.TRIANGLEMESH);
		meshR = (TriangleMesh)new STLReader().loadBinary(sketchPath("ejeR.stl"),STLReader.TRIANGLEMESH);
		meshB = (TriangleMesh)new STLReader().loadBinary(sketchPath("ejeB.stl"),STLReader.TRIANGLEMESH);
		meshT = (TriangleMesh)new STLReader().loadBinary(sketchPath("ejeT.stl"),STLReader.TRIANGLEMESH);
		
		gfxBase = new ToxiclibsSupport(this);
		gfxS = new ToxiclibsSupport(this);
		gfxL = new ToxiclibsSupport(this);
		gfxU = new ToxiclibsSupport(this);
		gfxR = new ToxiclibsSupport(this);
		gfxB = new ToxiclibsSupport(this);
		gfxT = new ToxiclibsSupport(this);
		
		motoman = new Motoman(scene, meshBase, gfxBase, meshS, gfxS, meshL, gfxL, meshU, gfxU, meshR, gfxR, meshB, gfxB, meshT, gfxT);
	}
	
	public void draw(){
		background(0);
		lights();
		motoman.draw(ang);
		//draw the ground
		noStroke();
		fill(120, 120, 120);
		float nbPatches = 100;
		normal(0.0f,0.0f,1.0f);
		for (int j=0; j<nbPatches; ++j) {
		beginShape(QUAD_STRIP );
		for (int i=0; i<=nbPatches; ++i) {
		  vertex((200*(float)i/nbPatches-100), (200*j/nbPatches-100));
		  vertex((200*(float)i/nbPatches-100), (200*(float)(j+1)/nbPatches-100));
		}
		endShape();
		}
		
//		fill(255);
//		translate(-40, 0, 10);
//		box(20);
	}
	
	
	public void keyPressed(){
		if(keyCode == TAB){
			ang[0] -= 1;			
		}else if(keyCode == ENTER){
			ang[0] += 1;
		}
		System.out.println(ang[0]);
	}

}
