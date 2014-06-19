import java.io.IOException;
import java.net.ServerSocket;

public class AudioThread1 implements Runnable
{
	Server server;
	AudioThread1(Server server)
	{
		this.server=server;
		Thread t=new Thread(this);
		t.start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
        {
			 Server.serverSocketAudio=new ServerSocket(5566);
			 while(true)
			 {
				 Server.clientAudio=Server.serverSocketAudio.accept();
				 new LoginThreadAudio(server);
				 
			 }
        } 
		 catch (IOException e) 
		 {
			 e.printStackTrace();
		 }
	}
	
}
