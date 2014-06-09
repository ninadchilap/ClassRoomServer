import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Formatter;

public class print_in_file {
print_in_file(String macid,String username,String roll,String doubtSubject,String doubtText)
{
try
{
   String filename= "Images/print.txt";
   FileWriter fw = new FileWriter(filename,true); //the true will append the new data
   
  // fw.write("mac_id\t\t\tusername\t\t\troll no.\t\t\tdoubt topic\t\t\tdoubt text\n");//appends the string to the file
 // fw.write(String.format("%-20s%-20s%-20s%-20s%-10s", "MAC_ID", "USERNAME", "ROLL NO.","DOUBT TOPIC","DOUBT TEXT\n\n\n"));
 String ss=doubtText;
  StringBuilder str = new StringBuilder(doubtText);
  int i=str.length()-str.length()/2;
  int flag=0;
  while(i<str.length() && flag==0)
  {
  if(doubtText.charAt(i)!=' ')
  {
i++;  
  }
  else
  {
  flag=1;
  }
  
  }
  if(flag==1)
  str.insert(i, "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
  
  
  fw.write(String.format("%-20s%-20s%-20s%-20s%-10s",macid,username,roll,doubtSubject,str+"\n\n\n"));
 
  fw.close();
}
catch(IOException ioe)
{
   System.err.println("IOException: " + ioe.getMessage());
}
}

}