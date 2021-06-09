package models.arquivo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ManipulacaoArquivo {

	private FileInputStream fis;

	public byte[] arquivoParaByte(File arquivo) throws FileNotFoundException, IOException {

		byte[] arquivoBytes = new byte[(int) arquivo.length()];
		fis = new FileInputStream(arquivo);
		fis.read(arquivoBytes);
		fis.close();

		return arquivoBytes;
	}

	public PublicKey produzirChavePublicaArquivo(File arquivoChavePublica) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {

		byte[] leituraChavePublicaRSA = new byte[(int) arquivoChavePublica.length()];
		fis = new FileInputStream(arquivoChavePublica);
		fis.read(leituraChavePublicaRSA);
		fis.close();
		
		byte[] chavePublicaRSABytes = Base64.getDecoder().decode(leituraChavePublicaRSA);

		X509EncodedKeySpec chavePublicaRSA = new X509EncodedKeySpec(chavePublicaRSABytes);
		KeyFactory fabricaChave = KeyFactory.getInstance("RSA");

		return fabricaChave.generatePublic(chavePublicaRSA);
	}

	public PrivateKey produzirChavePrivadaArquivo(File arquivoChavePrivada) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {

		byte[] leituraChavePrivadaRSA = new byte[(int) arquivoChavePrivada.length()];
		fis = new FileInputStream(arquivoChavePrivada);
		fis.read(leituraChavePrivadaRSA);
		fis.close();
		
		byte[] chavePrivadaRSABytes = Base64.getDecoder().decode(leituraChavePrivadaRSA);

		PKCS8EncodedKeySpec chavePrivadaRSA = new PKCS8EncodedKeySpec(chavePrivadaRSABytes);
		KeyFactory fabricaChave = KeyFactory.getInstance("RSA");

		return fabricaChave.generatePrivate(chavePrivadaRSA);
	}
}
