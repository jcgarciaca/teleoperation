package cinematica;

public class Cinematica2 {
	
	float x = 0, y = 0, z = 0;
	float theta1 = 0, theta2 = 0, theta3 = 0;
	float[] theta = new float[3];
	int nIter = 100;
	
	Cinematica2 (float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	private float f(float u3){
		float func = (float) (Math.pow((Math.pow(((640*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) + (310*u3)/(Math.pow(u3, 2) + 1)), 2) + 
				Math.pow(((1280*u3)/(Math.pow(u3, 2) + 1) - (155*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) + 614), 2) - 
				Math.pow(x, 2) - Math.pow(y, 2) - Math.pow(z, 2) + 22500), 2)/90000 + (190340*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) - 
				(1571840*u3)/(Math.pow(u3, 2) + 1) + Math.pow(z, 2) - 810621);
		return func;
	}
	
	public void solve(){
		float u3 = u3_0, xb = x-999;
		float n = 0, del_x = 0.01;

		while(Math.abs(x-xb)>tol){
			n = n+1; 
			xb = x;
  			if(n > nIter) break;
  			double y = f(x);
  			double y_driv=(f(x+del_x) - y)/del_x;
  			x = xb - y/y_driv;
  			System.out.println(" n="+n+" x= "+x+" y = "+y);
		}
		System.out.println("Respuesta final = "+ x);
	}

}
