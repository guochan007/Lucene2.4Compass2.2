package com.util;

import org.compass.annotations.config.CompassAnnotationsConfiguration;
import org.compass.core.Compass;
import org.compass.core.config.CompassConfiguration;
import org.compass.gps.device.hibernate.HibernateSyncTransactionFactory;
import org.hibernate.SessionFactory;

public class CompassUtil {

   public static Compass getCompass() {
        CompassConfiguration conf = new CompassAnnotationsConfiguration().addScan("com.pojo");

//        CompassConfiguration conf= new CompassAnnotationsConfiguration();
        conf.setSetting("compass.engine.highlighter.default.formatter.simple.pre","<span style='color:red'>");
        conf.setSetting("compass.engine.highlighter.default.formatter.simple.post","</span>");

        conf.setSetting("compass.transaction.factory","org.compass.gps.device.hibernate.HibernateSyncTransactionFactory");
//        conf.configure();
        conf.configure("/compass.cfg.xml");
        
        SessionFactory sessionFactory=HibernateSessionFactory.getSessionFactory();
        HibernateSyncTransactionFactory.setSessionFactory(sessionFactory); 

        Compass compass = conf.buildCompass();

       return compass;
    }
   
   public static void main(String[] args) {
	   Compass compass=CompassUtil.getCompass();
	   System.out.println(compass);
   }
   
}