/**
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * @author pshrvst2
 *
 */
public class ClientInstance extends Thread {

	private static Logger log = Logger.getLogger(ClientInstance.class);
	private final String serverIp;
	private final String userCommand;

	public ClientInstance(String serverAddress, String userCmd) {
		this.serverIp = serverAddress;
		this.userCommand = userCmd;
	}

	public void run() {
		StringBuffer serverMessage = new StringBuffer();
		PrintWriter pw = null;
		BufferedReader serverReader = null;
		Socket socket;
		PrintWriter resultWriter = null;
		
		try {

			// logic to map server ip to server name begins
			String machineName = serverIp;
			int port = 0;
			VmIpAddresses vm = new VmIpAddresses();
			HashMap<String, String> map = vm.getMappedAddress();

			if (map.containsKey(serverIp)) {
				machineName = map.get(serverIp);
			}
			port = getPort(machineName);
			System.out.println(machineName + ":" + String.valueOf(port));
			// logic to map server ip to server name ends
			
			
			socket = new Socket(serverIp, port);
			log.info("Socket established with " + serverIp);
			serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream(), true);
			pw.println(userCommand);
			log.info("message flushed to server");

			String returnStr = "";
			resultWriter = new PrintWriter(machineName + ".txt", "UTF-8" );
			long threadId = Thread.currentThread().getId();
			log.info(threadId);
			
			while ((returnStr = serverReader.readLine()) != null) {
				log.info(" Thread Id " + threadId + " : " + returnStr);
				serverMessage.append(returnStr);
				serverMessage.append("\n");
				OutputClass.appendOutput(returnStr);
				resultWriter.println(returnStr);
			}
	
			serverReader.close();
			resultWriter.close();
			pw.close();
			socket.close();
		} catch (IOException e) {			
			log.error(e);
			e.printStackTrace();
		} finally {
			
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
