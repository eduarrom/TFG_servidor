package com.rodrigo.TFG_server.Integracion;


public class EMFSingleton {

    private static javax.persistence.EntityManagerFactory ourInstance = javax.persistence.Persistence.createEntityManagerFactory("TFG_server");

    public static javax.persistence.EntityManagerFactory getInstance() { return ourInstance;    }


    @Override
    protected void finalize() throws Throwable {
        if (ourInstance.isOpen()) {
            ourInstance.close();
        }
        super.finalize();
    }
}
