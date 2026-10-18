package nemsi.aistock.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.SecureRandom;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LocalJwtConfigurationTest {

    private ApplicationContextRunner contextRunner(String propertiesFile) {
        return new ApplicationContextRunner()
                .withInitializer(new ConfigDataApplicationContextInitializer())
                .withInitializer(context -> {
                    // 실행 환경의 키가 검증에 섞이지 않도록 분리
                    context.getEnvironment().getPropertySources()
                            .remove(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME);
                    context.getEnvironment().getPropertySources()
                            .remove(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME);
                })
                .withPropertyValues("spring.config.location=" + propertiesFile)
                .withBean(PropertySourcesPlaceholderConfigurer.class)
                .withBean(UserDetailsService.class, () -> mock(UserDetailsService.class))
                .withUserConfiguration(JwtTokenProvider.class);
    }

    @Test
    void localKeyIsStableWithinContextAndChangesAfterRestart() {
        contextRunner("classpath:application-local.properties").run(first -> {
            assertThat(first).hasNotFailed();
            JwtTokenProvider provider = first.getBean(JwtTokenProvider.class);
            String token = provider.createToken("local-user");
            assertThat(provider.validateToken(token)).isTrue();
            assertThat(provider.getUserPk(token)).isEqualTo("local-user");

            contextRunner("classpath:application-local.properties").run(second -> {
                assertThat(second).hasNotFailed();
                JwtTokenProvider restarted = second.getBean(JwtTokenProvider.class);
                assertThat(restarted.validateToken(token)).isFalse();
                assertThat(restarted.validateToken(restarted.createToken("local-user"))).isTrue();
            });
            assertThat(provider.validateToken(token)).isTrue();
        });
    }

    @Test
    void explicitSecretIsUsedAcrossContexts() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        String externalSecret = Base64.getEncoder().encodeToString(bytes);
        ApplicationContextRunner runner = contextRunner("classpath:application-local.properties")
                .withPropertyValues("JWT_SECRET=" + externalSecret);
        runner.run(first -> {
            assertThat(first).hasNotFailed();
            String token = first.getBean(JwtTokenProvider.class).createToken("local-user");
            runner.run(second -> {
                assertThat(second).hasNotFailed();
                assertThat(second.getBean(JwtTokenProvider.class).validateToken(token)).isTrue();
            });
        });
    }

    @Test
    void defaultConfigurationRequiresExternalSecret() {
        // 테스트용 동명 파일 대신 기본 실행 설정 확인
        contextRunner("file:src/main/resources/application.properties").run(context -> {
            assertThat(context).hasFailed();
            assertThat(context.getStartupFailure()).hasStackTraceContaining("JWT_SECRET");
        });
    }
}
