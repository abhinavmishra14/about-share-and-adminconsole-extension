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
package com.github.abhinavmishra14.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class NetUtility.
 */
public final class NetUtility {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NetUtility.class);

	/**
	 * The Constructor.
	 */
	private NetUtility() {
		super();
	}

	/**
	 * Gets an InetAddress object encapsulating what's most likely the machine's LAN IP address.<br>
	 * This method is intended for use as a replacement of JDK method
	 * InetAddress.getLocalHost, because InetAddress is ambiguous on Linux machines.<br>
	 * Linux systems enumerate the LoopBack network interface the same way as
	 * regular LAN network interfaces, but the JDK InetAddress.getLocalHost method
	 * does not specify the algorithm used to select the address returned under such
	 * circumstances, and will often return the LoopBack address, which is not valid
	 * for network communication. Details about the bug: <a href=
	 * "http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4665037">here</a>.
	 *
	 * @return java.net.InetAddress
	 * @throws UnknownHostException the unknown host exception
	 */
	public static InetAddress getLocalHost() throws UnknownHostException {
		try {
			InetAddress nonlbAddrsCandidate = null;
			// Iterate all network interface cards
			for (final Enumeration<NetworkInterface> netInterfaceEnum = NetworkInterface.getNetworkInterfaces(); 
					netInterfaceEnum.hasMoreElements();) {
				final NetworkInterface netInterface = netInterfaceEnum.nextElement();
				// Iterate all IP addresses assigned to each card
				for (final Enumeration<InetAddress> inetAddrsEnum = netInterface.getInetAddresses(); 
						inetAddrsEnum.hasMoreElements();) {
					final InetAddress inetAddr = inetAddrsEnum.nextElement();
					if (!inetAddr.isLoopbackAddress()) {
						if (inetAddr.isSiteLocalAddress()) {
							// Found nonLoopBack siteLocal address.
							return inetAddr;
						} else if (nonlbAddrsCandidate == null) {
							// Found nonLoopBack address but not necessarily siteLocal. 
							// Could be a candidate address to be returned if siteLocal address is not subsequently found
							nonlbAddrsCandidate = inetAddr;
							// Note that we only assign nonLoopBack nonSiteLocal addresses as candidate for first iteration only,
							// For subsequent iterations, candidate will be non-null always.
						}
					}
				}
			}
			if (nonlbAddrsCandidate != null) {
				// We did not find a siteLocal address, but we found some other nonLoopBack address.
				// Server might have a nonSiteLocal address assigned to its network interface cards (or it might
				// be running IPv6 which deprecates the "siteLocal" concept).
				// Return this nonLoopBack candidate address in this case
				return nonlbAddrsCandidate;
			}
			// At this point, we did not find a nonLoopBack address.
			// Fall back to returning whatever InetAddress.getLocalHost() returns
			final InetAddress jdkProvidedAddress = InetAddress.getLocalHost();
			if (jdkProvidedAddress == null) {
				throw new UnknownHostException("Unexpected null value from InetAddress.getLocalHost() method!");
			}
			return jdkProvidedAddress;
		} catch (SocketException socketEx) {
			LOGGER.error("SocketException occurred while scanning for LAN Address", socketEx);
			throw prepareUnknownHostException(socketEx);
		} catch (IOException ioex) {
			LOGGER.error("IOException occurred while scanning for LAN Address", ioex);
			throw prepareUnknownHostException(ioex);
		} catch (Exception excp) {
			LOGGER.error("Exception occurred while scanning for LAN Address", excp);
			throw prepareUnknownHostException(excp);
		}
	}

	/**
	 * Prepare unknown host exception.
	 *
	 * @param excp the exception
	 * @return the unknown host exception
	 */
	private static UnknownHostException prepareUnknownHostException(final Exception excp) {
		final UnknownHostException unHostEx = new UnknownHostException("Failed to determine LAN address due to: " + excp.getMessage());
		unHostEx.initCause(excp);
		return unHostEx;
	}
}
