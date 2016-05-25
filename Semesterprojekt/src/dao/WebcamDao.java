package dao;

import java.util.List;

import model.Webcam;

public interface WebcamDao {
	public void save(Webcam webcam, String user_id);
	public void delete(Integer id);
	public Webcam get(Integer id);
	public List<Webcam> list();
}
