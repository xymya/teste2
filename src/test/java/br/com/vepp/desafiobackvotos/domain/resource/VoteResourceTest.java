package br.com.vepp.desafiobackvotos.domain.resource;

import br.com.vepp.desafiobackvotos.domain.models.VoteDTO;
import br.com.vepp.desafiobackvotos.domain.models.VotingResultDTO;
import br.com.vepp.desafiobackvotos.domain.service.VotingService;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.enums.VoteTypeEnum;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(VoteResource.class)
class VoteResourceTest {
    @Autowired
    private WebTestClient webClient;

    @MockBean
    private VotingService votingService;

    @Test
    public void shouldGetResultVoting() {

        final var resultVoting = getResultVoting();
        when(votingService.getVotingResult(any())).thenReturn(resultVoting);

        webClient.get()
            .uri("/votes/{agendaId}/result", UUID.randomUUID().toString())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(VotingResultDTO.class);
    }

    @Test
    public void shouldOpenVoting() {

        when(votingService.openSession(anyString(), anyInt())).thenReturn(Mono.just(getAgenda()));

        webClient.post()
            .uri("/votes/{idAgenda}/open",UUID.randomUUID().toString())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Agenda.class);
    }

    @Test
    public void shouldSendVote() {
        final var mock = mock(VotingService.class);
        final var vote = getVote();

        doNothing().when(mock).sendVote(vote);
        webClient.post()
            .uri("/votes")
            .bodyValue(vote)
            .exchange()
            .expectStatus()
            .isOk();
    }

    private VoteDTO getVote() {
        return VoteDTO.builder()
            .cpf("0000000000")
            .voteType(VoteTypeEnum.SIM)
            .agendaId(UUID.randomUUID().toString())
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
                .dtEnd(LocalDateTime.now())
                .build();
    }
}