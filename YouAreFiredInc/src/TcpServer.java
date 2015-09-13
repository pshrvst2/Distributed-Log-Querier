/**
 * 
 */

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 * @author pshrvst2
 *
 */
public class TcpServer {

	/**
	 * @param args
	 */
	private static Logger log = Logger.getLogger(TcpServer.class);
	private static int clientNumber = 0;
	private static String serverIp = "127.0.0.1";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		ServerSocket serverSocketListener = null;
		serverIp = Inet4Address.getLocalHost().getHostAddress();
		System.out.println(serverIp);
		// Socket clientSocket = null;
		// logic to map server ip to server name begins
		String machineName = serverIp;
		int port = 0;
		VmIpAddresses vm = new VmIpAddresses();
		HashMap<String, String> map = vm.getMappedAddress();

		if (map.containsKey(serverIp)) {
			machineName = map.get(serverIp);
		}
		port = getPort(machineName);
		System.out.println(machineName);
		// logic to map server ip to server name ends

		boolean loggingFlag = initializeLogging();
		if (loggingFlag)
			System.out.println("Logging is initialized");
		else
			System.out.println("Logging coudn't be initialized");

		serverSocketListener = new ServerSocket(port);

		if (serverSocketListener.equals(null)) {
			System.out.println("Server socket failed to open! terminating");
			log.info("Server socket failed to open! terminating");
			serverSocketListener.close();
			return;
		} else {
			System.out
					.println("Server socket established, listening at port 2000");
			log.info("Server socket established, , listening at port 2000");

		}

		try {
			while (true) {
				new ServerInstance(serverSocketListener.accept(), serverIp,
						++clientNumber).start();
				log.info("Listening client: " + clientNumber);
			}
		} finally {
			serverSocketListener.close();
		}

	}

	public static boolean initializeLogging() {
		try {
			PatternLayout layout = new PatternLayout("%-5p %d %m%n");
			RollingFileAppender appender = new RollingFileAppender(layout,
					"CS425_MP1_server.log");

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

	public static int getPort(String vmName) {

		if (vmName.equalsIgnoreCase("vm1"))
			return 2001;

		else if (vmName.equalsIgnoreCase("vm2"))
			return 2002;

		else if (vmName.equalsIgnoreCase("vm3"))
			return 2003;
		else if (vmName.equalsIgnoreCase("vm4"))
			return 2004;
		else if (vmName.equalsIgnoreCase("vm5"))
			return 2005;
		else if (vmName.equalsIgnoreCase("vm6"))
			return 2006;
		else if (vmName.equalsIgnoreCase("vm7"))
			return 2007;
		else
			return 2000;

	}

}
