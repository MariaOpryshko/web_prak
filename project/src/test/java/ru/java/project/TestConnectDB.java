package ru.java.project;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@SpringBootTest
public class TestConnectDB {
    @Autowired
    private LocalSessionFactoryBean entityManagerFactory;

    @Test
    public void sessionOpens() {
        SessionFactory sessionFactory = entityManagerFactory.getObject();
        Assertions.assertNotNull(sessionFactory);
        Session session = sessionFactory.openSession();
        Assertions.assertNotNull(session);
    }
}
