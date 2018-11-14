package com.sts_ni.estudiocohortecssfv.datos.controlcambios;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import ni.com.sts.estudioCohorteCSSFV.modelo.ControlCambios;
import ni.com.sts.estudioCohorteCSSFV.modelo.HojaConsulta;

import org.hibernate.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
import com.sts_ni.estudiocohortecssfv.servicios.ControlCambiosService;
import com.sts_ni.estudiocohortecssfv.util.HibernateResource;
import com.sts_ni.estudiocohortecssfv.util.Mensajes;
import com.sts_ni.estudiocohortecssfv.util.UtilResultado;

/***
 * Clase para guardar la informacion en la tabla auditoria
 *
 */
public class ControlCambiosDA implements ControlCambiosService {
	
	private static final HibernateResource HIBERNATE_RESOURCE;
	private static final String QUERY_ELIMINA_CONTROL_CAMBIOS;
	public static final String Modificacion = "Modificacion";
	public static final String Discrepancia = "Discrepancia";
	
	static {
		QUERY_ELIMINA_CONTROL_CAMBIOS = "delete from control_cambios where to_char(fecha, 'yyyymmdd') = to_char(cast(:fecha as Date), 'yyyymmdd') " +
										" and cod_expediente = :CodExpediente and num_hoja_consulta = :NumHojaConsulta and controlador = :Controlador";
		HIBERNATE_RESOURCE = new HibernateResource();
	}

	@Override
	public String guardarControlCambios(String paramControlCambios){
		String result;
		try {
			
			JSONParser parser = new JSONParser();
			
            Object obj = new Object();
            obj = parser.parse(paramControlCambios);
            JSONArray controlCambiosJSON = (JSONArray) obj;			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();
	        
	        
	        //Boolean esInicio = true;
	        //Agregando cada registros
	        for(Object objCambios: controlCambiosJSON){
	        	
	        	JSONObject ctrCambios = (JSONObject) parser.parse(objCambios.toString());
	        	Integer codExpediente = Integer.parseInt(ctrCambios.get("codExpediente").toString());
	        	Integer numHojaConsulta = Integer.parseInt(ctrCambios.get("numHojaConsulta").toString());
	        	String controlador = ctrCambios.get("controlador").toString();
	        	
	        	/*if(esInicio){
	        		//Eliminando los registros de la misma hoja y expediente
	    	        Query qEliminar = HIBERNATE_RESOURCE.getSession().createSQLQuery(QUERY_ELIMINA_CONTROL_CAMBIOS);
	    	        qEliminar.setDate("fecha", today);
	    	        qEliminar.setInteger("CodExpediente", codExpediente);
	    	        qEliminar.setInteger("NumHojaConsulta", numHojaConsulta);
	    	        qEliminar.setString("Controlador", controlador);
	    	        qEliminar.executeUpdate();
	    	        
	    	        esInicio = false;
	        	}*/
	        	
	        	ControlCambios controlCambios = new ControlCambios();
	        	controlCambios.setCodExpediente(codExpediente);
	        	controlCambios.setFecha(today);
	        	controlCambios.setNombreCampo(ctrCambios.get("nombreCampo").toString());
	        	controlCambios.setValorCampo(ctrCambios.get("valorCampo").toString());
	        	controlCambios.setNumHojaConsulta(numHojaConsulta);
	        	controlCambios.setTipoControl(ctrCambios.get("tipoControl").toString());
	        	controlCambios.setUsuario(ctrCambios.get("usuario").toString());
	        	controlCambios.setControlador(controlador);
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }	        
            
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	@Override
	public String guardarControlCambios(ControlCambios controlCambios){
		String result;
		try {				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();
	        controlCambios.setFecha(today);
        	HIBERNATE_RESOURCE.getSession().save(controlCambios);        	
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	private ControlCambios getCtrlCambios(HojaConsulta hojaConsulta, ExamenesControlCambios ecc, Date today){
		ControlCambios controlCambios = new ControlCambios();
    	controlCambios.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
    	controlCambios.setCodExpediente(hojaConsulta.getCodExpediente());
    	controlCambios.setTipoControl(Modificacion);
    	controlCambios.setUsuario(ecc.getUsuario());
    	controlCambios.setControlador(ecc.getControlador());
    	controlCambios.setFecha(today);
    	
    	return controlCambios;
	}
	
	private ControlCambios getCtrlCambios(HojaConsulta hojaConsulta, Date today, String tipoControl){
		ControlCambios controlCambios = new ControlCambios();
    	controlCambios.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
    	controlCambios.setCodExpediente(hojaConsulta.getCodExpediente());
    	controlCambios.setTipoControl(tipoControl);
    	controlCambios.setFecha(today);
    	
    	return controlCambios;
	}
	
	private ControlCambios getCtrlCambios(HojaConsulta hojaConsulta, EstadoGeneralesControlCambios cc, Date today){
		ControlCambios controlCambios = new ControlCambios();
    	controlCambios.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
    	controlCambios.setCodExpediente(hojaConsulta.getCodExpediente());
    	controlCambios.setTipoControl(Modificacion);
    	controlCambios.setUsuario(cc.getUsuario());
    	controlCambios.setControlador(cc.getControlador());
    	controlCambios.setFecha(today);
    	
    	return controlCambios;
	}
	
	private ControlCambios getCtrlCambios(HojaConsulta hojaConsulta, String usuario, String controlador, Date today){
		ControlCambios controlCambios = new ControlCambios();
    	controlCambios.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
    	controlCambios.setCodExpediente(hojaConsulta.getCodExpediente());
    	controlCambios.setTipoControl(Modificacion);
    	controlCambios.setUsuario(usuario);
    	controlCambios.setControlador(controlador);
    	controlCambios.setFecha(today);
    	
    	return controlCambios;
	}
	
	@Override
	public String guardarControlCambios(HojaConsulta hojaConsulta, ExamenesControlCambios ecc){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();        
	        
	        if(ecc.getBhc().charValue() != hojaConsulta.getBhc().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);        	
	        	controlCambios.setNombreCampo("BHC");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getBhc()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
	        if(ecc.getSerologiaDengue().charValue() != hojaConsulta.getSerologiaDengue().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("Serologia Dengue");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getSerologiaDengue()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
        	if(ecc.getSerologiaChik().charValue() != hojaConsulta.getSerologiaChik().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("Serologia Chik");        	
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getSerologiaChik()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(ecc.getGotaGruesa().charValue() != hojaConsulta.getGotaGruesa().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("Gota Gruesa");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getGotaGruesa()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(ecc.getExtendidoPeriferico().charValue() != hojaConsulta.getExtendidoPeriferico().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("Extendido Periferico");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getExtendidoPeriferico()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(ecc.getEgo().charValue() != hojaConsulta.getEgo().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("EGO");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getEgo()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(ecc.getEgh().charValue() != hojaConsulta.getEgh().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("EGH");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getEgh()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(ecc.getCitologiaFecal().charValue() != hojaConsulta.getCitologiaFecal().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("Citologia Fecal");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getCitologiaFecal()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(ecc.getFactorReumatoideo().charValue() != hojaConsulta.getFactorReumatoideo().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("Factor Reumatoideo");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getFactorReumatoideo()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(ecc.getAlbumina().charValue() != hojaConsulta.getAlbumina().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("Albumina");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getAlbumina()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(ecc.getAstAlt().charValue() != hojaConsulta.getAstAlt().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("Ast Alt");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getAstAlt()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(ecc.getBilirrubinas().charValue() != hojaConsulta.getBilirrubinas().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("Bilirrubinas");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getBilirrubinas()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(ecc.getCpk().charValue() != hojaConsulta.getCpk().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("CPK");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getCpk()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(ecc.getColesterol().charValue() != hojaConsulta.getColesterol().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("Colesterol");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getColesterol()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(ecc.getInfluenza().charValue() != hojaConsulta.getInfluenza().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("Influenza");    
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getInfluenza()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(ecc.getOel().charValue() != hojaConsulta.getOel().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, ecc, today);
	        	controlCambios.setNombreCampo("OEL");    
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getOel()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
            
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	@Override
	public String guardarControlCambios(HojaConsulta hojaConsulta, EstadoGeneralesControlCambios cc){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();        
	        
	        if(cc.getFiebre().charValue() != hojaConsulta.getFiebre().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc, today);        	
	        	controlCambios.setNombreCampo("Fiebre");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getFiebre()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
	        if(cc.getAsomnoliento().charValue() != hojaConsulta.getAsomnoliento().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc, today);
	        	controlCambios.setNombreCampo("Asomnoliento");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getAsomnoliento()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getAstenia().charValue() != hojaConsulta.getAstenia().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc, today);
	        	controlCambios.setNombreCampo("Astenia");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getAstenia()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getConvulsiones().charValue() != hojaConsulta.getConvulsiones().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc, today);
	        	controlCambios.setNombreCampo("Convulsiones");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getConvulsiones()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getHipotermia().charValue() != hojaConsulta.getHipotermia().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc, today);
	        	controlCambios.setNombreCampo("Hipotermia");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getHipotermia()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getInquieto().charValue() != hojaConsulta.getInquieto().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc, today);
	        	controlCambios.setNombreCampo("Inquieto");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getInquieto()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getLetargia().charValue() != hojaConsulta.getLetargia().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc, today);
	        	controlCambios.setNombreCampo("Letargia");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getLetargia()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getMalEstado().charValue() != hojaConsulta.getMalEstado().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc, today);
	        	controlCambios.setNombreCampo("Mal Estado");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getMalEstado()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getPerdidaConsciencia().charValue() != hojaConsulta.getPerdidaConsciencia().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc, today);
	        	controlCambios.setNombreCampo("Perdida Consciencia");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getPerdidaConsciencia()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
            
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	@Override
	public String guardarControlCambios(HojaConsulta hojaConsulta, CabezaControlCambios cc){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();        
	        
	        if(cc.getCefalea().charValue() != hojaConsulta.getCefalea().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);        	
	        	controlCambios.setNombreCampo("Cefalea");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getCefalea()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
	        if(cc.getDolorRetroocular().charValue() != hojaConsulta.getDolorRetroocular().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Dolor Retroocular");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getDolorRetroocular()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getFontanelaAbombada().charValue() != hojaConsulta.getFontanelaAbombada().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Fontanela Abombada");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getFontanelaAbombada()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getHemorragiaSuconjuntival().charValue() != hojaConsulta.getHemorragiaSuconjuntival().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Hemorragia Suconjuntival");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getHemorragiaSuconjuntival()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getIctericiaConuntival().charValue() != hojaConsulta.getIctericiaConuntival().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Ictericia Conuntival");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getIctericiaConuntival()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getInyeccionConjuntival().charValue() != hojaConsulta.getInyeccionConjuntival().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Inyeccion Conjuntival");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getInyeccionConjuntival()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getRigidezCuello().charValue() != hojaConsulta.getRigidezCuello().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Rigidez Cuello");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getRigidezCuello()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	@Override
	public String guardarControlCambios(HojaConsulta hojaConsulta, CutaneoControlCambios cc){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();        
	        
	        if(cc.getCianosisCentral().charValue() != hojaConsulta.getCianosisCentral().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);        	
	        	controlCambios.setNombreCampo("Cianosis Central");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getCianosisCentral()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
	        if(cc.getEquimosis().charValue() != hojaConsulta.getEquimosis().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Equimosis");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getEquimosis()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getIctericia().charValue() != hojaConsulta.getIctericia().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Ictericia");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getIctericia()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getRahsGeneralizado().charValue() != hojaConsulta.getRahsGeneralizado().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Rahs Generalizado");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getRahsGeneralizado()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getRahsLocalizado().charValue() != hojaConsulta.getRahsLocalizado().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Rahs Localizado");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getRahsLocalizado()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getRahsMacular().charValue() != hojaConsulta.getRahsMacular().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Rahs Macular");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getRahsMacular()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getRahsMoteada().charValue() != hojaConsulta.getRahsMoteada().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Rahs Moteada");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getRahsMoteada()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getRashEritematoso().charValue() != hojaConsulta.getRashEritematoso().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Rash Eritematoso");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(cc.getRashEritematoso()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getRashPapular().charValue() != hojaConsulta.getRashPapular().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Rash Papular");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getRashPapular()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getRuborFacial().charValue() != hojaConsulta.getRuborFacial().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Rubor Facial");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getRuborFacial()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	@Override
	public String guardarControlCambios(HojaConsulta hojaConsulta, GargantaControlCambio cc){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();        
	        
	        if(cc.getEritema().charValue() != hojaConsulta.getEritema().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);        	
	        	controlCambios.setNombreCampo("Eritema");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getEritema()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
	        if(cc.getAdenopatiasCervicales().charValue() != hojaConsulta.getAdenopatiasCervicales().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Adenopatias Cervicales");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getAdenopatiasCervicales()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getDolorGarganta().charValue() != hojaConsulta.getDolorGarganta().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Dolor Garganta");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getDolorGarganta()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getExudado().charValue() != hojaConsulta.getExudado().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Exudado");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getExudado()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getPetequiasMucosa().charValue() != hojaConsulta.getPetequiasMucosa().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Petequias Mucosa");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getPetequiasMucosa()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	@Override
	public String guardarControlCambios(HojaConsulta hojaConsulta, OsteomuscularControlCambio cc){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();        
	        
	        if(cc.getAltralgia().charValue() != hojaConsulta.getAltralgia().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);        	
	        	controlCambios.setNombreCampo("Altralgia");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getAltralgia()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
	        if(cc.getArtralgiaDistal().charValue() != hojaConsulta.getArtralgiaDistal().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Artralgia Distal");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getArtralgiaDistal()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getArtralgiaProximal().charValue() != hojaConsulta.getArtralgiaProximal().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Artralgia Proximal");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getArtralgiaProximal()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getConjuntivitis().charValue() != hojaConsulta.getConjuntivitis().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Conjuntivitis");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getConjuntivitis()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getDolorCuello().charValue() != hojaConsulta.getDolorCuello().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Dolor Cuello");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getDolorCuello()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getEdemaCodos().charValue() != hojaConsulta.getEdemaCodos().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Edema Codos");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getEdemaCodos()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getEdemaHombros().charValue() != hojaConsulta.getEdemaHombros().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Edema Hombros");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getEdemaHombros()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getEdemaMunecas().charValue() != hojaConsulta.getEdemaMunecas().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Edema Munecas");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getEdemaMunecas()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getEdemaRodillas().charValue() != hojaConsulta.getEdemaRodillas().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Edema Rodillas");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getEdemaRodillas()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getEdemaTobillos().charValue() != hojaConsulta.getEdemaTobillos().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Edema Tobillos");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getEdemaTobillos()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getLumbalgia().charValue() != hojaConsulta.getLumbalgia().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Lumbalgia");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getLumbalgia()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getMialgia().charValue() != hojaConsulta.getMialgia().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Mialgia");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getMialgia()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getTenosinovitis().charValue() != hojaConsulta.getTenosinovitis().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Tenosinovitis");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getTenosinovitis()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	@Override
	public String guardarControlCambios(HojaConsulta hojaConsulta, DeshidratacionControlCambio cc){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();        
	        
	        if(cc.getBebeConSed().charValue() != hojaConsulta.getBebeConSed().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);        	
	        	controlCambios.setNombreCampo("Bebe con sed");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getBebeConSed()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
	        if(cc.getFontanelaHundida().charValue() != hojaConsulta.getFontanelaHundida().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Fontanela Hundida");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getFontanelaHundida()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getLenguaMucosasSecas().charValue() != hojaConsulta.getLenguaMucosasSecas().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Lengua mucosas secas");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getLenguaMucosasSecas()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getOjosHundidos().charValue() != hojaConsulta.getOjosHundidos().charAt(0)){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Ojos Hundidos");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getOjosHundidos().charAt(0)));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getOrinaReducida().charValue() != hojaConsulta.getOrinaReducida().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Orina reducida");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getOrinaReducida()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getPliegueCutaneo().charValue() != hojaConsulta.getPliegueCutaneo().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Pliegue cutaneo");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getPliegueCutaneo()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	@Override
	public String guardarControlCambios(HojaConsulta hojaConsulta, RenalControlCambio cc){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();        
	        
	        if(cc.getBilirrubinuria().charValue() != hojaConsulta.getBilirrubinuria().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);        	
	        	controlCambios.setNombreCampo("Bilirrubinuria");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getBilirrubinuria()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
	        if(cc.getEritrocitos().charValue() != hojaConsulta.getEritrocitos().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Eritrocitos");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getEritrocitos()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getLeucocituria().charValue() != hojaConsulta.getLeucocituria().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Leucocituria");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getLeucocituria()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getNitritos().charValue() != hojaConsulta.getNitritos().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Nitritos");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getNitritos().charValue()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getSintomasUrinarios().charValue() != hojaConsulta.getSintomasUrinarios().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Sintomas Urinarios");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getSintomasUrinarios()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	@Override
	public String guardarCtrlHistExamenFisico(HojaConsulta hojaConsulta, String usuario){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin(); 
	        ControlCambios controlCambios = new ControlCambios();
        	controlCambios.setCodExpediente(hojaConsulta.getCodExpediente());
        	controlCambios.setFecha(today);
        	controlCambios.setNombreCampo("Historia Examen Fisico");
        	controlCambios.setValorCampo(hojaConsulta.getHistoriaExamenFisico());
        	controlCambios.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
        	controlCambios.setTipoControl(Modificacion);
        	controlCambios.setUsuario(usuario);
        	controlCambios.setControlador("DiagnosticoExamenHistoriaActivity");
        	
        	HIBERNATE_RESOURCE.getSession().save(controlCambios);       	
        	HIBERNATE_RESOURCE.commit();            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
        	
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result; 
	}
	
	@Override
	public String guardarCtrlDiagnosticoHC(HojaConsulta hojaConsulta, HashMap<Integer, String> diagnosticos, String usuario){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();
	        
	        if(diagnosticos.containsKey(1)) {
	        	ControlCambios controlCambios = new ControlCambios();
	        	controlCambios.setCodExpediente(hojaConsulta.getCodExpediente());
	        	controlCambios.setFecha(today);
	        	controlCambios.setNombreCampo("Diagnostico 1");
	        	controlCambios.setValorCampo(diagnosticos.get(1));
	        	controlCambios.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
	        	controlCambios.setTipoControl(Modificacion);
	        	controlCambios.setUsuario(usuario);
	        	controlCambios.setControlador("DiagnosticoActivity");
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(diagnosticos.containsKey(2)) {
	        	ControlCambios controlCambios = new ControlCambios();
	        	controlCambios.setCodExpediente(hojaConsulta.getCodExpediente());
	        	controlCambios.setFecha(today);
	        	controlCambios.setNombreCampo("Diagnostico 2");
	        	controlCambios.setValorCampo(diagnosticos.get(2));
	        	controlCambios.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
	        	controlCambios.setTipoControl(Modificacion);
	        	controlCambios.setUsuario(usuario);
	        	controlCambios.setControlador("DiagnosticoActivity");
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(diagnosticos.containsKey(3)) {
	        	ControlCambios controlCambios = new ControlCambios();
	        	controlCambios.setCodExpediente(hojaConsulta.getCodExpediente());
	        	controlCambios.setFecha(today);
	        	controlCambios.setNombreCampo("Diagnostico 3");
	        	controlCambios.setValorCampo(diagnosticos.get(3));
	        	controlCambios.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
	        	controlCambios.setTipoControl(Modificacion);
	        	controlCambios.setUsuario(usuario);
	        	controlCambios.setControlador("DiagnosticoActivity");
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(diagnosticos.containsKey(4)) {
	        	ControlCambios controlCambios = new ControlCambios();
	        	controlCambios.setCodExpediente(hojaConsulta.getCodExpediente());
	        	controlCambios.setFecha(today);
	        	controlCambios.setNombreCampo("Diagnostico 4");
	        	controlCambios.setValorCampo(diagnosticos.get(4));
	        	controlCambios.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
	        	controlCambios.setTipoControl(Modificacion);
	        	controlCambios.setUsuario(usuario);
	        	controlCambios.setControlador("DiagnosticoActivity");
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(diagnosticos.containsKey(5)) {
	        	ControlCambios controlCambios = new ControlCambios();
	        	controlCambios.setCodExpediente(hojaConsulta.getCodExpediente());
	        	controlCambios.setFecha(today);
	        	controlCambios.setNombreCampo("otro diagnostico");
	        	controlCambios.setValorCampo(diagnosticos.get(5));
	        	controlCambios.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
	        	controlCambios.setTipoControl(Modificacion);
	        	controlCambios.setUsuario(usuario);
	        	controlCambios.setControlador("DiagnosticoActivity");
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
        	HIBERNATE_RESOURCE.commit();            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
        	
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result; 
	}
	
	@Override
	public String guardarCtrlPlanesHC(HojaConsulta hojaConsulta, String usuario){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin(); 
	        ControlCambios controlCambios = new ControlCambios();
        	controlCambios.setCodExpediente(hojaConsulta.getCodExpediente());
        	controlCambios.setFecha(today);
        	controlCambios.setNombreCampo("Planes");
        	controlCambios.setValorCampo(hojaConsulta.getPlanes());
        	controlCambios.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
        	controlCambios.setTipoControl(Modificacion);
        	controlCambios.setUsuario(usuario);
        	controlCambios.setControlador("DiagnosticoPlanesActivity");
        	
        	HIBERNATE_RESOURCE.getSession().save(controlCambios);       	
        	HIBERNATE_RESOURCE.commit();            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
        	
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result; 
	}
	
	@Override
	public String guardarCtrlProxCita(HojaConsulta hcActual, HojaConsulta hcNueva, String usuario){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();
            
	        if(hcActual.getProximaCita() != null && hcNueva.getProximaCita() != null 
	        		&& (hcNueva.getProximaCita().compareTo(hcActual.getProximaCita()) != 0)){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, Modificacion);        	
	        	controlCambios.setNombreCampo("Proxima Cita");
	        	controlCambios.setValorCampo( new SimpleDateFormat("dd/MM/yyyy").format(hcActual.getProximaCita()));
	        	controlCambios.setUsuario(usuario);
	        	controlCambios.setControlador("DiagnosticoProximaCitaActivity");
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
        	if((hcNueva.getTelef() != null && hcActual.getTelef() == null) ||
        	   (hcNueva.getTelef() == null && hcActual.getTelef() != null) ||
               (hcActual.getTelef() != null && hcNueva.getTelef().longValue() != hcActual.getTelef().longValue())){
	        	
        		ControlCambios controlCambios = getCtrlCambios(hcActual, today, Modificacion);
	        	controlCambios.setNombreCampo("Telefono");
	        	if(hcActual.getTelef() != null) {
	        		controlCambios.setValorCampo(hcActual.getTelef().toString());
	        	} else {
	        		controlCambios.setValorCampo("");
	        	}
	        	controlCambios.setUsuario(usuario);
	        	controlCambios.setControlador("DiagnosticoProximaCitaActivity");
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getHorarioClases() != null && hcNueva.getHorarioClases() != null 
        			&& (hcNueva.getHorarioClases().trim().compareTo(hcActual.getHorarioClases().trim()) != 0)){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, Modificacion);
	        	controlCambios.setNombreCampo("Horario Clases");
	        	controlCambios.setValorCampo(hcActual.getHorarioClases());
	        	controlCambios.setUsuario(usuario);
	        	controlCambios.setControlador("DiagnosticoProximaCitaActivity");
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	private ControlCambios getCtrlCambios(HojaConsulta hojaConsulta, Date today, String usuario, String tipoControl, String controlador){
		ControlCambios controlCambios = new ControlCambios();
    	controlCambios.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
    	controlCambios.setCodExpediente(hojaConsulta.getCodExpediente());
    	controlCambios.setTipoControl(tipoControl);
    	controlCambios.setFecha(today);
    	controlCambios.setUsuario(usuario);
    	controlCambios.setControlador(controlador);
    	
    	return controlCambios;
	}
	
	@Override
	public String guardarCtrlTratamiento(HojaConsulta hcActual, HojaConsulta hcNueva, String usuario){
		String result;
		try {			
				                
	        Date today = new Date();
	        String controlador = "DiagnosticoTratamientoActivity";
	        
	        HIBERNATE_RESOURCE.begin();
            
	        if(hcActual.getAcetaminofen().charValue() != hcNueva.getAcetaminofen().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);        	
	        	controlCambios.setNombreCampo("Acetaminofen");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getAcetaminofen()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
        	if(hcActual.getAsa().charValue() != hcNueva.getAsa().charValue()){	        	
        		ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Asa");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getAsa()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getIbuprofen().charValue() != hcNueva.getIbuprofen().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Ibuprofen");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getIbuprofen()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getPenicilina().charValue() != hcNueva.getPenicilina().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Penicilina");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getPenicilina()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getAmoxicilina().charValue() != hcNueva.getAmoxicilina().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Amoxicilina");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getAmoxicilina()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getDicloxacilina().charValue() != hcNueva.getDicloxacilina().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Dicloxacilina");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getDicloxacilina()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(!hcActual.getOtroAntibiotico().equalsIgnoreCase(hcNueva.getOtroAntibiotico())){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Discrepancia, controlador);
	        	controlCambios.setNombreCampo("Otro Antibiotico");
	        	controlCambios.setValorCampo(hcActual.getOtroAntibiotico());	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getOtro().charValue() != hcNueva.getOtro().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Otro");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getOtro()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getFurazolidona().charValue() != hcNueva.getFurazolidona().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Furazolidona");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getFurazolidona()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getMetronidazolTinidazol().charValue() != hcNueva.getMetronidazolTinidazol().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Metronidazol Tinidazol");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getMetronidazolTinidazol()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getAlbendazolMebendazol().charValue() != hcNueva.getAlbendazolMebendazol().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Albendazol Mebendazol");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getAlbendazolMebendazol()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getSulfatoFerroso().charValue() != hcNueva.getSulfatoFerroso().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Sulfato Ferroso");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getSulfatoFerroso()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getSueroOral().charValue() != hcNueva.getSueroOral().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Suero Oral");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getSueroOral()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getSulfatoZinc().charValue() != hcNueva.getSulfatoZinc().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Sulfato Zinc");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getSulfatoZinc()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getLiquidosIv().charValue() != hcNueva.getLiquidosIv().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Liquidos Iv");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getLiquidosIv()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getPrednisona().charValue() != hcNueva.getPrednisona().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Prednisona");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getPrednisona()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getHidrocortisonaIv().charValue() != hcNueva.getHidrocortisonaIv().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Hidrocortisona Iv");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getHidrocortisonaIv()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getSalbutamol().charValue() != hcNueva.getSalbutamol().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Salbutamol");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getSalbutamol()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(hcActual.getOseltamivir().charValue() != hcNueva.getOseltamivir().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Oseltamivir");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getOseltamivir()));	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
        	if(!hcActual.getPlanes().equalsIgnoreCase(hcNueva.getPlanes())){
	        	ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
	        	controlCambios.setNombreCampo("Planes");
	        	controlCambios.setValorCampo(hcActual.getPlanes());	        	
	        	
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
        	}
        	
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	@Override
	public String guardarControlCambios(HojaConsulta hojaConsulta, RespiratorioControlCambio cc){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();        
	        
	        if(cc.getAleteoNasal().charValue() != hojaConsulta.getAleteoNasal().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);        	
	        	controlCambios.setNombreCampo("Aleteo Nasal");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getAleteoNasal()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
	        if(cc.getApnea().charValue() != hojaConsulta.getApnea().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Apnea");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getApnea()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getCongestionNasal().charValue() != hojaConsulta.getCongestionNasal().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Congestion Nasal");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getCongestionNasal()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getCrepitos().charValue() != hojaConsulta.getCrepitos().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Crepitos");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getCrepitos()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if((cc.getEstiradorReposo() != null && hojaConsulta.getEstiradorReposo() == null) || 
	        		(cc.getEstiradorReposo() == null && hojaConsulta.getEstiradorReposo() != null) ||
	        		(cc.getEstiradorReposo().charValue() != hojaConsulta.getEstiradorReposo().charValue())){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Estirador Reposo");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getEstiradorReposo()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getOtalgia().charValue() != hojaConsulta.getOtalgia().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Otalgia");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getOtalgia()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if((cc.getOtraFif() != null && hojaConsulta.getOtraFif() == null) || 
	        		(cc.getOtraFif() == null && hojaConsulta.getOtraFif() != null) ||
	        		(cc.getOtraFif().charValue() != hojaConsulta.getOtraFif().charValue())){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Otra Fif");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getOtraFif()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getQuejidoEspiratorio().charValue() != hojaConsulta.getQuejidoEspiratorio().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Quejido Espiratorio");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getQuejidoEspiratorio()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getRespiracionRapida().charValue() != hojaConsulta.getRespiracionRapida().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Respiracion Rapida");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getRespiracionRapida()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getRinorrea().charValue() != hojaConsulta.getRinorrea().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Rinorrea");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getRinorrea()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getRoncos().charValue() != hojaConsulta.getRoncos().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Roncos");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getRoncos()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getSibilancias().charValue() != hojaConsulta.getSibilancias().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Sibilancias");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getSibilancias()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getTirajeSubcostal().charValue() != hojaConsulta.getTirajeSubcostal().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Tiraje Subcostal");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getTirajeSubcostal()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getTos().charValue() != hojaConsulta.getTos().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Tos");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getTos()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if((cc.getNuevaFif() != null && hojaConsulta.getNuevaFif() == null) || 
	        		(cc.getNuevaFif() == null && hojaConsulta.getNuevaFif() != null) ||
	        		(cc.getNuevaFif().compareTo(hojaConsulta.getNuevaFif()) != 0)){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Nueva Fif");
	        	if(hojaConsulta.getNuevaFif() != null) {
	        		controlCambios.setValorCampo(new SimpleDateFormat("dd/MM/yyyy").format(hojaConsulta.getNuevaFif()));
	        	} else {
	        		controlCambios.setValorCampo("");
	        	}
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	@Override
	public String guardarControlCambios(HojaConsulta hojaConsulta, VacunaControlCambio cc){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();        
	        
	        if(cc.getLactanciaMaterna().charValue() != hojaConsulta.getLactanciaMaterna().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);        	
	        	controlCambios.setNombreCampo("Lactancia Materna");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getLactanciaMaterna()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
	        if(cc.getVacunaInfluenza().charValue() != hojaConsulta.getVacunaInfluenza().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Vacuna Influenza");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getVacunaInfluenza()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getVacunasCompletas().charValue() != hojaConsulta.getVacunasCompletas().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Vacunas Completas");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getVacunasCompletas()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if((cc.getFechaVacuna() != null && hojaConsulta.getFechaVacuna() == null) || 
	        		(cc.getFechaVacuna() == null && hojaConsulta.getFechaVacuna() != null) ||
	        		(hojaConsulta.getFechaVacuna() != null && cc.getFechaVacuna().compareTo(hojaConsulta.getFechaVacuna()) != 0)){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Fecha Vacuna");
	        	if(hojaConsulta.getFechaVacuna() != null) {
	        		controlCambios.setValorCampo(new SimpleDateFormat("dd/MM/yyyy").format(hojaConsulta.getFechaVacuna()));
	        	} else {
	        		controlCambios.setValorCampo("");
	        	}
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	@Override
	public String guardarControlCambios(HojaConsulta hojaConsulta, EstadoNutricionalControlCambio cc){
		String result;
		try {			
				                
	        Date today = new Date();
	        
	        HIBERNATE_RESOURCE.begin();        
	        
	        if(cc.getBajoPeso().charValue() != hojaConsulta.getBajoPeso().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);        	
	        	controlCambios.setNombreCampo("Bajo Peso");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getBajoPeso()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
	        if(cc.getBajoPesoSevero().charValue() != hojaConsulta.getBajoPesoSevero().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Bajo Peso Severo");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getBajoPesoSevero()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getImc().compareTo(hojaConsulta.getImc()) != 0){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("IMC");
	        	controlCambios.setValorCampo(cc.getImc().toString());
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getNormal().charValue() != hojaConsulta.getNormal().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);        	
	        	controlCambios.setNombreCampo("Normal");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getNormal()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
	        if(cc.getObeso().charValue() != hojaConsulta.getObeso().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Obeso");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getObeso()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
	        if(cc.getSobrepeso().charValue() != hojaConsulta.getSobrepeso().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);        	
	        	controlCambios.setNombreCampo("Sobrepeso");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getSobrepeso()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
        	
	        if(cc.getSospechaProblema().charValue() != hojaConsulta.getSospechaProblema().charValue()){
	        	ControlCambios controlCambios = getCtrlCambios(hojaConsulta, cc.getUsuario(), cc.getControlador(), today);
	        	controlCambios.setNombreCampo("Sospecha Problema");
	        	controlCambios.setValorCampo(UtilResultado.getValorChar(hojaConsulta.getSospechaProblema()));
	        	HIBERNATE_RESOURCE.getSession().save(controlCambios);
	        }
	        
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
	        if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	        	HIBERNATE_RESOURCE.close();
	        }
	    }
		return result;
	}
	
	@Override
	public String guardarCtrlReferencia(HojaConsulta hcActual, HojaConsulta hcNueva, String usuario){
		String result;
		try {			
								
			Date today = new Date();
			String controlador = "ReferenciaSintomasActivity";
			
			HIBERNATE_RESOURCE.begin();		
			
			if( hcActual.getInterconsultaPediatrica().charValue() != hcNueva.getInterconsultaPediatrica().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Interconsulta Pediatrica");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getInterconsultaPediatrica()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getReferenciaHospital().charValue() != hcNueva.getReferenciaHospital().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Referencia Hospital");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getReferenciaHospital()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getReferenciaDengue().charValue() != hcNueva.getReferenciaDengue().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Referencia Dengue");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getReferenciaDengue()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getReferenciaIrag().charValue() != hcNueva.getReferenciaIrag().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Referencia Irag");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getReferenciaIrag()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getReferenciaChik().charValue() != hcNueva.getReferenciaChik().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Referencia Chik");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getReferenciaChik()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getEti().charValue() != hcNueva.getEti().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Eti");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getEti()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getIrag().charValue() != hcNueva.getIrag().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Irag");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getIrag()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getNeumonia().charValue() != hcNueva.getNeumonia().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Neumonia");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getNeumonia()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}		
			
			HIBERNATE_RESOURCE.commit();
			
			result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
			
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}
	
	@Override
	public String guardarCtrlPreclinicos(HojaConsulta hcActual, HojaConsulta hcNueva, String usuario){
		String result;
		try {			
								
			Date today = new Date();
			String controlador = "ConsultaActivity";
			
			HIBERNATE_RESOURCE.begin();		
			
			if( hcActual.getPesoKg().compareTo(hcNueva.getPesoKg()) != 0){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Peso Kg");
				controlCambios.setValorCampo(hcActual.getPesoKg().toString());	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getTallaCm().compareTo(hcNueva.getTallaCm()) != 0){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Talla cm");
				controlCambios.setValorCampo(hcActual.getTallaCm().toString());	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getTemperaturac().compareTo(hcNueva.getTemperaturac()) != 0){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Temperatura C°");
				controlCambios.setValorCampo(hcActual.getTemperaturac().toString());	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getExpedienteFisico().compareTo(hcNueva.getExpedienteFisico()) != 0){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Expediente Fisico");
				controlCambios.setValorCampo(hcActual.getExpedienteFisico());	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			HIBERNATE_RESOURCE.commit();
			
			result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
			
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}
	
	@Override
	public String guardarCtrlGastroIn(HojaConsulta hcActual, HojaConsulta hcNueva, String usuario, String controlador){
		String result;
		try {			
								
			Date today = new Date();
			
			HIBERNATE_RESOURCE.begin();		
			
			if( hcActual.getPocoApetito().charValue() != hcNueva.getPocoApetito().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Poco Apetito");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getPocoApetito()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getNausea().charValue() != hcNueva.getNausea().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Nausea");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getNausea()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getDificultadAlimentarse().charValue() != hcNueva.getDificultadAlimentarse().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Dificultad Alimentarse");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getDificultadAlimentarse()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if((hcActual.getVomito12horas() != null && hcNueva.getVomito12horas() == null) ||
			   (hcActual.getVomito12horas() == null && hcNueva.getVomito12horas() != null) ||
			   (hcActual.getVomito12horas() != null && hcActual.getVomito12horas().charValue() != hcNueva.getVomito12horas().charValue())){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Vomito 12 horas");
				if(hcActual.getVomito12horas() != null) {
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getVomito12horas()));
				} else {
					controlCambios.setValorCampo("''");
				}
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if ((hcActual.getVomito12h() != null && hcNueva.getVomito12h() == null) || 
				(hcActual.getVomito12h() == null && hcNueva.getVomito12h() != null) || 
				(hcActual.getVomito12h() != null && 
					hcActual.getVomito12h().shortValue() != hcNueva.getVomito12h().shortValue())) {
				ControlCambios controlCambios = getCtrlCambios(hcActual, today,
						usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Vomito # 12 horas");
				if (hcActual.getVomito12horas() != null) {
					controlCambios.setValorCampo(hcActual.getVomito12h()
							.toString());
				} else {
					controlCambios.setValorCampo("");
				}

				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getDiarrea().charValue() != hcNueva.getDiarrea().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Diarrea");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getDiarrea()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getDiarreaSangre().charValue() != hcNueva.getDiarreaSangre().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Diarrea Sangre");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getDiarreaSangre()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getEstrenimiento().charValue() != hcNueva.getEstrenimiento().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Estrenimiento");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getEstrenimiento()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if( hcActual.getDolorAbIntermitente().charValue() != hcNueva.getDolorAbIntermitente().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Dolor Ab Intermitente");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getDolorAbIntermitente()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}			

			if( hcActual.getDolorAbContinuo().charValue() != hcNueva.getDolorAbContinuo().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Dolor Ab Continuo");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getDolorAbContinuo()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}	

			if( hcActual.getEpigastralgia().charValue() != hcNueva.getEpigastralgia().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Epigastralgia");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getEpigastralgia()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}

			if( hcActual.getIntoleranciaOral().charValue() != hcNueva.getIntoleranciaOral().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Intolerancia Oral");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getIntoleranciaOral()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}

			if( hcActual.getDistensionAbdominal().charValue() != hcNueva.getDistensionAbdominal().charValue() ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Distension Abdominal");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getDistensionAbdominal()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}

			if((hcActual.getHepatomegalia() != null && hcNueva.getHepatomegalia() == null) ||
			   (hcActual.getHepatomegalia() == null && hcNueva.getHepatomegalia() != null) ||
			   (hcActual.getHepatomegalia() != null && 
			   		hcActual.getHepatomegalia().charValue() != hcNueva.getHepatomegalia().charValue())){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Hepatomegalia");
				controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getHepatomegalia()));	        	
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if ((hcActual.getHepatomegaliaCm() != null && hcNueva.getHepatomegaliaCm() == null) || 
				(hcActual.getHepatomegaliaCm() == null && hcNueva.getHepatomegaliaCm() != null) || 
				(hcActual.getHepatomegaliaCm() != null && 
					hcActual.getHepatomegaliaCm().doubleValue() != hcNueva.getHepatomegaliaCm().doubleValue())) {
				ControlCambios controlCambios = getCtrlCambios(hcActual, today,
						usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Hepatomegalia cm");
				if (hcActual.getVomito12horas() != null) {
					controlCambios.setValorCampo(hcActual.getHepatomegaliaCm()
							.toString());
				} else {
					controlCambios.setValorCampo("");
				}

				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			HIBERNATE_RESOURCE.commit();
			
			result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
			
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}
	
	@Override
	public String guardarCtrlCategoria(HojaConsulta hcActual, HojaConsulta hcNueva, String usuario){
		String result;
		try {			
								
			Date today = new Date();
			String controlador = "CategoriaSintomaActivity";
			
			HIBERNATE_RESOURCE.begin();		
			
			if( (hcActual.getSaturaciono2() != null && hcNueva.getSaturaciono2() == null) ||
					(hcActual.getSaturaciono2() == null && hcNueva.getSaturaciono2() != null) ||
					(hcActual.getSaturaciono2() != null && hcNueva.getSaturaciono2() != null && hcActual.getSaturaciono2().intValue() != hcNueva.getSaturaciono2().intValue()) ){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Saturación O2");
				if(hcActual.getSaturaciono2() != null) {
					controlCambios.setValorCampo(hcActual.getSaturaciono2().toString());
				} else {
					controlCambios.setValorCampo("");
				}
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if(hcActual.getCategoria() != null && hcNueva.getCategoria() != null) {
				if(hcActual.getCategoria().trim().toLowerCase().compareTo(hcNueva.getCategoria().
						trim().toLowerCase()) != 0){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Categoria");
					controlCambios.setValorCampo(hcActual.getCategoria());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if((hcActual.getCambioCategoria() != null && hcNueva.getCambioCategoria() == null) ||
					(hcActual.getCambioCategoria() == null && hcNueva.getCambioCategoria() != null) ||
					(hcActual.getCambioCategoria().charValue() != hcNueva.getCambioCategoria().charValue())){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Cambio Categoria");
				if(hcActual.getCambioCategoria() != null) {
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getCambioCategoria()));
				} else {
					controlCambios.setValorCampo("");
				}
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if(hcActual.getManifestacionHemorragica() != null && hcNueva.getManifestacionHemorragica() != null) {
				if(hcActual.getManifestacionHemorragica().charValue() != hcNueva.getManifestacionHemorragica().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Manifestacion Hemorragica");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getManifestacionHemorragica()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getPruebaTorniquetePositiva() != null && hcNueva.getPruebaTorniquetePositiva() != null) {
				if(hcActual.getPruebaTorniquetePositiva().charValue() != hcNueva.getPruebaTorniquetePositiva().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Prueba Torniquete Positiva");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getPruebaTorniquetePositiva()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getPetequia10Pt() != null && hcNueva.getPetequia10Pt() != null) {
				if(hcActual.getPetequia10Pt().charValue() != hcNueva.getPetequia10Pt().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Petequia 10 Pt");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getPetequia10Pt()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getPetequia20Pt() != null && hcNueva.getPetequia20Pt() != null) {
				if(hcActual.getPetequia20Pt().charValue() != hcNueva.getPetequia20Pt().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Petequia 20 Pt");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getPetequia20Pt()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getPielExtremidadesFrias() != null && hcNueva.getPielExtremidadesFrias() != null) {
				if(hcActual.getPielExtremidadesFrias().charValue() != hcNueva.getPielExtremidadesFrias().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Piel Extremidades Frias");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getPielExtremidadesFrias()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getPalidezEnExtremidades() != null && hcNueva.getPalidezEnExtremidades() != null) {
				if(hcActual.getPalidezEnExtremidades().charValue() != hcNueva.getPalidezEnExtremidades().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Palidez en Extremidades");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getPalidezEnExtremidades()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getEpistaxis() != null && hcNueva.getEpistaxis() != null) {
				if(hcActual.getEpistaxis().charValue() != hcNueva.getEpistaxis().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Epistaxis");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getEpistaxis()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getGingivorragia() != null && hcNueva.getGingivorragia() != null) {
				if(hcActual.getGingivorragia().charValue() != hcNueva.getGingivorragia().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Gingivorragia");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getGingivorragia()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getPetequiasEspontaneas() != null && hcNueva.getPetequiasEspontaneas() != null) {
				if(hcActual.getPetequiasEspontaneas().charValue() != hcNueva.getPetequiasEspontaneas().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Petequias Espontaneas");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getPetequiasEspontaneas()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getLlenadoCapilar2seg() != null && hcNueva.getLlenadoCapilar2seg() != null) {
				if(hcActual.getLlenadoCapilar2seg().charValue() != hcNueva.getLlenadoCapilar2seg().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Llenado Capilar 2 seg");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getLlenadoCapilar2seg()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getCianosis() != null && hcNueva.getCianosis() != null) {
				if(hcActual.getCianosis().charValue() != hcNueva.getCianosis().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Cianosis");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getCianosis()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if((hcActual.getLinfocitosaAtipicos() != null && hcNueva.getLinfocitosaAtipicos() == null) ||
					(hcActual.getLinfocitosaAtipicos() == null && 
						hcNueva.getLinfocitosaAtipicos() != null)||
					(hcActual.getLinfocitosaAtipicos() != null && hcActual.getLinfocitosaAtipicos().compareTo(hcNueva.getLinfocitosaAtipicos()) != 0)) {
				
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Linfocitos Atipicos");
				
				if(hcActual.getLinfocitosaAtipicos() != null) {
					controlCambios.setValorCampo(hcActual.getLinfocitosaAtipicos().toString());
				} else {
					controlCambios.setValorCampo("");
				}
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if((hcActual.getFechaLinfocitos() != null && hcNueva.getFechaLinfocitos() == null) ||
					(hcActual.getFechaLinfocitos() == null && hcNueva.getFechaLinfocitos() != null) ||
					(hcActual.getFechaLinfocitos() != null && hcActual.getFechaLinfocitos().compareTo(hcNueva.getFechaLinfocitos()) != 0)){
				ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
				controlCambios.setNombreCampo("Fecha Linfocitos");
				
				if(hcActual.getFechaLinfocitos() != null) {
					controlCambios.setValorCampo(new SimpleDateFormat("dd/MM/yyyy").format(hcActual.getFechaLinfocitos()));
				} else {
					controlCambios.setValorCampo("");
				}
				
				HIBERNATE_RESOURCE.getSession().save(controlCambios);
			}
			
			if(hcActual.getHipermenorrea() != null && hcNueva.getHipermenorrea() != null) {
				if(hcActual.getHipermenorrea().charValue() != hcNueva.getHipermenorrea().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Hipermenorrea");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getHipermenorrea()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getHematemesis() != null && hcNueva.getHematemesis() != null) {
				if(hcActual.getHematemesis().charValue() != hcNueva.getHematemesis().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Hematemesis");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getHematemesis()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getMelena() != null && hcNueva.getMelena() != null) {
				if(hcActual.getMelena().charValue() != hcNueva.getMelena().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Melena");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getMelena()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getHemoconc() != null && hcNueva.getHemoconc() != null) {
				if(hcActual.getHemoconc().charValue() != hcNueva.getHemoconc().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Hemoconcentración");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getHemoconc()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getHemoconcentracion() != null && hcNueva.getHemoconcentracion() != null) {
				if(hcActual.getHemoconcentracion().compareTo(hcNueva.getHemoconcentracion()) != 0){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Hemoconcentración");
					controlCambios.setValorCampo(hcActual.getHemoconcentracion().toString());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getHospitalizado() != null && hcNueva.getHospitalizado() != null) {
				if(hcActual.getHospitalizado().charValue() != hcNueva.getHospitalizado().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Ha sido hospitalizado en el último año?");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getHospitalizado()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getTransfusionSangre() != null && hcNueva.getTransfusionSangre() != null) {
				if(hcActual.getTransfusionSangre().charValue() != hcNueva.getTransfusionSangre().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Recibió Transfunsión de sangre en el último año?");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getTransfusionSangre()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getTomandoMedicamento() != null && hcNueva.getTomandoMedicamento() != null) {
				if(hcActual.getTomandoMedicamento().charValue() != hcNueva.getTomandoMedicamento().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Está tomando medicamento en este momento?");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getTomandoMedicamento()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getMedicamentoDistinto() != null && hcNueva.getMedicamentoDistinto() != null) {
				if(hcActual.getMedicamentoDistinto().charValue() != hcNueva.getMedicamentoDistinto().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Toma un medicamento distinto al mencionado en los últimos 6 meses?");
					controlCambios.setValorCampo(UtilResultado.getValorChar(hcActual.getMedicamentoDistinto()));	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			
			HIBERNATE_RESOURCE.commit();
			
			result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
			
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}
	
	@Override
	public String guardarCtrlGenerales(HojaConsulta hcActual, HojaConsulta hcNueva, String usuario){
		String result;
		try {			
								
			Date today = new Date();
			String controlador = "GeneralesSintomasActivity";
			
			HIBERNATE_RESOURCE.begin();		
			
			if(hcActual.getPas() != null && hcNueva.getPas() != null) {
				if(hcActual.getPas().intValue() != hcNueva.getPas().intValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("PAS");
					controlCambios.setValorCampo(hcActual.getPas().toString());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getPad() != null && hcNueva.getPad() != null) {
				if(hcActual.getPad().compareTo(hcNueva.getPad()) != 0){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("PAD");
					controlCambios.setValorCampo(hcActual.getPad().toString());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getFciaResp() != null && hcNueva.getFciaResp() != null) {
				if(hcActual.getFciaResp().intValue() != hcNueva.getFciaResp().intValue()){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Fcia Resp");
					controlCambios.setValorCampo(hcActual.getFciaResp().toString());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getFciaCard() != null && hcNueva.getFciaCard() != null) {
				if(hcActual.getFciaCard().intValue() != hcNueva.getFciaCard().intValue()){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Fcia Card");
					controlCambios.setValorCampo(hcActual.getFciaCard().toString());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getLugarAtencion() != null && hcNueva.getLugarAtencion() != null) {
				if(hcActual.getLugarAtencion().trim().toLowerCase().compareTo(hcNueva.getLugarAtencion().trim().toLowerCase()) != 0){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Lugar de Atención");
					controlCambios.setValorCampo(hcActual.getLugarAtencion());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getConsulta() != null && hcNueva.getConsulta() != null) {
				if(hcActual.getConsulta().trim().toLowerCase().compareTo(hcNueva.getConsulta().trim().toLowerCase()) != 0){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Consulta inicial");
					controlCambios.setValorCampo(hcActual.getConsulta());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getSegChick() != null && hcNueva.getSegChick() != null) {
				if(hcActual.getSegChick().charValue() != hcNueva.getSegChick().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Seguimiento Chick");
					controlCambios.setValorCampo(hcActual.getSegChick().toString());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getTurno() != null && hcNueva.getTurno() != null) {
				if(hcActual.getTurno().charValue() != hcNueva.getTurno().charValue() ){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Piel Extremidades Frias");
					
					if (hcActual.getTurno().charValue() == '1') {
						controlCambios.setValorCampo("Regular");
                    } else if (hcActual.getTurno().charValue() == '2') {
                    	controlCambios.setValorCampo("Noche");
                    } else {
                    	controlCambios.setValorCampo("Fin de semana");
                    }
					
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getTemMedc() != null && hcNueva.getTemMedc() != null) {
				if(hcActual.getTemMedc().doubleValue() != hcNueva.getTemMedc().doubleValue()){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Temp Medico C°");
					controlCambios.setValorCampo(hcActual.getTemMedc().toString());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getFis() != null && hcNueva.getFis() != null) {
				if(hcActual.getFis().compareTo(hcNueva.getFis()) != 0){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("FIS");
					controlCambios.setValorCampo(hcActual.getFis().toString());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getFif() != null && hcNueva.getFif() != null) {
				if(hcActual.getFif().compareTo(hcNueva.getFif()) != 0){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("FIF");
					controlCambios.setValorCampo(hcActual.getFif().toString());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getUltDiaFiebre() != null && hcNueva.getUltDiaFiebre() != null) {
				if(hcActual.getUltDiaFiebre().compareTo(hcNueva.getUltDiaFiebre()) != 0){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Ultimo dia fiebre");
					controlCambios.setValorCampo(hcActual.getUltDiaFiebre().toString());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getUltDosisAntipiretico() != null && hcNueva.getUltDosisAntipiretico() != null) {
				if(hcActual.getUltDosisAntipiretico().compareTo(hcNueva.getUltDosisAntipiretico()) != 0){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Ultima dosis antipirético");
					controlCambios.setValorCampo(hcActual.getUltDosisAntipiretico().toString());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getAmPmUltDiaFiebre() != null && hcNueva.getAmPmUltDiaFiebre() != null) {
				if(hcActual.getAmPmUltDiaFiebre().compareTo(hcNueva.getAmPmUltDiaFiebre()) != 0){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("AM PM Ultimo Dia Fiebre");
					controlCambios.setValorCampo(hcActual.getAmPmUltDiaFiebre().toString());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getHoraUltDosisAntipiretico() != null && hcNueva.getHoraUltDosisAntipiretico() != null) {
				if(hcActual.getHoraUltDosisAntipiretico().compareTo(hcNueva.getHoraUltDosisAntipiretico()) != 0){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("Hora");
					controlCambios.setValorCampo(hcActual.getHoraUltDosisAntipiretico().toString());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			if(hcActual.getAmPmUltDosisAntipiretico() != null && hcNueva.getAmPmUltDosisAntipiretico() != null) {
				if(hcActual.getAmPmUltDosisAntipiretico().compareTo(hcNueva.getAmPmUltDosisAntipiretico()) != 0){
					ControlCambios controlCambios = getCtrlCambios(hcActual, today, usuario, Modificacion, controlador);
					controlCambios.setNombreCampo("AM PM Hora");
					controlCambios.setValorCampo(hcActual.getAmPmUltDosisAntipiretico());	        	
					
					HIBERNATE_RESOURCE.getSession().save(controlCambios);
				}
			}
			
			HIBERNATE_RESOURCE.commit();
			
			result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
			
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();

		}finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}
	
}
