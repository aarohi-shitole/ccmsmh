package com.techvg.covid.care.web.rest;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techvg.covid.care.captcha.CaptchaValidator;
import com.techvg.covid.care.domain.SecurityUser;
import com.techvg.covid.care.repository.SecurityUserRepository;
import com.techvg.covid.care.security.jwt.JWTFilter;
import com.techvg.covid.care.security.jwt.PasswordDecryption;
import com.techvg.covid.care.security.jwt.TokenProvider;
import com.techvg.covid.care.service.totp.UserOTPService;
import com.techvg.covid.care.web.rest.vm.JWTToken;
import com.techvg.covid.care.web.rest.vm.LoginVM;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

	private final TokenProvider tokenProvider;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final SecurityUserRepository repo;

	private final UserOTPService userOTPService;

	private PasswordDecryption passDecryption;

	@Autowired
	CaptchaValidator captchaValidator;

	public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
			SecurityUserRepository repo, UserOTPService userOTPService) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.repo = repo;
		this.userOTPService = userOTPService;
	}

	// -----------------------------------------------------------------------------------------
	// Already used in Mobile in prod so duplicated for web from this method below
	// Without captcha for mobile application
	@PostMapping("/authenticate")
	public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
//		PasswordDecryption passDecryption = new PasswordDecryption();
//		String decryptPassword;
//		try {
//			decryptPassword = passDecryption.decrypt(loginVM);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);
//		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginVM.getUsername(), loginVM.getPassword());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		if (authentication.isAuthenticated()) {
			Optional<SecurityUser> securityUser = repo.findOneByLogin(loginVM.getUsername());
			if (loginVM.getOtpNumber() == null) {
				try {
					userOTPService.sendOTP(securityUser.get());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new UsernameNotFoundException(
							"User " + loginVM.getUsername() + " was not found in the system.");
				}
				return new ResponseEntity<>(new JWTToken(null, securityUser.get().getMobileNo()), null, HttpStatus.OK);
			} else if (loginVM.getOtpNumber() != null) {
				userOTPService.validateOtp(loginVM.getOtpNumber(), securityUser.get());
			}
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
		String jwt = tokenProvider.createToken(authentication, rememberMe);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

		this.tokenProvider.myHash.put(loginVM.getUsername(), jwt);

		return new ResponseEntity<>(new JWTToken(jwt, null), httpHeaders, HttpStatus.OK);

	}

	// -----------------------------------------------------------------
	// For web
	@PostMapping("/authenticateweb")
	public ResponseEntity<JWTToken> authorizeWeb(@Valid @RequestBody LoginVM loginVM) {

		if (loginVM.getOtpNumber()==null && !captchaValidator.isValidCaptcha(loginVM.getCaptacha())) {
			throw new UsernameNotFoundException("Invalid captcha");
		}
		
		PasswordDecryption passDecryption = new PasswordDecryption();
		String decryptPassword;
		try {
			decryptPassword = passDecryption.decrypt(loginVM);
		} catch (Exception e) {
			return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginVM.getUsername(), decryptPassword);
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		if (authentication.isAuthenticated()) {
			Optional<SecurityUser> securityUser = repo.findOneByLogin(loginVM.getUsername());
			if (loginVM.getOtpNumber() == null) {
				try {
					userOTPService.sendOTP(securityUser.get());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new UsernameNotFoundException(
							"User " + loginVM.getUsername() + " was not found in the system.");
				}
				return new ResponseEntity<>(new JWTToken(null, securityUser.get().getMobileNo()), null,
						HttpStatus.OK);
			} else if (loginVM.getOtpNumber() != null) {
				userOTPService.validateOtp(loginVM.getOtpNumber(), securityUser.get());
			}
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
		String jwt = tokenProvider.createToken(authentication, rememberMe);
		this.tokenProvider.myHash.put(loginVM.getUsername(), jwt);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		return new ResponseEntity<>(new JWTToken(jwt, null), httpHeaders, HttpStatus.OK);
	}
	// -------------------------------------------------------------------

	@PostMapping("/logout")
	public ResponseEntity<Authentication> fetchSignoutSite(HttpServletRequest request, HttpServletResponse response,
			@Valid @RequestBody LoginVM loginVM) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {

			try {

				if (!StringUtils.isEmpty(loginVM.getUsername())
						&& tokenProvider.myHash.containsKey(loginVM.getUsername())) {
					tokenProvider.myHash.remove(loginVM.getUsername());

				} else {
					throw new UsernameNotFoundException("Invalid UserName");
				}

			} catch (Exception e) {
				throw new UsernameNotFoundException("Invalid UserName");
			}

			new SecurityContextLogoutHandler().logout(request, response, auth);
			// SecurityContextHolder.getContext().setAuthentication(auth);
			// boolean rememberMe = false;
			// tokenProvider.createToken(auth, rememberMe);
		}

		return new ResponseEntity<>(null, null, HttpStatus.OK);
	}

	@PostMapping("/authenticateBackend")
	public ResponseEntity<JWTToken> authorizeBackend(@Valid @RequestBody LoginVM loginVM) {

//		PasswordDecryption passDecryption = new PasswordDecryption();
//		String decryptPassword;
//		try {
//			decryptPassword = passDecryption.decrypt(loginVM);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);
//		}
//
//		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//				loginVM.getUsername(), decryptPassword);

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginVM.getUsername(), loginVM.getPassword());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
		String jwt = tokenProvider.createToken(authentication, rememberMe);
		this.tokenProvider.myHash.put(loginVM.getUsername(), jwt);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		return new ResponseEntity<>(new JWTToken(jwt, null), httpHeaders, HttpStatus.OK);

	}

	// ---------------------------------------------------------------------------------
	@PostMapping("/authenticateBackendWeb")
	public ResponseEntity<JWTToken> authorizeBackendWeb(@Valid @RequestBody LoginVM loginVM) {
		if (captchaValidator.isValidCaptcha(loginVM.getCaptacha())) {
//		PasswordDecryption passDecryption = new PasswordDecryption();
//		String decryptPassword;
//		try {
//			decryptPassword = passDecryption.decrypt(loginVM);
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);
//		}
//
//		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//				loginVM.getUsername(), decryptPassword);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					loginVM.getUsername(), loginVM.getPassword());

			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
			String jwt = tokenProvider.createToken(authentication, rememberMe);
			this.tokenProvider.myHash.put(loginVM.getUsername(), jwt);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
			return new ResponseEntity<>(new JWTToken(jwt, null), httpHeaders, HttpStatus.OK);

		} else {
			throw new UsernameNotFoundException("Invalid captcha");
		}
	}

	@PostMapping("/isAuthenticated")
	public ResponseEntity<Authentication> isAuthenticated(@Valid @RequestBody JWTToken token) {
		if (this.tokenProvider.validateToken(token.getIdToken())) {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + token.getIdToken());
			return new ResponseEntity<>(this.tokenProvider.getAuthentication(token.getIdToken()), httpHeaders,
					HttpStatus.OK);
		}
		return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);
	}

//	/**
//	 * Object to return as body in JWT Authentication.
//	 */
//	public class JWTToken {
//
//		@NotNull
//		private String idToken;
//		private String mobileNo;
//
//		JWTToken(String idToken, String mobileNo) {
//			this.idToken = idToken;
//			this.mobileNo = mobileNo;
//		}
//
//		@JsonProperty("id_token")
//		String getIdToken() {
//			return idToken;
//		}
//
//		void setIdToken(String idToken) {
//			this.idToken = idToken;
//		}
//
//		@JsonProperty("mobile_no")
//		String getMobileNo() {
//			return mobileNo;
//		}
//
//		void setMobileNo(String mobileNo) {
//			this.mobileNo = mobileNo;
//		}
//	}
}
