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

package pt.pchouse.printer.pdf.agent.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Objects;

/**
 * @since 1.0.0
 */
@Component
@RequestScope
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class PrintRequest
{

    /**
     * Cut the paper after print the document
     *
     * @since 1.0.0
     */
    public static final int AFTER_PRINT_CUT_PAPER = 1;

    /**
     * Open the cash drawer after print the document
     *
     * @since 1.0.0
     */
    public static final int AFTER_PRINT_OPEN_CASH_DRAWER = 2;

    /**
     * @since 1.0.0
     */
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The JasperReports file, the compiled jrxml file (.jasper) as base64 encoded
     *
     * @since 1.0.0
     */
    private String pdf;

    /**
     * Bitwise operations after send the report to the printer
     * Only apply to printer mode
     *
     * @since 1.0.0
     */
    private int afterPrintOperations = 0;

    /**
     * Report request definition
     *
     * @since 1.0.0
     */
    public PrintRequest() {
        logger.debug("New instance of {}", this.getClass().getName());
    }

    /**
     * The base64 encoded jasper report (jasper file compiled from jrxml file)
     *
     * @return The base64 encoded jasper report
     * @since 1.0.0
     */
    public String getPdf() {
        return pdf;
    }

    /**
     * The base64 encoded jasper report (jasper file compiled from jrxml file)
     *
     * @param pdf The base64 encoded jasper report
     * @since 1.0.0
     */
    public void setPdf(String pdf) {
        this.pdf = pdf;
        logger.debug("Report was set");
    }

    /**
     * Get the bitwise after print operations
     *
     * @return The bitwise
     * @since 1.0.0
     */
    public int getAfterPrintOperations() {
        return afterPrintOperations;
    }

    /**
     * Set the bitwise after print operations
     *
     * @param afterPrintOperations The bitwise
     * @since 1.0.0
     */
    public void setAfterPrintOperations(int afterPrintOperations) {
        this.afterPrintOperations = afterPrintOperations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintRequest that = (PrintRequest) o;
        return afterPrintOperations == that.afterPrintOperations && Objects.equals(logger, that.logger) && Objects.equals(pdf, that.pdf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logger, pdf, afterPrintOperations);
    }
}
