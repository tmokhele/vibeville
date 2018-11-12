package bttc.app.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
@EnableRabbit
public class RabbitMQConfig implements RabbitListenerConfigurer {

    public static final String USER_REGISTRATION_REPROCESS = "reprocessing-user-registration-queue";
    public static final String USER_REGISTRATION = "user-registration-queue";
    private static final String EXCHANGE_USER_REGISTRATION = "user-registration-exchange";

    @Bean
    Queue userRegistrationQueue() {

        return QueueBuilder.durable(USER_REGISTRATION)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", USER_REGISTRATION_REPROCESS)
                .build();
    }

    @Bean
    Queue reprocessingUserRegistrationQueue() {
        return QueueBuilder.durable(USER_REGISTRATION_REPROCESS).build();
    }

    @Bean
    TopicExchange userRegistrationExchange() {
        return new TopicExchange(EXCHANGE_USER_REGISTRATION);
    }

    @Bean
    Binding bindingPublisher(Queue userRegistrationQueue, TopicExchange publisherExchange) {
        return BindingBuilder.bind(userRegistrationQueue).to(publisherExchange).with(USER_REGISTRATION);
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        return new DefaultMessageHandlerMethodFactory();
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

}
