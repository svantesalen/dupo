package se.klartbra.dupo.view.components;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import se.klartbra.dupo.control.language.LanguageController;
import se.klartbra.dupo.control.language.Words;
import se.klartbra.dupo.model.FileWithCopies;
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
		initiateContetns();

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

	public void initiateContetns() {
		textPane.setEditable(false);
		textPane.setText("");
		styledDoc = new DupoTextAreaStyledDocument(textPane);
	}

	public void setText(String text) {
		textPane.setText("");
		styledDoc.insertTypeText(text);
	}
	public void addText(String text) {
		styledDoc.insertTypeText(text);
	}

	public void addSearchInfoText(String text) {
		styledDoc.insertHeaderText(text);
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
		if (selectedText == null || selectedText.trim().isEmpty()) {
			return;
		}
		StringSelection stringSelection = new StringSelection(selectedText);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);		
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