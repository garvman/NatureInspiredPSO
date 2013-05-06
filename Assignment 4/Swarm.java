
import java.lang.Object;
import java.util.*;

public class Swarm {

	
	public Particle [] swarm;
	public double gBest;
	public double [] gBestLocation;
	
	public static double phi1 = 2.05;
	public static double phi2 = 2.05;
	public double phi = phi1 + phi2;
	public double constrictionFactor = 2.0 / (phi - 2.0 + Math.sqrt(phi*phi - 4.0*phi));
	
	
	public static int NUM_DIMENSIONS;
	public static int NUM_PARTICLES;
	public static int NUM_ITERATIONS;
	public static int FUNCTION_NUMBER;
	public static int NT_NUMBER;
	public static int NIS_NUMBER;
	public static boolean PARTICLE_INCLUDED;
	public static int [][] vonNeumannRef;

	
	//constant integer values for the neighborhood topologies
	public final int G_BEST = 0;
	public final int L_BEST = 1; //(AKA Ring)
	public final int VON_NEUMANN = 2;
	public final int RANDOM_NEIGHBORHOOD = 3;
	
	//constant integer values for the neighborhood influence schemes 
	public final int FIPS = 0;
	public final int STD = 1;
	
	private Random rand = new Random();
	
	
	public static void main(String[] args){
	
		checkArgs(args);
		
		vonNeumannRef = new int [(int)Math.sqrt(NUM_PARTICLES)][(int)Math.sqrt(NUM_PARTICLES)];
		int k = 0;
		for(int i = 0; i < vonNeumannRef.length; i++){
			for(int j = 0; j < vonNeumannRef[i].length; j++){
				vonNeumannRef[i][j] = k;
				k++;			
			}		
		}
		
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
			if(NT_NUMBER != 0)
				setNeighborhood(p);
			swarm[i] = p; 
			
		
		}
	
	}
	
	public void runSwarm(){
		
		for(int j = 0; j < NUM_ITERATIONS; j++){
			for(int i = 0; i < NUM_PARTICLES; i++){
				Particle p = swarm[i];
				updateVelocity(p);
				p.updatePosition();
				p.valueForPosition(FUNCTION_NUMBER);
				updateBests(p);
				
					
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
				for(int k = 0; k < swarm.length; k++){
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
				for(int k = 0; k < p.neighborhood.length; k++){
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
	
	//depending on the neighborhood set the neighborhood for 
	//a given particle
	public void setNeighborhood(Particle p){
		
		//lBest topology
		if(NT_NUMBER == L_BEST){
			p.neighborhood = new int [2];
			
			if(p.particleID == 0){
				p.neighborhood[0] = NUM_PARTICLES-1;
				p.neighborhood[1] = p.particleID+1;				
			}
			else if(p.particleID == NUM_PARTICLES-1){
				p.neighborhood[0] = 0;
				p.neighborhood[1] = p.particleID-1;				
			}
			else {
				p.neighborhood[0] = p.particleID+1;
				p.neighborhood[1] = p.particleID-1;
				
			}
		}
		
		//vonNeumann topology
		else if(NT_NUMBER == VON_NEUMANN){
			p.neighborhood = new int [8];
			int l = p.particleID%vonNeumannRef.length;
			int k = (p.particleID-l)/vonNeumannRef.length;
			
			//the particle is in the first row so need to deal with wrap around
			if(p.particleID < vonNeumannRef.length){
				//first particle has five wrap around elements
				if(p.particleID == 0){
					p.neighborhood[0] = vonNeumannRef[vonNeumannRef.length-1][vonNeumannRef.length-1];
					p.neighborhood[1] = vonNeumannRef[vonNeumannRef.length-1][0];
					p.neighborhood[2] = vonNeumannRef[vonNeumannRef.length-1][1];
					p.neighborhood[3] = vonNeumannRef[0][vonNeumannRef.length-1];
					p.neighborhood[4] = p.particleID +1; 
					p.neighborhood[5] = vonNeumannRef[1][vonNeumannRef.length-1];
					p.neighborhood[6] = vonNeumannRef[1][0];
					p.neighborhood[7] = vonNeumannRef[1][1];
				}
				//last element in the row also has five wrap around elements (special case)
				else if(p.particleID == vonNeumannRef.length-1){
					p.neighborhood[0] = vonNeumannRef[vonNeumannRef.length-1][0];
					p.neighborhood[1] = vonNeumannRef[vonNeumannRef.length-1][vonNeumannRef.length-2];
					p.neighborhood[2] = vonNeumannRef[vonNeumannRef.length-1][vonNeumannRef.length-1];
					p.neighborhood[3] = p.particleID -1;
					p.neighborhood[4] = vonNeumannRef[0][vonNeumannRef.length-1]; 
					p.neighborhood[5] = vonNeumannRef[1][vonNeumannRef.length-2];
					p.neighborhood[6] = vonNeumannRef[1][vonNeumannRef.length-1];
					p.neighborhood[7] = vonNeumannRef[1][0];
				
				}
				else{
					int m = 0;
					for(int i = -1; i < 2; i++){
						for(int j = -1; j < 2; j++){
							if(i==-1){
								p.neighborhood[m] = vonNeumannRef[vonNeumannRef.length-1][p.particleID+j];	
								k++;
							}
							else if(i==0 && j==0){
							
							}
							else{							
								p.neighborhood[m] = vonNeumannRef[k+i][l+j];
								k++;								
							}		
							
						}				
					}
					
				}
			
			}
			//the particle is in the last row so need to deal with wrap around (special case)
			else if(p.particleID >= vonNeumannRef.length*(vonNeumannRef[0].length)){
				if(p.particleID%vonNeumannRef.length == 0){
					p.neighborhood[0] = vonNeumannRef[k-1][vonNeumannRef.length];
					p.neighborhood[1] = vonNeumannRef[k-1][l];
					p.neighborhood[2] = vonNeumannRef[k-1][l+1];
					p.neighborhood[3] = vonNeumannRef[k][vonNeumannRef.length-1];
					p.neighborhood[4] = vonNeumannRef[k][l+1]; 
					p.neighborhood[5] = vonNeumannRef[0][vonNeumannRef.length-1];
					p.neighborhood[6] = vonNeumannRef[0][0];
					p.neighborhood[7] = vonNeumannRef[0][1];
				}
				else if(p.particleID == NUM_PARTICLES-1){
					p.neighborhood[0] = vonNeumannRef[k-1][l-1];
					p.neighborhood[1] = vonNeumannRef[k-1][l];
					p.neighborhood[2] = vonNeumannRef[k-1][0];
					p.neighborhood[3] = p.particleID -1;
					p.neighborhood[4] = vonNeumannRef[k][0]; 
					p.neighborhood[5] = vonNeumannRef[0][l-1];
					p.neighborhood[6] = vonNeumannRef[0][l];
					p.neighborhood[7] = vonNeumannRef[0][0];
				
				}
				else{
					int m = 0;
					for(int i = -1; i < 2; i++){
						for(int j = -1; j < 2; j++){
							if(i==1){
								p.neighborhood[m] = vonNeumannRef[0][l+j];
								k++;
							}
							else if(i==0 && j==0){
							
							}
							else{							
								p.neighborhood[m] = vonNeumannRef[k+i][l+j];	
								k++;
							}		
							
						}				
					}
					
				
				}
			
			
			}
			else if(p.particleID%vonNeumannRef.length == 0){
				int m = 0;
				for(int i = -1; i < 2; i++){
					for(int j = -1; j < 2; j++){
						if(j==-1){
							p.neighborhood[m] = vonNeumannRef[k+i][vonNeumannRef.length];
							k++;
						}
						else if(i==0 && j==0){
						
						}
						else{							
							p.neighborhood[m] = vonNeumannRef[k+i][l+j];	
							k++;
						}		
						
					}				
				}
			
			
			}
			else if(p.particleID%vonNeumannRef.length == vonNeumannRef.length-1){
				
				int m = 0;
				for(int i = -1; i < 2; i++){
					for(int j = -1; j < 2; j++){
						if(j==1){
							p.neighborhood[m] = vonNeumannRef[k+i][0];
							k++;
						}
						else if(i==0 && j==0){
						
						}
						else{							
							p.neighborhood[m] = vonNeumannRef[k+i][l+j];	
							k++;
						}		
						
					}				
				}
				
			
			}
			else{
			
				int m = 0;
				for(int i = -1; i < 2; i++){
					for(int j = -1; j < 2; j++){
						
						if(i==0 && j==0){
						
						}
						else{							
							p.neighborhood[m] = vonNeumannRef[k+i][l+j];	
							k++;
						}	
					}				
				}
			}
		}
		
		//set random neighborhood topology
		//this will be called after every iteration
		else if (NT_NUMBER == RANDOM_NEIGHBORHOOD){
			Vector<Integer> tmp = new Vector<Integer>();
			int j = 0;
			
			for(int i = 0; i < NUM_PARTICLES; i++){
				double val = rand.nextDouble();
				if(val>0.5){
					tmp.add(i);
					j++;
				}
			
			
			}
			
			p.neighborhood = new int[j];
			
			for(int k = 0; k < tmp.size(); k++){ 
				p.neighborhood[k] = tmp.elementAt(k);
			}
			
		
		}
		
	
	
	}
	
	//depending on the global and local best values and neighborhood
	public void updateVelocity(Particle p){
		
		//update particle velocity depending on the influence strategy and the 
		//whether the particle is included or not
		
		//FIPS particle included
		if (NIS_NUMBER == FIPS){
			for(int i = 0; i < NUM_DIMENSIONS; i++){
				p.velocity[i] = constrictionFactor*(phi1*velFIPS(p,i));
			}
		}
		//STD particle included
		else if (NIS_NUMBER == STD && PARTICLE_INCLUDED){
			for(int i = 0; i < NUM_DIMENSIONS; i++){
				p.velocity[i] = constrictionFactor*(p.velocity[i] + phi1*(p.pBestLocation[i]-p.position[i]) +
								phi2*(p.nBestLocation[i]-p.position[i]));
			}
		
		}
		//STD particle not included
		else if (NIS_NUMBER == STD &! PARTICLE_INCLUDED){
			for(int i = 0; i < NUM_DIMENSIONS; i++){
				p.velocity[i] = constrictionFactor*(p.velocity[i] +	phi2*(p.nBestLocation[i]-p.position[i]));
			}
		
		}
	
	
	}
	
	private double velFIPS(Particle p, int dimension){
	
		double pVel = 0; 
		if(PARTICLE_INCLUDED){
			pVel = p.pBestLocation[dimension] - p.position[dimension];
			Particle tmp; 
			
			for(int i = 0; i < p.neighborhood.length; i++){
				tmp = swarm[i];
				pVel += (tmp.pBestLocation[dimension] - tmp.position[dimension]);				
			
			}
		
		}
		else {
			Particle tmp; 
			
			for(int i = 0; i < p.neighborhood.length; i++){
				tmp = swarm[i];
				pVel += (tmp.pBestLocation[dimension] - tmp.position[dimension]);				
			
			}
		
		}
		
		return pVel;
	
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
		
			NT_NUMBER = Integer.parseInt(args[0]);
			int pInc = Integer.parseInt(args[1]);
			NIS_NUMBER = Integer.parseInt(args[2]);
			NUM_PARTICLES = Integer.parseInt(args[3]);
			NUM_ITERATIONS = Integer.parseInt(args[4]);
			FUNCTION_NUMBER = Integer.parseInt(args[5]);
			NUM_DIMENSIONS = Integer.parseInt(args[6]); 			
			
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
		System.out.println("java Swarm 'Neighborhood Topology Num' 'Particle Included' 'Neighborhood Influence Num'");
		System.out.println(" 'N Particles' 'N Iterations' 'Fn Number' 'N Dimensions' ");
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