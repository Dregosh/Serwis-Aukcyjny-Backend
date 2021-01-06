package com.sda.serwisaukcyjnybackend.config.app.pay;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("pay.paypal")
public class PayPalProperties {
    private String clientId;
    private String clientSecret;
    private String environment;
    private String successRedirectUrl;
    private String cancelRedirectUrl;
}
