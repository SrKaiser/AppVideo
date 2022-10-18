package appvideo.extra;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import appvideo.dominio.Video;

@SuppressWarnings("serial")
public class VideoTableModel extends AbstractTableModel {
	
	private static final int numColumnas = 4;
	private List<Video> videos;
	
	public VideoTableModel() {
		this.videos = new LinkedList<Video>();
	}
	
	@Override
	public int getRowCount() {
		if(videos.size()%numColumnas != 0) {
			return (videos.size()/numColumnas) +1;
		}
		return videos.size()/numColumnas;
	}

	@Override
	public int getColumnCount() {
		return numColumnas;
	}

	@Override
	public Video getValueAt(int rowIndex, int columnIndex) {
		return videos.get(rowIndex*4+columnIndex);
	}
	
	public void fillTable(List<Video> videos) {
		this.videos = videos;
		int tam = videos.size();
		for(int i = 0; i < numColumnas-(tam%numColumnas); i++) {
			this.videos.add(null);
		}
	}
	
	public void deleteRow(int row) {
		int tam;
		if( videos.size()%numColumnas == 0 ) {
			tam = videos.size()/numColumnas;
		} else {
			tam = (videos.size()/numColumnas)+1;
		}
		
		if( row < tam ) {
			videos.remove(4*row + 3);
			videos.remove(4*row + 2);
			videos.remove(4*row + 1);
			videos.remove(4*row);
		}
	}

}
