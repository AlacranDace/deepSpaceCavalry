package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;


import javax.swing.JPanel;

public class MiPanel extends JPanel implements KeyListener {
	/***************TRUCOS (mas en la clase Enemigo)*****************/
	private boolean vidaInfinita = 	/*Normal:false*/ 	false; 	//Para conseguir vida infinita
	private boolean diparaSeguido = /*Normal:false*/	false;	//cambiar a true para conseguir disparar MANTENIENDO la barra espaciadora
	private int probEnem=			/*Normal: 25000*/	25000;	//MINIMO 300!!!!!cuanto mas pequeño mayor probabilidad de que nazca un enemigo
	private int numEstrellas =		/*Normal:79*/ 		79;		//Pon aqui el numero de estrellas de fondo que quiere hacer
	
	/****************************************************************/
	
	static int puntuacion = 0;	//Para cambiar el nivel de la nave se hace desde aquí ahora
	static int vidaHalcon = 100;
	static int vidas = 3;
	
	
	private boolean Bcont =true;		//APAÑO para que las estrellas solo se creen una vez, NO USAR PARA NADA MAS
	private ArrayList<estrella> estrellas = new ArrayList<estrella>();

	private int cuentaEnemigos=0;	//Para que cada enemigo tenga in ident unico
	
	private parpadeo parp;
	
	Connection conexion;
	BaseDeDatos dtb;
	private int pantallas = 0;
	
	Rectangle Halcon;
	
	
	private Image imagen;
	private ArrayList<Image> HalconEvolucion = new ArrayList<Image>();
	private ArrayList<Image> LlamaMotor = new ArrayList<Image>();
	private ArrayList<Image> ImgDisparo = new ArrayList<Image>();
	private ArrayList<Image> ImgMisil = new ArrayList<Image>();
	private ArrayList<Image> enem1 = new ArrayList<Image>();
	private ArrayList<Image> enem2 = new ArrayList<Image>();
	private ArrayList<Image> enem3 = new ArrayList<Image>();
	private ArrayList<Image> dispMal = new ArrayList<Image>();
	private ArrayList<Image> explosion = new ArrayList<Image>();
	
	static private ArrayList<disparo> Disparos = new ArrayList<disparo>();
	static ArrayList<disparoMalo> DisparosMalos = new ArrayList<disparoMalo>();
	static private ArrayList<enemigo> Enemigos = new ArrayList<enemigo>();
	static private ArrayList<explosion> Explosiones = new ArrayList<explosion>();
	
	
	
	public MiPanel() {
		
		this.parp = new parpadeo(1000);
		Thread t = new Thread(this.parp);
		t.start();
		
		MiFrame.motores = new llama(MiFrame.mp);
		Thread th = new Thread(MiFrame.motores);
		th.start();
		
		dtb = new BaseDeDatos();
		conexion = dtb.Conectar();
		dtb.cargaPuntuaciones();
		dtb.elige10();
		/*PARA RESETEAR LA TABLA DE PUNTUACIONES, para mas seguridad 
		descuidos hay que descomentar el metodo en la propia clase*/
		//dtb.ResetDTB();	
		dtb.Desconecta();
		
		Toolkit to = Toolkit.getDefaultToolkit();
		
		for (int i = 1; i <= 5; i++) {
			imagen = to.getImage(getClass().getResource("/images/nave0" + i + ".png"));
			HalconEvolucion.add(imagen);
		}
		for (int i = 1; i <= 4; i++) {
			imagen = to.getImage(getClass().getResource("/images/llama" + i + ".png"));
			LlamaMotor.add(imagen);
		}
		for (int i = 1; i <= 4; i++) {
			imagen = to.getImage(getClass().getResource("/images/disparo0" + i + ".png"));
			ImgDisparo.add(imagen);
		}
		for (int i = 1; i <= 4; i++) {
			imagen = to.getImage(getClass().getResource("/images/misil" + i + ".png"));
			ImgMisil.add(imagen);
		}
		for (int i = 1; i <= 7; i++) {
			imagen = to.getImage(getClass().getResource("/images/enemigo1-" + i + ".png"));
			enem1.add(imagen);
		}
		for (int i = 1; i <= 7; i++) {
			imagen = to.getImage(getClass().getResource("/images/enemigo2-" + i + ".png"));
			enem2.add(imagen);
		}
		for (int i = 1; i <= 7; i++) {
			imagen = to.getImage(getClass().getResource("/images/enemigo3-" + i + ".png"));
			enem3.add(imagen);
		}
		for (int i = 1; i <= 5; i++) {
			imagen = to.getImage(getClass().getResource("/images/laser" + i + ".png"));
			dispMal.add(imagen);
		}
		for (int i = 1; i <= 7; i++) {
			imagen = to.getImage(getClass().getResource("/images/explosion0" + i + ".png"));
			explosion.add(imagen);
		}
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		PintaEstrellas(g);
		
		if (this.pantallas ==0){
			PintaTitulo(g);
		}
		if (this.pantallas == 1){
			PintaPuntuacion(g);	
		}
		CompruebaVida();
		PintaInfo(g);
		PintaNave(g);
		PintaDisparos(g);
		PintaEnemigos(g);
		PintaDispMal(g);
		PintaExplosiones(g);
		
		//System.out.println("Vida: "+vidaHalcon+" Puntos: "+puntuacion);
		
		
	}
	public void CompruebaVida(){			//EN ESTE METODO ESTA LA DINAMICA DEL JUEGO!!!!!
		if(this.pantallas >1){
			if(this.vidaHalcon<=0){
				this.vidas = this.vidas -1;
				this.vidaHalcon = 100;
			}
			if(this.vidas<=0){
				/*PARA HACER UN RESET añadiendo una puntuacion a la base de datos*/
				
				for(int i=0;i<this.Enemigos.size();i++){
					this.Enemigos.get(i).setAcabado(true);
				}
				for(int i=0;i<this.Explosiones.size();i++){
					this.Explosiones.get(i).setAcabado(true);
				}
				for(int i=0;i<this.Disparos.size();i++){
					this.Disparos.get(i).setAcabado(true);
				}
				for(int i=0;i<this.DisparosMalos.size();i++){
					this.DisparosMalos.get(i).setAcabado(true);
				}
				
				this.pantallas=0;
				
				dtb.ResetArrays();			/*Reseteamos los arrays que usamos para pintar*/
				puntuacion aux = new puntuacion(this.puntuacion);
				conexion = dtb.Conectar();
				dtb.altaPuntuacion(aux);
				dtb.cargaPuntuaciones();
				dtb.elige10();
				dtb.Desconecta();
				
				this.vidaHalcon=100;
				this.vidas = 3;
				this.puntuacion = 0;
			}
		}
	}
	
	public void PintaInfo(Graphics g){
		Font fuente = new Font("Arial", 1, 30);
		g.setFont(fuente);
		g.setColor(Color.yellow);
		if(this.pantallas >1){
			g.drawString(Integer.toString(this.puntuacion),20,40);
		
			g.setColor(Color.red);
			g.fillRect(350, 18, 300, 23);
		
			g.setColor(Color.blue);
			g.fillRect(350, 18, vidaHalcon*3, 23);
			
			for(int i=0; i<this.vidas;i++){
				
				Toolkit to1 = Toolkit.getDefaultToolkit();
				Image vida = to1.getImage(getClass().getResource("/images/vidas.png"));
				g.drawImage(vida,850+(i*50), 20,this);
			}
		}
	}
	public void PintaExplosiones(Graphics g){
		for(int i=0; i<this.Explosiones.size();i++){
			explosion aux = this.Explosiones.get(i);
			if(aux.isAcabado()==false){
				g.drawImage(explosion.get(aux.getPosicion()),aux.getX(), aux.getY(),this);
			}else{
				this.Explosiones.remove(aux);
			}
		}
	}
	
	private void PintaDispMal(Graphics g) {	
		for(int i=0; i<MiPanel.DisparosMalos.size();i++){
			disparoMalo aux = DisparosMalos.get(i);
			
			if (aux.isAcabado()==true){	//Con esto nos aseguraremos de no pintarlo y con el else de eliminarlo
				MiPanel.DisparosMalos.remove(aux);
			}else{
				Rectangle rec;
				switch(aux.getNivel()){//TODO mirar desde donde disparan en cada tipo de malo 
				case 1:
					rec = new Rectangle(aux.getX(), aux.getY()+5, 
							dispMal.get(aux.getNivel()-1).getWidth(null),dispMal.get(aux.getNivel()-1).getHeight(null));
					g.drawImage(dispMal.get(aux.getNivel()-1), aux.getX(), aux.getY()+5, this);
					break;
				case 2:
					rec = new Rectangle(aux.getX(), aux.getY()+5, 
							dispMal.get(aux.getNivel()-1).getWidth(null),dispMal.get(aux.getNivel()-1).getHeight(null));
					g.drawImage(dispMal.get(aux.getNivel()-1), aux.getX(), aux.getY()+5, this);
					break;
				case 3:
					rec = new Rectangle(aux.getX(), aux.getY()+5, 
							dispMal.get(aux.getNivel()-1).getWidth(null),dispMal.get(aux.getNivel()-1).getHeight(null));
					g.drawImage(dispMal.get(aux.getNivel()-1), aux.getX(), aux.getY()+5, this);
					break;
				case 4:
					rec = new Rectangle(aux.getX(), aux.getY()+5, 
							dispMal.get(aux.getNivel()-1).getWidth(null),dispMal.get(aux.getNivel()-1).getHeight(null));
					g.drawImage(dispMal.get(aux.getNivel()-1), aux.getX(), aux.getY()+5, this);
					break;
				case 5:
					rec = new Rectangle(aux.getX(), aux.getY()+5, 
							dispMal.get(aux.getNivel()-1).getWidth(null),dispMal.get(aux.getNivel()-1).getHeight(null));
					g.drawImage(dispMal.get(aux.getNivel()-1), aux.getX(), aux.getY()+5, this);
					break;
				default:
					rec = new Rectangle(0,0,0,0);
					break;
				}
				if(rec.intersects(this.Halcon)){
					if(this.vidaInfinita==false){
						this.vidaHalcon = this.vidaHalcon-aux.getDanio();		//Si chocamos con una nave quitamos puntos de vida a nuestra nave
					}
					aux.setAcabado(true);
				}
			}
		}
	}

	public void PintaEnemigos(Graphics g){
		if (this.pantallas==2){
		creaEnemigos();
		}
		for(int i=0; i<this.Enemigos.size();i++){
			enemigo aux = this.Enemigos.get(i);
			if (aux.isAcabado()==true){	//Con esto nos aseguraremos de no pintarlo y con el else de eliminarlo
				aux.setVida(-50);
				Enemigos.remove(aux);
				/*************PARA LAS EXPLOSIONES*******************/
				explosion ex = new explosion(aux.getX(),aux.getY());
				ex.start();
				this.Explosiones.add(ex);
				
				
			}else{
				Rectangle rec;
				switch (aux.getNivel()){
				case 1:
					rec = new Rectangle(aux.getX(), aux.getY(), 
							enem1.get(aux.getTipo()-1).getWidth(null),enem1.get(aux.getTipo()-1).getHeight(null));
					aux.setEspacio(rec);
					g.drawImage(enem1.get(aux.getTipo()-1),aux.getX(), aux.getY(),this);					
					break;
				case 2:
					rec = new Rectangle(aux.getX(), aux.getY(), 
							enem2.get(aux.getTipo()-1).getWidth(null),enem2.get(aux.getTipo()-1).getHeight(null));
					aux.setEspacio(rec);
					g.drawImage(enem2.get(aux.getTipo()-1),aux.getX(), aux.getY(),this);
					break;
				case 3:
					rec = new Rectangle(aux.getX(), aux.getY(), 
							enem3.get(aux.getTipo()-1).getWidth(null),enem3.get(aux.getTipo()-1).getHeight(null));
					aux.setEspacio(rec);
					g.drawImage(enem3.get(aux.getTipo()-1),aux.getX(), aux.getY(),this);
					break;
				default:
					rec = new Rectangle(0,0,0,0);
					break;
				}
				if(this.Halcon.intersects(rec)){
					if(this.vidaInfinita==false){
						this.vidaHalcon=this.vidaHalcon-5;		//Si chocamos con una nave quitamos 5 puntos de vida a nuestra nave
					}
					aux.setAcabado(true);
				}
			}
		}
	}
	
	public void creaEnemigos(){
		Random r = new Random();
		int prob = r.nextInt(this.probEnem);			//Sistema de probabilidad para que aparezcan enemigos
		switch(MiFrame.HalconMilenario.getNivel()){
		case 1:
			if(prob<100){
				enemigo aux = new enemigo(MiFrame.mp,1,this.cuentaEnemigos);this.cuentaEnemigos++;
				Enemigos.add(aux);
				Thread t = new Thread(aux);
				t.start();
			}
			break;
		case 2:
			if(prob<150){		//Estas son las probabilidades de que los enemigos aparezcan!!!!! 150 sobre 10000
				enemigo aux = new enemigo(MiFrame.mp,2,this.cuentaEnemigos);this.cuentaEnemigos++;
				Enemigos.add(aux);
				Thread t = new Thread(aux);
				t.start();
			}
			break;
		case 3:
			if(prob<200){
				enemigo aux = new enemigo(MiFrame.mp,2,this.cuentaEnemigos);this.cuentaEnemigos++;
				Enemigos.add(aux);
				Thread t = new Thread(aux);
				t.start();
			}
			break;
		case 4:
			if(prob<250){
				enemigo aux = new enemigo(MiFrame.mp,3,this.cuentaEnemigos);this.cuentaEnemigos++;
				Enemigos.add(aux);
				Thread t = new Thread(aux);
				t.start();
			}
			break;
		case 5:
			if(prob<300){
				enemigo aux = new enemigo(MiFrame.mp,3,this.cuentaEnemigos);this.cuentaEnemigos++;
				Enemigos.add(aux);
				Thread t = new Thread(aux);
				t.start();
			}
			break;
		}
	}
	
	public void PintaDisparos(Graphics g) {
		for(int i=0; i<this.Disparos.size();i++){
			disparo aux = this.Disparos.get(i);
			if (aux.isAcabado()==true){	//Con esto nos aseguraremos de no pintarlo y con el else de eliminarlo
				Disparos.remove(aux);
			}else{
				Rectangle rec;
				rec = new Rectangle(aux.getX(), aux.getY(), 
						ImgDisparo.get(0).getWidth(null),ImgDisparo.get(0).getHeight(null));
				aux.setEspacio(rec);
				if(aux.getNivel()!=5){
					g.drawImage(ImgDisparo.get(aux.getNivel()-1),aux.getX(), aux.getY(),this);
				}else{
					g.drawImage(ImgMisil.get(MiFrame.motores.getPosicion()),aux.getX(), aux.getY(),this);
				}
				
				for(int j=0; j<this.Enemigos.size();j++){
					if(aux.getEspacio().intersects(this.Enemigos.get(j).getEspacio())){
						enemigo aux2 = this.Enemigos.get(j);
						aux.setAcabado(true); //Matamos nuestro disparo
						aux2.setVida(aux2.getVida()-aux.getDanio());	//Restamos vida
						if (aux2.getVida()<=0){
							aux2.setAcabado(true);
						}
					}
				}
			}
		}
	}
	
	public void dispara(){
		switch (MiFrame.HalconMilenario.getNivel()){
		case 1:
			disparo aux1 = new disparo(MiFrame.HalconMilenario.getNivel(),MiFrame.HalconMilenario.getX()+16,MiFrame.HalconMilenario.getY()+13);
			aux1.start();
			this.Disparos.add(aux1);
//			Thread t1 = new Thread(aux1);
//			t1.start();
			break;
		case 2:
			disparo aux2 = new disparo(MiFrame.HalconMilenario.getNivel(),MiFrame.HalconMilenario.getX()+16,MiFrame.HalconMilenario.getY()+25);
			aux2.start();
			this.Disparos.add(aux2);
			
			disparo aux3 = new disparo(MiFrame.HalconMilenario.getNivel(),MiFrame.HalconMilenario.getX()+16,MiFrame.HalconMilenario.getY()+36);
			aux3.start();
			this.Disparos.add(aux3);
			
			break;
		case 3 :
			disparo aux4 = new disparo(MiFrame.HalconMilenario.getNivel(),MiFrame.HalconMilenario.getX()+40,MiFrame.HalconMilenario.getY()+10);
			aux4.start();
			this.Disparos.add(aux4);
			
			disparo aux5 = new disparo(MiFrame.HalconMilenario.getNivel(),MiFrame.HalconMilenario.getX()+76,MiFrame.HalconMilenario.getY()+43);
			aux5.start();
			this.Disparos.add(aux5);
			
			disparo aux6 = new disparo(MiFrame.HalconMilenario.getNivel(),MiFrame.HalconMilenario.getX()+40,MiFrame.HalconMilenario.getY()+75);
			aux6.start();
			this.Disparos.add(aux6);
			break;
		case 4 :
			disparo aux7 = new disparo(MiFrame.HalconMilenario.getNivel(),MiFrame.HalconMilenario.getX()+28,MiFrame.HalconMilenario.getY()+17);
			aux7.start();
			this.Disparos.add(aux7);
			
			disparo aux8 = new disparo(MiFrame.HalconMilenario.getNivel(),MiFrame.HalconMilenario.getX()+68,MiFrame.HalconMilenario.getY()+50);
			aux8.start();
			this.Disparos.add(aux8);
			
			disparo aux9 = new disparo(MiFrame.HalconMilenario.getNivel(),MiFrame.HalconMilenario.getX()+28,MiFrame.HalconMilenario.getY()+81);
			aux9.start();
			this.Disparos.add(aux9);
			break;
		case 5 :
			disparo aux10 = new disparo(MiFrame.HalconMilenario.getNivel(),MiFrame.HalconMilenario.getX()+60,MiFrame.HalconMilenario.getY()+21);
			aux10.start();
			this.Disparos.add(aux10);
			
			disparo aux11 = new disparo(MiFrame.HalconMilenario.getNivel(),MiFrame.HalconMilenario.getX()+60,MiFrame.HalconMilenario.getY()+82);
			aux11.start();
			this.Disparos.add(aux11);
			
			disparo aux12 = new disparo(MiFrame.HalconMilenario.getNivel(),MiFrame.HalconMilenario.getX()+60,MiFrame.HalconMilenario.getY()+143);
			aux12.start();
			this.Disparos.add(aux12);
			break;
		}
	}

	public void PintaNave(Graphics g) {
		
		compruebaNivelNave();
		
		Toolkit t = Toolkit.getDefaultToolkit();
		switch(MiFrame.HalconMilenario.getNivel()){
			case 1:
				this.Halcon = new Rectangle(MiFrame.HalconMilenario.getX(), MiFrame.HalconMilenario.getY(), 
						HalconEvolucion.get(0).getWidth(null),HalconEvolucion.get(0).getHeight(null));
				g.drawImage(HalconEvolucion.get(0), MiFrame.HalconMilenario.getX(), MiFrame.HalconMilenario.getY(), this);
				
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-11, MiFrame.motores.getY()+1,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-11, MiFrame.motores.getY()+24,this);
				break;
			case 2:
				this.Halcon = new Rectangle(MiFrame.HalconMilenario.getX(), MiFrame.HalconMilenario.getY(), 
						HalconEvolucion.get(1).getWidth(null),HalconEvolucion.get(1).getHeight(null));
				g.drawImage(HalconEvolucion.get(1), MiFrame.HalconMilenario.getX(), MiFrame.HalconMilenario.getY(), this);
				
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-10, MiFrame.motores.getY()+4,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-10, MiFrame.motores.getY()+14,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-10, MiFrame.motores.getY()+47,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-10, MiFrame.motores.getY()+57,this);
				break;
			case 3:
				this.Halcon = new Rectangle(MiFrame.HalconMilenario.getX(), MiFrame.HalconMilenario.getY(), 
						HalconEvolucion.get(2).getWidth(null),HalconEvolucion.get(2).getHeight(null));
				g.drawImage(HalconEvolucion.get(2), MiFrame.HalconMilenario.getX(), MiFrame.HalconMilenario.getY(), this);
				//Primer motor
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-10, MiFrame.motores.getY()+10,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-10, MiFrame.motores.getY()+19,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-10, MiFrame.motores.getY()+13,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-10, MiFrame.motores.getY()+16,this);
				//Segundo Motor
				int y3 = 57;
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-10, MiFrame.motores.getY()+10+y3,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-10, MiFrame.motores.getY()+19+y3,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-10, MiFrame.motores.getY()+13+y3,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-10, MiFrame.motores.getY()+16+y3,this);
				break;
			case 4:
				this.Halcon = new Rectangle(MiFrame.HalconMilenario.getX(), MiFrame.HalconMilenario.getY(), 
						HalconEvolucion.get(3).getWidth(null),HalconEvolucion.get(3).getHeight(null));
				g.drawImage(HalconEvolucion.get(3), MiFrame.HalconMilenario.getX(), MiFrame.HalconMilenario.getY(), this);
				//Motor principal
				int y4  = 39;
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-2, MiFrame.motores.getY()+9+y4,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-2, MiFrame.motores.getY()+7+y4,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-2, MiFrame.motores.getY()+10+y4,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-2, MiFrame.motores.getY()+19+y4,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-2, MiFrame.motores.getY()+13+y4,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-2, MiFrame.motores.getY()+16+y4,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-2, MiFrame.motores.getY()+6+y4,this);
				//Motor secundario 1
				int y4b  = -2;
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()+3, MiFrame.motores.getY()+9+y4b,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()+3, MiFrame.motores.getY()+7+y4b,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()+3, MiFrame.motores.getY()+11+y4b,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()+1, MiFrame.motores.getY()+9+y4b,this);
				//Motor secundario 2
				int y4c  = 85;
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()+3, MiFrame.motores.getY()+9+y4c,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()+3, MiFrame.motores.getY()+7+y4c,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()+3, MiFrame.motores.getY()+11+y4c,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()+1, MiFrame.motores.getY()+9+y4c,this);
				break;
			case 5:
				this.Halcon = new Rectangle(MiFrame.HalconMilenario.getX(), MiFrame.HalconMilenario.getY(), 
						HalconEvolucion.get(4).getWidth(null),HalconEvolucion.get(4).getHeight(null));
				g.drawImage(HalconEvolucion.get(4), MiFrame.HalconMilenario.getX(), MiFrame.HalconMilenario.getY(), this);
				//Motor1
				int y5 = 64;
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-9, MiFrame.motores.getY()+2+y5,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-9, MiFrame.motores.getY()+5+y5,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-9, MiFrame.motores.getY()+4+y5,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-9, MiFrame.motores.getY()+3+y5,this);
				
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-11, MiFrame.motores.getY()+2+y5,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-11, MiFrame.motores.getY()+5+y5,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-11, MiFrame.motores.getY()+4+y5,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-11, MiFrame.motores.getY()+3+y5,this);
				
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-13, MiFrame.motores.getY()+2+y5,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-13, MiFrame.motores.getY()+5+y5,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-13, MiFrame.motores.getY()+4+y5,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-13, MiFrame.motores.getY()+3+y5,this);
				
				//Motor2
				int y5b = 95;
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-9, MiFrame.motores.getY()+2+y5b,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-9, MiFrame.motores.getY()+5+y5b,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-9, MiFrame.motores.getY()+4+y5b,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-9, MiFrame.motores.getY()+3+y5b,this);
				
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-11, MiFrame.motores.getY()+2+y5b,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-11, MiFrame.motores.getY()+5+y5b,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-11, MiFrame.motores.getY()+4+y5b,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-11, MiFrame.motores.getY()+3+y5b,this);
				
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-13, MiFrame.motores.getY()+2+y5b,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-13, MiFrame.motores.getY()+5+y5b,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-13, MiFrame.motores.getY()+4+y5b,this);
				g.drawImage(LlamaMotor.get(MiFrame.motores.getPosicion()),MiFrame.motores.getX()-13, MiFrame.motores.getY()+3+y5b,this);
				
				break;
		}

	}
	public void compruebaNivelNave(){
		if (this.puntuacion<2000){
			MiFrame.HalconMilenario.setNivel(1);
		}
		if (2000<=this.puntuacion && this.puntuacion<4000){
			MiFrame.HalconMilenario.setNivel(2);
		}
		if (4000<=this.puntuacion && this.puntuacion<6000){
			MiFrame.HalconMilenario.setNivel(3);
		}
		if (6000<=this.puntuacion && this.puntuacion<8000){
			MiFrame.HalconMilenario.setNivel(4);
		}
		if (8000<=this.puntuacion){
			MiFrame.HalconMilenario.setNivel(5);
		}
	}

	private void PintaTitulo(Graphics g){
		Font fuente = new Font("Arial", 1, 100);
		g.setFont(fuente);
		g.setColor(Color.yellow);
		
		g.drawString("DEEP SPACE",180,280);
		g.drawString("CAVARLY",260,400);
		
		if (parp.isParpadeo() == true){
			
			Font fuente2 = new Font("Arial", 1, 35);
			g.setFont(fuente2);
			g.drawString("Pulse la barra espaciadora",280,550);
		}
	}
	private void PintaPuntuacion(Graphics g) {
		Font fuente = new Font("Arial", 1, 35);
		g.setFont(fuente);
		g.setColor(Color.yellow);
		int altura = 45;
		g.drawString("Top Ten:",400,altura);
		for(int i=0;i<dtb.getTopTen().size();i++){
			altura = altura + 55;
			String puntos = Integer.toString(dtb.getTopTen().get(i).getPuntos());
			g.drawString((i+1)+"�: "+puntos,420,altura);	
		}
		altura = altura + 55;
		if (parp.isParpadeo() == true){
			g.drawString("Pulse la barra espaciadora para comenzar el juego",80,altura);
		}
	}
	

	private void PintaEstrellas(Graphics g) {

		if (this.Bcont==true){
			CreaEstrellas();
			this.Bcont = false;
		}
		
		for (int i=0; i<estrellas.size(); i++){
			g.setColor(Color.white);
			estrella aux = estrellas.get(i);
			g.fillOval(aux.getX(), aux.getY(), aux.getGrosor(), aux.getGrosor());
		}
		
	}
	private void CreaEstrellas() {
		for (int i=0; i<this.numEstrellas; i++){
			estrella star = new estrella(MiFrame.mp);
			this.estrellas.add(star);
			//System.out.println("descanso:"+star.getVel()+" x:"+star.getX()+" y:"+star.getY()+" grosor:"+star.getGrosor());
			Thread t = new Thread(star);
			t.start();
		}
	}
	

	
	
	
	public void keyPressed(KeyEvent tecla) {
		if (this.diparaSeguido){
			switch(tecla.getKeyCode()) {
			case 32 : 
				if(this.pantallas<2){
					this.pantallas = pantallas + 1;
				}
				if(this.pantallas >1){dispara();}		//En el titulo no dispara, pero en el top ten si para que vean como se hace

				break;
			default:
				//System.out.println(tecla.getKeyCode());
			}
		}
	}
	public void keyReleased(KeyEvent tecla) {
		
		switch(tecla.getKeyCode()) {
		case 32 : 
			if(this.pantallas<2){
				this.pantallas = pantallas + 1;
			}
			if(this.pantallas >1){dispara();}		//En el titulo no dispara, pero en el top ten si para que vean como se hace

			break;
		default:
			//System.out.println(tecla.getKeyCode());
		}
		
	}
	public void keyTyped(KeyEvent arg0) {
	}

	public ArrayList<disparoMalo> getDisparosMalos() {
		return DisparosMalos;
	}

	public void setDisparosMalos(ArrayList<disparoMalo> disparosMalos) {
		DisparosMalos = disparosMalos;
	}

	
}