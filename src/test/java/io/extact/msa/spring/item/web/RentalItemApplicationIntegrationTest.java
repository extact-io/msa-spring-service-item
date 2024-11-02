package io.extact.msa.spring.item.web;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import io.extact.msa.spring.item.RentalItemApplication;
import io.extact.msa.spring.item.infrastructure.jpa.RentalItemJpaRepositoryConfig;
import io.extact.msa.spring.platform.core.auth.LoginUser;
import io.extact.msa.spring.platform.core.auth.RmsAuthentication;
import io.extact.msa.spring.platform.core.auth.client.LoginUserHeaderRequestInitializer;
import io.extact.msa.spring.platform.fw.infrastructure.external.ErrorMessageDeserializer;
import io.extact.msa.spring.platform.fw.infrastructure.external.RestClientErrorHandler;
import io.extact.msa.spring.test.spring.LocalHostUriBuilderFactory;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles({ "file", "test" })
@TestMethodOrder(OrderAnnotation.class)
public class RentalItemApplicationIntegrationTest {

    private static final RentalItemResponse item1 = new RentalItemResponse(1, "A0001", "レンタル品1号");
    private static final RentalItemResponse item2 = new RentalItemResponse(2, "A0002", "レンタル品2号");
    private static final RentalItemResponse item3 = new RentalItemResponse(3, "A0003", "レンタル品3号");
    private static final RentalItemResponse item4 = new RentalItemResponse(4, "A0004", "レンタル品4号");

    @Autowired
    private RentalItemClient client;

    @Configuration(proxyBeanMethods = false)
    @Import({ RentalItemApplication.class, RentalItemJpaRepositoryConfig.class })
    static class TestConfig {
        @Bean
        RentalItemClient personClient(Environment env) {

            RestClient restClient = RestClient.builder()
                    .uriBuilderFactory(new LocalHostUriBuilderFactory(env))
                    .defaultStatusHandler(new RestClientErrorHandler(new ErrorMessageDeserializer()))
                    .requestInitializer(new LoginUserHeaderRequestInitializer())
                    .build();

            RestClientAdapter adapter = RestClientAdapter.create(restClient);
            HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
            return factory.createClient(RentalItemClient.class);
        }
    }

    @BeforeEach
    void beforeEach() {
       RmsAuthentication testAuth = new TestRmsAuthentication(1, "MEMBER");
       SecurityContextHolder.getContext().setAuthentication(testAuth);
    }

    @Test
    @Order(1)
    void testGetAll() {
        List<RentalItemResponse> expected = List.of(item1, item2, item3, item4);
        List<RentalItemResponse> actual = client.getAll();
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @Order(2)
    void testGetOne() {
        // 該当あり
        RentalItemResponse actual = client.get(3);
        assertThat(actual).isEqualTo(item3);
        // 該当なし
        actual = client.get(999);
        assertThat(actual).isNull();
    }

    @Test
    @Order(3)
    void testAdd() {
        // given
        AddRentalItemRequest request = new AddRentalItemRequest("newNo", "追加アイテム");
        // when
        RentalItemResponse actual = client.add(request);
        // then
        assertThat(actual).isEqualTo(new RentalItemResponse(1000, "newNo", "追加アイテム"));
    }


    // ---------------------------------------------------- inner classes.

    @HttpExchange("/items")
    public interface RentalItemClient {

        @GetExchange
        List<RentalItemResponse> getAll();

        @GetExchange("/{id}")
        RentalItemResponse get(@PathVariable("id") Integer itemId);

        @PostExchange
        RentalItemResponse add(@RequestBody AddRentalItemRequest request);

        @PutExchange
        RentalItemResponse update(@RequestBody UpdateRentalItemRequest request);

        @DeleteExchange("/{id}")
        void delete(@PathVariable("id") Integer itemId);

        @GetExchange("/exists/{id}")
        boolean exists(@PathVariable("id") Integer itemId);
    }

    static class TestRmsAuthentication implements RmsAuthentication {

        private final LoginUser loginUser;

        TestRmsAuthentication(int userId, String role) {
            this.loginUser = LoginUser.of(userId, Set.of(role));
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object getCredentials() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object getDetails() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object getPrincipal() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getName() {
            throw new UnsupportedOperationException();
        }

        @Override
        public LoginUser getLoginUser() {
            return loginUser;
        }

    }
}
