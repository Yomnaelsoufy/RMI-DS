// import rmi.registery.RMI_Remote;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements RMI_Remote{

    public Graph getGraph() {
        return graph;
    }

    Graph graph;

    public Server() throws IOException {
        graph =new Graph("RMI_SERVER/file.txt");
    }
    @Override
    public String Execute_Batch(String batch) throws RemoteException {
        graph.logInfo("New batch request");
        Request request=new Request(graph);
        String str = request.performOperations(batch);
        graph.logInfo("End batch request");
        return str;
    }

    @Override
    public String getCurrentGraph() throws RemoteException {
        return graph.ConvertToStr();
    }
    public static void main(String[] args) throws IOException {
            String name = "RMI_Remote";
            RMI_Remote server = new Server();
            RMI_Remote stub = (RMI_Remote) UnicastRemoteObject.exportObject(server, 0);

            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind(name, stub);

            // Registry registry = LocateRegistry.getRegistry(); // run on local host and on post 1099
            // registry.rebind(name, stub);

            ((Server) server).graph.logInfo("Server register graph service into RMI registery");
    }
}
