package tourism_data.Surfing_The_Gangwon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

@Configuration
public class WebFluxJacksonConfig {

    @Bean
    public CodecCustomizer jsonCodecCustomizer(ObjectMapper om) {

        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return configurer -> {
            configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(om));
            configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(om));
        };
    }
}
