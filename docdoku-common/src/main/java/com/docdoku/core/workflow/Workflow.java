/*
 * DocDoku, Professional Open Source
 * Copyright 2006 - 2013 DocDoku SARL
 *
 * This file is part of DocDokuPLM.
 *
 * DocDokuPLM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DocDokuPLM is distributed in the hope that it will be useful,  
 * but WITHOUT ANY WARRANTY; without even the implied warranty of  
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  
 * GNU Affero General Public License for more details.  
 *  
 * You should have received a copy of the GNU Affero General Public License  
 * along with DocDokuPLM.  If not, see <http://www.gnu.org/licenses/>.  
 */
package com.docdoku.core.workflow;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Workflows organize tasks around documents on which they're applied to.  
 * 
 * @author Florent Garin
 * @version 1.0, 02/06/08
 * @since   V1.0
 */
@Table(name="WORKFLOW")
@javax.persistence.Entity
public class Workflow implements Serializable, Cloneable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("step ASC")
    private List<Activity> activities = new LinkedList<>();

    @javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date abortedDate;

    private String finalLifeCycleState;

    public Workflow(String pFinalLifeCycleState) {
        finalLifeCycleState = pFinalLifeCycleState;
    }

    public Workflow() {
    }

    public Collection<Task> getRunningTasks() {

        Activity current = getCurrentActivity();
        if (current != null) {
            return current.getOpenTasks();
        } else {
            return new ArrayList<>();
        }
    }

    public int getCurrentStep() {
        int i = 0;
        for (Activity activity : activities) {
            if (activity.isComplete()) {
                i++;
            } else {
                break;
            }
        }
        return i;
    }

    public Activity getCurrentActivity() {
        if (getCurrentStep() < activities.size()) {
            return getActivity(getCurrentStep());
        } else {
            return null;
        }
    }

    public String getFinalLifeCycleState() {
        return finalLifeCycleState;
    }

    public int getId() {
        return id;
    }

    public java.util.List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(java.util.List<Activity> activities) {
        this.activities = activities;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFinalLifeCycleState(String finalLifeCycleState) {
        this.finalLifeCycleState = finalLifeCycleState;
    }

    public int numberOfSteps() {
        return activities.size();
    }

    public List<String> getLifeCycle() {
        List<String> lc = new LinkedList<>();
        for (Activity activity : activities) {
            lc.add(activity.getLifeCycleState());
        }

        return lc;
    }

    public String getLifeCycleState() {
        Activity current = getCurrentActivity();
        return current == null ? finalLifeCycleState : current.getLifeCycleState();
    }

    public Activity getActivity(int pIndex) {
        return activities.get(pIndex);
    }

    public void abort() {
        for (Activity activity : activities) {
            for(Task task:activity.getTasks()){
                task.stop();
            }
        }
        this.setAbortedDate(new Date());
    }

    public void relaunch(int relaunchActivityStep) {

        for(Activity a :activities){
            if(a.getStep() < relaunchActivityStep){ 
                for(Task t : a.getTasks()){
                    t.reset(Task.Status.NOT_TO_BE_DONE);
                }
            }
            if(a.getStep() >= relaunchActivityStep){
                for(Task t : a.getTasks()){
                    t.reset(Task.Status.NOT_STARTED);
                }
            }
        }

        Activity currentActivity = activities.get(relaunchActivityStep);
        currentActivity.relaunch();

    }

    public Date getAbortedDate() {
        return (abortedDate!=null) ? (Date) abortedDate.clone() : null;
    }

    public void setAbortedDate(Date abortedDate) {
        this.abortedDate = (abortedDate!=null) ? (Date) abortedDate.clone() : null;
    }

    /**
     * perform a deep clone operation
     */
    @Override
    public Workflow clone() {
        Workflow clone;
        try {
            clone = (Workflow) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
        //perform a deep copy
        List<Activity> clonedActivities = new LinkedList<>();
        for (Activity activity : activities) {
            Activity clonedActivity = activity.clone();
            clonedActivity.setWorkflow(clone);
            clonedActivities.add(clonedActivity);
        }
        clone.activities = clonedActivities;

        if (abortedDate != null) {
            clone.abortedDate = (Date) abortedDate.clone();
        }
        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Workflow)) {
            return false;
        }
        Workflow workflow = (Workflow) obj;
        return workflow.id == id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
