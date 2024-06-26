package br.com.vepp.desafiobackvotos.domain.service;

import br.com.vepp.desafiobackvotos.domain.models.VoteDTO;
import br.com.vepp.desafiobackvotos.domain.models.VotingResultDTO;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.enums.VoteTypeEnum;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Associate;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Voting;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.repository.VotingRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
class VotingServiceTest {

    @InjectMocks
    private VotingService votingService;

    @Mock
    private VotingRepository votingRepository;

    @Mock
    private AgendaService agendaService;

    @Test
    public void shouldSendVote() {
        final var vote = getVoteDTO();
        final var agenda = getAgenda();

        when(votingRepository.findByAssociate(any())).thenReturn(Mono.empty());
        when(agendaService.findById(anyString())).thenReturn(Optional.of(agenda));
        when(votingRepository.save(any())).thenReturn(Mono.just(getVote()));

        assertDoesNotThrow(() ->votingService.sendVote(vote));
    }

    @Test
    public void shouldGetResultVoting() {
        final var vote = getVoteDTO();
        when(votingRepository.findVotesByVoteTypeAndAgendaId(any(), any())).thenReturn(Flux.just(getVote()));

        final var resultVoting = votingService.getVotingResult(vote.getAgendaId());
        assertAll(
            () -> assertEquals(resultVoting.getAgendaId(), vote.getAgendaId()),
            () -> assertEquals(resultVoting.getCountVotesYes(), 1L)
        );
    }

    @SneakyThrows
    @Test
    public void shouldOpenVoting() {
        final var agenda = getAgenda();
        when(agendaService.findById(anyString())).thenReturn(Optional.of(agenda));
        when(agendaService.saveAgenda(any())).thenReturn(Mono.just(agenda));

        final var agendaMono = votingService.openSession(UUID.randomUUID().toString(), 1);

        assertAll(
            () -> assertEquals(agendaMono.toFuture().get().getId(), agenda.getId())
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
            .description("TESTE")
            .active(true)
            .dtStart(LocalDateTime.now())
            .dtEnd(LocalDateTime.now().plusDays(10))
            .build();
    }

}