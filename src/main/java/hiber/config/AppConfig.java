package hiber.config;

import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "hiber")
public class AppConfig {

   private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
   private static final String DB_URL = "jdbc:mysql://localhost:3306/mybd";
   private static final String DB_USERNAME = "root";
   private static final String DB_PASSWORD = "11Anlip16!";

   @Autowired
   private Environment env;

   @Bean
   public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getProperty(DB_DRIVER));
      dataSource.setUrl(env.getProperty(DB_URL));
      dataSource.setUsername(env.getProperty(DB_USERNAME));
      dataSource.setPassword(env.getProperty(DB_PASSWORD));
      return dataSource;
   }

   @Bean
   public LocalSessionFactoryBean getSessionFactory() {
      LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
      factoryBean.setDataSource(getDataSource());

      Properties props=new Properties();
      props.put("hibernate.show_sql", env.getProperty("true"));
      props.put("hibernate.hbm2ddl.auto", env.getProperty("update"));

      factoryBean.setHibernateProperties(props);
      factoryBean.setAnnotatedClasses(User.class);
      return factoryBean;
   }

   @Bean
   public HibernateTransactionManager getTransactionManager() {
      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
      transactionManager.setSessionFactory(getSessionFactory().getObject());
      return transactionManager;
   }
}
