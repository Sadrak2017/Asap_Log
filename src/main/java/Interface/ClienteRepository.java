package Interface;

import java.util.List;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import model.Clientes;

@Repository
public interface ClienteRepository extends CrudRepository<Clientes, String> {
	 
	 @Override   
	 public void delete(Clientes cliente);
	
	 @Query("{ 'name' : { '$is' : ?0}}")
	 List<Clientes> findIlikeName(String str);
	 
	 

}