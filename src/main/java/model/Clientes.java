package model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@XmlRootElement
@EntityScan
@Document(collection = "clientes")
public class Clientes {
	  
	@Id
	private String id;
	private String cpf;
	private String name;
	private String city;
	private String uf;
	private String operacao;

    public Clientes() {
    }

    public Clientes(String name, String city, String uf, String cpf) {
        this.name = name;
        this.city = city;
        this.uf = uf;
        this.cpf = cpf;
    }
    
    public Clientes(String name, String city, String uf, String operacao, String cpf) {
        this.name = name;
        this.city = city;
        this.uf = uf;
        this.operacao = operacao;
        this.cpf = cpf;
    }

    public Clientes(String id, String name, String city, String uf, String operacao, String cpf) {
    	this.id = id;
    	this.name = name;
        this.city = city;
        this.uf = uf;
        this.cpf = cpf;
    }

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
    
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getUf() {
		return uf;
	}
	
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}