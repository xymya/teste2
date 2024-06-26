package br.com.vepp.desafiobackvotos.domain.models;

import br.com.vepp.desafiobackvotos.infrastruture.mongo.enums.CPFIntegrationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CPFIntegrationDTO {

    private CPFIntegrationStatusEnum status;
}
