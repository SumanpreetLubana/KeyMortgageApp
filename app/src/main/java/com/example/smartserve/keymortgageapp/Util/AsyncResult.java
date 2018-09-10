package com.example.smartserve.keymortgageapp.Util;

public interface AsyncResult<TData> {
    void success(TData data);
    void error(String error);



}
