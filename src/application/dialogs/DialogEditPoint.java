package application.dialogs;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

import geometry.Point;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DialogEditPoint extends JDialog {
	private JTextField tfX;
	private JTextField tfY;
	
	private Color foreground;
	private int x,y;
	
	public DialogEditPoint(Point p)
	{
		setSize(204, 128);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setTitle("Edit - Point");

		foreground = p.getForeground();

		JPanel jpMainPanel = new JPanel();
		getContentPane().add(jpMainPanel, BorderLayout.CENTER);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		jpMainPanel.setLayout(gridBagLayout);

		JLabel lblX = new JLabel("X:");
		GridBagConstraints gbc_lblX = new GridBagConstraints();
		gbc_lblX.insets = new Insets(0, 0, 5, 5);
		gbc_lblX.anchor = GridBagConstraints.EAST;
		gbc_lblX.gridx = 0;
		gbc_lblX.gridy = 0;
		jpMainPanel.add(lblX, gbc_lblX);

		tfX = new JTextField();
		tfX.setText(String.valueOf(p.getX()));
		GridBagConstraints gbc_tfX = new GridBagConstraints();
		gbc_tfX.insets = new Insets(0, 0, 5, 0);
		gbc_tfX.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfX.gridx = 1;
		gbc_tfX.gridy = 0;
		jpMainPanel.add(tfX, gbc_tfX);
		tfX.setColumns(10);

		JLabel lblY = new JLabel("Y:");
		GridBagConstraints gbc_lblY = new GridBagConstraints();
		gbc_lblY.anchor = GridBagConstraints.EAST;
		gbc_lblY.insets = new Insets(0, 0, 5, 5);
		gbc_lblY.gridx = 0;
		gbc_lblY.gridy = 1;
		jpMainPanel.add(lblY, gbc_lblY);

		tfY = new JTextField();
		tfY.setText(String.valueOf(p.getY()));
		GridBagConstraints gbc_tfY = new GridBagConstraints();
		gbc_tfY.insets = new Insets(0, 0, 5, 0);
		gbc_tfY.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfY.gridx = 1;
		gbc_tfY.gridy = 1;
		jpMainPanel.add(tfY, gbc_tfY);
		tfY.setColumns(10);

		JLabel lblColor = new JLabel("Color:");
		GridBagConstraints gbc_lblColor = new GridBagConstraints();
		gbc_lblColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblColor.gridx = 0;
		gbc_lblColor.gridy = 2;
		jpMainPanel.add(lblColor, gbc_lblColor);

		JButton btnColor = new JButton("");
		btnColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color previousForeground = foreground;//????
				JColorChooser jcc = new JColorChooser();
				foreground = jcc.showDialog(null, "Choose foreground color", foreground);

				if (foreground== null) {
					foreground = previousForeground;
				}

				btnColor.setBackground(foreground);
			}
		});
		btnColor.setBackground(foreground);
		btnColor.setPreferredSize(new Dimension(32, 20));
		GridBagConstraints gbc_btnColor = new GridBagConstraints();
		gbc_btnColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnColor.gridx = 1;
		gbc_btnColor.gridy = 2;
		jpMainPanel.add(btnColor, gbc_btnColor);

		JButton btnAccept = new JButton("Accept");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					x = Integer.parseInt(tfX.getText());
					y = Integer.parseInt(tfY.getText());

					if (x > 850 || x < 1 || y > 530 || y < 1) {
						throw new NumberFormatException();
					}
					dispose();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(DialogEditPoint.this, "Only numbers 1-850 for X and 1-530 for Y allowed",
							"Warrning", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.gridwidth = 2;
		gbc_btnAccept.gridx = 0;
		gbc_btnAccept.gridy = 3;
		jpMainPanel.add(btnAccept, gbc_btnAccept);
	}

	public Color getForeground() {
		return foreground;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	

}
