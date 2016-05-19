package dao;

import java.util.List;

import model.Benutzer;

public interface BenutzerDao {
	public void save(Benutzer benutzer);
	public void delete(Integer id);
	public Benutzer get(Integer id);
	public List<Benutzer> list();
}
