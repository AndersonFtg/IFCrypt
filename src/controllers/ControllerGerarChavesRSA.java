package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import models.arquivo.SelecaoArquivo;
import models.criptografia.GerarChavesRSA;
import models.util.MensagemAoUsuario;

public class ControllerGerarChavesRSA implements Initializable {

	@FXML
	private TextField txtLocalSalvarChavePubRSA, txtLocalSalvarChavePrivRSA;
	
	@FXML
	private Button btnInfoChavePub, btnInfoChavePriv, btnGerarChaves;

	private File dirSalvarChavePubRSA, dirSalvarChavePrivRSA;
	private SelecaoArquivo arquivo;
	private MensagemAoUsuario mensagem;
	private GerarChavesRSA gerChaves;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		arquivo = new SelecaoArquivo();
		mensagem = new MensagemAoUsuario();
		
		Platform.runLater(() -> txtLocalSalvarChavePubRSA.requestFocus());
		definirToolTipBotoes();
	}

	@FXML
	protected void escolherLocalSalvarChavePub() {
		
		dirSalvarChavePubRSA = arquivo.selecionarLocalSalvarChavePubRSA();
		txtLocalSalvarChavePubRSA.setText((dirSalvarChavePubRSA == null) ? "" : dirSalvarChavePubRSA.getAbsolutePath());
	}

	@FXML
	protected void escolherLocalSalvarChavePriv() {
		
		dirSalvarChavePrivRSA = arquivo.selecionarLocalSalvarChavePrivRSA();
		txtLocalSalvarChavePrivRSA.setText((dirSalvarChavePrivRSA == null) ? "" : dirSalvarChavePrivRSA.getAbsolutePath());
	}

	@FXML
	protected void gerarChaves() {
		
		btnGerarChaves.setDisable(true);

		if (txtLocalSalvarChavePubRSA.getText().equals("") || txtLocalSalvarChavePrivRSA.getText().equals("")) {
			mensagem.mensagemErro();
			btnGerarChaves.setDisable(false);
		}

		else {
			try {
				gerChaves = new GerarChavesRSA();
				
				if(gerChaves.gerarChavePublica(dirSalvarChavePubRSA) && gerChaves.gerarChavePrivada(dirSalvarChavePrivRSA)){
					mensagem.mensagemChaveSucesso();
					txtLocalSalvarChavePubRSA.clear();
					txtLocalSalvarChavePrivRSA.clear();
					txtLocalSalvarChavePubRSA.requestFocus();
					btnGerarChaves.setDisable(false);
				} 
				
				else {
					mensagem.mensagemChaveErro();
					btnGerarChaves.setDisable(false);
				}

			} catch (IOException |  NoSuchAlgorithmException e) {
				mensagem.mensagemChaveErro();
				btnGerarChaves.setDisable(false);
				e.printStackTrace();
			}
		}
	}
	
	private void definirToolTipBotoes() {
		btnInfoChavePub.setTooltip(new Tooltip("A Chave Pública será utilizada por outras pessoas para criptografar os arquivos e enviarem \r\n" + 
				"ao propietário da chave. Encoraja-se que essa chave seja divulgada para outras pessoas \r\n" + 
				"terem acesso e realizarem uma comunicação segura."));
		
		btnInfoChavePriv.setTooltip(new Tooltip("A Chave Privada será utilizada apenas pelo propietário para descriptografar os documentos\r\n" + 
				"recebidos e, caso deseje, assiná-los. Essa chave deve ser mantida em segredo e não deve\r\n" + 
				"ser divulgada, pois irá comprometer a segurança da comunicação."));
	}
}
