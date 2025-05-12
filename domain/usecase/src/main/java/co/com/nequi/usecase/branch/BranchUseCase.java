package co.com.nequi.usecase.branch;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.branch.gateways.BranchGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase {
    private final BranchGateway branchGateway;

    public Mono<Branch> createBranch(Branch branch) {
        return branchGateway.save(branch);
    }

    public Mono<Branch> getBranchById(Long id) {
        return branchGateway.findById(id);
    }

    public Flux<Branch> getAllBranches() {
        return branchGateway.findAll();
    }

    public Flux<Branch> getBranchesByFranchiseId(Long franchiseId) {
        return branchGateway.findByFranchiseId(franchiseId);
    }

    public Mono<Branch> updateBranch(Branch branch) {
        return branchGateway.update(branch);
    }

    public Mono<Void> deleteBranch(Long id) {
        return branchGateway.deleteById(id);
    }
} 