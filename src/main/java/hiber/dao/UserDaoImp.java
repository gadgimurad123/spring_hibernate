package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
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
        TypedQuery<User> query = sessionFactory
                .getCurrentSession()
                .createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User findUserByCars(String model, String seriesOfCar) {
        // В прошлом коммите есть вариант поиска только
        // по серии машины и он более органичный (как мне кажется),
        // но в условии написано искать по модели и серии, поэтому так

        User user = (User) sessionFactory
                .getCurrentSession()
                .createQuery("from User where car_series = :car_series")
                .setParameter("car_series", seriesOfCar)
                .getSingleResult();

        if (user.getCar().getModel().equals(model)) {
            return user;
        }
        return null;
    }
}
