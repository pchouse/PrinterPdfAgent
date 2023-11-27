# Printer agent

REST api intended to be used as printer agent to help print,
cut paper and open cash drawer from browser.

Send the pdf to API to be printed from javascript

```javascript

        const AFTER_PRINT_CUT_PAPER = 1;
        const AFTER_PRINT_OPEN_CASH_DRAWER = 2;
        
        let afterPrint = AFTER_PRINT_CUT_PAPER | AFTER_PRINT_OPEN_CASH_DRAWER;

        let abortController = new AbortController();
        let timeout = setTimeout(() => abortController.abort(), 1000 * 60);

        let fetchReceiptRequest = await fetch(
                "http://localhost:5999/print", {
                    method : "POST",
                    body   : JSON.stringify(
                               {
                                    "pdf": PdfAsBase64String,
                                    "afterPrintOperations": afterPrint,
                                }
                              ),
                    headers: {
                        "Content-Type": "application/json"
                    },
                    signal : abortController.signal
                }
        );

        clearTimeout(timeout);

        if (fetchReceiptRequest.status === 200) {

            let response = await fetchReceiptRequest.json();

            if (response.status !== "OK") {
                alert("Error:" + response.message);
            }
        }else{
            alert(await fetchReceiptRequest.text());
        }
        
```

To start teh API:
copy and change the application.properties and printer.properties to the jar folder

```bash
    /path/to/jdk17/bin/java -Dapp.home="/path/to/jar/folder"  -jar printer.pdf.agent-1.0.0.jar
```

Licence

Copyright 2023 PChouse - Reflexão, Estudos e Sistemas Informáricos, Lda

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
