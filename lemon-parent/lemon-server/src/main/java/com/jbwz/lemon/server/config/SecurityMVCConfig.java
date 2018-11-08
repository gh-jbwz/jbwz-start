package com.jbwz.lemon.server.config;

import com.jbwz.lemon.server.security.authorization.PathMatchVoter;
import com.jbwz.lemon.server.security.filter.LogAccessFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.*;

@EnableWebSecurity
@Configuration
public class SecurityMVCConfig extends WebSecurityConfigurerAdapter {

    public static final Set<String> ANYONE_ACCESS_URL = new HashSet<>();
    public static final Set<String> STATIC_RESOURCES_URL = new HashSet<>();

    private static AntPathMatcher matcher = new AntPathMatcher();
    @Autowired
    UserDetailsService userDetailsService;

    static {
//  these urls  can access for ANYONE
        STATIC_RESOURCES_URL.add("/favicon.ico");
        STATIC_RESOURCES_URL.add("/image/**");
        STATIC_RESOURCES_URL.add("/img/**");
        STATIC_RESOURCES_URL.add("/css/**");
        STATIC_RESOURCES_URL.add("/js/**");
        STATIC_RESOURCES_URL.add("/webjars/**");
        STATIC_RESOURCES_URL.add("/html/**");
        STATIC_RESOURCES_URL.add("/plugins/**");
        ANYONE_ACCESS_URL.addAll(STATIC_RESOURCES_URL);
        ANYONE_ACCESS_URL.add("/login-error");
        ANYONE_ACCESS_URL.add("/logout-success");
        ANYONE_ACCESS_URL.add("/login");
        ANYONE_ACCESS_URL.add("/error");
        ANYONE_ACCESS_URL.add("/user/findMobileNo");
        ANYONE_ACCESS_URL.add("/user/regSave");
    }

    public static boolean isNotStaticUrl(String path) {
        for (String s : STATIC_RESOURCES_URL) {
            if (matcher.match(s, path)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
        decisionVoters.add(new PathMatchVoter());
        AffirmativeBased affirmativeBased = new AffirmativeBased(decisionVoters);
        http
                .csrf().disable()
                .addFilterBefore(new LogAccessFilter(), LogoutFilter.class)
                .authorizeRequests()
                .accessDecisionManager(affirmativeBased)
                .antMatchers(collectionToArray(ANYONE_ACCESS_URL)).permitAll()//静态资源可以随便访问
                .anyRequest().authenticated() //任何url都要登陆后才能访问
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/login-success")
                .failureForwardUrl("/login-error").permitAll()
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/logout-success").permitAll()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private String[] collectionToArray(Collection<String> collection) {
        if (!CollectionUtils.isEmpty(collection)) {
            String[] a = new String[collection.size()];
            int i = 0;
            for (String s : collection) {
                a[i] = s;
                i++;
            }
            return a;
        }
        return null;
    }
}
