package com.rodrigo.TFG_server.Integracion;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EMFSingleton {


    private final static Logger log = LoggerFactory.getLogger(EMFSingleton.class);

    private static javax.persistence.EntityManagerFactory ourInstance = javax.persistence.Persistence.createEntityManagerFactory("TFG_server");

    public static javax.persistence.EntityManagerFactory getInstance() { return ourInstance;    }


    @Override
    protected void finalize() throws Throwable {
        log.info("Eliminando EMFSingleton...");
        if (ourInstance.isOpen()) {
            ourInstance.close();
        }
        super.finalize();
    }
}
