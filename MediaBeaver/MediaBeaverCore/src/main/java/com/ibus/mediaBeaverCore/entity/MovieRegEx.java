package com.ibus.mediaBeaverCore.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "Movie_Reg_Ex")
public class MovieRegEx implements Serializable,
		com.ibus.mediaBeaverCore.entity.Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty(message = "This field cannot be left empty")
	@Column
	private String expression;

	@Valid
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "assembledItem", column = @Column(name = "name_assembledItem")),
			@AttributeOverride(name = "removeCharacters", column = @Column(name = "name_removeCharacters")),
			@AttributeOverride(name = "recursiveRegEx", column = @Column(name = "name_recursiveRegEx")),
			@AttributeOverride(name = "cleaningRegEx", column = @Column(name = "name_cleaningRegEx")) })
	private RegExItemParser nameParser = new RegExItemParser();

	@Valid
	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "assembledItem", column = @Column(name = "year_assembledItem")),
			@AttributeOverride(name = "removeCharacters", column = @Column(name = "year_removeCharacters")),
			@AttributeOverride(name = "recursiveRegEx", column = @Column(name = "year_recursiveRegEx")),
			@AttributeOverride(name = "cleaningRegEx", column = @Column(name = "year_cleaningRegEx")) })
	private RegExItemParser yearParser = new RegExItemParser();

	@NotEmpty(message = "This field cannot be left empty")
	@Column
	private String testFileName;

	@Column
	private String generatedName;

	@Column
	private String generatedYear;

	@ManyToOne(fetch = FetchType.EAGER)
	private MediaTransformConfig parentConfig;

	@Transient
	private final List<JsonError> errors = new ArrayList<JsonError>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public RegExItemParser getNameParser() {
		return nameParser;
	}

	public void setNameParser(RegExItemParser nameParser) {
		this.nameParser = nameParser;
	}

	public RegExItemParser getYearParser() {
		return yearParser;
	}

	public void setYearParser(RegExItemParser yearParser) {
		this.yearParser = yearParser;
	}

	public String getTestFileName() {
		return testFileName;
	}

	public void setTestFileName(String testFileName) {
		this.testFileName = testFileName;
	}

	public String getGeneratedName() {
		return generatedName;
	}

	public void setGeneratedName(String generatedName) {
		this.generatedName = generatedName;
	}

	public String getGeneratedYear() {
		return generatedYear;
	}

	public void setGeneratedYear(String generatedYear) {
		this.generatedYear = generatedYear;
	}

	public List<JsonError> getErrors() {
		return errors;
	}

	public MediaTransformConfig getParentConfig() {
		return parentConfig;
	}

	public void setParentConfig(MediaTransformConfig parentConfig) {
		this.parentConfig = parentConfig;
	}

	public boolean fieldHasError(String field) {
		for (JsonError e : errors) {
			if (e.getField().equals(field))
				return true;
		}

		return false;
	}

}
