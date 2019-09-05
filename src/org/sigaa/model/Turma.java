package org.sigaa.model;

public class Turma {
	private String nome;
	private String codigo;
	private String local;
	private String horario;
	private int _id;
	private String _jvf;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String get_jvf() {
		return _jvf;
	}
	public void set_jvf(String _jvf) {
		this._jvf = _jvf;
	}
}
