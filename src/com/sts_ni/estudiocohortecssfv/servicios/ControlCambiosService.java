package com.sts_ni.estudiocohortecssfv.servicios;

import java.util.HashMap;
import java.util.Map;

import ni.com.sts.estudioCohorteCSSFV.modelo.ControlCambios;
import ni.com.sts.estudioCohorteCSSFV.modelo.HojaConsulta;

import com.sts_ni.estudiocohortecssfv.dto.CabezaControlCambios;
import com.sts_ni.estudiocohortecssfv.dto.CutaneoControlCambios;
import com.sts_ni.estudiocohortecssfv.dto.DeshidratacionControlCambio;
import com.sts_ni.estudiocohortecssfv.dto.EstadoGeneralesControlCambios;
import com.sts_ni.estudiocohortecssfv.dto.EstadoNutricionalControlCambio;
import com.sts_ni.estudiocohortecssfv.dto.ExamenesControlCambios;
import com.sts_ni.estudiocohortecssfv.dto.GargantaControlCambio;
import com.sts_ni.estudiocohortecssfv.dto.OsteomuscularControlCambio;
import com.sts_ni.estudiocohortecssfv.dto.RenalControlCambio;
import com.sts_ni.estudiocohortecssfv.dto.RespiratorioControlCambio;
import com.sts_ni.estudiocohortecssfv.dto.VacunaControlCambio;

public interface ControlCambiosService {
	
	public String guardarControlCambios(String paramControlCambios);
	
	public String guardarControlCambios(HojaConsulta hojaConsulta, ExamenesControlCambios ecc);

	public String guardarControlCambios(HojaConsulta hojaConsulta,
			EstadoGeneralesControlCambios egcc);

	public String guardarControlCambios(HojaConsulta hojaConsulta,
			CabezaControlCambios cc);

	public String guardarControlCambios(HojaConsulta hojaConsulta,
			CutaneoControlCambios cc);

	public String guardarControlCambios(HojaConsulta hojaConsulta,
			GargantaControlCambio cc);

	public String guardarControlCambios(HojaConsulta hojaConsulta,
			OsteomuscularControlCambio cc);

	public String guardarControlCambios(HojaConsulta hojaConsulta,
			DeshidratacionControlCambio cc);

	public String guardarControlCambios(HojaConsulta hojaConsulta,
			RenalControlCambio cc);
	
	public String guardarCtrlHistExamenFisico(HojaConsulta hojaConsulta, String usuario);
	
	public String guardarCtrlDiagnosticoHC(HojaConsulta hojaConsulta,  HashMap<Integer, String> diagnosticos, String usuario);
	
	public String guardarCtrlPlanesHC(HojaConsulta hojaConsulta, String usuario);
	
	public String guardarCtrlProxCita(HojaConsulta hcActual, HojaConsulta hcNueva, String usuario);
	
	public String guardarCtrlTratamiento(HojaConsulta hcActual, HojaConsulta hcNueva, String usuario);

	public String guardarControlCambios(HojaConsulta hojaConsulta,
			RespiratorioControlCambio cc);

	public String guardarControlCambios(HojaConsulta hojaConsulta,
			VacunaControlCambio cc);

	public String guardarControlCambios(HojaConsulta hojaConsulta,
			EstadoNutricionalControlCambio cc);
	
	public String guardarCtrlReferencia(HojaConsulta hcActual, HojaConsulta hcNueva, String usuario);

	public String guardarCtrlPreclinicos(HojaConsulta hcActual, HojaConsulta hcNueva,
			String usuario);
	
	public String guardarControlCambios(ControlCambios controlCambios);
	
	public String guardarCtrlGastroIn(HojaConsulta hcActual, HojaConsulta hcNueva, String usuario, String controlador);

	public String guardarCtrlCategoria(HojaConsulta hcActual, HojaConsulta hcNueva,
			String usuario);

	public String guardarCtrlGenerales(HojaConsulta hcActual, HojaConsulta hcNueva,
			String usuario);
	
	public Map<String, Object> getControlCambios(int numHojaConsulta, int codExpediente);
}
