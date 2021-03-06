package com.rodrigo.TFG_server.Negocio.Utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Rodrigo de Miguel González
 * @Date 2017-2018
 * TFG - Atravesando las Capas de una Aplicación Empresarial: Demostrador Tecnológico J2EE
 */
public class EmailValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * Validate hex with regular expression
     *
     * @param email email for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validate(String email) {

        if(email != null){
            email = email.trim();
            if(email != "") {
                matcher = pattern.matcher(email);
                return matcher.matches();
            } else
                return false;
        }else return false;

    }
}