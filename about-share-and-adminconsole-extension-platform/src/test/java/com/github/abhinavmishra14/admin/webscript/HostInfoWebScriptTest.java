/*
 * Created By: Abhinav Kumar Mishra
 * Copyright &copy; 2019. Abhinav Kumar Mishra. 
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.abhinavmishra14.admin.webscript;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

/**
 * The Class HostInfoWebScriptTest.
 */
public class HostInfoWebScriptTest {
	
	/**
	 * Test controller.
	 */
	@Test
	public void testController() {
		final WebScriptRequest req = Mockito.mock(WebScriptRequest.class);
		final Status status = Mockito.mock(Status.class);
		final Cache cache = Mockito.mock(Cache.class);

		final HostInfoWebscript ws = new HostInfoWebscript();
		final Map<String, Object> model = ws.executeImpl(req, status, cache);

		assertNotNull("Response from Web Script Java Controller is null", model);
		assertEquals("Incorrect Web Script Java Controller Response",
				true, model.get("result").toString().contains("hostName"));
	}
}
