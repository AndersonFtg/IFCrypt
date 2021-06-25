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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
		mensagem.mensagemProcessamento();
		
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
			
			if (assinaturaValida) {
				localSalvarDocDescriptografado = arquivo.selecionarLocalSalvarArquivoDecifrado();
				
				if (localSalvarDocDescriptografado == null) {
					btnDecriptDoc.setDisable(false);
				}
		
				else {
					try {	
						if (descriptArquivo.descriptografar(docCriptografado, localSalvarDocDescriptografado, docChavePrivDestino)) {
							mensagem.mensagemSucesso("descriptografado", "Descriptografia");
							limpaTxts();
							btnDecriptDoc.setDisable(false);
						}
		
						else {
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
}
