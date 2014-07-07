package beans;

import java.sql.ResultSet;
import java.util.ArrayList;

import database.select;
import database.update;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Information
{
	public String Prof;
	public String Dept;
	public String Lec;
	public static int noOfProf=0;
	public static int lecCount=0;
	public String emailid;
	public String password;
        public String lecPath;
	public ArrayList<String> user=new ArrayList<String>();
        public ArrayList<String> doubt=new ArrayList<String>();
      public ArrayList<String> text=new ArrayList<String>();
      
	public static boolean insertDepartment(Information a){
                int rs=0;
        
            try {
                rs=update.execute("Insert into department(Prof,Dept) values(trim('"+a.Prof+"'),trim('"+a.Dept+"'));");
                
                
               
            } catch (Exception ex) {
                Logger.getLogger(Information.class.getName()).log(Level.SEVERE, null, ex);
            }

            return rs==1;
        }
        
        public static int getSessionId()throws Exception
	{
            int SessionId=0;
		String query="SELECT MAX(SessionId) AS \"Session\"    FROM `lec`;";
		ResultSet rs= select.execute(query);
		
		if(rs.next())
		{	
                SessionId=rs.getInt("Session");
                }
		
		
		return  SessionId;
	}
	public static boolean insertLec(Information a){
                int rs=0;
        
            try {
                rs=update.execute("INSERT INTO `lec`( `Prof`, `Lecture`, `lecpath`) VALUES (trim('"+a.Prof+"'),trim('"+a.Lec+"'),trim('"+a.lecPath+"'));");
                
                
               
            } catch (Exception ex) {
                Logger.getLogger(Information.class.getName()).log(Level.SEVERE, null, ex);
            }

            return rs==1;
        }
        
	public static void insertQA(Information a){
                int rs=0;
        for(int i=0; i<(a.user.size());  i++){
           
           try {
               System.out.println(a.user.get(i)+"\t "+a.text.get(i)+"\t"+getSessionId()+"");
         
               rs=update.execute("INSERT INTO `qa`(`que`, `student`, `SessionId`) VALUES ('"+a.text.get(i)+"','"+a.user.get(i)+"',"+getSessionId()+");");
                
                
               
            } catch (Exception ex) {
                Logger.getLogger(Information.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }
        
        
}
