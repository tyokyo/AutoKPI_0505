package drag;

import javax.swing.JFrame;

public class sa {
	 public static void main(String[] args)
     {
       JFrame frame1 = new JFrame();
       frame1.add(new DragDao());
       frame1.setLocationRelativeTo(null);//
       frame1.setTitle("Frame 1");
       frame1.show();
     }
}
