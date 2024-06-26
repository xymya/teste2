package br.com.vepp.desafiobackvotos.domain.resource;

import br.com.vepp.desafiobackvotos.domain.service.AgendaService;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(AgendaResource.class)
class AgendaResourceTest {
    @Autowired
    private WebTestClient webClient;

    @MockBean
    private AgendaService agendaService;

    @Test
    public void shouldGetAllAgendas() {

        final var agenda = getAgenda();
        when(agendaService.findAll()).thenReturn(Flux.just(agenda));

        webClient.get()
            .uri("/agendas")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Agenda[].class);
    }

    @Test
    public void shouldCreateAgenda() {

        final var agenda = getAgenda();
        when(agendaService.saveAgenda(any())).thenReturn(Mono.just(agenda));

        webClient.post()
            .uri("/agendas")
            .bodyValue(agenda)
            .exchange()
            .expectStatus()
            .isCreated();
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