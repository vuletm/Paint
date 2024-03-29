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
import javax.swing.SwingConstants;

import geometry.Line;

public class DialogEditLine extends JDialog {

	private JTextField tfStartX;
	private JTextField tfStartY;
	private JTextField tfEndX;
	private JTextField tfEndY;
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private Color foreground;
	
	public DialogEditLine(Line l) {

		setSize(201, 187);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setTitle("Edit - Line");

		foreground = l.getForeground();

		JPanel jpMainPanel = new JPanel();
		getContentPane().add(jpMainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_jpMainPanel = new GridBagLayout();
		gbl_jpMainPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_jpMainPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_jpMainPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_jpMainPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		jpMainPanel.setLayout(gbl_jpMainPanel);

		JLabel lblStartX = new JLabel("X (Start point):");
		GridBagConstraints gbc_lblStartX = new GridBagConstraints();
		gbc_lblStartX.anchor = GridBagConstraints.EAST;
		gbc_lblStartX.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartX.gridx = 0;
		gbc_lblStartX.gridy = 0;
		jpMainPanel.add(lblStartX, gbc_lblStartX);

		tfStartX = new JTextField();
		tfStartX.setText(String.valueOf(l.getStart().getX()));
		GridBagConstraints gbc_tfStartX = new GridBagConstraints();
		gbc_tfStartX.insets = new Insets(0, 0, 5, 0);
		gbc_tfStartX.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStartX.gridx = 1;
		gbc_tfStartX.gridy = 0;
		jpMainPanel.add(tfStartX, gbc_tfStartX);
		tfStartX.setColumns(10);

		JLabel lblStartY = new JLabel("Y (Start point):");
		lblStartY.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblStartY = new GridBagConstraints();
		gbc_lblStartY.anchor = GridBagConstraints.EAST;
		gbc_lblStartY.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartY.gridx = 0;
		gbc_lblStartY.gridy = 1;
		jpMainPanel.add(lblStartY, gbc_lblStartY);

		tfStartY = new JTextField();
		tfStartY.setText(String.valueOf(l.getStart().getY()));
		GridBagConstraints gbc_tfStartY = new GridBagConstraints();
		gbc_tfStartY.insets = new Insets(0, 0, 5, 0);
		gbc_tfStartY.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfStartY.gridx = 1;
		gbc_tfStartY.gridy = 1;
		jpMainPanel.add(tfStartY, gbc_tfStartY);
		tfStartY.setColumns(10);

		JLabel lblEndX = new JLabel("X (End point):");
		GridBagConstraints gbc_lblEndX = new GridBagConstraints();
		gbc_lblEndX.anchor = GridBagConstraints.EAST;
		gbc_lblEndX.insets = new Insets(0, 0, 5, 5);
		gbc_lblEndX.gridx = 0;
		gbc_lblEndX.gridy = 2;
		jpMainPanel.add(lblEndX, gbc_lblEndX);

		tfEndX = new JTextField();
		tfEndX.setText(String.valueOf(l.getEnd().getX()));
		GridBagConstraints gbc_tfEndX = new GridBagConstraints();
		gbc_tfEndX.insets = new Insets(0, 0, 5, 0);
		gbc_tfEndX.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfEndX.gridx = 1;
		gbc_tfEndX.gridy = 2;
		jpMainPanel.add(tfEndX, gbc_tfEndX);
		tfEndX.setColumns(10);

		JLabel lblEndY = new JLabel("Y (End point):");
		GridBagConstraints gbc_lblEndY = new GridBagConstraints();
		gbc_lblEndY.anchor = GridBagConstraints.EAST;
		gbc_lblEndY.insets = new Insets(0, 0, 5, 5);
		gbc_lblEndY.gridx = 0;
		gbc_lblEndY.gridy = 3;
		jpMainPanel.add(lblEndY, gbc_lblEndY);

		tfEndY = new JTextField();
		tfEndY.setText(String.valueOf(l.getEnd().getY()));
		GridBagConstraints gbc_tfEndY = new GridBagConstraints();
		gbc_tfEndY.insets = new Insets(0, 0, 5, 0);
		gbc_tfEndY.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfEndY.gridx = 1;
		gbc_tfEndY.gridy = 3;
		jpMainPanel.add(tfEndY, gbc_tfEndY);
		tfEndY.setColumns(10);

		JLabel lblColor = new JLabel("Color:");
		GridBagConstraints gbc_lblColor = new GridBagConstraints();
		gbc_lblColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblColor.gridx = 0;
		gbc_lblColor.gridy = 4;
		jpMainPanel.add(lblColor, gbc_lblColor);

		JButton btnColor = new JButton("");
		btnColor.setBackground(foreground);
		btnColor.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent e) {
				Color previousForeground = foreground;
				JColorChooser jcc = new JColorChooser();
				foreground = jcc.showDialog(null, "Choose foreground color", foreground);

				if (foreground == null) {
					foreground = previousForeground;
				}

				btnColor.setBackground(foreground);
			}
		});
		btnColor.setPreferredSize(new Dimension(40, 20));
		GridBagConstraints gbc_btnColor = new GridBagConstraints();
		gbc_btnColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnColor.gridx = 1;
		gbc_btnColor.gridy = 4;
		jpMainPanel.add(btnColor, gbc_btnColor);

		JButton btnAccept = new JButton("Accept");
		btnAccept.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					startX = Integer.parseInt(tfStartX.getText());
					startY = Integer.parseInt(tfStartY.getText());
					endX = Integer.parseInt(tfEndX.getText());
					endY = Integer.parseInt(tfEndY.getText());

					if (startX < 1 || startX > 850 || startY < 1 || startY > 530 || endX < 1 || endX > 850 || endY < 1
							|| endY > 530) {
						throw new NumberFormatException();
					}
					dispose();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(DialogEditLine.this, "Only numbers 1-850 for X and 1-530 for Y allowed",
							"Warrning", JOptionPane.ERROR_MESSAGE);
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

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}
	
	public Color getForeground() {
		return foreground;
	}
	
}
