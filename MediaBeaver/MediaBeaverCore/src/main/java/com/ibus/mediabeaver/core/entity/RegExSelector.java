package com.ibus.mediabeaver.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "RegEx_Selector")
public class RegExSelector extends PersistentObject
{
	private static final long serialVersionUID = 1L;

	@Column
	private String description;
	
	@Column
	@NotEmpty(message = "This field cannot be left empty")
	private String expression;

	@Column
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@Cascade({CascadeType.ALL})
	private Set<RegExPathTokenSetter> pathTokenSetters = new HashSet<RegExPathTokenSetter>();
	
	@Column
	private int sorOrder;
	
	
	//TODO: DELETE?
	@Column
	private FileSystemType fileSystemTarget;

	public String getExpression()
	{
		return expression;
	}

	public void setExpression(String expression)
	{
		this.expression = expression;
	}
	
	//TODO RENAME
	public Set<RegExPathTokenSetter> getPathTokenSetters()
	{
		return pathTokenSetters;
	}

	//TODO RENAME
	public void setPathTokenSetters(Set<RegExPathTokenSetter> pathTokens)
	{
		this.pathTokenSetters = pathTokens;
	}
	
	public void addPathTokenSetter(RegExPathTokenSetter var)
	{
		getPathTokenSetters().add(var);
	}


	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getSorOrder()
	{
		return sorOrder;
	}

	public void setSorOrder(int sorOrder)
	{
		this.sorOrder = sorOrder;
	}

	

}
