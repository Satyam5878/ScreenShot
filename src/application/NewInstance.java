package application;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class NewInstance {
	// Variables declaration

	private Stage stage;
	private Scene scene;
	private BorderPane rootPane;
	private SplitPane centrePane;
	// Menu Bar Variables
	private MenuBar menuBar;
	// Menu
	private Menu newInstanceMenu;
	// Menu Items
	private MenuItem newInstanceMenuItem;

	// Centre Pane-> Split Pane
	private VBox leftPane,rightPane;
	private HBox hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7;
	private TextField locationTxtField, formateTxtField, nameTxtField;
	private Label locationLbl, formateLbl, nameLbl, msgLbl;
	private Button browseBtn, saveBtn, takeScreenShot;
	private DirectoryChooser dirChooser;
	private FileChooser fileChooser;
	private String locationPathString, formateString, nameString;

	private AtomicInteger uniqueId = new AtomicInteger(100);
	private BufferedImage screenShotImage = null;

	// Constructor to create the new application
	public NewInstance(Stage stage) {
		// Storing the main stage.
		this.stage = stage;

		rootPane = new BorderPane();
		// rootPane.setSpacing(10);

		scene = new Scene(rootPane, 600, 300);

		populateScene();
		this.stage.setTitle("Screen Shot Application");
		this.stage.setScene(scene);
		this.stage.setResizable(false);
		this.stage.show();

	}

	private void rootPaneTop() {
		// add menu bar
		menuBar = new MenuBar();
		newInstanceMenu = new Menu("New Instance");
		newInstanceMenuItem = new MenuItem("New Instance");
		newInstanceMenuItem.setOnAction(e -> {
			new NewInstance(new Stage());
		});
		newInstanceMenu.getItems().add(newInstanceMenuItem);
		menuBar.getMenus().add(newInstanceMenu);
		rootPane.setTop(menuBar);
	}

	private void rootPaneBottom() {
		rootPane.setBottom(null);
	}

	private void rootPaneLeft() {
		rootPane.setLeft(null);
	}

	private void rootPaneRight() {
		rootPane.setRight(null);
	}
	private void rootPaneCentre() {
		// add split pane
		centrePane = new SplitPane();
		populateLeftPane();
		populateRightPane();
		rootPane.setCenter(centrePane);
	}
	private void populateLeftPane(){
		leftPane = new VBox();// hbox1 Start
		hbox1 = new HBox();
		hbox1.setSpacing(10);
		locationLbl = new Label("Location :");

		hbox1.getChildren().addAll(locationLbl);
		leftPane.getChildren().add(hbox1);
		// hbox1 End

		// hbox2 Start
		hbox2 = new HBox();
		hbox2.setSpacing(10);

		locationTxtField = new TextField();
		locationTxtField.setEditable(false);
		locationTxtField.prefWidthProperty().bind(
				scene.widthProperty().multiply(.65));

		browseBtn = new Button("_Browse");
		browseBtn.prefWidthProperty().bind(scene.widthProperty().multiply(.30));
		browseBtn.setOnAction(e -> {
			dirChooser = new DirectoryChooser();
			dirChooser.setTitle("select Location");
			dirChooser.setInitialDirectory(new File("C:\\"));
			File file = dirChooser.showDialog(this.stage);
			if (file != null) {

				// System.out.println(file.toPath());
				// locationTxtField.setText()
				locationPathString = file.toPath().toString()
						.replace("\\", "\\\\");

				// System.out.println(locationPathString);
				locationTxtField.setText(locationPathString);
			} else {
				// System.out.println("file is NULL");
			}
			// String str = "amana";
			// str.r

		});

		hbox2.getChildren().addAll(locationTxtField, browseBtn);
		leftPane.getChildren().add(hbox2);
		// hbox2 End
		// hbox3 Start
		hbox3 = new HBox();
		hbox3.setSpacing(10);
		formateLbl = new Label("Formate(png,jpeg , etc.) : ");

		hbox3.getChildren().add(formateLbl);
		leftPane.getChildren().add(hbox3);
		// hbox3 End

		// hbox4 Start
		hbox4 = new HBox();
		hbox4.setSpacing(10);

		formateTxtField = new TextField();
		formateTxtField.prefWidthProperty().bind(
				scene.widthProperty().multiply(.60));
		// TODO add changeListener to this text Field

		hbox4.getChildren().add(formateTxtField);
		leftPane.getChildren().add(hbox4);
		// hbox4 End

		// hbox5 Start
		hbox5 = new HBox();
		hbox5.setSpacing(10);

		nameLbl = new Label("Name :");

		hbox5.getChildren().addAll(nameLbl);
		leftPane.getChildren().add(hbox5);

		// hbox5 End

		// hbox6 Start
		hbox6 = new HBox();
		hbox6.setSpacing(10);

		nameTxtField = new TextField();
		nameTxtField.prefWidthProperty().bind(
				scene.widthProperty().multiply(.60));
		// TODO --> add change listener

		hbox6.getChildren().addAll(nameTxtField);
		leftPane.getChildren().add(hbox6);

		// hbox6 End
		// hbox7 Start
		hbox7 = new HBox();
		hbox7.setSpacing(10);
		hbox7.setAlignment(Pos.CENTER_RIGHT);

		msgLbl = new Label("Message Box");
		msgLbl.setStyle("-fx-border-style:solid");
		takeScreenShot = new Button("_Screen Shot");
		takeScreenShot.setOnAction(e -> {
			try {
				stage.hide();
				Thread.sleep(1000);
				screenShotImage = new Robot()
						.createScreenCapture(new Rectangle(Toolkit
								.getDefaultToolkit().getScreenSize()));
				msgLbl.setText("Done");

				stage.show();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		saveBtn = new Button("_Save");
		saveBtn.prefWidthProperty().bind(scene.widthProperty().multiply(.30));
		saveBtn.disableProperty().bind(
				locationTxtField.textProperty().isEmpty());
		saveBtn.setOnAction(e -> {
			// TODO --> capture screenShot and save
			if (screenShotImage != null) {
				String tmp1, tmp2;
				/*
				 * fileChooser = new FileChooser();
				 * fileChooser.setTitle("Save screenShot");
				 * fileChooser.showSaveDialog(stage);
				 */
				if (nameString == null) {
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy_MM_dd_hh_mm_ss");
					tmp1 = sdf.format(date) + "_70856775" + "_" + "_"
							+ uniqueId.incrementAndGet();
				} else {
					tmp1 = nameString;
				}
				if (formateString == null) {
					tmp2 = "png";
				} else {
					tmp2 = formateString;
				}
				try {

					ImageIO.write(screenShotImage, formateString == null ? tmp2
							: formateString, new File(locationPathString + "\\"
							+ tmp1 + "." + tmp2));
				} catch (Exception e1) {
					msgLbl.setText("Location Incorrect");
					// TODO Auto-generated catch block
					// e1.printStackTrace();
				}
			}

			nameString = null;
			nameTxtField.setText(nameString);

			formateString = null;
			formateTxtField.setText(formateString);
			msgLbl.setText("Message Box");
		});
		hbox7.getChildren().addAll(msgLbl, takeScreenShot, saveBtn);
		leftPane.getChildren().add(hbox7);
		// hbox5 End

		/*
		 * Button btn = new Button("Take ScreenShot"); btn.setOnAction(e->{ try
		 * { BufferedImage screenShot = new Robot().createScreenCapture( new
		 * Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		 * ImageIO.write(screenShot,"png", new
		 * File("F:\\Projects\\Eclipse\\TestFile\\screenShot")); } catch
		 * (Exception e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } System.out.println("Done");
		 * 
		 * 
		 * });
		 * 
		 * HBox hbox = new HBox(); hbox.setAlignment(Pos.CENTER);
		 * hbox.getChildren().add(btn);
		 * 
		 * rootPane.getChildren().add(hbox);
		 */

		
		leftPane.setStyle("-fx-padding: 10;"
				+ "-fx-border-style: solid inside;" + "-fx-border-width: .1;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;"
				+ "-fx-border-color: blue;");
		//leftPane.setMaxWidth(300);
		
		centrePane.getItems().add(leftPane);
		
	}
	private void populateRightPane(){
		HBox tmp =new HBox();
		tmp.getChildren().add(new Button("Save"));
		//tmp.setMinWidth(300);
		centrePane.getItems().add(tmp);
		
	}

	public void populateScene() {
		// center
			rootPaneCentre();
		// top
		rootPaneTop();
		// right
		rootPaneRight();
		// bottom
		rootPaneBottom();
		// left
		rootPaneLeft();
		

		rootPane.getCenter().setOnMouseClicked(e->{
			System.out.println("Centre Pane Clicked");
		});
	}

}
