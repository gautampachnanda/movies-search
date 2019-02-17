package uk.co.aaditech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("uk.co.aaditech.config")
public class BasicConfiguration extends WebSecurityConfigurerAdapter {
	
	public  BasicConfiguration() {
		super();
		 SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}
	
	 @Override
	    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication()
	            .withUser("admin").password(encoder().encode("adminPass")).roles("ADMIN")
	            .and()
	            .withUser("user").password(encoder().encode("userPass")).roles("USER");
	    }

	    @Override
	    protected void configure(final HttpSecurity http) throws Exception {
	        http.csrf().disable()
	            .authorizeRequests()
	            .and()
	            .exceptionHandling()
	            .and()
	            .authorizeRequests()
	            .antMatchers("/api/csrfAttacker*").permitAll()
	            .antMatchers("/api/customer/**").permitAll()
	            .antMatchers("/api/foos/**").authenticated()
	            .antMatchers("/api/async/**").permitAll()
	            .antMatchers("/api/admin/**").hasRole("ADMIN")
	            .and()
	            .formLogin()
	   
	            .and()
	            .httpBasic()
	            .and()
	            .logout();
	    }
	    
 
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//      throws Exception {
//        auth
//          .inMemoryAuthentication()
//          .withUser("user")
//            .password("password")
//            .roles("USER")
//            .and()
//          .withUser("admin")
//            .password("admin")
//            .roles("USER", "ADMIN");
//    }
// 
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//          .authorizeRequests()
//          .anyRequest()
//          .authenticated()
//          .and()
//          .httpBasic();
//    }
//    
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     *      http
            .authorizeRequests()
                .antMatchers("/", "/home","/programme").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
     */
}
