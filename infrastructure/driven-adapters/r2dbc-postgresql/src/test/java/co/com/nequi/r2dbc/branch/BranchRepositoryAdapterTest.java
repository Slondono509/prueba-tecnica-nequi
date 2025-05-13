package co.com.nequi.r2dbc.branch;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.r2dbc.entity.BranchEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchRepositoryAdapterTest {

    @Mock
    private BranchRepository repository;

    @Mock
    private ObjectMapper mapper;

    private BranchRepositoryAdapter adapter;

    private BranchEntity branchEntity;
    private Branch branch;

    @BeforeEach
    void setUp() {
        adapter = new BranchRepositoryAdapter(repository, mapper);

        branchEntity = BranchEntity.builder()
                .id(1L)
                .name("Test Branch")
                .franchiseId(1L)
                .build();

        branch = Branch.builder()
                .id(1L)
                .name("Test Branch")
                .franchiseId(1L)
                .build();
    }

    @Test
    void save_ShouldSaveAndTransformEntity() {
        when(repository.save(any(BranchEntity.class))).thenReturn(Mono.just(branchEntity));

        StepVerifier.create(adapter.save(branchEntity))
                .expectNext(branch)
                .verifyComplete();

        verify(repository).save(any(BranchEntity.class));
    }

    @Test
    void findById_ShouldFindAndTransformEntity() {
        when(repository.findById(anyLong())).thenReturn(Mono.just(branchEntity));

        StepVerifier.create(adapter.findById(1L))
                .expectNext(branch)
                .verifyComplete();

        verify(repository).findById(1L);
    }

    @Test
    void findAll_ShouldFindAndTransformAllEntities() {
        when(repository.findAll()).thenReturn(Flux.just(branchEntity));

        StepVerifier.create(adapter.findAll())
                .expectNext(branch)
                .verifyComplete();

        verify(repository).findAll();
    }

    @Test
    void findByFranchiseId_ShouldFindAndTransformEntities() {
        when(repository.findByFranchiseId(anyLong())).thenReturn(Flux.just(branchEntity));

        StepVerifier.create(adapter.findByFranchiseId(1L))
                .expectNext(branch)
                .verifyComplete();

        verify(repository).findByFranchiseId(1L);
    }

    @Test
    void deleteById_ShouldDeleteEntity() {
        when(repository.deleteById(anyLong())).thenReturn(Mono.empty());

        StepVerifier.create(adapter.deleteById(1L))
                .verifyComplete();

        verify(repository).deleteById(1L);
    }

    @Test
    void update_ShouldUpdateAndTransformEntity() {
        when(repository.save(any(BranchEntity.class))).thenReturn(Mono.just(branchEntity));

        StepVerifier.create(adapter.update(branchEntity))
                .expectNext(branch)
                .verifyComplete();

        verify(repository).save(branchEntity);
    }

    @Test
    void save_ShouldHandleError() {
        when(repository.save(any(BranchEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("Error saving")));

        StepVerifier.create(adapter.save(branchEntity))
                .expectError(RuntimeException.class)
                .verify();

        verify(repository).save(any(BranchEntity.class));
    }

    @Test
    void findById_ShouldHandleError() {
        when(repository.findById(anyLong()))
                .thenReturn(Mono.error(new RuntimeException("Error finding")));

        StepVerifier.create(adapter.findById(1L))
                .expectError(RuntimeException.class)
                .verify();

        verify(repository).findById(1L);
    }

    @Test
    void findAll_ShouldHandleError() {
        when(repository.findAll())
                .thenReturn(Flux.error(new RuntimeException("Error finding all")));

        StepVerifier.create(adapter.findAll())
                .expectError(RuntimeException.class)
                .verify();

        verify(repository).findAll();
    }

    @Test
    void findByFranchiseId_ShouldHandleError() {
        when(repository.findByFranchiseId(anyLong()))
                .thenReturn(Flux.error(new RuntimeException("Error finding by franchise")));

        StepVerifier.create(adapter.findByFranchiseId(1L))
                .expectError(RuntimeException.class)
                .verify();

        verify(repository).findByFranchiseId(1L);
    }

    @Test
    void deleteById_ShouldHandleError() {
        when(repository.deleteById(anyLong()))
                .thenReturn(Mono.error(new RuntimeException("Error deleting")));

        StepVerifier.create(adapter.deleteById(1L))
                .expectError(RuntimeException.class)
                .verify();

        verify(repository).deleteById(1L);
    }

    @Test
    void update_ShouldHandleError() {
        when(repository.save(any(BranchEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("Error updating")));

        StepVerifier.create(adapter.update(branchEntity))
                .expectError(RuntimeException.class)
                .verify();

        verify(repository).save(branchEntity);
    }
} 