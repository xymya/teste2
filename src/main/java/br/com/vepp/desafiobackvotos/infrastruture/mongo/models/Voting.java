package br.com.vepp.desafiobackvotos.infrastruture.mongo.models;

import br.com.vepp.desafiobackvotos.infrastruture.mongo.enums.VoteTypeEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(value = "voting")
@AllArgsConstructor
@NoArgsConstructor
public class Voting {

    @Id
    private String id;

    private Associate associate;

    private Agenda agenda;

    private VoteTypeEnum voteType;

}
