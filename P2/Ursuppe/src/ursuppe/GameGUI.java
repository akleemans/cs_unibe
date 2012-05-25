package ursuppe;

import javax.swing.*;
import java.awt.*;
import java.io.*;
/**
 * GameGUI captures the standard out (System.out.print*-functions)
 * and displays them in a text area in a scroll pane.
 */
public class GameGUI extends JFrame {
	//generate some random serial number to satisfy eclipse
	private static final long serialVersionUID = 1240225963955027281L;
	JTextArea output;
	JScrollPane panelUrsuppe;
	
	protected GameGUI() {
		setSize(1000,800);
		setTitle("Ursuppe");
		setLocation(50,50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		
		//set size
		output = new javax.swing.JTextArea();
		output.setColumns(136);
		output.setRows(50);
		
		//set font
        Font consoleFont = new Font("Monospaced", Font.PLAIN, 12);
        output.setFont(consoleFont);
        
		panelUrsuppe = new javax.swing.JScrollPane(output);
		add(panelUrsuppe);
		System.setOut(new PrintStream(new JTextAreaOutputStream(output)));
		System.setErr(new PrintStream(new JTextAreaOutputStream(output)));
		//starting new thread 
		new Thread(new Runnable() {
			public void run(){
				}
		}).start();
	}
	
	//inner class for capturing standard out
	public class JTextAreaOutputStream extends OutputStream {
		JTextArea text;
		
		public JTextAreaOutputStream(JTextArea t) {
			super();
			text = t;
		}
		
		public void write(int i) { 
			text.append(Character.toString((char)i));
			setScrollbar();
		}
		
		public void write(char[] buf, int off, int len) {
			String s = new String(buf, off, len);
			text.append(s);
			setScrollbar();
		}
		
		public void setScrollbar() {
		    int max = panelUrsuppe.getVerticalScrollBar().getMaximum();
		    panelUrsuppe.getVerticalScrollBar().setValue(max);
		}
	}
}