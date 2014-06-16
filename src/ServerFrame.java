/*
 *  This class contains all the basic gui coding 
 * of the main server screen visible to the Professor.
 * 
 * the top right area of the screen contains ip address
 * and a randomly generated four digit session id.
 * 
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;

public class ServerFrame extends JFrame implements ActionListener,ChangeListener,MouseListener
{
	static ServerFrame currentObject;
	JPanel topPanel,ipPanel,studentMsg,parentPanel,audioPanel,studentPanel,textPanel,detailsPanel;
	/* all the other gui components are now added to this parentPanel */
	static JTabbedPane tabbedPane;
	JLabel ipLabel,sessionIdLabel;
	public int width,height;
	static JButton waitingButton;
	
	/*
	 * this comboBox is provided to the professor so that professor can select any number of students 
	 * that will be visible in the audio or text panel.
	 * 
	 * Professor can select any number in the combo box to limit the number of Visible students to avoid clumpsiness
	 */
	static JComboBox studentNumberComboBox;
	
	String ipAddress=Server.serverIpAddress,sessionId=""+Server.serverSessionId;
	
	/*
	 * Button linkedList for the Audio section of the App
	 */
	static LinkedList<JButton>addButton=new LinkedList<JButton>();
	static LinkedList<JButton>deleteButton=new LinkedList<JButton>();
	
	/*
	 * Button linkedList for the text section of the app
	 */
	static LinkedList<JButton>addButtonText=new LinkedList<JButton>();
	static LinkedList<JButton>deleteButtonText=new LinkedList<JButton>();
	
	ServerFrame()
	{
		super("Class Room Interaction");
		WelcomeDialog welcomeDialog=new WelcomeDialog(this);
		welcomeDialog.setVisible(true);
		/*******************************************/
		
		
		
		/****************************************/

    	String filename= "Images/print.txt";
        FileWriter fw;
	    try 
	    {
	    	int i=0;
	    	while(new File(filename).exists())
		    {
	    		i++;
	    		filename="Images/print"+(i)+".txt";
	    		
		    }
	    	
		    fw = new FileWriter(filename,false);
		    fw.write("Professor's Name : "+WelcomeDialog.professorsName+"\n");
		    fw.write("Department : "+WelcomeDialog.departmentName+"\n");
		    fw.write("Subject : "+WelcomeDialog.subjectName+"\n");
		    fw.write("Topic : "+WelcomeDialog.topicName+"\n"+"\n"+"\n");
		    
		    
		    fw.write(String.format("%-20s%s", "USERNAME","DOUBT TEXT\n\n\n"));
		    fw.close();
	    } 
	    catch (IOException e1) {
	    	// TODO Auto-generated catch block
	    	e1.printStackTrace();
	    } //the true will append the new data
	       
	    /******************************************/
		currentObject=this;
		/* initializeGraphicComponents();
		 * 
		 * this method will initialize all the graphic components displayed
		 * on the screen and ensuring that no NullPointerException is generated
		 */
		initializeGraphicComponents();

		audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
		audioPanel.setBackground(Color.white);
		studentMsg.setVisible(false);
		studentMsg=new JPanel();
		studentMsg.setLayout(new GridLayout(5,1,10,10));
	
		for(int i=0;i<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && i<Student.studentListText.size();i++)
		{
			studentMsg.add(createStudentTextMsgPanel(i,Student.studentListText.get(i)));
		}
		
		textPanel.add(studentMsg,BorderLayout.CENTER);
		
		///////////////////////////////////////
		/*
		 * the following function is used to maximize the ServerFrame
		 */
		this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		setLocation(0,0); // setting the location of the frame
		
		ipPanel=new JPanel(); // ip panel will contain the ip address and the auto-generated sessionID.
		
		topPanel=new JPanel(); // top panel will containg the ipPanel and the app's Name.
		
		
		topPanel.setLayout(new BorderLayout(10,10));
		ipPanel.setLayout(new GridLayout(2,1,10,10));
		
		/*
		 * adding different components to the ipPanel
		*/
		ipPanel.add(ipLabel);
		ipPanel.add(sessionIdLabel);
		
		/*
		 * there are two tabbedPanes in the center of the screen
		 * each pane is a Panel 
		 * there are two panels added to the JTabbedPane
		 * first one is the audio Panel
		 * and the second one is the texxt Panel
		 */
		
		/* this is the method for creating the audio panel*/
		createAudioPanel();
		
		/* this is the method for creating the text panel */
		createTextPanel();
		
		
		JLabel listLabel=new JLabel("Number of Visible Students :");
		listLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		JPanel bottomPanel=new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		/*
		 * now here you have to create  a button that will show the status of waiting list.
		 */
		
		waitingButton = new JButton("Waiting : "+studentNumberComboBox.getSelectedItem().toString());

		bottomPanel.add(listLabel);
		bottomPanel.add(studentNumberComboBox);
		
		bottomPanel.add(waitingButton);
		
		textPanel=new JPanel();
		textPanel.setLayout(new BorderLayout(10,10));
		tabbedPane=new JTabbedPane();
		tabbedPane.addTab("Audio",audioPanel);
		tabbedPane.addTab("Text",textPanel);
		
		/***********************************************/
		detailsPanel.setLayout(new BorderLayout(10,10));
		JLabel departmentLabel=new JLabel(WelcomeDialog.departmentName);
		departmentLabel.setFont(new Font("lucida console",Font.PLAIN,45));
		
		JPanel subDetailsPanel=new JPanel();
		subDetailsPanel.setLayout(new GridLayout(2,2,5,5));
		JLabel professorNameLabel=new JLabel("-"+WelcomeDialog.professorsName);
		professorNameLabel.setFont(new Font("lucida console",Font.PLAIN,25));
		JLabel subjectNameLabel=new JLabel(WelcomeDialog.subjectName);
		subjectNameLabel.setFont(new Font("lucida console",Font.PLAIN,30));
		subDetailsPanel.add(subjectNameLabel);
		JLabel topicNameLabel=new JLabel(WelcomeDialog.topicName);
		topicNameLabel.setFont(new Font("lucida console",Font.PLAIN,25));
		subDetailsPanel.add(topicNameLabel);
		subDetailsPanel.add(professorNameLabel);
		subDetailsPanel.add(new JLabel(""));
		
		
		/*
		 * details panel to be updated here
		 */
		/***********************************************/
		detailsPanel.add(subDetailsPanel,BorderLayout.CENTER);
		detailsPanel.add(departmentLabel,BorderLayout.NORTH);
		
		topPanel.add(detailsPanel,BorderLayout.CENTER);
		topPanel.add(ipPanel,BorderLayout.EAST);
		
		parentPanel.add(topPanel,BorderLayout.NORTH);
		parentPanel.add(tabbedPane,BorderLayout.CENTER);
		parentPanel.add(bottomPanel,BorderLayout.SOUTH);
		add(parentPanel);
		studentNumberComboBox.addActionListener(this);	
		
		//createBorderForMyPanels();
		waitingButton.addActionListener(this);
		tabbedPane.addChangeListener(this);
	}

	private void createBorderForMyPanels() // this method is used for the debugging purpose
	{
		/*
		 * although this method is never called 
		 * one can use this method for the debugging purpose to find all the different panels
		 */
		audioPanel.setBorder(new CompoundBorder(new LineBorder(Color.YELLOW, 2), new EmptyBorder(0, 0, 0, 0)));
		parentPanel.setBorder(new CompoundBorder(new LineBorder(Color.RED, 2), new EmptyBorder(0, 0, 0, 0)));
		topPanel.setBorder(new CompoundBorder(new LineBorder(Color.RED, 2), new EmptyBorder(0, 0, 0, 0)));
		ipPanel.setBorder(new CompoundBorder(new LineBorder(Color.YELLOW, 2), new EmptyBorder(4, 4, 4, 4)));
		studentMsg.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(4, 4, 4, 4)));
		
	}
	private void initializeGraphicComponents()
	{
		/* this method will initialize all the graphic components displayed
		 * on the screen and ensuring that no NullPointerException is generated
		 */
		
		detailsPanel=new JPanel();
		studentMsg=new JPanel();
		parentPanel=new JPanel();
		parentPanel.setLayout(new BorderLayout(0,0));

		audioPanel=new JPanel();
		studentPanel=new JPanel();
		textPanel=new JPanel();
		
		for(int i=0;i<Student.studentListAudio.size();i++)
		{
			addButton.add(new JButton("\u2714"));
			addButton.get(i).setBackground(new Color(200,240,200));
			deleteButton.add(new JButton("X"));
			deleteButton.get(i).setBackground(new Color(240,200,200));
			
			addButton.get(i).addActionListener(this);
			deleteButton.get(i).addActionListener(this);
		}
		for(int i=0;i<Student.studentListText.size();i++)
		{
			addButtonText.add(new JButton("\u2714"));
			addButtonText.get(i).setBackground(new Color(200,240,200));
			deleteButtonText.add(new JButton("X"));
			deleteButtonText.get(i).setBackground(new Color(240,200,200));
			
			
			addButtonText.get(i).addActionListener(this);
			deleteButtonText.get(i).addActionListener(this);
		}
		
		/*
		 * getting the screen dimensions to get the frame in maximized mode
		 */
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		
		/*
		 * ipLabel contains the ipAddress of the Professor
		 */
		ipLabel=new JLabel("IP Address : "+ipAddress);
		ipLabel.setFont(new Font("lucida console",Font.PLAIN,30));
		
		/*
		 * SessionIdLabel contains the unique Session id of the lecture
		 */
		sessionIdLabel=new JLabel("Session ID : "+sessionId);
		sessionIdLabel.setFont(new Font("lucida console",Font.PLAIN,30));
		
		studentNumberComboBox=new JComboBox();

		
		/* 
		 * inserting different values in the  comboBox 
		 * providing Proff the choice to select any number number of students to be currently visible
		 */
		
		for(int i=0;i<=200;i++)
		{
			studentNumberComboBox.addItem(""+i);
		}
	}
	

	private void createAudioPanel()
	{
		audioPanel=new JPanel();
		
		audioPanel.setLayout(new BorderLayout(10,10));
		
		studentPanel=new JPanel();
		studentPanel.setBorder(new CompoundBorder(new LineBorder(Color.YELLOW, 2), new EmptyBorder(4, 4, 4, 4)));
		
		if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5)
			studentPanel.setLayout(new GridLayout(5,1,10,10));
		else
			studentPanel.setLayout(new GridLayout(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()),1,10,10));
		
		audioPanel.setBackground(Color.white);
		
	}
	
	private void createTextPanel()
	{
		textPanel=new JPanel();
		textPanel.setLayout(new BorderLayout(10,10));
	    
		studentMsg=new JPanel();
	    studentMsg.setLayout(new GridLayout(5,1,1,1));
		textPanel.add(studentMsg,BorderLayout.CENTER);
	}	

	public JPanel createStudentTextMsgPanel(int i,Student student)
	{

		int studentPanelHeight=(int)(this.getHeight()*0.11);
		
		JPanel finalPanel=new JPanel();
		finalPanel.setBackground(Color.WHITE);
		finalPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 7));
		
		ResizeImage imagea=new ResizeImage(student.pic); //--new
		JPanel imagePanel=imagea.getResizeImage(); // --new
		imagePanel.setPreferredSize(new Dimension((int)(width*0.1),studentPanelHeight));
		
		JPanel namePanel=new JPanel();
		namePanel.setPreferredSize(new Dimension((int)(width*0.15),studentPanelHeight));
		namePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		JLabel studentNameLabel=new JLabel(student.studentName);
		studentNameLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		namePanel.add(studentNameLabel);

		JPanel doubtPanel=new JPanel();
		doubtPanel.setPreferredSize(new Dimension((int)(width*0.15),studentPanelHeight));
		doubtPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		JLabel doubtSubjectLabel=new JLabel(student.doubtSubject);
		doubtSubjectLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		doubtPanel.add(doubtSubjectLabel);
		//doubtPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		

		JPanel doubtMsgPanel=new JPanel();
		doubtMsgPanel.setPreferredSize(new Dimension((int)(width*0.4),(int)(studentPanelHeight)));
		doubtMsgPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		JTextArea doubtSubjectMsgLabel=new JTextArea(student.textMessage);
		doubtSubjectMsgLabel.setEditable(false);
		doubtSubjectMsgLabel.setPreferredSize(new Dimension((int)(width*0.4*0.98),(int)(studentPanelHeight*0.9)));
		doubtSubjectMsgLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		doubtMsgPanel.add(new JScrollPane(doubtSubjectMsgLabel));
		
		
		JPanel addButtonPanel=new JPanel();
		addButtonPanel.setPreferredSize(new Dimension((int)(width*0.05),studentPanelHeight));
		addButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		addButtonPanel.add(addButtonText.get(i));
		
		JPanel deleteButtonPanel=new JPanel();
		deleteButtonPanel.setPreferredSize(new Dimension((int)(width*0.05),studentPanelHeight));
		deleteButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		deleteButtonPanel.add(deleteButtonText.get(i));
		
		finalPanel.add(imagePanel);
		finalPanel.add(namePanel);
		finalPanel.add(doubtPanel);
		finalPanel.add(doubtMsgPanel);
		finalPanel.add(addButtonPanel);
		finalPanel.add(deleteButtonPanel);
		finalPanel.addMouseListener(this);
		return finalPanel;
	}
	
	public JPanel createStudentPanel(int i,Student student)
	{

		int studentPanelHeight=(int)(this.getHeight()*0.11);
		
		JPanel finalPanel=new JPanel();
		finalPanel.setBackground(Color.WHITE);
		finalPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 7));
		
		
		ResizeImage imagea=new ResizeImage(student.pic); //--new
		JPanel imagePanel=imagea.getResizeImage(); // --new
		
		imagePanel.setPreferredSize(new Dimension((int)(width*0.1),studentPanelHeight));
		
		JPanel namePanel=new JPanel();
		namePanel.setPreferredSize(new Dimension((int)(width*0.25),studentPanelHeight));
		namePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		JLabel studentNameLabel=new JLabel(student.studentName);
		studentNameLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		namePanel.add(studentNameLabel);
		
		JPanel doubtPanel=new JPanel();
		doubtPanel.setPreferredSize(new Dimension((int)(width*0.45),studentPanelHeight));
		doubtPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		JLabel doubtSubjectLabel=new JLabel(student.doubtSubject);
		doubtSubjectLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		doubtPanel.add(doubtSubjectLabel);
		
		JPanel addButtonPanel=new JPanel();
		addButtonPanel.setPreferredSize(new Dimension((int)(width*0.05),studentPanelHeight));
		addButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		addButtonPanel.add(addButton.get(i));
		
		JPanel deleteButtonPanel=new JPanel();
		deleteButtonPanel.setPreferredSize(new Dimension((int)(width*0.05),studentPanelHeight));
		deleteButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		deleteButtonPanel.add(deleteButton.get(i));
		
		finalPanel.add(imagePanel);
		finalPanel.add(namePanel);
		finalPanel.add(doubtPanel);
		finalPanel.add(addButtonPanel);
		finalPanel.add(deleteButtonPanel);
		finalPanel.addMouseListener(this);
		return finalPanel;
	}
/*
	public String appendString(String str)
	{
		for(int i=str.length();i<=30;i++)
			str=str+" ";
		return str;
	}
	*/
	public Insets getInsets()
	{
		return new Insets(10,20,20,20);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==waitingButton)
		{
			if(tabbedPane.getSelectedIndex()==0)
			{
				new WaitingDialogForAudio(currentObject).setVisible(true);
			}
			else if(tabbedPane.getSelectedIndex()==1)
			{
				new WaitingDialogForText(currentObject).setVisible(true);
			}
			return;
		}
		if(ae.getSource()==studentNumberComboBox)
		{
			
			if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
				waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
			else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
				waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
			else 
				waitingButton.setText("Waiting : 0");
			
			audioPanel.removeAll();
			studentPanel.removeAll();
			studentPanel=new JPanel();
			studentPanel.setBackground(Color.WHITE);
			
			if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
				studentPanel.setLayout(new GridLayout(5,1,10,10));
			else			
				studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
			for(int i=0;i<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && i<Student.studentListAudio.size();i++)
			{
				studentPanel.add(createStudentPanel(i,Student.studentListAudio.get(i)));
			}
			audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
			
			audioPanel.setBackground(Color.WHITE);
			
			////////////////////////////////////////////////////////////////
			textPanel.removeAll();
			studentMsg.removeAll();
			studentMsg=new JPanel();
			studentMsg.setBackground(Color.WHITE);
			if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListText.size()<=5)
				studentMsg.setLayout(new GridLayout(5,1,10,10));
			else
				studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
		
			for(int i=0;i<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && i<Student.studentListText.size();i++)
			{
				studentMsg.add(createStudentTextMsgPanel(i,Student.studentListText.get(i)));
			}
			
			textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);
			return;
		}
		
		for(int i=0;i<Student.studentListAudio.size();i++)
		{
			if(ae.getSource()==addButton.get(i))
			{
				new GeneralDialogBox(this, Student.studentListAudio.get(i)).setVisible(true);
				Student.studentListAudio.remove(i);
				
				audioPanel.removeAll();
				studentPanel.removeAll();
				studentPanel=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
					studentPanel.setLayout(new GridLayout(5,1,10,10));
				else
					studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
				
				studentPanel.setBackground(Color.white);
			
				for(int j=0;j<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && j<Student.studentListAudio.size();j++)
					studentPanel.add(createStudentPanel(j,Student.studentListAudio.get(j)));
				
				audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
				
				audioPanel.setBackground(Color.WHITE);
				
				if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else 
					waitingButton.setText("Waiting : 0");
				return;
			}
			if(ae.getSource()==deleteButton.get(i))
			{
				Student.studentListAudio.remove(i);
				
				audioPanel.removeAll();
				studentPanel.removeAll();
				studentPanel=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
					studentPanel.setLayout(new GridLayout(5,1,10,10));
				else
					studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
				
				studentPanel.setBackground(Color.white);
			
				for(int j=0;j<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && j<Student.studentListAudio.size();j++)
					studentPanel.add(createStudentPanel(j,Student.studentListAudio.get(j)));
				
				//audioPanel.add(studentPanel,BorderLayout.CENTER);
				audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
				
				audioPanel.setBackground(Color.WHITE);
				
				if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else 
					waitingButton.setText("Waiting : 0");
				return;
			}
			
		}
		for(int i=0;i<Student.studentListText.size();i++)
		{
			if(ae.getSource()==addButtonText.get(i))
			{
				new GeneralDialogBox(this, Student.studentListText.get(i)).setVisible(true);
				Student.studentListText.remove(i);
				
				textPanel.removeAll();
				studentMsg.removeAll();
				studentMsg=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListText.size()<=5)
					studentMsg.setLayout(new GridLayout(5,1,10,10));
				else
					studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
				
				studentMsg.setBackground(Color.white);
			
				for(int j=0;j<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && j<Student.studentListText.size();j++)
					studentMsg.add(createStudentTextMsgPanel(j,Student.studentListText.get(j)));
				
				textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);
				
				textPanel.setBackground(Color.WHITE);
				
				if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else 
					waitingButton.setText("Waiting : 0");
				return;
			
			}
			if(ae.getSource()==deleteButtonText.get(i))
			{
				Student.studentListText.remove(i);
				
				textPanel.removeAll();
				studentMsg.removeAll();
				studentMsg=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListText.size()<=5)
					studentMsg.setLayout(new GridLayout(5,1,10,10));
				else
					studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
				
				studentMsg.setBackground(Color.white);
			
				for(int j=0;j<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && j<Student.studentListText.size();j++)
					studentMsg.add(createStudentTextMsgPanel(j,Student.studentListText.get(j)));
				
				textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);
				
				textPanel.setBackground(Color.WHITE);
				if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else 
					waitingButton.setText("Waiting : 0");
				return;
			}
			
		}
	}

	public static void methodToAddActionListener(JButton b) 
	{
		b.addActionListener(currentObject);
	}
	
	public static void refreshFrame()
	{
		if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
			waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
		else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
			waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
		else 
			waitingButton.setText("Waiting : 0");
		new RefreshingThread(currentObject);
	}
	

	@Override
	public void stateChanged(ChangeEvent ce) 
	{
		System.out.println("you changed the state of the program...:P");
		if(((JTabbedPane)(ce.getSource())).getSelectedIndex()==0)
		{
			if((Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
				waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
			else 
				waitingButton.setText("Waiting : 0");
				
		}
		else if(((JTabbedPane)(ce.getSource())).getSelectedIndex()==1)
		{
			if((Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
				waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
			else 
				waitingButton.setText("Waiting : 0");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		((JPanel)(e.getSource())).setBorder(new CompoundBorder(new LineBorder(Color.RED, 2), new EmptyBorder(0, 0, 0, 0)));
		//setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		((JPanel)(e.getSource())).setBorder(new CompoundBorder(new LineBorder(Color.WHITE, 2), new EmptyBorder(0, 0, 0, 0)));
		//setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateAudioPanel()
	{
		System.out.println("updation of audio panel required : ");
		audioPanel.removeAll();
		studentPanel.removeAll();
		studentPanel=new JPanel();
		if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
			studentPanel.setLayout(new GridLayout(5,1,10,10));
		else
			studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
		
		studentPanel.setBackground(Color.white);
	
		for(int j=0;j<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && j<Student.studentListAudio.size();j++)
			studentPanel.add(createStudentPanel(j,Student.studentListAudio.get(j)));
		
		audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
		
		audioPanel.setBackground(Color.WHITE);
		
		if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
			waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
		else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
			waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
		else 
			waitingButton.setText("Waiting : 0");
	}
	
	public void updateTextPanel()
	{
		textPanel.removeAll();
		studentMsg.removeAll();
		studentMsg=new JPanel();
		if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListText.size()<=5)
			studentMsg.setLayout(new GridLayout(5,1,10,10));
		else
			studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
		
		studentMsg.setBackground(Color.white);
	
		for(int j=0;j<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && j<Student.studentListText.size();j++)
			studentMsg.add(createStudentTextMsgPanel(j,Student.studentListText.get(j)));
		
		textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);
		
		textPanel.setBackground(Color.WHITE);
		
		if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
			waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
		else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
			waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
		else 
			waitingButton.setText("Waiting : 0");
	}
}