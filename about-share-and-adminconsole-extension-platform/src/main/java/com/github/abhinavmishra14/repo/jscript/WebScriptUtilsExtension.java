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
package com.github.abhinavmishra14.repo.jscript;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.alfresco.repo.web.scripts.RepositoryContainer;
import org.alfresco.service.cmr.admin.RepoAdminService;
import org.alfresco.service.cmr.admin.RepoUsage;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.web.scripts.WebScriptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.extensions.webscripts.WebScript;

import com.github.abhinavmishra14.utils.NetUtility;

/**
 * Override of the JavaScript API ScriptUtils bean "utilsScript" to provide additional <br>
 * Remote API methods using objects not available to base Repository project. <br>
 * Mainly added getHostAddress and getHostName methods to facilitate the actual LAN address 
 * lookup instead of using JDK's InetAddress.getLocalHost().getHostAddress() and InetAddress.getLocalHost().getHostName()
 * which is ambiguous for Linux environments.
 */
public class WebScriptUtilsExtension extends WebScriptUtils {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(WebScriptUtilsExtension.class);

	/** The repository container. */
	private RepositoryContainer repositoryContainer;
	
	/** The repo admin service. */
	private RepoAdminService repoAdminService;

	/**
	 * Searches for webscript components with the given family name.<br>
	 * AS IS from org.alfresco.web.scripts.WebScriptUtils
	 * 
	 * @param family the family
	 * @return An array of webscripts that match the given family name
	 */
	public Object[] findWebScripts(final String family) {
		final List<Object> values = new ArrayList<Object>();
		for (final WebScript webscript : this.repositoryContainer.getRegistry().getWebScripts()) {
			if (family != null) {
				final Set<String> familys = webscript.getDescription().getFamilys();
				if (familys != null && familys.contains(family)) {
					values.add(webscript.getDescription());
				}
			} else {
				values.add(webscript.getDescription());
			}
		}
		return values.toArray(new Object[values.size()]);
	}

	/**
	 * Gets the host address.
	 *
	 * @return the host address
	 */
	public String getHostAddress() {
		try {
			LOGGER.info("getHostAddress invoked..");
			return NetUtility.getLocalHost().getHostAddress();
		} catch (UnknownHostException unknownHostex) {
			LOGGER.error("Exception occurred while scanning for LAN Host Address", unknownHostex);
			return "Unknown";
		}
	}

	/**
	 * Gets the host name.
	 *
	 * @return the host name
	 */
	public String getHostName() {
		try {
			LOGGER.info("getHostName invoked..");
			return NetUtility.getLocalHost().getHostName();
		} catch (UnknownHostException unknownHostex) {
			LOGGER.error("Exception occurred while scanning for HOSTName", unknownHostex);
			return "Unknown";
		}
	}
	
	/**
	 * Gets the restrictions. <br>
	 * AS IS from org.alfresco.web.scripts.WebScriptUtils
	 *
	 * @return the restrictions
	 */
	public RepoUsage getRestrictions() {
		return this.repoAdminService.getRestrictions();
	}

	/**
	 * Gets the usage.<br>
	 * AS IS from org.alfresco.web.scripts.WebScriptUtils
	 *
	 * @return the usage
	 */
	public RepoUsage getUsage() {
		return this.repoAdminService.getUsage();
	}

	/**
	 * Gets the list of repository stores<br>
	 * AS IS from org.alfresco.web.scripts.WebScriptUtils
	 * 
	 * @return stores
	 */
	public List<StoreRef> getStores() {
		return this.services.getNodeService().getStores();
	}
	
	/**
	 * Sets the repo admin service.
	 *
	 * @param repoAdminService the new repo admin service
	 */
	public final void setRepoAdminService(final RepoAdminService repoAdminService) {
		//Added this setter methods as RepoAdminService is deprecated via ServiceRegistry
		this.repoAdminService = repoAdminService;
	}

	/**
	 * Sets the repository container.
	 *
	 * @param repositoryContainer the new repository container
	 */
	public void setRepositoryContainer(final RepositoryContainer repositoryContainer) {
		this.repositoryContainer = repositoryContainer;
	}
}
