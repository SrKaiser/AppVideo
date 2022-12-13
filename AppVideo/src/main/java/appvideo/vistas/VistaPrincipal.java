package appvideo.vistas;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import java.awt.Dimension;
import java.io.File;
import javax.swing.border.LineBorder;

import appvideo.controlador.Controlador;
import appvideo.main.Launcher;
import appvideo.persistencia.DAOException;
import pulsador.Luz;

import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

public class VistaPrincipal extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int ANCHO_FRAME = 960;
	private static final int ALTO_FRAME = 600;

	private JFrame AppVideo;
	private JLabel logoAppVideo;
	private JLabel labelUsuario;
	private JLabel labelContrasena;
	private JTextField campoUsuario;
	private JButton botonAcceder;
	private JButton botonRegistrarse;
	private JButton btnPDF;
	private JButton btnTOP;

	private JPanel panel;
	private JPanel panelVentanas;
	private JPanel panelVista;
	private JPanel panelViejo;
	private JPanel panelNuevo;
	private JPanel panelAUX;

	private Controlador controlador;
	private VistaRegistro vistaRegistro;
	private JPanel vistaLogin;
	private VistaExplorar vistaExplorar;
	private VistaNuevaLista vistaNuevaLista;
	private Luz luz;

	private JLabel logoAppVideo2;
	private JButton btnExplorar;
	private JButton btnHistorial;
	private JButton btnMisListas;
	private JButton btnNuevaLista;
	private JComboBox<String> comboBoxFiltros;
	private JMenu mnUsuario;
	private JButton btnLogout;
	private JButton btnPremium;
	private JMenuBar menuBar;
	private JLabel lblNewLabel;
	private JPasswordField campoContrasena;
	private JLabel lblNewLabel_1;

	public VistaPrincipal() {
		this.controlador = Controlador.getUnicaInstancia();
		initialize();
		vistaRegistro = new VistaRegistro(this);
		vistaExplorar = new VistaExplorar(this);
		vistaNuevaLista = new VistaNuevaLista(this);
	}

	public JFrame getAppVideo() {
		return AppVideo;
	}

	public Controlador getControlador() {
		return controlador;
	}

	private void registroUsuario() {
		botonRegistrarse.addActionListener(ev -> {

			if (ev.getSource() == botonRegistrarse)
				cargarRegistro();
		});

	}

	private void accesoUsuario() {
		botonAcceder.addActionListener(ev -> {
			boolean login = Controlador.getUnicaInstancia().loginUsuario(campoUsuario.getText(),
					String.valueOf(campoContrasena.getPassword()));
			if (login) {
				vistaLogin = panelViejo;
				cargarRecientes();
				cargarFiltros();
				if (controlador.getUsuarioActual().isPremium()) {
					btnPDF.setVisible(true);
					btnTOP.setVisible(true);
				}
			} else {
				JOptionPane.showMessageDialog(AppVideo, "Nombre de usuario o password no coinciden", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		});

	}

	private void logoutUsuario() {
		btnLogout.addActionListener(ev -> {
			if (VistaRecientes.getReproduciendo() == true || VistaMisListas.getReproduciendo() == true) {
				Launcher.getVideoWeb().cancel();
			}
			controlador.logoutUsuario();
			cargarLogin();
		});
	}

	private void botonExplorar() {
		btnExplorar.addActionListener(ev -> {
			try {
				if (VistaRecientes.getReproduciendo() == true || VistaMisListas.getReproduciendo() == true) {
					Launcher.getVideoWeb().cancel();
				}
				cargarExplorar();
			} catch (DAOException e) {
				e.printStackTrace();
			}
		});
	}

	private void botonRecientes() {
		btnHistorial.addActionListener(ev -> {
			if (VistaMisListas.getReproduciendo() == true) {
				Launcher.getVideoWeb().cancel();
			}
			cargarRecientes();
		});
	}

	private void botonMisListas() {
		btnMisListas.addActionListener(ev -> {
			if (VistaRecientes.getReproduciendo() == true) {
				Launcher.getVideoWeb().cancel();
			}
			cargarMisListas();
		});
	}

	private void botonNuevaLista() {
		btnNuevaLista.addActionListener(ev -> {
			if (VistaRecientes.getReproduciendo() == true || VistaMisListas.getReproduciendo() == true) {
				Launcher.getVideoWeb().cancel();
			}
			cargarNuevaLista();
		});
	}

	private void botonLuz() {
		luz.addEncendidoListener(ev -> {
			JFileChooser fileChooser = new JFileChooser();
			int seleccion = fileChooser.showOpenDialog(luz);
			if (seleccion == JFileChooser.APPROVE_OPTION) {
				File fichero = fileChooser.getSelectedFile();
				this.controlador.cargarVideos(fichero);
				controlador.cargarEtiquetasXML();
			}
		});
	}
	
	private void botonPremium() {
		btnPremium.addActionListener(ev -> {
			if(controlador.getUsuarioActual().isPremium()) {
				Object[] options = {"Aceptar","Cancelar"};
			    int opcion =JOptionPane.showOptionDialog(AppVideo,"Ya es usuario premium, ¿desea dejar de serlo?\n Perderá todas sus ventajas","Premium",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,new ImageIcon(VistaPrincipal.class.getResource("/appvideo/imagenes/FotoPerfil.png")),options, options[0]);
			    if (opcion == JOptionPane.YES_OPTION) {
			    	controlador.setPremium(false);
					btnPDF.setVisible(false);
					btnTOP.setVisible(false);
					cargarFiltros();
			    }
			} else {
				Object[] options = {"Aceptar","Cancelar"};
			    int opcion =JOptionPane.showOptionDialog(AppVideo," Todavía no es usuario premium, ¿desea serlo?\n Ser premium desbloquea múltiples funciones"
			    		+ " como:\n Acceso a filtros, acceso a una lista con los videos más reproducidos y poder generar un PDF con "
			    		+ "tus listas","Premium",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,new ImageIcon(VistaPrincipal.class.getResource("/appvideo/imagenes/FotoPerfil.png")),options, options[0]);
			    if (opcion == JOptionPane.YES_OPTION) {
			    	controlador.setPremium(true);
					btnPDF.setVisible(true);
					btnTOP.setVisible(true);
					cargarFiltros();
			    }
			}
		});
	}
	
	private void botonPDF() {
		btnPDF.addActionListener(ev ->{
			try {
				controlador.generarPDF();
				JOptionPane.showMessageDialog(AppVideo, "PDF Generado correctamente.", "Registro", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(AppVideo, "No se pudo generar el PDF.", "Registro", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
	
	private void botonTOP() {
		btnTOP.addActionListener(ev ->{
			if (VistaMisListas.getReproduciendo() == true) {
				Launcher.getVideoWeb().cancel();
			}
			cargarMasVistos();
		});
	}
	
	private void seleccionarFiltro() {
		comboBoxFiltros.addActionListener(ev ->{
			if(controlador.isLogeado() && comboBoxFiltros.getSelectedItem() != null) {
				String nombreFiltro = (String) comboBoxFiltros.getSelectedItem();
				controlador.cambiarFiltro(nombreFiltro);
			}
		});
	}
	
	private void cargarFiltros() {
		comboBoxFiltros.removeAllItems();
		comboBoxFiltros.addItem("NoFiltro");
		if(controlador.getUsuarioActual().isPremium()) {
			comboBoxFiltros.addItem("FiltroMisListas");
			comboBoxFiltros.addItem("FiltroNombresLargos");
			comboBoxFiltros.addItem("FiltroImpopulares");
		}
		comboBoxFiltros.setSelectedItem(controlador.getUsuarioActual().getFiltro().getClass().getName().substring(17));
	}

	private void listener() {
		registroUsuario();
		accesoUsuario();
		logoutUsuario();
		botonExplorar();
		botonRecientes();
		botonMisListas();
		botonNuevaLista();
		botonLuz();
		botonPremium();
		botonPDF();
		botonTOP();
		seleccionarFiltro();
	}

	private void initialize() {
		AppVideo = new JFrame();
		AppVideo.setTitle("AppVideo");
		AppVideo.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(VistaPrincipal.class.getResource("/appvideo/imagenes/YouTube_social_white_squircle.png")));
		AppVideo.setBackground(Color.WHITE);
		//AppVideo.setBounds(800, 300, ANCHO_FRAME, ALTO_FRAME);
		AppVideo.setBounds(800, 300, 425, 310);
		AppVideo.setResizable(false);
		AppVideo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		AppVideo.getContentPane().add(panel, BorderLayout.NORTH);

		logoAppVideo2 = new JLabel("");
		logoAppVideo2.setHorizontalAlignment(SwingConstants.LEFT);
		logoAppVideo2
				.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/appvideo/imagenes/Logo AppVideo 5.png")));
		panel.add(logoAppVideo2);

		btnExplorar = new JButton("");
		btnExplorar.setBorder(null);
		btnExplorar.setMargin(new Insets(2, 5, 2, 5));
		btnExplorar.setBorderPainted(false);
		btnExplorar.setBackground(new Color(240, 240, 240));
		btnExplorar.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/appvideo/imagenes/ExplorarBoton.png")));
		panel.add(btnExplorar);

		btnHistorial = new JButton("");
		btnHistorial.setBackground(new Color(240, 240, 240));
		btnHistorial.setBorderPainted(false);
		btnHistorial.setBorder(null);
		btnHistorial.setMargin(new Insets(2, 5, 2, 5));
		btnHistorial.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/appvideo/imagenes/BotonRecientes.png")));
		panel.add(btnHistorial);

		btnMisListas = new JButton("");
		btnMisListas.setBackground(new Color(240, 240, 240));
		btnMisListas.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/appvideo/imagenes/BotonListas.png")));
		btnMisListas.setBorderPainted(false);
		btnMisListas.setBorder(null);
		panel.add(btnMisListas);

		btnNuevaLista = new JButton("");
		btnNuevaLista.setBackground(new Color(240, 240, 240));
		btnNuevaLista.setBorder(null);
		btnNuevaLista.setBorderPainted(false);
		btnNuevaLista.setMargin(new Insets(2, 5, 2, 5));
		btnNuevaLista.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/appvideo/imagenes/BotonNueva.png")));
		panel.add(btnNuevaLista);

		lblNewLabel = new JLabel("                       ");
		panel.add(lblNewLabel);

		comboBoxFiltros = new JComboBox<String>();
		comboBoxFiltros.setBackground(new Color(240, 240, 240));
		comboBoxFiltros.setPreferredSize(new Dimension(100, 22));
		panel.add(comboBoxFiltros);

		lblNewLabel_1 = new JLabel("  ");
		panel.add(lblNewLabel_1);

		luz = new Luz();
		luz.setEncendido(true);
		luz.setColor(Color.RED);
		panel.add(luz);

		menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBackground(new Color(240, 240, 240));
		panel.add(menuBar);

		mnUsuario = new JMenu("");
		mnUsuario.setBackground(new Color(240, 240, 240));
		mnUsuario.setBorder(null);
		mnUsuario.setIcon(new ImageIcon(VistaPrincipal.class.getResource("/appvideo/imagenes/FotoPerfil.png")));
		menuBar.add(mnUsuario);

		btnLogout = new JButton("Logout");
		btnLogout.setPreferredSize(new Dimension(75, 23));
		btnLogout.setMinimumSize(new Dimension(75, 23));
		btnLogout.setMaximumSize(new Dimension(75, 23));
		btnLogout.setBackground(new Color(240, 240, 240));
		btnLogout.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		btnLogout.setMargin(new Insets(2, 14, 2, 35));
		mnUsuario.add(btnLogout);

		btnPremium = new JButton("Premium");
		btnPremium.setForeground(new Color(255, 255, 255));
		btnPremium.setPreferredSize(new Dimension(75, 23));
		btnPremium.setMinimumSize(new Dimension(75, 23));
		btnPremium.setMaximumSize(new Dimension(75, 23));
		btnPremium.setBackground(new Color(128, 0, 0));
		btnPremium.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mnUsuario.add(btnPremium);
		
		btnPDF = new JButton("PDF");
		btnPDF.setForeground(new Color(255, 255, 255));
		btnPDF.setPreferredSize(new Dimension(75, 23));
		btnPDF.setMinimumSize(new Dimension(75, 23));
		btnPDF.setMaximumSize(new Dimension(75, 23));
		btnPDF.setBackground(new Color(255, 0, 0));
		btnPDF.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mnUsuario.add(btnPDF);
		
		btnTOP = new JButton("Top 10");
		btnTOP.setForeground(new Color(255, 255, 255));
		btnTOP.setPreferredSize(new Dimension(75, 23));
		btnTOP.setMinimumSize(new Dimension(75, 23));
		btnTOP.setMaximumSize(new Dimension(75, 23));
		btnTOP.setBackground(new Color(255, 0, 0));
		btnTOP.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		mnUsuario.add(btnTOP);
		
		btnPDF.setVisible(false);
		btnTOP.setVisible(false);
		
		panel.setVisible(false);

		panelVentanas = new JPanel();
		panelVentanas.setBorder(new LineBorder(new Color(0, 0, 0)));
		AppVideo.getContentPane().add(panelVentanas, BorderLayout.CENTER);
		panelViejo = panelVentanas;

		panelVista = new JPanel();
		panelVentanas.add(panelVista);
		GridBagLayout gbl_panelVista = new GridBagLayout();
		gbl_panelVista.columnWidths = new int[] { 0, 128, 0, 0, 0 };
		gbl_panelVista.rowHeights = new int[] { 31, 21, 52, 0, 76, 0 };
		gbl_panelVista.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelVista.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelVista.setLayout(gbl_panelVista);

		ImageIcon img = new ImageIcon(VistaPrincipal.class.getResource("/appvideo/imagenes/Logo AppVideo.png"));
		Image logo = img.getImage();
		Image logoModificado = logo.getScaledInstance(250, 65, Image.SCALE_SMOOTH);
		img = new ImageIcon(logoModificado);

		logoAppVideo = new JLabel("");
		logoAppVideo.setIcon(img);
		GridBagConstraints gbc_logoAppVideo = new GridBagConstraints();
		gbc_logoAppVideo.gridwidth = 2;
		gbc_logoAppVideo.insets = new Insets(0, 0, 5, 5);
		gbc_logoAppVideo.gridx = 1;
		gbc_logoAppVideo.gridy = 1;
		panelVista.add(logoAppVideo, gbc_logoAppVideo);

		labelUsuario = new JLabel("Usuario");
		labelUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_labelUsuario = new GridBagConstraints();
		gbc_labelUsuario.anchor = GridBagConstraints.EAST;
		gbc_labelUsuario.insets = new Insets(10, 0, 5, 5);
		gbc_labelUsuario.gridx = 1;
		gbc_labelUsuario.gridy = 2;
		panelVista.add(labelUsuario, gbc_labelUsuario);

		campoUsuario = new JTextField();
		GridBagConstraints gbc_campoUsuario = new GridBagConstraints();
		gbc_campoUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoUsuario.insets = new Insets(10, 10, 5, 20);
		gbc_campoUsuario.gridx = 2;
		gbc_campoUsuario.gridy = 2;
		panelVista.add(campoUsuario, gbc_campoUsuario);
		campoUsuario.setColumns(10);

		labelContrasena = new JLabel("Contraseña");
		labelContrasena.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_labelContrasena = new GridBagConstraints();
		gbc_labelContrasena.anchor = GridBagConstraints.EAST;
		gbc_labelContrasena.insets = new Insets(0, 0, 5, 5);
		gbc_labelContrasena.gridx = 1;
		gbc_labelContrasena.gridy = 3;
		panelVista.add(labelContrasena, gbc_labelContrasena);

		campoContrasena = new JPasswordField();
		campoContrasena.setColumns(10);
		GridBagConstraints gbc_campoContrasena = new GridBagConstraints();
		gbc_campoContrasena.insets = new Insets(0, 10, 5, 20);
		gbc_campoContrasena.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoContrasena.gridx = 2;
		gbc_campoContrasena.gridy = 3;
		panelVista.add(campoContrasena, gbc_campoContrasena);

		botonAcceder = new JButton("Acceder");
		GridBagConstraints gbc_botonAcceder = new GridBagConstraints();
		gbc_botonAcceder.insets = new Insets(0, 30, 0, 5);
		gbc_botonAcceder.gridx = 1;
		gbc_botonAcceder.gridy = 4;
		panelVista.add(botonAcceder, gbc_botonAcceder);

		botonRegistrarse = new JButton("Registrarse");
		GridBagConstraints gbc_botonRegistrarse = new GridBagConstraints();
		gbc_botonRegistrarse.insets = new Insets(0, 0, 0, 5);
		gbc_botonRegistrarse.gridx = 2;
		gbc_botonRegistrarse.gridy = 4;
		panelVista.add(botonRegistrarse, gbc_botonRegistrarse);

		listener();

	}

	public void cargarRegistro() {
		AppVideo.setBounds(800, 300, 558, 454);
		panel.setVisible(false);
		vistaLogin = panelViejo;
		panelNuevo = vistaRegistro;
		AppVideo.remove(panelViejo);
		panelViejo = panelNuevo;
		AppVideo.getContentPane().add(panelNuevo, BorderLayout.CENTER);
		this.vistaRegistro.limpiar();
		AppVideo.revalidate();
		AppVideo.repaint();
		AppVideo.validate();
	}

	public void limpiarLogin() {
		campoUsuario.setText("");
		campoContrasena.setText("");
	}

	public void cargarLogin() {
		AppVideo.setBounds(800, 300, 425, 310);
		panel.setVisible(false);
		panelNuevo = vistaLogin;
		AppVideo.remove(panelViejo);
		panelViejo = panelNuevo;
		AppVideo.getContentPane().add(panelNuevo, BorderLayout.CENTER);
		limpiarLogin();
		AppVideo.setResizable(true);
		AppVideo.revalidate();
		AppVideo.repaint();
		AppVideo.validate();
	}

	public void cargarExplorar() throws DAOException {
		AppVideo.setBounds(600, 200, ANCHO_FRAME, ALTO_FRAME);
		panel.setVisible(true);
		panelNuevo = vistaExplorar;
		vistaExplorar.cargarEtiquetas();
		AppVideo.remove(panelViejo);
		panelViejo = panelNuevo;
		AppVideo.getContentPane().add(panelNuevo, BorderLayout.CENTER);
		this.vistaRegistro.limpiar();
		AppVideo.setResizable(true);
		AppVideo.revalidate();
		AppVideo.repaint();
		AppVideo.validate();
	}

	public void cargarRecientes() {
		AppVideo.setBounds(600, 200, ANCHO_FRAME, ALTO_FRAME);
		panel.setVisible(true);
		VistaRecientes vistaRecientes = new VistaRecientes(this);
		panelNuevo = vistaRecientes;
		vistaRecientes.cargarListaRecientes();
		AppVideo.remove(panelViejo);
		panelViejo = panelNuevo;
		AppVideo.getContentPane().add(panelNuevo, BorderLayout.CENTER);
		this.vistaRegistro.limpiar();
		AppVideo.setResizable(true);
		AppVideo.revalidate();
		AppVideo.repaint();
		AppVideo.validate();
	}
	
	public void cargarMasVistos() {
		AppVideo.setBounds(600, 200, ANCHO_FRAME, ALTO_FRAME);
		panel.setVisible(true);
		VistaRecientes vistaRecientes = new VistaRecientes(this);
		panelNuevo = vistaRecientes;
		vistaRecientes.cargarMasVistos();
		AppVideo.remove(panelViejo);
		panelViejo = panelNuevo;
		AppVideo.getContentPane().add(panelNuevo, BorderLayout.CENTER);
		this.vistaRegistro.limpiar();
		AppVideo.setResizable(true);
		AppVideo.revalidate();
		AppVideo.repaint();
		AppVideo.validate();
	}

	public void cargarMisListas() {
		AppVideo.setBounds(600, 200, ANCHO_FRAME, ALTO_FRAME);
		panel.setVisible(true);
		panelNuevo = new VistaMisListas(this);
		AppVideo.remove(panelViejo);
		panelViejo = panelNuevo;
		AppVideo.getContentPane().add(panelNuevo, BorderLayout.CENTER);
		this.vistaRegistro.limpiar();
		AppVideo.setResizable(true);
		AppVideo.revalidate();
		AppVideo.repaint();
		AppVideo.validate();
	}

	public void cargarNuevaLista() {
		AppVideo.setBounds(600, 200, ANCHO_FRAME, ALTO_FRAME);
		panel.setVisible(true);
		panelNuevo = vistaNuevaLista;
		AppVideo.remove(panelViejo);
		panelViejo = panelNuevo;
		AppVideo.getContentPane().add(panelNuevo, BorderLayout.CENTER);
		this.vistaRegistro.limpiar();
		AppVideo.setResizable(true);
		AppVideo.revalidate();
		AppVideo.repaint();
		AppVideo.validate();
	}

	public void cargarVideo(VistaVideo vistaVideo) {
		AppVideo.setBounds(600, 200, ANCHO_FRAME, ALTO_FRAME);
		panel.setVisible(false);
		panelAUX = panelViejo;
		AppVideo.remove(panelViejo);
		panelViejo = vistaVideo;
		AppVideo.getContentPane().add(vistaVideo, BorderLayout.CENTER);
		AppVideo.revalidate();
		AppVideo.repaint();
		AppVideo.validate();
	}

	public void salirVideo() throws DAOException {
		AppVideo.setBounds(600, 200, ANCHO_FRAME, ALTO_FRAME);
		panel.setVisible(true);
		AppVideo.remove(panelViejo);
		vistaExplorar.cargarEtiquetas();
		panelViejo = panelAUX;
		AppVideo.getContentPane().add(panelAUX, BorderLayout.CENTER);
		AppVideo.revalidate();
		AppVideo.repaint();
		AppVideo.validate();
	}

}
