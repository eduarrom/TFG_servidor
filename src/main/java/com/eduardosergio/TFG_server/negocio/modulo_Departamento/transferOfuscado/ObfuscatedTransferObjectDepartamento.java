package com.eduardosergio.TFG_server.negocio.modulo_Departamento.transferOfuscado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.rodrigo.TFG_server.Negocio.Modulo_Departamento.Entidad.Transfers.TDepartamento;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ObfuscatedTransferObjectDepartamento {
	private List<String> maskedList;
	private HashMap<Object, Object> publicData;
	private HashMap<Object, Object> protectedData;

	
	public ObfuscatedTransferObjectDepartamento() {
		maskedList = new ArrayList<>();
		publicData = new HashMap<>();
		protectedData = new HashMap<>();
	}
	
	
	public TDepartamento defuse() {
		TDepartamento departamento = new TDepartamento();
		departamento.setId((long) protectedData.get("id"));
		departamento.setNominaMes((double) protectedData.get("nominaMes"));
		departamento.setSiglas((String) publicData.get("siglas"));
		departamento.setNombre((String) publicData.get("nombre"));
		
		return departamento;
	}
	
	public String toString() {
		String cadena = "";
		
		for (Entry<Object, Object> entrySet : publicData.entrySet()) {
			cadena += entrySet.getKey().toString() + " : " + entrySet.getValue().toString() + "\n";
		}
		
		return cadena;
	}
}

