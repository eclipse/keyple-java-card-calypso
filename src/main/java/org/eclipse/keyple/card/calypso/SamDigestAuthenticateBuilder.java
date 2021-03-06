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

import org.calypsonet.terminal.calypso.sam.CalypsoSam;
import org.calypsonet.terminal.card.ApduResponseApi;
import org.eclipse.keyple.core.util.ApduUtil;

/**
 * (package-private) <br>
 * Builds the Digest Authenticate APDU command.
 *
 * @since 2.0
 */
final class SamDigestAuthenticateBuilder
    extends AbstractSamCommandBuilder<SamDigestAuthenticateParser> {

  /** The command. */
  private static final CalypsoSamCommand command = CalypsoSamCommand.DIGEST_AUTHENTICATE;

  /**
   * Instantiates a new SamDigestAuthenticateBuilder .
   *
   * @param productType of the SAM.
   * @param signature the signature.
   * @throws IllegalArgumentException - if the signature is null or has a wrong length.
   * @since 2.0
   */
  public SamDigestAuthenticateBuilder(CalypsoSam.ProductType productType, byte[] signature) {
    super(command);
    if (productType != null) {
      this.defaultProductType = productType;
    }
    if (signature == null) {
      throw new IllegalArgumentException("Signature can't be null");
    }
    if (signature.length != 4 && signature.length != 8 && signature.length != 16) {
      throw new IllegalArgumentException(
          "Signature is not the right length : length is " + signature.length);
    }
    byte cla = SamUtilAdapter.getClassByte(defaultProductType);
    byte p1 = 0x00;
    byte p2 = (byte) 0x00;

    setApduRequest(
        new ApduRequestAdapter(
            ApduUtil.build(cla, command.getInstructionByte(), p1, p2, signature, null)));
  }

  /**
   * {@inheritDoc}
   *
   * @since 2.0
   */
  @Override
  public SamDigestAuthenticateParser createResponseParser(ApduResponseApi apduResponse) {
    return new SamDigestAuthenticateParser(apduResponse, this);
  }
}
