package com.sts_ni.estudiocohortecssfv.datos.inicio;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ni.com.sts.estudioCohorteCSSFV.modelo.ConsEstudios;
import ni.com.sts.estudioCohorteCSSFV.modelo.ControlCambios;
import ni.com.sts.estudioCohorteCSSFV.modelo.EghResultados;
import ni.com.sts.estudioCohorteCSSFV.modelo.EgoResultados;
import ni.com.sts.estudioCohorteCSSFV.modelo.EstudioCatalogo;
import ni.com.sts.estudioCohorteCSSFV.modelo.HojaConsulta;
import ni.com.sts.estudioCohorteCSSFV.modelo.InfluenzaMuestra;
import ni.com.sts.estudioCohorteCSSFV.modelo.MalariaResultados;
import ni.com.sts.estudioCohorteCSSFV.modelo.MotivoCancelacion;
import ni.com.sts.estudioCohorteCSSFV.modelo.OrdenLaboratorio;
import ni.com.sts.estudioCohorteCSSFV.modelo.PerifericoResultado;
import ni.com.sts.estudioCohorteCSSFV.modelo.SerologiaChikMuestra;
import ni.com.sts.estudioCohorteCSSFV.modelo.SerologiaDengueMuestra;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import com.sts_ni.estudiocohortecssfv.datos.controlcambios.ControlCambiosDA;
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
import com.sts_ni.estudiocohortecssfv.servicios.HojaConsultaService;
import com.sts_ni.estudiocohortecssfv.util.HibernateResource;
import com.sts_ni.estudiocohortecssfv.util.Mensajes;
import com.sts_ni.estudiocohortecssfv.util.UtilHojaConsulta;
import com.sts_ni.estudiocohortecssfv.util.UtilResultado;


public class HojaConsultaDA implements HojaConsultaService {

	private static String QUERY_HOJA_CONSULTA_BY_ID = "select h from HojaConsulta h where h.secHojaConsulta = :id";
	private static String QUERY_PACIENTE_BY_ID = "select p from Paciente p where p.secPaciente = :id";

	private static final HibernateResource HIBERNATE_RESOURCE = new HibernateResource();

	@Override
	public String getHojaConsultaTest(Integer secHojaConsulta) {
		String result = null;
		try {
			List oLista = new LinkedList(); // Listado final para el resultado
			Map fila = null; // Objeto para cada registro recuperado

			String sql = "select a " + "from HojaConsulta a ";

			if (secHojaConsulta > 0)
				sql += "where a.secHojaConsulta = :secHojaConsulta ";

			sql += "order by a.ordenLlegada ";

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			if (secHojaConsulta > 0)
				query.setParameter("secHojaConsulta", secHojaConsulta);

			List<HojaConsulta> objLista = query.list();

			if (objLista != null && objLista.size() > 0) {

				for (HojaConsulta hojaConsulta : objLista) {

					// Construir la fila del registro actual
					fila = new HashMap();
					fila.put("secHojaConsulta",
							hojaConsulta.getSecHojaConsulta());
					fila.put("codExpediente", hojaConsulta.getCodExpediente());
					fila.put("numHojaConsulta",
							hojaConsulta.getNumHojaConsulta());
					fila.put("estado", hojaConsulta.getEstado());

					oLista.add(fila);
				}

				// Construir la lista a una estructura JSON
				result = UtilResultado.parserResultado(oLista, "",
						UtilResultado.OK);
			} else {
				result = UtilResultado.parserResultado(null, Mensajes.NO_DATOS,
						UtilResultado.INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null,
					Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}

	/***
	 * Metodo para mostrar la lista de los pacientes que pertenecen al estado
	 * enfermeria
	 */
	@Override
	public String getListaInicioEnfermeria() {
		String result = null;
		try {
			List oLista = new LinkedList();
			Map fila = null;
			String sql = "select distinct h.sec_Hoja_Consulta, h.cod_Expediente, " + 
					" h.num_Hoja_Consulta, " + 
					" p.nombre1, p.nombre2, " + 
					" p.apellido1, p.apellido2, " + 
					" e.descripcion, e.estado, " + 
					" p.sexo, to_char(p.fecha_Nac, 'yyyyMMdd') \"fnac\", " + 
					" to_char(h.fecha_Consulta, 'yyyyMMdd HH:MI:SS am') \"fcon\", " + 
					" h.num_Hoja_Consulta \"nhc\", h.orden_Llegada, e.codigo, " +
					" (select um.nombre from Usuarios_View um where coalesce(h.usuario_Medico, -1) = um.id), " +
					" h.usuario_Enfermeria, " +
					" h.horasv, " +
					" e.orden " +
					" from hoja_consulta h " +
					" inner join paciente p on p.cod_Expediente = h.cod_Expediente " +
					" inner join estados_hoja e on e.codigo = h.estado " + 
					" where e.codigo not in ('7','8') ";

			sql += "order by e.orden asc, h.orden_Llegada asc";

			Query query = HIBERNATE_RESOURCE.getSession().createSQLQuery(sql);

			List<Object[]> objLista = new ArrayList<Object[]>();
			
			if(query.list() != null)
				objLista.addAll((List<Object[]>) query.list());
			

			if (objLista != null && objLista.size() > 0) {

				for (Object[] object : objLista) {

					// Construir la fila del registro actual usando arreglos

					fila = new HashMap();
					fila.put("secHojaConsulta", object[0]);
					fila.put("codExpediente", object[1]);
					fila.put("numHojaConsulta", object[2]);
					fila.put("nombrePaciente", object[3].toString() + " "
							+ ((object[4] != null) ? object[4].toString() : "") + " "
							+ object[5].toString() + " "
							+ ((object[6] != null) ? object[6].toString() : ""));
					fila.put("descripcion", object[7]);
					fila.put("estado", object[8]);
					fila.put("sexo", object[9]);
					fila.put("fechaNac", object[10]);
					fila.put("fechaConsulta", object[11]);
					fila.put("numHojaConsulta", object[12]);
					fila.put("ordenLlegada", object[13]);
					fila.put("codigoEstado", object[14]);
					fila.put("nombreMedico", (object[15] != null) ? object[15].toString() : null);
					fila.put("usuarioEnfermeria", object[16]);
					fila.put("horasv", (object[17] != null) ? object[17] : null);

					oLista.add(fila);
				}
				
				/***
				 * 		
				 */

				// Construir la lista a una estructura JSON
				result = UtilResultado.parserResultado(oLista, "",
						UtilResultado.OK);
			} else {
				result = UtilResultado.parserResultado(null, Mensajes.NO_PACIENTES_ENFERMERIA,
						UtilResultado.INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null,
					Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}

	/***
	 * Metod para guardar datos ingresados en datos preclinicos
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String enviarDatosPreclinicos(String paramHojaConsulta) {
		String result = null;
		try {
			int secHojaConsulta;
			Double pesoKg;
			Double tallaCm;
			Double temperaturac;
			String expedienteFisico;
			Short usuarioEnfermeria;
			Short usuarioMedico = null;
			String horasv;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			pesoKg = (((Number) hojaConsultaJSON.get("pesoKg")).doubleValue());
			tallaCm = ((((Number) hojaConsultaJSON.get("tallaCm"))
					.doubleValue()));
			temperaturac = (((Number) hojaConsultaJSON.get("temperaturac"))
					.doubleValue());
			expedienteFisico = ((hojaConsultaJSON.get("expedienteFisico")
					.toString()));
			usuarioEnfermeria = (((Number) hojaConsultaJSON
					.get("usuarioEnfermeria")).shortValue());
			
			if(hojaConsultaJSON.containsKey("usuarioMedico")) {
				usuarioMedico = (((Number) hojaConsultaJSON.get("usuarioMedico")).shortValue());
			}
			
			horasv = ((hojaConsultaJSON.get("horasv").toString()));

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());

			hojaConsulta.setSecHojaConsulta(secHojaConsulta);
			hojaConsulta.setPesoKg(BigDecimal.valueOf(pesoKg));
			hojaConsulta.setTallaCm(BigDecimal.valueOf(tallaCm));
			hojaConsulta.setTemperaturac(BigDecimal.valueOf(temperaturac));
			hojaConsulta.setExpedienteFisico(expedienteFisico);
			hojaConsulta.setUsuarioEnfermeria(usuarioEnfermeria);
			if(usuarioMedico != null) {
				hojaConsulta.setUsuarioMedico(usuarioMedico);
			}
			hojaConsulta.setHorasv(horasv);
			
			hojaConsulta.setEstado('2');

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
	 * Metodo para cancelar campos ingresados en datos preclinicos
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String cancelarDatosPreclinicos(String paramHojaConsulta) {
		String result = null;
		try {
			int secHojaConsulta;
			Double pesoKg;
			Double tallaCm;
			Double temperaturac;
			String expedienteFisico;
			Short usuarioEnfermeria;
			String motivo;
			String horasv;
 
			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			
			pesoKg = (((Number) hojaConsultaJSON.get("pesoKg")).doubleValue());
			
			tallaCm = ((((Number) hojaConsultaJSON.get("tallaCm"))
					.doubleValue()));
			
			temperaturac = (((Number) hojaConsultaJSON.get("temperaturac"))
					.doubleValue());
			
			expedienteFisico = ((hojaConsultaJSON.get("expedienteFisico")
					.toString()));
			
			usuarioEnfermeria = (((Number) hojaConsultaJSON
					.get("usuarioEnfermeria")).shortValue());
			
			motivo = ((hojaConsultaJSON.get("motivo")
					.toString()));
			
			horasv = ((hojaConsultaJSON.get("horasv").toString()));
			

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());

			hojaConsulta.setSecHojaConsulta(secHojaConsulta);
			hojaConsulta.setPesoKg(BigDecimal.valueOf(pesoKg));
			hojaConsulta.setTallaCm(BigDecimal.valueOf(tallaCm));
			hojaConsulta.setTemperaturac(BigDecimal.valueOf(temperaturac));
			hojaConsulta.setExpedienteFisico(expedienteFisico);
			hojaConsulta.setUsuarioEnfermeria(usuarioEnfermeria);
			hojaConsulta.setEstado('8');
			hojaConsulta.setHorasv(horasv);
			
			MotivoCancelacion motivoCancelacion = new MotivoCancelacion();
			motivoCancelacion.setNumHojaConsulta(secHojaConsulta);
			motivoCancelacion.setMotivo(motivo);
			motivoCancelacion.setCodExpediente(hojaConsulta.getCodExpediente());
			motivoCancelacion.setFechaCancela(Calendar.getInstance().getTime());
			motivoCancelacion.setUsuarioCancela(usuarioEnfermeria);
			

			HIBERNATE_RESOURCE.begin();
			HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);
			HIBERNATE_RESOURCE.getSession().saveOrUpdate(motivoCancelacion);
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
	 * Metodo para enviar a la cola al paciente que no atiende llamado
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String noAtiendeLlamadoDatosPreclinicos(String paramHojaConsulta) {
		String result = null;
		try {
			int secHojaConsulta;
			/*Short usuarioEnfermeria;*/

			//Obteniendo el maximo de orden de llegada para la fecha de hoy
			String sql = "select max(h.ordenLlegada) "
					+ " from HojaConsulta h, EstadosHoja e "
					+ " where h.estado = e.codigo "
					+ " and e.codigo = '1' "
					+ " and to_char(h.fechaConsulta, 'yyyyMMdd') = to_char(current_date, 'yyyyMMdd') ";

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			Integer maxOrdenLlegada = 0; 

			if(query.uniqueResult() != null){
				maxOrdenLlegada = ((Short) query.uniqueResult()).intValue();
			}else{
				//Obteniendo el maximo de orden de llegada que exista
				sql = "select max(h.ordenLlegada) "
						+ " from HojaConsulta h, EstadosHoja e "
						+ " where h.estado = e.codigo "
						+ " and e.codigo = '1' ";
				
				query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
				
				if(query.uniqueResult() != null){
					maxOrdenLlegada = ((Short) query.uniqueResult()).intValue();
				}
			}

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;
			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			/*usuarioEnfermeria = (((Number) hojaConsultaJSON
					.get("usuarioEnfermeria")).shortValue());*/

			query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			//hojaConsulta.setSecHojaConsulta(secHojaConsulta);

			hojaConsulta.setOrdenLlegada(Short.valueOf((maxOrdenLlegada + 1)
					+ ""));
			hojaConsulta.setUsuarioEnfermeria(null);

			HIBERNATE_RESOURCE.begin();
			HIBERNATE_RESOURCE.getSession().update(hojaConsulta);
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
	 * Metodo para buscar el paciente por el codigo
	 * @param paramPaciente, JSON
	 */
	@Override
	public String buscarPaciente(String paramPaciente) {
		String result = null;
		try {
			List oLista = new LinkedList();
			Map fila = null;

			String sql = "select p.nombre1, p.nombre2, "
					+ " p.apellido1, p.apellido2," + " ce.retirado "
					+ " from Paciente p, ConsEstudios ce "
					+ " where cast(p.codExpediente as string)= :codExpediente "
					+ " and p.codExpediente = ce.codigoExpediente " +
					" and ce.retirado != '1' ";
			
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
			query.setParameter("codExpediente", paramPaciente);
			
			List queryResultado = query.list();

			Object[] paciente = (queryResultado.size() > 0) ? (Object[]) queryResultado.get(0) : null;
			
			if (paciente != null && paciente.length > 0) {
				
				fila = new HashMap();
				fila.put(
						"nombrePaciente",
						paciente[0].toString() + " "
								+ ((paciente[1] != null) ? paciente[1].toString() : "") + " "
								+ paciente[2].toString() + " "
								+ ((paciente[3] != null) ? paciente[3].toString() : ""));
				oLista.add(fila);

				// Construir la lista a una estructura JSON
				result = UtilResultado.parserResultado(oLista, "",
						UtilResultado.OK);
			} else {
				result = UtilResultado.parserResultado(null,
						Mensajes.PACIENTE_NO_ENCONTRADO_RETIRADO, UtilResultado.INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null,
					Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}

	/***
	 * Metodo para guardar los datos encontrados por la funcion buscarPaciente
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarPacienteEmergencia(String paramHojaConsulta) {
		String result = null;
		HojaConsulta hojaConsulta = new HojaConsulta();

		try {
			int codExpediente;
			int numHojaConsulta;
			Short usuarioEnfermeria;
			String valorParametro;
			
			
			String sql = "select max(h.ordenLlegada) "
						+ " from HojaConsulta h"
						+ " where to_char(h.fechaConsulta, 'yyyyMMdd') = to_char(current_date, 'yyyyMMdd')";
			
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			Integer maxOrdenLlegada = (query.uniqueResult() == null) ? 1
					: ((Short) query.uniqueResult()).intValue() + 1;

			sql = "select max(h.numHojaConsulta) " + 
				  " from HojaConsulta h";

			query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			numHojaConsulta = ((query.uniqueResult() == null) ? 1
					: ((Integer) query.uniqueResult()).intValue()) + 1;
			
			sql = "select valores " + 
					" from ParametrosSistemas p where p.nombreParametro ='INICIO_HOJA_CONSULTA'";

			query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			valorParametro = query.uniqueResult().toString();
			
			if (Integer.valueOf(valorParametro) > numHojaConsulta) {
				numHojaConsulta = Integer.valueOf(valorParametro);
			}
				
			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			codExpediente = (((Number) hojaConsultaJSON.get("codExpediente"))
					.intValue());
			usuarioEnfermeria = (((Number) hojaConsultaJSON
					.get("usuarioEnfermeria")).shortValue());
			
			 sql = " select h.codExpediente" +
					" from HojaConsulta h " +
					" where to_char(h.fechaConsulta, 'yyyyMMdd') = to_char(current_date, 'yyyyMMdd') " +
					" and h.estado not in('7', '8') " +
					" and h.codExpediente = :codExpediente";
			 
			 query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
			 query.setParameter("codExpediente", codExpediente);
	
			Boolean existeExpActivo = query.list().size() > 0;

					if (!existeExpActivo){
						
						hojaConsulta.setOrdenLlegada(Short.valueOf((maxOrdenLlegada) + ""));
						
						hojaConsulta.setCodExpediente(codExpediente);
						hojaConsulta.setNumHojaConsulta(numHojaConsulta);
						hojaConsulta.setFechaConsulta(new Date());
						hojaConsulta.setUsuarioEnfermeria(usuarioEnfermeria);
						hojaConsulta.setEstado('1');

						HIBERNATE_RESOURCE.begin();
						HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);
						HIBERNATE_RESOURCE.commit();
						result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
					
			}else {
				result = UtilResultado.parserResultado(null,
						Mensajes.CODIGO_PACIENTE_YA_INGRESADO,
						UtilResultado.ERROR);
			}
			
			

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
	 * Metodo para traer el inicio de consulta
	 */
	@Override
	public String getListaInicioHojaConsulta() {
		String result = null;
		try {

			List oLista = new LinkedList();
			Map fila = null;

			String sql = "select distinct h.sec_Hoja_Consulta, h.cod_Expediente, " + 
					" h.num_Hoja_Consulta, " + 
					" p.nombre1 , p.nombre2," + 
					" p.apellido1, p.apellido2, " + 
					" e.descripcion, e.estado, " + 
					" p.sexo, to_char(p.fecha_Nac, 'yyyyMMdd') \"fnac\", " + 
					" to_char(h.fecha_Consulta, 'yyyyMMdd HH12:MI:SS') \"fcon\", e.codigo, h.usuario_Medico, " +
					" h.medico_Cambio_Turno, " +
					" (select um.nombre from Usuarios_View um where h.usuario_Medico = um.id), " +
					" e.orden, h.orden_Llegada " +
					" from hoja_consulta h " +
					" inner join paciente p on p.cod_Expediente = h.cod_Expediente " +
					" inner join estados_hoja e on e.codigo = h.estado  " + 
					" where e.codigo not in ('1','7','8') ";

			sql += "order by e.orden asc, h.orden_Llegada asc";

			Query query = HIBERNATE_RESOURCE.getSession().createSQLQuery(sql);

			List<Object[]> objLista = new ArrayList<Object[]>();
						
			if(query.list() != null)
				objLista.addAll((List<Object[]>) query.list());

			if (objLista != null && objLista.size() > 0) {

				for (Object[] object : objLista) {

					// Construir la fila del registro actual usando arreglos

					fila = new HashMap();
					fila.put("secHojaConsulta", object[0]);
					fila.put("codExpediente", object[1]);
					fila.put("numHojaConsulta", object[2]);
					
					fila.put("nombrePaciente", object[3].toString() + " "
							+ ((object[4] != null) ? object[4].toString() : "") + " "
							+ object[5].toString() + " "
							+ ((object[6] != null) ? object[6].toString() : ""));
					fila.put("descripcion", object[7]);
					fila.put("estado", object[8]);
					fila.put("sexo", object[9]);
					fila.put("fechaNac", object[10]);
					fila.put("fechaConsulta", object[11]);
					fila.put("codigoEstado", object[12]);
					fila.put("usuarioMedico", object[13]);
					fila.put("medicoCambioTurno", object[14]);
					fila.put("nombreMedico", (object[15] != null) ? object[15].toString() : null);

					oLista.add(fila);
				}

				// Construir la lista a una estructura JSON
				result = UtilResultado.parserResultado(oLista, "",
						UtilResultado.OK);
			} else {
				result = UtilResultado.parserResultado(null, Mensajes.NO_PACIENTECONSULTA,
						UtilResultado.INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null,
					Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
		/*
		 * String result= null; try { int secHojaConsulta; List oLista = new
		 * LinkedList(); Map fila = null;
		 * 
		 * String sql = "select h.secHojaConsulta, h.codExpediente, " +
		 * " h.numHojaConsulta, " + " p.nombre1, p.nombre2, " +
		 * " p.apellido1, p.apellido2, " + " e.descripcion, e.estado " +
		 * " from HojaConsulta h, Paciente p, EstadosHoja e " +
		 * " where h.codExpediente = p.codExpediente " +
		 * " and h.estado = e.secEstado " + " and e.codigo <> '1'" +
		 * " and to_char(h.fechaConsulta, 'yyyyMMdd') = to_char(current_date, 'yyyyMMdd') "
		 * ;
		 * 
		 * sql += "order by h.ordenLlegada asc";
		 * 
		 * Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
		 * 
		 * List<Object[]> objLista = (List<Object[]>) query.list();
		 * 
		 * if (objLista != null && objLista.size() > 0) {
		 * 
		 * for (Object[] object : objLista) {
		 * 
		 * // Construir la fila del registro actual usando arreglos
		 * 
		 * fila = new HashMap(); fila.put("secHojaConsulta", object[0]);
		 * fila.put("codExpediente", object[1]); fila.put("numHojaConsulta",
		 * object[2]); fila.put("nombrePaciente", object[3]+ " " + object[4]+
		 * " " + object[5]+ " " + object[6]); fila.put("descripcion",
		 * object[7]); fila.put("estado", object[8]);
		 * 
		 * 
		 * oLista.add(fila);
		 * 
		 * }
		 * 
		 * // Construir la lista a una estructura JSON result =
		 * UtilResultado.parserResultado(oLista, "", UtilResultado.OK); }else{
		 * result = UtilResultado.parserResultado(null, Mensajes.NO_DATOS,
		 * UtilResultado.INFO); }
		 * 
		 * 
		 * } catch (Exception e) { e.printStackTrace(); result =
		 * UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO,
		 * UtilResultado.ERROR); } finally { if
		 * (HIBERNATE_RESOURCE.getSession().isOpen()) {
		 * HIBERNATE_RESOURCE.close(); } } return result;
		 */
	}
	
	/***
	 * Metodo para obtener la lista de consulta por codigo de expediente
	 * @param codExpediente, Codigo Expediente
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getListaHojaConsultaPorExpediente(int codExpediente, boolean esEnfermeria) {
		String result = null;
		try {

			List oLista = new LinkedList();
			Map fila = null;

			String sql = ((esEnfermeria) ? "select distinct h.secHojaConsulta, h.codExpediente, " + 
					" h.numHojaConsulta, " + 
					" p.nombre1, p.nombre2, " + 
					" p.apellido1, p.apellido2, " + 
					" e.descripcion, e.estado, " + 
					" p.sexo, to_char(p.fechaNac, 'yyyyMMdd'), " + 
					" to_char(h.fechaConsulta, 'yyyyMMdd HH:MI:SS am'), " + 
					" h.ordenLlegada, e.codigo, " +
					" (select um.nombre from UsuariosView um where h.usuarioMedico = um.id), " +
					" h.usuarioEnfermeria, " +
					" h.horasv, " +
					" e.orden " : 
					"select h.secHojaConsulta, h.codExpediente, " + 
					" h.numHojaConsulta, " + 
					" p.nombre1 , p.nombre2," + 
					" p.apellido1, p.apellido2, " + 
					" e.descripcion, e.estado, " + 
					" p.sexo, to_char(p.fechaNac, 'yyyyMMdd'), " + 
					" to_char(h.fechaConsulta, 'yyyyMMdd HH12:MI:SS'),e.codigo, h.usuarioMedico, " +
					" h.medicoCambioTurno, " +
					" (select um.nombre from UsuariosView um where h.usuarioMedico = um.id) ");

			sql += " from HojaConsulta h, Paciente p, EstadosHoja e " +
					" where h.codExpediente = p.codExpediente " + 
					" and h.estado = e.codigo " +
					" and cast(h.codExpediente as string) like :codExpediente" + 
					((esEnfermeria) ? " and e.codigo not in('8','7','9') "  : 
						" and e.codigo not in('1', '8','7','9') ") ;
//					+ " and to_char(h.fechaConsulta, 'yyyyMMdd') = to_char(current_date, 'yyyyMMdd') ";

			sql += "order by h.ordenLlegada asc";

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			if (codExpediente > 0)
				query.setParameter("codExpediente", new StringBuffer().append('%').append(codExpediente).append('%').toString());

			List<Object[]> objLista = (List<Object[]>) query.list();

			if (objLista != null && objLista.size() > 0) {

				if(esEnfermeria) {
					for (Object[] object : objLista) {

						// Construir la fila del registro actual usando arreglos

						fila = new HashMap();
						fila.put("secHojaConsulta", object[0]);
						fila.put("codExpediente", object[1]);
						fila.put("numHojaConsulta", object[2]);
						fila.put("nombrePaciente", object[3].toString() + " "
								+ ((object[4] != null) ? object[4].toString() : "") + " "
								+ object[5].toString() + " "
								+ ((object[6] != null) ? object[6].toString() : ""));
						fila.put("descripcion", object[7]);
						fila.put("estado", object[8]);
						fila.put("sexo", object[9]);
						fila.put("fechaNac", object[10]);
						fila.put("fechaConsulta", object[11]);
						fila.put("ordenLlegada", object[12]);
						fila.put("codigoEstado", object[13]);
						fila.put("nombreMedico", (object[14] != null) ? object[14].toString() : null);
						fila.put("usuarioEnfermeria", object[15]);
						fila.put("horasv", (object[16] != null) ? object[16] : null);

						oLista.add(fila);
					}
				} else {
					for (Object[] object : objLista) {
						// Construir la fila del registro actual usando arreglos
						fila = new HashMap();
						fila.put("secHojaConsulta", object[0]);
						fila.put("codExpediente", object[1]);
						fila.put("numHojaConsulta", object[2]);
						
						fila.put("nombrePaciente", object[3].toString() + " "
								+ ((object[4] != null) ? object[4].toString() : "") + " "
								+ object[5].toString() + " "
								+ ((object[6] != null) ? object[6].toString() : ""));
						fila.put("descripcion", object[7]);
						fila.put("estado", object[8]);
						fila.put("sexo", object[9]);
						fila.put("fechaNac", object[10]);
						fila.put("fechaConsulta", object[11]);
						fila.put("codigoEstado", object[12]);
						fila.put("usuarioMedico", object[13]);
						fila.put("medicoCambioTurno", object[14]);
						fila.put("nombreMedico", (object[15] != null) ? object[15].toString() : null);
	
						oLista.add(fila);
	
					}
				}

				// Construir la lista a una estructura JSON
				result = UtilResultado.parserResultado(oLista, "",
						UtilResultado.OK);
			} else {
				result = UtilResultado.parserResultado(null, Mensajes.NO_DATOS,
						UtilResultado.INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null,
					Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}

	/***
	 * Meto para obtener una hoja de consulta por codigo de
	 * expediente.
	 * @param codExpediente, Codigo Expediente
	 * @param secHojaConsulta, Identificador Hoja consulta
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getDatosCabeceraSintomas(Integer codExpediente,
			Integer secHojaConsulta) {
		String result = null;
		try {
			List oLista = new LinkedList(); // Listado final para el resultado
			Map fila = null; // Objeto para cada registro recuperado

			String sql = "select h.expedienteFisico, p.edad, p.sexo, h.pesoKg, " + 
			" h.tallaCm, h.temperaturac, " + 
					" to_char(p.fechaNac, 'yyyyMMdd'), " + 
					" to_char(h.fechaConsulta, 'yyyyMMdd HH:MI:SS am'), " +
					" h.hora " + 
					" from HojaConsulta h, Paciente p " + 
					" where h.codExpediente = p.codExpediente " + 
					" and h.secHojaConsulta = :secHojaConsulta ";

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			query.setParameter("secHojaConsulta", secHojaConsulta);

			Object[] hojaConsultaPaciente = (Object[]) query.uniqueResult();

			if (hojaConsultaPaciente != null && hojaConsultaPaciente.length > 0) {

				sql = "select ec " + " from ConsEstudios c, EstudioCatalogo ec " + 
						" where c.codigoConsentimiento = ec.codEstudio"  + 
						" and c.codigoExpediente = :codExpediente " + 
						" and c.retirado != '1' " +
						" group by ec.codEstudio, ec.descEstudio";

				
				query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

				query.setParameter("codExpediente", codExpediente);

				List<EstudioCatalogo> lstConsEstudios = (List<EstudioCatalogo>) query
						.list();
				StringBuffer codigosEstudios = new StringBuffer();

				for (EstudioCatalogo estudioCatalogo : lstConsEstudios) {
					codigosEstudios.append(
							estudioCatalogo.getDescEstudio()).append(",");
				}

				fila = new HashMap();
				fila.put("expedienteFisico", hojaConsultaPaciente[0]);
				fila.put(
						"estudiosParticipante",
						codigosEstudios != null
								&& !codigosEstudios.toString().isEmpty() ? (codigosEstudios
								.substring(0, (codigosEstudios.length() - 1)))
								: "");
				fila.put("edad", hojaConsultaPaciente[1]);
				fila.put("sexo", hojaConsultaPaciente[2]);
				fila.put("pesoKg", hojaConsultaPaciente[3]);
				fila.put("tallaCm", hojaConsultaPaciente[4]);
				fila.put("temperaturac", hojaConsultaPaciente[5]);
				fila.put("fechaNacimiento", hojaConsultaPaciente[6]);
				fila.put("fechaConsulta", hojaConsultaPaciente[7]);
				fila.put("horaConsulta", hojaConsultaPaciente[8]);

				oLista.add(fila);

				// Construir la lista a una estructura JSON
				result = UtilResultado.parserResultado(oLista, "",
						UtilResultado.OK);
			} else {
				result = UtilResultado.parserResultado(null, Mensajes.NO_DATOS,
						UtilResultado.INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null,
					Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}

	/***
	 * Metodo para obtener hoja de consulta por identificador
	 * @param secHojaConsulta, Identificador Hoja consulta
	 */
	@SuppressWarnings("unchecked")
	@Override
	// OBTENER DATOS DE CONSULTA
	public String getHojaConsultaPorNumero(Integer secHojaConsulta) {
		String result = null;
		try {
			List oLista = new LinkedList(); // Listado final para el resultado
			Map fila = null; // Objeto para cada registro recuperado

			String sql = "select a " + "from HojaConsulta a ";

			if (secHojaConsulta > 0)
				sql += "where a.secHojaConsulta = :secHojaConsulta ";

			sql += "order by a.ordenLlegada ";

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			if (secHojaConsulta > 0)
				query.setParameter("secHojaConsulta", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());

			if (hojaConsulta != null && hojaConsulta.getSecHojaConsulta() > 0) {

				// Construir la fila del registro actual
				fila = new HashMap();
				fila.put("secHojaConsulta", hojaConsulta.getSecHojaConsulta());
				fila.put("codExpediente", hojaConsulta.getCodExpediente());
				fila.put("numHojaConsulta", hojaConsulta.getNumHojaConsulta());
				fila.put("estado", hojaConsulta.getEstado());

				// EXAMEN HISTORIA
				fila.put("historiaExamenFisico",
						hojaConsulta.getHistoriaExamenFisico());
				// TRATAMIENTO
				fila.put("acetaminofen", hojaConsulta.getAcetaminofen());
				fila.put("asa", hojaConsulta.getAsa());
				fila.put("ibuprofen", hojaConsulta.getIbuprofen());
				fila.put("penicilina", hojaConsulta.getPenicilina());
				fila.put("amoxicilina", hojaConsulta.getAmoxicilina());
				fila.put("dicloxacilina", hojaConsulta.getDicloxacilina());
				fila.put("otro", hojaConsulta.getOtro());
				fila.put("otroAntibiotico", hojaConsulta.getOtroAntibiotico());
				fila.put("furazolidona", hojaConsulta.getFurazolidona());
				fila.put("metronidazolTinidazol",
						hojaConsulta.getMetronidazolTinidazol());
				fila.put("albendazolMebendazol",
						hojaConsulta.getAlbendazolMebendazol());
				fila.put("sulfatoFerroso", hojaConsulta.getSulfatoFerroso());
				fila.put("sueroOral", hojaConsulta.getSueroOral());
				fila.put("sulfatoZinc", hojaConsulta.getSulfatoZinc());
				fila.put("liquidosIv", hojaConsulta.getLiquidosIv());
				fila.put("prednisona", hojaConsulta.getPrednisona());
				fila.put("hidrocortisonaIv", hojaConsulta.getHidrocortisonaIv());
				fila.put("salbutamol", hojaConsulta.getSalbutamol());
				fila.put("oseltamivir", hojaConsulta.getOseltamivir());
				// PLANES
				fila.put("planes", hojaConsulta.getPlanes());
				// DIAGNOSTICO
				fila.put("diagnostico1", hojaConsulta.getDiagnostico1());
				fila.put("diagnostico2", hojaConsulta.getDiagnostico2());
				fila.put("diagnostico3", hojaConsulta.getDiagnostico3());
				fila.put("diagnostico4", hojaConsulta.getDiagnostico4());
				fila.put("otroDiagnostico", hojaConsulta.getOtroDiagnostico());

				// PROXIMA CITA
				fila.put("telef", ( hojaConsulta.getTelef() != null ) ? hojaConsulta.getTelef() : null);
				fila.put("proximaCita", (hojaConsulta.getProximaCita() != null) ? new SimpleDateFormat("dd/MM/yyyy").format(hojaConsulta.getProximaCita()) : null);
				fila.put("colegio", hojaConsulta.getColegio());
				fila.put("horarioClases", hojaConsulta.getHorarioClases());

				oLista.add(fila);

				// Construir la lista a una estructura JSON
				result = UtilResultado.parserResultado(oLista, "",
						UtilResultado.OK);
			} else {
				result = UtilResultado.parserResultado(null, Mensajes.NO_DATOS,
						UtilResultado.INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null,
					Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}

	/***
	 * Metodo para guardar seccion Estado General
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarEstadoGeneralSintomas(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			Character fiebre;
			Character astenia;
			Character asomnoliento;
			Character malEstado;
			Character perdidaConsciencia;
			Character inquieto;
			Character convulsiones;
			Character hipotermia;
			Character letargia;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			fiebre = (hojaConsultaJSON.get("fiebre").toString().charAt(0));
			astenia = (hojaConsultaJSON.get("astenia").toString().charAt(0));
			asomnoliento = (hojaConsultaJSON.get("asomnoliento").toString()
					.charAt(0));
			malEstado = (hojaConsultaJSON.get("malEstado").toString().charAt(0));
			perdidaConsciencia = (hojaConsultaJSON.get("perdidaConsciencia")
					.toString().charAt(0));
			inquieto = (hojaConsultaJSON.get("inquieto").toString().charAt(0));
			convulsiones = (hojaConsultaJSON.get("convulsiones").toString()
					.charAt(0));
			hipotermia = (hojaConsultaJSON.get("hipotermia").toString()
					.charAt(0));
			letargia = (hojaConsultaJSON.get("letargia").toString().charAt(0));
			
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();
			
			//usuarioLogiado = 

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(UtilHojaConsulta.estadoGeneralCompletada(hojaConsulta)) {
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				EstadoGeneralesControlCambios egcc = new EstadoGeneralesControlCambios();
				egcc.setFiebre(fiebre);
				egcc.setAstenia(astenia);
				egcc.setAsomnoliento(asomnoliento);
				egcc.setMalEstado(malEstado);
				egcc.setPerdidaConsciencia(perdidaConsciencia);
				egcc.setInquieto(inquieto);
				egcc.setConvulsiones(convulsiones);
				egcc.setHipotermia(hipotermia);
				egcc.setLetargia(letargia);
				egcc.setUsuario(usuarioLogiado);
				egcc.setControlador("EstadoGeneralSintomasActivity");
				ctrlCambiosDA.guardarControlCambios(hojaConsulta, egcc);
			}

			hojaConsulta.setFiebre(fiebre);
			hojaConsulta.setAstenia(astenia);
			hojaConsulta.setAsomnoliento(asomnoliento);
			hojaConsulta.setMalEstado(malEstado);
			hojaConsulta.setPerdidaConsciencia(perdidaConsciencia);
			hojaConsulta.setInquieto(inquieto);
			hojaConsulta.setConvulsiones(convulsiones);
			hojaConsulta.setHipotermia(hipotermia);
			hojaConsulta.setLetargia(letargia);

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
	 * Metodo para guardar seccion Gastroinstestinal
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarGastroInSintomas(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			Character pocoApetito;
			Character nausea;
			Character dificultadAlimentarse;
			Character vomito12horas;
			Short vomito12h;
			Character diarrea;
			Character diarreaSangre;
			Character estrenimiento;
			Character dolorAbIntermitente;
			Character dolorAbContinuo;
			Character epigastralgia;
			Character intoleranciaOral;
			Character distensionAbdominal;
			Character hepatomegalia;
			Double hepatomegaliaCM;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			pocoApetito = (hojaConsultaJSON.get("pocoApetito").toString()
					.charAt(0));
			nausea = (hojaConsultaJSON.get("nausea").toString().charAt(0));
			dificultadAlimentarse = (hojaConsultaJSON.get(
					"dificultadAlimentarse").toString().charAt(0));
			vomito12horas = (hojaConsultaJSON.get("vomito12horas").toString()
					.charAt(0));
			
			vomito12h = hojaConsultaJSON.get("vomito12h") != null
					&& !hojaConsultaJSON.get("vomito12h").equals("null") ? ((Number) hojaConsultaJSON
					.get("vomito12h")).shortValue() : null;
			
			diarrea = (hojaConsultaJSON.get("diarrea").toString().charAt(0));			
			diarreaSangre = (hojaConsultaJSON.get("diarreaSangre").toString()
					.charAt(0));
			estrenimiento = (hojaConsultaJSON.get("estrenimiento").toString()
					.charAt(0));
			dolorAbIntermitente = (hojaConsultaJSON.get("dolorAbIntermitente")
					.toString().charAt(0));
			dolorAbContinuo = (hojaConsultaJSON.get("dolorAbContinuo")
					.toString().charAt(0));
			epigastralgia = (hojaConsultaJSON.get("epigastralgia").toString()
					.charAt(0));
			intoleranciaOral = (hojaConsultaJSON.get("intoleranciaOral")
					.toString().charAt(0));
			distensionAbdominal = (hojaConsultaJSON.get("distensionAbdominal")
					.toString().charAt(0));
			hepatomegalia = (hojaConsultaJSON.get("hepatomegalia").toString()
					.charAt(0));
			hepatomegaliaCM = hojaConsultaJSON.get("hepatomegaliaCM") != null
					&& !hojaConsultaJSON.get("hepatomegaliaCM").equals("null") ? ((Number) hojaConsultaJSON
					.get("hepatomegaliaCM")).doubleValue() : null;

			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();
			String controlador = "GastrointestinalSintomasActivity";
							
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(UtilHojaConsulta.gastrointestinalCompletada(hojaConsulta)) {
				HojaConsulta hcNueva = new HojaConsulta();
				
				hcNueva.setPocoApetito(pocoApetito);
				hcNueva.setNausea(nausea);
				hcNueva.setDificultadAlimentarse(dificultadAlimentarse);
				hcNueva.setVomito12horas(vomito12horas);
				hcNueva.setVomito12h(vomito12h);
				hcNueva.setDiarrea(diarrea);
				hcNueva.setDiarreaSangre(diarreaSangre);
				hcNueva.setEstrenimiento(estrenimiento);
				hcNueva.setDolorAbIntermitente(dolorAbIntermitente);
				hcNueva.setDolorAbContinuo(dolorAbContinuo);
				hcNueva.setEpigastralgia(epigastralgia);
				hcNueva.setIntoleranciaOral(intoleranciaOral);
				hcNueva.setDistensionAbdominal(distensionAbdominal);
				hcNueva.setHepatomegalia(hepatomegalia);
				if (hepatomegaliaCM != null){
					hcNueva.setHepatomegaliaCm(BigDecimal.valueOf(hepatomegaliaCM.doubleValue()));
				}
				
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				ctrlCambiosDA.guardarCtrlGastroIn(hojaConsulta, hcNueva, usuarioLogiado, controlador);
			}

			hojaConsulta.setPocoApetito(pocoApetito);
			hojaConsulta.setNausea(nausea);
			hojaConsulta.setDificultadAlimentarse(dificultadAlimentarse);
			hojaConsulta.setVomito12horas(vomito12horas);
			
			if(vomito12h != null){
				hojaConsulta.setVomito12h(vomito12h);
				
				if(!estaEnRango(0, 24, String.valueOf(vomito12h)) || 
						(hojaConsulta.getVomito12h() != null && hojaConsulta.getVomito12h() != vomito12h) ){
					ControlCambios controlCambios = new ControlCambios();
		        	controlCambios.setCodExpediente(hojaConsulta.getCodExpediente());		        	
		        	controlCambios.setNombreCampo("Vomito ultimas 12 horas");
		        	controlCambios.setValorCampo(String.valueOf(vomito12h));
		        	controlCambios.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
		        	controlCambios.setTipoControl(ControlCambiosDA.Discrepancia);
		        	controlCambios.setUsuario(usuarioLogiado);
		        	controlCambios.setControlador(controlador);
		        	
		        	ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
		        	ctrlCambiosDA.guardarControlCambios(controlCambios);
				}
			} else {
				hojaConsulta.setVomito12h(null);
			}
			
			hojaConsulta.setDiarrea(diarrea);
			hojaConsulta.setDiarreaSangre(diarreaSangre);
			hojaConsulta.setEstrenimiento(estrenimiento);
			hojaConsulta.setDolorAbIntermitente(dolorAbIntermitente);
			hojaConsulta.setDolorAbContinuo(dolorAbContinuo);
			hojaConsulta.setEpigastralgia(epigastralgia);
			hojaConsulta.setIntoleranciaOral(intoleranciaOral);
			hojaConsulta.setDistensionAbdominal(distensionAbdominal);
			hojaConsulta.setHepatomegalia(hepatomegalia);
			
			if (hepatomegaliaCM != null){
				hojaConsulta.setHepatomegaliaCm(BigDecimal.valueOf(hepatomegaliaCM.doubleValue()));

				if(!estaEnRango(0, 5, String.valueOf(hepatomegaliaCM)) ||
						(hojaConsulta.getHepatomegaliaCm() != null && hojaConsulta.getHepatomegaliaCm().doubleValue() != hepatomegaliaCM.doubleValue())){
					ControlCambios controlCambios = new ControlCambios();
		        	controlCambios.setCodExpediente(hojaConsulta.getCodExpediente());		        	
		        	controlCambios.setNombreCampo("Hepatomegalia Cm");
		        	controlCambios.setValorCampo(String.valueOf(hepatomegaliaCM));
		        	controlCambios.setNumHojaConsulta(hojaConsulta.getNumHojaConsulta());
		        	controlCambios.setTipoControl(ControlCambiosDA.Discrepancia);
		        	controlCambios.setUsuario(usuarioLogiado);
		        	controlCambios.setControlador(controlador);
		        	
		        	ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
		        	ctrlCambiosDA.guardarControlCambios(controlCambios);
				}
			} else {
				hojaConsulta.setHepatomegaliaCm(null);
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
	
	private boolean estaEnRango(double min, double max, String valor) {
        double valorComparar = Double.parseDouble(valor);
        return (valorComparar >= min && valorComparar <= max) ? true : false;
    }

	/***
	 * Metodo para guardar seccion Osteomuscular
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarOsteomuscularSintomas(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			Character altralgia;
			Character mialgia;
			Character lumbalgia;
			Character dolorCuello;
			Character tenosinovitis;
			Character artralgiaProximal;
			Character artralgiaDistal;
			Character conjuntivitis;
			Character edemaMunecas;
			Character edemaCodos;
			Character edemaHombros;
			Character edemaRodillas;
			Character edemaTobillos;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			altralgia = (hojaConsultaJSON.get("altralgia").toString().charAt(0));
			mialgia = (hojaConsultaJSON.get("mialgia").toString().charAt(0));
			lumbalgia = (hojaConsultaJSON.get("lumbalgia").toString().charAt(0));
			dolorCuello = (hojaConsultaJSON.get("dolorCuello").toString()
					.charAt(0));
			tenosinovitis = (hojaConsultaJSON.get("tenosinovitis").toString()
					.charAt(0));
			artralgiaProximal = (hojaConsultaJSON.get("artralgiaProximal")
					.toString().charAt(0));
			artralgiaDistal = (hojaConsultaJSON.get("artralgiaDistal")
					.toString().charAt(0));
			conjuntivitis = (hojaConsultaJSON.get("conjuntivitis").toString()
					.charAt(0));
			edemaMunecas = (hojaConsultaJSON.get("edemaMunecas").toString()
					.charAt(0));
			edemaCodos = (hojaConsultaJSON.get("edemaCodos").toString()
					.charAt(0));
			edemaHombros = (hojaConsultaJSON.get("edemaHombros").toString()
					.charAt(0));
			edemaRodillas = (hojaConsultaJSON.get("edemaRodillas").toString()
					.charAt(0));
			edemaTobillos = (hojaConsultaJSON.get("edemaTobillos").toString()
					.charAt(0));
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(UtilHojaConsulta.osteomuscularCompletada(hojaConsulta)) {
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				OsteomuscularControlCambio cc = new OsteomuscularControlCambio();
				cc.setAltralgia(altralgia);
				cc.setArtralgiaDistal(artralgiaDistal);
				cc.setArtralgiaProximal(artralgiaProximal);
				cc.setConjuntivitis(conjuntivitis);
				cc.setDolorCuello(dolorCuello);
				cc.setEdemaCodos(edemaCodos);
				cc.setEdemaHombros(edemaHombros);
				cc.setEdemaMunecas(edemaMunecas);
				cc.setEdemaRodillas(edemaRodillas);
				cc.setEdemaTobillos(edemaTobillos);
				cc.setLumbalgia(lumbalgia);
				cc.setMialgia(mialgia);
				cc.setTenosinovitis(tenosinovitis);
				cc.setUsuario(usuarioLogiado);
				cc.setControlador("OsteomuscularSintomasActivity");
				ctrlCambiosDA.guardarControlCambios(hojaConsulta, cc);
			}

			hojaConsulta.setAltralgia(altralgia);
			hojaConsulta.setMialgia(mialgia);
			hojaConsulta.setLumbalgia(lumbalgia);
			hojaConsulta.setDolorCuello(dolorCuello);
			hojaConsulta.setTenosinovitis(tenosinovitis);
			hojaConsulta.setArtralgiaProximal(artralgiaProximal);
			hojaConsulta.setArtralgiaDistal(artralgiaDistal);
			hojaConsulta.setConjuntivitis(conjuntivitis);
			hojaConsulta.setEdemaMunecas(edemaMunecas);
			hojaConsulta.setEdemaCodos(edemaCodos);
			hojaConsulta.setEdemaHombros(edemaHombros);
			hojaConsulta.setEdemaRodillas(edemaRodillas);
			hojaConsulta.setEdemaTobillos(edemaTobillos);

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
	 * Metodo para guardar seccion Cabeza
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarCabezaSintomas(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			Character cefalea;
			Character rigidezCuello;
			Character inyeccionConjuntival;
			Character hemorragiaSuconjuntival;
			Character dolorRetroocular;
			Character fontanelaAbombada;
			Character ictericiaConuntival;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			cefalea = (hojaConsultaJSON.get("cefalea").toString().charAt(0));
			rigidezCuello = (hojaConsultaJSON.get("rigidezCuello").toString()
					.charAt(0));
			inyeccionConjuntival = (hojaConsultaJSON
					.get("inyeccionConjuntival").toString().charAt(0));
			hemorragiaSuconjuntival = (hojaConsultaJSON.get(
					"hemorragiaSuconjuntival").toString().charAt(0));
			dolorRetroocular = (hojaConsultaJSON.get("dolorRetroocular")
					.toString().charAt(0));
			fontanelaAbombada = (hojaConsultaJSON.get("fontanelaAbombada")
					.toString().charAt(0));
			ictericiaConuntival = (hojaConsultaJSON.get("ictericiaConuntival")
					.toString().charAt(0));
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(UtilHojaConsulta.cabezaCompletada(hojaConsulta)) {
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				CabezaControlCambios cc = new CabezaControlCambios();
				cc.setCefalea(cefalea);
				cc.setRigidezCuello(rigidezCuello);
				cc.setInyeccionConjuntival(inyeccionConjuntival);
				cc.setHemorragiaSuconjuntival(hemorragiaSuconjuntival);
				cc.setDolorRetroocular(dolorRetroocular);
				cc.setFontanelaAbombada(fontanelaAbombada);
				cc.setIctericiaConuntival(ictericiaConuntival);
				cc.setUsuario(usuarioLogiado);
				cc.setControlador("CabezaSintomaActivity");
				ctrlCambiosDA.guardarControlCambios(hojaConsulta, cc);
			}

			hojaConsulta.setCefalea(cefalea);
			hojaConsulta.setRigidezCuello(rigidezCuello);
			hojaConsulta.setInyeccionConjuntival(inyeccionConjuntival);
			hojaConsulta.setHemorragiaSuconjuntival(hemorragiaSuconjuntival);
			hojaConsulta.setDolorRetroocular(dolorRetroocular);
			hojaConsulta.setFontanelaAbombada(fontanelaAbombada);
			hojaConsulta.setIctericiaConuntival(ictericiaConuntival);

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
	 * Metodo para guardar seccion Deshidratacion
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarDeshidraSintomas(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			Character lenguaMucosasSecas;
			Character pliegueCutaneo;
			Character orinaReducida;
			Character bebeConSed;
			String ojosHundidos;
			Character fontanelaHundida;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			lenguaMucosasSecas = (hojaConsultaJSON.get("lenguaMucosasSecas")
					.toString().charAt(0));
			pliegueCutaneo = (hojaConsultaJSON.get("pliegueCutaneo").toString()
					.charAt(0));
			orinaReducida = (hojaConsultaJSON.get("orinaReducida").toString()
					.charAt(0));
			bebeConSed = (hojaConsultaJSON.get("bebeConSed").toString()
					.charAt(0));
			ojosHundidos = (hojaConsultaJSON.get("ojosHundidos").toString());
			fontanelaHundida = (hojaConsultaJSON.get("fontanelaHundida")
					.toString().charAt(0));
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(UtilHojaConsulta.deshidratacionCompletada(hojaConsulta)) {
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				DeshidratacionControlCambio cc = new DeshidratacionControlCambio();
				cc.setBebeConSed(bebeConSed);
				cc.setFontanelaHundida(fontanelaHundida);
				cc.setLenguaMucosasSecas(lenguaMucosasSecas);
				cc.setOjosHundidos(ojosHundidos.charAt(0));
				cc.setOrinaReducida(orinaReducida);
				cc.setPliegueCutaneo(pliegueCutaneo);
				cc.setUsuario(usuarioLogiado);
				cc.setControlador("DeshidratacionSintomasActivity");
				ctrlCambiosDA.guardarControlCambios(hojaConsulta, cc);
			}

			hojaConsulta.setLenguaMucosasSecas(lenguaMucosasSecas);
			hojaConsulta.setPliegueCutaneo(pliegueCutaneo);
			hojaConsulta.setOrinaReducida(orinaReducida);
			hojaConsulta.setBebeConSed(bebeConSed);
			hojaConsulta.setOjosHundidos(ojosHundidos);
			hojaConsulta.setFontanelaHundida(fontanelaHundida);

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
	 * Metodo para guardar seccion Cutaneo
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarCutaneoSintomas(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			Character rahsLocalizado;
			Character rahsGeneralizado;
			Character rashEritematoso;
			Character rahsMacular;
			Character rashPapular;
			Character rahsMoteada;
			Character ruborFacial;
			Character equimosis;
			Character cianosisCentral;
			Character ictericia;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			rahsLocalizado = (hojaConsultaJSON.get("rahsLocalizado").toString()
					.charAt(0));
			rahsGeneralizado = (hojaConsultaJSON.get("rahsGeneralizado")
					.toString().charAt(0));
			rashEritematoso = (hojaConsultaJSON.get("rashEritematoso")
					.toString().charAt(0));
			rahsMacular = (hojaConsultaJSON.get("rahsMacular").toString()
					.charAt(0));
			rashPapular = (hojaConsultaJSON.get("rashPapular").toString()
					.charAt(0));
			rahsMoteada = (hojaConsultaJSON.get("rahsMoteada").toString()
					.charAt(0));
			ruborFacial = (hojaConsultaJSON.get("ruborFacial").toString()
					.charAt(0));
			equimosis = (hojaConsultaJSON.get("equimosis").toString().charAt(0));
			cianosisCentral = (hojaConsultaJSON.get("cianosisCentral")
					.toString().charAt(0));
			ictericia = (hojaConsultaJSON.get("ictericia").toString().charAt(0));
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(UtilHojaConsulta.cutaneoCompletada(hojaConsulta)) {
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				CutaneoControlCambios cc = new CutaneoControlCambios();
				cc.setRashEritematoso(rashEritematoso);
				cc.setRashPapular(rashPapular);
				cc.setCianosisCentral(cianosisCentral);
				cc.setIctericia(ictericia);
				cc.setEquimosis(equimosis);
				cc.setRahsGeneralizado(rahsGeneralizado);
				cc.setRahsLocalizado(rahsLocalizado);
				cc.setRahsMacular(rahsMacular);
				cc.setRahsMoteada(rahsMoteada);
				cc.setRuborFacial(ruborFacial);
				cc.setUsuario(usuarioLogiado);
				cc.setControlador("CutaneoSintomaActivity");
				ctrlCambiosDA.guardarControlCambios(hojaConsulta, cc);
			}

			hojaConsulta.setRahsLocalizado(rahsLocalizado);
			hojaConsulta.setRahsGeneralizado(rahsGeneralizado);
			hojaConsulta.setRashEritematoso(rashEritematoso);
			hojaConsulta.setRahsMacular(rahsMacular);
			hojaConsulta.setRashPapular(rashPapular);
			hojaConsulta.setRahsMoteada(rahsMoteada);
			hojaConsulta.setRuborFacial(ruborFacial);
			hojaConsulta.setEquimosis(equimosis);
			hojaConsulta.setCianosisCentral(cianosisCentral);
			hojaConsulta.setIctericia(ictericia);

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
	 * Metodo para guardar seccion Garganta
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarGargantaSintomas(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			Character eritema;
			Character dolorGarganta;
			Character adenopatiasCervicales;
			Character exudado;
			Character petequiasMucosa;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			eritema = (hojaConsultaJSON.get("eritema").toString().charAt(0));
			dolorGarganta = (hojaConsultaJSON.get("dolorGarganta").toString()
					.charAt(0));
			adenopatiasCervicales = (hojaConsultaJSON.get(
					"adenopatiasCervicales").toString().charAt(0));
			exudado = (hojaConsultaJSON.get("exudado").toString().charAt(0));
			petequiasMucosa = (hojaConsultaJSON.get("petequiasMucosa")
					.toString().charAt(0));
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(UtilHojaConsulta.gargantaCompletada(hojaConsulta)) {
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				GargantaControlCambio cc = new GargantaControlCambio();
				cc.setEritema(eritema);
				cc.setDolorGarganta(dolorGarganta);
				cc.setAdenopatiasCervicales(adenopatiasCervicales);
				cc.setExudado(exudado);
				cc.setPetequiasMucosa(petequiasMucosa);
				cc.setUsuario(usuarioLogiado);
				cc.setControlador("GargantaSintomasActivity");
				ctrlCambiosDA.guardarControlCambios(hojaConsulta, cc);
			}

			hojaConsulta.setEritema(eritema);
			hojaConsulta.setDolorGarganta(dolorGarganta);
			hojaConsulta.setAdenopatiasCervicales(adenopatiasCervicales);
			hojaConsulta.setExudado(exudado);
			hojaConsulta.setPetequiasMucosa(petequiasMucosa);

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
	 * Metodo para guardar seccion Renal
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarRenalSintomas(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			Character sintomasUrinarios;
			Character leucocituria;
			Character nitritos;
			Character eritrocitos;
			Character bilirrubinuria;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			sintomasUrinarios = (hojaConsultaJSON.get("sintomasUrinarios")
					.toString().charAt(0));
			leucocituria = (hojaConsultaJSON.get("leucocituria").toString()
					.charAt(0));
			nitritos = (hojaConsultaJSON.get("nitritos").toString().charAt(0));
			eritrocitos = (hojaConsultaJSON.get("eritrocitos").toString()
					.charAt(0));
			bilirrubinuria = (hojaConsultaJSON.get("bilirrubinuria").toString()
					.charAt(0));
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(UtilHojaConsulta.renalCompletada(hojaConsulta)) {
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				RenalControlCambio cc = new RenalControlCambio();
				cc.setSintomasUrinarios(sintomasUrinarios);
				cc.setLeucocituria(leucocituria);
				cc.setNitritos(nitritos);
				cc.setEritrocitos(eritrocitos);
				cc.setBilirrubinuria(bilirrubinuria);
				cc.setUsuario(usuarioLogiado);
				cc.setControlador("RenalSintomasActivity");
				ctrlCambiosDA.guardarControlCambios(hojaConsulta, cc);
			}

			hojaConsulta.setSintomasUrinarios(sintomasUrinarios);
			hojaConsulta.setLeucocituria(leucocituria);
			hojaConsulta.setNitritos(nitritos);
			hojaConsulta.setEritrocitos(eritrocitos);
			hojaConsulta.setBilirrubinuria(bilirrubinuria);

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
	 * Metodo para guardar seccion Estado Nutricional
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarEstadoNutriSintomas(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			Double imc;
			Character obeso;
			Character sobrepeso;
			Character sospechaProblema;
			Character normal;
			Character bajoPeso;
			Character bajoPesoSevero;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			imc = ((Number) hojaConsultaJSON.get("imc")).doubleValue();			
			obeso = (hojaConsultaJSON.get("obeso").toString().charAt(0));
			sobrepeso = (hojaConsultaJSON.get("sobrepeso").toString().charAt(0));
			sospechaProblema = (hojaConsultaJSON.get("sospechaProblema")
					.toString().charAt(0));
			normal = (hojaConsultaJSON.get("normal").toString().charAt(0));
			bajoPeso = (hojaConsultaJSON.get("bajoPeso").toString().charAt(0));
			bajoPesoSevero = (hojaConsultaJSON.get("bajoPesoSevero").toString()
					.charAt(0));
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(UtilHojaConsulta.estadoNutricionalCompletada(hojaConsulta)) {
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				EstadoNutricionalControlCambio cc = new EstadoNutricionalControlCambio();
				cc.setImc(BigDecimal.valueOf(imc));
				cc.setObeso(obeso);
				cc.setSobrepeso(sobrepeso);
				cc.setSospechaProblema(sospechaProblema);
				cc.setNormal(normal);
				cc.setBajoPeso(bajoPeso);
				cc.setBajoPesoSevero(bajoPesoSevero);
				cc.setUsuario(usuarioLogiado);
				cc.setControlador("RenalSintomasActivity");
				ctrlCambiosDA.guardarControlCambios(hojaConsulta, cc);
			}

			hojaConsulta.setImc(BigDecimal.valueOf(imc));
			hojaConsulta.setObeso(obeso);
			hojaConsulta.setSobrepeso(sobrepeso);
			hojaConsulta.setSospechaProblema(sospechaProblema);
			hojaConsulta.setNormal(normal);
			hojaConsulta.setBajoPeso(bajoPeso);
			hojaConsulta.setBajoPesoSevero(bajoPesoSevero);

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
	 * Metodo para guardar seccion Respiratorio
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarRespiratorioSintomas(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			Character tos;
			Character rinorrea;
			Character congestionNasal;
			Character otalgia;
			Character aleteoNasal;
			Character apnea;
			Character respiracionRapida;
			Character quejidoEspiratorio;
			Character estiradorReposo;
			Character tirajeSubcostal;
			Character sibilancias;
			Character crepitos;
			Character roncos;
			Character otraFif;
			String nuevaFif = null;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			tos = (hojaConsultaJSON.get("tos").toString().charAt(0));
			rinorrea = (hojaConsultaJSON.get("rinorrea").toString().charAt(0));
			congestionNasal = (hojaConsultaJSON.get("congestionNasal")
					.toString().charAt(0));
			otalgia = (hojaConsultaJSON.get("otalgia").toString().charAt(0));
			aleteoNasal = (hojaConsultaJSON.get("aleteoNasal").toString()
					.charAt(0));
			apnea = (hojaConsultaJSON.get("apnea").toString().charAt(0));
			respiracionRapida = (hojaConsultaJSON.get("respiracionRapida")
					.toString().charAt(0));
			quejidoEspiratorio = (hojaConsultaJSON.get("quejidoEspiratorio")
					.toString().charAt(0));
			estiradorReposo = (hojaConsultaJSON.get("estiradorReposo")
					.toString().charAt(0));
			tirajeSubcostal = (hojaConsultaJSON.get("tirajeSubcostal")
					.toString().charAt(0));
			sibilancias = (hojaConsultaJSON.get("sibilancias").toString()
					.charAt(0));
			crepitos = (hojaConsultaJSON.get("crepitos").toString().charAt(0));
			roncos = (hojaConsultaJSON.get("roncos").toString().charAt(0));
			otraFif = (hojaConsultaJSON.get("otraFif").toString().charAt(0));
			if(hojaConsultaJSON.containsKey("nuevaFif")) {
				nuevaFif = ((hojaConsultaJSON.get("nuevaFif").toString()));
			}
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(UtilHojaConsulta.respiratorioCompletada(hojaConsulta)) {
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				RespiratorioControlCambio cc = new RespiratorioControlCambio();
				cc.setAleteoNasal(aleteoNasal);
				cc.setApnea(apnea);
				cc.setCongestionNasal(congestionNasal);
				cc.setCrepitos(crepitos);
				cc.setEstiradorReposo(estiradorReposo);
				cc.setOtalgia(otalgia);
				cc.setOtraFif(otraFif);
				if(nuevaFif != null && !nuevaFif.isEmpty()) {
					cc.setNuevaFif(new SimpleDateFormat("dd/MM/yyyy").parse(nuevaFif));
				}
					
				cc.setQuejidoEspiratorio(quejidoEspiratorio);
				cc.setRespiracionRapida(respiracionRapida);
				cc.setRinorrea(rinorrea);
				cc.setRoncos(roncos);
				cc.setSibilancias(sibilancias);
				cc.setTirajeSubcostal(tirajeSubcostal);
				cc.setTos(tos);
				cc.setUsuario(usuarioLogiado);
				cc.setControlador("RespiratorioSintomasActivity");
				ctrlCambiosDA.guardarControlCambios(hojaConsulta, cc);
			}

			hojaConsulta.setTos(tos);
			hojaConsulta.setRinorrea(rinorrea);
			hojaConsulta.setCongestionNasal(congestionNasal);
			hojaConsulta.setOtalgia(otalgia);
			hojaConsulta.setAleteoNasal(aleteoNasal);
			hojaConsulta.setApnea(apnea);
			hojaConsulta.setRespiracionRapida(respiracionRapida);
			hojaConsulta.setQuejidoEspiratorio(quejidoEspiratorio);
			hojaConsulta.setEstiradorReposo(estiradorReposo);
			hojaConsulta.setTirajeSubcostal(tirajeSubcostal);
			hojaConsulta.setSibilancias(sibilancias);
			hojaConsulta.setCrepitos(crepitos);
			hojaConsulta.setRoncos(roncos);
			hojaConsulta.setOtraFif(otraFif);
			if(nuevaFif != null && !nuevaFif.isEmpty()) {
				hojaConsulta.setNuevaFif(new SimpleDateFormat("dd/MM/yyyy").parse(nuevaFif));
			}else{
				hojaConsulta.setNuevaFif(null);
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

	/***
	 * Metodo para guardar seccion Referencia
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarReferenciaSintomas(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			Character interconsultaPediatrica;
			Character referenciaHospital;
			Character referenciaDengue;
			Character referenciaIrag;
			Character referenciaChik;
			Character eti;
			Character irag;
			Character neumonia;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			interconsultaPediatrica = (hojaConsultaJSON.get(
					"interconsultaPediatrica").toString().charAt(0));
			referenciaHospital = (hojaConsultaJSON.get("referenciaHospital")
					.toString().charAt(0));
			referenciaDengue = (hojaConsultaJSON.get("referenciaDengue")
					.toString().charAt(0));
			referenciaIrag = (hojaConsultaJSON.get("referenciaIrag").toString()
					.charAt(0));
			referenciaChik = (hojaConsultaJSON.get("referenciaChik").toString()
					.charAt(0));
			eti = (hojaConsultaJSON.get("eti").toString().charAt(0));
			irag = (hojaConsultaJSON.get("irag").toString().charAt(0));
			neumonia = (hojaConsultaJSON.get("neumonia").toString().charAt(0));
			
			usuarioLogiado = (hojaConsultaJSON.get("usuarioLogiado").toString());

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(UtilHojaConsulta.referenciaCompletada(hojaConsulta)) {
				HojaConsulta hcNueva = new HojaConsulta();
				hcNueva.setInterconsultaPediatrica(interconsultaPediatrica);
				hcNueva.setReferenciaHospital(referenciaHospital);
				hcNueva.setReferenciaDengue(referenciaDengue);
				hcNueva.setReferenciaIrag(referenciaIrag);
				hcNueva.setReferenciaChik(referenciaChik);
				hcNueva.setEti(eti);
				hcNueva.setIrag(irag);
				hcNueva.setNeumonia(neumonia);
				
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				ctrlCambiosDA.guardarCtrlReferencia(hojaConsulta, hcNueva, usuarioLogiado);
			}
			
			hojaConsulta.setInterconsultaPediatrica(interconsultaPediatrica);
			hojaConsulta.setReferenciaHospital(referenciaHospital);
			hojaConsulta.setReferenciaDengue(referenciaDengue);
			hojaConsulta.setReferenciaIrag(referenciaIrag);
			hojaConsulta.setReferenciaChik(referenciaChik);
			hojaConsulta.setEti(eti);
			hojaConsulta.setIrag(irag);
			hojaConsulta.setNeumonia(neumonia);

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
	 * Metodo para guardar seccion Vacuna
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarVacunaSintomas(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			Character lactanciaMaterna;
			Character vacunasCompletas;
			Character vacunaInfluenza;
			String fechaVacuna = null;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			lactanciaMaterna = (hojaConsultaJSON.get("lactanciaMaterna")
					.toString().charAt(0));
			vacunasCompletas = (hojaConsultaJSON.get("vacunasCompletas")
					.toString().charAt(0));
			vacunaInfluenza = (hojaConsultaJSON.get("vacunaInfluenza")
					.toString().charAt(0));
			if(hojaConsultaJSON.containsKey("fechaVacuna")) {
				fechaVacuna = ((hojaConsultaJSON.get("fechaVacuna").toString()));
			}
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(UtilHojaConsulta.vacunaCompletada(hojaConsulta)) {
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				VacunaControlCambio cc = new VacunaControlCambio();
				cc.setLactanciaMaterna(lactanciaMaterna);
				cc.setVacunaInfluenza(vacunaInfluenza);
				cc.setVacunasCompletas(vacunasCompletas);
				cc.setUsuario(usuarioLogiado);
				if(fechaVacuna != null && !fechaVacuna.isEmpty()) {
					cc.setFechaVacuna(new SimpleDateFormat("dd/MM/yyyy").parse(fechaVacuna));
				}
				cc.setControlador("VacunasSintomasActivity");
				ctrlCambiosDA.guardarControlCambios(hojaConsulta, cc);
			}

			hojaConsulta.setLactanciaMaterna(lactanciaMaterna);
			hojaConsulta.setVacunasCompletas(vacunasCompletas);
			hojaConsulta.setVacunaInfluenza(vacunaInfluenza);
			if(fechaVacuna != null && !fechaVacuna.isEmpty()) {
				hojaConsulta.setFechaVacuna(new SimpleDateFormat("dd/MM/yyyy").parse(fechaVacuna));
			}else {
				hojaConsulta.setFechaVacuna(null);
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

	/***
	 * Metodo para validar consentimiento dengue
	 * @param codExpediente, Codigo Expediente
	 */
	@Override
	public String validarNoConsentimientoDengue(Integer codExpediente) {
		String result = null;
		try {
			List oLista = new LinkedList(); // Listado final para el resultado
			Map fila = null; // Objeto para cada registro recuperado

			String sql = "select c " + " from ConsEstudios c "
					+ " where c.codigoExpediente = :codExpediente "
					+ " and c.codigoConsentimiento = 1";

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			query.setParameter("codExpediente", codExpediente);
			query.setMaxResults(1);

			ConsEstudios hojaConsultaPaciente = (ConsEstudios) query.uniqueResult();

			if (hojaConsultaPaciente != null && hojaConsultaPaciente.getSecConsEstudios() > 0) {

				fila = new HashMap();
				fila.put("consentimiento", true);

				oLista.add(fila);

				// Construir la lista a una estructura JSON
				result = UtilResultado.parserResultado(oLista, "",
						UtilResultado.OK);
			} else {
				fila = new HashMap();
				fila.put("consentimiento", false);

				oLista.add(fila);

				result = UtilResultado.parserResultado(oLista, "",
						UtilResultado.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null,
					Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}

	/***
	 * Metodo para guardar seccion Categoria
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarCategoriaSintomas(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			Integer saturaciono2;
			//Double imc;
			String categoria = null;
			Character cambioCategoria = null;
			Character manifestacionHemorragica;
			Character pruebaTorniquetePositiva;
			Character petequia10Pt;
			Character petequia20Pt;
			Character pielExtremidadesFrias;
			Character palidezEnExtremidades;
			Character epistaxis;
			Character gingivorragia;
			Character petequiasEspontaneas;
			Character llenadoCapilar2seg;
			Character cianosis;
			Double linfocitosaAtipicos;
			String fechaLinfocitos;
			Character hipermenorrea;
			Character hematemesis;
			Character melena;
			Character hemoconc;
			Short hemoconcentracion;
			Character hospitalizado;
			String hospitalizadoEspecificar;
			Character transfusionSangre;
			String transfusionEspecificar;
			Character tomandoMedicamento;
			String medicamentoEspecificar;
			Character medicamentoDistinto;
			String medicamentoDistEspecificar;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();

			saturaciono2 = hojaConsultaJSON.containsKey("saturaciono2") ? ((Number) hojaConsultaJSON
					.get("saturaciono2")).intValue() : -1;
			//imc = ((Number) hojaConsultaJSON.get("imc")).doubleValue();
			if(hojaConsultaJSON.containsKey("categoria")) {
				categoria = (hojaConsultaJSON.get("categoria").toString());
			}
			if(hojaConsultaJSON.containsKey("cambioCategoria")) {
				cambioCategoria = (hojaConsultaJSON.get("cambioCategoria").toString().charAt(0));
			}

			manifestacionHemorragica = hojaConsultaJSON.containsKey("manifestacionHemorragica") ? 
					(hojaConsultaJSON.get("manifestacionHemorragica").toString().charAt(0)) : 
						null;
					
			pruebaTorniquetePositiva = hojaConsultaJSON.containsKey("pruebaTorniquetePositiva") ? 
					(hojaConsultaJSON.get("pruebaTorniquetePositiva").toString().charAt(0)) : 
						null;
					
			petequia10Pt = hojaConsultaJSON.containsKey("petequia10Pt") ? 
					(hojaConsultaJSON.get("petequia10Pt").toString().charAt(0)) : null;
					
			petequia20Pt = hojaConsultaJSON.containsKey("petequia20Pt") ? (hojaConsultaJSON
					.get("petequia20Pt").toString().charAt(0)) : null;
			pielExtremidadesFrias = hojaConsultaJSON
					.containsKey("pielExtremidadesFrias") ? (hojaConsultaJSON
					.get("pielExtremidadesFrias").toString().charAt(0)) : null;
			palidezEnExtremidades = hojaConsultaJSON
					.containsKey("palidezEnExtremidades") ? (hojaConsultaJSON
					.get("palidezEnExtremidades").toString().charAt(0)) : null;
			epistaxis = hojaConsultaJSON.containsKey("epistaxis") ? (hojaConsultaJSON
					.get("epistaxis").toString().charAt(0)) : null;
			gingivorragia = hojaConsultaJSON.containsKey("gingivorragia") ? (hojaConsultaJSON
					.get("gingivorragia").toString().charAt(0)) : null;
			petequiasEspontaneas = hojaConsultaJSON
					.containsKey("petequiasEspontaneas") ? (hojaConsultaJSON
					.get("petequiasEspontaneas").toString().charAt(0)) : null;
			llenadoCapilar2seg = hojaConsultaJSON
					.containsKey("llenadoCapilar2seg") ? (hojaConsultaJSON.get(
					"llenadoCapilar2seg").toString().charAt(0)) : null;
			cianosis = hojaConsultaJSON.containsKey("cianosis") ? (hojaConsultaJSON
					.get("cianosis").toString().charAt(0)) : null;
			linfocitosaAtipicos = hojaConsultaJSON
					.containsKey("linfocitosaAtipicos") ? ((Number) hojaConsultaJSON
					.get("linfocitosaAtipicos")).doubleValue() : null;
			fechaLinfocitos = hojaConsultaJSON.containsKey("fechaLinfocitos") ? ((hojaConsultaJSON
					.get("fechaLinfocitos").toString())) : null;
			hipermenorrea = hojaConsultaJSON.containsKey("hipermenorrea") ? (hojaConsultaJSON
					.get("hipermenorrea").toString().charAt(0)) : null;
			hematemesis = hojaConsultaJSON.containsKey("hematemesis") ? (hojaConsultaJSON
					.get("hematemesis").toString().charAt(0)) : null;
			melena = hojaConsultaJSON.containsKey("melena") ? (hojaConsultaJSON
					.get("melena").toString().charAt(0)) : null;
			hemoconc = hojaConsultaJSON.containsKey("hemoconc") ? (hojaConsultaJSON
					.get("hemoconc").toString().charAt(0)) : null;
			hemoconcentracion = hojaConsultaJSON
					.containsKey("hemoconcentracion") ? ((Number) hojaConsultaJSON
					.get("hemoconcentracion")).shortValue() : null;

			hospitalizado = (hojaConsultaJSON.containsKey("hospitalizado")) ?
					hojaConsultaJSON.get("hospitalizado").toString().charAt(0) : null;
			hospitalizadoEspecificar = hojaConsultaJSON
					.containsKey("hospitalizadoEspecificar") ? ((hojaConsultaJSON
					.get("hospitalizadoEspecificar").toString())) : null;
					
			transfusionSangre = (hojaConsultaJSON.containsKey("transfusionSangre")) ? 
					hojaConsultaJSON.get("transfusionSangre").toString().charAt(0) : null;
			transfusionEspecificar = hojaConsultaJSON
					.containsKey("transfusionEspecificar") ? ((hojaConsultaJSON
					.get("transfusionEspecificar").toString())) : null;
					
			tomandoMedicamento = (hojaConsultaJSON.containsKey("tomandoMedicamento")) ? 
					hojaConsultaJSON.get("tomandoMedicamento").toString().charAt(0) : null;
			medicamentoEspecificar = hojaConsultaJSON
					.containsKey("medicamentoEspecificar") ? ((hojaConsultaJSON
					.get("medicamentoEspecificar").toString())) : null;
					
			medicamentoDistinto = (hojaConsultaJSON.containsKey("medicamentoDistinto")) ? 
					hojaConsultaJSON.get("medicamentoDistinto").toString().charAt(0) : null;
			medicamentoDistEspecificar = hojaConsultaJSON
					.containsKey("medicamentoDistEspecificar") ? ((hojaConsultaJSON
					.get("medicamentoDistEspecificar").toString())) : null;
					
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(UtilHojaConsulta.categoriaCompletada(hojaConsulta)) {
				HojaConsulta hcNueva = new HojaConsulta();
				if (saturaciono2 != -1)
					hcNueva.setSaturaciono2(saturaciono2.shortValue());

				//hojaConsulta.setImc(BigDecimal.valueOf(imc.doubleValue()));
				hcNueva.setCategoria(categoria);
				hcNueva.setCambioCategoria(cambioCategoria);

				if (manifestacionHemorragica != null)
					hcNueva.setManifestacionHemorragica(manifestacionHemorragica);

				if (pruebaTorniquetePositiva != null)
					hcNueva.setPruebaTorniquetePositiva(pruebaTorniquetePositiva);

				if (petequia10Pt != null)
					hcNueva.setPetequia10Pt(petequia10Pt);

				if (petequia20Pt != null)
					hcNueva.setPetequia20Pt(petequia20Pt);

				if (pielExtremidadesFrias != null)
					hcNueva.setPielExtremidadesFrias(pielExtremidadesFrias);

				if (palidezEnExtremidades != null)
					hcNueva.setPalidezEnExtremidades(palidezEnExtremidades);

				if (epistaxis != null)
					hcNueva.setEpistaxis(epistaxis);

				if (gingivorragia != null)
					hcNueva.setGingivorragia(gingivorragia);

				if (petequiasEspontaneas != null)
					hcNueva.setPetequiasEspontaneas(petequiasEspontaneas);

				if (llenadoCapilar2seg != null)
					hcNueva.setLlenadoCapilar2seg(llenadoCapilar2seg);

				if (cianosis != null)
					hcNueva.setCianosis(cianosis);

				if (linfocitosaAtipicos != null)
					hcNueva.setLinfocitosaAtipicos(BigDecimal
							.valueOf(linfocitosaAtipicos.doubleValue()));

				if (fechaLinfocitos != null && !fechaLinfocitos.isEmpty())
					hcNueva.setFechaLinfocitos(new SimpleDateFormat(
							"dd/MM/yyyy").parse(fechaLinfocitos));

				if (hipermenorrea != null)
					hcNueva.setHipermenorrea(hipermenorrea);

				if (hematemesis != null)
					hcNueva.setHematemesis(hematemesis);

				if (melena != null)
					hcNueva.setMelena(melena);

				if (hemoconc != null)
					hcNueva.setHemoconc(hemoconc);
				
				if (hemoconcentracion != null)
					hcNueva.setHemoconcentracion(hemoconcentracion);

				if(hospitalizado != null)
					hcNueva.setHospitalizado(hospitalizado);
				if (hospitalizadoEspecificar != null)
					hcNueva.setHospitalizadoEspecificar(hospitalizadoEspecificar);

				if(transfusionSangre != null)
					hcNueva.setTransfusionSangre(transfusionSangre);
				if (transfusionEspecificar != null)
					hcNueva.setTransfusionEspecificar(transfusionEspecificar);

				if(tomandoMedicamento != null)
					hcNueva.setTomandoMedicamento(tomandoMedicamento);
				if (medicamentoEspecificar != null)
					hcNueva.setMedicamentoEspecificar(medicamentoEspecificar);

				if(medicamentoDistinto != null)
					hcNueva.setMedicamentoDistinto(medicamentoDistinto);
				
				if (medicamentoDistEspecificar != null)
					hcNueva.setMedicamentoDistEspecificar(medicamentoDistEspecificar);
				
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				ctrlCambiosDA.guardarCtrlCategoria(hojaConsulta, hcNueva, usuarioLogiado);
			}

			if (saturaciono2 != -1){
				hojaConsulta.setSaturaciono2(saturaciono2.shortValue());
			}else{
				hojaConsulta.setSaturaciono2(null);
			}

			//hojaConsulta.setImc(BigDecimal.valueOf(imc.doubleValue()));
			hojaConsulta.setCategoria(categoria);
			hojaConsulta.setCambioCategoria(cambioCategoria);

			if (manifestacionHemorragica != null)
				hojaConsulta
						.setManifestacionHemorragica(manifestacionHemorragica);

			if (pruebaTorniquetePositiva != null)
				hojaConsulta
						.setPruebaTorniquetePositiva(pruebaTorniquetePositiva);

			if (petequia10Pt != null)
				hojaConsulta.setPetequia10Pt(petequia10Pt);

			if (petequia20Pt != null)
				hojaConsulta.setPetequia20Pt(petequia20Pt);

			if (pielExtremidadesFrias != null)
				hojaConsulta.setPielExtremidadesFrias(pielExtremidadesFrias);

			if (palidezEnExtremidades != null)
				hojaConsulta.setPalidezEnExtremidades(palidezEnExtremidades);

			if (epistaxis != null)
				hojaConsulta.setEpistaxis(epistaxis);

			if (gingivorragia != null)
				hojaConsulta.setGingivorragia(gingivorragia);

			if (petequiasEspontaneas != null)
				hojaConsulta.setPetequiasEspontaneas(petequiasEspontaneas);

			if (llenadoCapilar2seg != null)
				hojaConsulta.setLlenadoCapilar2seg(llenadoCapilar2seg);

			if (cianosis != null)
				hojaConsulta.setCianosis(cianosis);

			if (linfocitosaAtipicos != null)
				hojaConsulta.setLinfocitosaAtipicos(BigDecimal
						.valueOf(linfocitosaAtipicos.doubleValue()));

			if (fechaLinfocitos != null && !fechaLinfocitos.isEmpty())
				hojaConsulta.setFechaLinfocitos(new SimpleDateFormat(
						"dd/MM/yyyy").parse(fechaLinfocitos));

			if (hipermenorrea != null)
				hojaConsulta.setHipermenorrea(hipermenorrea);

			if (hematemesis != null)
				hojaConsulta.setHematemesis(hematemesis);

			if (melena != null)
				hojaConsulta.setMelena(melena);

			if (hemoconc != null)
				hojaConsulta.setHemoconc(hemoconc);
			
			if (hemoconcentracion != null)
				hojaConsulta.setHemoconcentracion(hemoconcentracion);

			if(hospitalizado != null)
				hojaConsulta.setHospitalizado(hospitalizado);
			
			if (hospitalizadoEspecificar != null){
				hojaConsulta.setHospitalizadoEspecificar(hospitalizadoEspecificar);
			} else{
				hojaConsulta.setHospitalizadoEspecificar(null);
			}

			if(transfusionSangre != null)
				hojaConsulta.setTransfusionSangre(transfusionSangre);
			
			if (transfusionEspecificar != null){
				hojaConsulta.setTransfusionEspecificar(transfusionEspecificar);
			} else {
				hojaConsulta.setTransfusionEspecificar(null);
			}

			if(tomandoMedicamento != null)
				hojaConsulta.setTomandoMedicamento(tomandoMedicamento);
			
			if (medicamentoEspecificar != null){
				hojaConsulta.setMedicamentoEspecificar(medicamentoEspecificar);
			} else{
				hojaConsulta.setMedicamentoEspecificar(null);
			}

			if(medicamentoDistinto != null)
				hojaConsulta.setMedicamentoDistinto(medicamentoDistinto);
			
			if (medicamentoDistEspecificar != null){
				hojaConsulta.setMedicamentoDistEspecificar(medicamentoDistEspecificar);
			} else{
				hojaConsulta.setMedicamentoDistEspecificar(null);
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


	/***
	 * Metodo para obtener datos Cabecera para Tab Examenes
	 * @param codExpediente, Codigo Expediente
	 * @param secHojaConsulta, Identificador Hoja consulta
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getDatosCabeceraExamenes(Integer codExpediente,
			Integer secHojaConsulta) {
		String result = null;
		try {
			List oLista = new LinkedList(); // Listado final para el resultado
			Map fila = null; // Objeto para cada registro recuperado

			String sql = "select h.expedienteFisico, p.edad, p.sexo, h.pesoKg, " + 
					" h.tallaCm, h.temperaturac, " + 
					" to_char(p.fechaNac, 'yyyyMMdd'), " + 
					" to_char(h.fechaConsulta, 'yyyyMMdd HH:MI:SS am'), " +
					" h.hora " +
					" from HojaConsulta h, Paciente p "
					+ " where h.codExpediente = p.codExpediente "
					+ " and h.secHojaConsulta = :secHojaConsulta ";

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			query.setParameter("secHojaConsulta", secHojaConsulta);

			Object[] hojaConsultaPaciente = (Object[]) query.uniqueResult();

			if (hojaConsultaPaciente != null && hojaConsultaPaciente.length > 0) {

				sql = "select ec " + " from ConsEstudios c, EstudioCatalogo ec " + 
						" where c.codigoConsentimiento = ec.codEstudio"  + 
						" and c.codigoExpediente = :codExpediente " + 
						" and c.retirado != '1' " +
						" group by ec.codEstudio, ec.descEstudio";

				query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

				query.setParameter("codExpediente", codExpediente);

				List<EstudioCatalogo> lstConsEstudios = (List<EstudioCatalogo>) query
						.list();
				StringBuffer codigosEstudios = new StringBuffer();

				for (EstudioCatalogo estudioCatalogo : lstConsEstudios) {
					codigosEstudios.append(
							estudioCatalogo.getDescEstudio()).append(",");
				}

				fila = new HashMap();
				fila.put("expedienteFisico", hojaConsultaPaciente[0]);
				fila.put(
						"estudiosParticipante",
						codigosEstudios != null
								&& !codigosEstudios.toString().isEmpty() ? (codigosEstudios
								.substring(0, (codigosEstudios.length() - 1)))
								: "");
				fila.put("edad", hojaConsultaPaciente[1]);
				fila.put("sexo", hojaConsultaPaciente[2]);
				fila.put("pesoKg", hojaConsultaPaciente[3]);
				fila.put("tallaCm", hojaConsultaPaciente[4]);
				fila.put("temperaturac", hojaConsultaPaciente[5]);
				fila.put("fechaNacimiento", hojaConsultaPaciente[6]);
				fila.put("fechaConsulta", hojaConsultaPaciente[7]);
				fila.put("horaConsulta", hojaConsultaPaciente[8]);

				oLista.add(fila);

				// Construir la lista a una estructura JSON
				result = UtilResultado.parserResultado(oLista, "",
						UtilResultado.OK);
			} else {
				result = UtilResultado.parserResultado(null, Mensajes.NO_DATOS,
						UtilResultado.INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null,
					Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}
	
	/***
	 * valida si los examenes fueron marcados
	 */	
	private boolean validarTodoExamenMaracdo(HojaConsulta hojaConsulta) {
		if( hojaConsulta.getBhc() != null
				&& hojaConsulta.getSerologiaDengue() != null
				&& hojaConsulta.getSerologiaChik() != null
				&& hojaConsulta.getGotaGruesa() != null
				&& hojaConsulta.getExtendidoPeriferico() != null
				&& hojaConsulta.getEgo() != null
				&& hojaConsulta.getEgh() != null
				&& hojaConsulta.getCitologiaFecal() != null 
				&& hojaConsulta.getFactorReumatoideo() != null
				&& hojaConsulta.getAlbumina() != null
				&& hojaConsulta.getAstAlt() != null
				&& hojaConsulta.getBilirrubinas() != null
				&& hojaConsulta.getCpk() != null
				&& hojaConsulta.getColesterol() != null
				&& hojaConsulta.getInfluenza() != null) {
			return true;
		}
		
		return false;
	}

	/***
	 * Metodo para guardar los tipos de examenes a realizar
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarExamenes(String paramHojaConsulta) {
		String result = null;
		try {
			Integer secHojaConsulta;
			String fechaOrdenLaboratorio;
			Character bhc;
			Character serologiaDengue;
			Character serologiaChik;
			Character gotaGruesa;
			Character extendidoPeriferico;
			Character ego;
			Character egh;
			Character citologiaFecal;
			Character factorReumatoideo;
			Character albumina;
			Character astAlt;
			Character bilirrubinas;
			Character cpk;
			Character colesterol;
			Character influenza;
			Character oel;
			Short usuarioMedico;
			// String otroExamenLab;

			Integer secOrdenLaboratorio;
			String examen;
			char estado;

			String sql = "select max(o.numOrdenLaboratorio) "
					+ " from OrdenLaboratorio  o ";

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			Integer maxNumOrdenLab = (query.uniqueResult() == null) ? 1
					: ((Integer) query.uniqueResult()).intValue() + 1;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			fechaOrdenLaboratorio = (hojaConsultaJSON
					.get("fechaOrdenLaboratorio").toString());
			bhc = (hojaConsultaJSON.get("bhc").toString().charAt(0));
			serologiaDengue = (hojaConsultaJSON.get("serologiaDengue")
					.toString().charAt(0));
			serologiaChik = (hojaConsultaJSON.get("serologiaChik").toString()
					.charAt(0));
			gotaGruesa = (hojaConsultaJSON.get("gotaGruesa").toString()
					.charAt(0));
			extendidoPeriferico = (hojaConsultaJSON.get("extendidoPeriferico")
					.toString().charAt(0));
			ego = (hojaConsultaJSON.get("ego").toString().charAt(0));
			egh = (hojaConsultaJSON.get("egh").toString().charAt(0));
			citologiaFecal = (hojaConsultaJSON.get("citologiaFecal").toString()
					.charAt(0));
			factorReumatoideo = (hojaConsultaJSON.get("factorReumatoideo")
					.toString().charAt(0));
			albumina = (hojaConsultaJSON.get("albumina").toString().charAt(0));
			astAlt = (hojaConsultaJSON.get("astAlt").toString().charAt(0));
			bilirrubinas = (hojaConsultaJSON.get("bilirrubinas").toString()
					.charAt(0));
			cpk = (hojaConsultaJSON.get("cpk").toString().charAt(0));
			colesterol = (hojaConsultaJSON.get("colesterol").toString()
					.charAt(0));
			influenza = (hojaConsultaJSON.get("influenza").toString().charAt(0));
			oel = (hojaConsultaJSON.get("oel").toString().charAt(0));
			usuarioMedico = (((Number) hojaConsultaJSON
					.get("usuarioMedico")).shortValue());
			// otroExamenLab =
			// (hojaConsultaJSON.get("otroExamenLab").toString());

			query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			// HojaConsulta hojaConsulta = ((HojaConsulta)
			// query.uniqueResult());
			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			//Guardar control de cambios
			if(validarTodoExamenMaracdo(hojaConsulta)){
				ExamenesControlCambios ecc = new ExamenesControlCambios();
				ecc.setBhc(bhc);
				ecc.setSerologiaDengue(serologiaDengue);
				ecc.setSerologiaChik(serologiaChik);
				ecc.setGotaGruesa(gotaGruesa);
				ecc.setExtendidoPeriferico(extendidoPeriferico);
				ecc.setEgo(ego);
				ecc.setEgh(egh);
				ecc.setCitologiaFecal(citologiaFecal);
				ecc.setFactorReumatoideo(factorReumatoideo);
				ecc.setAlbumina(albumina);
				ecc.setAstAlt(astAlt);
				ecc.setBilirrubinas(bilirrubinas);
				ecc.setCpk(cpk);
				ecc.setColesterol(colesterol);
				ecc.setInfluenza(influenza);
				ecc.setOel(oel);
				ecc.setUsuario(hojaConsultaJSON.get("usuario").toString());
				ecc.setControlador(hojaConsultaJSON.get("controlador").toString());
				
				//Llamado al control de cambios
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				ctrlCambiosDA.guardarControlCambios(hojaConsulta, ecc);
			}
			

			if (hojaConsulta.getNumOrdenLaboratorio() != null) {
				HIBERNATE_RESOURCE.begin();
		
				if (hojaConsulta.getSerologiaDengue() == null
						|| hojaConsulta.getSerologiaDengue().toString()
								.compareTo("0") != 0) {
					if (serologiaDengue.toString().compareTo("0") == 0) {
						OrdenLaboratorio ordenLab = new OrdenLaboratorio();
						ordenLab.setHojaConsulta(hojaConsulta);
						ordenLab.setNumOrdenLaboratorio(hojaConsulta
								.getNumOrdenLaboratorio());
						ordenLab.setExamen("Serologia Dengue");
						ordenLab.setEstado('0');
						ordenLab.setTomaMx('0');
						HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
					}
				}
				if (hojaConsulta.getSerologiaChik() == null
						|| hojaConsulta.getSerologiaChik().toString()
								.compareTo("0") != 0) {
					if (serologiaChik.toString().compareTo("0") == 0) {
						OrdenLaboratorio ordenLab = new OrdenLaboratorio();
						ordenLab.setHojaConsulta(hojaConsulta);
						ordenLab.setNumOrdenLaboratorio(hojaConsulta
								.getNumOrdenLaboratorio());
						ordenLab.setExamen("Serologia CHICK");
						ordenLab.setEstado('0');
						ordenLab.setTomaMx('0');
						HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
					}
				}
				if (hojaConsulta.getGotaGruesa() == null
						|| hojaConsulta.getGotaGruesa().toString()
								.compareTo("0") != 0) {
					if (gotaGruesa.toString().compareTo("0") == 0) {
						OrdenLaboratorio ordenLab = new OrdenLaboratorio();
						ordenLab.setHojaConsulta(hojaConsulta);
						ordenLab.setNumOrdenLaboratorio(hojaConsulta
								.getNumOrdenLaboratorio());
						ordenLab.setExamen("Gota Gruesa");
						ordenLab.setEstado('0');
						ordenLab.setTomaMx('0');
						HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
					}
				}
				if (hojaConsulta.getExtendidoPeriferico() == null
						|| hojaConsulta.getExtendidoPeriferico().toString()
								.compareTo("0") != 0) {
					if (extendidoPeriferico.toString().compareTo("0") == 0) {
						OrdenLaboratorio ordenLab = new OrdenLaboratorio();
						ordenLab.setHojaConsulta(hojaConsulta);
						ordenLab.setNumOrdenLaboratorio(hojaConsulta
								.getNumOrdenLaboratorio());
						ordenLab.setExamen("Extendido Periferico");
						ordenLab.setEstado('0');
						ordenLab.setTomaMx('0');
						HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
					}
				}
				if (hojaConsulta.getEgo() == null
						|| hojaConsulta.getEgo().toString().compareTo("0") != 0) {
					if (ego.toString().compareTo("0") == 0) {
						OrdenLaboratorio ordenLab = new OrdenLaboratorio();
						ordenLab.setHojaConsulta(hojaConsulta);
						ordenLab.setNumOrdenLaboratorio(hojaConsulta
								.getNumOrdenLaboratorio());
						ordenLab.setExamen("EGO");
						ordenLab.setEstado('0');
						ordenLab.setTomaMx('0');
						HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
					}
				}
				if (hojaConsulta.getEgh() == null
						|| hojaConsulta.getEgh().toString().compareTo("0") != 0) {
					if (egh.toString().compareTo("0") == 0) {
						OrdenLaboratorio ordenLab = new OrdenLaboratorio();
						ordenLab.setHojaConsulta(hojaConsulta);
						ordenLab.setNumOrdenLaboratorio(hojaConsulta
								.getNumOrdenLaboratorio());
						ordenLab.setExamen("EGH");
						ordenLab.setEstado('0');
						ordenLab.setTomaMx('0');
						HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
					}
				}		
				
				if (hojaConsulta.getCitologiaFecal() == null
						|| hojaConsulta.getCitologiaFecal().toString().compareTo("0") != 0) {
					if (citologiaFecal.toString().compareTo("0") == 0) {
						OrdenLaboratorio ordenLab = new OrdenLaboratorio();
						ordenLab.setHojaConsulta(hojaConsulta);
						ordenLab.setNumOrdenLaboratorio(hojaConsulta
								.getNumOrdenLaboratorio());
						ordenLab.setExamen("Citologia Fecal");
						ordenLab.setEstado('0');
						ordenLab.setTomaMx('0');
						HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
					}
				}	
				
				if (hojaConsulta.getInfluenza() == null
						|| hojaConsulta.getInfluenza().toString()
								.compareTo("0") != 0) {
					if (influenza.toString().compareTo("0") == 0) {
						OrdenLaboratorio ordenLab = new OrdenLaboratorio();
						ordenLab.setHojaConsulta(hojaConsulta);
						ordenLab.setNumOrdenLaboratorio(hojaConsulta
								.getNumOrdenLaboratorio());
						ordenLab.setExamen("Influenza");
						ordenLab.setEstado('0');
						ordenLab.setTomaMx('0');
						HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
					}
				}				

				hojaConsulta.setFechaOrdenLaboratorio(new SimpleDateFormat(
						"yyyyMMdd hh:mm:ss a", Locale.ENGLISH).parse(fechaOrdenLaboratorio));
				hojaConsulta.setBhc(bhc);
				hojaConsulta.setSerologiaDengue(serologiaDengue);
				hojaConsulta.setSerologiaChik(serologiaChik);
				hojaConsulta.setGotaGruesa(gotaGruesa);
				hojaConsulta.setExtendidoPeriferico(extendidoPeriferico);
				hojaConsulta.setEgo(ego);
				hojaConsulta.setEgh(egh);
				hojaConsulta.setCitologiaFecal(citologiaFecal);
				hojaConsulta.setFactorReumatoideo(factorReumatoideo);
				hojaConsulta.setAlbumina(albumina);
				hojaConsulta.setAstAlt(astAlt);
				hojaConsulta.setBilirrubinas(bilirrubinas);
				hojaConsulta.setCpk(cpk);
				hojaConsulta.setColesterol(colesterol);
				hojaConsulta.setInfluenza(influenza);
				hojaConsulta.setOel(oel);
				
				if(hojaConsulta.getUsuarioMedico() != null ) {
					if(hojaConsulta.getUsuarioMedico() != usuarioMedico) {
						
						hojaConsulta.setMedicoCambioTurno(usuarioMedico);
					}
				} else {
					hojaConsulta.setUsuarioMedico(usuarioMedico);
				}
				// hojaConsulta.setOtroExamenLab(otroExamenLab);
				hojaConsulta.setEstado('4');

				HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);

				HIBERNATE_RESOURCE.commit();

			} else {

				
				hojaConsulta.setFechaOrdenLaboratorio(new SimpleDateFormat(
						"yyyyMMdd hh:mm:ss a", Locale.ENGLISH).parse(fechaOrdenLaboratorio));
				hojaConsulta.setBhc(bhc);
				hojaConsulta.setSerologiaDengue(serologiaDengue);
				hojaConsulta.setSerologiaChik(serologiaChik);
				hojaConsulta.setGotaGruesa(gotaGruesa);
				hojaConsulta.setExtendidoPeriferico(extendidoPeriferico);
				hojaConsulta.setEgo(ego);
				hojaConsulta.setEgh(egh);
				hojaConsulta.setCitologiaFecal(citologiaFecal);
				hojaConsulta.setFactorReumatoideo(factorReumatoideo);
				hojaConsulta.setAlbumina(albumina);
				hojaConsulta.setAstAlt(astAlt);
				hojaConsulta.setBilirrubinas(bilirrubinas);
				hojaConsulta.setCpk(cpk);
				hojaConsulta.setColesterol(colesterol);
				hojaConsulta.setInfluenza(influenza);
				hojaConsulta.setOel(oel);
				
				if(hojaConsulta.getUsuarioMedico() != null ) {
					if(hojaConsulta.getUsuarioMedico() != usuarioMedico) {
						
						hojaConsulta.setMedicoCambioTurno(usuarioMedico);
					}
				} else {
					hojaConsulta.setUsuarioMedico(usuarioMedico);
				}
				// hojaConsulta.setOtroExamenLab(otroExamenLab);
				hojaConsulta.setEstado('4');

				HIBERNATE_RESOURCE.begin();				

				//Bandera para actualizar el número de orden de laboratorio
				boolean agregaExamen = false; 
				if (serologiaDengue.toString().compareTo("0") == 0) {
					agregaExamen = true;
					OrdenLaboratorio ordenLab = new OrdenLaboratorio();
					ordenLab.setHojaConsulta(hojaConsulta);
					ordenLab.setNumOrdenLaboratorio(maxNumOrdenLab);
					ordenLab.setExamen("Serologia Dengue");
					ordenLab.setEstado('0');
					ordenLab.setTomaMx('0');
					HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
				}
				if (serologiaChik.toString().compareTo("0") == 0) {
					agregaExamen = true;
					OrdenLaboratorio ordenLab = new OrdenLaboratorio();
					ordenLab.setHojaConsulta(hojaConsulta);
					ordenLab.setNumOrdenLaboratorio(maxNumOrdenLab);
					ordenLab.setExamen("Serologia CHICK");
					ordenLab.setEstado('0');
					ordenLab.setTomaMx('0');
					HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
				}
				if (gotaGruesa.toString().compareTo("0") == 0) {
					agregaExamen = true;
					OrdenLaboratorio ordenLab = new OrdenLaboratorio();
					ordenLab.setHojaConsulta(hojaConsulta);
					ordenLab.setNumOrdenLaboratorio(maxNumOrdenLab);
					ordenLab.setExamen("Gota Gruesa");
					ordenLab.setEstado('0');
					ordenLab.setTomaMx('0');
					HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
				}
				if (extendidoPeriferico.toString().compareTo("0") == 0) {
					agregaExamen = true;
					OrdenLaboratorio ordenLab = new OrdenLaboratorio();
					ordenLab.setHojaConsulta(hojaConsulta);
					ordenLab.setNumOrdenLaboratorio(maxNumOrdenLab);
					ordenLab.setExamen("Extendido Periferico");
					ordenLab.setEstado('0');
					ordenLab.setTomaMx('0');
					HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
				}
				if (ego.toString().compareTo("0") == 0) {
					agregaExamen = true;
					OrdenLaboratorio ordenLab = new OrdenLaboratorio();
					ordenLab.setHojaConsulta(hojaConsulta);
					ordenLab.setNumOrdenLaboratorio(maxNumOrdenLab);
					ordenLab.setExamen("EGO");
					ordenLab.setEstado('0');
					ordenLab.setTomaMx('0');
					HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
				}
				if (egh.toString().compareTo("0") == 0) {
					agregaExamen = true;
					OrdenLaboratorio ordenLab = new OrdenLaboratorio();
					ordenLab.setHojaConsulta(hojaConsulta);
					ordenLab.setNumOrdenLaboratorio(maxNumOrdenLab);
					ordenLab.setExamen("EGH");
					ordenLab.setEstado('0');
					ordenLab.setTomaMx('0');
					HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
				}
				if (citologiaFecal.toString().compareTo("0") == 0) {
					agregaExamen = true;
					OrdenLaboratorio ordenLab = new OrdenLaboratorio();
					ordenLab.setHojaConsulta(hojaConsulta);
					ordenLab.setNumOrdenLaboratorio(maxNumOrdenLab);
					ordenLab.setExamen("Citologia Fecal");
					ordenLab.setEstado('0');
					ordenLab.setTomaMx('0');
					HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
				}	
				
				if (influenza.toString().compareTo("0") == 0) {
					agregaExamen = true;
					OrdenLaboratorio ordenLab = new OrdenLaboratorio();
					ordenLab.setHojaConsulta(hojaConsulta);
					ordenLab.setNumOrdenLaboratorio(maxNumOrdenLab);
					ordenLab.setExamen("Influenza");
					ordenLab.setEstado('0');
					ordenLab.setTomaMx('0');
					HIBERNATE_RESOURCE.getSession().saveOrUpdate(ordenLab);
				}
				
				/*Se generara un numero de orden de laboratorio en la tabla hoja consulta 
				siempre y cuando uno de estos examenes
				sea marcado como si*/
				
				if(agregaExamen){					
					hojaConsulta.setNumOrdenLaboratorio(maxNumOrdenLab);					
				}
				//Actualizando la hoja de consulta
				HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);
				
				HIBERNATE_RESOURCE.commit();
			}
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
	 * Metodo para buscar Examenes
	 * @param paramSecHojaConsulta, JSON
	 */
	@Override
	public String buscarExamenes(Integer paramSecHojaConsulta) {
		String result = null;
		try {
			List oLista = new LinkedList();
			JSONObject resultado = new JSONObject();
			Object obj = null;
			JSONArray array = new JSONArray();
			List lst = new LinkedList();
			Map fila = new HashMap();

			String sql = "select h.gotaGruesa, h.serologiaDengue, h.serologiaChik, "
					+ " h.extendidoPeriferico, h.ego, h.egh, "
					+ " h.influenza, h.numOrdenLaboratorio "
					+ " from HojaConsulta h "
					+ " where h.secHojaConsulta = :secHojaConsulta ";

			Query q = HIBERNATE_RESOURCE.getSession().createQuery(sql);
			q.setParameter("secHojaConsulta", paramSecHojaConsulta);

			Object[] objLista = (Object[]) q.uniqueResult();

			if (objLista != null && objLista.length > 0) {

				if (objLista[0] != null) {
					fila.put("gotaGruesa", objLista[0].toString());
				}
				if (objLista[1] != null) {
					fila.put("serologiaDengue", objLista[1].toString());
				}
				if (objLista[2] != null) {
					fila.put("serologiaChik", objLista[2].toString());
				}
				if (objLista[3] != null) {
					fila.put("extendidoPeriferico", objLista[3].toString());
				}
				if (objLista[4] != null) {
					fila.put("ego", objLista[4].toString());
				}
				if (objLista[5] != null) {
					fila.put("egh", objLista[5].toString());
				}
				if (objLista[6] != null) {
					fila.put("influenza", objLista[6].toString());
				}
				if (objLista[7] != null) {
					fila.put("numOrdenLaboratorio",
							((Integer) (objLista[7])).intValue());
				}

				lst.add(fila);

				String resultadoHojaConsulta = JSONValue.toJSONString(lst);
				obj = JSONValue.parse(resultadoHojaConsulta);
				array = (JSONArray) obj;
				resultado.put("hojaConsulta", array);

				if (objLista[0] != null) {
					if (objLista[0].toString().compareTo("0") == 0) {

						obj = new Object();
						array = new JSONArray();
						lst = new LinkedList();

						sql = "select mr "
								+ " from MalariaResultados mr, OrdenLaboratorio o "
								+ " where mr.secOrdenLaboratorio = o.secOrdenLaboratorio "
								+ /* utilizando producto cruz */
								" and o.numOrdenLaboratorio = :numOrdenLaboratorio";

						q = HIBERNATE_RESOURCE.getSession().createQuery(sql);
						q.setParameter("numOrdenLaboratorio",
								((Integer) objLista[7]).intValue());

						MalariaResultados malariaResultado = (MalariaResultados) q
								.uniqueResult();

						if (malariaResultado != null
								&& malariaResultado.getSecMalariaResultado() > 0) {

							SimpleDateFormat sdfHR = new SimpleDateFormat(
									"KK:mm a");

							fila = new HashMap();
							fila.put("secMalariaResultado",
									malariaResultado.getSecMalariaResultado());
							fila.put("secOrdenLaboratorio",
									malariaResultado.getSecOrdenLaboratorio());
							fila.put("PFalciparum",
									malariaResultado.getPFalciparum());
							fila.put("PVivax", malariaResultado.getPVivax());
							fila.put("negativo", malariaResultado.getNegativo());
							fila.put("codigoMuestra",
									malariaResultado.getCodigoMuestra());
							fila.put("usuarioBioanalista",
									malariaResultado.getUsuarioBioanalista());
							fila.put("horaReporte", sdfHR
									.format(malariaResultado.getHoraReporte()));
							fila.put("estado", malariaResultado.getEstado());

							lst.add(fila);

							String resultadoMalariaResultado = JSONValue
									.toJSONString(lst);
							obj = JSONValue.parse(resultadoMalariaResultado);
							array = (JSONArray) obj;
							resultado.put("malariaResultado", array);

						} else {
							resultado.put("malariaResultado", null);
						}
					}
				}

				if (objLista[1] != null) {
					if (objLista[1].toString().compareTo("0") == 0) {

						obj = null;
						array = new JSONArray();
						lst = new LinkedList();

						sql = "select sd "
								+ " from SerologiaDengueMuestra sd "
								+ " join sd.ordenLaboratorio o "
								+ " where o.numOrdenLaboratorio = :numOrdenLaboratorio ";

						q = HIBERNATE_RESOURCE.getSession().createQuery(sql);
						q.setParameter("numOrdenLaboratorio",
								((Integer) objLista[7]).intValue());

						SerologiaDengueMuestra serologiaDengue = (SerologiaDengueMuestra) q
								.uniqueResult();

						if (serologiaDengue != null
								&& serologiaDengue.getSecSerologiaDengue() > 0) {

							SimpleDateFormat sdfHR = new SimpleDateFormat(
									"KK:mm a");

							fila = new HashMap();
							fila.put("secSerologiaDengue",
									serologiaDengue.getSecSerologiaDengue());
							fila.put("ordenLaboratorio", serologiaDengue
									.getOrdenLaboratorio()
									.getSecOrdenLaboratorio());
							fila.put("codigoMuestra",
									serologiaDengue.getCodigoMuestra());
							fila.put("usuarioBioanalista",
									serologiaDengue.getUsuarioBioanalista());
							fila.put("horaReporte", sdfHR
									.format(serologiaDengue.getHoraReporte()));
							fila.put("estado", serologiaDengue.getEstado());

							lst.add(fila);

							String resultadoSerologiaDengue = JSONValue
									.toJSONString(lst);
							obj = JSONValue.parse(resultadoSerologiaDengue);
							array = (JSONArray) obj;
							resultado.put("serologiaDengue", array);

						} else {
							resultado.put("serologiaDengue", null);
						}

					}
				}

				if (objLista[2] != null) {
					if (objLista[2].toString().compareTo("0") == 0) {

						obj = null;
						array = new JSONArray();
						lst = new LinkedList();

						sql = "select sc "
								+ " from SerologiaChikMuestra sc "
								+ " join sc.ordenLaboratorio o "
								+ " where o.numOrdenLaboratorio = :numOrdenLaboratorio ";

						q = HIBERNATE_RESOURCE.getSession().createQuery(sql);
						q.setParameter("numOrdenLaboratorio",
								((Integer) objLista[7]).intValue());

						SerologiaChikMuestra serologiaChick = (SerologiaChikMuestra) q
								.uniqueResult();

						if (serologiaChick != null
								&& serologiaChick.getSecChikMuestra() > 0) {

							SimpleDateFormat sdfHR = new SimpleDateFormat(
									"KK:mm a");

							fila = new HashMap();
							fila.put("secChikMuestra",
									serologiaChick.getSecChikMuestra());
							fila.put("ordenLaboratorio", serologiaChick
									.getOrdenLaboratorio()
									.getSecOrdenLaboratorio());
							fila.put("codigoMuestra",
									serologiaChick.getCodigoMuestra());
							fila.put("usuarioBioanalista",
									serologiaChick.getUsuarioBioanalista());
							fila.put("horaReporte", sdfHR.format(serologiaChick
									.getHoraReporte()));
							fila.put("estado", serologiaChick.getEstado());

							lst.add(fila);

							String resultadoSerologiaChick = JSONValue
									.toJSONString(lst);
							obj = JSONValue.parse(resultadoSerologiaChick);
							array = (JSONArray) obj;
							resultado.put("serologiaChick", array);

						} else {
							resultado.put("serologiaChick", null);
						}
					}

				}

				if (objLista[3] != null) {
					if (objLista[3].toString().compareTo("0") == 0) {

						obj = null;
						array = new JSONArray();
						lst = new LinkedList();

						sql = "select pr "
								+ " from PerifericoResultado pr, OrdenLaboratorio o"
								+ " where pr.secOrdenLaboratorio = o.secOrdenLaboratorio "
								+ " and o.numOrdenLaboratorio = :numOrdenLaboratorio ";

						q = HIBERNATE_RESOURCE.getSession().createQuery(sql);
						q.setParameter("numOrdenLaboratorio",
								((Integer) objLista[7]).intValue());

						PerifericoResultado perifericoResult = (PerifericoResultado) q
								.uniqueResult();

						if (perifericoResult != null
								&& perifericoResult.getSecPerifericoResultado() > 0) {

							SimpleDateFormat sdfHR = new SimpleDateFormat(
									"KK:mm a");

							fila = new HashMap();
							fila.put("secPerifericoResultado", perifericoResult
									.getSecPerifericoResultado());
							fila.put("secOrdenLaboratorio",
									perifericoResult.getSecOrdenLaboratorio());
							fila.put("anisocitosis",
									perifericoResult.getAnisocitosis());
							fila.put("anisocromia",
									perifericoResult.getAnisocromia());
							fila.put("poiquilocitosis",
									perifericoResult.getPoiquilocitosis());
							fila.put("linfocitosAtipicos",
									perifericoResult.getLinfocitosAtipicos());
							fila.put("observacionSblanca",
									perifericoResult.getObservacionSblanca());
							fila.put("observacionPlaqueta",
									perifericoResult.getObservacionPlaqueta());
							fila.put("codigoMuestra",
									perifericoResult.getCodigoMuestra());
							fila.put("usuarioBioanalista",
									perifericoResult.getUsuarioBioanalista());
							fila.put("horaReporte", sdfHR
									.format(perifericoResult.getHoraReporte()));
							fila.put("estado", perifericoResult.getEstado());

							lst.add(fila);

							String resultadoPerifericoResultado = JSONValue
									.toJSONString(lst);
							obj = JSONValue.parse(resultadoPerifericoResultado);
							array = (JSONArray) obj;
							resultado.put("perifericoResultado", array);

						} else {
							resultado.put("perifericoResultado", null);
						}
					}
				}

				if (objLista[4] != null) {
					if (objLista[4].toString().compareTo("0") == 0) {

						obj = null;
						array = new JSONArray();
						lst = new LinkedList();

						sql = "select er "
								+ " from  EgoResultados er "
								+ " join er.ordenLaboratorio o"
								+ " where o.numOrdenLaboratorio = :numOrdenLaboratorio ";

						q = HIBERNATE_RESOURCE.getSession().createQuery(sql);
						q.setParameter("numOrdenLaboratorio",
								((Integer) objLista[7]).intValue());

						EgoResultados egoResult = (EgoResultados) q
								.uniqueResult();

						if (egoResult != null
								&& egoResult.getSecEgoResultado() > 0) {

							SimpleDateFormat sdfHR = new SimpleDateFormat(
									"KK:mm a");

							fila = new HashMap();
							fila.put("secEgoResultado",
									egoResult.getSecEgoResultado());
							fila.put("ordenLaboratorio", egoResult
									.getOrdenLaboratorio()
									.getSecOrdenLaboratorio());
							fila.put("color", egoResult.getColor());
							fila.put("aspecto", egoResult.getAspecto());
							fila.put("sedimento", egoResult.getSedimento());
							fila.put("densidad", egoResult.getDensidad());
							fila.put("proteinas", egoResult.getProteinas());
							fila.put("homoglobinas",
									egoResult.getHomoglobinas());
							fila.put("cuerpoCetonico",
									egoResult.getCuerpoCetonico());
							fila.put("ph", egoResult.getPh());
							fila.put("urobilinogeno",
									egoResult.getUrobilinogeno());
							fila.put("glucosa", egoResult.getGlucosa());
							fila.put("bilirrubinas",
									egoResult.getBilirrubinas());
							fila.put("nitritos", egoResult.getNitritos());
							fila.put("celulasEpiteliales",
									egoResult.getCelulasEpiteliales());
							fila.put("leucositos", egoResult.getLeucositos());
							fila.put("eritrocitos", egoResult.getEritrocitos());
							fila.put("cilindros", egoResult.getCilindros());
							fila.put("cristales", egoResult.getCristales());
							fila.put("hilosMucosos",
									egoResult.getHilosMucosos());
							fila.put("bacterias", egoResult.getBacterias());
							fila.put("levaduras", egoResult.getLevaduras());
							fila.put("observaciones",
									egoResult.getObservaciones());
							fila.put("codigoMuestra",
									egoResult.getCodigoMuestra());
							fila.put("usuarioBioanalista",
									egoResult.getUsuarioBioanalista());
							fila.put("horaReporte",
									sdfHR.format(egoResult.getHoraReporte()));
							fila.put("estado", egoResult.getEstado());

							lst.add(fila);

							String resultadoEGO = JSONValue.toJSONString(lst);
							obj = JSONValue.parse(resultadoEGO);
							array = (JSONArray) obj;
							resultado.put("egoResultado", array);

						} else {
							resultado.put("egoResultado", null);
						}
					}
				}

				if (objLista[5] != null) {
					if (objLista[5].toString().compareTo("0") == 0) {

						obj = null;
						array = new JSONArray();
						lst = new LinkedList();

						sql = "select eg "
								+ " from EghResultados eg, OrdenLaboratorio o "
								+ " where eg.secOrdenLaboratorio = o.secOrdenLaboratorio "
								+ " and o.numOrdenLaboratorio = :numOrdenLaboratorio ";

						q = HIBERNATE_RESOURCE.getSession().createQuery(sql);
						q.setParameter("numOrdenLaboratorio",
								((Integer) objLista[7]).intValue());

						EghResultados eghResult = (EghResultados) q
								.uniqueResult();

						if (eghResult != null
								&& eghResult.getSecEghResultado() > 0) {

							SimpleDateFormat sdfHR = new SimpleDateFormat(
									"KK:mm a");

							fila = new HashMap();
							fila.put("secEghResultado",
									eghResult.getSecEghResultado());
							fila.put("secOrdenLaboratorio",
									eghResult.getSecOrdenLaboratorio());
							fila.put("color", eghResult.getColor());
							fila.put("consistencia",
									eghResult.getConsistencia());
							fila.put("restosAlimenticios",
									eghResult.getRestosAlimenticios());
							fila.put("mucus", eghResult.getMucus());
							fila.put("ph", eghResult.getPh());
							fila.put("sangreOculta",
									eghResult.getSangreOculta());
							fila.put("bacterias", eghResult.getBacterias());
							fila.put("levaduras", eghResult.getLevaduras());
							fila.put("leucocitos", eghResult.getLeucocitos());
							fila.put("eritrocitos", eghResult.getEritrocitos());
							fila.put("filamentosMucosos",
									eghResult.getFilamentosMucosos());
							fila.put("EColi", eghResult.getEColi());
							fila.put("endolimaxNana",
									eghResult.getEndolimaxNana());
							fila.put("EHistolytica",
									eghResult.getEHistolytica());
							fila.put("gardiaAmblia",
									eghResult.getGardiaAmblia());
							fila.put("trichuris", eghResult.getTrichuris());
							fila.put("hymenolepisNana",
									eghResult.getHymenolepisNana());
							fila.put("strongyloideStercolaris",
									eghResult.getStrongyloideStercolaris());
							fila.put("unicinarias", eghResult.getUnicinarias());
							fila.put("enterovirus", eghResult.getEnterovirus());
							fila.put("observaciones",
									eghResult.getObservaciones());
							fila.put("codigoMuestra",
									eghResult.getCodigoMuestra());
							fila.put("usuarioBionalista",
									eghResult.getUsuarioBionalista());
							fila.put("horaReporte",
									sdfHR.format(eghResult.getHoraReporte()));
							fila.put("estado", eghResult.getEstado());

							lst.add(fila);

							String resultadoEGH = JSONValue.toJSONString(lst);
							obj = JSONValue.parse(resultadoEGH);
							array = (JSONArray) obj;
							resultado.put("eghResultado", array);

						} else {
							resultado.put("eghResultado", null);
						}
					}
				}

				if (objLista[6] != null) {
					if (objLista[6].toString().compareTo("0") == 0) {

						obj = null;
						array = new JSONArray();
						lst = new LinkedList();

						sql = "select im "
								+ " from InfluenzaMuestra im "
								+ " join im.ordenLaboratorio o "
								+ " where o.numOrdenLaboratorio = :numOrdenLaboratorio ";

						q = HIBERNATE_RESOURCE.getSession().createQuery(sql);
						q.setParameter("numOrdenLaboratorio",
								((Integer) objLista[7]).intValue());

						InfluenzaMuestra influenza = (InfluenzaMuestra) q
								.uniqueResult();

						if (influenza != null
								&& influenza.getSecInfluenzaMuestra() > 0) {

							SimpleDateFormat sdfHR = new SimpleDateFormat(
									"KK:mm a");

							fila = new HashMap();
							fila.put("secInfluenzaMuestra",
									influenza.getSecInfluenzaMuestra());
							fila.put("ordenLaboratorio", influenza
									.getOrdenLaboratorio()
									.getSecOrdenLaboratorio());
							fila.put("codigoMuestra",
									influenza.getCodigoMuestra());
							fila.put("usuarioBioanalista",
									influenza.getUsuarioBioanalista());
							fila.put("horaReporte",
									sdfHR.format(influenza.getHoraReporte()));
							fila.put("estado", influenza.getEstado());

							lst.add(fila);

							String resultadoInfluenza = JSONValue
									.toJSONString(lst);
							obj = JSONValue.parse(resultadoInfluenza);
							array = (JSONArray) obj;
							resultado.put("influenzaMuestra", array);

						} else {
							resultado.put("influenzaMuestra", null);
						}
					}
				}

				// Construir la lista a una estructura JSON
				result = UtilResultado.parserResultado2(resultado, "",
						UtilResultado.OK);
			} else {
				result = UtilResultado.parserResultado(null, Mensajes.NO_DATOS,
						UtilResultado.INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null,
					Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}

	/***
	 * Metodo para buscar Examenes marcados
	 * @param paramSecHojaConsulta, JSON
	 */
	@Override
	public String buscarExamenesChekeados(Integer paramSecHojaConsulta) {
		String result = null;
		try {
			List lst = new LinkedList();
			Map fila = null;

			String sql = "select h.bhc, h.serologiaDengue, h.serologiaChik, "
					+ " h.gotaGruesa, h.extendidoPeriferico, h.ego, h.egh, h.citologiaFecal, "
					+ " h.factorReumatoideo, h.albumina, h.astAlt, "
					+ " h.bilirrubinas, h.cpk, h.colesterol, h.influenza, h.otroExamenLab, h.oel "
					+ " from HojaConsulta h "
					+ " where h.secHojaConsulta = :secHojaConsulta ";

			Query q = HIBERNATE_RESOURCE.getSession().createQuery(sql);
			q.setParameter("secHojaConsulta", paramSecHojaConsulta);

			Object[] objLista = (Object[]) q.uniqueResult();

			// if(objLista != null && !objLista.toString().isEmpty()) ?
			// objLista.toString() : ""){}

			if (objLista != null && objLista.length > 0) {

				fila = new HashMap();
				if (objLista[0] != null) {
					fila.put("bhc", objLista[0].toString());
				}
				if (objLista[1] != null) {
					fila.put("serologiaDengue", objLista[1].toString());
				}
				if (objLista[2] != null) {
					fila.put("serologiaChik", objLista[2].toString());
				}
				if (objLista[3] != null) {
					fila.put("gotaGruesa", objLista[3].toString());
				}
				if (objLista[4] != null) {
					fila.put("extendidoPeriferico", objLista[4].toString());
				}
				if (objLista[5] != null) {
					fila.put("ego", objLista[5].toString());
				}
				if (objLista[6] != null) {
					fila.put("egh", objLista[6].toString());
				}
				if (objLista[7] != null) {
					fila.put("citologiaFecal", objLista[7].toString());
				}
				if (objLista[8] != null) {
					fila.put("factorReumatoideo", objLista[8].toString());
				}
				if (objLista[9] != null) {
					fila.put("albumina", objLista[9].toString());
				}
				if (objLista[10] != null) {
					fila.put("astAlt", objLista[10].toString());
				}
				if (objLista[11] != null) {
					fila.put("bilirrubinas", objLista[11].toString());
				}
				if (objLista[12] != null) {
					fila.put("cpk", objLista[12].toString());
				}
				if (objLista[13] != null) {
					fila.put("colesterol", objLista[13].toString());
				}
				if (objLista[14] != null) {
					fila.put("influenza", objLista[14].toString());
				}
				if (objLista[15] != null) {
					fila.put("otroExamenLab", objLista[15].toString());
				}
				if (objLista[16] != null) {
					fila.put("oel", objLista[16].toString());
				}
				// fila.put("otroExamenLab", objLista[15].toString());

				lst.add(fila);

				// Construir la lista a una estructura JSON
				result = UtilResultado.parserResultado(lst, "",
						UtilResultado.OK);
			} else {
				result = UtilResultado.parserResultado(null, Mensajes.NO_DATOS,
						UtilResultado.INFO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null,
					Mensajes.ERROR_NO_CONTROLADO, UtilResultado.ERROR);
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}

	// Funcion para guardar OtroExamen
	@Override
	public String guardarOtroExamen(String paramHojaConsulta) {
		String result = null;
		try {
			int secHojaConsulta;
			String otroExamenLab;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			otroExamenLab = (hojaConsultaJSON.get("otroExamenLab").toString());

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());

			// hojaConsulta.setSecHojaConsulta(secHojaConsulta);
			hojaConsulta.setOtroExamenLab(otroExamenLab);

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
	 * Metodo para obtener la hoja consulta en PDF
	 * @param secHojaConsulta, Identificador Hoja Consulta
	 */
	public byte[] getHojaConsultaPdf(Integer secHojaConsulta) {
		String nombreReporte = "HojaConsulta";
//		try {
//			List oLista = new LinkedList(); // Listado final para el resultado
//
//			String sql = "select a " + "from HojaConsulta a ";
//			if (secHojaConsulta > 0)
//				sql += "where a.secHojaConsulta = :secHojaConsulta ";
//
//			sql += "order by a.ordenLlegada ";
//
//			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
//
//			if (secHojaConsulta > 0)
//				query.setParameter("secHojaConsulta", secHojaConsulta);
//
//			List<HojaConsulta> objLista = query.list();
//
//			return UtilitarioReporte.mostrarReporte(nombreReporte, null,
//					objLista);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		} finally {
//			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
//				HIBERNATE_RESOURCE.close();
//			}
//		}
		return null;
	}
	
	/***
	 * Metodo para actualizar estado de la hoja consulta
	 * @param paramHojaConsulta, JSON
	 * @param cambioTurno, Si es cambio turno
	 */
	@Override
	public String actualizarEstadoEnConsulta(String paramHojaConsulta, boolean cambioTurno) {
		String result = null;
		try {
			int secHojaConsulta;
			short usuarioMedico=0;
			short medicoCambioTurno=0;
			char estado;
			boolean estaLibre = false;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			if (!cambioTurno)
				usuarioMedico= ((Number) hojaConsultaJSON.get("usuarioMedico"))
						.shortValue();
			else
				medicoCambioTurno= ((Number) hojaConsultaJSON.get("medicoCambioTurno"))
						.shortValue();	
			
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(!cambioTurno && (hojaConsulta.getUsuarioMedico() == null || 
					hojaConsulta.getUsuarioMedico().shortValue() == usuarioMedico)) {
				estaLibre = true;
			}
			
			if(cambioTurno && (hojaConsulta.getMedicoCambioTurno() == null || 
					hojaConsulta.getMedicoCambioTurno().shortValue() == medicoCambioTurno)) {
				estaLibre = true;
			}
			
			if(estaLibre) {
				hojaConsulta.setEstado('3');
				
				if (!cambioTurno){
					hojaConsulta.setUsuarioMedico(usuarioMedico);
					hojaConsulta.setHora(hojaConsultaJSON.get("horaConsulta").toString());
				}
				else
				{
					hojaConsulta.setMedicoCambioTurno(medicoCambioTurno);
					hojaConsulta.setFechaCambioTurno(new Date());
				}
				HIBERNATE_RESOURCE.begin();
				HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);
				HIBERNATE_RESOURCE.commit();
				result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
			} else {
				result = UtilResultado.parserResultado(null, Mensajes.PACIENTE_SELECCIONADO, UtilResultado.INFO);
			}
			

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
	
	/**
	 * Retorna el listado de medicos
	 */
	@Override
	public String getListaMedicos() {
		String result = null;
		try {

			List oLista = new LinkedList();
			Map fila = null;
			
			String sql = "select uv.id, uv.nombre, uv.codigopersonal from usuarios_view uv " + 
						 " inner join roles_view rv on uv.usuario = rv.usuario " +
						 " where rv.nombre in (select valores from parametros_sistemas " + 
						 " where nombre_parametro = 'PERFIL_MEDICO') order by uv.nombre";

			Query query = HIBERNATE_RESOURCE.getSession().createSQLQuery(sql);

			List<Object[]> objLista = (query.list() != null) ? (List<Object[]>) query.list() : new ArrayList<Object[]>();

			if (objLista != null && objLista.size() > 0) {

				for (Object[] object : objLista) {

					// Construir la fila del registro actual usando arreglos

					fila = new HashMap();
					fila.put("idMedico", object[0]);						
					fila.put("nombreMedico", object[1].toString());
					fila.put("codigoPersonal", object[2] != null ? object[2].toString(): "-" );
					
					oLista.add(fila);
				}

				// Construir la lista a una estructura JSON
				result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);
				
			} else {
				result = UtilResultado.parserResultado(null, Mensajes.NO_EXISTEN_MEDICOS, UtilResultado.INFO);
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
	
	/**
	 * Retorna el listado de medicos
	 */
	@Override
	public String getListaMedicos(String paramNombre) {
		String result = null;
		try {
			
			String nombre = "";

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramNombre);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			nombre = hojaConsultaJSON.get("nombre").toString().toUpperCase();

			List oLista = new LinkedList();
			Map fila = null;
			
			String sql = "select uv.id, uv.nombre, uv.codigopersonal from usuarios_view uv " + 
						 " inner join roles_view rv on uv.usuario = rv.usuario " +
						 " where rv.nombre in (select valores from parametros_sistemas " + 
						 " where nombre_parametro = 'PERFIL_MEDICO') " +
						 " and ( upper(uv.nombre) like :nombre " +
						 " or uv.codigopersonal like :nombre ) order by uv.nombre";

			Query query = HIBERNATE_RESOURCE.getSession().createSQLQuery(sql);

			query.setParameter("nombre", new StringBuffer().append('%').append(nombre).append('%').toString());			
			
			List<Object[]> objLista = (query.list() != null) ? (List<Object[]>) query.list() : new ArrayList<Object[]>();

			if (objLista != null && objLista.size() > 0) {

				for (Object[] object : objLista) {

					// Construir la fila del registro actual usando arreglos

					fila = new HashMap();
					fila.put("idMedico", object[0]);						
					fila.put("nombreMedico", object[1].toString());
					fila.put("codigoPersonal", object[2] != null ? object[2].toString(): "-" );
					
					oLista.add(fila);
				}

				// Construir la lista a una estructura JSON
				result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);
				
			} else {
				result = UtilResultado.parserResultado(null, Mensajes.NO_EXISTEN_MEDICOS, UtilResultado.INFO);
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
	 * Metodo para setear el usuario de enfermeria al paciente
	 */
	@Override
	public String actualizarEstadoEnfermeria(String paramHojaConsulta) {
		String result = null;
		try {
			int secHojaConsulta;
			short usuarioEnfermeria=0;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			usuarioEnfermeria = ((Number) hojaConsultaJSON.get("usuarioEnfermeria")).shortValue();
			
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(hojaConsulta.getUsuarioEnfermeria() == null || 
					hojaConsulta.getUsuarioEnfermeria().shortValue() == usuarioEnfermeria) {
				hojaConsulta.setUsuarioEnfermeria(usuarioEnfermeria);
				
				HIBERNATE_RESOURCE.begin();
				HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);
				HIBERNATE_RESOURCE.commit();
				result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
			} else {
				result = UtilResultado.parserResultado(null, Mensajes.PACIENTE_SELECCIONADO, UtilResultado.INFO);
			}
			

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
	
}
