package com.techvg.covid.care.service.totp;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techvg.covid.care.domain.SecurityUser;
import com.techvg.covid.care.repository.SecurityUserRepository;
import com.techvg.covid.care.service.SMSServices;
import com.techvg.covid.care.service.mapper.SecurityUserMapper;

@Service
@Transactional
public class UserOTPService {

	private SecurityUserRepository securityUserRepository;

	public UserOTPService(SecurityUserRepository securityUserRepository, PasswordEncoder passwordEncoder) {
		this.securityUserRepository = securityUserRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	private int expiryTime = 30 * 60 * 1000 ;// 30 min

	/**
	 * 
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	public String sendOTP(SecurityUser userDetails) throws Exception {
		String otp = generateOneTimePassword();
		SMSServices smsServices = new SMSServices();
		userDetails.setOneTimePassword(encodeOtp(otp));
		long exp = new Date().getTime() + 300000;
		userDetails.setOtpExpiryTime(Instant.now().plusMillis(expiryTime));
		securityUserRepository.save(userDetails);

		return smsServices.sendOtpSMS(SMSConstants.USERID, SMSConstants.PASSWORD, SMSConstants.OTPPRETEXT + otp,
				SMSConstants.SENDERID, userDetails.getMobileNo(), SMSConstants.DEPTKEY, SMSConstants.TEMPLATEID);
	}

	/**
	 * Return true if valid
	 * 
	 * @return
	 */
	public boolean validateOtp(String otp, SecurityUser userDetails) {
		System.out.println(Instant.now());
		System.out.println(userDetails.getOtpExpiryTime());
		if (otp.equals(userDetails.getOneTimePassword()) && Instant.now().isBefore(userDetails.getOtpExpiryTime())) {
			clearOTP(userDetails);
			return true;
		}
		throw new UsernameNotFoundException("Otp is invalid");

	}

	/**
	 * Clear OTP
	 */

	public void clearOTP(SecurityUser userDetail) {
		userDetail.setOneTimePassword(null);
		userDetail.setOtpExpiryTime(null);
		securityUserRepository.save(userDetail);

	}

	/**
	 * encode OTP
	 * 
	 * @param otpString
	 * @return
	 */
	private String encodeOtp(String otpString) {
		if (otpString == null)
			return this.passwordEncoder.encode(otpString);
		return otpString;
	}

	/**
	 * 
	 * @return
	 */
	public static String generateOneTimePassword() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);

		// this will convert any number sequence into 6 character.
		return String.format("%06d", number);
	}

}
