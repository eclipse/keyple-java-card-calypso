/* **************************************************************************************
 * Copyright (c) 2020 Calypso Networks Association https://calypsonet.org/
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
 * Builds the SV Prepare Load APDU command.
 *
 * @since 2.0
 */
final class SamSvPrepareLoadBuilder extends AbstractSamCommandBuilder<SamSvPrepareOperationParser> {
  /** The command reference. */
  private static final CalypsoSamCommand command = CalypsoSamCommand.SV_PREPARE_LOAD;

  /**
   * Instantiates a new SamSvPrepareLoadBuilder to prepare a load transaction.
   *
   * <p>Build the SvPrepareLoad APDU from the SvGet command and response, the SvReload partial
   * command
   *
   * @param samProductType the SAM revision.
   * @param svGetHeader the SV Get command header.
   * @param svGetData a byte array containing the data from the SV get command and response.
   * @param svReloadCmdBuildData the SV reload command builder data.
   * @since 2.0
   */
  public SamSvPrepareLoadBuilder(
      CalypsoSam.ProductType samProductType,
      byte[] svGetHeader,
      byte[] svGetData,
      byte[] svReloadCmdBuildData) {
    super(command);

    byte cla = SamUtilAdapter.getClassByte(samProductType);
    byte p1 = (byte) 0x01;
    byte p2 = (byte) 0xFF;
    byte[] data = new byte[19 + svGetData.length]; // header(4) + SvReload data (15) = 19 bytes

    System.arraycopy(svGetHeader, 0, data, 0, 4);
    System.arraycopy(svGetData, 0, data, 4, svGetData.length);
    System.arraycopy(
        svReloadCmdBuildData, 0, data, 4 + svGetData.length, svReloadCmdBuildData.length);

    setApduRequest(
        new ApduRequestAdapter(
            ApduUtil.build(cla, command.getInstructionByte(), p1, p2, data, null)));
  }

  /**
   * {@inheritDoc}
   *
   * @since 2.0
   */
  @Override
  public SamSvPrepareOperationParser createResponseParser(ApduResponseApi apduResponse) {
    return new SamSvPrepareOperationParser(apduResponse, this);
  }
}
