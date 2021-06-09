package models.criptografia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import models.arquivo.ManipulacaoArquivo;
import models.util.ValoresFixos;

public class DescriptografarArquivo {

	private Cipher cifraAES, cifraRSA;
	private byte[] chaveAESBytes;
	private SecretKeySpec chaveSecretaAES;
	private AssinaturaDigital assinatura;
	private ManipulacaoArquivo manArquivo;
	private int tamLeituraChaveAES = ValoresFixos.TAM_LEITURA_CHAVE_AES.getValor();
	
	// Construtor
	public DescriptografarArquivo() throws NoSuchAlgorithmException, NoSuchPaddingException {
		
		assinatura = new AssinaturaDigital();
		manArquivo = new ManipulacaoArquivo();
		
		cifraAES = Cipher.getInstance("AES");
		cifraRSA = Cipher.getInstance("RSA");
	}

	// Carregando para o sistema a chave que será utilizada para descriptografar o arquivo
	private void carregarChave(File arquivoConteudoChave, File arquivoChavePrivadaRSADest) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {

		//Transformando o arquivo em uma chave privada RSA para descriptografar a chave de sessão 
		PrivateKey chavePrivada = manArquivo.produzirChavePrivadaArquivo(arquivoChavePrivadaRSADest);
		
		setChaveNoArquivo(arquivoConteudoChave); //arquivo passa a ter apenas o conteudo da chave
		
		// Lendo e criando a chave de sessão AES do arquivo criptografado
		cifraRSA.init(Cipher.DECRYPT_MODE, chavePrivada);
		
		chaveAESBytes = new byte[ValoresFixos.TAM_BYTES_CHAVE_AES.getValor()];
		
		CipherInputStream leituraChave = new CipherInputStream(new FileInputStream(arquivoConteudoChave), cifraRSA);
		leituraChave.read(chaveAESBytes);
		chaveSecretaAES = new SecretKeySpec(chaveAESBytes, "AES");
		
		leituraChave.close();
	}

	// Descriptografar o arquivo
	public boolean descriptografar(File arquivoEntradaCifrado, File arquivoSaidaDecifrado, File chavePrivadaDestinatario) throws InvalidKeyException, IOException, NoSuchAlgorithmException, SignatureException, InvalidKeySpecException {
		
		// Removendo a assinatura do arquivo e salvando o conteúdo do arquivo criptografado mais a chave cifrada no mesmo arquivo
		removerAssinaturaConteudoArquivo(arquivoEntradaCifrado);
		
		// Salvando o conteúdo do arquivo cifrado, em um vetor de bytes, sem a chave
		byte[] conteudoCifradoArquivo = getConteudoArquivoSemChave(arquivoEntradaCifrado);
		
		carregarChave(arquivoEntradaCifrado, chavePrivadaDestinatario);
		
		// Salvando no arquivo o conteúdo do vetor de bytes conteudoCifradoArquivo
		FileOutputStream fos =  new FileOutputStream(arquivoEntradaCifrado);
		fos.write(conteudoCifradoArquivo);
		fos.close();
		
		cifraAES.init(Cipher.DECRYPT_MODE, chaveSecretaAES);
		
		arquivoSaidaDecifrado.createNewFile();
		
		CipherInputStream descriptografiaArquivo = new CipherInputStream(new FileInputStream(arquivoEntradaCifrado), cifraAES);
		FileOutputStream leituraArquivoSaida = new FileOutputStream(arquivoSaidaDecifrado);

		// Copiando e descriptografando o arquivo de entrada para o de saída
		int i;
		byte[] buffer = new byte[1024];
		while ((i = descriptografiaArquivo.read(buffer)) != -1) {
			leituraArquivoSaida.write(buffer, 0, i);
		}

		descriptografiaArquivo.close();
		leituraArquivoSaida.close();
		
		return true;
	}
	
	private void removerAssinaturaConteudoArquivo(File arquivo) throws FileNotFoundException, IOException {

		byte[] arquivoEntradaBytes = manArquivo.arquivoParaByte(arquivo);
		byte[] conteudoArquivoSemAssinatura = new byte[(int) arquivo.length() - ValoresFixos.TAM_LEITURA_ASSINATURA.getValor()];
		
		conteudoArquivoSemAssinatura = assinatura.getConteudoArquivoSemAssinatura(arquivoEntradaBytes);
		
		FileOutputStream fos = new FileOutputStream(arquivo);
		fos.write(conteudoArquivoSemAssinatura);
		fos.close();
	}

	public boolean verificarAssinatura(File arquivoEntrada, File chavePublicaRemetente) throws FileNotFoundException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {

		// Transformando o arquivo em uma chave pública 
		PublicKey chavePublica = manArquivo.produzirChavePublicaArquivo(chavePublicaRemetente);

		// Transformando o arquivo em um vetor de bytes
		byte[] arquivoComAssinatura = manArquivo.arquivoParaByte(arquivoEntrada);
		
		// Retornando a validade da assinatura. True para válida e false para inválida
		return assinatura.validarAssinatura(arquivoComAssinatura, chavePublica);
	}
	
	private void setChaveNoArquivo(File arquivoComChave) throws FileNotFoundException, IOException {
		
		byte[] conteudoArquivoComChave = manArquivo.arquivoParaByte(arquivoComChave);
		byte[] chaveSessaoCifrada = new byte[tamLeituraChaveAES];
		
		for(int i = 0; i < tamLeituraChaveAES; i++) {
			chaveSessaoCifrada[i] = conteudoArquivoComChave[i];
		}
		
		FileOutputStream fos = new FileOutputStream(arquivoComChave);
		fos.write(chaveSessaoCifrada);
		fos.close();
	}
	
	private byte[] getConteudoArquivoSemChave(File arquivo) throws FileNotFoundException, IOException {
		
		byte[] conteudoArquivoComChave = manArquivo.arquivoParaByte(arquivo);
		byte[] conteudoCifradoArquivo = new byte[(int) arquivo.length() - tamLeituraChaveAES];
		int j = 0;
		
		for(int i = tamLeituraChaveAES; i < arquivo.length(); i++) {
			conteudoCifradoArquivo[j] = conteudoArquivoComChave[i];
			j++;
		}
		
		return conteudoCifradoArquivo;
	}
}
