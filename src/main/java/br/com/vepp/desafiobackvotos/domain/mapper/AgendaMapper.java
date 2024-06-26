package br.com.vepp.desafiobackvotos.domain.mapper;

import br.com.vepp.desafiobackvotos.domain.models.AgendaDTO;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AgendaMapper {
    Agenda toEntity(AgendaDTO productDTO);

    AgendaDTO toDTO(Agenda product);
}
