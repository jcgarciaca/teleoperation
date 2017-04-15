package guimotoman;

import processing.core.*;
import remixlab.proscene.*;
import remixlab.proscene.Quaternion;
import toxi.geom.*;
import toxi.geom.mesh.*;
import toxi.processing.*;


public class Motoman {
	
	InteractiveFrame [] frameArray;
	PGraphics3D pgl;
	Scene my_scene;
	
	TriangleMesh meshBase, meshS, meshL, meshR, meshU, meshB, meshT;
	ToxiclibsSupport gfxBase, gfxS, gfxL, gfxR, gfxU, gfxB, gfxT;	
	
	public Motoman(Scene scene, TriangleMesh meshBas, ToxiclibsSupport gfxBas, 
			TriangleMesh meshAS, ToxiclibsSupport gfxAS, 
			TriangleMesh meshAL, ToxiclibsSupport gfxAL, 
			TriangleMesh meshAU, ToxiclibsSupport gfxAU, 
			TriangleMesh meshAR, ToxiclibsSupport gfxAR, 
			TriangleMesh meshAB, ToxiclibsSupport gfxAB,
			TriangleMesh meshAT, ToxiclibsSupport gfxAT){
		my_scene = scene;
		pgl = scene.pg3d;
		frameArray = new InteractiveFrame[6];
	    for (int i=0; i<6; ++i) {
	    	frameArray[i] = new InteractiveFrame(scene);
		    // Creates a hierarchy of frames
		    if (i>0) frame(i).setReferenceFrame(frame(i-1));
	    }
	    
	    meshBase = meshBas;
	    meshS = meshAS;
	    meshL = meshAL;
	    meshU = meshAU;
	    meshR = meshAR;
	    meshB = meshAB;
	    meshT = meshAT;
	    
	    gfxBase = gfxBas;
	    gfxS = gfxAS;
	    gfxL = gfxAL;
	    gfxU = gfxAU;
	    gfxR = gfxAR;
	    gfxB = gfxAB;
	    gfxT = gfxAT;
	    
	    // Initialize frames
	    frame(0).setTranslation(0, 0, (float)16.8);
	    frame(1).setTranslation(15, -10, (float)28.2); // Ubicación Articulacion L
	    frame(2).setTranslation(0, 4, (float)61.4);  // Posicion Articulacion U
	    frame(3).setTranslation((float)21.8, 7, (float)15.5);  // Posicion Articulacion R
	    frame(4).setTranslation((float)42.2, 0, 0);  // Posicion Articulacion B
	    frame(5).setTranslation(0, 0, (float)-8.5);  // Posicion Articulacion T
	    
	    
	    // Set frame constraints
//	    WorldConstraint baseConstraint = new WorldConstraint();
//	    baseConstraint.setTranslationConstraint(AxisPlaneConstraint.Type.PLANE, new PVector(0.0f,0.0f,1.0f));
//	    baseConstraint.setRotationConstraint(AxisPlaneConstraint.Type.AXIS, new PVector(0.0f,0.0f,1.0f));
//	    frame(0).setConstraint(baseConstraint);
	    
	    
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
	}
	
	
	public void draw(float[] ang) {
		
		pgl.fill(0,255,0);
		pgl.scale(1);
		pgl.noStroke();
		gfxBase.mesh(meshBase,false);
		
		pgl.pushMatrix();
		
		frame(0).applyTransformation();
		//frame(0).rotate(0, 0, (float)(1 - (ang[0]*0.1)), 0);
		System.out.println(frame(0).position());
		//frame(0).setOrientation(0, 0, ang[0], (float)0.1);
		//frame(0).rotate(new Quaternion(ang));
		//frame(0).applyTransformation();
		setColor( frame(0).grabsMouse() );
	    pgl.fill(0,0,255);
		pgl.scale(1);
		pgl.noStroke();
	    gfxS.mesh(meshS,false);
	    
	    pgl.pushMatrix();//not really necessary here
	    frame(1).applyTransformation();
	    setColor( frame(1).grabsMouse() );
	    pgl.fill(255,0,0);
		pgl.scale(1);
		pgl.noStroke();
	    gfxL.mesh(meshL,false);
	    
	    pgl.pushMatrix();//not really necessary here
	    frame(2).applyTransformation();
	    setColor( frame(2).grabsMouse() );
	    pgl.fill(0,255,255);
		pgl.scale(1);
		pgl.noStroke();
	    gfxU.mesh(meshU,false);
	    
	    pgl.pushMatrix();//not really necessary here
	    frame(3).applyTransformation();
	    setColor( frame(3).grabsMouse() );
	    pgl.fill(255,0,255);
		pgl.scale(1);
		pgl.noStroke();
	    gfxR.mesh(meshR,false);
	    
	    pgl.pushMatrix();//not really necessary here
	    frame(4).applyTransformation();
	    setColor( frame(4).grabsMouse() );
	    pgl.fill(255,255,0);
		pgl.scale(1);
		pgl.noStroke();
	    gfxB.mesh(meshB,false);
	    
	    pgl.pushMatrix();//not really necessary here
	    frame(5).applyTransformation();
	    setColor( frame(5).grabsMouse() );
	    pgl.fill(128,128,128);
		pgl.scale(1);
		pgl.noStroke();
	    gfxT.mesh(meshT,false);
	    
	    pgl.popMatrix();//frame(5)
	    
	    pgl.popMatrix();//frame(4)
	        
	    pgl.popMatrix();//frame(3)
	    
	    pgl.popMatrix();//frame(2)
	    
	    pgl.popMatrix();//frame(1)
	    
	    //totally necessary
	    pgl.popMatrix();//frame(0)
	} 
	

	public void setColor(boolean selected) {
	    if (selected) pgl.fill(200, 200, 0);
	    else pgl.fill(200, 200, 200);
	}
	  
	public InteractiveFrame frame(int i) {
	    return frameArray[i];
	}

}

