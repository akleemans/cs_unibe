import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
 * Implements a bouncing ball simulation.
 */
public class BouncingBallsSimulation extends Component implements Runnable {

	private static final long serialVersionUID = -2985687697048512563L;
	LinkedList<Ball> balls;	// List of balls.
	Image img;				// Image to display balls.
	int w, h;				// Width an height of image.
	Graphics2D gi;			// Graphics object to draw balls.
	float r;				// Radius of balls.
	int n;					// Number of balls.
	Thread thread;			// Thread that runs simulation loop.

	/**
	 * Initializes the simulation.
	 * 
	 * @param w width of simulation window.
	 * @param h height of simulation window.
	 * @param n number of balls.
	 * @param r radius of balls.
	 * @param v initial velocity of balls.
	 */
	public BouncingBallsSimulation(int w, int h, int n, float r, float v)
	{
		this.r = r;
		this.n = n;
		this.w = w;
		this.h = h;
		
		// Initialize balls by distributing them randomly.
		balls = new LinkedList<Ball>();
		for(int i=0; i<n; i++)
		{
			float vx = 2*(float)Math.random()-1;
			float vy = 2*(float)Math.random()-1;
			float tmp = (float)Math.sqrt((double)vx*vx+vy*vy);
			vx = vx/tmp*v;
			vy = vy/tmp*v;
			balls.add(new Ball(r+(float)Math.random()*(w-2*r), r+(float)Math.random()*(h-2*r), vx, vy, r));
		}

	}
	
	public Dimension getPreferredSize() {
        return new Dimension(w, h);
    }
	
	/**
	 * Paint the window that displays the simulation. This method is called
	 * automatically by the Java window system as a response to the call to 
	 * repaint() in the run() method below. 
	 */
	public void paint(Graphics g)
	{
		gi.clearRect(0, 0, w, h);
		
		Iterator<Ball> it = balls.iterator();
		while(it.hasNext())
		{
			Ball ball = it.next();
			gi.fill(new Ellipse2D.Float(ball.x-r, ball.y-r, 2*r, 2*r));
		}
		
		g.drawImage(img, 0, 0, null);
	}
	
	/**
	 * Starts the simulation loop.
	 */
	public void start()
	{
		img = createImage(w, h);
		gi = (Graphics2D)img.getGraphics();
		gi.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		thread = new Thread(this);
		thread.run();
	}
	
	/**
	 * The simulation loop.
	 */
	public void run()
	{
		// Set up timer.
		int c = 0;
		Timer timer = new Timer();
		timer.reset();
		
		// Loop forever (or until the user closes the main window).
		while(true)
		{
			// Run one simulation step.
        	Iterator<Ball> it = balls.iterator();
        	
        	// Iterate over all balls.
        	while(it.hasNext())
        	{
        		Ball ball = it.next();
        		
        		// Move the ball.
        		ball.move();
	        	
        		// Handle collisions with boundaries.
	        	if(ball.doesCollide((float)w,0.f,-1.f,0.f))
	        		ball.resolveCollision((float)w,0.f,-1.f,0.f);	       
	        	if(ball.doesCollide(0.f,0.f,1.f,0.f))
	        		ball.resolveCollision(0.f,0.f,1.f,0.f);	        	
	        	if(ball.doesCollide(0.f,(float)h,0.f,-1.f))
	        		ball.resolveCollision(0.f,(float)h,0.f,-1.f);	        	
	        	if(ball.doesCollide(0.f,0.f,0.f,1.f))
	        		ball.resolveCollision(0.f,0.f,0.f,1.f);
        		
	        	// Handle collisions with other balls.
        		Iterator<Ball> it2 = balls.iterator();
        		Ball ball2 = it2.next();
        		while(ball2 != ball)
        		{
        			if(ball.doesCollide(ball2))
        				ball.resolveCollision(ball2);
        			ball2 = it2.next();
        		}
        	}
        	
        	// Trigger update of display.
			repaint();
			
			// Print time per simulation step.
			c++;
			if(c==10)
			{
				System.out.printf("Timer per simulation step: %fms\n", (float)timer.timeElapsed()/(float)c);
				timer.reset();
				c = 0;
			}
		}
	}

}
