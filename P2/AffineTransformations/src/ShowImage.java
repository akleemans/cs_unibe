import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class ShowImage extends Panel implements ActionListener {

	private static final long serialVersionUID = -5406868933999874896L;

	static final Dimension SHOW_IMAGE_SIZE = new Dimension(1000, 1000);
	
	final BufferedImage image;
	final Transform transformator;
	Document document;
	
	public ShowImage() throws IOException {
		InputStream s = ClassLoader.getSystemResourceAsStream("shadowbird.png");
		assert s != null;
		image = ImageIO.read(s);
		Rectangle2D panelRectangle = new Rectangle2D.Double(0,0,1000,1000);
		Rectangle2D imageRectangle = new Rectangle2D.Double(0,0,image.getWidth(),image.getHeight());
		transformator = new Transform(imageRectangle, panelRectangle);
		Graphics graphic = image.getGraphics();
		this.paint(graphic);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(image,transformator.getAffineTransform(),this);
	}

	static public void main(String args[]) throws Exception {
		JFrame frame = new JFrame("Display image");
		Panel panel = new Panel();
		ShowImage image = new ShowImage();
		image.setPreferredSize(SHOW_IMAGE_SIZE);
		JLabel label = new JLabel("Enter transformation here:");
		label.setMaximumSize(new Dimension(100000, 20));
		JTextArea textArea = makeTextArea(image);
		image.document = textArea.getDocument();
		JButton button = new JButton("Set!");
		button.addActionListener(image);
		button.setMaximumSize(new Dimension(10000,20));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(image);
		panel.add(label);
		panel.add(textArea);
		panel.add(button);
		frame.setSize(new Dimension(1000,1000));
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

	private static JTextArea makeTextArea(ShowImage image) {
		JTextArea textArea = new JTextArea();
		textArea.setMaximumSize(new Dimension(100000, 200));
		textArea.setPreferredSize(new Dimension(0, 70));
		textArea.setVisible(true);
		textArea.setBorder(BorderFactory.createLineBorder(Color.black));
		return textArea;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String description;
		try {
			description = document.getText(0, document.getLength());
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
		
		transformator.addTransformation(description);
		
		this.repaint();
	}
	
}
