package site;

public class Image {
	int id;
	String text;
	int prio;

	public Image(int id, String text, int prio) {
		this.id = id;
		this.text = text;
		this.prio = prio;
	}
	
	public int getPrio() {
		return prio;
	}

	public int getID() {
		return id;
	}

	public String getText() {
		return text;
	}
}
