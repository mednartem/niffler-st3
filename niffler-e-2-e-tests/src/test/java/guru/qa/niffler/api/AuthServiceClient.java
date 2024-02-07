package guru.qa.niffler.api;

import com.fasterxml.jackson.databind.JsonNode;
import guru.qa.niffler.api.context.CookieContext;
import guru.qa.niffler.api.context.SessionStorageContext;
import guru.qa.niffler.api.interceptor.AddCookieInterceptor;
import guru.qa.niffler.api.interceptor.ReceivedCodeInterceptor;
import guru.qa.niffler.api.interceptor.ReceivedCookieInterceptor;
import io.qameta.allure.Step;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthServiceClient extends RestService {

    public AuthServiceClient() {
        super(config.nifflerAuthUrl(), true,
                new ReceivedCookieInterceptor(),
                new AddCookieInterceptor(),
                new ReceivedCodeInterceptor()
        );
    }

    private final AuthService authService = retrofit.create(AuthService.class);

    @Step("Do api login")
    public void doLogin(String username, String password) throws IOException {
        SessionStorageContext sessionStorageContext = SessionStorageContext.getInstance();
        CookieContext cookieContext = CookieContext.getInstance();
        authService.authorize(
                "code",
                "client",
                "openid",
                config.nifflerFrontUrl() + "/authorized",
                sessionStorageContext.getCodeChallenge(),
                "S256"
        ).execute();

        authService.login(
                username,
                password,
                cookieContext.getXsrfToken()
        ).execute();

        JsonNode response = authService.token(
                "Basic " + new String(Base64.getEncoder().encode("client:secret".getBytes(StandardCharsets.UTF_8))),
                "client",
                config.nifflerFrontUrl() + "/authorized",
                "authorization_code",
                sessionStorageContext.getCode(),
                sessionStorageContext.getCodeVerifier()
        ).execute().body();
        sessionStorageContext.setToken(response.get("id_token").asText());
    }
}
