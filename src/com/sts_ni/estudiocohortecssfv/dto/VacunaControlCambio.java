package com.sts_ni.estudiocohortecssfv.dto;

import java.util.Date;

public class VacunaControlCambio {

	Character lactanciaMaterna;
	Character vacunasCompletas;
	Character vacunaInfluenza;
	Date fechaVacuna;
	String usuario;
	String controlador;
	
	public Character getLactanciaMaterna() {
		return lactanciaMaterna;
	}
	public void setLactanciaMaterna(Character lactanciaMaterna) {
		this.lactanciaMaterna = lactanciaMaterna;
	}
	public Character getVacunasCompletas() {
		return vacunasCompletas;
	}
	public void setVacunasCompletas(Character vacunasCompletas) {
		this.vacunasCompletas = vacunasCompletas;
	}
	public Character getVacunaInfluenza() {
		return vacunaInfluenza;
	}
	public void setVacunaInfluenza(Character vacunaInfluenza) {
		this.vacunaInfluenza = vacunaInfluenza;
	}
	
	public Date getFechaVacuna() {
		return fechaVacuna;
	}
	public void setFechaVacuna(Date fechaVacuna) {
		this.fechaVacuna = fechaVacuna;
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
