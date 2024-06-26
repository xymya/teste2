package br.com.vepp.desafiobackvotos.infrastruture.kafka.models;

import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgendaResult {

    private Agenda agenda;
    private Long countVotesYes;
    private Long countVotesNo;
}
