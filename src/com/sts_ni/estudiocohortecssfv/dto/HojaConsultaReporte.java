package com.sts_ni.estudiocohortecssfv.dto;

import java.math.BigDecimal;
import java.util.Date;

public class HojaConsultaReporte {

	
	
	private int codExpediente;
	private Date fechaConsulta;
	private String nombreApellido;
	private BigDecimal pesoKg;
	private BigDecimal tallaCm;
	private int edad;
	private String edadCalculada;
	
	public String getEdadCalculada() {
		return edadCalculada;
	}
	public void setEdadCalculada(String edadCalculada) {
		this.edadCalculada = edadCalculada;
	}
	private Date FechaNac;
	private char sexo;
	private BigDecimal temperaturaC;
	
	private Short pas;
	private Short pad;	
	private Integer presion;
	private Short fciaResp;
	private Short fciaCard;
	private String lugarAtencion;
	private String consulta;
	private Character segChick;
	private Character turno;
	private BigDecimal temMedc;
	private Date ultDiaFiebre;
	private Date ultDosisAntipiretico;
	private String horaUltDosisAntipiretico;
	private String amPmUltDosisAntipiretico;
	private String amPmUltDiaFiebre;
	private String horasv;
	private String motivoCancelacion;
	private String hora;
	
	public String getHorasv() {
		return horasv;
	}
	public void setHorasv(String horasv) {
		this.horasv = horasv;
	}
	
	/*ESTADO GENERAL*/
	private Character fiebre;
	private Character astenia;
	private Character asomnoliento;
	private Character malEstado;
	private Character perdidaConsciencia;
	private Character inquieto;
	private Character convulsiones;
	private Character hipotermia;
	private Character letargia;
	/*GASTRO INSTESTINAL*/
	private Character pocoApetito;
	private Character nausea;
	private Character dificultadAlimentarse;
	private Character vomito12Horas;
	private Short vomito12h;	
	private Character diarrea;
	private Character diarreaSangre;
	private Character estrenimiento;
	private Character dolorAbIntermitente;
	private Character dolorAbContinuo;
	private Character epigastralgia;
	private Character intoleranciaOral;
	private Character distensionAbdominal;
	private Character transfusionSangre;
	
	public Character getTransfusionSangre() {
		return transfusionSangre;
	}
	public void setTransfusionSangre(Character transfusionSangre) {
		this.transfusionSangre = transfusionSangre;
	}
	private BigDecimal hepatomegaliaCm;
	private Character hepatomegalia;
	public Character getHepatomegalia() {
		return hepatomegalia;
	}
	public void setHepatomegalia(Character hepatomegalia) {
		this.hepatomegalia = hepatomegalia;
	}
	/*OSTEO MUSCULAR*/
	private Character altralgia;
	private Character mialgia;
	private Character lumbalgia;
	private Character dolorCuello;
	private Character tenosinovitis;
	private Character artralgiaProximal;
	private Character artralgiaDistal;
	private Character conjuntivitis;
	
	private Character edemaMunecas;
	private Character edemaCodos;
	private Character edemaHombros;
	private Character edemaRodillas;
	private Character edemaTobillos;
	/*CABEZA*/
	private Character cefalea;
	private Character rigidezCuello;
	private Character inyeccionConjuntival;
	private Character hemorragiaSuconjuntival;
	private Character dolorRetroocular;
	private Character fontanelaAbombada;
	private Character ictericiaConuntival;
	/*DESIHIDRATACION*/
	private Character lenguaMucosasSecas;
	private Character pliegueCutaneo;
	private Character orinaReducida;
	private Character bebeConSed;
	
	private String ojosHundidos;
	private Character fontanelaHundida;
	/*CUTANEO*/
	
	private Character rahsLocalizado;
	private Character rahsGeneralizado;
	private Character rashEritematoso;
	private Character rahsMacular;
	private Character rashPapular;
	
	/*piel moteada*/
	private Character ruborFacial;
	private Character equimosis;
	private Character cianosisCentral;
	private Character ictericia;
	
	/*GARGANTA*/
	private Character eritema;
	
	private Character dolorGarganta;
	private Character adenopatiasCervicales;
	private Character exudado;
	private Character petequiasMucosa;
	
	/*RENAL*/
	private Character sintomasUrinarios;
	private Character leucocituria;
	private Character nitritos;

	private Character eritrocitos;

	
	private Character interconsultaPediatrica;
	private Character referenciaHospital;
	private Character referenciaDengue;
	private Character referenciaIrag;
	private Character referenciaChik;
	private Character eti;
	private Character irag;
	private Character neumonia;
	
	/*ESTADO NUTRICIONAL*/
	private Character obeso;
	private Character sobrePeso;
	private Character sospechaProblema;
	private Character normal;
	
	private Character bajoPeso;
	private Character bajoPesoSevero;
	private BigDecimal imc;
	
	private Character lactanciaMaterna;
	private Character vacunasCompletas;
	private Character vacunaInfluenza;
	private Date fechaVacuna;
	
	/*RESPIRATORIO*/
	private Character tos;
	private Character rinorrea;
	private Character congestionNasal;
	private Character otalgia;
	private Character aleteoNasal;
	private Character apnea;
	private Character respiracionRapida;
	private Character quejidoEspiratorio;
	private Character estiradorReposo;
	private Character tirajeSubcostal;
	private Character sibilancias;	
	private Character crepitos;
	private Character roncos;
	
	private Character otraFif;
	private Date nuevaFif;
	private Short saturaciono2;
	private String categoria;
	
	private Character cambioCategoria;
	private Character manifestacionHemorragica;
	private Character pruebaTorniquetePositiva;
	private Character petequia10Pt;
	private Character petequia20Pt;
	private Character pielExtremidadesFrias;
	private Character palidezEnExtremidades;

	private Character epistaxis;
	private Character gingivorragia;
	private Character petequiasEspontaneas;
	private Character llenadoCapilar2Seg;
	private Character cianosis;

	private Character hipermenorrea;
	private Character hematemesis;
	private Character melena;
	private Character hemoconc;
	
	private Short hemoconcentracion;
	private BigDecimal linfocitosaAtipicos;
	private Date fechaLinfocitos;
	private Character rahsMoteada;
	
	public Character getRahsMoteada() {
		return rahsMoteada;
	}
	public void setRahsMoteada(Character rahsMoteada) {
		this.rahsMoteada = rahsMoteada;
	}
	public BigDecimal getLinfocitosaAtipicos() {
		return linfocitosaAtipicos;
	}
	public void setLinfocitosaAtipicos(BigDecimal linfocitosaAtipicos) {
		this.linfocitosaAtipicos = linfocitosaAtipicos;
	}
	public Date getFechaLinfocitos() {
		return fechaLinfocitos;
	}
	public void setFechaLinfocitos(Date fechaLinfocitos) {
		this.fechaLinfocitos = fechaLinfocitos;
	}
	private String hospitalizadoEspecificar;
	private Character hospitalizado;
	public Character getHospitalizado() {
		return hospitalizado;
	}
	public void setHospitalizado(Character hospitalizado) {
		this.hospitalizado = hospitalizado;
	}
	private String transfusionEspecificar;
	private String medicamentoDistEspecificar;
	private Character medicamentoDistinto;
	
	public Character getMedicamentoDistinto() {
		return medicamentoDistinto;
	}
	public void setMedicamentoDistinto(Character medicamentoDistinto) {
		this.medicamentoDistinto = medicamentoDistinto;
	}
	private String medicamentoEspecificar;
	public String getMedicamentoEspecificar() {
		return medicamentoEspecificar;
	}
	public void setMedicamentoEspecificar(String medicamentoEspecificar) {
		this.medicamentoEspecificar = medicamentoEspecificar;
	}
	private Character tomandoMedicamento;
	
	/*EXAMENES*/
	private Character bhc;
	private Character serologiaDengue;
	private Character serologiaChik;
	private Character gotaGruesa;
	private Character extendidoPeriferico;
	private Character ego;
	private Character egh;
	private Character citologiaFecal;
	private Character factorReumatoideo;
	private Character albumina;
	private Character astAlt;
	private Character bilirrubinas;
	private Character bilirrubinuria;
	private Character cpk;
	private Character colesterol;
	private Character influenza;
	
	String otroExamenLab;
	private Character oel;
	
	/*TRATAMIENTO*/
	private Character acetaminofen;
	private Character asa;
	private Character ibuprofen;
	private Character penicilina;
	private Character amoxicilina;
	private Character dicloxacilina;
	private String otroAntibiotico;
	private Character furazolidona;
	private Character metronidazolTinidazol;
	private Character albendazolMebendazol;
	private Character sulfatoFerroso;
	private Character sueroOral;
	private Character sulfatoZinc;
	private Character liquidosIv;
	private Character prednisona;
	private Character hidrocortisonaIv;
	private Character salbutamol;
	private Character oseltamivir;
	private String planes;
	private String historiaExamenFisico;
	private String diagnostico1;
	private String diagnostico2;
	private String diagnostico3;
	private String diagnostico4;
	private String telefono;
	private BigDecimal telef;
	private Date proximaCita;
	private String Colegio;
	private String  horarioClases;	
	private Date fis;
	private Date fif;
	private String otroDiagnostico;
	private Character otro;
	private String descripcionColegio;
	private String nombreUsuarioMedico;
	private String nombreUsuarioEnfermeria;
	private String expedienteFisico;
	private int numHojaConsulta;
	
	
	//ESTUDIOS PARTICIPANTES
	/*---*/
	private String estudiosParticipantes;
	/*---*/
	
	//NO ATIENDE LLAMADO ENFERMERIA Y MEDICO
	private String noAtiendeLlamadoEnfermeria;
	private String noAtiendeLlamadoMedico;
	
	private String codSupervisor;
	private String nombreSupervisor;
	
	public int getNumHojaConsulta() {
		return numHojaConsulta;
	}
	public void setNumHojaConsulta(int numHojaConsulta) {
		this.numHojaConsulta = numHojaConsulta;
	}
	public String getCodigoMedico() {
		return codigoMedico;
	}
	public void setCodigoMedico(String codigoMedico) {
		this.codigoMedico = codigoMedico;
	}
	public String getCodigoEnfermera() {
		return codigoEnfermera;
	}
	public void setCodigoEnfermera(String codigoEnfermera) {
		this.codigoEnfermera = codigoEnfermera;
	}
	public String getCodigoMedicoCambioTurno() {
		return codigoMedicoCambioTurno;
	}
	public void setCodigoMedicoCambioTurno(String codigoMedicoCambioTurno) {
		this.codigoMedicoCambioTurno = codigoMedicoCambioTurno;
	}
	public String getMedicoCambioTurno() {
		return medicoCambioTurno;
	}
	public void setMedicoCambioTurno(String medicoCambioTurno) {
		this.medicoCambioTurno = medicoCambioTurno;
	}
	public Date getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	public Date getFechaCierreCambioTurno() {
		return fechaCierreCambioTurno;
	}
	public void setFechaCierreCambioTurno(Date fechaCierreCambioTurno) {
		this.fechaCierreCambioTurno = fechaCierreCambioTurno;
	}
	private String codigoMedico;
	private String codigoEnfermera;
	private String codigoMedicoCambioTurno;
	private String medicoCambioTurno;
	
	private Date fechaCierre;
	private Date fechaCierreCambioTurno;
	
	public String getExpedienteFisico() {
		return expedienteFisico;
	}
	public void setExpedienteFisico(String expedienteFisico) {
		this.expedienteFisico = expedienteFisico;
	}
	public String getNombreUsuarioMedico() {
		return nombreUsuarioMedico;
	}
	public void setNombreUsuarioMedico(String nombreUsuarioMedico) {
		this.nombreUsuarioMedico = nombreUsuarioMedico;
	}
	public String getNombreUsuarioEnfermeria() {
		return nombreUsuarioEnfermeria;
	}
	public void setNombreUsuarioEnfermeria(String nombreUsuarioEnfermeria) {
		this.nombreUsuarioEnfermeria = nombreUsuarioEnfermeria;
	}

	
	public String getDescripcionColegio() {
		return descripcionColegio;
	}
	public void setDescripcionColegio(String descripcionColegio) {
		this.descripcionColegio = descripcionColegio;
	}
	public int getCodExpediente() {
		return codExpediente;
	}
	public void setCodExpediente(int codExpediente) {
		this.codExpediente = codExpediente;
	}
	public Date getFechaConsulta() {
		return fechaConsulta;
	}
	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}
	public String getNombreApellido() {
		return nombreApellido;
	}
	public void setNombreApellido(String nombreApellido) {
		this.nombreApellido = nombreApellido;
	}
	public BigDecimal getPesoKg() {
		return pesoKg;
	}
	public void setPesoKg(BigDecimal pesoKg) {
		this.pesoKg = pesoKg;
	}
	public BigDecimal getTallaCm() {
		return tallaCm;
	}
	public void setTallaCm(BigDecimal tallaCm) {
		this.tallaCm = tallaCm;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public Date getFechaNac() {
		return FechaNac;
	}
	public void setFechaNac(Date fechaNac) {
		FechaNac = fechaNac;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}
	public BigDecimal getTemperaturaC() {
		return temperaturaC;
	}
	public void setTemperaturaC(BigDecimal temperaturaC) {
		this.temperaturaC = temperaturaC;
	}
	public Short getPas() {
		return pas;
	}
	public void setPas(Short pas) {
		this.pas = pas;
	}
	public Short getPad() {
		return pad;
	}
	public void setPad(Short pad) {
		this.pad = pad;
	}	
	public Integer getPresion() {
		return presion;
	}
	public void setPresion(Integer presion) {
		this.presion = presion;
	}
	public Short getFciaResp() {
		return fciaResp;
	}
	public void setFciaResp(Short fciaResp) {
		this.fciaResp = fciaResp;
	}
	public Short getFciaCard() {
		return fciaCard;
	}
	public void setFciaCard(Short fciaCard) {
		this.fciaCard = fciaCard;
	}
	public String getLugarAtencion() {
		return lugarAtencion;
	}
	public void setLugarAtencion(String lugarAtencion) {
		this.lugarAtencion = lugarAtencion;
	}
	public String getConsulta() {
		return consulta;
	}
	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}
	public Character getSegChick() {
		return segChick;
	}
	public void setSegChick(Character segChick) {
		this.segChick = segChick;
	}
	public Character getTurno() {
		return turno;
	}
	public void setTurno(Character turno) {
		this.turno = turno;
	}
	public BigDecimal getTemMedc() {
		return temMedc;
	}
	public void setTemMedc(BigDecimal temMedc) {
		this.temMedc = temMedc;
	}
	public Date getUltDiaFiebre() {
		return ultDiaFiebre;
	}
	public void setUltDiaFiebre(Date ultDiaFiebre) {
		this.ultDiaFiebre = ultDiaFiebre;
	}
	public Date getUltDosisAntipiretico() {
		return ultDosisAntipiretico;
	}
	public void setUltDosisAntipiretico(Date ultDosisAntipiretico) {
		this.ultDosisAntipiretico = ultDosisAntipiretico;
	}
	public String getHoraUltDosisAntipiretico() {
		return horaUltDosisAntipiretico;
	}
	public void setHoraUltDosisAntipiretico(String horaUltDosisAntipiretico) {
		this.horaUltDosisAntipiretico = horaUltDosisAntipiretico;
	}
	public String getAmPmUltDosisAntipiretico() {
		return amPmUltDosisAntipiretico;
	}
	public void setAmPmUltDosisAntipiretico(String amPmUltDosisAntipiretico) {
		this.amPmUltDosisAntipiretico = amPmUltDosisAntipiretico;
	}
	public Character getFiebre() {
		return fiebre;
	}
	public void setFiebre(Character fiebre) {
		this.fiebre = fiebre;
	}
	public Character getAstenia() {
		return astenia;
	}
	public void setAstenia(Character astenia) {
		this.astenia = astenia;
	}
	public Character getAsomnoliento() {
		return asomnoliento;
	}
	public void setAsomnoliento(Character asomnoliento) {
		this.asomnoliento = asomnoliento;
	}
	public Character getMalEstado() {
		return malEstado;
	}
	public void setMalEstado(Character malEstado) {
		this.malEstado = malEstado;
	}
	public Character getPerdidaConsciencia() {
		return perdidaConsciencia;
	}
	public void setPerdidaConsciencia(Character perdidaConsciencia) {
		this.perdidaConsciencia = perdidaConsciencia;
	}
	public Character getInquieto() {
		return inquieto;
	}
	public void setInquieto(Character inquieto) {
		this.inquieto = inquieto;
	}
	public Character getConvulsiones() {
		return convulsiones;
	}
	public void setConvulsiones(Character convulsiones) {
		this.convulsiones = convulsiones;
	}
	public Character getHipotermia() {
		return hipotermia;
	}
	public void setHipotermia(Character hipotermia) {
		this.hipotermia = hipotermia;
	}
	public Character getLetargia() {
		return letargia;
	}
	public void setLetargia(Character letargia) {
		this.letargia = letargia;
	}
	public Character getPocoApetito() {
		return pocoApetito;
	}
	public void setPocoApetito(Character pocoApetito) {
		this.pocoApetito = pocoApetito;
	}
	public Character getNausea() {
		return nausea;
	}
	public void setNausea(Character nausea) {
		this.nausea = nausea;
	}
	public Character getDificultadAlimentarse() {
		return dificultadAlimentarse;
	}
	public void setDificultadAlimentarse(Character dificultadAlimentarse) {
		this.dificultadAlimentarse = dificultadAlimentarse;
	}
	public Character getVomito12Horas() {
		return vomito12Horas;
	}
	public void setVomito12Horas(Character vomito12Horas) {
		this.vomito12Horas = vomito12Horas;
	}
	public Short getVomito12h() {
		return vomito12h;
	}
	public void setVomito12h(Short vomito12h) {
		this.vomito12h = vomito12h;
	}
	public Character getDiarrea() {
		return diarrea;
	}
	public void setDiarrea(Character diarrea) {
		this.diarrea = diarrea;
	}
	public Character getDiarreaSangre() {
		return diarreaSangre;
	}
	public void setDiarreaSangre(Character diarreaSangre) {
		this.diarreaSangre = diarreaSangre;
	}
	public Character getEstrenimiento() {
		return estrenimiento;
	}
	public void setEstrenimiento(Character estrenimiento) {
		this.estrenimiento = estrenimiento;
	}
	public Character getDolorAbIntermitente() {
		return dolorAbIntermitente;
	}
	public void setDolorAbIntermitente(Character dolorAbIntermitente) {
		this.dolorAbIntermitente = dolorAbIntermitente;
	}
	public Character getDolorAbContinuo() {
		return dolorAbContinuo;
	}
	public void setDolorAbContinuo(Character dolorAbContinuo) {
		this.dolorAbContinuo = dolorAbContinuo;
	}
	public Character getEpigastralgia() {
		return epigastralgia;
	}
	public void setEpigastralgia(Character epigastralgia) {
		this.epigastralgia = epigastralgia;
	}
	public Character getIntoleranciaOral() {
		return intoleranciaOral;
	}
	public void setIntoleranciaOral(Character intoleranciaOral) {
		this.intoleranciaOral = intoleranciaOral;
	}
	public Character getDistensionAbdominal() {
		return distensionAbdominal;
	}
	public void setDistensionAbdominal(Character distensionAbdominal) {
		this.distensionAbdominal = distensionAbdominal;
	}
	public BigDecimal getHepatomegaliaCm() {
		return hepatomegaliaCm;
	}
	public void setHepatomegaliaCm(BigDecimal hepatomegaliaCm) {
		this.hepatomegaliaCm = hepatomegaliaCm;
	}
	public Character getAltralgia() {
		return altralgia;
	}
	public void setAltralgia(Character altralgia) {
		this.altralgia = altralgia;
	}
	public Character getMialgia() {
		return mialgia;
	}
	public void setMialgia(Character mialgia) {
		this.mialgia = mialgia;
	}
	public Character getLumbalgia() {
		return lumbalgia;
	}
	public void setLumbalgia(Character lumbalgia) {
		this.lumbalgia = lumbalgia;
	}
	public Character getDolorCuello() {
		return dolorCuello;
	}
	public void setDolorCuello(Character dolorCuello) {
		this.dolorCuello = dolorCuello;
	}
	public Character getTenosinovitis() {
		return tenosinovitis;
	}
	public void setTenosinovitis(Character tenosinovitis) {
		this.tenosinovitis = tenosinovitis;
	}
	public Character getArtralgiaProximal() {
		return artralgiaProximal;
	}
	public void setArtralgiaProximal(Character artralgiaProximal) {
		this.artralgiaProximal = artralgiaProximal;
	}
	public Character getArtralgiaDistal() {
		return artralgiaDistal;
	}
	public void setArtralgiaDistal(Character artralgiaDistal) {
		this.artralgiaDistal = artralgiaDistal;
	}
	public Character getConjuntivitis() {
		return conjuntivitis;
	}
	public void setConjuntivitis(Character conjuntivitis) {
		this.conjuntivitis = conjuntivitis;
	}
	public Character getEdemaMunecas() {
		return edemaMunecas;
	}
	public void setEdemaMunecas(Character edemaMunecas) {
		this.edemaMunecas = edemaMunecas;
	}
	public Character getEdemaCodos() {
		return edemaCodos;
	}
	public void setEdemaCodos(Character edemaCodos) {
		this.edemaCodos = edemaCodos;
	}
	public Character getEdemaHombros() {
		return edemaHombros;
	}
	public void setEdemaHombros(Character edemaHombros) {
		this.edemaHombros = edemaHombros;
	}
	public Character getEdemaRodillas() {
		return edemaRodillas;
	}
	public void setEdemaRodillas(Character edemaRodillas) {
		this.edemaRodillas = edemaRodillas;
	}
	public Character getEdemaTobillos() {
		return edemaTobillos;
	}
	public void setEdemaTobillos(Character edemaTobillos) {
		this.edemaTobillos = edemaTobillos;
	}
	public Character getCefalea() {
		return cefalea;
	}
	public void setCefalea(Character cefalea) {
		this.cefalea = cefalea;
	}
	public Character getRigidezCuello() {
		return rigidezCuello;
	}
	public void setRigidezCuello(Character rigidezCuello) {
		this.rigidezCuello = rigidezCuello;
	}
	public Character getInyeccionConjuntival() {
		return inyeccionConjuntival;
	}
	public void setInyeccionConjuntival(Character inyeccionConjuntival) {
		this.inyeccionConjuntival = inyeccionConjuntival;
	}
	public Character getHemorragiaSuconjuntival() {
		return hemorragiaSuconjuntival;
	}
	public void setHemorragiaSuconjuntival(Character hemorragiaSuconjuntival) {
		this.hemorragiaSuconjuntival = hemorragiaSuconjuntival;
	}
	public Character getDolorRetroocular() {
		return dolorRetroocular;
	}
	public void setDolorRetroocular(Character dolorRetroocular) {
		this.dolorRetroocular = dolorRetroocular;
	}
	public Character getFontanelaAbombada() {
		return fontanelaAbombada;
	}
	public void setFontanelaAbombada(Character fontanelaAbombada) {
		this.fontanelaAbombada = fontanelaAbombada;
	}
	public Character getIctericiaConuntival() {
		return ictericiaConuntival;
	}
	public void setIctericiaConuntival(Character ictericiaConuntival) {
		this.ictericiaConuntival = ictericiaConuntival;
	}
	
	public Character getLenguaMucosasSecas() {
		return lenguaMucosasSecas;
	}
	public void setLenguaMucosasSecas(Character lenguaMucosasSecas) {
		this.lenguaMucosasSecas = lenguaMucosasSecas;
	}
	public Character getPliegueCutaneo() {
		return pliegueCutaneo;
	}
	public void setPliegueCutaneo(Character pliegueCutaneo) {
		this.pliegueCutaneo = pliegueCutaneo;
	}
	public Character getOrinaReducida() {
		return orinaReducida;
	}
	public void setOrinaReducida(Character orinaReducida) {
		this.orinaReducida = orinaReducida;
	}
	public Character getBebeConSed() {
		return bebeConSed;
	}
	public void setBebeConSed(Character bebeConSed) {
		this.bebeConSed = bebeConSed;
	}
	public String getOjosHundidos() {
		return ojosHundidos;
	}
	public void setOjosHundidos(String ojosHundidos) {
		this.ojosHundidos = ojosHundidos;
	}
	public Character getFontanelaHundida() {
		return fontanelaHundida;
	}
	public void setFontanelaHundida(Character fontanelaHundida) {
		this.fontanelaHundida = fontanelaHundida;
	}
	public Character getRahsLocalizado() {
		return rahsLocalizado;
	}
	public void setRahsLocalizado(Character rahsLocalizado) {
		this.rahsLocalizado = rahsLocalizado;
	}
	public Character getRahsGeneralizado() {
		return rahsGeneralizado;
	}
	public void setRahsGeneralizado(Character rahsGeneralizado) {
		this.rahsGeneralizado = rahsGeneralizado;
	}
	public Character getRashEritematoso() {
		return rashEritematoso;
	}
	public void setRashEritematoso(Character rashEritematoso) {
		this.rashEritematoso = rashEritematoso;
	}
	public Character getRahsMacular() {
		return rahsMacular;
	}
	public void setRahsMacular(Character rahsMacular) {
		this.rahsMacular = rahsMacular;
	}
	public Character getRashPapular() {
		return rashPapular;
	}
	public void setRashPapular(Character rashPapular) {
		this.rashPapular = rashPapular;
	}
	public Character getRuborFacial() {
		return ruborFacial;
	}
	public void setRuborFacial(Character ruborFacial) {
		this.ruborFacial = ruborFacial;
	}
	public Character getEquimosis() {
		return equimosis;
	}
	public void setEquimosis(Character equimosis) {
		this.equimosis = equimosis;
	}
	public Character getCianosisCentral() {
		return cianosisCentral;
	}
	public void setCianosisCentral(Character cianosisCentral) {
		this.cianosisCentral = cianosisCentral;
	}
	public Character getIctericia() {
		return ictericia;
	}
	public void setIctericia(Character ictericia) {
		this.ictericia = ictericia;
	}
	public Character getEritema() {
		return eritema;
	}
	public void setEritema(Character eritema) {
		this.eritema = eritema;
	}
	public Character getDolorGarganta() {
		return dolorGarganta;
	}
	public void setDolorGarganta(Character dolorGarganta) {
		this.dolorGarganta = dolorGarganta;
	}
	public Character getAdenopatiasCervicales() {
		return adenopatiasCervicales;
	}
	public void setAdenopatiasCervicales(Character adenopatiasCervicales) {
		this.adenopatiasCervicales = adenopatiasCervicales;
	}
	public Character getExudado() {
		return exudado;
	}
	public void setExudado(Character exudado) {
		this.exudado = exudado;
	}
	public Character getPetequiasMucosa() {
		return petequiasMucosa;
	}
	public void setPetequiasMucosa(Character petequiasMucosa) {
		this.petequiasMucosa = petequiasMucosa;
	}
	public Character getSintomasUrinarios() {
		return sintomasUrinarios;
	}
	public void setSintomasUrinarios(Character sintomasUrinarios) {
		this.sintomasUrinarios = sintomasUrinarios;
	}
	public Character getLeucocituria() {
		return leucocituria;
	}
	public void setLeucocituria(Character leucocituria) {
		this.leucocituria = leucocituria;
	}
	public Character getNitritos() {
		return nitritos;
	}
	public void setNitritos(Character nitritos) {
		this.nitritos = nitritos;
	}
	public Character getEritrocitos() {
		return eritrocitos;
	}
	public void setEritrocitos(Character eritrocitos) {
		this.eritrocitos = eritrocitos;
	}
	public Character getInterconsultaPediatrica() {
		return interconsultaPediatrica;
	}
	public void setInterconsultaPediatrica(Character interconsultaPediatrica) {
		this.interconsultaPediatrica = interconsultaPediatrica;
	}
	public Character getReferenciaHospital() {
		return referenciaHospital;
	}
	public void setReferenciaHospital(Character referenciaHospital) {
		this.referenciaHospital = referenciaHospital;
	}
	public Character getReferenciaDengue() {
		return referenciaDengue;
	}
	public void setReferenciaDengue(Character referenciaDengue) {
		this.referenciaDengue = referenciaDengue;
	}
	public Character getReferenciaIrag() {
		return referenciaIrag;
	}
	public void setReferenciaIrag(Character referenciaIrag) {
		this.referenciaIrag = referenciaIrag;
	}
	public Character getReferenciaChik() {
		return referenciaChik;
	}
	public void setReferenciaChik(Character referenciaChik) {
		this.referenciaChik = referenciaChik;
	}
	public Character getEti() {
		return eti;
	}
	public void setEti(Character eti) {
		this.eti = eti;
	}
	public Character getIrag() {
		return irag;
	}
	public void setIrag(Character irag) {
		this.irag = irag;
	}
	public Character getNeumonia() {
		return neumonia;
	}
	public void setNeumonia(Character neumonia) {
		this.neumonia = neumonia;
	}
	public Character getObeso() {
		return obeso;
	}
	public void setObeso(Character obeso) {
		this.obeso = obeso;
	}
	public Character getSobrePeso() {
		return sobrePeso;
	}
	public void setSobrePeso(Character sobrePeso) {
		this.sobrePeso = sobrePeso;
	}
	public Character getSospechaProblema() {
		return sospechaProblema;
	}
	public void setSospechaProblema(Character sospechaProblema) {
		this.sospechaProblema = sospechaProblema;
	}
	public Character getNormal() {
		return normal;
	}
	public void setNormal(Character normal) {
		this.normal = normal;
	}
	public Character getBajoPeso() {
		return bajoPeso;
	}
	public void setBajoPeso(Character bajoPeso) {
		this.bajoPeso = bajoPeso;
	}
	public Character getBajoPesoSevero() {
		return bajoPesoSevero;
	}
	public void setBajoPesoSevero(Character bajoPesoSevero) {
		this.bajoPesoSevero = bajoPesoSevero;
	}
	public BigDecimal getImc() {
		return imc;
	}
	public void setImc(BigDecimal imc) {
		this.imc = imc;
	}
	public Character getLactanciaMaterna() {
		return lactanciaMaterna;
	}
	public void setLactanciaMaterna(Character lactanciaMaterna) {
		this.lactanciaMaterna = lactanciaMaterna;
	}
	public Character getVacunasCompletas() {
		return vacunasCompletas;
	}
	public void setVacunasCompletas(Character vacunasCompletas) {
		this.vacunasCompletas = vacunasCompletas;
	}
	public Character getVacunaInfluenza() {
		return vacunaInfluenza;
	}
	public void setVacunaInfluenza(Character vacunaInfluenza) {
		this.vacunaInfluenza = vacunaInfluenza;
	}
	public Date getFechaVacuna() {
		return fechaVacuna;
	}
	public void setFechaVacuna(Date fechaVacuna) {
		this.fechaVacuna = fechaVacuna;
	}
	public Character getTos() {
		return tos;
	}
	public void setTos(Character tos) {
		this.tos = tos;
	}
	public Character getRinorrea() {
		return rinorrea;
	}
	public void setRinorrea(Character rinorrea) {
		this.rinorrea = rinorrea;
	}
	public Character getCongestionNasal() {
		return congestionNasal;
	}
	public void setCongestionNasal(Character congestionNasal) {
		this.congestionNasal = congestionNasal;
	}
	public Character getOtalgia() {
		return otalgia;
	}
	public void setOtalgia(Character otalgia) {
		this.otalgia = otalgia;
	}
	public Character getAleteoNasal() {
		return aleteoNasal;
	}
	public void setAleteoNasal(Character aleteoNasal) {
		this.aleteoNasal = aleteoNasal;
	}
	public Character getApnea() {
		return apnea;
	}
	public void setApnea(Character apnea) {
		this.apnea = apnea;
	}
	public Character getRespiracionRapida() {
		return respiracionRapida;
	}
	public void setRespiracionRapida(Character respiracionRapida) {
		this.respiracionRapida = respiracionRapida;
	}
	public Character getQuejidoEspiratorio() {
		return quejidoEspiratorio;
	}
	public void setQuejidoEspiratorio(Character quejidoEspiratorio) {
		this.quejidoEspiratorio = quejidoEspiratorio;
	}
	public Character getEstiradorReposo() {
		return estiradorReposo;
	}
	public void setEstiradorReposo(Character estiradorReposo) {
		this.estiradorReposo = estiradorReposo;
	}
	public Character getTirajeSubcostal() {
		return tirajeSubcostal;
	}
	public void setTirajeSubcostal(Character tirajeSubcostal) {
		this.tirajeSubcostal = tirajeSubcostal;
	}
	public Character getSibilancias() {
		return sibilancias;
	}
	public void setSibilancias(Character sibilancias) {
		this.sibilancias = sibilancias;
	}
	public Character getCrepitos() {
		return crepitos;
	}
	public void setCrepitos(Character crepitos) {
		this.crepitos = crepitos;
	}
	public Character getRoncos() {
		return roncos;
	}
	public void setRoncos(Character roncos) {
		this.roncos = roncos;
	}
	public Character getOtraFif() {
		return otraFif;
	}
	public void setOtraFif(Character otraFif) {
		this.otraFif = otraFif;
	}
	public Date getNuevaFif() {
		return nuevaFif;
	}
	public void setNuevaFif(Date nuevaFif) {
		this.nuevaFif = nuevaFif;
	}
	public Short getSaturaciono2() {
		return saturaciono2;
	}
	public void setSaturaciono2(Short saturaciono2) {
		this.saturaciono2 = saturaciono2;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public Character getCambioCategoria() {
		return cambioCategoria;
	}
	public void setCambioCategoria(Character cambioCategoria) {
		this.cambioCategoria = cambioCategoria;
	}
	public Character getManifestacionHemorragica() {
		return manifestacionHemorragica;
	}
	public void setManifestacionHemorragica(Character manifestacionHemorragica) {
		this.manifestacionHemorragica = manifestacionHemorragica;
	}
	public Character getPruebaTorniquetePositiva() {
		return pruebaTorniquetePositiva;
	}
	public void setPruebaTorniquetePositiva(Character pruebaTorniquetePositiva) {
		this.pruebaTorniquetePositiva = pruebaTorniquetePositiva;
	}
	public Character getPetequia10Pt() {
		return petequia10Pt;
	}
	public void setPetequia10Pt(Character petequia10Pt) {
		this.petequia10Pt = petequia10Pt;
	}
	public Character getPetequia20Pt() {
		return petequia20Pt;
	}
	public void setPetequia20Pt(Character petequia20Pt) {
		this.petequia20Pt = petequia20Pt;
	}
	public Character getPielExtremidadesFrias() {
		return pielExtremidadesFrias;
	}
	public void setPielExtremidadesFrias(Character pielExtremidadesFrias) {
		this.pielExtremidadesFrias = pielExtremidadesFrias;
	}
	public Character getPalidezEnExtremidades() {
		return palidezEnExtremidades;
	}
	public void setPalidezEnExtremidades(Character palidezEnExtremidades) {
		this.palidezEnExtremidades = palidezEnExtremidades;
	}
	public Character getEpistaxis() {
		return epistaxis;
	}
	public void setEpistaxis(Character epistaxis) {
		this.epistaxis = epistaxis;
	}
	public Character getGingivorragia() {
		return gingivorragia;
	}
	public void setGingivorragia(Character gingivorragia) {
		this.gingivorragia = gingivorragia;
	}
	public Character getPetequiasEspontaneas() {
		return petequiasEspontaneas;
	}
	public void setPetequiasEspontaneas(Character petequiasEspontaneas) {
		this.petequiasEspontaneas = petequiasEspontaneas;
	}
	public Character getLlenadoCapilar2Seg() {
		return llenadoCapilar2Seg;
	}
	public void setLlenadoCapilar2Seg(Character llenadoCapilar2Seg) {
		this.llenadoCapilar2Seg = llenadoCapilar2Seg;
	}
	public Character getCianosis() {
		return cianosis;
	}
	public void setCianosis(Character cianosis) {
		this.cianosis = cianosis;
	}
	public Character getHipermenorrea() {
		return hipermenorrea;
	}
	public void setHipermenorrea(Character hipermenorrea) {
		this.hipermenorrea = hipermenorrea;
	}
	public Character getHematemesis() {
		return hematemesis;
	}
	public void setHematemesis(Character hematemesis) {
		this.hematemesis = hematemesis;
	}
	public Character getMelena() {
		return melena;
	}
	public void setMelena(Character melena) {
		this.melena = melena;
	}
	public Character getHemoconc() {
		return hemoconc;
	}
	public void setHemoconc(Character hemoconc) {
		this.hemoconc = hemoconc;
	}
	public Short getHemoconcentracion() {
		return hemoconcentracion;
	}
	public void setHemoconcentracion(Short hemoconcentracion) {
		this.hemoconcentracion = hemoconcentracion;
	}
	public String getHospitalizadoEspecificar() {
		return hospitalizadoEspecificar;
	}
	public void setHospitalizadoEspecificar(String hospitalizadoEspecificar) {
		this.hospitalizadoEspecificar = hospitalizadoEspecificar;
	}
	public String getTransfusionEspecificar() {
		return transfusionEspecificar;
	}
	public void setTransfusionEspecificar(String transfusionEspecificar) {
		this.transfusionEspecificar = transfusionEspecificar;
	}
	public String getMedicamentoDistEspecificar() {
		return medicamentoDistEspecificar;
	}
	public void setMedicamentoDistEspecificar(String medicamentoDistEspecificar) {
		this.medicamentoDistEspecificar = medicamentoDistEspecificar;
	}
	public Character getTomandoMedicamento() {
		return tomandoMedicamento;
	}
	public void setTomandoMedicamento(Character tomandoMedicamento) {
		this.tomandoMedicamento = tomandoMedicamento;
	}
	public Character getBhc() {
		return bhc;
	}
	public void setBhc(Character bhc) {
		this.bhc = bhc;
	}
	public Character getSerologiaDengue() {
		return serologiaDengue;
	}
	public void setSerologiaDengue(Character serologiaDengue) {
		this.serologiaDengue = serologiaDengue;
	}
	public Character getSerologiaChik() {
		return serologiaChik;
	}
	public void setSerologiaChik(Character serologiaChik) {
		this.serologiaChik = serologiaChik;
	}
	public Character getGotaGruesa() {
		return gotaGruesa;
	}
	public void setGotaGruesa(Character gotaGruesa) {
		this.gotaGruesa = gotaGruesa;
	}
	public Character getExtendidoPeriferico() {
		return extendidoPeriferico;
	}
	public void setExtendidoPeriferico(Character extendidoPeriferico) {
		this.extendidoPeriferico = extendidoPeriferico;
	}
	public Character getEgo() {
		return ego;
	}
	public void setEgo(Character ego) {
		this.ego = ego;
	}
	public Character getEgh() {
		return egh;
	}
	public void setEgh(Character egh) {
		this.egh = egh;
	}
	public Character getCitologiaFecal() {
		return citologiaFecal;
	}
	public void setCitologiaFecal(Character citologiaFecal) {
		this.citologiaFecal = citologiaFecal;
	}
	public Character getFactorReumatoideo() {
		return factorReumatoideo;
	}
	public void setFactorReumatoideo(Character factorReumatoideo) {
		this.factorReumatoideo = factorReumatoideo;
	}
	public Character getAlbumina() {
		return albumina;
	}
	public void setAlbumina(Character albumina) {
		this.albumina = albumina;
	}
	public Character getAstAlt() {
		return astAlt;
	}
	public void setAstAlt(Character astAlt) {
		this.astAlt = astAlt;
	}
	public Character getBilirrubinas() {
		return bilirrubinas;
	}
	public void setBilirrubinas(Character bilirrubinas) {
		this.bilirrubinas = bilirrubinas;
	}
	public Character getBilirrubinuria() {
		return bilirrubinuria;
	}
	public void setBilirrubinuria(Character bilirrubinuria) {
		this.bilirrubinuria = bilirrubinuria;
	}
	public Character getCpk() {
		return cpk;
	}
	public void setCpk(Character cpk) {
		this.cpk = cpk;
	}
	public Character getColesterol() {
		return colesterol;
	}
	public void setColesterol(Character colesterol) {
		this.colesterol = colesterol;
	}
	public Character getInfluenza() {
		return influenza;
	}
	public void setInfluenza(Character influenza) {
		this.influenza = influenza;
	}
	public String getOtroExamenLab() {
		return otroExamenLab;
	}
	public void setOtroExamenLab(String otroExamenLab) {
		this.otroExamenLab = otroExamenLab;
	}
	public Character getOel() {
		return oel;
	}
	public void setOel(Character oel) {
		this.oel = oel;
	}
	public Character getAcetaminofen() {
		return acetaminofen;
	}
	public void setAcetaminofen(Character acetaminofen) {
		this.acetaminofen = acetaminofen;
	}
	public Character getAsa() {
		return asa;
	}
	public void setAsa(Character asa) {
		this.asa = asa;
	}
	public Character getIbuprofen() {
		return ibuprofen;
	}
	public void setIbuprofen(Character ibuprofen) {
		this.ibuprofen = ibuprofen;
	}
	public Character getPenicilina() {
		return penicilina;
	}
	public void setPenicilina(Character penicilina) {
		this.penicilina = penicilina;
	}
	public Character getAmoxicilina() {
		return amoxicilina;
	}
	public void setAmoxicilina(Character amoxicilina) {
		this.amoxicilina = amoxicilina;
	}
	public Character getDicloxacilina() {
		return dicloxacilina;
	}
	public void setDicloxacilina(Character dicloxacilina) {
		this.dicloxacilina = dicloxacilina;
	}
	public String getOtroAntibiotico() {
		return otroAntibiotico;
	}
	public void setOtroAntibiotico(String otroAntibiotico) {
		this.otroAntibiotico = otroAntibiotico;
	}
	public Character getFurazolidona() {
		return furazolidona;
	}
	public void setFurazolidona(Character furazolidona) {
		this.furazolidona = furazolidona;
	}
	public Character getMetronidazolTinidazol() {
		return metronidazolTinidazol;
	}
	public void setMetronidazolTinidazol(Character metronidazolTinidazol) {
		this.metronidazolTinidazol = metronidazolTinidazol;
	}
	public Character getAlbendazolMebendazol() {
		return albendazolMebendazol;
	}
	public void setAlbendazolMebendazol(Character albendazolMebendazol) {
		this.albendazolMebendazol = albendazolMebendazol;
	}
	public Character getSulfatoFerroso() {
		return sulfatoFerroso;
	}
	public void setSulfatoFerroso(Character sulfatoFerroso) {
		this.sulfatoFerroso = sulfatoFerroso;
	}
	public Character getSueroOral() {
		return sueroOral;
	}
	public void setSueroOral(Character sueroOral) {
		this.sueroOral = sueroOral;
	}
	public Character getSulfatoZinc() {
		return sulfatoZinc;
	}
	public void setSulfatoZinc(Character sulfatoZinc) {
		this.sulfatoZinc = sulfatoZinc;
	}
	public Character getLiquidosIv() {
		return liquidosIv;
	}
	public void setLiquidosIv(Character liquidosIv) {
		this.liquidosIv = liquidosIv;
	}
	public Character getPrednisona() {
		return prednisona;
	}
	public void setPrednisona(Character prednisona) {
		this.prednisona = prednisona;
	}
	public Character getHidrocortisonaIv() {
		return hidrocortisonaIv;
	}
	public void setHidrocortisonaIv(Character hidrocortisonaIv) {
		this.hidrocortisonaIv = hidrocortisonaIv;
	}
	public Character getSalbutamol() {
		return salbutamol;
	}
	public void setSalbutamol(Character salbutamol) {
		this.salbutamol = salbutamol;
	}
	public Character getOseltamivir() {
		return oseltamivir;
	}
	public void setOseltamivir(Character oseltamivir) {
		this.oseltamivir = oseltamivir;
	}
	public String getPlanes() {
		return planes;
	}
	public void setPlanes(String planes) {
		this.planes = planes;
	}
	public String getHistoriaExamenFisico() {
		return historiaExamenFisico;
	}
	public void setHistoriaExamenFisico(String historiaExamenFisico) {
		this.historiaExamenFisico = historiaExamenFisico;
	}
	public String getDiagnostico1() {
		return diagnostico1;
	}
	public void setDiagnostico1(String diagnostico1) {
		this.diagnostico1 = diagnostico1;
	}
	public String getDiagnostico2() {
		return diagnostico2;
	}
	public void setDiagnostico2(String diagnostico2) {
		this.diagnostico2 = diagnostico2;
	}
	public String getDiagnostico3() {
		return diagnostico3;
	}
	public void setDiagnostico3(String diagnostico3) {
		this.diagnostico3 = diagnostico3;
	}
	public String getDiagnostico4() {
		return diagnostico4;
	}
	public void setDiagnostico4(String diagnostico4) {
		this.diagnostico4 = diagnostico4;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public BigDecimal getTelef() {
		return telef;
	}
	public void setTelef(BigDecimal telef) {
		this.telef = telef;
	}
	public Date getProximaCita() {
		return proximaCita;
	}
	public void setProximaCita(Date proximaCita) {
		this.proximaCita = proximaCita;
	}
	public String getColegio() {
		return Colegio;
	}
	public void setColegio(String colegio) {
		Colegio = colegio;
	}
	public String getHorarioClases() {
		return horarioClases;
	}
	public void setHorarioClases(String horarioClases) {
		this.horarioClases = horarioClases;
	}
	
	
	public Date getFis() {
		return fis;
	}
	public void setFis(Date fis) {
		this.fis = fis;
	}
	
	
	public Date getFif() {
		return fif;
	}
	public void setFif(Date fif) {
		this.fif = fif;
	}
	
	public String getOtroDiagnostico() {
		return otroDiagnostico;
	}
	public void setOtroDiagnostico(String otroDiagnostico) {
		this.otroDiagnostico = otroDiagnostico;
	}
	public Character getOtro() {
		return otro;
	}
	public void setOtro(Character otro) {
		this.otro = otro;
	}
	
	public String getAmPmUltDiaFiebre() {
		return amPmUltDiaFiebre;
	}
	public void setamPmUltDiaFiebre(String amPmUltDiaFiebre) {
		this.amPmUltDiaFiebre = amPmUltDiaFiebre;
	}
	public String getMotivoCancelacion() {
		return motivoCancelacion;
	}
	public void setMotivoCancelacion(String motivoCancelacion) {
		this.motivoCancelacion = motivoCancelacion;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public void setAmPmUltDiaFiebre(String amPmUltDiaFiebre) {
		this.amPmUltDiaFiebre = amPmUltDiaFiebre;
	}
	public String getEstudiosParticipantes() {
		return estudiosParticipantes;
	}
	public void setEstudiosParticipantes(String estudiosParticipantes) {
		this.estudiosParticipantes = estudiosParticipantes;
	}
	public String getNoAtiendeLlamadoEnfermeria() {
		return noAtiendeLlamadoEnfermeria;
	}
	public void setNoAtiendeLlamadoEnfermeria(String noAtiendeLlamadoEnfermeria) {
		this.noAtiendeLlamadoEnfermeria = noAtiendeLlamadoEnfermeria;
	}
	public String getNoAtiendeLlamadoMedico() {
		return noAtiendeLlamadoMedico;
	}
	public void setNoAtiendeLlamadoMedico(String noAtiendeLlamadoMedico) {
		this.noAtiendeLlamadoMedico = noAtiendeLlamadoMedico;
	}
	
	public String getCodSupervisor() {
		return codSupervisor;
	}
	public void setcodSupervisor(String codSupervisor) {
		this.codSupervisor = codSupervisor;
	}
	
	public String getNombreSupervisor() {
		return nombreSupervisor;
	}
	public void setNombreSupervisor(String nombreSupervisor) {
		this.nombreSupervisor = nombreSupervisor;
	}
	
}
