package p012;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import p012.Ball;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("serial")
public class Billiards extends JFrame {

	public static int Width = 800;
	public static int Height = 600;

	private JButton b_start, b_stop;

	private Board board;

	private ExecutorService pool;
	private boolean parada;

	// DONE update with number of group label. See practice statement.
	private final int N_BALL = 9 + 3;
	private Ball[] balls;

	public Billiards() {

		board = new Board();
		board.setForeground(new Color(0, 128, 0));
		board.setBackground(new Color(0, 128, 0));

		initBalls();

		b_start = new JButton("Empezar");
		b_start.addActionListener(new StartListener());
		b_stop = new JButton("Parar");
		b_stop.addActionListener(new StopListener());

		JPanel p_Botton = new JPanel();
		p_Botton.setLayout(new FlowLayout());
		p_Botton.add(b_start);
		p_Botton.add(b_stop);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(board, BorderLayout.CENTER);
		getContentPane().add(p_Botton, BorderLayout.PAGE_END);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setTitle("Práctica programación concurrente objetos móviles independientes");
		setResizable(false);
		setVisible(true);
	}

	private void initBalls() {
		// DONE init balls
		// Instanciamos un array del tama�o N_BALL
		balls = new Ball[N_BALL];
		// Creamos una nueva bola por cada elemento de balls
		for (int i = 0; i < balls.length; i++) {
			balls[i] = new Ball();
		}
	}

	private class StartListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// DONE Code is executed when stop button is pushed
			parada = true;
			class Hilos implements Runnable {
				Ball ball;

				private Hilos(Ball ball) {
					this.ball = ball;
				}

				public void run() {
					while (parada) {
						// metodo que evita que la bola se salga por los limites
						ball.reflect();
						// Metodo que mueve la bola
						ball.move();
						try {
							TimeUnit.MILLISECONDS.sleep(15);
						} catch (InterruptedException e) {
							//
							e.printStackTrace();
							parada = false;
						}
					}
				}
			}
			// Ahora debemso implementar la actualizacion de los hilos
			class Actualizacion implements Runnable {
				public void run() {
					while (parada) {
						// Actualizar la bola
						board.repaint();
						try {
							TimeUnit.MILLISECONDS.sleep(15);
						} catch (InterruptedException e) {
							e.printStackTrace();
							parada = false;
						}
					}
				}
			}
			// Instanciamos un FixedThreadPool del tama?o de nuestro array
			// mas 1 para el main.
			pool = Executors.newFixedThreadPool(N_BALL + 1);

			// Pintamos las bolas en el Dashboard
			board.setBalls(balls);

			// Ejecutamos todos lso hilos correspondientes a las bolas
			for (int i = 0; i < N_BALL; i++) {
				pool.execute(new Hilos(balls[i]));
			}
			// ejecutmaos el hilo que se encargara de actualizar
			pool.execute(new Actualizacion());
		}
	}

	private class StopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// DONE Auto-generated method stub
			pool.shutdown();
			parada = false;
		}
	}

	public static void main(String[] args) {
		new Billiards();
	}
}