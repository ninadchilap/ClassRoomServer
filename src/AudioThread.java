import java.io.IOException;
import java.net.ServerSocket;


public class AudioThread implements Runnable
{
	Server server;
	AudioThread(Server server)
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
			Server.serverSocketAudio=new ServerSocket(Server.serverPortAudio);
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