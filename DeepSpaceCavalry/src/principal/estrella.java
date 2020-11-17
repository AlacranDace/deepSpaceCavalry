package principal;

import java.util.Random;

public class estrella implements Runnable{
	
	MiPanel mp;
	
	private int x;
	private int y;
	private int grosor;
	private int vel;
	
	public estrella(MiPanel mp) {
		
		this.mp = mp;
		
		Random r = new Random();
		
		this.x = r.nextInt(MiFrame.anchura);
		this.y = r.nextInt(MiFrame.altura);
		this.grosor = r.nextInt(4)+2;
		this.vel = 6+(r.nextInt(5)*4);	
	}

	public void run() {
		
		while(true){
			
			NoTeSalgas();
			
			this.x = this.x - 3;
			
			try {
				Thread.sleep(vel);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			mp.repaint();		//ESTE ES EL IMPORTANTE; SIN ESTO NO PINTA
			
			
		}
		
	}
	
	public void NoTeSalgas(){
		
		if (this.x<=0){
			this.x = 1010;
			Random r = new Random();
			this.y = r.nextInt(MiFrame.altura);
			this.grosor = r.nextInt(4)+1;
			this.vel = 6+(r.nextInt(4)*4);
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

	public int getGrosor() {
		return grosor;
	}

	public void setGrosor(int grosor) {
		this.grosor = grosor;
	}

	public int getVel() {
		return vel;
	}

	public void setVel(int vel) {
		this.vel = vel;
	}
	
}
