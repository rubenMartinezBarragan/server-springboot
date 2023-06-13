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
	private Date dateLoan;

	@Column(name = "date_return", nullable = false)
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