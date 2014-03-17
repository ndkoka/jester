package org.jscep.jester;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.jscep.jester.io.BouncyCastleSignedDataDecoder;
import org.jscep.jester.io.EntityEncoder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import static org.junit.Assert.assertNotNull;

public class EstClientIT {
    @Test
    public void testObtainCaCertificates() throws IOException, Exception {
        HttpClient httpClient = getNewHttpClient();
        EstClient estClient = new EstClient(httpClient, new BouncyCastleSignedDataDecoder(), new EntityEncoder<CertificationRequest>() {
            @Override
            public void encode(OutputStream out, CertificationRequest... entity) throws IOException {
                
            }
        }, "localhost:8443");
        assertNotNull(estClient.obtainCaCertificates());
    }

    private HttpClient getNewHttpClient() throws Exception {
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), new AllowAllHostnameVerifier());
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();

    }
}