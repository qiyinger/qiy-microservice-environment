package swust.qiy.microservice.environment.auth.config;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qiying
 * @create 2019/4/15
 */
@Configuration
@Slf4j
public class MqConfiguration {

  @Autowired
  private ApplicationEventPublisher publisher;

  /**
   * 初始化rocketmq消息监听方式的消费者
   */
  @Bean
  public DefaultMQPushConsumer defaultMQPushConsumer() throws MQClientException {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("micro_consumer");
    consumer.setNamesrvAddr("localhost:9876");
    consumer.setInstanceName("auth");
    consumer.subscribe("micro_route", "gateway");

    consumer.registerMessageListener((List<MessageExt> msgs, ConsumeOrderlyContext context) -> {
      try {
        context.setAutoCommit(true);
        if (msgs.size() == 0) {
          return ConsumeOrderlyStatus.SUCCESS;
        }
        this.publisher.publishEvent(new RocketMqEvent(msgs, consumer));
      } catch (Exception e) {
        e.printStackTrace();
        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
      }
      // 如果没有return success，consumer会重复消费此信息，直到success。
      return ConsumeOrderlyStatus.SUCCESS;
    });
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(
              5000);// 延迟5秒再启动，主要是等待spring事件监听相关程序初始化完成，否则，回出现对RocketMQ的消息进行消费后立即发布消息到达的事件，然而此事件的监听程序还未初始化，从而造成消息的丢失
          /**
           * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
           */
          try {
            consumer.start();
          } catch (Exception e) {
            log.info("RocketMq pushConsumer Start failure!!!.");
            log.error(e.getMessage(), e);
          }
          log.info("RocketMq pushConsumer Started.");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

    }).start();
    return consumer;
  }

}
