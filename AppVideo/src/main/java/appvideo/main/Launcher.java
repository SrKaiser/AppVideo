package appvideo.main;

import java.awt.EventQueue;

import appvideo.vistas.VistaPrincipal;
import tds.video.VideoWeb;

public class Launcher 
{
	private static VideoWeb videoWeb;
	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	videoWeb = new VideoWeb();
                	VistaPrincipal pantallaInicial = new VistaPrincipal();
                	pantallaInicial.getAppVideo().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	public static VideoWeb getVideoWeb() {
		return videoWeb;
	}
	
	
}
