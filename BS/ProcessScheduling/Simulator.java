package pssimulator;

/**
 * A simulator interface. It is provided to ensure that the internal mechanisms
 * of the simulator remains hidden.
 *
 * Disclaimer: this process scheduling simulator (including fileformat, and
 * comments) is based on a similar Grant William Braught's project, which is
 * available at <http://users.dickinson.edu/~braught>.
 *
 * @author Frederick Aubert
 * @author University Of Neuchatel
 * @version March 2nd, 2007
 */
public interface Simulator {
    /**
     * Schedule a timer interrupt to occur after the specified number of time
     * units. The timer is set to begin when the system call that started it
     * completes. Thus a process started to run when a timer is set will be
     * allowed to run for a full "delay" time units.
     * Only one timer interrupt can be scheduled at a time. If the event
     * queue already contains a timer interrupt that timer interrupt will be
     * canceled and a new one created.
     *
     * @param delay the number of time units before the timer interrupt will
     *            occur.
     *
     * @return false if the event is ignored, true otherwise.
     */
    public boolean schedulePreemptionInterrupt(long delay);

    /**
     * Cancel a timer interrupt if one has been set. If a timer interrupt has
     * been set, it is removed from the event queue. If no timer interrupt has
     * been set this method does nothing.
     *
     * @return true if there was a pending preemption event, false otherwise.
     */
    public boolean cancelPreemptionInterrupt();

    /**
     * Query the overall time of a process.
     *
     * @param processID name of the process which is queried.
     *
     * @return the overall time of the process, or -1 if there is no such process.
     */
    public long queryOverallTime(String processID);

    /**
     * Query the remaining time of the current burst of a process.
     *
     * @param processID name of the process which is queried.
     *
     * @return the remaining time of the current burst of the process, or -1 if
     * there is no such process or no pending burst.
     */
    public long queryBurstRemainingTime(String processID);
}
