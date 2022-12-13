package appvideo.persistencia;

import appvideo.dominio.ListaVideos;

public interface IListaVideosDAO {
	
	void crearListaVideos(ListaVideos listavideos);
	boolean borrarListaVideos(ListaVideos listavideos);
	void modificarListaVideos(ListaVideos listavideos);
	ListaVideos obtenerListaVideos(int id);
}
