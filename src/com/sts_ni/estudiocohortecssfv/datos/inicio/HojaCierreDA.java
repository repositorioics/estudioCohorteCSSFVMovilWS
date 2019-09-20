package com.sts_ni.estudiocohortecssfv.datos.inicio;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ni.com.sts.estudioCohorteCSSFV.modelo.HojaConsulta;
import ni.com.sts.estudioCohorteCSSFV.modelo.InfluenzaMuestra;
import ni.com.sts.estudioCohorteCSSFV.modelo.MotivoCancelacion;
import ni.com.sts.estudioCohorteCSSFV.modelo.OrdenLaboratorio;
import ni.com.sts.estudioCohorteCSSFV.modelo.SerologiaChikMuestra;
import ni.com.sts.estudioCohorteCSSFV.modelo.SerologiaDengueMuestra;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sts_ni.estudiocohortecssfv.servicios.HojaCierreService;
import com.sts_ni.estudiocohortecssfv.servicios.HojaConsultaReporteService;
import com.sts_ni.estudiocohortecssfv.util.HibernateResource;
import com.sts_ni.estudiocohortecssfv.util.Mensajes;
import com.sts_ni.estudiocohortecssfv.util.UtilResultado;
import com.sts_ni.estudiocohortecssfv.util.UtilHojaConsulta;

/***
 * Clase para controlar los metodos que se conectan a los datos para los procesos
 * de cierre de hoja consulta.
 */
public class HojaCierreDA implements HojaCierreService {
	
	private static final String QUERY_HOJA_CONSULTA_BY_ID;
	
	private static final HibernateResource HIBERNATE_RESOURCE;
	
	private HojaConsultaReporteService consultaReporteService;
	
	static {
		QUERY_HOJA_CONSULTA_BY_ID = "select h from HojaConsulta h where h.secHojaConsulta = :id";
		HIBERNATE_RESOURCE = new HibernateResource();
	}
	
	/***
	 * Metodo que obtiene la informacion de la grilla de cierre de consulta.
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String cargarGrillaCierre(String paramHojaConsulta) {
		String result;
		try {
			
			List oLista = new LinkedList();
			Map fila = null;
			
			JSONParser parser = new JSONParser();
	        JSONObject hojaConsultaJSON = (JSONObject)((Object) parser.parse(paramHojaConsulta));
	        		
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(QUERY_HOJA_CONSULTA_BY_ID);
	        query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue());
	        
	        HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
	        
	        fila = new HashMap();
	        
	        query = HIBERNATE_RESOURCE.getSession().createQuery("select rv.nombre, uv.codigoPersonal, uv.nombre " +
	        		" from UsuariosView uv, RolesView rv " +
	        		" where uv.usuario = rv.usuario " +
	        		" and uv.id = :idUsuarioLog");
	        query.setParameter("idUsuarioLog", ((Number) hojaConsultaJSON.get("usuarioLog")).intValue());
	        
	        Object[] usuarioLog = (Object[]) query.list().get(0);
	        
            fila.put("cargoUsuarioLog", usuarioLog[0]);
            fila.put("numeroPersonalLog", usuarioLog[1]);
            fila.put("nombreUsuarioLog", usuarioLog[2]);
	        
	        if(hojaConsulta.getMedicoCambioTurno() != null && hojaConsulta.getMedicoCambioTurno().intValue() > 0) {
	        
		        query = HIBERNATE_RESOURCE.getSession().createQuery("select rv.nombre, uv.codigoPersonal, uv.nombre " +
		        		" from UsuariosView uv, RolesView rv " +
		        		" where uv.usuario = rv.usuario " +
		        		" and uv.id = :idUsuarioMedico");
		        query.setParameter("idUsuarioMedico", hojaConsulta.getUsuarioMedico().intValue());
		        
		        Object[] usuarioMedico = (Object[]) query.list().get(0);
		        
                fila.put("cargoMedico", usuarioMedico[0]);
                fila.put("numeroPersonalMedico", usuarioMedico[1]);
                fila.put("nombreMedico", usuarioMedico[2]);
	        }
	        
	        
	        if(hojaConsulta.getUsuarioEnfermeria() != null) {
	        
		        query = HIBERNATE_RESOURCE.getSession().createQuery("select rv.nombre, uv.codigoPersonal, uv.nombre " +
		        		" from UsuariosView uv, RolesView rv " +
		        		" where uv.usuario = rv.usuario " +
		        		" and uv.id = :idEnfermera");
		        query.setParameter("idEnfermera", Integer.valueOf(hojaConsulta.getUsuarioEnfermeria()));
		        
		        Object[] Enfermera = (Object[]) query.list().get(0);
		        
	            fila.put("cargoEnfermera", Enfermera[0]);
	            fila.put("numeroPersonalEnfermera", Enfermera[1]);
	            fila.put("nombreEnfermera", Enfermera[2]);
	        } else {
	        	fila.put("cargoEnfermera", "--");
	            fila.put("numeroPersonalEnfermera", "--");
	            fila.put("nombreEnfermera", "--");
	        }
	        
	        oLista.add(fila);
	        
	        result = UtilResultado.parserResultado(oLista, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();
			// TODO: handle exception
		}finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	        
	
//	@Override
//	public String procesoCierre(String paramHojaConsulta) {
//		String result;
//		try {
//			consultaReporteService = new HojaConsultaReporteDA();
//			JSONParser parser = new JSONParser();
//	        JSONObject hojaConsultaJSON = (JSONObject)((Object) parser.parse(paramHojaConsulta));
//	        		
//			Query query = HIBERNATE_RESOURCE.getSession().createQuery(QUERY_HOJA_CONSULTA_BY_ID);
//	        query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue());
//	        
//	        HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
//	        
//	        if(hojaConsulta.getEstado() == '5') {
//	        	hojaConsulta.setMedicoCambioTurno(((Number) hojaConsultaJSON.get("usuarioMedico")).shortValue());
//	        	hojaConsulta.setFechaCierreCambioTurno(new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(hojaConsultaJSON.get("fechaCierre").toString()));
//	        } else {
//	        	hojaConsulta.setFechaCierre(new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(hojaConsultaJSON.get("fechaCierre").toString()));
//	        }
//	        hojaConsulta.setEstado('7');
//	        HIBERNATE_RESOURCE.begin();
//            HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);
//            HIBERNATE_RESOURCE.commit();
//            
//            try {
//            	consultaReporteService.imprimirConsultaPdf(hojaConsulta.getSecHojaConsulta());
//            } catch(Exception e) {
//            	e.printStackTrace();
//            }
//	        
//            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
//		}  catch (Exception e) {
//			e.printStackTrace();
//			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
//			HIBERNATE_RESOURCE.rollback();
//			// TODO: handle exception
//		}finally {
//            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
//            	HIBERNATE_RESOURCE.close();
//            }
//        }
//		return result;
//	}
	
	/***
	 * Metodo que realiza el proceso de cierre de una hoja de consulta en especifico.
	 * @param paramHojaConsulta, JSON
	 */
	public String procesoCierre(String paramHojaConsulta) {
		String result;
		try {
			consultaReporteService = new HojaConsultaReporteDA();
			JSONParser parser = new JSONParser();
	        JSONObject hojaConsultaJSON = (JSONObject)((Object) parser.parse(paramHojaConsulta));
	        		
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(QUERY_HOJA_CONSULTA_BY_ID);
	        query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue());
	        
	        HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
	        
	        if(UtilHojaConsulta.validarTodasSecciones(hojaConsulta)) {
	        	
	        	query = HIBERNATE_RESOURCE.getSession().createQuery(new StringBuffer().append("select o from OrdenLaboratorio o ")
	        			.append("where o.hojaConsulta.secHojaConsulta = :id").toString());
		        query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue());
		        
		        boolean mensajeAlertaExam = false;
		        ArrayList<OrdenLaboratorio> lstOrdenLaboratorio = (ArrayList<OrdenLaboratorio>) query.list();
		        String sql;
		        boolean noExamenesTerminado = false;
		        
		        if(validarTodoExamenMaracdo(hojaConsulta)) {
		        
		        	
		        	if(hojaConsulta.getSerologiaDengue().toString().compareTo("0") == 0) {
		        		sql ="select sd " +
		            			" from SerologiaDengueMuestra sd " +
		            			" join sd.ordenLaboratorio o " +
		            			" join o.hojaConsulta h " +
		            			" where h.secHojaConsulta = :secHojaConsulta " +
		            			" and sd.estado != '0'";
		            		
		        		query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
		        		query.setParameter("secHojaConsulta", hojaConsulta.getSecHojaConsulta());
		        		SerologiaDengueMuestra sd = (SerologiaDengueMuestra)query.uniqueResult();
		        		
		        		if(sd == null || sd.getSecSerologiaDengue() == 0) {
		        			noExamenesTerminado = true;
		        		}
		        	}
		        	
		        	if(hojaConsulta.getSerologiaChik().toString().compareTo("0") == 0) {
		        		sql ="select sc " +
		            			" from SerologiaChikMuestra sc " +
		            			" join sc.ordenLaboratorio o " +
		            			" join o.hojaConsulta h " +
		            			" where h.secHojaConsulta = :secHojaConsulta " +
		            			" and sc.estado != '0'";
		            		
		        		query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
		        		query.setParameter("secHojaConsulta", hojaConsulta.getSecHojaConsulta());
		        		SerologiaChikMuestra sc = (SerologiaChikMuestra)query.uniqueResult();
		        		
		        		if(sc == null || sc.getSecChikMuestra() == 0) {
		        			noExamenesTerminado = true;
		        		}
		        	}
		        	
		        	if(hojaConsulta.getInfluenza().toString().compareTo("0") == 0) {
		        		sql ="select ifm " +
		            			" from InfluenzaMuestra ifm " +
		            			" join ifm.ordenLaboratorio o " +
		            			" join o.hojaConsulta h " +
		            			" where h.secHojaConsulta = :secHojaConsulta " +
		            			" and ifm.estado != '0'";
		            		
		        		query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
		        		query.setParameter("secHojaConsulta", hojaConsulta.getSecHojaConsulta());
		        		InfluenzaMuestra in = (InfluenzaMuestra)query.uniqueResult();
		        		
		        		if(in == null || in.getSecInfluenzaMuestra() == 0) {
		        			noExamenesTerminado = true;
		        		}
		        	}
		        	
		        	if(!noExamenesTerminado) {
		        	
				        for (OrdenLaboratorio ordenLaboratorio : lstOrdenLaboratorio) {
							if(new Character(ordenLaboratorio.getEstado()).compareTo('0') == 0){
								mensajeAlertaExam = true;
								break;
							}
						}
				        
				        if(hojaConsulta.getEstado() == '5') {
				        	hojaConsulta.setMedicoCambioTurno(((Number) hojaConsultaJSON.get("usuarioMedico")).shortValue());
				        	hojaConsulta.setFechaCierreCambioTurno(new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(hojaConsultaJSON.get("fechaCierre").toString()));
				        } else {
				        	hojaConsulta.setFechaCierre(new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(hojaConsultaJSON.get("fechaCierre").toString()));
				        }
				        hojaConsulta.setEstado('7');
				        HIBERNATE_RESOURCE.begin();
			            HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);
			            HIBERNATE_RESOURCE.commit();
			            
			            try {
			            	// Validacion para verificar que la hoja de consulta se halla cerrado antes de enviar la impresion de la hoja
			            	
			            	sql = "select count(*) from HojaConsulta where estado = '7' and secHojaConsulta = :id";
			            	query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
			            	query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue());
			            	
			            	Long resultHojaConsulta = (Long) query.uniqueResult();
			            	
			            	/* Si el resultadoHojaConsulta es mayor que 0, indica que la hoja de consulta se cerro correctamente
			            	entonces se procede a realizar la impresion de la hoja de consulta */
			            	if (resultHojaConsulta.intValue() > 0) {
			            		consultaReporteService.imprimirConsultaPdf(hojaConsulta.getSecHojaConsulta());
			            	} else {
			            		result = UtilResultado.parserResultado(null, Mensajes.HOJA_CONSULTA_NO_SE_PUDO_CERRAR, UtilResultado.INFO);
			            	}
			            } catch(Exception e) {
			            	e.printStackTrace();
			            }
				        
				        if(mensajeAlertaExam) {
				        	result = UtilResultado.parserResultado(null, Mensajes.EXISTEN_RESULTADOS_PENDIENTES, UtilResultado.INFO);
				        } else {
				        	result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
				        }
		        	} else {
		        		result = UtilResultado.parserResultado(null, Mensajes.EXISTEN_RESULTADOS_PENDIENTES, UtilResultado.ERROR);
		        	}
		        } else {
		        	result = UtilResultado.parserResultado(null, Mensajes.CASILLAS_SIN_MARCAR, UtilResultado.ERROR);
		        }
		        
	        } else {
	        	result = UtilResultado.parserResultado(null, Mensajes.CASILLAS_SIN_MARCAR, UtilResultado.ERROR);
	        }
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();
			// TODO: handle exception
		}finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo que realiza el proceso de cambia de turno en la consulta.
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String procesoCambioTurno(String paramHojaConsulta) {
		String result;
		try {
			
			JSONParser parser = new JSONParser();
	        JSONObject hojaConsultaJSON = (JSONObject)((Object) parser.parse(paramHojaConsulta));
	        		
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(QUERY_HOJA_CONSULTA_BY_ID);
	        query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue());
	        
	        HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
	        
	        hojaConsulta.setEstado('5');
	        hojaConsulta.setMedicoCambioTurno(null);
	        hojaConsulta.setFechaCambioTurno(new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(hojaConsultaJSON.get("fechaCambioTurno").toString()));
	        HIBERNATE_RESOURCE.begin();
            HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
	        /*if(hojaConsulta.getEstado() == '7') {
	        
		        hojaConsulta.setEstado('5');
		        hojaConsulta.setFechaCambioTurno(new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(hojaConsultaJSON.get("fechaCambioTurno").toString()));
		        HIBERNATE_RESOURCE.begin();
	            HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);
	            HIBERNATE_RESOURCE.commit();
	            
	            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        } else {
	        	result = UtilResultado.parserResultado(null, Mensajes.HOJA_CONSULTA_NO_CERRADA, UtilResultado.ERROR);
	        }*/
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();
			// TODO: handle exception
		}finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo que realiza el proceso de agregar una nueva hoja de consulta para un paciente
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String procesoAgregarHojaConsulta(String paramHojaConsulta) {
		String result;
		try {
			
			JSONParser parser = new JSONParser();
	        JSONObject hojaConsultaJSON = (JSONObject)((Object) parser.parse(paramHojaConsulta));
	        		
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(QUERY_HOJA_CONSULTA_BY_ID);
	        query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue());
	        
	        HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
	        
	        if(hojaConsulta.getEstado() == '7') {
		        
		        HIBERNATE_RESOURCE.begin();
		        HojaConsulta nuevaHojaConsulta = cargarNuevaHojaConsulta(hojaConsulta);
	            HIBERNATE_RESOURCE.getSession().saveOrUpdate(nuevaHojaConsulta);
	            HIBERNATE_RESOURCE.commit();
	            
	            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
            
	        } else {
	        	result = UtilResultado.parserResultado(null, Mensajes.HOJA_CONSULTA_NO_CERRADA, UtilResultado.ERROR);
	        }
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();
			// TODO: handle exception
		}finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
		
	/***
	 * Metodo que realiza el proceso de cancelar una hoja de consulta.
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String procesoCancelar(String paramHojaConsulta) {
		String result;
		try {
			
			JSONParser parser = new JSONParser();
	        JSONObject hojaConsultaJSON = (JSONObject)((Object) parser.parse(paramHojaConsulta));
	        		
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(QUERY_HOJA_CONSULTA_BY_ID);
	        query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue());
	        
	        HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
	        
	        hojaConsulta.setEstado('8');
	        
	        MotivoCancelacion motivoCancelacion = new MotivoCancelacion();
			motivoCancelacion.setNumHojaConsulta(((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue());
			motivoCancelacion.setMotivo((hojaConsultaJSON.get("motivo").toString()));
			motivoCancelacion.setCodExpediente(hojaConsulta.getCodExpediente());
			motivoCancelacion.setFechaCancela(Calendar.getInstance().getTime());
			motivoCancelacion.setUsuarioCancela(((Number) hojaConsultaJSON.get("usuarioMedico")).shortValue());
	        
	        HIBERNATE_RESOURCE.begin();
            HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);
            HIBERNATE_RESOURCE.getSession().saveOrUpdate(motivoCancelacion);
            HIBERNATE_RESOURCE.commit();
            
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();
			// TODO: handle exception
		}finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
	/***
	 * Metodo que realiza el proceso de no atiende llamado de consulta.
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String noAtiendeLlamadoCierre(String paramHojaConsulta){
		String result= null;
		try {
			
			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
            JSONObject hojaConsultaJSON = (JSONObject) obj;
			
			String sql = "select max(h.ordenLlegada) " +
					" from HojaConsulta h, EstadosHoja e " +
					" where h.estado = e.codigo " +
					" and e.codigo != '1' " +
					" and to_char(h.fechaConsulta, 'yyyyMMdd') = " +
					" (select to_char(h2.fechaConsulta, 'yyyyMMdd') from HojaConsulta h2 " +
					" where h2.secHojaConsulta = :id ) ";
			
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue());

            Integer maxOrdenLlegada = ((Short) query.uniqueResult()).intValue();
            
            query = HIBERNATE_RESOURCE.getSession().createQuery(QUERY_HOJA_CONSULTA_BY_ID);
            query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue());
            
            HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
            
            hojaConsulta.setOrdenLlegada(Short.valueOf((maxOrdenLlegada+1)+""));
            hojaConsulta.setEstado('2');
            
            /* Guardamos la hora en la que se llamo al paciente, 
             * si este no atiende al llamado, si existe una hora guardada, se obtiene el valor de la hora anterior y 
			 * se envia a guardar la hora anterior mas la nueva hora */
            Date date = new Date();
			 
			String strDateFormat = "hh:mm a";
			SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
			String noAtiendeLlamadoMedico = sdf.format(date);
			if (hojaConsulta.getNoAtiendeLlamadoMedico() != null) {
				String valorAnterior = hojaConsulta.getNoAtiendeLlamadoMedico();
				hojaConsulta.setNoAtiendeLlamadoMedico(valorAnterior+";"+noAtiendeLlamadoMedico);
			} else {
				hojaConsulta.setNoAtiendeLlamadoMedico(noAtiendeLlamadoMedico);
			}
            
            HIBERNATE_RESOURCE.begin();
            HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);
            HIBERNATE_RESOURCE.commit();
            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();
			// TODO: handle exception
		}finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
		
	}
	
	@Override
	public String validarSalirHojaConsulta(String paramHojaConsulta) {
		String result;
		try {
			
			JSONParser parser = new JSONParser();
	        JSONObject hojaConsultaJSON = (JSONObject)((Object) parser.parse(paramHojaConsulta));
	        		
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(QUERY_HOJA_CONSULTA_BY_ID);
	        query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue());
	        
	        HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
	        
	        if(hojaConsulta.getEstado() == '7' || 
	        		hojaConsulta.getEstado() == '5' || 
	        		hojaConsulta.getEstado() == '6' ||
	        		hojaConsulta.getEstado() == '3' ||
	        		hojaConsulta.getEstado() == '4') {
	            
	            result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
            
	        } else {
	        	result = UtilResultado.parserResultado(null, Mensajes.HOJA_CONSULTA_NO_CERRADA, UtilResultado.ERROR);
	        }
	        
		}  catch (Exception e) {
			e.printStackTrace();
			result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO + e.getMessage(), UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();
			// TODO: handle exception
		}finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return result;
	}
	
//	private boolean validarTodoDiagnosticoMaracdo(HojaConsulta hojaConsulta) {
//		if( hojaConsulta.getAcetaminofen() != null
//				&& hojaConsulta.getAsa() != null
//				&& hojaConsulta.getIbuprofen() != null
//				&& hojaConsulta.getPenicilina() != null
//				&& hojaConsulta.getAmoxicilina() != null
//				&& hojaConsulta.getDicloxacilina() != null
//				&& hojaConsulta.getOtroAntibiotico() != null
//				&& hojaConsulta.getFurazolidona() != null 
//				&& hojaConsulta.getMetronidazolTinidazol() != null
//				&& hojaConsulta.getAlbendazolMebendazol() != null
//				&& hojaConsulta.getSulfatoFerroso() != null
//				&& hojaConsulta.getSueroOral() != null
//				&& hojaConsulta.getSulfatoZinc() != null
//				&& hojaConsulta.getLiquidosIv() != null
//				&& hojaConsulta.getPrednisona() != null
//				&& hojaConsulta.getHidrocortisonaIv() != null
//				&& hojaConsulta.getSalbutamol() != null
//				&& hojaConsulta.getOseltamivir() != null) {
//			return true;
//		}
//		
//		return false;
//	}
	
	
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
	
	private HojaConsulta cargarNuevaHojaConsulta(HojaConsulta anteriorHoja) throws 
	HibernateException, NullPointerException {
		HojaConsulta nuevaHoja = null;
		try {
			nuevaHoja = new HojaConsulta();
			
			String sql = "select max(h.ordenLlegada) "
					+ " from HojaConsulta h"
					+ " where to_char(h.fechaConsulta, 'yyyyMMdd') = to_char(current_date, 'yyyyMMdd')";

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			Integer maxOrdenLlegada = (query.uniqueResult() == null) ? 1
					: ((Short) query.uniqueResult()).intValue() + 1;

	
			query = HIBERNATE_RESOURCE.getSession().
									createQuery("select max(h.numHojaConsulta) from HojaConsulta h");
	
			int numHoja = ((query.uniqueResult() == null) ? 1 :
								((Integer) query.uniqueResult()).intValue()) + 1;
			
			sql = "select valores " + 
					" from ParametrosSistemas p where p.nombreParametro ='INICIO_HOJA_CONSULTA'";

			query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			String valorParametro = query.uniqueResult().toString();
			if (Integer.valueOf(valorParametro) > numHoja) {
				numHoja = Integer.valueOf(valorParametro);
			}
			
			// Obteniendo los estudios del participante por el codExpediente
			sql = "(SELECT array_to_string( " + " ARRAY(SELECT DISTINCT ec.desc_estudio "
					+ "		from cons_estudios c "
					+ "		inner join estudio_catalogo ec on c.codigo_consentimiento = ec.cod_estudio "
					+ "		where c.codigo_expediente = :codExpediente and c.retirado != '1' "
					+ "		order by ec.desc_estudio asc), ', '))";

			query = HIBERNATE_RESOURCE.getSession().createSQLQuery(sql);
			query.setParameter("codExpediente", anteriorHoja.getCodExpediente());
			String estudios = (String) query.uniqueResult();

			Date date = new Date();
			String strDateFormat = "hh:mm a";
			SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
			String horaEnfermeria = sdf.format(date);
			
			nuevaHoja.setCodExpediente(anteriorHoja.getCodExpediente());
			nuevaHoja.setNumHojaConsulta(numHoja);
			nuevaHoja.setOrdenLlegada(maxOrdenLlegada.shortValue());
			nuevaHoja.setFechaConsulta(new Date());
			nuevaHoja.setUsuarioEnfermeria(anteriorHoja.getUsuarioEnfermeria());
			nuevaHoja.setExpedienteFisico(anteriorHoja.getExpedienteFisico());
			nuevaHoja.setPesoKg(anteriorHoja.getPesoKg());
			nuevaHoja.setTallaCm(anteriorHoja.getTallaCm());
			nuevaHoja.setTemperaturac(anteriorHoja.getTemperaturac());
			nuevaHoja.setEstudiosParticipantes(estudios);
			
			//Se guarda la hora enfermeria cuando se crea la observacion
			nuevaHoja.setHorasv(horaEnfermeria);
			//Se estaba setienando el anteriorHoja.getUsuarioMedico() se cambio a null
			nuevaHoja.setUsuarioMedico(null);
			
			//estos campos deben quedar en blanco Dr. Ojeda 29112018
			//nuevaHoja.setPas(anteriorHoja.getPas());
			//nuevaHoja.setPad(anteriorHoja.getPad());
			//nuevaHoja.setPresion(anteriorHoja.getPresion());			
			//nuevaHoja.setPas(anteriorHoja.getPas());
			//nuevaHoja.setPad(anteriorHoja.getPad());
			//nuevaHoja.setFciaCard(anteriorHoja.getFciaCard());
			//nuevaHoja.setFciaResp(anteriorHoja.getFciaResp());
			nuevaHoja.setLugarAtencion(anteriorHoja.getLugarAtencion());
			nuevaHoja.setConsulta("Seguimiento");//siempre debe ser seguimiento. Dr. Ojeda 29112018
			nuevaHoja.setSegChick(anteriorHoja.getSegChick());
			nuevaHoja.setTurno(anteriorHoja.getTurno());
			//nuevaHoja.setTemMedc(anteriorHoja.getTemMedc());
			nuevaHoja.setFis(anteriorHoja.getFis());
			nuevaHoja.setFif(anteriorHoja.getFif());
			//nuevaHoja.setUltDiaFiebre(anteriorHoja.getUltDiaFiebre());
			//nuevaHoja.setUltDosisAntipiretico(anteriorHoja.getUltDosisAntipiretico());
			//nuevaHoja.setAmPmUltDiaFiebre(anteriorHoja.getAmPmUltDiaFiebre());
			//nuevaHoja.setHoraUltDosisAntipiretico(anteriorHoja.getHoraUltDosisAntipiretico());
			//nuevaHoja.setAmPmUltDosisAntipiretico(anteriorHoja.getAmPmUltDosisAntipiretico());
			nuevaHoja.setEstado('6');
			
		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw e;
		} 
		return nuevaHoja;
	}


}
