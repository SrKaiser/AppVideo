package appvideo.vistas;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import appvideo.dominio.ListaVideos;
import appvideo.dominio.Video;
import appvideo.extra.TextPrompt;
import appvideo.extra.VideoTableModel;
import appvideo.persistencia.DAOException;
import appvideo.extra.RenderVideos;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class VistaNuevaLista extends JPanel {

	private JTextField txtBuscar;
	private VistaPrincipal vistaPrincipal;
	private JTextField textFieldBuscarLista;

	private JPanel panelTablaVideos;
	private JScrollPane scrollPaneTablaVideos;
	private JTable table;
	private VideoTableModel tablaVideos;

	private JButton btnBuscar;

	private JButton btnBuscarLista;
	private JButton btnCrear;
	private JButton btnEliminar;

	private JButton btnAnadir;
	private JButton btnQuitar;
	private JButton btnAceptar;

	private Video videoSeleccionado;

	private DefaultListModel<Video> listaModelVideos;
	private JList<Video> listVideos;
	private ListaVideos listaActual;

	public VistaNuevaLista(VistaPrincipal vistaPrincipal) {
		this.vistaPrincipal = vistaPrincipal;
		initialize();
	}

	public void actualizarTablaVideos() {
		table = new JTable();
		table.setBackground(new Color(240, 240, 240));
		table.setCellSelectionEnabled(true);
		table.setDefaultRenderer(Object.class, RenderVideos.createTableRenderer());
		tablaVideos = new VideoTableModel();

		table.setModel(tablaVideos);
		table.setRowHeight(125);
		table.getTableHeader().setUI(null);
		table.setShowGrid(false);

		TableColumnModel colModel = table.getColumnModel();
		for (int i = 0; i < 4; i++) {
			TableColumn col = colModel.getColumn(i);
			col.setPreferredWidth(145);
		}
		panelTablaVideos.setLayout(new BoxLayout(panelTablaVideos, BoxLayout.X_AXIS));

		scrollPaneTablaVideos.setViewportView(table);

		panelTablaVideos.add(scrollPaneTablaVideos);
		panelTablaVideos.repaint();
		panelTablaVideos.revalidate();
		tablaVideos.fireTableDataChanged();
	}

	private void explorarVideos() {
		btnBuscar.addActionListener(ev -> {
			String busqueda = txtBuscar.getText();

			if (!busqueda.isEmpty()) {
				List<Video> videos = null;
				try {
					videos = vistaPrincipal.getControlador().explorarVideos(busqueda, null);

				} catch (DAOException e) {
					e.printStackTrace();
				}
				int filas = table.getRowCount();
				for (int i = filas - 1; i >= 0; i--)
					tablaVideos.deleteRow(i);
				if (videos != null)
					tablaVideos.fillTable(videos);
			} else {
				int filas = table.getRowCount();
				for (int i = filas - 1; i >= 0; i--)
					tablaVideos.deleteRow(i);

				List<Video> todosVideos = null;
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

	private void buscarLista() {
		btnBuscarLista.addActionListener(ev -> {
			if (textFieldBuscarLista.getText().isBlank()) {
				listaModelVideos.clear();
				listaModelVideos.removeAllElements();
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(),
						"Introduce el nombre de una lista para buscar.\n", "Nueva Lista", JOptionPane.ERROR_MESSAGE);
			} else if (vistaPrincipal.getControlador().getListaVideosNombre(textFieldBuscarLista.getText()) != null) {
				listaModelVideos.clear();
				listaModelVideos.removeAllElements();
				listaActual = vistaPrincipal.getControlador().getListaVideosNombre(textFieldBuscarLista.getText());
				for (Video elemento : listaActual.getVideos())
					listaModelVideos.addElement(elemento);
			} else {
				listaModelVideos.clear();
				listaModelVideos.removeAllElements();
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(),
						"No existe una lista con este nombre, si quieres crearla dale al botón.\n", "Nueva Lista",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	private void crearLista() {
		btnCrear.addActionListener(ev -> {
			if (textFieldBuscarLista.getText().isBlank()) {
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(),
						"Introduce el nombre de una lista para crear.\n", "Nueva Lista", JOptionPane.ERROR_MESSAGE);
			} else if (vistaPrincipal.getControlador().getListaVideosNombre(textFieldBuscarLista.getText()) == null) {
				listaModelVideos.clear();
				listaModelVideos.removeAllElements();
				listaActual = new ListaVideos(textFieldBuscarLista.getText());
				vistaPrincipal.getControlador().anadirListaVideos(listaActual);
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(),
						"Lista de vídeos - " + listaActual.getNombre() + " - creada con éxito.\n", "Nueva Lista",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	private void eliminarLista() {
		btnEliminar.addActionListener(ev -> {
			if (textFieldBuscarLista.getText().isBlank()) {
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(),
						"Introduce el nombre de una lista para eliminar.\n", "Nueva Lista", JOptionPane.ERROR_MESSAGE);
			} else if (vistaPrincipal.getControlador().getListaVideosNombre(textFieldBuscarLista.getText()) != null) {
				vistaPrincipal.getControlador().eliminarListaVideos(textFieldBuscarLista.getText());
				listaModelVideos.removeAllElements();
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(),
						"Lista de vídeos - " + textFieldBuscarLista.getText() + " - eliminada con éxito.\n", "Nueva Lista",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(), "No existe una lista con este nombre\n",
						"Nueva Lista", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	private void anadirVideo() {
		btnAnadir.addActionListener(ev -> {
			if (videoSeleccionado != null) {
				listaModelVideos.addElement(videoSeleccionado);
			} else {
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(), "Por favor, selecciona un video.\n",
						"Nueva Lista", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void quitarVideo() {
		btnQuitar.addActionListener(ev -> {
			try {
				Video video = listaModelVideos.get(listVideos.getSelectedIndex());
				listaModelVideos.removeElement(video);
				listaActual.removeVideo(video);
			} catch (ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(), "Por favor, selecciona un video.\n",
						"Nueva Lista", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void aceptarLista() {
		btnAceptar.addActionListener(ev -> {
			listaActual.removeAllVideos();
			for(int i = 0; i < listaModelVideos.size(); i++){
                listaActual.addVideo(listaModelVideos.get(i));
            }
			vistaPrincipal.getControlador().actualizarListaVideos(listaActual);
			JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(),
					"Lista de vídeos - " + listaActual.getNombre() + " - actualizada con éxito.\n", "Nueva Lista",
					JOptionPane.INFORMATION_MESSAGE);
		});
	}

	private void seleccionarVideo() {
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int fila = table.rowAtPoint(e.getPoint());
				int columna = table.columnAtPoint(e.getPoint());
				if (tablaVideos.getValueAt(fila, columna) != null) {
					videoSeleccionado = tablaVideos.getValueAt(fila, columna);
					validate();
				}
			}
		});
	}

	private void listener() {
		explorarVideos();
		buscarLista();
		crearLista();
		eliminarLista();
		anadirVideo();
		quitarVideo();
		aceptarLista();
		seleccionarVideo();
	}

	private void initialize() {

		setBounds(100, 100, 930, 540);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 588, 319, 0 };
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

		JLabel lblNewLabel = new JLabel("\r\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 6));
		panel_1.add(lblNewLabel);

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);

		textFieldBuscarLista = new JTextField();
		TextPrompt promptBuscarLista = new TextPrompt("Buscar lista", textFieldBuscarLista);
		promptBuscarLista.changeAlpha(0.5f);
		panel_3.add(textFieldBuscarLista);
		textFieldBuscarLista.setColumns(30);

		JPanel panel_6 = new JPanel();
		panel_1.add(panel_6);

		btnBuscarLista = new JButton("Buscar");
		panel_6.add(btnBuscarLista);

		btnCrear = new JButton("Crear");
		panel_6.add(btnCrear);

		btnEliminar = new JButton("Eliminar");
		panel_6.add(btnEliminar);

		JPanel panelListaVideos = new JPanel();
		panel_1.add(panelListaVideos);
		panelListaVideos.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPaneListaVideos = new JScrollPane();
		panelListaVideos.add(scrollPaneListaVideos);

		listVideos = new JList<Video>();
		listVideos.setCellRenderer(RenderVideos.createListRenderer());
		listaModelVideos = new DefaultListModel<Video>();
		listVideos.setModel(listaModelVideos);
		scrollPaneListaVideos.setViewportView(listVideos);

		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);

		btnAnadir = new JButton("Añadir");
		panel_5.add(btnAnadir);

		btnQuitar = new JButton("Quitar");
		panel_5.add(btnQuitar);

		JLabel lblNewLabel_1 = new JLabel("                   ");
		panel_5.add(lblNewLabel_1);

		btnAceptar = new JButton("Aceptar");
		panel_5.add(btnAceptar);

		listener();

	}

}
