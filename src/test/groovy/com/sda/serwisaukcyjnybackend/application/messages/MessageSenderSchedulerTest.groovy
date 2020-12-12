package com.sda.serwisaukcyjnybackend.application.messages

import com.sda.serwisaukcyjnybackend.domain.message.Message
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository
import com.sda.serwisaukcyjnybackend.domain.message.MessageStatus
import com.sda.serwisaukcyjnybackend.domain.message.MessageType
import org.thymeleaf.ITemplateEngine
import org.thymeleaf.context.IContext
import spock.lang.Specification
import spock.lang.Subject

class MessageSenderSchedulerTest extends Specification {
    @Subject
    def messageSender

    def mailSender
    def messageRepository
    def templateEngine

    def setup() {
        mailSender = Mock(CustomMailSender)
        messageRepository = Mock(MessageRepository)
        templateEngine = Mock(ITemplateEngine)
        messageSender = new MessageSenderScheduler(mailSender, messageRepository, templateEngine)
        messageSender.maxTries = 2
    }

    def "should send message"() {
        given:
        def message = new Message(new HashMap<String, Object>(), getMessageType(), "test@test")
        message.sendTries = tries

        when:
        messageSender.mailSend()

        then:
        1 * messageRepository.getAllByMessageStatusOrderByCreatedAtDesc(MessageStatus.CREATED) >> List.of(message)
        1 * templateEngine.process(_ as String, _ as IContext) >> "processedHtml"
        1 * mailSender.sendMail(_, _, _) >> { exception() }
        1 * messageRepository.save(_ as Message) >> {Message m ->
            assert message.messageStatus == result
        }

        where:
        tries | exception                  || result
        0     | { null }                   || MessageStatus.SEND
        0     | { throw new Exception() }  || MessageStatus.CREATED
        2     | { throw new Exception() }  || MessageStatus.CANCELLED
    }

    def getMessageType() {
        return MessageType.values()[(new Random()).nextInt(MessageType.values().length)];
    }
}
