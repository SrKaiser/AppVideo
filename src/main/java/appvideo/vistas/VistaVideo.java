package appvideo.vistas;

import javax.swing.JPanel;

import appvideo.dominio.Video;
import appvideo.main.Launcher;
import tds.video.VideoWeb;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class VistaVideo extends JPanel {
	
	private Video video;
	private VistaPrincipal vistaPrincipal;
	private VideoWeb videoWeb;
	
	public VistaVideo(VistaPrincipal vistaPrincipal, Video video) {
		this.video = video;
		this.vistaPrincipal = vistaPrincipal;
		this.videoWeb = Launcher.getVideoWeb();
	
		initialize();
	}
	
	private void initialize() {
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel lblTituloVideo = new JLabel(video.getTitulo(),JLabel.CENTER);
		lblTituloVideo.setFont(new Font("Yu Gothic Medium", Font.BOLD, 22));
		lblTituloVideo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel.add(lblTituloVideo);
		panel.add(videoWeb);
		
		JPanel panelBotones = new JPanel();
		panel.add(panelBotones);
		
		JButton btnSalir = new JButton("Salir");
		panelBotones.add(btnSalir);
		btnSalir.addActionListener(ev -> {
			videoWeb.cancel();
			vistaPrincipal.salirVideo();
		});
		videoWeb.playVideo(video.getUrl());
		//this.vistaPrincipal.getAppVideo().validate();
		
	}

}
