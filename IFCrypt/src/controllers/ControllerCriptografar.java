package controllers;
 
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.arquivo.SelecaoArquivo;
import models.criptografia.CriptografarArquivo;
import models.util.MensagemAoUsuario;

public class ControllerCriptografar implements Initializable {

	@FXML
	private TextField txtArquivoCript, txtChavePubCript, txtChavePrivCript;
	
	@FXML
	private Button btnCriptDoc;

	private File docEscolhido, localSalvarDocCriptografado, docChavePubDestino, docChavePrivRemetente;
	private SelecaoArquivo arquivo;
	private CriptografarArquivo criptArquivo;
	private MensagemAoUsuario mensagem;
	private Stage stage = new Stage();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		arquivo = new SelecaoArquivo();
		mensagem = new MensagemAoUsuario();
		
		Platform.runLater(() -> txtArquivoCript.requestFocus());
	}

	@FXML
	protected void escolherArquivo() {
		docEscolhido = arquivo.selecionarArquivoPDF();
		txtArquivoCript.setText((docEscolhido == null) ? "" : docEscolhido.getAbsolutePath());
	}

	@FXML
	protected void escolherChavePublica() {
		docChavePubDestino = arquivo.selecionarChavePub();
		txtChavePubCript.setText((docChavePubDestino == null) ? "" : docChavePubDestino.getAbsolutePath());
	}

	@FXML
	protected void escolherChavePrivada() {
		docChavePrivRemetente = arquivo.selecionarChavePriv();
		txtChavePrivCript.setText((docChavePrivRemetente == null) ? "" : docChavePrivRemetente.getAbsolutePath());
	}

	@FXML
	protected void criptografarArquivo() {
		
		btnCriptDoc.setDisable(true);
		
		if (verificaSeTxtsEstaoVazios()) {
			mensagem.mensagemErro();
			btnCriptDoc.setDisable(false);
		}
		
		else {
			
			localSalvarDocCriptografado = arquivo.selecionarLocalSalvarArquivoCifrado();
			
			if (localSalvarDocCriptografado == null) {
				mensagem.mensagemAlerta();
				btnCriptDoc.setDisable(false);
			}

			else {
				try {
					telaCarregando(stage);
					criptArquivo = new CriptografarArquivo();
	
					if (criptArquivo.criptografar(docEscolhido, localSalvarDocCriptografado, docChavePubDestino, docChavePrivRemetente)) {
						stage.close();
						mensagem.mensagemSucesso("criptografado");
						limpaTxts();
						btnCriptDoc.setDisable(false);
					}
	
					else {
						stage.close();
						mensagem.mensagemErroFinal("criptografar");
						btnCriptDoc.setDisable(false);
					}
	
				} catch (NoSuchAlgorithmException | NoSuchPaddingException | IOException | InvalidKeyException
						| InvalidKeySpecException | SignatureException | IllegalBlockSizeException | BadPaddingException e) {
					mensagem.mensagemErroFinal("criptografar");
					btnCriptDoc.setDisable(false);
					e.printStackTrace();
				}
			}
		}
	}

	private boolean verificaSeTxtsEstaoVazios() {
		
		if (txtArquivoCript.getText().equals("") || txtChavePubCript.getText().equals("")
				|| txtChavePrivCript.getText().equals("")) {
			return true;
		}

		return false;
	}
	
	private void limpaTxts() {
		
		 txtArquivoCript.clear();
		 txtChavePubCript.clear();
		 txtChavePrivCript.clear();
		 txtArquivoCript.requestFocus();
	}
	
	private void telaCarregando(Stage stage) {
		
		Parent root = null;
		
		try {
			root = (Parent) FXMLLoader.load(getClass().getResource("/views/tela_carregando_cript.fxml"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
}
