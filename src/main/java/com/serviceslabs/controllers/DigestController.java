package com.serviceslabs.controllers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/digest")
public class DigestController {
  private static long currentTime = System.currentTimeMillis();

    public class Hash {
        private final String original;
        private final String sha1;

        public Hash(String original, String sha1) {
            this.original = original;
            this.sha1 = sha1;
        }
        public String getOriginal() { return original; }
        public String getSha1() { return sha1; }
    }

    @RequestMapping(value="/sha1", method = RequestMethod.GET)
    public @ResponseBody Hash getSha1(@RequestParam("string") String string) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(string.getBytes("utf8"));
            byte[] sha1Bytes = digest.digest();
            byte[] sha1Base64Bytes = Base64.encodeBase64(sha1Bytes);
            String sha1 = new String(sha1Base64Bytes);
            return new Hash( string, Long.toString(currentTime));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

	   /*

	       <bean
        class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix">
            <value>/WEB-INF/pages/</value>
        </property>
        <property name="suffix">
            <value>.jsp</value>
        </property>
    </bean>



	    */

}
