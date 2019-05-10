package com.sts_ni.estudiocohortecssfv.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ni.com.sts.estudioCohorteCSSFV.modelo.HojaConsulta;

public class UtilHojaConsulta {
	
	public static boolean validarCasoA(HojaConsulta hojaConsulta) {
		try {
			int contador = 0;
	
			if (hojaConsulta.getMialgia() != null &&
				hojaConsulta.getMialgia().compareTo('0') == 0) {
				contador++;
			} if (hojaConsulta.getAltralgia()!= null &&
				hojaConsulta.getAltralgia().compareTo('0') == 0) {
				contador++;
			} if (hojaConsulta.getNausea()!= null &&
				hojaConsulta.getNausea().compareTo('0') == 0) {
				contador++;
			} if (hojaConsulta.getRahsLocalizado() != null &&
					hojaConsulta.getRahsLocalizado().compareTo('0') == 0) {
					contador++;
			} if (hojaConsulta.getRashEritematoso() != null &&
					hojaConsulta.getRashEritematoso().compareTo('0') == 0) {
					contador++;
			} if (hojaConsulta.getRahsMoteada() != null &&
				hojaConsulta.getRahsMoteada().compareTo('0') == 0) {
				contador++;
			} if (hojaConsulta.getRahsMacular()!= null &&
				hojaConsulta.getRahsMacular().compareTo('0') == 0) {
				contador++;
			} if (hojaConsulta.getRashPapular() != null &&
				hojaConsulta.getRashPapular().compareTo('0') == 0) {
				contador++;
			} if (hojaConsulta.getPetequia10Pt() != null &&
				hojaConsulta.getPetequia10Pt().compareTo('0') == 0) {
				contador++;
			} if (hojaConsulta.getPetequia20Pt() != null &&
				hojaConsulta.getPetequia20Pt().compareTo('0') == 0) {
				contador++;
			} if (hojaConsulta.getPetequiasMucosa() != null &&
				hojaConsulta.getPetequiasMucosa().compareTo('0') == 0) {
				contador++;
			} if (hojaConsulta.getPetequiasEspontaneas() != null &&
				hojaConsulta.getPetequiasEspontaneas().compareTo('0') == 0) {
				contador++;
			} if (hojaConsulta.getCefalea() != null &&
				hojaConsulta.getCefalea().compareTo('0') == 0) {
				contador++;
			}
	
			if (contador > 1) {
				return true;
			}
		} catch (NullPointerException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}

		return false;
	}
	
	public static boolean validarCasoB(HojaConsulta hojaConsulta) {
		try {
			if( hojaConsulta.getAstenia().compareTo('0') != 0
					&& hojaConsulta.getAsomnoliento().compareTo('0') != 0
					&& hojaConsulta.getPerdidaConsciencia().compareTo('0') != 0
					&& hojaConsulta.getInquieto().compareTo('0') != 0
					&& hojaConsulta.getConvulsiones().compareTo('0') != 0
					&& hojaConsulta.getHipotermia().compareTo('0') != 0
					&& hojaConsulta.getLetargia().compareTo('0') != 0 //Estado General
					&& hojaConsulta.getPocoApetito().compareTo('0') != 0
					&& hojaConsulta.getNausea().compareTo('0') != 0
					&& hojaConsulta.getDificultadAlimentarse().compareTo('0') != 0
					&& hojaConsulta.getVomito12horas().compareTo('0') != 0
					&& hojaConsulta.getDiarrea().compareTo('0') != 0
					&& hojaConsulta.getDiarreaSangre().compareTo('0') != 0
					&& hojaConsulta.getEstrenimiento().compareTo('0') != 0
					&& hojaConsulta.getDolorAbContinuo().compareTo('0') != 0
					&& hojaConsulta.getDolorAbIntermitente().compareTo('0') != 0
					&& hojaConsulta.getEpigastralgia().compareTo('0') != 0
					&& hojaConsulta.getIntoleranciaOral().compareTo('0') != 0
					&& hojaConsulta.getDistensionAbdominal().compareTo('0') != 0
					&& hojaConsulta.getHepatomegalia().compareTo('0') != 0 //Gastroinstestinal
					&& hojaConsulta.getAltralgia().compareTo('0') != 0
					&& hojaConsulta.getMialgia().compareTo('0') != 0
					&& hojaConsulta.getLumbalgia().compareTo('0') != 0
					&& hojaConsulta.getDolorCuello().compareTo('0') != 0
					&& hojaConsulta.getTenosinovitis().compareTo('0') != 0
					&& hojaConsulta.getArtralgiaDistal().compareTo('0') != 0
					&& hojaConsulta.getArtralgiaProximal().compareTo('0') != 0
					&& hojaConsulta.getConjuntivitis().compareTo('0') != 0
					&& hojaConsulta.getEdemaMunecas().compareTo('0') != 0
					&& hojaConsulta.getEdemaCodos().compareTo('0') != 0
					&& hojaConsulta.getEdemaHombros().compareTo('0') != 0
					&& hojaConsulta.getEdemaRodillas().compareTo('0') != 0
					&& hojaConsulta.getEdemaTobillos().compareTo('0') != 0 //osteomuscular
					&& hojaConsulta.getCefalea().compareTo('0') != 0
					&& hojaConsulta.getRigidezCuello().compareTo('0') != 0
					&& hojaConsulta.getInyeccionConjuntival().compareTo('0') != 0
					&& hojaConsulta.getHemorragiaSuconjuntival().compareTo('0') != 0
					&& hojaConsulta.getDolorRetroocular().compareTo('0') != 0
					&& hojaConsulta.getFontanelaAbombada().compareTo('0') != 0
					&& hojaConsulta.getIctericiaConuntival().compareTo('0') != 0 // Cabeza
					&& hojaConsulta.getLenguaMucosasSecas().compareTo('0') != 0
					&& hojaConsulta.getPliegueCutaneo().compareTo('0') != 0
					&& hojaConsulta.getOrinaReducida().compareTo('0') != 0
					&& hojaConsulta.getBebeConSed().compareTo('0') != 0
					&& hojaConsulta.getOjosHundidos().compareTo("0") != 0
					&& hojaConsulta.getFontanelaHundida().compareTo('0') != 0 //Deshidratacion
					&& hojaConsulta.getRahsLocalizado().compareTo('0') != 0
					&& hojaConsulta.getRahsGeneralizado().compareTo('0') != 0
					&& hojaConsulta.getRashEritematoso().compareTo('0') != 0
					&& hojaConsulta.getRahsMacular().compareTo('0') != 0
					&& hojaConsulta.getRashPapular().compareTo('0') != 0
					&& hojaConsulta.getRahsMoteada().compareTo('0') != 0
					&& hojaConsulta.getRuborFacial().compareTo('0') != 0
					&& hojaConsulta.getEquimosis().compareTo('0') != 0
					&& hojaConsulta.getCianosisCentral().compareTo('0') != 0
					&& hojaConsulta.getIctericia().compareTo('0') != 0 // Cutaneo
					&& hojaConsulta.getEritema().compareTo('0') != 0
					&& hojaConsulta.getDolorGarganta().compareTo('0') != 0
					&& hojaConsulta.getAdenopatiasCervicales().compareTo('0') != 0
					&& hojaConsulta.getExudado().compareTo('0') != 0
					&& hojaConsulta.getPetequiasMucosa().compareTo('0') != 0 //Garganta
					&& hojaConsulta.getSintomasUrinarios().compareTo('0') != 0
					&& hojaConsulta.getLeucocituria().compareTo('0') != 0
					&& hojaConsulta.getNitritos().compareTo('0') != 0
					&& hojaConsulta.getEritrocitos().compareTo('0') != 0
					&& hojaConsulta.getBilirrubinuria().compareTo('0') != 0 //Renal
					&& hojaConsulta.getObeso().compareTo('0') != 0
					&& hojaConsulta.getSobrepeso().compareTo('0') != 0
					&& hojaConsulta.getSospechaProblema().compareTo('0') != 0
					&& hojaConsulta.getNormal().compareTo('0') != 0
					&& hojaConsulta.getBajoPeso().compareTo('0') != 0
					&& hojaConsulta.getBajoPesoSevero().compareTo('0') != 0 // Estado Nutricional
					&& hojaConsulta.getTos().compareTo('0') != 0
					&& hojaConsulta.getRinorrea().compareTo('0') != 0
					&& hojaConsulta.getCongestionNasal().compareTo('0') != 0
					&& hojaConsulta.getOtalgia().compareTo('0') != 0
					&& hojaConsulta.getAleteoNasal().compareTo('0') != 0
					&& hojaConsulta.getApnea().compareTo('0') != 0
					&& hojaConsulta.getRespiracionRapida().compareTo('0') != 0
					&& hojaConsulta.getQuejidoEspiratorio().compareTo('0') != 0
					&& hojaConsulta.getEstiradorReposo().compareTo('0') != 0
					&& hojaConsulta.getTirajeSubcostal().compareTo('0') != 0
					&& hojaConsulta.getSibilancias().compareTo('0') != 0
					&& hojaConsulta.getCrepitos().compareTo('0') != 0
					&& hojaConsulta.getRoncos().compareTo('0') != 0
					&& hojaConsulta.getOtraFif().compareTo('0') != 0 // Respiratorio
					&& hojaConsulta.getInterconsultaPediatrica().compareTo('0') != 0
					&& hojaConsulta.getReferenciaHospital().compareTo('0') != 0
					&& hojaConsulta.getReferenciaDengue().compareTo('0') != 0
					&& hojaConsulta.getReferenciaIrag().compareTo('0') != 0
					&& hojaConsulta.getReferenciaChik().compareTo('0') != 0
					&& hojaConsulta.getEti().compareTo('0') != 0
					&& hojaConsulta.getIrag().compareTo('0') != 0
					&& hojaConsulta.getNeumonia().compareTo('0') != 0 // Referencia
					&& hojaConsulta.getManifestacionHemorragica().compareTo('0') != 0
					&& hojaConsulta.getPruebaTorniquetePositiva().compareTo('0') != 0
	    			&& hojaConsulta.getPetequia10Pt().compareTo('0') != 0
	    			&& hojaConsulta.getPetequia20Pt().compareTo('0') != 0
	    			&& hojaConsulta.getPielExtremidadesFrias().compareTo('0') != 0
	    			&& hojaConsulta.getPalidezEnExtremidades().compareTo('0') != 0
	    			&& hojaConsulta.getEpistaxis().compareTo('0') != 0
	    			&& hojaConsulta.getGingivorragia().compareTo('0') != 0
	    			&& hojaConsulta.getPetequiasEspontaneas().compareTo('0') != 0
	    			&& hojaConsulta.getLlenadoCapilar2seg().compareTo('0') != 0
	    			&& hojaConsulta.getCianosis().compareTo('0') != 0
	    			&& hojaConsulta.getHipermenorrea().compareTo('0') != 0
	    			&& hojaConsulta.getHematemesis().compareTo('0') != 0
	    			&& hojaConsulta.getMelena().compareTo('0') != 0) {
				return true;
			}
		} catch (NullPointerException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		return false;
	}
	
	public static boolean validarCasoETI(HojaConsulta hojaConsulta) {
		try {
			if((hojaConsulta.getRinorrea() != null
					&& hojaConsulta.getRinorrea().compareTo('0') == 0)
				|| (hojaConsulta.getDolorGarganta() != null
						&& hojaConsulta.getDolorGarganta().compareTo('0') == 0)
				|| (hojaConsulta.getTos() != null
						&& hojaConsulta.getTos().compareTo('0') == 0)) {
				return true;
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			
		}
		
		return false;
	}
	
	public static boolean validarCasoIRAG(HojaConsulta hojaConsulta) {
		
		try {
			if((hojaConsulta.getEstiradorReposo() != null
					&& hojaConsulta.getEstiradorReposo().compareTo('0') == 0)
				|| (hojaConsulta.getAleteoNasal() != null
						&& hojaConsulta.getAleteoNasal().compareTo('0') == 0)
				|| (hojaConsulta.getApnea() != null
						&& hojaConsulta.getApnea().compareTo('0') == 0)
				|| (hojaConsulta.getQuejidoEspiratorio() != null
						&& hojaConsulta.getQuejidoEspiratorio().compareTo('0') == 0)
				|| (hojaConsulta.getTirajeSubcostal() != null
						&& hojaConsulta.getTirajeSubcostal().compareTo('0') == 0)
				|| (hojaConsulta.getCianosisCentral() != null
						&& hojaConsulta.getCianosisCentral().compareTo('0') == 0)) {
				return true;
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
		}
		
		return false;
	}
	
	public static boolean validarCasoNeumonia(HojaConsulta hojaConsulta) {
		if((hojaConsulta.getTos() != null 
				&& hojaConsulta.getTos().compareTo('0') == 0)
				&& hojaConsulta.getCrepitos().compareTo('0') == 0) {
			return true;
		}
		
		if(hojaConsulta.getRespiracionRapida() != null 
				&& hojaConsulta.getRespiracionRapida().compareTo('0') == 0) {
			return true;
		}
		
		if(hojaConsulta.getCrepitos() != null 
				&& hojaConsulta.getCrepitos().compareTo('0') == 0) {
			return true;
		}
		
		return false;
	}
	
	
	public static boolean validarTodosSintomasMarcado(HojaConsulta hojaConsulta) {
		if( hojaConsulta.getAstenia() != null
				&& hojaConsulta.getAsomnoliento() != null
				&& hojaConsulta.getPerdidaConsciencia() != null
				&& hojaConsulta.getInquieto() != null
				&& hojaConsulta.getConvulsiones() != null
				&& hojaConsulta.getHipotermia() != null
				&& hojaConsulta.getLetargia() != null //Estado General
				&& hojaConsulta.getPocoApetito() != null
				&& hojaConsulta.getNausea() != null
				&& hojaConsulta.getDificultadAlimentarse() != null
				&& hojaConsulta.getVomito12horas() != null
				&& hojaConsulta.getDiarrea() != null
				&& hojaConsulta.getDiarreaSangre() != null
				&& hojaConsulta.getEstrenimiento() != null
				&& hojaConsulta.getDolorAbContinuo() != null
				&& hojaConsulta.getDolorAbIntermitente() != null
				&& hojaConsulta.getEpigastralgia() != null
				&& hojaConsulta.getIntoleranciaOral() != null
				&& hojaConsulta.getDistensionAbdominal() != null
				&& hojaConsulta.getHepatomegalia() != null //Gastroinstestinal
				&& hojaConsulta.getAltralgia() != null
				&& hojaConsulta.getMialgia() != null
				&& hojaConsulta.getLumbalgia() != null
				&& hojaConsulta.getDolorCuello() != null
				&& hojaConsulta.getTenosinovitis() != null
				&& hojaConsulta.getArtralgiaDistal() != null
				&& hojaConsulta.getArtralgiaProximal() != null
				&& hojaConsulta.getConjuntivitis() != null
				&& hojaConsulta.getEdemaMunecas() != null
				&& hojaConsulta.getEdemaCodos() != null
				&& hojaConsulta.getEdemaHombros() != null
				&& hojaConsulta.getEdemaRodillas() != null
				&& hojaConsulta.getEdemaTobillos() != null //osteomuscular
				&& hojaConsulta.getCefalea() != null
				&& hojaConsulta.getRigidezCuello() != null
				&& hojaConsulta.getInyeccionConjuntival() != null
				&& hojaConsulta.getHemorragiaSuconjuntival() != null
				&& hojaConsulta.getDolorRetroocular() != null
				&& hojaConsulta.getFontanelaAbombada() != null
				&& hojaConsulta.getIctericiaConuntival() != null // Cabeza
				&& hojaConsulta.getLenguaMucosasSecas() != null
				&& hojaConsulta.getPliegueCutaneo() != null
				&& hojaConsulta.getOrinaReducida() != null
				&& hojaConsulta.getBebeConSed() != null
				&& hojaConsulta.getOjosHundidos() != null
				&& hojaConsulta.getFontanelaHundida() != null //Deshidratacion
				&& hojaConsulta.getRahsLocalizado() != null
				&& hojaConsulta.getRahsGeneralizado() != null
				&& hojaConsulta.getRashEritematoso() != null
				&& hojaConsulta.getRahsMacular() != null
				&& hojaConsulta.getRashPapular() != null
				&& hojaConsulta.getRahsMoteada() != null
				&& hojaConsulta.getRuborFacial() != null
				&& hojaConsulta.getEquimosis() != null
				&& hojaConsulta.getCianosisCentral() != null
				&& hojaConsulta.getIctericia() != null // Cutaneo
				&& hojaConsulta.getEritema() != null
				&& hojaConsulta.getDolorGarganta() != null
				&& hojaConsulta.getAdenopatiasCervicales() != null
				&& hojaConsulta.getExudado() != null
				&& hojaConsulta.getPetequiasMucosa() != null //Garganta
				&& hojaConsulta.getSintomasUrinarios() != null
				&& hojaConsulta.getLeucocituria() != null
				&& hojaConsulta.getNitritos() != null
				&& hojaConsulta.getEritrocitos() != null
				&& hojaConsulta.getBilirrubinas() != null //Renal
				&& hojaConsulta.getObeso() != null
				&& hojaConsulta.getSobrepeso() != null
				&& hojaConsulta.getSospechaProblema() != null
				&& hojaConsulta.getNormal() != null
				&& hojaConsulta.getBajoPeso() != null
				&& hojaConsulta.getBajoPesoSevero() != null // Estado Nutricional
				&& hojaConsulta.getTos() != null
				&& hojaConsulta.getRinorrea() != null
				&& hojaConsulta.getCongestionNasal() != null
				&& hojaConsulta.getOtalgia() != null
				&& hojaConsulta.getAleteoNasal() != null
				&& hojaConsulta.getApnea() != null
				&& hojaConsulta.getRespiracionRapida() != null
				&& hojaConsulta.getQuejidoEspiratorio() != null
				&& hojaConsulta.getEstiradorReposo() != null
				&& hojaConsulta.getTirajeSubcostal() != null
				&& hojaConsulta.getSibilancias() != null
				&& hojaConsulta.getCrepitos() != null
				&& hojaConsulta.getRoncos() != null
				&& hojaConsulta.getOtraFif() != null // Respiratorio
				&& hojaConsulta.getInterconsultaPediatrica() != null
				&& hojaConsulta.getReferenciaHospital() != null
				&& hojaConsulta.getReferenciaDengue() != null
				&& hojaConsulta.getReferenciaIrag() != null
				&& hojaConsulta.getReferenciaChik() != null
				&& hojaConsulta.getEti() != null
				&& hojaConsulta.getIrag() != null
				&& hojaConsulta.getNeumonia() != null // Referencia
    			&& hojaConsulta.getCambioCategoria() != null
    			&& hojaConsulta.getCategoria() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean generalesCompletada(HojaConsulta hojaConsulta) {
		if( hojaConsulta.getPas() != null && 
			hojaConsulta.getPad() != null && 
			hojaConsulta.getFciaCard() != null && 
			hojaConsulta.getFciaResp() != null && 
			hojaConsulta.getLugarAtencion() != null && 
			hojaConsulta.getConsulta() != null && 
			hojaConsulta.getTurno() != null && 
			hojaConsulta.getTemMedc() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean estadoGeneralCompletada(HojaConsulta hojaConsulta) {
		if( hojaConsulta.getAstenia() != null && 
			hojaConsulta.getAsomnoliento() != null && 
			hojaConsulta.getPerdidaConsciencia() != null && 
			hojaConsulta.getInquieto() != null && 
			hojaConsulta.getConvulsiones() != null && 
			hojaConsulta.getHipotermia() != null && 
			hojaConsulta.getLetargia() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean gastrointestinalCompletada(HojaConsulta hojaConsulta) {
		if(hojaConsulta.getPocoApetito() != null && 
			hojaConsulta.getNausea() != null && 
			hojaConsulta.getDificultadAlimentarse() != null && 
			hojaConsulta.getDiarrea() != null && 
			hojaConsulta.getDiarreaSangre() != null && 
			hojaConsulta.getEstrenimiento() != null && 
			hojaConsulta.getDolorAbContinuo() != null && 
			hojaConsulta.getDolorAbIntermitente() != null && 
			hojaConsulta.getEpigastralgia() != null && 
			hojaConsulta.getIntoleranciaOral() != null && 
			hojaConsulta.getDistensionAbdominal() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean osteomuscularCompletada(HojaConsulta hojaConsulta) {
		if(hojaConsulta.getAltralgia() != null
				&& hojaConsulta.getMialgia() != null
				&& hojaConsulta.getLumbalgia() != null
				&& hojaConsulta.getDolorCuello() != null
				&& hojaConsulta.getTenosinovitis() != null
				&& hojaConsulta.getArtralgiaDistal() != null
				&& hojaConsulta.getArtralgiaProximal() != null
				&& hojaConsulta.getConjuntivitis() != null
				&& hojaConsulta.getEdemaMunecas() != null
				&& hojaConsulta.getEdemaCodos() != null
				&& hojaConsulta.getEdemaHombros() != null
				&& hojaConsulta.getEdemaRodillas() != null
				&& hojaConsulta.getEdemaTobillos() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean cabezaCompletada(HojaConsulta hojaConsulta) {
		if(hojaConsulta.getCefalea() != null && 
				hojaConsulta.getRigidezCuello() != null &&  
				hojaConsulta.getInyeccionConjuntival() != null &&  
				hojaConsulta.getHemorragiaSuconjuntival() != null && 
				hojaConsulta.getDolorRetroocular() != null && 
				hojaConsulta.getFontanelaAbombada() != null && 
				hojaConsulta.getIctericiaConuntival() != null) {
			return true;
		}
		
		return false;
	}
		
	public static boolean deshidratacionCompletada(HojaConsulta hojaConsulta) {
		if(hojaConsulta.getLenguaMucosasSecas() != null && 
				hojaConsulta.getPliegueCutaneo() != null && 
				hojaConsulta.getOrinaReducida() != null && 
				hojaConsulta.getBebeConSed() != null && 
				hojaConsulta.getOjosHundidos() != null && 
				hojaConsulta.getFontanelaHundida() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean cutaneoCompletada(HojaConsulta hojaConsulta) {
		if(hojaConsulta.getRahsLocalizado() != null && 
				hojaConsulta.getRahsGeneralizado() != null && 
				hojaConsulta.getRashEritematoso() != null && 
				hojaConsulta.getRahsMacular() != null && 
				hojaConsulta.getRashPapular() != null && 
				hojaConsulta.getRahsMoteada() != null && 
				hojaConsulta.getRuborFacial() != null && 
				hojaConsulta.getEquimosis() != null && 
				hojaConsulta.getCianosisCentral() != null && 
				hojaConsulta.getIctericia() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean gargantaCompletada(HojaConsulta hojaConsulta) {
		if(hojaConsulta.getEritema() != null && 
				hojaConsulta.getDolorGarganta() != null && 
				hojaConsulta.getAdenopatiasCervicales() != null && 
				hojaConsulta.getExudado() != null && 
				hojaConsulta.getPetequiasMucosa() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean renalCompletada(HojaConsulta hojaConsulta) {
		if(hojaConsulta.getSintomasUrinarios() != null && 
				hojaConsulta.getLeucocituria() != null && 
				hojaConsulta.getNitritos() != null && 
				hojaConsulta.getEritrocitos() != null && hojaConsulta.getBilirrubinuria() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean  estadoNutricionalCompletada(HojaConsulta hojaConsulta) {
		if(hojaConsulta.getObeso() != null && 
				hojaConsulta.getSobrepeso() != null && 
				hojaConsulta.getSospechaProblema() != null && 
				hojaConsulta.getNormal() != null && 
				hojaConsulta.getBajoPeso() != null && 
				hojaConsulta.getBajoPesoSevero() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean respiratorioCompletada(HojaConsulta hojaConsulta) {
		if(hojaConsulta.getTos() != null && 
				hojaConsulta.getRinorrea() != null && 
				hojaConsulta.getCongestionNasal() != null && 
				hojaConsulta.getOtalgia() != null && 
				hojaConsulta.getAleteoNasal() != null && 
				hojaConsulta.getApnea() != null && 
				hojaConsulta.getRespiracionRapida() != null && 
				hojaConsulta.getQuejidoEspiratorio() != null && 
				hojaConsulta.getEstiradorReposo() != null && 
				hojaConsulta.getTirajeSubcostal() != null && 
				hojaConsulta.getSibilancias() != null && 
				hojaConsulta.getCrepitos() != null && 
				hojaConsulta.getRoncos() != null && 
				hojaConsulta.getOtraFif() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean referenciaCompletada(HojaConsulta hojaConsulta) {
		if(hojaConsulta.getInterconsultaPediatrica() != null && 
				hojaConsulta.getReferenciaHospital() != null && 
				hojaConsulta.getReferenciaDengue() != null && 
				hojaConsulta.getReferenciaIrag() != null && 
				hojaConsulta.getReferenciaChik() != null && 
				hojaConsulta.getEti() != null && 
				hojaConsulta.getIrag() != null && 
				hojaConsulta.getNeumonia() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean vacunaCompletada(HojaConsulta hojaConsulta) {
		if(hojaConsulta.getLactanciaMaterna() != null && 
				hojaConsulta.getVacunasCompletas() != null && 
				hojaConsulta.getVacunaInfluenza() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean categoriaCompletada(HojaConsulta hojaConsulta) {
		if(hojaConsulta.getCategoria() != null ||
				hojaConsulta.getManifestacionHemorragica() != null ||
				hojaConsulta.getPruebaTorniquetePositiva() != null ||
				hojaConsulta.getPetequia10Pt() != null ||
				hojaConsulta.getPetequia20Pt() != null ||
				hojaConsulta.getPetequiasEspontaneas() != null ||
				hojaConsulta.getPielExtremidadesFrias() != null ||
				hojaConsulta.getPalidezEnExtremidades() != null ||
				hojaConsulta.getEpistaxis() != null  ||
				hojaConsulta.getGingivorragia() != null  ||
				hojaConsulta.getLlenadoCapilar2seg() != null  ||
				hojaConsulta.getCianosis() != null  ||
				hojaConsulta.getHipermenorrea() != null  ||
				hojaConsulta.getHematemesis() != null  ||
				hojaConsulta.getMelena() != null  ||
				hojaConsulta.getHemoconc() != null  ||
				hojaConsulta.getLinfocitosaAtipicos() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean historialExamenCompletada(HojaConsulta hojaConsulta) {
		if( hojaConsulta.getHistoriaExamenFisico() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean tratamientoPlanesCompletada(HojaConsulta hojaConsulta) {
		if( hojaConsulta.getAcetaminofen() != null
				&& hojaConsulta.getAsa() != null
				&& hojaConsulta.getIbuprofen() != null
				&& hojaConsulta.getPenicilina() != null
				&& hojaConsulta.getAmoxicilina() != null
				&& hojaConsulta.getDicloxacilina() != null
				&& hojaConsulta.getOtroAntibiotico() != null
				&& hojaConsulta.getFurazolidona() != null 
				&& hojaConsulta.getMetronidazolTinidazol() != null
				&& hojaConsulta.getAlbendazolMebendazol() != null
				&& hojaConsulta.getSulfatoFerroso() != null
				&& hojaConsulta.getSueroOral() != null
				&& hojaConsulta.getSulfatoZinc() != null
				&& hojaConsulta.getLiquidosIv() != null
				&& hojaConsulta.getPrednisona() != null
				&& hojaConsulta.getHidrocortisonaIv() != null
				&& hojaConsulta.getSalbutamol() != null
				&& hojaConsulta.getOseltamivir() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean diagnosticoCompletada(HojaConsulta hojaConsulta) {
		if( hojaConsulta.getDiagnostico1() != null) {
			return true;
		}
		
		return false;
	}
	
	public static boolean proximaCitaCompletada(HojaConsulta hojaConsulta) {
		if( hojaConsulta.getProximaCita() != null ||
				hojaConsulta.getTelef() != null ||
				hojaConsulta.getHorarioClases() != null) {
			return true;
		}
		
		return false;
	}

	public static boolean validarTodasSecciones(HojaConsulta hojaConsulta) {
		return generalesCompletada(hojaConsulta) && estadoGeneralCompletada(hojaConsulta) && 
				gastrointestinalCompletada(hojaConsulta) && osteomuscularCompletada(hojaConsulta) &&
				cabezaCompletada(hojaConsulta) && deshidratacionCompletada(hojaConsulta) &&
				cutaneoCompletada(hojaConsulta) && gargantaCompletada(hojaConsulta) &&
				renalCompletada(hojaConsulta) && estadoNutricionalCompletada(hojaConsulta) && 
				respiratorioCompletada(hojaConsulta) && referenciaCompletada(hojaConsulta) &&
				vacunaCompletada(hojaConsulta) && historialExamenCompletada(hojaConsulta) &&
				tratamientoPlanesCompletada(hojaConsulta) && diagnosticoCompletada(hojaConsulta) &&
				proximaCitaCompletada(hojaConsulta)
				//Nueva Linea de codigo agregada
				&& categoriaCompletada(hojaConsulta);
	}
	
	public static String DateToString(Date fecha, String formato){
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return sdf.format(fecha);
	}

	/*public static Date StringToDate(String fecha, String formato) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return sdf.parse(fecha);
	}
	
	public static Date StringToDate(String fecha, String formato, Locale locale) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(formato, locale);
		return sdf.parse(fecha);
	}*/
}

