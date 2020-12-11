package com.sts_ni.estudiocohortecssfv.datos.enfermeria;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ni.com.sts.estudioCohorteCSSFV.modelo.EstudioCatalogo;

import org.hibernate.Query;

import com.sts_ni.estudiocohortecssfv.servicios.EnfermeriaService;
import com.sts_ni.estudiocohortecssfv.util.HibernateResource;
import com.sts_ni.estudiocohortecssfv.util.Mensajes;
import com.sts_ni.estudiocohortecssfv.util.UtilResultado;

/***
 * Clase para controlar los procesos de datos realacionados a Enfermeria
 *
 */
public class EnfermeriaDA implements EnfermeriaService {

	private static final HibernateResource HIBERNATE_RESOURCE = new HibernateResource();
	
	/***
	 * Metodo para obtener los datos preclinicos
	 * @param secHojaConsulta, Id Hoja Consulta
	 */
	@Override
	public String getDatosPreclinicos(Integer secHojaConsulta) {
		String result = null;
		try {
			List oLista = new LinkedList(); // Listado final para el resultado
			Map fila = null; // Objeto para cada registro recuperado

			String sql = "select h.expedienteFisico, p.edad, p.sexo, h.pesoKg, " + 
					" h.tallaCm, h.temperaturac, " + 
					" h.usuarioMedico, " +
					" h.consultaRespiratorio " + 
					" from HojaConsulta h, Paciente p " + 
					" where h.codExpediente = p.codExpediente " + 
					" and h.secHojaConsulta = :secHojaConsulta ";

			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			query.setParameter("secHojaConsulta", secHojaConsulta);

			Object[] hojaConsultaPaciente = (Object[]) query.uniqueResult();

			if (hojaConsultaPaciente != null && hojaConsultaPaciente.length > 0) {

				fila = new HashMap();
				fila.put("expedienteFisico", hojaConsultaPaciente[0]);
				fila.put("edad", hojaConsultaPaciente[1]);
				fila.put("sexo", hojaConsultaPaciente[2]);
				fila.put("pesoKg", hojaConsultaPaciente[3]);
				fila.put("tallaCm", hojaConsultaPaciente[4]);
				fila.put("temperaturac", hojaConsultaPaciente[5]);
				if(hojaConsultaPaciente[6] != null) {
					fila.put("usuarioMedico", hojaConsultaPaciente[6]);
				}
				if(hojaConsultaPaciente[7] != null) {
					fila.put("consultaRespiratorio", hojaConsultaPaciente[7]);
				}

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

}
