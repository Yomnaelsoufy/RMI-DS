import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;

public class Main {

    public static void main(String[] args) throws IOException {
       /* Graph a=new Graph("file.txt");
        Operations op=new Operations("Q 1 3",a);
        op.Execute();
        System.out.println(op.getResult());
        System.out.println(a.BFS_Search(1,3));
       System.out.println(a.ConvertToStr());*/
        Server server=new Server();
        String b="Q 1 3\nA 4 5\nQ 1 5\nQ 5 1\nF";
        System.out.println(server.Execute_Batch(b));
    }
}
