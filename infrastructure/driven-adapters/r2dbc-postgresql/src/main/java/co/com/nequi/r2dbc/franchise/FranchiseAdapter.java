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
            FranchiseEntity franchiseEntity = FranchiseEntity.builder().name(franchise.getName()).build();
            return franchiseRepositoryAdapter.save(franchiseEntity);
        } catch (Exception e) {
            log.error("Error processing saveFranchise {}", e.getMessage());
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Franchise> findById(Long id) {
        return null;
    }

    @Override
    public Flux<Franchise> findAll() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return null;
    }

    @Override
    public Mono<Franchise> update(Franchise franchise) {
        return null;
    }
}
