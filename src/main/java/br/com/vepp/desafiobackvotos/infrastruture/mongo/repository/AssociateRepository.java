package br.com.vepp.desafiobackvotos.infrastruture.mongo.repository;

import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Associate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AssociateRepository extends ReactiveMongoRepository<Associate, String> {

    Mono<Associate> findByCpf(String cpf);

}
