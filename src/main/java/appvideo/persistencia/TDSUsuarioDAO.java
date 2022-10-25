package appvideo.persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import appvideo.dominio.FiltroVideo;
import appvideo.dominio.ListaVideos;
import appvideo.dominio.Usuario;
import beans.Entidad;
import beans.Propiedad;

public class TDSUsuarioDAO implements IUsuarioDAO {

	private static final String USUARIO = "usuario"; 

	private static final String NOMBRE = "nombre";
	private static final String APELLIDOS = "apellidos";
	private static final String EMAIL = "email";
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String FECHA_NACIMIENTO = "fechaNacimiento";
	private static final String PREMIUM = "premium";
	
	private static final String VIDEOS_RECIENTES = "videosRecientes";
	private static final String LISTAS_VIDEOS = "listasVideos";
	
	private static final String FILTRO = "filtro";

	private static TDSUsuarioDAO unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;
	
	private SimpleDateFormat dateFormat;
	
	/* Mantenemos una única creación de esta clase */ 
	public static TDSUsuarioDAO getUnicaInstancia() {
		if (unicaInstancia == null)
			return new TDSUsuarioDAO();
		else
			return unicaInstancia;

	}
	
	/* Constructor para TDSUsuarioDAO, función private para controlar su única instancia */
	private TDSUsuarioDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	private Entidad usuarioToEntidad(Usuario usuario) {
		Entidad eUsuario = new Entidad();
		eUsuario.setNombre(USUARIO);
		
		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(NOMBRE, usuario.getNombre()),
				new Propiedad(APELLIDOS, usuario.getApellidos()), 
				new Propiedad(EMAIL, usuario.getEmail()),
				new Propiedad(LOGIN, usuario.getLogin()), 
				new Propiedad(PASSWORD, usuario.getPassword()),
				new Propiedad(FECHA_NACIMIENTO, dateFormat.format(usuario.getFechaNacimiento())),
				new Propiedad(PREMIUM, String.valueOf(usuario.isPremium())),
				new Propiedad(VIDEOS_RECIENTES, String.valueOf(usuario.getVideosRecientes().getId())),
				new Propiedad(LISTAS_VIDEOS, parseListasVideos(usuario.getListasVideos())),
				new Propiedad(FILTRO, String.valueOf(usuario.getFiltro())))));
		return eUsuario;
	}

	public void crearUsuario(Usuario usuario) {
		// Creo la entidad de Usuario
		Entidad eUsuario = this.usuarioToEntidad(usuario);
		// Registro su identidad en el servicio de persistencia
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// Al usuario se le asigna su ID único que crea el servicio de persistencia
		usuario.setId(eUsuario.getId());
	}

	public boolean borrarUsuario(Usuario usuario) {
		Entidad eUsuario;
		eUsuario = servPersistencia.recuperarEntidad(usuario.getId());
		return servPersistencia.borrarEntidad(eUsuario);
	}
	
	public void modificarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getId());

		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals(PASSWORD)) {
				prop.setValor(usuario.getPassword());
			} else if (prop.getNombre().equals(EMAIL)) {
				prop.setValor(usuario.getEmail());
			} else if (prop.getNombre().equals(NOMBRE)) {
				prop.setValor(usuario.getNombre());
			} else if (prop.getNombre().equals(APELLIDOS)) {
				prop.setValor(usuario.getApellidos());
			} else if (prop.getNombre().equals(LOGIN)) {
				prop.setValor(usuario.getLogin());
			} else if (prop.getNombre().equals(FECHA_NACIMIENTO)) {
				prop.setValor(dateFormat.format(usuario.getFechaNacimiento()));
			} else if (prop.getNombre().equals(PREMIUM)) {
				prop.setValor(String.valueOf(usuario.isPremium()));
			} else if (prop.getNombre().equals(VIDEOS_RECIENTES)) {
				prop.setValor(String.valueOf(usuario.getVideosRecientes().getId()));
			} else if (prop.getNombre().equals(LISTAS_VIDEOS)) {
				prop.setValor(parseListasVideos(usuario.getListasVideos()));
			} else if (prop.getNombre().equals(FILTRO)) {
				prop.setValor(String.valueOf(usuario.getFiltro().getCodigo()));
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}
	
	private Usuario entidadToUsuario(Entidad eUsuario) {
			
		String nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, NOMBRE);
		String apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, APELLIDOS);
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, EMAIL);
		Date fechaNacimiento = null;
		try {
			fechaNacimiento = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, FECHA_NACIMIENTO));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String login = servPersistencia.recuperarPropiedadEntidad(eUsuario, LOGIN);
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, PASSWORD);
		boolean premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, PREMIUM));
		ListaVideos recientes = null;
		recientes = parseVideosRecientes(servPersistencia.recuperarPropiedadEntidad(eUsuario, VIDEOS_RECIENTES));
		List<ListaVideos> listasVideos = null;
		listasVideos = parseListasVideos2(servPersistencia.recuperarPropiedadEntidad(eUsuario, LISTAS_VIDEOS));
		//FiltroVideo filtro = null;
		//filtro = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eUsuario, FILTRO));
		
		Usuario usuario = new Usuario(nombre, apellidos, email, fechaNacimiento, login, password);
		usuario.setId(eUsuario.getId());
		usuario.setPremium(premium);
		usuario.setVideosRecientes(recientes);
		usuario.setListasVideos(listasVideos);
		//usuario.setFiltro(filtro);

		return usuario;
	}
	
	public Usuario obtenerUsuario(int id) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(id);
		
		return entidadToUsuario(eUsuario);
	}

	public List<Usuario> obtenerTodosUsuarios() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades(USUARIO);

		List<Usuario> usuarios = new LinkedList<Usuario>();
		for (Entidad eUsuario : entidades) {
			usuarios.add(obtenerUsuario(eUsuario.getId()));
		}

		return usuarios;
	}
	
	private ListaVideos parseVideosRecientes(String id) {
		if(id != null) {
			
			TDSListaVideosDAO listaVideosDAO = TDSListaVideosDAO.getUnicaInstancia();
			return listaVideosDAO.obtenerListaVideos(Integer.valueOf(id));
		}
		return new ListaVideos("recientes");
		
	}
	
	private String parseListasVideos(List<ListaVideos> listavideos) {
		String listasVideosJuntos = "";
		for (ListaVideos listaVideos : listavideos) {
			listasVideosJuntos += listaVideos.getId() + " ";
		}
		return listasVideosJuntos.trim();
	}
	
	private List<ListaVideos> parseListasVideos2(String listasVideosJuntos) {
		List<ListaVideos> listavideos = new LinkedList<ListaVideos>();
		if(listasVideosJuntos != null) {
			StringTokenizer strTok = new StringTokenizer(listasVideosJuntos, " ");
			TDSListaVideosDAO listaVideosDAO = TDSListaVideosDAO.getUnicaInstancia();
			while (strTok.hasMoreTokens()) {
				listavideos.add(listaVideosDAO.obtenerListaVideos(Integer.valueOf((String) strTok.nextElement())));
			}
		}
		return listavideos;
	}
	
	private ListaVideos parseFiltros(String id) {
		TDSListaVideosDAO listaVideosDAO = TDSListaVideosDAO.getUnicaInstancia();
		return listaVideosDAO.obtenerListaVideos(Integer.valueOf(id));
		
	}

}
