package com.accenture.aws.lambda;

import com.accenture.aws.lambda.domain.Order;
import com.accenture.aws.lambda.respository.OrderDao;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringbootAwsLambdaApplication {

    @Autowired
    private OrderDao orderDao;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAwsLambdaApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Supplier<List<Order>> orders() {
        return () -> orderDao.buildOrders();
    }

    @Bean
    public Function<APIGatewayProxyRequestEvent, List<Order>> findOrderByName() {
        return (requestEvent) -> orderDao.buildOrders().stream()
                .filter(order -> order.getName().equals(requestEvent.getQueryStringParameters().get("name"))).collect(Collectors.toList());
    }

    @Bean
    public Supplier<String> getApiResponse() {
        return () -> orderDao.getApiResponse();
    }

}
