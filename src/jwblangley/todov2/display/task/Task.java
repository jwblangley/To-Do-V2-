package jwblangley.todov2.display.task;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import jwblangley.todov2.display.Display;

public class Task extends JButton {
	public String NAME;

	public Task(String name, int width, int height) {
		super(name);
		this.NAME = name;
		setSize(width, height);
		setFont(Display.normalFont);
		setBackground(Color.orange);
		setBorder(BorderFactory.createEmptyBorder());
		setFocusPainted(false);
	}
}
