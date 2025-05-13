package co.com.nequi.usecase.product;

import co.com.nequi.model.product.Product;
import co.com.nequi.model.product.gateways.ProductGateway;
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
class ProductUseCaseTest {

    @Mock
    private ProductGateway productGateway;

    private ProductUseCase productUseCase;

    private Product product;

    @BeforeEach
    void setUp() {
        productUseCase = new ProductUseCase(productGateway);
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setBranchId(1L);
        product.setStock(10);
    }

    @Test
    void createProduct_ShouldCreateSuccessfully() {
        when(productGateway.save(any(Product.class)))
                .thenReturn(Mono.just(product));

        StepVerifier.create(productUseCase.createProduct(product))
                .expectNext(product)
                .verifyComplete();

        verify(productGateway).save(product);
    }

    @Test
    void getProductById_ShouldReturnProduct() {
        when(productGateway.findById(anyLong()))
                .thenReturn(Mono.just(product));

        StepVerifier.create(productUseCase.getProductById(1L))
                .expectNext(product)
                .verifyComplete();

        verify(productGateway).findById(1L);
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        when(productGateway.findAll())
                .thenReturn(Flux.just(product));

        StepVerifier.create(productUseCase.getAllProducts())
                .expectNext(product)
                .verifyComplete();

        verify(productGateway).findAll();
    }

    @Test
    void getProductsByBranchId_ShouldReturnProducts() {
        when(productGateway.findByBranchId(anyLong()))
                .thenReturn(Flux.just(product));

        StepVerifier.create(productUseCase.getProductsByBranchId(1L))
                .expectNext(product)
                .verifyComplete();

        verify(productGateway).findByBranchId(1L);
    }

    @Test
    void updateProduct_ShouldUpdateSuccessfully() {
        when(productGateway.update(any(Product.class)))
                .thenReturn(Mono.just(product));

        StepVerifier.create(productUseCase.updateProduct(product))
                .expectNext(product)
                .verifyComplete();

        verify(productGateway).update(product);
    }

    @Test
    void deleteProduct_ShouldDeleteSuccessfully() {
        when(productGateway.deleteById(anyLong()))
                .thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.deleteProduct(1L))
                .verifyComplete();

        verify(productGateway).deleteById(1L);
    }

    @Test
    void updateProductStock_ShouldUpdateStockSuccessfully() {
        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Test Product");
        updatedProduct.setBranchId(1L);
        updatedProduct.setStock(20);

        when(productGateway.findById(anyLong()))
                .thenReturn(Mono.just(product));
        when(productGateway.update(any(Product.class)))
                .thenReturn(Mono.just(updatedProduct));

        StepVerifier.create(productUseCase.updateProductStock(1L, 20))
                .expectNext(updatedProduct)
                .verifyComplete();

        verify(productGateway).findById(1L);
        verify(productGateway).update(any(Product.class));
    }

    @Test
    void getProductsWithHighestStockByBranch_ShouldReturnProducts() {
        when(productGateway.findProductsWithHighestStockByBranch(anyLong()))
                .thenReturn(Flux.just(product));

        StepVerifier.create(productUseCase.getProductsWithHighestStockByBranch(1L))
                .expectNext(product)
                .verifyComplete();

        verify(productGateway).findProductsWithHighestStockByBranch(1L);
    }
}
