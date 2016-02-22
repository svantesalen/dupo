package se.klartbra.dupo.view;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PopUp {

	private static Logger log = LogManager.getLogger(PopUp.class);
	public enum Answer {
		YES,NO
	}

	private PopUp() {}

	/**
	 * PopUp: chose directories.
	 * 
	 * @return Directories selected by user, or empty list if user aborts
	 */
	public static File[] letUserChooseDirectories() {
		return letUserChooseFromFileSystem(JFileChooser.DIRECTORIES_ONLY);
	}

	/**
	 * PopUp: chose files
	 * 
	 * @return Files selected by user, or empty list if user aborts
	 */
	public static File[] letUserChooseFiles() {
		return letUserChooseFromFileSystem(JFileChooser.FILES_ONLY);
	}

	private static File[] letUserChooseFromFileSystem(int mode) {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileSelectionMode(mode);
		int userOption = chooser.showOpenDialog(null);
		if (userOption==JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFiles();
		} 
		return new File[0];
	}

	public static Answer question (
			Component component, 
			String title,
			String text) 
	{
		log.debug("enter");
		int n = JOptionPane.showConfirmDialog(
				component, 
				text, 
				title,
				JOptionPane.YES_NO_OPTION);

		switch (n) {
		case JOptionPane.YES_OPTION: 
			return Answer.YES;
		case JOptionPane.NO_OPTION: 
		default: 
			return Answer.NO;
		}
	}
}
