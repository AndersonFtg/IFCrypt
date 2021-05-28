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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import models.arquivo.ManipulacaoArquivo;
import models.util.ValoresFixos;

public class CriptografarArquivo {
	
	private Cipher cifraAES, cifraRSA;
	private byte[] chaveAESBytes; // representa a chave em um vetor de bytes
	private SecretKeySpec chaveSecretaAES; // constroi a chave (SecretKey) com base no vetor de bytes
	private AssinaturaDigital assinatura;
	private ManipulacaoArquivo manArquivo;
	private File arquivoCifrado;

	// Construtor
	public CriptografarArquivo() throws NoSuchAlgorithmException, NoSuchPaddingException {
		
		assinatura = new AssinaturaDigital();
		manArquivo = new ManipulacaoArquivo();
		
		cifraAES = Cipher.getInstance("AES");
		cifraRSA = Cipher.getInstance("RSA");
	}

	// Gerando a chave que será utilizada para criptografar o arquivo
	private void gerarChave() throws NoSuchAlgorithmException {
		
		KeyGenerator geradorChave = KeyGenerator.getInstance("AES");
		geradorChave.init(ValoresFixos.TAM_BITS_CHAVE_AES.getValor()); // definindo o tamanho (em bits) da chave
		SecretKey chave = geradorChave.generateKey(); // gera a chave AES
		chaveAESBytes = chave.getEncoded(); // transformando a chave em bytes
		chaveSecretaAES = new SecretKeySpec(chaveAESBytes, "AES"); // construindo a chave AES com base no vetor de bytes
	}

	// Criptografando o arquivo
	public boolean criptografar(File arquivoEntrada, File arquivoSaida, File chavePublicaDest, File chavePrivadaRem) throws InvalidKeyException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		gerarChave();
		
		this.arquivoCifrado = arquivoSaida;
		
		// Criptografando o arquivo com a chave de sessão AES
		cifraAES.init(Cipher.ENCRYPT_MODE, chaveSecretaAES);

		arquivoCifrado.createNewFile();

		FileInputStream leituraArquivoEntrada = new FileInputStream(arquivoEntrada); // criando uma conexão com o arquivo que será criptografado
		CipherOutputStream criptografiaArquivo = new CipherOutputStream(new FileOutputStream(arquivoCifrado), cifraAES);

		// Copiando e criptografando o arquivo de entrada para o de saída
		int i;
		byte[] buffer = new byte[1024];
		while ((i = leituraArquivoEntrada.read(buffer)) != -1) {
			criptografiaArquivo.write(buffer, 0, i);
		}

		criptografiaArquivo.close();
		leituraArquivoEntrada.close();
	
		// Salvando a chave de sessão no inicio do mesmo arquivo que foi criptografado
		salvarChaveSecreta(chavePublicaDest);
		
		// Assinatura Digital
		return assinarDocumento(chavePrivadaRem);
	}

	private void salvarChaveSecreta(File arquivoChavePublicaRSADest) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
		
		byte[] arquivoCifradoBytes = manArquivo.arquivoParaByte(arquivoCifrado); // Salvo o conteúdo do arquivo cifrado com o AES

		// Criando a chave pública que está no arquivo
		PublicKey chavePublica = manArquivo.produzirChavePublicaArquivo(arquivoChavePublicaRSADest);

		// Encriptando a chave secreta com a chave pública e salvando no arquivo
		cifraRSA.init(Cipher.ENCRYPT_MODE, chavePublica);
		
		CipherOutputStream criptografiaChave = new CipherOutputStream(new FileOutputStream(arquivoCifrado), cifraRSA);
		criptografiaChave.write(chaveAESBytes);
		criptografiaChave.close();

		byte[] chaveCifradaBytes = manArquivo.arquivoParaByte(arquivoCifrado);
		
		FileOutputStream fos = new FileOutputStream(arquivoCifrado);
		fos.write(chaveCifradaBytes);
		fos.write(arquivoCifradoBytes);
		fos.close();
	}

	private boolean assinarDocumento(File arquivoChavePrivadaRSARem) throws NoSuchAlgorithmException, FileNotFoundException, IOException, InvalidKeySpecException, InvalidKeyException, SignatureException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		PrivateKey chavePrivada = manArquivo.produzirChavePrivadaArquivo(arquivoChavePrivadaRSARem);
		byte[] arquivoPDFBytes = manArquivo.arquivoParaByte(arquivoCifrado);
		byte[] assinaturaArquivo = assinatura.gerarAssinatura(arquivoPDFBytes, chavePrivada); // retorna vetor de bytes com a assinatura
		
		return assinatura.setAssinaturaNoArquivo(arquivoCifrado, assinaturaArquivo); // anexa a assinatura ao início do arquivo
	}
}
