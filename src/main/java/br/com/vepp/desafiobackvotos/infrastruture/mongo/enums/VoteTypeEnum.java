package br.com.vepp.desafiobackvotos.infrastruture.mongo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VoteTypeEnum {

    SIM("Sim"),
    NAO("Não");

    private String value;

}
