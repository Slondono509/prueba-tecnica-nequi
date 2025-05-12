package co.com.nequi.usecase.product;

import co.com.nequi.model.product.Product;
import co.com.nequi.model.product.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {
    private final ProductGateway productGateway;

    public Mono<Product> createProduct(Product product) {
        return productGateway.save(product);
    }

    public Mono<Product> getProductById(Long id) {
        return productGateway.findById(id);
    }

    public Flux<Product> getAllProducts() {
        return productGateway.findAll();
    }

    public Flux<Product> getProductsByBranchId(Long branchId) {
        return productGateway.findByBranchId(branchId);
    }

    public Mono<Product> updateProduct(Product product) {
        return productGateway.update(product);
    }

    public Mono<Void> deleteProduct(Long id) {
        return productGateway.deleteById(id);
    }

    public Mono<Product> updateProductStock(Long id, Integer newStock) {
        return productGateway.findById(id)
                .map(product -> {
                    product.setStock(newStock);
                    return product;
                })
                .flatMap(productGateway::update);
    }

    public Flux<Product> getProductsWithHighestStockByBranch(Long franchiseId) {
        return productGateway.findProductsWithHighestStockByBranch(franchiseId);
    }
} 