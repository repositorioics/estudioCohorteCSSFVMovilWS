package com.sts_ni.estudiocohortecssfv.servicios;

public interface HojaConsultaReporteService {
	
	
	public byte[] getHojaConsultaPdf(Integer secHojaConsulta);
	
	public void imprimirConsultaPdf(Integer secHojaConsulta);
	
	public void imprimirConsultaTest();

}
