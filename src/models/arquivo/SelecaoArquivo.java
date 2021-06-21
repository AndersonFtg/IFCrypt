package models.arquivo;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import models.util.MensagemAoUsuario;;

public class SelecaoArquivo {

	private File arquivoPDFSelecionado, arquivoCifrado, arquivoChavePub, arquivoChavePriv;
	private File arquivoSalvarDocCifrado, arquivoSalvarDocDecifrado, arquivoSalvarChavePub, arquivoSalvarChavePriv;
	private Window window;
	private FileChooser fileChooserSelArquivo, fileChooserSaveArquivo;
	private MensagemAoUsuario mensagem;
	
	public SelecaoArquivo() {
		
		window = null;
		mensagem = new MensagemAoUsuario();
	}
	
	public File selecionarArquivoPDF() {
		
		fileChooserSelArquivo = new FileChooser();
		fileChooserSelArquivo.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
				
		if (arquivoPDFSelecionado != null) {
			fileChooserSelArquivo.setInitialDirectory(arquivoPDFSelecionado.getParentFile());
		}

		arquivoPDFSelecionado = fileChooserSelArquivo.showOpenDialog(window);
		
		if (arquivoPDFSelecionado == null) {
			mensagem.mensagemAlerta();
			return null;
		}

		return arquivoPDFSelecionado;
	}
	
	public File selecionarArquivoCifrado() {
		
		fileChooserSelArquivo = new FileChooser();
		fileChooserSelArquivo.getExtensionFilters().add(new FileChooser.ExtensionFilter("IFC", "*.ifc"));
		
		if (arquivoCifrado != null) {
			fileChooserSelArquivo.setInitialDirectory(arquivoCifrado.getParentFile());
		}
		
		arquivoCifrado = fileChooserSelArquivo.showOpenDialog(window);

		if (arquivoCifrado == null) {
			mensagem.mensagemAlerta();
			return null;
		}

		return arquivoCifrado;
	}

	public File selecionarChavePub() {
		
		fileChooserSelArquivo = new FileChooser();
		fileChooserSelArquivo.getExtensionFilters().add(new ExtensionFilter("PUBK", "*.pubk"));
		
		if (arquivoChavePub != null) {
			fileChooserSelArquivo.setInitialDirectory(arquivoChavePub.getParentFile());
		}
		
		else if (arquivoChavePriv != null) {
			fileChooserSelArquivo.setInitialDirectory(arquivoChavePriv.getParentFile());
		}
		
		arquivoChavePub = fileChooserSelArquivo.showOpenDialog(window);

		if (arquivoChavePub == null) {
			mensagem.mensagemAlerta();
			return null;
		}

		return arquivoChavePub;
	}
	
	public File selecionarChavePriv() {
		
		fileChooserSelArquivo = new FileChooser();
		fileChooserSelArquivo.getExtensionFilters().add(new ExtensionFilter("PRIVK", "*.privk"));
		
		if (arquivoChavePriv != null) {
			fileChooserSelArquivo.setInitialDirectory(arquivoChavePriv.getParentFile());
		}

		else if (arquivoChavePub != null) {
			fileChooserSelArquivo.setInitialDirectory(arquivoChavePub.getParentFile());
		}
		
		arquivoChavePriv = fileChooserSelArquivo.showOpenDialog(window);

		if (arquivoChavePriv == null) {
			mensagem.mensagemAlerta();
			return null;
		}

		return arquivoChavePriv;
	}

	public File selecionarLocalSalvarArquivoCifrado() {
		
		fileChooserSaveArquivo = new FileChooser();
		fileChooserSaveArquivo.getExtensionFilters().add(new FileChooser.ExtensionFilter("IFC", "*.ifc"));
		
		if (arquivoPDFSelecionado != null) {
			fileChooserSaveArquivo.setInitialDirectory(arquivoPDFSelecionado.getParentFile());
		}

		arquivoSalvarDocCifrado = fileChooserSaveArquivo.showSaveDialog(window);
		
		if (arquivoSalvarDocCifrado == null) {
			mensagem.mensagemAlerta();
			return null;
		}

		return arquivoSalvarDocCifrado;
	}
	
	public File selecionarLocalSalvarArquivoDecifrado() {
		
		fileChooserSaveArquivo = new FileChooser();
		fileChooserSaveArquivo.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		
		if (arquivoCifrado != null) {
			fileChooserSaveArquivo.setInitialDirectory(arquivoCifrado.getParentFile());
		}

		arquivoSalvarDocDecifrado = fileChooserSaveArquivo.showSaveDialog(window);
		
		if (arquivoSalvarDocDecifrado == null) {
			mensagem.mensagemAlerta();
			return null;
		}

		return arquivoSalvarDocDecifrado;
	}
	
	public File selecionarLocalSalvarChavePubRSA() {
		
		fileChooserSaveArquivo = new FileChooser();
		fileChooserSaveArquivo.getExtensionFilters().add(new ExtensionFilter("PUBK", "*.pubk"));
		
		if (arquivoSalvarChavePub != null) {
			fileChooserSaveArquivo.setInitialDirectory(arquivoSalvarChavePub.getParentFile());
		}

		else if (arquivoSalvarChavePriv != null) {
			fileChooserSaveArquivo.setInitialDirectory(arquivoSalvarChavePriv.getParentFile());
		}
		
		arquivoSalvarChavePub = fileChooserSaveArquivo.showSaveDialog(window);

		if (arquivoSalvarChavePub == null) {
			mensagem.mensagemAlerta();
			return null;
		}

		return arquivoSalvarChavePub;
	}
	
	public File selecionarLocalSalvarChavePrivRSA() {
		
		fileChooserSaveArquivo = new FileChooser();
		fileChooserSaveArquivo.getExtensionFilters().add(new ExtensionFilter("PRIVK", "*.privk"));
		
		if (arquivoSalvarChavePriv != null) {
			fileChooserSaveArquivo.setInitialDirectory(arquivoSalvarChavePriv.getParentFile());
		}

		else if (arquivoSalvarChavePub != null) {
			fileChooserSaveArquivo.setInitialDirectory(arquivoSalvarChavePub.getParentFile());
		}
		
		arquivoSalvarChavePriv = fileChooserSaveArquivo.showSaveDialog(window);

		if (arquivoSalvarChavePriv == null) {
			mensagem.mensagemAlerta();
			return null;
		}

		return arquivoSalvarChavePriv;
	}
}
