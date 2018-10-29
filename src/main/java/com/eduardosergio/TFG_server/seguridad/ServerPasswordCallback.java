package com.eduardosergio.TFG_server.seguridad;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

public class ServerPasswordCallback implements CallbackHandler {

	@Override
	public void handle(Callback[] arg0) throws IOException, UnsupportedCallbackException {
	        WSPasswordCallback pc = (WSPasswordCallback) arg0[0];

	        // set the password for our message.
	        pc.setPassword("storepass");

	}

}
