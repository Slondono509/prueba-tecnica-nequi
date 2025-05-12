package co.com.nequi.model.branch.gateways;

import co.com.nequi.model.branch.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchGateway {
    Mono<Branch> save(Branch branch);
    Mono<Branch> findById(Long id);
    Flux<Branch> findAll();
    Flux<Branch> findByFranchiseId(Long franchiseId);
    Mono<Void> deleteById(Long id);
    Mono<Branch> update(Branch branch);
} 