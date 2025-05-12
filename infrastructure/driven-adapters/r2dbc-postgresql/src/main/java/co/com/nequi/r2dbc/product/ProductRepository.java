package co.com.nequi.r2dbc.product;

import co.com.nequi.r2dbc.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {
    
    Flux<ProductEntity> findByBranchId(Long branchId);

    @Query("""
            WITH RankedProducts AS (
                SELECT p.*,
                       RANK() OVER (PARTITION BY p.branch_id ORDER BY p.stock DESC) as stock_rank
                FROM products p
                INNER JOIN branches b ON p.branch_id = b.id
                WHERE b.franchise_id = :franchiseId
            )
            SELECT id, name, stock, branch_id
            FROM RankedProducts
            WHERE stock_rank = 1
            """)
    Flux<ProductEntity> findProductsWithHighestStockByBranch(Long franchiseId);
} 