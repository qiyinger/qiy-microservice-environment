package swust.qiy.microservice.gateway.config;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.ApplicationEvent;

/**
 * @author qiying
 * @create 2019/4/15
 */
public class RocketMqEvent extends ApplicationEvent {

  private static final long serialVersionUID = -4468405250074063206L;
  private DefaultMQPushConsumer consumer;
  private List<MessageExt> msgs;

  public RocketMqEvent(List<MessageExt> msgs, DefaultMQPushConsumer consumer) throws Exception {
    super(msgs);
    this.consumer = consumer;
    this.setMsgs(msgs);
  }


  public DefaultMQPushConsumer getConsumer() {
    return consumer;
  }

  public void setConsumer(DefaultMQPushConsumer consumer) {
    this.consumer = consumer;
  }

  public MessageExt getMessageExt(int idx) {
    return getMsgs().get(idx);
  }

  public List<MessageExt> getMsgs() {
    return msgs;
  }

  public void setMsgs(List<MessageExt> msgs) {
    this.msgs = msgs;
  }

}
