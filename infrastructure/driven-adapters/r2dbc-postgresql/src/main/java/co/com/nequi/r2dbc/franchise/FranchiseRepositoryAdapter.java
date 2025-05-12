package co.com.nequi.r2dbc.franchise;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.r2dbc.entity.FranchiseEntity;
import co.com.nequi.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Log4j2
public class FranchiseRepositoryAdapter extends ReactiveAdapterOperations<Franchise, FranchiseEntity, Long, FranchiseRepository> {

    protected FranchiseRepositoryAdapter(FranchiseRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Franchise.class));
    }

    public Mono<Franchise> save(FranchiseEntity franchiseEntity) {
        return repository.save(franchiseEntity)
                .doOnSubscribe(subscription -> log.info("[save] Subscribed to the save process for the entity with ID: {}", franchiseEntity.getId()))
                .doOnSuccess(savedEntity -> log.info("[save] Entity successfully saved: {}", savedEntity))
                .doOnError(error -> log.error("[save] Error saving the entity with ID: {}", franchiseEntity.getId(), error))
                .map(this::toFranchise)
                .doOnNext(franchise -> log.info("[save] Successfully transformed to Franchise: {}", franchise));
    }

    public Mono<Franchise> findById(Long id) {
        return repository.findById(id)
                .doOnSubscribe(subscription -> log.info("[findById] Looking for entity with ID: {}", id))
                .doOnSuccess(entity -> log.info("[findById] Entity found: {}", entity))
                .doOnError(error -> log.error("[findById] Error finding entity with ID: {}", id, error))
                .map(this::toFranchise)
                .doOnNext(franchise -> log.info("[findById] Successfully transformed to Franchise: {}", franchise));
    }

    public Flux<Franchise> findAll() {
        return repository.findAll()
                .doOnSubscribe(subscription -> log.info("[findAll] Starting to fetch all entities"))
                .doOnComplete(() -> log.info("[findAll] Completed fetching all entities"))
                .doOnError(error -> log.error("[findAll] Error fetching all entities", error))
                .map(this::toFranchise)
                .doOnNext(franchise -> log.info("[findAll] Transformed entity to Franchise: {}", franchise));
    }

    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id)
                .doOnSubscribe(subscription -> log.info("[deleteById] Starting deletion of entity with ID: {}", id))
                .doOnSuccess(v -> log.info("[deleteById] Successfully deleted entity with ID: {}", id))
                .doOnError(error -> log.error("[deleteById] Error deleting entity with ID: {}", id, error));
    }

    public Mono<Franchise> update(FranchiseEntity franchiseEntity) {
        return repository.save(franchiseEntity)
                .doOnSubscribe(subscription -> log.info("[update] Starting update for entity with ID: {}", franchiseEntity.getId()))
                .doOnSuccess(savedEntity -> log.info("[update] Entity successfully updated: {}", savedEntity))
                .doOnError(error -> log.error("[update] Error updating entity with ID: {}", franchiseEntity.getId(), error))
                .map(this::toFranchise)
                .doOnNext(franchise -> log.info("[update] Successfully transformed to Franchise: {}", franchise));
    }

    private Franchise toFranchise(FranchiseEntity franchiseEntity) {
        return Franchise.builder()
                .id(franchiseEntity.getId())
                .name(franchiseEntity.getName())
                .build();
    }
}
