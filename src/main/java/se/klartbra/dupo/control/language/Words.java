package se.klartbra.dupo.control.language;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Words {
	protected static ResourceBundle bundle = null;
	private static Logger log = LogManager.getLogger(Words.class);

	private Words() {}

	public static String get(String keyword) {
		if (bundle == null) {
			bundle = LanguageController.DEFAULT_LANGUAGE.getResourceBundle();
		}
		try {
			return bundle.getString(keyword);
		} catch (MissingResourceException e) {
			throw e;
		}

	}

	public static String[] getStringArray(String keyword) {
		if (bundle == null) {
			bundle = LanguageController.DEFAULT_LANGUAGE.getResourceBundle();
		}
		return bundle.getStringArray(keyword);
	}

	public static void printAll() {
		for(String key: bundle.keySet()) {
			String value = bundle.getString(key);
			if(value.trim().isEmpty()) {
				value = "****** EMPTY ******";
			}
			log.info(value);
		}
	}

}