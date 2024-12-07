package com.klef.project.service;

import java.util.List;

import com.klef.project.model.Job;

public interface JobService {
	public String addjob(Job j);
	  public String updatejob(Job j);
	  public Job viewjobbyid(long id);
	  public List<Job> viewAllJobs();
	  public String deleteJob(long id);
}
