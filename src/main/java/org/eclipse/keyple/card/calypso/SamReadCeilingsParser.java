/* **************************************************************************************
 * Copyright (c) 2018 Calypso Networks Association https://calypsonet.org/
 *
 * See the NOTICE file(s) distributed with this work for additional information
 * regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ************************************************************************************** */
package org.eclipse.keyple.card.calypso;

import java.util.HashMap;
import java.util.Map;
import org.calypsonet.terminal.card.ApduResponseApi;

/**
 * Parses the Read ceilings response.
 *
 * @since 2.0
 */
final class SamReadCeilingsParser extends AbstractSamResponseParser {

  private static final Map<Integer, StatusProperties> STATUS_TABLE;

  static {
    Map<Integer, StatusProperties> m =
        new HashMap<Integer, StatusProperties>(AbstractSamResponseParser.STATUS_TABLE);
    m.put(
        0x6900,
        new StatusProperties(
            "An event counter cannot be incremented.", CalypsoSamCounterOverflowException.class));
    m.put(
        0x6A00,
        new StatusProperties("Incorrect P1 or P2.", CalypsoSamIllegalParameterException.class));
    m.put(0x6200, new StatusProperties("Correct execution with warning: data not signed.", null));
    STATUS_TABLE = m;
  }

  /**
   * {@inheritDoc}
   *
   * @since 2.0
   */
  @Override
  protected Map<Integer, StatusProperties> getStatusTable() {
    return STATUS_TABLE;
  }

  /**
   * Instantiates a new SamReadEventCounterParser.
   *
   * @param response of the SamReadEventCounterParser.
   * @param builder the reference to the builder that created this parser.
   * @since 2.0
   */
  public SamReadCeilingsParser(ApduResponseApi response, SamReadCeilingsBuilder builder) {
    super(response, builder);
  }

  /**
   * Gets the key parameters.
   *
   * @return The ceilings data (Value or Record)
   * @since 2.0
   */
  public byte[] getCeilingsData() {
    return isSuccessful() ? response.getDataOut() : null;
  }
}
