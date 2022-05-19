import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// import Request; 

public class BatchGenerator {
    Random randomGenerator; // For random number generators

    int numOfRequests;
    double writePercentage;
    ArrayList<String> requestStrings;
    int NumberOfNodes;

    public BatchGenerator() {
        this.randomGenerator = new Random(); // For random number generators
        this.numOfRequests = 10;
        this.writePercentage = 0.5;
        this.NumberOfNodes = 15;
    }

    public BatchGenerator(int numOfRequests, double writePercentage, int NumberOfNodes) {
        this.randomGenerator = new Random(); // For random number generators
        this.numOfRequests = numOfRequests;
        this.writePercentage = writePercentage;
        this.NumberOfNodes = NumberOfNodes;
    }

    public Client_Request getReqeust () {
        generateBatch();
        String requestString = "" ;

		for(String request : this.requestStrings) {
			requestString += request;
			requestString += "\n";
		}
		requestString += "F";

		return new Client_Request(requestString , this.numOfRequests, this.writePercentage);
    }

    private void generateBatch() {
        this.requestStrings = new ArrayList<String>();

		for (int i=0;i<this.numOfRequests;i++) {
			if(i < this.numOfRequests*this.writePercentage) 
                requestStrings.add(this.requestWrite());
            else requestStrings.add(this.requestRead());
			
		}
        Collections.shuffle(requestStrings);
    }

    String twoNodes() {
        return (randomGenerator.nextInt(NumberOfNodes) + 1) + " " + (randomGenerator.nextInt(NumberOfNodes) + 1);
    }

    private String requestWrite() {
        String request = "";
        if( randomGenerator.nextInt(2) == 0) // Generate 0 for add 1 for delete
            request += "A ";
        
        else request += "D ";

        return request + twoNodes();
    }

    private String requestRead() {
        return "Q " + twoNodes();
    }


    public static void main(String[] args) {
        BatchGenerator bG = new BatchGenerator();
        Client_Request request = bG.getReqeust();
        System.out.println(request.getrequeString());
    }
}