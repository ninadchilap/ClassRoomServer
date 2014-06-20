import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class NotifyAllClients implements Runnable
{
	DataOutputStream dos;
	 static Socket client_speak;
	 String type,ip;
	 static Thread th;
	 NotifyAllClients(String ip,String type)
	{
		this.ip=ip;
		this.type=type;
		th=new Thread(this);
        th.start();
	}
	public void run() {
		// TODO Auto-generated method stub

		try {
			if("multi".equals(type))
           	{
				for(int i=0;i<Student.studentListAudio.size();i++)
				{
					client_speak = new Socket(Student.studentListAudio.get(i).ip, 5570);
					DataOutputStream dos=new DataOutputStream(client_speak.getOutputStream());
					dos.writeUTF((i+1)+"");
	
				}
           	}
			else if("single".equals(type))
			{
				
				client_speak = new Socket(ip, 5570);
				
				DataOutputStream dos=new DataOutputStream(client_speak.getOutputStream());
				dos.writeUTF(Student.studentListAudio.size()+"");
				System.out.println("//////////////////////////////////////////////////////////////");
			}
			else if("single_to_kick".equals(type))
			{
				
				client_speak = new Socket(ip, 5570);
				
				DataOutputStream dos=new DataOutputStream(client_speak.getOutputStream());
				dos.writeUTF("kick_you");
				System.out.println("1111111111122222222222222333333333333344444444444455555555555555");
			}
			
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();

		}
		finally {
			if (client_speak != null) {
				try {
						// close socket connection after using it
					client_speak.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}// End run
}
