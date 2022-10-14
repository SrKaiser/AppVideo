package appvideo.vistas;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import appvideo.dominio.Etiqueta;
import appvideo.dominio.Video;
import appvideo.extra.TextPrompt;
import appvideo.extra.VideoTable;
import appvideo.main.Launcher;
import appvideo.persistencia.DAOException;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class VistaExplorar extends JPanel {

	private JTextField txtBuscar;
	private VistaPrincipal vistaPrincipal;
	private JTable table;

	private VideoTable tablaVideos;
	private JScrollPane scrollPaneTablaVideos;
	private JPanel panelTablaVideos;

	private JList<String> listEtiquetasDisponibles;
	private JList<String> listEtiquetasSeleccionadas;
	private DefaultListModel<String> listaModelEtiquetas;
	private DefaultListModel<String> listaModelSeleccion;
	private JScrollPane scrollPaneEtiquetasDisponibles;

	private JButton btnBuscar;
	private JButton btnAnadir;
	private JButton btnQuitar;

	public VistaExplorar(VistaPrincipal vistaPrincipal) {
		this.vistaPrincipal = vistaPrincipal;
		initialize();
	}

	public void actualizarTablaVideos() {
		table = new JTable();
		table.setBackground(new Color(240, 240, 240));
		table.setCellSelectionEnabled(true);
		table.setDefaultRenderer(Object.class, Launcher.createTableRenderer());
		tablaVideos = new VideoTable();
		try {
			tablaVideos.fillTable(vistaPrincipal.getControlador().getVideos());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		table.setModel(tablaVideos);
		table.setRowHeight(125);
		table.getTableHeader().setUI(null);
		table.setShowGrid(false);

		TableColumnModel colModel = table.getColumnModel();
		for (int i = 0; i < 4; i++) {
			TableColumn col = colModel.getColumn(i);
			col.setPreferredWidth(145);
		}

		scrollPaneTablaVideos.setViewportView(table);

		panelTablaVideos.add(scrollPaneTablaVideos);
		panelTablaVideos.repaint();
		panelTablaVideos.revalidate();
		tablaVideos.fireTableDataChanged();
	}

	private void explorarVideos() {
		btnBuscar.addActionListener(ev -> {
			String busqueda = txtBuscar.getText();
			LinkedList<Etiqueta> etiquetas = new LinkedList<>();
			for(Object o : listaModelSeleccion.toArray()) {
				etiquetas.add(vistaPrincipal.getControlador().getEtiqueta((String)o));
			}
			
			if (!busqueda.isEmpty() || !etiquetas.isEmpty()) {
				List<Video> videos = null;
				try {
					videos = vistaPrincipal.getControlador().explorarVideos(busqueda, etiquetas);
				
				} catch (DAOException e) {
					e.printStackTrace();
				}
				int filas = table.getRowCount();
				for (int i = filas-1; i >= 0; i--)
					tablaVideos.deleteRow(i);
				if (videos != null)
					tablaVideos.fillTable(videos);
			}
			else {
				int filas = table.getRowCount();
				for (int i = filas-1; i >= 0; i--)
					tablaVideos.deleteRow(i);
				
				LinkedList<Video> todosVideos = null;
				try {
					todosVideos = vistaPrincipal.getControlador().getVideos();
				} catch (DAOException e) {
					e.printStackTrace();
				}
				
				tablaVideos.fillTable(todosVideos);
				
			}
			tablaVideos.fireTableDataChanged();
			validate();	
		});
	}

	private void reproducirVideo() {

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int fila = table.rowAtPoint(e.getPoint());
					int columna = table.columnAtPoint(e.getPoint());
					if (tablaVideos.getValueAt(fila, columna) != null) {
						vistaPrincipal.getControlador().addVideoRecientes(tablaVideos.getValueAt(fila,columna));
						vistaPrincipal.cargarVideo(new VistaVideo(vistaPrincipal, tablaVideos.getValueAt(fila, columna)));
					}
				}
			}
		});
	}

	public void cargarEtiquetas() throws DAOException {
		listEtiquetasDisponibles.removeAll();
		listaModelEtiquetas.removeAllElements();
		for (Etiqueta etiqueta : vistaPrincipal.getControlador().obtenerTodasEtiquetas()) {
			listaModelEtiquetas.addElement(etiqueta.getNombre());
			validate();
		}

	}

	private void anadirEtiqueta() {
		btnAnadir.addActionListener(ev -> {
			try {
				String nombreEtiqueta = listaModelEtiquetas.get(listEtiquetasDisponibles.getSelectedIndex());
				if (!listaModelSeleccion.contains(nombreEtiqueta)) {
					listaModelSeleccion.addElement(nombreEtiqueta);
				} else {
					JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(),
							"La etiqueta seleccionada ya está añadida.\n", "Etiqueta", JOptionPane.ERROR_MESSAGE);
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(), "Por favor, selecciona una etiqueta.\n",
						"Etiqueta", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void quitarEtiqueta() {
		btnQuitar.addActionListener(ev -> {
			try {
				String nombreEtiqueta = listaModelSeleccion.get(listEtiquetasSeleccionadas.getSelectedIndex());
				listaModelSeleccion.removeElement(nombreEtiqueta);
			} catch (ArrayIndexOutOfBoundsException e){
				JOptionPane.showMessageDialog((Component) ev.getSource(), "Tienes que seleccionar una etiqueta");
			}
		});
	}

	private void listener() {
		explorarVideos();
		reproducirVideo();
		anadirEtiqueta();
		quitarEtiqueta();
	}

	private void initialize() {
		setBounds(100, 100, 870, 592);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 518, 219, 0 };
		gridBagLayout.rowHeights = new int[] { 503, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 101, 357, 0 };
		gbl_panel.rowHeights = new int[] { 27, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 0;
		panel.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));

		txtBuscar = new JTextField();
		TextPrompt promptBuscar = new TextPrompt("Explorar videos", txtBuscar);
		promptBuscar.changeAlpha(0.5f);
		panel_2.add(txtBuscar);
		txtBuscar.setColumns(40);

		btnBuscar = new JButton("");
		btnBuscar.setBackground(new Color(240, 240, 240));
		btnBuscar.setBorder(null);
		btnBuscar.setMargin(new Insets(2, 2, 2, 2));
		btnBuscar.setIcon(new ImageIcon(VistaExplorar.class.getResource("/appvideo/imagenes/BotonBusca.png")));
		panel_2.add(btnBuscar);

		panelTablaVideos = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.gridwidth = 2;
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 1;
		panel.add(panelTablaVideos, gbc_panel_4);
		panelTablaVideos.setLayout(new BoxLayout(panelTablaVideos, BoxLayout.X_AXIS));

		scrollPaneTablaVideos = new JScrollPane();

		actualizarTablaVideos();

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		JLabel lblNewLabel_2 = new JLabel("\r\n");
		panel_1.add(lblNewLabel_2);

		JLabel lblEtiquetasDisponibles = new JLabel("Etiquetas Disponibles");
		lblEtiquetasDisponibles.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblEtiquetasDisponibles.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEtiquetasDisponibles.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblEtiquetasDisponibles);

		JLabel lblNewLabel_1 = new JLabel("\r\n");
		panel_1.add(lblNewLabel_1);

		scrollPaneEtiquetasDisponibles = new JScrollPane();
		panel_1.add(scrollPaneEtiquetasDisponibles);

		listEtiquetasDisponibles = new JList<String>();
		listaModelEtiquetas = new DefaultListModel<String>();
		listEtiquetasDisponibles.setModel(listaModelEtiquetas);
		scrollPaneEtiquetasDisponibles.setViewportView(listEtiquetasDisponibles);

		JLabel lblNewLabel = new JLabel("\r\n\r\n\r\n\r\n");
		panel_1.add(lblNewLabel);

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

		JLabel lblNewLabel_5 = new JLabel("             ");
		panel_3.add(lblNewLabel_5);

		btnAnadir = new JButton("Añadir");
		panel_3.add(btnAnadir);

		JLabel lblNewLabel_6 = new JLabel("              ");
		panel_3.add(lblNewLabel_6);

		btnQuitar = new JButton("Quitar");
		panel_3.add(btnQuitar);

		JLabel lblNewLabel_7 = new JLabel("\r\n");
		panel_1.add(lblNewLabel_7);

		JLabel lblNewLabel_3 = new JLabel("Etiquetas Seleccionadas");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("\r\n\r\n");
		panel_1.add(lblNewLabel_4);

		JScrollPane scrollPaneEtiquetasSeleccionadas = new JScrollPane();
		panel_1.add(scrollPaneEtiquetasSeleccionadas);

		listEtiquetasSeleccionadas = new JList<String>();
		listaModelSeleccion = new DefaultListModel<String>();
		listEtiquetasSeleccionadas.setModel(listaModelSeleccion);
		scrollPaneEtiquetasSeleccionadas.setViewportView(listEtiquetasSeleccionadas);

		listener();
	}

}
