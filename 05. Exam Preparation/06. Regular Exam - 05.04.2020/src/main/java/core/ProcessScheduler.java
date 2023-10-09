package core;

import model.Task;
import shared.Scheduler;

import java.util.*;
import java.util.stream.Collectors;

public class ProcessScheduler implements Scheduler {
    private Map<Integer, Task> tasksById;

    private LinkedList<Task> processedTasks;
    int storedTaskNumber;


    public ProcessScheduler() {
        this.tasksById = new LinkedHashMap<>();
        this.processedTasks = new LinkedList<>();
    }

    @Override
    public void add(Task task) {
        if (this.tasksById.containsKey(task.getId())) {
            throw new IllegalArgumentException();
        }

        this.tasksById.put(task.getId(), task);
        this.storedTaskNumber++;

        this.processedTasks.offer(task);
    }

    @Override
    public Task process() {
        Task taskForRemoving = this.processedTasks.pop();
        this.tasksById.remove(taskForRemoving.getId());
        this.storedTaskNumber--;
        return taskForRemoving;
    }

    @Override
    public Task peek() {
        return this.processedTasks.peek();
    }

    @Override
    public Boolean contains(Task task) {
        return this.tasksById.containsKey(task.getId());
    }

    @Override
    public int size() {
        return this.storedTaskNumber;
    }

    @Override
    public Boolean remove(Task task) {
        Task searchedTask = this.tasksById.get(task.getId());

        if (searchedTask == null) {
            throw new IllegalArgumentException();
        }

        this.tasksById.remove(searchedTask.getId());
        this.processedTasks.remove(searchedTask);

        this.storedTaskNumber--;

        return true;
    }

    @Override
    public Boolean remove(int id) {
        Task searchedTask = this.tasksById.get(id);

        if (searchedTask == null) {
            throw new IllegalArgumentException();
        }

        this.tasksById.remove(searchedTask.getId());
        this.processedTasks.remove(searchedTask);

        this.storedTaskNumber--;

        return true;
    }

    @Override
    public void insertBefore(int id, Task task) {
        Task taskAfter = this.tasksById.get(id);

        int indexOfInserting = this.processedTasks.indexOf(taskAfter) - 1;
        this.processedTasks.set(indexOfInserting, task);
        storedTaskNumber++;

        this.tasksById.put(task.getId(), task);
    }

    @Override
    public void insertAfter(int id, Task task) {
          Task taskBefore = this.tasksById.get(id);
          int indexOfInserting = this.processedTasks.indexOf(taskBefore) + 1;
          this.processedTasks.set(indexOfInserting, task);
          storedTaskNumber++;

          this.tasksById.put(task.getId(), task);
    }

    @Override
    public void clear() {
        this.processedTasks.clear();
        this.tasksById = new LinkedHashMap<>();
        storedTaskNumber = 0;
    }

    @Override
    public Task[] toArray() {
        return (Task[]) this.tasksById.values().toArray();
    }

    @Override
    public void reschedule(Task first, Task second) {
        if (!this.tasksById.containsKey(first.getId()) || !this.tasksById.containsKey(second.getId())) {
            throw new IllegalArgumentException();
        }

        int firstIndex = this.processedTasks.indexOf(first);
        int secondIndex = this.processedTasks.indexOf(second);

        processedTasks.set(firstIndex, second);
        processedTasks.set(secondIndex, first);
    }

    @Override
    public List<Task> toList() {
        return new ArrayList<>(this.tasksById.values());
    }

    @Override
    public void reverse() {
        LinkedList<Task> linkedList = new LinkedList<>();

        for (int i = storedTaskNumber - 1;  i >= 0 ; i--) {
            linkedList.add(this.processedTasks.get(i));
        }

        this.processedTasks = linkedList;
    }

    @Override
    public Task find(int id) {
        if (!this.tasksById.containsKey(id)) {
            throw new IllegalArgumentException();
        }

        return this.tasksById.get(id);
    }

    @Override
    public Task find(Task task) {
        if (!this.tasksById.containsKey(task.getId())) {
            throw new IllegalArgumentException();
        }

        return this.tasksById.get(task.getId());
    }
}
