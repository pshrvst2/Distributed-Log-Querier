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

import org.apache.log4j.Logger;



/**
 * @author pshrvst2
 *
 */
public class ServerInstance extends Thread {
	
	private static Logger log = Logger.getLogger(ServerInstance.class);
	private Socket clientSocket = null;
	private int clientNbr = 0;
	
	public ServerInstance(Socket clientSocket, int clientNbr) {
		//super();
		log.info("Connection established with client number = " +clientNbr);
		log.info("Connection established at socket = " +clientSocket);
		System.out.println("Connection established with client number = " +clientNbr);
		System.out.println("Connection established at socket = " +clientSocket);
		
		this.clientSocket = clientSocket;
		this.clientNbr = clientNbr;
	}
	
	public void run(){
		
		try 
		{
			String clientCommand = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());
			PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
			
			boolean flag = true;
			while (flag)
			{
				clientCommand = reader.readLine();
				log.info("Client fired -->" +clientCommand);
				System.out.println("Client fired -->" +clientCommand);
				if(clientCommand.equalsIgnoreCase("exit"))
					flag = false;
				
				Runtime rt = Runtime.getRuntime();
		        Process proc = rt.exec(clientCommand);
		        String outputOfTheCommand = inputStreamToString(proc.getInputStream());
		        if(!outputOfTheCommand.isEmpty())
		        {
		        	System.out.println(outputOfTheCommand);
		        	pw.println(outputOfTheCommand);
		        	log.info("message flushed back to client");
		        	log.info("Server to client --> " + clientNbr);
					log.info("Server reply --> " + outputOfTheCommand);
		        }
		        // print out the error message if there is any
		        else
		        {
		        	System.out.println("Error Message as below: ");
		        	System.out.println(outputOfTheCommand);          
		        	System.out.println(" ");
		        	pw.println("Error Message as below: ");
		        	pw.println(outputOfTheCommand);
		        	log.info("message flushed back to server");
		        }
		        

			}
			
			reader.close();
			writer.close();
			clientSocket.close();
			
			log.info("All connections closed, bye");
			System.out.println("All connections closed, bye");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
