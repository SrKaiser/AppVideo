package appvideo.vistas;

import javax.swing.JPanel;

import appvideo.dominio.Etiqueta;
import appvideo.dominio.Video;
import appvideo.main.Launcher;
import appvideo.persistencia.DAOException;
import tds.video.VideoWeb;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;

@SuppressWarnings("serial")
public class VistaVideo extends JPanel {
	
	private Video video;
	private VistaPrincipal vistaPrincipal;
	private VideoWeb videoWeb;
	private JTextField textFieldEtiqueta;
	private JPanel panelEtiquetas;
	
	private JButton btnSalir;
	private JButton btnAnadir;
	
	private JLabel lblMiniatura;
	private JLabel lblCopyright;
	
	public VistaVideo(VistaPrincipal vistaPrincipal, Video video) {
		this.video = video;
		this.vistaPrincipal = vistaPrincipal;
		this.videoWeb = Launcher.getVideoWeb();
	
		initialize();
	}
	
	private void salirReproduccion() {
		btnSalir.addActionListener(ev -> { 
			videoWeb.cancel();
		try {
			vistaPrincipal.salirVideo();
		} catch (DAOException e) {
			e.printStackTrace();
		} });
	}
	
	private void anadirEtiqueta() {
		btnAnadir.addActionListener(ev -> { 
			String nombreEtiqueta = textFieldEtiqueta.getText();
			if (!nombreEtiqueta.isEmpty()) {
				
				panelEtiquetas.removeAll();
				
				boolean anadir = vistaPrincipal.getControlador().anadirEtiquetaVideo(nombreEtiqueta, video);
				if (!anadir) JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(), "La etiqueta introducida ya está asociada al video.\n", "Etiqueta", JOptionPane.ERROR_MESSAGE);
				
				textFieldEtiqueta.setText("");
				
				mostrarEtiquetas();
				
				panelEtiquetas.revalidate();
				panelEtiquetas.repaint();
			}
		});
	}
	
	private void listener() {
		salirReproduccion();
		anadirEtiqueta();
	}
	
	private void mostrarEtiquetas() {
		for(Etiqueta e : vistaPrincipal.getControlador().obtenerEtiquetasVideo(video))
		{
			JLabel lblEtiqueta = new JLabel(" " + e.getNombre() + " ");
			lblEtiqueta.setFont(new Font("Arial", Font.BOLD, 13));
			lblEtiqueta.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
			panelEtiquetas.add(lblEtiqueta);
		}
	}
	
	public void ocultarDatos() {
		btnSalir.setVisible(false);
		lblCopyright.setVisible(false);
		lblMiniatura.setVisible(false);
	}
	
	private void initialize() {
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel lblTituloVideo = new JLabel(video.getTitulo(),JLabel.CENTER);
		lblTituloVideo.setFont(new Font("Yu Gothic Medium", Font.BOLD, 22));
		lblTituloVideo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		vistaPrincipal.getControlador().aumentarReproducciones(video);
		JLabel lblReproducciones = new JLabel(video.getNumReproducciones() + " Reproducciones");
		lblReproducciones.setFont(new Font("Yu Gothic Medium", Font.BOLD, 15));
		lblReproducciones.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel.add(lblTituloVideo);
		panel.add(videoWeb);
		
		JLabel lblNewLabel = new JLabel("\r\n\r\n");
		panel.add(lblNewLabel);
		panel.add(lblReproducciones);
		
		JLabel lblNewLabel_1 = new JLabel("\r\n");
		panel.add(lblNewLabel_1);
		
		panelEtiquetas = new JPanel();
		panel.add(panelEtiquetas);
		
		mostrarEtiquetas();
		
		JLabel lblNewLabel_2 = new JLabel("\r\n");
		panel.add(lblNewLabel_2);
		
		JPanel panelBotones = new JPanel();
		panel.add(panelBotones);
		
		textFieldEtiqueta = new JTextField();
		panelBotones.add(textFieldEtiqueta);
		textFieldEtiqueta.setColumns(10);
		
		btnAnadir = new JButton("Añadir");
		panelBotones.add(btnAnadir);
		
		btnSalir = new JButton("Salir");
		panelBotones.add(btnSalir);
		
		JLabel lblNewLabel_3 = new JLabel("\r\n\r\n");
		panel.add(lblNewLabel_3);
		
		lblMiniatura= new JLabel();
		lblMiniatura.setAlignmentX(0.5f);
		lblMiniatura.setIcon(videoWeb.getThumb(video.getUrl()));
		panel.add(lblMiniatura);
		
		JLabel lblNewLabel_4 = new JLabel("\r\n\r\n");
		panel.add(lblNewLabel_4);
		
		lblCopyright=new JLabel(videoWeb.getVersion());
		lblCopyright.setAlignmentX(0.5f);
		panel.add(lblCopyright);

		videoWeb.playVideo(video.getUrl());
		
		listener();
		
	}

}
