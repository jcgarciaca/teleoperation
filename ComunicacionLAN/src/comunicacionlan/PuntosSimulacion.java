package comunicacionlan;

public class PuntosSimulacion {
	
	float ejeS, ejeL, ejeU, ejeR, ejeB, ejeT;
	int estGripper;
	
	public PuntosSimulacion(float ejeS, float ejeL, float ejeU, float ejeR, float ejeB, float ejeT, int estGripper){
		this.ejeS = ejeS;
		this.ejeL = ejeL;
		this.ejeU = ejeU;		
		this.ejeR = ejeR;		
		this.ejeB = ejeB;
		this.ejeT = ejeT;
		this.estGripper = estGripper;
	}
}
