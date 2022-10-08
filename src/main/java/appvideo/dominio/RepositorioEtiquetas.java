package appvideo.dominio;

import java.util.Comparator;
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
	
	public void cargarEtiquetasXML() {
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
		LinkedList<Etiqueta> listaEtiquetas = new LinkedList<>();
		coleccionEtiquetasNombre.values().stream()
		.sorted( Comparator.comparing( Etiqueta::getNombre))
		.forEach(e -> listaEtiquetas.add(e));
		return listaEtiquetas;
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
	
	public Etiqueta crearEtiqueta(String nombre) {
		
		if(coleccionEtiquetasNombre.get(nombre)!=null) {
			return null;
		}
		
		Etiqueta e = new Etiqueta(nombre);
		coleccionEtiquetasNombre.put(nombre, e);
		return e;
	}
	
	public void addColeccionIDEtiqueta(Etiqueta etiqueta) {
		coleccionEtiquetasID.put(etiqueta.getId(), etiqueta);
	}
}
