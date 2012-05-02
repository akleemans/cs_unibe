import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
 * Implements a bouncing ball simulation.
 */
public class BouncingBallsSimulationImproved extends Component implements Runnable {

	private static final long serialVersionUID = -2985684593448512563L;
	LinkedList<Ball>[][] balls;	// List of balls.
	LinkedList<Ball> temp;		// Temporary List, containing balls.
	Image img;					// Image to display balls.
	int w, h;					// Width an height of image.
	Graphics2D gi;				// Graphics object to draw balls.
	float r;					// Radius of balls.
	int n;						// Number of balls.
	int a,b;					
	Thread thread;				// Thread that runs simulation loop.

	/**
	 * Initializes the simulation.
	 * 
	 * @param w width of simulation window.
	 * @param h height of simulation window.
	 * @param n number of balls.
	 * @param r radius of balls.
	 * @param v initial velocity of balls.
	 */
	public BouncingBallsSimulationImproved(int w, int h, int n, float r, float v, int a, int b)
	{
		this.r = r;
		this.n = n;
		this.w = w;
		this.h = h;
		this.a = a;
		this.b = b;
		
		
		// Initialize balls by distributing them randomly.
		temp = new LinkedList<Ball>();
		balls = new LinkedList[a][b];
		for (int i=0; i<a; i++) 
			for (int j=0; j<b; j++)
				balls[i][j] = new LinkedList<Ball>();
		
		for(int i=0; i<n; i++)
		{
			float vx = 2*(float)Math.random()-1;
			float vy = 2*(float)Math.random()-1;
			float tmp = (float)Math.sqrt((double)vx*vx+vy*vy);
			vx = vx/tmp*v;
			vy = vy/tmp*v;
			
			
			Ball ball = new Ball(r+(float)Math.random()*(w-2*r), r+(float)Math.random()*(h-2*r), vx, vy, r);
			balls[0][0].add(ball);
			temp.add(ball);
			//balls.add(new Ball(r+(float)Math.random()*(w-2*r), r+(float)Math.random()*(h-2*r), vx, vy, r));
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
		
		Iterator<Ball> it = temp.iterator();
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
	
	public void run() {
		// Set up timer.
		int c = 0;
		Timer timer = new Timer();
		timer.reset();
		
		// Loop forever (or until the user closes the main window).
		while(true) {

			// Place balls into lists
			try {
				for (int i=0; i<a; i++) {
					for (int j=0; j<b; j++) {
						ListIterator<Ball> it = balls[i][j].listIterator();
			        	while(it.hasNext()){
 			        		Ball ball = it.next();
			        		int s = (int)Math.floor(ball.x/w*(a-1));
			        		int t = (int)Math.floor(ball.y/h*(b-1));
			        		if (s!=i || t!=j) {
			        			it.remove(); 
			        			balls[s][t].add(ball);
			        		}
			        	}
					}
				}
			} catch(java.util.ConcurrentModificationException e) {}
		        		
			// Move balls
			for(int i=0; i<a; i++) {
				for(int j=0; j<b; j++) {

					Iterator<Ball> it = balls[i][j].iterator();
        	
		        	while(it.hasNext()) {
		        		Ball ball = it.next();
			        	
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
			        	for (int i1=i-1; i1<=i+1; i1++) {
			        		for (int j1=j-1; j1<=j+1; j1++) {

			        			if (i1>=0 && j1>=0 && i1<a && j1<b) {
						        	Iterator<Ball> it2 = balls[i1][j1].iterator();
					        		while (it2.hasNext()) {
					        			Ball ball2 = it2.next();
					        			
					        			//check for collision
					        			if (ball!=ball2 && ball.doesCollide(ball2))
					        				ball.resolveCollision(ball2);
					        		}
			        			}
			        		}
			        	}
			        	// Move the ball.
		        		ball.move();
		        	}
				}
        	}    	
        	// Trigger update of display.
			repaint();
			
			// Print time per simulation step.
			c++;
			if (c==10) {
				System.out.printf("Timer per simulation step: %fms\n", (float)timer.timeElapsed()/(float)c);
				timer.reset();
				c = 0;
			}
		}
	}
}
