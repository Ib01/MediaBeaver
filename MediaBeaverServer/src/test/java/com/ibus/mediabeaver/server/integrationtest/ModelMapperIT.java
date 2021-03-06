package com.ibus.mediabeaver.server.integrationtest;

import org.hibernate.Session;
import org.junit.Before;

import com.ibus.mediabeaver.core.data.HibernateUtil;

public class ModelMapperIT
{
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
	
	
	/*@Test
	public void mapMediaConfigTest()
	{
		ModelMapper modelMapper = Mapper.getMapper();
		
		MediaConfig obj = TestHelper.getMediaConfigFullGraph();
		MediaConfigViewModel vm = modelMapper.map(obj, MediaConfigViewModel.class);
		
		AssertMediaConfigObjsEqual(obj, vm);
		
		assertTrue(true);
	}
	
	
	@Test
	public void mapAndSaveTest()
	{
		ModelMapper modelMapper = Mapper.getMapper();

		MediaConfig mc1 = TestHelper.getMediaConfigFullGraph();
		MediaConfigViewModel vm1 = modelMapper.map(mc1, MediaConfigViewModel.class);
		MediaConfig mc2 = modelMapper.map(vm1, MediaConfig.class);
		
		//Save entity
		StartTransaction();
				
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(mc2);
		String mc1Id = mc1.getId();
		
		EndTransaction();
		
		
		//get entity out and check same as orriginal
		StartTransaction();
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		
		MediaConfig mc3 = Repository.getEntity(MediaConfig.class, mc1Id);
		TestHelper.mediaConfigsFullGraphEqual(mc1, mc3);
		
		EndTransaction();
		
		
	}
	
	
	@Test
	public void mapChangeAndSaveTest()
	{
		ModelMapper modelMapper = Mapper.getMapper();

		MediaConfig mc1 = TestHelper.getMediaConfigFullGraph();
		MediaConfigViewModel vm1 = modelMapper.map(mc1, MediaConfigViewModel.class);
		vm1.setDescription("maUpdateAndSaveTest");
		MediaConfig mc2 = modelMapper.map(vm1, MediaConfig.class);
		
		//Save entity
		StartTransaction();
				
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(mc2);
		String mc1Id = mc1.getId();
		
		EndTransaction();
		
		
		//get entity out and check same as orriginal
		StartTransaction();
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		
		MediaConfig mc3 = Repository.getEntity(MediaConfig.class, mc1Id);
		assertTrue(mc3.getDescription().equals("maUpdateAndSaveTest"));
		
		EndTransaction();
		
		
	}
	
	
	@Test
	public void saveMapAndUpdateMediaConfigTest()
	{
		ModelMapper modelMapper = Mapper.getMapper();
		
		//add mc
		StartTransaction();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c = TestHelper.getMediaConfigFullGraph();
		s.save(c);
		
		MediaConfigViewModel vm = modelMapper.map(c, MediaConfigViewModel.class);
		EndTransaction();
		
		
		//change c in another transaction and update
		StartTransaction();
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		
		MediaConfig c2 = modelMapper.map(vm, MediaConfig.class);
		c2.setDescription("adf");
		s.update(c2);
		String id = c2.getId();
		
		EndTransaction();
		
		
		//check update
		StartTransaction();
		
		MediaConfig c3 = Repository.getEntity(MediaConfig.class, id);
		assert(c3.getDescription().equals("adf"));
		
		EndTransaction();
		
		
		
	}
	
	@Test
	public void saveMapListsTest()
	{
		List<MediaConfig> objL = new ArrayList<MediaConfig>();
		MediaConfig mc1 = TestHelper.getMediaConfigFullGraph();
		MediaConfig mc2 = TestHelper.getMediaConfigFullGraph();
		objL.add(mc1);
		objL.add(mc2);
		
		List<MediaConfigViewModel> vmL = new ArrayList<MediaConfigViewModel>();
		
		ModelMapper modelMapper = Mapper.getMapper();
		modelMapper.map(objL,vmL);
		
		assert(vmL.size() ==2);
	}*/
	
	
	
	
	
	// utilities ////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	/*private void AssertMediaConfigObjsEqual(MediaConfig obj, MediaConfigViewModel vm)
	{
		assertTrue(vm.getAction().equals(obj.getAction()));
		assertTrue(vm.getDescription().equals(obj.getDescription()));
		assertTrue(vm.getDestinationRoot().equals(obj.getDestinationRoot()));
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
	*/
	/*private void AssertRegExSelectorObjsEqual(RegExSelector obj, RegExSelectorViewModel vm)
	{
		assertTrue(vm.getId().equals(obj.getId()));
		assertTrue(vm.getDescription().equals(obj.getDescription()));
		assertTrue(vm.getExpression().equals(obj.getExpression()));
		
		assertTrue(vm.getPathTokenSetters().size() > 0);
		AssertRegExVariableObjsEqual(obj.getPathTokenSetters().iterator().next(),vm.getPathTokenSetters().iterator().next());
	}
	
	
	private void AssertRegExVariableObjsEqual(RegExPathTokenSetter obj, RegExPathTokenSetterViewModel vm)
	{
		assertTrue(vm.getGroupAssembly().equals(obj.getGroupAssembly()));
		assertTrue(vm.getId().equals(obj.getId()));
		assertTrue(vm.getReplaceWithCharacter().equals(obj.getReplaceWithCharacter()));
		assertTrue(vm.getReplaceExpression().equals(obj.getReplaceExpression()));
		assertTrue(vm.getPathTokenName().equals(obj.getPathTokenName()));
		
	}*/
	
	
	
}













