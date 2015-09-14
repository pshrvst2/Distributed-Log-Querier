/**
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * @author pshrvst2
 *
 */
public class ServerInstance extends Thread {

	private static Logger log = Logger.getLogger(ServerInstance.class);
	private String serverIpAddress = null;
	private Socket clientSocket = null;
	//private final String PATH = " /home/pshrvst2/git/YouAreFiredInc/YouAreFiredInc/vm1.log";
	private final String PATH = " /home/xchen135/git/YouAreFiredInc/YouAreFiredInc/vm2.log";

	public ServerInstance(Socket clientSocket, String ip, int clientNbr) {
		log.info("Connection established with client number = " + clientNbr);
		log.info("Connection established at socket = " + clientSocket);
		
		System.out.println("Connection established with client number = " + clientNbr);
		System.out.println("Connection established at socket = " + clientSocket);
		
		this.clientSocket = clientSocket;
		this.serverIpAddress = ip;
	}

	// Method return the grep command result from the server
	public static BufferedReader grepCommandResult(String Pattern, String fileName, String path) {
		BufferedReader br = null;
		try {
			Runtime rt = Runtime.getRuntime();
			String clientCommand = "grep " + Pattern + path + fileName;
			Process proc = rt
					.exec(new String[] { "bash", "-c", clientCommand });

			br = new BufferedReader(
					new InputStreamReader(proc.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return br;
	}
	
	public void run() {
		
		try {
			String clientCommand = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			BufferedReader processReader = null;
			OutputStreamWriter writer = new OutputStreamWriter(
					clientSocket.getOutputStream());
			PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(),
					true);

			clientCommand = reader.readLine();
			log.info("Client fired -->" + clientCommand);
			System.out.println("Client fired -->" + clientCommand);

			Runtime rt = Runtime.getRuntime();
			clientCommand = clientCommand + PATH;
			Process proc = rt.exec(new String[] { "bash", "-c", clientCommand });

			System.out.println("The complete command is:" + clientCommand);

			// logic to map server ip to server name begins
			String machineName = serverIpAddress;
			VmIpAddresses vm = new VmIpAddresses();
			HashMap<String, String> map = vm.getMappedAddress();

			if (map.containsKey(serverIpAddress)) {
				machineName = map.get(serverIpAddress);
			}
			System.out.println(machineName);
			// logic to map server ip to server name ends

			String message = "";

			processReader = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));

			while ((message = processReader.readLine()) != null) {
				pw.println(machineName + " : " + message);
				System.out.println(machineName + " : " + message);
			}
			pw.close();
			reader.close();
			writer.close();
			clientSocket.close();
			log.info("All connections closed, bye");
			System.out.println("All connections closed, bye");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
