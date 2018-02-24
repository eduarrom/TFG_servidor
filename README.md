# PruebaCXF

Proyecto de ejemplo para practicar con el lado del servior y aprender a usar **JAX-WS** con la implementación **CXF** usando servicios **SOAP**.

Está montado con **Maven** y se usa **Jenkins** para hacer pruebas iniciales con la Integración Continua. (Posteriormente se añadirá **JUnit** para las pruebas unitarias)

El proyecto contiene pruebas para ver el funcionamiento dentro del marco de mi del [Trabajo de Final de Grado](https://cv-rodrigodemiguel.herokuapp.com/#TFG) de Ingeniería del Software.

## Entorno

 - **Java** 8
 - **JDK** 1.8.0_152
 - **CXF** 3.2.1
 - **Maven** 3.3.9
 - **JUnit** 5.0.2
 - **Hibernate** 4.2.21
 - **Apache Tomcat** 8.5.23
 - **IntelliJ IDEA** 2017.2.6
 - **Jenkins** 2.92
 
## Dependencias

No tiene dependencias de otros proyectos externos, aunque tiene una [aplicación cliente](https://github.com/hunzaGit/pruebaCXF-cliente) (también de prueba) que consume los servicios expuestos mediante **WSDL**.
 
## Funcionamiento
 
Simplemente tiene una clase que expone un servicio el cual recibe un nombre y retorna un saludo


## Instalación


    $ mvn clean package
    
Desplegar el .war en Tomcat con Tomcat_Manager o:

    $ cp target/pruebaCXF.war $CATALINA_BASE/webapps/
    
    $ open http://localhost:${tomcat_port}/pruebaCXF/

