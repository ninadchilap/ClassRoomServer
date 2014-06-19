import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class NotificationToSpeak implements Runnable
{
	DataOutputStream dos;
	 Socket client_speak;
	 String ip_client;
	NotificationToSpeak(String ip)
	{
		this.ip_client=ip;
		Thread thread=new Thread(this);
        thread.start();
	}
	public void run() {
		// TODO Auto-generated method stub

		try 
		{

			System.out.println("hhhhhhhhhhhhhhhhhhhh"+ip_client);
			char ip1[]=ip_client.toCharArray();
	        if(ip1[0]=='/')
	        	ip_client=new String(ip1,1,ip_client.length()-1);
	        
			System.out.println("hhhhhhhhhhhhhhhhhhhh"+ip_client);
			client_speak = new Socket(ip_client, 5570);
			DataOutputStream dos=new DataOutputStream(client_speak.getOutputStream());
			dos.writeUTF("start_speaking");

			System.out.println("the client is now set to speaking mode...******************************************************");
			
			}
			catch (Exception e) {
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
	
	