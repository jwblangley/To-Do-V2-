package jwblangley.todov2.animations;

import java.util.Timer;
import java.util.TimerTask;

import jwblangley.todov2.display.task.Task;

public class Horizontal extends Timer {
	public Timer timer;
	public Task task;
	public boolean direction;
	public int speed, acceleration, endLoc;

	public Horizontal(Task task, boolean direction, int speed, int acceleration, int endLoc) {
		this.task = task;
		this.direction = direction;
		this.speed = speed;
		this.acceleration = acceleration;
		this.endLoc = endLoc;
		timer = new Timer();
		timer.schedule(new AHTask(), 0, 10);
	}

	public class AHTask extends TimerTask {
		public void run() {
			int currentX = task.getLocation().x;
			currentX += speed;
			task.setLocation(currentX, task.getLocation().y);
			speed += acceleration;
			if (direction) {
				if (currentX >= endLoc) {
					task.setLocation(endLoc, task.getLocation().y);
					timer.cancel();
				}
			} else {
				if (currentX <= endLoc) {
					task.setLocation(endLoc, task.getLocation().y);
					timer.cancel();
				}
			}

		}
	}
}
