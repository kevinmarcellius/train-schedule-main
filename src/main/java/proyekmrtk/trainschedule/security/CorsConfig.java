package proyekmrtk.trainschedule.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Value("${app.main-domain}")
    private String root;
    @Value("${app.top-up-domain}")
    private String topUp;
    @Value("${app.train-schedule-domain}")
    private String trainSchedule;
    @Value("${app.buy-tickets-domain}")
    private String buyTickets;
    @Value("${app.payment-history-domain}")
    private String paymentHistory;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(this.root, this.topUp, this.trainSchedule, this.buyTickets, this.paymentHistory)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
