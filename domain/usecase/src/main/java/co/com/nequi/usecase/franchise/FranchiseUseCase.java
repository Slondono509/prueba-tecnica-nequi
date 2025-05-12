package co.com.nequi.usecase.franchise;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.gateways.FranchiseGateway;
import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.product.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {
    private final FranchiseGateway franchiseGateway;

    public Mono<Franchise> createFranchise(Franchise franchise) {
        return franchiseGateway.save(franchise);
    }

    public Mono<Franchise> getFranchiseById(Long id) {
        return franchiseGateway.findById(id);
    }

    public Flux<Franchise> getAllFranchises() {
        return franchiseGateway.findAll();
    }

    public Mono<Franchise> updateFranchise(Franchise franchise) {
        return franchiseGateway.update(franchise);
    }

    public Mono<Void> deleteFranchise(Long id) {
        return franchiseGateway.deleteById(id);
    }

    public Mono<Franchise> addBranchToFranchise(Long franchiseId, Branch branch) {
        return franchiseGateway.findById(franchiseId)
                .map(franchise -> {
                    franchise.getBranches().add(branch);
                    return franchise;
                })
                .flatMap(franchiseGateway::update);
    }

    public Mono<Franchise> addProductToBranch(Long franchiseId, String branchId, Product product) {
        return franchiseGateway.findById(franchiseId)
                .map(franchise -> {
                    franchise.getBranches().stream()
                            .filter(branch -> branch.getId().equals(branchId))
                            .findFirst()
                            .ifPresent(branch -> branch.getProducts().add(product));
                    return franchise;
                })
                .flatMap(franchiseGateway::update);
    }

    public Mono<Franchise> removeProductFromBranch(Long franchiseId, String branchId, String productId) {
        return franchiseGateway.findById(franchiseId)
                .map(franchise -> {
                    franchise.getBranches().stream()
                            .filter(branch -> branch.getId().equals(branchId))
                            .findFirst()
                            .ifPresent(branch -> 
                                branch.setProducts(
                                    branch.getProducts().stream()
                                        .filter(product -> !product.getId().equals(productId))
                                        .toList()
                                )
                            );
                    return franchise;
                })
                .flatMap(franchiseGateway::update);
    }

    public Mono<Franchise> updateProductStock(Long franchiseId, String branchId, String productId, Integer newStock) {
        return franchiseGateway.findById(franchiseId)
                .map(franchise -> {
                    franchise.getBranches().stream()
                            .filter(branch -> branch.getId().equals(branchId))
                            .findFirst()
                            .ifPresent(branch -> 
                                branch.getProducts().stream()
                                    .filter(product -> product.getId().equals(productId))
                                    .findFirst()
                                    .ifPresent(product -> product.setStock(newStock))
                            );
                    return franchise;
                })
                .flatMap(franchiseGateway::update);
    }

    public Flux<Product> getProductsWithHighestStockByBranch(Long franchiseId) {
        return franchiseGateway.findById(franchiseId)
                .flatMapMany(franchise -> 
                    Flux.fromIterable(franchise.getBranches())
                        .map(branch -> 
                            branch.getProducts().stream()
                                .max((p1, p2) -> p1.getStock().compareTo(p2.getStock()))
                                .orElse(null)
                        )
                        .filter(product -> product != null)
                );
    }
}
