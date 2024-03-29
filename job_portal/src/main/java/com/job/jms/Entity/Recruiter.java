package com.job.jms.Entity;

import javax.persistence.*;
import org.hibernate.Session;
import java.util.List;

@Entity
@Table(name = "recruiter")
public class Recruiter {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recruiter_id")
  private Long rid;

  private String name;

  @Column(name = "email_id")
  private String email;

  @Column(name = "company_name")
  private String companyName;

  @OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL)
  private List<Job> jobs;

  // Constructors, getters, and setters

  public Recruiter() {
  }

  public Recruiter(String name, String email, String companyName) {
      this.name = name;
      this.email = email;
      this.companyName = companyName;
  }

  public Long getRid() {
      return rid;
  }

  public void setRid(Long rid) {
      this.rid = rid;
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public String getEmail() {
      return email;
  }

  public void setEmail(String email) {
      this.email = email;
  }

  public String getCompanyName() {
      return companyName;
  }

  public void setCompanyName(String companyName) {
      this.companyName = companyName;
  }

  public List<Job> getJobs() {
      return jobs;
  }

  public void setJobs(List<Job> jobs) {
      this.jobs = jobs;
  }
  public void updateApplicationStatus(Session session, Long applicationId, String newStatus) {
		// TODO Auto-generated method stub
		
	}
  @Override
  public String toString() {
      return "Recruiter{" +"rid=" + rid +", name='" + name + '\'' + ", email='" + email + '\'' +", companyName='" + 
  companyName + '\'' +
              '}';
  }
}