package principal;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class MiFrame extends JFrame {

	static MiPanel mp;
	public static int anchura = 1000;	//maximo mi ordenador 1370
	public static int altura = 700;		//maximo mi ordenador 740
	static MiNave HalconMilenario;
	static llama motores;
	
	public MiFrame() {
		
		mp = new MiPanel();
		
		HalconMilenario = new MiNave(mp);
		mp.addMouseMotionListener(HalconMilenario);		//A VER CUIDADO CON ESTO
		mp.addMouseMotionListener(motores);	
		
		mp.addKeyListener(mp);
		mp.setBackground(Color.black);
		mp.setFocusable(true);

		add(mp);
		
		setSize(anchura, altura);
		setDefaultCloseOperation(3);
		setLocationRelativeTo(null);

	}
}
