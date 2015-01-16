package com.ericxu131.exwechat.model;

/**
 *
 * @author eric
 */
public class AccessToken {

    private final String token;
    private final Long expiresTimestemp;

    public AccessToken(String token, Long expiresTimestemp) {
        this.token = token;
        this.expiresTimestemp = expiresTimestemp;
    }

    public String getToken() {
        return token;
    }

    public Long getExpiresTimestemp() {
        return expiresTimestemp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccessToken other = (AccessToken) obj;
        if ((this.token == null) ? (other.token != null) : !this.token.equals(other.token)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AccessToken{" + "token=" + token + ", expiresTimestemp=" + expiresTimestemp + '}';
    }

}
