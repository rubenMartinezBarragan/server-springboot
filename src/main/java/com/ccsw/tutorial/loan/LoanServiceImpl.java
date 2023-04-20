package com.ccsw.tutorial.loan;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

import jakarta.transaction.Transactional;

/**
 * @author ruben martinez barragan
 *
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    private boolean gameLoan;
    private int numberGame;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> findPage(LoanSearchDto dto) {

        return this.loanRepository.findAll(dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Loan> find(Long idGame, Long idClient, Date dateSearch) {
        LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", idGame));
        LoanSpecification clientSpec = new LoanSpecification(new SearchCriteria("client.id", ":", idClient));
        LoanSpecification dateLoanSpec = new LoanSpecification(new SearchCriteria("date_loan", "<:", dateSearch));
        LoanSpecification dateReturnSpec = new LoanSpecification(new SearchCriteria("date_return", ">:", dateSearch));

        Specification<Loan> spec = Specification.where(gameSpec).and(clientSpec).and(dateLoanSpec).and(dateReturnSpec);

        return this.loanRepository.findAll(spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {
        if (this.loanRepository.findById(id).orElse(null) == null)
            throw new Exception("El préstamo que se quiere eliminar no existe");

        this.loanRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(LoanDto dto) throws Exception {
        Loan loan = new Loan();

        BeanUtils.copyProperties(dto, loan, "id", "client", "game");

        loan.setGame(gameService.get(dto.getGame().getId()));
        loan.setClient(clientService.get(dto.getClient().getId()));

        // Inicio validacion: 'La fecha de fin NO puede ser anterior a la fecha de
        // inicio'
        if (dto.getDate_return().before(dto.getDate_loan()))
            throw new Exception("La fecha de fin NO puede ser anterior a la fecha de inicio");

        // Inicio validacion: 'El periodo de prestamo maximo solo puede ser de 14 dias'
        long daysBetween = ChronoUnit.DAYS.between(dto.getDate_loan().toLocalDate(),
                dto.getDate_return().toLocalDate());

        if (daysBetween > 14L)
            throw new Exception("El periodo de préstamo máximo solo puede ser de 14 días");

        // Inicio validacion: 'El mismo juego no puede estar prestado a dos clientes
        // distintos en un mismo dia'
        gameLoan = true;

        LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", dto.getGame().getId()));

        Specification<Loan> specGame = Specification.where(gameSpec);

        loanRepository.findAll(specGame).forEach((p) -> {
            if ((!p.getClient().getId().equals(dto.getClient().getId()))
                    && ((dto.getDate_loan().after(p.getDate_loan()) && dto.getDate_loan().before(p.getDate_return()))))
                gameLoan = false;
        });

        if (!gameLoan)
            throw new Exception("El mismo juego no puede estar prestado a dos clientes distintos en un mismo día");

        // Inicio validacion: 'Un mismo cliente no puede tener prestados mas de 2 juegos
        // en un mismo dia'
        gameLoan = true;
        numberGame = 0;

        LoanSpecification clientSpec = new LoanSpecification(
                new SearchCriteria("client.id", ":", dto.getClient().getId()));

        Specification<Loan> specClient = Specification.where(clientSpec);

        loanRepository.findAll(specClient).forEach((p) -> {
            if ((p.getClient().getId().equals(dto.getClient().getId()))
                    && ((dto.getDate_loan().after(p.getDate_loan()) && dto.getDate_loan().before(p.getDate_return()))))
                numberGame++;

            if (numberGame >= 2)
                gameLoan = false;
        });

        if (!gameLoan)
            throw new Exception("Un mismo cliente no puede tener prestados más de 2 juegos en un mismo día");

        // Guardar prestamo
        this.loanRepository.save(loan);
    }

}