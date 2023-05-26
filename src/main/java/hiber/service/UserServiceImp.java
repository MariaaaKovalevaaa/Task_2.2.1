package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //Аннотация показывает, что класс представляет собой сервис для реализации бизнес-логики.
//и эта аннотация - разновидность @Component, поэтому из этого класса тоже можно получить бин - эта
// аннотация попадает под сканирование
public class UserServiceImp implements UserService {

   @Autowired
   private UserDao userDao;

   @Transactional // Эта аннотация используется д/того, чтобы самим не открывать и не закрыть транзакции.
   // Ставится только в методах Сервиса. Spring ищет, есть ли у нас бин с именем TransactionManager (у нас есть).
   // Если есть, то Spring его найдет и сам подключит. TransactionManager осуществляет старт новой транзакции
   // у метода, аннотированного @Transactional.
   // Чтобы все это было возможно в конфиг-классе ставится аннотация @EnableTransactionManagement
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional (readOnly = true) //это значит, что по умолчанию этот метод сервиса
   // будет выполняться в транзакции только для чтения
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Transactional (readOnly = true)
   @Override
   public User getOwnerByCar(String name, int series){
      return userDao.getOwnerByCar(name, series);
   }
}
