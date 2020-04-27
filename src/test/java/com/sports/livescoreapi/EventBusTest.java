package com.sports.livescoreapi;

import com.sports.livescoreapi.events.Event;
import com.sports.livescoreapi.events.MatchStartedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventBusTest {

    private EventRepository eventStore;

    private final String USER_ID = "user_x";
    private final String VERSION = "1";

    @BeforeEach
    void setUp() {
        eventStore = mock(EventRepository.class);
        when(eventStore.save(any(Event.class))).thenReturn(null);
    }

    @Test
    void persist_events() {
        EventBus eventBus = new EventBus(eventStore);

        MatchStartedEvent startedEvent = new MatchStartedEvent(UUID.randomUUID(), LocalDateTime.now(), USER_ID, VERSION,
                LocalDateTime.now(), Team.of("PALMEIRAS"), Team.of("CORINTHIANS"));

        eventBus.post(startedEvent);

        verify(eventStore, times(1)).save(any(MatchStartedEvent.class));
    }
}