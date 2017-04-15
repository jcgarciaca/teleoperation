package comunicacionlan;

public class Ejecucion extends Thread{
	
	VariablesGlobales vg = new VariablesGlobales();
	
	public void run(){
		while(true){
			if(vg.getMover()){
				System.out.println("Moviendo");
				vg.changeMover(false);
				ComunicacionLAN.SetPosition(vg.getS(), vg.getL(), vg.getU(),					
						vg.getR(), vg.getB(), vg.getT());
				System.out.println("Termino Moviendo");
			}
		}
	}

}
