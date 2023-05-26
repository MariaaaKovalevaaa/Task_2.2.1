package hiber.config;

import hiber.model.Car;
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


@Configuration //Говорит о том, что это конфигурационный класс
@PropertySource("classpath:db.properties") //Указываем, где брать свойства
@EnableTransactionManagement // Аннотация @EnableTransactionManagement означает, что классы,
// помеченные @Transactional, должны быть обернуты аспектом транзакций.
// Эта аннотация активирует возможности Spring бесшовной транзакции через @Transactional
@ComponentScan(value = "hiber") //Указываем какой пакет сканировать на предмет наличия
// аннотаций @Component, @Service, @Repository
public class AppConfig {

   @Autowired //Подключаем зависимость
   private Environment env; //Интерфейс Environment - это окружение, в котором приложение запущено.

   //Бин этого класса ответственен за подключение к БД
   @Bean
   public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getProperty("db.driver"));
      dataSource.setUrl(env.getProperty("db.url"));
      dataSource.setUsername(env.getProperty("db.username"));
      dataSource.setPassword(env.getProperty("db.password"));
      return dataSource;
   }


   //Бин этого класса, ответственен за создание SessionFactory - фабрики по производству сессий
   @Bean
   public LocalSessionFactoryBean getSessionFactory() {
      LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
      factoryBean.setDataSource(getDataSource());
      
      Properties props=new Properties();
      props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
      props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
      props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
//      props.put("hibernate.CURRENT_SESSION_CONTEXT_CLASS", env.getProperty("hibernate.CURRENT_SESSION_CONTEXT_CLASS"));

      factoryBean.setHibernateProperties(props);
      factoryBean.setAnnotatedClasses(User.class, Car.class);
      return factoryBean;
   }

   //Бин этого класса ответственен за осуществление старта транзакции у методов, аннотированных @Transactional
   @Bean
   public HibernateTransactionManager getTransactionManager() {
      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
      transactionManager.setSessionFactory(getSessionFactory().getObject());
      return transactionManager;
   }
}
