package test;

import java.io.IOException;

import org.sigaa.SigaaAPI;
import org.sigaa.model.Turma;
import org.sigaa.model.Usuario;
import org.sigaa.model.response.RequisicaoMenu;
import org.sigaa.model.response.RequisicaoTurma;

public class Test {
	public static void main(String[] args) throws IOException {
		SigaaAPI api = new SigaaAPI();
		if(api.login(new Usuario("usuario", "senha")).isSucesso()) {
			RequisicaoMenu menu = api.getMenu();
			
			for(Turma t : menu.getPaginaMenu().getTurmas()) {
				RequisicaoTurma turma = api.getTurma(t);
				
				System.out.println(turma.getPaginaTurma().getNomeTurma());
				
				System.out.println("Mensagens:");
				
				turma.getPaginaTurma().getMensagens().forEach(m -> {
					System.out.println(m.getTitulo());
					m.getArquivos().forEach(a -> {
						System.out.println(" -" + a);
					});
				});
				
				System.out.println("Noticias:");
				turma.getPaginaTurma().getNoticias().forEach(m -> {
					System.out.println(" -"+m.getTitulo());
					System.out.println(" --"+m.getData());
					System.out.println(" --"+m.getNoticia());
				});
			}
			
			api.logout();
		}
	}
}
