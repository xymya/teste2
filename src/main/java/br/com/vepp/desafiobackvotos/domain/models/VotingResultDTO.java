package br.com.vepp.desafiobackvotos.domain.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "VotingResultDTO", description = "Objeto referente ao resultado da votação")
public class VotingResultDTO {

    @Schema(description = "Id da pauta", example = "1")
    private String agendaId;

    @Schema(description = "Quantidade de votos 'SIM'", example = "5")
    private Long countVotesYes;

    @Schema(description = "Quantidade de votos 'NAO'", example = "3")
    private Long countVotesNo;

}
