package co.com.nequi.r2dbc.product;

import co.com.nequi.model.product.Product;
import co.com.nequi.model.product.gateways.ProductGateway;
import co.com.nequi.r2dbc.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class ProductAdapter implements ProductGateway {

    private final ProductRepositoryAdapter productRepositoryAdapter;

    @Override
    public Mono<Product> save(Product product) {
        try {
            ProductEntity productEntity = ProductEntity.builder()
                    .name(product.getName())
                    .stock(product.getStock())
                    .branchId(product.getBranchId())
                    .build();
            return productRepositoryAdapter.save(productEntity);
        } catch (Exception e) {
            log.error("Error processing saveProduct {}", e.getMessage());
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Product> findById(Long id) {
        try {
            return productRepositoryAdapter.findById(id)
                    .doOnSuccess(product -> log.debug("Product found with id: {}", id))
                    .doOnError(e -> log.error("Error finding product with id {}: {}", id, e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing findById {}", e.getMessage());
            return Mono.error(e);
        }
    }

    @Override
    public Flux<Product> findAll() {
        try {
            return productRepositoryAdapter.findAll()
                    .doOnComplete(() -> log.debug("Completed fetching all products"))
                    .doOnError(e -> log.error("Error fetching all products: {}", e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing findAll {}", e.getMessage());
            return Flux.error(e);
        }
    }

    @Override
    public Flux<Product> findByBranchId(Long branchId) {
        try {
            return productRepositoryAdapter.findByBranchId(branchId)
                    .doOnComplete(() -> log.debug("Completed fetching products for branch: {}", branchId))
                    .doOnError(e -> log.error("Error fetching products for branch {}: {}", branchId, e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing findByBranchId {}", e.getMessage());
            return Flux.error(e);
        }
    }

    @Override
    public Flux<Product> findProductsWithHighestStockByBranch(Long franchiseId) {
        try {
            return productRepositoryAdapter.findProductsWithHighestStockByBranch(franchiseId)
                    .doOnComplete(() -> log.debug("Completed fetching products with highest stock for franchise: {}", franchiseId))
                    .doOnError(e -> log.error("Error fetching products with highest stock for franchise {}: {}", franchiseId, e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing findProductsWithHighestStockByBranch {}", e.getMessage());
            return Flux.error(e);
        }
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        try {
            return productRepositoryAdapter.deleteById(id)
                    .doOnSuccess(v -> log.debug("Product deleted with id: {}", id))
                    .doOnError(e -> log.error("Error deleting product with id {}: {}", id, e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing deleteById {}", e.getMessage());
            return Mono.error(e);
        }
    }

    @Override
    public Mono<Product> update(Product product) {
        try {
            ProductEntity productEntity = ProductEntity.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .stock(product.getStock())
                    .branchId(product.getBranchId())
                    .build();
            return productRepositoryAdapter.update(productEntity)
                    .doOnSuccess(updated -> log.debug("Product updated with id: {}", product.getId()))
                    .doOnError(e -> log.error("Error updating product with id {}: {}", product.getId(), e.getMessage()));
        } catch (Exception e) {
            log.error("Error processing update {}", e.getMessage());
            return Mono.error(e);
        }
    }
} 