package exception;

public class WebcamNotSavedException extends RuntimeException {
	
	public WebcamNotSavedException() {
		super("Webcam konnte nicht gespeichert werden!");
	}
}
