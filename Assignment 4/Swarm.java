
import java.lang.Object;
import java.util.Random;

public class Swarm {

	Particle [] swarm;
	
	public int NUM_DIMENSIONS;
	public int NUM_PARTICLES;
	public int NUM_ITERATIONS;
	public int FUNCTION_NUMBER;
	public int NT_NUMBER;
	public int NIS_NUMBER;
	public boolean PARTICLE_INCLUDED;
	
	
	public static void main(String[] args){
		Swarm swarm = new Swarm ();
		swarm.initSwarm();
		swarm.runSwarm();


	}
	

	//swarm constructor
	public Swarm(){
	
		double gBest; 
		swarm = new Particle [NUM_PARTICLES];
		double [] gBestLocation = new double [NUM_DIMENSIONS];
	
	}

	//for the NUM_PARTICLES create a new particle object
	//set the neighborhood according to the neighborhood parameters
	public void initSwarm(){
		
		
		for(int i = 0; i < NUM_PARTICLES; i++){
			Particle p = new Particle(NUM_DIMENSIONS, i);
			p.setNeighborhood(NT_NUMBER, NUM_PARTICLES, PARTICLE_INCLUDED);
			swarm[i] = p; 
			
		
		}
	
	}
	
	public void runSwarm(){
		
		for(int j = 0; j < NUM_ITERATIONS; j++){
			for(int i = 0; i < NUM_PARTICLES; i++){
				Particle p = swarm[i];
				p.updatePosition();
				p.valueForPosition(FUNCTION_NUMBER);
				p.updateBests();
				p.updateVelocity(NIS_NUMBER, PARTICLE_INCLUDED);		
		
			}
		}
	
	
	}



}