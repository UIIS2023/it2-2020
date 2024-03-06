package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import java.awt.Font;
import geometry.Shape;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;

public class FrmDrawing extends JFrame {

	private JPanel contentPane;
	int state = 0;
	public DefaultListModel<Shape> dlm = new DefaultListModel<Shape>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmDrawing frame = new FrmDrawing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmDrawing() {
		setTitle("Dunja Cvjetinovic IT2 - 2020");
		PnlDrawing pnlDrawing = new PnlDrawing(this); //ovime povezujemo framework i panel (deklaracija framework);
		pnlDrawing.setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
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
		//----------------
		JScrollPane scrlListShape = new JScrollPane();
		//----------------
		JToggleButton tglSelect = new JToggleButton("Select");
		tglSelect.setFont(new Font("Times New Roman", Font.BOLD, 12));
		tglSelect.setBackground(Color.PINK);
		tglSelect.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, new Color(0, 0, 0)));
		group.add(tglSelect);
		tglSelect.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				state = 6;
			}
		});
		//----------------
		JToggleButton tglModify = new JToggleButton("Modify");
		tglModify.setFont(new Font("Times New Roman", Font.BOLD, 12));
		tglModify.setBackground(Color.PINK);
		tglModify.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, new Color(0, 0, 0)));
		group.add(tglModify);
		tglModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				pnlDrawing.modifyShape();
			}
		});
		//-----------------
		JToggleButton tglDelete = new JToggleButton("Delete");
		tglDelete.setFont(new Font("Times New Roman", Font.BOLD, 12));
		tglDelete.setBackground(Color.PINK);
		tglDelete.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, new Color(0, 0, 0)));
		group.add(tglDelete);
		tglDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = 0;
				pnlDrawing.delete();
			}
		});
		pnlWest.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlWest.add(rdbPoint);
		pnlWest.add(rdbLine);
		pnlWest.add(rdbRectangle);
		pnlWest.add(rdbCircle);
		pnlWest.add(rdbDonut);
		pnlWest.add(scrlListShape);
		pnlWest.add(tglSelect);
		pnlWest.add(tglModify);
		pnlWest.add(tglDelete);
		
		pnlDrawing.setSize(new Dimension(20,40));
		pnlDrawing.setPreferredSize(new Dimension(200,400));
		contentPane.add(pnlDrawing);
	}

//______________________ Getters and Setters ______________________________
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public DefaultListModel<Shape> getDlm() {
		return dlm;
	}

	public void setDlm(DefaultListModel<Shape> dlm) {
		this.dlm = dlm;
	}

}
