package appvideo.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;


@SuppressWarnings("serial")
public class VistaMisListas extends JPanel {

	private VistaPrincipal vistaPrincipal;
	
	public VistaMisListas(VistaPrincipal vistaPrincipal) {
		this.vistaPrincipal = vistaPrincipal;
		initialize();
	}
	
	private void initialize() {
		setBounds(100, 100, 930, 540);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{518, 219, 0};
		gridBagLayout.rowHeights = new int[]{503, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
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
		
		JComboBox comboBox = new JComboBox();
		panel_3.add(comboBox);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_4.add(scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
		
		JButton btnNewButton = new JButton("Reproducir");
		panel_5.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("                              ");
		panel_5.add(lblNewLabel);
		
		JButton btnCancelar = new JButton("Cancelar");
		panel_5.add(btnCancelar);

		
	}


}
