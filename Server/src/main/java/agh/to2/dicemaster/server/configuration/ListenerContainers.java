package agh.to2.dicemaster.server.configuration;

import agh.to2.dicemaster.server.listeners.RegisteredClientListener;
import agh.to2.dicemaster.server.listeners.RegistrationListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerContainers {
    @Bean(name = "registrationListenerContainer")
    SimpleMessageListenerContainer registrationContainer(ConnectionFactory connectionFactory,
                                                         RegistrationListener listener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("registrationQueue");
        container.setMessageListener(listener);
        return container;
    }

    @Bean(name = "clientListenerContainer")
    SimpleMessageListenerContainer registeredClientContainer(ConnectionFactory connectionFactory,
                                                             RegisteredClientListener listener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(listener);
        return container;
    }
}
