package com.adidas.backend.base.domain.config;


public class MQTopics {
    
    public final static String SALE_CREATE = "sale-create";;
    public final static String SALE_GET_ALL = "sale-get-all";
    public final static String SALE_EVENT_ONCHANGE = "sale-event-onchange";
    
    public final static String QUEUE_CREATE = "queue-create";
    public final static String QUEUE_STARTED = "queue-started";
    public final static String QUEUE_PAUSE = "queue-pause";
    public final static String QUEUE_RESTART = "queue-restart";
    public final static String QUEUE_GET_ALL = "queue-get-all";
    public final static String QUEUE_EVENT_ONCHANGE = "queue-event-onchange";
    
    public final static String MEMBER_GET = "member-get";
    public final static String MEMBER_GET_ALL = "member-get-all";
    public final static String MEMBER_CREATE = "member-create";
    public final static String MEMBER_ADD_POINTS = "member-add-points";
    public final static String MEMBER_DELETE = "member-delete";
    public final static String MEMBER_EVENT_ONCHANGE = "member-event-onchange";
    
    public final static String EMAIL_SEND_PENDING = "email-send-pending";
    public final static String EMAIL_GET_ALL = "email-get-all";
    public final static String EMAIL_GET_PENDING = "email-get-pending";
    public final static String EMAIL_GET_ERRORS = "email-get-errors";
    public final static String EMAIL_EVENT_ONCHANGE = "email-event-onchange";
    
    public final static String EMAIL_SEND = "email-send";
    public final static String GLOBAL_UPDATE = "global-update";
    
   
}
