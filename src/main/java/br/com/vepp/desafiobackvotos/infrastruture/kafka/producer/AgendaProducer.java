package br.com.vepp.desafiobackvotos.infrastruture.kafka.producer;

import br.com.vepp.desafiobackvotos.domain.service.AgendaService;
import br.com.vepp.desafiobackvotos.infrastruture.kafka.models.AgendaResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class AgendaProducer {

    private final String topicProduct;
    private final AgendaService agendaService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ObjectMapper mapper;

    @Autowired
    public AgendaProducer(@Value("${api.command.kafka.topic-agenda-name}") String topicProduct,
                          KafkaTemplate<String, Object> kafkaTemplate,
                          AgendaService agendaService,
                          ObjectMapper mapper) {
        this.topicProduct = topicProduct;
        this.kafkaTemplate = kafkaTemplate;
        this.agendaService = agendaService;
        this.mapper = mapper;
    }

    public void sendResult(final AgendaResult kafkaTopicIn) throws JsonProcessingException {
        final var key = Objects.requireNonNullElse(kafkaTopicIn.getAgenda().getId(), UUID.randomUUID().toString());
        final var agenda = agendaService.findById(kafkaTopicIn.getAgenda().getId()).orElseThrow();

        kafkaTopicIn.setAgenda(agenda);

        final var value = mapper.writeValueAsString(kafkaTopicIn);

        final var result = kafkaTemplate.send(topicProduct, key, value);
        log.info("send kafka {}", result);
    }
}
