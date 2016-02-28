package se.klartbra.dupo.view.look;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.images.ImageLoader;
import se.klartbra.images.ImagePaths;

/**
 * Set basic look and feel for the application.
 * 
 * @author svante
 *
 */
public class LookAndFeel {

	private static Logger log = LogManager.getLogger(LookAndFeel.class);

	/**
	 * No CTOR needed.
	 */
	private LookAndFeel() {}

	public static void set() {
		Fonts.setApplicationFont();
		setUIManagerLook();
	}
	
	private static void setUIManagerLook() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			log.error(e);
		} catch (ClassNotFoundException e) {
			log.error(e);
		} catch (InstantiationException e) {
			log.error(e);
		} catch (IllegalAccessException e) {
			log.error(e);
		}

		UIManager.put("MenuItem.selectionBackground", Colors.lightWhite);
		UIManager.put("MenuItem.selectionForeground", DupoTheme.bgColor);
		UIManager.put("Menu.selectionBackground", Colors.lightWhite);
		UIManager.put("Menu.selectionForeground", DupoTheme.bgColor);
		UIManager.put("Menu.selectionForeground", DupoTheme.bgColor);

		UIManager.put("ToolTip.background", Colors.white);    
		UIManager.put("ToolTip.foreground", DupoTheme.bgColor);    
		Border border = BorderFactory.createLineBorder(Colors.white, 2);
		UIManager.put("ToolTip.border", border);

		UIManager.put("Panel.background", Colors.lighterGray);

		UIManager.put("Button.background",Colors.white);
		UIManager.put("Button.foreground",DupoTheme.bgColor);

		Font font = new Font(Fonts.getApplicationFont().getFontName(), Font.PLAIN, 20);
		UIManager.put("OptionPane.font", font);
		UIManager.put("OptionPane.messageFont", font);
		UIManager.put("OptionPane.buttonFont", font);
		UIManager.put("OptionPane.background", Colors.lighterGray);
		UIManager.put("OptionPane.errorDialog.border.background", DupoTheme.bgColor);
		UIManager.put("OptionPane.errorDialog.titlePane.background", DupoTheme.bgColor);
		UIManager.put("OptionPane.errorDialog.titlePane.foreground", DupoTheme.bgColor);
		UIManager.put("OptionPane.errorDialog.titlePane.shadow", DupoTheme.bgColor);
		UIManager.put("OptionPane.foreground", DupoTheme.bgColor);
		UIManager.put("OptionPane.messageBackground", Colors.lighterGray);
		UIManager.put("OptionPane.messageForeground", DupoTheme.bgColor);
		UIManager.put("OptionPane.questionDialog.titlePane.background", Colors.lighterGray);
		UIManager.put("OptionPane.questionDialog.titlePane.foreground",  DupoTheme.bgColor);
		UIManager.put("OptionPane.questionDialog.titlePane.shadow",  DupoTheme.bgColor);
		UIManager.put("OptionPane.questionDialog.border.background", Colors.lighterGray);
		UIManager.put("OptionPane.infoDialog.titlePane.background", Colors.lighterGray);
		UIManager.put("OptionPane.infoDialog.titlePane.foreground",  DupoTheme.bgColor);
		UIManager.put("OptionPane.infoDialog.titlePane.shadow",  DupoTheme.bgColor);
		UIManager.put("OptionPane.infoDialog.border.background", Colors.lighterGray);
		UIManager.put("OptionPane.warningDialog.border.background", Colors.lighterGray);
		UIManager.put("OptionPane.warningDialog.titlePane.background", Colors.lighterGray);
		UIManager.put("OptionPane.warningDialog.titlePane.foreground",  DupoTheme.bgColor);
		UIManager.put("OptionPane.warningDialog.titlePane.shadow", DupoTheme.bgColor);
		UIManager.put("OptionPane.questionIcon",ImageLoader.createIcon(ImagePaths.QUESTION_ICON_PATH, 40, 40));
		UIManager.put("OptionPane.errorIcon",ImageLoader.createIcon(ImagePaths.ERROR_ICON_PATH, 40, 40));
		UIManager.put("OptionPane.informationIcon", ImageLoader.createIcon(ImagePaths.INFO_ICON_PATH, 40, 40));		
	}
}