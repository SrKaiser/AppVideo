package appvideo.dominio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import appvideo.persistencia.DAOException;
import appvideo.persistencia.FactoriaDAOAbstracto;

public class RepositorioEtiquetas {
	private static RepositorioEtiquetas unicaInstancia;
	private FactoriaDAOAbstracto factoria;

	private HashMap<Integer, Etiqueta> coleccionEtiquetasID;
	private HashMap<String, Etiqueta> coleccionEtiquetasNombre;
	
	public static RepositorioEtiquetas getUnicaInstancia() {
		if (unicaInstancia == null) unicaInstancia = new RepositorioEtiquetas();
		return unicaInstancia;
	}
	
	private RepositorioEtiquetas(){
		coleccionEtiquetasID = new HashMap<Integer, Etiqueta>();
		coleccionEtiquetasNombre = new HashMap<String, Etiqueta>();
		try {
			factoria = FactoriaDAOAbstracto.getInstancia();
			List<Etiqueta> listaEtiquetas = factoria.getEtiquetaDAO().obtenerTodasEtiquetas();
			for (Etiqueta etiqueta : listaEtiquetas) {
				coleccionEtiquetasID.put(etiqueta.getId(), etiqueta);
				coleccionEtiquetasNombre.put(etiqueta.getNombre(), etiqueta);
			}
		} catch (DAOException eDAO) {
			   eDAO.printStackTrace();
		}
	}
	
	public List<Etiqueta> getEtiquetas() throws DAOException {
		return new LinkedList<Etiqueta>(coleccionEtiquetasID.values());
	}
	
	public Etiqueta getEtiqueta(int id) {
		return coleccionEtiquetasID.get(id);
	}
	
	public Etiqueta getEtiqueta(String nombre) {
		return coleccionEtiquetasNombre.get(nombre);
	}
	
	public void addEtiqueta(Etiqueta etiqueta) {
		coleccionEtiquetasID.put(etiqueta.getId(), etiqueta);
		coleccionEtiquetasNombre.put(etiqueta.getNombre(), etiqueta);
	}
	
	public void removeEtiqueta(Etiqueta etiqueta) {
		coleccionEtiquetasID.remove(etiqueta.getId());
		coleccionEtiquetasNombre.remove(etiqueta.getNombre());
	}
}
