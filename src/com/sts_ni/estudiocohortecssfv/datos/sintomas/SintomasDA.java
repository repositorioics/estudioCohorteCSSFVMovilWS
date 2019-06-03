package com.sts_ni.estudiocohortecssfv.datos.sintomas;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ni.com.sts.estudioCohorteCSSFV.modelo.EstudioCatalogo;
import ni.com.sts.estudioCohorteCSSFV.modelo.HojaConsulta;
import ni.com.sts.estudioCohorteCSSFV.modelo.HorarioAtencion;
import ni.com.sts.estudioCohorteCSSFV.modelo.OrdenLaboratorio;
import ni.com.sts.estudioCohorteCSSFV.modelo.VigilanciaIntegradaIragEti;

import org.hibernate.Query;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sts_ni.estudiocohortecssfv.datos.controlcambios.ControlCambiosDA;
import com.sts_ni.estudiocohortecssfv.servicios.SintomasService;
import com.sts_ni.estudiocohortecssfv.util.HibernateResource;
import com.sts_ni.estudiocohortecssfv.util.Mensajes;
import com.sts_ni.estudiocohortecssfv.util.UtilResultado;
import com.sts_ni.estudiocohortecssfv.util.UtilHojaConsulta;

/***
 * Clase que controla los procesos de datos de sintomas
 *
 */
public class SintomasDA implements SintomasService {

	private static final HibernateResource HIBERNATE_RESOURCE = new HibernateResource();
	private static final String QUERY_HOJA_CONSULTA_BY_ID = "select h from HojaConsulta h where h.secHojaConsulta = :id";
	
	/***
	 * Metodo para obtener el turno de un dia
	 * @param diaSemana, Dia de la semana
	 */
	@Override
	public String getHorarioTurno(Integer diaSemana) {
		String result = null;
		try {
            List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;             //Objeto para cada registro recuperado

            String sql = "select ha " +
                         "from HorarioAtencion ha " +
                         "where cast( ha.dia as integer) = :diaSemana";

            Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql).setParameter("diaSemana", diaSemana);

            List<HorarioAtencion> objLista = query.list();

            if (objLista != null && objLista.size() > 0) {

                for (HorarioAtencion horarioAtencion : objLista) {

                    // Construir la fila del registro actual
                    fila = new HashMap();
                    fila.put("secHorarioAtencion", horarioAtencion.getSecHorarioAtencion());
                    fila.put("turno", horarioAtencion.getTurno());
                    fila.put("dia", horarioAtencion.getDia());
                    fila.put("horaInicio", new SimpleDateFormat("KK:mm a").format(horarioAtencion.getHoraInicio()));
                    fila.put("horaFin", new SimpleDateFormat("KK:mm a").format(horarioAtencion.getHoraFin()));

                    oLista.add(fila);
                }

                // Construir la lista a una estructura JSON
                result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);
            }else{
            	result = UtilResultado.parserResultado(null, Mensajes.NO_DATOS, UtilResultado.INFO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo para obtener los datos ingresados de la seccion General
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getGeneralesSintomas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());

			fila = new HashMap();
            fila.put("presion", hojaConsulta.getPresion());
            fila.put("pas", hojaConsulta.getPas());
            fila.put("pad", hojaConsulta.getPad());
            fila.put("fciaResp", hojaConsulta.getFciaResp());
            fila.put("fciaCard", hojaConsulta.getFciaCard());
            fila.put("lugarAtencion", hojaConsulta.getLugarAtencion() != null ? hojaConsulta.getLugarAtencion().trim() : "");
            fila.put("consulta", hojaConsulta.getConsulta() != null ? hojaConsulta.getConsulta().trim() : "");
            fila.put("segChick", hojaConsulta.getSegChick());
            fila.put("turno", hojaConsulta.getTurno());
            fila.put("temMedc", ((hojaConsulta.getTemMedc() != null) ? hojaConsulta.getTemMedc().doubleValue() : null));
            fila.put("fis", (hojaConsulta.getFis() != null) ? new SimpleDateFormat("dd/MM/yyyy").format(hojaConsulta.getFis())
            		: null);
            fila.put("fif", (hojaConsulta.getFif() != null) ? new SimpleDateFormat("dd/MM/yyyy").format(hojaConsulta.getFif())
            		: null);
            fila.put("ultDiaFiebre", (hojaConsulta.getUltDiaFiebre()!= null) ? new SimpleDateFormat("dd/MM/yyyy")
            	.format(hojaConsulta.getUltDiaFiebre()) : null);
            fila.put("amPmUltDiaFiebre", hojaConsulta.getAmPmUltDiaFiebre());
            fila.put("ultDosisAntipiretico", (hojaConsulta.getUltDosisAntipiretico() != null) ? new SimpleDateFormat("dd/MM/yyyy")
            	.format(hojaConsulta.getUltDosisAntipiretico()) : null);
            fila.put("horaUltDosisAntipiretico", (hojaConsulta.getHoraUltDosisAntipiretico() != null) ? new SimpleDateFormat("HH:mm")
            	.format(hojaConsulta.getHoraUltDosisAntipiretico()) + " "  
            	+ hojaConsulta.getAmPmUltDosisAntipiretico().trim() : null);
            fila.put("amPmUltDosisAntipiretico", hojaConsulta.getAmPmUltDosisAntipiretico());
            
            //Agregando datos de la hoja para control de cambios
            fila.put("numHojaConsulta", hojaConsulta.getNumHojaConsulta());
            fila.put("codExpediente", hojaConsulta.getCodExpediente());

            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo para obtener los datos ingresados de la seccion Estado General
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getEstadoGeneralSintomas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
					
			fila = new HashMap();
			fila.put("fiebre", hojaConsulta.getFiebre());
            fila.put("astenia", hojaConsulta.getAstenia());
            fila.put("asomnoliento", hojaConsulta.getAsomnoliento());
            fila.put("malEstado", hojaConsulta.getMalEstado());
            fila.put("perdidaConsciencia", hojaConsulta.getPerdidaConsciencia());
            fila.put("inquieto", hojaConsulta.getInquieto());
            fila.put("convulsiones", hojaConsulta.getConvulsiones());
            fila.put("hipotermia", hojaConsulta.getHipotermia());
            fila.put("letargia", hojaConsulta.getLetargia());

            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo para obtener los datos ingresados de la seccion Gastroinstestinal
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getGastroinstestinalSintomas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
					
			fila = new HashMap();
			fila.put("pocoApetito", hojaConsulta.getPocoApetito());
            fila.put("nausea", hojaConsulta.getNausea());
            fila.put("dificultadAlimentarse", hojaConsulta.getDificultadAlimentarse());
            fila.put("vomitoHoras", hojaConsulta.getVomito12horas());
            fila.put("vomito12h", hojaConsulta.getVomito12h());
            fila.put("diarrea", hojaConsulta.getDiarrea());
            fila.put("diarreaSangre", hojaConsulta.getDiarreaSangre());
            fila.put("estrenimiento", hojaConsulta.getEstrenimiento());
            fila.put("dolorAbContinuo", hojaConsulta.getDolorAbContinuo());
            fila.put("dolorAbIntermitente", hojaConsulta.getDolorAbIntermitente());
            fila.put("epigastralgia", hojaConsulta.getEpigastralgia());
            fila.put("intoleranciaOral", hojaConsulta.getIntoleranciaOral());
            fila.put("distensionAbdominal", hojaConsulta.getDistensionAbdominal());
            fila.put("hepatomegalia", hojaConsulta.getHepatomegalia());
            fila.put("hepatomegaliaCm", hojaConsulta.getHepatomegaliaCm());

            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo para obtener los datos ingresados de la seccion Osteomuscular
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getOsteomuscularSintomas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
					
			fila = new HashMap();
			fila.put("altralgia", hojaConsulta.getAltralgia());
            fila.put("mialgia", hojaConsulta.getMialgia());
            fila.put("lumbalgia", hojaConsulta.getLumbalgia());
            fila.put("dolorCuello", hojaConsulta.getDolorCuello());
            fila.put("tenosinovitis", hojaConsulta.getTenosinovitis());
            fila.put("artralgiaDistal", hojaConsulta.getArtralgiaDistal());
            fila.put("artralgiaProximal", hojaConsulta.getArtralgiaProximal());
            fila.put("conjuntivitis", hojaConsulta.getConjuntivitis());
            fila.put("edemaMunecas", hojaConsulta.getEdemaMunecas());
            fila.put("edemaCodos", hojaConsulta.getEdemaCodos());
            fila.put("edemaHombros", hojaConsulta.getEdemaHombros());
            fila.put("edemaRodillas", hojaConsulta.getEdemaRodillas());
            fila.put("edemaTobillos", hojaConsulta.getEdemaTobillos());

            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo para obtener los datos ingresados de la seccion Cabeza
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getCabezaSintomas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
					
			fila = new HashMap();
			fila.put("cefalea", hojaConsulta.getCefalea());
            fila.put("rigidezCuello", hojaConsulta.getRigidezCuello());
            fila.put("inyeccionConjuntival", hojaConsulta.getInyeccionConjuntival());
            fila.put("hemorragiaSuconjuntival", hojaConsulta.getHemorragiaSuconjuntival());
            fila.put("dolorRetroocular", hojaConsulta.getDolorRetroocular());
            fila.put("fontanelaAbombada", hojaConsulta.getFontanelaAbombada());
            fila.put("ictericiaConuntival", hojaConsulta.getIctericiaConuntival());
            
            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo para obtener los datos ingresados de la seccion Deshidratacion
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getDeshidratacionSintomas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
					
			fila = new HashMap();
			fila.put("lenguaMucosasSecas", hojaConsulta.getLenguaMucosasSecas());
            fila.put("pliegueCutaneo", hojaConsulta.getPliegueCutaneo());
            fila.put("orinaReducida", hojaConsulta.getOrinaReducida());
            fila.put("bebeConSed", hojaConsulta.getBebeConSed());
            fila.put("ojosHundidos", (hojaConsulta.getOjosHundidos() != null
            		&& !hojaConsulta.getOjosHundidos().isEmpty()) ? 
            				hojaConsulta.getOjosHundidos().charAt(0) : null);
            fila.put("fontanelaHundida", hojaConsulta.getFontanelaHundida());
            
            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo para obtener los datos ingresados de la seccion Cutaneo
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getCutaneoSintomas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			fila = new HashMap();
			fila.put("rahsLocalizado", hojaConsulta.getRahsLocalizado());
            fila.put("rahsGeneralizado", hojaConsulta.getRahsGeneralizado());
            fila.put("rashEritematoso", hojaConsulta.getRashEritematoso());
            fila.put("rahsMacular", hojaConsulta.getRahsMacular());
            fila.put("rashPapular", hojaConsulta.getRashPapular());
            fila.put("rahsMoteada", hojaConsulta.getRahsMoteada());
            fila.put("ruborFacial", hojaConsulta.getRuborFacial());
            fila.put("equimosis", hojaConsulta.getEquimosis());
            fila.put("cianosisCentral", hojaConsulta.getCianosisCentral());
            fila.put("ictericia", hojaConsulta.getIctericia());
            
            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo para obtener los datos ingresados de la seccion Garganta
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getGargantaSintomas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
					
			fila = new HashMap();
			fila.put("eritema", hojaConsulta.getEritema());
            fila.put("dolorGarganta", hojaConsulta.getDolorGarganta());
            fila.put("adenopatiasCervicales", hojaConsulta.getAdenopatiasCervicales());
            fila.put("exudado", hojaConsulta.getExudado());
            fila.put("petequiasMucosa", hojaConsulta.getPetequiasMucosa());
            
            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo para obtener los datos ingresados de la seccion Renal
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getRenalSintomas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			fila = new HashMap();
			fila.put("sintomasUrinarios", hojaConsulta.getSintomasUrinarios());
            fila.put("leucocituria", hojaConsulta.getLeucocituria());
            fila.put("nitritos", hojaConsulta.getNitritos());
            fila.put("eritrocitos", hojaConsulta.getEritrocitos());
            fila.put("bilirrubinuria", hojaConsulta.getBilirrubinuria());
            
            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo para obtener los datos ingresados de la seccion Estado Nutricional
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getEstadoNutricionalSintomas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			fila = new HashMap();
			fila.put("imc", hojaConsulta.getImc());
			fila.put("lugarAtencion", hojaConsulta.getLugarAtencion());
			fila.put("obeso", hojaConsulta.getObeso());
            fila.put("sobrepeso", hojaConsulta.getSobrepeso());
            fila.put("sospechaProblema", hojaConsulta.getSospechaProblema());
            fila.put("normal", hojaConsulta.getNormal());
            fila.put("bajoPeso", hojaConsulta.getBajoPeso());
            fila.put("bajoPesoSevero", hojaConsulta.getBajoPesoSevero());
            
            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo para obtener los datos ingresados de la seccion Respiratorio
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getRespiratorioSintomas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			fila = new HashMap();
			fila.put("tos", hojaConsulta.getTos());
            fila.put("rinorrea", hojaConsulta.getRinorrea());
            fila.put("congestionNasal", hojaConsulta.getCongestionNasal());
            fila.put("otalgia", hojaConsulta.getOtalgia());
            fila.put("aleteoNasal", hojaConsulta.getAleteoNasal());
            fila.put("apnea", hojaConsulta.getApnea());
            fila.put("respiracionRapida", hojaConsulta.getRespiracionRapida());
            fila.put("quejidoEspiratorio", hojaConsulta.getQuejidoEspiratorio());
            fila.put("estiradorReposo", hojaConsulta.getEstiradorReposo());
            fila.put("tirajeSubcostal", hojaConsulta.getTirajeSubcostal());
            fila.put("sibilancias", hojaConsulta.getSibilancias());
            fila.put("crepitos", hojaConsulta.getCrepitos());
            fila.put("roncos", hojaConsulta.getRoncos());
            fila.put("otraFif", hojaConsulta.getOtraFif());
            fila.put("nuevaFif", (hojaConsulta.getNuevaFif() != null) ? new SimpleDateFormat("dd/MM/yyyy")
        	.format(hojaConsulta.getNuevaFif()) : null);
            
            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
		
	/***
	 * Metodo para obtener los datos ingresados de la seccion Referencia
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getReferenciaSintomas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			fila = new HashMap();
			fila.put("interconsultaPediatrica", hojaConsulta.getInterconsultaPediatrica());
            fila.put("referenciaHospital", hojaConsulta.getReferenciaHospital());
            fila.put("referenciaDengue", hojaConsulta.getReferenciaDengue());
            fila.put("referenciaIrag", hojaConsulta.getReferenciaIrag());
            fila.put("referenciaChik", hojaConsulta.getReferenciaChik());
            fila.put("eti", hojaConsulta.getEti());
            fila.put("irag", hojaConsulta.getIrag());
            fila.put("neumonia", hojaConsulta.getNeumonia());
            
            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo para obtener los datos ingresados de la seccion Vacuna
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getVacunasSintomas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			fila = new HashMap();
			fila.put("lactanciaMaterna", hojaConsulta.getLactanciaMaterna());
            fila.put("vacunasCompletas", hojaConsulta.getVacunasCompletas());
            fila.put("vacunaInfluenza", hojaConsulta.getVacunaInfluenza());
            fila.put("fechaVacuna", (hojaConsulta.getFechaVacuna() != null) ? new SimpleDateFormat("dd/MM/yyyy")
        	.format(hojaConsulta.getFechaVacuna()) : null);
            
            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo para obtener los datos ingresados de la seccion Categorias
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getCategoriasSintomas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			fila = new HashMap();
			fila.put("saturaciono", hojaConsulta.getSaturaciono2());
			//fila.put("imc", ((hojaConsulta.getImc() != null) ? hojaConsulta.getImc().doubleValue() : null));
            fila.put("categoria", hojaConsulta.getCategoria());
            fila.put("cambioCategoria", hojaConsulta.getCambioCategoria());
            fila.put("manifestacionHemorragica", hojaConsulta.getManifestacionHemorragica());
            fila.put("pruebaTorniquetePositiva", hojaConsulta.getPruebaTorniquetePositiva());
            fila.put("petequiaPt", hojaConsulta.getPetequia10Pt());
            fila.put("petequiasPt", hojaConsulta.getPetequia20Pt());
            fila.put("pielExtremidadesFrias", hojaConsulta.getPielExtremidadesFrias());
            fila.put("palidezEnExtremidades", hojaConsulta.getPalidezEnExtremidades());
            fila.put("epitaxis", hojaConsulta.getEpistaxis());
            fila.put("gingivorragia", hojaConsulta.getGingivorragia());
            fila.put("peteqiasEspontaneas", hojaConsulta.getPetequiasEspontaneas());
            fila.put("llenadoCapilarseg", hojaConsulta.getLlenadoCapilar2seg());
            fila.put("cianosis", hojaConsulta.getCianosis());
            fila.put("linfocitosAtipicos", (hojaConsulta.getLinfocitosaAtipicos() != null) 
            		? hojaConsulta.getLinfocitosaAtipicos().doubleValue() : null);
            fila.put("fecha", (hojaConsulta.getFechaLinfocitos() != null) ? new SimpleDateFormat("dd/MM/yyyy")
        	.format(hojaConsulta.getFechaLinfocitos()) : null);
            fila.put("hipermenorrea", hojaConsulta.getHipermenorrea());
            fila.put("hematemesis", hojaConsulta.getHematemesis());
            fila.put("melena", hojaConsulta.getMelena());
            fila.put("hemoconc", hojaConsulta.getHemoconc());
            fila.put("hemoconcentracion", hojaConsulta.getHemoconcentracion());
            fila.put("hospitalizado", hojaConsulta.getHospitalizado());
            fila.put("hospitalizadoEspecificar", hojaConsulta.getHospitalizadoEspecificar());
            fila.put("transfucionSangre", hojaConsulta.getTransfusionSangre());
            fila.put("transfuncionEspecificar", hojaConsulta.getTransfusionEspecificar());
            fila.put("tomandoMedicamento", hojaConsulta.getTomandoMedicamento());
            fila.put("tomandoMedicamentoEspecificar", hojaConsulta.getMedicamentoEspecificar());
            fila.put("medicamentoDistinto", hojaConsulta.getMedicamentoDistinto());
            fila.put("medicamentoDistintoEspecificar", hojaConsulta.getMedicamentoDistEspecificar());
            
            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo Para guardar los campos ingresados en Generales de Sintomas
	 * 
	 * @param paramHojaConsulta, JSON
	 * @return
	 */
	@Override
	public String guardarGeneralesSintomas(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			//Integer presion;
			Short pas;
			Short pad;
			Integer fciaResp;
			Integer fciaCard;
			String lugarAtencion;
			String consulta;
			String segChick;
			String turno;
			Double temMedc;
			String fis;
			String fif;
			String ultDiaFiebre;
			String ultDosisAntipiretico;
			String amPmUltDiaFiebre;
			String horaUltDosisAntipiretico;
			String amPmUltDosisAntipiretico;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			//presion = ((Number) hojaConsultaJSON.get("presion")).intValue();
			pas = ((Number) hojaConsultaJSON.get("pas")).shortValue();
			pad = ((Number) hojaConsultaJSON.get("pad")).shortValue();
			
			fciaResp = ((Number) hojaConsultaJSON.get("fciaResp")).intValue();
			fciaCard = ((Number) hojaConsultaJSON.get("fciaCard")).intValue();
			lugarAtencion = ((hojaConsultaJSON.get("lugarAtencion").toString()));
			consulta = ((hojaConsultaJSON.get("consulta").toString()));
			segChick = (hojaConsultaJSON.containsKey("segChick")) ? 
					hojaConsultaJSON.get("segChick").toString() : null;
			turno = ((hojaConsultaJSON.get("turno").toString()));
			temMedc = (((Number) hojaConsultaJSON.get("temMedc")).doubleValue());
			fis = (hojaConsultaJSON.containsKey("fis")) ?
					hojaConsultaJSON.get("fis").toString() : null;
			fif = (hojaConsultaJSON.containsKey("fif")) ? 
					hojaConsultaJSON.get("fif").toString() : null;
			ultDiaFiebre = (hojaConsultaJSON.containsKey("ultDiaFiebre")) ? 
					hojaConsultaJSON.get("ultDiaFiebre").toString() : null;
			ultDosisAntipiretico = (hojaConsultaJSON.containsKey("ultDosisAntipiretico")) ? 
					hojaConsultaJSON.get("ultDosisAntipiretico").toString() : null;
			
			//Se valida null
			amPmUltDiaFiebre = hojaConsultaJSON.containsKey("amPmUltDiaFiebre") ? 
					hojaConsultaJSON.get("amPmUltDiaFiebre").toString() : null;			
			
			horaUltDosisAntipiretico = hojaConsultaJSON.containsKey("horaUltDosisAntipiretico") ? 
					hojaConsultaJSON.get("horaUltDosisAntipiretico").toString() : null;
			
			amPmUltDosisAntipiretico = hojaConsultaJSON.containsKey("amPmUltDosisAntipiretico") ? 
					hojaConsultaJSON.get("amPmUltDosisAntipiretico").toString() : null;
			
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(UtilHojaConsulta.generalesCompletada(hojaConsulta)) {
				HojaConsulta hcNueva = new HojaConsulta();
				
				hcNueva.setPas(pas.shortValue());
				hcNueva.setPad(pad.shortValue());
				hcNueva.setFciaCard(fciaCard.shortValue());
				hcNueva.setFciaResp(fciaResp.shortValue());
				hcNueva.setLugarAtencion(lugarAtencion);
				hcNueva.setConsulta(consulta);
				if(segChick != null && !segChick.isEmpty()) {
					hcNueva.setSegChick(segChick.charAt(0));
				}
				
				hcNueva.setTurno(turno.charAt(0));
				hcNueva.setTemMedc(BigDecimal.valueOf(temMedc));			
				if(fis != null && !fis.isEmpty()) {
					hcNueva.setFis( fis.trim().length() > 0 ? new SimpleDateFormat("dd/MM/yyyy").parse(fis) : null);
				}
				if(fif != null && !fif.isEmpty()) {						
					hcNueva.setFif( fif.trim().length() > 0 ? new SimpleDateFormat("dd/MM/yyyy").parse(fif) : null);
				}
				
				if(ultDiaFiebre != null) {
					hcNueva.setUltDiaFiebre( ultDiaFiebre.trim().length() > 0 ? new SimpleDateFormat("dd/MM/yyyy").parse(ultDiaFiebre) : null);
				}
				
				if(ultDosisAntipiretico != null) {
					hcNueva.setUltDosisAntipiretico( ultDosisAntipiretico.trim().length() > 0 ? new SimpleDateFormat("dd/MM/yyyy").parse(ultDosisAntipiretico) : null);
				}
				
				if(amPmUltDiaFiebre != null) {
					hcNueva.setAmPmUltDiaFiebre( amPmUltDiaFiebre.trim().length() > 0 ? amPmUltDiaFiebre : null);
				}
				
				if(horaUltDosisAntipiretico != null) {
					hcNueva.setHoraUltDosisAntipiretico( horaUltDosisAntipiretico.trim().length() > 0 ? new SimpleDateFormat("hh:mm").parse(horaUltDosisAntipiretico) : null);
				}
				
				if(amPmUltDosisAntipiretico != null) {
					hcNueva.setAmPmUltDosisAntipiretico( amPmUltDosisAntipiretico.trim().length() > 0 ? amPmUltDosisAntipiretico : null);
				}
				
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				ctrlCambiosDA.guardarCtrlGenerales(hojaConsulta, hcNueva, usuarioLogiado);
			}

			// hojaConsulta.setSecHojaConsulta(secHojaConsulta);
			//hojaConsulta.setPresion(presion);
			hojaConsulta.setPas(pas.shortValue());
			hojaConsulta.setPad(pad.shortValue());
			hojaConsulta.setFciaCard(fciaCard.shortValue());
			hojaConsulta.setFciaResp(fciaResp.shortValue());
			hojaConsulta.setLugarAtencion(lugarAtencion);
			hojaConsulta.setConsulta(consulta);
			
			if(segChick != null && !segChick.isEmpty()) {
				hojaConsulta.setSegChick(segChick.charAt(0));
			}else{
				hojaConsulta.setSegChick(null);
			}
			
			hojaConsulta.setTurno(turno.charAt(0));
			hojaConsulta.setTemMedc(BigDecimal.valueOf(temMedc));			
			if(fis != null && !fis.isEmpty()) {
				hojaConsulta.setFis( fis.trim().length() > 0 ? new SimpleDateFormat("dd/MM/yyyy").parse(fis) : null);
			}
			if(fif != null && !fif.isEmpty()) {						
				hojaConsulta.setFif( fif.trim().length() > 0 ? new SimpleDateFormat("dd/MM/yyyy").parse(fif) : null);
			}
			
			if(ultDiaFiebre != null) {
				hojaConsulta.setUltDiaFiebre( ultDiaFiebre.trim().length() > 0 ? new SimpleDateFormat("dd/MM/yyyy").parse(ultDiaFiebre) : null);
			}
			
			if(ultDosisAntipiretico != null) {
				hojaConsulta.setUltDosisAntipiretico( ultDosisAntipiretico.trim().length() > 0 ? new SimpleDateFormat("dd/MM/yyyy").parse(ultDosisAntipiretico) : null);
			}
			
			if(amPmUltDiaFiebre != null) {
				hojaConsulta.setAmPmUltDiaFiebre( amPmUltDiaFiebre.trim().length() > 0 ? amPmUltDiaFiebre : null);
			}
			
			if(horaUltDosisAntipiretico != null) {
				hojaConsulta.setHoraUltDosisAntipiretico( horaUltDosisAntipiretico.trim().length() > 0 ? new SimpleDateFormat("hh:mm").parse(horaUltDosisAntipiretico) : null);
			}
			
			if(amPmUltDosisAntipiretico != null) {
				hojaConsulta.setAmPmUltDosisAntipiretico( amPmUltDosisAntipiretico.trim().length() > 0 ? amPmUltDosisAntipiretico : null);
			}

			HIBERNATE_RESOURCE.begin();
			HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);
			HIBERNATE_RESOURCE.commit();
			result = UtilResultado.parserResultado(null, "", UtilResultado.OK);

		} catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null,
					Mensajes.ERROR_NO_CONTROLADO + e.getMessage(),
					UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();
			// TODO: handle exception
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;

	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public String validacionMatrizSintoma(String paramHojaConsulta) {
		StringBuffer result = new StringBuffer();
		try {
			Integer secHojaConsulta;
			Integer codExpediente;
			boolean crearFicha = false;
			
			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());

			if (hojaConsulta.getFiebre() != null && 
					hojaConsulta.getFiebre().compareTo('0') == 0) {
				if (hojaConsulta.getFif() != null) {
					// Criterios Caso A
					if (UtilHojaConsulta.validarCasoA(hojaConsulta)) {
						result.append(Mensajes.MATRIZ_CASO_A).append("\n");
					}

					// Criterios Caso B
					if (UtilHojaConsulta.validarCasoB(hojaConsulta)) {
						result.append(Mensajes.MATRIZ_CASO_B).append("\n");
					}

					Calendar fechaConsulta = new GregorianCalendar();
					fechaConsulta.setTime(hojaConsulta.getFechaConsulta());
					Calendar fechaSintoma = new GregorianCalendar();
					fechaSintoma.setTime(hojaConsulta.getFis());
					fechaSintoma.add(Calendar.DAY_OF_MONTH, 1);
					Calendar fechaInicioFiebre = new GregorianCalendar();
					fechaInicioFiebre.setTime(hojaConsulta.getFif());
					Calendar hoy = Calendar.getInstance();
					int dayEti = -1;
					int dayFiebre = -1;
					
					long difSintoma = fechaConsulta.getTimeInMillis() - fechaSintoma.getTimeInMillis();
					dayEti = (int) (difSintoma/(1000*24*60*60));
					
				    long difFiebre = hoy.getTimeInMillis() - fechaInicioFiebre.getTimeInMillis();
				    dayFiebre = (int) (difFiebre/(1000*24*60*60));

					if (dayEti <= 4) {
						if (UtilHojaConsulta.validarCasoETI(hojaConsulta)) {
							result.append(Mensajes.MATRIZ_CASO_ETI).append("\n");
						}
					}
					
					if(dayFiebre <= 4) {
						if (UtilHojaConsulta.validarCasoIRAG(hojaConsulta)) {
							result.append(Mensajes.MATRIZ_CASO_IRAG).append("\n");
						}
					}

					if (UtilHojaConsulta.validarCasoNeumonia(hojaConsulta)) {
						result.append(Mensajes.MATRIZ_CASO_NEUMO).append("\n");
					} 

					if (dayFiebre >= 5 || dayFiebre == -1) {
						if (UtilHojaConsulta.validarCasoIRAG(hojaConsulta)) {
							result.append(Mensajes.MATRIZ_CASO_IRAG_FIEBRE).append(
									"\n");
						}
					}
					// Verificamos si ETI o Neumonia es verdadero en la hoja de consulta y si existe fecha inicio sintomas o fecha inicio fiebre
					if ((hojaConsulta.getEti() !=  null && hojaConsulta.getEti().compareTo('0') == 0 
							|| hojaConsulta.getNeumonia() !=  null && hojaConsulta.getNeumonia().compareTo('0') == 0) 
							&& (hojaConsulta.getFis() != null || hojaConsulta.getFif() != null)) {
						//Crear la Ficha para ETI
						String sql = "Select vi "
								+ " from VigilanciaIntegradaIragEti vi "
								+ " where vi.secHojaConsulta = :secHojaConsulta";
										
						query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
						query.setParameter("secHojaConsulta", secHojaConsulta);
												
						VigilanciaIntegradaIragEti vigilanciaIntegrada = ((VigilanciaIntegradaIragEti) query.uniqueResult());
						
						// obteniendo los estudios a los que pertenece el participante
						codExpediente = hojaConsulta.getCodExpediente();
						sql = "select ec " +
								" from ConsEstudios c, EstudioCatalogo ec " +
								" where c.codigoConsentimiento = ec.codEstudio"  +
								" and c.codigoExpediente = :codExpediente " +
								" and c.retirado != '1' " +
								" group by ec.codEstudio, ec.descEstudio";
						
						query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

						query.setParameter("codExpediente", codExpediente);

						List<EstudioCatalogo> lstConsEstudios = (List<EstudioCatalogo>) query
								.list();
						
						for (EstudioCatalogo estudioCatalogo : lstConsEstudios) {
							if (estudioCatalogo.getDescEstudio().equals("Influenza") 
									|| estudioCatalogo.getDescEstudio().equals("CH Familia")) {
								crearFicha = true;
							}
						}
						
						// Se crea la ficha si el participante pertenece a los estudios de Influeza y CH Familia
						if (vigilanciaIntegrada == null && crearFicha) {
							VigilanciaIntegradaIragEti vigilanciaIntegradaResult = new VigilanciaIntegradaIragEti();
							vigilanciaIntegradaResult.setSecHojaConsulta(secHojaConsulta);
							vigilanciaIntegradaResult.setCodExpediente(hojaConsulta.getCodExpediente());
							vigilanciaIntegradaResult.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
							vigilanciaIntegradaResult.setEti('0'); //TRUE
							vigilanciaIntegradaResult.setIrag('1'); //FALSE
							vigilanciaIntegradaResult.setIragInusitada('1'); //FALSE
							vigilanciaIntegradaResult.setFechaCreacion(new Date());
							vigilanciaIntegradaResult.setUsuarioMedico(hojaConsulta.getUsuarioMedico());
							crearFicha = false;
							HIBERNATE_RESOURCE.begin();
							HIBERNATE_RESOURCE.getSession().saveOrUpdate(vigilanciaIntegradaResult);
							HIBERNATE_RESOURCE.commit();
							
							result.append(Mensajes.FICHA_CASO_ETI).append(
									"\n");
						}
					}
					
					// Verificamos si IRAG en la hoja de consulta es verdadero y si existe fecha inicio sintomas o fecha inicio fiebre
					if (hojaConsulta.getIrag() != null && hojaConsulta.getIrag().compareTo('0') == 0
							&& (hojaConsulta.getFis() != null || hojaConsulta.getFif() != null)) {
						//Crear la Ficha para ETI
						String sql = "Select vi "
								+ " from VigilanciaIntegradaIragEti vi "
								+ " where vi.secHojaConsulta = :secHojaConsulta";
						
						query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
						query.setParameter("secHojaConsulta", secHojaConsulta);
						
						VigilanciaIntegradaIragEti vigilanciaIntegrada = ((VigilanciaIntegradaIragEti) query.uniqueResult());
						
						// obteniendo los estudios a los que pertenece el participante
						codExpediente = hojaConsulta.getCodExpediente();
						sql = "select ec " +
								" from ConsEstudios c, EstudioCatalogo ec " +
								" where c.codigoConsentimiento = ec.codEstudio"  +
								" and c.codigoExpediente = :codExpediente " +
								" and c.retirado != '1' " +
								" group by ec.codEstudio, ec.descEstudio";
						
						query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

						query.setParameter("codExpediente", codExpediente);

						List<EstudioCatalogo> lstConsEstudios = (List<EstudioCatalogo>) query
								.list();
						
						for (EstudioCatalogo estudioCatalogo : lstConsEstudios) {
							if (estudioCatalogo.getDescEstudio().equals("Influenza") 
									|| estudioCatalogo.getDescEstudio().equals("CH Familia")) {
								crearFicha = true;
							}
						}
						
						// Se crea la ficha si el participante pertenece a los estudios de Influeza y CH Familia
						if (vigilanciaIntegrada == null && crearFicha) {
							VigilanciaIntegradaIragEti vigilanciaIntegradaResult = new VigilanciaIntegradaIragEti();
							vigilanciaIntegradaResult.setSecHojaConsulta(secHojaConsulta);
							vigilanciaIntegradaResult.setCodExpediente(hojaConsulta.getCodExpediente());
							vigilanciaIntegradaResult.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
							vigilanciaIntegradaResult.setEti('1'); //FALSE
							vigilanciaIntegradaResult.setIrag('0'); //TRUE
							vigilanciaIntegradaResult.setIragInusitada('1'); //FALSE
							vigilanciaIntegradaResult.setFechaCreacion(new Date());
							vigilanciaIntegradaResult.setUsuarioMedico(hojaConsulta.getUsuarioMedico());
							crearFicha = false;
							HIBERNATE_RESOURCE.begin();
							HIBERNATE_RESOURCE.getSession().saveOrUpdate(vigilanciaIntegradaResult);
							HIBERNATE_RESOURCE.commit();
							
							result.append(Mensajes.FICHA_CASO_IRAG).append(
									"\n");
						}
					}
				} else {
					result.append(Mensajes.ERROR_MARCO_FIEBRE_SIN_FIF).append(
							"\n");
				}

			} else {

				if (UtilHojaConsulta.validarCasoNeumonia(hojaConsulta)) {
					result.append(Mensajes.MATRIZ_CASO_NEUMO).append("\n");
				}

				if (UtilHojaConsulta.validarCasoIRAG(hojaConsulta)) {
					result.append(Mensajes.MATRIZ_CASO_IRAG_SIN_FIEBRE).append(
							"\n");
				}
				
				// Verificamos si ETI o Neumonia es verdadero en la hoja de consulta y si existe fecha inicio sintomas o fecha inicio fiebre
				if ((hojaConsulta.getEti() !=  null && hojaConsulta.getEti().compareTo('0') == 0 
						|| hojaConsulta.getNeumonia() !=  null && hojaConsulta.getNeumonia().compareTo('0') == 0) 
						&& (hojaConsulta.getFis() != null || hojaConsulta.getFif() != null)) {
					//Crear la Ficha para ETI
					String sql = "Select vi "
							+ " from VigilanciaIntegradaIragEti vi "
							+ " where vi.secHojaConsulta = :secHojaConsulta";
					
					query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
					query.setParameter("secHojaConsulta", secHojaConsulta);
											
					VigilanciaIntegradaIragEti vigilanciaIntegrada = ((VigilanciaIntegradaIragEti) query.uniqueResult());
					
					// obteniendo los estudios a los que pertenece el participante
					codExpediente = hojaConsulta.getCodExpediente();
					sql = "select ec " +
							" from ConsEstudios c, EstudioCatalogo ec " +
							" where c.codigoConsentimiento = ec.codEstudio"  +
							" and c.codigoExpediente = :codExpediente " +
							" and c.retirado != '1' " +
							" group by ec.codEstudio, ec.descEstudio";
					
					query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

					query.setParameter("codExpediente", codExpediente);

					List<EstudioCatalogo> lstConsEstudios = (List<EstudioCatalogo>) query
							.list();
					
					for (EstudioCatalogo estudioCatalogo : lstConsEstudios) {
						if (estudioCatalogo.getDescEstudio().equals("Influenza") 
								|| estudioCatalogo.getDescEstudio().equals("CH Familia")) {
							crearFicha = true;
						}
					}
					
					// Se crea la ficha si el participante pertenece a los estudios de Influeza y CH Familia
					if (vigilanciaIntegrada == null && crearFicha) {
						VigilanciaIntegradaIragEti vigilanciaIntegradaResult = new VigilanciaIntegradaIragEti();
						vigilanciaIntegradaResult.setSecHojaConsulta(secHojaConsulta);
						vigilanciaIntegradaResult.setCodExpediente(hojaConsulta.getCodExpediente());
						vigilanciaIntegradaResult.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
						vigilanciaIntegradaResult.setEti('0'); //TRUE
						vigilanciaIntegradaResult.setIrag('1'); //FALSE
						vigilanciaIntegradaResult.setIragInusitada('1'); //FALSE
						vigilanciaIntegradaResult.setFechaCreacion(new Date());
						vigilanciaIntegradaResult.setUsuarioMedico(hojaConsulta.getUsuarioMedico());
						crearFicha = false;
						HIBERNATE_RESOURCE.begin();
						HIBERNATE_RESOURCE.getSession().saveOrUpdate(vigilanciaIntegradaResult);
						HIBERNATE_RESOURCE.commit();
						
						result.append(Mensajes.FICHA_CASO_ETI).append(
								"\n");
					}
				}
				
				// Verificamos si IRAG en la hoja de consulta es verdadero y si existe fecha inicio sintomas o fecha inicio fiebre
					if (hojaConsulta.getIrag() != null && hojaConsulta.getIrag().compareTo('0') == 0 
							&& (hojaConsulta.getFis() != null || hojaConsulta.getFif() != null)) {
					//Crear la Ficha para ETI
					String sql = "Select vi "
							+ " from VigilanciaIntegradaIragEti vi "
							+ " where vi.secHojaConsulta = :secHojaConsulta";
					
					query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
					query.setParameter("secHojaConsulta", secHojaConsulta);
					
					VigilanciaIntegradaIragEti vigilanciaIntegrada = ((VigilanciaIntegradaIragEti) query.uniqueResult());
					
					// obteniendo los estudios a los que pertenece el participante
					codExpediente = hojaConsulta.getCodExpediente();
					sql = "select ec " +
							" from ConsEstudios c, EstudioCatalogo ec " +
							" where c.codigoConsentimiento = ec.codEstudio"  +
							" and c.codigoExpediente = :codExpediente " +
							" and c.retirado != '1' " +
							" group by ec.codEstudio, ec.descEstudio";
					
					query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

					query.setParameter("codExpediente", codExpediente);

					List<EstudioCatalogo> lstConsEstudios = (List<EstudioCatalogo>) query
							.list();
					
					for (EstudioCatalogo estudioCatalogo : lstConsEstudios) {
						if (estudioCatalogo.getDescEstudio().equals("Influenza") 
								|| estudioCatalogo.getDescEstudio().equals("CH Familia")) {
							crearFicha = true;
						}
					}
					
					// Se crea la ficha si el participante pertenece a los estudios de Influeza y CH Familia
					if (vigilanciaIntegrada == null && crearFicha) {
						VigilanciaIntegradaIragEti vigilanciaIntegradaResult = new VigilanciaIntegradaIragEti();
						vigilanciaIntegradaResult.setSecHojaConsulta(secHojaConsulta);
						vigilanciaIntegradaResult.setCodExpediente(hojaConsulta.getCodExpediente());
						vigilanciaIntegradaResult.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
						vigilanciaIntegradaResult.setEti('1'); //FALSE
						vigilanciaIntegradaResult.setIrag('0'); //TRUE
						vigilanciaIntegradaResult.setIragInusitada('1'); //FALSE
						vigilanciaIntegradaResult.setFechaCreacion(new Date());
						vigilanciaIntegradaResult.setUsuarioMedico(hojaConsulta.getUsuarioMedico());
						crearFicha = false;
						HIBERNATE_RESOURCE.begin();
						HIBERNATE_RESOURCE.getSession().saveOrUpdate(vigilanciaIntegradaResult);
						HIBERNATE_RESOURCE.commit();
						
						result.append(Mensajes.FICHA_CASO_IRAG).append(
								"\n");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return (result != null && !result.toString().isEmpty()) ? result.toString() : "";
	}
	
	/***
	 *  Metodo para guardar datos ingresados en datos preclinicos
	 *  @param paramHojaConsulta, JSON
	 */
	@Override
	public String editarDatosPreclinicos(String paramHojaConsulta) {
		String result = null;
		try {
			
			int secHojaConsulta;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			HojaConsulta hcNueva = new HojaConsulta();
			hcNueva.setPesoKg(BigDecimal.valueOf(((Number) hojaConsultaJSON.get("pesoKg")).doubleValue()));
			hcNueva.setTallaCm(BigDecimal.valueOf((((Number) hojaConsultaJSON.get("tallaCm"))
					.doubleValue())));
			hcNueva.setTemperaturac(BigDecimal.valueOf(((Number) hojaConsultaJSON.get("temperaturac"))
					.doubleValue()));
			hcNueva.setExpedienteFisico(hojaConsultaJSON.get("expedienteFisico")
					.toString());
			
			ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
			ctrlCambiosDA.guardarCtrlPreclinicos(hojaConsulta, hcNueva, hojaConsultaJSON.get("usuarioLogiado").toString());

			hojaConsulta.setPesoKg(BigDecimal.valueOf(((Number) hojaConsultaJSON.get("pesoKg")).doubleValue()));
			hojaConsulta.setTallaCm(BigDecimal.valueOf((((Number) hojaConsultaJSON.get("tallaCm"))
					.doubleValue())));
			hojaConsulta.setTemperaturac(BigDecimal.valueOf(((Number) hojaConsultaJSON.get("temperaturac"))
					.doubleValue()));
			hojaConsulta.setExpedienteFisico(hojaConsultaJSON.get("expedienteFisico")
					.toString());

			HIBERNATE_RESOURCE.begin();
			HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);
			HIBERNATE_RESOURCE.commit();
			result = UtilResultado.parserResultado(null, "", UtilResultado.OK);

		} catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null,
					Mensajes.ERROR_NO_CONTROLADO + e.getMessage(),
					UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();
			// TODO: handle exception
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}
	
	/***
	 * Metodo para obtener las secciones con informacion ya ingresada
	 * @param paramHojaConsulta, JSON
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getSeccionesSintomasCompletadas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;  

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			fila = new HashMap();
			fila.put("esGeneralesCompletada", UtilHojaConsulta.generalesCompletada(hojaConsulta));
            fila.put("esEstadoGeneralesCompletada", UtilHojaConsulta.estadoGeneralCompletada(hojaConsulta));
            fila.put("esGastroinstetinalCompletada", UtilHojaConsulta.gastrointestinalCompletada(hojaConsulta));
            fila.put("esOsteomuscularCompletada", UtilHojaConsulta.osteomuscularCompletada(hojaConsulta));
            fila.put("esCabezaCompletada", UtilHojaConsulta.cabezaCompletada(hojaConsulta));
            fila.put("esDeshidratacionCompletada", UtilHojaConsulta.deshidratacionCompletada(hojaConsulta));
            fila.put("esCutaneoCompletada", UtilHojaConsulta.cutaneoCompletada(hojaConsulta));
            fila.put("esGargantaCompletada", UtilHojaConsulta.gargantaCompletada(hojaConsulta));
            fila.put("esRenalCompletada", UtilHojaConsulta.renalCompletada(hojaConsulta));
            fila.put("esEstadoNutricionalCompletada", UtilHojaConsulta.estadoNutricionalCompletada(hojaConsulta));
            fila.put("esRespiratorioCompletada", UtilHojaConsulta.respiratorioCompletada(hojaConsulta));
            fila.put("esReferenciaCompletada", UtilHojaConsulta.referenciaCompletada(hojaConsulta));
            fila.put("esVacunaCompletada", UtilHojaConsulta.vacunaCompletada(hojaConsulta));
            fila.put("esCategoriaCompletada", UtilHojaConsulta.categoriaCompletada(hojaConsulta));
            
            oLista.add(fila);
            
            result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);

		} catch (Exception e) {
            e.printStackTrace();
            result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
        } finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
}
