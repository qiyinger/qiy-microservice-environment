package swust.qiy.microservice.gateway.access;

import java.util.List;

/**
 * @author qiying
 * @create 2019/4/22
 */
public class IpRepository {


  public void put(String routeId, List<String> ipList, boolean isBlank) {

  }

  public boolean isHitWhite(String routeId, String ip) {
    return true;
  }

  public boolean isHitBlank(String routeId, String ip) {
    return true;
  }


}
