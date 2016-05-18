package utils;

import java.net.MalformedURLException;
import java.net.URL;

public class WebCam {
	
	String cam_name = null;
	URL    url      = null;
	int    cam_id   = -1;
	
	public String getCamName() {
		return cam_name;
	}
	public void setCamName(String name) {
		this.cam_name = name;
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(String url) throws MalformedURLException {
		this.url = new URL(url);
	}
	public int getCamID() {
		return cam_id;
	}
	public void setCamID(int id) {
		this.cam_id = id;
	}
}
