package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository //Аннотация показывает, что класс применяется для работы с поиском, для получения и хранения данных.
//И эта аннотация - разновидность @Component, поэтому из этого класса тоже можно получить бин - эта
// аннотация попадает под сканирование
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked") //отключает предупреждения, связанные с "сырыми" типами (Raw Types)
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User", User.class);
      return query.getResultList();
   }
   @Override
   @SuppressWarnings("unchecked")
   public User getOwnerByCar (String model, int series) {
      return sessionFactory.getCurrentSession().createQuery("from User user where user.car.model =: model and " +
              "user.car.series =: series", User.class)
              .setParameter("model", model).setParameter("series", series).uniqueResult();
   }
}
