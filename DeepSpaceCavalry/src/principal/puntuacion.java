package principal;

public class puntuacion {
	
	private int puntos;
	private puntuacion siguiente = null;
	
	public puntuacion(int punt){
		this.puntos = punt;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public puntuacion getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(puntuacion siguiente) {
		this.siguiente = siguiente;
	}

}
