package fi.bilot.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ToDo
{
	private int id;
	private String summary;
	private String description;

	public ToDo()
	{

	}

	public ToDo(final int id, final String summary)
	{
		this.id = id;
		this.summary = summary;
	}

	public ToDo(final int id, final String summary, final String description)
	{
		this.id = id;
		this.summary = summary;
		this.description = description;
	}

	public int getId()
	{
		return id;
	}

	public void setId(final int id)
	{
		this.id = id;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(final String summary)
	{
		this.summary = summary;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}
}
