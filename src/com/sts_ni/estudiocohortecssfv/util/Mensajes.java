package com.sts_ni.estudiocohortecssfv.util;



public class Mensajes {

    public enum TipoMensaje {INFO, EXCLAM, QUESTION , ERROR}

    /**
    * Mensaje de error cuando se produce un error no controlado y se produce una
    * excepción
    */
    public static final String ERROR_NO_CONTROLADO="Se ha producido un error no controlado en el servicio: ";
    /**
    * Mensaje que según la gravedad del error, se solicita la notificación al
    * administrador del sistema
    */
    public static final String NOTIFICACION_ADMINISTRADOR="Anote el detalle del mensaje y notifique al administrador del sistema. ";
    /**
    * Mensaje que se presenta cuando se guarda un registro
    * de forma exitosa.
    */
    public static final String REGISTRO_GUARDADO="El registro ha sido almacenado con éxito. ";
    public static final String REGISTRO_ACTUALIZADO="El registro ha sido actualizado con éxito. ";
    public static final String REGISTRO_NO_SELECCIONADO="El registro no pudo ser seleccionado. ";
    public static final String REGISTRO_ELIMINADO="El registro ha sido eliminado con éxito. ";
    public static final String EXCEPCION_REGISTRO_EXISTE="El registro no puede ser guardado. El registro ya existe. ";
    public static final String REGISTRO_NO_GUARDADO="Se ha producido un error al guardar el registro. ";
    public static final String REGISTRO_DUPLICADO="El registro ya existe y no puede estar duplicado.";
    public static final String ELIMINAR_REGISTRO_NO_EXISTE="El registro ya no existe y por tanto, no puede ser eliminado";
    public static final String ENCONTRAR_REGISTRO_NO_EXISTE="El registro ya no existe, ha sido eliminado por otro usuario";
    public static final String ELIMINAR_RESTRICCION="El registro no puede ser eliminado ya que otros registros dependen de él";
    public static final String REGISTRO_NO_ELIMINADO="Se ha producido un error al eliminar el registro. ";
    public static final String REGISTRO_NO_ELIMINADO_CONSTRAINT="Se ha producido un error al eliminar el registro. El registro tiene dependencias";
    public static final String REGISTRO_AGREGADO="Registro Agregado con éxito";
    public static final String REGISTRO_NO_ENCONTRADO="No se han encontrado resultados";
    public static final String EXCEPCION_VALORES_REQUERIDOS="El registro no puede ser guardado. Valores requeridos sin definir.";
    public static final String AUTENTICACION_FALLIDA="El nombre de usuario o contraseña es incorrecto.";
    public static final String ALUMNO_YA_FUE_REGISTRADO="Alumno ya se encuentra registrado para la asignatura y/o grupo seleccionadas";
    public static final String PACIENTE_EN_ESTADO_RETIRADO="Paciente en estado retriado";
    public static final String MATRIZ_CASO_A="Reune criterios para Caso A. Recuerde tomar muestras serologicas y metabolmica";
    public static final String MATRIZ_CASO_B="Reune criterios para Caso B. Recuerde tomar muestras serologica";
    public static final String MATRIZ_CASO_ETI="Reune criterios ETI. Recuerde tomar muestras de influenza";
    public static final String MATRIZ_CASO_IRAG="Reune criterios IRAG. Recuerde tomar muestras de influenza";
    public static final String MATRIZ_CASO_NEUMO="Reune criterios de Neumonia. Recuerde tomar muestras respiratoria";
    public static final String MATRIZ_CASO_IRAG_SIN_FIEBRE="Reune criterios IRAG SIN FIEBRE. Recuerde tomar muestras respiratoria";
    public static final String MATRIZ_CASO_IRAG_FIEBRE="Reune criterios IRAG. Recuerde tomar muestras respiratoria";
    public static final String PACIENTE_NO_ENCONTRADO_RETIRADO ="No se encentro el paciente, puede estar en estado retirado";

    public static final String FECHAFINAL_MAYORIGUAL_FECHAINICIO = "Fecha final debe ser mayor o igual a Fecha inicio.";
    public static final String PARAMETROS_BUSQUEDA_REQUERIDOS="Debe seleccionar e ingresar los parámetros de búsqueda.";
    public static final String ERROR_CONSULTAR_DATOS="Se produjo un error al consultar los datos.";
    public static final String ERROR_VALIDAR_DATOS="Se ha producido un error al validar los datos.";
    public static final String ERROR_PROCESAR_DATOS="Se ha producido un error al procesar los datos.";
    public static final String CONFIRMA_ELIMINAR_REGISTRO="¿Está seguro que desea eliminar el registro?";
    public static final String CRITERIO_BUSQUEDA_REQUERIDO="Debe especificar criterio de búsqueda.";
    public static final String SELECCIONE_REGISTRO_LISTADO="Debe seleccionar registro del listado.";
    public static final String ERROR_ACTUALIZAR_REGISTRO="Se ha producido un error al actualizar el registro.";
    public static final String ERROR_CONSULTAR_REGISTRO="Se producido un error al consultar registro.";
    public static final String VALORES_REQUERIDOS="Algunos datos son requeridos, por favor verifique.";
    public static final String VALORES_ILEGALES="Valor ilegal, por favor verifique.";
    public static final String CASILLAS_SIN_MARCAR = "Existen Casillas sin marcar, Favor Verificar";
    public static final String EXISTEN_RESULTADOS_PENDIENTES = "Existen Resultados de Laboratorio Pendientes";
    public static final String HOJA_CONSULTA_NO_CERRADA = "Hoja de Consulta NO Cerrada, favor verificar";
    public static final String CODIGO_PACIENTE_NO_EXISTE = "Código de Paciente no existe";
    public static final String HOJA_INFLUENZA_CERRADA = "Este seguimiento ya se encuentra cerrado!";


    public static final String NO_DATOS="No se han encontrado datos.";
    public static final String NO_PACIENTECONSULTA="No hay pacientes pendientes para Consulta.";
    public static final String NO_PACIENTES_ENFERMERIA="No hay pacientes pendientes para Enfermeria";
    public static final String NO_EXISTEN_MEDICOS="No se encontraron médicos";
    public static final String PACIENTE_SELECCIONADO="Este paciente ya esta siendo atendido por otro usuario";
    
    public static final String CODIGO_PACIENTE_YA_INGRESADO="El codigo del paciente ya fue ingresado.";

    public static final String NO_EXISTE_HC_CODEXP="No existe hoja de consulta para el código del paciente.";
    public static final String HOJA_INF_NO_CERRADA="El paciente tiene hoja de seguimiento influenza sin cerrar.";
    public static final String HOJA_SIN_FIS_FIF="Primero debe completar la FIS y la FIF de la hoja de consulta.";
    public static final String NO_PUEDE_CREAR_HOJA_FLU="No se cumplen los requerimientos para crear la hoja de influenza";
    public static final String NO_PUEDE_CREAR_HOJA_FLU_ETI="El paciente debe presentar FIS más Eti para crear la hoja de influenza";
    public static final String NO_PUEDE_CREAR_HOJA_FLU_IRAG="El paciente debe presentar FIS más Irag para crear la hoja de influenza";
    public static final String NO_PUEDE_CREAR_HOJA_FLU_NEUMONIA="El paciente debe presentar FIS más Neumonia para crear la hoja de influenza";
    public static final String NO_PUEDE_CREAR_HOJA_SOLO_FIS="No se puede crear la hoja de influenza solo con FIS";
    
    public static final String NO_PUEDE_CREAR_HOJA_FLU_ESTUDIO_DENGUE="No se puede crear la hoja de influenza, el participante solo pertenece al estudio Dengue";
    public static final String NO_PUEDE_CREAR_HOJA_ZIKA_ESTUDIO_DENGUE="No se puede crear la hoja de zika, el participante no pertenece al estudio Dengue";
    
    public static final String ERROR_MARCO_FIEBRE_SIN_FIF="ERROR: Marco Fiebre y no ingreso la FIF favor revisar";
    
    public static final String HOJA_ZIKA_NO_CERRADA="El paciente tiene hoja de seguimiento zika sin cerrar.";
    
    public static final String RAZON_CANCELA_EXAMEN="Cancelado desde la aplicacion movil";
    
    public static final String FICHA_CASO_ETI="Recuerde llenar la ficha de vigilancia integrada ETI";
    public static final String FICHA_CASO_IRAG="Recuerde llenar la ficha de vigilancia integrada IRAG";
    
    public static final String HOJA_CONSULTA_NO_SE_PUDO_CERRAR = "La hoja de consulta no se pudo cerrar, Favor intente cerrar la nuevamente";
    
    public static final String PACIENTE_NO_PUEDE_SER_CATEGORIA_NA="Este paciente no puede ser categoria NA";
    public static final String PACIENTE_NO_PUEDE_SER_CATEGORIA_ABCD="Este paciente no puede ser categoria A, B, C, D";
    
    public static final String HOJA_SIN_FIS_CON_CAT_D="Para crear una hoja de zika con categoria D, solo se necesita la FIS";
    public static final String HOJA_ZIKA_SIN_CAT="No se puede crear una hoja de zika sin haber marcado la categoria en la hoja de consulta";
    public static final String HOJA_ZIKA_CON_CAT_C="No puede crear una hoja de zika con categoria C";
    public static final String DEBE_CREAR_HOJA_INFLUENZA = "Para poder cerrar la Hoja de Consulta, debera crear la Hoja de Influenza";
    public static final String ERROR_GUARDAR_EXAMEN_INFLUENZA = "No se puede enviar un examen de Influenza al laboratorio, debido a que no marco ETI, IRAG o Neumonia";
    public static final String ERROR_GUARDAR_EXAMEN_INFLUENZA_VALORES_NULL = "No se puede enviar un examen de Influenza al laboratorio, debido a que ETI, IRAG o Neumonia no tienen valor";
    public static final String ERROR_GUARDAR_EXAMEN_INFLUENZA_REQUERIDO = "Se debe de enviar a realizar el examen de influenza, ya que el paciente presento una ETI ó IRAG ó Neumonia";
    
    public static final String ERROR_AL_IMPRIMIR_HOJA_INFLUENZA = "Tiene que cerrar la Hoja de Seguimiento Influenza para poder imprimir";
    public static final String HOJA_INFLUENZA_IMPRESA = "Se envio la Hoja de Seguimiento Influenza a la impresora";
    
    public static final String ERROR_AL_IMPRIMIR_HOJA_ZIKA = "Tiene que cerrar la Hoja de Seguimiento Zika para poder imprimir";
    public static final String HOJA_ZIKA_IMPRESA = "Se envio la Hoja de Seguimiento Zika a la impresora";
    
    public static final String ERROR_FECHA_CIERRE = "La fecha cierre no puede ser menor a la fecha de inicio del seguimiento, favor revisar la fecha de su dispositivo";
}
