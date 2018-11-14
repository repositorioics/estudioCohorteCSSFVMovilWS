package com.sts_ni.estudiocohortecssfv.interfaces;

import org.apache.commons.configuration.CompositeConfiguration;

public interface PropiedadesIT
{

    public abstract void addPropertyFilefromClassPath(String s);

    public abstract void addPropertyFilefromFileSystem(String s);

    public abstract void addPropertyFilefromFileSystem(String s, String s1);

    public abstract CompositeConfiguration getCompositeConfiguration();

    public abstract String userDir();

    public abstract String userHome();

    public static final String BASEPATH = "/ni/com/sts/propiedadesGeneral/";
}
