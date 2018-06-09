package 学生基本信息;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.*; 

public class StudentManagement extends JFrame implements ActionListener{

	JTabbedPane dbTabPane;
	JPanel inputPanel;      //录入面板
	JPanel viewPanel;      //浏览面板
	JPanel updatePanel;      //更新面板
	JPanel deletePanel;      //删除面板
	
	JButton inputBtn;            //录入
	JButton clearBtn1;
	StudentPanel inputInnerPanel;
	
	JTextArea viewArea;     //浏览
	JButton viewBtn;
	
	StudentPanel updateInnerPanel;   //修改数据
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
		super("学生基本信息管理系统");
		serGUIComponent();
	}
	
	public void serGUIComponent() {
		// TODO Auto-generated method stub
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		dbTabPane = new JTabbedPane();
		
		//定义录入面板
		inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());
		inputBtn = new JButton("录入");
		clearBtn1 = new JButton("清除");
		/*使用this对象，可以不用声明内部类而直接在本类中实现接口覆盖接口的方法。*/
		inputBtn.addActionListener(this);
		clearBtn1.addActionListener(this);
		inputInnerPanel = new StudentPanel();
		inputPanel.add(inputInnerPanel);
		inputPanel.add(inputBtn);
		inputPanel.add(clearBtn1);
		dbTabPane.add("录入数据", inputPanel);
		
		//定义浏览面板
		viewPanel = new JPanel();
		viewPanel.setLayout(new BorderLayout());
		viewArea = new JTextArea(6,35);
		viewBtn = new JButton("浏览");
		viewPanel.add(new JScrollPane(viewArea), BorderLayout.CENTER);
		viewPanel.add(viewBtn,BorderLayout.SOUTH);
		viewBtn.addActionListener(this);
		dbTabPane.addTab("浏览数据", viewPanel);
		
		//定义更新面板
		updateInnerPanel = new StudentPanel();
		updateInputLbl = new JLabel("输入学号：");
		updateInputText = new JTextField(10);
		updateInputText.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//将更新面板的显示内容清除
				viewARecord(updateInputText.getText(),updateInnerPanel);
				updateInputText.setText("");
			}
		});
		updateBtn = new JButton("修改");
		updateBtn.addActionListener(this);
		updatePanel = new JPanel();
		updatePanel.add(updateInnerPanel);
		updatePanel.add(updateInputLbl);
		updatePanel.add(updateInputText);
		updatePanel.add(updateBtn);
		dbTabPane.add("修改数据", updatePanel);
		
		//定义删除面板
		deleteInnerPanel = new StudentPanel();
		inputNoLabel = new JLabel("输入学号：");
		inputNoField = new JTextField(10);
		inputNoField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//将删除面板的显示内容清除
				viewARecord(inputNoField.getText(),deleteInnerPanel);
				inputNoField.setText("");
			}
		});
		deleteBtn = new JButton("删除");
		deleteBtn.addActionListener(this);
		deletePanel = new JPanel();
		deletePanel.add(deleteInnerPanel);
		deletePanel.add(inputNoLabel);
		deletePanel.add(inputNoField);
		deletePanel.add(deleteBtn);
		dbTabPane.add("删除数据", deletePanel);
		
		c.add(BorderLayout.NORTH,dbTabPane);
		
	}
	
	public void connection(){      //建立连接数据库
		try{
			Class.forName("com.hxtt.sql.access.AccessDriver"); //JDBC-ODBC桥接器
			System.out.println("驱动已加载");
			conn = DriverManager.getConnection("jdbc:Access:///E:/java程序设计/java数据库编程/Access2/student.mdb","","");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}catch(ClassNotFoundException e1){
			e1.printStackTrace();
		}catch(SQLException e2){
			e2.getSQLState();
			e2.getMessage();
		}
	}
	
	public void close(){    //关闭执行语句和数据库
		try{
			if(stmt != null)
				stmt.close();
			if(conn != null)
				conn.close();
		}catch(SQLException e2){
			System.out.println("不能正常关闭");
		}
	}
	
	public void inputRecords(){      //录入学生数据
		String no = inputInnerPanel.getNo();
		String name = inputInnerPanel.getName();
		String gender = inputInnerPanel.getGender();
		String birth = inputInnerPanel.getBirth();
		String address = inputInnerPanel.getAddress();
		String tel = inputInnerPanel.getTel();
		
		try{
			connection();
			String InsSQL;
			InsSQL = "INSERT INTO student(学号,姓名,性别,出生年月,家庭住址,联系电话)"+"VALUES("+"'"+no+"',"+"'"+name+"',"+"'"+gender+"',"+"'"+birth+"',"+"'"+address+"',"+"'"+tel+"')";
			stmt.execute(InsSQL);
			JOptionPane.showMessageDialog(null, "插入成功");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close();
		}
	}
	
	public void viewARecord(String no, StudentPanel p){
		try{
			connection();
			String viewSQL = "SELECT * From student where 学号='"+no+"'";
			ResultSet rs = stmt.executeQuery(viewSQL);
			if(rs.next()){
				p.setNo(rs.getString("学号"));
				p.setName(rs.getString("姓名"));
				p.setGender(rs.getString("性别"));
				p.setBirth(rs.getString("出生年月"));
				p.setAddress(rs.getString("家庭住址"));
				p.setTel(rs.getString("联系电话"));
			}
		}catch(SQLException e){
			System.out.println("浏览学生记录失败");
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
			//获得字段名称
			for(int i = 1; i <= nums; i++){
				viewString+=rsMeta.getColumnName(i) + "\t";
			}
			viewString += "\n";
			
			//获得数据表student的记录
			while(rs.next()){
				for(int i = 1; i <= nums; i++){
					viewString += rs.getString(i) + "\t";
				}
				viewString += "\n";
				viewArea.setText(viewString);
			}
			rs.close();
		}catch(SQLException e){
			System.out.println("浏览学生记录失败");
			e.printStackTrace();
		}finally{
			close();
		}
	}
	
	public void updateRecord(String no){    //修改学号为no学生的记录
		String updateSQL = "update student set 姓名 =?,"+"性别=?,"+"出生年月=?,"+"家庭住址=?,"+"联系电话=?"+""+"where 学号='"+no+"'";
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
			JOptionPane.showMessageDialog(null, "修改数据成功");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close();
		}
	}
	
	public void deleteRecord(String no){
		String delStr = "delete from student where 学号='"+no+"'";
		try{
			connection();
			stmt.execute(delStr);
			JOptionPane.showMessageDialog(null, "删除成功");
		}catch(SQLException e){
			System.err.println("删除失败");
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
