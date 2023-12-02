package ru.itis.listener;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.repository.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class CustomServletContextListener implements ServletContextListener {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "erfan443565";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/semestrovka";
    private static final String DB_DRIVER = "org.postgresql.Driver";
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_PASSWORD);
        dataSource.setUrl(DB_URL);

        ServletContext servletContext = servletContextEvent.getServletContext();

        AccountRepository accountRepository = new AccountRepositoryJdbcImpl(dataSource);
        servletContext.setAttribute("accountRep", accountRepository);
        SignUpService signUpService = new SignUpServiceImpl(accountRepository);
        servletContext.setAttribute("signService", signUpService);
        ArticleService articleService = new ArticleServiceJdbcImpl(dataSource);
        servletContext.setAttribute("artService", articleService);
        SettingsRepository settingsRepository = new SettingsRepositoryJdbcImpl(dataSource);
        servletContext.setAttribute("settingService", settingsRepository);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
