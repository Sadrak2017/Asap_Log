package Interface;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import model.Clientes;

@Repository
public interface ClienteRepository extends MongoRepository<Clientes, String> {
	 
	 @Override   
	 public void delete(Clientes cliente);
	
	 @Query("{ 'name' : {$regex : ?0}}")
	 List<Clientes> findIlikeName(String name);
	 
}