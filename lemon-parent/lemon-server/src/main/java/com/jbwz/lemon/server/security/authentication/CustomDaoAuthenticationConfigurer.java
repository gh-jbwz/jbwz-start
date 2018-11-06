package com.jbwz.lemon.server.security.authentication;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.UserDetailsAwareConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomDaoAuthenticationConfigurer<B extends ProviderManagerBuilder<B>, U extends UserDetailsService>
    extends UserDetailsAwareConfigurer<B, U> {

  private CustomDaoAuthenticationProvider provider;
  private final U userDetailsService;


  /**
   * Creates a new instance
   */
  public CustomDaoAuthenticationConfigurer(U userDetailsService,
      CustomDaoAuthenticationProvider provider) {
    this.userDetailsService = userDetailsService;
    this.provider = provider;
    this.provider.setUserDetailsService(userDetailsService);
  }

  /**
   * Adds an {@link ObjectPostProcessor} for this class.
   *
   * @return the {@link CustomDaoAuthenticationConfigurer} for further customizations
   */
  @SuppressWarnings("unchecked")
  public CustomDaoAuthenticationConfigurer withObjectPostProcessor(
      ObjectPostProcessor<?> objectPostProcessor) {
    addObjectPostProcessor(objectPostProcessor);
    return this;
  }

  /**
   * Allows specifying the {@link PasswordEncoder} to use with the {@link
   * DaoAuthenticationProvider}. The default is to use plain text.
   *
   * @param passwordEncoder The {@link PasswordEncoder} to use.
   */
  public CustomDaoAuthenticationConfigurer passwordEncoder(PasswordEncoder passwordEncoder) {
    provider.setPasswordEncoder(passwordEncoder);
    return this;
  }


  @Override
  public void configure(B builder) {
    provider = postProcess(provider);
    builder.authenticationProvider(provider);
  }

  /**
   * Gets the {@link UserDetailsService} that is used with the {@link DaoAuthenticationProvider}
   *
   * @return the {@link UserDetailsService} that is used with the {@link DaoAuthenticationProvider}
   */
  public U getUserDetailsService() {
    return userDetailsService;
  }
}
