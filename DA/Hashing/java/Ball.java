
public class Ball {

	public float x, y;
	float vx, vy;
	float r;
	
	public Ball(float x, float y, float vx, float vy, float r)
	{
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.r = r;
	}
	
	public void move()
	{
		x = x+vx;
		y = y+vy;
	}
	
	public boolean doesCollide(float bx, float by, float nx, float ny)
	{
		if((x-bx)*nx+(y-by)*ny < r)
			return true;
		else 
			return false;
	}
	
	public boolean doesCollide(Ball b)
	{
		if((x-b.x)*(x-b.x)+(y-b.y)*(y-b.y) < 4*r*r)
			return true;
		else
			return false;
	}
	
	public void resolveCollision(Ball b)
	{
		// The normal direction at the contact point.
		float dx = x-b.x;
		float dy = y-b.y;
		float l = (float)Math.sqrt((double)(dx*dx+dy*dy));
		dx = dx/l;
		dy = dy/l;
		
		// Compute tangent and normal components of velocities.
		// Note that the tangent vector is (-dy,dx).
		float vn = dx*vx+dy*vy;;
		float vt = -dy*vx+dx*vy;
		
		float vnb = dx*b.vx+dy*b.vy;
		float vtb = -dy*b.vx+dx*b.vy;
		
		// Tangent component of velocity stays the same for each ball.
		// Normal velocity is exchanged between balls.
		vx = vt*(-dy) + vnb*dx;
		vy = vt*dx + vnb*dy;
		
		b.vx = vtb*(-dy) + vn*dx;
		b.vy = vtb*dx + vn*dy;
		
		// Move one of the balls away to avoid repeated collision.
		b.x = x-dx*2*r;
		b.y = y-dy*2*r;
	}
	
	public void resolveCollision(float bx, float by, float nx, float ny)
	{
		// Move the ball back into the bounds of the simulation.
		float xp = x+nx*(r-((x-bx)*nx+(y-by)*ny)); 
		float yp = y+ny*(r-((x-bx)*nx+(y-by)*ny));
		x = xp;
		y = yp;

		// Determine speed after collision by reflecting it about the normal.
		// (vxp,vyp) = 2*dot((-vx,-vy),(nx,ny))*(nx,ny)+(vx,vy)
		float vxp = 2*(-vx*nx-vy*ny)*nx+vx;
		float vyp = 2*(-vx*nx-vy*ny)*ny+vy;
		vx = vxp;
		vy = vyp;
	}
}
