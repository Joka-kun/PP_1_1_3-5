package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users(" +
                    "id bigint AUTO_INCREMENT," +
                    "name varchar(100) ," +
                    "lastName varchar(150) ," +
                    "age int ," +
                    "PRIMARY KEY (id)" +
                    ")").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            sessionFactory.getCurrentSession().beginTransaction().rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            sessionFactory.getCurrentSession().beginTransaction().rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            sessionFactory.getCurrentSession().beginTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            sessionFactory.getCurrentSession().beginTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            list = session.createQuery("select u from User u", User.class).getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            sessionFactory.getCurrentSession().beginTransaction().rollback();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE Users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            sessionFactory.getCurrentSession().beginTransaction().rollback();
        }
    }
}
