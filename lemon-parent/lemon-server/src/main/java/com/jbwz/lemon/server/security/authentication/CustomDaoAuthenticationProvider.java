package com.jbwz.lemon.server.security.authentication;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

public class CustomDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
  // ~ Static fields/initializers
  // =====================================================================================

  /**
   * The plaintext password used to perform  on when the user is not found to avoid SEC-2056.
   */
  private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

  // ~ Instance fields
  // ================================================================================================

  private PasswordEncoder passwordEncoder;

  /**
   * The password used to perform  on when the user is not found to avoid SEC-2056. This is
   * necessary, because some {@link PasswordEncoder} implementations will short circuit if the
   * password is not in a valid format.
   */
  private String userNotFoundEncodedPassword;


  private UserDetailsService userDetailsService;

  public CustomDaoAuthenticationProvider() {
    setHideUserNotFoundExceptions(false);
    setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
  }

  // ~ Methods
  // ========================================================================================================

  protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    if (authentication.getCredentials() == null) {
      this.logger.debug("Authentication failed: no credentials provided");
      throw new BadCredentialsException(this.messages
          .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
              "Bad credentials"));
    } else {
      String presentedPassword = authentication.getCredentials().toString();
      if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
        this.logger.debug("Authentication failed: password does not match stored value");
        throw new BadCredentialsException(this.messages
            .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
                "Bad credentials"));
      }
    }
  }

  protected void doAfterPropertiesSet() throws Exception {
    Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
  }

  protected final UserDetails retrieveUser(String username,
                                           UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    this.prepareTimingAttackProtection();

    try {
      BaseFormLoginService baseFormLoginService = (BaseFormLoginService) this
          .getUserDetailsService();
      UserDetails loadedUser = baseFormLoginService
          .loadUserByToken((FormLoginToken) authentication);
      if (loadedUser == null) {
        throw new InternalAuthenticationServiceException(
            "UserDetailsService returned null, which is an interface contract violation");
      } else {
        return loadedUser;
      }
    } catch (UsernameNotFoundException var4) {
      this.mitigateAgainstTimingAttack(authentication);
      throw var4;
    } catch (InternalAuthenticationServiceException var5) {
      throw var5;
    } catch (Exception var6) {
      throw new InternalAuthenticationServiceException(var6.getMessage(), var6);
    }
  }

  private void prepareTimingAttackProtection() {
    if (this.userNotFoundEncodedPassword == null) {
      this.userNotFoundEncodedPassword = this.passwordEncoder.encode("userNotFoundPassword");
    }

  }

  private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
    if (authentication.getCredentials() != null) {
      String presentedPassword = authentication.getCredentials().toString();
      this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
    }

  }

  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
    this.passwordEncoder = passwordEncoder;
    this.userNotFoundEncodedPassword = null;
  }

  protected PasswordEncoder getPasswordEncoder() {
    return this.passwordEncoder;
  }

  public void setUserDetailsService(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  protected UserDetailsService getUserDetailsService() {
    return this.userDetailsService;
  }
}

