package se.klartbra.dupo.view.look;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.control.DupoEnvironment;
import se.klartbra.dupo.start.StartHere;

/**
 * Load the applications font from a TrueTypeFont file.
 * @author svante
 *
 */
public class Fonts {
	private static final String FONT_FILE_PATH = "/resources/fonts/DroidSans.ttf";
	private static Font applicationFont=null;
	private static Logger log = LogManager.getLogger(Fonts.class);

	private Fonts() {}

	public static Font getApplicationFont() {
		return applicationFont;
	}

	public static boolean setApplicationFont() {
		try {
			applicationFont=loadFont();
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			log.info("Registering application font: "+applicationFont.getFontName());
			return ge.registerFont(applicationFont);

		} catch (IOException | FontFormatException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		log.error("Could not load application font");
		applicationFont=null;
		return false;
	}

	private static Font loadFont() throws FontFormatException, IOException {
		log.debug("");
		if(DupoEnvironment.runningFromJar()) {
			log.debug("");
			try {
				log.debug("");
				return loadFontJar();				
			} catch (Exception e) {
				log.error(e);
				return  loadFontEclipse();								
			}			
		}
		try {
			return  loadFontEclipse();				
		} catch (Exception e) {
			log.error(e);
			return  loadFontJar();								
		}			
	}

	private static Font loadFontJar() throws FontFormatException, IOException {
		InputStream istream = StartHere.class.getClass().getResourceAsStream(FONT_FILE_PATH);
		return  Font.createFont(Font.TRUETYPE_FONT, istream);
		//		font = myFont.deriveFont(36.0f); // NOSONAR
	}


	private static Font loadFontEclipse() throws FontFormatException, IOException {
		log.debug("");
		ClassLoader classLoader = StartHere.class.getClassLoader();
		File file = new File(classLoader.getResource("fonts/DroidSans.ttf").getFile());
		return Font.createFont(Font.TRUETYPE_FONT, file);
	}
}
