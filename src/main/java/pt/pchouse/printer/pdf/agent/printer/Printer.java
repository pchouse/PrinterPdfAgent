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

package pt.pchouse.printer.pdf.agent.printer;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.print.*;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

/**
 * @since 1.0.0
 */
@Component
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class Printer
{

    /**
     * @since 1.0.0
     */
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @since 1.0.0
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * @since 1.0.0
     */
    @Autowired
    private PrinterConfig printerConfig;

    /**
     * @since 1.0.0
     */
    private static PrintService printService;

    /**
     * @return The printer service
     * @throws PrinterException If error
     * @since 1.0.0
     */
    public PrintService getPrintService() throws PrinterException {
        if (printService == null) {

            Optional<PrintService> optional = Arrays.stream(PrintServiceLookup
                            .lookupPrintServices(null, null))
                    .filter(prtService -> prtService.getName().equals(printerConfig.getName()))
                    .findFirst();

            if (optional.isEmpty()) {
                String msg = String.format(
                        "Printer with name '%s' not exist, if exists please restart the API Print agent service",
                        printerConfig.getName()
                );
                logger.error(msg);
                throw new PrinterException(msg);
            }
            logger.debug("Printer service set to printer name '{}'", printerConfig.getName());
            printService = optional.get();
        }

        return printService;
    }

    /**
     * Cut the paper
     *
     * @throws PrintException   If fails
     * @throws PrinterException If fails
     * @since 1.0.0
     */
    public void cutPaper() throws PrintException, PrinterException {
        String esc = new String(printerConfig.getInitBytes());
        esc += new String(printerConfig.getCutBytes());
        Doc doc = new SimpleDoc(
                esc.getBytes(),
                DocFlavor.BYTE_ARRAY.AUTOSENSE,
                null
        );
        getPrintService().createPrintJob().print(doc, null);
    }

    /**
     * Open the cash drawer
     *
     * @throws PrintException   If fails
     * @throws PrinterException If fails
     * @since 1.0.0
     */
    public void cashDrawer() throws PrintException, PrinterException {
        String esc = new String(printerConfig.getInitBytes());
        esc += new String(printerConfig.getCashDrawerBytes());
        Doc doc = new SimpleDoc(
                esc.getBytes(),
                DocFlavor.BYTE_ARRAY.AUTOSENSE,
                null
        );
        getPrintService().createPrintJob().print(doc, null);
    }

    /**
     * Cut the paper and open cash drawer
     *
     * @throws PrintException   If fails
     * @throws PrinterException If fails
     * @since 1.0.0
     */
    public void cutAndCashDrawer() throws PrintException, PrinterException {
        String esc = new String(printerConfig.getInitBytes());
        esc += new String(printerConfig.getCutBytes());
        esc += new String(printerConfig.getCashDrawerBytes());
        Doc doc = new SimpleDoc(
                esc.getBytes(),
                DocFlavor.BYTE_ARRAY.AUTOSENSE,
                null
        );
        getPrintService().createPrintJob().print(doc, null);
    }

    /**
     * @param base64Pdf The pdf tp print as base64 encode
     * @throws PrinterException                If print fails
     * @throws java.awt.print.PrinterException If print fails
     * @throws IOException                     If load pdf fails
     * @since 1.0.0
     */
    public void PrintBase64StringPdf(String base64Pdf) throws
            PrinterException,
            java.awt.print.PrinterException,
            IOException
    {

        PrintService printService = this.getPrintService();
        PDDocument   document     = Loader.loadPDF(Base64.getDecoder().decode(base64Pdf));
        PrinterJob   job          = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        job.setPrintService(printService);
        job.print();
    }

}
