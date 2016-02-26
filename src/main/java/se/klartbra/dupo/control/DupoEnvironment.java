package se.klartbra.dupo.control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
