package com.sda.serwisaukcyjnybackend.application.messages.tracker

import com.sda.serwisaukcyjnybackend.domain.message.Message
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository
import com.sda.serwisaukcyjnybackend.domain.message.MessageStatus
import com.sda.serwisaukcyjnybackend.domain.message.MessageType
import com.sda.serwisaukcyjnybackend.domain.user.event.UserEmailVerified
import spock.lang.Specification
import spock.lang.Subject

class UserEmailVerifiedTrackerTest extends Specification {
    @Subject
    def tracker

    def messageRepository

    def setup() {
        messageRepository = Mock(MessageRepository)
        tracker = new UserEmailVerifiedTracker(messageRepository)
    }

    def "should track event"() {
        given:
        def event = new UserEmailVerified("test@test", "test")

        when:
        tracker.trackEvent(event)

        then:
        1 * messageRepository.save(_ as Message) >> { Message message ->
            assert message.messageType == MessageType.INVITE_MESSAGE
            assert message.messageStatus == MessageStatus.CREATED
        }
    }
}
