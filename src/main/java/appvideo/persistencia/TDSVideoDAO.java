package appvideo.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import appvideo.dominio.Etiqueta;
import appvideo.dominio.Video;
import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class TDSVideoDAO implements IVideoDAO{
	
	private static final String VIDEO = "video"; 
	
	private static final String TITULO = "titulo";
	private static final String URL = "url";
	private static final String ETIQUETAS = "etiquetas";
	private static final String NUM_REPRODUCCIONES = "numReproducciones";

	private static TDSVideoDAO unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;
	
	/* Mantenemos una única creación de esta clase */ 
	public static TDSVideoDAO getUnicaInstancia() {
		if (unicaInstancia == null)
			return new TDSVideoDAO();
		else
			return unicaInstancia;

	}
	
	/* Constructor para TDSVideoDAO, función private para controlar su única instancia */
	private TDSVideoDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	private Entidad videoToEntidad(Video video) {
		Entidad eVideo = new Entidad();
		eVideo.setNombre(VIDEO);

		eVideo.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(TITULO, video.getTitulo()),
				new Propiedad(URL, video.getUrl()), 
				new Propiedad(ETIQUETAS, parseEtiquetas(video.getEtiquetas())),
				new Propiedad(NUM_REPRODUCCIONES, String.valueOf(video.getNumReproducciones())))));
		return eVideo;
	}

	@Override
	public void crearVideo(Video video) {
		// Creo la entidad de video
		Entidad eVideo = this.videoToEntidad(video);
		// Registro su identidad en el servicio de persistencia
		eVideo = servPersistencia.registrarEntidad(eVideo);
		// Al video se le asigna su ID único que crea el servicio de persistencia
		video.setId(eVideo.getId());
		
	}

	@Override
	public boolean borrarVideo(Video video) {
		Entidad eVideo;
		eVideo = servPersistencia.recuperarEntidad(video.getId());
		return servPersistencia.borrarEntidad(eVideo);
	}

	@Override
	public void modificarVideo(Video video) {
		Entidad eVideo = servPersistencia.recuperarEntidad(video.getId());

		for (Propiedad prop : eVideo.getPropiedades()) {
			if (prop.getNombre().equals(TITULO)) {
				prop.setValor(video.getTitulo());
			} else if (prop.getNombre().equals(URL)) {
				prop.setValor(video.getUrl());
			} else if (prop.getNombre().equals(ETIQUETAS)) {
				String etiquetas = parseEtiquetas(video.getEtiquetas());
				prop.setValor(etiquetas);
			} else if (prop.getNombre().equals(NUM_REPRODUCCIONES)) {
				prop.setValor(String.valueOf(video.getNumReproducciones()));
			}
			servPersistencia.modificarPropiedad(prop);
		}
		
	}
	
	private Video entidadToVideo(Entidad eVideo) {
		
		String titulo = servPersistencia.recuperarPropiedadEntidad(eVideo, TITULO);
		String url = servPersistencia.recuperarPropiedadEntidad(eVideo, URL);
		LinkedList<Etiqueta> etiquetas;
		etiquetas = parseEtiquetas2(servPersistencia.recuperarPropiedadEntidad(eVideo, ETIQUETAS));
		int numReproducciones = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eVideo, NUM_REPRODUCCIONES));
		
		Video video = new Video(titulo, url);
		video.setId(eVideo.getId());
		video.setEtiquetas(etiquetas);
		video.setNumReproducciones(numReproducciones);

		return video;
	}

	@Override
	public Video obtenerVideo(int id) {
		Entidad eVideo = servPersistencia.recuperarEntidad(id);

		return entidadToVideo(eVideo);
	}

	@Override
	public LinkedList<Video> obtenerTodosVideos() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades(VIDEO);

		LinkedList<Video> videos = new LinkedList<Video>();
		for (Entidad eVideo : entidades) {
			videos.add(obtenerVideo(eVideo.getId()));
		}

		return videos;
	}
	
	//TODO Revisar estas dos funciones
	private String parseEtiquetas(LinkedList<Etiqueta> etiquetas) {
		String etiquetasJuntas = "";
		for (Etiqueta etiqueta : etiquetas) {
			etiquetasJuntas += etiqueta.getId() + " ";
		}
		return etiquetasJuntas.trim();
	}
	
	private LinkedList<Etiqueta> parseEtiquetas2(String etiquetasJuntas) {
		LinkedList<Etiqueta> etiquetas = new LinkedList<Etiqueta>();
		StringTokenizer strTok = new StringTokenizer(etiquetasJuntas, " ");
		TDSEtiquetaDAO etiquetaDAO = TDSEtiquetaDAO.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			etiquetas.add(etiquetaDAO.obtenerEtiqueta(Integer.valueOf((String)strTok.nextElement())));
		}
		return etiquetas;
	}
}
