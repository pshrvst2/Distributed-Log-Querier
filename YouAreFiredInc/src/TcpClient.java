/**
 * 
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Thread.State;
import java.util.ArrayList;
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

	/*
	 * private static BufferedReader userReader = null; private static
	 * BufferedReader serverReader = null;
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 * Socket socket = null; String userMessage = ""; String serverMessage =
		 * "";
		 */
		BufferedReader userReader = null;

		try {
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
			// System.out.println("You entered the command: "+userCommand.substring(0,
			// 4));

			if (!userCommand.substring(0, 4).equalsIgnoreCase("grep")) {
				System.out
						.println("Only grep accepted as per YouAreFiredInc project");
				return;
			}

			VmIpAddresses vm = new VmIpAddresses();
			/*
			 * ListIterator<String> listItr = null; listItr =
			 * vm.getAddresses().listIterator();
			 */

			List<String> vmList = vm.getAddresses();
			List<Thread> clientThreadList = new ArrayList<Thread>();
			/*
			 * while(listItr.hasNext()) {
			 * System.out.println("Connecting to server: " +listItr.next()); new
			 * ClientInstance(listItr.next()).start();
			 * 
			 * }
			 */

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

			if (clientThreadList.isEmpty()) {
				System.out.println("The complete output!");
				System.out.println(OutputClass.getOutputofthecommand()
						.toString());
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
