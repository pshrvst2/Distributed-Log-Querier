/**
 * 
 */

/**
 * @author pshrvst2
 *
 */
public class OutputClass {
	
	private static StringBuffer outputofthecommand = new StringBuffer();

	public static StringBuffer getOutputofthecommand() {
		return outputofthecommand;
	}

	public static void setOutputofthecommand(StringBuffer outputofthecommand) {
		OutputClass.outputofthecommand = outputofthecommand;
	}
	
	public static void appendOutput(String str)
	{
		outputofthecommand.append(str);
		outputofthecommand.append("\n");
	}

}
