package org.sigaa.disserializer;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.sigaa.model.DadosInstitucionais;
import org.sigaa.model.Turma;
import org.sigaa.model.PaginaMenu;

public class DesserializadorPaginaMenu {
	
	public PaginaMenu desserializar(Document documento) {
		PaginaMenu menu = new PaginaMenu();
		
		List<Turma> turmas = new ArrayList<Turma>();
		/* Pegar as turmas */
		documento.getElementById("turmas-portal")
		.getElementsByTag("tbody")
		.get(0).children().forEach(disciplina -> {
			Turma dis = new Turma();
			Elements dados = disciplina.children();
			if(dados.size()==1) return;
			
			final String nome = dados.get(0).text();
			final String codigo = dados.get(1).text();
			final String local = dados.get(2).text();
			final String horario = dados.get(3).text();
			
			dis.setNome(nome);
			dis.setCodigo(codigo);
			dis.setLocal(local);
			dis.setHorario(horario);
			
			turmas.add(dis);
		});
		
		menu.setTurmas(turmas);
		
		/* Pegar os dados institucionais */
		DadosInstitucionais dadosInstitucionais = new DadosInstitucionais();
		Elements dados = documento.getElementById("agenda-docente")
		.getElementsByTag("tbody").get(0)
		.children();
		
		final String matricula		= dados.get(0).children().get(1).text().trim();
		final String curso			= dados.get(1).children().get(1).text().trim();
		final String nivel			= dados.get(2).children().get(1).text().trim();
		final String status		= dados.get(3).children().get(1).text().trim();
		final String email			= dados.get(4).children().get(1).text().trim();
		final String entrada		= dados.get(5).children().get(1).text().trim();
		
		dadosInstitucionais.setMatricula(Integer.parseInt(matricula));
		dadosInstitucionais.setCurso(curso);
		dadosInstitucionais.setNivel(nivel);
		dadosInstitucionais.setStatus(status);
		dadosInstitucionais.setEmail(email);
		dadosInstitucionais.setEntrada(entrada);
		
		menu.setDadosInstitucionais(dadosInstitucionais);
		return menu;
	}
}
