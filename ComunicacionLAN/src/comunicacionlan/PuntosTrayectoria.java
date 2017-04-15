package comunicacionlan;

public class PuntosTrayectoria {
	
//	float ejeS, ejeL, ejeU, ejeR, ejeB, ejeT;
//	String tMov;
//	
//	int velInterpolacion;
//	int estGripper;
//	
//	public PuntosTrayectoria(float ejeS, float ejeL, float ejeU, float ejeR, float ejeB, float ejeT, String tMov, int velInterpolacion, int estGripper){
//		this.ejeS = ejeS;
//		this.ejeL = ejeL;
//		this.ejeU = ejeU;
//		this.ejeR = ejeR;
//		this.ejeB = ejeB;
//		this.ejeT = ejeT;
//		this.tMov = tMov;
//		this.velInterpolacion = velInterpolacion;
//		this.estGripper = estGripper;
//	}
	
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
