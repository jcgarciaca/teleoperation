package comunicacionlan;

public class VariablesGlobales {
	
	public static int posS = 0, posL = 0, posU = 0, posR = 0, posB = 0, posT = 0;
	public static boolean mover = false, closePort = false;	
	
	public void changeS(int nuevo){
		posS = nuevo;
	}
	
	public void changeL(int nuevo){
		posL = nuevo;
	}
	
	public void changeU(int nuevo){
		posU = nuevo;
	}
	
	public void changeR(int nuevo){
		posR = nuevo;
	}
	
	public void changeB(int nuevo){
		posB = nuevo;
	}
	
	public void changeT(int nuevo){
		posT = nuevo;
	}
	
	public int getS(){
		return posS;
	}
	
	public int getL(){
		return posL;
	}
	
	public int getU(){
		return posU;
	}
	
	public int getR(){
		return posR;
	}
	
	public int getB(){
		return posB;
	}
	
	public int getT(){
		return posT;
	}
	
	public void changeMover(boolean nMover){
		mover = nMover;
	}
	
	public boolean getMover(){
		return mover;
	}
	
	public void changeClose(boolean nClose){
		closePort = nClose;
	}
	
	public boolean getClose(){
		return closePort;
	}
}
