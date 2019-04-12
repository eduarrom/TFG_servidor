package com.eduardosergio.TFG_server.seguridad.auditInterceptor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class AuditInterceptorLog {

    @GeneratedValue(strategy = GenerationType.AUTO) //IDENTITY
    @Id
    protected Long id;

    @NotBlank
    @Column(nullable = false)
    protected String ip;
    
    @NotBlank
    @Column(nullable = false)
    protected String userAndPassword;
    
    @NotBlank
    @Column(nullable = false)
    protected String invokedService;
    
    @NotNull
    @Column(nullable = false)
    private Date fecha;
    
    
    public AuditInterceptorLog(String ip, String userAndPassword, String invokedService) {
        this.ip = ip;
        this.fecha = new Date();
        this.userAndPassword = userAndPassword;
        this.invokedService = invokedService;
    }
    
    public AuditInterceptorLog() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserAndPassword() {
		return userAndPassword;
	}

	public void setUserAndPassword(String userAndPassword) {
		this.userAndPassword = userAndPassword;
	}

	public String getInvokedService() {
		return invokedService;
	}

	public void setInvokedService(String invokedService) {
		this.invokedService = invokedService;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
    
    
}
