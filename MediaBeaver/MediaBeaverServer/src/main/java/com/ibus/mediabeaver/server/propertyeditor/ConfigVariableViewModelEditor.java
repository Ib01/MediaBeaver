package com.ibus.mediabeaver.server.propertyeditor;

import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibus.mediabeaver.server.viewmodel.ConfigVariableViewModel;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;

public class ConfigVariableViewModelEditor extends PropertyEditorSupport
{
	List<ConfigVariableViewModel> configs;
	
	public ConfigVariableViewModelEditor(List<ConfigVariableViewModel> list)
	{
		this.configs = list;
	}
	
	public void setAsText(String id)
	{
		ConfigVariableViewModel selected= new ConfigVariableViewModel();
		for(ConfigVariableViewModel c : configs)
		{
			if(c.getId().equals(id))
			{
				selected = c;
			}
		}
		
		setValue(selected);
	}
}

