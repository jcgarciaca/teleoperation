package comunicacionlan;

public class Movement extends Thread{
	
	VariablesGlobales vg = new VariablesGlobales();
	
	public void run(){
		if(vg.getMover()){
			vg.changeMover(false);
			System.out.println("Moviendo");
			ComunicacionLAN.SetPosition(vg.getS(), vg.getL(), vg.getU(), 
					vg.getR(), vg.getB(), vg.getT());
			System.out.println("Termina moviendo");
		}
	}

}
