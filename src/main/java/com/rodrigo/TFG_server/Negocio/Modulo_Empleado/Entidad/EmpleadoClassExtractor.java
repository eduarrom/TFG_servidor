//package com.rodrigo.TFG_server.Negocio.Modulo_Proyecto.Entidad;
//
//
////import org.eclipse.persistence.descriptors.ClassExtractor;
////import org.eclipse.persistence.sessions.Record;
////import org.eclipse.persistence.sessions.Session;
//
//public class EmpleadoClassExtractor extends ClassExtractor {
//
//    @Override
//    public Class extractClassFromRow(Record record, Session session) {
//        System.out.println("---------- EmpleadoClassExtractor  ------------");
//        System.out.println("record = [" + record + "]");
//        System.out.println("session = [" + session + "]");
//        System.out.println("-----------------------------------------------");
//
//        if (null != record.get("@horasJornada")) {
//            return EmpleadoTParcial.class;
//        } else if (null != record.get("@antiguedad")) {
//            return EmpleadoTCompleto.class;
//        }
//        return null;
//    }
//
//
//}
