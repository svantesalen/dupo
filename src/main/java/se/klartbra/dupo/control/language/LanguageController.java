package se.klartbra.dupo.control.language;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.klartbra.dupo.view.MainWindow;
import se.klartbra.dupo.view.PopUp;

/**
 * Change the language used in the application.
 * 
 * @author svante
 *
 */
public class LanguageController {

	private static Logger log = LogManager.getLogger(PopUp.class);

	public static final Language DEFAULT_LANGUAGE = Language.ENGLISH;


	private static Language currentLanguage = DEFAULT_LANGUAGE;
	private static String message = "Språk (Language)";
	private static String title = "Välj (Select)";
	private LanguageController() {}

	public enum Language {
		ENGLISH("en"), SWEDISH("se");
		private static final String BUNDLE_NAME = "i18n.LangBundle";
		private static final String BUNDLE_PATH = "resources.i18n.LangBundle";
		private String localeSuffix;

		Language(String localeSuffix) {
			this.localeSuffix = localeSuffix;
		}

		public ResourceBundle getResourceBundle() {
			log.debug("Suffix="+localeSuffix);
			if(runningFromJar()) {
				return ResourceBundle.getBundle(BUNDLE_PATH, new Locale(localeSuffix));
			} else {
				return ResourceBundle.getBundle(BUNDLE_NAME, new Locale(localeSuffix));
			}
		}

		public static Language [] getAllLanguages() {
			return Language.values();
		}
		@Override
		public String toString() {
			return name();
		}

		public static Language getLanguageFrom(String languageName) throws LanguageException {
			if(languageName != null && !languageName.trim().isEmpty()) {
				for(Language language: Language.values()) {
					if(languageName.trim().equalsIgnoreCase(language.name())) {
						return language;
					}
				}
			}
			throw new  LanguageException("Not a defined language: "+languageName);
		}
	}

	public static Language getCurrentLanguage() {
		return currentLanguage;
	}

	public static void setLanguage(String languageStr) throws LanguageException {
		Language language = Language.getLanguageFrom(languageStr);
		setLanguage(language);
	}

	public static void setLanguage(Language language) {
		currentLanguage = language;
		Words.bundle = language.getResourceBundle();
	}

	public static void newLanguage() {
		Language newLanguage = (Language) JOptionPane.showInputDialog(
				null,
				message,
				title,
				JOptionPane.PLAIN_MESSAGE,
				null,
				Language.getAllLanguages(),
				DEFAULT_LANGUAGE);
		if(newLanguage == null) {
			return;
		}
		setLanguage(newLanguage);
		MainWindow.getInstance().repaint();
	}

	private static boolean runningFromJar() {
		URL url = LanguageController.class.getResource("LanguageController.class");
		String path = url.toString();
		log.info("start path:"+path);
		if(path.startsWith("file")) {
			log.info("Running from eclipse or similar.");
			return false;
		}
		log.info("Running from inside a jar.");
		return true;
	}


}

