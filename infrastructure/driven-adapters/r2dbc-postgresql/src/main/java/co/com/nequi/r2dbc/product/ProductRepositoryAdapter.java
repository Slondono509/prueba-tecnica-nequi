package co.com.nequi.r2dbc.product;

import co.com.nequi.model.product.Product;
import co.com.nequi.r2dbc.entity.ProductEntity;
import co.com.nequi.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Log4j2
public class ProductRepositoryAdapter extends ReactiveAdapterOperations<Product, ProductEntity, Long, ProductRepository> {

    protected ProductRepositoryAdapter(ProductRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }

    public Mono<Product> save(ProductEntity productEntity) {
        return repository.save(productEntity)
                .doOnSubscribe(subscription -> log.info("[save] Subscribed to the save process for the entity with ID: {}", productEntity.getId()))
                .doOnSuccess(savedEntity -> log.info("[save] Entity successfully saved: {}", savedEntity))
                .doOnError(error -> log.error("[save] Error saving the entity with ID: {}", productEntity.getId(), error))
                .map(this::toProduct)
                .doOnNext(product -> log.info("[save] Successfully transformed to Product: {}", product));
    }

    public Mono<Product> findById(Long id) {
        return repository.findById(id)
                .doOnSubscribe(subscription -> log.info("[findById] Looking for entity with ID: {}", id))
                .doOnSuccess(entity -> log.info("[findById] Entity found: {}", entity))
                .doOnError(error -> log.error("[findById] Error finding entity with ID: {}", id, error))
                .map(this::toProduct)
                .doOnNext(product -> log.info("[findById] Successfully transformed to Product: {}", product));
    }

    public Flux<Product> findAll() {
        return repository.findAll()
                .doOnSubscribe(subscription -> log.info("[findAll] Starting to fetch all entities"))
                .doOnComplete(() -> log.info("[findAll] Completed fetching all entities"))
                .doOnError(error -> log.error("[findAll] Error fetching all entities", error))
                .map(this::toProduct)
                .doOnNext(product -> log.info("[findAll] Transformed entity to Product: {}", product));
    }

    public Flux<Product> findByBranchId(Long branchId) {
        return repository.findByBranchId(branchId)
                .doOnSubscribe(subscription -> log.info("[findByBranchId] Starting to fetch products for branch ID: {}", branchId))
                .doOnComplete(() -> log.info("[findByBranchId] Completed fetching products for branch ID: {}", branchId))
                .doOnError(error -> log.error("[findByBranchId] Error fetching products for branch ID: {}", branchId, error))
                .map(this::toProduct)
                .doOnNext(product -> log.info("[findByBranchId] Transformed entity to Product: {}", product));
    }

    public Flux<Product> findProductsWithHighestStockByBranch(Long franchiseId) {
        return repository.findProductsWithHighestStockByBranch(franchiseId)
                .doOnSubscribe(subscription -> log.info("[findProductsWithHighestStockByBranch] Starting to fetch products for franchise ID: {}", franchiseId))
                .doOnComplete(() -> log.info("[findProductsWithHighestStockByBranch] Completed fetching products for franchise ID: {}", franchiseId))
                .doOnError(error -> log.error("[findProductsWithHighestStockByBranch] Error fetching products for franchise ID: {}", franchiseId, error))
                .map(this::toProduct)
                .doOnNext(product -> log.info("[findProductsWithHighestStockByBranch] Transformed entity to Product: {}", product));
    }

    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id)
                .doOnSubscribe(subscription -> log.info("[deleteById] Starting deletion of entity with ID: {}", id))
                .doOnSuccess(v -> log.info("[deleteById] Successfully deleted entity with ID: {}", id))
                .doOnError(error -> log.error("[deleteById] Error deleting entity with ID: {}", id, error));
    }

    public Mono<Product> update(ProductEntity productEntity) {
        return repository.save(productEntity)
                .doOnSubscribe(subscription -> log.info("[update] Starting update for entity with ID: {}", productEntity.getId()))
                .doOnSuccess(savedEntity -> log.info("[update] Entity successfully updated: {}", savedEntity))
                .doOnError(error -> log.error("[update] Error updating entity with ID: {}", productEntity.getId(), error))
                .map(this::toProduct)
                .doOnNext(product -> log.info("[update] Successfully transformed to Product: {}", product));
    }

    private Product toProduct(ProductEntity productEntity) {
        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .stock(productEntity.getStock())
                .branchId(productEntity.getBranchId())
                .build();
    }
} 