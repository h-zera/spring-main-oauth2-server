package com.hzera.oauth2.spring.oauth2.server.common.domain;

public class PageRequestSortException extends HZeraBusinessException {

    public PageRequestSortException() {
        super("Requested sort query has either bad format or a nonexisting field", "SORT1");
    }

}
