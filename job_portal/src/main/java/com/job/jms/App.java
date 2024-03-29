package com.job.jms;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.job.jms.Operations.CandidateOperations;
import com.job.jms.Operations.RecruiterOperations;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Create session factory
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n ------WELCOME TO THE JOB PORTAL------");
                System.out.println("[1] CANDIDATE OPERATIONS");
                System.out.println("[2] RECRUITER OPERATIONS");
                System.out.println("[3] EXIT");
                System.out.print(">> ENTER YOUR CHOICE: ");
                choice = scanner.nextInt();

                
                switch (choice) {
                    case 1:
                        CandidateOperations.performCandidateOperations(session, scanner);
                        break;
                    case 2:
                        RecruiterOperations.performRecruiterOperations(session, scanner);
                        break;
                    case 3:
                        System.out.println("\n<< THANK YOU FOR USING OUR JOB PORTAL... >>\n");
                        break;
                    default:
                        System.out.println("\n### ERROR : Invalid choice. Please enter a valid option.  ###\n");
                        break;
                }
            } while (choice != 3);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close session factory
            sessionFactory.close();
        }
    }
}