import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WelcomeDialog extends Dialog implements ActionListener
{
	JPanel headingPanel,middlePanel,parentPanel,leftPanel,rightPanel,buttonPanel;
	JLabel headingLabel,nameLabel,departmentLabel,subjectLabel,topicLabel;
	JButton okButton,cancelButton;
	JTextField nameTextField,departmentTextField,subjectTextField,topicTextField;
	public static String professorsName,departmentName,subjectName,topicName;
	
	WelcomeDialog(ServerFrame serverFrame,String professorName,String departmentName,String subjectName,String topicName)
	{
		
		super(serverFrame,true);
		this.setTitle("Login Details");
		WelcomeDialog.professorsName=professorName;
		WelcomeDialog.departmentName=departmentName;
		WelcomeDialog.subjectName=subjectName;
		WelcomeDialog.topicName=topicName;
		
		this.setResizable(false);
		//this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		
		//this.setUndecorated(true);
		//this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		
		initialiseGraphicComponents();
		//this.setLocationRelativeTo(null);
		setSize(520,325);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - getHeight()/2);
		//setLocation(100,100);
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
		
		parentPanel.setBorder(BorderFactory.createEmptyBorder(10,40,10,30));
		
		this.add(parentPanel);
		
		addWindowListener(new DlgAdapter(this));
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
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
		
		Font font=new Font("lucida console",Font.PLAIN,20);
		nameLabel=new JLabel("Professor's Name");
		nameLabel.setFont(font);
		departmentLabel=new JLabel("Department");
		departmentLabel.setFont(font);
		subjectLabel=new JLabel("Subject");
		subjectLabel.setFont(font);
		topicLabel=new JLabel("Topic");
		topicLabel.setFont(font);
		
		nameTextField=new JTextField();
		nameTextField.setText(professorsName);
		
		departmentTextField=new JTextField();
		departmentTextField.setText(departmentName);
		
		subjectTextField=new JTextField();
		subjectTextField.setText(subjectName);
		
		topicTextField=new JTextField();
		topicTextField.setText(topicName);
		
		okButton=new JButton("OK");
		cancelButton=new JButton("Cancel");
	}
	/*
	 *
	public Insets getInsets()
	{
		return new Insets(20,10,10,10);
	}
	*/
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		// TODO Auto-generated method stub
		if(ae.getSource()==okButton)
		{
			if(nameTextField.getText().equals("") || departmentTextField.getText().equals("") || subjectTextField.getText().equals("") || topicTextField.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this,"Please fill all the Fields","Field Missing",JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				professorsName=nameTextField.getText();
				departmentName=departmentTextField.getText();
				subjectName=subjectTextField.getText();
				topicName=topicTextField.getText();
				this.dispose();
			}				
		}
		else if(ae.getSource()==cancelButton)
		{
			if(JOptionPane.showConfirmDialog(this, "Are you sure, you want to exit?","Confirm Exit Dialog",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION)
				return;
			
			System.exit(1);
		}
	}
}