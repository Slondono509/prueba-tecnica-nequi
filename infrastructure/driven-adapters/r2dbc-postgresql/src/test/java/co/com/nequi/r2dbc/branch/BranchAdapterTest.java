package co.com.nequi.r2dbc.branch;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.r2dbc.entity.BranchEntity;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchAdapterTest {

    @Mock
    private BranchRepositoryAdapter repositoryAdapter;

    private BranchAdapter adapter;

    private Branch branch;
    private BranchEntity branchEntity;

    @BeforeEach
    void setUp() {
        adapter = new BranchAdapter(repositoryAdapter);

        branch = Branch.builder()
                .id(1L)
                .name("Test Branch")
                .franchiseId(1L)
                .build();

        branchEntity = BranchEntity.builder()
                .id(1L)
                .name("Test Branch")
                .franchiseId(1L)
                .build();
    }

    @Test
    void save_ShouldSaveAndReturnBranch() {
        when(repositoryAdapter.save(any(BranchEntity.class))).thenReturn(Mono.just(branch));

        StepVerifier.create(adapter.save(branch))
                .expectNext(branch)
                .verifyComplete();

        verify(repositoryAdapter).save(any(BranchEntity.class));
    }

    @Test
    void save_ShouldHandleError() {
        when(repositoryAdapter.save(any(BranchEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("Error saving")));

        StepVerifier.create(adapter.save(branch))
                .expectError(RuntimeException.class)
                .verify();

        verify(repositoryAdapter).save(any(BranchEntity.class));
    }

    @Test
    void findById_ShouldReturnBranch() {
        when(repositoryAdapter.findById(anyLong())).thenReturn(Mono.just(branch));

        StepVerifier.create(adapter.findById(1L))
                .expectNext(branch)
                .verifyComplete();

        verify(repositoryAdapter).findById(1L);
    }

    @Test
    void findById_ShouldHandleError() {
        when(repositoryAdapter.findById(anyLong()))
                .thenReturn(Mono.error(new RuntimeException("Error finding")));

        StepVerifier.create(adapter.findById(1L))
                .expectError(RuntimeException.class)
                .verify();

        verify(repositoryAdapter).findById(1L);
    }

    @Test
    void findAll_ShouldReturnAllBranches() {
        when(repositoryAdapter.findAll()).thenReturn(Flux.just(branch));

        StepVerifier.create(adapter.findAll())
                .expectNext(branch)
                .verifyComplete();

        verify(repositoryAdapter).findAll();
    }

    @Test
    void findAll_ShouldHandleError() {
        when(repositoryAdapter.findAll())
                .thenReturn(Flux.error(new RuntimeException("Error finding all")));

        StepVerifier.create(adapter.findAll())
                .expectError(RuntimeException.class)
                .verify();

        verify(repositoryAdapter).findAll();
    }

    @Test
    void findByFranchiseId_ShouldReturnBranches() {
        when(repositoryAdapter.findByFranchiseId(anyLong())).thenReturn(Flux.just(branch));

        StepVerifier.create(adapter.findByFranchiseId(1L))
                .expectNext(branch)
                .verifyComplete();

        verify(repositoryAdapter).findByFranchiseId(1L);
    }

    @Test
    void findByFranchiseId_ShouldHandleError() {
        when(repositoryAdapter.findByFranchiseId(anyLong()))
                .thenReturn(Flux.error(new RuntimeException("Error finding by franchise")));

        StepVerifier.create(adapter.findByFranchiseId(1L))
                .expectError(RuntimeException.class)
                .verify();

        verify(repositoryAdapter).findByFranchiseId(1L);
    }

    @Test
    void deleteById_ShouldDeleteBranch() {
        when(repositoryAdapter.deleteById(anyLong())).thenReturn(Mono.empty());

        StepVerifier.create(adapter.deleteById(1L))
                .verifyComplete();

        verify(repositoryAdapter).deleteById(1L);
    }

    @Test
    void deleteById_ShouldHandleError() {
        when(repositoryAdapter.deleteById(anyLong()))
                .thenReturn(Mono.error(new RuntimeException("Error deleting")));

        StepVerifier.create(adapter.deleteById(1L))
                .expectError(RuntimeException.class)
                .verify();

        verify(repositoryAdapter).deleteById(1L);
    }

    @Test
    void update_ShouldUpdateAndReturnBranch() {
        when(repositoryAdapter.update(any(BranchEntity.class))).thenReturn(Mono.just(branch));

        StepVerifier.create(adapter.update(branch))
                .expectNext(branch)
                .verifyComplete();

        verify(repositoryAdapter).update(any(BranchEntity.class));
    }

    @Test
    void update_ShouldHandleError() {
        when(repositoryAdapter.update(any(BranchEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("Error updating")));

        StepVerifier.create(adapter.update(branch))
                .expectError(RuntimeException.class)
                .verify();

        verify(repositoryAdapter).update(any(BranchEntity.class));
    }
} 