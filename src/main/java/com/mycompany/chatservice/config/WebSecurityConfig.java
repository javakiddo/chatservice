package com.mycompany.chatservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.mycompany.chatservice.security.RememberMeServices;
import com.mycompany.chatservice.security.RestUnauthorizedEntryPoint;
import com.mycompany.chatservice.security.UserDetailsService;

@Configuration
@EnableWebSecurity

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    public static final String REMEMBER_ME_KEY = "rememberme_key";

    public WebSecurityConfig() {
        super();
        logger.info("loading SecurityConfig ................................................ ");
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RestUnauthorizedEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    private AuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler restAuthenticationFailureHandler;

    @Autowired
    private RememberMeServices rememberMeServices;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/index.html", "/login.html",
                "/partials/**", "/template/**", "/", "/error/**");
    }

    
	

	private static final String SECURE_ADMIN_PASSWORD = "rockandroll";
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .headers().disable()
        .csrf().disable()
        .authorizeRequests()
            .antMatchers("/failure").permitAll()
            .antMatchers("/v2/api-docs").hasAnyAuthority("admin")
            .antMatchers("/users/**").hasAnyAuthority("admin")
            .anyRequest().authenticated()
            .and()
        .exceptionHandling()
            .authenticationEntryPoint(restAuthenticationEntryPoint)
            .accessDeniedHandler(restAccessDeniedHandler)
            .and()
        .formLogin()
            .loginProcessingUrl("/authenticate")
            .successHandler(restAuthenticationSuccessHandler)
            .failureHandler(restAuthenticationFailureHandler)
            .usernameParameter("username")
            .passwordParameter("password")
            .permitAll()
            .and()
        .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
            .deleteCookies("JSESSIONID")
            .permitAll()
            .and()
        .rememberMe()
            .rememberMeServices(rememberMeServices)
            .key(REMEMBER_ME_KEY)
            .and();
		
		
		
//		http
//			.csrf().disable()
//			.formLogin()
//				.loginPage("/index.html")				
//				.defaultSuccessUrl("/chat.html")
//				.permitAll()
//				.and()
//			.logout()
//				.logoutSuccessUrl("/index.html")
//				.permitAll()
//				.and()
//			.authorizeRequests()
//				.antMatchers("/js/**", "/lib/**", "/images/**", "/css/**", "/index.html", "/").permitAll()
//				.antMatchers("/websocket").hasRole("ADMIN")
//				.anyRequest().authenticated();
				
	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		
//		auth.authenticationProvider(new AuthenticationProvider() {
//			
//			@Override
//			public boolean supports(Class<?> authentication) {
//				return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//			}
//			
//			@Override
//			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//				UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
//				
//				List<GrantedAuthority> authorities = SECURE_ADMIN_PASSWORD.equals(token.getCredentials()) ? 
//														AuthorityUtils.createAuthorityList("ROLE_ADMIN") : null;
//														
//				return new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials(), authorities);
//			}
//		});
//	}
}
