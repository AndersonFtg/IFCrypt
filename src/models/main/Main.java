package models.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) { 
		
		try {
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("/views/tela_principal.fxml"));
	
			Scene scene = new Scene(root);
			stage.setTitle("IFCrypt");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setOnCloseRequest(actionEvent -> Platform.exit());
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static void mudarScene(String nomeArquivo, AnchorPane pane) {
		
		AnchorPane telaCarregada = null;
		
		try {
			telaCarregada = FXMLLoader.load(Main.class.getResource(nomeArquivo));
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		pane.getChildren().clear();
		pane.getChildren().addAll(telaCarregada);
	}
}
