package com.sts_ni.estudiocohortecssfv.dto;

import java.util.Date;

public class FichaEpiSindromesFebriles {
	private int codExpediente;
	private String categoria;
	private int numHojaConsulta;
	private Date fechaConsulta;
	private Date fis;
	private Date fif;
	private String expedienteFisico;
	private String nombreApellido;
	private String tutor;
	private char sexo;
	private Date fechaNac;
	private String edadCalc;
	private String nombreMedico;
	private String codigoPersonal;
	private String estudiosParticipantes;
	private String direccion;
	
	public int getCodExpediente() {
		return codExpediente;
	}
	public void setCodExpediente(int codExpediente) {
		this.codExpediente = codExpediente;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public int getNumHojaConsulta() {
		return numHojaConsulta;
	}
	public void setNumHojaConsulta(int numHojaConsulta) {
		this.numHojaConsulta = numHojaConsulta;
	}
	public Date getFechaConsulta() {
		return fechaConsulta;
	}
	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}
	public Date getFis() {
		return fis;
	}
	public void setFis(Date fis) {
		this.fis = fis;
	}
	public Date getFif() {
		return fif;
	}
	public void setFif(Date fif) {
		this.fif = fif;
	}
	public String getExpedienteFisico() {
		return expedienteFisico;
	}
	public void setExpedienteFisico(String expedienteFisico) {
		this.expedienteFisico = expedienteFisico;
	}
	public String getNombreApellido() {
		return nombreApellido;
	}
	public void setNombreApellido(String nombreApellido) {
		this.nombreApellido = nombreApellido;
	}
	public String getTutor() {
		return tutor;
	}
	public void setTutor(String tutor) {
		this.tutor = tutor;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	public Date getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}
	public String getEdadCalc() {
		return edadCalc;
	}
	public void setEdadCalc(String edadCalc) {
		this.edadCalc = edadCalc;
	}
	public String getNombreMedico() {
		return nombreMedico;
	}
	public void setNombreMedico(String nombreMedico) {
		this.nombreMedico = nombreMedico;
	}
	public String getCodigoPersonal() {
		return codigoPersonal;
	}
	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}
	public String getEstudiosParticipantes() {
		return estudiosParticipantes;
	}
	public void setEstudiosParticipantes(String estudiosParticipantes) {
		this.estudiosParticipantes = estudiosParticipantes;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
