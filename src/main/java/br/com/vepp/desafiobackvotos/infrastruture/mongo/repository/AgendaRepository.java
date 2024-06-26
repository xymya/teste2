package br.com.vepp.desafiobackvotos.infrastruture.mongo.repository;

import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AgendaRepository extends ReactiveMongoRepository<Agenda, String> {

    @Query(value = "{ 'active' : ?0 }")
    Flux<Agenda> findByActive(boolean value);
}
