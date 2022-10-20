package appvideo.dominio;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Usuario {
	
	private int id;
	private String nombre;
	private String apellidos;
	private String email;
	private Date fechaNacimiento;
	private String login;
	private String password;
	private boolean premium;
	
	private ListaVideos videosRecientes;
	private static final int MAX_VIDEOS_RECIENTES = 5;
	
	private List<ListaVideos> listasVideos;
	
	public Usuario(String nombre, String apellidos, String email, Date fechaNacimiento, String login, String password) {
		this.id = 0;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
		this.login = login;
		this.password = password;
		this.premium = false;
		
		this.videosRecientes = new ListaVideos("recientes");
		this.listasVideos = new LinkedList<ListaVideos>();
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public ListaVideos getVideosRecientes() {
		return videosRecientes;
	}

	public void setVideosRecientes(ListaVideos videosRecientes) {
		this.videosRecientes = videosRecientes;
	}
	
	public void addVideoReciente(Video video) {
		
		if(videosRecientes.getNombresVideos().contains(video.getTitulo())) {
			videosRecientes.eliminarVideo(video);
				
		}
		
		if(videosRecientes.getVideos().size() >= MAX_VIDEOS_RECIENTES) {
			videosRecientes.getVideos().removeLast();
		}
		
		videosRecientes.getVideos().addFirst(video);
	}

	public List<ListaVideos> getListasVideos() {
		return listasVideos;
	}

	public void setListasVideos(List<ListaVideos> listasVideos) {
		this.listasVideos = listasVideos;
	}
	
	public ListaVideos getListaVideosNombre(String nombreLista) {
		for (ListaVideos listaVideos : listasVideos) {
			if (listaVideos.getNombre().equals(nombreLista)) {
				return listaVideos;
			}
		}
		return null;
	}
	
	public void addListaVideos(ListaVideos listaVideos) {
		listasVideos.add(listaVideos);
	}
	
	public void removeListaVideos(String nombreLista) {
		for (ListaVideos listaVideos : listasVideos) {
			if (listaVideos.getNombre().equals(nombreLista)) {
				listasVideos.remove(listaVideos);
			}
		}
	}
	
	public String imprimirListas() {
		String imprimir = "";
		for(ListaVideos listaVideo : listasVideos) {
			imprimir += "Nombre de la lista: " + listaVideo.getNombre() + "\n";
			imprimir += listaVideo.imprimirVideos() + "\n";
		}
		return imprimir;
	}
	
	
}
