package co.com.nequi.usecase.franchise;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.gateways.FranchiseGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {
    private final FranchiseGateway franchiseGateway;

    public Mono<Franchise> createFranchise(Franchise franchise) {
        return franchiseGateway.save(franchise);
    }

    public Mono<Franchise> getFranchiseById(Long id) {
        return franchiseGateway.findById(id);
    }

    public Flux<Franchise> getAllFranchises() {
        return franchiseGateway.findAll();
    }

    public Mono<Franchise> updateFranchise(Franchise franchise) {
        return franchiseGateway.update(franchise);
    }

    public Mono<Void> deleteFranchise(Long id) {
        return franchiseGateway.deleteById(id);
    }
}
