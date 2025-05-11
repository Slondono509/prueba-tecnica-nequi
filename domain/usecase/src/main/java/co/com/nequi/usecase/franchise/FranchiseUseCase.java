package co.com.nequi.usecase.franchise;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.franchise.gateways.FranchiseRepository;
import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.product.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {
    private final FranchiseRepository franchiseRepository;

    public Mono<Franchise> createFranchise(Franchise franchise) {
        return franchiseRepository.save(franchise);
    }

    public Mono<Franchise> getFranchiseById(Long id) {
        return franchiseRepository.findById(id);
    }

    public Flux<Franchise> getAllFranchises() {
        return franchiseRepository.findAll();
    }

    public Mono<Franchise> updateFranchise(Franchise franchise) {
        return franchiseRepository.update(franchise);
    }

    public Mono<Void> deleteFranchise(Long id) {
        return franchiseRepository.deleteById(id);
    }

    public Mono<Franchise> addBranchToFranchise(String franchiseId, Branch branch) {
        return franchiseRepository.findById(franchiseId)
                .map(franchise -> {
                    franchise.getBranches().add(branch);
                    return franchise;
                })
                .flatMap(franchiseRepository::update);
    }

    public Mono<Franchise> addProductToBranch(String franchiseId, String branchId, Product product) {
        return franchiseRepository.findById(franchiseId)
                .map(franchise -> {
                    franchise.getBranches().stream()
                            .filter(branch -> branch.getId().equals(branchId))
                            .findFirst()
                            .ifPresent(branch -> branch.getProducts().add(product));
                    return franchise;
                })
                .flatMap(franchiseRepository::update);
    }

    public Mono<Franchise> removeProductFromBranch(String franchiseId, String branchId, String productId) {
        return franchiseRepository.findById(franchiseId)
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
                .flatMap(franchiseRepository::update);
    }

    public Mono<Franchise> updateProductStock(String franchiseId, String branchId, String productId, Integer newStock) {
        return franchiseRepository.findById(franchiseId)
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
                .flatMap(franchiseRepository::update);
    }

    public Flux<Product> getProductsWithHighestStockByBranch(String franchiseId) {
        return franchiseRepository.findById(franchiseId)
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
