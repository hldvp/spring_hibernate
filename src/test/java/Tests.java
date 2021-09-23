import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class Tests {
    AnnotationConfigApplicationContext context;

    @Before
    public void setUp() {
        this.context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    public void testSetUsers() {

        UserService userService = context.getBean(UserService.class);



        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru"));



    }

    @Test
    public void testSetUsersWithCar() {
        UserService userService = context.getBean(UserService.class);

        ArrayList<Car> arCar = new ArrayList<>();
        for (int i = 0; i<5; i++) {
            arCar.add(i, new Car("nis" + i, 10 + i));
        }
        userService.add(new User("User1", "Lastname1", "user1@mail.ru", arCar.get(1)));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru", arCar.get(2)));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru", arCar.get(3)));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru", arCar.get(4)));
    }

    @Test
    public void testGetUsers() {
        UserService userService = context.getBean(UserService.class);

        List<User> users = userService.listUsers();

        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println(user.getUserCar());
            System.out.println();
        }
    }

    @Test
    public void testGetById () {
        UserService userService = context.getBean(UserService.class);

        User user = userService.getUserById(7);
         System.out.println(user);

    }

    @Test
    public void testOneToOne () {
        Car car = new Car("Nissan",456);
        User u = new User("Polina", "Kholodova","hldvp@ya.ru");
        u.setUserCar(car);

        UserService userService = context.getBean(UserService.class);
        userService.add(u);
    }

    @Test
    public void test() {
        UserService userService = context.getBean(UserService.class);

        List<User> users = userService.getUserByCar("nis4",14);

        for (User user : users){
            System.out.println(user);
        }

    }
};
