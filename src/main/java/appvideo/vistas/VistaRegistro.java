package appvideo.vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import appvideo.controlador.Controlador;
import appvideo.extra.TextPrompt;

import javax.swing.border.EtchedBorder;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class VistaRegistro extends JPanel {

	private JTextField campoNombre;
	private JTextField campoApellidos;
	private JTextField campoCorreo;
	private JTextField campoUsuario;
	private JLabel etiquetaApp;
	private JButton botonRegistrarse;
	private JButton botonVolver;
	private JDateChooser dateChooser;
	
	private Controlador controlador;
	private VistaPrincipal vistaPrincipal;
	private JPasswordField campoContrasena;
	private JPasswordField campoConfirmar;

	public void limpiar() {
		campoNombre.setText("");
		campoApellidos.setText("");
		dateChooser.setDate(null);
		campoCorreo.setText("");
		campoUsuario.setText("");
		campoContrasena.setText("");
		campoConfirmar.setText("");
	}

	public VistaRegistro(VistaPrincipal vistaPrincipal) {
		this.vistaPrincipal = vistaPrincipal;
		this.controlador = vistaPrincipal.getControlador();
		initialize();
	}
	
	private void volver() {
		botonVolver.addActionListener(ev -> {

			if (ev.getSource() == botonVolver)
					vistaPrincipal.cargarLogin();
		});
			
	}
	
	private void registrarse() {
		botonRegistrarse.addActionListener(ev -> {
			int registrado;
			registrado = controlador.registrarUsuario(campoNombre.getText(),
					campoApellidos.getText(), campoCorreo.getText(), dateChooser.getDate(), campoUsuario.getText(),
					String.valueOf(campoContrasena.getPassword()), String.valueOf(campoConfirmar.getPassword()));
			if (registrado == Controlador.REGISTRO_CORRECTO) {
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(), "Usuario registrado correctamente.", "Registro", JOptionPane.INFORMATION_MESSAGE);
				vistaPrincipal.cargarLogin();
			} else if (registrado == Controlador.REGISTRO_REPETIDO) {
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(), "El usuario introducido ya está registrado.\n", "Registro", JOptionPane.ERROR_MESSAGE);
			} else if (registrado == Controlador.REGISTRO_INCOMPLETO) {
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(), "Por favor rellena todos los campos.\n", "Registro", JOptionPane.ERROR_MESSAGE);
			} else if (registrado == Controlador.REGISTRO_PASSWORDFAIL) {
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(), "Las contraseñas no coinciden.\n", "Registro", JOptionPane.ERROR_MESSAGE);
			} else if ( registrado == Controlador.REGISTRO_EMAILFAIL) {
				JOptionPane.showMessageDialog(vistaPrincipal.getAppVideo(), "El email introducido no es válido.\n", "Registro", JOptionPane.ERROR_MESSAGE);
			}
		});
			
	}
	
	private void manejadorListener() {
		volver();
		registrarse();
	}

	
	private void initialize() {
		setBounds(800, 300, 557, 454);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 203, 189, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{54, 65, 75, 77, 61, 0, 63, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		setMinimumSize(new Dimension(600, 550));
		setMaximumSize(new Dimension(800, 600));
		
		etiquetaApp = new JLabel("Registro para AppVideo  ");
		etiquetaApp.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		GridBagConstraints gbc_etiquetaApp = new GridBagConstraints();
		gbc_etiquetaApp.gridwidth = 2;
		gbc_etiquetaApp.insets = new Insets(0, 0, 5, 5);
		gbc_etiquetaApp.gridx = 1;
		gbc_etiquetaApp.gridy = 0;
		add(etiquetaApp, gbc_etiquetaApp);
		
		campoNombre = new JTextField();
		campoNombre.setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Nombre", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)), "Nombre", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_campoNombre = new GridBagConstraints();
		gbc_campoNombre.insets = new Insets(0, 20, 5, 20);
		gbc_campoNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoNombre.gridx = 1;
		gbc_campoNombre.gridy = 1;
		add(campoNombre, gbc_campoNombre);
		campoNombre.setColumns(10);
		
		campoApellidos = new JTextField();
		campoApellidos.setBorder(new TitledBorder(null, "Apellidos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_campoApellidos = new GridBagConstraints();
		gbc_campoApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoApellidos.insets = new Insets(0, 20, 5, 20);
		gbc_campoApellidos.gridx = 2;
		gbc_campoApellidos.gridy = 1;
		add(campoApellidos, gbc_campoApellidos);
		campoApellidos.setColumns(10);
		
		campoCorreo = new JTextField();
		TextPrompt promptEmail = new TextPrompt("Ej: ejemplo@empresa.es ", campoCorreo);
		promptEmail.changeAlpha(0.5f);
		campoCorreo.setBorder(new TitledBorder(null, "Correo electr\u00F3nico", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_campoCorreo = new GridBagConstraints();
		gbc_campoCorreo.gridwidth = 2;
		gbc_campoCorreo.insets = new Insets(0, 20, 5, 20);
		gbc_campoCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoCorreo.gridx = 1;
		gbc_campoCorreo.gridy = 2;
		add(campoCorreo, gbc_campoCorreo);
		campoCorreo.setColumns(10);
		
		campoUsuario = new JTextField();
		campoUsuario.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Usuario", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_campoUsuario = new GridBagConstraints();
		gbc_campoUsuario.insets = new Insets(0, 20, 5, 20);
		gbc_campoUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoUsuario.gridx = 1;
		gbc_campoUsuario.gridy = 3;
		add(campoUsuario, gbc_campoUsuario);
		campoUsuario.setColumns(10);
		
		
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 20, 5, 20);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 2;
		gbc_textField_5.gridy = 3;
	
		dateChooser = new JDateChooser();
		dateChooser.setPreferredSize(new Dimension(325, 50));
		dateChooser.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Fecha de Nacimiento", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		dateChooser.setBackground(Color.WHITE);
		add(dateChooser, gbc_textField_5);
		
		campoContrasena = new JPasswordField();
		campoContrasena.setBorder(new TitledBorder(null, "Contrase\u00F1a", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_campoContrasena = new GridBagConstraints();
		gbc_campoContrasena.insets = new Insets(0, 20, 5, 20);
		gbc_campoContrasena.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoContrasena.gridx = 1;
		gbc_campoContrasena.gridy = 4;
		add(campoContrasena, gbc_campoContrasena);
		
		campoConfirmar = new JPasswordField();
		campoConfirmar.setBorder(new TitledBorder(null, "Confirmar Contrase\u00F1a", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_campoConfirmar = new GridBagConstraints();
		gbc_campoConfirmar.insets = new Insets(0, 20, 5, 20);
		gbc_campoConfirmar.fill = GridBagConstraints.HORIZONTAL;
		gbc_campoConfirmar.gridx = 2;
		gbc_campoConfirmar.gridy = 4;
		add(campoConfirmar, gbc_campoConfirmar);
		
		botonVolver = new JButton("Volver");
		GridBagConstraints gbc_botonVolver = new GridBagConstraints();
		gbc_botonVolver.anchor = GridBagConstraints.WEST;
		gbc_botonVolver.insets = new Insets(0, 22, 0, 5);
		gbc_botonVolver.gridx = 1;
		gbc_botonVolver.gridy = 6;
		add(botonVolver, gbc_botonVolver);
		
		botonRegistrarse = new JButton("Registrarse");
		GridBagConstraints gbc_botonRegistrarse = new GridBagConstraints();
		gbc_botonRegistrarse.anchor = GridBagConstraints.EAST;
		gbc_botonRegistrarse.insets = new Insets(0, 0, 0, 20);
		gbc_botonRegistrarse.gridx = 2;
		gbc_botonRegistrarse.gridy = 6;
		add(botonRegistrarse, gbc_botonRegistrarse);
		
		manejadorListener();

	}

}
