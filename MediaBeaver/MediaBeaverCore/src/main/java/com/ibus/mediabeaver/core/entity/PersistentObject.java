package com.ibus.mediabeaver.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public abstract class PersistentObject implements Serializable
{
	public static final long serialVersionUID = 1L;

	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(updatable = false, nullable = false)
	private String id = IdGenerator.createId();

	
	@Version
	@Column(name = "version")
	private int version = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column()
	private Date lastUpdate;

	protected void copy(final PersistentObject source)
	{
		this.id = source.id;
		this.version = source.version;
		this.lastUpdate = source.lastUpdate;
	}

	public String getId()
	{
		return this.id;
	}

	@SuppressWarnings("unused")
	private void setId(final String id)
	{
		this.id = id;
	}

	public int getVersion()
	{
		return this.version;
	}

	@SuppressWarnings("unused")
	private void setVersion(final int version)
	{
		this.version = version;
	}

	public Date getLastUpdate()
	{
		return this.lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate)
	{
		this.lastUpdate = lastUpdate;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		
		if (o == null || !(o instanceof PersistentObject))
		{
			return false;
		}

		PersistentObject other = (PersistentObject) o;

		// if the id is missing, return false
		if (id == null)
			return false;

		// equivalence by id
		return id.equals(other.getId());
	}

	public int hashCode()
	{
		if (id != null)
		{
			return id.hashCode();
		} else
		{
			return super.hashCode();
		}
	}

	public String toString()
	{
		return this.getClass().getName() + "[id=" + id + "]";
	}

}