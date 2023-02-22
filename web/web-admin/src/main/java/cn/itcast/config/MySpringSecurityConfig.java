package cn.itcast.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // 声明当前类是一个配置类，注意，当前配置类也需要被扫描
@EnableWebSecurity // 开启Spring Security的自动配置，会自动给我们生成一个登陆页面
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启Controller中方法的权限控制
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // 在内存中设置一个认证的用户名和密码
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("");
    }*/


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //必须调用父类的方法，否则认证过程将失效，除非当前方法配置了认证和授权的过程
        /*super.configure(http);
        // 允许iframe嵌套显示
        http.headers().frameOptions().disable();*/
        // 允许iframe显示
        http.headers().frameOptions().sameOrigin();
        // 配置可以匿名访问的资源
        http.authorizeRequests().antMatchers("/static/**","/login").permitAll().anyRequest().authenticated();
        // 自定义登陆界面
        http.formLogin().loginPage("/login") // 配置去自定义页面访问的路径
                        .defaultSuccessUrl("/"); // 配置登录成功之前去往的地址

        // 配置登出的地址及登出成功之后去往的地址
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");
        // 关闭跨域请求伪造
        http.csrf().disable();
        // 配置自定义的无权限的访问的处理器
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());


    }

    // 创建一个密码加密器放到IOC容器中
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(); // 上面用什么加密就new什么
    }



}
