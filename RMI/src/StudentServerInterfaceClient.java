import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class StudentServerInterfaceClient extends JFrame {

    // Declare a Student instance

    private StudentServerInterface student;

    private JButton jbtGetScore = new JButton("Get Score");
    private JButton jbtAdd      = new JButton("Add Student");
    private JButton jbtRemove   = new JButton("Remove Student");
    private JButton jbtUpdate   = new JButton("Update Student");
    private JButton jbtGetAll   = new JButton("All student list");
    
    private JTextField jtfName = new JTextField();
    private JTextField jtfScore = new JTextField();
    
    Vector<String> listData = new Vector<String>();
	HashMap<String, Double> hm = new HashMap<String, Double>();
	
    public StudentServerInterfaceClient() {
        // Initialize RMI
        initializeRMI();
        
        JPanel jPanel1 = new JPanel();
        //jPanel1.setLayout(new GridLayout(5, 2));
        jPanel1.setLayout(new GridLayout(6,1));
        jPanel1.add(new JLabel("Name"));
        jPanel1.add(jtfName);
        jPanel1.add(new JLabel("Score"));
        jPanel1.add(jtfScore);
        
        jPanel1.add(jbtGetScore);
        jPanel1.add(jbtAdd);
        jPanel1.add(jbtRemove);
        jPanel1.add(jbtUpdate);
        jPanel1.add(jbtGetAll);
        
        add(jPanel1, BorderLayout.CENTER);

        jbtGetScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                getScore();
            }
        });
        jbtAdd.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent evt) {
        		addScore();
        	}
        });
        jbtRemove.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent evt) {
        		removeScore();
        	}
        });
        jbtUpdate.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent evt) {
        		updateScore();
        	}
        });
        jbtGetAll.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent evt) {
        		getAllScore();
        	}
        });       
        setTitle("StudentServerInterfaceClient");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(3);
    }

    private void getScore() {
        try {
        	if(!jtfName.getText().trim().equals("")){
	            double score = student.findScore(jtfName.getText().trim());
	            if (score < 0) {
	                jtfScore.setText("Not found");
	            } else {
	                jtfScore.setText(new Double(score).toString());
	            }
        	} else{
        		JOptionPane.showMessageDialog(this, "Name not supposed to be space");
        	}
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void addScore() {
        try {
        	if(!jtfName.getText().trim().equals("") && !jtfScore.getText().trim().equals("")){
        		student.addStudent(jtfName.getText().trim(),Double.parseDouble(jtfScore.getText()));
        	} else{
        		JOptionPane.showMessageDialog(this, "Name or score values are wrong");
        	}
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void removeScore() {
    	try {
    		if(!jtfName.getText().trim().equals("")){
        		student.removeStudent(jtfName.getText().trim());
    		} else{
    			JOptionPane.showMessageDialog(this, "Name not supposed to be space");
    		}

    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    private void updateScore() {
    	try {
    		if(!jtfName.getText().trim().equals("") && !jtfScore.getText().trim().equals("")){
    			student.updateStudent(jtfName.getText().trim(),Double.parseDouble(jtfScore.getText()));
    		} else{
        		JOptionPane.showMessageDialog(this, "Name or score values are wrong");
        	}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    private void getAllScore() {
    	
    	try {
    		hm = student.getAllScores();
    		for(Map.Entry m:hm.entrySet()){  
    			   listData.add(m.getKey()+" "+m.getValue());		   
    		}
    		System.out.println(listData);
    		JOptionPane.showMessageDialog(null,new JScrollPane(new JList(listData.toArray())));
    		hm.clear();
    		listData.clear();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    /**
     * Initialize RMI
     */
    protected void initializeRMI() { 
        String host = "localhost";
        try {
            Registry registry = LocateRegistry.getRegistry(host,5000);
            student = (StudentServerInterface) registry.lookup("StudentServerInterfaceImpl");
            System.out.println("Server object " + student + " found");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        StudentServerInterfaceClient frame = new StudentServerInterfaceClient();
    }
}