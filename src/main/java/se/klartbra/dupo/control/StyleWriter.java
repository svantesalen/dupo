package se.klartbra.dupo.control;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.klartbra.dupo.control.filehandling.FileOperations;
import se.klartbra.dupo.control.language.Words;
import se.klartbra.dupo.model.FileWithCopies;
import se.klartbra.dupo.view.components.DupoTextArea;
import se.klartbra.dupo.view.components.DupoTextAreaStyledDocument;

/**
 * Writes styled text to be put out in a text area. Style will depend on the contents.
 * 
 * @author svante
 *
 */
public class StyleWriter {

	private FileWithCopies fileWithCopies;
	private DupoTextArea textArea;
	private DupoTextAreaStyledDocument styledDoc;

	private boolean foundSymlinkOnPath=false;
	
	public StyleWriter(DupoTextArea textArea, FileWithCopies fileWithCopies) {
		this.textArea = textArea;
		this.styledDoc = textArea.getDupoTextAreaStyledDocument();
		this.fileWithCopies = fileWithCopies;
	}

	
	public void write() {
		textArea.initiateContents();
		if(Controller.getInstance().isHelpOn()) {
		addExplanation();
		}
		writePath(fileWithCopies.getFile());
		if(foundSymlinkOnPath) {
			addInfoAboutSymlink();
			foundSymlinkOnPath = false;
		} else {
			addSizeInfo(fileWithCopies.getFile());
		}
		styledDoc.insertInfoText("\n");

		for(File file: fileWithCopies.getAllCopies()) {
			writePath(file);
			if(foundSymlinkOnPath) {
				addInfoAboutSymlink();
				foundSymlinkOnPath = false;
			} else {
				addSizeInfo(fileWithCopies.getFile());
			}
			styledDoc.insertInfoText("\n");
		}
		textArea.setCaretTopOfDoc();
	}

	/**
	 * Write dir by dir starting from top. Other style for symbolic links.
	 * 
	 * @param file
	 */
	private void writePath(File file) {
		// Add size?
		// Empty dir other color?
		List<File> family = getFamily(file);
		writeWithStyleStart(family.get(0));
		int i;
		for(i=1; i<family.size(); i++) {
			styledDoc.insertResultText(Character.toString(File.separatorChar));
			writeWithStyleContinue(family.get(i)); 			
		}
	}

	private List<File> getFamily(File file) {
		List<File> family = new ArrayList<>();
		family.add(file);
		File parent = file.getParentFile();
		while(parent != null) {
			family.add(parent);
			parent = parent.getParentFile();
		}
		Collections.reverse(family);
		return family;
	}

	/**
	 * The first directory may have prefix (like c:\\) so better write full path.
	 * @param file
	 */
	private void writeWithStyleStart(File file) {
		writeWithStyle(file, file.getAbsolutePath());

	}
	private void writeWithStyleContinue(File file) {
		writeWithStyle(file, file.getName());		
	}

	private void writeWithStyle(File file, String text) {
		if(FileOperations.isSymlink(file)) {
			styledDoc.insertSymbolicLinkText(text);
			foundSymlinkOnPath = true;
		} else {
			styledDoc.insertResultText(text);
		}
	}

	private void addSizeInfo(File file) {
		int numberOfDirs = FileOperations.getNumberOfSubDirectories(file);
		int numberOfFiles = FileOperations.getNumberOfFiles(file);
		if(numberOfDirs == 0 && numberOfFiles == 0) {
			styledDoc.insertEmptyDirText(Words.get("EMPTY_DIR"));			
		} else {
			styledDoc.insertNumberOfFilesText("    "+Integer.toString(numberOfDirs)+".");
			styledDoc.insertNumberOfSubDirsText(" "+Integer.toString(numberOfFiles)+"/");
		}
	}

	private void addInfoAboutSymlink() {
		styledDoc.insertSymbolicLinkText("       "+Words.get("SYMBOLIC_LINK"));
	}

	private void addExplanation() {
		styledDoc.insertUnderlinedInfoText(Words.get("EXPLANATION"));	
		styledDoc.insertInfoText("\n\n");
		styledDoc.insertSymbolicLinkText(Words.get("SYMBOLIC_LINK"));
		styledDoc.insertInfoText(" = "+Words.get("THIS_IS_A_LINK"));
		styledDoc.insertInfoText("\n");
		
		styledDoc.insertNumberOfFilesText("2.  ");
		styledDoc.insertInfoText(" = " + Words.get("THIS_IS_NUMBER_OF_FILES"));	
		styledDoc.insertInfoText("\n");
		
		styledDoc.insertNumberOfSubDirsText("3/  ");
		styledDoc.insertInfoText(" = " + Words.get("THIS_IS_NUMBER_OF_SUBDIRS"));	
		styledDoc.insertInfoText("\n\n");
		styledDoc.insertUnderlinedInfoText(Words.get("COPIES_FOUND"));	
		styledDoc.insertInfoText("\n\n");
	}
		
}
