package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.observation.Observation;
import com.sda.serwisaukcyjnybackend.domain.observation.ObservationRepository;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StopObservingAuctionCommandHandlerTest {
    @Mock
    ObservationRepository observationRepository;

    @InjectMocks
    StopObservingAuctionCommandHandler handler;

    @Test
    void shouldStopObservingAuction() {
        //given
        StopObservingAuctionCommand command = new StopObservingAuctionCommand(1L, 1L);
        Observation observation = createObservation();
        when(observationRepository.getOne(anyLong())).thenReturn(observation);

        //when & then
        handler.handle(command);
    }

    @Test
    void shouldThrowExceptionWhenUserMismatch() {
        //given
        StopObservingAuctionCommand command = new StopObservingAuctionCommand(1L, 2L);
        Observation observation = createObservation();
        when(observationRepository.getOne(anyLong())).thenReturn(observation);

        //when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> handler.handle(command));
    }

    Observation createObservation() {
        Observation observation = new Observation(new Auction(), createUser(1L));
        observation.setId(1L);
        return observation;
    }

    User createUser(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

}
