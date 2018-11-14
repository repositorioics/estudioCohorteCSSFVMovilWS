package com.sts_ni.estudiocohortecssfv.dto;

import java.math.BigDecimal;

public class EstadoNutricionalControlCambio {

	BigDecimal imc;
	Character obeso;
	Character sobrepeso;
	Character sospechaProblema;
	Character normal;
	Character bajoPeso;
	Character bajoPesoSevero;
	String usuario;
	String controlador;
	
	public BigDecimal getImc() {
		return imc;
	}
	public void setImc(BigDecimal imc) {
		this.imc = imc;
	}
	public Character getObeso() {
		return obeso;
	}
	public void setObeso(Character obeso) {
		this.obeso = obeso;
	}
	public Character getSobrepeso() {
		return sobrepeso;
	}
	public void setSobrepeso(Character sobrepeso) {
		this.sobrepeso = sobrepeso;
	}
	public Character getSospechaProblema() {
		return sospechaProblema;
	}
	public void setSospechaProblema(Character sospechaProblema) {
		this.sospechaProblema = sospechaProblema;
	}
	public Character getNormal() {
		return normal;
	}
	public void setNormal(Character normal) {
		this.normal = normal;
	}
	public Character getBajoPeso() {
		return bajoPeso;
	}
	public void setBajoPeso(Character bajoPeso) {
		this.bajoPeso = bajoPeso;
	}
	public Character getBajoPesoSevero() {
		return bajoPesoSevero;
	}
	public void setBajoPesoSevero(Character bajoPesoSevero) {
		this.bajoPesoSevero = bajoPesoSevero;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getControlador() {
		return controlador;
	}
	public void setControlador(String controlador) {
		this.controlador = controlador;
	}
}
