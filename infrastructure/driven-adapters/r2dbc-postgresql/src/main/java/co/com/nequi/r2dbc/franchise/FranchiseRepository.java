package co.com.nequi.r2dbc.franchise;

import co.com.nequi.r2dbc.entity.FranchiseEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, String>, ReactiveQueryByExampleExecutor<FranchiseEntity>{
}
