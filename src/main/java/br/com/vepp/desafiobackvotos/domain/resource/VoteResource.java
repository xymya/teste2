package br.com.vepp.desafiobackvotos.domain.resource;

import br.com.vepp.desafiobackvotos.domain.models.VoteDTO;
import br.com.vepp.desafiobackvotos.domain.models.VotingResultDTO;
import br.com.vepp.desafiobackvotos.domain.service.VotingService;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@Tag(name = "Recurso de gerenciamento de votação")
@ApiResponses(value = {
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
})
@RestController
@RequestMapping("/v1/votes")
@RequiredArgsConstructor
public class VoteResource {

    private final VotingService votingService;

    @PostMapping("/{idAgenda}/open")
    @Operation(description = "Serviço responsável por abrir uma sessão de votação de uma pauta")
    @ApiResponse(responseCode = "200", description = "Sessão de votação aberta.")
    public ResponseEntity<Mono<Agenda>> openVoting(@PathVariable String idAgenda, @RequestParam(defaultValue = "1") Integer time)  {
        final var agenda = votingService.openSession(idAgenda, time);
        return ResponseEntity.status(HttpStatus.OK).body(agenda);
    }

    @PostMapping()
    @Operation(description = "Serviço responsável por realizar o voto em uma pauta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Voto registro."),
        @ApiResponse(responseCode = "200", description = "Voto não registro.")
    })
    public ResponseEntity<Void> sendVote(@RequestBody VoteDTO voteDTO) {
        votingService.sendVote(voteDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{agendaId}/result")
    @Operation(description = "Serviço responsável por obter o resultado da votação de uma pauta")
    @ApiResponse(responseCode = "200", description = "Resultado obtido.")
    public ResponseEntity<VotingResultDTO> getVotingResult(@PathVariable String agendaId) {
        VotingResultDTO result = votingService.getVotingResult(agendaId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
