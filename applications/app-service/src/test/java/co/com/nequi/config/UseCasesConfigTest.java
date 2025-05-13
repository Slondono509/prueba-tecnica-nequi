package co.com.nequi.config;

import co.com.nequi.model.branch.gateways.BranchGateway;
import co.com.nequi.model.franchise.gateways.FranchiseGateway;
import co.com.nequi.model.product.gateways.ProductGateway;
import co.com.nequi.usecase.branch.BranchUseCase;
import co.com.nequi.usecase.franchise.FranchiseUseCase;
import co.com.nequi.usecase.product.ProductUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UseCasesConfigTest {

    private AnnotationConfigApplicationContext context;

    @InjectMocks
    UseCasesConfig useCasesConfig;

    @Mock
    BranchUseCase branchUseCase;

    @Mock
    FranchiseUseCase franchiseUseCase;

    @Mock
    ProductUseCase productUseCase;

    @Mock
    BranchGateway branchGateway;

    @Mock
    FranchiseGateway franchiseGateway;

    @Mock
    ProductGateway productGateway;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext();
        context.register(TestConfig.class);
        context.refresh();
    }

    @Test
    void shouldLoadUseCaseBeans() {
        BranchUseCase branchUseCase = context.getBean(BranchUseCase.class);
        FranchiseUseCase franchiseUseCase = context.getBean(FranchiseUseCase.class);
        ProductUseCase productUseCase = context.getBean(ProductUseCase.class);

        assertThat(branchUseCase).isNotNull();
        assertThat(franchiseUseCase).isNotNull();
        assertThat(productUseCase).isNotNull();
    }

    @Test
    void shouldNotLoadBeansThatDoNotMatchPattern() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean("someOtherBeanName"));
    }

    @Configuration
    static class TestConfig {

        @Bean
        public BranchUseCase branchUseCase() {
            return new BranchUseCase(mock(BranchGateway.class));
        }

        @Bean
        public FranchiseUseCase franchiseUseCase() {
            return new FranchiseUseCase(mock(FranchiseGateway.class));
        }

        @Bean
        public ProductUseCase productUseCase() {
            return new ProductUseCase(mock(ProductGateway.class));
        }
    }

    @Test
    void shouldCreateBranchUseCaseBean() {
        branchUseCase = useCasesConfig.branchUseCase(branchGateway);
        Assertions.assertNotNull(branchUseCase);
    }

    @Test
    void shouldCreateFranchiseUseCaseBean() {
        franchiseUseCase = useCasesConfig.franchiseUseCase(franchiseGateway);
        Assertions.assertNotNull(franchiseUseCase);
    }

    @Test
    void shouldCreateProductUseCaseBean() {
        productUseCase = useCasesConfig.productUseCase(productGateway);
        Assertions.assertNotNull(productUseCase);
    }
}