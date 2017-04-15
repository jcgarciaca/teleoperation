package cinematica;

public class Principal {
	
	public static void main(String[] args) {
		
		Cinematica cinematica = new Cinematica(798, 372, 815);
		cinematica.solve();
		System.out.println(cinematica.getAng()[0] + " " + cinematica.getAng()[1] + " " + cinematica.getAng()[2]);
	}

}
