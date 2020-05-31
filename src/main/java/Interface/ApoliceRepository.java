package Interface;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import model.Apolice;

@Repository
public interface ApoliceRepository extends MongoRepository<Apolice, String> {

	@Override   
    public void delete(Apolice apolice);
	
	@Query(value = "{'idCliente' :?0 }")
	List<Apolice> findContrato(String str);


}