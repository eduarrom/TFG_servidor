package com.eduardosergio.TFG_server.negocio.modulo_Departamento.serv_aplicacion.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eduardosergio.TFG_server.negocio.modulo_Departamento.serv_aplicacion.SSP_SA_Departamento;
import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto
import com.rodrigo.TFG_cliente.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamentoCompleto;
import com.rodrigo.TFG_cliente.Negocio.Modulo_Departamento.Excepciones.DepartamentoException;
import com.rodrigo.TFG_cliente.Negocio.Modulo_Empleado.Delegado.impl.Delegado_EmpleadoImpl;

public class SSP_SA_DepartamentoImpl implements SSP_SA_Departamento {
	
	private static Logger log = LoggerFactory.getLogger(Delegado_EmpleadoImpl.class);

    private SSP_SA_Departamento portDepartamento;

    private String HOST = "https://localhost" ;

    private String PORT = "8443";

    private String APP_URI = "/TFG_server/services";


    private final String URL_WSDL = "http://localhost:8080/TFG_server/wsdl/SA_Departamento.wsdl";

    private final String URL_SERVICE = HOST + ":"+ PORT + APP_URI + "/SA_DepartamentoSOAP";

    private final String NAMESPACE_URI = "http://impl.Serv_aplicacion.modulo_departamento.negocio.TFG_server.eduardosergio.com/";

    private final String SERVICE_NAME = "Broker_SA_DepartamentoImpl";
	
	@Override
	public TDepartamentoCompleto buscarByID(Long id) throws DepartamentoException {
		return portDepartamento.buscarByID(id);
	}
}
