/*
 * this thread is used to automatically refresh the audio and text panel 
 * 
 * as soon as a new student is added, it is necessary for the server to keep the track of the student
 * and for doing the same, we run the refreshing thread to update the view of Professor
 * so that professor can have an updated view each time as soon as a student visits.
 */

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RefreshingThread implements Runnable
{
	ServerFrame sf;
	RefreshingThread(ServerFrame sf)
	{
		this.sf=sf;
		Thread t=new Thread(this);
		t.start();
	}

	@Override
	public void run() 
	{
		/*
		 * refreshing the Audio Panel
		 */
		
		sf.audioPanel.removeAll();
		sf.studentPanel.removeAll();
		sf.studentPanel=new JPanel();
		sf.studentPanel.setBackground(Color.WHITE);
				
		if(Integer.parseInt(sf.studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListAudio.size()<=5)
			sf.studentPanel.setLayout(new GridLayout(5,1,10,10));
		else
			sf.studentPanel.setLayout(new BoxLayout(sf.studentPanel, BoxLayout.Y_AXIS));
		for(int i=0;i<Integer.parseInt(sf.studentNumberComboBox.getSelectedItem().toString()) && i<Student.studentListAudio.size();i++)
		{
			sf.studentPanel.add(sf.createStudentPanel(i,Student.studentListAudio.get(i)));
		}
		sf.audioPanel.add(new JScrollPane(sf.studentPanel),BorderLayout.CENTER);
				
		sf.audioPanel.setBackground(Color.WHITE);
				
		
		/*
		 * refreshing the Text panel
		 */
		
		sf.textPanel.removeAll();
		sf.studentMsg.removeAll();
		sf.studentMsg=new JPanel();
		sf.studentMsg.setBackground(Color.WHITE);
		if(Integer.parseInt(sf.studentNumberComboBox.getSelectedItem().toString())<5 || Student.studentListText.size()<=5)
			sf.studentMsg.setLayout(new GridLayout(5,1,10,10));
		else
			sf.studentMsg.setLayout(new BoxLayout(sf.studentMsg, BoxLayout.Y_AXIS));
			
		for(int i=0;i<Integer.parseInt(sf.studentNumberComboBox.getSelectedItem().toString()) && i<Student.studentListText.size();i++)
		{
			sf.studentMsg.add(sf.createStudentTextMsgPanel(i,Student.studentListText.get(i)));
		}
		sf.textPanel.add(new JScrollPane(sf.studentMsg),BorderLayout.CENTER);
				
		/*
		 * Now revalidating both the audio and the text panels
		 */
		sf.audioPanel.revalidate();
		sf.textPanel.revalidate();
	}
}