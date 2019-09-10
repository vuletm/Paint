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

public class DialogAddSquare extends JDialog{
	
	private JTextField tfSide;
	private int side;
	
	
	public DialogAddSquare()
	{
		setSize(188, 89);
		setLocationRelativeTo(null);
		setModal(true);
		setTitle("Draw - Square");
		setResizable(false);
		
		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{0, 0, 0};
		gbl_mainPanel.rowHeights = new int[]{0, 0, 0};
		gbl_mainPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		mainPanel.setLayout(gbl_mainPanel);
		
		JLabel lblSide = new JLabel("Side:");
		GridBagConstraints gbc_lblSide = new GridBagConstraints();
		gbc_lblSide.insets = new Insets(0, 0, 5, 5);
		gbc_lblSide.anchor = GridBagConstraints.EAST;
		gbc_lblSide.gridx = 0;
		gbc_lblSide.gridy = 0;
		mainPanel.add(lblSide, gbc_lblSide);
		
		tfSide = new JTextField();
		GridBagConstraints gbc_tfSide = new GridBagConstraints();
		gbc_tfSide.insets = new Insets(0, 0, 5, 0);
		gbc_tfSide.anchor = GridBagConstraints.NORTH;
		gbc_tfSide.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSide.gridx = 1;
		gbc_tfSide.gridy = 0;
		mainPanel.add(tfSide, gbc_tfSide);
		tfSide.setColumns(10);
		
		JButton btnAccept = new JButton("Accept");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					
					side=Integer.parseInt(tfSide.getText());
					
					if(side>400 || side<1)
						throw new NumberFormatException();
					
					dispose();
					
				}catch(NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(null, "Only numbers 1-400 allowed","Error",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.gridx = 1;
		gbc_btnAccept.gridy = 1;
		mainPanel.add(btnAccept, gbc_btnAccept);
	}
	
	public int getSide() {
		return side;
	}
	
	
}
