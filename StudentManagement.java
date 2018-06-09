package ѧ��������Ϣ;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.*; 

public class StudentManagement extends JFrame implements ActionListener{

	JTabbedPane dbTabPane;
	JPanel inputPanel;      //¼�����
	JPanel viewPanel;      //������
	JPanel updatePanel;      //�������
	JPanel deletePanel;      //ɾ�����
	
	JButton inputBtn;            //¼��
	JButton clearBtn1;
	StudentPanel inputInnerPanel;
	
	JTextArea viewArea;     //���
	JButton viewBtn;
	
	StudentPanel updateInnerPanel;   //�޸�����
	JLabel updateInputLbl;
	JTextField updateInputText;
	JButton updateBtn;
	
	StudentPanel deleteInnerPanel;
	JLabel inputNoLabel;
	JTextField inputNoField;
	JButton deleteBtn;
	
	Connection conn;
	Statement stmt;
	
	public StudentManagement(){
		super("ѧ��������Ϣ����ϵͳ");
		serGUIComponent();
	}
	
	public void serGUIComponent() {
		// TODO Auto-generated method stub
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		dbTabPane = new JTabbedPane();
		
		//����¼�����
		inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());
		inputBtn = new JButton("¼��");
		clearBtn1 = new JButton("���");
		/*ʹ��this���󣬿��Բ��������ڲ����ֱ���ڱ�����ʵ�ֽӿڸ��ǽӿڵķ�����*/
		inputBtn.addActionListener(this);
		clearBtn1.addActionListener(this);
		inputInnerPanel = new StudentPanel();
		inputPanel.add(inputInnerPanel);
		inputPanel.add(inputBtn);
		inputPanel.add(clearBtn1);
		dbTabPane.add("¼������", inputPanel);
		
		//����������
		viewPanel = new JPanel();
		viewPanel.setLayout(new BorderLayout());
		viewArea = new JTextArea(6,35);
		viewBtn = new JButton("���");
		viewPanel.add(new JScrollPane(viewArea), BorderLayout.CENTER);
		viewPanel.add(viewBtn,BorderLayout.SOUTH);
		viewBtn.addActionListener(this);
		dbTabPane.addTab("�������", viewPanel);
		
		//����������
		updateInnerPanel = new StudentPanel();
		updateInputLbl = new JLabel("����ѧ�ţ�");
		updateInputText = new JTextField(10);
		updateInputText.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//������������ʾ�������
				viewARecord(updateInputText.getText(),updateInnerPanel);
				updateInputText.setText("");
			}
		});
		updateBtn = new JButton("�޸�");
		updateBtn.addActionListener(this);
		updatePanel = new JPanel();
		updatePanel.add(updateInnerPanel);
		updatePanel.add(updateInputLbl);
		updatePanel.add(updateInputText);
		updatePanel.add(updateBtn);
		dbTabPane.add("�޸�����", updatePanel);
		
		//����ɾ�����
		deleteInnerPanel = new StudentPanel();
		inputNoLabel = new JLabel("����ѧ�ţ�");
		inputNoField = new JTextField(10);
		inputNoField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//��ɾ��������ʾ�������
				viewARecord(inputNoField.getText(),deleteInnerPanel);
				inputNoField.setText("");
			}
		});
		deleteBtn = new JButton("ɾ��");
		deleteBtn.addActionListener(this);
		deletePanel = new JPanel();
		deletePanel.add(deleteInnerPanel);
		deletePanel.add(inputNoLabel);
		deletePanel.add(inputNoField);
		deletePanel.add(deleteBtn);
		dbTabPane.add("ɾ������", deletePanel);
		
		c.add(BorderLayout.NORTH,dbTabPane);
		
	}
	
	public void connection(){      //�����������ݿ�
		try{
			Class.forName("com.hxtt.sql.access.AccessDriver"); //JDBC-ODBC�Ž���
			System.out.println("�����Ѽ���");
			conn = DriverManager.getConnection("jdbc:Access:///E:/java�������/java���ݿ���/Access2/student.mdb","","");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}catch(ClassNotFoundException e1){
			e1.printStackTrace();
		}catch(SQLException e2){
			e2.getSQLState();
			e2.getMessage();
		}
	}
	
	public void close(){    //�ر�ִ���������ݿ�
		try{
			if(stmt != null)
				stmt.close();
			if(conn != null)
				conn.close();
		}catch(SQLException e2){
			System.out.println("���������ر�");
		}
	}
	
	public void inputRecords(){      //¼��ѧ������
		String no = inputInnerPanel.getNo();
		String name = inputInnerPanel.getName();
		String gender = inputInnerPanel.getGender();
		String birth = inputInnerPanel.getBirth();
		String address = inputInnerPanel.getAddress();
		String tel = inputInnerPanel.getTel();
		
		try{
			connection();
			String InsSQL;
			InsSQL = "INSERT INTO student(ѧ��,����,�Ա�,��������,��ͥסַ,��ϵ�绰)"+"VALUES("+"'"+no+"',"+"'"+name+"',"+"'"+gender+"',"+"'"+birth+"',"+"'"+address+"',"+"'"+tel+"')";
			stmt.execute(InsSQL);
			JOptionPane.showMessageDialog(null, "����ɹ�");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close();
		}
	}
	
	public void viewARecord(String no, StudentPanel p){
		try{
			connection();
			String viewSQL = "SELECT * From student where ѧ��='"+no+"'";
			ResultSet rs = stmt.executeQuery(viewSQL);
			if(rs.next()){
				p.setNo(rs.getString("ѧ��"));
				p.setName(rs.getString("����"));
				p.setGender(rs.getString("�Ա�"));
				p.setBirth(rs.getString("��������"));
				p.setAddress(rs.getString("��ͥסַ"));
				p.setTel(rs.getString("��ϵ�绰"));
			}
		}catch(SQLException e){
			System.out.println("���ѧ����¼ʧ��");
			e.printStackTrace();
		}finally{
			close();
		}
	}
	
	public void viewRecords(){
		try{
			viewArea.setText("");
			String viewString = "";
			connection();
			ResultSet rs = stmt.executeQuery("SELECT * From student");
			ResultSetMetaData rsMeta = rs.getMetaData();
			int nums = rsMeta.getColumnCount();
			//����ֶ�����
			for(int i = 1; i <= nums; i++){
				viewString+=rsMeta.getColumnName(i) + "\t";
			}
			viewString += "\n";
			
			//������ݱ�student�ļ�¼
			while(rs.next()){
				for(int i = 1; i <= nums; i++){
					viewString += rs.getString(i) + "\t";
				}
				viewString += "\n";
				viewArea.setText(viewString);
			}
			rs.close();
		}catch(SQLException e){
			System.out.println("���ѧ����¼ʧ��");
			e.printStackTrace();
		}finally{
			close();
		}
	}
	
	public void updateRecord(String no){    //�޸�ѧ��Ϊnoѧ���ļ�¼
		String updateSQL = "update student set ���� =?,"+"�Ա�=?,"+"��������=?,"+"��ͥסַ=?,"+"��ϵ�绰=?"+""+"where ѧ��='"+no+"'";
		PreparedStatement stmt;
		
		try{
			connection();
			stmt = conn.prepareStatement(updateSQL);
			stmt.setString(1, updateInnerPanel.getName());
			stmt.setString(2, updateInnerPanel.getGender());
			stmt.setString(3, updateInnerPanel.getBirth());
			stmt.setString(4, updateInnerPanel.getAddress());
			stmt.setString(5, updateInnerPanel.getTel());
			stmt.execute();
			stmt.close();
			JOptionPane.showMessageDialog(null, "�޸����ݳɹ�");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close();
		}
	}
	
	public void deleteRecord(String no){
		String delStr = "delete from student where ѧ��='"+no+"'";
		try{
			connection();
			stmt.execute(delStr);
			JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
		}catch(SQLException e){
			System.err.println("ɾ��ʧ��");
			e.printStackTrace();
		}finally{
			close();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StudentManagement app = new StudentManagement();
		app.setSize(500,260);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == inputBtn){
			inputRecords();
		}else if(e.getSource() == viewBtn){
			viewRecords();
		}else if(e.getSource() == updateBtn){
			updateRecord(updateInnerPanel.getNo());
		}else if(e.getSource() == deleteBtn){
			deleteRecord(deleteInnerPanel.getNo());
			deleteInnerPanel.clearContent();
		}else if(e.getSource() == clearBtn1){
			inputInnerPanel.clearContent();
		}
	}

}
