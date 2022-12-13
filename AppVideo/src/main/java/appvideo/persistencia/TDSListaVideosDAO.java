package appvideo.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

import appvideo.dominio.ListaVideos;
import appvideo.dominio.Video;
import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class TDSListaVideosDAO implements IListaVideosDAO {

	private static final String LISTA_VIDEOS = "listavideos";

	private static final String NOMBRE = "nombre";
	private static final String VIDEOS = "videos";

	private static TDSListaVideosDAO unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;

	public static TDSListaVideosDAO getUnicaInstancia() {
		if (unicaInstancia == null)
			return new TDSListaVideosDAO();
		else
			return unicaInstancia;
	}

	private TDSListaVideosDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	private Entidad listaToEntidad(ListaVideos listavideos) {
		Entidad eListaVideo = new Entidad();
		eListaVideo.setNombre(LISTA_VIDEOS);

		eListaVideo.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(NOMBRE, listavideos.getNombre()),
				new Propiedad(VIDEOS, parseVideos(listavideos.getVideos())))));
		return eListaVideo;
	}
	
	

	@Override
	public void crearListaVideos(ListaVideos listavideos) {
		// Creo la entidad de lista video
		Entidad eListaVideo = this.listaToEntidad(listavideos);
		// Registro su identidad en el servicio de persistencia
		eListaVideo = servPersistencia.registrarEntidad(eListaVideo);
		// A la lista se le asigna su ID único que crea el servicio de persistencia
		listavideos.setId(eListaVideo.getId());

	}

	@Override
	public boolean borrarListaVideos(ListaVideos listavideos) {
		Entidad eListaVideo;
		eListaVideo = servPersistencia.recuperarEntidad(listavideos.getId());
		return servPersistencia.borrarEntidad(eListaVideo);
	}

	@Override
	public void modificarListaVideos(ListaVideos listavideos) {
		Entidad eListaVideo = servPersistencia.recuperarEntidad(listavideos.getId());

		for (Propiedad prop : eListaVideo.getPropiedades()) {
			if (prop.getNombre().equals(NOMBRE)) {
				prop.setValor(listavideos.getNombre());
			} else if (prop.getNombre().equals(VIDEOS)) {
				String videos = parseVideos(listavideos.getVideos());
				prop.setValor(videos);
			}
			servPersistencia.modificarPropiedad(prop);
		}

	}

	private ListaVideos entidadToVideo(Entidad eListaVideo) {
		
		String titulo = servPersistencia.recuperarPropiedadEntidad(eListaVideo, NOMBRE);
		LinkedList<Video> videos;
		videos = parseVideos2(servPersistencia.recuperarPropiedadEntidad(eListaVideo, VIDEOS));
		
		ListaVideos listaVideos = new ListaVideos(titulo, videos);
		listaVideos.setId(eListaVideo.getId());

		return listaVideos;
	}
	
	@Override
	public ListaVideos obtenerListaVideos(int id) {
		Entidad eListaVideo = servPersistencia.recuperarEntidad(id);

		return entidadToVideo(eListaVideo);
	}
	
	private String parseVideos(LinkedList<Video> videos) {
		String videosJuntos = "";
		for (Video video : videos) {
			videosJuntos += video.getId() + " ";
		}
		return videosJuntos.trim();
	}
	
	private LinkedList<Video> parseVideos2(String videosJuntos) {
		LinkedList<Video> videos = new LinkedList<Video>();
		StringTokenizer strTok = new StringTokenizer(videosJuntos, " ");
		TDSVideoDAO videoDAO = TDSVideoDAO.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			videos.add(videoDAO.obtenerVideo(Integer.valueOf((String)strTok.nextElement())));
		}
		return videos;
	}

}
