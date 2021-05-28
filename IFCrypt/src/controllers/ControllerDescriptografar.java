package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;

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
import models.criptografia.DescriptografarArquivo;
import models.util.MensagemAoUsuario;

public class ControllerDescriptografar implements Initializable {

	@FXML
	private TextField txtArquivoDecript, txtChavePubDecript, txtChavePrivDecript;
	
	@FXML
	private Button btnDecriptDoc;

	private File docCriptografado, localSalvarDocDescriptografado, docChavePubRemetente, docChavePrivDestino;
	private SelecaoArquivo arquivo;
	private DescriptografarArquivo descriptArquivo;
	private MensagemAoUsuario mensagem;
	private Stage stage = new Stage();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		arquivo = new SelecaoArquivo();
		mensagem = new MensagemAoUsuario();
		
		Platform.runLater(() -> txtArquivoDecript.requestFocus());
	}

	@FXML
	protected void escolherArquivo() {
		docCriptografado = arquivo.selecionarArquivoCifrado();
		txtArquivoDecript.setText((docCriptografado == null) ? "" : docCriptografado.getAbsolutePath());
	}
	
	@FXML
	protected void escolherChavePublica() {
		docChavePubRemetente = arquivo.selecionarChavePub();
		txtChavePubDecript.setText((docChavePubRemetente == null) ? "" : docChavePubRemetente.getAbsolutePath());
	}

	@FXML
	protected void escolherChavePrivada() {
		docChavePrivDestino = arquivo.selecionarChavePriv();
		txtChavePrivDecript.setText((docChavePrivDestino == null) ? "" : docChavePrivDestino.getAbsolutePath());
	}

	@FXML
	protected void descriptografarArquivo() {
		
		btnDecriptDoc.setDisable(true);
		
		try {
			descriptArquivo = new DescriptografarArquivo();
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
			ex.printStackTrace();
		}
		
		if (verificaSeTxtsEstaoVazios()) {
			mensagem.mensagemErro();
			btnDecriptDoc.setDisable(false);
		}
		
		else {
			
			boolean assinaturaValida = false;
		
			try {
				assinaturaValida = descriptArquivo.verificarAssinatura(docCriptografado, docChavePubRemetente);
			
			} catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | SignatureException
					| IOException ex) {
				mensagem.mensagemErroValidarAssinatura();
				btnDecriptDoc.setDisable(false);
				ex.printStackTrace();
			}
			
			if(assinaturaValida) {
				localSalvarDocDescriptografado = arquivo.selecionarLocalSalvarArquivoDecifrado();
				
				if (localSalvarDocDescriptografado == null) {
					mensagem.mensagemAlerta();
					btnDecriptDoc.setDisable(false);
				}
		
				else {
					try {						
						telaCarregando(stage); 
							
						if (descriptArquivo.descriptografar(docCriptografado, localSalvarDocDescriptografado, docChavePrivDestino)) {
							stage.close();
							mensagem.mensagemSucesso("descriptografado");
							limpaTxts();
							btnDecriptDoc.setDisable(false);
						}
		
						else {
							stage.close();
							mensagem.mensagemErroFinal("descriptografar");
							btnDecriptDoc.setDisable(false);
						}
		
					} catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException
							| IOException | SignatureException e) {
						mensagem.mensagemErroFinal("descriptografar");
						btnDecriptDoc.setDisable(false);
						e.printStackTrace();
					}
				}
			}
			
			else {
				mensagem.mensagemAssinaturaInvalida();
				btnDecriptDoc.setDisable(false);
			}
		}
	}

	private boolean verificaSeTxtsEstaoVazios() {

		if (txtArquivoDecript.getText().equals("") || txtChavePubDecript.getText().equals("")
				|| txtChavePrivDecript.getText().equals("")) {
			return true;
		}

		return false;
	}
	
	private void limpaTxts() {
		
		txtArquivoDecript.clear();
		txtChavePubDecript.clear();
		txtChavePrivDecript.clear();
		txtArquivoDecript.requestFocus();
	}
	
	private void telaCarregando(Stage stage) {
		
		Parent root = null;
		
		try {
			root = (Parent) FXMLLoader.load(getClass().getResource("/views/tela_carregando_decript.fxml"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
}
