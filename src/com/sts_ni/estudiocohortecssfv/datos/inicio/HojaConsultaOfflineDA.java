package com.sts_ni.estudiocohortecssfv.datos.inicio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.axis.encoding.Base64;
import org.apache.log4j.helpers.Transform;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sts_ni.estudiocohortecssfv.servicios.HojaConsultaOfflineService;
import com.sts_ni.estudiocohortecssfv.servicios.HojaConsultaReporteService;
import com.sts_ni.estudiocohortecssfv.util.HibernateResource;
import com.sts_ni.estudiocohortecssfv.util.Mensajes;
import com.sts_ni.estudiocohortecssfv.util.UtilResultado;

import ni.com.sts.estudioCohorteCSSFV.modelo.ConsEstudios;
import ni.com.sts.estudioCohorteCSSFV.modelo.HojaConsulta;
import ni.com.sts.estudioCohorteCSSFV.modelo.Paciente;
import ni.com.sts.estudioCohorteCSSFV.modelo.SeguimientoInfluenza;
import ni.com.sts.estudioCohorteCSSFV.modelo.UsuariosView;

public class HojaConsultaOfflineDA implements HojaConsultaOfflineService {
	
	private static final HibernateResource HIBERNATE_RESOURCE = new HibernateResource();
	//private HojaConsultaReporteService consultaReporteService;
	/***
	 * Metodo obtener todas las hojas de consultas para trabajar de forma offline.
	 */
	@Override
	public String getHojasConsultasOffline() {
		String result = null;
		try {
			List oLista = new LinkedList();
			Map fila = null;
			
			String sql = "SELECT secHojaConsulta, codExpediente, numHojaConsulta, ordenLlegada, "
					+ "estado, fechaConsulta, usuarioEnfermeria, usuarioMedico, pesoKg, "
					+ "tallaCm, temperaturac, presion, fciaResp, fciaCard, lugarAtencion, "
					+ "consulta, segChick, turno, temMedc, ultDiaFiebre, ultDosisAntipiretico, "
					+ "fiebre, astenia, asomnoliento, malEstado, perdidaConsciencia, "
					+ "inquieto, convulsiones, hipotermia, letargia, cefalea, rigidezCuello, "
					+ "inyeccionConjuntival, hemorragiaSuconjuntival, dolorRetroocular, "
					+ "fontanelaAbombada, ictericiaConuntival, eritema, dolorGarganta, "
					+ "adenopatiasCervicales, exudado, petequiasMucosa, tos, rinorrea, "
					+ "congestionNasal, otalgia, aleteoNasal, apnea, respiracionRapida, "
					+ "quejidoEspiratorio, estiradorReposo, tirajeSubcostal, sibilancias, "
					+ "crepitos, roncos, otraFif, nuevaFif, pocoApetito, nausea, "
					+ "dificultadAlimentarse, vomito12horas, diarrea, diarreaSangre, "
					+ "estrenimiento, dolorAbIntermitente, dolorAbContinuo, epigastralgia, "
					+ "intoleranciaOral, distensionAbdominal, hepatomegalia, lenguaMucosasSecas, "
					+ "pliegueCutaneo, orinaReducida, bebeConSed, ojosHundidos, "
					+ "fontanelaHundida, sintomasUrinarios, leucocituria, nitritos, "
					+ "bilirrubinuria, altralgia, mialgia, lumbalgia, dolorCuello, "
					+ "tenosinovitis, artralgiaProximal, artralgiaDistal, conjuntivitis, "
					+ "edemaMunecas, edemaCodos, edemaHombros, edemaRodillas, edemaTobillos, "
					+ "rahsLocalizado, rahsGeneralizado, rashEritematoso, rahsMacular, "
					+ "rashPapular, rahsMoteada, ruborFacial, equimosis, cianosisCentral, "
					+ "ictericia, obeso, sobrepeso, sospechaProblema, normal, bajoPeso, "
					+ "bajoPesoSevero, lactanciaMaterna, vacunasCompletas, vacunaInfluenza, "
					+ "fechaVacuna, interconsultaPediatrica, referenciaHospital, "
					+ "referenciaDengue, referenciaIrag, referenciaChik, eti, irag, "
					+ "neumonia, saturaciono2, imc, categoria, cambioCategoria, manifestacionHemorragica, "
					+ "pruebaTorniquetePositiva, petequia10Pt, petequia20Pt, pielExtremidadesFrias, "
					+ "palidezEnExtremidades, epistaxis, gingivorragia, petequiasEspontaneas, "
					+ "llenadoCapilar2seg, cianosis, linfocitosaAtipicos, fechaLinfocitos, "
					+ "hipermenorrea, hematemesis, melena, hemoconcentracion, hospitalizado, "
					+ "hospitalizadoEspecificar, transfusionSangre, transfusionEspecificar, "
					+ "tomandoMedicamento, medicamentoEspecificar, medicamentoDistinto, "
					+ "medicamentoDistEspecificar, bhc, serologiaDengue, serologiaChik, "
					+ "gotaGruesa, extendidoPeriferico, ego, egh, citologiaFecal, "
					+ "factorReumatoideo, albumina, astAlt, bilirrubinas, cpk, colesterol, "
					+ "influenza, otroExamenLab, numOrdenLaboratorio, acetaminofen, "
					+ "asa, ibuprofen, penicilina, amoxicilina, dicloxacilina, otroAntibiotico, "
					+ "furazolidona, metronidazolTinidazol, albendazolMebendazol, "
					+ "sulfatoFerroso, sueroOral, sulfatoZinc, liquidosIv, prednisona, "
					+ "hidrocortisonaIv, salbutamol, oseltamivir, historiaExamenFisico, "
					+ "diagnostico1, diagnostico2, diagnostico3, diagnostico4, otroDiagnostico, "
					+ "proximaCita, horarioClases, fechaCierre, fechaCambioTurno, "
					+ "fechaCierreCambioTurno, amPmUltDiaFiebre, horaUltDosisAntipiretico, "
					+ "amPmUltDosisAntipiretico, expedienteFisico, colegio, fechaOrdenLaboratorio, "
					+ "estadoCarga, otro, fis, fif, hepatomegaliaCm, eritrocitos, "
					+ "planes, medicoCambioTurno, hemoconc, vomito12h, pad, pas, telef, "
					+ "oel, hora, horasv, noAtiendeLlamadoEnfermeria, noAtiendeLlamadoMedico, "
					+ "estudiosParticipantes, uaf, repeatKey, cv, consultaRespiratorio, "
					+ "usuarioCierraHoja, hojaImpresa "
					+ "FROM HojaConsulta "
					//+ "ORDER BY numHojaConsulta DESC "
					+ "WHERE estado in('7') ORDER BY numHojaConsulta DESC";
			
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
			query.setMaxResults(2000);
			
			List<Object[]> objLista = (query.list() != null) ? (List<Object[]>) query.list() : new ArrayList<Object[]>();
			
			if (objLista != null && objLista.size() > 0) {
				
				for (Object[] object : objLista) {
					// Construir la fila del registro actual usando arreglos
					
					fila = new HashMap();
					fila.put("secHojaConsulta", object[0].toString());
					fila.put("codExpediente", object[1].toString());
					fila.put("numHojaConsulta", object[2].toString());
					fila.put("numOrdenLlegada", object[3].toString());
					fila.put("estado", object[4].toString());
					fila.put("fechaConsulta", object[5].toString());
					fila.put("usuarioEnfermeria", object[6] != null ? object[6].toString() : null);
					fila.put("usuarioMedico", object[7] != null ? object[7].toString() : null);
					fila.put("pesoKg", object[8] != null ? object[8].toString(): null );
					fila.put("tallaCm", object[9] != null ? object[9].toString() : null);
					fila.put("temperaturac", object[10] != null ? object[10].toString() : null);
					fila.put("presion", object[11] != null ? object[11].toString() : null);
					fila.put("fciaResp", object[12] != null ? object[12].toString() : null);
					fila.put("fciaCard", object[13] != null ? object[13].toString() : null);
					fila.put("lugarAtencion", object[14] != null ? object[14].toString() : null);
					fila.put("consulta", object[15] != null ? object[15].toString() : null);
					fila.put("segChick", object[16] != null ? object[16].toString() : null);
					fila.put("turno", object[17] != null ? object[17].toString() : null);
					fila.put("temMedc", object[18] != null ? object[18].toString() : null);
					fila.put("ultDiaFiebre", object[19] !=  null ? object[19].toString() : null);
					fila.put("ultDosisAntipiretico", object[20] != null ? object[20].toString() : null);
					fila.put("fiebre", object[21] != null ? object[21].toString() : null);
					fila.put("astenia", object[22] != null ? object[22].toString() : null);
					fila.put("asomnoliento", object[23] != null ? object[23].toString() : null);
					fila.put("malEstado", object[24] != null ? object[24].toString() : null);
					fila.put("perdidaConsciencia", object[25] != null ? object[25].toString() : null);
					fila.put("inquieto", object[26] != null ? object[26].toString() : null);
					fila.put("convulsiones", object[27] != null ? object[27].toString() : null);
					fila.put("hipotermia", object[28] != null ? object[28].toString() : null);
					fila.put("letargia", object[29] != null ? object[29].toString() : null);
					fila.put("cefalea", object[30] != null ? object[30].toString() : null);
					fila.put("rigidezCuello", object[31] != null ? object[31].toString() : null);
					fila.put("inyeccionConjuntival", object[32] != null ? object[32].toString() : null);
					fila.put("hemorragiaSuconjuntival", object[33] != null ? object[33].toString() : null);
					fila.put("dolorRetroocular", object[34] != null ? object[34].toString() : null);
					fila.put("fontanelaAbombada", object[35] != null ? object[35].toString() : null);
					fila.put("ictericiaConuntival", object[36] != null ? object[36].toString() : null);
					fila.put("eritema", object[37] != null ? object[37].toString() : null);
					fila.put("dolorGarganta", object[38] != null ? object[38].toString() : null);
					fila.put("adenopatiasCervicales", object[39] != null ? object[39].toString() : null);
					fila.put("exudado", object[40] != null ? object[40].toString() : null);
					fila.put("petequiasMucosa", object[41] != null ? object[41].toString() : null);
					fila.put("tos", object[42] != null ? object[42].toString() : null);
					fila.put("rinorrea", object[43] != null ? object[43].toString() : null);
					fila.put("congestionNasal", object[44] != null ? object[44].toString() : null);
					fila.put("otalgia", object[45] != null ? object[45].toString() : null);
					fila.put("aleteoNasal", object[46] != null ? object[46].toString() : null);
					fila.put("apnea", object[47] != null ? object[47].toString() : null);
					fila.put("respiracionRapida", object[48] != null ? object[48].toString() : null);
					fila.put("quejidoEspiratorio", object[49] != null ? object[49].toString() : null);
					fila.put("estiradorReposo", object[50] != null ? object[50].toString() : null);
					fila.put("tirajeSubcostal", object[51] != null ? object[51].toString() : null);
					fila.put("sibilancias", object[52] != null ? object[52].toString() : null);
					fila.put("crepitos", object[53] != null ? object[53].toString() : null);
					fila.put("roncos", object[54] != null ? object[54].toString() : null);
					fila.put("otraFif", object[55] != null ? object[55].toString() : null);
					fila.put("nuevaFif", object[56] != null ? object[56].toString() : null);
					fila.put("pocoApetito", object[57] != null ? object[57].toString() : null);
					fila.put("nausea", object[58] != null ? object[58].toString() : null);
					fila.put("dificultadAlimentarse", object[59] != null ? object[59].toString() : null);
					fila.put("vomito12horas", object[60] != null ? object[60].toString() : null);
					fila.put("diarrea", object[61] != null ? object[61].toString() : null);
					fila.put("diarreaSangre", object[62] != null ? object[62].toString() : null);
					fila.put("estrenimiento", object[63] != null ? object[63].toString() : null);
					fila.put("dolorAbIntermitente", object[64] != null ? object[64].toString() : null);
					fila.put("dolorAbContinuo", object[65] != null ? object[65].toString() : null);
					fila.put("epigastralgia", object[66] != null ? object[66].toString() : null);
					fila.put("intoleranciaOral", object[67] != null ? object[67].toString() : null);
					fila.put("distensionAbdominal", object[68] != null ? object[68].toString() : null);
					fila.put("hepatomegalia", object[69] != null ? object[69].toString() : null);
					fila.put("lenguaMucosasSecas", object[70] != null ? object[70].toString() : null);
					fila.put("pliegueCutaneo", object[71] != null ? object[71].toString() : null);
					fila.put("orinaReducida", object[72] != null ? object[72].toString() : null);
					fila.put("bebeConSed", object[73] != null ? object[73].toString() : null);
					fila.put("ojosHundidos", object[74] != null ? object[74].toString() : null);
					fila.put("fontanelaHundida", object[75] != null ? object[75].toString() : null);
					fila.put("sintomasUrinarios", object[76] != null ? object[76].toString() : null);
					fila.put("leucocituria", object[77] != null ? object[77].toString() : null);
					fila.put("nitritos", object[78] != null ? object[78].toString() : null);
					fila.put("bilirrubinuria", object[79] != null ? object[79].toString() : null);
					fila.put("altralgia", object[80] != null ? object[80].toString() : null);
					fila.put("mialgia", object[81] != null ? object[81].toString() : null);
					fila.put("lumbalgia", object[82] != null ? object[82].toString() : null);
					fila.put("dolorCuello", object[83] != null ? object[83].toString() : null);
					fila.put("tenosinovitis", object[84] != null ? object[84].toString() : null);
					fila.put("artralgiaProximal", object[85] != null ? object[85].toString() : null);
					fila.put("artralgiaDistal", object[86] != null ? object[86].toString() : null);
					fila.put("conjuntivitis", object[87] != null ? object[87].toString() : null);
					fila.put("edemaMunecas", object[88] != null ? object[88].toString() : null);
					fila.put("edemaCodos", object[89] != null ? object[89].toString() : null);
					fila.put("edemaHombros", object[90] != null ? object[90].toString() : null);
					fila.put("edemaRodillas", object[91] != null ? object[91].toString() : null);
					fila.put("edemaTobillos", object[92] != null ? object[92].toString() : null);
					fila.put("rahsLocalizado", object[93] != null ? object[93].toString() : null);
					fila.put("rahsGeneralizado", object[94] != null ? object[94].toString() : null);
					fila.put("rashEritematoso", object[95] != null ? object[95].toString() : null);
					fila.put("rahsMacular", object[96] != null ? object[96].toString() : null);
					fila.put("rashPapular", object[97] != null ? object[97].toString() : null);
					fila.put("rahsMoteada", object[98] != null ? object[98].toString() : null);
					fila.put("ruborFacial", object[99] != null ? object[99].toString() : null);
					fila.put("equimosis", object[100] != null ? object[100].toString() : null);
					fila.put("cianosisCentral", object[101] != null ? object[101].toString() : null);
					fila.put("ictericia", object[102] != null ? object[102].toString() : null);
					fila.put("obeso", object[103] != null ? object[103].toString() : null);
					fila.put("sobrepeso", object[104] != null ? object[104].toString() : null);
					fila.put("sospechaProblema", object[105] != null ? object[105].toString() : null);
					fila.put("normal", object[106] != null ? object[106].toString() : null);
					fila.put("bajoPeso", object[107] != null ? object[107].toString() : null);
					fila.put("bajoPesoSevero", object[108] != null ?object[108].toString() : null);
					fila.put("lactanciaMaterna", object[109] != null ? object[109].toString() : null);
					fila.put("vacunasCompletas", object[110] != null ? object[110].toString() : null);
					fila.put("vacunaInfluenza", object[111] != null ? object[111].toString() : null);
					fila.put("fechaVacuna", object[112] != null ?  object[112].toString() : null);
					fila.put("interconsultaPediatrica", object[113] != null ? object[113].toString() : null);
					fila.put("referenciaHospital", object[114] != null ? object[114].toString() : null);
					fila.put("referenciaDengue", object[115] != null ? object[115].toString() : null);
					fila.put("referenciaIrag", object[116] != null ? object[116].toString() : null);
					fila.put("referenciaChik", object[117] != null ? object[117].toString() : null);
					fila.put("eti", object[118] != null ? object[118].toString() : null);
					fila.put("irag", object[119] != null ? object[119].toString() : null);
					fila.put("neumonia", object[120] != null ? object[120].toString() : null);
					fila.put("saturaciono2", object[121] != null ? object[121].toString() : null);
					fila.put("imc", object[122] != null ? object[122].toString() : null);
					fila.put("categoria", object[123] != null ? object[123].toString() : null);
					fila.put("cambioCategoria", object[124] != null ? object[124].toString() : null);
					fila.put("manifestacionHemorragica", object[125] != null ? object[125].toString() : null);
					fila.put("pruebaTorniquetePositiva", object[126] != null ? object[126].toString() : null);
					fila.put("petequia10Pt", object[127] != null ? object[127].toString() : null);
					fila.put("petequia20Pt", object[128] != null ? object[128].toString() : null);
					fila.put("pielExtremidadesFrias", object[129] != null ? object[129].toString() : null);
					fila.put("palidezEnExtremidades", object[130] != null ? object[130].toString() : null);
					fila.put("epistaxis", object[131] != null ? object[131].toString() : null);
					fila.put("gingivorragia", object[132] != null ? object[132].toString() : null);
					fila.put("petequiasEspontaneas", object[133] != null ? object[133].toString() : null);
					fila.put("llenadoCapilar2seg", object[134] != null ? object[134].toString() : null);
					fila.put("cianosis", object[135] != null ? object[135].toString() : null);
					fila.put("linfocitosaAtipicos", object[136] != null ? object[136].toString() : null);
					fila.put("fechaLinfocitos", object[137] != null ? object[137].toString() : null);
					fila.put("hipermenorrea", object[138] != null ? object[138].toString() : null);
					fila.put("hematemesis", object[139] != null ? object[139].toString() : null);
					fila.put("melena", object[140] != null ? object[140].toString() : null);
					fila.put("hemoconcentracion", object[141] != null ? object[141].toString() : null);
					fila.put("hospitalizado", object[142] != null ? object[142].toString() : null);
					fila.put("hospitalizadoEspecificar", object[143] != null ? object[143].toString() : null);
					fila.put("transfusionSangre", object[144] != null ? object[144].toString() : null);
					fila.put("transfusionEspecificar", object[145] != null ? object[145].toString() : null);
					fila.put("tomandoMedicamento", object[146] != null ? object[146].toString() : null);
					fila.put("medicamentoEspecificar", object[147] != null ? object[147].toString() : null);
					fila.put("medicamentoDistinto", object[148] != null ? object[148].toString() : null);
					fila.put("medicamentoDistEspecificar", object[149] != null ? object[149].toString() : null);
					fila.put("bhc", object[150] != null ? object[150].toString() : null);
					fila.put("serologiaDengue", object[151] != null ? object[151].toString() : null);
					fila.put("serologiaChik", object[152] != null ? object[152].toString() : null);
					fila.put("gotaGruesa", object[153] != null ? object[153].toString() : null);
					fila.put("extendidoPeriferico", object[154] != null ? object[154].toString() : null);
					fila.put("ego", object[155] != null ? object[155].toString() : null);
					fila.put("egh", object[156] != null ? object[156].toString() : null);
					fila.put("citologiaFecal", object[157] != null ? object[157].toString() : null);
					fila.put("factorReumatoideo", object[158] != null ? object[158].toString() : null);
					fila.put("albumina", object[159] != null ? object[159].toString() : null);
					fila.put("astAlt", object[160] != null ? object[160].toString() : null);
					fila.put("bilirrubinas", object[161] != null ? object[161].toString() : null);
					fila.put("cpk", object[162] != null ? object[162].toString() : null);
					fila.put("colesterol", object[163] != null ? object[163].toString() : null);
					fila.put("influenza", object[164] != null ? object[164].toString() : null);
					fila.put("otroExamenLab", object[165] != null ? object[165].toString() : null);
					fila.put("numOrdenLaboratorio", object[166] != null ? object[166].toString() : null);
					fila.put("acetaminofen", object[167] != null ? object[167].toString() : null);
					fila.put("asa", object[168] != null ? object[168].toString() : null);
					fila.put("ibuprofen", object[169] != null ? object[169].toString() : null);
					fila.put("penicilina", object[170] != null ? object[170].toString() : null);
					fila.put("amoxicilina", object[171] != null ? object[171].toString() : null);
					fila.put("dicloxacilina", object[172] != null ? object[172].toString() : null);
					fila.put("otroAntibiotico", object[173] != null ? object[173].toString() : null);
					fila.put("furazolidona", object[174] != null ? object[174].toString() : null);
					fila.put("metronidazolTinidazol", object[175] != null ? object[175].toString() : null);
					fila.put("albendazolMebendazol", object[176] != null ? object[176].toString() : null);
					fila.put("sulfatoFerroso", object[177] != null ? object[177].toString() : null);
					fila.put("sueroOral", object[178] != null ? object[178].toString() : null);
					fila.put("sulfatoZinc", object[179] != null ? object[179].toString() : null);
					fila.put("liquidosIv", object[180] != null ? object[180].toString() : null);
					fila.put("prednisona", object[181] != null ? object[181].toString() : null);
					fila.put("hidrocortisonaIv", object[182] != null ? object[182].toString() : null);
					fila.put("salbutamol", object[183] != null ? object[183].toString() : null);
					fila.put("oseltamivir", object[184] != null ? object[184].toString() : null);
					fila.put("historiaExamenFisico", object[185] != null ? object[185].toString() : null);
					fila.put("diagnostico1", object[186] != null ? object[186].toString() : null);
					fila.put("diagnostico2", object[187] != null ? object[187].toString() : null);
					fila.put("diagnostico3", object[188] != null ? object[188].toString() : null);
					fila.put("diagnostico4", object[189] != null ? object[189].toString() : null);
					fila.put("otroDiagnostico", object[190] != null ? object[190].toString() : null);
					fila.put("proximaCita", object[191] != null ? object[191].toString() : null);
					fila.put("horarioClases", object[192] != null ? object[192].toString() : null);
					fila.put("fechaCierre", object[193] != null ? object[193].toString() : null);
					fila.put("fechaCambioTurno", object[194] != null ? object[194].toString() : null);
					fila.put("fechaCierreCambioTurno", object[195] != null ? object[195].toString() : null);
					fila.put("amPmUltDiaFiebre", object[196] != null ? object[196].toString() : null);
					fila.put("horaUltDosisAntipiretico", object[197] != null ? object[197].toString() : null);
					fila.put("amPmUltDosisAntipiretico", object[198] != null ? object[198].toString() : null);
					fila.put("expedienteFisico", object[199] != null ? object[199].toString() : null);
					fila.put("colegio", object[200] != null ? object[200].toString() : null);
					fila.put("fechaOrdenLaboratorio", object[201] != null ? object[201].toString() : null);
					fila.put("estadoCarga", object[202] != null ? object[202].toString() : null);
					fila.put("otro", object[203] != null ? object[203].toString() : null);
					fila.put("fis", object[204] != null ? object[204].toString() : null);
					fila.put("fif", object[205] != null ? object[205].toString() : null);
					fila.put("hepatomegaliaCm", object[206] != null ? object[206].toString() : null);
					fila.put("eritrocitos", object[207] != null ? object[207].toString() : null);
					fila.put("planes", object[208] != null ? object[208].toString() : null);
					fila.put("medicoCambioTurno", object[209] != null ? object[209].toString() : null);
					fila.put("hemoconc", object[210] != null ? object[210].toString() : null);
					fila.put("vomito12h", object[211] != null ? object[211].toString() : null);
					fila.put("pad", object[212] != null ? object[212].toString() : null);
					fila.put("pas", object[213] != null ? object[213].toString() : null);
					fila.put("telef", object[214] != null ? object[214].toString() : null);
					fila.put("oel", object[215] != null ? object[215].toString() : null);	
					fila.put("hora", object[216] !=  null ? object[216].toString() : null);
					fila.put("horasv", object[217] != null ? object[217].toString() : null);
					fila.put("noAtiendeLlamadoEnfermeria", object[218] != null ? object[218].toString() : null);
					fila.put("noAtiendeLlamadoMedico", object[219] != null ? object[219].toString() : null);
					fila.put("estudiosParticipantes", object[220] != null ? object[220].toString() : null);
					//fila.put("uaf", object[221].toString());
					//fila.put("repeatKey", object[222] != null ? object[222].toString() : null);
					fila.put("cV", object[223] != null ? object[223].toString() : null); 
					fila.put("consultaRespiratorio", object[224] != null ? object[224].toString() : null);
					//fila.put("usuarioCierraHoja", object[225] != null ? object[225].toString() : null);
					//fila.put("hojaImpresa", object[226] != null ? object[226].toString() : null);
					
					oLista.add(fila);
				}
				
			}
			
			result = UtilResultado.parserResultado(oLista, "",
					UtilResultado.OK);
		} catch (Exception e) {
			// TODO: handle exception
				e.printStackTrace();
				result = UtilResultado.parserResultado(null, Mensajes.ERROR_NO_CONTROLADO,
						UtilResultado.ERROR);
			 
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
			freememory();
		}
		return result;
	}
	
	public void freememory() {
		Runtime.getRuntime().runFinalization();
		Runtime.getRuntime().gc();
		System.gc();
    }
	
	/***
	 * Metodo obtener todos los usuarios para trabajar de forma offline.
	 */
	@Override
	public String getUsuariosOffline() {
		String result = null;
		try {
			
			List oLista = new LinkedList();
			Map fila = null;
			
			String sql = "select uv.id, uv.nombre, uv.codigopersonal, " +
						" uv.usuario, uv.pass " +
						" from usuarios_view uv ";

			Query query = HIBERNATE_RESOURCE.getSession().createSQLQuery(sql);

			List<Object[]> objLista = (query.list() != null) ? (List<Object[]>) query.list() : new ArrayList<Object[]>();
			
			if (objLista != null && objLista.size() > 0) {

				for (Object[] object : objLista) {
					// Construir la fila del registro actual usando arreglos

					fila = new HashMap();
					fila.put("id", object[0]);						
					fila.put("nombre", object[1].toString());
					fila.put("codigoPersonal", object[2] != null ? object[2].toString(): "-" );
					fila.put("usuario", object[3].toString());
					fila.put("pass", object[4].toString());
					
					oLista.add(fila);
				}
			}
						
			result = UtilResultado.parserResultado(oLista, "",
					UtilResultado.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
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
	 * Metodo obtener todos los roles para trabajar de forma offline.
	 */
	@Override
	public String getRolesOffline() {
		String result = null;
		try {
			
			List oLista = new LinkedList();
			Map fila = null;
			
			String sql = "select rv.nombre, " +
						" rv.usuario " +
						" from roles_view rv ";

			Query query = HIBERNATE_RESOURCE.getSession().createSQLQuery(sql);

			List<Object[]> objLista = (query.list() != null) ? (List<Object[]>) query.list() : new ArrayList<Object[]>();
			Integer cont = 1;
			
			if (objLista != null && objLista.size() > 0) {

				for (Object[] object : objLista) {
					// Construir la fila del registro actual usando arreglos

					fila = new HashMap();
					fila.put("id", cont);						
					fila.put("nombre", object[0].toString());
					fila.put("usuario", object[1].toString());
					
					oLista.add(fila);
					cont ++;
				}
			}
						
			result = UtilResultado.parserResultado(oLista, "",
					UtilResultado.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
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
	 * Metodo obtener todos los estudios.
	 */
	@Override
	public String getEstudiosOffline() {
		String result = null;
		try {
			
			List oLista = new LinkedList();
			Map fila = null;
			
			String sql = "select a.cod_estudio, a.desc_estudio " +
						" from estudio_catalogo a ";

			Query query = HIBERNATE_RESOURCE.getSession().createSQLQuery(sql);

			List<Object[]> objLista = (query.list() != null) ? (List<Object[]>) query.list() : new ArrayList<Object[]>();
			
			if (objLista != null && objLista.size() > 0) {

				for (Object[] object : objLista) {
					// Construir la fila del registro actual usando arreglos

					fila = new HashMap();
					fila.put("codEstudio", object[0].toString());
					fila.put("descEstudio", object[1].toString());
					
					oLista.add(fila);
				}
			}
						
			result = UtilResultado.parserResultado(oLista, "",
					UtilResultado.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
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
	 * Metodo obtener todos los pacientes.
	 */
	@Override
	public String getParticipantesOffline() {
		String result = null;
		try {
			
			List oLista = new LinkedList();
			Map fila = null;
			
			String sql = "select a.sec_paciente, a.cod_expediente, a.nombre1, "
					+ " a.nombre2, a.apellido1, a.apellido2, a.sexo, "
					+ " a.direccion, a.fecha_nac, a.retirado " +
					" from paciente a ";

			Query query = HIBERNATE_RESOURCE.getSession().createSQLQuery(sql);

			List<Object[]> objLista = (query.list() != null) ? (List<Object[]>) query.list() : new ArrayList<Object[]>();
			
			if (objLista != null && objLista.size() > 0) {

				for (Object[] object : objLista) {
					// Construir la fila del registro actual usando arreglos

					fila = new HashMap();
					fila.put("secPaciente", object[0].toString());
					fila.put("codExpediente", object[1].toString());
					fila.put("nombrePaciente", object[2].toString() + " "
							+ ((object[3] != null) ? object[3].toString() : "") + " "
							+ object[4].toString() + " "
							+ ((object[5] != null) ? object[5].toString() : ""));
					fila.put("sexo", object[6].toString());
					fila.put("direccion", object[7] != null ? object[7].toString() : "-");
					fila.put("fechaNac", object[8].toString());
					fila.put("retirado", object[9].toString());
					
					
					oLista.add(fila);
				}
			}
						
			result = UtilResultado.parserResultado(oLista, "",
					UtilResultado.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
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
	 * Metodo obtener todos los estados de las hojas.
	 */
	@Override
	public String getEstadosHojasOffline() {
		String result = null;
		try {
			
			List oLista = new LinkedList();
			Map fila = null;
			
			String sql = "select a.sec_estado, a.codigo, a.descripcion, a.estado, a.orden " +
						" from estados_hoja a ";

			Query query = HIBERNATE_RESOURCE.getSession().createSQLQuery(sql);

			List<Object[]> objLista = (query.list() != null) ? (List<Object[]>) query.list() : new ArrayList<Object[]>();
			
			if (objLista != null && objLista.size() > 0) {

				for (Object[] object : objLista) {
					// Construir la fila del registro actual usando arreglos

					fila = new HashMap();
					fila.put("secEstado", object[0].toString());
					fila.put("codigo", object[1].toString());
					fila.put("descripcion", object[2].toString());
					fila.put("estado", object[3].toString());
					fila.put("orden", object[4].toString());
					
					oLista.add(fila);
				}
			}
						
			result = UtilResultado.parserResultado(oLista, "",
					UtilResultado.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
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
	 * Metodo obtener todos los cons_estudios.
	 */
	@Override
	public String getConsEstudiosOffline() {
		String result = null;
		try {
			
			List oLista = new LinkedList();
			Map fila = null;
			
			String sql = "select a.sec_cons_estudios, a.codigo_expediente, a.codigo_consentimiento, a.retirado " +
						" from cons_estudios a ";

			Query query = HIBERNATE_RESOURCE.getSession().createSQLQuery(sql);

			List<Object[]> objLista = (query.list() != null) ? (List<Object[]>) query.list() : new ArrayList<Object[]>();
			
			if (objLista != null && objLista.size() > 0) {

				for (Object[] object : objLista) {
					// Construir la fila del registro actual usando arreglos

					fila = new HashMap();
					fila.put("secConsEstudios", object[0].toString());
					fila.put("codigoExpediente", object[1].toString());
					fila.put("codigoConsentimiento", object[2].toString());
					fila.put("retirado", object[3].toString());
					
					oLista.add(fila);
				}
			}
						
			result = UtilResultado.parserResultado(oLista, "",
					UtilResultado.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
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
	
	@Override
	public String getCantidadHojasConsultas() {
		String result = "";
		try {
			String sql = "SELECT count(*) as cantidad from hoja_consulta";
			Query query = HIBERNATE_RESOURCE.getSession().createSQLQuery(sql);
			result = query.uniqueResult().toString();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		
	    
	    return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public String guardarHojaConsultaOffline(String paramHojaConsulta) {
		String result = null;
		try {
			
			HojaConsulta hojaConsulta;
			
			JSONParser parser = new JSONParser();
			Object obj = (Object) parser.parse(paramHojaConsulta);
			JSONArray hojaConsultaArray = (JSONArray) obj;
			List<HojaConsulta> hojaConsultaList = new ArrayList<>();
			if (paramHojaConsulta != "") {
				for (int i = 0; i < hojaConsultaArray.size(); i++) {
					
					hojaConsulta = new HojaConsulta();
					obj = new Object();
					obj = (Object) parser.parse(hojaConsultaArray
							.get(i).toString());
					JSONObject hojaConsultaJSON = (JSONObject) obj;
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
					
					Date fechaConsulta = format.parse(hojaConsultaJSON.get("fechaConsulta").toString());
					Integer codExpediente = Integer.valueOf(hojaConsultaJSON.get("codExpediente").toString());
					
					/*String sql = "SELECT count(*) FROM hoja_consulta a "
							+ "WHERE a.cod_expediente = :codExpediente "
							+ "AND to_char(a.fecha_consulta, 'yyyyMMdd') = :fechaConsulta";*/
					String sql = "SELECT count(*) FROM hoja_consulta a "
							+ "WHERE a.cod_expediente = :codExpediente "
							+ "AND a.fecha_consulta = :fechaConsulta";
					//to_char(fecha_consulta, 'yyyyMMdd')
					Query query = HIBERNATE_RESOURCE.getSession().createSQLQuery(sql);
					query.setParameter("codExpediente", codExpediente);
					query.setParameter("fechaConsulta", fechaConsulta);
					
					BigInteger total = (BigInteger) query.uniqueResult();
					
					if (total.intValue() <= 0) {
						/*Generales Sintomas*/
						Short pas = ((Number) hojaConsultaJSON.get("pas")).shortValue();
						Short pad = ((Number) hojaConsultaJSON.get("pad")).shortValue();
						
						Double pesoKg = (((Number) hojaConsultaJSON.get("pesoKg")).doubleValue());
						Double tallaCm = ((((Number) hojaConsultaJSON.get("tallaCm"))
								.doubleValue()));
						Double temperaturac = (((Number) hojaConsultaJSON.get("temperaturac"))
								.doubleValue());
						String expedienteFisico = ((hojaConsultaJSON.get("expedienteFisico")
								.toString()));
					 	Integer fciaResp = ((Number) hojaConsultaJSON.get("fciaResp")).intValue();
						Integer fciaCard = ((Number) hojaConsultaJSON.get("fciaCard")).intValue();
						String lugarAtencion = ((hojaConsultaJSON.get("lugarAtencion").toString()));
						String horas = ((hojaConsultaJSON.get("hora").toString()));
						String horasv = ((hojaConsultaJSON.get("horasv").toString()));
						String consulta = ((hojaConsultaJSON.get("consulta").toString()));
						String segChick = (hojaConsultaJSON.containsKey("segChick")) ? 
								hojaConsultaJSON.get("segChick").toString() : null;
						String turno = ((hojaConsultaJSON.get("turno").toString()));
						Double temMedc = (((Number) hojaConsultaJSON.get("temMedc")).doubleValue());
						String fis = (hojaConsultaJSON.containsKey("fis")) ?
								hojaConsultaJSON.get("fis").toString() : null;
						String fif = (hojaConsultaJSON.containsKey("fif")) ? 
								hojaConsultaJSON.get("fif").toString() : null;
						String ultDiaFiebre = (hojaConsultaJSON.containsKey("ultDiaFiebre")) ? 
								hojaConsultaJSON.get("ultDiaFiebre").toString() : null;
						String ultDosisAntipiretico = (hojaConsultaJSON.containsKey("ultDosisAntipiretico")) ? 
								hojaConsultaJSON.get("ultDosisAntipiretico").toString() : null;
						String amPmUltDiaFiebre = hojaConsultaJSON.containsKey("amPmUltDiaFiebre") ? 
								hojaConsultaJSON.get("amPmUltDiaFiebre").toString() : null;			
						String horaUltDosisAntipiretico = hojaConsultaJSON.containsKey("horaUltDosisAntipiretico") ? 
								hojaConsultaJSON.get("horaUltDosisAntipiretico").toString() : null;
						String amPmUltDosisAntipiretico = hojaConsultaJSON.containsKey("amPmUltDosisAntipiretico") ? 
								hojaConsultaJSON.get("amPmUltDosisAntipiretico").toString() : null;
						/*Estado General Sintoma*/
						Character fiebre = (hojaConsultaJSON.get("fiebre").toString().charAt(0));
						Character astenia = (hojaConsultaJSON.get("astenia").toString().charAt(0));
						Character asomnoliento = (hojaConsultaJSON.get("asomnoliento").toString()
								.charAt(0));
						Character malEstado = (hojaConsultaJSON.get("malEstado").toString().charAt(0));
						Character perdidaConsciencia = (hojaConsultaJSON.get("perdidaConsciencia")
								.toString().charAt(0));
						Character inquieto = (hojaConsultaJSON.get("inquieto").toString().charAt(0));
						Character convulsiones = (hojaConsultaJSON.get("convulsiones").toString()
								.charAt(0));
						Character hipotermia = (hojaConsultaJSON.get("hipotermia").toString()
								.charAt(0));
						Character letargia = (hojaConsultaJSON.get("letargia").toString().charAt(0));
						/*GastroIntestinal Sintoma*/
						Character pocoApetito = (hojaConsultaJSON.get("pocoApetito").toString()
								.charAt(0));
						Character nausea = (hojaConsultaJSON.get("nausea").toString().charAt(0));
						Character dificultadAlimentarse = (hojaConsultaJSON.get(
								"dificultadAlimentarse").toString().charAt(0));
						Character vomito12horas = (hojaConsultaJSON.get("vomito12horas").toString()
								.charAt(0));
						Short vomito12h = hojaConsultaJSON.get("vomito12h") != null
								&& !hojaConsultaJSON.get("vomito12h").equals("null") ? ((Number) hojaConsultaJSON
								.get("vomito12h")).shortValue() : null;
						Character diarrea = (hojaConsultaJSON.get("diarrea").toString().charAt(0));			
						Character diarreaSangre = (hojaConsultaJSON.get("diarreaSangre").toString()
								.charAt(0));
						Character estrenimiento = (hojaConsultaJSON.get("estrenimiento").toString()
								.charAt(0));
						Character dolorAbIntermitente = (hojaConsultaJSON.get("dolorAbIntermitente")
								.toString().charAt(0));
						Character dolorAbContinuo = (hojaConsultaJSON.get("dolorAbContinuo")
								.toString().charAt(0));
						Character epigastralgia = (hojaConsultaJSON.get("epigastralgia").toString()
								.charAt(0));
						Character intoleranciaOral = (hojaConsultaJSON.get("intoleranciaOral")
								.toString().charAt(0));
						Character distensionAbdominal = (hojaConsultaJSON.get("distensionAbdominal")
								.toString().charAt(0));
						Character hepatomegalia = (hojaConsultaJSON.get("hepatomegalia").toString()
								.charAt(0));
						Double hepatomegaliaCM = hojaConsultaJSON.get("hepatomegaliaCM") != null
								&& !hojaConsultaJSON.get("hepatomegaliaCM").equals("null") ? ((Number) hojaConsultaJSON
								.get("hepatomegaliaCM")).doubleValue() : null;
						/*Osteomuscular Sintoma*/
						Character altralgia = (hojaConsultaJSON.get("altralgia").toString().charAt(0));
						Character mialgia = (hojaConsultaJSON.get("mialgia").toString().charAt(0));
						Character lumbalgia = (hojaConsultaJSON.get("lumbalgia").toString().charAt(0));
						Character dolorCuello = (hojaConsultaJSON.get("dolorCuello").toString()
								.charAt(0));
						Character tenosinovitis = (hojaConsultaJSON.get("tenosinovitis").toString()
								.charAt(0));
						Character artralgiaProximal = (hojaConsultaJSON.get("artralgiaProximal")
								.toString().charAt(0));
						Character artralgiaDistal = (hojaConsultaJSON.get("artralgiaDistal")
								.toString().charAt(0));
						Character conjuntivitis = (hojaConsultaJSON.get("conjuntivitis").toString()
								.charAt(0));
						Character edemaMunecas = (hojaConsultaJSON.get("edemaMunecas").toString()
								.charAt(0));
						Character edemaCodos = (hojaConsultaJSON.get("edemaCodos").toString()
								.charAt(0));
						Character edemaHombros = (hojaConsultaJSON.get("edemaHombros").toString()
								.charAt(0));
						Character edemaRodillas = (hojaConsultaJSON.get("edemaRodillas").toString()
								.charAt(0));
						Character edemaTobillos = (hojaConsultaJSON.get("edemaTobillos").toString()
								.charAt(0));
						/*Cabeza Sintomas*/
						Character cefalea = (hojaConsultaJSON.get("cefalea").toString().charAt(0));
						Character rigidezCuello = (hojaConsultaJSON.get("rigidezCuello").toString()
								.charAt(0));
						Character inyeccionConjuntival = (hojaConsultaJSON
								.get("inyeccionConjuntival").toString().charAt(0));
						Character hemorragiaSuconjuntival = (hojaConsultaJSON.get(
								"hemorragiaSuconjuntival").toString().charAt(0));
						Character dolorRetroocular = (hojaConsultaJSON.get("dolorRetroocular")
								.toString().charAt(0));
						Character fontanelaAbombada = (hojaConsultaJSON.get("fontanelaAbombada")
								.toString().charAt(0));
						Character ictericiaConuntival = (hojaConsultaJSON.get("ictericiaConuntival")
								.toString().charAt(0));
						/*Deshidratacion Sintomas*/
						Character lenguaMucosasSecas = (hojaConsultaJSON.get("lenguaMucosasSecas")
								.toString().charAt(0));
						Character pliegueCutaneo = (hojaConsultaJSON.get("pliegueCutaneo").toString()
								.charAt(0));
						Character orinaReducida = (hojaConsultaJSON.get("orinaReducida").toString()
								.charAt(0));
						Character bebeConSed = (hojaConsultaJSON.get("bebeConSed").toString()
								.charAt(0));
						String ojosHundidos = (hojaConsultaJSON.get("ojosHundidos").toString());
						Character fontanelaHundida = (hojaConsultaJSON.get("fontanelaHundida")
								.toString().charAt(0));
						/*Cutaneo Sintomas*/
						Character rahsLocalizado = (hojaConsultaJSON.get("rahsLocalizado").toString()
								.charAt(0));
						Character rahsGeneralizado = (hojaConsultaJSON.get("rahsGeneralizado")
								.toString().charAt(0));
						Character rashEritematoso = (hojaConsultaJSON.get("rashEritematoso")
								.toString().charAt(0));
						Character rahsMacular = (hojaConsultaJSON.get("rahsMacular").toString()
								.charAt(0));
						Character rashPapular = (hojaConsultaJSON.get("rashPapular").toString()
								.charAt(0));
						Character rahsMoteada = (hojaConsultaJSON.get("rahsMoteada").toString()
								.charAt(0));
						Character ruborFacial = (hojaConsultaJSON.get("ruborFacial").toString()
								.charAt(0));
						Character equimosis = (hojaConsultaJSON.get("equimosis").toString().charAt(0));
						Character cianosisCentral = (hojaConsultaJSON.get("cianosisCentral")
								.toString().charAt(0));
						Character ictericia = (hojaConsultaJSON.get("ictericia").toString().charAt(0));
						/*Garganta Sintomas*/
						Character eritema = (hojaConsultaJSON.get("eritema").toString().charAt(0));
						Character dolorGarganta = (hojaConsultaJSON.get("dolorGarganta").toString()
								.charAt(0));
						Character adenopatiasCervicales = (hojaConsultaJSON.get(
								"adenopatiasCervicales").toString().charAt(0));
						Character exudado = (hojaConsultaJSON.get("exudado").toString().charAt(0));
						Character petequiasMucosa = (hojaConsultaJSON.get("petequiasMucosa")
								.toString().charAt(0));
						/*Renal Sintomas*/
						Character sintomasUrinarios = (hojaConsultaJSON.get("sintomasUrinarios")
								.toString().charAt(0));
						Character leucocituria = (hojaConsultaJSON.get("leucocituria").toString()
								.charAt(0));
						Character nitritos = (hojaConsultaJSON.get("nitritos").toString().charAt(0));
						Character eritrocitos = (hojaConsultaJSON.get("eritrocitos").toString()
								.charAt(0));
						Character bilirrubinuria = (hojaConsultaJSON.get("bilirrubinuria").toString()
								.charAt(0));
						/*Estado Nutricional Sintomas*/
						Double imc = ((Number) hojaConsultaJSON.get("imc")).doubleValue();			
						Character obeso = (hojaConsultaJSON.get("obeso").toString().charAt(0));
						Character sobrepeso = (hojaConsultaJSON.get("sobrepeso").toString().charAt(0));
						Character sospechaProblema = (hojaConsultaJSON.get("sospechaProblema")
								.toString().charAt(0));
						Character normal = (hojaConsultaJSON.get("normal").toString().charAt(0));
						Character bajoPeso = (hojaConsultaJSON.get("bajoPeso").toString().charAt(0));
						Character bajoPesoSevero = (hojaConsultaJSON.get("bajoPesoSevero").toString()
								.charAt(0));
						/*Respiratorio Sintomas*/
						Character tos = (hojaConsultaJSON.get("tos").toString().charAt(0));
						Character rinorrea = (hojaConsultaJSON.get("rinorrea").toString().charAt(0));
						Character congestionNasal = (hojaConsultaJSON.get("congestionNasal")
								.toString().charAt(0));
						Character otalgia = (hojaConsultaJSON.get("otalgia").toString().charAt(0));
						Character aleteoNasal = (hojaConsultaJSON.get("aleteoNasal").toString()
								.charAt(0));
						Character apnea = (hojaConsultaJSON.get("apnea").toString().charAt(0));
						Character respiracionRapida = (hojaConsultaJSON.get("respiracionRapida")
								.toString().charAt(0));
						Character quejidoEspiratorio = (hojaConsultaJSON.get("quejidoEspiratorio")
								.toString().charAt(0));
						Character estiradorReposo = (hojaConsultaJSON.get("estiradorReposo")
								.toString().charAt(0));
						Character tirajeSubcostal = (hojaConsultaJSON.get("tirajeSubcostal")
								.toString().charAt(0));
						Character sibilancias = (hojaConsultaJSON.get("sibilancias").toString()
								.charAt(0));
						Character crepitos = (hojaConsultaJSON.get("crepitos").toString().charAt(0));
						Character roncos = (hojaConsultaJSON.get("roncos").toString().charAt(0));
						Character otraFif = (hojaConsultaJSON.get("otraFif").toString().charAt(0));
						String nuevaFif = null;
						if(hojaConsultaJSON.containsKey("nuevaFif")) {
							nuevaFif = ((hojaConsultaJSON.get("nuevaFif").toString()));
						}
						/*Referencia Sintomas*/
						Character interconsultaPediatrica = (hojaConsultaJSON.get(
								"interconsultaPediatrica").toString().charAt(0));
						Character referenciaHospital = (hojaConsultaJSON.get("referenciaHospital")
								.toString().charAt(0));
						Character referenciaDengue = (hojaConsultaJSON.get("referenciaDengue")
								.toString().charAt(0));
						Character referenciaIrag = (hojaConsultaJSON.get("referenciaIrag").toString()
								.charAt(0));
						Character referenciaChik = (hojaConsultaJSON.get("referenciaChik").toString()
								.charAt(0));
						Character eti = (hojaConsultaJSON.get("eti").toString().charAt(0));
						Character irag = (hojaConsultaJSON.get("irag").toString().charAt(0));
						Character neumonia = (hojaConsultaJSON.get("neumonia").toString().charAt(0));
						Character cV = (hojaConsultaJSON.get("cV").toString().charAt(0));
						/*Vacunas Sintomas*/
						Character lactanciaMaterna = (hojaConsultaJSON.get("lactanciaMaterna")
								.toString().charAt(0));
						Character vacunasCompletas = (hojaConsultaJSON.get("vacunasCompletas")
								.toString().charAt(0));
						Character vacunaInfluenza = (hojaConsultaJSON.get("vacunaInfluenza")
								.toString().charAt(0));
						String fechaVacuna = null;
						if(hojaConsultaJSON.containsKey("fechaVacuna")) {
							fechaVacuna = ((hojaConsultaJSON.get("fechaVacuna").toString()));
						}
						/*Categoria Sintomas*/
						String categoria = null;
						Character cambioCategoria = null;
						Integer saturaciono2 = hojaConsultaJSON.containsKey("saturaciono2") ? ((Number) hojaConsultaJSON
								.get("saturaciono2")).intValue() : -1;
						if(hojaConsultaJSON.containsKey("categoria")) {
							categoria = (hojaConsultaJSON.get("categoria").toString());
						}
						if(hojaConsultaJSON.containsKey("cambioCategoria")) {
							cambioCategoria = (hojaConsultaJSON.get("cambioCategoria").toString().charAt(0));
						}

						Character manifestacionHemorragica = hojaConsultaJSON.containsKey("manifestacionHemorragica") ? 
								(hojaConsultaJSON.get("manifestacionHemorragica").toString().charAt(0)) : 
									null;
								
						Character pruebaTorniquetePositiva = hojaConsultaJSON.containsKey("pruebaTorniquetePositiva") ? 
								(hojaConsultaJSON.get("pruebaTorniquetePositiva").toString().charAt(0)) : 
									null;
								
						Character petequia10Pt = hojaConsultaJSON.containsKey("petequia10Pt") ? 
								(hojaConsultaJSON.get("petequia10Pt").toString().charAt(0)) : null;
								
						Character petequia20Pt = hojaConsultaJSON.containsKey("petequia20Pt") ? (hojaConsultaJSON
								.get("petequia20Pt").toString().charAt(0)) : null;
						Character pielExtremidadesFrias = hojaConsultaJSON
								.containsKey("pielExtremidadesFrias") ? (hojaConsultaJSON
								.get("pielExtremidadesFrias").toString().charAt(0)) : null;
						Character palidezEnExtremidades = hojaConsultaJSON
								.containsKey("palidezEnExtremidades") ? (hojaConsultaJSON
								.get("palidezEnExtremidades").toString().charAt(0)) : null;
						Character epistaxis = hojaConsultaJSON.containsKey("epistaxis") ? (hojaConsultaJSON
								.get("epistaxis").toString().charAt(0)) : null;
						Character gingivorragia = hojaConsultaJSON.containsKey("gingivorragia") ? (hojaConsultaJSON
								.get("gingivorragia").toString().charAt(0)) : null;
						Character petequiasEspontaneas = hojaConsultaJSON
								.containsKey("petequiasEspontaneas") ? (hojaConsultaJSON
								.get("petequiasEspontaneas").toString().charAt(0)) : null;
						Character llenadoCapilar2seg = hojaConsultaJSON
								.containsKey("llenadoCapilar2seg") ? (hojaConsultaJSON.get(
								"llenadoCapilar2seg").toString().charAt(0)) : null;
						Character cianosis = hojaConsultaJSON.containsKey("cianosis") ? (hojaConsultaJSON
								.get("cianosis").toString().charAt(0)) : null;
						Double linfocitosaAtipicos = hojaConsultaJSON
								.containsKey("linfocitosaAtipicos") ? ((Number) hojaConsultaJSON
								.get("linfocitosaAtipicos")).doubleValue() : null;
						String fechaLinfocitos = hojaConsultaJSON.containsKey("fechaLinfocitos") ? ((hojaConsultaJSON
								.get("fechaLinfocitos").toString())) : null;
						Character hipermenorrea = hojaConsultaJSON.containsKey("hipermenorrea") ? (hojaConsultaJSON
								.get("hipermenorrea").toString().charAt(0)) : null;
						Character hematemesis = hojaConsultaJSON.containsKey("hematemesis") ? (hojaConsultaJSON
								.get("hematemesis").toString().charAt(0)) : null;
						Character melena = hojaConsultaJSON.containsKey("melena") ? (hojaConsultaJSON
								.get("melena").toString().charAt(0)) : null;
						Character hemoconc = hojaConsultaJSON.containsKey("hemoconc") ? (hojaConsultaJSON
								.get("hemoconc").toString().charAt(0)) : null;
						Short hemoconcentracion = hojaConsultaJSON
								.containsKey("hemoconcentracion") ? ((Number) hojaConsultaJSON
								.get("hemoconcentracion")).shortValue() : null;

						Character hospitalizado = (hojaConsultaJSON.containsKey("hospitalizado")) ?
								hojaConsultaJSON.get("hospitalizado").toString().charAt(0) : null;
						String hospitalizadoEspecificar = hojaConsultaJSON
								.containsKey("hospitalizadoEspecificar") ? ((hojaConsultaJSON
								.get("hospitalizadoEspecificar").toString())) : null;
								
						Character transfusionSangre = (hojaConsultaJSON.containsKey("transfusionSangre")) ? 
								hojaConsultaJSON.get("transfusionSangre").toString().charAt(0) : null;
						String transfusionEspecificar = hojaConsultaJSON
								.containsKey("transfusionEspecificar") ? ((hojaConsultaJSON
								.get("transfusionEspecificar").toString())) : null;
								
						Character tomandoMedicamento = (hojaConsultaJSON.containsKey("tomandoMedicamento")) ? 
								hojaConsultaJSON.get("tomandoMedicamento").toString().charAt(0) : null;
						String medicamentoEspecificar = hojaConsultaJSON
								.containsKey("medicamentoEspecificar") ? ((hojaConsultaJSON
								.get("medicamentoEspecificar").toString())) : null;
								
						Character medicamentoDistinto = (hojaConsultaJSON.containsKey("medicamentoDistinto")) ? 
								hojaConsultaJSON.get("medicamentoDistinto").toString().charAt(0) : null;
						String medicamentoDistEspecificar = hojaConsultaJSON
								.containsKey("medicamentoDistEspecificar") ? ((hojaConsultaJSON
								.get("medicamentoDistEspecificar").toString())) : null;
						/*Examenes*/
						Character bhc = (hojaConsultaJSON.get("bhc").toString().charAt(0));
						Character serologiaDengue = (hojaConsultaJSON.get("serologiaDengue")
								.toString().charAt(0));
						Character serologiaChik = (hojaConsultaJSON.get("serologiaChik").toString()
								.charAt(0));
						Character gotaGruesa = (hojaConsultaJSON.get("gotaGruesa").toString()
								.charAt(0));
						Character extendidoPeriferico = (hojaConsultaJSON.get("extendidoPeriferico")
								.toString().charAt(0));
						Character ego = (hojaConsultaJSON.get("ego").toString().charAt(0));
						Character egh = (hojaConsultaJSON.get("egh").toString().charAt(0));
						Character citologiaFecal = (hojaConsultaJSON.get("citologiaFecal").toString()
								.charAt(0));
						Character factorReumatoideo = (hojaConsultaJSON.get("factorReumatoideo")
								.toString().charAt(0));
						Character albumina = (hojaConsultaJSON.get("albumina").toString().charAt(0));
						Character astAlt = (hojaConsultaJSON.get("astAlt").toString().charAt(0));
						Character bilirrubinas = (hojaConsultaJSON.get("bilirrubinas").toString()
								.charAt(0));
						Character cpk = (hojaConsultaJSON.get("cpk").toString().charAt(0));
						Character colesterol = (hojaConsultaJSON.get("colesterol").toString()
								.charAt(0));
						Character influenza = (hojaConsultaJSON.get("influenza").toString().charAt(0));
						Character oel = (hojaConsultaJSON.get("oel").toString().charAt(0));
						String otroExamenLab = hojaConsultaJSON
								.containsKey("otroExamenLab") ? ((hojaConsultaJSON
								.get("otroExamenLab").toString())) : null;
						/*Historia y Examen Fisico*/
						String historiaExamenFisico = ((hojaConsultaJSON.get("historiaExamenFisico").toString()));
						/*Tratamiento y Planes*/
						Character acetaminofen = hojaConsultaJSON.get("acetaminofen").toString()
								.charAt(0);
						Character ASA = hojaConsultaJSON.get("asa").toString().charAt(0);
						Character ibuprofen = hojaConsultaJSON.get("ibuprofen").toString().charAt(0);
						Character penicilina = hojaConsultaJSON.get("penicilina").toString()
								.charAt(0);
						Character amoxicilina = hojaConsultaJSON.get("amoxicilina").toString()
								.charAt(0);
						Character dicloxacilina = hojaConsultaJSON.get("dicloxacilina").toString()
								.charAt(0);
						String otroAntibiotico = hojaConsultaJSON.get("otroAntibiotico")
								.toString();
						Character otro = hojaConsultaJSON.get("otro").toString().charAt(0);
						Character furazolidona = hojaConsultaJSON.get("furazolidona").toString()
								.charAt(0);
						Character metronidazolTinidazol = hojaConsultaJSON
								.get("metronidazolTinidazol").toString().charAt(0);
						Character albendazolMebendazol = hojaConsultaJSON.get("albendazolMebendazol")
								.toString().charAt(0);
						Character sulfatoFerroso = hojaConsultaJSON.get("sulfatoFerroso").toString()
								.charAt(0);
						Character sueroOral = hojaConsultaJSON.get("sueroOral").toString().charAt(0);
						Character sulfatoZinc = hojaConsultaJSON.get("sulfatoZinc").toString()
								.charAt(0);
						Character liquidosIv = hojaConsultaJSON.get("liquidosIv").toString()
								.charAt(0);
						Character prednisona = hojaConsultaJSON.get("prednisona").toString()
								.charAt(0);
						Character hidrocortisonaIv = hojaConsultaJSON.get("hidrocortisonaIv")
								.toString().charAt(0);
						Character salbutamol = hojaConsultaJSON.get("salbutamol").toString()
								.charAt(0);
						Character oseltamivir = hojaConsultaJSON.get("oseltamivir").toString()
								.charAt(0);
						String planes = (hojaConsultaJSON.containsKey("planes")) ? hojaConsultaJSON
								.get("planes").toString() : "";
						/*Diagnosticos*/
						Short diagnostico1 = (((Number) hojaConsultaJSON.get("diagnostico1"))
								.shortValue());
						Short diagnostico2 = (((Number) hojaConsultaJSON.get("diagnostico2"))
								.shortValue());
						Short diagnostico3 = (((Number) hojaConsultaJSON.get("diagnostico3"))
								.shortValue());
						Short diagnostico4 = (((Number) hojaConsultaJSON.get("diagnostico4"))
								.shortValue());
						String otroDiagnostico = hojaConsultaJSON.get("otroDiagnostico")
								.toString();
						/*Proxima Cita*/
						String telef = (hojaConsultaJSON.containsKey("telef")) ?
								  hojaConsultaJSON.get("telef").toString() : null;
						String proximaCita = (hojaConsultaJSON.containsKey("proximaCita")) ?
								  hojaConsultaJSON.get("proximaCita").toString() : null;
						String colegio = (hojaConsultaJSON.containsKey("colegio")) ? 
								  hojaConsultaJSON.get("colegio").toString() : null;
						String horarioClases = (hojaConsultaJSON.containsKey("horarioClases")) ?  
								  hojaConsultaJSON.get("horarioClases").toString() : null;
						String estudiosParticipantes = hojaConsultaJSON.get("estudiosParticipantes")
								.toString();
						hojaConsulta.setCodExpediente(codExpediente);
						hojaConsulta.setNumHojaConsulta(obtenerNumHojaConsulta());
						hojaConsulta.setOrdenLlegada(Short.valueOf(obtenerNumOrdenLlegada() + ""));
						hojaConsulta.setEstado(hojaConsultaJSON.get("estado").toString().charAt(0));
						//Date fechaConsulta = format.parse(hojaConsultaJSON.get("fechaConsulta").toString());
						hojaConsulta.setFechaConsulta(fechaConsulta);
						Date fechaCierre = format.parse(hojaConsultaJSON.get("fechaCierre").toString());
						hojaConsulta.setFechaCierre(fechaCierre);
						hojaConsulta.setUsuarioEnfermeria(Short.valueOf(hojaConsultaJSON.get("usuarioEnfermeria").toString()));
						hojaConsulta.setUsuarioMedico(Short.valueOf(hojaConsultaJSON.get("usuarioMedico").toString()));
						hojaConsulta.setPas(pas.shortValue());
						hojaConsulta.setPad(pad.shortValue());
						hojaConsulta.setPesoKg(BigDecimal.valueOf(pesoKg));
						hojaConsulta.setTallaCm(BigDecimal.valueOf(tallaCm));
						hojaConsulta.setExpedienteFisico(expedienteFisico);
						hojaConsulta.setHora(horas);
						hojaConsulta.setHorasv(horasv);
						hojaConsulta.setEstudiosParticipantes(estudiosParticipantes);
						hojaConsulta.setTemperaturac(BigDecimal.valueOf(temperaturac));
						hojaConsulta.setFciaCard(fciaCard.shortValue());
						hojaConsulta.setFciaResp(fciaResp.shortValue());
						hojaConsulta.setLugarAtencion(lugarAtencion);
						hojaConsulta.setConsulta(consulta);
						String supervisor = supervisorHojaConsulta();
						hojaConsulta.setSupervisor(Short.valueOf(supervisor));
						hojaConsulta.setUsuarioCierraHoja(Short.valueOf(hojaConsultaJSON.get("usuarioMedico").toString()));
						//hojaConsulta.setDigitada(false);
						//hojaConsulta.setUaf(false);
						if(segChick != null && !segChick.isEmpty()) {
							hojaConsulta.setSegChick(segChick.charAt(0));
						}else{
							hojaConsulta.setSegChick(null);
						}
						
						if(hojaConsulta.getTurno() == null) {
							hojaConsulta.setTurno(turno.charAt(0));	
						}
						hojaConsulta.setTemMedc(BigDecimal.valueOf(temMedc));
						
						if(fis != null && !fis.isEmpty()) {
							Date fechaInicioSintomas = format.parse(fis);
							hojaConsulta.setFis(fechaInicioSintomas);
							//hojaConsulta.setFis( fis.trim().length() > 0 ? new SimpleDateFormat("dd/MM/yyyy").parse(fis) : null);
						} else {
							hojaConsulta.setFis(null);
						}
						if(fif != null && !fif.isEmpty()) {
							Date fechaInicioFiebre = format.parse(fis);
							hojaConsulta.setFif(fechaInicioFiebre);
							//hojaConsulta.setFif( fif.trim().length() > 0 ? new SimpleDateFormat("dd/MM/yyyy").parse(fif) : null);
						} else {
							hojaConsulta.setFif(null);
						}
						
						if(ultDiaFiebre != null) {
							Date date = format.parse(ultDiaFiebre);
							hojaConsulta.setUltDiaFiebre(date);
							//hojaConsulta.setUltDiaFiebre( ultDiaFiebre.trim().length() > 0 ? new SimpleDateFormat("dd/MM/yyyy").parse(ultDiaFiebre) : null);
						} else {
							hojaConsulta.setUltDiaFiebre(null);
						}
						
						if(ultDosisAntipiretico != null) {
							Date date = format.parse(ultDosisAntipiretico);
							hojaConsulta.setUltDosisAntipiretico(date);
							//hojaConsulta.setUltDosisAntipiretico( ultDosisAntipiretico.trim().length() > 0 ? new SimpleDateFormat("dd/MM/yyyy").parse(ultDosisAntipiretico) : null);
						} else {
							hojaConsulta.setUltDosisAntipiretico(null);
						}
						
						if(amPmUltDiaFiebre != null) {
							hojaConsulta.setAmPmUltDiaFiebre(amPmUltDiaFiebre.trim().length() > 0 ? amPmUltDiaFiebre : null);
						} else {
							hojaConsulta.setAmPmUltDiaFiebre(null);
						}
						
						if(horaUltDosisAntipiretico != null) {
							hojaConsulta.setHoraUltDosisAntipiretico(horaUltDosisAntipiretico.trim().length() > 0 
									? new SimpleDateFormat("hh:mm").parse(horaUltDosisAntipiretico) : null);
							String getAMPM = horaUltDosisAntipiretico.substring(6, horaUltDosisAntipiretico.length());
							if (getAMPM != null) {
								hojaConsulta.setAmPmUltDosisAntipiretico(getAMPM);
							}
						} else {
							hojaConsulta.setHoraUltDosisAntipiretico(null);
							hojaConsulta.setAmPmUltDosisAntipiretico(null);
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
						hojaConsulta.setPocoApetito(pocoApetito);
						hojaConsulta.setNausea(nausea);
						hojaConsulta.setDificultadAlimentarse(dificultadAlimentarse);
						hojaConsulta.setVomito12horas(vomito12horas);
						if(vomito12h != null) {
							if (vomito12h.toString().trim().equals("0")) {
								hojaConsulta.setVomito12h(null);
							} else {
								hojaConsulta.setVomito12h(vomito12h);
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
						} else {
							hojaConsulta.setHepatomegaliaCm(null);
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
						hojaConsulta.setCefalea(cefalea);
						hojaConsulta.setRigidezCuello(rigidezCuello);
						hojaConsulta.setInyeccionConjuntival(inyeccionConjuntival);
						hojaConsulta.setHemorragiaSuconjuntival(hemorragiaSuconjuntival);
						hojaConsulta.setDolorRetroocular(dolorRetroocular);
						hojaConsulta.setFontanelaAbombada(fontanelaAbombada);
						hojaConsulta.setIctericiaConuntival(ictericiaConuntival);
						hojaConsulta.setLenguaMucosasSecas(lenguaMucosasSecas);
						hojaConsulta.setPliegueCutaneo(pliegueCutaneo);
						hojaConsulta.setOrinaReducida(orinaReducida);
						hojaConsulta.setBebeConSed(bebeConSed);
						hojaConsulta.setOjosHundidos(ojosHundidos);
						hojaConsulta.setFontanelaHundida(fontanelaHundida);
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
						hojaConsulta.setEritema(eritema);
						hojaConsulta.setDolorGarganta(dolorGarganta);
						hojaConsulta.setAdenopatiasCervicales(adenopatiasCervicales);
						hojaConsulta.setExudado(exudado);
						hojaConsulta.setPetequiasMucosa(petequiasMucosa);
						hojaConsulta.setSintomasUrinarios(sintomasUrinarios);
						hojaConsulta.setLeucocituria(leucocituria);
						hojaConsulta.setNitritos(nitritos);
						hojaConsulta.setEritrocitos(eritrocitos);
						hojaConsulta.setBilirrubinuria(bilirrubinuria);
						hojaConsulta.setImc(BigDecimal.valueOf(imc));
						hojaConsulta.setObeso(obeso);
						hojaConsulta.setSobrepeso(sobrepeso);
						hojaConsulta.setSospechaProblema(sospechaProblema);
						hojaConsulta.setNormal(normal);
						hojaConsulta.setBajoPeso(bajoPeso);
						hojaConsulta.setBajoPesoSevero(bajoPesoSevero);
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
							Date date = format2.parse(nuevaFif);
							hojaConsulta.setNuevaFif(date);
							//hojaConsulta.setNuevaFif(new SimpleDateFormat("dd/MM/yyyy").parse(nuevaFif));
						} else {
							hojaConsulta.setNuevaFif(null);
						}
						hojaConsulta.setInterconsultaPediatrica(interconsultaPediatrica);
						hojaConsulta.setReferenciaHospital(referenciaHospital);
						hojaConsulta.setReferenciaDengue(referenciaDengue);
						hojaConsulta.setReferenciaIrag(referenciaIrag);
						hojaConsulta.setReferenciaChik(referenciaChik);
						hojaConsulta.setEti(eti);
						hojaConsulta.setIrag(irag);
						hojaConsulta.setNeumonia(neumonia);
						hojaConsulta.setCv(cV);
						hojaConsulta.setLactanciaMaterna(lactanciaMaterna);
						hojaConsulta.setVacunasCompletas(vacunasCompletas);
						hojaConsulta.setVacunaInfluenza(vacunaInfluenza);
						if(fechaVacuna != null && !fechaVacuna.isEmpty()) {
							Date date = format2.parse(fechaVacuna);
							hojaConsulta.setFechaVacuna(date);
							//hojaConsulta.setFechaVacuna(new SimpleDateFormat("dd/MM/yyyy").parse(fechaVacuna));
						} else {
							hojaConsulta.setFechaVacuna(null);
						}
						if (saturaciono2 != -1){
							hojaConsulta.setSaturaciono2(saturaciono2.shortValue());
						}else{
							hojaConsulta.setSaturaciono2(null);
						}
						hojaConsulta.setCategoria(categoria);
						hojaConsulta.setCambioCategoria(cambioCategoria);
						hojaConsulta.setManifestacionHemorragica(manifestacionHemorragica);
						hojaConsulta.setPruebaTorniquetePositiva(pruebaTorniquetePositiva);
						hojaConsulta.setPetequia10Pt(petequia10Pt);
						hojaConsulta.setPetequia20Pt(petequia20Pt);
						hojaConsulta.setPielExtremidadesFrias(pielExtremidadesFrias);
						hojaConsulta.setPalidezEnExtremidades(palidezEnExtremidades);
						hojaConsulta.setEpistaxis(epistaxis);
						hojaConsulta.setGingivorragia(gingivorragia);
						hojaConsulta.setPetequiasEspontaneas(petequiasEspontaneas);
						hojaConsulta.setLlenadoCapilar2seg(llenadoCapilar2seg);
						hojaConsulta.setCianosis(cianosis);
						if (linfocitosaAtipicos != null)
							hojaConsulta.setLinfocitosaAtipicos(BigDecimal.valueOf(linfocitosaAtipicos.doubleValue()));
						if (fechaLinfocitos != null && !fechaLinfocitos.isEmpty()) {
							Date date = format2.parse(fechaLinfocitos);
							hojaConsulta.setFechaLinfocitos(date);
						}
							//hojaConsulta.setFechaLinfocitos(new SimpleDateFormat("dd/MM/yyyy").parse(fechaLinfocitos));
						hojaConsulta.setHipermenorrea(hipermenorrea);
						hojaConsulta.setHematemesis(hematemesis);
						hojaConsulta.setMelena(melena);
						hojaConsulta.setHemoconc(hemoconc);
						if (hemoconcentracion != null)
							hojaConsulta.setHemoconcentracion(hemoconcentracion);
						hojaConsulta.setHospitalizado(hospitalizado);
						if (hospitalizadoEspecificar != null) {
							hojaConsulta.setHospitalizadoEspecificar(hospitalizadoEspecificar);
						} else {
							hojaConsulta.setHospitalizadoEspecificar(null);
						}
						hojaConsulta.setTransfusionSangre(transfusionSangre);
						if (transfusionEspecificar != null) {
							hojaConsulta.setTransfusionEspecificar(transfusionEspecificar);
						} else {
							hojaConsulta.setTransfusionEspecificar(null);
						}
						hojaConsulta.setTomandoMedicamento(tomandoMedicamento);
						if (medicamentoEspecificar != null) {
							hojaConsulta.setMedicamentoEspecificar(medicamentoEspecificar);
						} else {
							hojaConsulta.setMedicamentoEspecificar(null);
						}
						hojaConsulta.setMedicamentoDistinto(medicamentoDistinto);
						if (medicamentoDistEspecificar != null) {
							hojaConsulta.setMedicamentoDistEspecificar(medicamentoDistEspecificar);
						} else {
							hojaConsulta.setMedicamentoDistEspecificar(null);
						}
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
						if (otroExamenLab != null) {
							hojaConsulta.setOtroExamenLab(otroExamenLab.toUpperCase());
						} else {
							hojaConsulta.setOtroExamenLab(null);
						}
						hojaConsulta.setHistoriaExamenFisico(historiaExamenFisico);
						hojaConsulta.setAcetaminofen(acetaminofen);
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
						hojaConsulta.setDiagnostico1(diagnostico1);
						hojaConsulta.setDiagnostico2(diagnostico2);
						hojaConsulta.setDiagnostico3(diagnostico3);
						hojaConsulta.setDiagnostico4(diagnostico4);
						hojaConsulta.setOtroDiagnostico(otroDiagnostico);
						hojaConsulta.setHojaImpresa('S');
						if(telef != null && telef.trim().length() > 0 && !telef.equalsIgnoreCase("null")) {
							if (telef.trim().equals("0")) {
								hojaConsulta.setTelef(null);
							} else {
								hojaConsulta.setTelef(Long.parseLong(telef));	
							}
						} else {
							   hojaConsulta.setTelef(null);
						}
						if(proximaCita != null && !proximaCita.isEmpty()) {
							Date date = format2.parse(proximaCita);
							hojaConsulta.setProximaCita(date);
							//hojaConsulta.setProximaCita(new SimpleDateFormat("dd/MM/yyyy").parse(proximaCita));
						}
						if(colegio != null && !colegio.isEmpty()) {
							hojaConsulta.setColegio(colegio);
						}
						if(horarioClases != null && !horarioClases.isEmpty()) {
							hojaConsulta.setHorarioClases(horarioClases);
						}
						hojaConsulta.setConsultaRespiratorio('1');
						hojaConsulta.setOffline(true);
						
						HIBERNATE_RESOURCE.begin();
						HIBERNATE_RESOURCE.getSession().saveOrUpdate(hojaConsulta);
						HIBERNATE_RESOURCE.commit();
						hojaConsultaList.add(hojaConsulta);
					}
				
				}
				if (hojaConsultaList.size() > 0) {
					for (int j = 0; j < hojaConsultaList.size(); j++) {
						HojaConsultaReporteDA hojaConsultaReporteDA = new HojaConsultaReporteDA();
						//consultaReporteService.imprimirConsultaPdf(hojaConsultaList.get(j).getSecHojaConsulta());
						hojaConsultaReporteDA.imprimirConsultaPdf(hojaConsultaList.get(j).getSecHojaConsulta());
					}
				}
				result = UtilResultado.parserResultado(null, "SUCCESS", UtilResultado.OK);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = UtilResultado.parserResultado(null,
					Mensajes.ERROR_NO_CONTROLADO + e.getMessage(),
					UtilResultado.ERROR);
			HIBERNATE_RESOURCE.rollback();
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}
	
	public Integer obtenerNumOrdenLlegada() {
		Integer result = null;
		try {
			String sql = "select max(h.ordenLlegada) "
					+ " from HojaConsulta h"
					+ " where to_char(h.fechaConsulta, 'yyyyMMdd') = to_char(current_date, 'yyyyMMdd')";
			
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			result = (query.uniqueResult() == null) ? 1
						: ((Short) query.uniqueResult()).intValue() + 1;
			//result = ((Short) query.uniqueResult()).intValue() + 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			HIBERNATE_RESOURCE.rollback();
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}
	
	public Integer obtenerNumHojaConsulta() {
		Integer result = null;
		try {
			String sql = "select max(h.numHojaConsulta) " + 
					  " from HojaConsulta h";
			
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);

			result = ((query.uniqueResult() == null) ? 1
					: ((Integer) query.uniqueResult()).intValue()) + 1;
			//result = ((Integer) query.uniqueResult()).intValue() + 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			HIBERNATE_RESOURCE.rollback();
		} finally {
			if (HIBERNATE_RESOURCE.getSession().isOpen()) {
				HIBERNATE_RESOURCE.close();
			}
		}
		return result;
	}
	
	/*
	 * Metodo para obtener los supervisores establecidos en los parametros 
	 * y retornar uno de forma aleatoria 
	 * */
	public String supervisorHojaConsulta() {
		String supervisor = null;
		try {
			String sql = "select valores " + 
					" from ParametrosSistemas p where p.nombreParametro ='SUPERVISORES_HC'";
			
			Query query = HIBERNATE_RESOURCE.getSession().createQuery(sql);
			
			String valorParametro = query.uniqueResult().toString();
			if (valorParametro != null) {
				String[] parts = valorParametro.split(",");
				
				int indiceAleatorio = numeroAleatorioEnRango(0, parts.length - 1);
				supervisor = parts[indiceAleatorio];
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			HIBERNATE_RESOURCE.rollback();
		} finally {
            if (HIBERNATE_RESOURCE.getSession().isOpen()) {
            	HIBERNATE_RESOURCE.close();
            }
        }
		return supervisor;
	}
	
	public static int numeroAleatorioEnRango(int minimo, int maximo) {
        // nextInt regresa en rango pero con límite superior exclusivo, por eso sumamos 1
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }
}
