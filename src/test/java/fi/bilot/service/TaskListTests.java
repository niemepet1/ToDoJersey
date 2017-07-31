package fi.bilot.service;

import org.junit.Assert;
import org.junit.Test;

/*
 * Created by Serge Tsyba (serge.tsyba@bilot.fi) on Jul 31, 2017.
 */
public class TaskListTests {
    @Test
    public void testTaskListAddition() {
        final TaskList taskList = new TaskList();
        final int taskCount = taskList.getSize();
        
        final Task task = new Task("Sample task");
        taskList.addTask(task);
        
        final int taskCountAfterAddingTask = taskList.getSize();
        Assert.assertEquals(taskCountAfterAddingTask, taskCount + 1);
    }
}
