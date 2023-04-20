package com.ccsw.tutorial.loan;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.tutorial.loan.model.Loan;

@ExtendWith(MockitoExtension.class)
public class LoanTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    public static final Long EXISTS_LOAN_ID = 1L;

    @Test
    public void deleteExistsLoanIdShouldDelete() throws Exception {
        Loan loan = mock(Loan.class);
        when(loanRepository.findById(EXISTS_LOAN_ID)).thenReturn(Optional.of(loan));

        loanService.delete(EXISTS_LOAN_ID);

        verify(loanRepository).deleteById(EXISTS_LOAN_ID);
    }

}