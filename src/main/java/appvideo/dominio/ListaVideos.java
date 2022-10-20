package appvideo.dominio;

import java.util.LinkedList;

public class ListaVideos {
	
	private int id;
	private String nombre;
	private LinkedList<Video> videos;
	
	
	public ListaVideos(String nombre, LinkedList<Video> videos) {
		this.id = 0;
		this.nombre = nombre;
		this.videos = videos;
	}
	
	public ListaVideos(String nombre) {
		this(nombre, new LinkedList<Video>());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LinkedList<Video> getVideos() {
		return videos;
	}
	
	public LinkedList<String> getNombresVideos(){
		LinkedList<String> nombresVideos = new LinkedList<>();
		for(Video video : videos) {
			nombresVideos.add(video.getTitulo());
		}
		return nombresVideos;
	}
	
	public void eliminarVideo(Video video) {
		Video videoAUX=null;
		for(Video video1 : videos) {
			if(video1.getTitulo().equals(video.getTitulo())){
				videoAUX = video1;
			}
		}
		videos.remove(videoAUX);
	}

	public void setVideos(LinkedList<Video> videos) {
		this.videos = videos;
	}
	
	public void addVideo(Video video) {
		videos.add(video);
	}
	
	public void removeVideo(Video video) {
		videos.remove(video);
	}
	
	public void removeAllVideos() {
		for(Video video : videos) {
			videos.remove(video);
		}

	}
	
	public String imprimirVideos() {
		String imprimir = "";
		for (Video video : videos) {
			imprimir += "\tTítulo: " + video.getTitulo() + " - Reproducciones: " + video.getNumReproducciones() + "\n";
		}
		return imprimir;
	}
	
	
	
	
}
