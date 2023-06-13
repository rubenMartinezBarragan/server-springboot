package com.ccsw.tutorial.loan.model;

import java.sql.Date;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;

/**
 * @author ruben martinez barragan
 *
 */
public class LoanDto {

    private Long id;
    private GameDto game;
    private ClientDto client;
    private Date dateLoan;
    private Date dateReturn;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the game
     */
    public GameDto getGame() {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(GameDto game) {
        this.game = game;
    }

    /**
     * @return the client
     */
    public ClientDto getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(ClientDto client) {
        this.client = client;
    }

    /**
     * @return the dateLoan
     */
    public Date getDateLoan() {
        return dateLoan;
    }

    /**
     * @param dateLoan the dateLoan to set
     */
    public void setDateLoan(Date dateLoan) {
        this.dateLoan = dateLoan;
    }

    /**
     * @return the dateReturn
     */
    public Date getDateReturn() {
        return dateReturn;
    }

    /**
     * @param dateReturn the dateReturn to set
     */
    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }

}