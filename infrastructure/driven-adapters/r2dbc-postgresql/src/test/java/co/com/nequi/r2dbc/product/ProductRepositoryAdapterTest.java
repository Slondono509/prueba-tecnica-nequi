package co.com.nequi.r2dbc.product;

import co.com.nequi.model.product.Product;
import co.com.nequi.r2dbc.entity.ProductEntity;
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
class ProductRepositoryAdapterTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ObjectMapper mapper;

    private ProductRepositoryAdapter adapter;

    private ProductEntity productEntity;
    private Product product;

    @BeforeEach
    void setUp() {
        adapter = new ProductRepositoryAdapter(repository, mapper);

        productEntity = ProductEntity.builder()
                .id(1L)
                .name("Test Product")
                .stock(10)
                .branchId(1L)
                .build();

        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .stock(10)
                .branchId(1L)
                .build();
    }

    @Test
    void save_ShouldSaveAndTransformEntity() {
        when(repository.save(any(ProductEntity.class))).thenReturn(Mono.just(productEntity));

        StepVerifier.create(adapter.save(productEntity))
                .expectNext(product)
                .verifyComplete();

        verify(repository).save(any(ProductEntity.class));
    }

    @Test
    void findById_ShouldFindAndTransformEntity() {
        when(repository.findById(anyLong())).thenReturn(Mono.just(productEntity));

        StepVerifier.create(adapter.findById(1L))
                .expectNext(product)
                .verifyComplete();

        verify(repository).findById(1L);
    }

    @Test
    void findAll_ShouldFindAndTransformAllEntities() {
        when(repository.findAll()).thenReturn(Flux.just(productEntity));

        StepVerifier.create(adapter.findAll())
                .expectNext(product)
                .verifyComplete();

        verify(repository).findAll();
    }

    @Test
    void findByBranchId_ShouldFindAndTransformEntities() {
        when(repository.findByBranchId(anyLong())).thenReturn(Flux.just(productEntity));

        StepVerifier.create(adapter.findByBranchId(1L))
                .expectNext(product)
                .verifyComplete();

        verify(repository).findByBranchId(1L);
    }

    @Test
    void findProductsWithHighestStockByBranch_ShouldFindAndTransformEntities() {
        when(repository.findProductsWithHighestStockByBranch(anyLong())).thenReturn(Flux.just(productEntity));

        StepVerifier.create(adapter.findProductsWithHighestStockByBranch(1L))
                .expectNext(product)
                .verifyComplete();

        verify(repository).findProductsWithHighestStockByBranch(1L);
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
        when(repository.save(any(ProductEntity.class))).thenReturn(Mono.just(productEntity));

        StepVerifier.create(adapter.update(productEntity))
                .expectNext(product)
                .verifyComplete();

        verify(repository).save(productEntity);
    }

    @Test
    void save_ShouldHandleError() {
        when(repository.save(any(ProductEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("Error saving")));

        StepVerifier.create(adapter.save(productEntity))
                .expectError(RuntimeException.class)
                .verify();

        verify(repository).save(any(ProductEntity.class));
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
    void findByBranchId_ShouldHandleError() {
        when(repository.findByBranchId(anyLong()))
                .thenReturn(Flux.error(new RuntimeException("Error finding by branch")));

        StepVerifier.create(adapter.findByBranchId(1L))
                .expectError(RuntimeException.class)
                .verify();

        verify(repository).findByBranchId(1L);
    }

    @Test
    void findProductsWithHighestStockByBranch_ShouldHandleError() {
        when(repository.findProductsWithHighestStockByBranch(anyLong()))
                .thenReturn(Flux.error(new RuntimeException("Error finding products with highest stock")));

        StepVerifier.create(adapter.findProductsWithHighestStockByBranch(1L))
                .expectError(RuntimeException.class)
                .verify();

        verify(repository).findProductsWithHighestStockByBranch(1L);
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
        when(repository.save(any(ProductEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("Error updating")));

        StepVerifier.create(adapter.update(productEntity))
                .expectError(RuntimeException.class)
                .verify();

        verify(repository).save(productEntity);
    }
} 