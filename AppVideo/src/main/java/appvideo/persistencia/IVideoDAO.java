package appvideo.persistencia;

import java.util.LinkedList;

import appvideo.dominio.Video;

public interface IVideoDAO {
	
	void crearVideo(Video video);
	boolean borrarVideo(Video video);
	void modificarVideo(Video video);
	Video obtenerVideo(int id);
	LinkedList<Video> obtenerTodosVideos();
}
