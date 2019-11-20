package com.sts_ni.estudiocohortecssfv.servicios;

public interface ExpedienteService {


	public String getListaHojaConsultaExp(int codExpediente);
	
	public String buscarPacienteCrearHoja(int codExpediente);
	
	public String buscarHojaSeguimientoInfluenza(int numHojaInfluenza);
	
	public String buscarPacienteSeguimientoInfluenza(int codExpediente);
	
	public String guardarSeguimientoInfluenza(String paramHojaInfluenza, String paramSeguimientoInfluenza);
	
	public String getListaSeguimientoInfluenza(int paramSecHojaInfluenza);
	
	public byte[] getSeguimientoInfluenzaPdf(int paramSecHojaInfluenza);
	
	public String imprimirSeguimientoInfluenciaPdf(int paramSecHojaInfluenza);

	public String crearSeguimientoInfluenza(String paramCrearHoja);

	public String reimpresionHojaConsulta(int paramsecHojaConsulta);
	
	public String getListaSeguimientoZika(int paramSecHojaZika);
	
	public String buscarPacienteSeguimientoZika(int codExpediente);
	
	public String buscarHojaSeguimientoZika(int numHojaZika);
	
	public String crearSeguimientoZika(String paramCrearHoja);
	
	public String guardarSeguimientoZika(String paramHojaZika, String paramSeguimientoZika);
	
	public byte[] getSeguimientoZikaPdf(int paramSecHojaZika);
	
	public String imprimirSeguimientoZikaPdf(int paramSecHojaZika);
	
	public String buscarFichaVigilanciaIntegrada(int codExpediente, int numHojaConsulta);
	
	public String guardarFichaVigilanciaIntegrada(String paramVigilanciaIntegrada);
	
	public String getDepartamentos();
	
	public String getMunicipios(int divisionpoliticaId);
	
	public byte[] getFichaPdf(Integer secVigilanciaIntegrada);
	
	public void imprimirFichaPdf(int secVigilanciaIntegrada);
}
