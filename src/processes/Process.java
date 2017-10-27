package processes;

/**
 * Represents a Linux command.
 * @author szhu842, osim082
 */
public class Process {

	private final String cmd;
	private final String name;

	/**
	 * @param cmd Command as a string.
	 * @param name Name that will refer to the command.
	 */
	public Process(String cmd, String name) {
		this.cmd = cmd;
		this.name = name;
	}

	/**
	 * Get the command.
	 * @return Returns the command line as string.
	 */
	public String getCmd() {
		return cmd;
	}

	/**
	 * Get the name.
	 * @return Returns the name of the command.
	 */
	public String getName() {
		return name;
	}
}
