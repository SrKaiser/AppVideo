package appvideo.persistencia;

/** 
 * Factoria concreta DAO para el Servidor de Persistencia de la asignatura TDS.
 * 
 */

public final class TDSFactoriaDAO extends FactoriaDAOAbstracto {
	
	public TDSFactoriaDAO() {	}
	
	@Override
	public TDSUsuarioDAO getUsuarioDAO() {	
		return TDSUsuarioDAO.getUnicaInstancia(); 
	}
	
	@Override
	public TDSVideoDAO getVideoDAO() {	
		return TDSVideoDAO.getUnicaInstancia(); 
	}
	
	@Override
	public TDSEtiquetaDAO getEtiquetaDAO() {	
		return TDSEtiquetaDAO.getUnicaInstancia(); 
	}
	
	@Override
	public IListaVideosDAO getListaVideosDAO() {	
		return TDSListaVideosDAO.getUnicaInstancia(); 
	}

}