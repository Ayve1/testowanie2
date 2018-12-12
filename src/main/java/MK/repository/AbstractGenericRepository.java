package MK.repository;

import MK.exceptions.MyException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGenericRepository<T> implements GenericRepository<T> {

    protected final SessionFactory sessionFactory = DbConnection.getInstance().getSessionFactory();
    private final Class<T> type
            = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    /**
     *
     *
     * @param t
     * @return
     */

    @Override
    public T saveOrUpdate(T t) {
        Session session = null;
        Transaction tx = null;
        T item = null;
        try {
            if (t == null) {
                throw new NullPointerException("ITEM IS NULL");
            }

            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            /*item = (T) */session.saveOrUpdate(t);
            tx.commit();
        } catch (Exception e) {
            throw new MyException("SAVEORUPDATE EXCEPTION", LocalDateTime.now());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return t;
    }

    @Override
    public void delete(Long id) {
        Session session = null;
        Transaction tx = null;
        try {
            if (id == null) {
                throw new NullPointerException("ID IS NULL");
            }

            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            T item = session.get(type, id);
            session.delete(item);
            tx.commit();
        } catch (Exception e) {
            throw new MyException("DELETE EXCEPTION", LocalDateTime.now());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteAll() {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            List<T> items = session
                    .createQuery("select t from " + type.getCanonicalName() + " t", type)
                    .getResultList();
            for (T i : items) {
                session.delete(i);
            }
            tx.commit();
        } catch (Exception e) {
            throw new MyException("DELETE ALL EXCEPTION", LocalDateTime.now());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<T> findOne(Long id) {
        Optional<T> item = Optional.empty();
        Session session = null;
        Transaction tx = null;
        try {
            if (id == null) {
                throw new NullPointerException("ID IS NULL");
            }
            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            item = Optional.ofNullable(session.get(type, id));
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("FIND ONE EXCEPTION", LocalDateTime.now());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return item;
    }

    @Override
    public List<T> findAll() {
        List<T> items = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            items = session
                    .createQuery("select t from " + type.getCanonicalName() + " t", type)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            throw new MyException( "FIND ALL EXCEPTION", LocalDateTime.now());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return items;
    }

    public boolean update(T t) {
        T item = null;
        Session session = null;
        Transaction tx;
        try {
            if (t == null) {
                throw new NullPointerException("ID IS NULL");
            }
            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            session.update(t);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("FIND ONE EXCEPTION", LocalDateTime.now());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return true;
    }
}
