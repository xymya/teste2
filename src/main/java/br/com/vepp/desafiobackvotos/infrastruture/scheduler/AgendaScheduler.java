package br.com.vepp.desafiobackvotos.infrastruture.scheduler;

import br.com.vepp.desafiobackvotos.domain.service.AgendaService;
import br.com.vepp.desafiobackvotos.domain.service.VotingService;
import br.com.vepp.desafiobackvotos.infrastruture.kafka.mapper.KafkaAgendaMapper;
import br.com.vepp.desafiobackvotos.infrastruture.kafka.producer.AgendaProducer;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AgendaScheduler {

    private final AgendaService agendaService;

    private final AgendaProducer agendaProducer;

    private final VotingService votingService;
    private final KafkaAgendaMapper kafkaAgendaMapper;

    @Scheduled(fixedRateString = "${api.timer-scheduled}")
    public void closerScheduler() {

        List<Agenda> list = agendaService.findByActive(true);

        list.stream()
            .filter(agenda -> LocalDateTime.now().isAfter(agenda.getDtEnd()))
            .forEach(agenda -> {
                try {
                    agendaProducer.sendResult(kafkaAgendaMapper.toPayloadKafka(votingService.getVotingResult(agenda.getId())));
                } catch (JsonProcessingException e) {
                    log.info("Erro ao processar resultado de votação para agenda {}", agenda.getId());
                }

                agenda.setActive(false);
                try {
                    agendaService.saveAgenda(agenda).toFuture().get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
                log.info("Pauta {} encerrada votação", agenda.getId());
                log.info("Pauta enviada para fila: {}", agenda);
            });
    }
}
