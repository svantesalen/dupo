package se.klartbra.dupo.start;

import se.klartbra.dupo.view.MainWindow;
import se.klartbra.dupo.view.look.LookAndFeel;

/**
 * Programs start class.
 * 
 * The program will find equal directories.
 *
 * @author svante
 *
 */
public class StartHere {

	private StartHere() {}
	
	public static void main(String[] args) {
		LookAndFeel.set();
		MainWindow.createAndShowGui();
	}

	


	
}
