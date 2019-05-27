/**
 * 
 */
package swust.qiy.microservice.gateway.rateLimit;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Limit by routeId
 * 
 * @author lizw@primeton.com
 */
public class RouteKeyResolver implements KeyResolver, Ordered {

	@Override
	public Mono<String> resolve(ServerWebExchange exchange) {
		Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
		String key = null == route ? null : route.getId();
		key = StringUtils.isBlank(key) ? "RouteKeyResolver" : key; //$NON-NLS-1$
		return Mono.just(key);
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
