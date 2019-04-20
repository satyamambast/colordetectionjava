package colors;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.*;   
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;

public class HelloCV {
    public static void main(String[] args){
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
            System.out.println("mat = " + mat.dump());
            Mat frame = new Mat();
            VideoCapture camera = new VideoCapture(0);
            camera.read(frame);
            Imgcodecs.imwrite( "pehla.jpg", frame );
            JFrame jframe = new JFrame("Video Title");

            	    //Inform jframe what to do in the event that you close the program
            	    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            	    //Create a new JLabel object vidpanel
            	    JLabel vidPanel = new JLabel("",JLabel.CENTER);

            	    //assign vidPanel to jframe
            	    jframe.setContentPane(vidPanel);

            	    //set frame size
            	    jframe.setSize(2000, 4000);

            	    //make jframe visible
            	    jframe.setVisible(true);

            	    while (true) {
            	        //If next video frame is available
            	        if (camera.read(frame)) {
            	            //Create new image icon object and convert Mat to Buffered Image
            	            ImageIcon image = new ImageIcon(Mat2BufferedImage(frame));
            	            //Update the image in the vidPanel
            	            vidPanel.setIcon(image);
            	            //Update the vidPanel in the JFrame
            	            vidPanel.repaint();

            	        }
            	    }
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
