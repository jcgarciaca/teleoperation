package lancommcomplete;

public class PuntosTrayectoria {
	
	int ejeS, ejeL, ejeU, ejeR, ejeB, ejeT, estGripper, timmer;
	String tMov;
	
	int velInterpolacion;
	
	int indice;
	
	public PuntosTrayectoria(int indice, int ejeS, int ejeL, int ejeU, int ejeR, int ejeB, int ejeT, int estGripper, int timmer,  String tMov, int velInterpolacion){
		this.indice = indice;		
		this.ejeS = ejeS;
		this.ejeL = ejeL;
		this.ejeU = ejeU;
		this.ejeR = ejeR;
		this.ejeB = ejeB;
		this.ejeT = ejeT;
		this.estGripper = estGripper;
		this.timmer = timmer;
		this.tMov = tMov;
		this.velInterpolacion = velInterpolacion;	
	}

}
