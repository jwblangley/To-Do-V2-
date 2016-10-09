package jwblangley.todov2.animations;

import java.util.Timer;
import java.util.TimerTask;

import jwblangley.todov2.display.task.Task;

public class GrowTask extends Timer {
	public Timer timer;
	public Task task;
	public int speed, acceleration, endX, endY, endWidth, endHeight, incrementX;

	public GrowTask(Task task, int speed, int acceleration, int endX, int endY, int endWidth, int endHeight) {
		this.task = task;
		this.speed = speed;
		this.acceleration = acceleration;
		this.endX = endX;
		this.endY = endY;
		this.endWidth = endWidth;
		this.endHeight = endHeight;
		this.incrementX = endWidth / endHeight;
		timer = new Timer();
		timer.schedule(new GTTask(), 0, 10);
		this.task.setSize(Math.round(endWidth / 25), Math.round(endHeight / 25));
	}

	public class GTTask extends TimerTask {
		public void run() {
			int currentWidth = task.getSize().width;
			int currentHeight = task.getSize().height;
			currentWidth += speed * incrementX;
			currentHeight += speed;
			task.setSize(currentWidth, currentHeight);
			speed += acceleration;
			if (currentHeight >= endHeight) {
				task.setLocation(endX, endY);
				task.setSize(endWidth, endHeight);
				timer.cancel();
			}

		}
	}
}