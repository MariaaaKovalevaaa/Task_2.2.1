package hiber.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;

   @Column(name = "name")
   private String firstName;

   @Column(name = "last_name")
   private String lastName;

   @Column(name = "email")
   private String email;

   @OneToOne(cascade = CascadeType.ALL) //Cascade операций-это выполнение операции не только для Entity,
   // на котором операция вызывается, но и на связанных с ним Entity. Этот параметр определяет,
   // что должно происходить с зависимыми сущностями, если мы меняем главную сущность.
   // В данном случае ALL означает, что все действия, которые мы выполняем с родительским объектом,
   // нужно повторить и для его зависимых объектов.
   @JoinColumn(name = "car") //Соединительный столбец в таблице users, т.е мы указываем, что для связи
   // с классом Car должен использоваться столбец "car" таблицы users, он будет Foreing Key
   // для столбца "id" таблицы cars, который будет являться Primary Key в самой таблице cars
   private Car car;

   public User() {}

   public User(String firstName, String lastName, String email, Car car) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.car = car;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Car getCar() {
      return car;
   }

   public void setCar(Car car) {
      this.car = car;
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", firstName='" + firstName + '\'' +
              ", lastName='" + lastName + '\'' +
              ", email='" + email + '\'' +
              ", car=" + car +
              '}';
   }
}
