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

public class DialogAddHexagon extends JDialog {
	
	private JTextField tfRadius;
	private int radius;
	
	public DialogAddHexagon()
	{
		setSize(188, 96);
		setLocationRelativeTo(null);
		setModal(true);
		setTitle("Draw - Hexagon");
		setResizable(false);
		
		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{0, 0, 0};
		gbl_mainPanel.rowHeights = new int[]{0, 0, 0};
		gbl_mainPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		mainPanel.setLayout(gbl_mainPanel);
		
		JLabel lblRadius = new JLabel("Radius:");
		GridBagConstraints gbc_lblRadius = new GridBagConstraints();
		gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
		gbc_lblRadius.anchor = GridBagConstraints.EAST;
		gbc_lblRadius.gridx = 0;
		gbc_lblRadius.gridy = 0;
		mainPanel.add(lblRadius, gbc_lblRadius);
		
		tfRadius = new JTextField();
		GridBagConstraints gbc_tfRadius = new GridBagConstraints();
		gbc_tfRadius.insets = new Insets(0, 0, 5, 0);
		gbc_tfRadius.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfRadius.gridx = 1;
		gbc_tfRadius.gridy = 0;
		mainPanel.add(tfRadius, gbc_tfRadius);
		tfRadius.setColumns(10);
		
		JButton btnAccept = new JButton("Accept");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					
					radius =Integer.parseInt(tfRadius.getText());
					
					if(radius>400 || radius<1)
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

	public int getRadius() {
		return radius;
	}
}
