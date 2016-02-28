package se.klartbra.dupo.view.look;

import java.awt.Color;
import java.awt.Font;


/**
 * A selection of colors and fonts to get a theme.
 * 
 * @author svante
 *
 */
public class DupoTheme {

	public static final Font buttons = new Font(getDefaultFontName(), Font.BOLD, 20);
	public static final Font popUp = new Font(getDefaultFontName(), Font.PLAIN, 16);
	public static final Font popUpHeading = new Font(getDefaultFontName(), Font.PLAIN, 18);
	public static final Font jListFontSelectedItem = new Font(getDefaultFontName(), Font.PLAIN, 16);
	public static final Font jListFont = new Font(getDefaultFontName(), Font.PLAIN, 16);
	public static final Font borderTitleFont = new Font(getDefaultFontName(), Font.PLAIN, 13);
	
	public static final Color textAreaResultColor = Colors.white;
	public static final Color textAreaProgressColor = Colors.lightYellow;

	public static final Color button_txt = Colors.white;
	public static final Color bgColor = Colors.blueGreen;
	public static final Color borderTitleColor = Colors.lightBlueGreen;
	public static final Color jListFontSelectedItemColor = Colors.darkYellow;
	
	public static final Color buttonGainedFocusBgColor = Colors.darkYellow;
	public static final Color buttonGainedFocusFgColor = Colors.black;

	public static final Color buttonLostFocusBgColor = bgColor;
	public static final Color buttonLostFocusFgColor = Colors.white;
	

	public static final Color lightGray = new Color(203, 203, 203);
	public static final Color lightYellow = new Color(255, 195, 94);
	
	private DupoTheme() {}

	public static String getDefaultFontName() {
		return Fonts.getApplicationFont().getFontName();
	}

}
