package com.sda.serwisaukcyjnybackend.application.messages

import com.sda.serwisaukcyjnybackend.domain.message.Message
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository
import com.sda.serwisaukcyjnybackend.domain.message.MessageStatus
import spock.lang.Specification
import spock.lang.Subject

class MessageCleanerTest extends Specification {
    @Subject
    def cleaner

    def messageRepository

    def setup() {
        messageRepository = Mock(MessageRepository)
        cleaner = new MessageCleaner(messageRepository)
    }

    def "should clean messages"() {
        given:
        def message = new Message()

        when:
        cleaner.cleanOldMessages()

        then:
        1 * messageRepository.findAllByMessageStatus(MessageStatus.SEND) >> List.of(message)
        1 * messageRepository.deleteAll(_)
    }


}
