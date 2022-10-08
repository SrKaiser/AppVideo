package appvideo.vistas;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import appvideo.extra.TextPrompt;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import javax.swing.JScrollPane;


@SuppressWarnings("serial")
public class VistaNuevaLista extends JPanel {

	private JTextField txtBuscar;
	private VistaPrincipal vistaPrincipal;
	private JTextField textField_2;
	
	public VistaNuevaLista(VistaPrincipal vistaPrincipal) {
		this.vistaPrincipal = vistaPrincipal;
		initialize();
	}
	
	private void initialize() {
		
		setBounds(100, 100, 930, 540);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{518, 219, 0};
		gridBagLayout.rowHeights = new int[]{503, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(10);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		txtBuscar = new JTextField();
		TextPrompt promptBuscar = new TextPrompt("Explorar videos", txtBuscar);
		promptBuscar.changeAlpha(0.5f);
		panel_2.add(txtBuscar);
		txtBuscar.setColumns(40);
		
		JButton btnBuscar = new JButton("");
		btnBuscar.setBackground(new Color(240, 240, 240));
		btnBuscar.setBorder(null);
		btnBuscar.setMargin(new Insets(2, 2, 2, 2));
		btnBuscar.setIcon(new ImageIcon(VistaExplorar.class.getResource("/appvideo/imagenes/BotonBusca.png")));
		panel_2.add(btnBuscar);
		
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
		
		textField_2 = new JTextField();
		TextPrompt promptBuscarLista = new TextPrompt("Buscar lista", textField_2);
		promptBuscarLista.changeAlpha(0.5f);
		panel_3.add(textField_2);
		textField_2.setColumns(30);
		
		JPanel panel_6 = new JPanel();
		panel_1.add(panel_6);
		
		JButton btnBuscarLista = new JButton("Buscar");
		panel_6.add(btnBuscarLista);
		
		JButton btnCrear = new JButton("Crear");
		panel_6.add(btnCrear);
		
		JButton btnEliminar = new JButton("Eliminar");
		panel_6.add(btnEliminar);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_4.add(scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
		
		JButton btnAnadir = new JButton("Añadir");
		panel_5.add(btnAnadir);
		
		JButton btnQuitar = new JButton("Quitar");
		panel_5.add(btnQuitar);
		
		JLabel lblNewLabel_1 = new JLabel("                   ");
		panel_5.add(lblNewLabel_1);
		
		JButton btnAceptar = new JButton("Aceptar");
		panel_5.add(btnAceptar);

		
	}


}
