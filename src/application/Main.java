
package application;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class Main extends Application{

  //Class Member variable
	private Stage stage;
	private Scene scene;
	private VBox rootPane;
	private HBox hbox1,hbox2,hbox3,hbox4,hbox5,hbox6,hbox7;
	private TextField locationTxtField,formateTxtField,nameTxtField;
	private Label locationLbl,formateLbl,nameLbl,msgLbl;
	private Button browseBtn,saveBtn,takeScreenShot;
	private DirectoryChooser dirChooser;
	private FileChooser fileChooser;
	private String locationPathString,formateString,nameString;
	
	private AtomicInteger uniqueId = new AtomicInteger(100);
	private BufferedImage screenShotImage = null;  
	public static void main(String[] args){
		Application.launch(args);
	}
	
	public void start(Stage stage)throws Exception{
		this.stage = stage;
		
		rootPane = new VBox();
		rootPane.setSpacing(10);
		rootPane.setStyle("-fx-padding: 10;" +
						"-fx-border-style: solid inside;" +
						"-fx-border-width: 2;" +
						"-fx-border-insets: 5;" +
						"-fx-border-radius: 5;" +
						"-fx-border-color: blue;");
		
		scene = new Scene(rootPane,300,300);
		
		populateScene();
		this.stage.setTitle("ScreenShot");
		this.stage.setScene(scene);
		this.stage.setResizable(false);
		this.stage.show();
		
		
	}
	
	public void populateScene(){
	//hbox1 Start
		hbox1 = new HBox();
			hbox1.setSpacing(10);
			locationLbl = new Label("Location :");
			
			hbox1.getChildren().addAll(locationLbl);
		rootPane.getChildren().add(hbox1);
	//hbox1 End
		
	//hbox2 Start
		hbox2 = new HBox();
			hbox2.setSpacing(10);
			
			locationTxtField  = new TextField();
			locationTxtField.setEditable(false);
			locationTxtField.prefWidthProperty().bind(scene.widthProperty().multiply(.65));
			
			browseBtn = new Button("_Browse");
			browseBtn.prefWidthProperty().bind(scene.widthProperty().multiply(.30));
			browseBtn.setOnAction(e->{
				dirChooser = new DirectoryChooser();
				dirChooser.setTitle("select Location");
				dirChooser.setInitialDirectory(new File("C:\\"));
				File file = dirChooser.showDialog(this.stage);
				if(file != null){
					
					//System.out.println(file.toPath());
					//locationTxtField.setText()
					locationPathString = file.toPath().toString().replace("\\", "\\\\");
					
					//System.out.println(locationPathString);
					locationTxtField.setText(locationPathString);
				}
				else{
					//System.out.println("file is NULL");
				}
				//String str = "amana";
				//str.r
				
		});
	
		hbox2.getChildren().addAll(locationTxtField ,browseBtn);
		rootPane.getChildren().add(hbox2);
	//hbox2 End
	//hbox3 Start
		hbox3 = new HBox();
			hbox3.setSpacing(10);
			formateLbl = new Label("Formate(png,jpeg , etc.) : ");
			
			hbox3.getChildren().add(formateLbl);
		rootPane.getChildren().add(hbox3);
	//hbox3 End
	
	//hbox4 Start
		hbox4 = new HBox();
			hbox4.setSpacing(10);
			
			formateTxtField = new TextField();
			formateTxtField.prefWidthProperty().bind(scene.widthProperty().multiply(.60));
			//TODO add changeListener to this text Field
			
			hbox4.getChildren().add(formateTxtField);
		rootPane.getChildren().add(hbox4);
	//hbox4 End
		
	//hbox5 Start 
		hbox5 = new HBox();
			hbox5.setSpacing(10);
			
			nameLbl = new Label("Name :");
			
			hbox5.getChildren().addAll(nameLbl);
		rootPane.getChildren().add(hbox5);	
	
	//hbox5 End
		
	//hbox6 Start
		hbox6 = new HBox();
			hbox6.setSpacing(10);
		
			nameTxtField = new TextField();
			nameTxtField.prefWidthProperty().bind(scene.widthProperty().multiply(.60));
			//TODO --> add change listener
			
		
		hbox6.getChildren().addAll(nameTxtField);
	rootPane.getChildren().add(hbox6);
	
	//hbox6 End
	//hbox7 Start
		hbox7 = new HBox();
			hbox7.setSpacing(10);
			hbox7.setAlignment(Pos.CENTER_RIGHT);
			
			msgLbl = new Label("Message Box");
			msgLbl.setStyle("-fx-border-style:solid");
			takeScreenShot = new Button("_Screen Shot");
			takeScreenShot.setOnAction(e->{
				try {
					stage.hide();
					Thread.sleep(1000);
					screenShotImage = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
					msgLbl.setText("Done");
					
					stage.show();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			saveBtn = new Button("_Save");
			saveBtn.prefWidthProperty().bind(scene.widthProperty().multiply(.30));
			saveBtn.disableProperty().bind(locationTxtField.textProperty().isEmpty());
			saveBtn.setOnAction(e->{
				//TODO --> capture screenShot and save
				if(screenShotImage != null){
					String tmp1,tmp2;
				/*	fileChooser = new FileChooser();
					fileChooser.setTitle("Save screenShot");
					fileChooser.showSaveDialog(stage);
				*/
					if(nameString == null){
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
						tmp1 = sdf.format(date)+"_70856775"+"_"+"_"+uniqueId.incrementAndGet();
					}
					else{
						tmp1 = nameString;
					}
					if(formateString == null){
						tmp2 = "png";
					}
					else{
						tmp2 = formateString;
					}
					try {
						
						ImageIO.write(screenShotImage, formateString==null?tmp2:formateString,new File(locationPathString+"\\"+tmp1+"."+tmp2));
					} catch (Exception e1) {
						msgLbl.setText("Location Incorrect");
						// TODO Auto-generated catch block
						//e1.printStackTrace();
					}
				}
				
				nameString = null;
				nameTxtField.setText(nameString);
				
				formateString = null;
				formateTxtField.setText(formateString);
				msgLbl.setText("Message Box");
			});
			hbox7.getChildren().addAll(msgLbl,takeScreenShot,saveBtn);
		rootPane.getChildren().add(hbox7);
	//hbox5 End
			
	/*		
		Button btn = new Button("Take ScreenShot");
		btn.setOnAction(e->{
			try {
				BufferedImage screenShot = new Robot().createScreenCapture(
						new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
				ImageIO.write(screenShot,"png", new File("F:\\Projects\\Eclipse\\TestFile\\screenShot")); 
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Done");
			
			
		});
		
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().add(btn);
		
		rootPane.getChildren().add(hbox);
		*/
	}
	
	
	
	
	
	
	
	
	
	
}
