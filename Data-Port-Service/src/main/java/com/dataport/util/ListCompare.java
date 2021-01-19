package com.dataport.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class ListCompare {

    List<String> result = null;

    public List<String> compare(List<String> originalIds, List<String> existingIds) {
        result = new ArrayList<>();
        Map<String, String> originalIdMap = prepareIdMap(originalIds);
        for (String id : existingIds) {
            if (!originalIdMap.containsKey(id)) {
                result.add(id);
            }
        }
        return result;
    }

    private Map<String, String> prepareIdMap(List<String> ids) {
        Map<String, String> idMap = new HashMap<>();
        ids.forEach(id -> {
            if (!id.equals("")) { idMap.put(id, "test data"); }
        });
        return idMap;
    }
}
