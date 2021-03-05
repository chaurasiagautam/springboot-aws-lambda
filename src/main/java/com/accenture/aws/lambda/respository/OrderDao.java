package com.accenture.aws.lambda.respository;

import com.accenture.aws.lambda.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class OrderDao {

    @Autowired
    RestTemplate template;

    public List<Order> buildOrders(){
        return Stream.of(
                new Order(101, "Mobile", 20000, 1),
                new Order(102, "Book", 999, 2),
                new Order(278, "Book", 1466, 3),
                new Order(953, "Jeans", 4499, 1)
        ).collect(Collectors.toList());
    }

    public String getProductList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return template.exchange("https://api.github.com/users/chaurasiagautam/repos", HttpMethod.GET, entity, String.class).getBody();
    }
}
