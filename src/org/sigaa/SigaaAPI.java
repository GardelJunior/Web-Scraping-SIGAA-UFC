package org.sigaa;

import java.io.*;
import java.util.*;

import org.jsoup.*;
import org.jsoup.Connection.*;
import org.jsoup.nodes.*;
import org.sigaa.model.*;
import org.sigaa.model.response.*;

public class SigaaAPI {
	
	private String validJsessionID = null;
	private Autenticacao usuario = null;
	
	/**
	 * Realiza o login no Sigaa
	 * @param usuario : usuário com informações de login
	 * @return usuário logado com seus dados preenchidos.
	 * @throws IOException 
	 */
	public RequisicaoLogin login(Usuario usuario) throws IOException {
		Response res = null;
		if(validJsessionID == null) {
			validJsessionID = getValidJsessionID();
		}
		res = Jsoup.connect("https://si3.ufc.br/sigaa/logar.do?dispatch=logOn")
				.data("user.login", "")
				.data("user.senha","")
				.data("urlRedisrect","")
				.data("entrar","Entrar")
				.data("width","0")
				.data("height","0")
				.data("acao","")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Cookie", validJsessionID)
				.header("Referer", "https://si3.ufc.br/sigaa/verTelaLogin.do")
				.method(Method.POST).execute();
		
		RequisicaoLogin ret = new RequisicaoLogin();
		
		if(res != null) {
			String body = ((Document)res.parse()).body().text();
			
			if(body.contains("Usuário e/ou senha inválidos")) {
				ret.setSucesso(false);
				ret.setMensagemErro("Usuário e/ou senha inválidos");
			}else if(body.contains("Tentativa de acesso por aplicativo externo. Operação negada!")){
				ret.setSucesso(false);
				ret.setMensagemErro("Operação Negada");
			}else if(body.contains("Por favor, aguarde enquanto carregamos as suas")){
				ret.setSucesso(true);
			}
			
		}else {
			ret.setSucesso(false);
			ret.setMensagemErro("Falha ao fazer o login");
		}
		return ret;
	}

	
	private String getValidJsessionID() {
		Response res;
		Map<String, String> jsession = null;
		try {
			res = Jsoup.connect("https://si3.ufc.br/sigaa/verTelaLogin.do").execute();
			jsession = res.cookies();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsession.get("JSESSIONID");
	}
}
