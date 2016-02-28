package se.klartbra.dupo.control.language;

/**
 * Used when language can't be loaded as expected.
 * @author svante
 *
 */
public class LanguageException extends Exception {

	private static final long serialVersionUID = 1L;

	public LanguageException(String message) {
		super(message);
	}

}
