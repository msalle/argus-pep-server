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

package org.glite.authz.pep.utils;

import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Hashtable;

import org.bouncycastle.asn1.x509.X509Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Distinguished Name (DN) handling routines. These routines are separated into
 * this separate class to ensure the DN is always handled the same way. This
 * separation also makes changing the handling easy in case when for example the
 * encoding changes or there is need for changes in internationalization
 * support.
 * 
 * @author Joni Hahkala Created on August 26, 2003, 10:21 AM
 * @deprecated use caNl instead
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DNHandler {
    /** Logging Facility. */
    final Logger LOGGER= LoggerFactory.getLogger(DNHandler.class);

    /** symbol table for printing the RDN identifiers, with "emailAddress" */
    public static Hashtable s_rfc2253v2Lookup;
    /** symbol table for printing the RDN identifiers, with "Email" */
    public static Hashtable s_rfc2253Lookup;

    static {
        // The lookup with emailAddress
        s_rfc2253v2Lookup= (Hashtable) X509Name.RFC2253Symbols.clone();
        s_rfc2253v2Lookup.put(X509Name.EmailAddress, "emailAddress");

        // The lookup with legacy openssl RDS symbol "Email"
        s_rfc2253Lookup= (Hashtable) X509Name.DefaultSymbols.clone();
        s_rfc2253Lookup.put(X509Name.EmailAddress, "Email");

        // add lookups for legacy openssl RDS identifiers.
        X509Name.DefaultLookUp.put("email", X509Name.E);
        X509Name.DefaultLookUp.put("serialnumber", X509Name.SN);
        // X509Name.DefaultReverse = false;
    }

    /**
     * Picks up the issuer from the certificate as a DN class without any
     * transformations etc.
     * 
     * @param cert
     *            The certificate to the the issuer from.
     * @return The DN class representation of the issuer.
     */
    public static DN getIssuerDN(X509Certificate cert) {
        // logger.debug("getting the issuer");
        return getPrincipalDN(cert.getIssuerDN());
    }

    /**
     * Picks up the subject from the certificate as a DN class without any
     * transformations etc.
     * 
     * @param cert
     *            The certificate to the the issuer from.
     * @return The DN class representation of the subject.
     */
    public static DN getSubjectDN(X509Certificate cert) {
        return getPrincipalDN(cert.getSubjectDN());
    }

    /**
     * Generates a DN object form the Principal object.
     * 
     * @param principal
     *            The Principal to get the DN from.
     * @return The DN class representation of the DN.
     */
    public static DN getPrincipalDN(Principal principal) {
        // logger.debug("getting the DN from principal");
        return new DNImplRFC2253(principal);
    }

    /**
     * Generates a DN object from a String. If the string starts with a slash
     * character, it is assumed to be old openssl X500 form, e.g.
     * "/C=FR/O=Acme/CN=John Doe". Otherwise the string is assumed to be RFC
     * 2253 format DN in the reverse order as described in RFC2253, e.g.
     * "CN=John Doe, O=Acme, C=FR".
     * 
     * @param inputDN
     *            The string to get the DN from.
     * @return The DN class representation of the DN.
     */
    public static DN getDNRFC2253(String inputDN) {
        return new DNImplRFC2253(inputDN);
    }
}
