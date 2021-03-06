
import java.lang.Object;
import java.util.Random;
//import java.lang.Math;

public class Particle {

	
	/*
	
	*/
	
	//assigned values for each of the functions this PSO will test on
	//therefore a user can just type the corresponding number instead of the 
	//name of the function
	public final int SPHERE_FUNCTION = 0;
	public final int ROSENBROCK_FUNCTION = 1;
	public final int GRIEWANK_FUNCTION = 2;
	public final int ACKLEY_FUNCTION = 3;
	public final int RASTRIGIN_FUNCTION = 4;
	
	
	
	int particleID;
	double pPosVal; 
	double pBest;
	double nBest;
	
	public int [] neighborhood; 
	public double [] velocity;	
	public double [] position;	
	public double [] pBestLocation;	
	public double [] nBestLocation;
	
	
	
	
	private Random rand = new Random();
	
	
	//int neighborhoodInfluence;

	//Particle Constructor
	//velocity array to keep track of velocity
	//int pBest to keep track particles best
	public Particle(int NUM_DIMENSIONS, int id){
	
		//neighborhood for this particle 
		//the size of the neighborhood will depend on which neighborhood is implemented
		
		particleID = id;
		int sign;
		velocity = new double [NUM_DIMENSIONS];		
		position = new double [NUM_DIMENSIONS];		
		pBestLocation = new double [NUM_DIMENSIONS];		
		nBestLocation = new double [NUM_DIMENSIONS];	
		pBest = Double.MAX_VALUE;
		nBest = Double.MAX_VALUE;
				
		
		//position in N dimensional space and randomize it
		//pPosValue = double.MAX_VALUE;		
		for(int i =0; i< NUM_DIMENSIONS; i++){
		
			position[i] = rand.nextDouble();
			if(rand.nextDouble()<0.5)
				sign = -1;
			else 
				sign = 1;
				
			velocity[i] = 2*sign*rand.nextDouble();
			
		}		
			
	}
	
	//update the position of the particle
	public void updatePosition(){
		//System.out.println("inside update pos" );
		//System.out.print("velocity: ");
		for(int i = 0; i < position.length; i++){
		
			position[i] = position[i] + velocity[i];
			
			//System.out.print(velocity[i] + ", ");
		}
		
	
	}
	
	//calculate value for position
	public void valueForPosition(int FUNCTION_NUMBER){
	
		double ret = 0.0;
	
		if(FUNCTION_NUMBER == SPHERE_FUNCTION){
			ret = evalSphere(0);
		}
		else if (FUNCTION_NUMBER == ROSENBROCK_FUNCTION){
			ret = evalRosenbrock(0);		
		}
		else if (FUNCTION_NUMBER == GRIEWANK_FUNCTION){
			ret = evalGriewank();		
		}
		else if (FUNCTION_NUMBER == ACKLEY_FUNCTION){
			ret = evalAckley();		
		}
		else if (FUNCTION_NUMBER == RASTRIGIN_FUNCTION){
			ret = evalRastrigin(0);		
		}
		
		//System.out.println(" Value: " + ret);
		pPosVal = ret;
	}
	
	private double evalSphere (int index){
		if(index == (position.length-1))
			return position[index]*position[index];
			
		index++;	
		//System.out.println(index);	
		return (position[index]*position[index] + evalSphere(index));
	
	}
	
	private double evalRosenbrock (int i){
		if(i == (position.length -2))
			return 100.0*((position[i]*position[i]-position[i+1])*
					(position[i]*position[i]-position[i+1]) + ((position[i]-1)*
					(position[i]-1)));
	
		return (100.0*((position[i]*position[i]-position[i+1])*
					(position[i]*position[i]-position[i+1]) + ((position[i]-1)*
					(position[i]-1)))) + evalRosenbrock(i+1);
	}
	
	private double evalGriewank (){
		double sumSquares = sumSquares(0);
		double productCos = productCos(0);
	
		return sumSquares/4000.0 - productCos + 1.0;
	}
	
	private double evalAckley (){
		double sumSquares = sumSquares(0);
		double sumCos = sumCos(0);
	
		return -20.0*Math.exp(-0.2*Math.sqrt(sumSquares/2.0))-Math.exp(sumCos/2.0)+
				20.0 + Math.E;
	}
	
	private double evalRastrigin(int i){
		if(i == (position.length -1))
			return position[i]*position[i] - 10.0*Math.cos(2.0*Math.PI*position[i]) + 10.0;
			
		return ((position[i]*position[i] - 10.0*Math.cos(2.0*Math.PI*position[i]) + 10.0) + 
				evalRastrigin(i+1));
		
	
	}
	private double sumSquares(int index){
		if(index == (position.length -1))
			return position[index]*position[index];
			
		return (position[index]*position[index] + sumSquares(index + 1));
	
	
	}
	//need to look at Griewank again to see what happens when index is 0, undefined 
	private double productCos (int index){
	
		if(index == (position.length -1))
			return Math.cos(position[index]/Math.sqrt(index));
			
		return Math.cos(position[index]/Math.sqrt(index))*productCos(index+1);
	
	}
	
	private double sumCos (int index){
		if(index == (position.length -1))
			return Math.cos(2.0*Math.PI*position[index]);
	
		return Math.cos(2.0*Math.PI*position[index]) + sumCos(index+1);
	}
	
	


}