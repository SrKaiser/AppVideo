package appvideo.dominio;

public class NoFiltro implements FiltroVideo {
	
	private int codigo;
	
	public NoFiltro() {
		this.codigo = CODIGO_NOFILTRO;
	}
	
	@Override
	public boolean isVideoOk(Video video, Usuario usuario) {
		return true;
	}

	@Override
	public int getCodigo() {
		return codigo;
	}

}
