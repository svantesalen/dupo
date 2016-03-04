package se.klartbra.dupo.view.components;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.control.language.LanguageController;
import se.klartbra.dupo.control.language.Words;
import se.klartbra.dupo.model.FileWithCopies;
import se.klartbra.dupo.view.PopUp;
import se.klartbra.dupo.view.look.DupoTheme;

/**
 * A text area used for:
 * <ul>
 * <li>list all copies within the selected {@link FileWithCopies}</li>
 * <li>show messages during search</li>
 * </ul>
 * 
 * @author svante
 * 
 */
public class DupoTextArea implements PopUpHandler {
	private static Logger log = LogManager.getLogger(DupoTextArea.class);
	private static DupoTextArea instance;
	private JTextPane textPane  = new JTextPane();
	private JScrollPane scrollPane;
	private DupoTextAreaStyledDocument styledDoc;
	private TitledBorder border;
	private PopUpListener popUpListener;

	/**
	 * CTOR
	 */
	public DupoTextArea() {
		instance = this;

		textPane.setText("");
		textPane.setBackground(DupoTheme.bgColor);
		scrollPane  = new JScrollPane(textPane);
		scrollPane.setBackground(DupoTheme.bgColor);

		initiateBorders();
		initiateContents();

		popUpListener = new PopUpListener(this);
		textPane.addMouseListener(popUpListener);
	}

	public static DupoTextArea getInstance() {
		return instance;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setCaretTopOfDoc() {
		textPane.setCaretPosition(0);
	}

	public DupoTextAreaStyledDocument getDupoTextAreaStyledDocument() {
		return styledDoc;
	}

	public void initiateContents() {
		textPane.setEditable(false);
		textPane.setText("");
		styledDoc = new DupoTextAreaStyledDocument(textPane);
	}

	public void setText(String text) {
		textPane.setText("");
		styledDoc.insertResultText(text);
	}
	public void addText(String text) {
		styledDoc.insertResultText(text);
	}

	public void addSearchInfoText(String text) {
		styledDoc.insertProgressText(text);
	}

	public String getText() {
		return textPane.getText();
	}

	private void initiateBorders() {
		border = BorderFactory.createTitledBorder(Words.get("BORDER_TEXT_DETAILS"));
		border.setTitleFont(DupoTheme.borderTitleFont);
		border.setTitleColor(DupoTheme.borderTitleColor);
		textPane.setBorder(border);

		Border emptyBorder = BorderFactory.createEmptyBorder();
		scrollPane.setBorder(emptyBorder);		
	}

	@Override
	public void copy() {
		String selectedText = textPane.getSelectedText();
		String path = getPath(selectedText);
		log.info("path="+path);
		StringSelection stringSelection = new StringSelection(path);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);		

		try {
			File file = new File (path);
			Desktop desktop = Desktop.getDesktop();
			desktop.open(file);
		} catch (NullPointerException | IOException | UnsupportedOperationException | IllegalArgumentException e) {
			log.error("Not a file or cannot start desktop: "+path, e);
		}
	}

	private String getPath(String text) {
		log.info("text="+text);
		try {
			int beginIndex = 0;
			if(text.startsWith("//")) {
				beginIndex++;
			}
			int endIndex = text.lastIndexOf("  ");
			String path = text.substring(beginIndex, endIndex);
			return path.trim();
		} catch (RuntimeException e) {
			log.error("Not a path", e);
			PopUp.errorMessage(null, Words.get("MESSAGE_NOT_A_PATH"));
			return "";
		}
	}

	@Override
	public void language() {
		LanguageController.newLanguage();		
	}

	public void repaint() {
		border.setTitle(Words.get("BORDER_TEXT_DETAILS"));
		textPane.repaint();
		popUpListener.repaint();
	}

}