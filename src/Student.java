/*
 * this class will store the student's information  
 * 
 * Each student will have the following properties : 
 * 		studentName 	- The name of the student
 * 		macAddress 		- The MAC address of the tablet the student is using.
 * 		pic 			- the relative address of the pic of student
 * 		textMessage  	- the detailed doubt of the student
 * 		doubtType 		- type of doubt - either "audio" or "text"
 */
import java.util.LinkedList;
import javax.swing.JButton;

public class Student
{
	// this class stores the student information
	String studentName,rollno,macAddress,pic,doubtSubject,textMessage,doubtType;
	
	/* This is the LinkedList that will store the list of students who are having a Text doubt*/
	static LinkedList<Student> studentListText=new LinkedList<Student>(); 
	
	/* This is the LinkedList that will store the list of students who are having a Audio doubt*/
	static LinkedList<Student> studentListAudio=new LinkedList<Student>();
	
	/* This Linked List will have the list of all the students */
	static LinkedList<Student> studentList=new LinkedList<Student>();

	Student()
	{
		
	}
	Student(String studentName,String rollno,String macAddress,String pic,String doubtSubject,String textMessage,String doubtType)
	{
		this.textMessage=textMessage;
		this.rollno=rollno;
		this.doubtType=doubtType;
		this.doubtSubject=doubtSubject;
		this.studentName=studentName;
		this.macAddress=macAddress;
		this.pic=pic;
		if(doubtType.equals("audio")) // if the doubtType is audio then add the student to studentListAudio
		{
			textMessage="";
			JButton tickButton=new JButton("\u2714");
			JButton crossButton=new JButton("X");

			ServerFrame.methodToAddActionListener(tickButton);
			ServerFrame.methodToAddActionListener(crossButton);

			ServerFrame.addButton.add(tickButton);
			ServerFrame.deleteButton.add(crossButton);
			
			studentListAudio.add(this);
		}
		else if(doubtType.equals("text"))// if the doubtType is audio then add the student to studentListText
		{
			System.out.println("Reaced in Student Object");
			JButton tickButton=new JButton("\u2714");
			JButton crossButton=new JButton("X");
			
			ServerFrame.methodToAddActionListener(tickButton);
			ServerFrame.methodToAddActionListener(crossButton);
			
			ServerFrame.addButtonText.add(tickButton);
			ServerFrame.deleteButtonText.add(crossButton);
			studentListText.add(this);
		}

		else if(doubtType.equals("remove"))
		{
			int flag_remove=0;
			for(int i=0;i<studentListText.size()&&flag_remove==0;i++)
			{
				if(studentListText.get(i).macAddress.equals(macAddress)&&studentListText.get(i).doubtSubject.equals(doubtSubject)&&studentListText.get(i).textMessage.equals(textMessage) )
				{
					flag_remove=1;
					studentListText.remove(i);
				}
			}
		}
		else if(doubtType.equals(""))// if the doubtType is not specified then add it to simple studentList
		{
			studentList.add(this);
		}
		//new RefreshingThread(sf);
		ServerFrame.refreshFrame();
	}

	public static LinkedList<Student> getStudentListText()
	{
		return studentListText;
	}
	public static LinkedList<Student> getStudentListAudio()
	{
		return studentListAudio;
	}
}