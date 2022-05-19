import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_Remote extends Remote {
    public String Execute_Batch(String batch) throws RemoteException;
    public String getCurrentGraph() throws RemoteException;
}
