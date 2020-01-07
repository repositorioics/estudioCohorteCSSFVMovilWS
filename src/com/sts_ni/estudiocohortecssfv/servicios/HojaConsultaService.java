package com.sts_ni.estudiocohortecssfv.servicios;

public interface HojaConsultaService {

	public String getHojaConsultaTest(Integer secHojaConsulta);

	public String getListaInicioEnfermeria();
	
	public String enviarDatosPreclinicos(String hojaConsulta);
	
	public String cancelarDatosPreclinicos(String paramHojaConsulta);

	public String noAtiendeLlamadoDatosPreclinicos(String paramHojaConsulta);

	public String buscarPaciente(String paramPaciente);
	
	public String guardarPacienteEmergencia(String paramHojaConsulta);

	public String getListaInicioHojaConsulta();
	
	public String  getListaHojaConsultaPorExpediente(int codExpediente, boolean esEnfermeria);
	
	public String getDatosCabeceraSintomas(Integer codExpediente, Integer secHojaConsulta);
	
	public String getHojaConsultaPorNumero(Integer secHojaConsulta);

	public String guardarEstadoGeneralSintomas(String paramHojaConsulta);

	public String guardarGastroInSintomas(String paramHojaConsulta);

	public String guardarOsteomuscularSintomas(String paramHojaConsulta);

	public String guardarCabezaSintomas(String paramHojaConsulta);

	public String guardarDeshidraSintomas(String paramHojaConsulta);

	public String guardarCutaneoSintomas(String paramHojaConsulta);

	public String guardarGargantaSintomas(String paramHojaConsulta);

	public String guardarRenalSintomas(String paramHojaConsulta);

	public String guardarEstadoNutriSintomas(String paramHojaConsulta);

	public String guardarRespiratorioSintomas(String paramHojaConsulta);

	public String guardarReferenciaSintomas(String paramHojaConsulta);

	public String guardarVacunaSintomas(String paramHojaConsulta);
	
	public String validarNoConsentimientoDengue(Integer codExpediente);

	public String guardarCategoriaSintomas(String paramHojaConsulta);
	
	//=================================================
	public String getDatosCabeceraExamenes(Integer codExpediente, Integer secHojaConsulta);

	public String guardarExamenes(String paramHojaConsulta);

	public String buscarExamenes(Integer paramSecHojaConsulta);

	public String buscarExamenesChekeados(Integer paramSecHojaConsulta);

	public String guardarOtroExamen(String paramHojaConsulta);

	public String actualizarEstadoEnConsulta(String paramHojaConsulta,boolean cambioTurno );
	
	public String getListaMedicos();
	
	public String getListaMedicos(String paramNombre);

	public String actualizarEstadoEnfermeria(String paramHojaConsulta);

}
