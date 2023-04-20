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
    private Date date_loan;
    private Date date_return;

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
     * @return the date_loan
     */
    public Date getDate_loan() {
        return date_loan;
    }

    /**
     * @param date_loan the date_loan to set
     */
    public void setDate_loan(Date date_loan) {
        this.date_loan = date_loan;
    }

    /**
     * @return the date_return
     */
    public Date getDate_return() {
        return date_return;
    }

    /**
     * @param date_return the date_return to set
     */
    public void setDate_return(Date date_return) {
        this.date_return = date_return;
    }

}