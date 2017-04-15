package cinematica;

public class Cinematica {
	
	float x = 0, y = 0, z = 0;
	float theta1 = 0, theta2 = 0, theta3 = 0;
	float[] theta = new float[3];
	Double d;
	
	float[] raices = new float[4];
	
	public Cinematica(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void solve(){		
		int i = 0;
		float m, u3, u3_0 = 0, inicio = 0, h = 0.001f;
		u3 = inicio + h;
		while(Math.abs(f(u3)) > 0.0001 && u3_0 != u3){
			m = (f(u3) - f(u3_0))/(u3 - u3_0);
			u3_0 = u3;
			u3 = u3 - f(u3)/m;
			if(i >= 100) break;
			i++;
		}
		raices[0] = u3;
//		System.out.println("u3 " + u3);
		
		u3_0 = raices[0] + h;
		inicio = raices[0] + h;
		u3 = inicio + h;
		i = 0;
		while(Math.abs(f2(u3)) > 0.0001 && u3_0 != u3){			
			m = (f2(u3) - f2(u3_0))/(u3 - u3_0);
			u3_0 = u3;
			u3 = u3 - f2(u3)/m;
			if(i >= 100) break;
			i++;
		}
		raices[1] = u3;
		
		u3_0 = raices[0] + h;
		inicio = raices[0] + h;
		u3 = inicio + h;
		i = 0;
		while(Math.abs(f3(u3)) > 0.0001 && u3_0 != u3){
			m = (f3(u3) - f3(u3_0))/(u3 - u3_0);
			u3_0 = u3;
			u3 = u3 - f3(u3)/m;
			if(i >= 100) break;
			i++;
		}
		raices[2] = u3;
		
		u3_0 = raices[0] + h;
		inicio = raices[0] + h;
		u3 = inicio + h;
		i = 0;
		while(Math.abs(f4(u3)) > 0.0001 && u3_0 != u3){
			m = (f4(u3) - f4(u3_0))/(u3 - u3_0);
			u3_0 = u3;
			u3 = u3 - f4(u3)/m;
			if(i >= 100) break;
			i++;
		}
		raices[3] = u3;
		
		System.out.println(raices[0] + " " + raices[1] + " " + raices[2] + " " + raices[3]);
		
		u3 = raices[0];
		for(int ind = 0; ind < 4; ind++){
			if(raices[ind] < u3) u3 = raices[ind];
		}

		d = new Double(u3);
		if(d.isNaN()) System.out.println("No encontro solucion real");
		else{
			theta3 = (float)(2*Math.atan(u3));//;(float) (2*Math.atan(u3));
			solveTheta2();
		}
		theta1 = (float)Math.atan2(y, x);
	}
	
	public float f(float u3){		
//		float func = (float) Math.pow(u3, 2) + 1;
		
		float func = (float) (Math.pow((Math.pow(((640*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) + (310*u3)/(Math.pow(u3, 2) + 1)), 2) + 
				Math.pow(((1280*u3)/(Math.pow(u3, 2) + 1) - (155*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) + 614), 2) - 
				Math.pow(x, 2) - Math.pow(y, 2) - Math.pow(z, 2) + 22500), 2)/90000 + (190340*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) - 
				(1571840*u3)/(Math.pow(u3, 2) + 1) + Math.pow(z, 2) - 810621);
		return func;
	}
	
	public float f2(float u3){		
//		float func = (float) Math.pow(u3, 2) + 1;
		
		float func = (float) ((Math.pow((Math.pow(((640*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) + (310*u3)/(Math.pow(u3, 2) + 1)), 2) + 
				Math.pow(((1280*u3)/(Math.pow(u3, 2) + 1) - (155*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) + 614), 2) - 
				Math.pow(x, 2) - Math.pow(y, 2) - Math.pow(z, 2) + 22500), 2)/90000 + (190340*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) - 
				(1571840*u3)/(Math.pow(u3, 2) + 1) + Math.pow(z, 2) - 810621)) / (u3 - raices[0]);
		return func;
	}
	
	public float f3(float u3){		
//		float func = (float) Math.pow(u3, 2) + 1;
		
		float func = (float) ((Math.pow((Math.pow(((640*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) + (310*u3)/(Math.pow(u3, 2) + 1)), 2) + 
				Math.pow(((1280*u3)/(Math.pow(u3, 2) + 1) - (155*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) + 614), 2) - 
				Math.pow(x, 2) - Math.pow(y, 2) - Math.pow(z, 2) + 22500), 2)/90000 + (190340*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) - 
				(1571840*u3)/(Math.pow(u3, 2) + 1) + Math.pow(z, 2) - 810621)) / ((u3 - raices[0]) * (u3 - raices[1]));
		return func;
	}
	
	public float f4(float u3){		
//		float func = (float) Math.pow(u3, 2) + 1;
		
		float func = (float) ((Math.pow((Math.pow(((640*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) + (310*u3)/(Math.pow(u3, 2) + 1)), 2) + 
				Math.pow(((1280*u3)/(Math.pow(u3, 2) + 1) - (155*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) + 614), 2) - 
				Math.pow(x, 2) - Math.pow(y, 2) - Math.pow(z, 2) + 22500), 2)/90000 + (190340*(Math.pow(u3, 2) - 1))/(Math.pow(u3, 2) + 1) - 
				(1571840*u3)/(Math.pow(u3, 2) + 1) + Math.pow(z, 2) - 810621)) / ((u3 - raices[0]) * (u3 - raices[1]) * (u3 - raices[2]));
		return func;
	}
	
	public void solveTheta2(){		
		float a1 = 150, a2 = 614, a3 = 155, d4 = -640;

		float alpha1 = - (float)Math.PI / 2, alpha2 = (float)Math.PI, alpha3 = -(float)Math.PI / 2, d2 = 0, d3 = 0;

		float s_alpha1 = (float)Math.sin(alpha1), c_alpha1 = (float)Math.cos(alpha1), s_alpha2 = (float)Math.sin(alpha2), 
				c_alpha2 = (float)Math.cos(alpha2), s_alpha3 = (float)Math.sin(alpha3), c_alpha3 = (float)Math.cos(alpha3);

		float s3 = (float)Math.sin(theta3), c3 = (float)Math.cos(theta3);


		float f1 = a3 * c3 + d4 * s_alpha3 * s3 + a2;
		float f2 = a3 * c_alpha2 * s3 - d4 * s_alpha3 * c_alpha2 * c3 - d4 * s_alpha2 * c_alpha3 - d3 * s_alpha2;
		float f3 = a3 * s_alpha2 * s3 - d4 * s_alpha3 *s_alpha2 * c3 + d4 * c_alpha2 * c_alpha3 + d3 * c_alpha2;
		
		float k1 = f1;
		float k2 = -f2;
		float k3 = (float)(Math.pow(f1, 2) + Math.pow(f2, 2) + Math.pow(f3, 2) + Math.pow(a1, 2) + Math.pow(d2, 2) + 2 * d2 * f3);
		float k4 = f3 * c_alpha1 + d2 * c_alpha1;
		
		float r = (float)(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		
		float s2 = (float)(0.5*(-s_alpha1*k2*k3-2*a1*k1*k4+2*a1*k1*z+s_alpha1*k2*r)/(a1*s_alpha1*(Math.pow(k1, 2)+ Math.pow(k2, 2))));
		
		float c2 = (float) (0.5*(2*k2*a1*k4-2*k2*a1*z-k3*s_alpha1*k1+r*s_alpha1*k1)/a1/s_alpha1/(Math.pow(k1, 2)+Math.pow(k2, 2)));	
		
		theta2 = (float)Math.atan2(s2, c2);
	}
	
	public float[] getAng(){
		if(!d.isNaN() && !d.isInfinite()){
			theta[0] = (float)(theta1 * 180 / Math.PI);//Math.round(theta1 * 180 / Math.PI);
			theta[1] = (float)(90 + theta2 * 180 / Math.PI);//90 + Math.round(theta2 * 180 / Math.PI);
			theta[2] = (float)(theta3 * 180 / Math.PI);//Math.round(theta3 * 180 / Math.PI);			
		}
		return theta;
	}
	
	
	public void changeX(float x){
		this.x = x;
	}
	
	public void changeY(float y){
		this.y = y;
	}
	
	public void changeZ(float z){
		this.z = z;
	}

}
