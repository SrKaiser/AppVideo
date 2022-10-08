package appvideo.persistencia;

import java.util.List;

import appvideo.dominio.Usuario;

public interface IUsuarioDAO {
	
	void crearUsuario(Usuario asistente);
	boolean borrarUsuario(Usuario asistente);
	void modificarUsuario(Usuario asistente);
	Usuario obtenerUsuario(int id);
	List<Usuario> obtenerTodosUsuarios();
}
