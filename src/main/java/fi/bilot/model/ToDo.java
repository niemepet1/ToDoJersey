package fi.bilot.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ToDo
{
	private String id;
	private String summary;
	private String description;

	public ToDo()
	{

	}

	public ToDo(final String id, final String summary)
	{
		this.id = id;
		this.summary = summary;
	}

	public String getId()
	{
		return id;
	}

	public void setId(final String id)
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
