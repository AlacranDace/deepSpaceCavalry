package principal;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class ProgramaPrincipal {
 
	public static void main(String[] args) {
		
		MiFrame mf = new MiFrame();
		mf.setVisible(true);
		mf.setResizable(false);
		
		/****************Para hacer desaparecer el cursor******************************/
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		// Set the blank cursor to the JFrame.
		mf.getContentPane().setCursor(blankCursor);

	}

}
