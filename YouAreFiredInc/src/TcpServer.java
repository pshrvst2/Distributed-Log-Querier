/**
 * 
 */

import java.io.IOException;
import java.net.ServerSocket;
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

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		ServerSocket serverSocketListener = null;
		//Socket clientSocket = null;

		boolean loggingFlag = initializeLogging();
		if (loggingFlag)
			System.out.println("Logging is initialized");
		else
			System.out.println("Logging coudn't be initialized");

		serverSocketListener = new ServerSocket(2000);

		if (serverSocketListener.equals(null)) {
			System.out.println("Server socket failed to open! terminating");
			log.info("Server socket failed to open! terminating");
			serverSocketListener.close();
			return;
		} else {
			System.out.println("Server socket established, listening at port 2000");
			log.info("Server socket established, , listening at port 2000");
		}

		try {
			while (true) {
				new ServerInstance(serverSocketListener.accept(),
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

}
