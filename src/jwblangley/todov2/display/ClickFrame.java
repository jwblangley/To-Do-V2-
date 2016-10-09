package jwblangley.todov2.display;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import jwblangley.todov2.animations.ExpandFrame;
import jwblangley.todov2.display.task.Task;

public class ClickFrame extends JFrame implements ActionListener {
	public String name;
	public int xLoc, yLoc, width, height;
	public JButton tickButton, upButton, downButton, moreButton, saveButton;
	public JTextArea infoArea;
	public JPanel totalGUI;
	public Task task;
	private JFrame infoFrame;

	public ClickFrame(String name, Task task, int width, int height, int xLoc, int yLoc) {
		super(name);
		this.width = width;
		this.height = height;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.task = task;
		setResizable(false);
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		setSize(0, 0);
		ExpandFrame ef = new ExpandFrame(this, 1, 2, width, height, xLoc, yLoc);
		setAlwaysOnTop(Display.onTop);

		totalGUI = new JPanel();
		totalGUI.setLayout(null);
		totalGUI.setLocation(0, 0);
		totalGUI.setSize(width, height);
		totalGUI.setBackground(Color.WHITE);
		add(totalGUI);

		tickButton = new JButton();
		tickButton.setIcon(new ImageIcon("images/Tick.png"));
		tickButton.setSize(66, 66);
		tickButton.setBackground(Color.WHITE);
		tickButton.setBorder(BorderFactory.createEmptyBorder());
		tickButton.setLocation(12, 2);
		tickButton.addActionListener(this);
		totalGUI.add(tickButton);

		upButton = new JButton();
		upButton.setIcon(new ImageIcon("images/Up.png"));
		upButton.setSize(33, 33);
		upButton.setBackground(Color.WHITE);
		upButton.setBorder(BorderFactory.createEmptyBorder());
		upButton.setLocation(78, 2);
		upButton.addActionListener(this);
		totalGUI.add(upButton);

		downButton = new JButton();
		downButton.setIcon(new ImageIcon("images/Down.png"));
		downButton.setSize(33, 33);
		downButton.setBackground(Color.WHITE);
		downButton.setBorder(BorderFactory.createEmptyBorder());
		downButton.setLocation(78, 35);
		downButton.addActionListener(this);
		totalGUI.add(downButton);

		moreButton = new JButton("More");
		moreButton.setSize(115, 20);
		moreButton.setBackground(Color.WHITE);
		moreButton.setBorder(BorderFactory.createEmptyBorder());
		moreButton.setLocation(5, 70);
		moreButton.addActionListener(this);
		totalGUI.add(moreButton);

		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tickButton) {
			Display.completeTask(task);
			dispose();
		}
		if (e.getSource() == upButton) {
			if (Display.TASKS.indexOf(task) != 0) {
				Display.swap(task, Display.TASKS.get(Display.TASKS.indexOf(task) - 1));
			}
		}
		if (e.getSource() == downButton) {
			if (Display.TASKS.indexOf(task) != Display.TASKS.size() - 1) {
				Display.swap(Display.TASKS.get(Display.TASKS.indexOf(task) + 1), task);
			}
		}
		if (e.getSource() == moreButton) {
			infoFrame = new JFrame(task.NAME);
			infoFrame.setResizable(false);
			infoFrame.setDefaultLookAndFeelDecorated(true);
			infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			infoFrame.setLayout(null);
			infoFrame.setVisible(true);
			infoFrame.setSize(0, 0);
			ExpandFrame ef = new ExpandFrame(infoFrame, 1, 2, width, height, xLoc - width, yLoc);
			infoFrame.setAlwaysOnTop(Display.onTop);
			infoFrame.getContentPane().setBackground(Color.WHITE);

			infoArea = new JTextArea(task.NAME);
			infoArea.setSize(width, 69);
			infoArea.setLocation(0, 0);
			infoArea.setEditable(true);
			infoArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doNothing");
			infoArea.setLineWrap(true);
			infoArea.setWrapStyleWord(true);
			infoFrame.getContentPane().add(infoArea);

			saveButton = new JButton("Save");
			saveButton.setSize(115, 20);
			saveButton.setBackground(Color.WHITE);
			saveButton.setBorder(BorderFactory.createEmptyBorder());
			saveButton.setLocation(5, 70);
			saveButton.addActionListener(this);
			infoFrame.getContentPane().add(saveButton);

		}
		if (e.getSource() == saveButton) {
			task.NAME = infoArea.getText();
			task.setText(task.NAME);
			infoFrame.setTitle(task.NAME);
			setTitle(task.NAME);
			Display.updateFile();
		}

	}

}
