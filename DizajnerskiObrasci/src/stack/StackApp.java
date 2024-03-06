package stack;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import drawingApp.Circle;

import javax.swing.JSplitPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StackApp extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<Circle> dlm= new DefaultListModel<>();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StackApp frame = new StackApp();
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
	public StackApp() {
		setTitle("StackApp - Marko Karapandzic IT9-2021");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 666, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelBtn = new JPanel();
		contentPane.add(panelBtn, BorderLayout.SOUTH);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=0;
				DlgCircle dlgCircle = new DlgCircle();
				dlgCircle.setVisible(true);
				if(dlgCircle.getCircle()!=null) {
					dlm.add(i, dlgCircle.getCircle());
					i++;
				}
			}
		});
		panelBtn.add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!dlm.isEmpty()) {
					DlgCircle dlg = new DlgCircle();
					dlg.setCircle(dlm.get(0));
					dlg.setVisible(true);
					dlm.remove(0);
				}
			}
		});
		panelBtn.add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JList<Circle> lstStack = new JList<Circle>();
		scrollPane.setViewportView(lstStack);
		
		JLabel lblTitle = new JLabel("Stack");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		scrollPane.setColumnHeaderView(lblTitle);
		
		lstStack.setModel(dlm);
	}

}





















