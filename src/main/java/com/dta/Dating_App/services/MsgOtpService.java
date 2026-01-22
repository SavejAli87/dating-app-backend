package com.dta.Dating_App.services;

import com.dta.Dating_App.entitys.User;
import com.dta.Dating_App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MsgOtpService {

    @Value("${msg91.authkey}")
    private String authKey;

    @Value("${msg91.template_id}")
    private String templateId;

    @Value("${msg91.url.send}")
    private String sendUrl;

    @Value("${msg91.url.verify}")
    private String verifyUrl;

    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    //  SEND OTP
    public String sendOtp(String mobile) {

        String url = sendUrl
                + "?template_id=" + templateId
                + "&mobile=91" + mobile
                + "&authkey=" + authKey;

        String response = restTemplate.getForObject(url, String.class);

        return "OTP Sent Successfully \nMSG91 Response: " + response;
    }

    //  VERIFY OTP
    public String verifyOtp(Long userId,String mobile, String otp) {

        String url = verifyUrl
                + "?mobile=91" + mobile
                + "&otp=" + otp
                + "&authkey=" + authKey;

        String response = restTemplate.getForObject(url, String.class);


        // success check (simple)
        boolean verified = response != null && response.toLowerCase().contains("success");

        if (verified) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found "));

            user.setMobile(mobile);
            userRepository.save(user);

            return "OTP Verified  Mobile Saved \nMSG91 Response: " + response;
        }

        return "OTP Verified \nMSG91 Response: " + response;
    }
}
