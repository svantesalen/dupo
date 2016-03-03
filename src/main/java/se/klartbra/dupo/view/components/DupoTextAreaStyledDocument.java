package se.klartbra.dupo.view.components;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.view.look.DupoTheme;

/**
 * Document used to write formatted text in the text pane.
 * @author svante
 *
 */
public class DupoTextAreaStyledDocument {

	private static Logger log = LogManager.getLogger(DupoTextAreaStyledDocument.class);
	private StyledDocument doc;
	private Style resultStyle;
	private Style numberOfSubDirsStyle;
	private Style numberOfFilesStyle;
	private Style symbolicLinkStyle;
	private Style emptyDirStyle;
	private Style infoStyle;
	private Style underlinedInfoStyle;
	private Style progressStyle;

	/**
	 * CTOR
	 * @param textPane
	 */
	public DupoTextAreaStyledDocument(JTextPane textPane) {		
		textPane.setEditable(false);
		textPane.setText("");
		doc = textPane.getStyledDocument();
		setResultStyle();
		setSymbolicLinkStyle();
		setEmptyDirStyle();
		setNumberOfSubDirsStyleStyle();
		setNumberOfFilesStyleStyle();
		setProgressStyle();
		setInfoStyle();
		setUnderlinedInfoStyle();
	}

	/**
	 * Used for presenting final result.
	 */
	private void setResultStyle() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		resultStyle = doc.addStyle("resultStyle",def);

		StyleConstants.setForeground(resultStyle,DupoTheme.textAreaResultColor);
		StyleConstants.setFontFamily(resultStyle, DupoTheme.getDefaultFontName());
		StyleConstants.setBold(resultStyle, false);
		StyleConstants.setFontSize(resultStyle, 14);
	}
	
	/**
	 * Used for presenting symbolic link.
	 */
	private void setSymbolicLinkStyle() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		symbolicLinkStyle = doc.addStyle("symbolicLinkStyle",def);

		StyleConstants.setForeground(symbolicLinkStyle,DupoTheme.textAreaSymbolicLinkColor);
		StyleConstants.setFontFamily(symbolicLinkStyle, DupoTheme.getDefaultFontName());
		StyleConstants.setBold(symbolicLinkStyle, false);
		StyleConstants.setItalic(symbolicLinkStyle, true);
		StyleConstants.setFontSize(symbolicLinkStyle, 14);
	}

	/**
	 * Used for presenting number of sub-directories.
	 */
	private void setNumberOfSubDirsStyleStyle() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		numberOfSubDirsStyle = doc.addStyle("numberOfSubDirsLinkStyle",def);

		StyleConstants.setForeground(numberOfSubDirsStyle,DupoTheme.textAreaNumberOfSubDirsColor);
		StyleConstants.setFontFamily(numberOfSubDirsStyle, DupoTheme.getDefaultFontName());
		StyleConstants.setBold(numberOfSubDirsStyle, false);
		StyleConstants.setFontSize(numberOfSubDirsStyle, 14);
	}

	/**
	 * Used for presenting number of files.
	 */
	private void setNumberOfFilesStyleStyle() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		numberOfFilesStyle = doc.addStyle("numberOfFilesStyle",def);

		StyleConstants.setForeground(numberOfFilesStyle,DupoTheme.textAreaNumberOfFilesColor);
		StyleConstants.setFontFamily(numberOfFilesStyle, DupoTheme.getDefaultFontName());
		StyleConstants.setBold(numberOfFilesStyle, false);
		StyleConstants.setFontSize(numberOfFilesStyle, 14);
	}

	/**
	 * Used for presenting info.
	 */
	private void setInfoStyle() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		infoStyle = doc.addStyle("infoStyle",def);

		StyleConstants.setForeground(infoStyle,DupoTheme.textAreaInfoColor);
		StyleConstants.setFontFamily(infoStyle, DupoTheme.getDefaultFontName());
		StyleConstants.setItalic(infoStyle, false);
		StyleConstants.setBold(infoStyle, false);
		StyleConstants.setFontSize(infoStyle, 14);
	}
	private void setUnderlinedInfoStyle() {
		underlinedInfoStyle = doc.addStyle("underlinedInfoStyle",infoStyle);
		StyleConstants.setUnderline(underlinedInfoStyle, true);

	}

	private void setEmptyDirStyle() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		emptyDirStyle = doc.addStyle("emptyDirStyle",def);

		StyleConstants.setForeground(emptyDirStyle,DupoTheme.textAreaEmptyDirColor);
		StyleConstants.setFontFamily(emptyDirStyle, DupoTheme.getDefaultFontName());
		StyleConstants.setItalic(emptyDirStyle, false);
		StyleConstants.setBold(emptyDirStyle, false);
		StyleConstants.setFontSize(emptyDirStyle, 14);
	}
	/**
	 * Used for presenting progress information.
	 */
	private void setProgressStyle() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		progressStyle = doc.addStyle("progressStyle",def);

		StyleConstants.setForeground(progressStyle,DupoTheme.textAreaProgressColor);
		StyleConstants.setBold(progressStyle, false);
		StyleConstants.setFontFamily(progressStyle, DupoTheme.getDefaultFontName());
		StyleConstants.setFontSize(progressStyle, 12);
	}

	public void insertProgressText(String text) {
		insertStyledText(text, progressStyle);
	}

	public void insertResultText(String text) {
		insertStyledText(text, resultStyle);
	}

	public void insertSymbolicLinkText(String text) {
		insertStyledText(text, symbolicLinkStyle);
	}

	public void insertInfoText(String text) {
		insertStyledText(text, infoStyle);
	}

	public void insertEmptyDirText(String text) {
		insertStyledText(text, emptyDirStyle);
	}

	public void insertUnderlinedInfoText(String text) {
		insertStyledText(text, underlinedInfoStyle);
	}

	public void insertNumberOfSubDirsText(String text) {
		insertStyledText(text, numberOfSubDirsStyle);
	}

	public void insertNumberOfFilesText(String text) {
		insertStyledText(text, numberOfFilesStyle);
	}

	public void insertStyledText(String text, Style style) {
		try {
			doc.insertString(doc.getLength(), text, style );
		} catch (BadLocationException e) {
			log.error(e);
		}
	}

}
