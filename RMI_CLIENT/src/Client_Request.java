public class Client_Request {

    private String requestString ; 
	private int numOfRequests ;
	private double writePercentage ;
	private long responseTime;
	private String response;

	public Client_Request(String requestString , int numOfRequests, double writePercentage) {
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

    public void setReponse(String response) {
		this.response = response;
    }

    public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
    }

	public String getReponse() {
		return this.response;
    }

    public long getResponseTime() {
		return this.responseTime;
    }
}
