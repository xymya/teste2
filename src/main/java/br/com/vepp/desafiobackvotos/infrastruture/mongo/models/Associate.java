package br.com.vepp.desafiobackvotos.infrastruture.mongo.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(value = "associate")
@AllArgsConstructor
@NoArgsConstructor
public class Associate {

    @Id
    private String cpf;

    private String fullName;
}
