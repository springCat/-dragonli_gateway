package org.springcat.gateway;

import org.springcat.dragonli.core.config.ConfigUtil;
import org.springcat.dragonli.core.config.SettingGroup;
import org.springcat.dragonli.core.consul.ConsulConf;
import org.springcat.dragonli.core.consul.ConsulUtil;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiGateWayFilter extends HttpFilter {

    private final static ApiGateWayConf apiGateWayConf;

    private final static ApiGatewayInvoke apiGatewayInvoke;

    static {
        apiGateWayConf = ApiGateWayConf.GetInstance();
        apiGatewayInvoke = ApiGatewayInvokeStarter.initApiGatewayInvoke(apiGateWayConf);

        ConsulConf consulConf = ConfigUtil.getPrjConf(SettingGroup.consul);
        ConsulUtil.init(consulConf);
    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        apiGatewayInvoke.invokePost(req, res);
    }
}
