//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.30 at 09:14:47 PM EST 
//

package com.ibus.tmdb.client.domain;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Image")
public class Image 
{
	@Id
	private Long dsId;
	/*@XmlTransient
	private com.googlecode.objectify.Key<Movie> parent;*/
	
	@XmlAttribute
    protected String type;
	@XmlAttribute
	protected String size;
	@XmlAttribute
	protected String url;
	@XmlAttribute
	protected String id;
	@XmlAttribute
	protected String width;
	@XmlAttribute
	protected String height;

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String value) {
        this.size = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String value) {
        this.url = value;
    }

    public String getImageId() {
        return id;
    }
    
    public void setImageId(String value) {
        this.id = value;
    }

    public Long getId() {
        return dsId;
    }
    
    public void setId(Long value) {
        this.dsId = value;
    }
    
/*	public com.googlecode.objectify.Key<Movie> getParent() {
		return parent;
	}

	public void setParent(com.googlecode.objectify.Key<Movie> parent) {
		this.parent = parent;
	}*/

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
	
	

}
