package com.sts_ni.estudiocohortecssfv.util;

import org.apache.commons.configuration.CompositeConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateResource {
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static final SessionFactory sessionFactory;

    static {

        Configuration configuration = new Configuration();
        CompositeConfiguration config = UtilProperty.getConfiguration("EstudioCohorteCssfvMovilWSExt.properties", "com/sts_ni/estudiocohortecssfv/properties/EstudioCohorteCssfvMovilWSInt.properties");

        configuration.configure();
        configuration.setProperty("hibernate.connection.datasource", config.getString("JNDI"));
        configuration.setProperty("hibernate.default_schema", config.getString("DEFAULT_SCHEMA"));
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public HibernateResource() {

    }

    /**
    * Se recupera o crea un objeto sesión. El objeto session se utiliza para
    * obtener, actualizar y borrar objetos en la base de datos. Nos permite
    * manejar las transacciones de acceso a la BD.
    */
    public Session getSession() {

        Session iSession = (Session) HibernateResource.session.get();

        if (iSession == null || iSession.isOpen() == false) {
            iSession = sessionFactory.openSession();
            HibernateResource.session.set(iSession);
        }
        return iSession;
    }

    /**
    * Iniciamos una nueva transacción.
    */
    public void begin() {
        try {
            if (!getSession().getTransaction().isActive()) {
                getSession().beginTransaction();
            }
        } catch (Exception e) {
            System.out.println("---- EXCEPTION");
            System.out.println("Error en JTA Transaction: " + e.toString());
        }

    }

    /**
    * Ejecutamos la transacción.
    */
    public void commit() {
        try {
            if (getSession().getTransaction().isActive()){
                getSession().getTransaction().commit();
            }

        } catch (Exception e) {
            System.out.println("---- EXCEPTION");
            System.out.println("Error en JTA Transaction: " + e.toString());
        }
    }

    /**
    * Atómica: Una transacción ha de ser todo o nada. Si falla en completarse,
    * la base de datos se dejará como si ni una de las operaciones se hubiera
    * llevado a cabo.
    */
    public void rollback() {
        try {
            getSession().getTransaction().rollback();
        } catch (Exception e) {
            System.out.println("---- EXCEPTION");
            System.out.println("Error en JTA Transaction: " + e.toString());
        }

    }

    /**
    * Una vez finalizada la transacción cerramos la sesión.
    */
    public void close() {
        try {
            getSession().close();
            HibernateResource.session.set(null);
        } catch (Exception e) {
            System.out.println("---- EXCEPTION");
            System.out.println("Error en JTA Transaction: " + e.toString());
        }
    }

}
