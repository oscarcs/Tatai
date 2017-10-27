package processes;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Class used to manage processes, processes are added with a name of reference
 * and command to be executed, then the process can be run, or have a service
 * created to run it.
 * @author szhu842, osim082
 */
public class ServiceFactory {

	private HashMap<String, Process> processes;

	public ServiceFactory() {
		processes = new HashMap<>();
	}

	/**
	 * Method used to add a process by reference to the factory.
	 * @param name The name that the command will be referred to by.
	 * @param cmd The command that will be executed on the command line.
	 */
	public void addProcess(String name, String cmd) {

		// Check name doesn't already exist.
		if (processes.containsKey(name)) {
			throw new ProcessException("Process with " + name + " already exists.");
		}

		// Add the process to the processes map.
		processes.put(name, new Process(cmd, name));
	}

	/**
	 * Runs the process referred to by name, returns the outputs as a ProcessOutput.
	 * Using this method, no modification can be applied to the service, therefore
	 * onSuccess() cannot be implemented usefully for non-timed commands.
	 * @param directory Directory to run in.
	 * @param name Name of command to run.
	 * @return ProcessOutput separated in stdout and stderr.
	 * @throws IOException
	 */
	public ProcessOutput run(String directory, String name) throws IOException {
		String directoryLine = "";
		if (!(directory == null || directory == "")) {
			directoryLine = "cd " + directory + System.getProperty("line.separator");
		}
		Process task = processes.get(name);
		String cmd = directoryLine + task.getCmd();
		java.lang.Process process = new ProcessBuilder("/bin/bash", "-c", cmd).start();
		
		// Read stdout and stderr.
		InputStream stdout = process.getInputStream();
		InputStream stderr = process.getErrorStream();
		BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
		BufferedReader stderrBuffered = new BufferedReader(new InputStreamReader(stderr));
		String line;
		String out = "", err = "";
		
		while ((line = stdoutBuffered.readLine()) != null) {
			out = out + line + "\n";
		}
		while ((line = stderrBuffered.readLine()) != null) {
			err = err + line + "\n";
		}
		
		// Get the output and destroy the process.
		ProcessOutput output = new ProcessOutput(out, err);
		process.destroy();
		return output;
	}

	/**
	 * Customizable service that will run the command can have setOnSucceeded()
	 * passed in as a EventHandler.
	 * @param directory Directory that the program will run in.
	 * @param name Name of the process to be run.
	 * @param successEventHandler EventHandler triggered when service finishes.
	 * @return
	 */
	public Service<ProcessOutput> makeService(
		String directory, 
		String name,
		EventHandler<WorkerStateEvent> successEventHandler
	) {
		Service<ProcessOutput> service = new Service<ProcessOutput>() {
			@Override
			protected Task<ProcessOutput> createTask() {
				return new Task<ProcessOutput>() {
					@Override
					protected ProcessOutput call() throws Exception {
						return ServiceFactory.this.run(directory, name);
					}
				};
			}
		};
		service.setOnSucceeded(successEventHandler);
		return service;
	}
}
