package se.klartbra.dupo.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.control.filehandling.CopyFinder;
import se.klartbra.dupo.control.filehandling.FileOperations;
import se.klartbra.dupo.control.language.Words;
import se.klartbra.dupo.model.AllFilesWithCopies;
import se.klartbra.dupo.view.MainWindow;
import se.klartbra.dupo.view.components.ButtonPanel;

/**
 * The thread doing the search.<br> 
 * An own thread is needed since the search is time consuming and the user might want to interrupt it.
 * 
 * @author svante
 *
 */
public class Worker extends SwingWorker<AllFilesWithCopies, String>{
	private static Logger log = LogManager.getLogger(Worker.class);
	private static final int PUBLISH_INTERVAL=300;
	private static final int PUBLISH_CR_INTERVAL=50000;
	private List<File> directories;

	/**
	 * CTOR. 
	 * @param directories
	 */
	public Worker(List<File> directories) {
		this.directories = directories;
	}

	@Override
	protected AllFilesWithCopies doInBackground() throws Exception {
		addSubDirectories(directories);
		int size = directories.size();
		publish("\n"+Words.get("NUMBER_OF_DIR_TO_SEARCH")+ " "+size);
		MainWindow.getInstance().startFinding();
		CopyFinder copyFinder = new CopyFinder();
		File currentDir;
		for(int i=0; i<size; i++) {
			currentDir = directories.get(i);
			if(copyFinder.findCopies(currentDir, directories, i+1)) {
				publish("\n"+Words.get("COPIES_FOUND_FOR")+currentDir.getAbsolutePath()+" :");
			} else {
				publish("\n"+Words.get("SEARCHING")+" ("+i+Words.get("OF")+size+") "+currentDir.getAbsolutePath());				
			}			
			if(!ButtonPanel.getInstance().isFinding()) {	
				break;
			}
		}
		return copyFinder.getAllFilesWithCopies();
	}

	private void addSubDirectories(List<File> directories) {
		String message = Words.get("MESSAGE_FIND_ALL_SUBDIRS")+"\n"+ FileOperations.toString(directories);
		MainWindow.getInstance().setText(message);
		List<File> subDirectories = getSubDirectoriesRecursively(directories);
		directories.addAll(subDirectories);		
	}

	public  List<File> getSubDirectoriesRecursively(List<File> directories) {
		List<File> subDirectories = new ArrayList<>();
		for(File dir: directories) {
			subDirectories.addAll(getSubDirectoriesRecursively(dir));
		}
		return subDirectories;
	}

	private  List<File> getSubDirectoriesRecursively(File dir) {
		List<File> subDirectories = new ArrayList<>();
		int counter = 1;
		File[] files = dir.listFiles();
		if(files == null) {
			return subDirectories;			
		}
		for(File file: files) {
			maybePublish(counter++);
			if(file.isDirectory()) {
				subDirectories.add(file);
				subDirectories.addAll(getSubDirectoriesRecursively(file));
			}
		}
		return subDirectories;
	}

	private void maybePublish(int counter) {
		if(counter % PUBLISH_CR_INTERVAL == 0) {
			publish("\n.");
		} else if(counter % PUBLISH_INTERVAL  == 0) {
			publish(".");
		}
	}

	/**
	 * Receive values from {@link Worker#publish(String...)}
	 */
	@Override
	protected void process(List<String> chunks) {
		// They may come grouped in chunks.
		String mostRecentValue = chunks.get(chunks.size()-1);
		log.debug("mostRecentValue="+mostRecentValue);
		MainWindow.getInstance().addSearchInfoText(mostRecentValue);
	}

	/**
	 * Receive the return value from {@link Worker#doInBackground()}
	 */
	@Override
	protected void done() {
		MainWindow.getInstance().stopFinding();
		AllFilesWithCopies allFilesWithCopies;
		try {
			allFilesWithCopies = get();
			if(allFilesWithCopies.isEmpty()) {
				MainWindow.getInstance().setText(Words.get("MESSAGE_NO_COPIES_FOUND"));
			} else {
				allFilesWithCopies.cleanUp();
			}
			MainWindow.getInstance().populateListPanel(allFilesWithCopies);
			log.debug(allFilesWithCopies.toString());
		} catch (InterruptedException e) {
			log.error("Interrupted", e);
		} catch (ExecutionException e) {
			log.error("ExecutionException", e);
		}
	}
}
