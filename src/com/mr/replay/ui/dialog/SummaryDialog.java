package com.mr.replay.ui.dialog;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import com.mr.replay.ui.panel.SummaryPanel;

public class SummaryDialog extends JDialog implements ActionListener{

	@Override
	protected JRootPane createRootPane() {
		// TODO Auto-generated method stub
		KeyStroke  stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0);
		JRootPane rootPane = new JRootPane();
		rootPane.registerKeyboardAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				close();
			}
		},stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		return rootPane;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int w=600;
	int h=450;

	public SummaryDialog(JFrame parent ,String videopath,String startpont,String endpoint){
		super(parent, "设置", true);
		this.setBounds(100, 100, w, h);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int) (toolkit.getScreenSize().getWidth() - w) / 2;
		int y = (int) (toolkit.getScreenSize().getHeight() - h) / 2;
		this.setLocation(x, y);// 设置窗口居中		
		this.setContentPane(new SummaryPanel( videopath, startpont, endpoint));
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
            	close();
            }
        });
	}
	
	public void close(){
		this.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
