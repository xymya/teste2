package br.com.vepp.desafiobackvotos.domain.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "AgendaDTO", description = "Objeto referente ao dados da pauta")
public class AgendaDTO {

    @Schema(name = "id", description = "Id da pauta", example = "1")
    private Long id;

    @Schema(name = "description", description = "Descrição da pauta", example = "Pauta referente a prestacão de contas")
    private String description;

}
