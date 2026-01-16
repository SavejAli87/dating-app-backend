package com.dta.Dating_App.services;

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
    public String verifyOtp(String mobile, String otp) {

        String url = verifyUrl
                + "?mobile=91" + mobile
                + "&otp=" + otp
                + "&authkey=" + authKey;

        String response = restTemplate.getForObject(url, String.class);

        return "OTP Verified \nMSG91 Response: " + response;
    }
}
