package appvideo.persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
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
				new Propiedad(FECHA_NACIMIENTO, dateFormat.format(usuario.getFechaNacimiento())))));
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
		

		Usuario usuario = new Usuario(nombre, apellidos, email, fechaNacimiento, login, password);
		usuario.setId(eUsuario.getId());

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

}
