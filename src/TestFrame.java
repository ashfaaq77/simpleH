
import java.util.Random;

public abstract class TestFrame extends TestFrameConfig {
	
	protected final long[] SEEDS;

	public TestFrame() {
		
		Random random = new Random(1563413L);
		SEEDS = new long[TOTAL_RUNS];
		
		for(int i = 0; i < TOTAL_RUNS; i++)
		{
			SEEDS[i] = random.nextLong();
		}
		
	}
	
	
	public abstract void runTests();
	
	public static void main(String [] args)
	{
		TestFrame runner;
		
		runner = new Lab_01_Runner();
		runner.runTests();
		System.exit(0);
	}
}
