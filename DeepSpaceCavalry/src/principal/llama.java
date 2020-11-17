package principal;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class llama implements Runnable, MouseMotionListener {

	MiPanel mp;
	private int x;
	private int y;
	private int posicion;
	private int vel=200;
	
	public llama (MiPanel mp) {
		this.mp = mp;
		
	}

	public void run() {
		this.posicion = 0;
		while (true){
			int i;
			for (i=0; i<4;i++){
				this.posicion = i;
				try {
					Thread.sleep(vel);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
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

	
	public void mouseDragged(MouseEvent e) {
		this.x=e.getX();
		this.y=e.getY();
		
	}

	public void mouseMoved(MouseEvent e) {
		this.x=e.getX();
		this.y=e.getY();
		
	}

}
