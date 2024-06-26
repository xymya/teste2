package br.com.vepp.desafiobackvotos.infrastruture.mongo.repository;

import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Associate;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.repository.AssociateRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class AssociateRepositoryTest {

    @Autowired
    private AssociateRepository repository;

    @SneakyThrows
    @Test
    public void shouldSaveSingleAssociate() {

        final var associate = getAssociate();

        repository.save(associate).toFuture().get();

        assertAll(
            () -> assertNotEquals(repository.findAll()
                    .collectList().toFuture().get().size(), 0),
            () -> assertNotEquals(repository.findAll()
                    .collectList().toFuture().get().size(), -1)
        );
    }

    @SneakyThrows
    @Test
    public void shouldFindAssociates() {

        final var associates = repository.findAll()
            .collectList();

        assertAll(
            () -> assertNotEquals(associates.toFuture().get().isEmpty(), true)
        );
    }

    private Associate getAssociate() {
        return Associate.builder()
            .cpf("00000000000")
            .fullName("teste")
            .build();
    }
}