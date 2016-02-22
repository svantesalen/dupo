package se.klartbra.dupo.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.control.filehandling.CopyFinder;
import se.klartbra.dupo.control.filehandling.FileOperations;
import se.klartbra.dupo.model.AllFilesWithCopies;
import se.klartbra.dupo.view.MainWindow;
import se.klartbra.dupo.view.PopUp;
import se.klartbra.dupo.view.PopUp.Answer;

public class Controller {

	private static Logger log = LogManager.getLogger(Controller.class);
	private static Controller instance = new Controller();

	private Controller() {}

	public static Controller getController() {
		return instance;
	}

	/**
	 * Method called when user hits the button.
	 */
	public void onClick() {
		MainWindow.getInstance().setText("Looking for copies...");
		List<File> directories = getDirectoriesToCompare();
		log.debug("Selected directories:\n"+toString(directories));
		addSubDirectories(directories);
		log.debug("Selected sub directories:\n"+toString(directories));
		CopyFinder copyFinder = new CopyFinder();
		List<File> copies = copyFinder.findCopies(directories);
		if(copies.isEmpty()) {
			MainWindow.getInstance().setText("NO COPIES FOUND");
		} else {
			AllFilesWithCopies allFilesWithCopies = copyFinder.getAllFilesWithCopies();
			allFilesWithCopies.cleanUp();
			MainWindow.getInstance().setText(allFilesWithCopies.toString());		
		}
	}

	private void addSubDirectories(List<File> directories) {
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
}
