package br.com.vepp.desafiobackvotos.infrastruture.integration;


import br.com.vepp.desafiobackvotos.domain.models.CPFIntegrationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cpfIntegration", url = "${api.url-cpf-integration}")
public interface CPFIntegration {

    @GetMapping("/{cpf}")
    public ResponseEntity<CPFIntegrationDTO> isCPFValid(@PathVariable String cpf);

}
