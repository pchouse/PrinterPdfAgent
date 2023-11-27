/*
 * Copyright 2023 PChouse - Reflexão, Estudos e Sistemas Informáricos, Lda
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pt.pchouse.printer.pdf.agent.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @since 1.0.0
 */
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class Response
{

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The response status enumeration
     *
     * @since 1.0.0
     */
    public enum Status
    {
        OK,
        ERROR,
    }

    /**
     * The report status response
     *
     * @since 1.0.0
     */
    protected Status status;

    /**
     * Response message
     *
     * @since 1.0.0
     */
    protected String message = "";

    /**
     * The request Response
     *
     * @since 1.0.0
     */
    public Response() {
        logger.debug("New instance of {}", this.getClass().getName());
    }

    /**
     * Get the response report status
     *
     * @return The status
     * @since 1.0.0
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set the response report status
     *
     * @param status the status
     * @since 1.0.0
     */
    public void setStatus(Status status) {
        this.status = status;
        logger.debug("Response status set to {}", this.status.toString());
    }

    /**
     * Get the response message
     *
     * @return The message
     * @since 1.0.0
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the response message
     *
     * @param message The message
     * @since 1.0.0
     */
    public void setMessage(String message) {
        this.message = message;
        logger.debug("Message set to {}", this.message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equals(logger, response.logger) &&
                status == response.status &&
                Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logger, status, message);
    }
}
