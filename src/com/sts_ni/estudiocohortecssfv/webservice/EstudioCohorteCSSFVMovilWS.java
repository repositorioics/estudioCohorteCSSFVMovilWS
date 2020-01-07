package com.sts_ni.estudiocohortecssfv.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.sts.ni.estudiocohortecssfv.datos.diagnostico.DiagnosticoDA;
import com.sts_ni.estudiocohortecssfv.datos.controlcambios.ControlCambiosDA;
import com.sts_ni.estudiocohortecssfv.datos.enfermeria.EnfermeriaDA;
import com.sts_ni.estudiocohortecssfv.datos.inicio.ExpedienteDA;
import com.sts_ni.estudiocohortecssfv.datos.inicio.HojaCierreDA;
import com.sts_ni.estudiocohortecssfv.datos.inicio.HojaConsultaDA;
import com.sts_ni.estudiocohortecssfv.datos.inicio.HojaConsultaReporteDA;
import com.sts_ni.estudiocohortecssfv.datos.sintomas.SintomasDA;
import com.sts_ni.estudiocohortecssfv.servicios.ControlCambiosService;
import com.sts_ni.estudiocohortecssfv.servicios.DiagnosticoService;
import com.sts_ni.estudiocohortecssfv.servicios.EnfermeriaService;
import com.sts_ni.estudiocohortecssfv.servicios.ExpedienteService;
import com.sts_ni.estudiocohortecssfv.servicios.HojaCierreService;
import com.sts_ni.estudiocohortecssfv.servicios.HojaConsultaReporteService;
import com.sts_ni.estudiocohortecssfv.servicios.HojaConsultaService;
import com.sts_ni.estudiocohortecssfv.servicios.SintomasService;

@WebService(name = "EstudioCohorteCSSFVMovilWSService")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public class EstudioCohorteCSSFVMovilWS {

	private HojaConsultaService HOJA_CONSULTA_SERVICE = new HojaConsultaDA();
	private SintomasService SINTOMAS_SERVICE = new SintomasDA();
	private HojaCierreService HOJA_CIERRE_SERVICE = new HojaCierreDA();

	private DiagnosticoService DIAGNOSTICO_SERVICE = new DiagnosticoDA();
	private ExpedienteService EXPEDIENTE_SERVICE = new ExpedienteDA();
	private EnfermeriaService ENFERMERIA_SERVICE = new EnfermeriaDA();

	private HojaConsultaReporteService HOJA_CONSULTA_REPORTE_SERVICE = new HojaConsultaReporteDA();
	private ControlCambiosService CONTROL_CAMBIOS_SERVICE = new ControlCambiosDA();

	@WebMethod(operationName = "getHojaConsultaTest")
	@WebResult(name = "listaHojaResultadoJSON")
	public String getHojaConsultaTest(
			@WebParam(name = "secHojaConsulta", partName = "secHojaConsulta", mode = Mode.IN) Integer secHojaConsulta) {
		return HOJA_CONSULTA_SERVICE.getHojaConsultaTest(secHojaConsulta);
	}

	@WebMethod(operationName = "getListaInicioEnfermeria")
	@WebResult(name = "listaInicioEnfermeriaJSON")
	public String getListaInicioEnfermeria() {
		return HOJA_CONSULTA_SERVICE.getListaInicioEnfermeria();
	}

	@WebMethod(operationName = "enviarDatosPreclinicos")
	@WebResult(name = "enviarDatosPreclinicosJSON")
	public String enviarDatosPreclinicos(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE.enviarDatosPreclinicos(paramHojaConsulta);
	}

	@WebMethod(operationName = "cancelarDatosPreclinicos")
	@WebResult(name = "cancelarDatosPreclinicosJSON")
	public String cancelarDatosPreclinicos(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE
				.cancelarDatosPreclinicos(paramHojaConsulta);
	}

	@WebMethod(operationName = "noAtiendeLlamadoDatosPreclinicos")
	@WebResult(name = "noAtiendeLlamadoDatosPreclinicosJSON")
	public String noAtiendeLlamadoDatosPreclinicos(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE
				.noAtiendeLlamadoDatosPreclinicos(paramHojaConsulta);
	}

	@WebMethod(operationName = "buscarPaciente")
	@WebResult(name = "buscarPacienteJSON")
	public String buscarPaciente(
			@WebParam(name = "paramPaciente", partName = "paramPaciente", mode = Mode.IN) String paramPaciente) {
		return HOJA_CONSULTA_SERVICE.buscarPaciente(paramPaciente);
	}

	@WebMethod(operationName = "guardarPacienteEmergencia")
	@WebResult(name = "guardarPacienteEmergenciaJSON")
	public String guardarPacienteEmergencia(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE
				.guardarPacienteEmergencia(paramHojaConsulta);
	}

	@WebMethod(operationName = "getListaInicioHojaConsulta")
	@WebResult(name = "listaInicioConsultaJSON")
	public String getInicioHojaConsulta() {
		return HOJA_CONSULTA_SERVICE.getListaInicioHojaConsulta();
	}

	@WebMethod(operationName = "getListaHojaConsultaPorExpediente")
	@WebResult(name = "ListaHojaConsultaPorExpedienteJSON")
	public String getListaHojaConsultaPorExpediente(
			@WebParam(name = "paramCodExpediente", mode = Mode.IN) int paramCodExpediente,
			@WebParam(name = "paramEsEnfermeria", mode = Mode.IN) boolean paramEsEnfermeria) {
		return HOJA_CONSULTA_SERVICE
				.getListaHojaConsultaPorExpediente(paramCodExpediente, paramEsEnfermeria);
	}

	@WebMethod(operationName = "getDatosCabeceraSintomas")
	@WebResult(name = "resultadoJSON")
	public String getDatosCabeceraSintomas(
			@WebParam(name = "paramExpediente", partName = "paramExpediente", mode = Mode.IN) Integer paramExpediente,
			@WebParam(name = "paramHojaConsultaId", partName = "paramHojaConsultaId", mode = Mode.IN) Integer paramHojaConsultaId) {
		return HOJA_CONSULTA_SERVICE.getDatosCabeceraSintomas(paramExpediente,
				paramHojaConsultaId);
	}

	@WebMethod(operationName = "getHorarioTurno")
	@WebResult(name = "resultadoJSON")
	public String getHorarioTurno(
			@WebParam(name = "paramDiaSemana", partName = "paramDiaSemana", mode = Mode.IN) Integer paramDiaSemana) {
		return this.SINTOMAS_SERVICE.getHorarioTurno(paramDiaSemana);
	}

	@WebMethod(operationName = "guardarExamenHistoricoHojaConsulta")
	@WebResult(name = "guardarExamenHistoricoHojaConsultaJSON")
	public String guardarExamenHistoricoHojaConsulta(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return DIAGNOSTICO_SERVICE
				.guardarExamenHistoricoHojaConsulta(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarTratamientoHojaConsulta")
	@WebResult(name = "guardarTratamientoHojaConsultaJSON")
	public String guardarTratamientoHojaConsulta(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return DIAGNOSTICO_SERVICE
				.guardarTratamientoHojaConsulta(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarPlanesHojaConsulta")
	@WebResult(name = "guardarPlanesHojaConsultaJSON")
	public String guardarPlanesHojaConsulta(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return DIAGNOSTICO_SERVICE
				.guardarPlanesHojaConsulta(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarDiagnosticoHojaConsulta")
	@WebResult(name = "guardarDiagnosticoHojaConsultaJSON")
	public String guardarDiagnosticoHojaConsulta(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return DIAGNOSTICO_SERVICE
				.guardarDiagnosticoHojaConsulta(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarProximaCitaHojaConsulta")
	@WebResult(name = "guardarProximaCitaHojaConsultaJSON")
	public String guardarProximaCitaHojaConsulta(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return DIAGNOSTICO_SERVICE
				.guardarProximaCitaHojaConsulta(paramHojaConsulta);
	}

	@WebMethod(operationName = "getHojaConsultaPorNumero")
	@WebResult(name = "getHojaConsultaPorNumeroJSON")
	public String getHojaConsultaPorNumero(
			@WebParam(name = "paramSecHojaConsulta", partName = "paramSecHojaConsulta", mode = Mode.IN) Integer secHojaConsulta) {
		return HOJA_CONSULTA_SERVICE.getHojaConsultaPorNumero(secHojaConsulta);
	}

	@WebMethod(operationName = "getListaDiagnostico")
	@WebResult(name = "getListaDiagnosticoJSON")
	public String getListaDiagnostico() {
		return DIAGNOSTICO_SERVICE.getListaDiagnostico();
	}

	@WebMethod(operationName = "guardarGeneralesSintomas")
	@WebResult(name = "resultadoJSON")
	public String guardarGeneralesSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.guardarGeneralesSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarEstadoGeneralSintomas")
	@WebResult(name = "resultadoJSON")
	public String guardarEstadoGeneralSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE
				.guardarEstadoGeneralSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarGastroInSintomas")
	@WebResult(name = "resultadoJSON")
	public String guardarGastroInSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE.guardarGastroInSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarOsteomuscularSintomas")
	@WebResult(name = "resultadoJSON")
	public String guardarOsteomuscularSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE
				.guardarOsteomuscularSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarCabezaSintomas")
	@WebResult(name = "resultadoJSON")
	public String guardarCabezaSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE.guardarCabezaSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarDeshidraSintomas")
	@WebResult(name = "resultadoJSON")
	public String guardarDeshidraSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE.guardarDeshidraSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarCutaneoSintomas")
	@WebResult(name = "resultadoJSON")
	public String guardarCutaneoSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE.guardarCutaneoSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarGargantaSintomas")
	@WebResult(name = "resultadoJSON")
	public String guardarGargantaSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE.guardarGargantaSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarRenalSintomas")
	@WebResult(name = "resultadoJSON")
	public String guardarRenalSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE.guardarRenalSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarEstadoNutriSintomas")
	@WebResult(name = "resultadoJSON")
	public String guardarEstadoNutriSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE
				.guardarEstadoNutriSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarRespiratorioSintomas")
	@WebResult(name = "resultadoJSON")
	public String guardarRespiratorioSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE
				.guardarRespiratorioSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarReferenciaSintomas")
	@WebResult(name = "resultadoJSON")
	public String guardarReferenciaSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE
				.guardarReferenciaSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "guardarVacunaSintomas")
	@WebResult(name = "resultadoJSON")
	public String guardarVacunaSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE.guardarVacunaSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "validarNoConsentimientoDengue")
	@WebResult(name = "resultadoJSON")
	public String validarNoConsentimientoDengue(
			@WebParam(name = "paramExpediente", partName = "paramExpediente", mode = Mode.IN) Integer paramExpediente) {
		return HOJA_CONSULTA_SERVICE
				.validarNoConsentimientoDengue(paramExpediente);
	}

	@WebMethod(operationName = "guardarCategoriaSintomas")
	@WebResult(name = "resultadoJSON")
	public String guardarCategoriaSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE
				.guardarCategoriaSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "validacionMatrizSintoma")
	@WebResult(name = "resultadoJSON")
	public String validacionMatrizSintoma(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.validacionMatrizSintoma(paramHojaConsulta);
	}

	@WebMethod(operationName = "getDatosCabeceraExamenes")
	@WebResult(name = "resultadoJSON")
	public String getDatosCabeceraExamenes(
			@WebParam(name = "paramExpediente", partName = "paramExpediente", mode = Mode.IN) Integer paramExpediente,
			@WebParam(name = "paramHojaConsultaId", partName = "paramHojaConsultaId", mode = Mode.IN) Integer paramHojaConsultaId) {
		return HOJA_CONSULTA_SERVICE.getDatosCabeceraExamenes(paramExpediente,
				paramHojaConsultaId);
	}

	@WebMethod(operationName = "guardarExamenes")
	@WebResult(name = "resultadoJSON")
	public String guardarExamenes(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE.guardarExamenes(paramHojaConsulta);
	}

	@WebMethod(operationName = "buscarExamenes")
	@WebResult(name = "resultadoJSON")
	public String buscarExamenes(
			@WebParam(name = "paramSecHojaConsulta", partName = "paramSecHojaConsulta", mode = Mode.IN) Integer paramSecHojaConsulta) {
		return HOJA_CONSULTA_SERVICE.buscarExamenes(paramSecHojaConsulta);
	}

	@WebMethod(operationName = "buscarExamenesChekeados")
	@WebResult(name = "resultadoJSON")
	public String buscarExamenesChekeados(
			@WebParam(name = "paramSecHojaConsulta", partName = "paramSecHojaConsulta", mode = Mode.IN) Integer paramSecHojaConsulta) {
		return HOJA_CONSULTA_SERVICE
				.buscarExamenesChekeados(paramSecHojaConsulta);
	}

	@WebMethod(operationName = "guardarOtroExamen")
	@WebResult(name = "resultadoJSON")
	public String guardarOtroExamen(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE.guardarOtroExamen(paramHojaConsulta);
	}

	@WebMethod(operationName = "getHojaConsultaPdf")
	@WebResult(name = "getHojaConsultaPdfByte")
	public byte[] getHojaConsultaPdf(
			@WebParam(name = "secHojaConsulta", partName = "secHojaConsulta", mode = Mode.IN) Integer secHojaConsulta) {
		return HOJA_CONSULTA_REPORTE_SERVICE
				.getHojaConsultaPdf(secHojaConsulta);
	}

	@WebMethod(operationName = "getListaHojaConsultaExp")
	@WebResult(name = "resultadoJSON")
	public String getListaHojaConsultaExp(
			@WebParam(name = "paramCodExpediente", partName = "paramCodExpediente", mode = Mode.IN) int paramCodExpediente) {
		return EXPEDIENTE_SERVICE.getListaHojaConsultaExp(paramCodExpediente);
	}
	
	@WebMethod(operationName = "buscarPacienteCrearHoja")
	@WebResult(name = "resultadoJSON")
	public String buscarPacienteCrearHoja(@WebParam(name = "paramCodExpediente", partName = "paramCodExpediente", 
	mode = Mode.IN) int paramCodExpediente) {
		return EXPEDIENTE_SERVICE.buscarPacienteCrearHoja(paramCodExpediente);
	}

	@WebMethod(operationName = "buscarHojaSeguimientoInfluenza")
	@WebResult(name = "resultadoJSON")
	public String buscarHojaSeguimientoInfluenza(
			@WebParam(name = "paramNumHojaSeguimiento", partName = "paramNumHojaSeguimiento", mode = Mode.IN) int paramNumHojaSeguimiento) {
		return EXPEDIENTE_SERVICE
				.buscarHojaSeguimientoInfluenza(paramNumHojaSeguimiento);
	}

	@WebMethod(operationName = "buscarPacienteSeguimientoInfluenza")
	@WebResult(name = "resultadoJSON")
	public String buscarPacienteSeguimientoInfluenza(
			@WebParam(name = "paramCodExpediente", partName = "paramCodExpediente", mode = Mode.IN) int paramCodExpediente) {
		return EXPEDIENTE_SERVICE
				.buscarPacienteSeguimientoInfluenza(paramCodExpediente);
	}

	@WebMethod(operationName = "cargarGrillaCierre")
	@WebResult(name = "resultadoJSON")
	public String cargarGrillaCierre(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CIERRE_SERVICE.cargarGrillaCierre(paramHojaConsulta);
	}

	@WebMethod(operationName = "procesoCierre")
	@WebResult(name = "resultadoJSON")
	public String procesoCierre(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CIERRE_SERVICE.procesoCierre(paramHojaConsulta);
	}

	@WebMethod(operationName = "procesoCambioTurno")
	@WebResult(name = "resultadoJSON")
	public String procesoCambioTurno(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CIERRE_SERVICE.procesoCambioTurno(paramHojaConsulta);
	}

	@WebMethod(operationName = "procesoAgregarHojaConsulta")
	@WebResult(name = "resultadoJSON")
	public String procesoAgregarHojaConsulta(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CIERRE_SERVICE
				.procesoAgregarHojaConsulta(paramHojaConsulta);
	}

	@WebMethod(operationName = "procesoCancelar")
	@WebResult(name = "resultadoJSON")
	public String procesoCancelar(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CIERRE_SERVICE.procesoCancelar(paramHojaConsulta);
	}

	@WebMethod(operationName = "noAtiendeLlamadoCierre")
	@WebResult(name = "resultadoJSON")
	public String noAtiendeLlamadoCierre(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CIERRE_SERVICE.noAtiendeLlamadoCierre(paramHojaConsulta);
	}

	@WebMethod(operationName = "validarSalirHojaConsulta")
	@WebResult(name = "resultadoJSON")
	public String validarSalirHojaConsulta(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CIERRE_SERVICE.validarSalirHojaConsulta(paramHojaConsulta);
	}

	@WebMethod(operationName = "imprimirConsultaPdf")
	@WebResult(name = "imprimirConsultaPdfvoid")
	public void imprimirConsultaPdf(
			@WebParam(name = "secHojaConsulta", partName = "secHojaConsulta", mode = Mode.IN) Integer secHojaConsulta) {
		HOJA_CONSULTA_REPORTE_SERVICE.imprimirConsultaPdf(secHojaConsulta);
	}

	@WebMethod(operationName = "getListaSeguimientoInfluenza")
	@WebResult(name = "resultadoJSON")
	public String getListaSeguimientoInfluenza(
			@WebParam(name = "paramSecHojaInfluenza", partName = "paramSecHojaInfluenza", mode = Mode.IN) int paramSecHojaInfluenza) {
		return EXPEDIENTE_SERVICE
				.getListaSeguimientoInfluenza(paramSecHojaInfluenza);
	}

	@WebMethod(operationName = "guardarSeguimientoInfluenza")
	@WebResult(name = "resultadoJSON")
	public String guardarSeguimientoInfluenza(
			@WebParam(name = "paramHojaInfluenza", partName = "paramHojaInfluenza", mode = Mode.IN) String paramHojaInfluenza,
			@WebParam(name = "paramSeguimientoInfluenza", partName = "paramSeguimientoInfluenza", mode = Mode.IN) String paramSeguimientoInfluenza) {
		return EXPEDIENTE_SERVICE.guardarSeguimientoInfluenza(
				paramHojaInfluenza, paramSeguimientoInfluenza);
	}

	@WebMethod(operationName = "actualizarEstadoEnConsulta")
	@WebResult(name = "resultadoJSON")
	public String actualizarEstadoEnConsulta(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta,
			@WebParam(name = "paramCambioTurno", partName = "paramCambioTurno", mode = Mode.IN) boolean  paramCambioTurno)
			{
		return HOJA_CONSULTA_SERVICE
				.actualizarEstadoEnConsulta(paramHojaConsulta,paramCambioTurno
						);
	}

	@WebMethod(operationName = "getGeneralesSintomas")
	@WebResult(name = "resultadoJSON")
	public String getGeneralesSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getGeneralesSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "getEstadoGeneralSintomas")
	@WebResult(name = "resultadoJSON")
	public String getEstadoGeneralSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getEstadoGeneralSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "getGastroinstestinalSintomas")
	@WebResult(name = "resultadoJSON")
	public String getGastroinstestinalSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getGastroinstestinalSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "getOsteomuscularSintomas")
	@WebResult(name = "resultadoJSON")
	public String getOsteomuscularSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getOsteomuscularSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "getCabezaSintomas")
	@WebResult(name = "resultadoJSON")
	public String getCabezaSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getCabezaSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "getDeshidratacionSintomas")
	@WebResult(name = "resultadoJSON")
	public String getDeshidratacionSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getDeshidratacionSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "getCutaneoSintomas")
	@WebResult(name = "resultadoJSON")
	public String getCutaneoSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getCutaneoSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "getGargantaSintomas")
	@WebResult(name = "resultadoJSON")
	public String getGargantaSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getGargantaSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "getRenalSintomas")
	@WebResult(name = "resultadoJSON")
	public String getRenalSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getRenalSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "getEstadoNutricionalSintomas")
	@WebResult(name = "resultadoJSON")
	public String getEstadoNutricionalSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getEstadoNutricionalSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "getRespiratorioSintomas")
	@WebResult(name = "resultadoJSON")
	public String getRespiratorioSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getRespiratorioSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "getReferenciaSintomas")
	@WebResult(name = "resultadoJSON")
	public String getReferenciaSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getReferenciaSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "getVacunasSintomas")
	@WebResult(name = "resultadoJSON")
	public String getVacunasSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getVacunasSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "getCategoriasSintomas")
	@WebResult(name = "resultadoJSON")
	public String getCategoriasSintomas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getCategoriasSintomas(paramHojaConsulta);
	}

	@WebMethod(operationName = "getSeguimientoInfluenzaPdf")
	@WebResult(name = "getSeguimientoInfluenzaPdfByte")
	public byte[] getSeguimientoInfluenzaPdf(
			@WebParam(name = "paramNumHojaSeguimiento", partName = "paramNumHojaSeguimiento", mode = Mode.IN) int paramNumHojaSeguimiento) {
		return EXPEDIENTE_SERVICE
				.getSeguimientoInfluenzaPdf(paramNumHojaSeguimiento);
	}

	@WebMethod(operationName = "imprimirSeguimientoInfluenciaPdf")
	@WebResult(name = "resultadoJSON")
	public String imprimirSeguimientoInfluenciaPdf(
			@WebParam(name = "paramNumHojaSeguimiento") int paramNumHojaSeguimiento) {
		return EXPEDIENTE_SERVICE
				.imprimirSeguimientoInfluenciaPdf(paramNumHojaSeguimiento);
	}
	
	
	@WebMethod(operationName = "obtenerDatosPaciente")
	@WebResult(name = "obtenerDatosPacienteJSON")
	public String obtenerDatosPaciente(
			@WebParam(name = "paramCodExpediente", mode = Mode.IN) int paramCodExpediente) {
		return DIAGNOSTICO_SERVICE.obtenerDatosPaciente(paramCodExpediente);
	}
	
	@WebMethod(operationName = "imprimirConsultaTest")
	@WebResult(name = "imprimirConsultaPdfvoid")
	public void imprimirConsultaTest() {
		HOJA_CONSULTA_REPORTE_SERVICE.imprimirConsultaTest();
	}
	
	@WebMethod(operationName = "editarDatosPreclinicos")
	@WebResult(name = "editarDatosPreclinicosJSON")
	public String editarDatosPreclinicos(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.editarDatosPreclinicos(paramHojaConsulta);
	}
	
	@WebMethod(operationName = "guardarControlCambios")
	@WebResult(name = "resultadoJSON")
	public String guardarControlCambios(
			@WebParam(name = "paramControlCambios", partName = "paramControlCambios", mode = Mode.IN) String paramControlCambios) {
		return CONTROL_CAMBIOS_SERVICE.guardarControlCambios(paramControlCambios);				
	}
	
	@WebMethod(operationName = "getListaMedicos")
	@WebResult(name = "listaMedicosJSON")
	public String getListaMedicos() {
		return HOJA_CONSULTA_SERVICE.getListaMedicos();
	}
	
	@WebMethod(operationName = "getListaMedicosPorNombre")
	@WebResult(name = "listaMedicosJSON")
	public String getListaMedicosPorNombre(@WebParam(name = "paramNombre", partName = "paramNombre", mode = Mode.IN) String paramNombre) {
		return HOJA_CONSULTA_SERVICE.getListaMedicos(paramNombre);
	}
	
	@WebMethod(operationName = "crearSeguimientoInfluenza")
	@WebResult(name = "resultadoJSON")
	public String crearSeguimientoInfluenza(
			@WebParam(name = "paramCrearHoja", partName = "paramCrearHoja", mode = Mode.IN) String paramCrearHoja) {
		return EXPEDIENTE_SERVICE.crearSeguimientoInfluenza(paramCrearHoja);
	}
	
	@WebMethod(operationName = "getTodasEscuela")
	@WebResult(name = "listaMedicosJSON")
	public String getTodasEscuela() {
		return DIAGNOSTICO_SERVICE.getTodasEscuela();
	}
	
	@WebMethod(operationName = "getSeccionesSintomasCompletadas")
	@WebResult(name = "resultadoJSON")
	public String getSeccionesSintomasCompletadas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return SINTOMAS_SERVICE.getSeccionesSintomasCompletadas(paramHojaConsulta);
	}
	
	@WebMethod(operationName = "getSeccionesDiagnosticoCompletadas")
	@WebResult(name = "resultadoJSON")
	public String getSeccionesDiagnosticoCompletadas(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return DIAGNOSTICO_SERVICE.getSeccionesDiagnosticoCompletadas(paramHojaConsulta);
	}
	
	@WebMethod(operationName = "getDatosPreclinicos")
	@WebResult(name = "resultadoJSON")
	public String getDatosPreclinicos(@WebParam(name = "paramHojaConsultaId", 
	partName = "paramHojaConsultaId", mode = Mode.IN) Integer paramHojaConsultaId) {
		return ENFERMERIA_SERVICE.getDatosPreclinicos(paramHojaConsultaId);
	}
	
	@WebMethod(operationName = "actualizarEstadoEnfermeria")
	@WebResult(name = "hojaConsultaJSON")
	public String actualizarEstadoEnfermeria(
			@WebParam(name = "paramHojaConsulta", partName = "paramHojaConsulta", mode = Mode.IN) String paramHojaConsulta) {
		return HOJA_CONSULTA_SERVICE.actualizarEstadoEnfermeria(paramHojaConsulta);
	}
	
	@WebMethod(operationName = "reimpresionHojaConsulta")
	@WebResult(name = "hojaConsultaJSON")
	public String reimpresionHojaConsulta(
			@WebParam(name = "paramsecHojaConsulta", partName = "paramsecHojaConsulta", mode = Mode.IN) int paramsecHojaConsulta) {
		return EXPEDIENTE_SERVICE.reimpresionHojaConsulta(paramsecHojaConsulta);
	}
	
	//Hoja Zika
	@WebMethod(operationName = "getListaSeguimientoZika")
	@WebResult(name = "resultadoJSON")
	public String getListaSeguimientoZika(
			@WebParam(name = "paramSecHojaZika", partName = "paramSecHojaZika", mode = Mode.IN) int paramSecHojaZika) {
		return EXPEDIENTE_SERVICE
				.getListaSeguimientoZika(paramSecHojaZika);
	}
	
	@WebMethod(operationName = "buscarPacienteSeguimientoZika")
	@WebResult(name = "resultadoJSON")
	public String buscarPacienteSeguimientoZika(
			@WebParam(name = "paramCodExpediente", partName = "paramCodExpediente", mode = Mode.IN) int paramCodExpediente) {
		return EXPEDIENTE_SERVICE
				.buscarPacienteSeguimientoZika(paramCodExpediente);
	}
	
	@WebMethod(operationName = "buscarHojaSeguimientoZika")
	@WebResult(name = "resultadoJSON")
	public String buscarHojaSeguimientoZika(
			@WebParam(name = "paramNumHojaSeguimiento", partName = "paramNumHojaSeguimiento", mode = Mode.IN) int paramNumHojaSeguimiento) {
		return EXPEDIENTE_SERVICE
				.buscarHojaSeguimientoZika(paramNumHojaSeguimiento);
	}
	
	@WebMethod(operationName = "crearSeguimientoZika")
	@WebResult(name = "resultadoJSON")
	public String crearSeguimientoZika(
			@WebParam(name = "paramCrearHoja", partName = "paramCrearHoja", mode = Mode.IN) String paramCrearHoja) {
		return EXPEDIENTE_SERVICE.crearSeguimientoZika(paramCrearHoja);
	}
	
	@WebMethod(operationName = "guardarSeguimientoZika")
	@WebResult(name = "resultadoJSON")
	public String guardarSeguimientoZika(
			@WebParam(name = "paramHojaZika", partName = "paramHojaZika", mode = Mode.IN) String paramHojaZika,
			@WebParam(name = "paramSeguimientoZika", partName = "paramSeguimientoZika", mode = Mode.IN) String paramSeguimientoZika) {
		return EXPEDIENTE_SERVICE.guardarSeguimientoZika(
				paramHojaZika, paramSeguimientoZika);
	}
	
	@WebMethod(operationName = "getSeguimientoZikaPdf")
	@WebResult(name = "getSeguimientoZikaPdfByte")
	public byte[] getSeguimientoZikaPdf(
			@WebParam(name = "paramNumHojaSeguimiento", partName = "paramNumHojaSeguimiento", mode = Mode.IN) int paramNumHojaSeguimiento) {
		return EXPEDIENTE_SERVICE
				.getSeguimientoZikaPdf(paramNumHojaSeguimiento);
	}
	
	@WebMethod(operationName = "imprimirSeguimientoZikaPdf")
	@WebResult(name = "resultadoJSON")
	public String imprimirSeguimientoZikaPdf(
			@WebParam(name = "paramNumHojaSeguimiento") int paramNumHojaSeguimiento) {
		return EXPEDIENTE_SERVICE
				.imprimirSeguimientoZikaPdf(paramNumHojaSeguimiento);
	}
	
	@WebMethod(operationName = "buscarFichaVigilanciaIntegrada")
	@WebResult(name = "resultadoJSON")
	public String buscarFichaVigilanciaIntegrada(
			@WebParam(name = "codExpediente", partName = "codExpediente", mode = Mode.IN) int codExpediente,
			@WebParam(name = "numHojaConsulta", partName = "numHojaConsulta", mode = Mode.IN) int numHojaConsulta){
		return EXPEDIENTE_SERVICE
				.buscarFichaVigilanciaIntegrada(codExpediente, numHojaConsulta);
	}
	
	@WebMethod(operationName = "guardarFichaVigilanciaIntegrada")
	@WebResult(name = "resultadoJSON")
	public String guardarFichaVigilanciaIntegrada(
			@WebParam(name = "paramVigilanciaIntegrada", partName = "paramVigilanciaIntegrada", mode = Mode.IN) String paramVigilanciaIntegrada) {
		return EXPEDIENTE_SERVICE.guardarFichaVigilanciaIntegrada(paramVigilanciaIntegrada);
	}
	
	@WebMethod(operationName = "getDepartamentos")
	@WebResult(name = "listaDepartamentosJSON")
	public String getDepartamentos() {
		return EXPEDIENTE_SERVICE.getDepartamentos();
	}
	
	@WebMethod(operationName = "getMunicipios")
	@WebResult(name = "resultadoJSON")
	public String getMunicipios(
			@WebParam(name = "divisionpoliticaId", partName = "divisionpoliticaId", mode = Mode.IN) int divisionpoliticaId) {
		return EXPEDIENTE_SERVICE.getMunicipios(divisionpoliticaId);
	}
	
	@WebMethod(operationName = "getFichaPdf")
	@WebResult(name = "getFichaPdfByte")
	public byte[] getFichaPdf(
			@WebParam(name = "secVigilanciaIntegrada", partName = "secVigialnciaIntegrada", mode = Mode.IN) int secVigilanciaIntegrada) {
		return EXPEDIENTE_SERVICE
				.getFichaPdf(secVigilanciaIntegrada);
	}
	
	@WebMethod(operationName = "imprimirFichaPdf")
	@WebResult(name = "imprimirFichaPdfvoid")
	public void imprimirFichaPdf(
			@WebParam(name = "secVigilanciaIntegrada") int secVigilanciaIntegrada) {
		EXPEDIENTE_SERVICE
				.imprimirFichaPdf(secVigilanciaIntegrada);
	}
	
	@WebMethod(operationName = "procesoActivarDiagnostico")
	@WebResult(name = "resultadoJSON")
	public String procesoActivarDiagnostico(
			@WebParam(name = "secHojaConsulta", partName = "secHojaConsulta", mode = Mode.IN) int secHojaConsulta) {
		return DIAGNOSTICO_SERVICE.activarDiagnosticos(secHojaConsulta);
	}
	
	/*Fecha Creacion 6/12/2019 -- SC*/
	@WebMethod(operationName = "getFichaEpiSindromesFebrilesPdf")
	@WebResult(name = "getFichaEpiSindromesFebrilesPdfByte")
	public byte[] getFichaEpiSindromesFebrilesPdf(
			@WebParam(name = "numHojaConsulta", partName = "numHojaConsulta", mode = Mode.IN) int numHojaConsulta) {
		return EXPEDIENTE_SERVICE
				.getFichaEpiSindromesFebrilesPdf(numHojaConsulta);
	}
	
	@WebMethod(operationName = "imprimirFichaEpiSindromesFebrilesPdf")
	@WebResult(name = "imprimirFichaEpiSindromesFebrilesPdfvoid")
	public void imprimirFichaEpiSindromesFebrilesPdf(
			@WebParam(name = "numHojaConsulta") int numHojaConsulta) {
		EXPEDIENTE_SERVICE
				.imprimirFichaEpiSindromesFebrilesPdf(numHojaConsulta);
	}
	
	@WebMethod(operationName = "getFisAndFifByCodExp")
	@WebResult(name = "resultadoJSON")
	public String getFisAndFifByCodExp(
			@WebParam(name = "codExpediente", partName = "codExpediente", mode = Mode.IN) int codExpediente) {
		return SINTOMAS_SERVICE.getFisAndFifByCodExp(codExpediente);
	}
	
	//--------------------------------------------------------------------------------
}
