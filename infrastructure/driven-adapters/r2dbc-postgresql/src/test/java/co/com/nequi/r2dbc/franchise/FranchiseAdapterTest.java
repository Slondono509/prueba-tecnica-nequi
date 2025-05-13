package co.com.nequi.r2dbc.franchise;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.r2dbc.entity.FranchiseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FranchiseAdapterTest {

    @Mock
    private FranchiseRepositoryAdapter franchiseRepositoryAdapter;

    @InjectMocks
    private FranchiseAdapter franchiseAdapter;

    private Franchise franchise;
    private FranchiseEntity franchiseEntity;

    @BeforeEach
    void setUp() {
        franchise = Franchise.builder()
                .id(1L)
                .name("Test Franchise")
                .build();

        franchiseEntity = FranchiseEntity.builder()
                .id(1L)
                .name("Test Franchise")
                .build();
    }

    @Test
    void save_ShouldSaveSuccessfully() {
        when(franchiseRepositoryAdapter.save(any(FranchiseEntity.class)))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseAdapter.save(franchise))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseRepositoryAdapter).save(any(FranchiseEntity.class));
    }

    @Test
    void save_ShouldHandleError() {
        when(franchiseRepositoryAdapter.save(any(FranchiseEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("Error saving")));

        StepVerifier.create(franchiseAdapter.save(franchise))
                .expectError(RuntimeException.class)
                .verify();

        verify(franchiseRepositoryAdapter).save(any(FranchiseEntity.class));
    }

    @Test
    void findById_ShouldReturnFranchise() {
        when(franchiseRepositoryAdapter.findById(anyLong()))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseAdapter.findById(1L))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseRepositoryAdapter).findById(1L);
    }

    @Test
    void findById_ShouldHandleError() {
        when(franchiseRepositoryAdapter.findById(anyLong()))
                .thenReturn(Mono.error(new RuntimeException("Error finding")));

        StepVerifier.create(franchiseAdapter.findById(1L))
                .expectError(RuntimeException.class)
                .verify();

        verify(franchiseRepositoryAdapter).findById(1L);
    }

    @Test
    void findAll_ShouldReturnAllFranchises() {
        when(franchiseRepositoryAdapter.findAll())
                .thenReturn(Flux.just(franchise));

        StepVerifier.create(franchiseAdapter.findAll())
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseRepositoryAdapter).findAll();
    }

    @Test
    void findAll_ShouldHandleError() {
        when(franchiseRepositoryAdapter.findAll())
                .thenReturn(Flux.error(new RuntimeException("Error finding all")));

        StepVerifier.create(franchiseAdapter.findAll())
                .expectError(RuntimeException.class)
                .verify();

        verify(franchiseRepositoryAdapter).findAll();
    }

    @Test
    void deleteById_ShouldDeleteSuccessfully() {
        when(franchiseRepositoryAdapter.deleteById(anyLong()))
                .thenReturn(Mono.empty());

        StepVerifier.create(franchiseAdapter.deleteById(1L))
                .verifyComplete();

        verify(franchiseRepositoryAdapter).deleteById(1L);
    }

    @Test
    void deleteById_ShouldHandleError() {
        when(franchiseRepositoryAdapter.deleteById(anyLong()))
                .thenReturn(Mono.error(new RuntimeException("Error deleting")));

        StepVerifier.create(franchiseAdapter.deleteById(1L))
                .expectError(RuntimeException.class)
                .verify();

        verify(franchiseRepositoryAdapter).deleteById(1L);
    }

    @Test
    void update_ShouldUpdateSuccessfully() {
        when(franchiseRepositoryAdapter.update(any(FranchiseEntity.class)))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseAdapter.update(franchise))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseRepositoryAdapter).update(any(FranchiseEntity.class));
    }

    @Test
    void update_ShouldHandleError() {
        when(franchiseRepositoryAdapter.update(any(FranchiseEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("Error updating")));

        StepVerifier.create(franchiseAdapter.update(franchise))
                .expectError(RuntimeException.class)
                .verify();

        verify(franchiseRepositoryAdapter).update(any(FranchiseEntity.class));
    }
} 