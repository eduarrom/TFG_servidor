# TFG_server

Proyecto con el backend de mi TFG, usa **JAX-WS** y **JAX-RS** con la implementación **CXF** usando servicios **SOAP** y **REST**.

Está montado con **Maven** y se usa **Jenkins** para hacer pruebas iniciales con la Integración Continua y **JUnit** para las pruebas unitarias. Posterior mente se usará **JMeter** para las pruebas de carga.

El proyecto forma parte de mi TFG [Trabajo de Final de Grado](https://hunzagit.github.io/Portfolio-Online/#TFG) de Ingeniería del Software.

## Entorno

 - **Java** 8
 - **JDK** 1.8.0_152
 - **CXF** 3.2.1
 - **Maven** 3.3.9
 - **JUnit** 5.0.2
 - **Hibernate** 4.2.21
 - **MySQL** 5.0.11
 - **Apache Tomcat** 8.5.23
 - **IntelliJ IDEA** 2017.2.6
 - **Jenkins** 2.92
 


## Branches

La rama **Development** contiene el código que se está desarrollando y que no ha pasado satisfactoriamente todas las pruebas.
La rama **master** contiene el codigo que ha pasado satisfactoriamente las pruebas, todo ello gestionado por Jenkins.


## Dependencias

No tiene dependencias de otros proyectos externos, aunque tiene una [aplicación cliente](https://github.com/hunzaGit/TFG_cliente) que consume los servicios expuestos mediante **WSDL** y **REST**.
 

## Instalación

    $ mvn clean package
    
Desplegar el .war en Tomcat con Tomcat_Manager o:

    $ cp target/TFG_server.war $CATALINA_BASE/webapps/
    
    $ open http://localhost:${tomcat_port}/TFG_server/

