package myformework.springbootstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

@SpringBootApplication
@ComponentScans(value = {@ComponentScan("com.vuck.h_dao"),
        @ComponentScan("com.vuck.config"), @ComponentScan("com.vuck.action")})
//@MapperScan(basePackageClasses = MenuService.class)
@EntityScan("com.vuck.entity")
public class SpringbootstudyApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(SpringbootstudyApplication.class, args);
    }

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }
}
