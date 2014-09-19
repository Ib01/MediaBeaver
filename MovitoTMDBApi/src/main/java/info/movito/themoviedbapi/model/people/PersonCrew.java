package info.movito.themoviedbapi.model.people;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PersonCrew extends Person {


    @JsonProperty("department")
    private String department;

    @JsonProperty("job")
    private String job;


    public String getDepartment() {
        return department;
    }


    public String getJob() {
        return job;
    }


    public void setDepartment(String department) {
        this.department = StringUtils.trimToEmpty(department);
    }


    public void setJob(String job) {
        this.job = StringUtils.trimToEmpty(job);
    }
}
