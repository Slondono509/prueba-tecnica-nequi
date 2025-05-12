package co.com.nequi.r2dbc.franchise;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.gateways.FranchiseGateway;
import co.com.nequi.r2dbc.entity.FranchiseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class FranchiseAdapter implements FranchiseGateway {

    private final FranchiseRepositoryAdapter franchiseRepositoryAdapter;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        try {
            FranchiseEntity franchiseEntity = FranchiseEntity.builder()
                    .name(franchise.getName())
                    .build();
            return franchiseRepositoryAdapter.save(franchiseEntity);
        } catch (Exception e) {
            log.error("Error processing saveFranchise {}", e.getMessage());
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Franchise> findById(Long id) {
        try {
            return franchiseRepositoryAdapter.findById(id)
                    .doOnSuccess(franchise -> log.debug("Franchise found with id: {}", id))
                    .doOnError(e -> log.error("Error finding franchise with id {}: {}", id, e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing findById {}", e.getMessage());
            return Mono.error(e);
        }
    }

    @Override
    public Flux<Franchise> findAll() {
        try {
            return franchiseRepositoryAdapter.findAll()
                    .doOnComplete(() -> log.debug("Completed fetching all franchises"))
                    .doOnError(e -> log.error("Error fetching all franchises: {}", e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing findAll {}", e.getMessage());
            return Flux.error(e);
        }
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        try {
            return franchiseRepositoryAdapter.deleteById(id)
                    .doOnSuccess(v -> log.debug("Franchise deleted with id: {}", id))
                    .doOnError(e -> log.error("Error deleting franchise with id {}: {}", id, e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing deleteById {}", e.getMessage());
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Franchise> update(Franchise franchise) {
        try {
            FranchiseEntity franchiseEntity = FranchiseEntity.builder()
                    .id(franchise.getId())
                    .name(franchise.getName())
                    .build();
            return franchiseRepositoryAdapter.update(franchiseEntity)
                    .doOnSuccess(updated -> log.debug("Franchise updated with id: {}", franchise.getId()))
                    .doOnError(e -> log.error("Error updating franchise with id {}: {}", franchise.getId(), e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing update {}", e.getMessage());
            return Mono.error(e);
        }
    }
}
