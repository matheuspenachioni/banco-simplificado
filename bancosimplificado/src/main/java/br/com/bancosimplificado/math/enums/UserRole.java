package br.com.bancosimplificado.math.enums;

public enum UserRole {
	USER("Usuário"),
	MERCHANT("Lojista");

	private String description;
	
	private UserRole(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	//...To String
	@Override
	public String toString() {
		return this.description;
	}
	
}
