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

	public ClientInstance(String serverAddress, String userCmd)
	{
		this.serverIp = serverAddress;
		//this.socket = new Socket(serverIp, 2000);
		this.userCommand = userCmd;
	}

	public void run() 
	{
		//Socket socket = null;
		String userMessage = "";
		String serverMessage = "";

		try {
			socket = new Socket(serverIp, 2000);

			log.info("Socket established with " +serverIp);

			userReader = new BufferedReader(new InputStreamReader(System.in));
			serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			PrintWriter pw = null;
			pw = new PrintWriter(socket.getOutputStream(), true);
			
			boolean flag = true;
					pw.println(userCommand);
				log.info("message flushed to server");
				// pw.flush();
				
				//while(serverReader)
				serverMessage = serverReader.readLine();
				log.info(serverMessage);
				System.out.println(serverMessage);
			//}

			userReader.close();
			serverReader.close();
			pw.close();
			socket.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			/*try 
			{
				userReader.close();
				serverReader.close();
				socket.close();
				log.info("All connections closed. Exiting from the program. Bye!");
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e);
				e.printStackTrace();
			}*/
		}

		
	}
	
	//method convert InputStream to String
    private static String inputStreamToString(InputStream stream)
    {
            BufferedReader reader = null;
            StringBuilder builder = new StringBuilder();
            String message;
            
            try
            {
                            reader = new BufferedReader(new InputStreamReader(stream));
                            while((message = reader.readLine()) != null)
                            {
                                    builder.append(message+"\n");
                            }
            }
            catch(IOException e)
            {
                            e.printStackTrace();
            }
            finally
            {
                    if (reader != null)
                    {
                            try
                            {
                                    reader.close();
                            }
                            catch(IOException e)
                            {
                                    e.printStackTrace();
                            }
                    }
            }
            return builder.toString();
    }


}
