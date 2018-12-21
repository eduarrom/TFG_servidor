package com.eduardosergio.TFG_server.negocio.modulo_Departamento.serv_aplicacion.impl;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eduardosergio.TFG_server.negocio.modulo_Departamento.Authenticator;
import com.eduardosergio.TFG_server.negocio.modulo_Departamento.serv_aplicacion.SSP_SA_Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Excepciones.DepartamentoFieldInvalidException;

public class SSP_SA_DepartamentoImpl implements SSP_SA_Departamento {
	
	private Logger log = LoggerFactory.getLogger(SSP_SA_DepartamentoImpl.class);

    private String HOST = "https://localhost" ;

    private String PORT = "8443";

    private String APP_URI = "/TFG_server/services";

    private final String SERVICE_NAME = "/SA_Departamento/departamento";

    private final String URL_SERVICIO = HOST + ":" + PORT + APP_URI + SERVICE_NAME;



    private Client cliente;


    public SSP_SA_DepartamentoImpl() {
        log.info("Creando DelegadoDelNegocio");

        log.info("Creando cliente");
        cliente = ClientBuilder
                .newBuilder()
                .newClient()
                .register(new Authenticator("user", "pass"));

        log.info("DelegadoDelNegocio creado");
    }
    
    @Override
    public TDepartamentoCompleto buscarByID(Long id) throws DepartamentoException {
        log.info("Delegado_DepartamentoImpl.buscarDepartamentoByID");
        log.info("id = [" + id + "]");

        String urlFinal = URL_SERVICIO + "";

        System.out.println("urlFinal = [" + urlFinal + "/" + id.toString() + "]");


        try {
            WebTarget wt = cliente.target(urlFinal);
            wt = wt.path(id.toString());
            Invocation.Builder b = wt.request();
            TDepartamentoCompleto dept = b.get(TDepartamentoCompleto.class);

            System.out.println("dept = [" + dept + "]");
            return dept;

        } catch (BadRequestException e) {

            System.out.println("Bad request");
            throw new DepartamentoFieldInvalidException("ERROR EN LA PETICION!");

        } catch (NotFoundException e) {

            System.out.println("Not found");
            return null;
        }
        
    }


}
