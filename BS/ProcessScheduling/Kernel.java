package pssimulator;

/**
 * Classes implementing this interface act as an operating system kernel in the
 * simulation. All system calls and interrupts generated during the simulation
 * are passed onto the kernel to be processed.
 *
 * <p>
 * Note that to be compatible with the simulation framework, all classes
 * implementing the Kernel interface must have only a no-arg constructor.
 *
 * Disclaimer: this process scheduling simulator (including fileformat, and
 * comments) is based on a similar Grant William Braught's project, which is
 * available at <http://users.dickinson.edu/~braught>.
 *
 * @author Frederick Aubert
 * @author University Of Neuchatel
 * @version February 23, 2007
 * @revised February 26, March 2nd, 2007
 */
public interface Kernel {
    /**
     * This system call is issued once for each device listed in the devices file specified
     * on the command line when the Simulator is executed.
     */
    public void systemCallInitIODevice(String deviceID, Simulator simulator);

    /**
     * This system call is issued once for each process, at the process' arrival time
     * as specified in its data file. This system call indicates the arrival of the
     * process in the system.
     */
    public void systemCallProcessCreation(String processID, long timer, Simulator simulator);

    /**
     * This system call is issued once for each process, once the process has completed
     * its execution.
     */
    public void systemCallProcessTermination(long timer, Simulator simulator);

    /**
     * This system call is issued each time a user process requests and I/O Operation.
     * Note that any requests for operations on devices that do not exist should cause
     * the program to terminate with an error message.
     */
    public void systemCallIORequest(String deviceID, long timer, Simulator simulator);

    /**
     * This interrupt may arrive from any I/O device created by a InitDevice system
     * call. If an interrupt arrives from any other device the scheduler should cause
     * the program to terminate with an error message.
     */
    public void interruptIODevice(String deviceID, long timer, Simulator simulator);

    /**
     * This interrupt may arrive when preemption occurs.
     */
    public void interruptPreemption(long timer, Simulator simulator);

    /**
     * Return the name of the process currently in the running state. When no
     * other process is ready to run the Kernel must report that the "Idle"
     * process is running.
     */
    public String running(long timer, Simulator simulator);

    /**
     * This method is called when the simulation has completed. The code in this
     * method should compute statistics and display the results of the
     * simulation.
     */
    public void terminate(long timer, SimulatorStatistics simulator);
}