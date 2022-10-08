package appvideo.persistencia;

public abstract class FactoriaDAOAbstracto {
	
	public static final String DAO_TDS = "appvideo.persistencia.TDSFactoriaDAO";

	private static FactoriaDAOAbstracto unicaInstancia = null;
	
	/** 
	 * Crea un tipo de factoria DAO.
	 * Solo existe el tipo TDSFactoriaDAO
	 */
	
	public static FactoriaDAOAbstracto getInstancia(String tipo) throws DAOException{
		if (unicaInstancia == null)
			try { 
				unicaInstancia=(FactoriaDAOAbstracto) Class.forName(tipo).newInstance(); //.getDeclaredConstructor()
			} catch (Exception e) {	
				throw new DAOException(e.getMessage());
		} 
		return unicaInstancia;
	}
	

	public static FactoriaDAOAbstracto getInstancia() throws DAOException{
		if (unicaInstancia == null) return getInstancia (FactoriaDAOAbstracto.DAO_TDS);
		else return unicaInstancia;
	}

	protected FactoriaDAOAbstracto (){}
	/* Metodos factoria para obtener adaptadores */
	public abstract IUsuarioDAO getUsuarioDAO();
	public abstract IVideoDAO getVideoDAO();
	public abstract IEtiquetaDAO getEtiquetaDAO();
	public abstract IListaVideosDAO getListaVideosDAO();
}