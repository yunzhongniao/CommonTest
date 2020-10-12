package org.yunzhong.CommonTest.datacenter;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.yunzhong.CommonTest.model.DatacenterParamDef;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class Datacenter {

    private Map<String, DatacenterParamDef> params;

    public void init() {
    }

    public Map<String, Object> getValues() {
        return null;
    }
}
