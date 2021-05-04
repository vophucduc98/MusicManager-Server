package service;

import java.util.List;

import model.SongDTO;

public interface SongDAOMbean{
	public void add(SongDTO dto) throws InterruptedException;
	public void delete(int id) throws InterruptedException;
	public void update(SongDTO dto) throws InterruptedException;
	public List<SongDTO> findAll();
	public SongDTO findOne(int id);
}
