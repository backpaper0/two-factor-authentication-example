import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

import com.example.otp.TimeBasedOneTimePasswordGenerator;

/**
 * OTP確認用のコード。
 *
 */
public class OneTimePassword {

    public static void main(final String[] args) throws Exception {

        //ここに鍵を入れる
        final String keyAsHex = "0000000000000000000000000000000000000000";

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = 0; i < keyAsHex.length(); i += 2) {
            final int b = Integer.valueOf(keyAsHex.substring(i, i + 2), 16);
            out.write(b);
        }

        final byte[] key = out.toByteArray();

        final TimeBasedOneTimePasswordGenerator generator = TimeBasedOneTimePasswordGenerator
                .builder()
                .algorithm("HmacSHA1")
                .build();

        while (true) {
            final int generated = generator.generate(key);
            System.out.printf("%06d%n", generated);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
