package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
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
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public void findUserByCars(String sqlQuery) {

        Transaction transaction = null;
        User user = null;

        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();

            Query query = session.createQuery("from users where series = :series");
            query.setParameter("series", sqlQuery);
            user = (User) query.getSingleResult();
            System.out.println(user.toString());
            transaction.commit();
        } catch (Exception e) {
            e.getStackTrace();
            transaction.rollback();
        }
    }
}
