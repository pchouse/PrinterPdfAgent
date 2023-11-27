package pt.pchouse.printer.pdf.agent;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import pt.pchouse.printer.pdf.agent.auth.Auth;
import pt.pchouse.printer.pdf.agent.printer.PrinterConfig;
import pt.pchouse.printer.pdf.agent.request.PrintRequest;
import pt.pchouse.printer.pdf.agent.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerTest
{

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PrinterConfig printerConfig;

    @MockBean
    private Auth auth;


    @Test
    void testIsAlive() {
        String url = "http://localhost:" + port + "/isAlive";

        RequestEntity<Void> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<Response> responseEntity = restTemplate.exchange(
                request,
                Response.class
        );

        Response reportResponse = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assert reportResponse != null;
        assertThat(reportResponse.getStatus()).isEqualTo(Response.Status.OK);
    }


    @Test
    void testPrintPdf() {
        String url = "http://localhost:" + port + "/print";

        PrintRequest printRequest = new PrintRequest();
        printRequest.setAfterPrintOperations(0);
        printRequest.setPdf(ControllerTest.base64Pdf);

        Mockito.when(printerConfig.getName()).thenReturn("report");
        Mockito.when(auth.isNotAuthorized()).thenReturn(false);

        RequestEntity<PrintRequest> request = RequestEntity.post(url)
                .accept(MediaType.APPLICATION_JSON)
                .body(printRequest);

        ResponseEntity<Response> responseEntity = restTemplate.exchange(
                request,
                Response.class
        );

        Response reportResponse = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assert reportResponse != null;
        assertThat(reportResponse.getStatus()).isEqualTo(Response.Status.OK);
    }

    //<editor-fold defaultstate="collapsed">
    final static String base64Pdf = "JVBERi0xLjYKJcOkw7zDtsOfCjIgMCBvYmoKPDwvTGVuZ3RoIDMg" +
            "MCBSL0ZpbHRlci9GbGF0ZURlY29kZT4+CnN0cmVhbQp4nD1MuwrCQBDs9yumFnLubrL3gONAQQu7wI" +
            "GF2KnpBNP4+yYnysAwL4ad4E0vMNixRlgyp8EQB3HRC+Y7nTd4fhcL5on2lbRPzhBC71JMqDdsjwJR1" +
            "MclsxTNrCv1pZO/73zmga1FnkOJmWPpdO1l0UMxzpyKZd61uD38Ftd6okOlkUZ8ABlwJbsKZW5kc3RyZW" +
            "FtCmVuZG9iagoKMyAwIG9iagoxMzgKZW5kb2JqCgo1IDAgb2JqCjw8L0xlbmd0aCA2IDAgUi9GaWx0ZXIv" +
            "RmxhdGVEZWNvZGUvTGVuZ3RoMSA5NzU2Pj4Kc3RyZWFtCnic5ThtdBvVle/OjGTJlqyPyCZYJBp" +
            "JsUmwLTlxDAmJsWJbshMbrMQ2SAmJNZZkS8SWtJLiNHRp3NJA6pAmZdPwURbSHsphWZ/DmKSs6VJiCr" +
            "TLdttAC3uW0iw+p3RPu0uIl6ZdFrC9970ZOU4I9HTP/tuR3pv7fe+79743I+Wze+" +
            "LEQEYJT3zRYSnjMpZYCSH/RAhYoyN5sbGrbAPC04RwPx/IDA4//He3Xy" +
            "BEOEVI0anBoX0DRyxPfJsQQ4IQ/RuJuBR7dE1dDSHlG9HG9QkkdM3tK0I8j/iKxHD+C+u" +
            "EqRWIP4j4yqF0VHrK8H4x4lOILx2WvpCpEnwc4v+CuJiShuMfPvpSDPEPCSnJZdK5fIwcnCfENUT5mWw80/lw/yuI30c" +
            "IfxRpgB96GRDUUpzjBY22SKcvJv8/L81hUkbaNY3ERDJsvuTix8nV5CFC5t+j2MV5rnP+o//LKHTK7UHyBDlFDpO3yE6VES" +
            "BBkiR7kLL4epG8jlR6Bcl28hQZ+wyz42QS+YpchByhK7niFSQPkJPkx5d4CZJh8kWM5XvkLVhNXsVWSZMPQEe+TF5Bqx8g" +
            "7eYrmeJKcRpg4MAi6tvkW9whsoV7F5GHKIfzcmbyMnkEdqHlPK7z8MKKN37K6L3kLpy7SYKMIMwuTeMnvyT6+d/jqu4i" +
            "W8hXyCYytEjjeXiMx5bme8hjmNMXGc1bYBa183dwz3Lc7F8h8g0yiEMCXDt3mN/0GRn6sy++lxhhFV9J9FficmuJae4" +
            "jbs38BX4FKSa98zMF2nzH/O95aS4l9AnXaBqFn3yeD+03hGHUJvO/mfviXExzi+YJrNaThPjadmwPh3p7urdtDXbdcnNnx" +
            "5bN7W0Bf2tL8yZf002NGzfcuH7dDdc3rK7zemprVl5bVbnC7XI6ltosZlOpsaRYryvSagSeA1IjyhDxy3ylaAlIbr9baq+tE" +
            "f1LE621NX53ICKLkijjTahyt7czkluSxYgoV+FNWkSOyD6UHLhM0qdI+hYkwSxuJBupC7co/7TVLU7C9q0hhA+3usOifI7" +
            "BNzNYqGKIERGnEzVYVDRa0S8HRhJj/gjGCBMlxS3ulnhxbQ2ZKC5BsAQheaU7MwErbwIGcCv9N05wRGekbnGlfikmB7eG/K12pz" +
            "NcW7NZLnW3MhZpYSZlbYtcxEyKSRo6OSRO1EyN3TdpJv2RakPMHZNuD8m8hLpjvH9s7F7ZUi2vcrfKq+58dymuPC7XuFv9cj" +
            "W12rFtwU/HRZcgayrNbnHsDwSX4z733qUUSaVoK81/IBSUuRYZtoWc9LIHMNdjYwG3GBiLjEmT86P9btHsHpswGMYyfkw3" +
            "CYbQxOT89w/Z5cB9YdkcScCNYXXpgW0d8pKtO0IyVxkQExJS8Nvkdq6zOy0LMsHPYhNMCyYHM+x00jQcmvSRfkTk0a0h" +
            "BRdJv/0Z4vNWh2UuQjlTBU5ZL+WMFjgL6hE31rajOzQmC5WbY24/ZvyQJI/2Y3fdQQvjNsulf7Q73WNWi7jeG2ayIka1OZYUZU0V" +
            "Jgm1Fitg31CVMTNDSv+o3M7Z0UGVxSqud6MZasfv9kfU70hiKRoQMdHt1Uoj9IRkXysCPkmtmH+izosaUgQLlmxlxZS97" +
            "oxsczcvVJeG5U92h5iKqibbWmQSiapastfP9pXoH4u0KiFQW+6toedI/fz0xFrRfrKerCXhVipc3oJdVuUfC8UGZEfEHs" +
            "N9NyCG7E7ZF8YKh92heJi2HWZo1bSdNUeY9UpPqKPb3bF1e2idGojCoOaESv9lZtwhu2IGG1DWVerEEGfnwyhoRoIYQMDdv" +
            "BFnuahSh8OMCWdU2rjNG8UQ2ElBGsOQV4n+eKsqR/FLjGpoO7W0F6xpKYp2WtrtzrBTuWprOGSLqmPU0NGkthdYeEwhQ4f92d" +
            "LOSDSXS2nTiyF33B12J0TZFwzRtdH0sCyryWA5V2vVcwm2KFmYJuJEdgGhyZQD1fbFyZXbGL6Atl/G3lxgi2M6d0f3G" +
            "DXuVg0SjHyzTGgL+9ZZ7OwsoBvajWevaMYtzTb02ITPRzdz4kZqxL05NubuDm1k0nie3GW/k/qykg7o6GmurcGjrXnCD" +
            "Qe3TvjgYPf20HNmfC882BN6hgOuJdIcnliBvNBzIj40GJWjVEqkiEgRamkbIjomb3/OR8go4wqMwPDoJBBG0xVoQKKTnEIz" +
            "K46qmCMf4ZAjKBxfQVpAmk6hjTIauyYITZmvWOPT+fQ+A2fk7BNASc8g5fv4HqsHctIARrBPoNY2Rp6E0Qm9z65IjKKET4nw" +
            "YO9F173bQycN+HS2sxkdNdML22VpAouNjxW/GKON8pfhxFgkTDcbKcfS4BdkcN+EZXLfhIFoDXKxO94sl7ibKb2J0psUupbSi7B" +
            "FoRxQfRRrH5SBdsCOkBO3pFjxqn3MfI5WKoyHypj5N7WYsUr83fAivoPaYKPvrJUr4XR8WbmB6EDP63R6C6/nI2E9b+UI1xcm1q" +
            "ZyMJXDdDmcLocj5bC/HPrKAYkio++eKYcz5XCC8TLl0FUODsZQ6HI5PMZYaabmK4c6JkDK4R3GHWX0OkbZMM/8KGpHGKOL8WYYXS74" +
            "UBREpjPDDE0xN6OMi6F5Cz52Llx/Ubiy6rXrMvqnOJRHmqotpH4pmy31S719u3bWW6xw1XpL/eo6Z8MNFrfLBG6L0+K+1gP" +
            "VYLmqDDa8WT+7094iPNJqX/6PX1j9ZoNdeMD2OmyYe+X1opKPd9sb6AsVEPwtJ9yFNbCTlC9gsNngaq0JhGuWGZZEwn2GtIGr" +
            "NQBPDGYDp9cYDILdbouE7dAXtluFkkhYwMoI/PQyOLEMMssguAx8y5TFYsTVBAOutljJehZw386d1WrMNOomsFzbQAOuwnidN9Qj5L" +
            "Q4xSao550bxsf595vFzBtvwx0On88xdxx0wAXXNy/5+HVcx6MVDXOn3yqdvXBiLvad2bPmD+ceZWv52vx7/AVNJ6klYd/aq3TXLie" +
            "Way1ez3Kd7brrNH1huG6Jzd4XXmoTZrww7YXXvDDlhRk213lB9LLIafpJUz0Nvp4Fvx6Dxqkeo8awl9RftRzq11zfsNajbVh7ff2" +
            "aq+gS3C5tmW05Dq3bVXXtoU3uyu+F7vvrxuiXDnwp2jjz5nde2OQeOH7PA43R/Qf2Rxvfnx76ZS8kv+dtP/Kl9l2baj3r" +
            "bt2/88Sz1XO/e3zLcGTTrY013g077o788M0qJ311ZjVyYo10ZCmZ8o2SMk1xsanMVHG1XosbRG+0WnGjWM19YStfbDKa+sJG65" +
            "EK2F8B6QrwVoCpAt6pgNMV8BijdFVAE6PPM/oZRuxjYusUudNMWdF8mqntZzoORtEt6tjL25f17UKzqrWvVhOo1B3W0oJjBjXYpb" +
            "R1sQtECyaO3/7ws/2Jv/n23C1vzv7ksXH4CN7779/x8ne/Pnvg4QtzzfYGtfJ7fvbPSk403ZiTMrIcO/emMrPZbrQDaMuNNovVY" +
            "tQKDtGMTY3NatcL+qsxT0V9eJhYQCto+8I2wWoW4TURMiL4RFp3VnMM+5K4Sf1Cw7KPle03uBj4mnIseREgrl9YFzz5k9k3Hxvn" +
            "Wj6Zuf/LsPsbc6fn7oXib/79306cfIDrnBMKC3nq+Xteqpr9rb2B64S7Hvry7EsHaA9vwYL347pKoMT3A43RUKLVYbw6nmh4bGHeet" +
            "QIo0bIGCFmhB4jtBpBNILZCIIRZowwbYQ3jPCyEU4VuGuNsMII7y6iP26EYwUjkYJYHROzMTsbFqS/aoQ886QYEpiD14zATRlBNs" +
            "IJZiDItJUokKew0PziU63v4ql28Vj7rBMxe8lph72zuq5yIeNlRfDs+Oxvx8e5pePBQiobKgpnmeZBzN0ScpuvbkmR1qrXl2pLy" +
            "2waYrLgrtBx+EgpNZT2hZcUGaykDJp8ZSCWwXQZnCgDuvN34bFVX684V+71i3qXlp5FYHEvagEQ7j4Fr45Ln7xyau6G8XE4zj0jv" +
            "E9D+tgqPPvxw4UQP3Gwn8HEhjFOatqJiVihz/eBpdRkEqxGs8FQVGQW+CU2Y6mlFDezxQJm/E1qKBJMgKEXg/WCDd61wRs2eNk" +
            "Gp2zwuA2O2eCrNsjbIGaDHhu02mCtDVbYAA9xwQZ/rvz6z1FYLC0wmSkbcLINTtjgqA1GbZCxQdAGPhvU2UC0gdkG00zoMoEuG3" +
            "yq7Jc0xxU7ZPHDkV2FvcrKZF3vxdPas7BVraxe62nX0BpBPbBa8U4eeCf8dK7tQXj1BXj7qdlXTx2YnbkXDv0b/KKBVunDj3W0Wn" +
            "D33F1CYnaP8nzsmX9Pcy8+U0Q8effbtBUGoiVOl2hfhk+SZct4vd6Cx4rAX4V7c8mICwZcEHBBlQv+6IKfuwCmXHDKBY+74JgLvuq" +
            "CoAtaXbDWBStcILhgPY4LLnjXBS8zuWOL2GYXcNMuyLgg4gKfC0SXkrq+i/tlUZ76lCThzqEP3UvOL/XZhekoBbdILGbiZA8tD2" +
            "KcxWzFI0vdWRoh/9HRue/PHQMJei/8usXuf3H3PDl34XzPZN04nL87ULUWItCCY+fGueknVjfMnZn70dxbc2dWXgN3VVx/fQX7r" +
            "5W7+qHg8tIf9pk2/oE4lP/5/qH1tZ9d/BcHMzqJGaV/AnIqCfWKnHN+ctuCEFz2149eux6fgr8mlTg2CDnyNaqK9w2aW8kG7imy" +
            "RfNj3P820sOkfwgr4Rgc45ZzY/xK/k5hm/DvzGIJ8ag+OWImXnI7Ai/xPyI84y6H1ILfWxdiAJS8VYU5UkQGVJjHx8uwCgsoc1C" +
            "FNcRIHlRhLe7z76pwEbmTnFJhHb7zelRYT0qhWYWLIQVBFS4h13AvLPx77eF+qcJG0sDrVLiUVPCNNHqB/us2zt+mwkBEgVdhjp" +
            "QKbhXmyfXCahUWUGZQhTWkQrhXhbVkufBtFS4iF4TTKqwjKzUnVVhPrtG8rcLF3K80/6XCJWSd7hcqbCC360tU2Eju0Bd8l" +
            "ZK1+tdbk4PJfPLOeEyMSXlJjKYz+7LJwUReXBldJa6pW10ntqXTg0NxsSWdzaSzUj6ZTnmKWy4XWyNuQxPtUr5G3JyKejqT/XFF" +
            "VuyOZ5MD2+KDe4ak7KZcNJ6KxbNirXi5xOX4rfFsjiJrPKs9DReZl8smc6Ik5rNSLD4sZXeL6YFL4xCz8cFkLh/PIjGZEns93R4x" +
            "KOXjqbwopWJiz4Ji18BAMhpnxGg8m5dQOJ1PYKR37Mkmc7FklHrLeRYWsCgb3fn4SFy8Wcrn47l0qlnKoS+MrCeZSudqxL2JZ" +
            "DQh7pVyYiyeSw6mkNm/T7xUR0SuhGtJpdIjaHIkXoNxD2TjuUQyNSjm6JJVbTGfkPJ00cPxfDYZlYaG9mHJhjOo1Y812pvMJ9Dxc" +
            "Dwn3hLfK25LD0uppzxKKJibAcypmBzOZNMjLMbaXDQbj6fQmRST+pNDyTxaS0hZKYoZw7QlozmWEUyEmJFStf492XQmjpHe1tZ5U" +
            "RADVLKZSw+NoGcqnYrHY9Qjhj0SH0IldDyUTu+m6xlIZzHQWD5RuyjygXQqj6ppUYrFcOGYrXR0zzCtE6Y5XwhOimbTyMsMSXm" +
            "0MpzzJPL5zI1e7969ez2SWpooVsaDlr2fx8vvy8TVemSpleGhTix/ipZuD6svXUT35k6xK4P5CWBwoipQIxY6c7VnteoC05jM5HO" +
            "eXHLIk84OersCnaSVJMkgjjyOO0mcxPDBFSMS4hJCUZImGbKPZJlUAqkiWYnUVXhfQ+rIahwiaUOpNPKHUF8kLQhnUYvOErObJik" +
            "8RosZ5/OtrUFomxpFO9OuQWgz6kfRQifq9SN3sV2RdDNKEo9ZqjlI9mAcElI2kRxqxVEmxiRE/Jkn/kkbf4p/K4NyC5w1GNdqH" +
            "A1X1PxTdpNoSWSZzjMOjXSYRb8baWnU+7x8iCgXZ9XLISfOsBizSm33okQ3kwoyTZqJPPOWYlI9V/DYhR4HUD/KKlmQjDLbtCM" +
            "Uy2mEE2pO78B8Z1kEMaZXWFsOPX+6AlfujW4W3QjzeTOjUzzHeM2I59R1KTnrYVGkkUpzsRcjoX4TDJZYPmNMm/ZYStXsx64TP" +
            "9ePqOpKal1SzMeIGiXVqVHzPcDmHPObQh8ii0+p8qW+RZYniWVdqfQwcvNMNor0IfzsU3fZMGZF8dWv7qO9bFcm1BUPM7siuQXve1" +
            "lXpFndUk4Xq/HFrCh9M6D2qch0Mwin2SoKeaxltaEribNIKSSxnd+PGkPMtxJbgnWHxGobV2udZyso5CumrpRGnWGUWuJnfUH" +
            "3e1zN6W14TnRe0aKSwcW9SWsyxOLNLbKdYtHGFtaoZJtKDamelBUPsfNo90J9Bli/KRmNMWu1n5HzAZabvOo1zSKK4UepuNJbadT" +
            "dw+qh7Celm/OfypzE8ptW9TLsVMqrsQyz/ZFgHZghN+KLpRejox8P68PFuyaq7hmPGrP3f61H48qwDC7eH9mFWIYxxk5196cWdt2e" +
            "Rfu3UIluPIM62XmRUfsnoGZOvMwC3TWXn5mr2Zl56SqUbkwinmfx5FguPWwNg8jvQg+d9B1a+UVwAEO6wjWhD27qhzgBSMAg/qx" +
            "3QITcAn2kFzaRRvDh3Ye8Zry3IE7vHmgkoyjXiPSbEN+I9A14djpwbsLRheMIDgGHIlGHEl68e1W8FvEa1DiDM7BBqU1Ipfcti" +
            "LfjvU29B5Dux7tfxTcjjncSgSJ8CW9i82kQfCdhehbOzII4C/s/huDHMPrB0Q+4/5xZ5Xh65vQM13W+7/zT5/m682A6Dzpyznwue" +
            "C5yLnPuxDltsek9MJD/AMuvp9c53mk82/uvjb/qJWdxZWfrzgbPjp6Vz2rOAt/7K77cYZ4Sp+qmMlOjU69NTU/NTOlGXzj6Ave" +
            "D570O0/OO5znHya6T+0/ykSfB9KTjSS74rci3uKOPgOkRxyPeR/iHH/I4Hmpb7njg+LWO6eMzx7nJ+amTx42WwPPQBZ2kEXN4y" +
            "0l+3vH0pjK4GZdlwtmBw4ujC0caxxEc+JsHxR04vNDpW8f3fRNK7rffX33/F+8/dL8mc8/oPUfv4UcPHD3APT1yeoTLBVc50qlq" +
            "R6rtOsfV9Ut7i+r5Xi26Qe++zf2VKwORPp+jD4V2bK9zbG9b5VhSb+3V4IIFFDTxDr6J7+LT/BH+NF+k2xZc7tiKYzo4E+R8Qb0h" +
            "YOpydHm7+Mn5aV+8w4nWtmS2jG7hNwdWOdrb1jlMbY42b9uZtnfazrdp+9rgMfwGng6cDvC+wCpvwBdY7gxc027vLa8v67WA" +
            "qddcb+rlAAtdT3q9pnkTZzL1mfabeBNpItxoOWhgEo5O9HRXV3dMFs1v65B1wR0yHJQru+ns27pd1h6USe/2HaEJgK+HDxw+T" +
            "JqXdchrukNyZFm4Q44h4KPAKALmZRPlpDmcy+Wr2QXV1QjvwZlU76lG4q6cQiULfFKdgxweUTmmBNVUQMEB52rKQwLVA9TelSN0" +
            "osxqRYlq51RzTFmZGLB01/8AkCCaowplbmRzdHJlYW0KZW5kb2JqCgo2IDAgb2JqCjU1ODkKZW5kb2JqCgo3IDAgb2JqCjw8L1R5c" +
            "GUvRm9udERlc2NyaXB0b3IvRm9udE5hbWUvQkFBQUFBK0xpYmVyYXRpb25TZXJpZgovRmxhZ3MgNAovRm9udEJCb3hbLTU0MyAtMz" +
            "AzIDEyNzcgOTgxXS9JdGFsaWNBbmdsZSAwCi9Bc2NlbnQgMAovRGVzY2VudCAwCi9DYXBIZWlnaHQgOTgxCi9TdGVtViA4MAov" +
            "Rm9udEZpbGUyIDUgMCBSCj4+CmVuZG9iagoKOCAwIG9iago8PC9MZW5ndGggMjY4L0ZpbHRlci9GbGF0ZURlY29kZT4+CnN0cmVhb" +
            "Qp4nF2Ry27DIBBF93wFy3QRgR2naSTLUpQ2khd9qG4+AMPYRaoBYbzw35eBtJW6AJ15XHRnYOf2sTU6sDdvZQeBDtooD7NdvATaw6g" +
            "NKUqqtAy3KN1yEo6wqO3WOcDUmsHWNWHvsTYHv9LNSdke7gh79Qq8NiPdXM9djLvFuS+YwATKSdNQBUN851m4FzEBS6ptq2JZh3Ub" +
            "JX8NH6sDWqa4yFakVTA7IcELMwKpOW9ofbk0BIz6Vyt4lvSD/BQ+thaxlfN91UQuE1d75F3O75CrxCVH3ud84vvMJfIha4/ID5mf" +
            "kI+ZC+RT5kMydnOAFnGHP6NTuXgfx06LTvPipNrA718461CVzjeEPIJsCmVuZHN0cmVhbQplbmRvYmoKCjkgMCBvYmoKPDwvVHlw" +
            "ZS9Gb250L1N1YnR5cGUvVHJ1ZVR5cGUvQmFzZUZvbnQvQkFBQUFBK0xpYmVyYXRpb25TZXJpZgovRmlyc3RDaGFyIDAKL0xhc3RDa" +
            "GFyIDEwCi9XaWR0aHNbNzc3IDYxMCA2MTAgNTU2IDI1MCA1NTYgNjY2IDMzMyA3MjIgNzIyIDcyMiBdCi9Gb250RGVzY3JpcH" +
            "RvciA3IDAgUgovVG9Vbmljb2RlIDggMCBSCj4+CmVuZG9iagoKMTAgMCBvYmoKPDwvRjEgOSAwIFIKPj4KZW5kb2JqCgoxMSAw" +
            "IG9iago8PC9Gb250IDEwIDAgUgovUHJvY1NldFsvUERGL1RleHRdCj4+CmVuZG9iagoKMSAwIG9iago8PC9UeXBlL1BhZ2UvUGFy" +
            "ZW50IDQgMCBSL1Jlc291cmNlcyAxMSAwIFIvTWVkaWFCb3hbMCAwIDU5NS4zMDM5MzcwMDc4NzQgODQxLjg4OTc2Mzc3OTUyOF0v" +
            "R3JvdXA8PC9TL1RyYW5zcGFyZW5jeS9DUy9EZXZpY2VSR0IvSSB0cnVlPj4vQ29udGVudHMgMiAwIFI+PgplbmRvYmoKCjQgMCBv" +
            "YmoKPDwvVHlwZS9QYWdlcwovUmVzb3VyY2VzIDExIDAgUgovTWVkaWFCb3hbIDAgMCA1OTUuMzAzOTM3MDA3ODc0IDg0MS44ODk3N" +
            "jM3Nzk1MjggXQovS2lkc1sgMSAwIFIgXQovQ291bnQgMT4+CmVuZG9iagoKMTIgMCBvYmoKPDwvVHlwZS9DYXRhbG9nL1BhZ2VzIDQg" +
            "MCBSCi9PcGVuQWN0aW9uWzEgMCBSIC9YWVogbnVsbCBudWxsIDBdCi9MYW5nKHB0LVBUKQo+PgplbmRvYmoKCjEzIDAgb2JqCjw8L" +
            "0NyZWF0b3I8RkVGRjAwNTcwMDcyMDA2OTAwNzQwMDY1MDA3Mj4KL1Byb2R1Y2VyPEZFRkYwMDRDMDA2OTAwNjIwMDcyMDA2NTAwNEY" +
            "wMDY2MDA2NjAwNjkwMDYzMDA2NTAwMjAwMDM3MDAyRTAwMzM+Ci9DcmVhdGlvbkRhdGUoRDoyMDIzMTEwMjE1MzYyOVonKT4+CmVu" +
            "ZG9iagoKeHJlZgowIDE0CjAwMDAwMDAwMDAgNjU1MzUgZiAKMDAwMDAwNjc1NiAwMDAwMCBuIAowMDAwMDAwMDE5IDAwMDAwIG4gCjAw" +
            "MDAwMDAyMjggMDAwMDAgbiAKMDAwMDAwNjkyNSAwMDAwMCBuIAowMDAwMDAwMjQ4IDAwMDAwIG4gCjAwMDAwMDU5MjEgMDAwMDAgbi" +
            "AKMDAwMDAwNTk0MiAwMDAwMCBuIAowMDAwMDA2MTMyIDAwMDAwIG4gCjAwMDAwMDY0NjkgMDAwMDAgbiAKMDAwMDAwNjY2OSAwMDAwMC" +
            "BuIAowMDAwMDA2NzAxIDAwMDAwIG4gCjAwMDAwMDcwNTAgMDAwMDAgbiAKMDAwMDAwNzE0NyAwMDAwMCBuIAp0cmFpbGVyCjw8L1" +
            "NpemUgMTQvUm9vdCAxMiAwIFIKL0luZm8gMTMgMCBSCi9JRCBbIDxEQkQxMDQ1NkNDOEUwQ0E5MENEQ0JENzZGRDZCNjBCOT" +
            "4KPERCRDEwNDU2Q0M4RTBDQTkwQ0RDQkQ3NkZENkI2MEI5PiBdCi9Eb2NDaGVja3N1bSAvNkY5NkM0MUZGMjc1MDkwQUFENzlCRkE2Rjc0ODA" +
            "5Q0MKPj4Kc3RhcnR4cmVmCjczMTcKJSVFT0YK";
//</editor-fold>
}
