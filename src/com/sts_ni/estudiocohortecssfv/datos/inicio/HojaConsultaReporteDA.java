package com.sts_ni.estudiocohortecssfv.datos.inicio;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ni.com.sts.estudioCohorteCSSFV.modelo.HojaConsulta;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.sts_ni.estudiocohortecssfv.datos.controlcambios.ControlCambiosDA;
import com.sts_ni.estudiocohortecssfv.dto.HojaConsultaReporte;
import com.sts_ni.estudiocohortecssfv.servicios.ControlCambiosService;
import com.sts_ni.estudiocohortecssfv.servicios.HojaConsultaReporteService;
import com.sts_ni.estudiocohortecssfv.util.HibernateResource;
import com.sts_ni.estudiocohortecssfv.util.UtilitarioReporte;

/***
 * Clase para conexion a la BD que contiene los metodos para obtener la impresion de la 
 * Hoja consulta PDF.
 */
public class HojaConsultaReporteDA implements HojaConsultaReporteService {
	
	
	private static final HibernateResource HIBERNATE_RESOURCE = new HibernateResource();
	private ControlCambiosService controlCambiosService = new ControlCambiosDA();
	/***
	 * Metodo Para imprimir en la impresora por defecto la Hoja de consulta.
	 */
	public void imprimirConsultaPdf(Integer secHojaConsulta){
		
		 UtilitarioReporte ureporte=new UtilitarioReporte();
	     ureporte.imprimirDocumento("HojaConsulta_" + secHojaConsulta, 
	    		 					getHojaConsultaPdf( secHojaConsulta));

	}
	
	public void imprimirConsultaTest(){
		
		 UtilitarioReporte ureporte=new UtilitarioReporte();
	     ureporte.imprimirDocumentoTest("HojaConsulta");

	}

	/***
	 * Metodo que carga toda la informacion de la hoja de consulta por su Id.
	 * @param secHojaConsulta, Id Hoja de Consulta.
	 */
	public byte[] getHojaConsultaPdf(Integer secHojaConsulta){
	String nombreReporte="HojaConsulta";
	try {
	    List  oLista = new LinkedList(); //Listado final para el resultado
	  
	
	    String sql = " select " +
				" h.cod_expediente \"codExpediente\" ,  " +
				" h.fecha_consulta \"fechaConsulta\", " +
				"p.nombre1 ||' '||COALESCE(p.nombre2,'')||' '||p.apellido1||' '||COALESCE(p.apellido2,'') \"nombreApellido\", " +
				"h.peso_kg \"pesoKg\", " +
				"h.talla_cm \"tallaCm\",  " +
				"p.edad, " +
				"obtenerEdad(fecha_nac) \"edadCalculada\", " +
				"p.fecha_nac \"FechaNac\", " +
				"p.sexo, " +
				"h.fcia_resp \"fciaResp\", " +
				"h.fcia_card \"fciaCard\", " +
				"h.temperaturac \"temperaturaC\", " +
				"h.lugar_atencion \"lugarAtencion\", " +
				"h.consulta, " +
				"h.turno , " +
				"h.tem_medc \"temMedc\", " +
				"h.fis, " +
				"h.fif, " +
				"h.ult_dia_fiebre \"ultDiaFiebre\", " +
				"h.ult_dosis_antipiretico \"ultDosisAntipiretico\", " +
				"to_char(h.hora_ult_dosis_antipiretico, 'HH12:MI') \"horaUltDosisAntipiretico\", " +
				"h.am_pm_ult_dosis_antipiretico \"amPmUltDosisAntipiretico\", " +
				"h.am_pm_ult_dia_fiebre \"amPmUltDiaFiebre\", "+
				"h.fiebre, " +
				"h.astenia, " +
				"h.asomnoliento, " +
				"h.mal_estado \"malEstado\", " +
				"h.perdida_consciencia \"perdidaConsciencia\", " +
				"h.inquieto, " +
				"h.convulsiones, " +
				"h.hipotermia, " +
				"h.letargia, " +
				"h.poco_apetito \"pocoApetito\", " +
				"h.nausea, " +
				"h.dificultad_alimentarse \"dificultadAlimentarse\", " +
				"h.vomito_12horas \"vomito12Horas\"  , h.vomito12h, " +
				"h.diarrea, " +
				"h.diarrea_sangre \"diarreaSangre\", " +
				"h.estrenimiento, " +
				"h.dolor_ab_intermitente \"dolorAbIntermitente\", " +
				"h.dolor_ab_continuo \"dolorAbContinuo\", " +
				"h.epigastralgia, " +
				"h.intolerancia_oral \"intoleranciaOral\", " +
				"h.distension_abdominal \"distensionAbdominal\", " +
				"h.hepatomegalia_cm \"hepatomegaliaCm\", " +
				"h.hepatomegalia , " +
				"h.altralgia, " +
				"h.mialgia, " +
				"h.lumbalgia, " +
				"h.dolor_cuello \"dolorCuello\", " +
				"h.tenosinovitis, " +
				"h.artralgia_proximal \"artralgiaProximal\", " +
				"h.artralgia_distal \"artralgiaDistal\", " +
				"h.conjuntivitis, " +
				"h.edema_munecas \"edemaMunecas\", " +
				"h.edema_codos \"edemaCodos\", " +
				"h.edema_hombros \"edemaHombros\", " +
				"h.edema_rodillas \"edemaRodillas\", " +
				"h.edema_tobillos \"edemaTobillos\", " +
				"h.cefalea, " +
				"h.rigidez_cuello \"rigidezCuello\", " +
				"h.inyeccion_conjuntival \"inyeccionConjuntival\", " +
				"h.hemorragia_suconjuntival \"hemorragiaSuconjuntival\", " +
				"h.dolor_retroocular \"dolorRetroocular\", " +
				"h.fontanela_abombada \"fontanelaAbombada\", " +
				"h.ictericia_conuntival \"ictericiaConuntival\", " +
				"h.lengua_mucosas_secas \"lenguaMucosasSecas\", " +
				"h.pliegue_cutaneo \"pliegueCutaneo\", " +
				"h.orina_reducida \"orinaReducida\", " +
				"h.bebe_con_sed \"bebeConSed\", " +
				"h.ojos_hundidos \"ojosHundidos\", " +
				"h.fontanela_hundida \"fontanelaHundida\", " +
				"h.rahs_localizado \"rahsLocalizado\", " +
				"h.rahs_generalizado \"rahsGeneralizado\", " +
				"h.rash_eritematoso \"rashEritematoso\", " +
				"h.rahs_macular \"rahsMacular\", " +
				"h.rash_papular \"rashPapular\", " +
				"h.rubor_facial \"ruborFacial\", " +
				"h.equimosis ," +
				"h.cianosis_central \"cianosisCentral\", " +
				"h.ictericia, " +
				"h.eritema," +
				" h.dolor_garganta \"dolorGarganta\", " +
				" h.adenopatias_cervicales \"adenopatiasCervicales\", " +
				" h.exudado, " +
				" h.petequias_mucosa \"petequiasMucosa\", " +
				" h.sintomas_urinarios \"sintomasUrinarios\", " +
				" h.leucocituria , " +
				" h.nitritos, " +
				" h.eritrocitos, " +
				" h.bilirrubinas, " +
				" h.interconsulta_pediatrica \"interconsultaPediatrica\", " +
				" h.referencia_hospital \"referenciaHospital\", " +
				" h.referencia_dengue \"referenciaDengue\", " +
				" h.referencia_irag \"referenciaIrag\", " +
				" h.referencia_chik \"referenciaChik\", " +
				" h.eti, " +
				" h.irag, " +
				" h.neumonia, " +
				" h.obeso, " +
				" h.sobrepeso \"sobrePeso\", " +
				" h.sospecha_problema \"sospechaProblema\" , " +
				" h.normal, " +
				" h.bajo_peso \"bajoPeso\", " +
				" h.bajo_peso_severo \"bajoPesoSevero\", " +
				" h.imc, " +
				" h.lactancia_materna \"lactanciaMaterna\", " +
				" h.vacunas_completas \"vacunasCompletas\", " +
				" h.vacuna_influenza \"vacunaInfluenza\", " +
				" h.fecha_vacuna \"fechaVacuna\", " +
				" h.tos, " +
				" h.rinorrea, " +
				" h.congestion_nasal \"congestionNasal\", " +
				" h.otalgia, " +
				" h.aleteo_nasal \"aleteoNasal\", " +
				" h.apnea, " +
				" h.respiracion_rapida \"respiracionRapida\", " +
				" h.quejido_espiratorio \"quejidoEspiratorio\", " +
				" h.estirador_reposo \"estiradorReposo\", " +
				" h.tiraje_subcostal \"tirajeSubcostal\", " +
				" h.sibilancias, " +
				" h.crepitos, " +
				" h.roncos, " +
				" h.otra_fif \"otraFif\", " +
				" h.nueva_fif \"nuevaFif\", " +
				" h.saturaciono2, " +
				" h.categoria, " +
				" h.cambio_categoria \"cambioCategoria\", " +
				" h.manifestacion_hemorragica \"manifestacionHemorragica\", " +
				" h.prueba_torniquete_positiva \"pruebaTorniquetePositiva\", " +
				" h.petequia_10_pt \"petequia10Pt\", " +
				" h.petequia_20_pt \"petequia20Pt\", " +
				" h.piel_extremidades_frias \"pielExtremidadesFrias\", " +
				" h.palidez_en_extremidades \"palidezEnExtremidades\", " +
				" h.epistaxis, " +
				" h.gingivorragia, " +
				" h.petequias_espontaneas \"petequiasEspontaneas\", " +
				" h.llenado_capilar_2seg \"llenadoCapilar2Seg\", " +
				" h.cianosis, " +
				" h.hipermenorrea, " +
				" h.hematemesis, " +
				" h.melena, h.hemoconc, " +
				" h.hemoconcentracion, " +
				" h.hospitalizado_especificar \"hospitalizadoEspecificar\", " +
				" h.transfusion_especificar \"transfusionEspecificar\", " +
				" h.medicamento_dist_especificar \"medicamentoDistEspecificar\", " +
				" h.tomando_medicamento \"tomandoMedicamento\" , " +
				" h.bhc, " +
				" h.serologia_dengue \"serologiaDengue\", " +
				" h.seg_chick \"segChick\", " +
				" h.gota_gruesa \"gotaGruesa\", " +
				" h.extendido_periferico \"extendidoPeriferico\", " +
				" h.ego, " +
				" h.egh, " +
				" h.citologia_fecal \"citologiaFecal\", " +
				" h.factor_reumatoideo \"factorReumatoideo\", " +
				" h.albumina, " +
				" h.ast_alt \"astAlt\", " +
				
				" h.cpk , " +
				" h.colesterol, " +
				" h.influenza, " +
				" h.otro_examen_lab \"otroExamenLab\", " +
				" h.oel, " +
				
				" h.acetaminofen, " +
				" h.asa, " +
				" h.furazolidona, " +
				" h.ibuprofen, " +
				" h.penicilina, " +
				" h.amoxicilina, " +
				" h.dicloxacilina, " +
				" h.otro_diagnostico \"otroDiagnostico\" , " +
				
				" h.metronidazol_tinidazol \"metronidazolTinidazol\", " +
				" h.albendazol_mebendazol \"albendazolMebendazol\", " +
				" h.sulfato_ferroso \"sulfatoFerroso\", " +
				" h.suero_oral \"sueroOral\", " +
				" h.sulfato_zinc \"sulfatoZinc\", " +
				" h.liquidos_iv \"liquidosIv\", " +
				" h.prednisona, " +
				" h.hidrocortisona_iv \"hidrocortisonaIv\", " +
				" h.salbutamol, " +
				" h.oseltamivir, " +
				
				" h.planes, " +
				" h.historia_examen_fisico \"historiaExamenFisico\", " +
				
				"(select diagnostico from diagnostico where sec_diagnostico=h.diagnostico1) diagnostico1, " +
				"(select diagnostico from diagnostico where sec_diagnostico=h.diagnostico2 ) diagnostico2, " +
				"(select diagnostico from diagnostico where sec_diagnostico=h.diagnostico3) diagnostico3, " +
				"(select diagnostico from diagnostico where sec_diagnostico= h.diagnostico4) diagnostico4, " +
				" p.telefono, " +
				" h.telef, h.proxima_cita \"proximaCita\",  " +
				" h.Colegio, " +
				" h.horario_clases \"horarioClases\", " +
				" h.otro, " +
				" h.otro_antibiotico \"otroAntibiotico\",  " +
				" ec.descripcion \"descripcionColegio\", " +
				" uvmed.nombre \"nombreUsuarioMedico\", " +
				" uvenf.nombre \"nombreUsuarioEnfermeria\", " +
				
				" h.medicamento_especificar \"medicamentoEspecificar\", " +
				" h.presion,  h.pas, h.pad, " +
				" h.expediente_fisico \"expedienteFisico\",  " +
				" uvmed.codigopersonal \"codigoMedico\",  " +
				" uvenf.codigopersonal \"codigoEnfermera\",  " +
				" umcamt.codigopersonal \"codigoMedicoCambioTurno\",  " +
				" umcamt.nombre \"medicoCambioTurno\",  " +
				" h.fecha_cierre \"fechaCierre\",  " +
				" h.fecha_cierre_cambio_turno \"fechaCierreCambioTurno\",  " +
				" h.num_hoja_consulta \"numHojaConsulta\",  " +
				" h.hospitalizado ,   " +
				" h.transfusion_sangre \"transfusionSangre\", " +
				" h.medicamento_distinto \"medicamentoDistinto\" ,   " +
				" h.linfocitosa_atipicos \"linfocitosaAtipicos\" ,   " +
				" h.fecha_linfocitos \"fechaLinfocitos\", " +
				" h.rahs_moteada \"rahsMoteada\", " +
				" h.serologia_chik \"serologiaChik\", " +
				" h.bilirrubinuria, " +
				" h.horasv, " +
				" h.hora, " +
				" mtc.motivo \"motivoCancelacion\", " +
				" h.no_atiende_llamado_enfermeria \"noAtiendeLlamadoEnfermeria\", " +
				" h.no_atiende_llamado_medico \"noAtiendeLlamadoMedico\", " +
				//------------------------------------------
				" (SELECT array_to_string( " + 
				"  ARRAY(select DISTINCT " +
				" CASE "+ 
				"		  when ec.desc_estudio = 'Dengue' then 'DEN' " + 
				"		  when ec.desc_estudio = 'Influenza' then 'FLU' " + 
				"		  when ec.desc_estudio = 'Cohorte BB' then 'BB' " + 
				"		  when ec.desc_estudio = 'Transmision' then 'TR' " + 
				"		  when ec.desc_estudio = 'Chikungunya' then 'CHIK' " + 
				"		  when ec.desc_estudio = 'Seroprevalencia Chik' then 'S-CHIK' " + 
				"		  when ec.desc_estudio = 'Transmision Chik' then 'TR-CHIK' " + 
				"		  when ec.desc_estudio = 'Indice Cluster Dengue' then 'IC-DEN' " + 
				"		  when ec.desc_estudio = 'Transmision Zika' then 'TR-ZIKA' " + 
				"		  when ec.desc_estudio = 'CH Familia' then 'CH-F' " + 
				"		  when ec.desc_estudio = 'Arbovirus Seroprev.' then 'A-SERO' " +
				"		  when ec.desc_estudio = 'Influenza UO1' then 'UO1' " +
				"		  when ec.desc_estudio = 'UO1' then 'UO1' " +
				" END " +
				" from cons_estudios c " + 
				" inner join hoja_consulta as hc on c.codigo_expediente = hc.cod_expediente " + 
				" inner join estudio_catalogo ec on c.codigo_consentimiento = ec.cod_estudio " + 
				" where hc.sec_hoja_consulta = :secHojaConsulta and c.retirado != '1' " +
				" group by ec.desc_estudio), ', ')) \"estudiosParticipantes\" " +
				//------------------------------------------
				" from hoja_consulta h " +
				" inner join paciente p on h.cod_expediente=p.cod_expediente  "+ 
				" left join escuela_catalogo ec on cast(h.colegio as integer)=ec.cod_escuela  "+
				" left join usuarios_view uvmed on h.usuario_medico=uvmed.id "+
				" left join usuarios_view uvenf on h.usuario_enfermeria=uvenf.id "+
				" left join usuarios_view umcamt on h.medico_cambio_turno=umcamt.id " +
				" left join motivo_cancelacion mtc on h.sec_hoja_consulta = mtc.num_hoja_consulta";
	    
	if(secHojaConsulta > 0)
	    	sql += " where  h.sec_hoja_consulta = :secHojaConsulta ";
	
	   Query query =  HIBERNATE_RESOURCE.getSession().createSQLQuery(sql)
		        .setResultTransformer(Transformers.aliasToBean(HojaConsultaReporte.class))
	     .setParameter("secHojaConsulta", secHojaConsulta);
	   
	   List result = query.list();
       
	   //Verificando si existen textos enormes
	   sql = "select a from HojaConsulta a where a.secHojaConsulta = :secHojaConsulta ";				

		query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
		query.setParameter("secHojaConsulta", secHojaConsulta);

		HojaConsulta hcon = (HojaConsulta) query.uniqueResult();		
		boolean datosAdicionales = false;
		 Map<String, Object> parametros = null;
		if(hcon != null) parametros = controlCambiosService.getControlCambios(hcon.getNumHojaConsulta(), hcon.getCodExpediente());
		
		if(hcon != null && ((hcon.getHistoriaExamenFisico() != null && hcon.getHistoriaExamenFisico().trim().length() > 2000) 
			|| (hcon.getPlanes() != null && hcon.getPlanes().trim().length() > 460)	)){
			return  UtilitarioReporte.mostrarReporte(nombreReporte, parametros, result,true, hcon, false);
		} else {	    
			return  UtilitarioReporte.mostrarReporte(nombreReporte, parametros, result,true, null, false);
		}
	
	} catch (Exception e) {
	   e.printStackTrace();
	   
	} finally {
	   if (HIBERNATE_RESOURCE.getSession().isOpen()) {
	   	HIBERNATE_RESOURCE.close();
	   }
	}
	return null;
	}

}
