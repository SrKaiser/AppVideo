package appvideo.test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import appvideo.dominio.Etiqueta;
import appvideo.dominio.RepositorioVideos;
import appvideo.dominio.Video;
import appvideo.persistencia.DAOException;

public class AppTest {

	public RepositorioVideos repositorio;
	public List<Video> coleccionVideos;
	public Video video1;
	public Video video2;
	public Video video3;


	@Before
	public void setUp() throws DAOException {
		repositorio = RepositorioVideos.getUnicaInstancia();

		// Quitamos los posibles videos que haya debido a la persistencia para poder ser
		// conscientes al 100% de los videos que manejamos.
		for (Video video : repositorio.getVideos()) {
			repositorio.removeVideo(video);
		}

		coleccionVideos = new LinkedList<Video>();

		video1 = new Video("Invasion", "https://www.youtube.com/watch?v=DlaHxL3mHAU");
		video1.setId(1);
		video1.setNumReproducciones(0);
		coleccionVideos.add(video1);
		video2 = new Video("Arctic Monkeys - Do I Wanna Know?", "https://www.youtube.com/watch?v=bpOSxM0rNPM");
		video2.setId(2);
		video2.setNumReproducciones(5);
		coleccionVideos.add(video2);
		video3 = new Video("Me at the zoo", "https://www.youtube.com/watch?v=jNQXAC9IVRw", new Etiqueta("Zoo"));
		video3.setId(3);
		video3.setNumReproducciones(10);
		coleccionVideos.add(video3);

	}

	@Test
	public void testAddVideo() throws DAOException {

		// Pruebo el método addVideo al repositorio
		for (Video video : coleccionVideos) {
			repositorio.addVideo(video);
		}

		LinkedList<Video> videosRepositorio = repositorio.getVideos();

		// Compruebo que se han añadido
		for (Video video : coleccionVideos) {
			assertTrue(videosRepositorio.contains(video));
		}
	}

	@Test
	public void testRemoveVideo() throws DAOException {
		// Para borrar primero debemos añadir, sabemos que funciona correctamente por el test anterior
		for (Video video : coleccionVideos) {
			repositorio.addVideo(video);
		}
		
		// Pruebo el método removeVideo del repositorio
		for (Video video : coleccionVideos) {
			repositorio.removeVideo(video);
		}
		
		LinkedList<Video> videosRepositorio = repositorio.getVideos();

		// Compruebo que se han añadido
		for (Video video : coleccionVideos) {
			assertFalse(videosRepositorio.contains(video));
		}
	}
	
	@Test
	public void testExplorarVideoTitulo() throws DAOException {
		// Para buscar primero debemos añadir, sabemos que funciona correctamente por el test anterior
		for (Video video : coleccionVideos) {
			repositorio.addVideo(video);
		}
		
		// Pruebo el método explorarVideos del repositorio
		List<Video> videosExplorar = repositorio.explorarVideos("Me at", null);
		// Compruebo que la búsqueda es correcta
		assertEquals(videosExplorar.get(0).getTitulo(), video3.getTitulo());
		
	}
	
	@Test
	public void testExplorarVideoSinTitulo() throws DAOException {
		// Para buscar primero debemos añadir, sabemos que funciona correctamente por el test anterior
		for (Video video : coleccionVideos) {
			repositorio.addVideo(video);
		}
		
		// Pruebo el método explorarVideos del repositorio
		List<Video> videosExplorar = repositorio.explorarVideos(new String(), video3.getEtiquetas());
		// Compruebo que la búsqueda es correcta
		assertEquals(videosExplorar.get(0).getTitulo(), video3.getTitulo());
		
	}
	
	@Test
	public void testgetMasVistos() throws DAOException {
		// Para buscar primero debemos añadir, sabemos que funciona correctamente por el test anterior
		for (Video video : coleccionVideos) {
			repositorio.addVideo(video);
		}
		
		// Pruebo el método explorarVideos del repositorio
		List<Video> videosMasVistos = repositorio.getMasVistos();
		// Compruebo que devuelve una lista con el orden correcto
		assertEquals(videosMasVistos.get(0).getTitulo(), video3.getTitulo());
		assertEquals(videosMasVistos.get(1).getTitulo(), video2.getTitulo());
		assertEquals(videosMasVistos.get(2).getTitulo(), video1.getTitulo());
		
	}
	
	

}
