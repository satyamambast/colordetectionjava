package colors;
import java.util.*;

import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.core.CvType;

import org.opencv.core.Size;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.*;   
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import org.opencv.imgproc.Imgproc;

public class HelloCV {
	static int rll=0,gll=0,bll=0,ruu=255,guu=255,buu=255;
    public static void main(String[] args){
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat frame = new Mat();
            
            VideoCapture camera = new VideoCapture(0);
            JFrame jframe = new JFrame("options");
            //JFrame jf2 = new JFrame("Video");

            	    //Inform jframe what to do in the event that you close the program
            	    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	   // jf2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            	    //Create a new JLabel object vidpanel
            	    JLabel vidPanel = new JLabel("");
                    JButton red = new JButton("RED");
                    JButton green = new JButton("GREEN");
                    JButton blue = new JButton("BLUE");
                    JButton Default = new JButton ("DEFAULT");
                    JPanel p=new JPanel();
                    p.setLayout(null);               
                    red.setBounds(100,100,100,100);
                    green.setBounds(250,100,100,100);
                    blue.setBounds(400,100,100,100);
                    Default.setBounds(550,100,100,100);
                    vidPanel.setBounds(100,300,700,700);
                    p.add(red);
                    p.add(green);
                    p.add(blue);
                    p.add(Default);
                    p.add(vidPanel);
                    //assign vidPanel to jframe
                    
                    jframe.getContentPane().add(p,BorderLayout.CENTER);
                    jframe.getContentPane().add(p);
            	   // jf2.setContentPane(vidPanel);

            	    //set frame size
            	    jframe.setSize(2000, 4000);
            	    //jf2.setSize(2000, 4000);

            	    //make jframe visible
            	    jframe.setVisible(true);
            	    //jf2.setVisible(true);
            	    green.addActionListener(new ActionListener()
            	    {
            	                 public void actionPerformed(ActionEvent ae)
            	      {
            	             rll=0;gll=74;bll=46;
            	             ruu=255;guu=134;buu=106;
            	                 }
            	           });
            	    blue.addActionListener(new ActionListener()
            	    {
            	                 public void actionPerformed(ActionEvent ae)
            	      {
            	             rll=0;gll=61;bll=157;
            	             ruu=255;guu=121;buu=217;
//            	                	 bll=bll+1;
//            	                	 System.out.println("bl="+bll);
            	                 }
            	           });
            	    red.addActionListener(new ActionListener()
            	    {
            	                 public void actionPerformed(ActionEvent ae)
            	      {
            	             rll=0;gll=150;bll=100;
            	             ruu=255;guu=170;buu=160;
//            	                	 gll=gll+1;
//            	                	 System.out.println("gr= "+gll);
            	             
            	                 }
            	           });
            	    Default.addActionListener(new ActionListener()
            	    {
            	                 public void actionPerformed(ActionEvent ae)
            	      {
            	             rll=0;gll=0;bll=0;
            	             ruu=255;guu=255;buu=255;
            	                 }
            	           });
	    while (true) {
            	        //If next video frame is available
            	        if (camera.read(frame)) {
            	        	BufferedImage image = Mat2BufferedImage(skinDetection(frame,rll,gll,bll,ruu,guu,buu));
            	            ImageIcon img = new ImageIcon(image);
            	            vidPanel.setIcon(img);
            	            //Update the vidPanel in the JFrame
            	            vidPanel.repaint();

            	        }
            	    }

    }
    public static Mat skinDetection(Mat src,int rl,int gl,int bl,int ru,int gu,int bu) {
        // define the upper and lower boundaries of the HSV pixel
        // intensities to be considered 'skin'
        Scalar lower = new Scalar(rl, gl, bl);
        Scalar upper = new Scalar(ru, gu, bu);

        // Convert to HSV
        Mat hsvFrame = new Mat(src.rows(), src.cols(), CvType.CV_8U, new Scalar(3));
        Imgproc.cvtColor(src, hsvFrame, Imgproc.COLOR_RGB2YUV, 3);

        // Mask the image for skin colors
        Mat skinMask = new Mat(hsvFrame.rows(), hsvFrame.cols(), CvType.CV_8U, new Scalar(3));
        Core.inRange(hsvFrame, lower, upper, skinMask);
        final Size kernelSize = new Size(11, 11);
        final Point anchor = new Point(-1, -1);
        final int iterations = 2;

        final Size ksize = new Size(3, 3);

        Mat skin = new Mat(skinMask.rows(), skinMask.cols(), CvType.CV_8U, new Scalar(3));
        Imgproc.GaussianBlur(skinMask, skinMask, ksize, 0);
        Core.bitwise_and(src, src, skin, skinMask);

        return skin;
    }
    public static BufferedImage Mat2BufferedImage(Mat m) {
        //Method converts a Mat to a Buffered Image
        int type = BufferedImage.TYPE_BYTE_GRAY;
         if ( m.channels() > 1 ) {
             type = BufferedImage.TYPE_3BYTE_BGR;
         }
         int bufferSize = m.channels()*m.cols()*m.rows();
         byte [] b = new byte[bufferSize];
         m.get(0,0,b); // get all the pixels
         BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
         final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
         System.arraycopy(b, 0, targetPixels, 0, b.length);  
         return image;
        }
}
