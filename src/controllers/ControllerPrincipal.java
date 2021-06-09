package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import models.main.Main;

public class ControllerPrincipal implements Initializable {
	
	@FXML 
	private AnchorPane paneDinamico;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		exibeTelaHome();	
	}

	@FXML
	protected void exibeTelaHome() {
		Main.mudarScene("/views/tela_home.fxml", paneDinamico);
	}
	
	@FXML
	protected void exibeTelaGerarChavesRSA() {
		Main.mudarScene("/views/tela_gerar_chaves_rsa.fxml", paneDinamico);
	}
	
	@FXML
	protected void exibeTelaCriptografar() {
		Main.mudarScene("/views/tela_criptografar.fxml", paneDinamico);
	}

	@FXML
	protected void exibeTelaDescriptografar() {
		Main.mudarScene("/views/tela_descriptografar.fxml", paneDinamico);
	}	
}
