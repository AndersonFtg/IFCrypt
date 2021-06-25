package models.criptografia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import models.arquivo.ManipulacaoArquivo;
import models.util.ValoresFixos;

public class AssinaturaDigital {

	private Signature assinatura;
	private ManipulacaoArquivo manArquivo;
	private int tamAssinaturaArq = ValoresFixos.TAM_LEITURA_ASSINATURA.getValor();

	public AssinaturaDigital() throws NoSuchAlgorithmException {
		
		assinatura = Signature.getInstance("SHA256withRSA");
		manArquivo = new ManipulacaoArquivo();
	}

	public byte[] gerarAssinatura(byte[] arquivoSemAssinatura, PrivateKey chavePrivada)	throws InvalidKeyException, SignatureException {

		assinatura.initSign(chavePrivada);
		assinatura.update(arquivoSemAssinatura);
		
		return assinatura.sign();
	}

	public boolean validarAssinatura(byte[] arquivoComAssinatura, PublicKey chavePublica) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException, InvalidKeySpecException, FileNotFoundException, IOException {

		assinatura.initVerify(chavePublica);
		assinatura.update(getConteudoArquivoSemAssinatura(arquivoComAssinatura));
		
		return assinatura.verify(getAssinatuaNoArquivo(arquivoComAssinatura));
	}

	public byte[] getAssinatuaNoArquivo(byte[] arquivoComAssinatura) {

		byte[] assinaturaArquivo = new byte[tamAssinaturaArq];

		for (int i = 0; i < tamAssinaturaArq; i++) {
			assinaturaArquivo[i] = arquivoComAssinatura[i];
		}
		
		return assinaturaArquivo;
	}

	public byte[] getConteudoArquivoSemAssinatura(byte[] arquivo) {

		byte[] conteudoArquivo = new byte[arquivo.length - tamAssinaturaArq];
		int j = 0;

		for (int i = tamAssinaturaArq; i < arquivo.length; i++) {
			conteudoArquivo[j] = arquivo[i];
			j++;
		}
		
		return conteudoArquivo;
	}

	public boolean setAssinaturaNoArquivo(File arquivoCriptografado, byte[] assinatura) throws IOException {

		byte[] arquivoCriptografadoBytes = manArquivo.arquivoParaByte(arquivoCriptografado);

		FileOutputStream fos = new FileOutputStream(arquivoCriptografado);
		fos.write(assinatura);
		fos.write(arquivoCriptografadoBytes);
		fos.close();
		
		return true;
	}
}
