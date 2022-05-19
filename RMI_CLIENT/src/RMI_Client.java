import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class RMI_Client extends Thread{

    private int NUMBER_OF_REQUESTS =  10;
    private double WRITE_PERCENTAGE = 0.6;
    private int NUMBER_OF_NODES = 7;
    private FileHandler logFileName;
    private FileHandler statFileName;
    private int logFileNum = 0;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    Random randomGenerator = new Random(); // For random number generators

    public void run()  {
        // Connect to registry
        try {
            logFileNum++;
            
            logFileName = new FileHandler("ClientLogFile" + logFileNum + ".log"); 
            SimpleFormatter formatter = new SimpleFormatter();
            logFileName.setFormatter(formatter);
            LOGGER.addHandler(logFileName);
            
            Registry registry = LocateRegistry.getRegistry("localhost");
            // Obtain reference to the remote object
            RMI_Remote RMI_Remote_Interface = (RMI_Remote) registry.lookup("RMI_Remote");
            logInfo("Connected to RMI remote interface");
            // Generate request batch
            ArrayList<Client_Request> requests = this.generateBatch();
            // Send Requests

            for(Client_Request request : requests) {
                String requestString = request.getrequeString();
                logInfo("Sending request: \n" + requestString );
                long startTime = System.currentTimeMillis();
                String response = RMI_Remote_Interface.Execute_Batch(requestString); // Send bach string
                long endTime = System.currentTimeMillis();
				long responseTime = endTime - startTime;
				request.setReponse(response);
				request.setResponseTime(responseTime);
                // Sleep for a while
                System.out.println(response);
                logInfo("Recieved response: " + response + "\nSleeping\n");
                int sleepTime = randomGenerator.nextInt(100);
                Thread.sleep(sleepTime);
            }
            logInfo("Done with requests\nDying...\nBYE :)\n");
            logFileName.close();

            File logFile = new File("statFile" + logFileNum + ".txt");
            FileWriter logFileWriter = new FileWriter(logFile , true);
            
            // statFileName = new FileHandler("statFile" + logFileNum + ".log");
            // statFileName.setFormatter(formatter);
            
            // LOGGER.addHandler(statFileName);
            for (Client_Request request : requests) {
                // logFileWriter.write((int) request.getResponseTime());
                logFileWriter.write(request.getNumOfRequests());
            }
            logFileWriter.close();

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public ArrayList<Client_Request> generateBatch() {
        ArrayList<Client_Request> requests = new ArrayList<Client_Request>();

		BatchGenerator requestGenerator = new BatchGenerator (NUMBER_OF_REQUESTS, WRITE_PERCENTAGE , NUMBER_OF_NODES);
		
		for(int i=0;i<NUMBER_OF_REQUESTS;i++) {
			Client_Request request = requestGenerator.getReqeust();
			requests.add(request);
		}
		
		return requests;
    }

    public void logInfo(String message) {
        LOGGER.log(Level.INFO, message);
    }

    public void logNum(String message) {
        LOGGER.log(Level.INFO, message);
    }
    
    public static void main(String[] args) {
        RMI_Client client = new RMI_Client();
        client.start();
    }
}
