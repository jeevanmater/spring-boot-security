package jeevankumar.spring_boot_security.dto;

public class JwtAuthenticationResponse {

    private String accessToken;

    public JwtAuthenticationResponse() {
    }

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
