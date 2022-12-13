package appvideo.dominio;

public interface FiltroVideo {
	
	public static final int CODIGO_NOFILTRO = 0;
	public static final int CODIGO_MISLISTAS = 1;
	public static final int CODIGO_NOMBRESLARGOS = 2;
	public static final int CODIGO_IMPOPULARES = 3;
	
	public boolean isVideoOk(Video video, Usuario usuario);
	public int getCodigo();
}
