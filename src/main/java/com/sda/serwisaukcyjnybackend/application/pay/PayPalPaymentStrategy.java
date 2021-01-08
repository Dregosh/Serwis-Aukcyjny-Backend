package com.sda.serwisaukcyjnybackend.application.pay;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.sda.serwisaukcyjnybackend.config.app.pay.PayPalProperties;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PayPalPaymentStrategy implements PaymentStrategy {
    private static final String PAYMENT_METHOD = "paypal";
    private static final String PAYMENT_INTENT = "sale";
    private static final String APPROVAL_URL = "approval_url";
    private static final String CURRENCY = "PLN";

    private final PayPalProperties payPalProperties;
    private APIContext apiContext;

    @Value("${app.premium.price}")
    private BigDecimal price;

    @PostConstruct
    protected void initialize() {
        apiContext = new APIContext(payPalProperties.getClientId(),
                payPalProperties.getClientSecret(), payPalProperties.getEnvironment());
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        var amount = createAmount();
        var transaction = new Transaction();
        transaction.setAmount(amount);
        var transactions = List.of(transaction);
        var payer = createPayer(request.getUser());
        var redirectUrls = createRedirectUrls();

        var payment = new Payment();
        payment.setIntent(PAYMENT_INTENT);
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        Payment createdPayment;

        try {
            createdPayment = payment.create(apiContext);
        } catch (PayPalRESTException e) {
            throw new PaymentExecutionException("Could not create payment");
        }
        var redirectUrl = createdPayment.getLinks().stream()
                .filter(link -> APPROVAL_URL.equals(link.getRel()))
                .findFirst()
                .map(Links::getHref)
                .orElseThrow(() -> new PaymentExecutionException("Could not get redirect url from PayPal"));
        return new CreateOrderResponse(createdPayment.getId(), redirectUrl);
    }

    @Override
    public CompleteOrderResponse completeOrder(CompleteOrderRequest request) {
        var payment = new Payment();
        payment.setId(request.getPaymentId());

        var paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(request.getPayerId());

        Payment executedPayment;
        try {
            executedPayment = payment.execute(apiContext, paymentExecution);
        } catch (PayPalRESTException e) {
            throw new PaymentExecutionException(String.format("Could not execute payment with id: %s", request.getPaymentId()));
        }
        return new CompleteOrderResponse(executedPayment.getId());
    }

    private Amount createAmount() {
        var amount = new Amount();
        amount.setCurrency(CURRENCY);
        amount.setTotal(price.toString());
        return amount;
    }

    private Payer createPayer(User user) {
        var payer = new Payer();
        var payerInfo = new PayerInfo();
        payerInfo.setEmail(user.getEmail());
        payerInfo.setFirstName(user.getFirstName());
        payerInfo.setLastName(user.getLastName());
        payer.setPayerInfo(payerInfo);
        payer.setPaymentMethod(PAYMENT_METHOD);
        return payer;
    }

    private RedirectUrls createRedirectUrls() {
        var redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(payPalProperties.getSuccessRedirectUrl());
        redirectUrls.setCancelUrl(payPalProperties.getCancelRedirectUrl());
        return redirectUrls;
    }
}
