package swust.qiy.microservice.gateway.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import swust.qiy.microservice.core.mybatis.injector.BatchInjector;

/**
 * @author qiying
 * @create 2019/1/23
 */
@Configuration
@MapperScan("swust.qiy.microservice.management.dao")
public class MyBatisPlusConfiguration {

  @Bean
  public ISqlInjector batchInjector() {
    return new BatchInjector();
  }

  @Bean
  public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    paginationInterceptor.setDialectType(DbType.MYSQL.getDb());
    return paginationInterceptor;
  }

}
