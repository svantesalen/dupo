package se.klartbra.dupo.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.control.filehandling.FileOperations;
import se.klartbra.dupo.control.language.Words;
import se.klartbra.dupo.view.PopUp;
import se.klartbra.dupo.view.PopUp.Answer;

/**
 * This class is in charge of the searching, though it delegates the job to a SwingWorker thread.
 * 
 * @author svante
 *
 */
public class Controller {

	private static Logger log = LogManager.getLogger(Controller.class);
	private static Controller instance;
	private boolean help = false;
	private Controller() {}

	public static Controller getInstance() {
		if(instance==null) {
			instance = new Controller();
		}
		return instance;
	}

	/**
	 * Method called when user hits the button.
	 */ 
	public void onFindCopiesButtonClick() {
		List<File> directories = getDirectoriesToCompare();
		if(directories.isEmpty()) {
			return;
		}
		log.debug("Selected directories:\n"+toString(directories));

//		addSubDirectories(directories);
		Worker worker = new Worker(directories);
		worker.execute();
	}

	// for test
	public void addSubDirectories(List<File> directories) {
		List<File> subDirectories = FileOperations.getSubDirectoriesRecursively(directories);
		directories.addAll(subDirectories);		
	}

	private String toString(List<File> fileList) {
		StringBuilder sb = new StringBuilder();
		for(File f: fileList) {
			sb.append(f.getAbsolutePath());
			sb.append("\n");
		}
		return sb.toString();
	}

	public List<File> getDirectoriesToCompare() {	
		List<File> fileList = new ArrayList<>();
		File[] files;
		Answer answer;
		do {
			files = PopUp.letUserChooseDirectories();
			if(files.length == 0) {
				break;
			}
			addToFileList(fileList, files);
			answer = PopUp.question(null, "More?", "Select more directories?");
		} while(answer == Answer.YES);
		return fileList;
	}

	private void addToFileList(List<File> fileList, File[] files) {
		for(int i=0; i<files.length; i++) {
			if(files[i]==null) {
				continue;
			}
			fileList.add(files[i]);
		}
	}

	public void handleExit() {
		Answer answer = PopUp.question(null, Words.get("POP_UP_EXIT_TITLE"), Words.get("POP_UP_EXIT_MESSAGE"));
		if(answer == Answer.YES) {
			System.exit(0); // NOSONAR			
		}
	}

	public void turnOnHelp() {
		help = true;
	}

	public boolean isHelpOn() {
		return help;
	}
}
