package 学生基本信息;

/**
 * 功能简介：实现"录入","修改","浏览","删除"学生基本信息的面板
 */

import java.awt.*;
import javax.swing.*;

public class StudentPanel extends JPanel{
	JLabel nolabel;       //学号标签
	JLabel namelabel;     //姓名标签
	JLabel genderlabel;   //性别标签
	JLabel birthlabel;    //出生年月标签
	JLabel addresslabel;  //家庭住址标签
	JLabel tellabel;      //电话标签
	
	JTextField nofield;     //学号输入框
	JTextField namefield;   //姓名输入框
	JTextField genderfield; //性别输入框
	JTextField birthfield;  //出生年月输入框
	JTextField addressfield;//家庭住址输入框
	JTextField telfield;    //电话输入框
	
	public StudentPanel(){
		setGUIComponent();
	}
	
	public void setGUIComponent() {
		// TODO Auto-generated method stub
		//初始化组件
		nolabel = new JLabel("学号");
		nofield = new JTextField(10);
		namelabel = new JLabel("姓名");
		namefield = new JTextField(10);
		genderlabel = new JLabel("性别");
		genderfield = new JTextField(10);
		birthlabel = new JLabel("出生年月");
		birthfield = new JTextField(10);
		addresslabel = new JLabel("家庭住址");
		addressfield = new JTextField(10);
		tellabel = new JLabel("联系电话");
		telfield = new JTextField(10);
		
		//设置组件
		this.setLayout(new GridLayout(3,4));
		this.add(nolabel);
		this.add(nofield);
		this.add(namelabel);
		this.add(namefield);
		this.add(genderlabel);
		this.add(genderfield);
		this.add(birthlabel);
		this.add(birthfield);
		this.add(addresslabel);
		this.add(addressfield);
		this.add(tellabel);
		this.add(telfield);
		this.setVisible(true);
		this.setSize(300,300);
		
	}
	
	//清楚文本框的内容
	public void clearContent(){
		nofield.setText("");
		namefield.setText("");
		genderfield.setText("");
		birthfield.setText("");
		addressfield.setText("");
		telfield.setText("");
	}
	
	//输入
	public void setNo(String n){
		nofield.setText(n);
	}
	public void setName(String n){
		namefield.setText(n);
	}
	public void setGender(String n){
		genderfield.setText(n);
	}
	public void setBirth(String n){
		birthfield.setText(n);
	}
	public void setAddress(String n){
		addressfield.setText(n);
	}
	public void setTel(String n){
		telfield.setText(n);
	}
	
	//得到文本框内容
	public String getNo(){
		return nofield.getText();
	}
	public String getName(){
		return namefield.getText();
	}
	public String getGender(){
		return genderfield.getText();
	}
	public String getBirth(){
		return birthfield.getText();
	}
	public String getAddress(){
		return addressfield.getText();
	}
	public String getTel(){
		return telfield.getText();
	}
	
}
