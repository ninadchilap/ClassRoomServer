import java.awt.*; 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImagePanel extends JPanel
{
    private BufferedImage image;

    public ImagePanel(String path,int imageWidth,int imageHeight) {
       try 
       {
    	   image = ImageIO.read(new File(path));
    
    	   Dimension size = new Dimension(imageWidth, imageHeight);
    	   setPreferredSize(size);
    	   //setMinimumSize(size);
    	   //setMaximumSize(size);
    	   setSize(size);
    	   //setLayout(null);
    	   Image scaledImage = image.getScaledInstance(imageWidth,imageHeight,Image.SCALE_SMOOTH);
       } 
       catch (IOException ex) {
    	   System.out.println("hello lavish kothari");
            // handle exception...
       }
    }
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}