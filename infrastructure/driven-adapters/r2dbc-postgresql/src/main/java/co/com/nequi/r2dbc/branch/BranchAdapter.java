package co.com.nequi.r2dbc.branch;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.branch.gateways.BranchGateway;
import co.com.nequi.r2dbc.entity.BranchEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class BranchAdapter implements BranchGateway {

    private final BranchRepositoryAdapter branchRepositoryAdapter;

    @Override
    public Mono<Branch> save(Branch branch) {
        try {
            BranchEntity branchEntity = BranchEntity.builder()
                    .name(branch.getName())
                    .franchiseId(branch.getFranchiseId())
                    .build();
            return branchRepositoryAdapter.save(branchEntity);
        } catch (Exception e) {
            log.error("Error processing saveBranch {}", e.getMessage());
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Branch> findById(Long id) {
        try {
            return branchRepositoryAdapter.findById(id)
                    .doOnSuccess(branch -> log.debug("Branch found with id: {}", id))
                    .doOnError(e -> log.error("Error finding branch with id {}: {}", id, e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing findById {}", e.getMessage());
            return Mono.error(e);
        }
    }

    @Override
    public Flux<Branch> findAll() {
        try {
            return branchRepositoryAdapter.findAll()
                    .doOnComplete(() -> log.debug("Completed fetching all branches"))
                    .doOnError(e -> log.error("Error fetching all branches: {}", e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing findAll {}", e.getMessage());
            return Flux.error(e);
        }
    }

    @Override
    public Flux<Branch> findByFranchiseId(Long franchiseId) {
        try {
            return branchRepositoryAdapter.findByFranchiseId(franchiseId)
                    .doOnComplete(() -> log.debug("Completed fetching branches for franchise: {}", franchiseId))
                    .doOnError(e -> log.error("Error fetching branches for franchise {}: {}", franchiseId, e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing findByFranchiseId {}", e.getMessage());
            return Flux.error(e);
        }
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        try {
            return branchRepositoryAdapter.deleteById(id)
                    .doOnSuccess(v -> log.debug("Branch deleted with id: {}", id))
                    .doOnError(e -> log.error("Error deleting branch with id {}: {}", id, e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing deleteById {}", e.getMessage());
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Branch> update(Branch branch) {
        try {
            BranchEntity branchEntity = BranchEntity.builder()
                    .id(branch.getId())
                    .name(branch.getName())
                    .franchiseId(branch.getFranchiseId())
                    .build();
            return branchRepositoryAdapter.update(branchEntity)
                    .doOnSuccess(updated -> log.debug("Branch updated with id: {}", branch.getId()))
                    .doOnError(e -> log.error("Error updating branch with id {}: {}", branch.getId(), e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing update {}", e.getMessage());
            return Mono.error(e);
        }
    }
} 