package com.sda.serwisaukcyjnybackend.application.auth.verification

import com.sda.serwisaukcyjnybackend.application.auth.KeycloakService
import com.sda.serwisaukcyjnybackend.application.auth.exception.InvalidVerificationCodeException
import com.sda.serwisaukcyjnybackend.domain.user.User
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCode
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCodeRepository
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification
import spock.lang.Subject

class VerifyUserCommandHandlerTest extends Specification {
    @Subject
    def handler

    def keycloakService
    def verificationCodeRepository
    def eventPublisher

    def setup() {
        keycloakService = Mock(KeycloakService)
        verificationCodeRepository = Mock(VerificationCodeRepository)
        eventPublisher = Mock(ApplicationEventPublisher)
        handler = new VerifyUserCommandHandler(verificationCodeRepository, keycloakService, eventPublisher)
    }

    def "should verify user"() {
        given:
        def command = new VerifyUserCommand(UUID.randomUUID().toString())
        def verificationCode = new VerificationCode(new User())

        when:
        handler.handle(command)

        then:
        1 * verificationCodeRepository.findByCode(_) >> Optional.of(verificationCode)
        1 * keycloakService.verifyUserEmail(_)
        1 * verificationCodeRepository.delete(_)
        1 * eventPublisher.publishEvent(_)
    }

    def "should throw invalid verification code" () {
        given:
        def command = new VerifyUserCommand(UUID.randomUUID().toString())

        when:
        handler.handle(command)

        then:
        1 * verificationCodeRepository.findByCode(_) >> Optional.empty()
        InvalidVerificationCodeException exception = thrown()
    }
}
