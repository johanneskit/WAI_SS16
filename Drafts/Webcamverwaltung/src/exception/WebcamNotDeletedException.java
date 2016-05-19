package exception;

public class WebcamNotDeletedException extends RuntimeException {
	
	public WebcamNotDeletedException(Integer id) {
		super("Webcam mit der Id " + id + " konnte nicht geändert werden!");
	}
}
