package cn.widealpha.train.config;

import com.alipay.api.CertAlipayRequest;

import java.io.FileWriter;

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000118605589";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDXT4TOoq1Y3gsV8p/upzgInw0bRiScDjSTBBKHeweWLapgLKqitap4zUFC5Pi7/v8VVn+kikVi9ZFVERQ3vY487jRC/jBo6dJylBMGnIvKACHQABgI7VFk/v4Tr3OaIjEOtGRMRWGNYs9IR3S7jtw+jBfxlsIGWc+Z4X7cKTZphbUwganFCUyr+LQc1pafO/KYC5Q3TQbUozJ5QsW1/Y1Q3XCrHYC26ZOSDX+vablW0/jm0+8uoAbGsV4YBbviwnmN3ZbHtoCndEj3KoKfcxRWZNVsHq9tHIez5LiNczJFxaPhBLm2IyIkr3w0Yo78CkD2pHyRFhH8g/cmuH4L5xIhAgMBAAECggEAUBHuXbn5pTbOAJd+Co59NBeSFDZ9nttZieLV0TRbmenZlnKF8oSEpPAP+QSVy7QEwLauOU6Ch06zgWMcla4Um2wqW0+USS/LZ3jRqn/FLJXn/SZLyeYXguuhKUjyQLu+aq2DLyoOITpBw6tvKeDy3v/SV7Gs7hewMJW/S7i3132HQ8gn0/98jXu5HfEoMRbQBJY+/Et2+G5yFRffqDSPAr79X/8P1hTtxe77tIfQ/4b3Fn8PHdodgamRlvJTHhLtI3tlVcFzq9PrtEnh9slfc9encUaXMVim1uUxS/i0JOm4a0ezxx4fSdQQndIrSy2CnbgogE/XPZlxamOgb/kl0QKBgQDulLPXFdqP9fPukomqZoSG2ptL/UViWtRy6RL81X0pY+3L8XjMxKkJadoxXtGSKj2ZBNZyr5bfGTn/q+oBezmMop4VpoNTLf19YvLDFZhbcIl23bbTRcGvw4MffELga7ii7nRVLmcXva6yDAjxGIK1O1XjXDKsVhAh0vr0LZ/l7wKBgQDnB9+vAvfxeOIfpVWjuKn23ACGjQbPVSfAqRTNijMXFiybSEf6BBnCzkeKRqRHiG76hrXKgmJRhv1n6ZE5Pqyll+0FAQdh8DQ0qEjTJxVEq6IGm7QHbicJWpiNVSYRFskXwFq09cuE4LqDosu4DN09C3w+89hEhuRG5YXhY/8Y7wKBgQDtKT64kU3Ji2mnf11/2RbmYsq7OBJP2hCUBeLNGH+ptF8/O1Ok4d1SsTmnmCTlkj3XLXrrWGa2cYI5CDC+tjqX9VMGHJcxCWeRy9mcWYwxYWYUlgNWc2I9ETH4C3MDWbGZrUZRIqZycCkHbnU5DO9prokLUKxCqe6xYOP79JqdWwKBgQCcnD/LmY2OSU2v+hoFtAazawuIKk11etpJxdRuSMK9YbC3StZP6zuzTLY+TAjM3PWuKmdNcflIHuPk7vYdAhly33wyqjHg+D3LfFBxosrOFDLvm9j8OHWJ57oGBLSbXQDQcvqKNRzTZKvK1C6ZA09uNDzzR8VRHsfcL3cEBQ9GTQKBgGt5Nn27twCTLPdvSDfFy6ScuzS0eZ1iitWvgZ7GErEbzcluA3V8JCT5dlW0foDa8XVBP/rVlz2nDQwDxhXqZUaesmIjlCtQv7RTczWRCs7ve4DbcSaRFcyWIc5dySP+8qsHqgpmbKLXwYTBVVhgKf0pMQVbQY9CQNRP/eWu+CLk";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi3vX9uafru3C10ZzF0Z/Oss3DCNIw2eTg0JOmwPBq4MyIoWc1gy+OeG77EQ1202TVbUrWw3vMkrfS7VCTZJ3ouOWuYWIcV6OAQrdlv3A8qtI4qHWyMiPNZtzkMbeur64nPY3j5lBGtkaCBF27j269O0sJHjq4AgIOk2w+fcn5lP4DoIhbHYnU+b+JOdAh08aApP/F3q8mrXcNQpm4Do5uVvJU/odcsdenk3OboPJzsrOLLom2ue+iNe0cEgcPviQM+U1E4KNr/2lh7fYq2zSBkSENAyIKzc+cay+j3drSuGVuFHYj2tf+1N8ykMN7t4eNEj8wW7N467jsSLpvluLoQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "https://widealpha.top:8443/train/orderForm/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public static String app_cert_path = "/crt/appCertPublicKey.crt";
    public static String alipay_cert_path = "/crt/alipayCertPublicKey.crt";
    public static String alipay_root_cert_path = "/crt/alipayRootCert.crt";

    public static CertAlipayRequest certAlipayRequest() {
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        //设置网关地址
        certAlipayRequest.setServerUrl(gatewayUrl);
        //设置应用Id
        certAlipayRequest.setAppId(app_id);
        //设置应用私钥
        certAlipayRequest.setPrivateKey(merchant_private_key);
        //设置请求格式，固定值json
        certAlipayRequest.setFormat("json");
        //设置字符集
        certAlipayRequest.setCharset(charset);
        //设置签名类型
        certAlipayRequest.setSignType(sign_type);
        //设置应用公钥证书路径
        certAlipayRequest.setCertPath(app_cert_path);
        //设置支付宝公钥证书路径
        certAlipayRequest.setAlipayPublicCertPath(alipay_cert_path);
        //设置支付宝根证书路径
        certAlipayRequest.setRootCertPath(alipay_root_cert_path);
        return certAlipayRequest;
    }
}