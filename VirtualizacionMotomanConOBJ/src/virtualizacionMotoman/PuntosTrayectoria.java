package virtualizacionMotoman;

public class PuntosTrayectoria {
	
	float ejeS, ejeL, ejeU, ejeR, ejeB, ejeT;
	String tMov;
	
	int velInterpolacion;
	int estGripper;
	
	public PuntosTrayectoria(float ejeS, float ejeL, float ejeU, float ejeR, float ejeB, float ejeT, String tMov, int velInterpolacion, int estGripper){
		this.ejeS = ejeS;
		this.ejeL = ejeL;
		this.ejeU = ejeU;
		this.ejeR = ejeR;
		this.ejeB = ejeB;
		this.ejeT = ejeT;
		this.tMov = tMov;
		this.velInterpolacion = velInterpolacion;
		this.estGripper = estGripper;
	}

}
