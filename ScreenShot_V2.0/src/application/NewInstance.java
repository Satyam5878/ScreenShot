package application;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

	// Configuration File
	private DBFile configFile;
	
	// Center Pane-> Split Pane
	private VBox leftPane,rightPane;
	private HBox hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7;
	private TextField locationTxtField, formateTxtField, nameTxtField;
	private Label locationLbl, formateLbl, nameLbl, msgLbl;
	private Button browseBtn, saveBtn, takeScreenShot;
	private DirectoryChooser dirChooser;
	private FileChooser fileChooser;
	private String locationPathString, formateString, nameString;
	
	
	// Center Pane-> Split Pane-> right pane
	private HBox saveBtns;
	private ListView<String> screenShotsListView;
	private Button saveAll,deleteAll;
	
	// Members Specific to List View 
	private ObservableList<String> screenShotsListString;
	private ArrayList<String> screenShotsPathString;
	private ArrayList<BufferedImage> screenShotsListImage;
	private ContextMenu contextMenu;
	private MenuItem save,delete;
	
	
	
	private AtomicInteger uniqueId = new AtomicInteger(100);
	private BufferedImage screenShotImage = null;

	// Constructor to create the new application
	public NewInstance(Stage stage) {
		// Storing the main stage.
		this.stage = stage;

		rootPane = new BorderPane();
		// rootPane.setSpacing(10);

		scene = new Scene(rootPane, 650, 300);

		
		// populate the scene
		populateScene();
		
		// Populate the Utility member for list view.
		populateList();
						
				
		
		// set title 
		this.stage.setTitle("Screen Shot Application");
		this.stage.setScene(scene);
		this.stage.setResizable(false);
		this.stage.show();
		
		
		// for now this functionality is on hold
		// Before closing the window this should be executed.
		this.stage.setOnCloseRequest(e->{
			try {
				saveConfigFile();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

	}

	// This function saves the configuration file.
	private void saveConfigFile() throws IOException{
		FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir")+"/Configuration.conf");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(configFile);
		oos.close();
		fout.close();
		System.out.println(System.getProperty("user.dir"));
	}
	
	/**
	 * This function sets the top of the scene.
	 * Menu bar population.
	 * And functionality to create the new Window.
	 * This new window is separate from old window
	 *  
	 * */
	private void rootPaneTop() {
		
		// add menu bar
		menuBar = new MenuBar();
		newInstanceMenu = new Menu("New Instance");
		newInstanceMenuItem = new MenuItem("New Instance");
		
		// Creates the new window
		newInstanceMenuItem.setOnAction(e -> {
			new NewInstance(new Stage());
		});
		newInstanceMenu.getItems().add(newInstanceMenuItem);
		menuBar.getMenus().add(newInstanceMenu);
		rootPane.setTop(menuBar);
	}

	// all other panes are null and not used
	private void rootPaneBottom() {
		rootPane.setBottom(null);
	}

	private void rootPaneLeft() {
		rootPane.setLeft(null);
	}

	private void rootPaneRight() {
		rootPane.setRight(null);
	}
	
	/***
	 * Center Pane of root contains the split pane. 
	 * Left pane contains the functionality to to take the screen shot.
	 * Right Pane contains the list of screen shot taken and not saved.
	 * */
	private void rootPaneCentre() {
		// add split pane
		centrePane = new SplitPane();
		populateLeftPane();
		populateRightPane();
		rootPane.setCenter(centrePane);
	}
	private void populateLeftPane(){
		leftPane = new VBox();
		//leftPane.setSpacing(10);
		
		// hbox1 Start
			hbox1 = new HBox();
			hbox1.setSpacing(10);
			locationLbl = new Label("Location :");
		
	
			hbox1.getChildren().addAll(locationLbl);
			leftPane.getChildren().add(hbox1);
		// hbox1 End
		// hbox2 Start
			hbox2 = new HBox();
			hbox2.setSpacing(5);
	
			locationTxtField = new TextField();
			locationTxtField.setEditable(false);
			locationTxtField.prefWidthProperty().bind(
					scene.widthProperty().multiply(.50));
	
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
			leftPane.getChildren().add(new HBox(new Label()));
		// hbox3 Start
			hbox3 = new HBox();
			hbox3.setSpacing(10);
			formateLbl = new Label("Formate(png,jpeg , etc.) : ");
	
			hbox3.getChildren().add(formateLbl);
			leftPane.getChildren().add(hbox3);
		// hbox3 End
			
		// hbox4 Start
			hbox4 = new HBox();
			hbox4.setSpacing(5);
	
			formateTxtField = new TextField();
			
			formateTxtField.textProperty().addListener(new ChangeListener<String>(){

				@Override
				public void changed(ObservableValue<? extends String> prop,
						String oldValue, String newValue) {
					// TODO Auto-generated method stub
					formateString = newValue;
				}
				
			});
			
			
			formateTxtField.prefWidthProperty().bind(
					scene.widthProperty().multiply(.60));
			// TODO add changeListener to this text Field
	
			hbox4.getChildren().add(formateTxtField);
			leftPane.getChildren().add(hbox4);
		// hbox4 End
			leftPane.getChildren().add(new HBox(new Label()));
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
			nameTxtField.textProperty().addListener(new ChangeListener<String>(){

				@Override
				public void changed(ObservableValue<? extends String> prop,
						String oldValue, String newValue) {
					// TODO Auto-generated method stub
					nameString = newValue;
				}
				
			});
			nameTxtField.prefWidthProperty().bind(
					scene.widthProperty().multiply(.60));
			// TODO --> add change listener
	
			hbox6.getChildren().addAll(nameTxtField);
			leftPane.getChildren().add(hbox6);

		// hbox6 End
			leftPane.getChildren().add(new HBox(new Label()));
		// hbox7 Start
		hbox7 = new HBox();
		hbox7.setSpacing(5);
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

		saveBtn = new Button("_Put");
		saveBtn.prefWidthProperty().bind(scene.widthProperty().multiply(.15));
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
				
				// Set Name according the fields or default
				if (nameString == null) {
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy_MM_dd_hh_mm_ssss");
					tmp1 = sdf.format(date) + "_70856775" + "_" + "_"
							+ uniqueId.incrementAndGet();
				} else {
					tmp1 = nameString;
				}
				
				// Set format according fields or default 
				if (formateString == null) {
					tmp2 = "png";
				} else {
					tmp2 = formateString;
				}
				try {

					System.out.println("Putting the Image in list.");
					addScreenShot(locationPathString,tmp1 + "." + tmp2,screenShotImage);
					
					
					
				} catch (Exception e1) {
					msgLbl.setText("Location Incorrect");
					// TODO Auto-generated catch block
					// e1.printStackTrace();
				}finally{
					screenShotImage = null;
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
		leftPane.setMaxWidth(300);
		leftPane.setMinWidth(300);
		centrePane.getItems().add(leftPane);
		
	}
	private void populateRightPane(){
		
		
		rightPane = new VBox();
			rightPane.setSpacing(5);
		
			saveBtns = new HBox();
				saveBtns.setSpacing(5);
				saveBtns.setAlignment(Pos.CENTER_RIGHT);
				saveAll = new Button("Save All");
				saveAll.setOnAction(e->{
					System.out.println("Save All pressed");
					// TODO:
					saveScreenShot(-2); // -2 indicate for saving all the screen shots
					
				});
				deleteAll = new Button("Delete All");
				deleteAll.setOnAction(e->{
					System.out.println("Delete All pressed");
					// TODO:
					deleteScreenShot(-2); // -2 indicates deleting all the screen shots
				});
				saveBtns.getChildren().addAll(saveAll,deleteAll); // Adding SaveAll btn to saveBtns Hbox
		
		
			rightPane.getChildren().addAll(saveBtns);
			// listView: TODO
			
			
			
			screenShotsListView = new ListView<String>();
				screenShotsListView.setPlaceholder(new Label("Your screen shot will appear here."));
				
				contextMenu = new ContextMenu();
					save = new MenuItem("_Save");
					save.setOnAction(e->{
						// TODO
						System.out.println("Save Pressed");
						int idx = screenShotsListView.getSelectionModel().getSelectedIndex();
						saveScreenShot(idx);
						screenShotsListView.getSelectionModel().clearSelection();
					});
					delete = new MenuItem("_Delete");
					delete.setOnAction(e->{
						// TODO:
						System.out.println("Delete Pressed");
						int idx = screenShotsListView.getSelectionModel().getSelectedIndex();
						deleteScreenShot(idx);
						screenShotsListView.getSelectionModel().clearSelection();
					});
					
					contextMenu.getItems().addAll(save,delete);
					
				screenShotsListView.setContextMenu(contextMenu);
			rightPane.getChildren().addAll(screenShotsListView);
			//tmp.setMinWidth(300);
			rightPane.setStyle("-fx-padding: 10;"
					+ "-fx-border-style: solid inside;" + "-fx-border-width: .1;"
					+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;"
					+ "-fx-border-color: blue;");
		centrePane.getItems().add(rightPane); // adding rightPane to center split pane
		
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
		

		// this code is for testing
		rootPane.getCenter().setOnMouseClicked(e->{
			System.out.println("Centre Pane Clicked");
		});
		//
	}
	
	
	// Utility function for initializing the list view member.
	private void populateList(){
		
		System.out.println("PoulateList...");
		screenShotsListImage = new ArrayList<BufferedImage>();
		screenShotsPathString = new ArrayList<String>();
		
		screenShotsListString = FXCollections.observableArrayList();
		screenShotsListString.addListener(new ListChangeListener<String>(){

			@Override
			public void onChanged(ListChangeListener.Change<? extends String> c) {
				// TODO Auto-generated method stub
				
				// Add all the elements to the list view again
				System.out.println("List String Changed.....");
				while(c.next()){
					screenShotsListView.getItems().clear();
					screenShotsListView.getItems().addAll(screenShotsListString);
				
				}			
			}
		});	
	}
	/**
	 * 	This function will delete the Image at index idx.
	 * 
	 * */
	private void deleteScreenShot(int idx){
		
		System.out.println("deleteScreenShot is called.");
		if(idx>=0 && idx<= screenShotsListString.size()){
			screenShotsPathString.remove(idx);
			screenShotsListImage.remove(idx);
			screenShotsListString.remove(idx);
		}
		else if(idx ==-2){
			for(int i=screenShotsListString.size()-1;i>=0;--i){
				screenShotsPathString.remove(i);
				screenShotsListImage.remove(i);
				
				
			}
			screenShotsListString.clear();
			
		}
		
		
	}
	
	/**
	 * This function will save the Image at index idx and delete it from the list.
	 * 
	 * */
	private void saveScreenShot(int idx){
		System.out.println("saveScreenShot is called.");
		// Code for writing the image to file.
		if(idx>=0 && idx<= screenShotsListString.size()){
			try {
				ImageIO.write(screenShotsListImage.get(idx), 
						//formateString == null ? tmp2: formateString, 
						screenShotsListString.get(idx).substring(screenShotsListString.get(idx).indexOf(".")+1),
						new File(screenShotsPathString.get(idx) + "\\"+screenShotsListString.get(idx)));
				deleteScreenShot(idx);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(idx == -2){
			try {
				// if -2 means to save all of them and then delete all
				for(int i=0;i<screenShotsListString.size();++i){
					ImageIO.write(screenShotsListImage.get(i), 
							//formateString == null ? tmp2: formateString, 
							screenShotsListString.get(i).substring(screenShotsListString.get(i).indexOf(".")+1),
							new File(screenShotsPathString.get(i) + "\\"+screenShotsListString.get(i)));
				}
				deleteScreenShot(idx);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
	/**
	 * This function adds the screen shot in the string and image list.
	 * 
	 * */
	private void addScreenShot(String pathName,String Name,BufferedImage screenShot){
		System.out.println("addScreenShot is called.");
		
		
		//screenShotsListImage.add(new BufferedImage(screenShotImage));
		
		ColorModel cm = screenShot.getColorModel();
		//System.out.println("Putting the Image in list2.");
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		//System.out.println("Putting the Image in list3.");
		WritableRaster raster = screenShot.copyData(null);
		//System.out.println("Putting the Image in list4.");
		BufferedImage newScreenShot =  new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		//System.out.println("Putting the Image in list5.");
		
		screenShotsPathString.add(pathName);
		System.out.println(pathName);
		//System.out.println("Putting the Image in list6.");
		screenShotsListImage.add(newScreenShot);
		//System.out.println("Putting the Image in list7.");
		screenShotsListString.add(new String(Name));
		//System.out.println("Putting the Image in list8.");
		System.out.println("Putting the Image in list done...");
	}

}
