package comunicacionlan;

import processing.core.*;

public class Principal {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
			
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VariablesGlobales vg = new VariablesGlobales();
		ComunicacionLAN motoman = new ComunicacionLAN();
		
		motoman.abrirPuerto();
		PApplet.main(new String[] { "--present", "comunicacionlan.MotomanUnal" });
		
		System.out.println("Continua");
		
		vg.changeMover(true);
		
		while(true){
			if(vg.getMover()){
				vg.changeMover(false);
				ComunicacionLAN.SetPosition(vg.getS(), vg.getL(), vg.getU(), 
						vg.getR(), vg.getB(), vg.getT());
			}			
			if(vg.getClose()){
				vg.changeClose(false);
				ComunicacionLAN.ClosePort();
				System.exit(0);
				break;
			}
		}
	}

}
