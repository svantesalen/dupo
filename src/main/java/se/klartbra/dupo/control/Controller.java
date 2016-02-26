package se.klartbra.dupo.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.control.filehandling.FileOperations;
import se.klartbra.dupo.control.language.Words;
import se.klartbra.dupo.view.MainWindow;
import se.klartbra.dupo.view.PopUp;
import se.klartbra.dupo.view.PopUp.Answer;

public class Controller {

	private static Logger log = LogManager.getLogger(Controller.class);
	private static Controller instance;

	private Controller() {}

	public static Controller getInstance() {
		if(instance==null) {
			instance = new Controller();
		}
		return instance;
	}
//
//	/**
//	 * Method called when user hits the button.
//	 */
//	public void onFindCopiesButtonClick2() {
//		List<File> directories = getDirectoriesToCompare();
//		if(directories.isEmpty()) {
//			return;
//		}
//		log.debug("Selected directories:\n"+toString(directories));
//		MainWindow.getInstance().setText(Words.get("MESSAGE_LOOKING_FOR_COPIES")+":\n");
//		MainWindow.getInstance().addText(toString(directories));
//		addSubDirectories(directories);
//		log.debug("Selected sub directories:\n"+toString(directories));
//		CopyFinder copyFinder = new CopyFinder();
//		copyFinder.findCopies(directories);
//		if(copyFinder.getAllFilesWithCopies().isEmpty()) {
//			MainWindow.getInstance().setText(Words.get("MESSAGE_NO_COPIES_FOUND"));
//		} else {
//			AllFilesWithCopies allFilesWithCopies = copyFinder.getAllFilesWithCopies();
//			allFilesWithCopies.cleanUp();
//			MainWindow.getInstance().setText(allFilesWithCopies.toString());
//			MainWindow.getInstance().populateListPanel(allFilesWithCopies);
//		}
//	}
//	
//	public void onFindCopiesButtonClickOld() {
//		List<File> directories = getDirectoriesToCompare();
//		if(directories.isEmpty()) {
//			return;
//		}
//		log.debug("Selected directories:\n"+toString(directories));
//		MainWindow.getInstance().setText(Words.get("MESSAGE_LOOKING_FOR_COPIES")+":\n");
//		MainWindow.getInstance().addText(toString(directories));
//		addSubDirectories(directories);
//		log.debug("Selected sub directories:\n"+toString(directories));
//		CopyFinder copyFinder = new CopyFinder();
//		List<File> copies = copyFinder.findCopies(directories);
//		if(copies.isEmpty()) {
//			MainWindow.getInstance().setText(Words.get("MESSAGE_NO_COPIES_FOUND"));
//		} else {
//			AllFilesWithCopies allFilesWithCopies = copyFinder.getAllFilesWithCopies();
//			allFilesWithCopies.cleanUp();
//			MainWindow.getInstance().setText(allFilesWithCopies.toString());
//			MainWindow.getInstance().populateListPanel(allFilesWithCopies);
//		}
//	}
//	
	/**
	 * Method called when user hits the button.
	 */ 
	public void onFindCopiesButtonClick() {
		List<File> directories = getDirectoriesToCompare();
		if(directories.isEmpty()) {
			return;
		}
		log.debug("Selected directories:\n"+toString(directories));
		MainWindow.getInstance().setText(Words.get("MESSAGE_LOOKING_FOR_COPIES")+":\n");
		MainWindow.getInstance().addText(toString(directories));
		addSubDirectories(directories);
		Worker worker = new Worker(directories);
		worker.execute();
		
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
}
