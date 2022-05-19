public class Operations {
    private Graph graph;
    private int n1;
    private int n2;
    private char op_type;
    private long time_taken;
    private int result;

    public Operations(String query,Graph graph){
        this.graph=graph;
        parse_Query(query);
    }
    private void parse_Query(String query){
        String[] splits = query.split(" ", 3);
        this.op_type=splits[0].charAt(0);
        this.n1=Integer.parseInt(splits[1]);
        this.n2=Integer.parseInt(splits[2]);
    }
    public void Execute()
    {
        long startTime = System.nanoTime();
        if (this.op_type == 'Q' || this.op_type == 'q') {
            this.result = this.graph.BFS_Search(n1, n2);
        } else if (this.op_type == 'A' || this.op_type == 'a') {
            this.graph.Add_Edge(n1, n2);
        } else if (this.op_type == 'D' || this.op_type == 'd') {
            this.graph.remove_Edge(n1, n2);
        } else {
            System.out.println("Not supported operation");
        }
        this.time_taken = System.nanoTime() - startTime;
    }
    public int getResult() {
        return result;
    }

    public char getType() {
        return this.op_type;
    }

    public long getTime_taken() {
        return this.time_taken;
    }

}
