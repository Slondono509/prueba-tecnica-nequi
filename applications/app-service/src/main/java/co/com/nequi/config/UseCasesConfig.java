package co.com.nequi.config;

import co.com.nequi.model.branch.gateways.BranchGateway;
import co.com.nequi.model.franchise.gateways.FranchiseGateway;
import co.com.nequi.model.product.gateways.ProductGateway;
import co.com.nequi.usecase.branch.BranchUseCase;
import co.com.nequi.usecase.franchise.FranchiseUseCase;
import co.com.nequi.usecase.product.ProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.nequi.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {
    @Bean
    public BranchUseCase branchUseCase(BranchGateway branchGateway) {
        return new BranchUseCase(branchGateway);
    }

    @Bean
    public FranchiseUseCase franchiseUseCase(FranchiseGateway franchiseGateway) {
        return new FranchiseUseCase(franchiseGateway);
    }

    @Bean
    public ProductUseCase productUseCase(ProductGateway productGateway) {
        return new ProductUseCase(productGateway);
    }
        
}
