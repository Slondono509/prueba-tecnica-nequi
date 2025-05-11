package co.com.nequi.usecase.product;

import co.com.nequi.model.product.Product;
import co.com.nequi.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {
    private final ProductRepository productRepository;

    public Mono<Product> createProduct(Product product) {
        return productRepository.save(product);
    }

    public Mono<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Flux<Product> getProductsByBranchId(Long branchId) {
        return productRepository.findByBranchId(branchId);
    }

    public Mono<Product> updateProduct(Product product) {
        return productRepository.update(product);
    }

    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }

    public Mono<Product> updateProductStock(Long id, Integer newStock) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setStock(newStock);
                    return product;
                })
                .flatMap(productRepository::update);
    }

    public Flux<Product> getProductsWithHighestStockByBranch(Long franchiseId) {
        return productRepository.findProductsWithHighestStockByBranch(franchiseId);
    }
} 