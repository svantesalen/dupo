package se.klartbra.dupo.view.components;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.control.language.Words;
import se.klartbra.dupo.view.PopUp;
import se.klartbra.dupo.view.PopUp.Answer;

/**
 * A button that breaks the current search for duplicates.
 * 
 * @author svante
 *
 */
@SuppressWarnings("serial")
public class BreakButton extends FocusableButton { // NOSONAR

	private static Logger log = LogManager.getLogger(BreakButton.class);

	public BreakButton(String txt) {
		super(txt);
	}

	@Override
	protected void handleKeyEvent(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			log.debug("CR key code:"+e.getKeyCode());					
			onClick();
		} else {
			log.debug("Not a CR key code:"+e.getKeyCode());					
		}				
	}

	@Override
	protected void handleActionEvent(ActionEvent e) {
		onClick();
	}

	private void onClick() {
		Answer answer = PopUp.question(this, Words.get("QUESTION_BREAK_TITLE"), Words.get("QUESTION_BREAK_TEXT"));
		if(answer == Answer.YES) {
			ButtonPanel.getInstance().finding(false);
		}
	}

}
