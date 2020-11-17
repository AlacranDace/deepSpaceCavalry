package principal;

import java.awt.Rectangle;

public class disparo extends Thread {
	
	private int nivel;
	private int danio;
	private int x;
	private int y;
	private boolean acabado = false;
	private Rectangle espacio;
	
	public disparo(int niv, int x, int y){
		this.nivel=niv;
		this.x=x;
		this.y=y;
		switch(this.nivel){
			case 1:
				this.danio = 5;
				break;
			case 2:
				this.danio = 10;
				break;
			case 3:
				this.danio = 15;
				break;
			case 4:
				this.danio = 20;
				break;
			case 5:
				this.danio = 25;
				break;
		}
	}
	
	public void run() {
		while(acabado!=true){		//ATENTO A ESTO MELON QUE NO SE PORQUE ESTABA LO OTRO PUESTO
			this.x = this.x + 3;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(this.x>1010){this.acabado=true;}
		}
		this.setAcabado(true);
		
	}

	public Rectangle getEspacio() {
		return espacio;
	}

	public void setEspacio(Rectangle espacio) {
		this.espacio = espacio;
	}

	public int getDanio() {
		return danio;
	}

	public void setDanio(int danio) {
		this.danio = danio;
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

	public boolean isAcabado() {
		return acabado;
	}

	public void setAcabado(boolean acabado) {
		this.acabado = acabado;
	}

	

}
