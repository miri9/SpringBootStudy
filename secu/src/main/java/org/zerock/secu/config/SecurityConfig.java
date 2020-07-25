package org.zerock.secu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.secu.service.SecuCheckService;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new SecuCheckService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* 5.0 부터 패스워드 인코딩 설정 필요. 아래와 같이 커스텀 서비스로 대체할 수 있다.
        auth.inMemoryAuthentication().withUser("M90").password("$2a$10$z6mTEnBvL4NVH6jupxTl8eL/ZNEf.Bd2I4JY14/mAo4N7Z7yQqQhW")
        .roles("MEMBER");*/

        auth.userDetailsService(userDetailsService()); // 커스텀 서비스 호출

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("configure....................");
        log.info("configure....................");

        // ROLE_ => 부트 에선 사용 X
        http.authorizeRequests().antMatchers("/sample/all").permitAll();
        http.authorizeRequests().antMatchers("/sample/member").hasRole("MEMBER");
        http.authorizeRequests().antMatchers("/sample/admin").hasRole("ADMIN");

        //로그인 페이지
        http.formLogin();

        // csrf disable
        http.csrf().disable();

        
    }
    
    

}