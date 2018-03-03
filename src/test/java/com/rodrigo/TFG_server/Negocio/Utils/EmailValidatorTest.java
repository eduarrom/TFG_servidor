package com.rodrigo.TFG_server.Negocio.Utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Email validator Testing
 *
 * @author rodri
 * Original code of mkyong
 * http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
 *
 */
public class EmailValidatorTest {

    private static EmailValidator emailValidator;

    @BeforeAll
    public static void initData() {
        emailValidator = new EmailValidator();
    }

    public static Object[][] ValidEmailProvider() {
        return new Object[][] { { new String[] { "rodri@yahoo.com",
                "rodri-100@yahoo.com", "rodri.100@yahoo.com",
                "rodri111@rodri.com", "rodri-100@rodri.net",
                "rodri.100@rodri.com.au", "rodri@1.com",
                "rodri@gmail.com.com", "rodri+100@gmail.com",
                "rodri-100@yahoo-test.com", "rodri@gmail.com" } } };
    }


    public static Object[][] InvalidEmailProvider() {
        return new Object[][] { { new String[] { "rodri", "rodri@.com.my",
                "rodri123@gmail.a", "rodri123@.com", "rodri123@.com.com",
                ".rodri@rodri.com", "rodri()*@gmail.com", "rodri@%*.com",
                "rodri..2002@gmail.com", "rodri.@gmail.com",
                "rodri@rodri@gmail.com", "rodri@gmail.com.1a" } } };
    }

    @ParameterizedTest
    @MethodSource("ValidEmailProvider")
    public void emailValidoTest(String[] Email) {

        for (String temp : Email) {
            boolean valid = emailValidator.validate(temp);
            System.out.println("Email si valido : " + temp + " , " + valid);
            assertTrue(valid);
        }

    }

    @ParameterizedTest
    @MethodSource("InvalidEmailProvider")
    public void emailInvalidoTest(String[] Email) {

        for (String temp : Email) {
            boolean valid = emailValidator.validate(temp);
            System.out.println("Email no valido : " + temp + " , " + valid);
            assertFalse(valid);
        }
    }
}