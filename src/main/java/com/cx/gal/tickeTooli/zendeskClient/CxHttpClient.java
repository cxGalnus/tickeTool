package com.cx.gal.tickeTooli.zendeskClient;



import com.cx.gal.tickeTooli.exceptions.TickeToolException;
import com.cx.gal.tickeTooli.zendeskClient.dto.UsersResponse;
import com.cx.gal.tickeTooli.zendeskClient.utils.HttpClientHelper;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;


import java.io.IOException;
import java.net.MalformedURLException;

import static com.cx.gal.tickeTooli.zendeskClient.utils.zendeskParam.TEMPLATE_TO_ENCODE;
import static com.cx.gal.tickeTooli.zendeskClient.utils.zendeskParam.USERS;


/**
 * Created by Galn on 05/02/2018.
 */
public class CxHttpClient {

    private Logger logi;
    private HttpClient apacheClient;
    private String token = "";
    private String apiToken = "W03A342tCA7s3HVJMLy4s2HQV8UJgpgtuk7oB8Er";
    private String rootUri = "https://internalcheckmarx.zendesk.com/api/v2/";
    private String email;


    private final HttpRequestInterceptor requestFilter = new HttpRequestInterceptor() {
        public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
            if (!token.isEmpty()) {
                httpRequest.addHeader(HttpHeaders.AUTHORIZATION, "Basic" + " " + token);
            }
        }
    };

    public CxHttpClient(String email, String password, Logger logi) throws MalformedURLException {
        this.logi = logi;
        this.email = email;

        //create httpclient
        HttpClientBuilder builder = HttpClientBuilder.create().addInterceptorFirst(requestFilter);
        builder.useSystemProperties();
        apacheClient = builder.build();
    }

    public UsersResponse login() throws IOException, TickeToolException {
        String credentials = TEMPLATE_TO_ENCODE.replace("{email}", email).replace("{apiToken}", apiToken);

        byte[] encodedBytes = Base64.encodeBase64(credentials.getBytes());
        token = new String(encodedBytes);
        //todo add logs shit
        return getRequest(USERS, ContentType.APPLICATION_JSON.toString(), UsersResponse.class, HttpStatus.SC_OK, "authenticate", false);
    }


    //GET REQUEST
    public <T> T getRequest(String relPath, String contentType, Class<T> responseType, int expectStatus, String failedMsg, boolean isCollection) throws IOException, TickeToolException {
        HttpGet get = new HttpGet(rootUri + relPath);
        return request(get, contentType, null, responseType, expectStatus, failedMsg, isCollection);
    }


    //POST REQUEST
    public <T> T postRequest(String relPath, String contentType, HttpEntity entity, Class<T> responseType, int expectStatus, String failedMsg) throws IOException, TickeToolException {
        HttpPost post = new HttpPost(rootUri + relPath);
        return request(post, contentType, entity, responseType, expectStatus, failedMsg, false);
    }

    //PUT REQUEST
    public <T> T putRequest(String relPath, String contentType, HttpEntity entity, Class<T> responseType, int expectStatus, String failedMsg) throws IOException, TickeToolException {
        HttpPut put = new HttpPut(rootUri + relPath);
        return request(put, contentType, entity, responseType, expectStatus, failedMsg, false);
    }

    private <T> T request(HttpRequestBase httpMethod, String contentType, HttpEntity entity, Class<T> responseType, int expectStatus, String failedMsg, boolean isCollection) throws IOException, TickeToolException {
        httpMethod.addHeader("Content-type", contentType);

        if (entity != null && httpMethod instanceof HttpEntityEnclosingRequestBase) { //Entity for Post methods
            ((HttpEntityEnclosingRequestBase) httpMethod).setEntity(entity);
        }
        HttpResponse response = null;

        try {
            response = apacheClient.execute(httpMethod);
            HttpClientHelper.validateResponse(response, expectStatus, "Failed to " + failedMsg);

            //extract response as object and return the link
            return HttpClientHelper.convertToObject(response, responseType, isCollection);
        } finally {
            httpMethod.releaseConnection();
            HttpClientUtils.closeQuietly(response);
        }
    }

    public void close() {
        HttpClientUtils.closeQuietly(apacheClient);
    }


}
