package appvideo.extra;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import appvideo.dominio.Video;
import appvideo.main.Launcher;

public class RenderVideos {
	
	@SuppressWarnings("serial")
	public static TableCellRenderer createTableRenderer(){
		return new DefaultTableCellRenderer() {
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
					label.setIcon(Launcher.getVideoWeb().getThumb(video.getUrl()));
					label.setText(video.getTitulo());
					
				}
				return label;
			}
		};
	}
	
	@SuppressWarnings("serial")
	public static ListCellRenderer<? super Video> createListRenderer() {
		return new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				JLabel label = null;
				if (value != null) {
					label = (JLabel) c;
					Video video = (Video) value;
					label.setIcon(Launcher.getVideoWeb().getThumb(video.getUrl()));
					label.setText(video.getTitulo());
					label.setHorizontalTextPosition(JLabel.CENTER);
					label.setVerticalTextPosition(JLabel.BOTTOM);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setVerticalAlignment(SwingConstants.CENTER);
				}
				return label;
			}
		};
	}
}
