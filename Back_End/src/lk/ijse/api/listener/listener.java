package lk.ijse.api.listener;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

 public class listener implements ServletContextListener {

        @Override
        public void contextInitialized(ServletContextEvent servletContextEvent) {

         ServletContext servletContext = servletContextEvent.getServletContext();
            BasicDataSource pool = new BasicDataSource();

            pool.setDriverClassName("com.mysql.jdbc.Driver");
            pool.setUrl("jdbc:mysql://localhost:3306/company");
            pool.setUsername("root");
            pool.setPassword("root123");
            pool.setInitialSize(40);
            pool.setMaxTotal(40);
            servletContext.setAttribute("dbcp",pool);
        }

        @Override
        public void contextDestroyed(ServletContextEvent servletContextEvent) {
            try {
                ((BasicDataSource)servletContextEvent.getServletContext().getAttribute("dbcp")).close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }