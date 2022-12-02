package com.idiots.authentication.lang;


import cn.hutool.core.lang.Pair;
import org.springframework.http.HttpMethod;

import java.util.LinkedList;
import java.util.List;

/**
 * @author neo-w7
 * Date 2022-12-03
 * Description
 */
public class Constant {
    public static final String V1 = "/api/v1";

    public static final List<Pair<HttpMethod, String>> WHITES = new LinkedList<>();

    static {
        WHITES.add(Pair.of(HttpMethod.POST, "/admin/login"));
        //WHITES.add(Pair.of(HttpMethod.POST, V1 + "/login"));
        WHITES.add(Pair.of(HttpMethod.POST, "/admin/logout"));
        //WHITES.add(Pair.of(HttpMethod.POST, V1 + "/logout"));
        WHITES.add(Pair.of(HttpMethod.GET, "/favicon.ico"));
        WHITES.add(Pair.of(HttpMethod.GET, "/swagger-ui.html"));
        WHITES.add(Pair.of(HttpMethod.GET, "/swagger-ui/**"));
        WHITES.add(Pair.of(HttpMethod.GET, "/v3/api/**"));
    }
}
