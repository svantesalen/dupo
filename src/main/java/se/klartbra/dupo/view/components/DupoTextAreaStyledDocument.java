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
		setProgressStyle();
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

	public void insertStyledText(String text, Style style) {
		try {
			doc.insertString(doc.getLength(), text, style );
			doc.insertString(doc.getLength(), "\n", style );
		} catch (BadLocationException e) {
			log.error(e);
		}
	}

}
