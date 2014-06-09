import java.awt.BorderLayout; 
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
		//dialogLeftPanel.add(new ImagePanel(student.pic, 200,200),BorderLayout.CENTER);
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
		for(int i=str.length();i<=30;i++)
			str=str+" ";
		return str;
	}
	
	public Insets getInsets()
	{
		return new Insets(30,20,20,20);
	}
}