package org.sigaa.model.response;

import org.sigaa.model.PaginaTurma;

public class RequisicaoTurma {
	private boolean sucesso;
	private String mensagemErro;
	private PaginaTurma paginaTurma;
	
	public boolean isSucesso() {
		return sucesso;
	}
	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}
	public String getMensagemErro() {
		return mensagemErro;
	}
	public void setMensagemErro(String mensagemErro) {
		this.mensagemErro = mensagemErro;
	}
	public PaginaTurma getPaginaTurma() {
		return paginaTurma;
	}
	public void setPaginaTurma(PaginaTurma paginaTurma) {
		this.paginaTurma = paginaTurma;
	}
}
