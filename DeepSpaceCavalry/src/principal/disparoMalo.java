package principal;

public class disparoMalo implements Runnable{
	
	private int nivel;
	private int danio;
	private int x;
	private int y;
	private boolean acabado = false;
	
	public disparoMalo(int nivel, int x, int y){
		this.nivel=nivel;
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
		while(this.x > 0){
			this.x = this.x - 3;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.acabado = true;
		
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
