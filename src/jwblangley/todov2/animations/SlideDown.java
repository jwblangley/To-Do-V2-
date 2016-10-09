package jwblangley.todov2.animations;

import java.util.Timer;
import java.util.TimerTask;

import jwblangley.todov2.display.task.Task;

public class SlideDown extends Timer {
	public Timer timer;
	public Task task;
	public int speed, acceleration, endLoc;

	public SlideDown(Task task, int speed, int acceleration, int endLoc) {
		this.task = task;
		this.speed = speed;
		this.acceleration = acceleration;
		this.endLoc = endLoc;
		timer = new Timer();
		timer.schedule(new SDTask(), 0, 10);
	}

	public class SDTask extends TimerTask {
		public void run() {
			int currentY = task.getLocation().y;
			currentY += speed;
			task.setLocation(task.getLocation().x, currentY);
			speed += acceleration;
			if (currentY <= endLoc) {
				task.setLocation(task.getLocation().x, endLoc);
				timer.cancel();
			}

		}
	}
}