package application;

import java.io.Serializable;

import javax.swing.filechooser.FileSystemView;

public class DBFile implements Serializable{
	private String savingPath;
	public DBFile(){
		savingPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()+"/Desktop";
		//Path.get(System.getProperty("user.home")+"/Desktop");
	}
	
	public void setPath(String path){
		savingPath = path;
	}
	public String getPath(){
		return savingPath; 
	}
	
	
	
	
}
