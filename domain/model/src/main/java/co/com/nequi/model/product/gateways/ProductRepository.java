package co.com.nequi.model.product.gateways;

import co.com.nequi.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> save(Product product);
    Mono<Product> findById(Long id);
    Flux<Product> findAll();
    Flux<Product> findByBranchId(Long branchId);
    Mono<Void> deleteById(Long id);
    Mono<Product> update(Product product);
    Flux<Product> findProductsWithHighestStockByBranch(Long franchiseId);
} 