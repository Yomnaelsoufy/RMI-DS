import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Graph a=new Graph("F:\\RMI_SERVER\\src\\file.txt");
        System.out.println(a.BFS_Search(1,3));
        a.Add_Edge(4,5);
        a.Add_Edge(5,3);
        a.remove_Edge(2,3);
        System.out.println(a.BFS_Search(1,3));
        a.print();
    }
}
