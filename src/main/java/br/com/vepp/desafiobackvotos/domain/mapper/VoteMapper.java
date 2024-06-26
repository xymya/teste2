package br.com.vepp.desafiobackvotos.domain.mapper;

import br.com.vepp.desafiobackvotos.domain.models.VoteDTO;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Voting;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteMapper {
    Voting toEntity(VoteDTO productDTO);

    VoteDTO toDTO(Voting product);
}
