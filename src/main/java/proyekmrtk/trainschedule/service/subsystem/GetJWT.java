package proyekmrtk.trainschedule.service.subsystem;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class GetJWT {
    @Value("${app.key}")
    private String key;

    public String inspectCookiesForJWT(HttpServletRequest request) {
        var cookie = WebUtils.getCookie(request, this.key);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }
}
