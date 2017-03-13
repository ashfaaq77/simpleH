import g53dsm.domains.chesc2014_SAT.SAT;
import g53dsm.satheuristics.SATHeuristic;
import g53dsm.statistics.BoxPlot;
import g53dsm.statistics.LineGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;


public class Lab_01_Runner extends TestFrame
{
	private static final int HEURISTIC_TESTS = 1;
	
	public void runTests()
	{
		double[][] data = new double[TOTAL_RUNS][HEURISTIC_TESTS];
		String[] names = new String[HEURISTIC_TESTS];
		
		for(int heuristicTest = 0; heuristicTest < HEURISTIC_TESTS; heuristicTest++) {
			
			double bestRun = Double.MAX_VALUE;
			double worstRun = Double.MIN_VALUE;
			
			ArrayList<Double> bestRunTrace = null;
			ArrayList<Double> worstRunTrace = null;
			
			ArrayList<Double> runScores = new ArrayList<Double>();
			
			for(int run = 0; run < TOTAL_RUNS; run++) {
				
				SAT sat = new SAT(INSTANCE_ID, RUN_TIME, SEEDS[run]);
				ArrayList<Double> fitnessTrace = new ArrayList<Double>();
				
				Random random = new Random(SEEDS[run]);
				SATHeuristic heuristic = TestFrameConfig.getSATHeuristic(heuristicTest, random);
				
				while(!sat.hasTimeExpired()) {
					
					heuristic.applyHeuristic(sat);
					double fitness = sat.getObjectiveFunctionValue(SATHeuristic.CURRENT_SOLUTION_INDEX);
					fitnessTrace.add(fitness);
				}
				
				double currentBestSolution = sat.getBestSolutionValue();
				data[run][heuristicTest] = currentBestSolution;
				runScores.add(currentBestSolution);
				
				if(currentBestSolution < bestRun) {
					
					bestRun = currentBestSolution;
					bestRunTrace = fitnessTrace;
				}
				
				if(currentBestSolution > worstRun) {
					
					worstRun = currentBestSolution;
					worstRunTrace = fitnessTrace;
				}
				
				names[heuristicTest] = heuristic.getHeuristicName();
				
				System.out.println("Heuristic: " + heuristic.getHeuristicName());
				System.out.println("Run ID: " + run);
				System.out.println("Best Solution Value: " + sat.getBestSolutionValue());
				System.out.println("Best Solution: " + sat.getBestSolutionAsString());
				System.out.println();
			}
			
			//create fitness traces
			new LineGraph(names[heuristicTest] + " Best Fitness Trace", INSTANCE_ID).createGraph(bestRunTrace);
			new LineGraph(names[heuristicTest] + " Worst Fitness Trace", INSTANCE_ID).createGraph(worstRunTrace);
			
			//print or save results
			StringBuilder sb = new StringBuilder();
			sb.append(names[heuristicTest] + "," + RUN_TIME + "," + INSTANCE_ID);
			for(double ofv : runScores) {
				sb.append("," + ofv);
			}
			
			saveData("HeuristicResults" + TOTAL_RUNS + "Runs.csv", sb.toString());
			
		}
		
		new BoxPlot(BOXPLOT_TITLE, false).createPlot(data, names);

	}
	
	private void saveData(String filePath, String data) {
		
		Path path = Paths.get("./" + filePath);
		if(!Files.exists(path)) {
			try {
				Files.createFile(path);
				
				//add header
				String header = "Heuristic,Run Time,Instance ID";
				for(int i = 0; i < TOTAL_RUNS; i++) {
					
					header += ("," + i);
				}
				
				Files.write(path, (header + "\r\n" + data).getBytes());
				
			} catch (IOException e) {
				System.err.println("Could not create file at " + path.toAbsolutePath());
				System.err.println("Printing data to screen instead...");
				System.out.println(data);
			}
			
		} else {
			
			try {
				byte[] currentData = Files.readAllBytes(path);
				data = "\r\n" + data;
				byte[] newData = data.getBytes();
				byte[] writeData = new byte[currentData.length + newData.length];
				System.arraycopy(currentData, 0, writeData, 0, currentData.length);
				System.arraycopy(newData, 0, writeData, currentData.length, newData.length);
				Files.write(path, writeData);
				
			} catch (IOException e) {
				System.err.println("Could not create file at " + path.toAbsolutePath());
				System.err.println("Printing data to screen instead...");
				System.out.println(data);
			}
			
		}
		
	}
		
}
