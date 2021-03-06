/*
 * Copyright (c) Members of the EGEE Collaboration. 2006-2010.
 * See http://www.eu-egee.org/partners/ for details on the copyright holders.
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

package org.glite.authz.pep.obligation.dfpmap;

import java.util.List;

import javax.security.auth.x500.X500Principal;

import org.glite.authz.common.fqan.FQAN;
import org.glite.authz.pep.obligation.ObligationProcessingException;

/**
 * A strategy for mapping a subject, identified by a DN, primary FQAN, and secondary FQANs to a POSIX account indicator.
 */
public interface AccountIndicatorMappingStrategy {

    /**
     * Maps a subject to a POSIX account indicator.
     * 
     * @param subjectDN subject's DN
     * @param primaryFQAN subject's primary FQAN
     * @param secondaryFQANs subject's secondary FQANs
     * 
     * @return the account indicator or null
     * 
     * @throws ObligationProcessingException thrown if there is a problem mapping the subject to an account
     */
    public String mapToAccountIndicator(X500Principal subjectDN, FQAN primaryFQAN, List<FQAN> secondaryFQANs)
            throws ObligationProcessingException;
}