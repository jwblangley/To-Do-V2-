package jwblangley.todov2.animations;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class ExpandFrame extends Timer  {
	public Timer timer;
	public JFrame jf;
	public int speed, acceleration, endLocX, endLocY, endWidth, endHeight;

	public ExpandFrame(JFrame jf, int speed, int acceleration, int endWidth, int endHeight, int endLocX, int endLocY) {
		this.jf = jf;
		this.speed = speed;
		this.acceleration = acceleration;
		this.endLocX = endLocX;
		this.endLocY = endLocY;
		this.endWidth = endWidth;
		this.endHeight = endHeight;
		timer = new Timer();
		timer.schedule(new AHTask(), 0, 10);
	}

	public class AHTask extends TimerTask {
		public void run() {
			int currentWidth = jf.getWidth();
			int currentHeight = jf.getHeight();
			int currentX = jf.getLocation().x;
			int currentY = jf.getLocation().y;
			currentWidth += speed;
			currentHeight += speed;
			currentX = endLocX + endWidth - currentWidth;
			currentY = endLocY + endHeight - currentHeight;
			jf.setSize(currentWidth, currentHeight);
			jf.setLocation(currentX, currentY);
			speed += acceleration;
			if (currentWidth >= endWidth) {
				jf.setSize(endWidth, endHeight);
				jf.setLocation(endLocX, endLocY);
				timer.cancel();
			}
			
			

		}
	}

}
