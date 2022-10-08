package appvideo.persistencia;

import java.util.List;

import appvideo.dominio.Etiqueta;

public interface IEtiquetaDAO {
	
	void crearEtiqueta(Etiqueta etiqueta);
	boolean borrarEtiqueta(Etiqueta etiqueta);
	void modificarEtiqueta(Etiqueta etiqueta);
	Etiqueta obtenerEtiqueta(int id);
	List<Etiqueta> obtenerTodasEtiquetas();
}
