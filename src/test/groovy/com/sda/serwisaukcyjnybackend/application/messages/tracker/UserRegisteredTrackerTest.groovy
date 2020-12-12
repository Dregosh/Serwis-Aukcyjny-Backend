package com.sda.serwisaukcyjnybackend.application.messages.tracker

import com.sda.serwisaukcyjnybackend.domain.message.Message
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository
import com.sda.serwisaukcyjnybackend.domain.message.MessageStatus
import com.sda.serwisaukcyjnybackend.domain.message.MessageType
import com.sda.serwisaukcyjnybackend.domain.user.event.UserRegistered
import spock.lang.Specification
import spock.lang.Subject

class UserRegisteredTrackerTest extends Specification {
    @Subject
    def tracker

    def messageRepository

    def setup() {
        messageRepository = Mock(MessageRepository)
        tracker = new UserRegisteredTracker(messageRepository)
        tracker.guiUrl = "http://localhost:4200"
    }

    def "should track event"() {
        given:
        def event = new UserRegistered("testTest", "test", "test@test")

        when:
        tracker.trackEvent(event)

        then:
        1 * messageRepository.save(_ as Message) >> {Message message ->
            assert message.messageType == MessageType.REGISTER_MESSAGE
            assert message.messageStatus == MessageStatus.CREATED
        }
    }
}
