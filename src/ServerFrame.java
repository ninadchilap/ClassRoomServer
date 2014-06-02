import java.util.LinkedList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

public class ServerFrame extends JFrame implements ActionListener
{
	static ServerFrame currentObject;
	JPanel topPanel,ipPanel,studentMsg,parentPanel,audioPanel,studentPanel,textPanel;
	/* all the other gui components are now added to this parentPanel */
	
	JLabel ipLabel,sessionIdLabel,ipValueLabel,sessionIdValueLabel,appName;
	public int width,height;
	
	JComboBox studentNumberComboBox;

	String ipAddress=Server.serverIpAddress,sessionId=""+Server.serverSessionId;
	static LinkedList<JButton>addButton=new LinkedList<JButton>();
	static LinkedList<JButton>deleteButton=new LinkedList<JButton>();
	
	static LinkedList<JButton>addButtonText=new LinkedList<JButton>();
	static LinkedList<JButton>deleteButtonText=new LinkedList<JButton>();
	
	ServerFrame()
	{
		currentObject=this;
		/* initializeGraphicComponents();
		 * 
		 * this method will initialize all the graphic components displayed
		 * on the screen and ensuring that no NullPointerException is generated
		 */
		initializeGraphicComponents();
		//studentNumberComboBox.setSelectedIndex(0);
		///////////////////////////////////////
		/*
		studentPanel.setVisible(false);
		studentPanel=new JPanel();
		if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5)
			studentPanel.setLayout(new GridLayout(5,1,10,10));
		else
			studentPanel.setLayout(new GridLayout(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()),1,10,10));
		//studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
		studentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		studentPanel.setBorder(new CompoundBorder(new LineBorder(Color.YELLOW, 2), new EmptyBorder(5, 5, 5, 5)));
		for(int i=0;i<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && i<Student.studentListAudio.size();i++)
			studentPanel.add(createStudentPanel(i,Student.studentListAudio.get(i)));
		*/
		//audioPanel.add(studentPanel,BorderLayout.CENTER);
		//audioPanel.setBorder(new CompoundBorder(new LineBorder(Color.red, 2), new EmptyBorder(5, 5, 5, 5)));
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
		this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		//setSize((int)(width),(int)(height)); //setting the size of frame
		setLocation(0,0); // setting the location of the frame
		//this.setResizable(false); // setting the resizable attribute - false so that the user cannot resize the frame
		
		ipPanel=new JPanel(); // ip panel will contain the ip address and the auto-generated sessionID.
		
		topPanel=new JPanel(); // top panel will containg the ipPanel and the app's Name.
		
		
		topPanel.setLayout(new BorderLayout(10,10));
		ipPanel.setLayout(new GridLayout(2,1,10,10));
		
		/*
		 * adding different components to the ipPanel
		*/
		ipPanel.add(ipLabel);
		//ipPanel.add(ipValueLabel);
		ipPanel.add(sessionIdLabel);
		//ipPanel.add(sessionIdValueLabel);
		
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
		bottomPanel.add(listLabel,BorderLayout.CENTER);
		bottomPanel.add(studentNumberComboBox,BorderLayout.EAST);
		*/
		
		bottomPanel.add(listLabel);
		bottomPanel.add(studentNumberComboBox);
		textPanel=new JPanel();
		textPanel.setLayout(new BorderLayout(10,10));
		JTabbedPane tabbedPane=new JTabbedPane();
		tabbedPane.addTab("Audio",new JScrollPane(audioPanel));
		tabbedPane.addTab("Text",textPanel);
		
		topPanel.add(appName,BorderLayout.CENTER);
		topPanel.add(ipPanel,BorderLayout.EAST);
		
		parentPanel.add(topPanel,BorderLayout.NORTH);
		parentPanel.add(tabbedPane,BorderLayout.CENTER);
		parentPanel.add(bottomPanel,BorderLayout.SOUTH);
		add(parentPanel);
		studentNumberComboBox.addActionListener(this);	
		//createBorderForMyPanels();
	}

	private void createBorderForMyPanels(){
		audioPanel.setBorder(new CompoundBorder(new LineBorder(Color.YELLOW, 2), new EmptyBorder(0, 0, 0, 0)));
		parentPanel.setBorder(new CompoundBorder(new LineBorder(Color.RED, 2), new EmptyBorder(0, 0, 0, 0)));
		topPanel.setBorder(new CompoundBorder(new LineBorder(Color.RED, 2), new EmptyBorder(0, 0, 0, 0)));
		ipPanel.setBorder(new CompoundBorder(new LineBorder(Color.YELLOW, 2), new EmptyBorder(4, 4, 4, 4)));
		//studentMsg.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(4, 4, 4, 4)));
		
	}
	private void initializeGraphicComponents()
	{
		/* this method will initialize all the graphic components displayed
		 * on the screen and ensuring that no NullPointerException is generated
		 */
		studentMsg=new JPanel();
		parentPanel=new JPanel();
		parentPanel.setLayout(new BorderLayout(10,10));

		audioPanel=new JPanel();
		studentPanel=new JPanel();
		textPanel=new JPanel();
		
		appName=new JLabel("App Name");
		appName.setFont(new Font("lucida console",Font.PLAIN,70));
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
		
		ipLabel=new JLabel("IP Address : "+ipAddress);
		ipLabel.setFont(new Font("lucida console",Font.PLAIN,30));
		sessionIdLabel=new JLabel("Session ID : "+sessionId);
		sessionIdLabel.setFont(new Font("lucida console",Font.PLAIN,30));
		
		ipValueLabel=new JLabel(ipAddress);
		ipValueLabel.setFont(new Font("lucida console",Font.PLAIN,30));
		
		sessionIdValueLabel=new JLabel(sessionId);
		sessionIdValueLabel.setFont(new Font("lucida console",Font.PLAIN,30));
		
		studentNumberComboBox=new JComboBox();

		
		/* inserting different values in the  comboBox 
		 * providing Proff the choice to select any number number of students to be currently visible
		 */
		studentNumberComboBox.addItem("0");
		studentNumberComboBox.addItem("1");
		studentNumberComboBox.addItem("2");
		studentNumberComboBox.addItem("3");
		studentNumberComboBox.addItem("4");
		studentNumberComboBox.addItem("5");
		studentNumberComboBox.addItem("6");
		studentNumberComboBox.addItem("7");
		studentNumberComboBox.addItem("8");
		studentNumberComboBox.addItem("9");
		studentNumberComboBox.addItem("10");
		studentNumberComboBox.addItem("11");
		studentNumberComboBox.addItem("12");
		studentNumberComboBox.addItem("13");
		studentNumberComboBox.addItem("14");
		studentNumberComboBox.addItem("15");
		
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
		//studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
		
		//audioPanel.add(studentPanel,BorderLayout.CENTER);
		//audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
		
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

	private JPanel createStudentTextMsgPanel(int i,Student student)
	{
		/*
		JPanel panel = new JPanel(new BorderLayout(10,10));
		JPanel center1=new JPanel(new GridLayout(1,2,10,10));
		JPanel east1=new JPanel();
		east1.setLayout(new GridLayout(3,2,10,10));
		
		JPanel west2=new JPanel(new GridLayout(1,3,10,10));
		JPanel east2=new JPanel();
		east2.setLayout(new GridLayout(1,1,10,10));
		ImagePanel imagePanel=new ImagePanel("/home/lavish/Server_ClassRoom_Interaction/Server_ClassRoom_Interaction/Images/a.jpg",width/4,100);
		
		imagePanel.setSize(100, 100);
		west2.add(imagePanel);
		
		  
	    west2.add(new JLabel(student.studentName));
	    west2.add(new JLabel(student.doubtSubject));

		west2.setBackground(Color.gray);
		
	      
		JTextArea textMessageArea=new JTextArea(student.textMessage);
		textMessageArea.setFont(new Font("lucida console",Font.PLAIN,20));
		textMessageArea.setEditable(false);
		east2.add(new JScrollPane(textMessageArea));
		    
		center1.add(west2);
		center1.add(east2);
		   
		    
		east1.add(new JLabel(""));
		east1.add(new JLabel(""));
		    
	    east1.add(addButtonText.get(i));
		east1.add(deleteButtonText.get(i));
			
		east1.add(new JLabel(""));
		east1.add(new JLabel(""));
			
	    panel.add(center1,BorderLayout.CENTER);
	    panel.add(east1,BorderLayout.EAST);
		    
	    return panel;
	    */

		int studentPanelHeight=(int)(this.getHeight()*0.11);
		
		JPanel finalPanel=new JPanel();
		finalPanel.setBackground(Color.WHITE);
		finalPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 7));
		//finalPanel.setPreferredSize(new Dimension((int)(width),(int)(height*0.1)));
		//finalPanel.setPreferredSize(new Dimension(audioPanel.getWidth(),studentPanelHeight));
		//finalPanel.setBorder(new CompoundBorder(new LineBorder(Color.RED, 2), new EmptyBorder(0, 0, 0, 0)));
		
		ResizeImage imagea=new ResizeImage(student.pic); //--new
		JPanel imagePanel=imagea.getResizeImage(); // --new
		imagePanel.setPreferredSize(new Dimension((int)(width*0.1),studentPanelHeight));
		imagePanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		
		//ImagePanel imagePanel=new ImagePanel(Student.studentListAudio.get(i).pic,(int)(width*0.1),studentPanelHeight);
		//1111
		//imagePanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		JPanel namePanel=new JPanel();
		namePanel.setPreferredSize(new Dimension((int)(width*0.15),studentPanelHeight));
		namePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		JLabel studentNameLabel=new JLabel(student.studentName);
		studentNameLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		namePanel.add(studentNameLabel);
		namePanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));

		JPanel doubtPanel=new JPanel();
		doubtPanel.setPreferredSize(new Dimension((int)(width*0.15),studentPanelHeight));
		doubtPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		JLabel doubtSubjectLabel=new JLabel(student.doubtSubject);
		doubtSubjectLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		doubtPanel.add(doubtSubjectLabel);
		doubtPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		

		JPanel doubtMsgPanel=new JPanel();
		doubtMsgPanel.setPreferredSize(new Dimension((int)(width*0.4),(int)(studentPanelHeight)));
		doubtMsgPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		JTextArea doubtSubjectMsgLabel=new JTextArea(student.textMessage);
		doubtSubjectMsgLabel.setPreferredSize(new Dimension((int)(width*0.4*0.98),(int)(studentPanelHeight*0.9)));
		doubtSubjectMsgLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		doubtMsgPanel.add(new JScrollPane(doubtSubjectMsgLabel));
		doubtMsgPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		
		JPanel addButtonPanel=new JPanel();
		addButtonPanel.setPreferredSize(new Dimension((int)(width*0.05),studentPanelHeight));
		addButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		addButtonPanel.add(addButtonText.get(i));
		addButtonPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		JPanel deleteButtonPanel=new JPanel();
		deleteButtonPanel.setPreferredSize(new Dimension((int)(width*0.05),studentPanelHeight));
		deleteButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		deleteButtonPanel.add(deleteButtonText.get(i));
		deleteButtonPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		finalPanel.add(imagePanel);
		finalPanel.add(namePanel);
		finalPanel.add(doubtPanel);
		finalPanel.add(doubtMsgPanel);
		finalPanel.add(addButtonPanel);
		finalPanel.add(deleteButtonPanel);
		
		return finalPanel;
	}
	private JPanel createStudentPanel(int i,Student student)
	{
		/*
	    JPanel studentPanel=new JPanel();
		studentPanel.setLayout(new BorderLayout(10,10));
		studentPanel.setBorder(BorderFactory.createTitledBorder(""));
		
		JPanel westStudentPanel=new JPanel();
		westStudentPanel.setLayout(new GridLayout(1,2,10,10));
		
		JPanel eastStudentPanel=new JPanel();
		eastStudentPanel.setLayout(new GridLayout(3,2,10,10));

		JPanel westsub1=new JPanel(new GridLayout(1,2,10,10));
		
		JPanel westsub2=new JPanel(new GridLayout(1,1,10,10));
		
         
		JPanel img=new JPanel(new GridLayout(1,2,10,10));
		ImagePanel imagePanel=new ImagePanel(Student.studentListAudio.get(i).pic,width/4,100);
		//imagePanel.setSize(100, 100);
		//imagePanel.setBorder(new EmptyBorder(0, 10, 0,20) );
		img.add(imagePanel);
		img.add(new JLabel("  "));
		westsub1.add(img);
		westsub1.add(new JLabel(appendString(student.studentName)));
	    westsub2.add(new JLabel(student.doubtSubject));
		
		westStudentPanel.add(westsub1);
		westStudentPanel.add(westsub2);
		
		
		eastStudentPanel.add(new JLabel(""));
		eastStudentPanel.add(new JLabel(""));
		
		eastStudentPanel.add(addButton.get(i));
		
		eastStudentPanel.add(deleteButton.get(i));
		eastStudentPanel.add(new JLabel(""));
		eastStudentPanel.add(new JLabel(""));
		
		studentPanel.add(westStudentPanel,BorderLayout.CENTER);
		studentPanel.add(eastStudentPanel,BorderLayout.EAST);
		
		return studentPanel;
		*/
		
		int studentPanelHeight=(int)(this.getHeight()*0.11);
		
		JPanel finalPanel=new JPanel();
		finalPanel.setBackground(Color.WHITE);
		finalPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 7));
		//finalPanel.setPreferredSize(new Dimension((int)(width),(int)(height*0.1)));
		//finalPanel.setPreferredSize(new Dimension(audioPanel.getWidth(),studentPanelHeight));
		//finalPanel.setBorder(new CompoundBorder(new LineBorder(Color.RED, 2), new EmptyBorder(0, 0, 0, 0)));
		
		
		ResizeImage imagea=new ResizeImage(student.pic); //--new
		JPanel imagePanel=imagea.getResizeImage(); // --new
		
		//ImagePanel imagePanel=new ImagePanel(Student.studentListAudio.get(i).pic,(int)(width*0.1),studentPanelHeight);
		imagePanel.setPreferredSize(new Dimension((int)(width*0.1),studentPanelHeight));
		imagePanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		JPanel namePanel=new JPanel();
		namePanel.setPreferredSize(new Dimension((int)(width*0.25),studentPanelHeight));
		namePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		JLabel studentNameLabel=new JLabel(student.studentName);
		studentNameLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		namePanel.add(studentNameLabel);
		namePanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		JPanel doubtPanel=new JPanel();
		doubtPanel.setPreferredSize(new Dimension((int)(width*0.45),studentPanelHeight));
		doubtPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		JLabel doubtSubjectLabel=new JLabel(student.doubtSubject);
		doubtSubjectLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		doubtPanel.add(doubtSubjectLabel);
		doubtPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		JPanel addButtonPanel=new JPanel();
		addButtonPanel.setPreferredSize(new Dimension((int)(width*0.05),studentPanelHeight));
		addButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		addButtonPanel.add(addButton.get(i));
		addButtonPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		JPanel deleteButtonPanel=new JPanel();
		deleteButtonPanel.setPreferredSize(new Dimension((int)(width*0.05),studentPanelHeight));
		deleteButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		deleteButtonPanel.add(deleteButton.get(i));
		deleteButtonPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		finalPanel.add(imagePanel);
		finalPanel.add(namePanel);
		finalPanel.add(doubtPanel);
		finalPanel.add(addButtonPanel);
		finalPanel.add(deleteButtonPanel);
		
		return finalPanel;
	}

	public String appendString(String str)
	{
		for(int i=str.length();i<=30;i++)
			str=str+" ";
		return str;
	}
	
	public Insets getInsets()
	{
		return new Insets(40,20,20,20);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==studentNumberComboBox)
		{
			System.out.println("hello Kavi");
			//studentPanel.setVisible(false);
			audioPanel.removeAll();
			studentPanel.removeAll();
			studentPanel=new JPanel();
			//studentPanel.setPreferredSize(new Dimension(audioPanel.getWidth(),audioPanel.getHeight()));
			//studentPanel.setBorder(new CompoundBorder(new LineBorder(Color.MAGENTA, 2), new EmptyBorder(0, 0, 0, 0)));
			studentPanel.setBackground(Color.WHITE);
			
			if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5)
				studentPanel.setLayout(new GridLayout(5,1,10,10));
			else
				//studentPanel.setLayout(new GridLayout(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()),1,10,10));
			
			studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
			for(int i=0;i<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && i<Student.studentListAudio.size();i++)
			{
				System.out.println("hello Lavi");
				studentPanel.add(createStudentPanel(i,Student.studentListAudio.get(i)));
			}
			//studentPanel.setVisible(true);
			//audioPanel.add(studentPanel,BorderLayout.CENTER);
			audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
			
			audioPanel.setBackground(Color.WHITE);
			
			////////////////////////////////////////////////////////////////
			textPanel.removeAll();
			studentMsg.removeAll();
			//studentMsg.setVisible(false);
			studentMsg=new JPanel();
			studentMsg.setBackground(Color.WHITE);
			if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5)
				studentMsg.setLayout(new GridLayout(5,1,10,10));
			else
				//studentPanel.setLayout(new GridLayout(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()),1,10,10));
			
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
				
				//studentPanel.setVisible(false);
				audioPanel.removeAll();
				studentPanel.removeAll();
				studentPanel=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5)
					studentPanel.setLayout(new GridLayout(5,1,10,10));
				else
					//studentPanel.setLayout(new GridLayout(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()),1,10,10));
				studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
				//studentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				
				studentPanel.setBackground(Color.white);
			
				for(int j=0;j<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && j<Student.studentListAudio.size();j++)
					studentPanel.add(createStudentPanel(j,Student.studentListAudio.get(j)));
				
				//audioPanel.add(studentPanel,BorderLayout.CENTER);
				audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
				
				audioPanel.setBackground(Color.WHITE);
				return;
			}
			if(ae.getSource()==deleteButton.get(i))
			{
				Student.studentListAudio.remove(i);
				
				//studentPanel.setVisible(false);
				audioPanel.removeAll();
				studentPanel.removeAll();
				studentPanel=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5)
					studentPanel.setLayout(new GridLayout(5,1,10,10));
				else
					//studentPanel.setLayout(new GridLayout(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()),1,10,10));
				studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
				//studentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				
				studentPanel.setBackground(Color.white);
			
				for(int j=0;j<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && j<Student.studentListAudio.size();j++)
					studentPanel.add(createStudentPanel(j,Student.studentListAudio.get(j)));
				
				//audioPanel.add(studentPanel,BorderLayout.CENTER);
				audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
				
				audioPanel.setBackground(Color.WHITE);
				return;
			}
			
		}
		for(int i=0;i<Student.studentListText.size();i++)
		{
			if(ae.getSource()==addButtonText.get(i))
			{
				new GeneralDialogBox(this, Student.studentListText.get(i)).setVisible(true);
				Student.studentListText.remove(i);
				
				//studentPanel.setVisible(false);
				textPanel.removeAll();
				studentMsg.removeAll();
				studentMsg=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5)
					studentMsg.setLayout(new GridLayout(5,1,10,10));
				else
					//studentPanel.setLayout(new GridLayout(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()),1,10,10));
				studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
				//studentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				
				studentMsg.setBackground(Color.white);
			
				for(int j=0;j<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && j<Student.studentListText.size();j++)
					studentMsg.add(createStudentTextMsgPanel(j,Student.studentListText.get(j)));
				
				//audioPanel.add(studentPanel,BorderLayout.CENTER);
				textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);
				
				textPanel.setBackground(Color.WHITE);
				return;
			
			}
			if(ae.getSource()==deleteButtonText.get(i))
			{
				Student.studentListText.remove(i);
				
				//studentPanel.setVisible(false);
				textPanel.removeAll();
				studentMsg.removeAll();
				studentMsg=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5)
					studentMsg.setLayout(new GridLayout(5,1,10,10));
				else
					//studentPanel.setLayout(new GridLayout(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()),1,10,10));
				studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
				//studentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				
				studentMsg.setBackground(Color.white);
			
				for(int j=0;j<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && j<Student.studentListText.size();j++)
					studentMsg.add(createStudentTextMsgPanel(j,Student.studentListText.get(j)));
				
				//audioPanel.add(studentPanel,BorderLayout.CENTER);
				textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);
				
				textPanel.setBackground(Color.WHITE);
				return;
			}
			
		}
	}

	public static void methodToAddActionListener(JButton b) {
		// TODO Auto-generated method stub
		b.addActionListener(currentObject);
	}
}