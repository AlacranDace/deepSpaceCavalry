package principal;

import java.awt.Rectangle;
import java.util.Random;

public class enemigo implements Runnable {
	
	/***********TRUCOS*******************/
	private int probDisparoMalo = 	/*Normal:15000*/	15000;	//si mas pequeño mayor prob de que te disparen
	private int sumaPuntos = 		/*Normal:0*/		0;		//Tocando esta variable cada nave sumara los puntos que pongas ademas de los suyos propios
	private int velocidad = 		/*Normal:0*/		0;		//NUMERO DEL 1 al 45!!!Sino se rompe--> A mas cerca de 45 mas rapido iran las naves
	/************************************/
	
	private int cont;
	private MiPanel mp;
	private int nivel;		//Puede ser 1,2 o 3
	private int tipo;		//Puede ser del 1 al 7, cambiará su velocidad de avance
	private int vida;		//Dependera del nivel y tipo del enemigo
	private int x;
	private int y;
	private int vel;		//Dependera del tipo del enemigo, cuanto mas rapido mas facil de matar
	private int puntos;		//A mayor rapidez mas puntos
	private boolean acabado = false;	//Miraremos esto para ver si pintarlo o no
	private Rectangle espacio;
	
	public Rectangle getEspacio() {
		return espacio;
	}

	public void setEspacio(Rectangle espacio) {
		this.espacio = espacio;
	}

	public boolean isAcabado() {
		return acabado;
	}

	public void setAcabado(boolean acabado) {
		this.acabado = acabado;
	}

	public enemigo (MiPanel mp, int niv, int cont){
		
		this.mp = mp;
		this.nivel = niv;
		this.cont = cont;
		
		Random r = new Random();
		this.x = 1050;
		this.y = 50+r.nextInt(MiFrame.altura-50);
		this.tipo = 1+r.nextInt(7);
		eligeVida();
		switch(this.tipo){
		case 1:
			this.puntos = 10+sumaPuntos;
			this.vel = 50-velocidad;
			break;
		case 2:
			this.puntos = 9+sumaPuntos;
			this.vel = 55-velocidad;
			break;
		case 3:
			this.puntos = 8+sumaPuntos;
			this.vel = 60-velocidad;
			break;
		case 4:
			this.puntos = 7+sumaPuntos;
			this.vel = 65-velocidad;
			break;
		case 5:
			this.puntos = 6+sumaPuntos;
			this.vel = 70-velocidad;
			break;
		case 6:
			this.puntos = 5+sumaPuntos;
			this.vel = 75-velocidad;
			break;
		case 7:
			this.puntos = 4+sumaPuntos;
			this.vel = 80-velocidad;
			break;
	}
		
	}
	
	private void eligeVida(){
		
		switch(this.nivel){
		case 1:
			for (int i=1; i<=7; i++){
				if (this.tipo ==i){
					this.vida = 2+(i*2);
				}
			}
			break;
		case 2:
			for (int i=1; i<=7; i++){
				if (this.tipo ==i){
					this.vida = 16+(i*2);
				}
			}
			break;
		case 3:
			for (int i=1; i<=7; i++){
				if (this.tipo ==i){
					this.vida = 30+(i*2);
				}
			}
			break;
		}
		
	}
	public void run() {
		
		while(this.acabado==false){		//Asi el run se saldra del ciclo
			dispara();
			this.x = this.x - 3;
			if (x<-10){
				this.vida = -1000;
			}
			
			try {
				Thread.sleep(this.vel);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//Cada vez que muera un enemigo, este sumara puntos, pero solo sumará si lo ha matado con disparos xk ninguna nave llegara a tener una vida inferior a -500
		if (this.vida < 0 && this.vida>-500){
			MiPanel.puntuacion = MiPanel.puntuacion + this.puntos;
		}
		this.acabado = true;
	}
	
	public void dispara(){
		
		Random r = new Random();
		int prob = r.nextInt(this.probDisparoMalo);			//Sistema de probabilidad para que aparezcan enemigos
		switch(MiFrame.HalconMilenario.getNivel()){
		case 1:
			if(prob<60){		//Estas son las probabilidades de que los enemigos disparen!!!!! 150 sobre 10000
				disparoMalo aux = new disparoMalo(r.nextInt(5)+1,this.x,this.y);
				mp.DisparosMalos.add(aux);
				Thread t = new Thread(aux);
				t.start();
			}
			break;
		case 2:
			if(prob<70){		
				disparoMalo aux = new disparoMalo(r.nextInt(5)+1,this.x,this.y);
				mp.getDisparosMalos().add(aux);
				Thread t = new Thread(aux);
				t.start();
			}
			break;
		case 3:
			if(prob<80){
				disparoMalo aux = new disparoMalo(r.nextInt(5)+1,this.x,this.y);
				mp.getDisparosMalos().add(aux);
				Thread t = new Thread(aux);
				t.start();
			}
			break;
		case 4:
			if(prob<100){
				disparoMalo aux = new disparoMalo(r.nextInt(5)+1,this.x,this.y);
				mp.getDisparosMalos().add(aux);
				Thread t = new Thread(aux);
				t.start();
			}
			break;
		case 5:
			if(prob<125){
				disparoMalo aux = new disparoMalo(r.nextInt(5)+1,this.x,this.y);
				mp.getDisparosMalos().add(aux);
				Thread t = new Thread(aux);
				t.start();
			}
			break;
		}
	}

	public int getCont() {
		return cont;
	}

	public void setCont(int cont) {
		this.cont = cont;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
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

	public int getVel() {
		return vel;
	}

	public void setVel(int vel) {
		this.vel = vel;
	}
}
