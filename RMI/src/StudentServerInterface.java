import java.rmi.*;
import java.util.HashMap;

public interface StudentServerInterface extends Remote {
    public double findScore(String name) throws RemoteException;
    public void updateStudent(String name, double score) throws RemoteException;
    public void addStudent(String name, double score) throws RemoteException;
    public void removeStudent(String name) throws RemoteException;
    public HashMap<String, Double> getAllScores() throws RemoteException;
}
