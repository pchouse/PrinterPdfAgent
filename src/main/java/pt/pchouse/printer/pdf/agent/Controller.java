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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.pchouse.printer.pdf.agent.auth.IAuth;
import pt.pchouse.printer.pdf.agent.printer.Printer;
import pt.pchouse.printer.pdf.agent.request.PrintRequest;
import pt.pchouse.printer.pdf.agent.response.Response;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
public class Controller
{

    /**
     * @since 1.0.0
     */
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @since 1.0.0
     */
    @Value("${pom.version}")
    private String pomVersion;

    /**
     * @since 1.0.0
     */
    @Autowired
    private ApplicationContext appContext;

    /**
     * @since 1.0.0
     */
    @Autowired
    private Executor executor;

    /**
     * Handle to printer cut the paper
     *
     * @return The response
     * @since 1.0.0
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/isAlive", method = RequestMethod.GET)
    public CompletableFuture<ResponseEntity<Response>> isAlive() {

        Response response = new Response();
        response.setStatus(Response.Status.OK);
        response.setMessage("Server OK");

        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(200).body(response), executor);
    }


    /**
     * Handle to printer cut the paper
     *
     * @return The response
     * @since 1.0.0
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/print", method = RequestMethod.POST)
    @ResponseBody
    public CompletableFuture<ResponseEntity<Response>> print(@RequestBody PrintRequest printRequest) {

        logger.debug("New print request");

        IAuth auth = appContext.getBean(IAuth.class);
        auth.catchRemoteIP();

        return CompletableFuture.supplyAsync(() -> {

            Response response = appContext.getBean(Response.class);

            try {

                if (auth.isNotAuthorized()) {
                    response.setStatus(Response.Status.ERROR);
                    response.setMessage("Client not authorized");
                    logger.debug("Client not authorized, respond with http status code 400");
                    return ResponseEntity.status(400).body(response);
                }

                Printer printer = appContext.getBean(Printer.class);

                printer.PrintBase64StringPdf(printRequest.getPdf());

                if ((printRequest.getAfterPrintOperations() & PrintRequest.AFTER_PRINT_CUT_PAPER) != 0) {
                    printer.cutPaper();
                }

                if ((printRequest.getAfterPrintOperations() & PrintRequest.AFTER_PRINT_OPEN_CASH_DRAWER) != 0) {
                    printer.cashDrawer();
                }

                response.setStatus(Response.Status.OK);

                return ResponseEntity.status(200).body(response);
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.setStatus(Response.Status.ERROR);
                response.setMessage(e.getMessage());
                return ResponseEntity.status(400).body(response);
            }
        }, executor);
    }


    /**
     * Handle to printer cut the paper
     *
     * @return The response
     * @since 1.0.0
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/cut", method = RequestMethod.GET)
    @ResponseBody
    public CompletableFuture<ResponseEntity<Response>> cutPaper() {

        logger.debug("New cut paper request");

        IAuth auth = appContext.getBean(IAuth.class);
        auth.catchRemoteIP();

        return CompletableFuture.supplyAsync(() -> {

            Response response = appContext.getBean(Response.class);

            try {

                if (auth.isNotAuthorized()) {
                    response.setStatus(Response.Status.ERROR);
                    response.setMessage("Client not authorized");
                    logger.debug("Client not authorized, respond with http status code 400");
                    return ResponseEntity.status(400).body(response);
                }

                appContext.getBean(Printer.class).cutPaper();

                response.setStatus(Response.Status.OK);

                return ResponseEntity.status(200).body(response);
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.setStatus(Response.Status.ERROR);
                response.setMessage(e.getMessage());
                return ResponseEntity.status(400).body(response);
            }
        }, executor);
    }

    /**
     * Handler to printer cut paper and open cash drawer
     *
     * @return The response
     * @since 1.0.0
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/cutandopen", method = RequestMethod.GET)
    @ResponseBody
    public CompletableFuture<ResponseEntity<Response>> cutPaperAndOpenCashDrawer() {

        logger.debug("New cut paper and open cash drawer request");

        IAuth auth = appContext.getBean(IAuth.class);
        auth.catchRemoteIP();

        return CompletableFuture.supplyAsync(() -> {

            Response response = appContext.getBean(Response.class);

            try {

                if (auth.isNotAuthorized()) {
                    response.setStatus(Response.Status.ERROR);
                    response.setMessage("Client not authorized");
                    logger.debug("Client not authorized, respond with http status code 400");
                    return ResponseEntity.status(400).body(response);
                }

                appContext.getBean(Printer.class).cutAndCashDrawer();

                response.setStatus(Response.Status.OK);
                return ResponseEntity.status(200).body(response);
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.setStatus(Response.Status.ERROR);
                response.setMessage(e.getMessage());
                return ResponseEntity.status(400).body(response);
            }
        }, executor);
    }

    /**
     * Handle to printer open cash drawer
     *
     * @return The response
     * @since 1.0.0
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/cashdrawer", method = RequestMethod.GET)
    @ResponseBody
    public CompletableFuture<ResponseEntity<Response>> openCashDrawerPaper() {

        logger.debug("New open cash drawer request");

        IAuth auth = appContext.getBean(IAuth.class);
        auth.catchRemoteIP();

        return CompletableFuture.supplyAsync(() -> {

            Response response = appContext.getBean(Response.class);

            try {

                if (auth.isNotAuthorized()) {
                    response.setStatus(Response.Status.ERROR);
                    response.setMessage("Client not authorized");
                    logger.debug("Client not authorized, respond with http status code 400");
                    return ResponseEntity.status(400).body(response);
                }

                appContext.getBean(Printer.class).cashDrawer();

                response.setStatus(Response.Status.OK);

                return ResponseEntity.status(200).body(response);
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.setStatus(Response.Status.ERROR);
                response.setMessage(e.getMessage());
                return ResponseEntity.status(400).body(response);
            }
        }, executor);
    }

    /**
     * Get version
     *
     * @return The response
     * @since 1.0.0
     */
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    @ResponseBody
    public CompletableFuture<ResponseEntity<Response>> getVersion() {

        logger.debug("Get version");

        IAuth auth = appContext.getBean(IAuth.class);
        auth.catchRemoteIP();

        return CompletableFuture.supplyAsync(() -> {

            Response response = appContext.getBean(Response.class);

            try {

                if (auth.isNotAuthorized()) {
                    response.setStatus(Response.Status.ERROR);
                    response.setMessage("Client not authorized");
                    logger.debug("Client not authorized, respond with http status code 400");
                    return ResponseEntity.status(400).body(response);
                }

                response.setMessage(pomVersion);
                response.setStatus(Response.Status.OK);
                return ResponseEntity.status(200).body(response);
            } catch (Exception e) {
                logger.error(e.getMessage());
                response.setStatus(Response.Status.ERROR);
                response.setMessage(e.getMessage());
                return ResponseEntity.status(400).body(response);
            }
        }, executor);
    }

    /**
     * Catch all non-existent action path
     *
     * @return HttpStatus 404 with message "Action not found"
     * @since 1.0.0
     */
    @RequestMapping("*")
    public CompletableFuture<ResponseEntity<Response>> catchAllNotFound() {
        return CompletableFuture.supplyAsync(() -> {
            logger.debug("Catch capture a request for a method that not exist");
            Response reportResponse = appContext.getBean(Response.class);
            reportResponse.setStatus(Response.Status.ERROR);
            reportResponse.setMessage("Action not found");
            return ResponseEntity.status(404).body(reportResponse);
        });
    }

}
