import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;


public class RMI_Client extends Thread{

    int NUMBER_OF_REQUESTS =  100 ;
    double WRITE_PERCENTAGE = 0.4;
    int NUMBER_OF_NODES = 15;

    Random randomGenerator = new Random(); // For random number generators

    public void run()  {
        // Connect to registry
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            // Obtain reference to the remote object
            RMI_Remote RMI_Remote_Interface = (RMI_Remote) registry.lookup("RMI_Remote");
            // Generate request batch
            ArrayList<Request> requests = this.generateBatch();
            // Send Requests

            for(Request request : requests) {
                String response = RMI_Remote_Interface.Execute_Batch(request.getrequeString()); // Send bach string
                // Sleep for a while
                int sleepTime = randomGenerator.nextInt(100);
                Thread.sleep(sleepTime);
            }

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public ArrayList<Request> generateBatch() {
        ArrayList<Request> requests = new ArrayList<Request>();

		BatchGenerator requestGenerator = new BatchGenerator (NUMBER_OF_REQUESTS, WRITE_PERCENTAGE , NUMBER_OF_NODES);
		
		for(int i=0;i<NUMBER_OF_REQUESTS;i++) {
			Request request = requestGenerator.getReqeust();
			requests.add(request);
		}
		
		return requests;
    }
    
    public static void main(String[] args) {
        System.out.println("LOL");
    }
}
