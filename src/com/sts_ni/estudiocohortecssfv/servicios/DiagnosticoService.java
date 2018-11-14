package com.sts_ni.estudiocohortecssfv.servicios;

public interface DiagnosticoService {

	public String getListaDiagnostico();

	public String guardarExamenHistoricoHojaConsulta(String paramHojaConsulta);

	public String guardarTratamientoHojaConsulta(String paramHojaConsulta);

	public String guardarPlanesHojaConsulta(String paramHojaConsulta);

	public String guardarDiagnosticoHojaConsulta(String paramHojaConsulta);

	public String guardarProximaCitaHojaConsulta(String paramHojaConsulta);

	public String obtenerDatosPaciente(int codExpediente);

	public String getTodasEscuela();

	public String getSeccionesDiagnosticoCompletadas(String paramHojaConsulta);

}
