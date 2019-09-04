package org.sigaa.model;

import java.util.List;

public class PaginaMenu {
	private DadosInstitucionais dadosInstitucionais;
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
}
