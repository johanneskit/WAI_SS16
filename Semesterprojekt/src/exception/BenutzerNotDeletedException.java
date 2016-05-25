package exception;

public class BenutzerNotDeletedException extends RuntimeException {
	
	public BenutzerNotDeletedException(Integer id) {
		super("Benutzer mit der Id " + id + " konnte nicht geändert werden!");
	}
}
