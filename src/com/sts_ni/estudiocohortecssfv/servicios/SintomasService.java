package com.sts_ni.estudiocohortecssfv.servicios;

public interface SintomasService {

	public String getHorarioTurno(Integer diaSemana);

	public String getGeneralesSintomas(String paramHojaConsulta);

	public String getEstadoGeneralSintomas(String paramHojaConsulta);

	public String getGastroinstestinalSintomas(String paramHojaConsulta);

	public String getOsteomuscularSintomas(String paramHojaConsulta);

	public String getCabezaSintomas(String paramHojaConsulta);

	public String getDeshidratacionSintomas(String paramHojaConsulta);

	public String getCutaneoSintomas(String paramHojaConsulta);

	public String getGargantaSintomas(String paramHojaConsulta);

	public String getRenalSintomas(String paramHojaConsulta);

	public String getEstadoNutricionalSintomas(String paramHojaConsulta);

	public String getRespiratorioSintomas(String paramHojaConsulta);

	public String getReferenciaSintomas(String paramHojaConsulta);

	public String getVacunasSintomas(String paramHojaConsulta);

	public String getCategoriasSintomas(String paramHojaConsulta);
	
	public String guardarGeneralesSintomas(String paramHojaConsulta);

	public String validacionMatrizSintoma(String paramHojaConsulta);

	public String editarDatosPreclinicos(String paramHojaConsulta);

	public String getSeccionesSintomasCompletadas(String paramHojaConsulta);

}
