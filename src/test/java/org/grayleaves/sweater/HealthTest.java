package org.grayleaves.sweater;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//FIXME would like to use glassfish for serverless testing without introducing glassfish in prod
// would need to instantiate a javax.ws.rs.core.Application for testing, 
// not the subclass org.glassfish.jersey.server.ResourceConfig, as here 
public class HealthTest extends EnvironmentTest {

	@Override
	protected Application configure() {
		return new TestingApiV1App();
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		Map<String, String> newenv = new HashMap<>();
		newenv.put(HealthResponse.INSTANCE_KEY, "1");
		setEnv(newenv); 
		ApiV1App.determineInstance();
}
	
	@Test
	public void healthReturnsStatusUp() {
		HealthResponse healthResponse = target("v1/health").request().get(HealthResponse.class);  
		assertEquals("UP", healthResponse.getStatus()); 
	}
	@Test
	public void healthReturnsInstanceNumber() {
		HealthResponse healthResponse = target("v1/health").request().get(HealthResponse.class);  
		assertEquals("1", healthResponse.getCfInstanceIndex()); 
	}
	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@ApplicationPath("/api/*")
	private class TestingApiV1App extends ResourceConfig {
	    public TestingApiV1App() {
	        packages("org.grayleaves.sweater");
	    }	
    }
}
