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

package pt.pchouse.printer.pdf.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pt.pchouse.printer.pdf.agent.response.Response;

/**
 * @since 1.0.0
 */
@ControllerAdvice
public class ErrorHandler
{

    /**
     * @since 1.0.0
     */
    @Autowired
    private ApplicationContext appContext;

    /**
     * @param ex       The not handled exception
     * @param request  The request container
     * @param response The response container
     * @return The Internal Error response
     * @see <a href="https://stackoverflow.com/a/48509042">https://stackoverflow.com/a/48509042</a>
     * @since 1.0.0
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        Response reportResponse = appContext.getBean(Response.class);
        reportResponse.setStatus(Response.Status.ERROR);

        if (ex instanceof HttpMessageNotReadableException) {
            reportResponse.setMessage("Request body error. Possible empty or json wrong format.");
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            reportResponse.setMessage("Request body wrong type.");
        } else {
            reportResponse.setMessage(ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(reportResponse);
    }

}
