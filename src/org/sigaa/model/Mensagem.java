package org.sigaa.model;

import java.util.List;

public class Mensagem {
	private String titulo;
	private List<String> arquivos;
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public List<String> getArquivos() {
		return arquivos;
	}
	public void setArquivos(List<String> arquivos) {
		this.arquivos = arquivos;
	}
}
