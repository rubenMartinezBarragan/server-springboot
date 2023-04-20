package com.ccsw.tutorial.loan;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author ruben martinez barragan
 *
 */
@Tag(name = "Loan", description = "API of Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    DozerBeanMapper mapper;

    /**
     * Método para recuperar un listado paginado de {@link Loan}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link LoanDto}
     */
    @Operation(summary = "Find Page", description = "Method that return a page of Loans")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<LoanDto> findPage(@RequestBody LoanSearchDto dto) {

        Page<Loan> page = this.loanService.findPage(dto);

        return new PageImpl<>(
                page.getContent().stream().map(e -> mapper.map(e, LoanDto.class)).collect(Collectors.toList()),
                page.getPageable(), page.getTotalElements());
    }

    /**
     * Método para recuperar una lista de {@link Loan}
     *
     * @param idGame     PK título del juego
     * @param idClient   PK del cliente
     * @param dateSearch fecha
     * @return {@link List} de {@link LoanDto}
     */
    @Operation(summary = "Find", description = "Method that return a filtered list of Loan")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<LoanDto> find(@RequestParam(value = "idGame", required = false) Long idGame,
            @RequestParam(value = "idClient", required = false) Long idClient,
            @RequestParam(value = "dateSearch", required = false) Date dateSearch) {
        List<Loan> loans = loanService.find(idGame, idClient, dateSearch);

        return loans.stream().map(e -> mapper.map(e, LoanDto.class)).collect(Collectors.toList());
    }

    /**
     * Método para eliminar un {@link Loan}
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Loan")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {
        this.loanService.delete(id);
    }

    /**
     * Método para crear un {@link Loan}
     *
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save", description = "Method that saves a Loan")
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public void save(@RequestBody LoanDto dto) throws Exception {
        loanService.save(dto);
    }

}