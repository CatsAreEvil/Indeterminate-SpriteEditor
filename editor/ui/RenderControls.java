package editor.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

public class RenderControls extends UIElement{
	private JButton play;
	private JButton pause;
	private JLabel currentFrame;

	public RenderControls(Editor edit, Rectangle bounds)
	{
		super(edit, bounds);
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		play = new JButton("Play");
		panel.add(play);
		pause = new JButton("Pause");
		panel.add(pause);
		currentFrame = new JLabel("0");
		panel.add(currentFrame);
		panel.setLayout(new FlowLayout());
	}
	
	public void createEvents()
	{
		 play.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		editor.setPlaying(true);
	    	}
	    });
	    pause.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		editor.setPlaying(false);
	    	}
	    });
	}
}
