package drag;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class DragDao extends JPanel implements MouseMotionListener,MouseListener {

	/** 矩形的起点 左上角* */
	private static Point rectStart = null;

	/** 矩形的终点 右下角* */
	private static Point rectStop = null;

	/** 是否绘制虚线矩形* */
	private boolean dottedTag = true;
	
	/*************这里更改了****************/
	private List<Shape> list = new ArrayList<Shape>(); 

	public DragDao() {
		this.setBackground(Color.WHITE);
		this.setSize(1000, 1000);
		rectStart = new Point(0, 0);
		rectStop = new Point(0, 0);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		// this.isClear = isClear;
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		/** 矩形的宽度* */
		int w = Math.abs(rectStop.x - rectStart.x);
		/** 矩形的高度* */
		int h = Math.abs(rectStop.y - rectStart.y);

		/** 起点终点的最小值作为起点* */
		Point min = new Point(0, 0);
		min.x = Math.min(rectStop.x, rectStart.x);
		min.y = Math.min(rectStop.y, rectStart.y);
		/** 起点终点的最小值作为终点* */
		Point max = new Point(0, 0);
		max.x = Math.max(rectStop.x, rectStart.x);
		max.y = Math.max(rectStop.y, rectStart.y);

		/** 如果是绘制虚线矩形* */
		if ( dottedTag) {
			/** new float[] { 5, 5, } 数组可以改变虚线的密度* */
			Stroke dash = new BasicStroke(0.5f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_ROUND, 0.5f, new float[] { 5, 5, }, 0f);
			g2.setStroke(dash);
			g2.setColor(Color.BLUE);
			g2.drawRect(min.x, min.y, w, h);
		} else {
			list.add(new Rectangle2D.Double(min.x, min.y, w, h)); 
		}
		/*************这里更改了****************/
		g2.setStroke(new BasicStroke());
		g2.setColor(new Color(198, 214, 239));
		for(Shape s:list){
			g2.fill(s);
		}

	}

	/** *鼠标拖动 */
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		/** 随着鼠标的拖动 改变终点* */
		rectStop = arg0.getPoint();
		this.repaint();
	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/** 鼠标按键在组件上单击（按下并释放）时调用* */
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/** 鼠标按键在组件上按下时调用 */
	public void mousePressed(MouseEvent arg0) {
		/** 设置可以进行绘制* */
		dottedTag = true;
		/** 记录起始点* */
		rectStart = arg0.getPoint();
		/** 记录起终点* */
		rectStop = rectStart;

	}

	/** 鼠标按钮在组件上释放时调用* */
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		dottedTag = false;
		this.repaint();
	}

	/** 鼠标进入到组件上时调用* */
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/** 鼠标离开组件时调用* */
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}

