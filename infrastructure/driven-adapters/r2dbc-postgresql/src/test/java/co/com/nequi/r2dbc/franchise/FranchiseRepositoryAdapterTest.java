package co.com.nequi.r2dbc.franchise;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.r2dbc.entity.FranchiseEntity;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FranchiseRepositoryAdapterTest {

    @Mock
    private FranchiseRepository repository;

    @Mock
    private ObjectMapper mapper;

    private FranchiseRepositoryAdapter adapter;

    private FranchiseEntity franchiseEntity;
    private Franchise franchise;

    @BeforeEach
    void setUp() {
        adapter = new FranchiseRepositoryAdapter(repository, mapper);

        franchiseEntity = FranchiseEntity.builder()
                .id(1L)
                .name("Test Franchise")
                .build();

        franchise = Franchise.builder()
                .id(1L)
                .name("Test Franchise")
                .build();
    }

    @Test
    void save_ShouldSaveAndTransformEntity() {
        when(repository.save(any(FranchiseEntity.class))).thenReturn(Mono.just(franchiseEntity));

        StepVerifier.create(adapter.save(franchiseEntity))
                .expectNext(franchise)
                .verifyComplete();

        verify(repository).save(any(FranchiseEntity.class));
    }

    @Test
    void findById_ShouldFindAndTransformEntity() {
        when(repository.findById(anyLong())).thenReturn(Mono.just(franchiseEntity));

        StepVerifier.create(adapter.findById(1L))
                .expectNext(franchise)
                .verifyComplete();

        verify(repository).findById(1L);
    }

    @Test
    void findAll_ShouldFindAndTransformAllEntities() {
        when(repository.findAll()).thenReturn(Flux.just(franchiseEntity));

        StepVerifier.create(adapter.findAll())
                .expectNext(franchise)
                .verifyComplete();

        verify(repository).findAll();
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
        when(repository.save(any(FranchiseEntity.class))).thenReturn(Mono.just(franchiseEntity));

        StepVerifier.create(adapter.update(franchiseEntity))
                .expectNext(franchise)
                .verifyComplete();

        verify(repository).save(franchiseEntity);
    }

    @Test
    void save_ShouldHandleError() {
        when(repository.save(any(FranchiseEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("Error saving")));

        StepVerifier.create(adapter.save(franchiseEntity))
                .expectError(RuntimeException.class)
                .verify();

        verify(repository).save(any(FranchiseEntity.class));
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
        when(repository.save(any(FranchiseEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("Error updating")));

        StepVerifier.create(adapter.update(franchiseEntity))
                .expectError(RuntimeException.class)
                .verify();

        verify(repository).save(franchiseEntity);
    }
} 