import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;


class LoginThreadAudio implements Runnable
{
	DataInputStream dis;
	DataOutputStream dos;
	String username,roll,macid,doubtSubject,doubtText,ip;
    String clientIpAddress,clientSessionID;
    Socket client;
    Server server;
    BufferedImage image;
    LoginThreadAudio(Server server)
    {
    	System.out.println("Thread started");
        this.server=server;
        client=server.clientAudio;
        Thread thread=new Thread(this);
        thread.start();
    }

    public void receiveFile(File file)throws IOException
    {
    	FileOutputStream fileOut=new FileOutputStream(file);
    	byte[]buf=new byte[Short.MAX_VALUE];
    	int byteSent;
    	while((byteSent=dis.readShort())!=-1)
    	{
    		dis.readFully(buf,0,byteSent);
    		fileOut.write(buf,0,byteSent);
    	}
    	fileOut.close();
    }
    
    @Override
    public void run()
    {
    	System.out.println("thread is running^6666666666666666666666666666666666");
        {
            try
            {
            	dis=new DataInputStream(client.getInputStream());
               	dos=new DataOutputStream(client.getOutputStream());
              
               	String test="else";
               //	test=dis.readUTF();
               	{
            	    if("remove".equals(test))
            		{
                    	System.out.println("somethin is worng!!!!!!");
	            		macid=dis.readUTF();
	            		//ip=dis.readUTF();
	            		doubtSubject=dis.readUTF();
	            		doubtText=dis.readUTF();
	            		new Student("","",macid,ip,"",doubtSubject,doubtText,"remove");
	            		dos.writeUTF("received");
	            		client.close();
	            	}
            	    else if("Status".equals(test))
                    {
                    	
                    	macid=dis.readUTF();
                    	for(int i=0;i<Student.studentListAudio.size();i++)
		                 {
		                    	if(Student.studentListAudio.get(i).macAddress.equals(macid))
		                    	{
		                    		dos.writeUTF(Integer.toString(i+1));
		                    	}
		                 }
		        	
                    }
            		else // this is the case when student is requesting for the text doubt
            		{
            			username=dis.readUTF();
    				    roll=dis.readUTF();
    				    macid=dis.readUTF();
    				    doubtSubject=dis.readUTF();
    				    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaasssssssssssssssssssssssssssssss");
    				    System.out.println("username="+username);
    				    System.out.println("roll="+roll);
    				    System.out.println("macid="+macid);
    				    System.out.println("doubtSubject="+doubtSubject);
    				    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaasssssssssssssssssssssssssssssss11111111");
    				   // System.out.println("doubtText="+doubtText);
    				    
    				    File outFile=new File("/home/lavish/Server_ClassRoom_Interaction/Server_ClassRoom_Interaction/Images/"+macid+".jpg");
    				    
    				  //  receiveFile(outFile);
    				
    				    //System.out.println(outFile.getAbsolutePath());
    				    ip=client.getInetAddress()+"";
    				    
    				    char ip1[]=ip.toCharArray();
    			        if(ip1[0]=='/')
    			        	ip=new String(ip1,1,ip.length()-1);
    				    
    				    
    				    
    				    //new Student(username,roll,macid,ip,outFile.getAbsolutePath(),doubtSubject,"","audio");
    				    new Student(username,roll,macid,ip,"/home/lavish/Server_ClassRoom_Interaction/Server_ClassRoom_Interaction/Images/a.jpg",doubtSubject,"","audio");

    				    new NotifyAllClients(ip,"single");
    				    //dos.writeUTF("received");
    				    String disconnectAudioDoubt=dis.readUTF();
    				    if(disconnectAudioDoubt.equals("kick_me_out"))
    				    {
    				    	String currentMacId=dis.readUTF();
    				    	for(int i=0;i<Student.studentListAudio.size();i++)
    						{
    							
    							if(Student.studentListAudio.get(i).macAddress.equals(currentMacId) )
    							{
    								Student.studentListAudio.remove(i);
    								ServerFrame.refreshFrame();
    							}
    						}
    				    }
    				    client.close();
    				}
            		               
            	}
            }
            catch(Exception e)
            {
            	e.printStackTrace();
            	System.out.println(e.getMessage());
            }
            finally
            {
            	System.out.println("client ended");
                
            	try 
            	{
				   client.close();
            	} catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
            	}
                    
            }
        }
    }
}