package chap22_Command.ex22_3;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import chap22_Command.ex22_1.command.Command;
import chap22_Command.ex22_1.command.MacroCommand;
import chap22_Command.ex22_1.drawer.ColorCommand;
import chap22_Command.ex22_1.drawer.DrawCanvas;
import chap22_Command.ex22_1.drawer.DrawCommand;

public class Main extends JFrame implements ActionListener {
	private MacroCommand	_history = new MacroCommand();
	private DrawCanvas		_canvas = new DrawCanvas(_history, 400, 400);
	private JButton		_clearButton	= new JButton("clear");
	private JButton		_redButton		= new JButton("red");
	private JButton		_blackButton	= new JButton("black");
	private JButton		_undoButton		= new JButton("undo");


	public Main(String title) {
		super(title);

		// Listener登録
		_canvas.addMouseMotionListener( new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Command command = new DrawCommand(_canvas, e.getPoint());
				_history.append(command);

				command.execute();
			}
		});


		_clearButton.addActionListener(this);
		_redButton.addActionListener(this);
		_blackButton.addActionListener(this);
		_undoButton.addActionListener(this);

		// レイアウト設定
		Box buttonBox = new Box(BoxLayout.X_AXIS);
		buttonBox.add(_clearButton);
		buttonBox.add(_redButton);
		buttonBox.add(_blackButton);
		buttonBox.add(_undoButton);

		Box mainBox = new Box(BoxLayout.Y_AXIS);
		mainBox.add(buttonBox);
		mainBox.add(_canvas);

		Container container = super.getContentPane();
		container.add(mainBox);

		// JFrame設定
		super.pack();
		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	// ActionListener
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == _clearButton) {
			_history.clear();

			_canvas.init();
			_canvas.repaint();
		} else if (e.getSource() == _redButton) {
			Color red = Color.red;

			Command command = new ColorCommand(_canvas, red);
			_history.append(command);

			command.execute();
		} else if (e.getSource() == _blackButton) {
			Color black = Color.black;

			Command command = new ColorCommand(_canvas, black);
			_history.append(command);

			command.execute();
		} else if (e.getSource() == _undoButton) {
			_history.undo();

			_canvas.init();
			_canvas.repaint();
		}

	}


	public static void main(String[] args) {
		new Main("Command Pattern Sample");
	}

}
