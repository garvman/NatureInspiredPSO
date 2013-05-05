
import java.lang.Object;
import java.util.*;

public class Swarm {

	
	public Particle [] swarm;
	public double gBest;
	public double [] gBestLocation;
	
	public static int NUM_DIMENSIONS;
	public static int NUM_PARTICLES;
	public static int NUM_ITERATIONS;
	public static int FUNCTION_NUMBER;
	public static int NT_NUMBER;
	public static int NIS_NUMBER;
	public static boolean PARTICLE_INCLUDED;
	
	
	public static void main(String[] args){
	
		checkArgs(args);
	
		Swarm swarm = new Swarm ();
		swarm.initSwarm();
		swarm.runSwarm();


	}
	

	//swarm constructor
	public Swarm(){
	
		gBest = Double.MAX_VALUE; 
		swarm = new Particle [NUM_PARTICLES];
		gBestLocation = new double [NUM_DIMENSIONS];
	
	}

	//for the NUM_PARTICLES create a new particle object
	//set the neighborhood according to the neighborhood parameters
	public void initSwarm(){
		
		
		for(int i = 0; i < NUM_PARTICLES; i++){
			Particle p = new Particle(NUM_DIMENSIONS, i);
			if(NT_NUMBER =! 0)
				p.setNeighborhood(NT_NUMBER, NUM_PARTICLES, PARTICLE_INCLUDED);
			swarm[i] = p; 
			
		
		}
	
	}
	
	public void runSwarm(){
		
		for(int j = 0; j < NUM_ITERATIONS; j++){
			for(int i = 0; i < NUM_PARTICLES; i++){
				Particle p = swarm[i];
				//p.updateVelocity(NIS_NUMBER, PARTICLE_INCLUDED);
				p.updatePosition();
				p.valueForPosition(FUNCTION_NUMBER);
				
				
					
			}
			System.out.println();
		}
	
	
	}
	
	private void updateBests(Particle p){
	
		if(p.pPosVal < p.pBest){
			p.pBest = p.pPosVal;
			p.pBestLocation = p.position;
			
			if(PARTICLE_INCLUDED){
				if(p.pPosVal < p.nBest){
			
					p.nBest = p.pPosVal;
					p.nBestLocation = p.position;
			
				}
			}
			//if gBest NT is the whole swarm
			if(NT_NUMBER == 0){
				for(int k = 0; i < swarm.length; i++){
					if(k != p.particleID){			
						Particle neighbor = swarm[k];
						if(neighbor.pBest < p.nBest){
							p.nBest = neighbor.pBest;
							p.nBestLocation = neighbor.pBestLocation;
				
						}		
					}					
				}
			
			
			}
			else{//check neighborhood for best
				for(int k = 0; i < p.neighborhood.length; i++){
					int l = p.neighborhood[k];
				
					Particle neighbor = swarm[l];
					if(neighbor.pBest < p.nBest){
						p.nBest = neighbor.pBest;
						p.nBestLocation = neighbor.pBestLocation;
				
					}					
				}
			
				
			}	
			//update global best if there is a new one
			if(p.pPosVal < gBest){
				gBest = p.pPosVal;
				gBestLocation = p.position;
		
			}	
		}	
	
		
	}
	
	private static void checkArgs(String [] args){
		System.out.println();
		System.out.println();
		
		if(args.length != 7){
			System.out.println("" + args.length);
			printCorrectParamImplementation();
			System.exit(1);
	
		}
		else{
		
			FUNCTION_NUMBER = Integer.parseInt(args[0]);
			NUM_DIMENSIONS = Integer.parseInt(args[1]); 
			NUM_PARTICLES = Integer.parseInt(args[2]);
			NUM_ITERATIONS = Integer.parseInt(args[3]);
			NT_NUMBER = Integer.parseInt(args[4]);
			NIS_NUMBER = Integer.parseInt(args[5]);
			int pInc = Integer.parseInt(args[6]);
			
			if(pInc == 1){
				PARTICLE_INCLUDED = true;
			}
			else{
				PARTICLE_INCLUDED = false;
			}
			
			int i = 0;
			if((FUNCTION_NUMBER > 4)||(FUNCTION_NUMBER < 0)){
				System.out.println("Function Number out of bounds");
				i++;
			}
			if(NUM_DIMENSIONS <= 0){
				System.out.println("Number of Dimensions must be greater than 0");
				i++;
			}
			if(NUM_PARTICLES <= 0){
				System.out.println("Number of Particles must be greater than 0");
				i++;
			}
			if(NUM_ITERATIONS <= 0){
				System.out.println("Number of Iterations must be greater than 0");
				i++;
			}
			if((NT_NUMBER > 3)||(NT_NUMBER < 0)){
				System.out.println("Neighborhood Topology number is out of bounds");
				i++;
			}
			if((NIS_NUMBER > 3)||(NIS_NUMBER < 0)){
				System.out.println("Neighborhood Influence number is out of bounds");
				i++;
			}
			if((pInc > 1)||(pInc < 0)){
				System.out.println("Particle Included number must be either 1 or 0");
				i++;
			}
			
			if(i>0){
				System.out.println();
				System.out.println();
				printCorrectParamImplementation();
				System.exit(1);
			}
		
		
		}
	
	
	}
	private static void printCorrectParamImplementation(){
	
		System.out.println("Your input is invalid. It must have the following form:");
		System.out.println();
		System.out.println("java Swarm 'Fn Number' 'N Dimensions' 'N Particles' 'N Iterations'");
		System.out.println("'Neighborhood Topology Num' 'Neighborhood Influence Num' 'Particle Included'");
		System.out.println();
		System.out.println("Where you should input the following numbers for desired parameters:");
		System.out.println();
		System.out.println("Function Number:");
		System.out.println("SPHERE_FUNCTION = 0");
		System.out.println("ROSENBROCK_FUNCTION = 1");
		System.out.println("GRIEWANK_FUNCTION = 2");
		System.out.println("ACKLEY_FUNCTION = 3");
		System.out.println("RASTRIGIN_FUNCTION = 4");
		System.out.println();
		System.out.println("N Dimensions: desired number of dimensions for your run");
		System.out.println("N Particles:  desired number of particles for your run");
		System.out.println("N Iterations: desired number of iterations for your run");
		System.out.println();
		System.out.println("Neighborhood Topology Num:");
		System.out.println("G_BEST = 0");
		System.out.println("L_BEST = 1");
		System.out.println("VON_NEUMANN = 2");
		System.out.println("RANDOM_NEIGHBORHOOD = 3");
		System.out.println();
		System.out.println("Neighborhood Influence Num:");
		System.out.println("FIPS = 0");
		System.out.println("STD = 1");
		System.out.println();
		System.out.println("Particle Included:");
		System.out.println("FALSE = 0");
		System.out.println("TRUE = 1");
		System.out.println();
		System.out.println("Please re-enter your desired parameters");
	
	}



}