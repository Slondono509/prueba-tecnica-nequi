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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
class PostgresqlConnectionConfigurationTest {

    @InjectMocks
    private PostgresqlConnectionConfiguration configuration;

    @Mock
    private FranchiseRepositoryAdapter franchiseRepositoryAdapter;

    @Mock
    private BranchRepositoryAdapter branchRepositoryAdapter;

    @Mock
    private ProductRepositoryAdapter productRepositoryAdapter;

    @Test
    void franchiseGateway_ShouldCreateBean() {        
        FranchiseGateway gateway = configuration.franchiseGateway(franchiseRepositoryAdapter);
        
        assertNotNull(gateway, "El gateway no debería ser null");
        assertTrue(gateway instanceof FranchiseAdapter, "El gateway debería ser una instancia de FranchiseAdapter");
    }

    @Test
    void branchGateway_ShouldCreateBean() {        
        BranchGateway gateway = configuration.branchGateway(branchRepositoryAdapter);
        
        assertNotNull(gateway, "El gateway no debería ser null");
        assertTrue(gateway instanceof BranchAdapter, "El gateway debería ser una instancia de BranchAdapter");
    }

    @Test
    void productGateway_ShouldCreateBean() {        
        ProductGateway gateway = configuration.productGateway(productRepositoryAdapter);
        
        assertNotNull(gateway, "El gateway no debería ser null");
        assertTrue(gateway instanceof ProductAdapter, "El gateway debería ser una instancia de ProductAdapter");
    }
}
