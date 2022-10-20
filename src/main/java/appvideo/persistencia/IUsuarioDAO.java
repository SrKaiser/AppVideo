package appvideo.persistencia;

import java.util.List;

import appvideo.dominio.Usuario;

public interface IUsuarioDAO {
	
	void crearUsuario(Usuario usuario);
	boolean borrarUsuario(Usuario usuario);
	void modificarUsuario(Usuario usuario);
	Usuario obtenerUsuario(int id);
	List<Usuario> obtenerTodosUsuarios();
}
