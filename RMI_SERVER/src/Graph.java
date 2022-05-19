import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Graph {
    private HashMap<Integer, HashSet<Integer>> graph;

    public Graph(String pathfile) throws FileNotFoundException {
     Initializer(pathfile);
    }

    private void Initializer(String pathfile) throws FileNotFoundException {
        graph=new HashMap<>();
        File file = new File(pathfile);
        Scanner sc=new Scanner(file);
        while (sc.hasNextLine()){
            String line=sc.nextLine();
            if (line.equals("S") || line.equals("s"))
            {
            break;
            }
            String []nodes=line.split(" ");
            Add_Edge(Integer.parseInt(nodes[0]),Integer.parseInt(nodes[1]));
        }
        sc.close();
    }
    public int BFS_Search(int n1,int n2)
    {
        Queue<Integer>queue=new LinkedList<>();
        queue.add(n1);
        HashMap<Integer, Integer> dist= new HashMap<>();
        dist.put(n1,0);
        while (!queue.isEmpty()){
            int n=queue.poll();

            int n_dist= dist.get(n);
            //required node founded
            if (n==n2)return n_dist;

            HashSet<Integer> neighbors=graph.get(n);

            if (neighbors==null)return -1;

            for(int neighbor:neighbors){
                //to check that the node not visited before
                if(dist.get(neighbor)==null){
                    queue.add(neighbor);
                    dist.put(neighbor,n_dist+1);
                }
            }

        }
        return -1;
    }
    public void Add_Edge(int n1,int n2)
    {
        HashSet set=graph.getOrDefault(n1,new HashSet<>());
        set.add(n2);
        graph.put(n1,set);
        if (graph.get(n2) == null) {
            graph.put(n2, new HashSet<>());
        }
    }
    public void remove_Edge(int n1,int n2)
    {
        if(graph.get(n1)==null)return;
        graph.get(n1).remove(n2);
    }
    public void print(){
      for(int k:graph.keySet()){
          System.out.print("For node "+k+": ");
          for(int j:graph.get(k)){
              System.out.print(j+" ");
          }
          System.out.println();
      }
    }
}
