package appvideo.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import appvideo.dominio.ListaVideos;
import appvideo.dominio.Video;
import appvideo.main.Launcher;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class VistaMisListas extends JPanel {

	private VistaPrincipal vistaPrincipal;
	private List<String> nombresListasVideos;
	private JComboBox<String> comboBoxListasVideos;

	private DefaultListModel<Video> listaModelVideos;
	private JList<Video> listVideos;
	private Video videoSeleccionado = null;

	private JButton btnReproducir;
	private VistaVideo vistaVideo;
	private JPanel panelVideo;
	private JButton btnCancelar;

	public VistaMisListas(VistaPrincipal vistaPrincipal) {
		this.vistaPrincipal = vistaPrincipal;
		initialize();
	}
	
	//TODO ERROR
	public void cargarComboBox() {
		comboBoxListasVideos.removeAllItems();
		nombresListasVideos = vistaPrincipal.getControlador().getNombresListasVideos();
		for (int i = 0; i < nombresListasVideos.size(); i++) {
			comboBoxListasVideos.addItem(nombresListasVideos.get(i));
		}
	}

	private void seleccionarListaVideo() {
		comboBoxListasVideos.addActionListener(ev -> {
			listaModelVideos.clear();
			listaModelVideos.removeAllElements();
			String listaSeleccionada = comboBoxListasVideos.getSelectedItem().toString();
			ListaVideos listaVideos = vistaPrincipal.getControlador().getListaVideosNombre(listaSeleccionada);
			for (Video elemento : listaVideos.getVideos())
				listaModelVideos.addElement(elemento);
		});
	}

	private void seleccionarVideo() {
		listVideos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				videoSeleccionado = listaModelVideos.get(listVideos.getSelectedIndex());
			}
		});
	}

	private void reproducirVideo() {
		btnReproducir.addActionListener(ev -> {
			if (videoSeleccionado != null) {
				panelVideo.removeAll();
				vistaVideo = new VistaVideo(vistaPrincipal, videoSeleccionado);
				panelVideo.add(vistaVideo);
				vistaVideo.ocultarDatos();
			} else {
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(), "Por favor, selecciona un video.\n",
						"Mis listas", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void cancelarReproduccion() {
		btnCancelar.addActionListener(ev -> {
			vistaVideo.cancelarReproduccion();
			panelVideo.removeAll();
			panelVideo.repaint();
			panelVideo.revalidate();

		});
	}

	private void listener() {
		seleccionarListaVideo();
		seleccionarVideo();
		reproducirVideo();
		cancelarReproduccion();
	}

	private void initialize() {
		setBounds(100, 100, 930, 540);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 518, 219, 0 };
		gridBagLayout.rowHeights = new int[] { 503, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));

		panelVideo = new JPanel();
		panel.add(panelVideo, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

		comboBoxListasVideos = new JComboBox<String>();
		panel_3.add(comboBoxListasVideos);

		JPanel panelListaVideos = new JPanel();
		panel_1.add(panelListaVideos);
		panelListaVideos.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPaneListaVideos = new JScrollPane();
		panelListaVideos.add(scrollPaneListaVideos);

		listVideos = new JList<Video>();
		listVideos.setCellRenderer(Launcher.createListRenderer());
		listaModelVideos = new DefaultListModel<Video>();
		listVideos.setModel(listaModelVideos);
		scrollPaneListaVideos.setViewportView(listVideos);

		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);

		btnReproducir = new JButton("Reproducir");
		panel_5.add(btnReproducir);

		JLabel lblNewLabel = new JLabel("                              ");
		panel_5.add(lblNewLabel);

		btnCancelar = new JButton("Cancelar");
		panel_5.add(btnCancelar);

		listener();

	}

}
