package ursuppe;

import javax.swing.JFrame;

/**
 * Provides an output GUI, which can be set silent
 * with runSilent().
 */
public class OutputControl {
	
	private JFrame obj;
	
	public OutputControl() {
		this.obj = new GameGUI();
		obj.setVisible(false);
	}

	public void showOutput() {
		obj.setVisible(true);
	}
}
