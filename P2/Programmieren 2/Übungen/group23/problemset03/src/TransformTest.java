import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class TransformTest {
	
	Transform transform;
	
	Point2D  target;
	Point2D  origin = new Point2D.Double(0,0);

	Rectangle2D panelRectangle = new Rectangle2D.Double(0,0,1000,1000);
	Rectangle2D imageRectangle;
	
	final BufferedImage image;
	
	public TransformTest()  throws IOException {
		InputStream s = ClassLoader.getSystemResourceAsStream("shadowbird.png");
		assert s != null;
		image = ImageIO.read(s);
		imageRectangle =  new Rectangle2D.Double(0,0,image.getWidth(),image.getHeight());
	}
	
	@Before
	public void setUp() {
		transform = new Transform(imageRectangle,panelRectangle);
		target = new Point2D.Double();
	}
	
	@After
	public void tearDown() {
		target = null;
		transform = null;
	}
	
    @Test(expected=AssertionError.class)
	public void assertionsOn() {
		assert false;
	}
	
	@Test
	public void testTranslate() {
		transform.addTransformation("translate 2 1"); 
		transform.getAffineTransform().transform(new Point2D.Double(2,3),target);
		assertTrue(target.distance(new Point2D.Double(4,4)) < 0.001);
	}
	
	@Test
	public void testTranslateReals() {
		transform.addTransformation("translate 2.0 1.0");
		transform.getAffineTransform().transform(new Point2D.Double(2,3),target);
		assertTrue(target.distance(new Point2D.Double(4,4)) < 0.001);
	}
	
	@Test
	public void testRotateRadiansClockwise() {
			transform.addTransformation("rotate 1.57079633 clockwise");
			transform.getAffineTransform().transform(new Point2D.Double(1,1), target);
			assertTrue(target.distance(new Point2D.Double(1,1)) < 0.01);
	}
	

	@Test
	public void testRotateRadiansAnticlockwise()  {
			transform.addTransformation("rotate 1.57079633 anticlockwise");
			transform.getAffineTransform().transform(new Point2D.Double(1,-1), target);
			assertTrue(target.distance(new Point2D.Double(1,-1)) < 0.01);
		}
	
/*	@Test
	public void testRotateRadiansAnticlockwise2()  {
			transform.addTransformation("rotate 1.57079633 anticlockwise");
			transform.getAffineTransform().transform(new Point2D.Double(1,-1), target);
			assertThat(target, closeTo(new Point2D.Double(1,-1)));
		}*/
	
	
	/*
	 * AKL Scaling changed to 1.2 instead of 2.
	 */
	
	@Test
	public void testScale() {
		transform.addTransformation("scale 1.2");
		transform.getAffineTransform().transform(new Point2D.Double(5,15),target);
		assertTrue(target.distance(new Point2D.Double(6,18)) < 0.001);
	}
	
	/*
	 * AKL Changed following test case, "scale 2" isn't
	 * within the picture frame anymore
	 */
	@Test
	public void testTranslateThenScale() {
		transform.addTransformation("translate 2 1"); 
		transform.addTransformation("scale .5"); 
		transform.getAffineTransform().transform(new Point2D.Double(2,3),target);
		assertTrue(target.distance(new Point2D.Double(2,2)) < 0.001);
	}
	
}



