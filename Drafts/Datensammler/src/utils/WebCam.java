package utils;

import java.net.MalformedURLException;
import java.net.URL;

public class WebCam {
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
	
	String name = null;
	URL url = null;
}
