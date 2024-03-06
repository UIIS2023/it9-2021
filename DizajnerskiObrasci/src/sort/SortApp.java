package sort;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import drawingApp.Circle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class SortApp extends JFrame {

	private JPanel contentPane;
	private ArrayList<Circle> circleList  = new ArrayList<Circle>();
	private DefaultListModel<Circle> dlm = new DefaultListModel<Circle>();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SortApp frame = new SortApp();
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
	public SortApp() {
		setTitle("SortApp - Marko Karapandzic IT9-2021");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnAdd = new JButton("Add");
		
		panel.add(btnAdd);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnClose);
		
		JLabel lblSort = new JLabel("Sort");
		lblSort.setHorizontalAlignment(SwingConstants.CENTER);
		lblSort.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblSort, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JList<Circle> circleJList = new JList<Circle>();
		scrollPane.setViewportView(circleJList);
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgCircleSort dlg = new DlgCircleSort();
				dlg.setVisible(true);
				if(dlg.getCircle()!=null) {
					circleList.add(dlg.getCircle());
					
				}
				circleList.sort(null);
				circleJList.setModel(getSortedModel(circleList));
			}
		});
		
		
	}
	private DefaultListModel<Circle> getSortedModel(ArrayList<Circle> sortedList){
		Iterator<Circle> it = sortedList.iterator();
		dlm.clear();
		
		while(it.hasNext()) {
			dlm.addElement(it.next());
			
		}
		return dlm;
	}
}
