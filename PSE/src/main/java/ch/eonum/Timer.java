package ch.eonum;

import java.util.Date;

/**
 * Timer class for timing and logging.
 */
public class Timer
{
	private long _startTime;

	/**
	 * Creates a new Timer instance or resets an existing one.
	 */
	public Timer()
	{
		this.reset();
	}

	/**
	 * Sets the Timer back to zero.
	 */
	public void reset()
	{
		this._startTime = this.timeNow();
	}

	/**
	 * Returns the time elapsed but does not reset the timer.
	 * 
	 * @return Elapsed time in milliseconds.
	 */
	public long timeElapsed()
	{
		return this.timeNow() - this._startTime;
	}

	/**
	 * Returns the actual time.
	 * 
	 * @return Actual System Time in milliseconds.
	 * @see Date#getTime()
	 */
	protected long timeNow()
	{
		return new Date().getTime();
	}
}
