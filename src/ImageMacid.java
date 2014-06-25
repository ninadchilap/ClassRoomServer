import java.util.LinkedList;


public class ImageMacid
{
	String pic,macid;
	static LinkedList<ImageMacid> imagemacid=new LinkedList<ImageMacid>(); 
	
	ImageMacid(String macid,String pic)
	{
		this.macid=macid;
		this.pic=pic;
		imagemacid.add(this);
	}
	
	

}
