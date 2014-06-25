import java.io.FileWriter;
import java.io.IOException;

public class print_in_file 
{
	print_in_file(String macid,String username,String roll,String doubtSubject,String doubtText)
	{
		try
		{
			String filename= ServerFrame.filenameInServerFrame;
			FileWriter fw = new FileWriter(filename,true); //the true will append the new data
			
			doubtText.replace('\n', ' ');
			fw.write(String.format("%s #: %s #: %s\n",username,doubtSubject,doubtText));
			fw.close();
		}
		catch(IOException ioe)
		{
		   System.err.println("IOException: " + ioe.getMessage());
		}
	}
}