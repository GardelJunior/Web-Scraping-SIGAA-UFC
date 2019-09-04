package org.sigaa.model.response;

import org.sigaa.model.PaginaMenu;

public class RequisicaoMenu {
	private boolean sucesso;
	private String mensagemErro;
	private PaginaMenu paginaMenu;
	
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
	public PaginaMenu getPaginaMenu() {
		return paginaMenu;
	}
	public void setPaginaMenu(PaginaMenu paginaMenu) {
		this.paginaMenu = paginaMenu;
	}
}
