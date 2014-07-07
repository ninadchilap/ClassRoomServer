/*
 * lavish kothari
 *  This class contains all the basic gui coding 
 * of the main server screen visible to the Professor.
 * 
 * the top right area of the screen contains ip address
 * and a randomly generated four digit session id.
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ServerFrame extends JFrame implements ActionListener,ChangeListener,MouseListener,KeyListener
{
	/*
	  adding a menu bar
	 */
	LinkedList<JButton> tempAddButtonList,tempDeleteButtonList;
	LinkedList<Student>tempStudentLinkedList;
	
	static String currentlySpeakingip,filenameInServerFrame;
	int fileNumber;
	MenuBar menuBar;
	Menu menu;
	MenuItem newMenuItem,exportMenuItem,exitMenuItem,checkForNonSenseMenuItem,watchWaitingListMenuItem;
	static int fileNo=0;
	
	static ServerFrame currentObject;
	JPanel topPanel,ipPanel,studentMsg,parentPanel,audioPanel,studentPanel,textPanel,detailsPanel;
	/* all the other gui components are now added to this parentPanel */
	static JTabbedPane tabbedPane;
	JLabel ipLabel,sessionIdLabel;
	public int width,height;
	static JButton waitingButton;
	static String ipAddressString,sessionIdString;
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
	
	static String professorName,departmentName,subjectName,topicName;
	
	static GeneralDialogBox gd;
	
	JIconTextField searchField;
	
	ServerFrame(String professorName,String departmentName,String subjectName,String topicName) throws IOException
	{
		super("Class Room Interaction");
		
		/*
		File image2=new File("Images/mic_icon.png");
		System.out.println("hello lavish kothari");
		try {
			Image image = ImageIO.read(image2);
			this.setIconImages((List<? extends Image>) image);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		*/
		final List<Image> icons = new ArrayList<Image>();
		icons.add(ImageIO.read(new File("Images/mic_logo.png")));
		this.setIconImages(icons);
        
		/**************************/
		/*
		 *  set the look and feel to reflect the platform
		 */
		
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		/*************************/
		ServerFrame.professorName=professorName;
		ServerFrame.departmentName=departmentName;
		ServerFrame.subjectName=subjectName;
		ServerFrame.topicName=topicName;
		
		WelcomeDialog welcomeDialog=new WelcomeDialog(this,professorName,departmentName,subjectName,topicName);
		welcomeDialog.setVisible(true);
		/*******************************************/
		
		/****************************************/

    	filenameInServerFrame= "Images/print.txt";
        FileWriter fw;
	    try 
	    {
	    	int i=0;
                fileNo=i;
	    	while(new File(filenameInServerFrame).exists())
		    {
	    		i++;
                        fileNo=i;
	    		filenameInServerFrame="Images/print"+(i)+".txt";
		    }
	    	fileNumber=i;
		    fw = new FileWriter(filenameInServerFrame,false);
		    fw.write("Professor's Name : "+WelcomeDialog.professorsName+"\n");
		    fw.write("Department : "+WelcomeDialog.departmentName+"\n");
		    fw.write("Subject : "+WelcomeDialog.subjectName+"\n");
		    fw.write("Topic : "+WelcomeDialog.topicName+"\n"+"\n"+"\n");
		    
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
		studentMsg.setLayout(new GridLayout(5,1,10,5));
	
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
		topPanel.setBackground(new Color(255,255,255));
		
		topPanel.setLayout(new BorderLayout(30,30));
		
		//topPanel.setBorder(new MatteBorder(0,0,1,0,new Color(240,240,240)));
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
		
		
		JLabel listLabel=new JLabel("View :");
		listLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		JPanel bottomPanel=new JPanel();
		bottomPanel.setLayout(new BorderLayout(10,10));
		
		/*
		 * now here you have to create  a button that will show the status of waiting list.
		 */
		
		//waitingButton = new JButton("Waiting : "+studentNumberComboBox.getSelectedItem().toString());
		waitingButton = new JButton("Waiting : 0");

		JPanel viewPanel=new JPanel();
		viewPanel.setLayout(new FlowLayout());
		viewPanel.setBackground(new Color(255,255,255));
		viewPanel.add(listLabel);
		viewPanel.add(studentNumberComboBox);
		bottomPanel.add(viewPanel,BorderLayout.WEST);
		//bottomPanel.add(studentNumberComboBox,BorderLayout.CENTER);
		
		//bottomPanel.add(waitingButton,BorderLayout.EAST);
		bottomPanel.add(searchField,BorderLayout.EAST);
		
		textPanel=new JPanel();
		textPanel.setBackground(Color.WHITE);
		textPanel.setLayout(new BorderLayout(10,10));
		tabbedPane=new JTabbedPane();
		ImageIcon icon1=new ImageIcon("Images/audio2.png");
		ImageIcon icon2=new ImageIcon("Images/doubt_text.png");
		
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=0 marginwidth="+(int)(this.width*0.2152)+" marginheight=0><h2>Audio<h2></body></html>",audioPanel);
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=0 marginwidth="+(int)(this.width*0.2152)+" marginheight=0><h2>Text</h2></body></html>",textPanel);
		
		/***********************************************/
		detailsPanel.setLayout(new BorderLayout(10,10));
		/*
		JPanel subDetailsPanel=new JPanel();
		subDetailsPanel.setLayout(new GridLayout(2,2,5,5));
		*/
		/*
		 * creating labels
		 */
		JLabel departmentLabel=new JLabel("@ "+WelcomeDialog.departmentName);
		departmentLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		departmentLabel.setForeground(new Color(200,200,200));
		
		JLabel professorNameLabel=new JLabel(WelcomeDialog.professorsName);
		professorNameLabel.setFont(new Font("lucida console",Font.PLAIN,30));
		professorNameLabel.setForeground(new Color(100,100,100));
		
		JLabel subjectNameLabel=new JLabel(" "+WelcomeDialog.subjectName);
		subjectNameLabel.setFont(new Font("lucida console",Font.PLAIN,45));
		subjectNameLabel.setForeground(new Color(150,150,150));
		subjectNameLabel.setBorder(new MatteBorder(0, 3, 0, 0, new Color(200,200,200)));
		
		
		JLabel topicNameLabel=new JLabel(WelcomeDialog.topicName);
		topicNameLabel.setFont(new Font("lucida console",Font.BOLD,45));
		
		/*
		subDetailsPanel.add(subjectNameLabel);
		subDetailsPanel.add(topicNameLabel);
		subDetailsPanel.add(professorNameLabel);
		subDetailsPanel.add(new JLabel(""));
		
		detailsPanel.add(subDetailsPanel,BorderLayout.CENTER);
		detailsPanel.add(departmentLabel,BorderLayout.NORTH);
		*/
		
		JPanel topDetailsPanel=new JPanel();
		topDetailsPanel.setLayout(new BorderLayout(10,10));
		JPanel bottomDetailsPanel=new JPanel();
		bottomDetailsPanel.setLayout(new BorderLayout(10,10));
		
		topDetailsPanel.add(topicNameLabel,BorderLayout.WEST);
		topDetailsPanel.add(subjectNameLabel,BorderLayout.CENTER);
		
		bottomDetailsPanel.setBackground(new Color(255,255,255));
		topDetailsPanel.setBackground(new Color(255,255,255));
		
		bottomDetailsPanel.add(professorNameLabel,BorderLayout.WEST);
		bottomDetailsPanel.add(departmentLabel,BorderLayout.CENTER);
		
		detailsPanel.add(topDetailsPanel,BorderLayout.NORTH);
		detailsPanel.add(bottomDetailsPanel,BorderLayout.SOUTH);
		detailsPanel.setBorder(new MatteBorder(0, 0, 0, 3, new Color(200,200,200)));
		
		//topPanel.setBorder(new EmptyBorder(20,20,20,20));
		detailsPanel.setBackground(new Color(255,255,255));
		ipPanel.setBackground(new Color(255,255,255));
		
		topPanel.add(detailsPanel,BorderLayout.CENTER);
		topPanel.add(ipPanel,BorderLayout.EAST);
		
		//ipPanel.setBorder(new CompoundBorder(new LineBorder(Color.YELLOW, 2), new EmptyBorder(0, 0, 0, 0)));
		ipPanel.addMouseListener(this);
		
		parentPanel.setBorder(BorderFactory.createEmptyBorder(15,40,20,40));
		parentPanel.setBackground(new Color(255,255,255));
		parentPanel.add(topPanel,BorderLayout.NORTH);
		tabbedPane.setBorder(new EmptyBorder(20, 0, 0, 0));
		parentPanel.add(tabbedPane,BorderLayout.CENTER);
		bottomPanel.setBackground(new Color(255,255,255));
		parentPanel.add(bottomPanel,BorderLayout.SOUTH);
		this.add(parentPanel);
		this.setMenuBar(menuBar);
		studentNumberComboBox.addActionListener(this);	
		
		newMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);
		exportMenuItem.addActionListener(this);
		checkForNonSenseMenuItem.addActionListener(this);
		watchWaitingListMenuItem.addActionListener(this);
		
		//createBorderForMyPanels();
		waitingButton.addActionListener(this);
		tabbedPane.addChangeListener(this);
		searchField.addKeyListener(this);
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
	private void initializeGraphicComponents() throws IOException
	{
		/* this method will initialize all the graphic components displayed
		 * on the screen and ensuring that no NullPointerException is generated
		 */
		searchField = new JIconTextField();
		menuBar=new MenuBar();
		menu=new Menu("Menu");
		newMenuItem=new MenuItem("New Server");
		exportMenuItem=new MenuItem("Export");
		checkForNonSenseMenuItem=new MenuItem("Check for Non-Sense");
		watchWaitingListMenuItem=new MenuItem("Watch Waiting List");
		exitMenuItem=new MenuItem("Exit");
		
		
		menuBar.add(menu);
		menu.add(newMenuItem);
		menu.add(exportMenuItem);
		menu.add(checkForNonSenseMenuItem);
		menu.add(watchWaitingListMenuItem);
		menu.addSeparator();
		menu.add(exitMenuItem);
		
		
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
		studentNumberComboBox.setSelectedIndex(5);
	}
	

	private void createAudioPanel()
	{ 
		audioPanel=new JPanel();
		
		audioPanel.setLayout(new BorderLayout(10,10));
		
		studentPanel=new JPanel();
		studentPanel.setBorder(new CompoundBorder(new LineBorder(Color.YELLOW, 2), new EmptyBorder(4, 4, 4, 4)));
		
		if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5)
			studentPanel.setLayout(new GridLayout(5,1,10,5));
		else
			studentPanel.setLayout(new GridLayout(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()),1,10,10));
		
		audioPanel.setBackground(new Color(240,240,240));
		
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
		
		int width=(int)(this.width*1.04); // -- new
		
		JPanel finalPanel=new JPanel();
		finalPanel.setBackground(Color.WHITE);
		finalPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 1));
		finalPanel.setBorder(new MatteBorder(0, 5, 0, 0, Color.WHITE));
		
		ResizeImage imagea=new ResizeImage(student.pic); //--new
		JPanel imagePanel=imagea.getResizeImage(); // --new
		imagePanel.setPreferredSize(new Dimension((int)(width*0.08),studentPanelHeight));
		
		JPanel namePanel=new JPanel();
		namePanel.setPreferredSize(new Dimension((int)(width*0.155),studentPanelHeight));
		namePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		JLabel studentNameLabel=new JLabel(student.studentName);
		studentNameLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		namePanel.add(studentNameLabel);

		JPanel doubtPanel=new JPanel();
		doubtPanel.setPreferredSize(new Dimension((int)(width*0.17),studentPanelHeight));
		doubtPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		JLabel doubtSubjectLabel=new JLabel(student.doubtSubject);
		doubtSubjectLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		doubtPanel.add(doubtSubjectLabel);
		
		JPanel doubtMsgPanel=new JPanel();
		doubtMsgPanel.setPreferredSize(new Dimension((int)(width*0.4),(int)(studentPanelHeight)));
		doubtMsgPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		JTextArea doubtSubjectMsgLabel=new JTextArea(student.textMessage);
		doubtSubjectMsgLabel.setEditable(false);
		doubtSubjectMsgLabel.setPreferredSize(new Dimension((int)(width*0.4*0.98),(int)(studentPanelHeight*0.9)));
		doubtSubjectMsgLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		doubtMsgPanel.add(new JScrollPane(doubtSubjectMsgLabel));
		
		JPanel addButtonPanel=new JPanel();
		addButtonPanel.setPreferredSize(new Dimension((int)(width*0.04),studentPanelHeight));
		addButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		addButtonPanel.add(addButtonText.get(i));
		
		JPanel deleteButtonPanel=new JPanel();
		deleteButtonPanel.setPreferredSize(new Dimension((int)(width*0.04),studentPanelHeight));
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
		int width=(int)(this.width*1.04);
		JPanel finalPanel=new JPanel();
		finalPanel.setBackground(Color.WHITE);
		finalPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 1));
		//finalPanel.setBorder(new MatteBorder(0, 15, 0, 0, Color.WHITE));
		
		
		ResizeImage imagea=new ResizeImage(student.pic); 
		JPanel imagePanel=imagea.getResizeImage(); 
		imagePanel.setPreferredSize(new Dimension((int)(width*0.08),studentPanelHeight));
		
		JPanel namePanel=new JPanel();
		namePanel.setPreferredSize(new Dimension((int)(width*0.252),studentPanelHeight));
		namePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		JLabel studentNameLabel=new JLabel(student.studentName);
		studentNameLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		namePanel.add(studentNameLabel);
		
		JPanel doubtPanel=new JPanel();
		doubtPanel.setPreferredSize(new Dimension((int)(width*0.448),studentPanelHeight));
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
	/*
	public Insets getInsets()
	{
		return new Insets((int)(this.getHeight()*0.06),(int)(this.getHeight()*0.025),(int)(this.getHeight()*0.025),(int)(this.getHeight()*0.025));
	}
	*/
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent ae)
	{

		if(ae.getSource()==newMenuItem)
		{
			/*
			 * confirming whether the professor 
			 * surely wants to start a new session
			 */
			int option=JOptionPane.showConfirmDialog(this, "Would you like to export the current session before starting the new Session?","Confirm Dialog",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(option==JOptionPane.CANCEL_OPTION)
				return;
			if(option==JOptionPane.YES_OPTION)
			{
				/*
				 * this code will be given later
				 */
				/*
				fileNumber will now contain the latest file to be uploaded to the server
				*/
				String filepath=filenameInServerFrame;
				int fileNumber=fileNo;
				System.out.println("path="+filepath+"\nfileno="+fileNumber);
	            new AakashForumServer(filepath,fileNo);
				
				return;
			}
			
			
			////////////////////////////////////
			
			
			if(AudioThread.th.isAlive())
			{
				System.out.println("this thread for stopping the thread is working");
				AudioThread.th.stop();
			}
			if(AudioThread1.th.isAlive())
			{
				AudioThread1.th.stop();
			}
			if(LoginThreadAudio.th.isAlive())
			{
				LoginThreadAudio.th.stop();
			}
			if(LoginThreadText.th.isAlive())
			{
				LoginThreadText.th.stop();
			}
			if(NotificationToSpeak.th.isAlive())
			{
				NotificationToSpeak.th.stop();
			}
			if(NotifyAllClients.th.isAlive())
			{
				NotifyAllClients.th.stop();
			}
			if(TextThread.th.isAlive())
			{
				TextThread.th.stop();
			}
			
			//////////////////////////////////////
			  
			if(Server.serverSocketAudio!=null)
				try {
					System.out.println("one thing done");
					Server.serverSocketAudio.close();
					Server.serverSocketAudio=null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	
	    	if(Server.serverSocketText!=null)
				try {
					Server.serverSocketText.close();
					Server.serverSocketText=null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	
	    	
	    	if(Server.clientAudio!=null)
				try {
					Server.clientAudio.close();
					Server.clientAudio=null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	
	    	if(Server.clientText!=null)
				try {
					Server.clientText.close();
					Server.clientText=null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	
	    	if(AudioThread.serverSocket!=null)
	    	{
	    		AudioThread.serverSocket.close();
	    		AudioThread.serverSocket=null;
	    	
	    	}
	    	
	    	if(NotificationToSpeak.client_speak!=null)
				try {
					NotificationToSpeak.client_speak.close();
					NotificationToSpeak.client_speak=null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	
	    	
	    	if(NotifyAllClients.client_speak!=null)
	    	{
	    		try {
					NotifyAllClients.client_speak.close();
					NotifyAllClients.client_speak=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    	/////////////////////////////////////////////////////////////////////////////////////////////
			this.dispose();
			try {
				Server.mainExecution(WelcomeDialog.professorsName,WelcomeDialog.departmentName,"","");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		if(ae.getSource()==exportMenuItem)
		{
			/*
			 * this code will be given later
			 */
			/*
			fileNumber will now contain the latest file to be uploaded to the server
			*/
			String filepath=filenameInServerFrame;
			int fileNumber=fileNo;
			System.out.println("path="+filepath+"\nfileno="+fileNumber);
            new AakashForumServer(filepath,fileNo);
			
			return;
		}
		if(ae.getSource()==checkForNonSenseMenuItem)
		{
			System.out.println("sys3");
			return;
		}
		if(ae.getSource()==exitMenuItem)
		{
			if(JOptionPane.showConfirmDialog(this, "Are you sure, you want to exit?","Confirm Exit Dialog",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION)
				return;
			
			System.exit(0);
			return;
		}
		if(ae.getSource()==watchWaitingListMenuItem)
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
		
	///////////////////////////////////////////////////////
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


			///////////////////////////////////////////
			/*
			 * doing extras for the search functionality
			 */
			///////////////////////////////////////////
			if(searchField.getText().equals(""))
			{
				ServerFrame.refreshFrame();
				return;
			}
			else
			{
				if(tabbedPane.getSelectedIndex()==0)// for audio
				{
					audioPanel.removeAll();
					studentPanel.removeAll();
					studentPanel=new JPanel();
					if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
						studentPanel.setLayout(new GridLayout(5,1,10,5));
					else
						studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
					
					studentPanel.setBackground(Color.white);
				
					for(int j=0;j<Student.studentListAudio.size();j++)
					{
						Student currentStudent=Student.studentListAudio.get(j);
						if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
							studentPanel.add(createStudentPanel(j,Student.studentListAudio.get(j)));
					}
					audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
					
					audioPanel.setBackground(Color.WHITE);
					
					if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
						waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
					else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
						waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
					else 
						waitingButton.setText("Waiting : 0");
					
					audioPanel.revalidate();
					studentPanel.revalidate();
				}
				else if(tabbedPane.getSelectedIndex()==1)// for text
				{
					textPanel.removeAll();
					studentMsg.removeAll();
					studentMsg=new JPanel();
					if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListText.size()<=5)
						studentMsg.setLayout(new GridLayout(5,1,10,5));
					else
						studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
					
					studentMsg.setBackground(Color.white);
				
					for(int j=0;j<Student.studentListText.size();j++)
					{
						Student currentStudent=Student.studentListText.get(j);
						if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
							studentMsg.add(createStudentTextMsgPanel(j,Student.studentListText.get(j)));
					}
					textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);
					
					textPanel.setBackground(Color.WHITE);
					
					if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
						waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
					else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
						waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
					else 
						waitingButton.setText("Waiting : 0");
					
					textPanel.revalidate();
					studentMsg.revalidate();
				}

				
			}
			///////////////////////////////////////////
			/*
			* doing extras for the search functionality
			* finishes here
			*/
			///////////////////////////////////////////

			
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
				studentPanel.setLayout(new GridLayout(5,1,10,5));
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
				studentMsg.setLayout(new GridLayout(5,1,10,5));
			else
				studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
		
			for(int i=0;i<Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()) && i<Student.studentListText.size();i++)
			{
				studentMsg.add(createStudentTextMsgPanel(i,Student.studentListText.get(i)));
			}
			
			textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);


			///////////////////////////////////////////
			/*
			 * doing extras for the search functionality
			 */
			///////////////////////////////////////////
			if(searchField.getText().equals(""))
			{
				ServerFrame.refreshFrame();
				return;
			}
			else
			{
				if(tabbedPane.getSelectedIndex()==0)// for audio
				{
					audioPanel.removeAll();
					studentPanel.removeAll();
					studentPanel=new JPanel();
					if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
						studentPanel.setLayout(new GridLayout(5,1,10,5));
					else
						studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
					
					studentPanel.setBackground(Color.white);
				
					for(int j=0;j<Student.studentListAudio.size();j++)
					{
						Student currentStudent=Student.studentListAudio.get(j);
						if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
							studentPanel.add(createStudentPanel(j,Student.studentListAudio.get(j)));
					}
					audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
					
					audioPanel.setBackground(Color.WHITE);
					
					if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
						waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
					else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
						waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
					else 
						waitingButton.setText("Waiting : 0");
					
					audioPanel.revalidate();
					studentPanel.revalidate();
				}
				else if(tabbedPane.getSelectedIndex()==1)// for text
				{
					textPanel.removeAll();
					studentMsg.removeAll();
					studentMsg=new JPanel();
					if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListText.size()<=5)
						studentMsg.setLayout(new GridLayout(5,1,10,5));
					else
						studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
					
					studentMsg.setBackground(Color.white);
				
					for(int j=0;j<Student.studentListText.size();j++)
					{
						Student currentStudent=Student.studentListText.get(j);
						if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
							studentMsg.add(createStudentTextMsgPanel(j,Student.studentListText.get(j)));
					}
					textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);
					
					textPanel.setBackground(Color.WHITE);
					
					if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
						waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
					else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
						waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
					else 
						waitingButton.setText("Waiting : 0");
					
					textPanel.revalidate();
					studentMsg.revalidate();
				}

				
			}
			///////////////////////////////////////////
			/*
			* doing extras for the search functionality
			* finishes here
			*/
			///////////////////////////////////////////

			
			return;
		}
		
		for(int i=0;i<Student.studentListAudio.size();i++)
		{
			if(ae.getSource()==addButton.get(i))
			{
				currentlySpeakingip=Student.studentListAudio.get(i).ip;
				/*
				 * Sending the client the permission to speak - server says - "its your turn"
				 */
				new NotificationToSpeak((Student.studentListAudio.get(i)).ip); // new
				
				gd=new GeneralDialogBox(this, Student.studentListAudio.get(i));
				gd.setVisible(true);
				
				System.out.println("/////////////////hellohellohellohellohellohellopppppppppppppppppppppppppppppppppppppp");
				
				//new NotifyAllClients(Student.studentListAudio.get(i).ip,"single_to_kick");
				for(int j =0 ;j<Student.studentListAudio.size();j++)
				{
					if(Student.studentListAudio.get(j).ip.equals(currentlySpeakingip))
					{
						new NotifyAllClients(Student.studentListAudio.get(j).ip,"single_to_kick");
						Student.studentListAudio.remove(j);
					
					}
				}
				
				
				
				currentlySpeakingip="";
				new NotifyAllClients("","multi");
				
				/*
				 * now broadcast the respective queue number to each client
				 */
				//new NotifyAllClients(); // new
				/////////////////////////////////////////////////////////////////////////
				audioPanel.removeAll();
				studentPanel.removeAll();
				studentPanel=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
					studentPanel.setLayout(new GridLayout(5,1,10,5));
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
				refreshFrame();


				///////////////////////////////////////////
				/*
				 * doing extras for the search functionality
				 */
				///////////////////////////////////////////
				if(searchField.getText().equals(""))
				{
					ServerFrame.refreshFrame();
					return;
				}
				else
				{
					if(tabbedPane.getSelectedIndex()==0)// for audio
					{
						audioPanel.removeAll();
						studentPanel.removeAll();
						studentPanel=new JPanel();
						if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
							studentPanel.setLayout(new GridLayout(5,1,10,5));
						else
							studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
						
						studentPanel.setBackground(Color.white);
					
						for(int j=0;j<Student.studentListAudio.size();j++)
						{
							Student currentStudent=Student.studentListAudio.get(j);
							if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
								studentPanel.add(createStudentPanel(j,Student.studentListAudio.get(j)));
						}
						audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
						
						audioPanel.setBackground(Color.WHITE);
						
						if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else 
							waitingButton.setText("Waiting : 0");
						
						audioPanel.revalidate();
						studentPanel.revalidate();
					}
					else if(tabbedPane.getSelectedIndex()==1)// for text
					{
						textPanel.removeAll();
						studentMsg.removeAll();
						studentMsg=new JPanel();
						if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListText.size()<=5)
							studentMsg.setLayout(new GridLayout(5,1,10,5));
						else
							studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
						
						studentMsg.setBackground(Color.white);
					
						for(int j=0;j<Student.studentListText.size();j++)
						{
							Student currentStudent=Student.studentListText.get(j);
							if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
								studentMsg.add(createStudentTextMsgPanel(j,Student.studentListText.get(j)));
						}
						textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);
						
						textPanel.setBackground(Color.WHITE);
						
						if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else 
							waitingButton.setText("Waiting : 0");
						
						textPanel.revalidate();
						studentMsg.revalidate();
					}

					
				}
				///////////////////////////////////////////
				/*
				* doing extras for the search functionality
				* finishes here
				*/
				///////////////////////////////////////////

				
				return;
			}
			if(ae.getSource()==deleteButton.get(i))
			{
				
				new NotifyAllClients(Student.studentListAudio.get(i).ip,"single_to_kick");
				
				Student.studentListAudio.remove(i);
				new NotifyAllClients("","multi");
				
				audioPanel.removeAll();
				studentPanel.removeAll();
				studentPanel=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
					studentPanel.setLayout(new GridLayout(5,1,10,5));
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


				///////////////////////////////////////////
				/*
				 * doing extras for the search functionality
				 */
				///////////////////////////////////////////
				if(searchField.getText().equals(""))
				{
					ServerFrame.refreshFrame();
					return;
				}
				else
				{
					if(tabbedPane.getSelectedIndex()==0)// for audio
					{
						audioPanel.removeAll();
						studentPanel.removeAll();
						studentPanel=new JPanel();
						if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
							studentPanel.setLayout(new GridLayout(5,1,10,5));
						else
							studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
						
						studentPanel.setBackground(Color.white);
					
						for(int j=0;j<Student.studentListAudio.size();j++)
						{
							Student currentStudent=Student.studentListAudio.get(j);
							if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
								studentPanel.add(createStudentPanel(j,Student.studentListAudio.get(j)));
						}
						audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
						
						audioPanel.setBackground(Color.WHITE);
						
						if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else 
							waitingButton.setText("Waiting : 0");
						
						audioPanel.revalidate();
						studentPanel.revalidate();
					}
					else if(tabbedPane.getSelectedIndex()==1)// for text
					{
						textPanel.removeAll();
						studentMsg.removeAll();
						studentMsg=new JPanel();
						if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListText.size()<=5)
							studentMsg.setLayout(new GridLayout(5,1,10,5));
						else
							studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
						
						studentMsg.setBackground(Color.white);
					
						for(int j=0;j<Student.studentListText.size();j++)
						{
							Student currentStudent=Student.studentListText.get(j);
							if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
								studentMsg.add(createStudentTextMsgPanel(j,Student.studentListText.get(j)));
						}
						textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);
						
						textPanel.setBackground(Color.WHITE);
						
						if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else 
							waitingButton.setText("Waiting : 0");
						
						textPanel.revalidate();
						studentMsg.revalidate();
					}

					
				}
				///////////////////////////////////////////
				/*
				* doing extras for the search functionality
				* finishes here
				*/
				///////////////////////////////////////////

				
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
					studentMsg.setLayout(new GridLayout(5,1,10,5));
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


				///////////////////////////////////////////
				/*
				 * doing extras for the search functionality
				 */
				///////////////////////////////////////////
				if(searchField.getText().equals(""))
				{
					ServerFrame.refreshFrame();
					return;
				}
				else
				{
					if(tabbedPane.getSelectedIndex()==0)// for audio
					{
						audioPanel.removeAll();
						studentPanel.removeAll();
						studentPanel=new JPanel();
						if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
							studentPanel.setLayout(new GridLayout(5,1,10,5));
						else
							studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
						
						studentPanel.setBackground(Color.white);
					
						for(int j=0;j<Student.studentListAudio.size();j++)
						{
							Student currentStudent=Student.studentListAudio.get(j);
							if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
								studentPanel.add(createStudentPanel(j,Student.studentListAudio.get(j)));
						}
						audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
						
						audioPanel.setBackground(Color.WHITE);
						
						if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else 
							waitingButton.setText("Waiting : 0");
						
						audioPanel.revalidate();
						studentPanel.revalidate();
					}
					else if(tabbedPane.getSelectedIndex()==1)// for text
					{
						textPanel.removeAll();
						studentMsg.removeAll();
						studentMsg=new JPanel();
						if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListText.size()<=5)
							studentMsg.setLayout(new GridLayout(5,1,10,5));
						else
							studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
						
						studentMsg.setBackground(Color.white);
					
						for(int j=0;j<Student.studentListText.size();j++)
						{
							Student currentStudent=Student.studentListText.get(j);
							if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
								studentMsg.add(createStudentTextMsgPanel(j,Student.studentListText.get(j)));
						}
						textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);
						
						textPanel.setBackground(Color.WHITE);
						
						if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else 
							waitingButton.setText("Waiting : 0");
						
						textPanel.revalidate();
						studentMsg.revalidate();
					}

					
				}
				///////////////////////////////////////////
				/*
				* doing extras for the search functionality
				* finishes here
				*/
				///////////////////////////////////////////

				
				return;
			
			}
			if(ae.getSource()==deleteButtonText.get(i))
			{
				Student.studentListText.remove(i);
				
				textPanel.removeAll();
				studentMsg.removeAll();
				studentMsg=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListText.size()<=5)
					studentMsg.setLayout(new GridLayout(5,1,10,5));
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
				
				


				///////////////////////////////////////////
				/*
				 * doing extras for the search functionality
				 */
				///////////////////////////////////////////
				if(searchField.getText().equals(""))
				{
					ServerFrame.refreshFrame();
					return;
				}
				else
				{
					if(tabbedPane.getSelectedIndex()==0)// for audio
					{
						audioPanel.removeAll();
						studentPanel.removeAll();
						studentPanel=new JPanel();
						if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
							studentPanel.setLayout(new GridLayout(5,1,10,5));
						else
							studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
						
						studentPanel.setBackground(Color.white);
					
						for(int j=0;j<Student.studentListAudio.size();j++)
						{
							Student currentStudent=Student.studentListAudio.get(j);
							if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
								studentPanel.add(createStudentPanel(j,Student.studentListAudio.get(j)));
						}
						audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
						
						audioPanel.setBackground(Color.WHITE);
						
						if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else 
							waitingButton.setText("Waiting : 0");
						
						audioPanel.revalidate();
						studentPanel.revalidate();
					}
					else if(tabbedPane.getSelectedIndex()==1)// for text
					{
						textPanel.removeAll();
						studentMsg.removeAll();
						studentMsg=new JPanel();
						if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListText.size()<=5)
							studentMsg.setLayout(new GridLayout(5,1,10,5));
						else
							studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
						
						studentMsg.setBackground(Color.white);
					
						for(int j=0;j<Student.studentListText.size();j++)
						{
							Student currentStudent=Student.studentListText.get(j);
							if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
								studentMsg.add(createStudentTextMsgPanel(j,Student.studentListText.get(j)));
						}
						textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);
						
						textPanel.setBackground(Color.WHITE);
						
						if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
							waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
						else 
							waitingButton.setText("Waiting : 0");
						
						textPanel.revalidate();
						studentMsg.revalidate();
					}

					
				}
				///////////////////////////////////////////
				/*
				* doing extras for the search functionality
				* finishes here
				*/
				///////////////////////////////////////////

				
				return;
			}
			
		}

		///////////////////////////////////////////
		/*
		 * doing extras for the search functionality
		 */
		///////////////////////////////////////////
		if(searchField.getText().equals(""))
		{
			ServerFrame.refreshFrame();
			return;
		}
		else
		{
			if(tabbedPane.getSelectedIndex()==0)// for audio
			{
				audioPanel.removeAll();
				studentPanel.removeAll();
				studentPanel=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
					studentPanel.setLayout(new GridLayout(5,1,10,5));
				else
					studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
				
				studentPanel.setBackground(Color.white);
			
				for(int j=0;j<Student.studentListAudio.size();j++)
				{
					Student currentStudent=Student.studentListAudio.get(j);
					if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
						studentPanel.add(createStudentPanel(j,Student.studentListAudio.get(j)));
				}
				audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
				
				audioPanel.setBackground(Color.WHITE);
				
				if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else 
					waitingButton.setText("Waiting : 0");
				
				audioPanel.revalidate();
				studentPanel.revalidate();
			}
			else if(tabbedPane.getSelectedIndex()==1)// for text
			{
				textPanel.removeAll();
				studentMsg.removeAll();
				studentMsg=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListText.size()<=5)
					studentMsg.setLayout(new GridLayout(5,1,10,5));
				else
					studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
				
				studentMsg.setBackground(Color.white);
			
				for(int j=0;j<Student.studentListText.size();j++)
				{
					Student currentStudent=Student.studentListText.get(j);
					if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
						studentMsg.add(createStudentTextMsgPanel(j,Student.studentListText.get(j)));
				}
				textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);
				
				textPanel.setBackground(Color.WHITE);
				
				if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else 
					waitingButton.setText("Waiting : 0");
				
				textPanel.revalidate();
				studentMsg.revalidate();
			}

			
		}
		///////////////////////////////////////////
		/*
		* doing extras for the search functionality
		* finishes here
		*/
		///////////////////////////////////////////

		
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
		((JPanel)(e.getSource())).setBorder(new CompoundBorder(new LineBorder(new Color(200,200,200), 2), new EmptyBorder(0, 0, 0, 0)));
		//setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		if(e.getSource()==ipPanel)
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
			return;
		}
		
	
	}
    
	@Override
	public void mouseExited(MouseEvent e) {
		((JPanel)(e.getSource())).setBorder(new EmptyBorder(0, 0, 0, 0));
		//setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		if(e.getSource()==ipPanel)
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			return;
		}// TODO Auto-generated method stub
		//finalPanel.setBorder(new MatteBorder(0, 15, 0, 0, Color.WHITE));
		
		((JPanel)(e.getSource())).setBorder(new CompoundBorder(new LineBorder(Color.WHITE, 2), new EmptyBorder(0, 0, 0, 0)));
		//((JPanel)(e.getSource())).setBorder(new MatteBorder(0, 15, 0, 0, Color.WHITE));
		//setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==ipPanel)
			new FocusedIpPanel(this).setVisible(true);
		
	}
	
	public void updateAudioPanel()
	{
		System.out.println("updation of audio panel required : ");
		audioPanel.removeAll();
		studentPanel.removeAll();
		studentPanel=new JPanel();
		if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
			studentPanel.setLayout(new GridLayout(5,1,10,5));
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
			studentMsg.setLayout(new GridLayout(5,1,10,5));
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

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent ae) 
	{
		if(ae.getSource()==searchField)
		{
			if(searchField.getText().equals(""))
			{
				ServerFrame.refreshFrame();
				return;
			}
			if(tabbedPane.getSelectedIndex()==0)// for audio
			{
				audioPanel.removeAll();
				studentPanel.removeAll();
				studentPanel=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
					studentPanel.setLayout(new GridLayout(5,1,10,5));
				else
					studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
				
				studentPanel.setBackground(Color.white);
			
				for(int j=0;j<Student.studentListAudio.size();j++)
				{
					Student currentStudent=Student.studentListAudio.get(j);
					if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
						studentPanel.add(createStudentPanel(j,Student.studentListAudio.get(j)));
				}
				audioPanel.add(new JScrollPane(studentPanel),BorderLayout.CENTER);
				
				audioPanel.setBackground(Color.WHITE);
				
				if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else 
					waitingButton.setText("Waiting : 0");
				
				audioPanel.revalidate();
				studentPanel.revalidate();
			}
			else if(tabbedPane.getSelectedIndex()==1)// for text
			{
				textPanel.removeAll();
				studentMsg.removeAll();
				studentMsg=new JPanel();
				if(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListText.size()<=5)
					studentMsg.setLayout(new GridLayout(5,1,10,5));
				else
					studentMsg.setLayout(new BoxLayout(studentMsg, BoxLayout.Y_AXIS));
				
				studentMsg.setBackground(Color.white);
			
				for(int j=0;j<Student.studentListText.size();j++)
				{
					Student currentStudent=Student.studentListText.get(j);
					if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
						studentMsg.add(createStudentTextMsgPanel(j,Student.studentListText.get(j)));
				}
				
				textPanel.add(new JScrollPane(studentMsg),BorderLayout.CENTER);
				
				textPanel.setBackground(Color.WHITE);
				
				if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else 
					waitingButton.setText("Waiting : 0");
				
				textPanel.revalidate();
				studentMsg.revalidate();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}