package aqar;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessengerService {

    private static final String MESSAGE = "{\"recipient\": {\"phone_number\": \"%s\"},\"message\": {\"text\": \"%s\"}}";

    @Value("${aqarme.messenger.url}")
    private String url;

    private RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public MessengerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public void send(String recipient, String str) {

        String message = String.format(MESSAGE, recipient, str);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(message, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        System.out.println(response);
    }
}
