package br.com.vepp.desafiobackvotos.domain.models;

import br.com.vepp.desafiobackvotos.infrastruture.mongo.enums.VoteTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "VoteDTO", description = "Objeto referente ao dados do voto")
public class VoteDTO {

    @Schema(description = "CPF do associado", example = "16767347248")
    private String cpf;

    @Schema(description = "Id da pauta", example = "1")
    private String agendaId;

    @Schema(description = "Valor do voto", example = "SIM")
    private VoteTypeEnum voteType;
}
