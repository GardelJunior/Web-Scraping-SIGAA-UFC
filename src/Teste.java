import java.util.*;

import org.jsoup.*;
import org.jsoup.Connection.*;
import org.jsoup.helper.HttpConnection.Request;
import org.jsoup.nodes.*;
import org.sigaa.*;
import org.sigaa.model.*;
import org.sigaa.model.response.*;

import com.sun.tools.jconsole.JConsoleContext.*;

public class Teste {
	public static void main(String[] args) {
		try {
			Response res = Jsoup.connect("https://si3.ufc.br/sigaa/verTelaLogin.do").execute();
			Map<String, String> jsession = res.cookies();
			Document html = res.parse();
			System.out.println(html.title());
			
			for(String hd : jsession.keySet()) {
				System.out.println("Cabeçalho: " + hd);
				System.out.println("Valor: "+ jsession.get(hd));
			}
			
			Response r = Jsoup.connect("https://si3.ufc.br/sigaa/logar.do?dispatch=logOn")
					.data("user.login", "")
					.data("user.senha","")
					.data("urlRedisrect","")
					.data("entrar","Entrar")
					.data("width","0")
					.data("height","0")
					.data("acao","")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("Cookie", jsession.get("JSESSIONID"))
					.header("Referer", "https://si3.ufc.br/sigaa/verTelaLogin.do")
					.method(Method.POST).execute();
			
			Document rr = r.parse();
			
			String body = rr.body().text();
			
			if(body.contains("Usuário e/ou senha inválidos")) {
				System.out.println("Usuário e/ou Senha inválido!");
			}else if(body.contains("Tentativa de acesso por aplicativo externo. Operação negada!")){
				System.out.println("Aplicação externa negada!");
			}else if(body.contains("Por favor, aguarde enquanto carregamos as suas")){
				System.out.println("OK PORRA!");
			}
			
			
			
			SigaaAPI api = new SigaaAPI();
			RequisicaoLogin login = api.login(new Usuario("", ""));
			if(login.isSucesso()) {
				Usuario logado = login.getAutenticacao().getUsuarioAutenticado();
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
