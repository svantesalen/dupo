package se.klartbra.dupo.control.network;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Use to start users browser (Firefox, Chrome...)
 *  
 * @author svante
 *
 */
public class HttpBrowserController {
	private static Logger log = LogManager.getLogger(HttpBrowserController.class);

	private HttpBrowserController() {}

	public static boolean open(String url) {
		String server = url;
		if(url==null||url.isEmpty() ) {
			server = Paths.GOOGLE_SEARCH_URL;
		}
		log.info(server);
		if(Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(new URI(url));
				return true;
			} catch (IOException | URISyntaxException e) {
				log.error(e);	
			}
		} else {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("xdg-open " + url);
				return true;
			} catch (IOException e) {
				log.error(e);	
			}
		}
		return false;
	}
}

