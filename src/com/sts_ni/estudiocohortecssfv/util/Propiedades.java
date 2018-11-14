package com.sts_ni.estudiocohortecssfv.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import com.sts_ni.estudiocohortecssfv.interfaces.PropiedadesIT;


@SuppressWarnings({"rawtypes","unchecked"})
public class Propiedades implements PropiedadesIT
{

    private List propertyFromFileSystem;
    private List propertyFromClassPath;
    private List basePath;
    private CompositeConfiguration config;
    private final Logger logger = Logger.getLogger(this.getClass());

    public Propiedades()
    {
        config = new CompositeConfiguration();
        propertyFromFileSystem = new ArrayList();
        propertyFromClassPath = new ArrayList();
        basePath = new ArrayList();
    }

    public void addPropertyFilefromClassPath(String fileName)
    {
        loadPropertyfromClasspath(fileName);
    }

    public void addPropertyFilefromFileSystem(String fileName, String basePath)
    {
        loadPropertyfromFileSystem(fileName, basePath);
    }

    public void addPropertyFilefromFileSystem(String fileName)
    {

        propertyFromClassPath.add(fileName);
        loadPropertyfromFileSystem(fileName, userDir() + "/ni/com/sts/propertiesGenerales/");

    }

    private void loadPropertyfromClasspath(String fileName)
    {
        propertyFromFileSystem.add(fileName);
        PropertiesConfiguration configProperty = new PropertiesConfiguration();
        configProperty.setFileName(fileName);
        try
        {
            configProperty.load();
        }
        catch(ConfigurationException e)
        {
            String msg = "No se pudo cargar el archivo de propiedades del classpath[" + fileName + "]";
            logger.error(msg);
            throw new RuntimeException(msg);
        }
        config.addConfiguration(configProperty);
    }

    private void loadPropertyfromFileSystem(String fileName, String basePath)
    {

        propertyFromFileSystem.add(fileName);
        basePath = basePath.replace('/', System.getProperty("file.separator").charAt(0));
        this.basePath.add(basePath);

        File file = new File(basePath + fileName);
        PropertiesConfiguration configProperty = null;
        try
        {
            configProperty = new PropertiesConfiguration(file);
        }
        catch(ConfigurationException e)
        {
            String msg = "No se pudo cargar el archivo de propiedades del fileSystem[" + basePath + fileName + "]";
            logger.error(msg);
            e.printStackTrace();
            throw new RuntimeException(msg);
        }
        configProperty.setFile(file);
        config.addConfiguration(configProperty);
    }

    public String userDir()
    {
        String userDir = System.getProperty("jboss.server.data.dir");
        return userDir;
    }

    public String userHome()
    {
        String userHome = System.getProperty("user.home");
        System.out.print("user.home[" + userHome + "]");
        return userHome;
    }

    public CompositeConfiguration getCompositeConfiguration()
    {
        System.out.println("Controller():start return getcomposite: " + basePath);
        return config;
    }


}
