package exception;

public class BenutzerNotFoundException extends RuntimeException {
	
	public BenutzerNotFoundException(Integer id) {
		super("Benutzer mit der Id " + id + " wurde nicht gefunden!");
	}
	
	public BenutzerNotFoundException() {
		super("Benutzer können nicht aufgelistet werden!");
	}
}
