package exception;

public class WebcamNotFoundException extends RuntimeException {
	
	public WebcamNotFoundException(Integer id) {
		super("Webcam mit der Id " + id + " wurde nicht gefunden!");
	}
	
	public WebcamNotFoundException() {
		super("Webcams k�nnen nicht aufgelistet werden!");
	}
}
