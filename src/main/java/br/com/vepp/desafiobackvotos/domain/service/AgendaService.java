package br.com.vepp.desafiobackvotos.domain.service;

import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.repository.AgendaRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public Mono<Agenda> saveAgenda(Agenda agenda) {
        Mono<Agenda> agendaDb = agendaRepository.save(agenda).log();
        log.info("Pauta salva: {}", agenda);
        return agendaDb;
    }

    public Flux<Agenda> findAll() {
        return agendaRepository.findAll();
    }

    public Optional<Agenda> findById(String agendaId) {
        try {
            final var agenda = agendaRepository.findById(agendaId);
            return Optional.of(agenda.toFuture().get());
        } catch (InterruptedException | ExecutionException e) {
            return Optional.empty();
        }
    }

    @SneakyThrows
    public List<Agenda> findByActive(boolean value) {
        return agendaRepository.findByActive(value).collectList()
            .toFuture()
            .get();
    }
}
