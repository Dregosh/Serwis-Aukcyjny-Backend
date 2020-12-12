package com.sda.serwisaukcyjnybackend.application.auth.register

import com.sda.serwisaukcyjnybackend.application.auth.KeycloakService
import com.sda.serwisaukcyjnybackend.domain.shared.Address
import com.sda.serwisaukcyjnybackend.domain.user.*
import spock.lang.Specification
import spock.lang.Subject

class RegisterUserCommandHandlerTest extends Specification {
    @Subject
    def handler

    def keycloakService
    def userRepository
    def verificationCodeRepository

    def setup() {
        keycloakService = Mock(KeycloakService)
        userRepository = Mock(UserRepository)
        verificationCodeRepository = Mock(VerificationCodeRepository)
        handler = new RegisterUserCommandHandler(keycloakService, userRepository, verificationCodeRepository)
    }

    def "should register user"() {
        given:
        def command = prepareCommand();
        def user = prepareUserFromCommand(command)

        when:
        handler.handle(command)

        then:
        1 * userRepository.save(_) >> user
        1 * keycloakService.addUser(_)
        1 * verificationCodeRepository.save(_)
    }

    static def prepareCommand() {
        def command = new RegisterUserCommand();
        command.address = new Address();
        command.firstName = "test"
        command.lastName = "test"
        command.email = "test@test"
        command.password = new char[] {'a', 's', 'd'}
        return command;
    }

    static def prepareUserFromCommand(RegisterUserCommand command) {
        return new User(command.getEmail(), command.getFirstName(),
                command.getLastName(), command.getDisplayName(),
                command.getAddress(), AccountStatus.ACTIVE, AccountType.NORMAL);
    }
}
