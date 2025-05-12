package co.com.nequi.r2dbc.franchise;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.r2dbc.entity.FranchiseEntity;
import co.com.nequi.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.function.Function;
@Repository
@Log4j2
public class FranchiseRepositoryAdapter extends ReactiveAdapterOperations<Franchise, FranchiseEntity,String, FranchiseRepository> {

    protected FranchiseRepositoryAdapter(FranchiseRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Franchise.class) );
    }

    public Mono<Franchise> save(FranchiseEntity franchiseEntity){
        return repository.save(franchiseEntity)
                .doOnSubscribe(subscription -> log.info("[save] Subscribed to the save process for the entity with ID: {}", franchiseEntity.getId()))
                .doOnSuccess(savedEntity -> log.info("[save] Entity successfully saved: {}", savedEntity))
                .doOnError(error -> log.error("[save] Error saving the entity with ID: {}", franchiseEntity.getId(), error))
                .map(this::toFranchise)
                .doOnNext(transaction -> log.info("[save] Successfully transformed to Transaction: {}", transaction));
    }

    private Franchise toFranchise(FranchiseEntity franchiseEntity){
        return Franchise.builder()
                .id(franchiseEntity.getId())
                .name(franchiseEntity.getName())
                .build();
    }

}
