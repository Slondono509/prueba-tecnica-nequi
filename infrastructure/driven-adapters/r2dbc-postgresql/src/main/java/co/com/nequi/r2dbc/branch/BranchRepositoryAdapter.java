package co.com.nequi.r2dbc.branch;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.r2dbc.entity.BranchEntity;
import co.com.nequi.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Log4j2
public class BranchRepositoryAdapter extends ReactiveAdapterOperations<Branch, BranchEntity, Long, BranchRepository> {

    protected BranchRepositoryAdapter(BranchRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Branch.class));
    }

    public Mono<Branch> save(BranchEntity branchEntity) {
        return repository.save(branchEntity)
                .doOnSubscribe(subscription -> log.info("[save] Subscribed to the save process for the entity with ID: {}", branchEntity.getId()))
                .doOnSuccess(savedEntity -> log.info("[save] Entity successfully saved: {}", savedEntity))
                .doOnError(error -> log.error("[save] Error saving the entity with ID: {}", branchEntity.getId(), error))
                .map(this::toBranch)
                .doOnNext(branch -> log.info("[save] Successfully transformed to Branch: {}", branch));
    }

    public Mono<Branch> findById(Long id) {
        return repository.findById(id)
                .doOnSubscribe(subscription -> log.info("[findById] Looking for entity with ID: {}", id))
                .doOnSuccess(entity -> log.info("[findById] Entity found: {}", entity))
                .doOnError(error -> log.error("[findById] Error finding entity with ID: {}", id, error))
                .map(this::toBranch)
                .doOnNext(branch -> log.info("[findById] Successfully transformed to Branch: {}", branch));
    }

    public Flux<Branch> findAll() {
        return repository.findAll()
                .doOnSubscribe(subscription -> log.info("[findAll] Starting to fetch all entities"))
                .doOnComplete(() -> log.info("[findAll] Completed fetching all entities"))
                .doOnError(error -> log.error("[findAll] Error fetching all entities", error))
                .map(this::toBranch)
                .doOnNext(branch -> log.info("[findAll] Transformed entity to Branch: {}", branch));
    }

    public Flux<Branch> findByFranchiseId(Long franchiseId) {
        return repository.findByFranchiseId(franchiseId)
                .doOnSubscribe(subscription -> log.info("[findByFranchiseId] Starting to fetch branches for franchise ID: {}", franchiseId))
                .doOnComplete(() -> log.info("[findByFranchiseId] Completed fetching branches for franchise ID: {}", franchiseId))
                .doOnError(error -> log.error("[findByFranchiseId] Error fetching branches for franchise ID: {}", franchiseId, error))
                .map(this::toBranch)
                .doOnNext(branch -> log.info("[findByFranchiseId] Transformed entity to Branch: {}", branch));
    }

    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id)
                .doOnSubscribe(subscription -> log.info("[deleteById] Starting deletion of entity with ID: {}", id))
                .doOnSuccess(v -> log.info("[deleteById] Successfully deleted entity with ID: {}", id))
                .doOnError(error -> log.error("[deleteById] Error deleting entity with ID: {}", id, error));
    }

    public Mono<Branch> update(BranchEntity branchEntity) {
        return repository.save(branchEntity)
                .doOnSubscribe(subscription -> log.info("[update] Starting update for entity with ID: {}", branchEntity.getId()))
                .doOnSuccess(savedEntity -> log.info("[update] Entity successfully updated: {}", savedEntity))
                .doOnError(error -> log.error("[update] Error updating entity with ID: {}", branchEntity.getId(), error))
                .map(this::toBranch)
                .doOnNext(branch -> log.info("[update] Successfully transformed to Branch: {}", branch));
    }

    private Branch toBranch(BranchEntity branchEntity) {
        return Branch.builder()
                .id(branchEntity.getId())
                .name(branchEntity.getName())
                .franchiseId(branchEntity.getFranchiseId())
                .build();
    }
} 