import java.rmi.*;
import java.rmi.server.*;
import java.sql.*;
import java.util.*;

public class StudentServerInterfaceImpl
        extends UnicastRemoteObject
        implements StudentServerInterface {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:8889/StudentDB";
	static final String USER = "root";
	static final String PASS = "root";
	
	Connection conn;
	Statement stmt;
	
	PreparedStatement updateStudent;
	PreparedStatement addStudent;
	PreparedStatement removeStudent;
	PreparedStatement getAllScore;
	PreparedStatement findScore;
	
	private HashMap<String, Double> scores = new HashMap<String, Double>();

    public StudentServerInterfaceImpl() throws RemoteException {
        try {
			initializeStudent();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    protected void initializeStudent() throws ClassNotFoundException, SQLException {
    	
    	Class.forName(JDBC_DRIVER); // loading drivers
		System.out.println("Connecting to database...");
		conn=DriverManager.getConnection(DB_URL,USER,PASS);//connecting to my database
		System.out.println("Creating statement...");
		stmt = conn.createStatement();//create a statement
		
		String updateTableSQL = "UPDATE scores SET score = ? WHERE name = ?";
		updateStudent = conn.prepareStatement(updateTableSQL);
		
		String insertTableSQL = "INSERT INTO scores values(?,?)";
		addStudent = conn.prepareStatement(insertTableSQL);
		
		String deleteTableSQL = "DELETE FROM scores WHERE name = ?";
		removeStudent = conn.prepareStatement(deleteTableSQL);
		
		String selectTableSQL = "SELECT name, score FROM scores";
		getAllScore = conn.prepareStatement(selectTableSQL);
		
		String findTableSQL  = "SELECT name, score FROM scores WHERE name = ?";
		findScore = conn.prepareStatement(findTableSQL);

    }

	@Override
	public void updateStudent(String name, double score) throws RemoteException {
		try {
			updateStudent.setDouble(1,score);
			updateStudent.setString(2,name);
			updateStudent.executeUpdate();
			//log for server
			System.out.println("Record is updated to student table : "+ name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addStudent(String name, double score) throws RemoteException {
		try {
			addStudent.setString(1,name);
			addStudent.setDouble(2,score);
			addStudent.executeUpdate();
			System.out.println("Record is inserted into student table : "+ name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeStudent(String name) throws RemoteException {
		try {
			removeStudent.setString(1,name);
			removeStudent.executeUpdate();
			System.out.println("Record is deleted from student table : "+ name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public HashMap<String, Double> getAllScores() throws RemoteException {
		try {
			ResultSet rs = getAllScore.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				Double score = rs.getDouble("score");
				scores.put(name, score);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return scores;
	}
	
	@Override
	public double findScore(String name) throws RemoteException {
		Double score = 0.0;
		try {
			findScore.setString(1,name);
			ResultSet rs = findScore.executeQuery();
			while (rs.next()) {
				score = rs.getDouble("score");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return score;
    }
}
