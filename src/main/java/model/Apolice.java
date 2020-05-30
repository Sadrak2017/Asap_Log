package model;

import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@XmlRootElement
@Document(collection = "apolice")
public class Apolice{

	@Id
	private String id;
	private String idCliente;
	private String operacao;
	private String nrApolice;
	private float vlrApolice;
	private String dtInicio;
	private String dtFim;
	private String placa;
	private Clientes cliente;

	public Apolice() {
	
	}
    public Apolice(String idCliente, String nrApolice, float vlrApolice, String dtInicio, String dtFim, String placa) {
        this.idCliente = idCliente;
        this.nrApolice = nrApolice;
        this.vlrApolice = vlrApolice;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.placa = placa;
    }
    
    public Apolice(Clientes cliente, String nrApolice, float vlrApolice, String dtInicio, String dtFim, String placa) {
        this.nrApolice = nrApolice;
        this.vlrApolice = vlrApolice;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.placa = placa;
        this.cliente = cliente;
    }

    public Apolice(String idCliente, String operacao, String nrApolice, float vlrApolice, String dtInicio, String dtFim, String placa) {
        this.idCliente = idCliente;
        this.operacao = operacao;
        this.nrApolice = nrApolice;
        this.vlrApolice = vlrApolice;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.placa = placa;
    }

    
	
	public Clientes getCliente() {
		return cliente;
	}
	
	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
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
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public String getNrApolice() {
		return nrApolice;
	}
	public void setNrApolice(String nrApolice) {
		this.nrApolice = nrApolice;
	}
	public float getVlrApolice() {
		return vlrApolice;
	}
	public void setVlrApolice(float vlrApolice) {
		this.vlrApolice = vlrApolice;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getDtInicio() {
		return dtInicio;
	}
	public void setDtInicio(String string) {
		this.dtInicio = string;
	}
	public String getDtFim() {
		return dtFim;
	}
	public void setDtFim(String dtFim) {
		this.dtFim = dtFim;
	}
	
	
}
