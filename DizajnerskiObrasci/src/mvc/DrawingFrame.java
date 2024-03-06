package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import drawing.PnlDrawing;
import geometry.Shape;
import javax.swing.BoxLayout;

public class DrawingFrame extends JFrame implements Observer {
	
	private DrawingView view = new DrawingView();
	private JPanel contentPane;
	int state = 0;
	public DefaultListModel<String> dlm = new DefaultListModel<String>();
	private JPanel borderColorPanel;
	private JPanel innerColorPanel;
	private JButton btnModify;
	private JToggleButton tglSelect;
	private JButton btnDelete;
	private JButton btnUndo;
	private JButton btnRedo;
	private JButton btnUpZ;
	private JButton btnDownZ;
	private JButton btnBringToFrontZ;
	private JButton btnBringToBackZ;
	private JButton nextLogBtn;

	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	public DrawingView getView() {
		return view;
	}
	private DrawingController controller;
	public DrawingFrame() {
		view.addMouseListener(new MouseAdapter( ) {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClicked(e);
			}
		});
		
		setTitle("Dunja Cvjetinovic IT2 - 2020");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		ButtonGroup group = new ButtonGroup();
		
//____________________________ North ___________________________________
		
		JPanel pnlNorth = new JPanel();
		pnlNorth.setForeground(new Color(0, 0, 0));
		pnlNorth.setBackground(Color.pink);
		contentPane.add(pnlNorth, BorderLayout.NORTH);
		
		JLabel lblDrawing = new JLabel("Drawing");
		lblDrawing.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblDrawing.setBackground(new Color(0, 255, 102));
		lblDrawing.setHorizontalAlignment(SwingConstants.CENTER);
		pnlNorth.add(lblDrawing);
		
//_____________________________ West ___________________________________
		
		JPanel pnlWest = new JPanel();
		pnlWest.setBackground(Color.BLACK);
		contentPane.add(pnlWest, BorderLayout.WEST);
		//----------------
		JRadioButton rdbPoint = new JRadioButton("Point");
		rdbPoint.setForeground(Color.PINK);
		rdbPoint.setFont(new Font("Times New Roman", Font.BOLD, 12));
		rdbPoint.setBackground(new Color(0, 0, 0));
		group.add(rdbPoint);
		rdbPoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				state = 1;
			}
		});
		//----------------
		JRadioButton rdbLine = new JRadioButton("Line");
		rdbLine.setForeground(Color.PINK);
		rdbLine.setFont(new Font("Times New Roman", Font.BOLD, 12));
		rdbLine.setBackground(new Color(0, 0, 0));
		group.add(rdbLine);
		rdbLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				state = 2;
			}
		});
		//----------------
		JRadioButton rdbRectangle = new JRadioButton("Rectangle");
		rdbRectangle.setForeground(Color.PINK);
		rdbRectangle.setFont(new Font("Times New Roman", Font.BOLD, 12));
		rdbRectangle.setBackground(new Color(0, 0, 0));
		group.add(rdbRectangle);
		rdbRectangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				state = 3;
			}
		});
		//----------------
		JRadioButton rdbCircle = new JRadioButton("Circle");
		rdbCircle.setForeground(Color.PINK);
		rdbCircle.setFont(new Font("Times New Roman", Font.BOLD, 12));
		rdbCircle.setBackground(new Color(0, 0, 0));
		group.add(rdbCircle);
		rdbCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				state = 4;
			}
		});
		//----------------
		JRadioButton rdbDonut = new JRadioButton("Donut");
		rdbDonut.setForeground(Color.PINK);
		rdbDonut.setFont(new Font("Times New Roman", Font.BOLD, 12));
		rdbDonut.setBackground(new Color(0, 0, 0));
		group.add(rdbDonut);
		rdbDonut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				state = 5;
			}
		});
		JRadioButton rdbHexagon = new JRadioButton("Hexagon");
		rdbHexagon.setForeground(Color.PINK);
		rdbHexagon.setFont(new Font("Times New Roman", Font.BOLD, 12));
		rdbHexagon.setBackground(new Color(0, 0, 0));
		group.add(rdbHexagon);
		rdbHexagon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				state = 6;
			}
		});
		//----------------
		JScrollPane scrlListShape = new JScrollPane();
		//----------------
		tglSelect = new JToggleButton("Select");
		tglSelect.setFont(new Font("Times New Roman", Font.BOLD, 12));
		tglSelect.setBackground(Color.PINK);
		tglSelect.setEnabled(false);
		tglSelect.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, new Color(0, 0, 0)));
		group.add(tglSelect);
		tglSelect.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				state = 7;
			}
		});
		//----------------
		btnModify = new JButton("Modify");
		btnModify.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnModify.setBackground(Color.PINK);
		btnModify.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, new Color(0, 0, 0)));
		btnModify.setEnabled(false);
		group.add(btnModify);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.updateShape();
			}
		});
		//-----------------
		btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnDelete.setBackground(Color.PINK);
		btnDelete.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, new Color(0, 0, 0)));
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.deleteShape();
			}
		});
		
		btnUndo = new JButton("Undo");
		btnUndo.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnUndo.setBackground(Color.PINK);
		btnUndo.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, new Color(0, 0, 0)));
		btnUndo.setEnabled(false);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		
		btnRedo = new JButton("Redo");
		btnRedo.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnRedo.setBackground(Color.PINK);
		btnRedo.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, new Color(0, 0, 0)));
		btnRedo.setEnabled(false);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		
		btnUpZ = new JButton("Up");
		btnUpZ.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnUpZ.setBackground(Color.PINK);
		btnUpZ.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, new Color(0, 0, 0)));
		btnUpZ.setEnabled(false);
		btnUpZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.upZAxis();
			}
		});
		
		btnDownZ = new JButton("Down");
		btnDownZ.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnDownZ.setBackground(Color.PINK);
		btnDownZ.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, new Color(0, 0, 0)));
		btnDownZ.setEnabled(false);
		btnDownZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.downZAxis();
			}
		});
		
		btnBringToFrontZ = new JButton("Bring to front");
		btnBringToFrontZ.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnBringToFrontZ.setBackground(Color.PINK);
		btnBringToFrontZ.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, new Color(0, 0, 0)));
		btnBringToFrontZ.setEnabled(false);
		btnBringToFrontZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToFrontZAxis();
			}
		});
		
		btnBringToBackZ = new JButton("Bring to back");
		btnBringToBackZ.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnBringToBackZ.setBackground(Color.PINK);
		btnBringToBackZ.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, new Color(0, 0, 0)));
		btnBringToBackZ.setEnabled(false);
		btnBringToBackZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToBackZAxis();
			}
		});
		
		
		pnlWest.setLayout(new BoxLayout(pnlWest, BoxLayout.Y_AXIS));
		pnlWest.add(rdbPoint);
		pnlWest.add(rdbLine);
		pnlWest.add(rdbRectangle);
		pnlWest.add(rdbCircle);
		pnlWest.add(rdbDonut);
		pnlWest.add(rdbHexagon);
		pnlWest.add(scrlListShape);
		pnlWest.add(tglSelect);
		pnlWest.add(btnModify);
		pnlWest.add(btnDelete);
		pnlWest.add(btnUndo);
		pnlWest.add(btnRedo);
		pnlWest.add(btnUpZ);
		pnlWest.add(btnDownZ);
		pnlWest.add(btnBringToFrontZ);
		pnlWest.add(btnBringToBackZ);
		
		contentPane.add(view, BorderLayout.CENTER);
		
//_____________________________ South___________________________________
		
		JScrollPane scrollPane = new JScrollPane();
		JList<String> logList = new JList<String>();
		scrollPane.setViewportView(logList);
		dlm = new DefaultListModel<String>();
		logList.setModel(dlm);
		
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		
// ______________________________East____________________________________
		
		JPanel pnlEast = new JPanel();
		pnlEast.setLayout(new BoxLayout(pnlEast, BoxLayout.Y_AXIS));
		pnlEast.setForeground(new Color(0, 0, 0));
		pnlEast.setBackground(Color.pink);
		
		JLabel borderColorLabel = new JLabel("Border color");
		pnlEast.add(borderColorLabel);
		borderColorPanel = new JPanel();
		borderColorPanel.setMaximumSize(new Dimension(10, 10));
		borderColorPanel.setBackground(Color.BLACK);
		pnlEast.add(borderColorPanel);
		borderColorPanel.addMouseListener(new MouseAdapter( ) {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color color = JColorChooser.showDialog(null, "Choose a border color", borderColorPanel.getBackground());
				if (color != null) {
					borderColorPanel.setBackground(color);
				}
			}
		});
		
		JLabel innerColorLabel = new JLabel("Inner color");
		pnlEast.add(innerColorLabel);
		innerColorPanel = new JPanel();
		innerColorPanel.setMaximumSize(new Dimension(10, 10));
		innerColorPanel.setBackground(Color.WHITE);
		pnlEast.add(innerColorPanel);
		innerColorPanel.addMouseListener(new MouseAdapter( ) {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color color = JColorChooser.showDialog(null, "Choose a inner color", innerColorPanel.getBackground());
				if (color != null) {
					innerColorPanel.setBackground(color);
				}
			}
		});
		
		JButton saveTextBtn = new JButton("Save log");
		saveTextBtn.setBackground(Color.PINK);
		saveTextBtn.setForeground(new Color(0, 0, 0));
		saveTextBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		saveTextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveText();
			}
		});
		
		JButton saveSerializationBtn = new JButton("Save serialized");
		saveSerializationBtn.setBackground(Color.PINK);
		saveSerializationBtn.setForeground(new Color(0, 0, 0));
		saveSerializationBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		saveSerializationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveSerialized();
			}
		});
		
		pnlEast.add(saveTextBtn);
		pnlEast.add(saveSerializationBtn);
		
		JButton readSerializationBtn = new JButton("Read serialized");
		readSerializationBtn.setBackground(Color.PINK);
		readSerializationBtn.setForeground(new Color(0, 0, 0));
		readSerializationBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		readSerializationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.readSerialized();
			}
		});
		
		pnlEast.add(readSerializationBtn);
		
		JButton readLogBtn = new JButton("Read log");
		readLogBtn.setBackground(Color.PINK);
		readLogBtn.setForeground(new Color(0, 0, 0));
		readLogBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		readLogBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.readLog();
			}
		});
		pnlEast.add(readLogBtn);
		
		nextLogBtn = new JButton("Next log");
		nextLogBtn.setBackground(Color.PINK);
		nextLogBtn.setForeground(new Color(0, 0, 0));
		nextLogBtn.setFont(new Font("Times New Roman", Font.BOLD, 12));
		nextLogBtn.setEnabled(false);
		nextLogBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.parseNextLog();
			}
		});
		
		
		pnlEast.add(nextLogBtn);
		
		contentPane.add(pnlEast, BorderLayout.EAST);
		
	}
	public int getState() {
		return state;
	}
	
	public void addLogToList(String log) {
		this.dlm.addElement(log);
	}
	
	public void clearLogToList() {
		this.dlm.clear();
	}
	
	public List<String> getLogs() {
		List<String> helperList = new ArrayList<String>();
		int size = this.dlm.getSize();
		for (int i = 0; i < size; i++ ) {
			helperList.add(dlm.getElementAt(i));
		}
		
		return helperList;
	}
	
	public Color getGlobalBorderColor() {
		return borderColorPanel.getBackground();
	}
	
	public Color getGlobalInnerColor() {
		return innerColorPanel.getBackground();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		List<Shape> shapes = (List<Shape>)arg;
		int numberOfSelectedShapes = 0;
		for (Shape shape : shapes) {
			if (shape.isSelected()) {
				numberOfSelectedShapes++;
			}
		}
		
		if (numberOfSelectedShapes == 1) {
			btnModify.setEnabled(true);
		} else {
			btnModify.setEnabled(false);
		}
		
		if (shapes.size() > 0) {
			tglSelect.setEnabled(true);
		} else {
			tglSelect.setEnabled(false);
		}
		
		if (numberOfSelectedShapes >= 1) {
			btnDelete.setEnabled(true);
		} else {
			btnDelete.setEnabled(false);
		}
		
		if (numberOfSelectedShapes == 1) {
			Shape selectedShape = null;
			for (Shape shape : shapes) {
				if (shape.isSelected()) {
					selectedShape = shape;
					break;
				}
			}
			
			if (shapes.indexOf(selectedShape) < shapes.size() - 1) {
				btnUpZ.setEnabled(true);
				btnBringToFrontZ.setEnabled(true);
			} else {
				btnUpZ.setEnabled(false);
				btnBringToFrontZ.setEnabled(false);
			}
			
			if (shapes.indexOf(selectedShape) > 0) {
				btnDownZ.setEnabled(true);
				btnBringToBackZ.setEnabled(true);
			} else {
				btnDownZ.setEnabled(false);
				btnBringToBackZ.setEnabled(false);
			}
			
		} else {
			btnUpZ.setEnabled(false);
			btnBringToFrontZ.setEnabled(false);
			btnDownZ.setEnabled(false);
			btnBringToBackZ.setEnabled(false);
		}
		
	}
	
	public void enableUndoButton(boolean isEnabled) {
		btnUndo.setEnabled(isEnabled);
	}
	
	public void enableRedoButton(boolean isEnabled) {
		btnRedo.setEnabled(isEnabled);
	}
	
	public void enableNextLogButton(boolean isEnabled) {
		nextLogBtn.setEnabled(isEnabled);
	}
	
}
