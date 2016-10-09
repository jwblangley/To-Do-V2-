package jwblangley.todov2.animations;

import java.util.Timer;
import java.util.TimerTask;

import jwblangley.todov2.display.ClickFrame;
import jwblangley.todov2.display.task.Task;

public class SwapTasks extends Timer {
	public Timer timer;
	public Task task1, task2;
	public int speed, acceleration, end1, end2;

	public SwapTasks(Task task1, Task task2, int speed, int acceleration) {
		this.task1 = task1;
		this.task2 = task2;
		this.speed = speed;
		this.acceleration = acceleration;
		this.end1 = task2.getLocation().y;
		this.end2 = task1.getLocation().y;
		timer = new Timer();
		timer.schedule(new AHTask(), 0, 10);
	}

	public class AHTask extends TimerTask {
		public void run() {
			int task1Y, task2Y;
			if (task1.getLocation().y > task2.getLocation().y) {
				task1Y = task1.getLocation().y;
				task2Y = task2.getLocation().y;
			} else {
				task1Y = task2.getLocation().y;
				task2Y = task1.getLocation().y;
			}
			task1Y -= speed;
			task2Y += speed;
			task1.setLocation(task1.getLocation().x, task1Y);
			task2.setLocation(task2.getLocation().x, task2Y);
			speed += acceleration;

			if (task1Y <= end1 || task2Y >= end2) {
				task1.setLocation(task1.getLocation().x, end1);
				task2.setLocation(task2.getLocation().x, end2);
				timer.cancel();
			}
		}
	}

}
