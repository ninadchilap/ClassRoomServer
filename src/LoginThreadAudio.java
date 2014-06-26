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
    static Thread th;
    boolean makeEntry;
    LoginThreadAudio(Server server)
    {
    	System.out.println("Thread started");
        this.server=server;
        client=server.clientAudio;
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
            			int complete=0;
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
    				   ///////////////////////SEND IMAGE with flag////////////////
    				    String flag_image=dis.readUTF();
    				    System.out.println("ankit ankit ankit nakit111111222222222222!!!!!!!!!"+flag_image);
    				    /*
    				     * 
    				     */
    				    makeEntry=CountMacId.MacIdPrimaryKeyAudio(macid);
    				    
    				    if(flag_image.equals("send_image"))
    				    {
		    				    String macid2=macid;
		     				     macid2= macid2.replace(":","");	
		     				    File outFile=new File("Images/"+macid2+".jpg");
		     				    System.out.println("111111111222222222"+macid2);
		     				    receiveFile(outFile);
		     				
		     				    System.out.println("file path "+outFile.getAbsolutePath());
		     				   ip=client.getInetAddress()+"";
		   				    
			   				    char ip1[]=ip.toCharArray();
			   			        if(ip1[0]=='/')
			   			        	ip=new String(ip1,1,ip.length()-1);
			   			        
			   			        if(makeEntry)
			   			        {
			   			        	new Student(username,roll,macid,ip,outFile.getAbsolutePath(),doubtSubject,"","audio");
			   			        	new	ImageMacid(macid,outFile.getAbsolutePath());
			   			        }
			   			        complete=1;
    				    }
    				    else if(flag_image.equals("not_send_image"))
    				    {
    				    	
    				    	for(int i=0;i<ImageMacid.imagemacid.size()&&complete==0;i++)
    				    	{
    				    		if(ImageMacid.imagemacid.get(i).macid.equals(macid))
    				    		{
    				    			ip=client.getInetAddress()+"";
    				    			char ip1[]=ip.toCharArray();
    			   			        if(ip1[0]=='/')
    			   			        	ip=new String(ip1,1,ip.length()-1);
    			   			        if(makeEntry)
    				   			        new Student(username,roll,macid,ip,ImageMacid.imagemacid.get(i).pic,doubtSubject,"","audio");
    				    			complete=1;
    				    		}
    				    	}
    				    	if(complete==0)
    				    	{
        				    	dos.writeUTF("not_done");
        				    	String macid2=macid;
		     				    macid2= macid2.replace(":","");	
		     				    File outFile=new File("Images/"+macid2+".jpg");
		     				    System.out.println("111111111222222222"+macid2);
		     				    receiveFile(outFile);
		     				
		     				    System.out.println("file path "+outFile.getAbsolutePath());
		     				    ip=client.getInetAddress()+"";
		     				    
			   				    char ip1[]=ip.toCharArray();
			   			        if(ip1[0]=='/')
			   			        	ip=new String(ip1,1,ip.length()-1);
			   			        
			   			     if(makeEntry)
			   			     {
			   			        new Student(username,roll,macid,ip,outFile.getAbsolutePath(),doubtSubject,"","audio");
			   			        new	ImageMacid(macid,outFile.getAbsolutePath());
			   			     }  
			   			     complete=1;
			   			     System.out.println("not done audio!!!!!!!!!!!!!!!!!!!");
    				    	}
    				        else
    				        	dos.writeUTF("ok_done");
    				    }
    				   
    				    
    				    
    				    
    				    //////////////////////////////////////////////////////
    				   if(complete==1 && makeEntry)
    				   {
		    				    new NotifyAllClients(ip,"single");
		    				    //dos.writeUTF("received");
		    				    String disconnectAudioDoubt=dis.readUTF();
		    				    if(disconnectAudioDoubt.equals("kick_me_out_speaking")) // when the client that is speaking requests kick_me_out
		    				    {
		    				    	
		    				    	System.out.println("something is gooooooooooooooooooooing wrong");
		    				    	if(ServerFrame.gd!=null)
		    				    		ServerFrame.gd.dispose();
		    				    }
		    				    else// if(disconnectAudioDoubt.equals("kick_me_out_waiting")) // when the client that is waiting in the queue requests kick_me_out
		    				    {
		    				    	/*
		    				    	 * here you dont need to close the 
		    				    	 * general dialog box corresponding to the 
		    				    	 * action kick_me_out
		    				    	 */
		    						System.out.println("0)))))))))))))))))))))))))))))))))))))))))))))))))0");
		    						
		    				    	String currentMacId=dis.readUTF();
		    				    	for(int i=0;i<Student.studentListAudio.size();i++)
		    				    	{
		    							if(Student.studentListAudio.get(i).macAddress.equals(currentMacId))
		    							{
		    								Student.studentListAudio.remove(i);
		    								
		    								ServerFrame.refreshFrame();
		    							}
		    				    	}
		    				    	new NotifyAllClients("", "multi");
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