package br.com.vepp.desafiobackvotos.domain.service;

import br.com.vepp.desafiobackvotos.domain.models.VoteDTO;
import br.com.vepp.desafiobackvotos.domain.models.VotingResultDTO;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.enums.VoteTypeEnum;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Associate;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Voting;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.repository.AgendaRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AgendaServiceTest {

    @InjectMocks
    private AgendaService agendaService;

    @Mock
    private AgendaRepository agendaRepository;


    @SneakyThrows
    @Test
    public void shouldCreateAgenda() {
        final var agenda = getNewAgenda();

        when(agendaRepository.save(any())).thenReturn(Mono.just(agenda));

        final var agendaDB = agendaService.saveAgenda(agenda).toFuture().get();

        assertAll(
            () -> assertEquals(agendaDB.getId(), agenda.getId())
        );
    }

    @Test
    public void shouldGetSingleAgenda() {
        final var agenda = getAgenda();

        when(agendaRepository.findById(anyString())).thenReturn(Mono.just(agenda));

        final var agendaOptional = agendaService.findById(agenda.getId());

        assertAll(
                () -> assertTrue(agendaOptional.isPresent()),
                () -> assertEquals(agendaOptional.get().getId(), agenda.getId())
        );
    }

    @SneakyThrows
    @Test
    public void shouldGetAllAgendas() {
        final var agenda = getAgenda();

        when(agendaRepository.findAll()).thenReturn(Flux.just(agenda));

        final var agendaOptional = agendaService.findAll().collectList().toFuture().get();

        assertAll(
                () -> assertFalse(agendaOptional.isEmpty()),
                () -> assertEquals(agendaOptional.get(0).getId(), agenda.getId())
        );
    }

    @SneakyThrows
    @Test
    public void shouldGetOpenedAgendas() {
        final var agenda = getAgenda();

        when(agendaRepository.findByActive(anyBoolean())).thenReturn(Flux.just(agenda));

        final var agendaOptional = agendaService.findByActive(true);

        assertAll(
                () -> assertFalse(agendaOptional.isEmpty()),
                () -> assertEquals(agendaOptional.get(0).getId(), agenda.getId())
        );
    }

    private VoteDTO getVoteDTO() {
        return VoteDTO.builder()
                .cpf("0000000000")
                .voteType(VoteTypeEnum.SIM)
                .agendaId(UUID.randomUUID().toString())
                .build();
    }

    private Voting getVote() {
        return Voting.builder()
                .associate(Associate.builder()
                        .cpf("000000000")
                        .fullName("teste")
                        .build())
                .voteType(VoteTypeEnum.SIM)
                .agenda(getAgenda())
                .build();
    }

    private VotingResultDTO getResultVoting() {
        return VotingResultDTO.builder()
                .agendaId(UUID.randomUUID().toString())
                .countVotesNo(4L)
                .countVotesYes(6L)
                .build();
    }

    private Agenda getAgenda() {
        return Agenda.builder()
                .id(UUID.randomUUID().toString())
                .description("TESTE")
                .active(true)
                .dtStart(LocalDateTime.now())
                .dtEnd(LocalDateTime.now().plusDays(10))
                .build();
    }

    private Agenda getNewAgenda() {
        return Agenda.builder()
                .description("TESTE")
                .active(true)
                .dtStart(LocalDateTime.now())
                .dtEnd(LocalDateTime.now().plusDays(10))
                .build();
    }

}