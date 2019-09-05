package org.sigaa;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.sigaa.deserializer.DesserializadorPaginaMenu;
import org.sigaa.deserializer.DesserializadorPaginaTurma;
import org.sigaa.model.Autenticacao;
import org.sigaa.model.PaginaMenu;
import org.sigaa.model.PaginaTurma;
import org.sigaa.model.Turma;
import org.sigaa.model.Usuario;
import org.sigaa.model.response.RequisicaoLogin;
import org.sigaa.model.response.RequisicaoMenu;
import org.sigaa.model.response.RequisicaoTurma;

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
				.data("user.login", usuario.getUsuario())
				.data("user.senha",usuario.getSenha())
				.data("urlRedirect","")
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
				ret.setAutenticacao(getAutenticacao(usuario));
			}
		}else {
			ret.setSucesso(false);
			ret.setMensagemErro("Falha ao fazer o login");
		}
		this.usuario = ret.getAutenticacao();
		return ret;
	}
	
	/**
	 * Realiza uma requisição ao Menu do SIGAA,
	 * @return A pagina Menu, contendo as disciplinas e outros dados.
	 * @throws IOException 
	 */

	public RequisicaoMenu getMenu() throws IOException {
		RequisicaoMenu req = new RequisicaoMenu();
		if(usuario != null) {
			Response res = Jsoup.connect("https://si3.ufc.br/sigaa/verPortalDiscente.do")
					.header("Cookie", validJsessionID)
					.header("Host", "si3.ufc.br")
					.header("Referer", "https://si3.ufc.br/sigaa/pag-inaInicial.do")
					.execute();
			if(res.statusCode() == 200) {
				PaginaMenu pagina = new DesserializadorPaginaMenu().desserializar(res.parse());
				req.setPaginaMenu(pagina);
				req.setSucesso(true);
			}else {
				req.setMensagemErro("Erro, mensagem HTTP: " + res.statusCode());
				req.setSucesso(false);
			}
		}else {
			throw new RuntimeException("Para chamar o Menu é necessário efetuar login primeiro!");
		}
		return req;
	}
	
	private Autenticacao getAutenticacao(Usuario usuario) throws IOException {
		Autenticacao aut = new Autenticacao();
		
		Response res = Jsoup.connect("https://si3.ufc.br/sigaa/paginaInicial.do")
				.header("Cookie", validJsessionID)
				.header("Host", "si3.ufc.br")
				.header("Referer", "https://si3.ufc.br/sigaa/verTelaLogin.do%3bjsessionid="+validJsessionID)
				.execute();
		String nome = res.parse().getElementsByClass("nome_usuario").get(0).getElementsByTag("p").get(0).text();
		
		usuario.setNome(nome);
		aut.setUsuarioAutenticado(usuario);
		return aut;
	}
	
	public RequisicaoTurma getTurma(Turma turma) throws IOException {
		if(usuario == null) {
			throw new RuntimeException("O login é necessario para ver turma");
		}
		RequisicaoTurma req = new RequisicaoTurma();
		Response res = Jsoup.connect("https://si3.ufc.br/sigaa/portais/discente/discente.jsf")
				.data("form_acessarTurmaVirtual","form_acessarTurmaVirtual")
				.data("idTurma",String.valueOf(turma.get_id()))
				.data("javax.faces.ViewState",turma.get_jvf())
				.data("form_acessarTurmaVirtual:turmaVirtual","form_acessarTurmaVirtual:turmaVirtual")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Cookie", validJsessionID)
				.header("Referer", "https://si3.ufc.br/sigaa/portais/discente/discente.jsf")
				.method(Method.POST).execute();
		
		if(res.statusCode() == 200) {
			PaginaTurma pagina = new DesserializadorPaginaTurma().desserializar(res.parse());
			req.setPaginaTurma(pagina);
			req.setSucesso(true);
		}else {
			req.setMensagemErro("Erro, mensagem HTTP: " + res.statusCode());
			req.setSucesso(false);
		}
		
		return req;
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
		return "JSESSIONID=" + jsession.get("JSESSIONID");
	}
 	
 	/**
 	 * Realiza o logout no SIGAA
 	 * @throws IOException 
 	 */
 	public void logout() throws IOException {
 		if(usuario != null) {
 			Jsoup.connect("https://si3.ufc.br/sigaa/logar.do?dispatch=logOff")
 					.header("Cookie", validJsessionID)
 					.header("Host", "si3.ufc.br")
 					.header("Referer", "https://si3.ufc.br/sigaa/paginaInicial.do")
 					.get();
 			
 		}
 	}

	public Autenticacao getUsuario() {
		return usuario;
	}
}
