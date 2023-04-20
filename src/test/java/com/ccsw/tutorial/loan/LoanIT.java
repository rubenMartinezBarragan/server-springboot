package com.ccsw.tutorial.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    private static final int TOTAL_LOANS = 5;
    private static final int PAGE_SIZE = 4;

    private static final String GAME_ID_PARAM = "idGame";
    private static final String CLIENT_ID_PARAM = "idClient";
    private static final String DATE_SEARCH_PARAM = "dateSearch";

    public static final Long EXISTS_GAME_ID = 1L;
    public static final Long EXISTS_CLIENT_ID = 1L;
    private static final Date EXISTS_DATE_SEARCH_VALUE = Date.valueOf("2020-01-03");

    private static final Long NOT_EXISTS_GAME_ID = 0L;
    private static final Long NOT_EXISTS_CLIENT_ID = 0L;
    private static final Date NOT_EXISTS_DATE_SEARCH_VALUE = Date.valueOf("2000-01-01");

    public static final Long DELETE_LOAN_ID = 5L;
    private static final int TOTAL_LOAN = 5;

    private static final Long NEW_GAME = 5L;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<LoanDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LoanDto>>() {
    };

    ParameterizedTypeReference<List<LoanDto>> responseTypeList = new ParameterizedTypeReference<List<LoanDto>>() {
    };

    ParameterizedTypeReference<List<LoanDto>> responseType = new ParameterizedTypeReference<List<LoanDto>>() {
    };

    private String getUrlWithParamsGameAndClient() {
        return UriComponentsBuilder.fromHttpUrl(LOCALHOST + port + SERVICE_PATH)
                .queryParam(GAME_ID_PARAM, "{" + GAME_ID_PARAM + "}")
                .queryParam(CLIENT_ID_PARAM, "{" + CLIENT_ID_PARAM + "}").encode().toUriString();
    }

    private String getUrlWithParamDate() {
        return UriComponentsBuilder.fromHttpUrl(LOCALHOST + port + SERVICE_PATH)
                .queryParam(DATE_SEARCH_PARAM, "{" + DATE_SEARCH_PARAM + "}").encode().toUriString();
    }

    private String getUrlWithAllParams() {
        return UriComponentsBuilder.fromHttpUrl(LOCALHOST + port + SERVICE_PATH)
                .queryParam(GAME_ID_PARAM, "{" + GAME_ID_PARAM + "}")
                .queryParam(CLIENT_ID_PARAM, "{" + CLIENT_ID_PARAM + "}")
                .queryParam(DATE_SEARCH_PARAM, "{" + DATE_SEARCH_PARAM + "}").encode().toUriString();
    }

    @Test
    public void findFirstPageWithFourSizeShouldReturnFirstFourResults() {
        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithFourSizeShouldReturnLastResult() {
        int elementsCount = TOTAL_LOANS - PAGE_SIZE;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(1, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(elementsCount, response.getBody().getContent().size());
    }

    @Test
    public void findAllShouldReturnAllLoan() {
        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET,
                null, responseTypeList);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().size());
    }

    @Test
    public void findWithoutFiltersShouldReturnAllLoansInDB() {
        int LOANS_WITHOUT_FILTER = 5;

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, null);
        params.put(CLIENT_ID_PARAM, null);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParamsGameAndClient(), HttpMethod.GET,
                null, responseType, params);

        assertNotNull(response);
        assertEquals(LOANS_WITHOUT_FILTER, response.getBody().size());
    }

    @Test
    public void findExistsGameShouldReturnLoansWithThatGame() {
        int GAMES_WITH_FILTER = 2;

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, EXISTS_GAME_ID);
        params.put(CLIENT_ID_PARAM, null);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParamsGameAndClient(), HttpMethod.GET,
                null, responseType, params);

        assertNotNull(response);
        assertEquals(GAMES_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findExistsClientShouldReturnLoansWithThatClient() {
        int CLIENTS_WITH_FILTER = 3;

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, null);
        params.put(CLIENT_ID_PARAM, EXISTS_CLIENT_ID);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParamsGameAndClient(), HttpMethod.GET,
                null, responseType, params);

        assertNotNull(response);
        assertEquals(CLIENTS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findExistsDateShouldReturnLoansWithThatDate() {
        int LOANS_WITH_FILTER = 4;

        Map<String, Object> params = new HashMap<>();
        params.put(DATE_SEARCH_PARAM, EXISTS_DATE_SEARCH_VALUE);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParamDate(), HttpMethod.GET, null,
                responseType, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findExistsGameAndClientShouldReturnLoansWithThatGameAndClient() {
        int GAMES_AND_CLIENTS_WITH_FILTER = 2;

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, EXISTS_GAME_ID);
        params.put(CLIENT_ID_PARAM, EXISTS_CLIENT_ID);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParamsGameAndClient(), HttpMethod.GET,
                null, responseType, params);

        assertNotNull(response);
        assertEquals(GAMES_AND_CLIENTS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findExistsGameAndClientAndDateShouldReturnLoansWithThatGameAndClientAndDate() {
        int GAMES_AND_CLIENTS_AND_DATE_WITH_FILTER = 1;

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, EXISTS_GAME_ID);
        params.put(CLIENT_ID_PARAM, EXISTS_CLIENT_ID);
        params.put(DATE_SEARCH_PARAM, EXISTS_DATE_SEARCH_VALUE);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithAllParams(), HttpMethod.GET, null,
                responseType, params);

        assertNotNull(response);
        assertEquals(GAMES_AND_CLIENTS_AND_DATE_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findNotExistsGameShouldReturnEmpty() {
        int GAMES_WITH_FILTER = 0;

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, NOT_EXISTS_GAME_ID);
        params.put(CLIENT_ID_PARAM, null);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParamsGameAndClient(), HttpMethod.GET,
                null, responseType, params);

        assertNotNull(response);
        assertEquals(GAMES_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findNotExistsClientShouldReturnEmpty() {
        int CLIENTS_WITH_FILTER = 0;

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, null);
        params.put(CLIENT_ID_PARAM, NOT_EXISTS_CLIENT_ID);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParamsGameAndClient(), HttpMethod.GET,
                null, responseType, params);

        assertNotNull(response);
        assertEquals(CLIENTS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findNotExistsDateShouldReturnEmpty() {
        int LOANS_WITH_FILTER = 0;

        Map<String, Object> params = new HashMap<>();
        params.put(DATE_SEARCH_PARAM, NOT_EXISTS_DATE_SEARCH_VALUE);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParamDate(), HttpMethod.GET, null,
                responseType, params);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void findNotExistsGameOrClientShouldReturnEmpty() {
        int GAMES_AND_CLIENTS_WITH_FILTER = 0;

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, NOT_EXISTS_GAME_ID);
        params.put(CLIENT_ID_PARAM, NOT_EXISTS_CLIENT_ID);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParamsGameAndClient(), HttpMethod.GET,
                null, responseType, params);
        assertNotNull(response);
        assertEquals(GAMES_AND_CLIENTS_WITH_FILTER, response.getBody().size());

        params.put(GAME_ID_PARAM, NOT_EXISTS_GAME_ID);
        params.put(CLIENT_ID_PARAM, EXISTS_CLIENT_ID);

        response = restTemplate.exchange(getUrlWithParamsGameAndClient(), HttpMethod.GET, null, responseType, params);
        assertNotNull(response);
        assertEquals(GAMES_AND_CLIENTS_WITH_FILTER, response.getBody().size());

        params.put(GAME_ID_PARAM, EXISTS_GAME_ID);
        params.put(CLIENT_ID_PARAM, NOT_EXISTS_CLIENT_ID);

        response = restTemplate.exchange(getUrlWithParamsGameAndClient(), HttpMethod.GET, null, responseType, params);
        assertNotNull(response);
        assertEquals(GAMES_AND_CLIENTS_WITH_FILTER, response.getBody().size());
    }

    @Test
    public void deleteWithExistsIdShouldDeleteLoan() {
        long newLoanSize = TOTAL_LOAN - 1;

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + DELETE_LOAN_ID, HttpMethod.DELETE, null,
                Void.class);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOAN));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newLoanSize, response.getBody().getTotalElements());
    }

    @Test
    public void deleteWithNotExistsIdShouldThrowException() {
        long deleteLoanId = TOTAL_LOAN + 1;

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + deleteLoanId,
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void saveLoandShouldCreateNewLoan() {
        LoanDto dto = new LoanDto();

        java.util.Date utilDateToDay = new java.util.Date();
        java.sql.Date sqlDateToday = new java.sql.Date(utilDateToDay.getTime());
        java.sql.Date sqlDateTomorrow = Date.valueOf(sqlDateToday.toLocalDate().plusDays(1));

        ClientDto clientDto = new ClientDto();
        clientDto.setId(5L);

        GameDto gameDto = new GameDto();
        gameDto.setId(5L);

        dto.setDate_loan(sqlDateToday);
        dto.setDate_return(sqlDateTomorrow);
        dto.setClient(clientDto);
        dto.setGame(gameDto);

        Map<String, Object> params = new HashMap<>();
        params.put(GAME_ID_PARAM, NEW_GAME);
        params.put(CLIENT_ID_PARAM, null);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(getUrlWithParamsGameAndClient(), HttpMethod.GET,
                null, responseType, params);

        assertNotNull(response);
        assertEquals(0, response.getBody().size());

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        response = restTemplate.exchange(getUrlWithParamsGameAndClient(), HttpMethod.GET, null, responseType, params);

        assertNotNull(response);
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void saveLoandEndDateBeforeStartDateShouldThrowException() {
        LoanDto dto = new LoanDto();

        java.util.Date utilDateToDay = new java.util.Date();
        java.sql.Date sqlDateToday = new java.sql.Date(utilDateToDay.getTime());
        java.sql.Date sqlDateYesterday = Date.valueOf(sqlDateToday.toLocalDate().minusDays(1));

        ClientDto clientDto = new ClientDto();
        clientDto.setId(5L);

        GameDto gameDto = new GameDto();
        gameDto.setId(5L);

        dto.setDate_loan(sqlDateToday);
        dto.setDate_return(sqlDateYesterday);
        dto.setClient(clientDto);
        dto.setGame(gameDto);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void saveLoanGreaterThan14DaysShouldThrowException() {
        LoanDto dto = new LoanDto();

        java.util.Date utilDateToDay = new java.util.Date();
        java.sql.Date sqlDateToday = new java.sql.Date(utilDateToDay.getTime());
        java.sql.Date sqlDateGreaterThan14Days = Date.valueOf(sqlDateToday.toLocalDate().plusDays(15));

        ClientDto clientDto = new ClientDto();
        clientDto.setId(5L);

        GameDto gameDto = new GameDto();
        gameDto.setId(5L);

        dto.setDate_loan(sqlDateToday);
        dto.setDate_return(sqlDateGreaterThan14Days);
        dto.setClient(clientDto);
        dto.setGame(gameDto);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void saveLoanDifferentClientsSameDayShouldThrowException() {
        LoanDto dto = new LoanDto();

        java.sql.Date sqlDateLoan = Date.valueOf("2020-01-02");
        java.sql.Date sqlDateReturn = Date.valueOf("2020-01-05");

        ClientDto clientDto = new ClientDto();
        clientDto.setId(5L);

        GameDto gameDto = new GameDto();
        gameDto.setId(1L);

        dto.setDate_loan(sqlDateLoan);
        dto.setDate_return(sqlDateReturn);
        dto.setClient(clientDto);
        dto.setGame(gameDto);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void saveLoanClienMoreThanTwoGamesShouldThrowException() {
        LoanDto dto = new LoanDto();

        java.sql.Date sqlDateLoan = Date.valueOf("2020-01-03");
        java.sql.Date sqlDateReturn = Date.valueOf("2020-01-05");

        ClientDto clientDto = new ClientDto();
        clientDto.setId(1L);

        GameDto gameDto = new GameDto();
        gameDto.setId(5L);

        dto.setDate_loan(sqlDateLoan);
        dto.setDate_return(sqlDateReturn);
        dto.setClient(clientDto);
        dto.setGame(gameDto);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}