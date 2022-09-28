package project.jpa.repository;

import project.jpa.entity.Memo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class MemoRepository {
    private final EntityManagerFactory emf;

    public MemoRepository() {
        this.emf = Persistence.createEntityManagerFactory("java-was-2022");
    }

    public Long save(Memo memo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(memo);
        em.getTransaction().commit();
        return memo.getId();
    }

    public Memo findById(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Memo.class, id);
    }

    public List<Memo> findAll() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("select m from MEMO m", Memo.class).getResultList();
    }
}
