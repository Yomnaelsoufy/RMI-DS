import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.File;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Graph {
    private HashMap<Integer, HashSet<Integer>> graph;
    private ReadWriteLock readWriteLock;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private FileHandler fh;
    public Graph(String pathfile) throws IOException {
        try {
            // This block configure the logger with handler and formatter
            fh = new FileHandler("MyLogFile.log");
            LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Initializer(pathfile);
    }

    private void Initializer(String pathfile) throws FileNotFoundException {
        readWriteLock = new ReentrantReadWriteLock();
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
        logInfo("Initial graph is initialized successfully.");
    }
    public int BFS_Search(int n1,int n2)
    {
        readWriteLock.readLock().lock();
        Queue<Integer>queue=new LinkedList<>();
        queue.add(n1);
        HashMap<Integer, Integer> dist= new HashMap<>();
        dist.put(n1,0);
        int res=-1;
        while (!queue.isEmpty()){
            int n=queue.poll();

            int n_dist= dist.get(n);
            //required node founded
            if (n==n2)
            {
                res=n_dist;
                break;
            }

            HashSet<Integer> neighbors=graph.get(n);

            if (neighbors==null)break;

            for(int neighbor:neighbors){
                //to check that the node not visited before
                if(dist.get(neighbor)==null){
                    queue.add(neighbor);
                    dist.put(neighbor,n_dist+1);
                }
            }

        }
        readWriteLock.readLock().unlock();
        if (res == -1)
            logInfo("Query: There is no path between " + n1 + " and " + n2);
        else
            logInfo("Query: shortest path distance between: " + n1 + " and " + n2 + " is " + res);
        return res;
    }
    public void Add_Edge(int n1,int n2)
    {
        readWriteLock.writeLock().lock();
        HashSet set=graph.getOrDefault(n1,new HashSet<>());
        set.add(n2);
        graph.put(n1,set);
        if (graph.get(n2) == null) {
            graph.put(n2, new HashSet<>());
        }
        logInfo("Add a new edge to the graph: " + n1 + " " + n2);
        readWriteLock.writeLock().unlock();
    }
    public void remove_Edge(int n1,int n2)
    {
        readWriteLock.writeLock().lock();
        if(graph.get(n1)==null)return;
        graph.get(n1).remove(n2);
        logInfo("Remove an edge from the graph: " + n1 + " " + n2);
        readWriteLock.readLock().unlock();
    }
    public String ConvertToStr(){
        readWriteLock.readLock().lock();
        String res = "";
      for(int k:graph.keySet()){
          for(int j:graph.get(k)){
              res+=(k+" "+j+"\n");
          }
      }
      readWriteLock.readLock().unlock();
      return res;
    }
    public void logInfo(String message) {
        LOGGER.log(Level.INFO, message);
    }
}
