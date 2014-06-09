import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WelcomeDialog extends Dialog implements ActionListener
{
	JPanel headingPanel,middlePanel,parentPanel,leftPanel,rightPanel,buttonPanel;
	JLabel headingLabel,nameLabel,departmentLabel,subjectLabel,topicLabel;
	JButton okButton,cancelButton;
	JTextField nameTextField,departmentTextField,subjectTextField,topicTextField;
	WelcomeDialog(ServerFrame serverFrame)
	{
		super(serverFrame,true);
		initialiseGraphicComponents();
		setSize(500,300);
		setLocation(100,100);
		headingPanel.add(headingLabel);
		parentPanel.setLayout(new BorderLayout(10,10));
		headingLabel.setFont(new Font("lucida console",Font.PLAIN,40));
		
		middlePanel.setLayout(new BorderLayout(10,10));
		
		leftPanel.setLayout(new GridLayout(5,1,10,10));
		rightPanel.setLayout(new GridLayout(5,1,10,10));
		
		leftPanel.add(nameLabel);
		leftPanel.add(departmentLabel);
		leftPanel.add(subjectLabel);
		leftPanel.add(topicLabel);
		
		rightPanel.add(nameTextField);
		rightPanel.add(departmentTextField);
		rightPanel.add(subjectTextField);
		rightPanel.add(topicTextField);
		
		middlePanel.add(leftPanel,BorderLayout.WEST);
		middlePanel.add(rightPanel,BorderLayout.CENTER);
		
		buttonPanel.setLayout(new GridLayout(1,4,10,10));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(new JLabel(""));
		
		
		parentPanel.add(headingPanel,BorderLayout.NORTH);
		parentPanel.add(middlePanel,BorderLayout.CENTER);
		parentPanel.add(buttonPanel,BorderLayout.SOUTH);
		add(parentPanel);
		
		addWindowListener(new DlgAdapter(this));
		
	}
	void initialiseGraphicComponents()
	{
		parentPanel=new JPanel();
		headingLabel=new JLabel("Lecture Details");
		headingPanel=new JPanel();
		middlePanel=new JPanel();
		
		leftPanel=new JPanel();
		rightPanel=new JPanel();
		buttonPanel=new JPanel();
		
		nameLabel=new JLabel("Professor's Name");
		departmentLabel=new JLabel("Department");
		subjectLabel=new JLabel("Subject");
		topicLabel=new JLabel("Topic");
		
		nameTextField=new JTextField();
		departmentTextField=new JTextField();
		subjectTextField=new JTextField();
		topicTextField=new JTextField();
		
		okButton=new JButton("OK");
		cancelButton=new JButton("Cancel");
	}
	public Insets getInsets()
	{
		return new Insets(20,10,10,10);
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		// TODO Auto-generated method stub
		if(ae.getSource()==okButton)
		{
			
		}
		else if(ae.getSource()==cancelButton)
		{
			System.exit(1);
		}
	}
}
