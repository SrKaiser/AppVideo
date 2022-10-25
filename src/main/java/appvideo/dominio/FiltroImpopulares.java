package appvideo.dominio;

public class FiltroImpopulares implements FiltroVideo{
	
	private int codigo;
	
	public FiltroImpopulares() {
		this.codigo = CODIGO_IMPOPULARES;
	}
	
	@Override
	public boolean isVideoOk(Video video, Usuario usuario) {
		return video.getNumReproducciones() > 5;
	}

	@Override
	public int getCodigo() {
		return codigo;
	}
}
