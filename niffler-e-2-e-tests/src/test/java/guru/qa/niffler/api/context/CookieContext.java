package guru.qa.niffler.api.context;

import java.util.HashMap;
import java.util.Map;

public class CookieContext {
    private static final ThreadLocal<CookieContext> INSTANCE = ThreadLocal.withInitial(CookieContext::new);
    private static final String
            JSESSIONID_KEY = "JSESSIONID",
            XSRF_TOKEN_KEY = "XSRF-TOKEN";
    private final Map<String, String> store = new HashMap<>();

    public static CookieContext getInstance() {
        return INSTANCE.get();
    }

    public String getJSessionId() {
        return store.get(JSESSIONID_KEY);
    }

    public String getXsrfToken() {
        return store.get(XSRF_TOKEN_KEY);
    }

    public String getJSessionIdFormattedCookie() {
        String cookieValue = store.get(JSESSIONID_KEY);
        if (cookieValue == null) {
            return JSESSIONID_KEY + "=";
        }
        return JSESSIONID_KEY + "=" + cookieValue;
    }

    public String getXsrfTokenFormattedCookie() {
        String cookieValue = store.get(XSRF_TOKEN_KEY);
        if (cookieValue == null) {
            return XSRF_TOKEN_KEY + "=";
        }
        return XSRF_TOKEN_KEY + "=" + cookieValue;
    }

    public void setJSessionId(String jSessionId) {
        store.put(JSESSIONID_KEY, jSessionId);
    }

    public void setXsrfTokenKey(String xsrfTokenKey) {
        store.put(XSRF_TOKEN_KEY, xsrfTokenKey);
    }

    public void clearContext() {
        store.clear();
    }
}
