package fi.bilot.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

/*
 * Created by Serge Tsyba (serge.tsyba@bilot.fi) on Jul 31, 2017.
 */
public class TaskListTests {
    @Test
    public void testTaskListAddition() {
        final TaskList taskList = new TaskList();
        final int taskCount = taskList.getSize();

        taskList.createTask("Sample task");

        final int taskCountAfterAddingTask = taskList.getSize();
        Assert.assertEquals(taskCountAfterAddingTask, taskCount + 1);
    }

    @Test
    public void testTaskListDeletion() {
        final TaskList taskList = new TaskList();
        final Task task = taskList.createTask("Sample task");
        taskList.deleteTask(task);

        final int taskCountAfterDeletingTask = taskList.getSize();
        Assert.assertEquals(0, taskCountAfterDeletingTask);
    }

    @Test
    public void testIncompleteTasks() {
        final TaskList taskList = new TaskList();
        final Task task1 = taskList.createTask("First Task");
        final Task task2 = taskList.createTask("Second Task");

        task2.setCompleted();

        final Collection<Task> incompleteTasks = taskList.getIncompleteTasks();

        Assert.assertEquals( 1, incompleteTasks.size());
        Assert.assertEquals(task1, incompleteTasks.iterator().next());
    }
}
