package co.com.nequi.api;

import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.product.Product;
import co.com.nequi.usecase.franchise.FranchiseUseCase;
import co.com.nequi.usecase.branch.BranchUseCase;
import co.com.nequi.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class Handler {
    private final FranchiseUseCase franchiseUseCase;
    private final BranchUseCase branchUseCase;
    private final ProductUseCase productUseCase;

    // Franchise handlers
    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        return request.bodyToMono(Franchise.class)
                .flatMap(franchiseUseCase::createFranchise)
                .flatMap(franchise -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(franchise));
    }

    public Mono<ServerResponse> getAllFranchises(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(franchiseUseCase.getAllFranchises(), Franchise.class);
    }

    public Mono<ServerResponse> getFranchiseById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return franchiseUseCase.getFranchiseById(id)
                .flatMap(franchise -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(franchise))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateFranchise(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Franchise.class)
                .map(franchise -> franchise.toBuilder().id(id).build())
                .flatMap(franchiseUseCase::updateFranchise)
                .flatMap(franchise -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(franchise))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteFranchise(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return franchiseUseCase.deleteFranchise(id)
                .then(ServerResponse.noContent().build());
    }

    // Branch handlers
    public Mono<ServerResponse> createBranch(ServerRequest request) {
        return request.bodyToMono(Branch.class)
                .flatMap(branchUseCase::createBranch)
                .flatMap(branch -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(branch));
    }

    public Mono<ServerResponse> getAllBranches(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(branchUseCase.getAllBranches(), Branch.class);
    }

    public Mono<ServerResponse> getBranchById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return branchUseCase.getBranchById(id)
                .flatMap(branch -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(branch))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getBranchesByFranchiseId(ServerRequest request) {
        Long franchiseId = Long.valueOf(request.pathVariable("franchiseId"));
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(branchUseCase.getBranchesByFranchiseId(franchiseId), Branch.class);
    }

    public Mono<ServerResponse> updateBranch(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Branch.class)
                .map(branch -> branch.toBuilder().id(id).build())
                .flatMap(branchUseCase::updateBranch)
                .flatMap(branch -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(branch))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteBranch(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return branchUseCase.deleteBranch(id)
                .then(ServerResponse.noContent().build());
    }

    // Product handlers
    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .flatMap(productUseCase::createProduct)
                .flatMap(product -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(product));
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(productUseCase.getAllProducts(), Product.class);
    }

    public Mono<ServerResponse> getProductById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return productUseCase.getProductById(id)
                .flatMap(product -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(product))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getProductsByBranchId(ServerRequest request) {
        Long branchId = Long.valueOf(request.pathVariable("branchId"));
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(productUseCase.getProductsByBranchId(branchId), Product.class);
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Product.class)
                .map(product -> product.toBuilder().id(id).build())
                .flatMap(productUseCase::updateProduct)
                .flatMap(product -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(product))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return productUseCase.deleteProduct(id)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> updateProductStock(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Integer.class)
                .flatMap(newStock -> productUseCase.updateProductStock(id, newStock))
                .flatMap(product -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(product))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getProductsWithHighestStockByBranch(ServerRequest request) {
        Long franchiseId = Long.valueOf(request.pathVariable("franchiseId"));
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(productUseCase.getProductsWithHighestStockByBranch(franchiseId), Product.class);
    }
}
