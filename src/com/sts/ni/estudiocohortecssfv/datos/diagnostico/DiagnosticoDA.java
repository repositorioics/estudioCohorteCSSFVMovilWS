package com.sts.ni.estudiocohortecssfv.datos.diagnostico;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ni.com.sts.estudioCohorteCSSFV.modelo.ConsEstudios;
import ni.com.sts.estudioCohorteCSSFV.modelo.Diagnostico;
import ni.com.sts.estudioCohorteCSSFV.modelo.EscuelaCatalogo;
import ni.com.sts.estudioCohorteCSSFV.modelo.HojaConsulta;

import org.hibernate.Query;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sts_ni.estudiocohortecssfv.datos.controlcambios.ControlCambiosDA;
import com.sts_ni.estudiocohortecssfv.servicios.DiagnosticoService;
import com.sts_ni.estudiocohortecssfv.util.HibernateResource;
import com.sts_ni.estudiocohortecssfv.util.Mensajes;
import com.sts_ni.estudiocohortecssfv.util.UtilHojaConsulta;
import com.sts_ni.estudiocohortecssfv.util.UtilResultado;

/***
 * Clase que controla los proceso de datos relacionados a Diagnostico.
 *
 */
public class DiagnosticoDA implements DiagnosticoService {
	
	private static final HibernateResource HIBERNATE_RESOURCE = new HibernateResource();
	private static String QUERY_HOJA_CONSULTA_BY_ID = "select h from HojaConsulta h where h.secHojaConsulta = :id";
	
	/***
	 * Metodo para obtener todos los Diagnosticos.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getListaDiagnostico(){
		String result = null;
		try {
            List  oLista = new LinkedList(); //Listado final para el resultado
            Map fila   = null;             //Objeto para cada registro recuperado

            String sql = "select d " +
                         "from Diagnostico d ";
            
            	sql += "where d.estado = '1' order by d.diagnostico ";

            Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
          
            List<Diagnostico> objLista = query.list();

            if (objLista != null && objLista.size() > 0) {

                for (Diagnostico diagnostico : objLista) {

                    // Construir la fila del registro actual
                    fila = new HashMap();
                    fila.put("secDiagnostico", diagnostico.getSecDiagnostico());
                    fila.put("codigoDiagnostico", diagnostico.getCodigoDignostico());
                    fila.put("diagnostico", diagnostico.getDiagnostico());
                    fila.put("estado", diagnostico.getEstado());

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
	 * Metodo para guardar examen historico.
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarExamenHistoricoHojaConsulta(String paramHojaConsulta) {
		String result = null;
		try {
			int secHojaConsulta;

			String historiaExamenFisico;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();

			historiaExamenFisico = ((hojaConsultaJSON
					.get("historiaExamenFisico").toString()));
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			if(hojaConsulta.getHistoriaExamenFisico() != null && !hojaConsulta.getHistoriaExamenFisico().isEmpty()) {
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				ctrlCambiosDA.guardarCtrlHistExamenFisico(hojaConsulta, usuarioLogiado);
			}

			hojaConsulta.setSecHojaConsulta(secHojaConsulta);

			hojaConsulta.setHistoriaExamenFisico(historiaExamenFisico);
			
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
	 * Metodo para guardar tratamiento
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarTratamientoHojaConsulta(String paramHojaConsulta) {
		String result = null;
		try {
			int secHojaConsulta;

			Character Acetaminofen;
			Character ASA;
			Character ibuprofen;
			Character penicilina;
			Character amoxicilina;
			Character dicloxacilina;
			Character otro;
			String otroAntibiotico;
			Character furazolidona;
			Character metronidazolTinidazol;
			Character albendazolMebendazol;
			Character sulfatoFerroso;
			Character sueroOral;
			Character sulfatoZinc;
			Character liquidosIv;
			Character prednisona;
			Character hidrocortisonaIv;
			Character salbutamol;
			Character oseltamivir;
			String planes;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			Acetaminofen = hojaConsultaJSON.get("acetaminofen").toString()
					.charAt(0);
			ASA = hojaConsultaJSON.get("asa").toString().charAt(0);
			ibuprofen = hojaConsultaJSON.get("ibuprofen").toString().charAt(0);
			penicilina = hojaConsultaJSON.get("penicilina").toString()
					.charAt(0);
			amoxicilina = hojaConsultaJSON.get("amoxicilina").toString()
					.charAt(0);
			dicloxacilina = hojaConsultaJSON.get("dicloxacilina").toString()
					.charAt(0);
			otroAntibiotico = hojaConsultaJSON.get("otroAntibiotico")
					.toString();
			otro = hojaConsultaJSON.get("otro").toString().charAt(0);
			furazolidona = hojaConsultaJSON.get("furazolidona").toString()
					.charAt(0);
			metronidazolTinidazol = hojaConsultaJSON
					.get("metronidazolTinidazol").toString().charAt(0);
			albendazolMebendazol = hojaConsultaJSON.get("albendazolMebendazol")
					.toString().charAt(0);
			sulfatoFerroso = hojaConsultaJSON.get("sulfatoFerroso").toString()
					.charAt(0);
			sueroOral = hojaConsultaJSON.get("sueroOral").toString().charAt(0);
			sulfatoZinc = hojaConsultaJSON.get("sulfatoZinc").toString()
					.charAt(0);
			liquidosIv = hojaConsultaJSON.get("liquidosIv").toString()
					.charAt(0);
			prednisona = hojaConsultaJSON.get("prednisona").toString()
					.charAt(0);
			hidrocortisonaIv = hojaConsultaJSON.get("hidrocortisonaIv")
					.toString().charAt(0);
			salbutamol = hojaConsultaJSON.get("salbutamol").toString()
					.charAt(0);
			oseltamivir = hojaConsultaJSON.get("oseltamivir").toString()
					.charAt(0);

			planes = (hojaConsultaJSON.containsKey("planes")) ? hojaConsultaJSON
					.get("planes").toString() : "";
					
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();		

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			//agregando control de cambios
			if(UtilHojaConsulta.tratamientoPlanesCompletada(hojaConsulta)) {				
				HojaConsulta hcNueva = new HojaConsulta();				
				
				hcNueva.setAcetaminofen(Acetaminofen);
				hcNueva.setAsa(ASA);
				hcNueva.setIbuprofen(ibuprofen);
				hcNueva.setPenicilina(penicilina);
				hcNueva.setAmoxicilina(amoxicilina);
				hcNueva.setDicloxacilina(dicloxacilina);
				hcNueva.setOtroAntibiotico(otroAntibiotico);
				hcNueva.setOtro(otro);
				hcNueva.setFurazolidona(furazolidona);
				hcNueva.setMetronidazolTinidazol(metronidazolTinidazol);
				hcNueva.setAlbendazolMebendazol(albendazolMebendazol);
				hcNueva.setSulfatoFerroso(sulfatoFerroso);
				hcNueva.setSueroOral(sueroOral);
				hcNueva.setSulfatoZinc(sulfatoZinc);
				hcNueva.setLiquidosIv(liquidosIv);
				hcNueva.setPrednisona(prednisona);
				hcNueva.setHidrocortisonaIv(hidrocortisonaIv);
				hcNueva.setSalbutamol(salbutamol);
				hcNueva.setOseltamivir(oseltamivir);
				hcNueva.setPlanes(planes);
				
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				ctrlCambiosDA.guardarCtrlTratamiento(hojaConsulta, hcNueva, usuarioLogiado);
			}
			
			hojaConsulta.setSecHojaConsulta(secHojaConsulta);
			hojaConsulta.setAcetaminofen(Acetaminofen);
			hojaConsulta.setAsa(ASA);
			hojaConsulta.setIbuprofen(ibuprofen);
			hojaConsulta.setPenicilina(penicilina);
			hojaConsulta.setAmoxicilina(amoxicilina);
			hojaConsulta.setDicloxacilina(dicloxacilina);
			hojaConsulta.setOtroAntibiotico(otroAntibiotico);
			hojaConsulta.setOtro(otro);
			hojaConsulta.setFurazolidona(furazolidona);
			hojaConsulta.setMetronidazolTinidazol(metronidazolTinidazol);
			hojaConsulta.setAlbendazolMebendazol(albendazolMebendazol);
			hojaConsulta.setSulfatoFerroso(sulfatoFerroso);
			hojaConsulta.setSueroOral(sueroOral);
			hojaConsulta.setSulfatoZinc(sulfatoZinc);
			hojaConsulta.setLiquidosIv(liquidosIv);
			hojaConsulta.setPrednisona(prednisona);
			hojaConsulta.setHidrocortisonaIv(hidrocortisonaIv);
			hojaConsulta.setSalbutamol(salbutamol);
			hojaConsulta.setOseltamivir(oseltamivir);
			hojaConsulta.setPlanes(planes);
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
	 * Metodo para guardar Planes
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarPlanesHojaConsulta(String paramHojaConsulta) {
		String result = null;
		try {
			int secHojaConsulta;

			String planes;
			String usuarioLogiado;
			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();

			planes = ((hojaConsultaJSON.get("planes").toString()));
			usuarioLogiado = ((hojaConsultaJSON.get("usuarioLogiado").toString()));			

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			//agregando control de cambios
			if(hojaConsulta.getPlanes() != null && !hojaConsulta.getPlanes().isEmpty()) {
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				ctrlCambiosDA.guardarCtrlPlanesHC(hojaConsulta, usuarioLogiado);
			}

			hojaConsulta.setSecHojaConsulta(secHojaConsulta);

			hojaConsulta.setPlanes(planes);
			

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
	 * Metodo para guardar Diagnosticos
	 * @param paramHojaConsulta, JSON
	 */
	@Override
	public String guardarDiagnosticoHojaConsulta(String paramHojaConsulta) {
		String result = null;
		try {
			int secHojaConsulta;

			Short diagnostico1;
			Short diagnostico2;
			Short diagnostico3;
			Short diagnostico4;
			String otroDiagnostico;
			String usuarioLogiado;

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta"))
					.intValue();
			diagnostico1 = (((Number) hojaConsultaJSON.get("diagnostico1"))
					.shortValue());
			diagnostico2 = (((Number) hojaConsultaJSON.get("diagnostico2"))
					.shortValue());
			diagnostico3 = (((Number) hojaConsultaJSON.get("diagnostico3"))
					.shortValue());
			diagnostico4 = (((Number) hojaConsultaJSON.get("diagnostico4"))
					.shortValue());
			otroDiagnostico = hojaConsultaJSON.get("otroDiagnostico")
					.toString();
			
			usuarioLogiado = hojaConsultaJSON.get("usuarioLogiado").toString();

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(
					QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", secHojaConsulta);

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
			
			//agregando control de cambios
			if(UtilHojaConsulta.diagnosticoCompletada(hojaConsulta)) {
				
				HashMap<Integer, String> lstDiagnosticos = new HashMap<Integer, String>();
				
				String sql = "select d " +
                        " from Diagnostico d " +
                        " where d.secDiagnostico = :id ";
				
				query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
				query.setParameter("id", diagnostico1.intValue());
				
				Diagnostico diagnostico = ((Diagnostico) query.uniqueResult());
				if(hojaConsulta.getDiagnostico1().intValue() != diagnostico1.intValue()) {
					lstDiagnosticos.put(1, diagnostico.getDiagnostico());
				}
				
				
				if(diagnostico2 != null) {
					if(hojaConsulta.getDiagnostico2().intValue() != diagnostico2.intValue()) {
						sql = "select d " +
	                        " from Diagnostico d " +
	                        " where d.secDiagnostico = :id ";
					
						query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
						query.setParameter("id", diagnostico2.intValue());
					
						diagnostico = ((Diagnostico) query.uniqueResult());
						
						if(diagnostico != null && diagnostico.getDiagnostico() != null){
							lstDiagnosticos.put(2, diagnostico.getDiagnostico());
						}
					}
				}
				
				if(diagnostico3 != null) {
					if(hojaConsulta.getDiagnostico3().intValue() != diagnostico3.intValue()) {
						sql = "select d " +
		                        " from Diagnostico d " +
		                        " where d.secDiagnostico = :id ";
						
						query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
						query.setParameter("id", diagnostico3.intValue());
						
						diagnostico = ((Diagnostico) query.uniqueResult());
						
						if(diagnostico != null && diagnostico.getDiagnostico() != null){
							lstDiagnosticos.put(3, diagnostico.getDiagnostico());
						}
					}
				}
				
				if(diagnostico4 != null) {
					if(hojaConsulta.getDiagnostico4().intValue() != diagnostico4.intValue()) {
						sql = "select d " +
		                        " from Diagnostico d " +
		                        " where d.secDiagnostico = :id ";
						
						query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
						query.setParameter("id", diagnostico4.intValue());
						
						diagnostico = ((Diagnostico) query.uniqueResult());
						
						if(diagnostico != null && diagnostico.getDiagnostico() != null){
							lstDiagnosticos.put(4, diagnostico.getDiagnostico());
						}
					}
				}
				
				if(otroDiagnostico != null) {
					if(hojaConsulta.getOtroDiagnostico().compareTo(otroDiagnostico) != 0) {
						lstDiagnosticos.put(5, otroDiagnostico);
					}
				}
				
				
				ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
				ctrlCambiosDA.guardarCtrlDiagnosticoHC(hojaConsulta, lstDiagnosticos, 
						usuarioLogiado);
			}
			
			hojaConsulta.setSecHojaConsulta(secHojaConsulta);
			hojaConsulta.setDiagnostico1(diagnostico1);
			hojaConsulta.setDiagnostico2(diagnostico2);
			hojaConsulta.setDiagnostico3(diagnostico3);
			hojaConsulta.setDiagnostico4(diagnostico4);
			hojaConsulta.setOtroDiagnostico(otroDiagnostico);
			
			
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
	 * Metodo para guardar ProximaCita
	 * @param paramHojaConsulta, JSON
	 */
	 @Override
	 public String guardarProximaCitaHojaConsulta(String paramHojaConsulta){
	 
	  String result= null;
	  try {
	   int secHojaConsulta;
	  
	   String telef;
	   String proximaCita;
	   String colegio;
	   String horarioClases;
	   String usuarioLogiado;
	   
	   JSONParser parser = new JSONParser();
	   Object obj = (Object) parser.parse(paramHojaConsulta);
	            JSONObject hojaConsultaJSON = (JSONObject) obj;
	            
	  secHojaConsulta = ((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue();
	  
	  telef = (hojaConsultaJSON.containsKey("telef")) ?
			  hojaConsultaJSON.get("telef").toString() : null;
	  proximaCita = (hojaConsultaJSON.containsKey("proximaCita")) ?
			  hojaConsultaJSON.get("proximaCita").toString() : null;
	  colegio = (hojaConsultaJSON.containsKey("colegio")) ? 
			  hojaConsultaJSON.get("colegio").toString() : null;
	  horarioClases = (hojaConsultaJSON.containsKey("horarioClases")) ?  
			  hojaConsultaJSON.get("horarioClases").toString() : null;
	  usuarioLogiado = (((String) hojaConsultaJSON.get("usuarioLogiado")));
	            
	  Query query = HIBERNATE_RESOURCE.getSession().createQuery(QUERY_HOJA_CONSULTA_BY_ID);
	  query.setParameter("id", secHojaConsulta);
	            
	   HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
	   hojaConsulta.setSecHojaConsulta(secHojaConsulta);
	   
	   //agregando control de cambios
	   HojaConsulta hjC = new HojaConsulta();
	   
	   if(proximaCita != null && !proximaCita.isEmpty()) {
		   hjC.setProximaCita(new SimpleDateFormat("dd/MM/yyyy").parse(proximaCita));
	   }
	   if(horarioClases != null && !horarioClases.isEmpty()) {
		   hjC.setHorarioClases(horarioClases);
	   }
	   if(telef != null && telef.trim().length() > 0 && !telef.equalsIgnoreCase("null") ){
		   hjC.setTelef(Long.parseLong(telef));
	   }
	   
	   ControlCambiosDA ctrlCambiosDA = new ControlCambiosDA();
	   ctrlCambiosDA.guardarCtrlProxCita(hojaConsulta, hjC, usuarioLogiado);
	   
	   if(telef != null && telef.trim().length() > 0 && !telef.equalsIgnoreCase("null") ){
		   hojaConsulta.setTelef(Long.parseLong(telef));
	   }else{
		   hojaConsulta.setTelef(null);
	   }
	   
	   if(proximaCita != null && !proximaCita.isEmpty()) {
		   hojaConsulta.setProximaCita(new SimpleDateFormat("dd/MM/yyyy").parse(proximaCita));
	   }
	   if(colegio != null && !colegio.isEmpty()) {
		   hojaConsulta.setColegio(colegio);
	   }
	   if(horarioClases != null && !horarioClases.isEmpty()) {
		   hojaConsulta.setHorarioClases(horarioClases);
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
	 
	 
	/***
	 * Metodo para buscar el paciente por el codigo expediente
	 * @param codExpediente, Codigo Expediente
	 */
	@Override
	public String obtenerDatosPaciente(int codExpediente) {
			String result = null;
			try {
				List oLista = new LinkedList();
				Map fila = null;

				String sql = "select p.secPaciente,ec.codEscuela ,ec.descripcion  from Paciente p, EscuelaCatalogo ec where p.escuela=ec.codEscuela and  p.codExpediente = :codExpediente";

				Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
				query.setParameter("codExpediente", codExpediente);

				Object[] objLista = (Object[]) query.uniqueResult();

				if (objLista != null && objLista.length > 0) {
						// Construir la fila del registro actual usando arreglos
						fila = new HashMap();
						fila.put("secPaciente", objLista[0]);
						fila.put("codEscuela", objLista[1]);
						fila.put("descripcion", objLista[2]);
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
	 * Metodo para obtener todas las escuelas
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getTodasEscuela() {
			String result = null;
			try {
	            List  oLista = new LinkedList(); //Listado final para el resultado
	            Map fila   = null;             //Objeto para cada registro recuperado

	            String sql = "select e " +
	                         "from EscuelaCatalogo e ";
	            
	            sql += "order by e.descripcion asc";

	            Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
	          
	            List<EscuelaCatalogo> objLista = query.list();

	            if (objLista != null && objLista.size() > 0) {

	                for (EscuelaCatalogo escuela : objLista) {

	                    // Construir la fila del registro actual
	                    fila = new HashMap();
	                    fila.put("codEscuela", escuela.getCodEscuela());
	                    fila.put("descripcion", escuela.getDescripcion());

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
	 * Metodo para obtener la secciones ya ingresadas
	 */
		@SuppressWarnings("unchecked")
		@Override
	public String getSeccionesDiagnosticoCompletadas(String paramHojaConsulta) {
		String result = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONObject hojaConsultaJSON = (JSONObject) obj;

			List oLista = new LinkedList(); // Listado final para el resultado
			Map fila = null;

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(QUERY_HOJA_CONSULTA_BY_ID);
			query.setParameter("id", ((Number) hojaConsultaJSON.get("secHojaConsulta")).intValue());

			HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());

			fila = new HashMap();
			fila.put("esHistorialExamenesCompletada", UtilHojaConsulta.historialExamenCompletada(hojaConsulta));
			fila.put("esTratamientoPlanesCompletada", UtilHojaConsulta.tratamientoPlanesCompletada(hojaConsulta));
			fila.put("esDiagnosticoCompletada", UtilHojaConsulta.diagnosticoCompletada(hojaConsulta));
			fila.put("esProximaCitaCompletada", UtilHojaConsulta.proximaCitaCompletada(hojaConsulta));

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
	
	//Metodo para activar los diagnosticos en la hoja de consulta cambio 07/11/2019 - SC
	public String activarDiagnosticos(int secHojaConsulta) {
		String result = null;
		try {
			//JSONParser parser = new JSONParser();
	        //JSONObject hojaConsultaJSON = (JSONObject)((Object) parser.parse(paramHojaConsulta));
	        
	        Query query = HIBERNATE_RESOURCE.getSession().createQuery(QUERY_HOJA_CONSULTA_BY_ID);
	        query.setParameter("id", secHojaConsulta);
	        
	        HojaConsulta hojaConsulta = ((HojaConsulta) query.uniqueResult());
	        
	        if(UtilHojaConsulta.validarSeccionesParaActivarDiagnostico(hojaConsulta)) {
	        	result = UtilResultado.parserResultado(null, "", UtilResultado.OK);
	        }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}
}
