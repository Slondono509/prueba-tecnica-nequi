package co.com.nequi.api;

import co.com.nequi.model.branch.Branch;
import co.com.nequi.model.franchise.Franchise;
import co.com.nequi.model.product.Product;
import co.com.nequi.usecase.branch.BranchUseCase;
import co.com.nequi.usecase.franchise.FranchiseUseCase;
import co.com.nequi.usecase.product.ProductUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@Import({BranchUseCase.class, FranchiseUseCase.class, ProductUseCase.class})
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private BranchUseCase branchUseCase;

    @MockitoBean
    private FranchiseUseCase franchiseUseCase;

    @MockitoBean
    private ProductUseCase productUseCase;

    private static final String API_BASE = "/api";
    private static final String FRANCHISES = API_BASE + "/franchises";
    private static final String BRANCHES = API_BASE + "/branches";
    private static final String PRODUCTS = API_BASE + "/products";

    @Test
    void testCreateFranchise() {
        Franchise franchise = new Franchise();
        when(franchiseUseCase.createFranchise(any(Franchise.class)))
            .thenReturn(Mono.just(franchise));

        webTestClient.post()
                .uri(FRANCHISES)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(franchise)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Franchise.class);
    }

    @Test
    void testGetAllFranchises() {
        when(franchiseUseCase.getAllFranchises())
            .thenReturn(Flux.just(new Franchise()));

        webTestClient.get()
                .uri(FRANCHISES)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Franchise.class);
    }

    @Test
    void testGetFranchiseById() {
        Long franchiseId = 1L;
        when(franchiseUseCase.getFranchiseById(anyLong()))
            .thenReturn(Mono.just(new Franchise()));

        webTestClient.get()
                .uri(FRANCHISES + "/{id}", franchiseId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Franchise.class);
    }

    @Test
    void testUpdateFranchise() {
        Long franchiseId = 1L;
        Franchise franchise = new Franchise();
        when(franchiseUseCase.updateFranchise(any(Franchise.class)))
            .thenReturn(Mono.just(franchise));

        webTestClient.put()
                .uri(FRANCHISES + "/{id}", franchiseId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(franchise)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Franchise.class);
    }

    @Test
    void testDeleteFranchise() {
        Long franchiseId = 1L;
        when(franchiseUseCase.deleteFranchise(anyLong()))
            .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri(FRANCHISES + "/{id}", franchiseId)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testCreateBranch() {
        Branch branch = new Branch();
        when(branchUseCase.createBranch(any(Branch.class)))
            .thenReturn(Mono.just(branch));

        webTestClient.post()
                .uri(BRANCHES)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(branch)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Branch.class);
    }

    @Test
    void testGetAllBranches() {
        when(branchUseCase.getAllBranches())
            .thenReturn(Flux.just(new Branch()));

        webTestClient.get()
                .uri(BRANCHES)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Branch.class);
    }

    @Test
    void testGetBranchById() {
        Long branchId = 1L;
        when(branchUseCase.getBranchById(anyLong()))
            .thenReturn(Mono.just(new Branch()));

        webTestClient.get()
                .uri(BRANCHES + "/{id}", branchId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Branch.class);
    }

    @Test
    void testGetBranchesByFranchiseId() {
        Long franchiseId = 1L;
        when(branchUseCase.getBranchesByFranchiseId(anyLong()))
            .thenReturn(Flux.just(new Branch()));

        webTestClient.get()
                .uri(FRANCHISES + "/{franchiseId}/branches", franchiseId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Branch.class);
    }

    @Test
    void testUpdateBranch() {
        Long branchId = 1L;
        Branch branch = new Branch();
        when(branchUseCase.updateBranch(any(Branch.class)))
            .thenReturn(Mono.just(branch));

        webTestClient.put()
                .uri(BRANCHES + "/{id}", branchId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(branch)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Branch.class);
    }

    @Test
    void testDeleteBranch() {
        Long branchId = 1L;
        when(branchUseCase.deleteBranch(anyLong()))
            .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri(BRANCHES + "/{id}", branchId)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        when(productUseCase.createProduct(any(Product.class)))
            .thenReturn(Mono.just(product));

        webTestClient.post()
                .uri(PRODUCTS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class);
    }

    @Test
    void testGetAllProducts() {
        when(productUseCase.getAllProducts())
            .thenReturn(Flux.just(new Product()));

        webTestClient.get()
                .uri(PRODUCTS)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class);
    }

    @Test
    void testGetProductById() {
        Long productId = 1L;
        when(productUseCase.getProductById(anyLong()))
            .thenReturn(Mono.just(new Product()));

        webTestClient.get()
                .uri(PRODUCTS + "/{id}", productId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class);
    }

    @Test
    void testGetProductsByBranchId() {
        Long branchId = 1L;
        when(productUseCase.getProductsByBranchId(anyLong()))
            .thenReturn(Flux.just(new Product()));

        webTestClient.get()
                .uri(BRANCHES + "/{branchId}/products", branchId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class);
    }

    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        Product product = new Product();
        when(productUseCase.updateProduct(any(Product.class)))
            .thenReturn(Mono.just(product));

        webTestClient.put()
                .uri(PRODUCTS + "/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class);
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;
        when(productUseCase.deleteProduct(anyLong()))
            .thenReturn(Mono.empty());

        webTestClient.delete()
                .uri(PRODUCTS + "/{id}", productId)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testUpdateProductStock() {
        Long productId = 1L;
        Integer newStock = 10;
        when(productUseCase.updateProductStock(anyLong(), any(Integer.class)))
            .thenReturn(Mono.just(new Product()));

        webTestClient.patch()
                .uri(PRODUCTS + "/{id}/stock", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newStock)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class);
    }

    @Test
    void testGetProductsWithHighestStockByBranch() {
        Long franchiseId = 1L;
        when(productUseCase.getProductsWithHighestStockByBranch(anyLong()))
            .thenReturn(Flux.just(new Product()));

        webTestClient.get()
                .uri(FRANCHISES + "/{franchiseId}/highest-stock-products", franchiseId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class);
    }
}
