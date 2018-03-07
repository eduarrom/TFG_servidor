package com.rodrigo.TFG_server.Integracion;



public class EMFSingleton {

    private static javax.persistence.EntityManagerFactory ourInstance;

    public static javax.persistence.EntityManagerFactory getInstance() {

        if(ourInstance == null){
            ourInstance =  javax.persistence.Persistence.createEntityManagerFactory("aplicacion");
        }
        return ourInstance;
    }


    @Override
    protected void finalize() throws Throwable {
        if(ourInstance.isOpen()){
            ourInstance.close();
        }
        super.finalize();
    }
}
