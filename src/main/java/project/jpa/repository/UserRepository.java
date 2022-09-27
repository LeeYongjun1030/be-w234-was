package project.jpa.repository;

import project.jpa.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserRepository {

    private final EntityManagerFactory emf;

    public UserRepository() {
        this.emf = Persistence.createEntityManagerFactory("java-was-2022");
    }

    public String save(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        return user.getUserId();
    }

    public User findById(String userId) {
        EntityManager em = emf.createEntityManager();
        return em.find(User.class, userId);
    }

    public List<User> findAll() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select u from USER u", User.class).getResultList();
    }
}
