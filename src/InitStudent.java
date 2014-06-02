import java.awt.image.BufferedImage; 
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

public class InitStudent 
{
	public BufferedImage bf;
	InitStudent(BufferedImage img)
	{
		bf=deepCopy(img);
		File f = new File("MyFile.png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String studentName=null;
		String macAddress = null; 
		String pic = null;
		int rollNumber;
		
		try 
		{
		    BufferedReader in = new BufferedReader(new FileReader("a.txt"));
		    pic="a.txt";
		    studentName=in.readLine();
		    macAddress=in.readLine();
		    rollNumber=Integer.parseInt(in.readLine()); 
		   
		    in.close();
		} 
		catch (IOException e) 
		{
			System.out.println();
		}
		
		//new Student(studentName,macAddress,pic,"","");
		
	}
	
	static BufferedImage deepCopy(BufferedImage bi) 
	{
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	
}