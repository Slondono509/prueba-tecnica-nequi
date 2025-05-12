package co.com.nequi.r2dbc.config;

import co.com.nequi.model.branch.gateways.BranchGateway;
import co.com.nequi.model.franchise.gateways.FranchiseGateway;
import co.com.nequi.model.product.gateways.ProductGateway;
import co.com.nequi.r2dbc.branch.BranchAdapter;
import co.com.nequi.r2dbc.branch.BranchRepositoryAdapter;
import co.com.nequi.r2dbc.franchise.FranchiseAdapter;
import co.com.nequi.r2dbc.franchise.FranchiseRepositoryAdapter;
import co.com.nequi.r2dbc.product.ProductAdapter;
import co.com.nequi.r2dbc.product.ProductRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
public class PostgresqlConnectionConfiguration {

    @Bean
    public FranchiseGateway franchiseGateway(FranchiseRepositoryAdapter franchiseRepositoryAdapter) {
        return new FranchiseAdapter(franchiseRepositoryAdapter);
    }

    @Bean
    public BranchGateway branchGateway(BranchRepositoryAdapter branchRepositoryAdapter) {
        return new BranchAdapter(branchRepositoryAdapter);
    }

    @Bean
    public ProductGateway productGateway(ProductRepositoryAdapter productRepositoryAdapter) {
        return new ProductAdapter(productRepositoryAdapter);
    }
} 