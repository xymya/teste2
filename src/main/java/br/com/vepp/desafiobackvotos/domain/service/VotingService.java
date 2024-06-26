package br.com.vepp.desafiobackvotos.domain.service;

import br.com.vepp.desafiobackvotos.common.exception.BadRequestException;
import br.com.vepp.desafiobackvotos.common.exception.NotFoundException;
import br.com.vepp.desafiobackvotos.domain.models.VoteDTO;
import br.com.vepp.desafiobackvotos.domain.models.VotingResultDTO;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.enums.VoteTypeEnum;
import br.com.vepp.desafiobackvotos.infrastruture.integration.CPFIntegration;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Agenda;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Associate;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.models.Voting;
import br.com.vepp.desafiobackvotos.infrastruture.mongo.repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VotingService {

    private final VotingRepository votingRepository;

    private final CPFIntegration cpfIntegration;

    private final AgendaService agendaService;

    @SneakyThrows
    public void sendVote(VoteDTO voteDTO) {

        if(!isOpenedAgenda(voteDTO.getAgendaId())) {
            log.warn("Sessão {} indisponível para votação", voteDTO.getAgendaId());
            throw new BadRequestException("Ocorreu um erro pois a sessão está indisponível para votação: " + voteDTO);
        }

        final var associate = Associate.builder()
            .cpf(voteDTO.getCpf())
            .build();
        Voting voting = votingRepository.findByAssociate(associate).toFuture().get();
        if (ObjectUtils.allNotNull(voting) && ObjectUtils.allNotNull(voting.getAssociate().getCpf())) {
            log.warn("Associado já realizou a votação: {}", voting.getId());
            throw new BadRequestException("Associado já realizou a votação: voto " + voting.getId());
        }

        try {
            /*ResponseEntity<CPFIntegrationDTO> response = cpfIntegration.isCPFValid(voteDTO.getCpf());

            if(response.getBody().getStatus().equals(CPFIntegrationStatusEnum.UNABLE_TO_VOTE)) {
                log.warn("CPF não habilitado para votar: {} ", voteDTO.getCpf());
                throw new BadRequestException("Ocorreu um erro pois o CPF não está habilitado para votar: " + voteDTO.getCpf()  );
            }*/

            voting = Voting.builder()
                .associate(associate)
                .agenda(Agenda.builder().id(voteDTO.getAgendaId()).build())
                .voteType(voteDTO.getVoteType())
                .build();

            final var votingDB = votingRepository.save(voting);
            log.info("Voto registrado: {}", votingDB.toFuture().get().getId());
        } catch (NotFoundException e) {
            log.warn("CPF inválido: {} ", voteDTO.getAgendaId());
            throw new NotFoundException("Associado não encontrado na base de dados.");
        } catch (DataIntegrityViolationException e) {
            log.warn("CPF {} já voltou na pauta {} ", voteDTO.getCpf(), voteDTO.getAgendaId());
            throw new BadRequestException("Ocorreu um erro pois o associado já voltou nessa Pauta");
        }
    }

    private boolean isOpenedAgenda(String agendaId) {
        Optional<Agenda> optional = agendaService.findById(agendaId);
        if(optional.isPresent()) {
            Agenda agenda = optional.get();
            return !LocalDateTime.now().isAfter(agenda.getDtEnd());
        }
        return false;
    }

    @SneakyThrows
    public VotingResultDTO getVotingResult(String agendaId) {
        final var votesIsNo = votingRepository.findVotesByVoteTypeAndAgendaId(VoteTypeEnum.NAO, agendaId)
            .collectList()
            .toFuture()
            .get();

        final var votesIsYes = votingRepository.findVotesByVoteTypeAndAgendaId(VoteTypeEnum.SIM, agendaId)
                .collectList()
                .toFuture()
                .get();

        return VotingResultDTO.builder()
                .agendaId(agendaId)
                .countVotesNo(votesIsNo.stream().count())
                .countVotesYes(votesIsYes.stream().count())
                .build();
    }

    public Mono<Agenda> openSession(String idAgenda, Integer time) {
        if (ObjectUtils.allNull(time) || (ObjectUtils.allNotNull(time) && ObjectUtils.compare(time, 0) == 0)) {
            time = 1;
        }

        final var agenda = agendaService.findById(idAgenda);

        if (agenda.isPresent()) {
            final var agendaDb = agenda.get();
            agendaDb.setActive(true);
            agendaDb.setDtStart(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
            agendaDb.setDtEnd(agendaDb.getDtStart().plusMinutes(time));
            log.info("Sessão aberta: {}", agendaDb);
            return agendaService.saveAgenda(agendaDb);
        }

        throw new NotFoundException("Pauta não encontrada: " + idAgenda);
    }
}
