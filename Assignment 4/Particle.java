
import java.lang.Object;
import java.util.Random;
//import java.lang.Math;

public class Particle {

	//assigned values for each of the functions this PSO will test on
	//therefore a user can just type the corresponding number instead of the 
	//name of the function
	public final int SPHERE_FUNCTION = 0;
	public final int ROSENBROCK_FUNCTION = 1;
	public final int GRIEWANK_FUNCTION = 2;
	public final int ACKLEY_FUNCTION = 3;
	public final int RASTRIGIN_FUNCTION = 4;
	
	//constant integer values for the neighborhood topologies
	public final int G_BEST = 0;
	public final int L_BEST = 1; //(AKA Ring)
	public final int VON_NEUMANN = 2;
	public final int RANDOM_NEIGHBORHOOD = 3;
	
	//constant integer values for the neighborhood influence schemes 
	public final int FIPS = 0;
	public final int STD = 1;
	
	public int NUM_DIMENSIONS;
	public int FUNCTION_NUMBER;
	public int NT_NUMBER;
	public int NIS_NUMBER;
	public boolean PARTICLE_INCLUDED;
	
	public int particleID;
	public double [] velocity;
	public double pPosValue;
	public double [] position;
	public double pBest;
	public double [] pBestLocation;
	public double nBest;
	public double [] nBestLocation;
	
	
	//neighborhood for this particle and a vector containing index of other 
	//particle neighborhoods this particle belongs to
	//the size of the neighborhood will depend on which neighborhood is implemented
	public Particle [] neighborhood; 
	
	private Random rand = new Random();

	
	//int neighborhoodInfluence;

	//Particle Constructor
	//velocity array to keep track of velocity
	//int pBest to keep track particles best
	public Particle(int nDim, int fnN ,int nT, int nI,int id, boolean pInc){
	
		NUM_DIMENSIONS = nDim;
		FUNCTION_NUMBER = fnN;
		NT_NUMBER = nT;
		NIS_NUMBER = nI;
		PARTICLE_INCLUDED = pInc;
		
		particleID = id;
				
		velocity = new double [NUM_DIMENSIONS];
		//position in N dimensional space and randomize it
		//pPosValue = double.MAX_VALUE;
		position = new double [NUM_DIMENSIONS];
		for(int i =0; i< NUM_DIMENSIONS; i++){
		
			position[i] = rand.nextDouble();
			
		}
		
		
		///pBest = double.MAX_VALUE; 
		pBestLocation = new double [NUM_DIMENSIONS];
		
		//bests for the neighborhood that is "centered" around this particle
		//only applies to certain neighborhood influences 
		//nBest = double.MAX_VALUE; 
		nBestLocation = new double [NUM_DIMENSIONS];
	
	}
	
	//depending on the neighborhood set the neighborhood for 
	//a given particle
	public void setNeighborhood(){
		
		if(NT_NUMBER == G_BEST){
			
		
		}
		else if(NT_NUMBER == L_BEST){
		
		}
		else if(NT_NUMBER == VON_NEUMANN){
		
		
		}
		else if (
	
	
	}
	
	//depending on the global and local best values and neighborhood
	public void updateVelocity(){
		
		//update particle velocity depending on the influence strategy and the 
		//whether the particle is included or not
		
		//FIPS particle included
		if (NIS_NUMBER == 0 && PARTICLE_INCLUDED){
		
		}
		//FIPS particle not included
		else if (NIS_NUMBER == 0 &! PARTICLE_INCLUDED){
		
		
		}
		//STD particle included
		else if (NIS_NUMBER == 1 && PARTICLE_INCLUDED){
		
		
		}
		//STD particle not included
		else if (NIS_NUMBER == 1 &! PARTICLE_INCLUDED){
		
		
		}
	
	
	}
	
	//update bests according to neighborhood scheme
	//if pBest is better than neighborhood best then set as nBest (if applicable)
	public void updateBests(){
	
	
	
	}
	
	//update the position of the particle
	public void updatePosition(){
		
		for(int i = 0;i < NUM_DIMENSIONS; i++){
		
			position[i] = position[i] + velocity[i];
		
		}
		
	
	}
	
	//calculate value for position
	public void valueForPosition(){
	
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
		
	
	}
	
	private double evalSphere (int index){
		if(index == (NUM_DIMENSIONS-1))
			return position[index]*position[index];
			
		return (position[index]*position[index] + evalSphere(index++));
	
	}
	
	private double evalRosenbrock (int i){
		if(i == (NUM_DIMENSIONS-2))
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
		if(i == (NUM_DIMENSIONS-1))
			return position[i]*position[i] - 10.0*Math.cos(2.0*Math.PI*position[i]) + 10.0;
			
		return ((position[i]*position[i] - 10.0*Math.cos(2.0*Math.PI*position[i]) + 10.0) + 
				evalRastrigin(i++));
		
	
	}
	private double sumSquares(int index){
		if(index == (NUM_DIMENSIONS -1))
			return position[index]*position[index];
			
		return (position[index]*position[index] + sumSquares(index + 1));
	
	
	}
	
	private double productCos (int index){
	
		if(index == (NUM_DIMENSIONS -1))
			return Math.cos(position[index]/Math.sqrt(index));
			
		return Math.cos(position[index]/Math.sqrt(index))*productCos(index++);
	
	}
	
	private double sumCos (int index){
		if(index == (NUM_DIMENSIONS -1))
			return Math.cos(2.0*Math.PI*position[index]);
	
		return Math.cos(2.0*Math.PI*position[index]) + sumCos(index++);
	}
	
	


}