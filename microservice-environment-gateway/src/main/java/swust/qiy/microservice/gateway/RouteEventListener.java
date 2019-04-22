package swust.qiy.microservice.gateway;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.event.EventListener;
import swust.qiy.microservice.gateway.config.RocketMqEvent;
import swust.qiy.microservice.gateway.dao.RouteDao;
import swust.qiy.microservice.gateway.dao.RouteStrategyDao;
import swust.qiy.microservice.gateway.dao.StrategyCallDao;
import swust.qiy.microservice.gateway.dao.StrategyIpDao;
import swust.qiy.microservice.gateway.entity.RouteInfo;
import swust.qiy.microservice.gateway.entity.RouteStrategy;
import swust.qiy.microservice.gateway.entity.StrategyCall;
import swust.qiy.microservice.gateway.query.RouteInfoQuery;
import swust.qiy.microservice.gateway.query.RouteStrategyQuery;
import swust.qiy.microservice.gateway.rateLimit.TokenBucketRateLimiter;
import swust.qiy.microservice.gateway.route.RepositoryUtil;
import swust.qiy.microservice.gateway.util.RouteConvertUtil;
import swust.qiy.microservice.sdk.core.ApplicationProperties;

/**
 * @author qiying
 * @create 2019/4/15
 */
@Slf4j
public class RouteEventListener {

  @Resource
  private RouteDao routeDao;
  @Resource
  private RouteStrategyDao routeStrategyDao;
  @Resource
  private StrategyIpDao strategyIpDao;
  @Resource
  private StrategyCallDao strategyCallDao;
  @Autowired
  private RepositoryUtil repositoryUtil;
  @Autowired
  private ApplicationProperties applicationProperties;
  @Autowired
  private TokenBucketRateLimiter tokenBucketRateLimiter;

  @EventListener(condition = "#event.msgs[0].topic=='gateway' && #event.msgs[0].tags=='route'")
  public void commonRouteEventListener(RocketMqEvent event) {
    MessageExt msg = event.getMessageExt(0);
    String option = new String(msg.getBody());
    if ("ALL".equals(option)) {
      List<RouteInfo> routeInfos = routeDao.selectList(
          RouteInfoQuery.getInstance().setGatewayId(applicationProperties.getThisId())
              .toQueryWrapper());
      if (CollectionUtils.isEmpty(routeInfos)) {
        repositoryUtil.clear(true);
      }
      List<RouteDefinition> routeDefinitions = routeInfos.stream()
          .map(RouteConvertUtil::getRouteDefinition)
          .collect(Collectors.toList());
      repositoryUtil.save(routeDefinitions, true);
    } else if ("PUBLISH".equals(option)) {
      String code = msg.getKeys();
      List<RouteInfo> routeInfos = routeDao
          .selectList(RouteInfoQuery.getInstance()
              .setGatewayId(applicationProperties.getThisId())
              .setCode(code).toQueryWrapper());
      if (CollectionUtils.isEmpty(routeInfos)) {
        log.error("");
        return;
      }
      RouteInfo routeInfo = routeInfos.get(0);
      repositoryUtil.save(RouteConvertUtil.getRouteDefinition(routeInfo), true);
    } else if (("STOP".equals(option))) {
      String code = msg.getKeys();
      if (!repositoryUtil.contain(code)) {
        log.error("");
        return;
      }
      repositoryUtil.delete(code, true);
    }
  }

  @EventListener(condition = "#event.msgs[0].topic=='gateway' && #event.msgs[0].tags=='rate_limit'")
  public void rateLimitEventListener(RocketMqEvent event) {
    MessageExt messageExt = event.getMessageExt(0);
    Integer routeId = Integer.valueOf(messageExt.getKeys());
    List<RouteStrategy> routeStrategies = routeStrategyDao
        .selectList(RouteStrategyQuery.getInstance().setRouteId(routeId).toQueryWrapper());
    if (CollectionUtils.isEmpty(routeStrategies)) {
      return;
    }
    List<Integer> strategyIds = routeStrategies.stream().map(RouteStrategy::getStrategyId)
        .collect(Collectors.toList());
    List<StrategyCall> strategyCalls = strategyCallDao.selectBatchIds(strategyIds);
    tokenBucketRateLimiter.cleanTokenBucket(routeId);
  }

}
