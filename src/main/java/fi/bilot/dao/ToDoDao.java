package fi.bilot.dao;

import java.util.HashMap;
import java.util.Map;

import fi.bilot.model.ToDo;


public enum ToDoDao
{
	instance;

	private final Map<String, ToDo> contentProvider = new HashMap<>();

	private ToDoDao()
	{
		ToDo todo = new ToDo("1", "Learn REST");
		todo.setDescription("Read http://www.vogella.com/tutorials/REST/article.html");
		contentProvider.put("1", todo);
		todo = new ToDo("2", "Learn Postgres");
		todo.setDescription("Read how to use Postgres with Java");
		contentProvider.put("2", todo);
	}

	public Map<String, ToDo> getModel()
	{
		return contentProvider;
	}
}
