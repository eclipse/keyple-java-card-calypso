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

import org.calypsonet.terminal.card.ApduResponseApi;

/**
 * (package-private)<br>
 * Superclass for all card command builders
 *
 * @since 2.0
 */
abstract class AbstractCardCommandBuilder<T extends AbstractCardResponseParser>
    extends AbstractApduCommandBuilder {

  /**
   * Constructor dedicated for the building of referenced Calypso commands
   *
   * @param commandRef a command reference from the Calypso command table.
   * @since 2.0
   */
  protected AbstractCardCommandBuilder(CalypsoCardCommand commandRef) {
    super(commandRef);
  }

  /**
   * Create the response parser matching the builder
   *
   * @param apduResponse the response data from the the card.
   * @return An {@link AbstractCardResponseParser}
   */
  public abstract T createResponseParser(ApduResponseApi apduResponse);

  /**
   * {@inheritDoc}
   *
   * @since 2.0
   */
  @Override
  public CalypsoCardCommand getCommandRef() {
    return (CalypsoCardCommand) commandRef;
  }

  /**
   * Indicates if the session buffer is used when executing this command.
   *
   * <p>Allows the management of the overflow of this buffer.
   *
   * @return True if this command uses the session buffer
   * @since 2.0
   */
  public abstract boolean isSessionBufferUsed();
}
