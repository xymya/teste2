package br.com.vepp.desafiobackvotos.domain.resource;

import br.com.vepp.desafiobackvotos.domain.models.AgendaDTO;
import br.com.vepp.desafiobackvotos.domain.service.AgendaService;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/v1/agendas")
@RequiredArgsConstructor
@Tag(name = "Rscurso de gerenciamento de pautas")
public class AgendaResource {

    private final AgendaService agendaService;

    @PostMapping
    @Operation(description = "Serviço responsável por criar uma pauta")
    @ApiResponse(responseCode = "201", description = "Pauta criada.")
    public ResponseEntity<Mono<Agenda>> createAgenda(@RequestBody AgendaDTO agenda) {
        final var agendaDB = agendaService.saveAgenda(Agenda.builder().description(agenda.getDescription()).build());
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaDB);
    }

    @GetMapping
    @Operation(description = "Serviço responsável por consultar as pautas")
    @ApiResponse(responseCode = "200", description = "pautas retornadas")
    public ResponseEntity<Flux<Agenda>> getAgendas() {
        return ResponseEntity.ok().body(agendaService.findAll());
    }
}
