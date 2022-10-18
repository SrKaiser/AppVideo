package appvideo.vistas;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JList;

import appvideo.dominio.Video;
import appvideo.extra.RenderVideos;


//TODO Pendiente cancelar la reproducción cuando se abandona la vista de recientes

@SuppressWarnings("serial")
public class VistaRecientes extends JPanel {
	
	private VistaPrincipal vistaPrincipal;
	
	private JList<Video> listVideosRecientes;
	private DefaultListModel<Video> listaModelVideos;
	
	private JPanel panelVideo;
	private VistaVideo vistaVideo;
	
	private static boolean reproduciendo = false;
	
	public VistaRecientes(VistaPrincipal vistaPrincipal) {
		this.vistaPrincipal = vistaPrincipal;

		initialize();
	}
	
	public static boolean getReproduciendo() {
		return reproduciendo;
	}
	
	private void reproducirVideo() {

		listVideosRecientes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Video video = listaModelVideos.get(listVideosRecientes.getSelectedIndex());
					reproduciendo = true;
					panelVideo.removeAll();
					vistaVideo = new VistaVideo(vistaPrincipal, video);
					panelVideo.add(vistaVideo);
					vistaVideo.ocultarDatos();
				}
			}
		});
	}
	
	private void listener() {
		reproducirVideo();
	}
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		panelVideo = new JPanel();
		panel.add(panelVideo, BorderLayout.CENTER);
		
		JPanel panelListaRecientes = new JPanel();
		add(panelListaRecientes, BorderLayout.EAST);
		panelListaRecientes.setLayout(new BoxLayout(panelListaRecientes, BoxLayout.X_AXIS));
		
		JScrollPane scrollPaneListaRecientes = new JScrollPane();
		panelListaRecientes.add(scrollPaneListaRecientes);
		
		listVideosRecientes = new JList<Video>();
		listVideosRecientes.setCellRenderer(RenderVideos.createListRenderer());
		listaModelVideos = new DefaultListModel<Video>();
		listVideosRecientes.setModel(listaModelVideos);
		scrollPaneListaRecientes.setViewportView(listVideosRecientes);
		
		cargarListaRecientes();
		
		listener();
	}
	
	public void cargarListaRecientes() {
		for(Video video : vistaPrincipal.getControlador().getVideosRecientes().getVideos()) {
			listaModelVideos.addElement(video);
		}
	}

}
