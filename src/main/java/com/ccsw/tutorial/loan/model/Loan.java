package com.ccsw.tutorial.loan.model;

import java.sql.Date;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.game.model.Game;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author ruben martinez barragan
 *
 */
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "date_loan", nullable = false)
    private Date date_loan;

    @Column(name = "date_return", nullable = false)
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
    public Game getGame() {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
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