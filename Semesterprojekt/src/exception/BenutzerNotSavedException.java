package exception;

public class BenutzerNotSavedException extends RuntimeException {
	
	public BenutzerNotSavedException() {
		super("Benutzer konnte nicht gespeichert werden!");
	}
}
