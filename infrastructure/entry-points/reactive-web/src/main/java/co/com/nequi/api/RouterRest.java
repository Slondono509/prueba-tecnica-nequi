package co.com.nequi.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    private static final String API_BASE = "/api";
    private static final String FRANCHISES = API_BASE + "/franchises";
    private static final String BRANCHES = API_BASE + "/branches";
    private static final String PRODUCTS = API_BASE + "/products";

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST(FRANCHISES), handler::createFranchise)
                .andRoute(GET(FRANCHISES), handler::getAllFranchises)
                .andRoute(GET(FRANCHISES + "/{id}"), handler::getFranchiseById)
                .andRoute(PUT(FRANCHISES + "/{id}"), handler::updateFranchise)
                .andRoute(DELETE(FRANCHISES + "/{id}"), handler::deleteFranchise)

                .andRoute(POST(BRANCHES), handler::createBranch)
                .andRoute(GET(BRANCHES), handler::getAllBranches)
                .andRoute(GET(BRANCHES + "/{id}"), handler::getBranchById)
                .andRoute(GET(FRANCHISES + "/{franchiseId}/branches"), handler::getBranchesByFranchiseId)
                .andRoute(PUT(BRANCHES + "/{id}"), handler::updateBranch)
                .andRoute(DELETE(BRANCHES + "/{id}"), handler::deleteBranch)

                .andRoute(POST(PRODUCTS), handler::createProduct)
                .andRoute(GET(PRODUCTS), handler::getAllProducts)
                .andRoute(GET(PRODUCTS + "/{id}"), handler::getProductById)
                .andRoute(GET(BRANCHES + "/{branchId}/products"), handler::getProductsByBranchId)
                .andRoute(PUT(PRODUCTS + "/{id}"), handler::updateProduct)
                .andRoute(DELETE(PRODUCTS + "/{id}"), handler::deleteProduct)
                .andRoute(PATCH(PRODUCTS + "/{id}/stock"), handler::updateProductStock)
                .andRoute(GET(FRANCHISES + "/{franchiseId}/highest-stock-products"), handler::getProductsWithHighestStockByBranch);
    }
}
