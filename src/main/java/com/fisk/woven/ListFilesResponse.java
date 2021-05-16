package com.fisk.woven;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListFilesResponse {

    @JsonProperty(value = "summary", required = true)
    List<DocumentProperties> summaryList;
    public ListFilesResponse(List<DocumentProperties> docList) {
        this.summaryList = docList;
    }

    public ListFilesResponse() {

    }
}
