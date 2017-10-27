package processes;

/**
 * Abstract the output of a command-line process in Linux
 */
public class ProcessOutput {
	private String stdout, stderr;

	/**
	 * Returns stdout as String.
	 * @return
	 */
	public String getStdout() {
		return stdout;
	}

	/**
	 * Returns stderr as String.
	 * @return
	 */
	public String getStderr() {
		return stderr;
	}

	/**
	 * @param stdout Stdout of the process as a string.
	 * @param stderr Stderr of the process as a string.
	 */
	public ProcessOutput(String stdout, String stderr) {
		this.stderr = stderr;
		this.stdout = stdout;
	}

}
