package br.com.vepp.desafiobackvotos.infrastruture.kafka.mapper;

import br.com.vepp.desafiobackvotos.domain.models.VotingResultDTO;
import br.com.vepp.desafiobackvotos.infrastruture.kafka.models.AgendaResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KafkaAgendaMapper {
    @Mapping(source = "agendaId", target = "agenda.id")
    AgendaResult toPayloadKafka(VotingResultDTO votingResultDTO);

    VotingResultDTO toDTO(AgendaResult product);
}
