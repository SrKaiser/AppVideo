package appvideo.dominio;

public class FiltroNombresLargos implements FiltroVideo{

	private int codigo;
	
	public FiltroNombresLargos() {
		this.codigo = CODIGO_NOMBRESLARGOS;
	}
	
	@Override
	public boolean isVideoOk(Video video, Usuario usuario) {
		return video.getTitulo().length() < 16;
	}

	@Override
	public int getCodigo() {
		return codigo;
	}

}
