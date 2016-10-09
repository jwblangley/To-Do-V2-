package jwblangley.todov2.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import jwblangley.todov2.animations.ExpandFrame;
import jwblangley.todov2.animations.GrowTask;
import jwblangley.todov2.animations.Horizontal;
import jwblangley.todov2.animations.SlideDown;
import jwblangley.todov2.animations.SlotUp;
import jwblangley.todov2.animations.SwapTasks;
import jwblangley.todov2.display.task.Task;

public class Display extends JFrame implements ActionListener {

	public static Display display;

	public final static int screenWidth = (int) (Toolkit.getDefaultToolkit().getScreenSize().width);
	public final int screenHeight = (int) (Toolkit.getDefaultToolkit().getScreenSize().height);
	public static final int WIDTH = (int) (screenWidth / 7);
	public Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
	public int taskBarSize = scnMax.bottom;
	public final int HEIGHT = screenHeight - taskBarSize;
	public static String title;
	public static ArrayList<Task> TASKS = new ArrayList<Task>();
	public JButton pinButton, addButton;
	public JTextField titleField;
	public static final Font titleFont = new Font("Calibri", Font.BOLD, 30);
	public static final Font normalFont = new Font("Calibri", Font.BOLD, 18);
	private JFrame newFrame;
	private JTextArea newArea;
	private JButton saveButton;
	private JPanel totalGUI;

	public static void main(String[] args) {
		File listFile = new File("List.txt");
		if (!listFile.exists()) {
			try {
				listFile.createNewFile();
				FileWriter fw = new FileWriter(listFile);
				PrintWriter pw = new PrintWriter(fw);
				pw.println("To Do");
				pw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileReader fr = new FileReader(listFile);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			br.close();
			fr.close();
			if (line == null) {
				FileWriter fw = new FileWriter(listFile);
				PrintWriter pw = new PrintWriter(fw);
				pw.println("To Do");
				pw.close();
				fw.close();
				line = "To Do";
			}
			title = line;
		} catch (IOException e) {
			e.printStackTrace();
		}

		display = new Display(title);

	}

	public Display(String name) {
		super(name);
		setVisible(true);
		setSize(WIDTH, HEIGHT);
		setLocation(screenWidth - WIDTH, 0);
		setResizable(false);
		setDefaultLookAndFeelDecorated(true);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);

		totalGUI = new JPanel();
		totalGUI.setSize(WIDTH, HEIGHT);
		totalGUI.setLayout(null);
		totalGUI.setBackground(Color.WHITE);
		add(totalGUI);

		pinButton = new JButton();
		pinButton.setIcon(new ImageIcon("images/Pin.png"));
		pinButton.setSize(25, 25);
		pinButton.setBackground(Color.LIGHT_GRAY);
		pinButton.setLocation(2, 2);
		pinButton.addActionListener(this);
		totalGUI.add(pinButton);

		addButton = new JButton();
		addButton.setIcon(new ImageIcon("images/Add.png"));
		addButton.setSize(25, 25);
		addButton.setBackground(Color.WHITE);
		addButton.setBorder(BorderFactory.createEmptyBorder());
		addButton.setLocation(2, 30);
		addButton.addActionListener(this);
		totalGUI.add(addButton);

		titleField = new JTextField(title);
		titleField.setFont(titleFont);
		titleField.setSize(WIDTH, 25);
		titleField.setLocation(0, 20);
		titleField.setHorizontalAlignment(JLabel.CENTER);
		titleField.setBorder(BorderFactory.createEmptyBorder());
		titleField.addActionListener(this);
		titleField.setOpaque(false);
		totalGUI.add(titleField);

		try {
			FileReader fr = new FileReader(new File("List.txt"));
			BufferedReader br = new BufferedReader(fr);
			String line;
			br.readLine(); // Read off the title
			int comulativeDepth = 75;
			while ((line = br.readLine()) != null) {
				Task tempTask = new Task(line, WIDTH, HEIGHT / 15);
				tempTask.setLocation(0, comulativeDepth);
				tempTask.addActionListener(this);
				comulativeDepth += tempTask.getHeight();
				totalGUI.add(tempTask);
				TASKS.add(tempTask);
			}
			applyColours();
		} catch (IOException e) {

		}

		repaint();

	}

	public static boolean onTop = true;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == titleField) {
			title = titleField.getText();
			setTitle(title);
		} else if (e.getSource() == pinButton) {
			setAlwaysOnTop(!isAlwaysOnTop());
			onTop = !onTop;
			if (isAlwaysOnTop()) {
				pinButton.setBackground(Color.LIGHT_GRAY);
			} else {
				pinButton.setBackground(Color.WHITE);
			}

		} else if (e.getSource() == addButton) {
			newFrame = new JFrame("New " + title);
			newFrame.setResizable(false);
			newFrame.setDefaultLookAndFeelDecorated(true);
			newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			newFrame.setLayout(null);
			newFrame.setVisible(true);
			newFrame.setSize(0, 0);
			ExpandFrame ef = new ExpandFrame(newFrame, 1, 2, 125, 125, screenWidth - WIDTH - 125, addButton.getLocation().y);
			newFrame.setAlwaysOnTop(isAlwaysOnTop());
			newFrame.getContentPane().setBackground(Color.WHITE);

			newArea = new JTextArea();
			newArea.setSize(125, 69);
			newArea.setLocation(0, 0);
			newArea.setEditable(true);
			newArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doNothing");
			newArea.setLineWrap(true);
			newArea.setWrapStyleWord(true);
			newFrame.getContentPane().add(newArea);

			saveButton = new JButton("Save");
			saveButton.setSize(115, 20);
			saveButton.setBackground(Color.WHITE);
			saveButton.setBorder(BorderFactory.createEmptyBorder());
			saveButton.setLocation(5, 70);
			saveButton.addActionListener(this);
			newFrame.getContentPane().add(saveButton);
		} else if (e.getSource() == saveButton) {
			// Create a new Task
			Task newTask = new Task(newArea.getText(), WIDTH, HEIGHT / 15);
			for (Task tempTask : TASKS) {
				SlideDown sd = new SlideDown(tempTask, 1, 1, tempTask.getLocation().y + (HEIGHT / 15));
			}

			newTask.setLocation(0, 75);
			TASKS.add(0, newTask);
			newTask.addActionListener(this);
			GrowTask gt = new GrowTask(newTask, 5, 0, newTask.getLocation().x, newTask.getLocation().y, WIDTH, HEIGHT / 15);
			newTask.setVisible(true);
			totalGUI.add(newTask);
			newFrame.dispose();
			applyColours();
		} else {
			ClickFrame cf = new ClickFrame(((Task) (e.getSource())).NAME, ((Task) (e.getSource())), 125, 125, screenWidth - WIDTH - 125, ((Task) (e.getSource())).getLocation().y);
		}
		applyColours();
		updateFile();
	}

	public static void completeTask(Task taskClicked) {
		Horizontal h = new Horizontal(taskClicked, true, 1, 2, WIDTH);
		boolean found = false;
		for (int i = 0; i < TASKS.size(); i++) {
			Task tempTask = TASKS.get(i);
			if (taskClicked == tempTask) {
				found = true;
				continue;
			}
			if (found) {
				SlotUp su = new SlotUp(tempTask, 1, 1, TASKS.get(i - 1).getLocation().y);
			}
		}
		TASKS.remove(taskClicked);
		applyColours();
		updateFile();
	}

	public static void swap(Task task1, Task task2) {
		Collections.swap(TASKS, TASKS.indexOf(task1), TASKS.indexOf(task2));
		SwapTasks st = new SwapTasks(task1, task2, 1, 5);
		applyColours();
		updateFile();

	}

	public static void updateFile() {
		try {
			FileWriter fw = new FileWriter(new File("List.txt"));
			PrintWriter pw = new PrintWriter(fw);
			pw.println(title);
			for (Task tempTask : TASKS) {
				pw.println(tempTask.NAME);
			}
			pw.close();
			fw.close();
		} catch (IOException e) {

		}
	}

	public static void applyColours() {
		ArrayList<Color> colors = new ArrayList<Color>();
		for (int i = 0; i < TASKS.size(); i++) {
			colors.add(new Color(255 - (255 * i / TASKS.size()), 255 * i / TASKS.size(), 255 - (int) (2 * Math.abs(127 - (255 * i / TASKS.size())))));
			TASKS.get(i).setBackground(colors.get(i));
		}
	}
	// public static void add
}
