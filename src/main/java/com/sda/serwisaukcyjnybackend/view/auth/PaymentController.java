package com.sda.serwisaukcyjnybackend.view.auth;

import com.sda.serwisaukcyjnybackend.application.auth.premium.CancelPremiumAccountOrder;
import com.sda.serwisaukcyjnybackend.application.auth.premium.CompletePremiumAccountOrder;
import com.sda.serwisaukcyjnybackend.application.auth.premium.CreatePremiumAccountOrder;
import com.sda.serwisaukcyjnybackend.application.command.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import static com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService.getLoggedUser;

@RestController
@RequestMapping("api/pay")
@RequiredArgsConstructor
public class PaymentController {
    private final CommandDispatcher commandDispatcher;

    @GetMapping("/buy-premium-link")
    public String getBuyUrl() {
        return commandDispatcher.handle(new CreatePremiumAccountOrder(getLoggedUser().getUserId())).getPayload();
    }

    @GetMapping("/success")
    public RedirectView completeOrder(@RequestParam("paymentId") String paymentId,
                                      @RequestParam("PayerID") String payerId) {
        var url = commandDispatcher.handle(new CompletePremiumAccountOrder(payerId, paymentId)).getPayload();
        return new RedirectView(url);
    }

    @GetMapping("/cancel")
    public RedirectView cancelOrder(@RequestParam("paymentId") String paymentId) {
        var url = commandDispatcher.handle(new CancelPremiumAccountOrder(paymentId)).getPayload();
        return new RedirectView(url);
    }

}
