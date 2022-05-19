public class Request {

    private String requestString ; 
	private int numOfRequests ;
	private double writePercentage ;

	public Request(String requestString , int numOfRequests, double writePercentage) {
		this.requestString = requestString;
		this.numOfRequests = numOfRequests;
        this.writePercentage = writePercentage;
	}

	public String getrequeString() {
		return this.requestString;
	}

	public int getNumOfRequests() {
		return this.numOfRequests;
	}

	public double getWritePercentage() {
		return this.writePercentage ;
	}
}
