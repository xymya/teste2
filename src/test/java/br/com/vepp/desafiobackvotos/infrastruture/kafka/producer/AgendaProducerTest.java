package br.com.vepp.desafiobackvotos.infrastruture.kafka.producer;

import br.com.vepp.desafiobackvotos.domain.service.AgendaService;
import br.com.vepp.desafiobackvotos.infrastruture.kafka.models.AgendaResult;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1)
class AgendaProducerTest {

    @Autowired
    private AgendaProducer producer;

    @Value("${api.command.kafka.topic-agenda-name}")
    private String topic;

    @MockBean
    private AgendaService agendaService;

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived()
            throws Exception {
        when(agendaService.findById(anyString())).thenReturn(Optional.of(getAgenda().getAgenda()));

        assertDoesNotThrow(() -> producer.sendResult(getAgenda()));
    }

    private AgendaResult getAgenda() {
        return AgendaResult.builder()
            .agenda(Agenda.builder()
                .id(UUID.randomUUID().toString())
                .description("TESTE")
                .active(true)
                .dtStart(LocalDateTime.now())
                .dtEnd(LocalDateTime.now())
                .build())
            .countVotesYes(2l)
            .countVotesNo(2l)
            .build();
    }
}