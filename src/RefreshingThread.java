/*
 * this thread is used to automatically refresh the audio and video panel
 * 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;


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
			//sf.audioPanel.repaint();
			//sf.textPanel.repaint();
				//SwingUtilities.updateComponentTreeUI(sf.audioPanel);
				//SwingUtilities.updateComponentTreeUI(sf.textPanel);
				
		//System.out.println("hello kavi refreshing the serverFrame...");
				//studentPanel.setVisible(false);
		sf.audioPanel.removeAll();
		sf.studentPanel.removeAll();
		sf.studentPanel=new JPanel();
				//studentPanel.setPreferredSize(new Dimension(audioPanel.getWidth(),audioPanel.getHeight()));
				//studentPanel.setBorder(new CompoundBorder(new LineBorder(Color.MAGENTA, 2), new EmptyBorder(0, 0, 0, 0)));
		sf.studentPanel.setBackground(Color.WHITE);
				
		if(Integer.parseInt(sf.studentNumberComboBox.getSelectedItem().toString())<5)
			sf.studentPanel.setLayout(new GridLayout(5,1,10,10));
		else
					//studentPanel.setLayout(new GridLayout(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()),1,10,10));
				
			sf.studentPanel.setLayout(new BoxLayout(sf.studentPanel, BoxLayout.Y_AXIS));
		for(int i=0;i<Integer.parseInt(sf.studentNumberComboBox.getSelectedItem().toString()) && i<Student.studentListAudio.size();i++)
		{
			//System.out.println("hello Lavi");
			sf.studentPanel.add(sf.createStudentPanel(i,Student.studentListAudio.get(i)));
		}
				//studentPanel.setVisible(true);
				//audioPanel.add(studentPanel,BorderLayout.CENTER);
		sf.audioPanel.add(new JScrollPane(sf.studentPanel),BorderLayout.CENTER);
				
		sf.audioPanel.setBackground(Color.WHITE);
				
				////////////////////////////////////////////////////////////////
		sf.textPanel.removeAll();
		sf.studentMsg.removeAll();
				//studentMsg.setVisible(false);
		sf.studentMsg=new JPanel();
		sf.studentMsg.setBackground(Color.WHITE);
		if(Integer.parseInt(sf.studentNumberComboBox.getSelectedItem().toString())<5)
			sf.studentMsg.setLayout(new GridLayout(5,1,10,10));
		else
					//studentPanel.setLayout(new GridLayout(Integer.parseInt(studentNumberComboBox.getSelectedItem().toString()),1,10,10));
				
			sf.studentMsg.setLayout(new BoxLayout(sf.studentMsg, BoxLayout.Y_AXIS));
			
		for(int i=0;i<Integer.parseInt(sf.studentNumberComboBox.getSelectedItem().toString()) && i<Student.studentListText.size();i++)
		{
			sf.studentMsg.add(sf.createStudentTextMsgPanel(i,Student.studentListText.get(i)));
		}
		sf.textPanel.add(new JScrollPane(sf.studentMsg),BorderLayout.CENTER);
				
		sf.audioPanel.revalidate();
		sf.textPanel.revalidate();
		//System.out.println("finishing the refreshing therad...");
	}
}