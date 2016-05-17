package utils;

import java.net.MalformedURLException;
import java.net.URL;

public class WebCam {
	
	String name = null;
	URL    url  = null;
	int    prio = 0;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(String url) throws MalformedURLException {
		this.url = new URL(url);
	}
	public int getPrio() {
		return prio;
	}
	public void setPrio(int prio) {
		this.prio = prio;
	}
}
