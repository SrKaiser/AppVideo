package appvideo.dominio;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import appvideo.persistencia.DAOException;
import appvideo.persistencia.FactoriaDAOAbstracto;

public class RepositorioUsuarios {
	private static RepositorioUsuarios unicaInstancia;
	private FactoriaDAOAbstracto factoria;

	private HashMap<Integer, Usuario> coleccionUsuariosID;
	private HashMap<String, Usuario> coleccionUsuariosLogin;

	public static RepositorioUsuarios getUnicaInstancia() {
		if (unicaInstancia == null) unicaInstancia = new RepositorioUsuarios();
		return unicaInstancia;
	}

	private RepositorioUsuarios (){
		coleccionUsuariosID = new HashMap<Integer, Usuario>();
		coleccionUsuariosLogin = new HashMap<String, Usuario>();
		
		try {
			factoria = FactoriaDAOAbstracto.getInstancia();
			List<Usuario> listaAsistentes = factoria.getUsuarioDAO().obtenerTodosUsuarios();
			for (Usuario usuario : listaAsistentes) {
				coleccionUsuariosID.put(usuario.getId(), usuario);
				coleccionUsuariosLogin.put(usuario.getLogin(), usuario);
			}
		} catch (DAOException eDAO) {
			   eDAO.printStackTrace();
		}
	}
	
	public List<Usuario> getUsuarios() throws DAOException {
		return new LinkedList<Usuario>(coleccionUsuariosLogin.values());
	}
	
	public Usuario getUsuario(String login) {
		return coleccionUsuariosLogin.get(login);
	}

	public Usuario getUsuario(int id) {
		return coleccionUsuariosID.get(id);
	}
	
	public void addUsuario(Usuario usuario) {
		coleccionUsuariosID.put(usuario.getId(), usuario);
		coleccionUsuariosLogin.put(usuario.getLogin(), usuario);
	}
	
	public void removeUsuario(Usuario usuario) {
		coleccionUsuariosID.remove(usuario.getId());
		coleccionUsuariosLogin.remove(usuario.getLogin());
	}

}

