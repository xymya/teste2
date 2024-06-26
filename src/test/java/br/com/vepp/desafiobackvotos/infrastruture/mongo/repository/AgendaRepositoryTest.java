package br.com.vepp.desafiobackvotos.infrastruture.mongo.repository;

import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.repository.AgendaRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class AgendaRepositoryTest {

    @Autowired
    private AgendaRepository repository;

    @SneakyThrows
    @Test
    public void shouldSaveSingleAgenda() {

        final var agenda = getAgenda();

        repository.save(agenda).toFuture().get();

        assertAll(
            () -> assertNotEquals(repository.findAll()
                .collectList().toFuture().get().size(), 0),
            () -> assertNotEquals(repository.findAll()
                .collectList().toFuture().get().size(), -1)
        );
    }

    @SneakyThrows
    @Test
    public void shouldFindAgendas() {

        final var agendas = repository.findAll()
                .collectList();

        assertAll(
                () -> assertNotEquals(agendas.toFuture().get().isEmpty(), true)
        );
    }

    private Agenda getAgenda() {
        return Agenda.builder()
            .description("TESTE")
            .active(true)
            .dtStart(LocalDateTime.now())
            .dtEnd(LocalDateTime.now())
            .build();
    }
}