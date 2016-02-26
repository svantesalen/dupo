package se.klartbra.dupo.view.components;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("serial")
public class DeleteButton extends FocusableButton { // NOSONAR

	private static Logger log = LogManager.getLogger(DeleteButton.class);

	public DeleteButton(String txt) {
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
		log.debug("DELETE!");
	}

}
