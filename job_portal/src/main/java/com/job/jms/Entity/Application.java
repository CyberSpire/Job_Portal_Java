package com.job.jms.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "application")
public class Application {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "application_id")
  private Long aid;

  @ManyToOne
  @JoinColumn(name = "job_id")
  private Job job;

  @ManyToOne
  @JoinColumn(name = "recruiter_id")
  private Recruiter recruiter;

  @ManyToOne
  @JoinColumn(name = "candidate_id")
  private Candidate candidate;

  private String status;
  private Date applicationDate;
  
  // Constructors, getters, and setters
  
	public Long getAid() {
		return aid;
	}
	public void setAid(Long aid) {
		this.aid = aid;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public Recruiter getRecruiter() {
		return recruiter;
	}
	public void setRecruiter(Recruiter recruiter) {
		this.recruiter = recruiter;
	}
	public Candidate getCandidate() {
		return candidate;
	}
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public Application(Long aid, Job job, Recruiter recruiter, Candidate candidate, String status,
			Date applicationDate) 
	{
		super();
		this.aid = aid;
		this.job = job;
		this.recruiter = recruiter;
		this.candidate = candidate;
		this.status = status;
		this.applicationDate = applicationDate;
	}
	
	public Application() 
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() 
	{
      return "Application{" +
              "aid=" + aid +
              ", status='" + status + '\'' +
              ", applicationDate=" + applicationDate +
              '}';
  }
}