


import java.lang.Object;
import java.util.Random;
public class PSO {

	//global ints to control parameters
	public static int NUM_DIMENSIONS;
	public static int NUM_ITERATIONS;
	public static int NUM_PARTICLES;

	//indicates which function/ topology/ influence structure respectively
	public static int FUNCTION_NUMBER;
	public static int NT_NUMBER;
	public static int NIS_NUMBER;
	

	public static boolean PARTICLE_INCLUDED = false;

	//take as an input the parameters for the PSO and set the ints 
	//above to the values inputed
	//run will have the PSO run for the specified number of iterations
	public static void main(String[] args){
		Swarm swarm = new Swarm (NUM_PARTICLES, NUM_DIMENSIONS);
		swarm.initSwarm(NUM_PARTICLES, NUM_DIMENSIONS, FUNCTION_NUMBER, NT_NUMBER,
						NIS_NUMBER, PARTICLE_INCLUDED);
		swarm.runSwarm(NUM_ITERATIONS, NUM_PARTICLES);


	}




}