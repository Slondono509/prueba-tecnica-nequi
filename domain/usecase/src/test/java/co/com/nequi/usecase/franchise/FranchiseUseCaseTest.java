package co.com.nequi.usecase.franchise;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.gateways.FranchiseGateway;
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
class FranchiseUseCaseTest {

    @Mock
    private FranchiseGateway franchiseGateway;

    private FranchiseUseCase franchiseUseCase;

    private Franchise franchise;

    @BeforeEach
    void setUp() {
        franchiseUseCase = new FranchiseUseCase(franchiseGateway);
        franchise = new Franchise();
        franchise.setId(1L);
        franchise.setName("Test Franchise");
    }

    @Test
    void createFranchise_ShouldCreateSuccessfully() {
        when(franchiseGateway.save(any(Franchise.class)))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseUseCase.createFranchise(franchise))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseGateway).save(franchise);
    }

    @Test
    void getFranchiseById_ShouldReturnFranchise() {
        when(franchiseGateway.findById(anyLong()))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseUseCase.getFranchiseById(1L))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseGateway).findById(1L);
    }

    @Test
    void getAllFranchises_ShouldReturnAllFranchises() {
        when(franchiseGateway.findAll())
                .thenReturn(Flux.just(franchise));

        StepVerifier.create(franchiseUseCase.getAllFranchises())
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseGateway).findAll();
    }

    @Test
    void updateFranchise_ShouldUpdateSuccessfully() {
        when(franchiseGateway.update(any(Franchise.class)))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseUseCase.updateFranchise(franchise))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseGateway).update(franchise);
    }

    @Test
    void deleteFranchise_ShouldDeleteSuccessfully() {
        when(franchiseGateway.deleteById(anyLong()))
                .thenReturn(Mono.empty());

        StepVerifier.create(franchiseUseCase.deleteFranchise(1L))
                .verifyComplete();

        verify(franchiseGateway).deleteById(1L);
    }
}
