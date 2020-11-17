package principal;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MiNave implements MouseMotionListener {
	
	private int nivel;
	private int x,y;
	private MiPanel mp;
	
	public MiNave(MiPanel mp){
		this.mp = mp;
		//this.nivel = 1;
	}
	
	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
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

	public void mouseDragged(MouseEvent e) {
		this.x=e.getX();
		this.y=e.getY();
	}



	public void mouseMoved(MouseEvent e) {
		this.x=e.getX();
		this.y=e.getY();
	}
	
}
