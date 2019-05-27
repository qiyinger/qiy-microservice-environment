/**
 * 
 */
package swust.qiy.microservice.gateway.rateLimit;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.ratelimit.PrincipalNameKeyResolver;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author lizw@primeton.com
 */
public class DefaultKeyResolver extends PrincipalNameKeyResolver implements Ordered {

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}

	@Override
	public Mono<String> resolve(ServerWebExchange exchange) {
		Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
		String key = null == route ? null : route.getId();
		key = StringUtils.isBlank(key) ? "RouteKeyResolver" : key;
		return Mono.just(key);
	}
	
}
