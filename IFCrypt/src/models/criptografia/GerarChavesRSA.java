package models.criptografia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;

import models.util.ValoresFixos;

public class GerarChavesRSA {
	
	private KeyPairGenerator geradorParChaves;
	private SecureRandom random;
	private KeyPair parChaves;
	
	public GerarChavesRSA() throws NoSuchAlgorithmException {
		
		geradorParChaves =  KeyPairGenerator.getInstance("RSA");
		random = SecureRandom.getInstanceStrong();
		geradorParChaves.initialize(ValoresFixos.TAM_CHAVE_RSA.getValor(), random);
		
		parChaves = geradorParChaves.generateKeyPair();
	}
	
	public boolean gerarChavePublica(File localSalvarChavePub) throws IOException {
		
		localSalvarChavePub.createNewFile();
		
		PublicKey pub = parChaves.getPublic();
		byte[] chavePub = pub.getEncoded();
		
		String chavePubString = Base64.getEncoder().encodeToString(chavePub);
		
		FileOutputStream salvarChavePub = new FileOutputStream(localSalvarChavePub);
		salvarChavePub.write(chavePubString.getBytes());
		salvarChavePub.close();
		
		return true;
	}
	
	public boolean gerarChavePrivada(File localSalvarChavePriv) throws IOException {
		
		localSalvarChavePriv.createNewFile();
		
		PrivateKey priv = parChaves.getPrivate();
		byte[] chavePriv = priv.getEncoded();
		
		String chavePrivString = Base64.getEncoder().encodeToString(chavePriv);
		
		FileOutputStream salvarChavePriv = new FileOutputStream(localSalvarChavePriv);
		salvarChavePriv.write(chavePrivString.getBytes());
		salvarChavePriv.close();
		
		return true;
	}
}
