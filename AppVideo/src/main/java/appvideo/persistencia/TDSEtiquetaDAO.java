package appvideo.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import appvideo.dominio.Etiqueta;
import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class TDSEtiquetaDAO implements IEtiquetaDAO {
	
	private static final String ETIQUETA = "etiqueta"; 
	
	private static final String NOMBRE = "nombre";
	
	private static TDSEtiquetaDAO unicaInstancia = null;
	private static ServicioPersistencia servPersistencia;
	
	/* Mantenemos una única creación de esta clase */ 
	public static TDSEtiquetaDAO getUnicaInstancia() {
		if (unicaInstancia == null)
			return new TDSEtiquetaDAO();
		else
			return unicaInstancia;
	}
	
	/* Constructor para TDSEtiquetaDAO, función private para controlar su única instancia */
	private TDSEtiquetaDAO() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	private Entidad etiquetaToEntidad(Etiqueta etiqueta) {
		Entidad eEtiqueta = new Entidad();
		eEtiqueta.setNombre(ETIQUETA);

		eEtiqueta.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad(NOMBRE, etiqueta.getNombre()))));
		return eEtiqueta;
	}

	@Override
	public void crearEtiqueta(Etiqueta etiqueta) {
		// Creo la entidad de etiqueta
		Entidad eEtiqueta = this.etiquetaToEntidad(etiqueta);
		// Registro su identidad en el servicio de persistencia
		eEtiqueta = servPersistencia.registrarEntidad(eEtiqueta);
		// A la etiqueta se le asigna su ID único que crea el servicio de persistencia
		etiqueta.setId(eEtiqueta.getId());
		
	}

	@Override
	public boolean borrarEtiqueta(Etiqueta etiqueta) {
		Entidad eEtiqueta;
		eEtiqueta = servPersistencia.recuperarEntidad(etiqueta.getId());
		return servPersistencia.borrarEntidad(eEtiqueta);
		
	}

	@Override
	public void modificarEtiqueta(Etiqueta etiqueta) {
		Entidad eEtiqueta = servPersistencia.recuperarEntidad(etiqueta.getId());

		for (Propiedad prop : eEtiqueta.getPropiedades()) {
			if (prop.getNombre().equals(NOMBRE)) {
				prop.setValor(etiqueta.getNombre());
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}
	
	private Etiqueta entidadToEtiqueta(Entidad eEtiqueta) {
		
		String nombre = servPersistencia.recuperarPropiedadEntidad(eEtiqueta, NOMBRE);
		
		Etiqueta etiqueta = new Etiqueta(nombre);
		etiqueta.setId(eEtiqueta.getId());

		return etiqueta;
	}


	@Override
	public Etiqueta obtenerEtiqueta(int id) {
		
		Entidad eEtiqueta = servPersistencia.recuperarEntidad(id);

		return entidadToEtiqueta(eEtiqueta);
	}

	@Override
	public List<Etiqueta> obtenerTodasEtiquetas() {
		List<Entidad> entidades = servPersistencia.recuperarEntidades(ETIQUETA);

		List<Etiqueta> etiquetas = new LinkedList<Etiqueta>();
		for (Entidad eEtiqueta : entidades) {
			etiquetas.add(obtenerEtiqueta(eEtiqueta.getId()));
		}

		return etiquetas;
	}

}
