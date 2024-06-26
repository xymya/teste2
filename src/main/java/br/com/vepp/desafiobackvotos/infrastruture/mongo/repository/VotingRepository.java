package br.com.vepp.desafiobackvotos.infrastruture.mongo.repository;

import br.com.vepp.desafiobackvotos.infrastruture.mongo.enums.VoteTypeEnum;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Associate;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Voting;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VotingRepository extends ReactiveMongoRepository<Voting, String> {
    Mono<Voting> findByAssociate(Associate associate);
    Flux<Voting> findVotesByVoteTypeAndAgendaId(VoteTypeEnum voteType, String agenda_id);
}
