package appvideo.main;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import appvideo.controlador.Controlador;
import appvideo.dominio.Video;
import appvideo.vistas.VistaPrincipal;
import tds.video.VideoWeb;

public class Launcher 
{
	private static VideoWeb videoWeb;
	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	videoWeb = new VideoWeb();
                	Controlador controlador = Controlador.getUnicaInstancia();
                	VistaPrincipal pantallaInicial = new VistaPrincipal(controlador);
                	pantallaInicial.getAppVideo().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	public static VideoWeb getVideoWeb() {
		return videoWeb;
	}
	
	//TODO Revisar
	public static TableCellRenderer createTableRenderer(){
		return new DefaultTableCellRenderer() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				JLabel label = null;
				if (value != null) {
					label = (JLabel) c;
					Video video = (Video) value;
					label.setHorizontalTextPosition(JLabel.CENTER);
					label.setVerticalTextPosition(JLabel.BOTTOM);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setVerticalAlignment(SwingConstants.CENTER);
					label.setIcon(videoWeb.getThumb(video.getUrl()));
					label.setText(video.getTitulo());
				}
				return label;
			}
		};
	}
}
