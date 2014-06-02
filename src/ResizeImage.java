
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class ResizeImage extends JFrame{
ImageIcon icon;

String path;
public ResizeImage(String path){
this.path=path;
}
public JPanel getResizeImage(){
icon = new ImageIcon(path);

JPanel panel = new JPanel(){
public void paintComponent(Graphics g) {

Dimension d = getSize();
g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
}
};

return panel;
}
/*
public static void main(String [] args){

ResizeImage frame = new ResizeImage();

JPanel panel=frame.getResizeImage();

frame.add(panel);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setSize(300, 300);
frame.setVisible(true);
}*/
} 