package models.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

public class MensagemAoUsuario {

	private Alert alert;

	public void mensagemErro() {

		alert = new Alert(AlertType.ERROR);
		alert.setGraphic(new ImageView(this.getClass().getResource("../../images/error.png").toString()));
		alert.setTitle("Erro");
		alert.setHeaderText("Campos Vazios");
		alert.setContentText("Preencha todos os campos para continuar.");
		alert.showAndWait();
	}

	public void mensagemErroFinal(String texto) {
		
		alert = new Alert(AlertType.ERROR);
		alert.setGraphic(new ImageView(this.getClass().getResource("../../images/cript_falha.png").toString()));
		alert.setTitle("Erro");
		alert.setHeaderText("Falha");
		alert.setContentText("Ocorreu um errro ao tentar " + texto + " o arquivo. Verifique os arquivos escolhidos.");
		alert.showAndWait();
	}

	public void mensagemSucesso(String texto) {
		
		alert = new Alert(AlertType.INFORMATION);
		alert.setGraphic(new ImageView(this.getClass().getResource("../../images/cript_sucesso.png").toString()));
		alert.setTitle("Sucesso");
		alert.setHeaderText("Sucesso");
		alert.setContentText("Arquivo " + texto + " com sucesso!");
		alert.showAndWait();
	}

	public void mensagemAlerta() {

		alert = new Alert(AlertType.WARNING);
		alert.setGraphic(new ImageView(this.getClass().getResource("../../images/warning.png").toString()));
		alert.setTitle("Atenção");
		alert.setHeaderText("Nenhum Arquivo Selecionado");
		alert.setContentText("Por favor, selecione um arquivo para continuar.");
		alert.showAndWait();
	}
	
	public void mensagemAssinaturaInvalida() {
		
		alert = new Alert(AlertType.ERROR);
		alert.setGraphic(new ImageView(this.getClass().getResource("../../images/error.png").toString()));
		alert.setTitle("Erro");
		alert.setHeaderText("Assinatura");
		alert.setContentText("Assinatura Inválida. Podem ter ocorrido modificações no documento ou pode ter sido corrompido.");
		alert.showAndWait();
	}
	
	public void mensagemErroValidarAssinatura() {
		
		alert = new Alert(AlertType.ERROR);
		alert.setGraphic(new ImageView(this.getClass().getResource("../../images/error.png").toString()));
		alert.setTitle("Erro");
		alert.setHeaderText("Assinatura");
		alert.setContentText("Ocorreu um erro ao validar a assinatura do arquivo. Favor verificar os arquivos escolhidos.");
		alert.showAndWait();
	}
	
	public void mensagemChaveSucesso() {
		
		alert = new Alert(AlertType.INFORMATION);
		alert.setGraphic(new ImageView(this.getClass().getResource("../../images/success.png").toString()));
		alert.setTitle("Sucesso");
		alert.setHeaderText("Chaves RSA");
		alert.setContentText("Chaves RSA Geradas com Sucesso.");
		alert.showAndWait();
	}
	
	public void mensagemChaveErro() {
		
		alert = new Alert(AlertType.ERROR);
		alert.setGraphic(new ImageView(this.getClass().getResource("../../images/error.png").toString()));
		alert.setTitle("Erro");
		alert.setHeaderText("Chaves RSA");
		alert.setContentText("Ocorreu um erro ao salvar as chaves. Verifique os campos preenchidos e tente novamente.");
		alert.showAndWait();
	}
}
