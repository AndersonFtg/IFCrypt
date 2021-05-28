package models.util;

public enum ValoresFixos {
	
	TAM_BITS_CHAVE_AES(256),
	TAM_BYTES_CHAVE_AES(32),
	TAM_CHAVE_RSA(1024),
	TAM_LEITURA_CHAVE_AES(128),
	TAM_LEITURA_ASSINATURA(128);
	
	private int valor;
	
	private ValoresFixos(int valor) {
		this.valor = valor;
	}
	
	public int getValor() {
		return valor;
	}
}
