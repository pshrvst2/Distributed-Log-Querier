/**
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * @author pshrvst2
 *
 */
public class ClientInstance extends Thread {

	private static Logger log = Logger.getLogger(ClientInstance.class);
	private static BufferedReader userReader = null;
	private static BufferedReader serverReader = null;
	private final String serverIp;
	private final String userCommand;
	private Socket socket;

	public ClientInstance(String serverAddress, String userCmd) {
		this.serverIp = serverAddress;
		// this.socket = new Socket(serverIp, 2000);
		this.userCommand = userCmd;
	}

	public void run() {
		StringBuffer serverMessage = new StringBuffer();

		try {
			socket = new Socket(serverIp, 2000);

			log.info("Socket established with " + serverIp);

			// userReader = new BufferedReader(new
			// InputStreamReader(System.in));
			serverReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			PrintWriter pw = null;
			pw = new PrintWriter(socket.getOutputStream(), true);

			pw.println(userCommand);
			log.info("message flushed to server");

			String returnStr = "";

			while ((returnStr = serverReader.readLine()) != null) {
				log.info(returnStr);
				serverMessage.append(returnStr);
				serverMessage.append("\n");
				OutputClass.appendOutput(returnStr);
			}
			
			//OutputClass.setOutputofthecommand(serverMessage);

			//log.info(serverMessage.toString());
			//System.out.println(serverMessage.toString());

			// userReader.close();
			serverReader.close();
			pw.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			/*
			 * try { userReader.close(); serverReader.close(); socket.close();
			 * log
			 * .info("All connections closed. Exiting from the program. Bye!");
			 * } catch (IOException e) { // TODO Auto-generated catch block
			 * log.error(e); e.printStackTrace(); }
			 */
		}

	}

}
