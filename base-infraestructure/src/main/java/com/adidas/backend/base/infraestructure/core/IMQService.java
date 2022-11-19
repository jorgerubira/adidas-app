package com.adidas.backend.base.infraestructure.core;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


public interface IMQService  {
    
    public void send(String topic, Object params) throws InterruptedException, ExecutionException, TimeoutException;
    public <R> R sendAndReceive(String topic, String group, Object params, Class<R> returnClass) throws InterruptedException, ExecutionException, TimeoutException;
    public <R> R sendAndReceive(String topic, Object params, Class<R> returnClass) throws InterruptedException, ExecutionException, TimeoutException;
    public <R> R receive(String topic, String group, Class<R> returnClass) throws InterruptedException, ExecutionException, TimeoutException;
    public <R> R receive(String topic, Class<R> returnClass) throws InterruptedException, ExecutionException, TimeoutException;
    
}
