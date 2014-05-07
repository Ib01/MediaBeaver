package com.ibus.mediabeaver.server.test;

import static org.junit.Assert.assertTrue;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExVariable;
import com.ibus.mediabeaver.core.util.TestHelper;
import com.ibus.mediabeaver.server.util.Mapper;
import com.ibus.mediabeaver.server.viewmodel.ConfigVariableViewModel;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExSelectorViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExVariableViewModel;

public class ModelMapperTests
{
	Mapper mapper = new Mapper();
	
	@Before
	public void beforeTest()
	{
		// start with a fresh schema
		HibernateUtil.createSchema();
	}

	public void StartTransaction()
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
	}

	public void EndTransaction()
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.getTransaction().commit();
	}
	
	
	@Test
	public void mapMediaConfigTest()
	{
		ModelMapper modelMapper = mapper.getMapper();
		
		MediaConfig obj = TestHelper.getMediaConfigFullGraph();
		MediaConfigViewModel vm = modelMapper.map(obj, MediaConfigViewModel.class);
		
		AssertMediaConfigObjsEqual(obj, vm);
		
		assertTrue(true);
	}
	
	
	@Test
	public void mapAndSaveTest()
	{
		ModelMapper modelMapper = mapper.getMapper();

		MediaConfig mc1 = TestHelper.getMediaConfigFullGraph();
		MediaConfigViewModel vm1 = modelMapper.map(mc1, MediaConfigViewModel.class);
		MediaConfig mc2 = modelMapper.map(vm1, MediaConfig.class);
		
		/*Save entity*/
		StartTransaction();
				
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(mc2);
		String mc1Id = mc1.getId();
		
		EndTransaction();
		
		
		/*get entity out and check same as orriginal*/
		StartTransaction();
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		
		MediaConfig mc3 = Repository.getEntity(MediaConfig.class, mc1Id);
		TestHelper.mediaConfigsFullGraphEqual(mc1, mc3);
		
		EndTransaction();
		
		
	}
	
	
	@Test
	public void maUpdateAndSaveTest()
	{
		ModelMapper modelMapper = mapper.getMapper();

		MediaConfig mc1 = TestHelper.getMediaConfigFullGraph();
		MediaConfigViewModel vm1 = modelMapper.map(mc1, MediaConfigViewModel.class);
		vm1.setDescription("maUpdateAndSaveTest");
		MediaConfig mc2 = modelMapper.map(vm1, MediaConfig.class);
		
		/*Save entity*/
		StartTransaction();
				
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(mc2);
		String mc1Id = mc1.getId();
		
		EndTransaction();
		
		
		/*get entity out and check same as orriginal*/
		StartTransaction();
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		
		MediaConfig mc3 = Repository.getEntity(MediaConfig.class, mc1Id);
		assertTrue(mc3.getDescription().equals("maUpdateAndSaveTest"));
		
		EndTransaction();
		
		
	}
	
	
	
	
	// utilities ////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	private void AssertMediaConfigObjsEqual(MediaConfig obj, MediaConfigViewModel vm)
	{
		assertTrue(vm.getAction().equals(obj.getAction()));
		assertTrue(vm.getDescription().equals(obj.getDescription()));
		assertTrue(vm.getDestinationRoot().equals(obj.getDestinationRoot()));
		assertTrue(vm.getExtensionsSelector().equals(obj.getExtensionsSelector()));
		assertTrue(vm.getId().equals(obj.getId()));
		assertTrue(vm.getRelativeDestinationPath().equals(obj.getRelativeDestinationPath()));
		assertTrue(vm.getSourceDirectory().equals(obj.getSourceDirectory()));
		
		assertTrue(vm.getConfigVariables().size() > 0);
		assertTrue(vm.getRegExSelectors().size() > 0);
		
		AssertConfigVariableObjsEqual(obj.getConfigVariables().iterator().next(), vm.getConfigVariables().iterator().next());
		AssertRegExSelectorObjsEqual(obj.getRegExSelectors().iterator().next(),vm.getRegExSelectors().iterator().next());
	}
	
	private void AssertConfigVariableObjsEqual(ConfigVariable obj, ConfigVariableViewModel vm)
	{
		assertTrue(vm.getId().equals(obj.getId()));
		assertTrue(vm.getName().equals(obj.getName()));
		assertTrue(vm.getValue().equals(obj.getValue()));
	}
	
	private void AssertRegExSelectorObjsEqual(RegExSelector obj, RegExSelectorViewModel vm)
	{
		assertTrue(vm.getId().equals(obj.getId()));
		assertTrue(vm.getDescription().equals(obj.getDescription()));
		assertTrue(vm.getExpression().equals(obj.getExpression()));
		
		assertTrue(vm.getVariables().size() > 0);
		AssertRegExVariableObjsEqual(obj.getVariables().iterator().next(),vm.getVariables().iterator().next());
	}
	
	
	private void AssertRegExVariableObjsEqual(RegExVariable obj, RegExVariableViewModel vm)
	{
		assertTrue(vm.getGroupAssembly().equals(obj.getGroupAssembly()));
		assertTrue(vm.getId().equals(obj.getId()));
		assertTrue(vm.getReplaceWithCharacter().equals(obj.getReplaceWithCharacter()));
		assertTrue(vm.getReplaceExpression().equals(obj.getReplaceExpression()));
		
		assertTrue(vm.getConfigVariable() != null);
		assertTrue(obj.getConfigVariable() != null);
		
		AssertConfigVariableObjsEqual(obj.getConfigVariable(), vm.getConfigVariable());
	}
	
	
	
}













