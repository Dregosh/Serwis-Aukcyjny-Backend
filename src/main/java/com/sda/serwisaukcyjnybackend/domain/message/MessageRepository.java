package com.sda.serwisaukcyjnybackend.domain.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> getAllByMessageStatusOrderByCreatedAtDesc(MessageStatus messageStatus);

    List<Message> findAllByMessageStatus(MessageStatus messageStatus);
}
