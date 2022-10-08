package appvideo.controlador;

import appvideo.persistencia.IUsuarioDAO;
import appvideo.persistencia.IVideoDAO;
import umu.tds.componente.*;
import appvideo.persistencia.DAOException;
import appvideo.persistencia.TDSFactoriaDAO;
import appvideo.persistencia.FactoriaDAOAbstracto;
import appvideo.persistencia.IEtiquetaDAO;
import appvideo.dominio.Usuario;
import appvideo.dominio.Video;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import appvideo.dominio.Etiqueta;
import appvideo.dominio.RepositorioEtiquetas;
import appvideo.dominio.RepositorioUsuarios;
import appvideo.dominio.RepositorioVideos;

public final class Controlador implements VideosListener {

	private static Controlador unicaInstancia;
	private FactoriaDAOAbstracto factoria;

	private Usuario usuarioActual;
	private RepositorioUsuarios repositorioUsuarios;
	private RepositorioVideos repositorioVideos;
	private RepositorioEtiquetas repositorioEtiquetas;

	private CargadorVideos cargadorVideos;

	public static final int REGISTRO_CORRECTO = 0;
	public static final int REGISTRO_REPETIDO = 1;
	public static final int REGISTRO_INCOMPLETO = 2;
	public static final int REGISTRO_PASSWORDFAIL = 3;
	public static final int REGISTRO_EMAILFAIL = 4;

	private Controlador() {

		cargadorVideos = new CargadorVideos();
		cargadorVideos.addVideoListener(this);

		repositorioUsuarios = RepositorioUsuarios.getUnicaInstancia();
		repositorioVideos = RepositorioVideos.getUnicaInstancia();
		repositorioEtiquetas = RepositorioEtiquetas.getUnicaInstancia();

		try {
			factoria = FactoriaDAOAbstracto.getInstancia(TDSFactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	

	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}

	/* Funciones para controlar el Usuario */

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public boolean esUsuarioRegistrado(String login) {
		return repositorioUsuarios.getUsuario(login) != null;
	}

	public boolean loginUsuario(String nombre, String password) {
		Usuario usuario = repositorioUsuarios.getUsuario(nombre);
		if (usuario != null && usuario.getPassword().equals(password)) {
			this.usuarioActual = usuario;
			return true;
		}
		return false;
	}

	public int registrarUsuario(String nombre, String apellidos, String email, Date fechaNacimiento, String login,
			String password, String password2) {

		if (esUsuarioRegistrado(login))
			return REGISTRO_REPETIDO;
		else if (nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty() || fechaNacimiento == null
				|| login.isEmpty() || password.isEmpty() || password2.isEmpty())
			return REGISTRO_INCOMPLETO;
		else if (!password.equals(password2))
			return REGISTRO_PASSWORDFAIL;

		String regex = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(email);
		if (!mat.find()) {
			return REGISTRO_EMAILFAIL;
		}

		Usuario usuario = new Usuario(nombre, apellidos, email, fechaNacimiento, login, password);
		IUsuarioDAO usuarioDAO = factoria.getUsuarioDAO(); /* Adaptador DAO para almacenar el nuevo Usuario en la BD */
		usuarioDAO.crearUsuario(usuario);
		repositorioUsuarios.addUsuario(usuario);
		return REGISTRO_CORRECTO;
	}

	public void logoutUsuario() {
		this.usuarioActual = null;
	}

	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getLogin()))
			return false;

		IUsuarioDAO usuarioDAO = factoria.getUsuarioDAO(); /* Adaptador DAO para borrar el Usuario de la BD */
		usuarioDAO.borrarUsuario(usuario);

		repositorioUsuarios.removeUsuario(usuario);
		return true;
	}

	/* Funciones para controlar los Videos */
	public LinkedList<Video> getVideos() throws DAOException {
		return repositorioVideos.getVideos();
	}

	public void registrarVideo(String nombre, String url, Etiqueta... etiquetas) {
		Video video = new Video(nombre, url, etiquetas);
		for(Etiqueta etiqueta : etiquetas)
			registrarEtiqueta(etiqueta);
		IVideoDAO videoDAO = factoria.getVideoDAO();
		videoDAO.crearVideo(video);

		repositorioVideos.addVideo(video);
	}

	public void cargarVideos(File archivo) {
		this.cargadorVideos.setArchivoVideos(archivo);
	}
	
	//TODO Revisar
	public Etiqueta[] etiquetaFromString(List<String> etiquetas) {
		ArrayList<Etiqueta> e = new ArrayList<Etiqueta>();
		etiquetas.stream().forEach(et -> e.add(new Etiqueta(et)));
		return e.toArray(new Etiqueta[0]); 
	}
	
	@Override
	public void nuevosVideos(EventObject evento) {
		VideosEvent ve = (VideosEvent) evento;
		Videos videos = ve.getVideos();
		for (umu.tds.componente.Video v : videos.getVideo()) {
			this.registrarVideo(v.getTitulo(), v.getURL(), this.etiquetaFromString(v.getEtiqueta()));
		}
	}
	
	public List<Video> explorarVideos(String titulo, LinkedList<Etiqueta> etiquetas) throws DAOException {
		return repositorioVideos.explorarVideos(titulo, etiquetas);
	}
	
	public void aumentarReproducciones(Video video) {
		video.aumentarReproduccion();
		IVideoDAO videoDAO = factoria.getVideoDAO();
		videoDAO.modificarVideo(video);
	}
	
	/* Funciones para controlar las Etiquetas */
	public void registrarEtiqueta(Etiqueta etiqueta) {
		IEtiquetaDAO etiquetaDAO = factoria.getEtiquetaDAO();
		etiquetaDAO.crearEtiqueta(etiqueta);
	}
	
	public boolean anadirEtiquetaVideo(String nombre, Video video) {
		Etiqueta etiqueta = repositorioEtiquetas.crearEtiqueta(nombre);
		if (etiqueta == null) return false;
		this.registrarEtiqueta(etiqueta);
		repositorioEtiquetas.addColeccionIDEtiqueta(etiqueta);
		video.addEtiqueta(etiqueta);
		IVideoDAO videoDAO = factoria.getVideoDAO();
		videoDAO.modificarVideo(video);
		return true;
	}
	
	public List<Etiqueta> obtenerTodasEtiquetas() throws DAOException {
		return this.repositorioEtiquetas.getEtiquetas();
	}
	
	public List<Etiqueta> obtenerEtiquetasVideo(Video video) {
		return video.getEtiquetas();
	}
	
	public Etiqueta getEtiqueta(String nombre) {
		return this.repositorioEtiquetas.getEtiqueta(nombre);
	}
	
	public void cargarEtiquetasXML() {
		repositorioEtiquetas.cargarEtiquetasXML();
	}
}
