package org.sigaa.model;

public class Autenticacao {
	
	private String jsessionid;
	private Usuario usuarioAutenticado;
	
	public String getJsessionid() {
		return jsessionid;
	}
	
	public void setJsessionid(String jsessionid) {
		this.jsessionid = jsessionid;
	}
	
	public Usuario getUsuarioAutenticado() {
		return usuarioAutenticado;
	}
	
	public void setUsuarioAutenticado(Usuario usuarioAutenticado) {
		this.usuarioAutenticado = usuarioAutenticado;
	}
}
