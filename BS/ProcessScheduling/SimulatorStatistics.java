package pssimulator;

import java.io.PrintStream;

/**
 * A simulator statistics interface. It is provided to ensure that the internal
 * mechanisms of the simulator remains hidden.
 *
 * Disclaimer: this process scheduling simulator (including fileformat, and
 * comments) is based on a similar Grant William Braught's project, which is
 * available at <http://users.dickinson.edu/~braught>.
 *
 * @author Frederick Aubert
 * @author University Of Neuchatel
 * @version March 2nd, 2007
 */
public interface SimulatorStatistics {
    /**
     * Return the current system time. This is the total number of virtual time
     * units that have elapsed since the system has started. It should agree
     * with the value obtained from getUserTime() + getIdleTime();
     *
     * @return the current system time.
     */
    public long getSystemTime();

    /**
     * Return the current user time. This is the number of virtual time units
     * that the system has spent performing user operations.
     *
     * @return the current user time.
     */
    public long getUserTime();

    /**
     * Return the current idle time. This is the number of virtual time units
     * that the system has spent executing the idle process.
     *
     * @return the current idle time.
     */
    public long getIdleTime();

    /**
     * Return the current system calls count. This is the number of time the
     * simulator has called a Kernel method.
     *
     * @return the current system calls count.
     */
	public int getSystemCallsCount();

	/**
	 * Print statistics. This is the method to call with your computed values to
	 * nicely print out the statistics.
	 *
	 * @param stream the output stream on which to print the statistics.
	 * @param system the system time.
	 * @param user the user time.
	 * @param idle the idle time.
	 * @param calls the number of system calls.
	 * @param saves the number of register saves.
	 * @param wmt the waiting mean time.
	 * @param att the average turnaroud time.
	 */
	public void formatStatistics(PrintStream stream, long system, long user, long idle, int calls, int saves, long wmt, long att);

}
