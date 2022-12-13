package appvideo.dominio;

public class FiltroMisListas implements FiltroVideo {
	
	private int codigo;
	
	public FiltroMisListas() {
		this.codigo = CODIGO_MISLISTAS;
	}

	@Override
	public boolean isVideoOk(Video video, Usuario usuario) {
		if (usuario.videoPerteneceLista(video)) return false;
	    return true;
	}

	@Override
	public int getCodigo() {
		return codigo;
	}

}
