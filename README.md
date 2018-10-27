# TFG_server

Proyecto con el backend de mi TFG, usa **JAX-WS** y **JAX-RS** con la implementación **CXF** usando servicios **SOAP** y **REST**.

Está montado con **Maven** y se usa **Jenkins** para hacer pruebas iniciales con la Integración Continua y **JUnit** para las pruebas unitarias.

El proyecto forma parte de mi TFG [Trabajo de Final de Grado](https://hunzagit.github.io/Portfolio-Online/#TFG) de Ingeniería del Software.

### Entorno

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
 


### Branches

La rama **Development** contiene el código que se está desarrollando y que no ha pasado satisfactoriamente todas las pruebas.
La rama **master** contiene el codigo que ha pasado satisfactoriamente las pruebas, todo ello gestionado por Jenkins.


### Dependencias

No tiene dependencias de otros proyectos externos, aunque tiene una [aplicación cliente](https://github.com/hunzaGit/TFG_cliente) que consume los servicios expuestos mediante **WSDL** y **REST**.
 
 
## Tomcat
Es necesario añadir al server.xml el siguiente Realm:

```xml
<Realm className="org.apache.catalina.realm.JDBCRealm" connectionURL="jdbc:mysql://localhost/TFG_BBDD_TOMCAT?user=root&amp;password=1234" driverName="com.mysql.jdbc.Driver" userRoleTable="user_roles" userTable="users" roleNameCol="role_name" userCredCol="user_pass" userNameCol="user_name"
/>
```
 También hay que añadir a la carpeta $CATALINA_BASE/lib los .jar del driver jdbc. Se pueden descargar [aquí](https://dev.mysql.com/downloads/connector/j/5.1.html).
 
## Base de datos 

La conexión a la base de datos debe estar configurada de la siguiente manera:

* Usuario: root
* Contraseña: 1234

### Esquemas necesarios en la BBDD


* TFG_BBDD_SERVER: en este esquema estarán las tablas de las 3 entidades de la aplicación. No es necesario crear ninguna tabla porque JPA las genera.

* TFG_BBDD_TOMCAT: en este esquema se almacenan los usuarios de Tomcat y sus roles para el uso de HTTPS. Las tablas necesarias son:

```sql
CREATE TABLE user_roles
(
  user_name varchar(15) not null,
  role_name varchar(15) not null,
  primary key (user_name, role_name)
)

/* Rol que permite escribir en el servicio web REST (Departamento) */
INSERT INTO TFG_BBDD_TOMCAT.user_roles (user_name, role_name) VALUES ('user', 'write'); 

/* Usuario que permite escribir en el servicio web SOAP (Empleado y proyecto) */
INSERT INTO TFG_BBDD_TOMCAT.user_roles (user_name, role_name) VALUES ('usuario', 'escritura');

CREATE TABLE users
(
  user_name varchar(15) not null
    primary key,
  user_pass varchar(15) not null
)

INSERT INTO TFG_BBDD_TOMCAT.users (user_name, user_pass) VALUES ('user', 'pass');
INSERT INTO TFG_BBDD_TOMCAT.users (user_name, user_pass) VALUES ('usuario', 'contra');
```

* TFG_BBDD_CLIENTE: en este esquema se almacena los usuarios que serán autenticados mediante JAAS. La tabla necesaria es:

```sql
CREATE TABLE usuario
(
  id       int auto_increment
    primary key,
  nombre   text not null,
  email    text not null,
  password text not null,
  rol      int  not null, /* (0: Usuario, 1: Admin, 2: Superuser */
  version  int  null
)

/* Insertar este usuario por comodidad, es el valor que sale por defecto en el formulario
La contraseña MTIzNA== es el resultado de encriptar 1234.
*/
INSERT INTO tfg_bbdd_cliente.usuario (id, nombre, email, password, rol, version) VALUES (1, 'Sergio', 'administrador@gmail.com', 'MTIzNA==', 1, 1);
```

### Instalación

    $ mvn clean package -Dmaven.test.skip=true
    
Desplegar el .war en Tomcat con Tomcat_Manager o:

    $ cp target/TFG_server.war $CATALINA_BASE/webapps/
    
    $ cp target/TFG_cliente.war $CATALINA_BASE/webapps/
    
    $ catalina run

