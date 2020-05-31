package service;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import Interface.ApoliceRepository;
import model.Apolice;

@Service
public class ApoliceService {

	@Autowired
    private MongoTemplate mongoTemplate; 
	
	@Autowired
    private ApoliceRepository apoliceRepository;
 
    public void salvar(String idCliente, Boolean inativo, String nrApolice, float vlrApolice, String dtInicio, String dtFim, String placa) {
        apoliceRepository.save(new Apolice(idCliente, inativo, nrApolice, vlrApolice, dtInicio, dtFim, placa));
    }
 
    public List<Apolice> findAll() {
       return (List<Apolice>) apoliceRepository.findAll();
    }
 
    public long count() {
        return apoliceRepository.count();
    }
 
    public Optional<Apolice> findById(String id) {
        return apoliceRepository.findById(id);
    }
 
    public void delete(String id) {
        apoliceRepository.deleteById(id);
    }
    
    public List<Apolice> nrContrato(String idCliente) {
    	Query query = new Query();
        query.addCriteria(Criteria.where("idCliente").is(idCliente));
      return mongoTemplate.find(query, Apolice.class);
	  }
   
}
