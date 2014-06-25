/*
 * This dialog Box is created when the Professor wishes to answer
 * a Student's question and clickes on the Tick Button corresponding to that student
 * 
 */

import java.awt.BorderLayout; 
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class GeneralDialogBox extends Dialog
{
	public GeneralDialogBox(JFrame serverFrame,Student student) 
	{
		super(serverFrame,true);
		setSize(800, 300);
		setLocation(serverFrame.getWidth()/4,serverFrame.getHeight()/4);
		setResizable(false);
		
		setLayout(new BorderLayout(10,10));
		
		JPanel dialogLeftPanel=new JPanel();
		dialogLeftPanel.setLayout(new BorderLayout(10,10));
		
		ResizeImage imagea=new ResizeImage(student.pic); //--new
		JPanel imagePanel=imagea.getResizeImage(); // --new
		dialogLeftPanel.add(imagePanel);
		JLabel dialogStudentName=new JLabel(appendString(student.studentName));
		dialogStudentName.setFont(new Font("lucida console",Font.PLAIN,20));
		dialogLeftPanel.add(dialogStudentName,BorderLayout.SOUTH);
		
		dialogLeftPanel.setBorder(BorderFactory.createTitledBorder(""));
		
		JPanel dialogRightPanel=new JPanel();
		dialogRightPanel.setLayout(new BorderLayout(10,10));
		
		JLabel dialogDoubtSubject=new JLabel(student.doubtSubject);
		dialogDoubtSubject.setFont(new Font("lucida console",Font.PLAIN,25));
		
		dialogRightPanel.add(dialogDoubtSubject,BorderLayout.NORTH);
		JTextArea dialogTextMessage=new JTextArea(student.textMessage);
		dialogTextMessage.setFont(new Font("lucida console",Font.PLAIN,20));
		
		dialogRightPanel.add(new JScrollPane(dialogTextMessage),BorderLayout.CENTER);
		
		dialogRightPanel.setBorder(BorderFactory.createTitledBorder(""));
		
		
		add(dialogLeftPanel,BorderLayout.WEST);
		add(dialogRightPanel,BorderLayout.CENTER);
		addWindowListener(new DlgAdapter(this));
		
	}
	public String appendString(String str)
	{
		/*
		 * this method is necessary to set the size of image through the length of nameString
		 * the length of nameString is kept at max 30 characters
		 * assuming that any name would never be longer than 30 characters;
		 * 
		 * the length of name (in this case 30)
		 * is used to set the width of panel in which we have our Student Image.
		 */
		for(int i=str.length();i<=30;i++)
			str=str+" ";
		return str;
	}
	
	public Insets getInsets()
	{
		return new Insets(30,20,20,20);
	}
}