public class Request {
    private long timetaken;
    private Graph graph;
    public Request(Graph graph)
    {
        this.graph=graph;
    }
    public String performOperations(String batch)
    {
        long startTime = System.nanoTime();
        String[] operations = batch.split("\n");
        String results = "";
        for (String operation : operations) {
            if (operation.equals("F") || operation.equals("f"))
                break;
            Operations operations1=new Operations(operation,graph);
            operations1.Execute();
            if (operations1.getType() == 'Q' || operations1.getType() == 'q') {
                results += (operations1.getResult() + "\n");
            }
        }
        this.timetaken = System.nanoTime() - startTime;
        return results;
    }
    public long getTimetaken() {
        return this.timetaken;
    }
}
