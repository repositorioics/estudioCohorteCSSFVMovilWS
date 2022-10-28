package com.sts_ni.estudiocohortecssfv.servicios;

public interface HojaConsultaOfflineService {

	public String getHojasConsultasOffline();
	//public List<HojaConsulta> getHojasConsultasOffline();
	
	public String getUsuariosOffline();
	
	public String getRolesOffline();
	
	public String getEstudiosOffline();
	
	public String getParticipantesOffline();
	
	public String getEstadosHojasOffline();
	
	public String getConsEstudiosOffline();
	
	public String getCantidadHojasConsultas();

	public String guardarHojaConsultaOffline(String paramHojaConsulta);
	
}
