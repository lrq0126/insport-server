package com.example.warehouse.DO.track17;

import lombok.Data;

import java.util.List;

@Data
public class ProvidersDo {

    private ProviderDo provider;

    private String service_type;
    private String latest_sync_status;
    private String latest_sync_time;
    private String events_hash;
    private List<ProviderEventDo> events;

}
