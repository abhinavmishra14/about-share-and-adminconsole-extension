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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import com.github.abhinavmishra14.utils.AlfrescoModuleConstants;
import com.github.abhinavmishra14.utils.NetUtility;

/**
 * The Class HostInfoWebscript.
 */
public class HostInfoWebscript extends DeclarativeWebScript {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(HostInfoWebscript.class);

	/**
	 * The Constructor.
	 *
	 */
	public HostInfoWebscript() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.springframework.extensions.webscripts.DeclarativeWebScript#executeImpl(org.springframework.extensions.webscripts.WebScriptRequest, org.springframework.extensions.webscripts.Status, org.springframework.extensions.webscripts.Cache)
	 */
	@Override
	protected Map<String, Object> executeImpl(final WebScriptRequest req,
			final Status status, final Cache cache) {
		final Map<String, Object> result = new HashMap<String, Object>(3);
		try {
			final JSONObject jsonObject = new JSONObject();
			final InetAddress localhost = NetUtility.getLocalHost();
			jsonObject.put(AlfrescoModuleConstants.HOSTNAME, localhost.getHostName());
			jsonObject.put(AlfrescoModuleConstants.IP_ADDRESS, localhost.getHostAddress());
			result.put(AlfrescoModuleConstants.RESULT, jsonObject.toString());
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("Returned host information: {}", result);
			}
		} catch (JSONException | UnknownHostException excp) {
			LOGGER.error("Error while retrieving host information", excp);
		}
		return result;
	}
}
