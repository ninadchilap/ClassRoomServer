import java.util.LinkedList;

public class CountMacId 
{
	String pic,username,mac_id;
	int count;
	static LinkedList<CountMacId> studentCountMac_id=new LinkedList<CountMacId>(); 
	
	CountMacId(String pic,String username,String mac_id)
		{
			this.pic=pic;
			this.username=username;
			this.mac_id=mac_id;
			int flag=0;
			
			for(int i=0;i<studentCountMac_id.size()&&flag==0;i++)
			{
				if(studentCountMac_id.get(i).mac_id==mac_id)
				{
					flag=1;
					this.count+=1;
				}
			}
			if(flag==0)
			this.count=1;
			studentCountMac_id.add(this);
		}
	public static boolean MacIdPrimaryKeyAudio(String macid)
	{
		for(int i=0;i<Student.studentListAudio.size();i++)
		{
			if(Student.studentListAudio.get(i).macAddress.equals(macid))
			{
				return false;
			}
		}
		return true;
	}

}