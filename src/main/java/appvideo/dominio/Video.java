package appvideo.dominio;

import java.util.Collections;
import java.util.LinkedList;

public class Video {
	
	private int id;
	private String titulo;
	private LinkedList<Etiqueta> etiquetas;
	private String url;
	private int numReproducciones;
	
	public Video(String titulo, String url, Etiqueta ...etiquetasVideo) {
		this.id = 0;
		this.titulo = titulo;
		this.etiquetas = new LinkedList<Etiqueta>();
		Collections.addAll(etiquetas, etiquetasVideo);
		this.url = url;
		this.numReproducciones = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LinkedList<Etiqueta> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(LinkedList<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getNumReproducciones() {
		return numReproducciones;
	}

	public void setNumReproducciones(int numReproducciones) {
		this.numReproducciones = numReproducciones;
	}

	public void addEtiqueta(Etiqueta etiqueta) {
		etiquetas.add(etiqueta);
	}
	
	
	
	
}
