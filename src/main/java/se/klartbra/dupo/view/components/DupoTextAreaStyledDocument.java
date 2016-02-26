package se.klartbra.dupo.view.components;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.control.language.Words;
import se.klartbra.dupo.view.look.Colors;
import se.klartbra.dupo.view.look.DupoTheme;

public class DupoTextAreaStyledDocument {

	private static Logger log = LogManager.getLogger(DupoTextAreaStyledDocument.class);
	private static String delimiter = " ";
	private static String newHeaderDelimiter = "_____________________";

	private StyledDocument doc=null;
	private Style headerStyle=null;
	private Style headerDelimiterStyle=null;
	private Style typeStyle=null;
	private Style authorStyle=null;
	private Style dateStyle=null;
	private Style bodyStyle=null;
	private Style underlineStyle=null;
	private Style questionStyle=null;
	private Style questionExplanationStyle=null;
	private Style answerStyle=null;
	private Style answerExplanationStyle=null;
	private Style delimiterStyle=null;

	public DupoTextAreaStyledDocument(JTextPane textPane) {		
		textPane.setEditable(false);
		textPane.setText("");
		doc = textPane.getStyledDocument();
		setHeaderDelimiterStyle();
		setDupoNameStyle();
		setDupoTypeStyle();
		setDupoAuthorStyle();
		setDateStyle();
		setBodyStyle();
		setQuestionStyle();
		setQuestionExplanationStyle();
		setAnswerStyle();
		setAnswerExplanationStyle();
		setDelimiterStyle();
		
	}

	private void setHeaderDelimiterStyle() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		headerDelimiterStyle = doc.addStyle("headerDelimiterStyle",def);

		StyleConstants.setForeground(headerDelimiterStyle,Colors.darkYellow);
		StyleConstants.setFontFamily(headerDelimiterStyle, DupoTheme.getDefaultFontName());
		StyleConstants.setBold(headerDelimiterStyle, true);
		StyleConstants.setFontSize(headerDelimiterStyle, 12);
	}
	private void setDupoNameStyle() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		headerStyle = doc.addStyle("headerStyle",def);

		StyleConstants.setForeground(headerStyle,DupoTheme.textAreaHeaderColor);
		StyleConstants.setFontFamily(headerStyle, DupoTheme.getDefaultFontName());
		StyleConstants.setBold(headerStyle, false);
		StyleConstants.setFontSize(headerStyle, 22);
	}

	private void setDupoTypeStyle() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		typeStyle = doc.addStyle("typeStyle",def);

		StyleConstants.setForeground(typeStyle,DupoTheme.textAreaHeaderColor2);
		StyleConstants.setBold(typeStyle, false);
		StyleConstants.setFontFamily(typeStyle, DupoTheme.getDefaultFontName());
		StyleConstants.setFontSize(typeStyle, 20);
	}

	private void setDupoAuthorStyle() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		authorStyle = doc.addStyle("authorStyle",def);

		StyleConstants.setForeground(authorStyle,DupoTheme.textAreaHeaderColor2);
		StyleConstants.setBold(authorStyle, false);
		StyleConstants.setFontFamily(authorStyle, DupoTheme.getDefaultFontName());
		StyleConstants.setFontSize(authorStyle, 20);
	}

	private void setBodyStyle() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		bodyStyle = doc.addStyle("bodyStyle",def);
		StyleConstants.setFontFamily(bodyStyle, DupoTheme.getDefaultFontName());
		StyleConstants.setFontSize(bodyStyle, 14);
	}

	private void setDateStyle() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		dateStyle = doc.addStyle("dateStyle",def);

		StyleConstants.setForeground(dateStyle,DupoTheme.textAreaHeaderColor2);
		StyleConstants.setBold(dateStyle, false);
		StyleConstants.setFontFamily(dateStyle, DupoTheme.getDefaultFontName());
		StyleConstants.setFontSize(dateStyle, 20);
	}

	private void setQuestionStyle() {
		questionStyle = doc.addStyle("questionStyle",bodyStyle);
		StyleConstants.setForeground(questionStyle, Colors.lightYellow);
		StyleConstants.setFontFamily(questionStyle, DupoTheme.getDefaultFontName());
		StyleConstants.setBold(questionStyle, false);
	}

	private void setQuestionExplanationStyle() {
		questionExplanationStyle = doc.addStyle("questionExplanationStyle",questionStyle);
		StyleConstants.setBold(questionExplanationStyle, false);
		StyleConstants.setItalic(questionExplanationStyle, false);
		StyleConstants.setForeground(questionExplanationStyle, Colors.darkOrange);

	}

	private void setAnswerStyle() {
		answerStyle = doc.addStyle("answerStyle",bodyStyle);
		StyleConstants.setBold(answerStyle, false);
		StyleConstants.setForeground(answerStyle, Colors.darkOrange);
	}

	private void setAnswerExplanationStyle() {
		answerExplanationStyle = doc.addStyle("answerExplanationStyle",answerStyle);
		StyleConstants.setBold(answerExplanationStyle, false);
		StyleConstants.setItalic(answerExplanationStyle, false);
		StyleConstants.setForeground(answerExplanationStyle, Colors.darkOrange);
	}

	private void setDelimiterStyle() {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		delimiterStyle = doc.addStyle("delimiterStyle",def);
		StyleConstants.setForeground(delimiterStyle, Color.darkGray);
		StyleConstants.setBold(delimiterStyle, true);
		StyleConstants.setFontSize(delimiterStyle, 20);
	}

	public void insertNumberOfQuestionsText(String text) {
		insertStyledText(text, dateStyle);
	}
	public void insertDateText(String text) {
		insertStyledText(text, dateStyle);
	}
	public void insertHeaderText(String text) {
		log.info("#####################################");
		insertStyledText(text, questionStyle);
	}
	public void insertTypeText(String text) {
		insertStyledText(text, typeStyle);
	}
	public void insertAuthorText(String text) {
		if(text.isEmpty()) {
			return;
		}
		String txt = Words.get("BROWSER_TEXTAREA_BY") +" " +text;
		insertStyledText(txt, authorStyle);
	}
	public void insertUnderlinedText(String text) {
		insertStyledText(text, underlineStyle);
	}

	public void insertQuestionText(String text) {
		insertStyledText(text, questionStyle);
	}

	public void insertQuestionExplanationText(String text) {
		insertStyledText(text, questionExplanationStyle);
	}

	public void insertAnswerText(String text) {
		insertStyledText(text, answerStyle);
	}

	public void insertAnswerExplanationText(String text) {
		insertStyledText(text, answerExplanationStyle);
	}

	public void insertNewHeaderDelimiter() {
		insertStyledText(newHeaderDelimiter, headerDelimiterStyle);
		insertStyledText(" ", questionStyle);
	}
	
	public void insertDelimiter() {
		insertStyledText(delimiter, delimiterStyle);
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
