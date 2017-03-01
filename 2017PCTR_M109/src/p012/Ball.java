package p012;

import java.awt.Image;
import javax.swing.ImageIcon;


//DONE Transform the code to be used safely in a concurrent context.  
public class Ball {
       //DONE  Find an archive named Ball.png 
	private String Ball = "Ball.png"; 

	private double x,y,dx,dy;
	private double v,fi;
	private Image image;

	public Ball() {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(Ball));
		image = ii.getImage();
		x = Billiards.Width/4-16;
		y = Billiards.Height/2-16;
		v = 5;
		fi =  Math.random() * Math.PI * 2;
	}

	public void move() {
		v = v*Math.exp(-v/1000);
		dx = v*Math.cos(fi);
		dy = v*Math.sin(fi);
		if (Math.abs(dx) < 1 && Math.abs(dy) < 1) {
			dx = 0;
			dy = 0;
		}
		x += dx;   
		y += dy;
		//DONE Check postcondition
		assert Math.abs(x  - Board.RIGHTBOARD) >  Math.abs(dx):"Una bola se salio por el lado de la derecha";
		assert Math.abs(y  - Board.BOTTOMBOARD) >  Math.abs(dy):"Una bola se salio por abajo";
		assert Math.abs(x + 32 - Board.LEFTBOARD) >  Math.abs(dx):"Una bola se salio por el lado de la Izquierda";
		assert Math.abs(y + 32 - Board.TOPBOARD) >  Math.abs(dy):"Una bola se salio por arriba";
	}

	public void reflect() {
		if (Math.abs(x + 32 - Board.RIGHTBOARD) <  Math.abs(dx)) {
			fi = Math.PI - fi;
		}
		if (Math.abs(y + 32 - Board.BOTTOMBOARD) <  Math.abs(dy)) {
			fi = - fi;
		}
		if (Math.abs(x - Board.LEFTBOARD) <  Math.abs(dx)) {
			fi = Math.PI - fi;
		}
		if (Math.abs(y - Board.TOPBOARD) <  Math.abs(dy)) {
			fi = - fi;
		}
		//DONE Check postcondition	
		assert Math.abs(x  - Board.RIGHTBOARD) >  Math.abs(dx):"La bola se ha salido por el lado de la derecha";
		assert Math.abs(y  - Board.BOTTOMBOARD) >  Math.abs(dy):"La bola se ha salido por abajo";
		assert Math.abs(x + 32 - Board.LEFTBOARD) >  Math.abs(dx):"La bola se ha salido por el lado de la izquierda";
		assert Math.abs(y + 32 - Board.TOPBOARD) >  Math.abs(dy):"La bola se ha salido por arriba";
	}

	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public double getFi() {
		return fi;
	}

	public double getdr() {
		return Math.sqrt(dx*dx+dy*dy);
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Image getImage() {
		return image;
	}

}

