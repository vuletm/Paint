package application.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import geometry.Circle;

public class DialogEditCircle extends JDialog {
	
	private JTextField tfCenterX;
	private JTextField tfCenterY;
	private JTextField tfRadius;
	private int centerX;
	private int centerY;
	private int radius;
	private Color foreground;
	private Color background;
	
	public DialogEditCircle(Circle c)
	{
		setSize(187, 186);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setTitle("Edit - Circle");

		foreground = c.getForeground();
		background = c.getBackground();

		JPanel jpMainPanel = new JPanel();
		getContentPane().add(jpMainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_jpMainPanel = new GridBagLayout();
		gbl_jpMainPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_jpMainPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_jpMainPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_jpMainPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		jpMainPanel.setLayout(gbl_jpMainPanel);

		JLabel lblCenterX = new JLabel("X (Center):");
		GridBagConstraints gbc_lblCenterX = new GridBagConstraints();
		gbc_lblCenterX.anchor = GridBagConstraints.EAST;
		gbc_lblCenterX.insets = new Insets(0, 0, 5, 5);
		gbc_lblCenterX.gridx = 0;
		gbc_lblCenterX.gridy = 0;
		jpMainPanel.add(lblCenterX, gbc_lblCenterX);

		tfCenterX = new JTextField();
		tfCenterX.setText(String.valueOf(c.getCenter().getX()));
		GridBagConstraints gbc_tfCenterX = new GridBagConstraints();
		gbc_tfCenterX.insets = new Insets(0, 0, 5, 0);
		gbc_tfCenterX.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfCenterX.gridx = 1;
		gbc_tfCenterX.gridy = 0;
		jpMainPanel.add(tfCenterX, gbc_tfCenterX);
		tfCenterX.setColumns(10);

		JLabel lblCenterY = new JLabel("Y (Center):");
		GridBagConstraints gbc_lblCenterY = new GridBagConstraints();
		gbc_lblCenterY.anchor = GridBagConstraints.EAST;
		gbc_lblCenterY.insets = new Insets(0, 0, 5, 5);
		gbc_lblCenterY.gridx = 0;
		gbc_lblCenterY.gridy = 1;
		jpMainPanel.add(lblCenterY, gbc_lblCenterY);

		tfCenterY = new JTextField();
		tfCenterY.setText(String.valueOf(c.getCenter().getY()));
		GridBagConstraints gbc_tfCenterY = new GridBagConstraints();
		gbc_tfCenterY.insets = new Insets(0, 0, 5, 0);
		gbc_tfCenterY.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfCenterY.gridx = 1;
		gbc_tfCenterY.gridy = 1;
		jpMainPanel.add(tfCenterY, gbc_tfCenterY);
		tfCenterY.setColumns(10);

		JLabel lblRadius = new JLabel("Radius:");
		GridBagConstraints gbc_lblRadius = new GridBagConstraints();
		gbc_lblRadius.anchor = GridBagConstraints.EAST;
		gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
		gbc_lblRadius.gridx = 0;
		gbc_lblRadius.gridy = 2;
		jpMainPanel.add(lblRadius, gbc_lblRadius);

		tfRadius = new JTextField();
		tfRadius.setText(String.valueOf(c.getRadius()));
		GridBagConstraints gbc_tfRadius = new GridBagConstraints();
		gbc_tfRadius.insets = new Insets(0, 0, 5, 0);
		gbc_tfRadius.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfRadius.gridx = 1;
		gbc_tfRadius.gridy = 2;
		jpMainPanel.add(tfRadius, gbc_tfRadius);
		tfRadius.setColumns(10);

		JLabel lblForeground = new JLabel("Foreground:");
		GridBagConstraints gbc_lblForeground = new GridBagConstraints();
		gbc_lblForeground.insets = new Insets(0, 0, 5, 5);
		gbc_lblForeground.gridx = 0;
		gbc_lblForeground.gridy = 3;
		jpMainPanel.add(lblForeground, gbc_lblForeground);

		JButton btnForeground = new JButton("");
		btnForeground.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Color previousForeground = foreground;
				JColorChooser jcc = new JColorChooser();
				foreground = jcc.showDialog(null, "Choose foreground color", foreground);

				if (foreground == null) {
					foreground = previousForeground;
				}

				btnForeground.setBackground(foreground);
			}
		});
		btnForeground.setBackground(foreground);
		btnForeground.setPreferredSize(new Dimension(40, 20));
		GridBagConstraints gbc_btnForeground = new GridBagConstraints();
		gbc_btnForeground.insets = new Insets(0, 0, 5, 0);
		gbc_btnForeground.gridx = 1;
		gbc_btnForeground.gridy = 3;
		jpMainPanel.add(btnForeground, gbc_btnForeground);

		JLabel lblBackground = new JLabel("Background:");
		GridBagConstraints gbc_lblBackground = new GridBagConstraints();
		gbc_lblBackground.insets = new Insets(0, 0, 5, 5);
		gbc_lblBackground.gridx = 0;
		gbc_lblBackground.gridy = 4;
		jpMainPanel.add(lblBackground, gbc_lblBackground);

		JButton btnBackground = new JButton("");
		btnBackground.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent e) {
				Color previousBackground = background;
				JColorChooser jcc = new JColorChooser();
				background = jcc.showDialog(null, "Choose background color", background);

				if (background == null) {
					background = previousBackground;
				}

				btnBackground.setBackground(background);
			}
		});
		btnBackground.setBackground(background);
		btnBackground.setPreferredSize(new Dimension(40, 20));
		GridBagConstraints gbc_btnBackground = new GridBagConstraints();
		gbc_btnBackground.insets = new Insets(0, 0, 5, 0);
		gbc_btnBackground.gridx = 1;
		gbc_btnBackground.gridy = 4;
		jpMainPanel.add(btnBackground, gbc_btnBackground);

		JButton btnAccept = new JButton("Accept");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {

					centerX = Integer.parseInt(tfCenterX.getText());
					centerY = Integer.parseInt(tfCenterY.getText());
					radius = Integer.parseInt(tfRadius.getText());

					if (centerX < 1 || centerX > 850 || centerY < 1 || centerY > 530 || radius < 1 || radius > 400) {
						throw new NumberFormatException();
					}

					dispose();

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(DialogEditCircle.this,
							"Only numbers 1-850 for X, 1-530 for Y and 1-400 for radius allowed", "Warrning",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.gridwidth = 2;
		gbc_btnAccept.gridx = 0;
		gbc_btnAccept.gridy = 5;
		jpMainPanel.add(btnAccept, gbc_btnAccept);
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getRadius() {
		return radius;
	}

	public Color getForeground() {
		return foreground;
	}

	public Color getBackground() {
		return background;
	}

}
