package ru.apolyakov.video_calls.api_gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import ru.apolyakov.video_calls.api_gateway.security.CustomSavedRequestAwareAuthenticationSuccessHandler;
import ru.apolyakov.video_calls.api_gateway.security.Http401UnauthorizedEntryPoint;
import ru.apolyakov.video_calls.api_gateway.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/actuator/*"),
            new AntPathRequestMatcher("/login", "POST"),
            new AntPathRequestMatcher("/api/auth/register", "POST"),
            new AntPathRequestMatcher("/api/auth/login", "POST") // this must be public
    );
    private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);


    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private Http401UnauthorizedEntryPoint restAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CustomSavedRequestAwareAuthenticationSuccessHandler customSuccessHandler() {
        return new CustomSavedRequestAwareAuthenticationSuccessHandler();
    }

    // return 401 instead 302
    @Bean
    public SimpleUrlAuthenticationFailureHandler customFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling()
//                    .authenticationEntryPoint(restAuthenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler)
                    .defaultAuthenticationEntryPointFor(new Http403ForbiddenEntryPoint(), PROTECTED_URLS)
                .and()
                    .authorizeRequests().anyRequest().authenticated()
                .and()
                    .cors()
                .and()
                    .anonymous().disable()
                    .rememberMe().disable()
                    .csrf().disable()
                    .formLogin()
                        .loginPage("/login").permitAll()
                        .successHandler(customSuccessHandler())
                        .failureHandler(customFailureHandler())
                .and()
//                    .httpBasic().disable()
                    .logout().disable();
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().requestMatchers(PUBLIC_URLS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userService)
            .passwordEncoder(passwordEncoder());
    }
}
