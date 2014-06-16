import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


public class AudioThread implements Runnable
{
	static int i=0;
	static AudioInputStream ais;
	static AudioFormat audioFormat;
	static int port = Server.serverPortAudio;
	static int sampleRate = 44100; //44100 for aakash
	static SourceDataLine sourceDataLine;
	static boolean streaming = true;
	private static InetAddress currentSpeakerAddress;
	public static final String PERMISSION_TEXT = "You may start talking";
	public static final String STOP_TEXT = "You may stop talking";
	

	AudioThread()
	{
		Thread t=new Thread(this);
		t.start();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub

		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] receiveData = new byte[8192];

	
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		ByteArrayInputStream baiss = new ByteArrayInputStream(receivePacket.getData());
		//System.out.println(" baiss Server packet: " + baiss.read());
		while (streaming == true) 
		{
			System.out.println(" streaming is: " + true);
			try {
				serverSocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(" baiss Server packet: " + baiss.read());
			String requestText = new String(receivePacket.getData());
			//System.out.println("in streaming Server requestText: " + requestText);
			InetAddress requestAddress = receivePacket.getAddress();
			System.out.println("requestAddress " + receivePacket.getAddress().toString());
			currentSpeakerAddress=requestAddress;
			notifyToTalk(requestAddress);
			
			if (requestText.contains("Raise Hand")) 
			{
				
					System.out.println(requestAddress.getHostAddress() + " is online");
					notifyToTalk(requestAddress);
					audioFormat = new AudioFormat(sampleRate, 16, 1, true, false);
				       
					try {
						sourceDataLine = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, audioFormat));
					} catch (LineUnavailableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						sourceDataLine.open(audioFormat);
					} catch (LineUnavailableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					sourceDataLine.start();
			} else if (requestText.contains("Withdraw")) {
				
				
				System.out.println("WITHDRAW RECEIVED" );
				  //notifyToStop(requestAddress);
					
				sourceDataLine.drain();
				sourceDataLine.close();		
				 
			} else if  (currentSpeakerAddress.equals(requestAddress))  {
				//FloatControl volumeControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
		        //volumeControl.setValue(2.056f);
				ais = new AudioInputStream(baiss, audioFormat, receivePacket.getLength());
				toSpeaker(receivePacket.getData());
				System.out.println(i++ + " " + receivePacket.getLength());
			}
		}
	}

	public static void toSpeaker(byte soundbytes[]) {
		try {
			sourceDataLine.write(soundbytes, 0, soundbytes.length);
		} catch (Exception e) {
			System.out.println("Not working in speakers...");
		}
	}

	

	

	private static void notifyToTalk(final InetAddress nextSpeaker) {
		final byte[] request = (PERMISSION_TEXT).getBytes();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new DatagramSocket().send(new DatagramPacket(request, request.length, nextSpeaker, port));
				//	System.out.println("Permission given to " + nextSpeaker.getHostAddress());
				}  catch (Exception e) {
				}
			}
		}).start();
	}
}