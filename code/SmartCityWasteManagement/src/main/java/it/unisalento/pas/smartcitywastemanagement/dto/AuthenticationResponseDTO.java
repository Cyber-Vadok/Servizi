package it.unisalento.pas.smartcitywastemanagement.dto;

/**
 * classe che rappresenta un json web token (jwt) che deve essere restituito quando si fa il login in modo che le volte
 * successive si usi il token (con deadtime);
 * si potrebbe aggiunge anche un refresh token per richiedere il nuovo token quando arriva al dead time
 */
public class AuthenticationResponseDTO {

  private String jwt;

  public AuthenticationResponseDTO(String jwt) {
    this.jwt = jwt;
  }

  public String getJwt() {
    return jwt;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }
}
