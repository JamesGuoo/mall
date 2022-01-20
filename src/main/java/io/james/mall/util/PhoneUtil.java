package io.james.mall.util;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PhoneUtil {

    private final Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0-8])|(18[0-9]))\\d{8}$");

    public boolean isPhone(String phone) {
        return pattern.matcher(phone).matches();
    }

    public boolean isNotPhone(String phone) {
        return !isPhone(phone);
    }
}
