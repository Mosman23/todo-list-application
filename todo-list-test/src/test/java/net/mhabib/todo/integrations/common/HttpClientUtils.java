package net.mhabib.todo.integrations.common;

import java.io.IOException;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

public class HttpClientUtils {

    private static final Logger LOGGER = Logger.getLogger(HttpClientUtils.class.getSimpleName());

    private String baseUrl;

    public HttpClientUtils(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public HttpResponse post(final String path, final String body) throws IOException {
        return Request.Post(getUrlForPath(path)).bodyString(body, ContentType.APPLICATION_JSON).execute().returnResponse();
    }

    public HttpResponse put(final String path, final String body) throws IOException {
        return Request.Put(getUrlForPath(path)).bodyString(body, ContentType.APPLICATION_JSON).execute().returnResponse();
    }

    public HttpResponse patch(final String path, final String body) throws IOException {
        return Request.Patch(getUrlForPath(path)).bodyString(body, ContentType.APPLICATION_JSON).execute().returnResponse();
    }

    public HttpResponse delete(final String path) throws IOException {
        return Request.Delete(getUrlForPath(path)).execute().returnResponse();
    }

    public HttpResponse get(final String path) throws IOException {
        return Request.Get(getUrlForPath(path)).execute().returnResponse();
    }

    public HttpResponse options(final String path) throws IOException {
        return Request.Options(getUrlForPath(path)).execute().returnResponse();
    }

    public HttpResponse headers(final String path) throws IOException {
        return Request.Head(getUrlForPath(path)).execute().returnResponse();
    }

    private String getUrlForPath(String path) {
        if (!path.startsWith("/")) {
            baseUrl += "/";
        }
        return baseUrl + path;
    }

}
