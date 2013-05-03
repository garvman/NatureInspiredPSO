
import java.lang.Object;
import java.util.Random;

public class Swarm {

	Particle [] swarm;

	//swarm constructor
	public Swarm(int NUM_PARTICLES,int NUM_DIMENSIONS){
	
		double gBest; 
		swarm = new Particle [NUM_PARTICLES];
		double [] gBestLocation = new double [NUM_DIMENSIONS];
	
	}

	//for the NUM_PARTICLES create a new particle object
	//set the neighborhood according to the neighborhood parameters
	public void initSwarm(int NUM_PARTICLES,int NUM_DIMENSIONS,int FUNCTION_NUMBER
						,int NT_NUMBER,int NIS_NUMBER, boolean PARTICLE_INCLUDED){
		
		
		for(int i = 0; i < NUM_PARTICLES; i++){
			Particle p = new Particle(NUM_DIMENSIONS,FUNCTION_NUMBER
						,NT_NUMBER,NIS_NUMBER,i, PARTICLE_INCLUDED);
			p.setNeighborhood();
			swarm[i] = p; 
			
		
		}
	
	}
	
	public void runSwarm(int NUM_ITERATIONS, int NUM_PARTICLES){
		
		for(int j = 0; j < NUM_ITERATIONS; j++){
			for(int i = 0; i < NUM_PARTICLES; i++){
				Particle p = swarm[i];
				p.updatePosition();
				p.valueForPosition();
				p.updateBests();
				p.updateVelocity();		
		
			}
		}
	
	
	}



}