package br.com.vepp.desafiobackvotos.infrastruture.mongo.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Getter
@Builder
@Document(value = "agenda")
@AllArgsConstructor
@NoArgsConstructor
public class Agenda {

    @Id
    private String id;

    private String description;

    private LocalDateTime dtStart;

    private LocalDateTime dtEnd;

    private boolean active;

}
