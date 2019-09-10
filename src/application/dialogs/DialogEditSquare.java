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
import javax.swing.SwingConstants;

import geometry.Square;

public class DialogEditSquare extends JDialog {
	
	private JTextField tfStartX;
	private JTextField tfStartY;
	private JTextField tfSide;
	private int startX;
	private int startY;
	private int side;
	private Color foreground;
	private Color background;
	
	public DialogEditSquare(Square s)
	{
		setSize(201, 187);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setTitle("Edit - Square");

		foreground = s.getForeground();
		background = s.getBackground();

		JPanel jpMainPanel = new JPanel();
		getContentPane().add(jpMainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_jpMainPanel = new GridBagLayout();
		gbl_jpMainPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_jpMainPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_jpMainPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_jpMainPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		jpMainPanel.setLayout(gbl_jpMainPanel);

		JLabel lblStartX = new JLabel("X (Start point):");
		lblStartX.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblStartX = new GridBagConstraints();
		gbc_lblStartX.anchor = GridBagConstraints.EAST;
		gbc_lblStartX.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartX.gridx = 0;
		gbc_lblStartX.gridy = 0;
		jpMainPanel.add(lblStartX, gbc_lblStartX);

		tfStartX = new JTextField();
		tfStartX.setText(String.valueOf(s.getUpperLeft().getX()));
		GridBagConstraints gbc_tfStartX = new GridBagConstraints();
		gbc_tfStartX.insets = new Insets(0, 0, 5, 0);
		gbc_tfStartX.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStartX.gridx = 1;
		gbc_tfStartX.gridy = 0;
		jpMainPanel.add(tfStartX, gbc_tfStartX);
		tfStartX.setColumns(10);

		JLabel lblStartY = new JLabel("Y (Start point):");
		GridBagConstraints gbc_lblStartY = new GridBagConstraints();
		gbc_lblStartY.anchor = GridBagConstraints.EAST;
		gbc_lblStartY.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartY.gridx = 0;
		gbc_lblStartY.gridy = 1;
		jpMainPanel.add(lblStartY, gbc_lblStartY);

		tfStartY = new JTextField();
		tfStartY.setText(String.valueOf(s.getUpperLeft().getY()));
		GridBagConstraints gbc_tfStartY = new GridBagConstraints();
		gbc_tfStartY.insets = new Insets(0, 0, 5, 0);
		gbc_tfStartY.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStartY.gridx = 1;
		gbc_tfStartY.gridy = 1;
		jpMainPanel.add(tfStartY, gbc_tfStartY);
		tfStartY.setColumns(10);

		JLabel lblSide = new JLabel("Side:");
		GridBagConstraints gbc_lblSide = new GridBagConstraints();
		gbc_lblSide.anchor = GridBagConstraints.EAST;
		gbc_lblSide.insets = new Insets(0, 0, 5, 5);
		gbc_lblSide.gridx = 0;
		gbc_lblSide.gridy = 2;
		jpMainPanel.add(lblSide, gbc_lblSide);

		tfSide = new JTextField();
		tfSide.setText(String.valueOf(s.getSide()));
		GridBagConstraints gbc_tfSide = new GridBagConstraints();
		gbc_tfSide.insets = new Insets(0, 0, 5, 0);
		gbc_tfSide.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSide.gridx = 1;
		gbc_tfSide.gridy = 2;
		jpMainPanel.add(tfSide, gbc_tfSide);
		tfSide.setColumns(10);

		JLabel lblForeground = new JLabel("Foreground:");
		GridBagConstraints gbc_lblForeground = new GridBagConstraints();
		gbc_lblForeground.anchor = GridBagConstraints.EAST;
		gbc_lblForeground.insets = new Insets(0, 0, 5, 5);
		gbc_lblForeground.gridx = 0;
		gbc_lblForeground.gridy = 3;
		jpMainPanel.add(lblForeground, gbc_lblForeground);

		JButton btnForeground = new JButton("");
		btnForeground.setBackground(foreground);
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
		btnForeground.setPreferredSize(new Dimension(40, 20));
		GridBagConstraints gbc_btnForeground = new GridBagConstraints();
		gbc_btnForeground.insets = new Insets(0, 0, 5, 0);
		gbc_btnForeground.gridx = 1;
		gbc_btnForeground.gridy = 3;
		jpMainPanel.add(btnForeground, gbc_btnForeground);

		JLabel lblBackground = new JLabel("Background:");
		GridBagConstraints gbc_lblBackground = new GridBagConstraints();
		gbc_lblBackground.anchor = GridBagConstraints.EAST;
		gbc_lblBackground.insets = new Insets(0, 0, 5, 5);
		gbc_lblBackground.gridx = 0;
		gbc_lblBackground.gridy = 4;
		jpMainPanel.add(lblBackground, gbc_lblBackground);

		JButton btnBackground = new JButton("");
		btnBackground.setBackground(background);
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
					startX = Integer.parseInt(tfStartX.getText());
					startY = Integer.parseInt(tfStartY.getText());
					side = Integer.parseInt(tfSide.getText());

					if (startX < 1 || startX > 850 || startY < 1 || startY > 530 || side < 1 || side > 400) {
						throw new NumberFormatException();
					}

					dispose();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(DialogEditSquare.this,
							"Only numbers 1-850 for X, 1-530 for Y and 1-400 for side allowed", "Warrning",
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
	
	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getSide() {
		return side;
	}

	public Color getForeground() {
		return foreground;
	}

	public Color getBackground() {
		return background;
	}

}
