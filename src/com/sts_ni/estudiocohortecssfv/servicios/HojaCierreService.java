package com.sts_ni.estudiocohortecssfv.servicios;

public interface HojaCierreService {

	public String procesoCierre(String paramHojaConsulta);

	public String procesoCambioTurno(String paramHojaConsulta);

	public String procesoAgregarHojaConsulta(String paramHojaConsulta);

	public String cargarGrillaCierre(String paramHojaConsulta);

	public String procesoCancelar(String paramHojaConsulta);

	public String noAtiendeLlamadoCierre(String paramHojaConsulta);

	public String validarSalirHojaConsulta(String paramHojaConsulta);

}
