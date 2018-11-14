package com.sts_ni.estudiocohortecssfv.dto;

public class RenalControlCambio {

	Character sintomasUrinarios;
	Character leucocituria;
	Character nitritos;
	Character eritrocitos;
	Character bilirrubinuria;
	String usuario;
	String controlador;
	
	public Character getSintomasUrinarios() {
		return sintomasUrinarios;
	}
	public void setSintomasUrinarios(Character sintomasUrinarios) {
		this.sintomasUrinarios = sintomasUrinarios;
	}
	public Character getLeucocituria() {
		return leucocituria;
	}
	public void setLeucocituria(Character leucocituria) {
		this.leucocituria = leucocituria;
	}
	public Character getNitritos() {
		return nitritos;
	}
	public void setNitritos(Character nitritos) {
		this.nitritos = nitritos;
	}
	public Character getEritrocitos() {
		return eritrocitos;
	}
	public void setEritrocitos(Character eritrocitos) {
		this.eritrocitos = eritrocitos;
	}
	public Character getBilirrubinuria() {
		return bilirrubinuria;
	}
	public void setBilirrubinuria(Character bilirrubinuria) {
		this.bilirrubinuria = bilirrubinuria;
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
