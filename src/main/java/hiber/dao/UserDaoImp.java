package hiber.dao;

import hiber.config.AppConfig;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.*;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserById(long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public List<User> getUserByCar(String model, int series) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(
                "from Car where model =:paramModel and series =:paramSeries")
                .setParameter("paramModel", model)
                .setParameter("paramSeries", series);
        List<Long> list = query.list();
        query = session.createQuery("from User where userCar in :param")
                .setParameter("param",list);

        return query.getResultList();
    }
}
