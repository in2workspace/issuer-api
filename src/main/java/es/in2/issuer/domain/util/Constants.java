package es.in2.issuer.domain.util;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String LEAR_CREDENTIAL = "LEARCredential";
    public static final String JWT_VC = "jwt_vc";
    public static final String CWT_VC = "cwt_vc";
    public static final String PRE_AUTHORIZATION_CODE = "pre-authorization_code";
    public static final String AUTHORIZATION_CODE = "authorization_code";

    public static final String GRANT_TYPE = "urn:ietf:params:oauth:grant-type:pre-authorized_code";
    public static final String CREDENTIAL_SUBJECT = "credentialSubject";

}
