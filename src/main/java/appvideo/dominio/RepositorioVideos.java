package appvideo.dominio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import appvideo.persistencia.DAOException;
import appvideo.persistencia.FactoriaDAOAbstracto;

public class RepositorioVideos {
	private static RepositorioVideos unicaInstancia;
	private FactoriaDAOAbstracto factoria;

	private HashMap<Integer, Video> coleccionVideosID;
	private HashMap<String, Video> coleccionVideosTitulo;

	public static RepositorioVideos getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new RepositorioVideos();
		return unicaInstancia;
	}

	private RepositorioVideos() {
		coleccionVideosID = new HashMap<Integer, Video>();
		coleccionVideosTitulo = new HashMap<String, Video>();
		try {
			factoria = FactoriaDAOAbstracto.getInstancia();
			LinkedList<Video> listaVideos = factoria.getVideoDAO().obtenerTodosVideos();
			for (Video video : listaVideos) {
				coleccionVideosID.put(video.getId(), video);
				coleccionVideosTitulo.put(video.getTitulo(), video);
			}
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}

	public LinkedList<Video> getVideos() throws DAOException {
		return new LinkedList<Video>(coleccionVideosTitulo.values());
	}

	public Video getVideo(int id) {
		return coleccionVideosID.get(id);
	}

	public Video getVideo(String titulo) {
		return coleccionVideosTitulo.get(titulo);
	}

	public void addVideo(Video video) {
		coleccionVideosID.put(video.getId(), video);
		coleccionVideosTitulo.put(video.getTitulo(), video);
	}

	public void removeVideo(Video video) {
		coleccionVideosID.remove(video.getId());
		coleccionVideosTitulo.remove(video.getTitulo());
	}

	public List<Video> explorarVideos(String titulo, LinkedList<Etiqueta> etiquetas) throws DAOException {
		List<Video> videos = new LinkedList<>();
		for (Video video : coleccionVideosTitulo.values()) {
			if (( video.getTitulo().contains(titulo) && video.tieneEtiqueta(etiquetas))  || (titulo.isEmpty() && video.tieneEtiqueta(etiquetas)))
				videos.add(video);
		}
		return videos;
	}

}
