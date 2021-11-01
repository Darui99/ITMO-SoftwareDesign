package ru.kurbatov.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kurbatov.mvc.dao.*;

@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public TaskDao taskDao() {
        return new TaskInMemoryDao();
    }

    @Bean
    public TodoListDao listDao() {
        return new TodoListInMemoryDao();
    }
}
