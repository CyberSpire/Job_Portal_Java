package com.job.jms.DaoImpl;

import com.job.jms.Dao.CandidateDAO;
import com.job.jms.Entity.Application;
import com.job.jms.Entity.Candidate;
import com.job.jms.Entity.Job;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class CandidateDAOImpl implements CandidateDAO {

    private final SessionFactory sessionFactory;

    public CandidateDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addCandidate(Candidate candidate) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(candidate);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteCandidate(long candidateId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Candidate candidate = session.get(Candidate.class, candidateId);
            if (candidate != null) {
                session.delete(candidate);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateCandidate(Candidate candidate) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(candidate);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Job> viewJobs() {
        try (Session session = sessionFactory.openSession()) {
            Query<Job> query = session.createQuery("FROM Job", Job.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void applyJob(long candidateId, long jobId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            // Load the Candidate and Job entities from the database
            Candidate candidate = session.get(Candidate.class, candidateId);
            Job job = session.get(Job.class, jobId);

            // Create a new Application
            Application application = new Application();
            application.setCandidate(candidate);
            application.setJob(job);

            // Save the Application
            session.save(application);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Handle or log the exception
        } finally {
            session.close();
        }
    }

    @Override
    public String viewApplicationStatus(long candidateId, long jobId) {
        String status = null;
        Session session = sessionFactory.openSession();

        try {
            // Create HQL query to retrieve application status
            String hql = "SELECT a.status FROM Application a WHERE a.candidate.cid = :candidateId AND a.job.jid = :jobId";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("candidateId", candidateId);
            query.setParameter("jobId", jobId);
            // Execute query
            List<String> results = query.getResultList();
            if (!results.isEmpty()) {
                // Assuming there's only one application status for a candidate and a job
                status = results.get(0);
            } else {
                // No application status found
                status = "No application status found";
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle or log the exception
        } finally {
            session.close();
        }

        return status;
    }


    @Override
    public Candidate getCandidateById(long candidateId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Candidate candidate = null;
        try {
            transaction = session.beginTransaction();
            candidate = session.get(Candidate.class, candidateId);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return candidate;
    }
}