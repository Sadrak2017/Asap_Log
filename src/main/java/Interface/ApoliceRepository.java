package Interface;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.Apolice;


@Repository
public interface ApoliceRepository extends CrudRepository<Apolice, String> {

	@Override   
    public void delete(Apolice apolice);

}