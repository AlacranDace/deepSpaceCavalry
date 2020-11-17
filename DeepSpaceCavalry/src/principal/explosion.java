package principal;

public class explosion  extends Thread {
	
	private int x;
	private int y;
	private int posicion;
	private int vel=100;
	private boolean acabado = false;

	public explosion (int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public void run() {
		for (int i=0; i<7;i++){
			this.posicion = i;
			try {
				Thread.sleep(vel);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.acabado = true;
	}
	
	public boolean isAcabado() {
		return acabado;
	}

	public void setAcabado(boolean acabado) {
		this.acabado = acabado;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public int getVel() {
		return vel;
	}

	public void setVel(int vel) {
		this.vel = vel;
	}
}
