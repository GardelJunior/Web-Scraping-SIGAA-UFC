package org.sigaa.model;

import java.util.List;

public class PaginaMenu {
	private DadosInstitucionais dadosInstitucionais;
	private String imagemPerfil;
	private List<Turma> turmas;
	
	public DadosInstitucionais getDadosInstitucionais() {
		return dadosInstitucionais;
	}
	public void setDadosInstitucionais(DadosInstitucionais dadosInstitucionais) {
		this.dadosInstitucionais = dadosInstitucionais;
	}
	public List<Turma> getTurmas() {
		return turmas;
	}
	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}
	public String getImagemPerfil() {
		return imagemPerfil;
	}
	public void setImagemPerfil(String imagemPerfil) {
		this.imagemPerfil = imagemPerfil;
	}
}
