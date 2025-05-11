package co.com.nequi.usecase.branch;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.branch.gateways.BranchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase {
    private final BranchRepository branchRepository;

    public Mono<Branch> createBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public Mono<Branch> getBranchById(Long id) {
        return branchRepository.findById(id);
    }

    public Flux<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Flux<Branch> getBranchesByFranchiseId(Long franchiseId) {
        return branchRepository.findByFranchiseId(franchiseId);
    }

    public Mono<Branch> updateBranch(Branch branch) {
        return branchRepository.update(branch);
    }

    public Mono<Void> deleteBranch(Long id) {
        return branchRepository.deleteById(id);
    }
} 