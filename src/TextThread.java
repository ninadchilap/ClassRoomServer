import java.io.IOException;
import java.net.ServerSocket;

public class TextThread implements Runnable
{
	static Thread th;
	Server server;
	TextThread(Server server)
	{
		this.server=server;
		th=new Thread(this);
		th.start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
        {
			 Server.serverSocketText=new ServerSocket(Server.serverPortText);
			 while(true)
			 {
				 Server.clientText=Server.serverSocketText.accept();
				 new LoginThreadText(server);
				 
			 }
        } 
		 catch (IOException e) 
		 {
			 e.printStackTrace();
		 }
	}
	
}
