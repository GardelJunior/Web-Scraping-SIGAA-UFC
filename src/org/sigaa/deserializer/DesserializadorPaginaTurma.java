package org.sigaa.deserializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.nodes.Document;
import org.sigaa.model.Mensagem;
import org.sigaa.model.Noticia;
import org.sigaa.model.PaginaTurma;

public class DesserializadorPaginaTurma {
	public PaginaTurma desserializar(Document documento) {
		PaginaTurma turma = new PaginaTurma();
		
		/* Pega o nome da turma */
		String nomeTurma = documento.getElementById("nomeTurma").text();
		turma.setNomeTurma(nomeTurma);
		
		/* Pega as noticias */
		List<Noticia> noticias = new ArrayList<Noticia>();
		List<String> txtNoticias = Stream.of(documento.getElementById("j_id_jsp_1287906063_60_body")
				.text().split("\n")).map(c -> c.trim()).filter(c -> c.length() > 0)
				.collect(Collectors.toList());
		
		//TODO: Ajustar o split das noticias, não está funcionando.
		
		if(txtNoticias.size() >= 3) {
			for(int i = 0 ; i < txtNoticias.size(); i += 3) {
				final int index = i;
				noticias.add(new Noticia() {{
						setData(txtNoticias.get(index));
						setTitulo(txtNoticias.get(index+1));
						setEscritor("");
						setNoticia("");
					}});
			}
		}else {
			noticias.add(new Noticia() {{ setTitulo("Nenhuma Noticia Cadastrada"); }});
		}
		
		turma.setNoticias(noticias);
		
		/* Pega as Mensagens */
		List<Mensagem> mensagens = new ArrayList<Mensagem>();
		
		documento.getElementsByClass("topico-aula").forEach(topico -> {
			List<String> arquivos = new ArrayList<>();
			
			topico.child(1).getElementsByTag("a").forEach(c -> arquivos.add(c.text()));
			
			mensagens.add(new Mensagem() {{
				setTitulo(topico.child(0).text());
				setArquivos(arquivos);
			}});
		});
		
		turma.setMensagens(mensagens);
		return turma;
	}
}
