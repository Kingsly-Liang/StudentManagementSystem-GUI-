package ѧ��������Ϣ;

/**
 * ���ܼ�飺ʵ��"¼��","�޸�","���","ɾ��"ѧ��������Ϣ�����
 */

import java.awt.*;
import javax.swing.*;

public class StudentPanel extends JPanel{
	JLabel nolabel;       //ѧ�ű�ǩ
	JLabel namelabel;     //������ǩ
	JLabel genderlabel;   //�Ա��ǩ
	JLabel birthlabel;    //�������±�ǩ
	JLabel addresslabel;  //��ͥסַ��ǩ
	JLabel tellabel;      //�绰��ǩ
	
	JTextField nofield;     //ѧ�������
	JTextField namefield;   //���������
	JTextField genderfield; //�Ա������
	JTextField birthfield;  //�������������
	JTextField addressfield;//��ͥסַ�����
	JTextField telfield;    //�绰�����
	
	public StudentPanel(){
		setGUIComponent();
	}
	
	public void setGUIComponent() {
		// TODO Auto-generated method stub
		//��ʼ�����
		nolabel = new JLabel("ѧ��");
		nofield = new JTextField(10);
		namelabel = new JLabel("����");
		namefield = new JTextField(10);
		genderlabel = new JLabel("�Ա�");
		genderfield = new JTextField(10);
		birthlabel = new JLabel("��������");
		birthfield = new JTextField(10);
		addresslabel = new JLabel("��ͥסַ");
		addressfield = new JTextField(10);
		tellabel = new JLabel("��ϵ�绰");
		telfield = new JTextField(10);
		
		//�������
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
	
	//����ı��������
	public void clearContent(){
		nofield.setText("");
		namefield.setText("");
		genderfield.setText("");
		birthfield.setText("");
		addressfield.setText("");
		telfield.setText("");
	}
	
	//����
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
	
	//�õ��ı�������
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
