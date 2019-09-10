package application.mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import application.observer.Observer;

public class DrawFrame extends JFrame implements Observer {
	/*
	 * Frame implementira Observer, zato sto Frame prati da li se neki oblik selektovao, cim se selektuje
	 * on enabluje dugmice u zavisnosti kolko je oblika selektovano 
	 */
	private DrawController controller;
	private DrawView view = new DrawView();
	
	private JLabel lblDescription;
	private JToggleButton tglbtnSelect;
	private JToggleButton tglbtnPoint;
	private JToggleButton tglbtnLine;
	private JToggleButton tglbtnRectangle;
	private JToggleButton tglbtnSquare;
	private JToggleButton tglbtnCircle;
	private JToggleButton tglbtnHexagon;
	private JButton btnUndo;
	private JButton btnRedo;
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnToback;
	private JButton btnTofront;
	private JButton btnBringtoback;
	private JButton btnBringtofront;
	private JButton btnForeground;
	private JButton btnBackground;
	
	private Color foreground=Color.BLACK;
	private Color background=Color.WHITE;

	private MouseEvent firstClick;
	
	//u dlm smestas bas podatke,npr String tekstove komandi, a ona JListi setujes ovaj model, tako da JLista zna da treba da pokazuje podatke iz ovog dlma
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	
	private JMenuItem mntmExit;
	private JButton btnRunNextCommand;
	
	public DrawFrame() {

		//view je JPanel
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				
				if(tglbtnSelect.isSelected())//ako je izabrano dugme Select pozovi Select metodu iz kontrolera????????
				{
					controller.select(click);
				}
				else if(tglbtnLine.isSelected())//ako je izabrano Line, drugacija je logika jer ne zelimo da na prvi klik odradi nesto
				{
					if(firstClick==null)//cuvanje prvog klika
					{
						firstClick=click;
					}
					else//ako vec postoji prvi klik,odradi crtanje linije
					{
						controller.draw(click,firstClick , foreground, background);
						firstClick=null;//resetovanje prvog klika da bi opet mogao da crta
					}
				}
				else//ako je izabrano sve osim select i line dugmeta, pozivamo draw metodu iz kontrolera?????
				{
					controller.draw(click,firstClick,foreground,background);
				}
			}
		});
		view.setBackground(Color.WHITE);
		setSize(1000,700);
		
		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel shapesPanel = new JPanel();
		mainPanel.add(shapesPanel, BorderLayout.EAST);
		GridBagLayout gbl_shapesPanel = new GridBagLayout();
		gbl_shapesPanel.columnWidths = new int[]{0, 0, 0};
		gbl_shapesPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_shapesPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_shapesPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		shapesPanel.setLayout(gbl_shapesPanel);
		
		tglbtnPoint = new JToggleButton("",new ImageIcon("images/point.png"));
		tglbtnPoint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblDescription.setText("Draw point");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDescription.setText(" ");
			}
		});
		tglbtnPoint.setPreferredSize(new Dimension(24, 24));
		GridBagConstraints gbc_tglbtnPoint = new GridBagConstraints();
		gbc_tglbtnPoint.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnPoint.gridx = 0;
		gbc_tglbtnPoint.gridy = 0;
		shapesPanel.add(tglbtnPoint, gbc_tglbtnPoint);
		
		tglbtnSquare = new JToggleButton("",new ImageIcon("images/square.png"));
		tglbtnSquare.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblDescription.setText("Draw square");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDescription.setText(" ");
			}
		});
		tglbtnSquare.setPreferredSize(new Dimension(24, 24));
		GridBagConstraints gbc_tglbtnSquare = new GridBagConstraints();
		gbc_tglbtnSquare.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnSquare.gridx = 1;
		gbc_tglbtnSquare.gridy = 0;
		shapesPanel.add(tglbtnSquare, gbc_tglbtnSquare);
		
		 tglbtnLine = new JToggleButton("",new ImageIcon("images/line.png"));
		 tglbtnLine.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseEntered(MouseEvent e) {
		 		lblDescription.setText("Draw line");
		 	}
		 	@Override
		 	public void mouseExited(MouseEvent e) {
		 		lblDescription.setText(" ");
		 	}
		 });
		tglbtnLine.setPreferredSize(new Dimension(24, 24));
		GridBagConstraints gbc_tglbtnLine = new GridBagConstraints();
		gbc_tglbtnLine.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnLine.gridx = 0;
		gbc_tglbtnLine.gridy = 1;
		shapesPanel.add(tglbtnLine, gbc_tglbtnLine);
		
		 tglbtnCircle = new JToggleButton("",new ImageIcon("images/circle.png"));
		 tglbtnCircle.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseEntered(MouseEvent e) {
		 		lblDescription.setText("Draw circle");
		 	}
		 	@Override
		 	public void mouseExited(MouseEvent e) {
		 		lblDescription.setText(" ");
		 	}
		 });
		tglbtnCircle.setPreferredSize(new Dimension(24, 24));
		GridBagConstraints gbc_tglbtnCircle = new GridBagConstraints();
		gbc_tglbtnCircle.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnCircle.gridx = 1;
		gbc_tglbtnCircle.gridy = 1;
		shapesPanel.add(tglbtnCircle, gbc_tglbtnCircle);
		
		 tglbtnRectangle = new JToggleButton("",new ImageIcon("images/rectangle.png"));
		 tglbtnRectangle.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseEntered(MouseEvent e) {
		 		lblDescription.setText("Draw rectangle");
		 	}
		 });
		tglbtnRectangle.setPreferredSize(new Dimension(24, 24));
		GridBagConstraints gbc_tglbtnRectangle = new GridBagConstraints();
		gbc_tglbtnRectangle.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnRectangle.gridx = 0;
		gbc_tglbtnRectangle.gridy = 2;
		shapesPanel.add(tglbtnRectangle, gbc_tglbtnRectangle);
		
		 tglbtnHexagon = new JToggleButton("",new ImageIcon("images/hexagon.png"));
		 tglbtnHexagon.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseEntered(MouseEvent e) {
		 		lblDescription.setText("Draw hexagon");
		 	}
		 	@Override
		 	public void mouseExited(MouseEvent e) {
		 		lblDescription.setText(" ");
		 	}
		 });
		tglbtnHexagon.setPreferredSize(new Dimension(24, 24));
		GridBagConstraints gbc_tglbtnHexagon = new GridBagConstraints();
		gbc_tglbtnHexagon.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnHexagon.gridx = 1;
		gbc_tglbtnHexagon.gridy = 2;
		shapesPanel.add(tglbtnHexagon, gbc_tglbtnHexagon);
		
		btnForeground = new JButton("");
		btnForeground.setBackground(foreground);
		btnForeground.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Color previousColor = foreground;//cuvamo prethodnu boju ako klikne X prilikom biranja boje jer postane null onda
				
				JColorChooser jccForeground = new JColorChooser();
				foreground = jccForeground.showDialog(null, "Choose foreground color", foreground); //smestamo u promenljivu izabranu boju
				
				if(foreground==null)//ako je kliknuo X prilikom biranja boje, postavljamo na prethodno sacuvanu boju
					foreground=previousColor;
				
				btnForeground.setBackground(foreground);//bojimo dugme
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblDescription.setText("Choose foreground color");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDescription.setText(" ");
			}
		});
		btnForeground.setPreferredSize(new Dimension(32, 32));
		GridBagConstraints gbc_btnForeground = new GridBagConstraints();
		gbc_btnForeground.insets = new Insets(0, 0, 5, 5);
		gbc_btnForeground.gridx = 0;
		gbc_btnForeground.gridy = 4;
		shapesPanel.add(btnForeground, gbc_btnForeground);
		
		btnBackground = new JButton("");
		btnBackground.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color previousColor = background;
				
				JColorChooser jccBackground = new JColorChooser();
				background = jccBackground.showDialog(null, "Choose background color", background);
				
				if(background==null)
					background=previousColor;
				
				btnBackground.setBackground(background);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblDescription.setText("Choose background color");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDescription.setText(" ");
			}
		});
		btnBackground.setBackground(background);
		btnBackground.setPreferredSize(new Dimension(32, 32));
		GridBagConstraints gbc_btnBackground = new GridBagConstraints();
		gbc_btnBackground.insets = new Insets(0, 0, 5, 0);
		gbc_btnBackground.gridx = 1;
		gbc_btnBackground.gridy = 4;
		shapesPanel.add(btnBackground, gbc_btnBackground);
		
		JPanel actionsPanel = new JPanel();
		mainPanel.add(actionsPanel, BorderLayout.WEST);
		GridBagLayout gbl_actionsPanel = new GridBagLayout();
		gbl_actionsPanel.columnWidths = new int[]{0, 0, 0};
		gbl_actionsPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_actionsPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_actionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		actionsPanel.setLayout(gbl_actionsPanel);
		
		tglbtnSelect = new JToggleButton("",new ImageIcon("images/select.png"));
		tglbtnSelect.setSelected(true);
		tglbtnSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblDescription.setText("Select tool");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDescription.setText(" ");
			}
		});
		GridBagConstraints gbc_tglbtnSelect = new GridBagConstraints();
		gbc_tglbtnSelect.gridwidth = 2;
		gbc_tglbtnSelect.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnSelect.gridx = 0;
		gbc_tglbtnSelect.gridy = 0;
		actionsPanel.add(tglbtnSelect, gbc_tglbtnSelect);
		
		btnEdit = new JButton("",new ImageIcon("images/edit.png"));
		btnEdit.setEnabled(false);
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblDescription.setText("Edit shape");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDescription.setText(" ");
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnEdit.isEnabled())
				{
					controller.edit();
				}
			}
		});
		btnEdit.setPreferredSize(new Dimension(24, 24));
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.insets = new Insets(0, 0, 5, 5);
		gbc_btnEdit.gridx = 0;
		gbc_btnEdit.gridy = 1;
		actionsPanel.add(btnEdit, gbc_btnEdit);
		
		btnDelete = new JButton("",new ImageIcon("images/delete.png"));
		btnDelete.setEnabled(false);
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblDescription.setText("Delete shape");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDescription.setText(" ");
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnDelete.isEnabled())
				{
					int option = JOptionPane.showConfirmDialog(DrawFrame.this,
							"Delete selected shape/s?", "Delete", JOptionPane.YES_NO_OPTION);
					
					if(option==JOptionPane.YES_OPTION)
					{
						controller.delete();
					}
				}
			}
		});
		btnDelete.setPreferredSize(new Dimension(24, 24));
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 5, 0);
		gbc_btnDelete.gridx = 1;
		gbc_btnDelete.gridy = 1;
		actionsPanel.add(btnDelete, gbc_btnDelete);
		
		btnToback = new JButton("",new ImageIcon("images/toback.png"));
		btnToback.setEnabled(false);
		btnToback.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblDescription.setText("To back");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDescription.setText(" ");
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnToback.isEnabled())
				{
					controller.toBack();
				}
			}
		});
		btnToback.setPreferredSize(new Dimension(24, 24));
		GridBagConstraints gbc_btnToback = new GridBagConstraints();
		gbc_btnToback.insets = new Insets(0, 0, 5, 5);
		gbc_btnToback.gridx = 0;
		gbc_btnToback.gridy = 2;
		actionsPanel.add(btnToback, gbc_btnToback);
		
		JPanel logPanel = new JPanel();
		mainPanel.add(logPanel, BorderLayout.SOUTH);
		
		btnRunNextCommand = new JButton("Run next command");
		btnRunNextCommand.setVisible(false);
		btnRunNextCommand.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.runNextCommand();
			}
		});
		logPanel.add(btnRunNextCommand);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(600, 80));
		logPanel.add(scrollPane);
		
		JList<String> logList = new JList<String>();
		logList.setModel(dlm);
		
		scrollPane.setViewportView(logList);
		
		mainPanel.add(view, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		btnUndo = new JButton("",new ImageIcon("images/undo.png"));
		btnUndo.setEnabled(false);
		btnUndo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblDescription.setText("Undo");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDescription.setText(" ");
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnUndo.isEnabled())
					controller.undo();
			}
		});
		btnUndo.setPreferredSize(new Dimension(24, 24));
		menuBar.add(btnUndo);
		
		btnRedo = new JButton("",new ImageIcon("images/redo.png"));
		btnRedo.setEnabled(false);
		btnRedo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblDescription.setText("Redo");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDescription.setText(" ");
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnRedo.isEnabled())
				{
					controller.redo();
				}
			}
		});
		btnRedo.setPreferredSize(new Dimension(24, 24));
		menuBar.add(btnRedo);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as log");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.save("log");
			}
		});
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmSaveAsDrawing = new JMenuItem("Save as drawing");
		mntmSaveAsDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//ako je kliknuo u meniju Save as drawing u metodu saljemo "drawing"
				controller.save("drawing");
			}
		});
		mnFile.add(mntmSaveAsDrawing);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.open();
			}
		});
		mnFile.add(mntmOpen);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option=JOptionPane.showConfirmDialog(DrawFrame.this, "Exit application?","Exit",JOptionPane.YES_NO_OPTION);
				if(option==JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		ButtonGroup bgButtons = new ButtonGroup();
		bgButtons.add(tglbtnPoint);
		bgButtons.add(tglbtnLine);
		bgButtons.add(tglbtnRectangle);
		bgButtons.add(tglbtnSquare);
		bgButtons.add(tglbtnCircle);
		bgButtons.add(tglbtnHexagon);
		bgButtons.add(tglbtnSelect);
		
		btnTofront = new JButton("",new ImageIcon("images/tofront.png"));
		btnTofront.setEnabled(false);
		btnTofront.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblDescription.setText("To front");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDescription.setText(" ");
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnTofront.isEnabled())
				{
					controller.toFront();
				}
			}
		});
		btnTofront.setPreferredSize(new Dimension(24, 24));
		GridBagConstraints gbc_btnTofront = new GridBagConstraints();
		gbc_btnTofront.fill = GridBagConstraints.VERTICAL;
		gbc_btnTofront.insets = new Insets(0, 0, 5, 0);
		gbc_btnTofront.gridx = 1;
		gbc_btnTofront.gridy = 2;
		actionsPanel.add(btnTofront, gbc_btnTofront);
		
		btnBringtoback = new JButton("",new ImageIcon("images/bringtoback.png"));
		btnBringtoback.setEnabled(false);
		btnBringtoback.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblDescription.setText("Bring to back");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDescription.setText(" ");
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(btnBringtoback.isEnabled())
				{
					controller.bringToBack();
				}
			}
		});
		btnBringtoback.setPreferredSize(new Dimension(24, 24));
		GridBagConstraints gbc_btnBringtoback = new GridBagConstraints();
		gbc_btnBringtoback.insets = new Insets(0, 0, 5, 5);
		gbc_btnBringtoback.gridx = 0;
		gbc_btnBringtoback.gridy = 3;
		actionsPanel.add(btnBringtoback, gbc_btnBringtoback);
		
		btnBringtofront = new JButton("",new ImageIcon("images/bringtofront.png"));
		btnBringtofront.setEnabled(false);
		btnBringtofront.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblDescription.setText("Bring to front");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblDescription.setText(" ");
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnBringtofront.isEnabled())
				{
					controller.bringToFront();
				}
			}
		});
		btnBringtofront.setPreferredSize(new Dimension(24, 24));
		GridBagConstraints gbc_btnBringtofront = new GridBagConstraints();
		gbc_btnBringtofront.insets = new Insets(0, 0, 5, 0);
		gbc_btnBringtofront.gridx = 1;
		gbc_btnBringtofront.gridy = 3;
		actionsPanel.add(btnBringtofront, gbc_btnBringtofront);
		
		JPanel descriptionPanel = new JPanel();
		mainPanel.add(descriptionPanel, BorderLayout.NORTH);
		
		lblDescription = new JLabel(" ");
		lblDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblDescription.setText("Select tool");
			}
		});
		descriptionPanel.add(lblDescription);
	}
	
	public DrawView getView() {
		return view;	
	}
	
	public void setController(DrawController controller) {
		this.controller=controller;
	}

	public JToggleButton getTglbtnSelect() {
		return tglbtnSelect;
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}

	public JToggleButton getTglbtnSquare() {
		return tglbtnSquare;
	}

	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}
	
	public JButton getBtnRedo()
	{
		return btnRedo;
	}
	
	

	public JButton getBtnToback() {
		return btnToback;
	}

	public JButton getBtnTofront() {
		return btnTofront;
	}

	public JButton getBtnBringtoback() {
		return btnBringtoback;
	}

	public JButton getBtnBringtofront() {
		return btnBringtofront;
	}

	public DefaultListModel<String> getDlm() {
		return dlm;
	}

	public JButton getBtnRunNextCommand() {
		return btnRunNextCommand;
	}

	
	//kad god se neki Shape selektuje, automatski se poziva update metoda 
	@Override
	public void update() {
		
		if(controller.isModelReversed())//proverava da li je lista naopacke, jer kad selektujemo oblik u kontroleru obrnemo listu
			// da bi selektovao poslednje nacrtan, onda ovde moramo da je vratimo na normalno da ne bi radili sa obrnutom listom
		{
			Collections.reverse(view.getModel().getAll());//vracamo na normalan redsled
		}
		
		int numberOfSelected=controller.getNumberOfSelected();//proveravamo kolko je selektovano metodom iz kontrolera
		
		if(numberOfSelected>0)//ako je vise od 0 selektovano
		{
			//enablujemo sledece dugmice
			btnDelete.setEnabled(true);
			btnEdit.setEnabled(true);
			btnToback.setEnabled(true);
			btnTofront.setEnabled(true);
			btnBringtoback.setEnabled(true);
			btnBringtofront.setEnabled(true);
			
			if(view.getModel().getAll().indexOf(controller.getSelected())==view.getModel().getAll().size()-1)//da li je selektovan oblik poslednji u listi, tj na vrhu
			{
				//ako je selektovan najgornji element, ugasimo dugmice za slanje na vrh
				btnTofront.setEnabled(false);
				btnBringtofront.setEnabled(false);
			}else if(view.getModel().getAll().indexOf(controller.getSelected())==0)
			{
				//ako je selektovan najdonji element tj prvi, ugasimo dugmice za slanje na dno
				btnToback.setEnabled(false);
				btnBringtoback.setEnabled(false);
			}
			
			if(view.getModel().getAll().size()==1)
			{
				//ako ima samo 1 oblik na crtezu, moramo ugasiti dugmice za slanje na vrh i dno jer ima samo 1
				btnTofront.setEnabled(false);
				btnBringtofront.setEnabled(false);
				btnToback.setEnabled(false);
				btnBringtoback.setEnabled(false);
			}
			
			
			if(numberOfSelected>1)
			{
				//ako je selektovano vise od 1 elementa, gasimo dugmice za izmenu i slanje na dno/vrh jer ne moze odjednom 2,ostavljamo samo Delete dugme enablovano
				btnEdit.setEnabled(false);
				btnToback.setEnabled(false);
				btnTofront.setEnabled(false);
				btnBringtoback.setEnabled(false);
				btnBringtofront.setEnabled(false);
			}
		}
		else//ako je selektovano manje od 1 oblika,gasimo sve dugmice 
		{
			btnDelete.setEnabled(false);
			btnEdit.setEnabled(false);
			btnToback.setEnabled(false);
			btnTofront.setEnabled(false);
			btnBringtoback.setEnabled(false);
			btnBringtofront.setEnabled(false);
		}
		
		if(controller.isModelReversed())//vracamo na obrnuto opet posle svega da bi radila selekcija dobro
		{
			Collections.reverse(view.getModel().getAll());
		}
	}
	
	//metoda koja vraca ceo korisnicki interfejs kako je bio pri pokretanju programa??????????????
	public void setDefaultFrame() {
		btnEdit.setEnabled(false);
		btnDelete.setEnabled(false);
		btnToback.setEnabled(false);
		btnTofront.setEnabled(false);
		btnBringtoback.setEnabled(false);
		btnBringtofront.setEnabled(false);
		btnUndo.setEnabled(false);
		btnRedo.setEnabled(false);
		foreground = Color.BLACK;
		btnForeground.setBackground(foreground);
		background = Color.WHITE;
		btnBackground.setBackground(background);
		tglbtnSelect.setSelected(true);
		dlm.clear();//brisanje Default List modela u koji smestamo log stringove
		firstClick = null;
	}
}
