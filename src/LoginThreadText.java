import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;


class LoginThreadText implements Runnable
{
	DataInputStream dis;
	DataOutputStream dos;
	String username,roll,macid,doubtSubject,doubtText,ip;
    File myFile = new File("s.txt");
    String clientIpAddress,clientSessionID;
    Socket client;
    Server server;
    BufferedImage image;
    static Thread th;
    LoginThreadText(Server server)
    {
    	System.out.println("Thread started");
        this.server=server;
        client=server.clientText;
        th=new Thread(this);
        th.start();
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
    	System.out.println("thread is running");
        {
            try
            {
            	dis=new DataInputStream(client.getInputStream());
               	dos=new DataOutputStream(client.getOutputStream());
              
               	String test;
               	test=dis.readUTF();
               	if("USER".equals(test))
               	{
               		dos.writeUTF("1");
               		clientSessionID=dis.readUTF();
               		if(server.serverSessionId==Integer.parseInt(clientSessionID))
	                {
	                    dos.writeUTF("1");
	                    client.close();
	                }
	               	else
	               	{
	               		dos.writeUTF("0");
	               	}
               	}
            	else
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
    				    doubtText=dis.readUTF();
    				
    				    System.out.println("username="+username);
    				    System.out.println("roll="+roll);
    				    System.out.println("macid="+macid);
    				    System.out.println("doubtSubject="+doubtSubject);
    				    System.out.println("doubtText="+doubtText);
    				    
    				    File outFile=new File("/home/lavish/Server_ClassRoom_Interaction/Server_ClassRoom_Interaction/Images/"+macid+".jpg");
    				    
    				    receiveFile(outFile);
    				
    				    System.out.println(outFile.getAbsolutePath());
    				    new print_in_file(macid,username,roll,doubtSubject,doubtText);
    				    ip=client.getInetAddress()+"";
    				    new Student(username,roll,macid,ip,outFile.getAbsolutePath(),doubtSubject,doubtText,"text");
    				
    				    dos.writeUTF("received");
    				
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