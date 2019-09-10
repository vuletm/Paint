package application.dialogs;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DialogAddRectangle extends JDialog {
	
	private JTextField tfWidth;
	private JTextField tfHeight;
	private int rectangleWidth;
	private int rectangleHeight;
	
	public DialogAddRectangle() {
		setSize(188, 120);
		setLocationRelativeTo(null);
		setModal(true);
		setTitle("Draw - Rectangle");
		setResizable(false);
		
		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{0, 0, 0};
		gbl_mainPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_mainPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		mainPanel.setLayout(gbl_mainPanel);
		
		JLabel lblWidth = new JLabel("Width:");
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.anchor = GridBagConstraints.EAST;
		gbc_lblWidth.gridx = 0;
		gbc_lblWidth.gridy = 0;
		mainPanel.add(lblWidth, gbc_lblWidth);
		
		tfWidth = new JTextField();
		GridBagConstraints gbc_tfWidth = new GridBagConstraints();
		gbc_tfWidth.insets = new Insets(0, 0, 5, 0);
		gbc_tfWidth.anchor = GridBagConstraints.NORTH;
		gbc_tfWidth.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfWidth.gridx = 1;
		gbc_tfWidth.gridy = 0;
		mainPanel.add(tfWidth, gbc_tfWidth);
		tfWidth.setColumns(10);
		
		JLabel lblHeight = new JLabel("Height:");
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.anchor = GridBagConstraints.EAST;
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 0;
		gbc_lblHeight.gridy = 1;
		mainPanel.add(lblHeight, gbc_lblHeight);
		
		tfHeight = new JTextField();
		GridBagConstraints gbc_tfHeight = new GridBagConstraints();
		gbc_tfHeight.insets = new Insets(0, 0, 5, 0);
		gbc_tfHeight.anchor = GridBagConstraints.NORTH;
		gbc_tfHeight.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfHeight.gridx = 1;
		gbc_tfHeight.gridy = 1;
		mainPanel.add(tfHeight, gbc_tfHeight);
		tfHeight.setColumns(10);
		
		JButton btnAccept = new JButton("Accept");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					
					rectangleWidth=Integer.parseInt(tfWidth.getText());
					rectangleHeight=Integer.parseInt(tfHeight.getText());
					
					if(rectangleWidth>400 || rectangleWidth<1 || rectangleHeight>400 || rectangleHeight<1)
					{
						throw new NumberFormatException();
					}
					
					dispose();
					
				}catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(null, "Only numbers 1-400 allowed","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.gridx = 1;
		gbc_btnAccept.gridy = 2;
		mainPanel.add(btnAccept, gbc_btnAccept);
	}

	public int getRectangleWidth() {
		return rectangleWidth;
	}

	public int getRectangleHeight() {
		return rectangleHeight;
	}
	
	
	
}
