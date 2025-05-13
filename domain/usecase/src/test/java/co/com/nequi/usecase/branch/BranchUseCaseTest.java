package co.com.nequi.usecase.branch;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.branch.gateways.BranchGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchUseCaseTest {

    @Mock
    private BranchGateway branchGateway;

    private BranchUseCase branchUseCase;

    private Branch branch;

    @BeforeEach
    void setUp() {
        branchUseCase = new BranchUseCase(branchGateway);
        branch = new Branch();
        branch.setId(1L);
        branch.setName("Test Branch");
        branch.setFranchiseId(1L);
    }

    @Test
    void createBranch_ShouldCreateSuccessfully() {
        when(branchGateway.save(any(Branch.class)))
                .thenReturn(Mono.just(branch));

        StepVerifier.create(branchUseCase.createBranch(branch))
                .expectNext(branch)
                .verifyComplete();

        verify(branchGateway).save(branch);
    }

    @Test
    void getBranchById_ShouldReturnBranch() {
        when(branchGateway.findById(anyLong()))
                .thenReturn(Mono.just(branch));

        StepVerifier.create(branchUseCase.getBranchById(1L))
                .expectNext(branch)
                .verifyComplete();

        verify(branchGateway).findById(1L);
    }

    @Test
    void getAllBranches_ShouldReturnAllBranches() {
        when(branchGateway.findAll())
                .thenReturn(Flux.just(branch));

        StepVerifier.create(branchUseCase.getAllBranches())
                .expectNext(branch)
                .verifyComplete();

        verify(branchGateway).findAll();
    }

    @Test
    void getBranchesByFranchiseId_ShouldReturnBranches() {
        when(branchGateway.findByFranchiseId(anyLong()))
                .thenReturn(Flux.just(branch));

        StepVerifier.create(branchUseCase.getBranchesByFranchiseId(1L))
                .expectNext(branch)
                .verifyComplete();

        verify(branchGateway).findByFranchiseId(1L);
    }

    @Test
    void updateBranch_ShouldUpdateSuccessfully() {
        when(branchGateway.update(any(Branch.class)))
                .thenReturn(Mono.just(branch));

        StepVerifier.create(branchUseCase.updateBranch(branch))
                .expectNext(branch)
                .verifyComplete();

        verify(branchGateway).update(branch);
    }

    @Test
    void deleteBranch_ShouldDeleteSuccessfully() {
        when(branchGateway.deleteById(anyLong()))
                .thenReturn(Mono.empty());

        StepVerifier.create(branchUseCase.deleteBranch(1L))
                .verifyComplete();

        verify(branchGateway).deleteById(1L);
    }
}
