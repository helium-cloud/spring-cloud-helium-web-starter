package org.helium.web.common.common.utils;
import com.google.gson.Gson;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author coral
 */
public class ObjectUtil {

    public static String mapToString(Map<String, String[]> paramMap){

        if (paramMap == null) {
            return "";
        }
        Map<String, Object> params = new HashMap<>(16);
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {

            String key = param.getKey();
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            String obj = StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "你是看不见我的" : paramValue;
            params.put(key,obj);
        }
        return new Gson().toJson(params);
    }

    public static String mapToStringAll(Map<String, String[]> paramMap){

        if (paramMap == null) {
            return "";
        }
        Map<String, Object> params = new HashMap<>(16);
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {

            String key = param.getKey();
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.put(key, paramValue);
        }
        return new Gson().toJson(params);
    }
}
