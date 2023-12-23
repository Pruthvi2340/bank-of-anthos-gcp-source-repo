package anthos.samples.bankofanthos.ledgermonolith;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JWTVerifierGenerator {

    private static final Logger LOGGER =
        LogManager.getLogger(JWTVerifierGenerator.class);

    @Bean (name = "verifier")
    public JWTVerifier generateJWTVerifier(
            @Value("${PUB_KEY_PATH}") final String publicKeyPath) {
        // load public key from file
        try {
            LOGGER.debug("Generating JWT token verifier");
            String keyStr =
                    new String(Files.readAllBytes(Paths.get(publicKeyPath)));
            keyStr = keyStr.replaceFirst("-----BEGIN PUBLIC KEY-----", "")
                    .replaceFirst("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] keyBytes = Base64.getDecoder().decode(keyStr);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(keyBytes);
            RSAPublicKey publicKey =
                    (RSAPublicKey) kf.generatePublic(keySpecX509);
            // Initialize JWT verifier.
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            return JWT.require(algorithm).build();
        } catch (IOException
                | NoSuchAlgorithmException
                | InvalidKeySpecException e) {
            LOGGER.error(String.format("Failed initializing JWT verifier: %s",
                e.toString()));
            throw new GenerateKeyException("Cannot generate key: ", e);
        }
    }

    public static class GenerateKeyException extends RuntimeException {
        public GenerateKeyException(String message, Exception e) {
            super(message, e);
        }
    }

}
