import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class WaitingDialogForText extends Dialog implements KeyListener,DocumentListener,ActionListener
{
	LinkedList<JButton> tempAddButtonList,tempDeleteButtonList;
	LinkedList<Student>tempStudentLinkedList;
	JTextField searchField;
	JPanel parentPanel,searchPanel,scrollPanel;
	ServerFrame sf;
	WaitingDialogForText(ServerFrame sf)
	{
		super(sf,true);
		this.sf=sf;
		this.setSize(500,500);
		this.setLocation(100,100);
		//this.setVisible(true);
		this.setTitle("Search Dialog for Text");
		
		initializeGraphicComponents();
		parentPanel.setLayout(new BorderLayout(10,10));
		scrollPanel.add(new JScrollPane(searchPanel),BorderLayout.CENTER);
		parentPanel.add(scrollPanel,BorderLayout.CENTER);
		parentPanel.add(searchField,BorderLayout.NORTH);
		
		this.add(parentPanel);
		addWindowListener(new DlgAdapter(this));
		searchField.addKeyListener(this);
		//searchField.getDocument().addDocumentListener(this);
	}
	
	private void initializeGraphicComponents()
	{
		searchField=new JTextField();
		parentPanel=new JPanel();
		searchPanel=new JPanel();
		searchPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		scrollPanel=new JPanel();
		scrollPanel.setLayout(new GridLayout(1,1,10,10));
	}
	
	@Override
	public void keyPressed(KeyEvent ke)
	{
		/*
		System.out.println(searchField.getText());
		LinkedList tempStudentLinkedList = new LinkedList();
		for(int i=0;i<Student.studentListText.size();i++)
		{
			Student currentStudent=Student.studentListText.get(i);
			if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
			{
				System.out.println(currentStudent.studentName+"  -->  "+currentStudent.studentName.indexOf(searchField.getText()));
				tempStudentLinkedList.add(currentStudent);
			}	
		}
		addStudentInSearchPanel(tempStudentLinkedList);
		System.out.println("size of tempStudentLinkedList = "+tempStudentLinkedList.size());
		
		searchField.requestFocus();
		
		*/
	}
	
	@Override
	public void keyReleased(KeyEvent ke) 
	{
		/*
		System.out.println(searchField.getText());
		LinkedList tempStudentLinkedList = new LinkedList();
		for(int i=0;i<Student.studentListText.size();i++)
		{
			Student currentStudent=Student.studentListText.get(i);
			if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
			{
				System.out.println(currentStudent.studentName+"  -->  "+currentStudent.studentName.indexOf(searchField.getText()));
				tempStudentLinkedList.add(currentStudent);
			}	
		}
		addStudentInSearchPanel(tempStudentLinkedList);
		System.out.println("size of tempStudentLinkedList = "+tempStudentLinkedList.size());
		
		searchField.requestFocus();
		*/
		
		System.out.println(searchField.getText());
		tempStudentLinkedList = new LinkedList();
		tempAddButtonList=new LinkedList();
		tempDeleteButtonList=new LinkedList();
		for(int i=0;i<Student.studentListText.size();i++)
		{
			Student currentStudent=Student.studentListText.get(i);
			if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
			{
				System.out.println(currentStudent.studentName+"  -->  "+currentStudent.studentName.indexOf(searchField.getText()));
				
				JButton tickButton=new JButton("\u2714");
				JButton crossButton=new JButton("X");
				tickButton.addActionListener(this);
				crossButton.addActionListener(this);
				//ServerFrame.methodToAddActionListener(tickButton);
				//ServerFrame.methodToAddActionListener(crossButton);
				
				tempAddButtonList.add(tickButton);
				tempDeleteButtonList.add(crossButton);
				
				tempStudentLinkedList.add(currentStudent);
			}
		}
		addStudentInSearchPanel(tempStudentLinkedList);
		System.out.println("size of tempStudentLinkedList = "+tempStudentLinkedList.size());
		
		searchField.requestFocus();
	
	}
	@Override
	public void keyTyped(KeyEvent ke) 
	{
		
	}
	
	public void addStudentInSearchPanel(LinkedList tempStudentLinkedList)
	{
		parentPanel.removeAll();
		
		scrollPanel.removeAll();
		searchPanel.removeAll();
		searchPanel=new JPanel();
		
		//scrollPanel=new JPanel();
		scrollPanel.setLayout(new GridLayout(1,1,10,10));
		
		searchPanel.setBackground(Color.WHITE);
		searchPanel.setBorder(new CompoundBorder(new LineBorder(Color.YELLOW, 2), new EmptyBorder(0, 0, 0, 0)));
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
		
		for(int i=0;i<tempStudentLinkedList.size();i++)
		{
			System.out.println("hello lavish kothari -- > i = "+i);
			searchPanel.add(createStudentPanel(i,(Student)(tempStudentLinkedList.get(i))));
		}
		//searchPanel.revalidate();
		scrollPanel.add(new JScrollPane(searchPanel));
		parentPanel.add(searchField,BorderLayout.NORTH);
		parentPanel.add(scrollPanel,BorderLayout.CENTER);
		parentPanel.setBorder(new CompoundBorder(new LineBorder(Color.YELLOW, 2), new EmptyBorder(0, 0, 0, 0)));
		parentPanel.revalidate();
		searchPanel.revalidate();
		scrollPanel.revalidate();
	}
	
	public JPanel createStudentPanel(int i,Student student)
	{
		int studentPanelHeight=(int)(this.getHeight()*0.11);
		int studentPanelWidth=(int)(this.getWidth());
		
		JPanel finalPanel=new JPanel();
		finalPanel.setBackground(Color.WHITE);
		finalPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 7));
		//finalPanel.setPreferredSize(new Dimension((int)(width),(int)(height*0.1)));
		//finalPanel.setPreferredSize(new Dimension(audioPanel.getWidth(),studentPanelHeight));
		//finalPanel.setBorder(new CompoundBorder(new LineBorder(Color.RED, 2), new EmptyBorder(0, 0, 0, 0)));
		
		
		ResizeImage imagea=new ResizeImage(student.pic); //--new
		JPanel imagePanel=imagea.getResizeImage(); // --new
		
		//ImagePanel imagePanel=new ImagePanel(Student.studentListAudio.get(i).pic,(int)(width*0.1),studentPanelHeight);
		imagePanel.setPreferredSize(new Dimension((int)(sf.width*0.1),studentPanelHeight));
		imagePanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		JPanel namePanel=new JPanel();
		namePanel.setPreferredSize(new Dimension((int)(sf.width*0.25),studentPanelHeight));
		namePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		JLabel studentNameLabel=new JLabel(student.studentName);
		studentNameLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		namePanel.add(studentNameLabel);
		namePanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		JPanel doubtPanel=new JPanel();
		doubtPanel.setPreferredSize(new Dimension((int)(sf.width*0.45),studentPanelHeight));
		doubtPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		JLabel doubtSubjectLabel=new JLabel(student.doubtSubject);
		doubtSubjectLabel.setFont(new Font("lucida console",Font.PLAIN,20));
		doubtPanel.add(doubtSubjectLabel);
		doubtPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		JPanel addButtonPanel=new JPanel();
		addButtonPanel.setPreferredSize(new Dimension((int)(sf.width*0.05),studentPanelHeight));
		addButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		addButtonPanel.add(tempAddButtonList.get(i));
		addButtonPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		JPanel deleteButtonPanel=new JPanel();
		deleteButtonPanel.setPreferredSize(new Dimension((int)(sf.width*0.05),studentPanelHeight));
		deleteButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int)(studentPanelHeight*0.3)));
		deleteButtonPanel.add(tempDeleteButtonList.get(i));
		deleteButtonPanel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(0, 0, 0, 0)));
		
		finalPanel.add(imagePanel);
		finalPanel.add(namePanel);
		finalPanel.add(doubtPanel);
		finalPanel.add(addButtonPanel);
		finalPanel.add(deleteButtonPanel);
		
		return finalPanel;
	}

	
	public Insets getInsets()
	{
		return new Insets(30,20,20,20);
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) 
	{
		/*
		// TODO Auto-generated method stub
		System.out.println(searchField.getText());
		LinkedList tempStudentLinkedList = new LinkedList();
		tempAddButtonList=new LinkedList();
		tempDeleteButtonList=new LinkedList();
		for(int i=0;i<Student.studentListText.size();i++)
		{
			Student currentStudent=Student.studentListText.get(i);
			if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
			{
				System.out.println(currentStudent.studentName+"  -->  "+currentStudent.studentName.indexOf(searchField.getText()));
				
				JButton tickButton=new JButton("\u2714");
				JButton crossButton=new JButton("X");
				
				ServerFrame.methodToAddActionListener(tickButton);
				ServerFrame.methodToAddActionListener(crossButton);
				
				tempAddButtonList.add(tickButton);
				tempDeleteButtonList.add(crossButton);
				
				tempStudentLinkedList.add(currentStudent);
			}
		}
		addStudentInSearchPanel(tempStudentLinkedList);
		System.out.println("size of tempStudentLinkedList = "+tempStudentLinkedList.size());
		
		searchField.requestFocus();
		*/
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) 
	{
		/*
		// TODO Auto-generated method stub
		System.out.println(searchField.getText());
		LinkedList tempStudentLinkedList = new LinkedList();
		for(int i=0;i<Student.studentListText.size();i++)
		{
			Student currentStudent=Student.studentListText.get(i);
			if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
			{
				System.out.println(currentStudent.studentName+"  -->  "+currentStudent.studentName.indexOf(searchField.getText()));
				tempStudentLinkedList.add(currentStudent);
			}	
		}
		addStudentInSearchPanel(tempStudentLinkedList);
		System.out.println("size of tempStudentLinkedList = "+tempStudentLinkedList.size());
		
		searchField.requestFocus();
		*/
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) 
	{
		/*
		// TODO Auto-generated method stub
		System.out.println(searchField.getText());
		LinkedList tempStudentLinkedList = new LinkedList();
		for(int i=0;i<Student.studentListText.size();i++)
		{
			Student currentStudent=Student.studentListText.get(i);
			if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
			{
				System.out.println(currentStudent.studentName+"  -->  "+currentStudent.studentName.indexOf(searchField.getText()));
				tempStudentLinkedList.add(currentStudent);
			}	
		}
		addStudentInSearchPanel(tempStudentLinkedList);
		System.out.println("size of tempStudentLinkedList = "+tempStudentLinkedList.size());
		
		searchField.requestFocus();
		*/
	}

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		for(int i=0;i<tempStudentLinkedList.size();i++)
		{
			
			if(ae.getSource()==tempAddButtonList.get(i))
			{
				System.out.println("You Found A button was clicked in the waiting Dialog For Text");
				
				new GeneralDialogBox(sf, tempStudentLinkedList.get(i)).setVisible(true);
				Student.studentListText.remove(tempStudentLinkedList.get(i));
				sf.updateTextPanel();
				
				//tempStudentLinkedList.remove(i);
				
				tempStudentLinkedList = new LinkedList();
				tempAddButtonList=new LinkedList();
				tempDeleteButtonList=new LinkedList();
				for(int j=0;j<Student.studentListText.size();j++)
				{
					Student currentStudent=Student.studentListText.get(j);
					if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
					{
						JButton tickButton=new JButton("\u2714");
						JButton crossButton=new JButton("X");
						tickButton.addActionListener(this);
						crossButton.addActionListener(this);
						//ServerFrame.methodToAddActionListener(tickButton);
						//ServerFrame.methodToAddActionListener(crossButton);
						
						tempAddButtonList.add(tickButton);
						tempDeleteButtonList.add(crossButton);
						
						tempStudentLinkedList.add(currentStudent);
					}
				}
				addStudentInSearchPanel(tempStudentLinkedList);
				System.out.println("size of tempStudentLinkedList = "+tempStudentLinkedList.size());
				
				searchField.requestFocus();
			
			}
			else if(ae.getSource()==tempDeleteButtonList.get(i))
			{
				Student.studentListText.remove(tempStudentLinkedList.get(i));
				sf.updateTextPanel();
				
				//tempStudentLinkedList.remove(i);
				
				tempStudentLinkedList = new LinkedList();
				tempAddButtonList=new LinkedList();
				tempDeleteButtonList=new LinkedList();
				for(int j=0;j<Student.studentListText.size();j++)
				{
					Student currentStudent=Student.studentListText.get(j);
					if(currentStudent.studentName.indexOf(searchField.getText())!=-1 || currentStudent.doubtSubject.indexOf(searchField.getText())!=-1)
					{
						JButton tickButton=new JButton("\u2714");
						JButton crossButton=new JButton("X");
						tickButton.addActionListener(this);
						crossButton.addActionListener(this);
						//ServerFrame.methodToAddActionListener(tickButton);
						//ServerFrame.methodToAddActionListener(crossButton);
						
						tempAddButtonList.add(tickButton);
						tempDeleteButtonList.add(crossButton);
						
						tempStudentLinkedList.add(currentStudent);
					}
				}
				addStudentInSearchPanel(tempStudentLinkedList);
				System.out.println("size of tempStudentLinkedList = "+tempStudentLinkedList.size());
				
				searchField.requestFocus();
			
			}
		}
		
		/*
		// TODO Auto-generated method stub
		for(int i=0;i<Student.studentListText.size();i++)
		{
			if(ae.getSource()==tempAddButtonList.get(i))
			{
				new GeneralDialogBox(sf, tempStudentLinkedList.get(i)).setVisible(true);
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
				if(tabbedPane.getSelectedIndex()==0 && (Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListAudio.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else if(tabbedPane.getSelectedIndex()==1 && (Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()))>=0)
					waitingButton.setText("Waiting : "+(Student.studentListText.size()-Integer.parseInt(studentNumberComboBox.getSelectedItem().toString())));
				else 
					waitingButton.setText("Waiting : 0");
				return;
			}
			
		}
	*/	
	}
}