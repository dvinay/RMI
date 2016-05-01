import java.rmi.registry.*;

public class RegisterWithRMIServer {
	public static void main(String[] args) {
		try {
			StudentServerInterface obj = new StudentServerInterfaceImpl();
			//Registry registry = LocateRegistry.getRegistry();
			Registry registry = LocateRegistry.createRegistry(5000);
			//registry.rebind("StudentServerInterfaceImpl", obj);
			registry.bind("StudentServerInterfaceImpl", obj);
			System.out.println("Student server " + obj + " registered");
			System.out.println("Student server started...");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
