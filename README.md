
CS425 – MP1 Report
Group 31 members:
1.	Xiaoming Chen (xchen135)			2.      Piyush Shrivastava (pshrvst2)
Design
The idea here is to query on all the seven machines and fetch the results to the user as early as possible. To make this possible, we have made use of Threading. Also, the server should be able to process the request made by different clients at the same time (in other words, parallel processing of the client request). Therefore, the server too creates thread for each client request. 
Initially, the server program is running on all the machines. The user open the client program from any of the seven available VMs. 
Logic at the server side:
1.	Each server open a server socket at a specified port and starts listening for any connection.
2.	As soon as it gets a client request, it creates a new thread which will then be purely dedicated to the client request. 
3.	The server thread now listens to the client command. It then opens a bash in the Java Runtime class and executes the command.
4.	The results in sent back to the client line by and line and once all the lines are sent, we close the connection (client one, not the server) and this ends the thread. All this time, the server is still active and listening for new client request which can be handled by creating a new server thread.
Logic used at the client side:
1.	Client programs validates whether the user has input correct command “grep” or not. There are other preliminary validations done on the client input.
2.	Once the user command passes the validations, it starts creating new socket connection in a thread class. This way, we are able to create connection and send the command to all running severs at the same time.
3.	 In the main class, we also keep a count of the threads created so that later the main thread waits for all the child threads to terminate and then it could begin further processing.
4.	The server returns the output of the grep command. The results are read and stored in vm*.txt files.
5.	The main thread waits for all child threads to get terminated. Then it opens each of the vm*.txt and displays the output on the user terminal. We also display the latency to the user. 
6.	When another request is fired, at step two we will delete the pervious result file which were created at step four.
Unit Test
An automated class creates a log file with frequent, infrequent and rare patterns. Then, the program calls the grep module of our actual server class. The results fetched are then compared with the actual result in the program and is displayed to the developer.
Summary
We have made extensive use of socket programming and multi-threading in Java. The unit test which is fully automated is also conducted successfully. Also, we have used log4j for logging all the actions on client and server. The logs are extremely helpful in the minor issues which we faced in the production environment (the sever VMs). The average latency for sever machine is 0.402s.
