package com.sts_ni.estudiocohortecssfv.util;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.PrinterInfo;
import javax.print.attribute.standard.PrinterIsAcceptingJobs;
import javax.print.attribute.standard.PrinterLocation;
import javax.print.attribute.standard.PrinterMakeAndModel;
import javax.print.attribute.standard.PrinterState;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ni.com.sts.estudioCohorteCSSFV.modelo.HojaConsulta;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;



/**
 * Clase utilitaria para la generaci�n de los reportes en un Viewer.
 * <p>
 * @author Honorio Trejos S.
 * @author <a href=mailto:honorio.trejos@simmo.cl>honorio.trejos@simmo.cl</a>
 * @version 1.0, &nbsp; 11/07/2013
 * @since
 */
public class UtilitarioReporte  {

 private static CompositeConfiguration config;
 private CompositeConfiguration configPrint;
 
 static Logger logger;
 
 static {
	 config= UtilProperty.getConfiguration("EstudioCohorteCssfvMovilWSExt.properties", "com/sts_ni/estudiocohortecssfv/properties/EstudioCohorteCssfvMovilWSInt.properties");
	 logger = Logger.getLogger("UtilitarioReporte");
 }
    
    /**
     * M�todo utilitario para generar reporte.
     *
     * @param titulo         Titulo del visor de reporte.
     * @param source         Ruta del reporte compilado (.jasper).
     * @param parametros     Lista de par�metros para el reporte.
     * @param collectionDataSource Colecci�n de DataSources para el reporte.
     */
     public static byte[] mostrarReporte( String nombreReporte,
             Map<String, Object> parametros, List<?> collectionDataSource, boolean multipleReporte, HojaConsulta datosAdicionales, 
             boolean tieneSeguimientoQuinceDias) {

        return getMostrarReporte( nombreReporte, parametros, collectionDataSource, false,multipleReporte, datosAdicionales, tieneSeguimientoQuinceDias);
     }

    /**
    * M�todo utilitario para generar reporte.
    *
    * @param titulo         Titulo del visor de reporte.
    * @param source         Ruta del reporte compilado (.jasper)
    * @param parametros     Lista de par�metros para el reporte.
    * @param collectionDataSource Colecci�n de DataSources para el reporte
    * @param indicarDirSubReport  Indica si se le pasar� al sub-reporte el path
    *                             donde se encuentra el reporte principal.
    */
    public static byte[] getMostrarReporte( String nombreReporte,
            Map<String, Object> parametros, List<?> collectionDataSource,
            boolean indicarDirSubReport, boolean multipleReporte, HojaConsulta datosAdicionales, boolean tieneSeguimientoQuinceDias) {
        	boolean isFlu = false;
    	try{
    		
    		 // Construir la URL donde se encuentran los reportes.
            String path = System.getProperty("jboss.server.data.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.reporte") + (nombreReporte.contains(".jasper")?nombreReporte:nombreReporte + ".jasper");
            String pathPag2 = System.getProperty("jboss.server.data.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.reporte") + ( (nombreReporte+"2").contains(".jasper")?(nombreReporte+"2"):(nombreReporte+"2") + ".jasper");
            String pathPagFlu3 = "";
            String pathPagFlu4 = "";
            if (nombreReporte.equals("rptSeguimientoInfluenza") && tieneSeguimientoQuinceDias) {
            	isFlu = true;
            	pathPagFlu3 = System.getProperty("jboss.server.data.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.reporte") + ( (nombreReporte+"3").contains(".jasper")?(nombreReporte+"3"):(nombreReporte+"3") + ".jasper");
                pathPagFlu4 = System.getProperty("jboss.server.data.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.reporte") + ( (nombreReporte+"4").contains(".jasper")?(nombreReporte+"4"):(nombreReporte+"4") + ".jasper");
            }
                
            path = path.replace('/', System.getProperty("file.separator").charAt(0));

                        
            logger.debug("path : " + path);
            logger.debug("================ URL ================");
            
            // Indicar si se le pasar� al subreporte la URL donde se encuentra el reporte principal.
            if (indicarDirSubReport) {       
                String pathSubReport = System.getProperty("user.dir") + System.getProperty("file.separator").charAt(0) +  config.getString("ruta.reportes");
                pathSubReport = pathSubReport.replace('/', System.getProperty("file.separator").charAt(0));
                parametros.put("SUBREPORT_DIR", pathSubReport); // Par�metro estandar de JasperReport.
                
            }
            
            JasperPrint report =JasperFillManager.fillReport(path, parametros, new JRBeanCollectionDataSource(collectionDataSource));
            if (multipleReporte){
	            JasperPrint fileAnexo = JasperFillManager.fillReport(pathPag2, parametros, new JRBeanCollectionDataSource(collectionDataSource));
	            report.addPage(fileAnexo.removePage( 0 ));
	            if (isFlu) {
	            	JasperPrint fileAnexoFlu3 = JasperFillManager.fillReport(pathPagFlu3, parametros, new JRBeanCollectionDataSource(collectionDataSource));
		            JasperPrint fileAnexoFlu4 = JasperFillManager.fillReport(pathPagFlu4, parametros, new JRBeanCollectionDataSource(collectionDataSource));
		            report.addPage(fileAnexoFlu3.removePage( 0 ));
		            report.addPage(fileAnexoFlu4.removePage( 0 ));
	            }
	            
	            if(datosAdicionales != null) {
	            	/*if ((datosAdicionales.getHistoriaExamenFisico() != null && 
	            			datosAdicionales.getHistoriaExamenFisico().trim().length() > 2000) &&
	            			(datosAdicionales.getPlanes() != null && datosAdicionales.getPlanes().trim().length() > 460)) {
	            		
	            		HashMap params = new HashMap(); 
		            	params.put("planes", datosAdicionales.getPlanes());
		            	params.put("historiaExamenFisico", datosAdicionales.getHistoriaExamenFisico());
		            	params.put("numHojaConsulta", datosAdicionales.getNumHojaConsulta());
		            	params.put("codExpediente", datosAdicionales.getCodExpediente());
		            	params.put("expedienteFisico", datosAdicionales.getExpedienteFisico());
		            	
		            	String pathPag4 = System.getProperty("jboss.server.data.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.reporte") + ( (nombreReporte+"4").contains(".jasper")?(nombreReporte+"4"):(nombreReporte+"4") + ".jasper");
		            	JasperPrint fileAnexo4 = JasperFillManager.fillReport(pathPag4, params, new JRBeanCollectionDataSource(collectionDataSource));
		            	report.addPage(fileAnexo4.removePage( 0 ));
		            	
	            		if (datosAdicionales.getHistoriaExamenFisico() != null && 
		            			datosAdicionales.getHistoriaExamenFisico().trim().length() > 6000) {
	            			String pathPag5 = System.getProperty("jboss.server.data.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.reporte") + ( (nombreReporte+"5").contains(".jasper")?(nombreReporte+"5"):(nombreReporte+"5") + ".jasper");
			            	JasperPrint fileAnexo5 = JasperFillManager.fillReport(pathPag5, params, new JRBeanCollectionDataSource(collectionDataSource));
			            	report.addPage(fileAnexo5.removePage( 0 ));
			            	
	            		}
	            		if (datosAdicionales.getPlanes() != null && datosAdicionales.getPlanes().trim().length() > 460) {
	            			String pathPag6 = System.getProperty("jboss.server.data.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.reporte") + ( (nombreReporte+"6").contains(".jasper")?(nombreReporte+"6"):(nombreReporte+"6") + ".jasper");
			            	JasperPrint fileAnexo6 = JasperFillManager.fillReport(pathPag6, params, new JRBeanCollectionDataSource(collectionDataSource));
			            	report.addPage(fileAnexo6.removePage( 0 ));
	            		}
	            	} else {
	            		HashMap params = new HashMap(); 
		            	params.put("planes", datosAdicionales.getPlanes());
		            	params.put("historiaExamenFisico", datosAdicionales.getHistoriaExamenFisico());
		            	params.put("numHojaConsulta", datosAdicionales.getNumHojaConsulta());
		            	params.put("codExpediente", datosAdicionales.getCodExpediente());
		            	params.put("expedienteFisico", datosAdicionales.getExpedienteFisico());
		            	
		            	String pathPag3 = System.getProperty("jboss.server.data.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.reporte") + ( (nombreReporte+"3").contains(".jasper")?(nombreReporte+"3"):(nombreReporte+"3") + ".jasper");
		            	JasperPrint fileAnexo2 = JasperFillManager.fillReport(pathPag3, params, new JRBeanCollectionDataSource(collectionDataSource));
		            	report.addPage(fileAnexo2.removePage( 0 ));
	            	}*/
	            	HashMap params = new HashMap(); 
	            	params.put("planes", datosAdicionales.getPlanes());
	            	params.put("historiaExamenFisico", datosAdicionales.getHistoriaExamenFisico());
	            	params.put("numHojaConsulta", datosAdicionales.getNumHojaConsulta());
	            	params.put("codExpediente", datosAdicionales.getCodExpediente());
	            	params.put("expedienteFisico", datosAdicionales.getExpedienteFisico());
	            	
	            	String pathPag3 = System.getProperty("jboss.server.data.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.reporte") + ( (nombreReporte+"3").contains(".jasper")?(nombreReporte+"3"):(nombreReporte+"3") + ".jasper");
	            	JasperPrint fileAnexo2 = JasperFillManager.fillReport(pathPag3, params, new JRBeanCollectionDataSource(collectionDataSource));
	            	report.addPage(fileAnexo2.removePage( 0 ));
	            }
            }
          return  JasperExportManager.exportReportToPdf(report);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public  void imprimirDocumento(String nombres, byte[] archivoByte){
    	
    	String path = System.getProperty("jboss.server.data.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.pdf") + (nombres.contains(".pdf")?nombres:nombres + ".pdf");
    	
  	    File file = new File(path);
    	try
    	{
              //convert array of bytes into file
           FileOutputStream fileOuputStream =
                      new FileOutputStream(file);
           fileOuputStream.write(archivoByte);
           fileOuputStream.close();
           
           PDDocument pdf = PDDocument.load(path);
           
           /*---------------------*/
           configPrint = UtilProperty.getConfigurationfromExternalFile("estudioCohorteCSSFVOPC.properties");
           String impresoraConsultorio = configPrint.getString("CONSULTORIOMEDICO");
           
           javax.print.PrintService[] service = PrinterJob.lookupPrintServices(); 
           DocPrintJob docPrintJob = null;
           
           //int count = service.length;
           for (int i = 0; i < service.length; i++) {
             if (service[i].getName().equals(impresoraConsultorio)) {
            	 System.out.println("Consultorio Medico: " + service[i].getName());
            	 docPrintJob = service[i].createPrintJob();
             }
           }

           PrinterJob pjob = PrinterJob.getPrinterJob();
           if (docPrintJob != null) {
        	   pjob.setPrintService(docPrintJob.getPrintService());
               pjob.setJobName("job");
               pdf.silentPrint(pjob);
           }
           /*---------------------*/
          
           
		   //pdf.silentPrint();
    		
    	} catch(IOException e) {
    		
    		e.printStackTrace();
    	} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
	}
    
    public  void imprimirDocumentoV2(String nombres, byte[] archivoByte, int consultorio) {
    	
    	String path = System.getProperty("jboss.server.data.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.pdf") + (nombres.contains(".pdf")?nombres:nombres + ".pdf");
    	
  	    File file = new File(path);
    	try
    	{
              //convert array of bytes into file
           FileOutputStream fileOuputStream =
                      new FileOutputStream(file);
           fileOuputStream.write(archivoByte);
           fileOuputStream.close();
           
           PDDocument pdf = PDDocument.load(path);
           
           /*---------------------*/
           configPrint = UtilProperty.getConfigurationfromExternalFile("estudioCohorteCSSFVOPC.properties");
           String impresoraConsultorio = configPrint.getString("CONSULTORIOMEDICO");
           String impresoraRespiratorio = configPrint.getString("CONSULTORIORESPIRATORIO");
           
           javax.print.PrintService[] service = PrinterJob.lookupPrintServices();
           //PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
           
           DocPrintJob docPrintJob = null;
           
           
           System.out.println("SERVICE: " + service);
           System.out.println("SERVICE LENGHT: " + service.length);
           System.out.println("Consultorio Medico: " + impresoraConsultorio);
           //System.out.println("PRINTSERVICES: " + printServices.length);
           
           
           //int count = service.length;
           for (int i = 0; i < service.length; i++) {
             if (service[i].getName().equals(impresoraConsultorio) && consultorio <= 0) {
            	 System.out.println("Consultorio Medico: " + service[i].getName());
            	 docPrintJob = service[i].createPrintJob();
             }
             
             if (service[i].getName().equals(impresoraRespiratorio) && consultorio > 0) {
            	 System.out.println("Consultorio Resp: " + service[i].getName());
            	 docPrintJob = service[i].createPrintJob();
             }
           }

           PrinterJob pjob = PrinterJob.getPrinterJob();
           if (docPrintJob != null) {
        	   pjob.setPrintService(docPrintJob.getPrintService());
               pjob.setJobName("job");
               pdf.silentPrint(pjob);
           }
           /*---------------------*/
          
           
		   //pdf.silentPrint();
    		
    	} catch(IOException e) {
    		
    		e.printStackTrace();
    	} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
	}
    
    
    public  void imprimirHojaConsulta(String nombres, byte[] archivoByte, boolean esConsultaRespiratoria){
    	
    	String path = System.getProperty("jboss.server.data.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.pdf") + (nombres.contains(".pdf")?nombres:nombres + ".pdf");
    	
  	    File file = new File(path);
    	try
    	{
              //convert array of bytes into file
           FileOutputStream fileOuputStream =
                      new FileOutputStream(file);
           fileOuputStream.write(archivoByte);
           fileOuputStream.close();
           
           PDDocument pdf = PDDocument.load(path);
           
           /*---------------------*/
           configPrint = UtilProperty.getConfigurationfromExternalFile("estudioCohorteCSSFVOPC.properties");
           String impresoraConsultorio = configPrint.getString("CONSULTORIOMEDICO");
           String impresoraRespiratorio = configPrint.getString("CONSULTORIORESPIRATORIO");
           
           javax.print.PrintService[] service = PrinterJob.lookupPrintServices(); 
           DocPrintJob docPrintJob = null;
           
           //int count = service.length;
           for (int i = 0; i < service.length; i++) {
             if (service[i].getName().equals(impresoraConsultorio) && !esConsultaRespiratoria) {
            	 System.out.println("Consultorio Medico: " + service[i].getName());
            	 docPrintJob = service[i].createPrintJob();
             }
             
             if (service[i].getName().equals(impresoraRespiratorio) && esConsultaRespiratoria) {
            	 System.out.println("Consultorio Resp: " + service[i].getName());
            	 docPrintJob = service[i].createPrintJob();
             }
           }

           PrinterJob pjob = PrinterJob.getPrinterJob();
           if (docPrintJob != null) {
        	   pjob.setPrintService(docPrintJob.getPrintService());
               pjob.setJobName("job");
               pdf.silentPrint(pjob);
           }
           /*---------------------*/
          
           
		   //pdf.silentPrint();
    		
    	} catch(IOException e) {
    		
    		e.printStackTrace();
    	} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
	}
    
    
    public void imprimirDocumentoFicha(String nombres,byte[] archivoByte, int consultorio, Integer valorParametro){
    	
    	String path = System.getProperty("jboss.server.data.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.pdf") + (nombres.contains(".pdf")?nombres:nombres + ".pdf");
    	
  	    File file = new File(path);
  	    //int copies = 5;
    	try
    	{
              //convert array of bytes into file
           /*FileOutputStream fileOuputStream =
                      new FileOutputStream(file);
           fileOuputStream.write(archivoByte);
           fileOuputStream.close();
           
           PrinterJob printJob = PrinterJob.getPrinterJob();
           printJob.setCopies(copies);
           
           PDDocument pdf = PDDocument.load(path);
		   pdf.silentPrint(printJob);*/
    		
    		//convert array of bytes into file
            FileOutputStream fileOuputStream =
                       new FileOutputStream(file);
            fileOuputStream.write(archivoByte);
            fileOuputStream.close();
            
            PDDocument pdf = PDDocument.load(path);
            
            /*---------------------*/
            configPrint = UtilProperty.getConfigurationfromExternalFile("estudioCohorteCSSFVOPC.properties");
            String impresoraConsultorio = configPrint.getString("CONSULTORIOMEDICO");
            String impresoraRespiratorio = configPrint.getString("CONSULTORIORESPIRATORIO");
            
            javax.print.PrintService[] service = PrinterJob.lookupPrintServices(); 
            DocPrintJob docPrintJob = null;
            
            //int count = service.length;
            for (int i = 0; i < service.length; i++) {
            	if (service[i].getName().equals(impresoraConsultorio) && consultorio <= 0) {
               	 System.out.println("Consultorio Medico: " + service[i].getName());
               	 docPrintJob = service[i].createPrintJob();
                }
            	
                if (service[i].getName().equals(impresoraRespiratorio) && consultorio > 0) {
               	 System.out.println("Consultorio Resp: " + service[i].getName());
               	 docPrintJob = service[i].createPrintJob();
                }
            }
            
            for (int j = 0; j < valorParametro; j++) {
            	PrinterJob pjob = PrinterJob.getPrinterJob();
                pjob.setPrintService(docPrintJob.getPrintService());
                //pjob.setCopies(copies);
                pjob.setJobName("job");
                pdf.silentPrint(pjob);
            }
            /*---------------------*/
    		
    	} catch(IOException e) {
    		
    		e.printStackTrace();
    	} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
	}
    
    


public  void imprimirDocumentoTest(String nombres){
    	
    	//String path = System.getProperty("jboss.server.data.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.pdf") + (nombres.contains(".docx")?nombres:nombres + ".docx");
    	String path = "c:\\HojaConsulta.docx";
    	try
    	{
           System.out.println("----------------->" + path);
    		
    		FileInputStream inputStream = null;
            inputStream = new FileInputStream(path);
            if (inputStream == null) {
                return;
            }
            
           DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
           Doc document = new SimpleDoc(inputStream, docFormat, null);
    
         //  printAvailable();
           PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
           PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
    
           if (defaultPrintService != null) {
               DocPrintJob printJob = defaultPrintService.createPrintJob();
               try {
                   printJob.print(document, attributeSet);
    
               } catch (Exception e) {
                   e.printStackTrace();
               }
           } else {
               System.err.println("No existen impresoras instaladas");
           }
           
           inputStream.close();
    		
    	}
    	catch(IOException e)
    	
    	{
    		
    		e.printStackTrace();
    	}
	        
	}

	public static void printAvailable() {
		 
	    // busca los servicios de impresion...
	    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
	
	    // -- ver los atributos de las impresoras...
	    for (PrintService printService : services) {
	
	        System.out.println(" ---- IMPRESORA: " + printService.getName());
	
	        PrintServiceAttributeSet printServiceAttributeSet = printService.getAttributes();
	
	        System.out.println("--- atributos");
	
	        // todos los atributos de la impresora
	        Attribute[] a = printServiceAttributeSet.toArray();
	        for (Attribute unAtribute : a) {
	            System.out.println("atributo: " + unAtribute.getName());
	        }
	
	        System.out.println("--- viendo valores especificos de los atributos ");
	
	        // valor especifico de un determinado atributo de la impresora
	        System.out.println("PrinterLocation: " + printServiceAttributeSet.get(PrinterLocation.class));
	        System.out.println("PrinterInfo: " + printServiceAttributeSet.get(PrinterInfo.class));
	        System.out.println("PrinterState: " + printServiceAttributeSet.get(PrinterState.class));
	        System.out.println("Destination: " + printServiceAttributeSet.get(Destination.class));
	        System.out.println("PrinterMakeAndModel: " + printServiceAttributeSet.get(PrinterMakeAndModel.class));
	        System.out.println("PrinterIsAcceptingJobs: " + printServiceAttributeSet.get(PrinterIsAcceptingJobs.class));
	
	    }
	
	}

    /**
     * M�todo para imprimir directamente en la impresora.
     *
     * @param nombreReporte
     * @param parametros
     * @param collectionDataSource
     */
//    public static void enviarAImpresora(String nombreReporte,
//            Map<String, Object> parametros, List<?> collectionDataSource) {
//
//        Window win = (Window) Executions.createComponents("etiqueta.zul", null, null);
//
//        //barCode = (String) parametros.get("pCodigoBulto");
//
//
//        Jasperreport report = null;
//        report = (Jasperreport) win.getFellow("imprimirReporte");
//
//        String path = System.getProperty("user.dir") + System.getProperty("file.separator").charAt(0) + config.getString("ruta.reportes") + (nombreReporte.contains(".jasper")?nombreReporte:nombreReporte + ".jasper");
//        path = path.replace('/', System.getProperty("file.separator").charAt(0));
//
//        report.setSrc(path);
//        report.setType("html");
//        report.setParameters(parametros!=null ? parametros : null);
//        report.setDatasource(collectionDataSource!=null ? new JRBeanCollectionDataSource(collectionDataSource) : null);
//
//        Clients.evalJavaScript("printing()");
//
//    }


    // ------------------------------ getters y setters ------------------------------

   

}
