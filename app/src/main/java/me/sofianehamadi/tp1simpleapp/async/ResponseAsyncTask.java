package me.sofianehamadi.tp1simpleapp.async;

/**
 * Created by MISTERSOFT on 01/02/2017.
 */

import java.io.Serializable;

/**
 * Response that describe a AsyncTask response
 * @param <T> Returnede value
 */
public class ResponseAsyncTask<T extends Serializable> {
    private T content;
    private boolean success;

    public ResponseAsyncTask(T _content) {
        this.content = _content;
        this.success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

}