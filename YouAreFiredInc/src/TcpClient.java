/**
 * 
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 * @author pshrvst2
 *
 */
public class TcpClient {

	/**
	 * @param args
	 */

	private static Logger log = Logger.getLogger(TcpClient.class);
	public static StringBuffer outputofthecommand = new StringBuffer();
	public static VmIpAddresses vm = null;

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader userReader = null;

		try 
		{
			boolean loggingFlag = initializeLogging();
			if (loggingFlag) {
				System.out.println("Logging is initialized");
				log.info("Logging Initialized");
			} else {
				System.out.println("Logging coudn't be initialized");
			}

			System.out.println("Enter your command in the form: grep <> <> <>");
			System.out.println();
			userReader = new BufferedReader(new InputStreamReader(System.in));
			String userCommand = userReader.readLine();


			if (!userCommand.substring(0, 4).equalsIgnoreCase("grep")) 
			{
				System.out.println("Only grep accepted as per YouAreFiredInc project");
				return;
			}

			vm = new VmIpAddresses();
			List<String> vmList = vm.getAddresses();
			List<Thread> clientThreadList = new ArrayList<Thread>();

			for (int i = 0; i < vmList.size(); i++) {
				System.out.println("Connecting to server: " + vmList.get(i));
				Thread clientInstance = new ClientInstance(vmList.get(i),
						userCommand);
				clientInstance.start();
				clientThreadList.add(clientInstance);
			}

			while (!clientThreadList.isEmpty()) {
				for (int i = 0; i < clientThreadList.size(); i++) {
					State state = clientThreadList.get(i).getState();
					if (state == Thread.State.TERMINATED
							|| state == Thread.State.BLOCKED) {
						clientThreadList.remove(clientThreadList.get(i));
					}
				}
			}

			if (clientThreadList.isEmpty()) 
			{
				System.out.println("The complete output!");
				for (int i = 0; i < vmList.size(); i++) 
				{
					String vmName = "";
					HashMap<String, String> map = vm.getMappedAddress();
					if (map.containsKey(vmList.get(i))) {
						vmName = map.get(vmList.get(i));
					}
					
					String fileName = vmName + ".txt";
					FileInputStream resFile = new FileInputStream(fileName);
					if (resFile.available() == 0)
					{
						System.out.println("No result from "+vmName);
					}
					else
					{
						@SuppressWarnings("resource")
						BufferedReader reader = new BufferedReader(new InputStreamReader(resFile));
						String singleLine = "";
						Thread t = Thread.currentThread();
						while((singleLine = reader.readLine())!=null)
						{
							t.sleep(1);
							System.out.println(singleLine);
						}
					}
				}
			}

		} catch (Exception e) {
			log.error(e);
		}

	}

	public static boolean initializeLogging() {
		try {
			PatternLayout layout = new PatternLayout("%-5p %d %m%n");
			RollingFileAppender appender = new RollingFileAppender(layout,
					"CS425_MP1_client.log");

			appender.setLayout(layout);
			appender.setName("LOGFILE");
			appender.setMaxFileSize("64MB");
			appender.activateOptions();

			Logger.getRootLogger().addAppender(appender);

			return true;
		} catch (Exception e) {
			// ;
			return false;
		}
	}

}
