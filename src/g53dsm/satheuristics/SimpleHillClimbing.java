package g53dsm.satheuristics;

import java.util.Random;

import g53dsm.domains.chesc2014_SAT.SAT;
import g53dsm.satheuristics.SATHeuristic;

/**
  *
  * Simple hill climbing heuristic class.
  *
  * TODO you need to implement the main part of the heuristic
  *
  * @author Kasra Babaei
  */
public class SimpleHillClimbing extends SATHeuristic {

	public SimpleHillClimbing(Random random) {
		
		super(random);
	}

	public void applyHeuristic(SAT problem) {
		
		//TODO implement random bit flip hill climbing
        //Pseudo-code:
        
        //get the number of variables
        int numberOfVariables = problem.getNumberOfVariables();
        
        //randomly go to another variable
        int variable = random.nextInt(numberOfVariables);
        
        //get the current fitness(same shit)
        double currentFitness = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
        
        //mute (aka flip) that bit
        problem.bitFlip(variable, CURRENT_SOLUTION_INDEX);
        
        //get the new fitness(same shit)
        double newFitness = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
        
        //accept if the current solution is better, otherwise reject
        if(newFitness < currentFitness){
        	//ok
        	
        }
        if(newFitness < currentFitness){
        	
        	//revert the change
        	problem.bitFlip(variable, CURRENT_SOLUTION_INDEX);
        }
        
        
	}
	
	public String getHeuristicName() 
	{
		return "Simple Hill Climbing";
	}

}
