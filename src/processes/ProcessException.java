package processes;

/**
 * Exception used when something wrong happens in Process processing.
 * @author szhu842
 */
public class ProcessException extends RuntimeException {
	public ProcessException(String message) {
		super(message);
	}
}
