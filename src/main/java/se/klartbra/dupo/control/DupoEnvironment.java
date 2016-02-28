package se.klartbra.dupo.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Checks if the program is running inside a jar or from a java project like eclipse.<br>
 * This info is needed since the paths used at loading the font file differs (which is crazy).
 * 
 * @author svante
 *
 */
public class DupoEnvironment {
	
	private static Logger log = LogManager.getLogger(DupoEnvironment.class);

	private DupoEnvironment() {}

	public static boolean runningFromJar() {
		String path = DupoEnvironment.class.getResource("DupoEnvironment.class").toString();
		if(path.startsWith("file")) {
			log.debug("Running from eclipse or similar.");
			return false;
		}
		log.debug("Running from inside a jar.");
		return true;
	}

}
