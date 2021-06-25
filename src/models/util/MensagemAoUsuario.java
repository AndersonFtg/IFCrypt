package models.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

public class MensagemAoUsuario {

	private Alert alert;

	public void mensagemErro() {

		alert = new Alert(AlertType.ERROR);
		alert.setGraphic(new ImageView(this.getClass().getResource("/images/error.png").toExternalForm()));
		alert.setTitle("Erro");
		alert.setHeaderText("Campos Vazios");
		alert.setContentText("Preencha todos os campos para continuar.");
		alert.showAndWait();
	}

	public void mensagemErroFinal(String texto) {
		
		alert = new Alert(AlertType.ERROR);
		alert.setGraphic(new ImageView(this.getClass().getResource("/images/cript_falha.png").toExternalForm()));
		alert.setTitle("Erro");
		alert.setHeaderText("Falha");
		alert.setContentText("Ocorreu um errro ao tentar " + texto + " o arquivo. Verifique os arquivos escolhidos.");
		alert.showAndWait();
	}

	public void mensagemSucesso(String texto, String header) {
		
		alert = new Alert(AlertType.INFORMATION);
		alert.setGraphic(new ImageView(this.getClass().getResource("/images/cript_sucesso.png").toExternalForm()));
		alert.setTitle("Sucesso");
		alert.setHeaderText(header);
		alert.setContentText("Arquivo " + texto + " com sucesso!");
		alert.showAndWait();
	}

	public void mensagemAlerta() {

		alert = new Alert(AlertType.WARNING);
		alert.setGraphic(new ImageView(this.getClass().getResource("/images/warning.png").toExternalForm()));
		alert.setTitle("Atenção");
		alert.setHeaderText("Nenhum Arquivo Selecionado");
		alert.setContentText("Por favor, selecione um arquivo para continuar.");
		alert.showAndWait();
	}
	
	public void mensagemAssinaturaInvalida() {
		
		alert = new Alert(AlertType.ERROR);
		alert.setGraphic(new ImageView(this.getClass().getResource("/images/error.png").toExternalForm()));
		alert.setTitle("Erro");
		alert.setHeaderText("Assinatura Digital");
		alert.setContentText("Assinatura digital inválida. Podem ter ocorrido modificações no documento ou pode ter sido corrompido.");
		alert.showAndWait();
	}
	
	public void mensagemErroValidarAssinatura() {
		
		alert = new Alert(AlertType.ERROR);
		alert.setGraphic(new ImageView(this.getClass().getResource("/images/error.png").toExternalForm()));
		alert.setTitle("Erro");
		alert.setHeaderText("Assinatura Digital");
		alert.setContentText("Ocorreu um erro ao validar a assinatura digtial do arquivo. Favor verificar os arquivos escolhidos.");
		alert.showAndWait();
	}
	
	public void mensagemChaveSucesso() {
		
		alert = new Alert(AlertType.INFORMATION);
		alert.setGraphic(new ImageView(this.getClass().getResource("/images/success.png").toExternalForm()));
		alert.setTitle("Sucesso");
		alert.setHeaderText("Chaves Criptográficas");
		alert.setContentText("Chaves criptográficas geradas com sucesso!");
		alert.showAndWait();
	}
	
	public void mensagemChaveErro() {
		
		alert = new Alert(AlertType.ERROR);
		alert.setGraphic(new ImageView(this.getClass().getResource("/images/error.png").toExternalForm()));
		alert.setTitle("Erro");
		alert.setHeaderText("Chaves Criptográficas");
		alert.setContentText("Ocorreu um erro ao salvar as chaves. Verifique os campos preenchidos e tente novamente.");
		alert.showAndWait();
	}

	public void mensagemProcessamento() {
		
		alert = new Alert(AlertType.WARNING);
		alert.setGraphic(new ImageView(this.getClass().getResource("/images/warning.png").toExternalForm()));
		alert.setTitle("Atenção");
		alert.setHeaderText("Procesamento");
		alert.setContentText("O mesmo arquivo não poderá ser descriptografado novamente.");
		alert.showAndWait();
	}
}
