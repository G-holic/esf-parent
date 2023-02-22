import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoderTest {

    /**
     * 测试BCryptPasswordEncoder密码加密器
     */

    @Test
    public void testBCryptPasswordEncoder(){

        // 创建加密器对象
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        // 对123456进行第一次加密
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println("encode = " + encode);

        /*
        第一次运行的结果：$2a$10$97inT2D3JqlIKAos6O8y4e3LWrYwIL1szxH8I9VMBJ7zGaev4CQPS
        第二次运行的结果：$2a$10$zlgiXuj2/tHM9KX0J9VKAuDcCD9GDBuzu5JtAdemtfpNAdyHzeMR2
        第三次运行的结果：$2a$10$vwtAEH.2DmnFrgWi1yOxCeWKLZzfcK1DSrbnGkZJ7Vs9LcxsTImwq
         */

        // 进行密码匹配
        boolean matches = bCryptPasswordEncoder
                .matches("123456", "$2a$10$97inT2D3JqlIKAos6O8y4e3LWrYwIL1szxH8I9VMBJ7zGaev4CQPS");
        System.out.println("matches = " + matches);
        boolean matches2 = bCryptPasswordEncoder
                .matches("123456", "$2a$10$zlgiXuj2/tHM9KX0J9VKAuDcCD9GDBuzu5JtAdemtfpNAdyHzeMR2");
        System.out.println("matches2 = " + matches2);
        boolean matches3 = bCryptPasswordEncoder
                .matches("123456", "$2a$10$vwtAEH.2DmnFrgWi1yOxCeWKLZzfcK1DSrbnGkZJ7Vs9LcxsTImwq");
        System.out.println("matches3 = " + matches3);

        boolean matches4 = bCryptPasswordEncoder
                .matches("123456", "$2a$10$o7rTcCnZIvOsj95Lu0RhVe0RjPm.HYKBlqDOwFqCBhkzPv3CX5CrW");
        System.out.println("matches4 = " + matches4);
    }
}
