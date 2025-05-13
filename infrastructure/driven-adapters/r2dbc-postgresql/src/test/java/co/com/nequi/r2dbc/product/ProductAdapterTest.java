package co.com.nequi.r2dbc.product;

import co.com.nequi.model.product.Product;
import co.com.nequi.r2dbc.entity.ProductEntity;
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
class ProductAdapterTest {

    @Mock
    private ProductRepositoryAdapter repositoryAdapter;

    private ProductAdapter adapter;

    private Product product;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        adapter = new ProductAdapter(repositoryAdapter);

        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .stock(10)
                .branchId(1L)
                .build();

        productEntity = ProductEntity.builder()
                .id(1L)
                .name("Test Product")
                .stock(10)
                .branchId(1L)
                .build();
    }

    @Test
    void save_ShouldSaveAndReturnProduct() {
        when(repositoryAdapter.save(any(ProductEntity.class))).thenReturn(Mono.just(product));

        StepVerifier.create(adapter.save(product))
                .expectNext(product)
                .verifyComplete();

        verify(repositoryAdapter).save(any(ProductEntity.class));
    }

    @Test
    void save_ShouldHandleError() {
        when(repositoryAdapter.save(any(ProductEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("Error saving")));

        StepVerifier.create(adapter.save(product))
                .expectError(RuntimeException.class)
                .verify();

        verify(repositoryAdapter).save(any(ProductEntity.class));
    }

    @Test
    void findById_ShouldReturnProduct() {
        when(repositoryAdapter.findById(anyLong())).thenReturn(Mono.just(product));

        StepVerifier.create(adapter.findById(1L))
                .expectNext(product)
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
    void findAll_ShouldReturnAllProducts() {
        when(repositoryAdapter.findAll()).thenReturn(Flux.just(product));

        StepVerifier.create(adapter.findAll())
                .expectNext(product)
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
    void findByBranchId_ShouldReturnProducts() {
        when(repositoryAdapter.findByBranchId(anyLong())).thenReturn(Flux.just(product));

        StepVerifier.create(adapter.findByBranchId(1L))
                .expectNext(product)
                .verifyComplete();

        verify(repositoryAdapter).findByBranchId(1L);
    }

    @Test
    void findByBranchId_ShouldHandleError() {
        when(repositoryAdapter.findByBranchId(anyLong()))
                .thenReturn(Flux.error(new RuntimeException("Error finding by branch")));

        StepVerifier.create(adapter.findByBranchId(1L))
                .expectError(RuntimeException.class)
                .verify();

        verify(repositoryAdapter).findByBranchId(1L);
    }

    @Test
    void findProductsWithHighestStockByBranch_ShouldReturnProducts() {
        when(repositoryAdapter.findProductsWithHighestStockByBranch(anyLong())).thenReturn(Flux.just(product));

        StepVerifier.create(adapter.findProductsWithHighestStockByBranch(1L))
                .expectNext(product)
                .verifyComplete();

        verify(repositoryAdapter).findProductsWithHighestStockByBranch(1L);
    }

    @Test
    void findProductsWithHighestStockByBranch_ShouldHandleError() {
        when(repositoryAdapter.findProductsWithHighestStockByBranch(anyLong()))
                .thenReturn(Flux.error(new RuntimeException("Error finding products with highest stock")));

        StepVerifier.create(adapter.findProductsWithHighestStockByBranch(1L))
                .expectError(RuntimeException.class)
                .verify();

        verify(repositoryAdapter).findProductsWithHighestStockByBranch(1L);
    }

    @Test
    void deleteById_ShouldDeleteProduct() {
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
    void update_ShouldUpdateAndReturnProduct() {
        when(repositoryAdapter.update(any(ProductEntity.class))).thenReturn(Mono.just(product));

        StepVerifier.create(adapter.update(product))
                .expectNext(product)
                .verifyComplete();

        verify(repositoryAdapter).update(any(ProductEntity.class));
    }

    @Test
    void update_ShouldHandleError() {
        when(repositoryAdapter.update(any(ProductEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("Error updating")));

        StepVerifier.create(adapter.update(product))
                .expectError(RuntimeException.class)
                .verify();

        verify(repositoryAdapter).update(any(ProductEntity.class));
    }
} 