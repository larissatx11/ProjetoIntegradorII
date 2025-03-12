package Classes;

public class Cartao {
private int id;
private int numero;
private String tipo;
private String bandeira;
private Double limite;
private Double valor_atual;
private int fechamento;
private int usuario_id;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getNumero() {
	return numero;
}
public void setNumero(int numero) {
	this.numero = numero;
}
public String getTipo() {
	return tipo;
}
public void setTipo(String tipo) {
	this.tipo = tipo;
}
public String getBandeira() {
	return bandeira;
}
public void setBandeira(String bandeira) {
	this.bandeira = bandeira;
}
public Double getLimite() {
	return limite;
}
public void setLimite(Double limite) {
	this.limite = limite;
}
public Double getValor_atual() {
	return valor_atual;
}
public void setValor_atual(Double valor_atual) {
	this.valor_atual = valor_atual;
}
public int getFechamento() {
	return fechamento;
}
public void setFechamento(int fechamento) {
	this.fechamento = fechamento;
}
public int getUsuario_id() {
	return usuario_id;
}
public void setUsuario_id(int usuario_id) {
	this.usuario_id = usuario_id;
}

}
