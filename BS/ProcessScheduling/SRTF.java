/* import */
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

import pssimulator.Simulator;
import pssimulator.SimulatorStatistics;

public class SRTF implements pssimulator.Kernel {

	/* members */
	final static String IDLE_PROCESS = "IDLE";
	final static int TIME_SLICE = 2; // according to description

	private HashMap<String, Device> Devices = new HashMap<String, Device>();
	private LinkedList<Process> QueuedProcesses = new LinkedList<Process>();
	private LinkedList<Process> FinishedProcesses = new LinkedList<Process>();

	private int saves = 0;

	/* methods */

	/**
	 * Initiate new Device.
	 */
	public void systemCallInitIODevice(String deviceID, Simulator simulator) {
		Devices.put(deviceID, new Device(deviceID));
	}

	/**
	 * Create new Process.
	 */
	public void systemCallProcessCreation(String processID, long timer,
			Simulator simulator) {
		QueuedProcesses.add(new Process(processID, timer));
		simulator.schedulePreemptionInterrupt(0);
	}

	/**
	 * User process requests an I/O-device.
	 */
	public void systemCallIORequest(String deviceID, long timer,
			Simulator simulator) {
		// leave CPU and queue line up at device queue
		Process p = QueuedProcesses.remove();
		Device d = Devices.get(deviceID);
		d.addWaitingProcess(p);

		// remember context switch
		saves++;
	}

	/**
	 * A process finishes.
	 */
	public void systemCallProcessTermination(long timer, Simulator simulator) {
		// switch queue
		Process p = QueuedProcesses.remove();
		p.finished(timer);
		FinishedProcesses.add(p);
	}

	/**
	 * Interrupt I/O-device.
	 */
	public void interruptIODevice(String deviceID, long timer,
			Simulator simulator) {
		Device d = Devices.get(deviceID);
		Process p = d.remove(timer);

		QueuedProcesses.add(p);
		p.startWaiting(timer);
		sortQueue(simulator);
	}

	private void sortQueue(Simulator simulator) {
		ProcessComparator<Process> comparer = new ProcessComparator<Process>(
				simulator);
		Collections.sort(QueuedProcesses, comparer);
	}

	/**
	 * Preemptive Interruption.
	 */
	public void interruptPreemption(long timer, Simulator simulator) {
		sortQueue(simulator);
	}

	/**
	 * Get currently active process.
	 */
	public String running(long timer, Simulator simulator) {
		if (!QueuedProcesses.isEmpty()) {
			Process p = QueuedProcesses.peek();
			if (p.isWaiting())
				p.stopWaiting(timer);
			return p.getProcessID();
		} else {
			return IDLE_PROCESS;
		}
	}

	/**
	 * Print statistics on termination.
	 */
	public void terminate(long timer, SimulatorStatistics simulator) {
		int totalWaitingTime = 0;
		int totalOverallTime = 0;

		for (Process p : FinishedProcesses) {
			totalWaitingTime += p.getWaitingTime();
			totalOverallTime += p.getOverallTime();
		}

		int size = FinishedProcesses.size();
		int wmt = totalWaitingTime / size;
		int att = totalOverallTime / size;

		simulator.formatStatistics(System.out, simulator.getSystemTime(),
				simulator.getUserTime(), simulator.getIdleTime(),
				simulator.getSystemCallsCount(), saves, wmt, att);
	}

	public class Process {

		private String processID;

		private boolean waiting = true;
		private long waitingTime = 0;
		private long totalWaitingTime = 0;

		private long startingTime;
		private long finishTime;

		public Process(String processID, long timer) {
			this.processID = processID;
			this.startingTime = timer;
		}

		public void finished(long timer) {
			this.finishTime = timer;
		}

		public boolean isWaiting() {
			return waiting;
		}

		public void startWaiting(long timer) {
			this.waiting = true;
			this.waitingTime = timer;
		}

		public void stopWaiting(long timer) {
			this.totalWaitingTime += (timer - this.waitingTime);
			this.waitingTime = 0;
			this.waiting = false;
		}

		public long getWaitingTime() {
			return this.totalWaitingTime;
		}

		public long getOverallTime() {
			long overallTime = this.finishTime - this.startingTime;
			return overallTime;
		}

		public String getProcessID() {
			return this.processID;
		}
	}

	public class Device {

		private String deviceID;
		private LinkedList<Process> Queue = new LinkedList<Process>();

		public Device(String deviceID) {
			this.deviceID = deviceID;
		}

		public String getProcessID() {
			return this.deviceID;
		}

		public void addWaitingProcess(Process p) {
			this.Queue.add(p);
		}

		public Process remove(long timer) {
			Process p = Queue.remove();
			return p;
		}
	}

	private class ProcessComparator<T> implements Comparator<Process> {
		private final Simulator simulator;

		public ProcessComparator(Simulator simulator) {
			this.simulator = simulator;
		}

		public int compare(Process p1, Process p2) {
			long burst1 = simulator.queryBurstRemainingTime(p1.getProcessID());
			long burst2 = simulator.queryBurstRemainingTime(p2.getProcessID());

			if (burst1 < burst2)
				return -1;
			else
				return 1;
		}
	}
}