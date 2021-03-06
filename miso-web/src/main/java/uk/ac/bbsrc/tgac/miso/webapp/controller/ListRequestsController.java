/*
 * Copyright (c) 2012. The Genome Analysis Centre, Norwich, UK
 * MISO project contacts: Robert Davey, Mario Caccamo @ TGAC
 * *********************************************************************
 *
 * This file is part of MISO.
 *
 * MISO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MISO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MISO.  If not, see <http://www.gnu.org/licenses/>.
 *
 * *********************************************************************
 */

package uk.ac.bbsrc.tgac.miso.webapp.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.bbsrc.tgac.miso.core.data.Project;
import com.eaglegenomics.simlims.core.User;
import uk.ac.bbsrc.tgac.miso.core.manager.RequestManager;
import com.eaglegenomics.simlims.core.manager.SecurityManager;

@Controller
public class ListRequestsController {
	protected static final Logger log = LoggerFactory.getLogger(ListRequestsController.class);

	@Autowired
	private SecurityManager securityManager;

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	@Autowired
	private RequestManager requestManager;

	public void setRequestManager(RequestManager requestManager) {
		this.requestManager = requestManager;
	}

	@RequestMapping("/requests")
	public ModelAndView listRequests(
			@RequestParam(value = "projectId", required = true) long projectId,
			ModelMap model) throws IOException {
		try {
			User user = securityManager
					.getUserByLoginName(SecurityContextHolder.getContext()
							.getAuthentication().getName());
			Project project = requestManager.getProjectById(projectId);
			if (!project.userCanRead(user)) {
				throw new SecurityException("Permission denied.");
			}
			//return new ModelAndView("/pages/listRequests.jsp", "requests", requestManager.listAllRequests(user, project));
            return null;
		} catch (IOException ex) {
			if (log.isDebugEnabled()) {
				log.debug("Failed to list groups", ex);
			}
			throw ex;
		}
	}
}
