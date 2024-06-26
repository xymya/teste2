package br.com.vepp.desafiobackvotos.infrastruture.mongo.repository;

import br.com.vepp.desafiobackvotos.infrastruture.mongo.enums.VoteTypeEnum;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Voting;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.repository.VotingRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class VotingRepositoryTest {

    @Autowired
    private VotingRepository repository;

    @SneakyThrows
    @Test
    public void shouldSaveSingleVoting() {

        final var voting = getVote();

        repository.save(voting).toFuture().get();

        assertAll(
            () -> assertNotEquals(repository.findAll()
                    .collectList().toFuture().get().size(), 0),
            () -> assertNotEquals(repository.findAll()
                    .collectList().toFuture().get().size(), -1)
        );
    }

    @SneakyThrows
    @Test
    public void shouldFindAssociates() {

        final var votes = repository.findAll()
                .collectList();

        assertAll(
            () -> assertNotEquals(votes.toFuture().get().isEmpty(), true)
        );
    }

    private Voting getVote() {
        return Voting.builder()
            .voteType(VoteTypeEnum.SIM)
            .agenda(Agenda.builder()
                .id(UUID.randomUUID().toString())
                .description("teste")
                .active(true)
                .dtStart(LocalDateTime.now())
                .dtEnd(LocalDateTime.now())
                .build())
            .build();
    }
}