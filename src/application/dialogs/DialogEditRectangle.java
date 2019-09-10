package application.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import geometry.Rectangle;

public class DialogEditRectangle extends JDialog {

	private JTextField tfStartX;
	private JTextField tfStartY;
	private JTextField tfHeight;
	private JTextField tfWidth;
	private int startX;
	private int startY;
	private int heightR;
	private int widthR;
	private Color foreground;
	private Color background;
	
	public DialogEditRectangle(Rectangle r)
	{
		setSize(193, 219);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setTitle("Edit - Rectangle");

		foreground = r.getForeground();
		background = r.getBackground();

		JPanel jpMainPanel = new JPanel();
		getContentPane().add(jpMainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_jpMainPanel = new GridBagLayout();
		gbl_jpMainPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_jpMainPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_jpMainPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_jpMainPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		jpMainPanel.setLayout(gbl_jpMainPanel);

		JLabel lblXstartPoint = new JLabel("X (Start point):");
		GridBagConstraints gbc_lblXstartPoint = new GridBagConstraints();
		gbc_lblXstartPoint.anchor = GridBagConstraints.EAST;
		gbc_lblXstartPoint.insets = new Insets(0, 0, 5, 5);
		gbc_lblXstartPoint.gridx = 0;
		gbc_lblXstartPoint.gridy = 0;
		jpMainPanel.add(lblXstartPoint, gbc_lblXstartPoint);

		tfStartX = new JTextField();
		tfStartX.setText(String.valueOf(r.getUpperLeft().getX()));
		GridBagConstraints gbc_tfStartX = new GridBagConstraints();
		gbc_tfStartX.insets = new Insets(0, 0, 5, 0);
		gbc_tfStartX.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStartX.gridx = 1;
		gbc_tfStartX.gridy = 0;
		jpMainPanel.add(tfStartX, gbc_tfStartX);
		tfStartX.setColumns(10);

		JLabel lblYstartPoint = new JLabel("Y (Start point):");
		GridBagConstraints gbc_lblYstartPoint = new GridBagConstraints();
		gbc_lblYstartPoint.anchor = GridBagConstraints.EAST;
		gbc_lblYstartPoint.insets = new Insets(0, 0, 5, 5);
		gbc_lblYstartPoint.gridx = 0;
		gbc_lblYstartPoint.gridy = 1;
		jpMainPanel.add(lblYstartPoint, gbc_lblYstartPoint);

		tfStartY = new JTextField();
		tfStartY.setText(String.valueOf(r.getUpperLeft().getY()));
		GridBagConstraints gbc_tfStartY = new GridBagConstraints();
		gbc_tfStartY.insets = new Insets(0, 0, 5, 0);
		gbc_tfStartY.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStartY.gridx = 1;
		gbc_tfStartY.gridy = 1;
		jpMainPanel.add(tfStartY, gbc_tfStartY);
		tfStartY.setColumns(10);

		JLabel lblHeight = new JLabel("Height:");
		GridBagConstraints gbc_lblHeight = new GridBagConstraints();
		gbc_lblHeight.anchor = GridBagConstraints.EAST;
		gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeight.gridx = 0;
		gbc_lblHeight.gridy = 2;
		jpMainPanel.add(lblHeight, gbc_lblHeight);

		tfHeight = new JTextField();
		tfHeight.setText(String.valueOf(r.getHeight()));
		GridBagConstraints gbc_tfHeight = new GridBagConstraints();
		gbc_tfHeight.insets = new Insets(0, 0, 5, 0);
		gbc_tfHeight.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfHeight.gridx = 1;
		gbc_tfHeight.gridy = 2;
		jpMainPanel.add(tfHeight, gbc_tfHeight);
		tfHeight.setColumns(10);

		JLabel lblWidth = new JLabel("Width:");
		GridBagConstraints gbc_lblWidth = new GridBagConstraints();
		gbc_lblWidth.anchor = GridBagConstraints.EAST;
		gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
		gbc_lblWidth.gridx = 0;
		gbc_lblWidth.gridy = 3;
		jpMainPanel.add(lblWidth, gbc_lblWidth);

		tfWidth = new JTextField();
		tfWidth.setText(String.valueOf(r.getSide()));
		GridBagConstraints gbc_tfWidth = new GridBagConstraints();
		gbc_tfWidth.insets = new Insets(0, 0, 5, 0);
		gbc_tfWidth.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfWidth.gridx = 1;
		gbc_tfWidth.gridy = 3;
		jpMainPanel.add(tfWidth, gbc_tfWidth);
		tfWidth.setColumns(10);

		JLabel lblForeground = new JLabel("Foreground:");
		GridBagConstraints gbc_lblForeground = new GridBagConstraints();
		gbc_lblForeground.insets = new Insets(0, 0, 5, 5);
		gbc_lblForeground.gridx = 0;
		gbc_lblForeground.gridy = 4;
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
		gbc_btnForeground.gridy = 4;
		jpMainPanel.add(btnForeground, gbc_btnForeground);

		JLabel lblBackground = new JLabel("Background:");
		GridBagConstraints gbc_lblBackground = new GridBagConstraints();
		gbc_lblBackground.insets = new Insets(0, 0, 5, 5);
		gbc_lblBackground.gridx = 0;
		gbc_lblBackground.gridy = 5;
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
		gbc_btnBackground.gridy = 5;
		jpMainPanel.add(btnBackground, gbc_btnBackground);

		JButton btnAccept = new JButton("Accept");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					startX = Integer.parseInt(tfStartX.getText());
					startY = Integer.parseInt(tfStartY.getText());
					heightR = Integer.parseInt(tfHeight.getText());
					widthR = Integer.parseInt(tfWidth.getText());

					if (startX < 1 || startX > 850 || startY < 1 || startY > 530 || heightR < 1 || heightR > 400
							|| widthR < 1 || widthR > 400) {
						throw new NumberFormatException();
					}

					dispose();

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(DialogEditRectangle.this,
							"Only numbers 1-850 for X, 1-530 for Y and 1-400 for height and width allowed", "Warrning",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_btnAccept = new GridBagConstraints();
		gbc_btnAccept.gridwidth = 2;
		gbc_btnAccept.gridx = 0;
		gbc_btnAccept.gridy = 6;
		jpMainPanel.add(btnAccept, gbc_btnAccept);

	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getHeightR() {
		return heightR;
	}

	public int getWidthR() {
		return widthR;
	}

	public Color getForeground() {
		return foreground;
	}

	public Color getBackground() {
		return background;
	}
	
}
