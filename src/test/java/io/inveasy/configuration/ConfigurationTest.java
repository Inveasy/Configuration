/*
 * Copyright 2018 Guillaume Gravetot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.inveasy.configuration;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Test(suiteName = "Unit tests", testName = "Configuration wrapper tests", description = "Unit tests of the Typesafe config wrapper class", groups = "unit-tests")
public class ConfigurationTest
{
	@Test
	public void testBasicConstruction()
	{
		assertNotNull(Configuration.getInstance());
	}
	
	@Test
	public void testPreReferenceLoading()
	{
		assertEquals(Configuration.getInstance().get("unit.test.key1"), "present");
	}
	
	@Test
	public void testApplicationLoading()
	{
		assertEquals(Configuration.getInstance().get("unit.test.key3"), "present");
	}
	
	@Test
	public void testOverride()
	{
		assertFalse(Configuration.getInstance().getBoolean("unit.test.key2"));
	}
}
