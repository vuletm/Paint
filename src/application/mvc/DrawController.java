package application.mvc;

import java.awt.Color;
//import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import application.command.CmdAdd;
import application.command.CmdBringToBack;
import application.command.CmdBringToFront;
import application.command.CmdDelete;
import application.command.CmdEditCircle;
import application.command.CmdEditHexagon;
import application.command.CmdEditLine;
import application.command.CmdEditPoint;
import application.command.CmdEditRectangle;
import application.command.CmdEditSquare;
import application.command.CmdToBack;
import application.command.CmdToFront;
import application.command.Command;
import application.dialogs.DialogAddCircle;
import application.dialogs.DialogAddHexagon;
import application.dialogs.DialogAddRectangle;
import application.dialogs.DialogAddSquare;
import application.dialogs.DialogEditCircle;
import application.dialogs.DialogEditHexagon;
import application.dialogs.DialogEditLine;
import application.dialogs.DialogEditPoint;
import application.dialogs.DialogEditRectangle;
import application.dialogs.DialogEditSquare;
import application.save.SaveDrawing;
import application.save.SaveLog;
import application.save.SaveManager;
import geometry.Circle;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Square;

public class DrawController implements Serializable {

	private DrawModel model;
	private DrawFrame frame;

	private ArrayList<Command> commandList = new ArrayList<Command>();// lista svih komandi izvrsenih u programu
	private int position;// pozicija trenutne aktivne komande, koristimo je za undo (vracanje unazad
							// pozicije) ili redo (povecavamo poziciju)

	private boolean reversed = false;// u ovoj promenljivi cuvamo da li je model naopacke ili ne

	// sluze za citanje u fajl?????
	private FileReader fr;
	private BufferedReader bf;

	public DrawController(DrawModel model, DrawFrame frame) {
		this.model = model;
		this.frame = frame;
	}

	public void select(MouseEvent click) {

		boolean successSelect = false;// u ovoj promenljivi cuvamo da li je uspeo da selektuje oblik bilo koji

		Collections.reverse(model.getAll());// okrecemo listu da bi selektovao poslednje nacrtan, tj oblik na vrhu
		reversed = true;

		for (Shape s : model.getAll()) {// prolazimo kroz sve oblike
			s.setObserver(frame);// frejm posmatra svaki oblik
			if (s.contains(click.getX(), click.getY())) {// ako neki od oblika sadrzi klik
				s.setSelected(true);// stavimo da je selektovan, trigerujemo observer??????
				successSelect = true;
				break;// iskacemo iz petlje jer zelimo da selektujemo samo 1 oblik sa jednim klikom
			}
		}
		Collections.reverse(model.getAll());// vracamo listu na normalno da bi normalno funnkcionisao program
		reversed = false;

		if (successSelect == false)// deselektovanje
		{
			for (Shape s : model.getAll()) {
				s.setSelected(false);
			}
		}
	}

	public void draw(MouseEvent click, MouseEvent firstClick, Color foreground, Color background) {
		if (frame.getTglbtnPoint().isSelected()) {// proveravamo koji je toggle button selektovan da bi znao sta da crta
			executeCommand(new CmdAdd(new Point(click.getX(), click.getY(), foreground), model));// pozivamo
																									// executeCommand
																									// metodu koja prima
																									// neku Command
		} else if (frame.getTglbtnLine().isSelected()) {
			Point start = new Point(firstClick.getX(), firstClick.getY());
			Point end = new Point(click.getX(), click.getY());

			executeCommand(new CmdAdd(new Line(start, end, foreground), model));
		} else if (frame.getTglbtnRectangle().isSelected()) {
			// pravimo objekat dijaloga, da bi se napravio dijalog
			DialogAddRectangle dialogAddRectangle = new DialogAddRectangle();
			dialogAddRectangle.setVisible(true);

			Point upperLeft = new Point(click.getX(), click.getY());// pravimo gore levo tacku od klika

			// pravimo visinu i sirinu iz dijaloga
			int rectangleWidth = dialogAddRectangle.getRectangleWidth();
			int rectangleHeight = dialogAddRectangle.getRectangleHeight();

			if (rectangleWidth <= 400 && rectangleWidth >= 1 && rectangleHeight <= 400 && rectangleHeight >= 1)
				executeCommand(new CmdAdd(
						new Rectangle(upperLeft, rectangleHeight, rectangleWidth, foreground, background), model));
		} else if (frame.getTglbtnSquare().isSelected()) {
			DialogAddSquare dialogAddSquare = new DialogAddSquare();
			dialogAddSquare.setVisible(true);

			Point upperLeft = new Point(click.getX(), click.getY());
			int side = dialogAddSquare.getSide();

			if (side <= 400 && side >= 1)
				executeCommand(new CmdAdd(new Square(upperLeft, side, foreground, background), model));
		} else if (frame.getTglbtnCircle().isSelected()) {
			DialogAddCircle dialogAddCircle = new DialogAddCircle();
			dialogAddCircle.setVisible(true);

			Point center = new Point(click.getX(), click.getY());
			int radius = dialogAddCircle.getRadius();

			if (radius <= 400 && radius >= 1)
				executeCommand(new CmdAdd(new Circle(center, radius, foreground, background), model));
		} else if (frame.getTglbtnHexagon().isSelected()) {
			DialogAddHexagon dialogAddHexagon = new DialogAddHexagon();
			dialogAddHexagon.setVisible(true);

			int radius = dialogAddHexagon.getRadius();

			if (radius <= 400 && radius >= 1)
				executeCommand(new CmdAdd(
						new HexagonAdapter(click.getX(), click.getY(), radius, foreground, background), model));
		}
	}

	public void edit() {
		/*
		 * Editovanje funkcionise tako sto kad kliknemo na edit dugme prvo skontamo koji
		 * je oblik selektovan, ako je tacka pravimo CmdEditPoint itd..
		 */
		for (Shape s : model.getAll()) {// proveravamo koji je oblik selektovan
			if (s.isSelected()) {
				if (s instanceof Point) {// ako je selektovan oblik instanca Point-a
					DialogEditPoint dialogEditPoint = new DialogEditPoint((Point) s);// pravimo dijalog za izmenu tacke
					dialogEditPoint.setVisible(true);

					if (dialogEditPoint.getX() > 0 && dialogEditPoint.getX() < 851 && dialogEditPoint.getY() > 0
							&& dialogEditPoint.getY() < 531)// provera unetih vrednosti,ako je sve ok izvrsavamo Command
															// {
						executeCommand(new CmdEditPoint((Point) s, new Point(dialogEditPoint.getX(),
								dialogEditPoint.getY(), dialogEditPoint.getForeground())));
				} else if (s instanceof Line) {
					DialogEditLine dialogEditLine = new DialogEditLine((Line) s);
					dialogEditLine.setVisible(true);
					if (dialogEditLine.getStartX() > 0 && dialogEditLine.getStartX() < 851
							&& dialogEditLine.getStartY() > 0 && dialogEditLine.getStartY() < 531
							&& dialogEditLine.getEndX() > 0 && dialogEditLine.getEndX() < 851
							&& dialogEditLine.getEndY() > 0 && dialogEditLine.getEndY() < 531) {
						executeCommand(new CmdEditLine((Line) s,
								new Line(new Point(dialogEditLine.getStartX(), dialogEditLine.getStartY()),
										new Point(dialogEditLine.getEndX(), dialogEditLine.getEndY()),
										dialogEditLine.getForeground())));
					}

				} else if (s instanceof Rectangle) {
					DialogEditRectangle dialogEditRectangle = new DialogEditRectangle((Rectangle) s);
					dialogEditRectangle.setVisible(true);
					if (dialogEditRectangle.getStartX() > 0 && dialogEditRectangle.getStartX() < 851
							&& dialogEditRectangle.getStartY() > 0 && dialogEditRectangle.getStartY() < 531
							&& dialogEditRectangle.getHeightR() > 0 && dialogEditRectangle.getHeightR() < 400
							&& dialogEditRectangle.getWidthR() > 0 && dialogEditRectangle.getWidthR() < 400) {
						executeCommand(new CmdEditRectangle((Rectangle) s,
								new Rectangle(
										new Point(dialogEditRectangle.getStartX(), dialogEditRectangle.getStartY()),
										dialogEditRectangle.getHeightR(), dialogEditRectangle.getWidthR(),
										dialogEditRectangle.getForeground(), dialogEditRectangle.getBackground())));
					}
				} else if (s instanceof Square) {
					DialogEditSquare dialogEditSquare = new DialogEditSquare((Square) s);
					dialogEditSquare.setVisible(true);
					if (dialogEditSquare.getStartX() > 0 && dialogEditSquare.getStartX() < 851
							&& dialogEditSquare.getStartY() > 0 && dialogEditSquare.getStartY() < 531
							&& dialogEditSquare.getSide() > 0 && dialogEditSquare.getSide() < 400) {
						executeCommand(new CmdEditSquare((Square) s,
								new Square(new Point(dialogEditSquare.getStartX(), dialogEditSquare.getStartY()),
										dialogEditSquare.getSide(), dialogEditSquare.getForeground(),
										dialogEditSquare.getBackground())));
					}
				} else if (s instanceof Circle) {
					DialogEditCircle dialogEditCircle = new DialogEditCircle((Circle) s);
					dialogEditCircle.setVisible(true);
					if (dialogEditCircle.getCenterX() > 0 && dialogEditCircle.getCenterX() < 851
							&& dialogEditCircle.getCenterY() > 0 && dialogEditCircle.getCenterY() < 531
							&& dialogEditCircle.getRadius() > 0 && dialogEditCircle.getRadius() < 400) {
						executeCommand(new CmdEditCircle((Circle) s,
								new Circle(new Point(dialogEditCircle.getCenterX(), dialogEditCircle.getCenterY()),
										dialogEditCircle.getRadius(), dialogEditCircle.getForeground(),
										dialogEditCircle.getBackground())));
					}
				} else if (s instanceof HexagonAdapter) {
					DialogEditHexagon dialogEditHexagon = new DialogEditHexagon((HexagonAdapter) s);
					dialogEditHexagon.setVisible(true);
					if (dialogEditHexagon.getCenterX() > 0 && dialogEditHexagon.getCenterX() < 851
							&& dialogEditHexagon.getCenterY() > 0 && dialogEditHexagon.getCenterY() < 531
							&& dialogEditHexagon.getRadius() > 0 && dialogEditHexagon.getRadius() < 400) {
						executeCommand(new CmdEditHexagon((HexagonAdapter) s,
								new HexagonAdapter(dialogEditHexagon.getCenterX(), dialogEditHexagon.getCenterY(),
										dialogEditHexagon.getRadius(), dialogEditHexagon.getForeground(),
										dialogEditHexagon.getBackground())));
					}
				}
			}

		}
	}

	public void delete() {
		ArrayList<Shape> shapesForDelete = new ArrayList<Shape>();// selektovani oblici za brisanje

		for (Shape s : model.getAll()) {
			if (s.isSelected())// prolazimo kroz ceo model, ako je neki oblik selektovan
				shapesForDelete.add(s);// ubacujemo ga u listu za brisanje
		}

		executeCommand(new CmdDelete(shapesForDelete, model));// saljemo u komandu za brisanje listu svih selektovanih
																// oblika

	}

	public void executeCommand(Command c) {
		// svaki put kad pozivamo izvrsenje komande radimo sledece:

		c.execute();// prvo izvrsimo komandu
		commandList.add(c);// dodamo komandu u listu svih, da bi posle mogli da prolazimo kroz nju,
							// undoujemo redoujemo i slicno
		position = commandList.size() - 1;// stavljamo da je trenutna pozicija aktivne komande poslednja u listi svih
											// komandi????
		frame.getBtnUndo().setEnabled(true);// svaki put kad izvrsimo komandu, enablujemo dugme za undo

		frame.getDlm().addElement(c.toString());// dodavanje tekst komande u log dole
		frame.update();// uradimo updateovanje dugmica (enablovanje/disablovanje)
	}

	public void undo() {
		// undo unexecutuje trenutno aktivnu komandu i vraca index za 1 da bi sledeci
		// put kad uradis undo izvrsio komandu pre toga

		commandList.get(position).unexecute();// unexecutovanje trenutno aktivne komande

		frame.getDlm().addElement("undo:" + commandList.get(position).toString());// dodavanje teksta undo-a u log

		position--; // vracanje pozicije unazad

		if (position < 0) {
			frame.getBtnUndo().setEnabled(false);// ako je pozicija komande nulta u listi, ne moze vise da undouje zato
													// gasimo dugme
		}

		frame.getBtnRedo().setEnabled(true);// svaki put kad uradi undo, upalimo redo dugme
		frame.update();

	}

	public void redo() {
		// izvrsavanje komande za 1 poziciju vise od trenutne

		position++;// povecavamo poziciju trenutne komande za 1 da bi se izvrsila ona sledeca ????
		commandList.get(position).execute();// izvrsavamo komandu

		frame.getDlm().addElement("redo:" + commandList.get(position).toString());// dodavanje teksta redo-a u log

		if (position == commandList.size() - 1) {// ako je pozicija trenutne komande poslednja u listi svih komandi, ne
													// moze vise da izvrsava unapred
			frame.getBtnRedo().setEnabled(false);
		}

		frame.getBtnUndo().setEnabled(true);
		frame.update();// zbog enablovanja dugmica
	}

	public int getNumberOfSelected() {
		// metoda koja vraca broj selektovanih oblika
		int numberOfSelected = 0;

		for (Shape s : model.getAll()) {
			if (s.isSelected() == true)
				numberOfSelected++;
		}
		return numberOfSelected;
	}

	public void toBack() {
		// metoda koja vraca selektovan oblik jedno mesto na dole
		executeCommand(new CmdToBack(model, getSelected()));

		// kad god uradi vracanje unazad,enabluj dugmice za slanje na vrh
		frame.getBtnTofront().setEnabled(true);
		frame.getBtnBringtofront().setEnabled(true);

		if (model.getAll().indexOf(getSelected()) == 0) {// ako je selektovani oblik skroz na dnu, ne moze vise da salje
															// unazad
			frame.getBtnToback().setEnabled(false);
			frame.getBtnBringtoback().setEnabled(false);
		}
	}

	public void toFront() {
		// sve kontra od gore
		executeCommand(new CmdToFront(model, getSelected()));

		frame.getBtnToback().setEnabled(true);
		frame.getBtnBringtoback().setEnabled(true);

		if (model.getAll().indexOf(getSelected()) == model.getAll().size() - 1) {
			frame.getBtnTofront().setEnabled(false);
			frame.getBtnBringtofront().setEnabled(false);
		}

	}

	public Shape getSelected() {
		// metoda koja vraca selektovan oblik
		for (Shape s : model.getAll()) {
			if (s.isSelected()) {
				return s;
			}
		}
		return null;
	}

	public void bringToBack() {
		executeCommand(new CmdBringToBack(model, getSelected()));

		frame.getBtnToback().setEnabled(false);
		frame.getBtnBringtoback().setEnabled(false);

		frame.getBtnTofront().setEnabled(true);
		frame.getBtnBringtofront().setEnabled(true);

	}

	public void bringToFront() {
		executeCommand(new CmdBringToFront(model, getSelected()));

		frame.getBtnTofront().setEnabled(false);
		frame.getBtnBringtofront().setEnabled(false);

		frame.getBtnToback().setEnabled(true);
		frame.getBtnBringtoback().setEnabled(true);
	}

	public boolean isModelReversed() {
		// metoda koja vraca da li je lista naopacke ili nije
		return reversed;
	}

	public void save(String option) {
		// Strategy obrazac

		// ako je kliknuo u meniju Save as drawing, koristicemo strategiju za cuvanje
		// kompletnog crteaz
		if (option.equals("drawing")) {
			SaveManager sm = new SaveManager(new SaveDrawing(model));// pravimo strategiju za cuvanje kompletnog crteza
			sm.save();
		} else if (option.equals("log")) {
			SaveManager sm = new SaveManager(new SaveLog(frame.getDlm()));
			sm.save();
		}

	}

	public void open() {
		// metoda koja se poziva kad klikne u meniju Open
		try {

			JFileChooser fileChooser = new JFileChooser();

			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {// ako je kliknuo Open dugme uspesno
				File f = (fileChooser.getSelectedFile());// uzmemo koji je fajl izabrao i smestimo ga u 'File f'

				if (f.getAbsolutePath().endsWith(".txt")) {// ako se zavrsava sa .txt znamo da je izabrao tekstualni
															// fajl loga a ne crtez

					model.getAll().clear();// ako otvara novi fajl, brisemo sve iz modela
					commandList.clear();// i sve komande
					frame.setDefaultFrame(); // i vracamo frame na pocetni izgled

					fr = new FileReader(f);// ????
					bf = new BufferedReader(fr);// ??????

					frame.getBtnRunNextCommand().setVisible(true);// enablujemo ono dugme pored loga da bi mogao komandu
																	// po komandu da ucitava

				} else {// ako nije izabrao fajl koji se zavrsava sa .txt znamo da je izabrao open
						// crteza//?????????????
					FileInputStream fis = new FileInputStream(f);// sluzi za bilo kakvo ucitavanje u fajl
					ObjectInputStream ois = new ObjectInputStream(fis);// sluzi za ucitavanje objekata

					ArrayList<Shape> loadedList = (ArrayList<Shape>) ois.readObject();// sacuvani objekat iz fajla,
																						// ucitavamo ovde

					model.setList(loadedList);// stavimo da je ucitana lista nas model, zato na ekranu bude ono sto smo
												// sacuvali, jer panel prikazuje model

					commandList.clear();// brisanje svih komandi iz liste

					frame.setDefaultFrame(); // vraca izgled programa na pocetni
					frame.update(); // update izgled frame-a

					ois.close();
					fis.close();
				}
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error while opening file", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void runNextCommand() {
		// metoda koje se pokrece kad god klikne ono dugme pored loga, tj ucitava jednu
		// liniju iz loga

		String line;// oznacava liniju u fajlu, npr
					// 'add:Line:start[671,107],end[370,204],foreground[0.0.0]'
		try {
			if ((line = bf.readLine()) != null) { // ako nije kraj fajla nastavi

				String[] lineElements = line.split(":"); // delimo liniju iz loga po dvotackama da bi znali dal je npr
															// na prvom mestu add ili delete..

				String valuesLine = lineElements[2].replaceAll("[^0-9,.]", "");// sluzi da obrise sve osim brojeva
																				// 0-9,zareza i tacke

				String[] values = valuesLine.split(",");// liniju bez slova zagrada i sranja splitujemo po zarezu da bi
														// izvukli vrednosti, npr 22 za x,33 za y

				if (lineElements[0].equals("add")) {// ako je na nultom mestu add
					if (lineElements[1].equals("Point")) {// a na prvom point, crtamo tacku
						Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
								parseColor(values[2]));// pravimo tacku od ucitanih vrednosti
						executeCommand(new CmdAdd(p, model));// izvrsavamo komandu za dodavanje sa ucitanom tackom iz
																// fajla
					} else if (lineElements[1].equals("Line")) {
						Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
						Point end = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));

						Line l = new Line(start, end, parseColor(values[4]));

						executeCommand(new CmdAdd(l, model));
					} else if (lineElements[1].equals("Rectangle")) {
						Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
						int height = Integer.parseInt(values[2]);
						int width = Integer.parseInt(values[3]);

						Rectangle r = new Rectangle(upperLeft, height, width, parseColor(values[4]),
								parseColor(values[5]));
						executeCommand(new CmdAdd(r, model));
					} else if (lineElements[1].equals("Square")) {

						Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
						int side = Integer.parseInt(values[2]);

						executeCommand(new CmdAdd(new Square(start, side, parseColor(values[3]), parseColor(values[4])),
								model));
					} else if (lineElements[1].equals("Circle")) {

						Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
						int radius = Integer.parseInt(values[2]);

						executeCommand(new CmdAdd(
								new Circle(center, radius, parseColor(values[3]), parseColor(values[4])), model));
					} else if (lineElements[1].equals("Hexagon")) {
						int x = Integer.parseInt(values[0]);
						int y = Integer.parseInt(values[1]);
						int r = Integer.parseInt(values[2]);
						executeCommand(new CmdAdd(
								new HexagonAdapter(x, y, r, parseColor(values[3]), parseColor(values[4])), model));
					}

				} else if (lineElements[0].equals("edit")) {
					// edit:Point:[426,184],foreground[0.0.0],to:Point:[1,1],foreground[0.0.0]

					// pravimo opet parsovanje zato sto imamo drugi deo
					String newValuesLine = lineElements[4].replaceAll("[^0-9,.]", "");
					String[] newValues = newValuesLine.split(",");

					if (lineElements[1].equals("Point")) {
						// ne editujemo bas ovu tacku ispod vec moramo naci tacku u modelu koja odgovara
						// ovoj
						Point oldState = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
								parseColor(values[2]));
						Point newState = new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1]),
								parseColor(newValues[2]));// proveri je l dobro ovo sto pricam

						for (Shape s : model.getAll()) {// trazimo u modelu koja tacka nam odgovara ucitanoj iz fajla
							if (s.equals(oldState)) {
								executeCommand(new CmdEditPoint((Point) s, newState));// radimo izmenu tacke
								break;
							}
						}
					} else if (lineElements[1].equals("Line")) {

						Line oldState = new Line(new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
								new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3])),
								parseColor(values[4]));
						Line newState = new Line(
								new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
								new Point(Integer.parseInt(newValues[2]), Integer.parseInt(newValues[3])),
								parseColor(newValues[4]));

						for (Shape s : model.getAll()) {
							if (s.equals(oldState)) {
								executeCommand(new CmdEditLine((Line) s, newState));
								break;
							}
						}
					} else if (lineElements[1].equals("Square")) {

						Square oldState = new Square(
								new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
								Integer.parseInt(values[2]), parseColor(values[3]), parseColor(values[4]));
						Square newState = new Square(
								new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
								Integer.parseInt(newValues[2]), parseColor(newValues[3]), parseColor(newValues[4]));

						for (Shape s : model.getAll()) {
							if (s.equals(oldState)) {
								executeCommand(new CmdEditSquare((Square) s, newState));
								break;
							}
						}
					} else if (lineElements[1].equals("Rectangle")) {
						Rectangle oldState = new Rectangle(
								new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
								Integer.parseInt(values[2]), Integer.parseInt(values[3]), parseColor(values[4]),
								parseColor(values[5]));
						Rectangle newState = new Rectangle(
								new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
								Integer.parseInt(newValues[2]), Integer.parseInt(newValues[3]),
								parseColor(newValues[4]), parseColor(newValues[5]));

						for (Shape s : model.getAll()) {
							if (s.equals(oldState)) {
								executeCommand(new CmdEditRectangle((Rectangle) s, newState));
								break;
							}
						}
					} else if (lineElements[1].equals("Circle")) {

						Circle oldState = new Circle(
								new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
								Integer.parseInt(values[2]), parseColor(values[3]), parseColor(values[4]));
						Circle newState = new Circle(
								new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
								Integer.parseInt(newValues[2]), parseColor(newValues[3]), parseColor(newValues[4]));

						for (Shape s : model.getAll()) {
							if (s.equals(oldState)) {
								executeCommand(new CmdEditCircle((Circle) s, newState));
								break;
							}
						}
					} else if (lineElements[1].equals("Hexagon")) {

						HexagonAdapter oldState = new HexagonAdapter(Integer.parseInt(values[0]),
								Integer.parseInt(values[1]), Integer.parseInt(values[2]), parseColor(values[3]),
								parseColor(newValues[4]));
						HexagonAdapter newState = new HexagonAdapter(Integer.parseInt(newValues[0]),
								Integer.parseInt(newValues[1]), Integer.parseInt(newValues[2]),
								parseColor(newValues[3]), parseColor(newValues[4]));

						for (Shape s : model.getAll()) {

							if (s.equals(oldState)) {
								executeCommand(new CmdEditHexagon((HexagonAdapter) s, newState));
								break;
							}
						}
					}
				} else if (lineElements[0].equals("delete")) {
					// delete:Point:[464,119],foreground[0.0.0]:Line:start[392,265],end[470,199],foreground[0.0.0]:

					// deletovanje radimo tako sto prodjemo kroz liniju podeljenu na dvotacke i
					// kad god naletimo na neku od reci('Point','Line'..)znamo da su vrednosti toga
					// na sledecem indeksu

					// lista za brisanje
					ArrayList<Shape> shapesToDelete = new ArrayList<Shape>();

					for (int i = 0; i < lineElements.length; i++) {
						if (lineElements[i].equals("Point")) {// ako u ucitanoj liniji naletimo na rec Point, pravimo
																// tacku od vrednosti koje su na sledecem indeksu
							String shapeValues = lineElements[i + 1].replaceAll("[^0-9,.]", "");
							String[] sValues = shapeValues.split(",");// je l dobro to sto pricam

							Point p = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]), // parsujemo
																											// vrednosti
																											// na +1
																											// indeksu
									parseColor(sValues[2]));
							shapesToDelete.add(p);// dodavanje u listu za brisanje, koju cemo na kraju slati u komandu
													// za brisanje
						} else if (lineElements[i].equals("Line")) {
							String shapeValues = lineElements[i + 1].replaceAll("[^0-9,.]", "");
							String[] sValues = shapeValues.split(",");

							Point start = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]));
							Point end = new Point(Integer.parseInt(sValues[2]), Integer.parseInt(sValues[3]));
							Color foreground = parseColor(sValues[4]);

							Line l = new Line(start, end, foreground);
							shapesToDelete.add(l);
						} else if (lineElements[i].equals("Square")) {
							String shapeValues = lineElements[i + 1].replaceAll("[^0-9,.]", "");
							String[] sValues = shapeValues.split(",");

							Point start = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]));
							int side = Integer.parseInt(sValues[2]);
							Color foreground = parseColor(sValues[3]);
							Color background = parseColor(sValues[4]);

							Square s = new Square(start, side, foreground, background);
							shapesToDelete.add(s);
						} else if (lineElements[i].equals("Rectangle")) {
							String shapeValues = lineElements[i + 1].replaceAll("[^0-9,.]", "");
							String[] sValues = shapeValues.split(",");

							Point start = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]));
							int height = Integer.parseInt(sValues[2]);
							int width = Integer.parseInt(sValues[3]);
							Color foreground = parseColor(sValues[4]);
							Color background = parseColor(sValues[5]);

							Rectangle r = new Rectangle(start, height, width, foreground, background);
							shapesToDelete.add(r);
						} else if (lineElements[i].equals("Circle")) {
							String shapeValues = lineElements[i + 1].replaceAll("[^0-9,.]", "");
							String[] sValues = shapeValues.split(",");

							Point center = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]));
							int radius = Integer.parseInt(sValues[2]);
							Color foreground = parseColor(sValues[3]);
							Color background = parseColor(sValues[4]);

							Circle c = new Circle(center, radius, foreground, background);
							shapesToDelete.add(c);
						} else if (lineElements[i].equals("Hexagon")) {
							String shapeValues = lineElements[i + 1].replaceAll("[^0-9,.]", "");
							String[] sValues = shapeValues.split(",");

							int radius = Integer.parseInt(sValues[2]);
							Color foreground = parseColor(sValues[3]);
							Color background = parseColor(sValues[4]);

							HexagonAdapter h = new HexagonAdapter(Integer.parseInt(sValues[0]),
									Integer.parseInt(sValues[1]), Integer.parseInt(sValues[2]), foreground, background);
							shapesToDelete.add(h);
						}
					}

					executeCommand(new CmdDelete(shapesToDelete, model));// brisanje

				} else if ((lineElements[0].equals("toback")) || (lineElements[0].equals("tofront"))
						|| (lineElements[0].equals("bringtoback")) || (lineElements[0].equals("bringtofront"))) {

					if (lineElements[1].equals("Point")) {// ako je na nultom mestu nesto od ovoga gore a na prvom mestu
															// tacka
						Color foreground = parseColor(values[2]);
						Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), foreground);
						// parsujemo tu tacku i nadjemo je u modelu,pa nju saljemo gde treba na vrh,dno
						// itd..

						for (Shape s : model.getAll()) {
							if (s.equals(p)) {
								if (lineElements[0].equals("toback"))
									executeCommand(new CmdToBack(model, (Point) s));
								else if (lineElements[0].equals("tofront"))
									executeCommand(new CmdToFront(model, (Point) s));
								else if (lineElements[0].equals("bringtoback"))
									executeCommand(new CmdBringToBack(model, (Point) s));
								else if (lineElements[0].equals("bringtofront"))
									executeCommand(new CmdBringToFront(model, (Point) s));
								break;
							}
						}
					} else if (lineElements[1].equals("Line")) {

						Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
						Point end = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
						Color outline = parseColor(values[4]);

						Line l = new Line(start, end, outline);

						for (Shape s : model.getAll()) {
							if (s.equals(l)) {
								if (lineElements[0].equals("toback"))
									executeCommand(new CmdToBack(model, (Line) s));
								else if (lineElements[0].equals("tofront"))
									executeCommand(new CmdToFront(model, (Line) s));
								else if (lineElements[0].equals("bringtoback"))
									executeCommand(new CmdBringToBack(model, (Line) s));
								else if (lineElements[0].equals("bringtofront"))
									executeCommand(new CmdBringToFront(model, (Line) s));
								break;
							}
						}
					} else if (lineElements[1].equals("Square")) {

						Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
						int side = Integer.parseInt(values[2]);
						Color outline = parseColor(values[3]);
						Color inside = parseColor(values[4]);

						Square sq = new Square(start, side, outline, inside);

						for (Shape s : model.getAll()) {
							if (s.equals(sq)) {
								if (lineElements[0].equals("toback"))
									executeCommand(new CmdToBack(model, (Square) s));
								else if (lineElements[0].equals("tofront"))
									executeCommand(new CmdToFront(model, (Square) s));
								else if (lineElements[0].equals("bringtoback"))
									executeCommand(new CmdBringToBack(model, (Square) s));
								else if (lineElements[0].equals("bringtofront"))
									executeCommand(new CmdBringToFront(model, (Square) s));
								break;
							}
						}
					} else if (lineElements[1].equals("Rectangle")) {

						Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
						int height = Integer.parseInt(values[2]);
						int width = Integer.parseInt(values[3]);
						Color outline = parseColor(values[4]);
						Color inside = parseColor(values[5]);

						Rectangle r = new Rectangle(start, height, width, outline, inside);

						for (Shape s : model.getAll()) {
							if (s.equals(r)) {
								if (lineElements[0].equals("toback"))
									executeCommand(new CmdToBack(model, (Rectangle) s));
								else if (lineElements[0].equals("tofront"))
									executeCommand(new CmdToFront(model, (Rectangle) s));
								else if (lineElements[0].equals("bringtoback"))
									executeCommand(new CmdBringToBack(model, (Rectangle) s));
								else if (lineElements[0].equals("bringtofront"))
									executeCommand(new CmdBringToFront(model, (Rectangle) s));
								break;
							}
						}
					} else if (lineElements[1].equals("Circle")) {

						Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
						int radius = Integer.parseInt(values[2]);
						Color outline = parseColor(values[3]);
						Color inside = parseColor(values[4]);

						Circle c = new Circle(center, radius, outline, inside);

						for (Shape s : model.getAll()) {
							if (s.equals(c)) {
								if (lineElements[0].equals("toback"))
									executeCommand(new CmdToBack(model, (Circle) s));
								else if (lineElements[0].equals("tofront"))
									executeCommand(new CmdToFront(model, (Circle) s));
								else if (lineElements[0].equals("bringtoback"))
									executeCommand(new CmdBringToBack(model, (Circle) s));
								else if (lineElements[0].equals("bringtofront"))
									executeCommand(new CmdBringToFront(model, (Circle) s));
								break;
							}
						}
					} else if (lineElements[1].equals("Hexagon")) {

						int x = Integer.parseInt(values[0]);
						int y = Integer.parseInt(values[1]);
						int r = Integer.parseInt(values[2]);
						HexagonAdapter h = new HexagonAdapter(x, y, r, parseColor(values[3]), parseColor(values[4]));

						for (Shape s : model.getAll()) {
							if (s.equals(h)) {
								if (lineElements[0].equals("toback"))
									executeCommand(new CmdToBack(model, (HexagonAdapter) s));
								else if (lineElements[0].equals("tofront"))
									executeCommand(new CmdToFront(model, (HexagonAdapter) s));
								else if (lineElements[0].equals("bringtoback"))
									executeCommand(new CmdBringToBack(model, (HexagonAdapter) s));
								else if (lineElements[0].equals("bringtofront"))
									executeCommand(new CmdBringToFront(model, (HexagonAdapter) s));
								break;
							}
						}
					}
				} else if (lineElements[0].equals("undo") || lineElements[0].equals("redo")) {
					// undo:add:Point:[313,146],foreground[0.0.0]

					String command = line.substring(5);// izlvacimo ceo tekst komande posle 'undo:' da bi nasli takvu
														// komandu u listi koandi

					for (Command c : commandList) {// od svih komandi, da li neka odgovara ovoj ucitanoj sto ide posle
													// reci 'undo:'
						if (c.toString().equals(command)) {
							if (lineElements[0].equals("undo"))
								undo();
							else
								redo();
						}
					}
				}

			} else {
				frame.getBtnRunNextCommand().setVisible(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Color parseColor(String text) {// prima tekst u formu 0.0.0 i od tog teksta pravi Color
		String[] rgb = text.split("\\.");
		int red = Integer.parseInt(rgb[0]);
		int green = Integer.parseInt(rgb[1]);
		int blue = Integer.parseInt(rgb[2]);

		Color c = new Color(red, green, blue);
		return c;
	}

}
