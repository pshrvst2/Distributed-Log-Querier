import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


public class UnitTestCases {
	// Pattern
	private static final String FREQ_PATTERN = "Mostly clear, with a low around 51.";
	private static final String SOMEWHAT_FREQ_PATTERN = "Sunny, with a high near 81.";
	private static final String RARE_PATTERN = "A chance of showers and thunderstorms.";
	// Path
	private static final String PATH= " /home/xchen135/git/YouAreFiredInc/YouAreFiredInc/";
	// File names
	private static final String FILE_NAME_T = "TempLog.txt";
	private static final String FILE_NAME_F = "TempFreqLog.txt";
	private static final String FILE_NAME_SF = "TempSFreqLog.txt";
	private static final String FILE_NAME_R = "TempRareLog.txt";
	// Counts of the pattern in log file
	private int numOfFreq =0 ;
	private int numOfSFreq = 0; 
	private int numOfRare = 0; 
	
	// Method generates the general log with three different patterns.
	public void testLogGenerator()
	{
		try 
		{
			FileWriter fw = new FileWriter(new File(FILE_NAME_T));
			Random rm = new Random();
			for(int i = 0; i<100; i++)
			{
				int ranNum = rm.nextInt(10)+1;
				if(ranNum <= 6)
				{
					fw.write(FREQ_PATTERN+"\n");
					numOfFreq ++;
				}
				else if(ranNum > 6 && ranNum<10)
				{
					fw.write(SOMEWHAT_FREQ_PATTERN+"\n");
					numOfSFreq++;
				}
				else
				{
					fw.write(RARE_PATTERN+"\n");
					numOfRare++;
				}			
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Method generates subfile for each pattern 
	public void fileGenerator(String FileName,String pattern, int counts)
	{
		try {
			FileWriter fwF = new FileWriter(new File(FileName));
			for(int i=0; i<counts; i++)
			{
				fwF.write(pattern+"\n");
			}
			fwF.close();		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Method creates three subfiles
	public void patternLogGenerator(){
		fileGenerator(FILE_NAME_F, FREQ_PATTERN,numOfFreq);
		fileGenerator(FILE_NAME_SF, SOMEWHAT_FREQ_PATTERN,numOfSFreq);
		fileGenerator(FILE_NAME_R, RARE_PATTERN,numOfRare);
	}

	// Method compare two buffers 
	public Boolean BufferComparer(BufferedReader brCommand,BufferedReader brFile, String fileName) throws IOException
	{
		BufferedReader brC = brCommand;
		BufferedReader brF = brFile;
		String lnTemp = null;
		String ln1 = null; 
		String ln2 =null;
		// add 1 for taking out the ":" sign
		int fileNameLength = fileName.length()+1;
		int index;
		
		while((lnTemp = brCommand.readLine())!=null && (ln2 = brF.readLine())!=null)
		{
			index = lnTemp.indexOf(fileName);
			ln1 = lnTemp.substring(index+fileNameLength);
			if(!ln1.equals(ln2))
				return false;
		}
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		
		
		int testNum = 0;
		int passNum = 0;
		
		BufferedReader BufferedR = null;
		BufferedReader BufferedFile = null; 
		String message = null;
		
		UnitTestCases tc = new UnitTestCases();
		tc.testLogGenerator();
		tc.patternLogGenerator();
		
		
		System.out.println("The Unit Testing -- #1 grep command test: ");
		System.out.println("");
		
		// Check the frequent pattern
		System.out.println("1. Start to check the frequnt pattern : "+ FREQ_PATTERN);
		testNum++;
		BufferedR = ServerInstance.grepCommandResult(FREQ_PATTERN, FILE_NAME_T,PATH);
		File f1 = new File(FILE_NAME_F);
		BufferedFile = new BufferedReader(new FileReader(f1));		
		if(tc.BufferComparer(BufferedR, BufferedFile,FILE_NAME_T))
		{
			System.out.println("The frequent pattern matched!");
			passNum++;
		}
		else
			System.out.println("Fail to match the frequent pattern!");		
		System.out.println("---------------------------------------------");
		
		// Check the some what frequent pattern
		System.out.println("2. Start to check the some what frequnt pattern : "+ SOMEWHAT_FREQ_PATTERN);
		testNum++;
		BufferedR = ServerInstance.grepCommandResult(SOMEWHAT_FREQ_PATTERN, FILE_NAME_T,PATH);
		File f2 = new File(FILE_NAME_SF);
		BufferedFile = new BufferedReader(new FileReader(f2));		
		if(tc.BufferComparer(BufferedR, BufferedFile,FILE_NAME_T))
		{
			System.out.println("The some what frequent pattern matched!");
			passNum++;
		}
		else
			System.out.println("Fail to match the some what frequent pattern!");
		System.out.println("---------------------------------------------");

		// Check the rare pattern
		System.out.println("3. Start to check the rare pattern : "+ RARE_PATTERN);
		testNum++;
		BufferedR = ServerInstance.grepCommandResult(RARE_PATTERN, FILE_NAME_T,PATH);
		File f3 = new File(FILE_NAME_R);
		BufferedFile = new BufferedReader(new FileReader(f3));		
		if(tc.BufferComparer(BufferedR, BufferedFile,FILE_NAME_T))
		{
			System.out.println("The rare pattern matched!");
			passNum++;
		}
		else
			System.out.println("Fail to match the rare pattern!");
		System.out.println("---------------------------------------------");

		System.out.println("We have "+testNum+" tests, passed "+passNum+".");
		if(testNum == passNum)
		{
			System.out.println("Unit test successed!");
		}
	}

}
