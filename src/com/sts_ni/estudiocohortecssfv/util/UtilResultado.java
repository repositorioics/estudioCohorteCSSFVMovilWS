package com.sts_ni.estudiocohortecssfv.util;

import java.util.List;

import org.json.simple.JSONObject;

public class UtilResultado {
	
	public static final int OK    = 0;
    public static final int INFO  = 1;
    public static final int ERROR = -1;

    /**
     *
     * @param oLista
     * @param mensaje
     * @param mensajeTipo
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static String parserResultado(List oLista, String mensaje, int mensajeCodigo) {
        String resultado = "";

        try {

            JSONObject msj = new JSONObject();
            msj.put("codigo", mensajeCodigo);
            msj.put("texto", mensaje);

            JSONObject obj = new JSONObject();
            obj.put("resultado", oLista);
            obj.put("mensaje", msj);

            resultado = obj.toJSONString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    /**
    *
    * @param oLista
    * @param mensaje
    * @param mensajeTipo
    * @return
    */
   @SuppressWarnings("unchecked")
public static String parserResultado2(JSONObject objeto, String mensaje, int mensajeCodigo) {
       String resultado = "";

       try {

           JSONObject msj = new JSONObject();
           msj.put("codigo", mensajeCodigo);
           msj.put("texto", mensaje);

           JSONObject obj = new JSONObject();
           obj.put("resultado", objeto);
           obj.put("mensaje", msj);

           resultado = obj.toJSONString();

       } catch (Exception e) {
           e.printStackTrace();
       }
       return resultado;
   }
   
   public static String getValorChar(Character valor){
	   Character cero = '0';
	   Character uno = '1';
	   
	   if(valor.charValue() == cero.charValue()){
		   return "SI";
	   }else if(valor.charValue() == uno.charValue()){
		   return "NO";
	   }else{
		   return "D";
	   }
   }
}
