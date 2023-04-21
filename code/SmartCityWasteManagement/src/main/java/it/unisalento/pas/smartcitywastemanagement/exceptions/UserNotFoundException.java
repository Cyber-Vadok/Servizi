package it.unisalento.pas.smartcitywastemanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * eccezione custom per gli utenti non trovati nel db, quindi avviene una gestione degli status code (@ResponseStatus)
 */

@ResponseStatus(code= HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception{

}
