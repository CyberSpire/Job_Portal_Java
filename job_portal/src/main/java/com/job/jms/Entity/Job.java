package com.job.jms.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "job")
public class Job {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "job_id")
  private Long jid;

  @ManyToOne
  @JoinColumn(name = "recruiter_id")
  private Recruiter recruiter;

  @Column(name = "job_role")
  private String jobRole;

  @Temporal(TemporalType.DATE)
  @Column(name = "posted_date")
  private Date postedDate;

  private String description;
  private String address;
  private double salary;

  @OneToMany(mappedBy = "job", fetch = FetchType.EAGER)
  private List<Application> applications;

  // Constructor with default postedDate as current date
  public Job() {
      this.postedDate = new Date(); // Set postedDate to current date when the job is created
  }

  // Constructors, getters, and setters
  
	public Long getJid() {
		return jid;
	}

	public void setJid(Long jid) {
		this.jid = jid;
	}

	public Recruiter getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(Recruiter recruiter) {
		this.recruiter = recruiter;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	public List<Candidate> getApplicants() {
	    List<Candidate> applicants = new ArrayList<>();
	    if (applications != null) {
	        for (Application application : applications) {
	            applicants.add(application.getCandidate());
	        }
	    }
	    return applicants;
	}

	public Job(Long jid, Recruiter recruiter, String jobRole, Date postedDate, String description, String address,
			double salary, List<Application> applications) 
	{
		super();
		this.jid = jid;
		this.recruiter = recruiter;
		this.jobRole = jobRole;
		this.postedDate = postedDate;
		this.description = description;
		this.address = address;
		this.salary = salary;
		this.applications = applications;
	}

	 @Override
	    public String toString() 
	 {
	        return "Job [jid=" + jid + ", recruiter=" + recruiter + ", jobRole=" + jobRole + ", postedDate=" + postedDate
	                + ", description=" + description + ", address=" + address + ", salary=" + salary + "]";
	 }
}